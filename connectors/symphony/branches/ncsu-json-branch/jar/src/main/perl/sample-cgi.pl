#!/usr/local/bin/perl


# sample CGI showing basic usage of Sirsi::API to deliver JSON

use strict;
use warnings;
use CGI::Carp qw(fatalsToBrowser);
use Sirsi::API;
use CGI;
use JSON;

# API calls produce marc8 encoded titles, names, etc.
# so if you want to use something more 'modern', you'll need this
# installable from CPAN
use MARC::Charset 'marc8_to_utf8';

binmode(STDOUT, ":utf8");

my $q = CGI->new();
my $remote_addr = $ENV{'REMOTE_ADDR'};

# list of IP addresses allowed to access this script; this is minimal
# security, but it's better than nothing at all.
my @allowed_ips = qw(127.0.0.1);

# PATH_INFO will typically start with a /
my $path_info = $ENV{PATH_INFO} || "/lookupUser";

check_access($remote_addr);

# always 10 chars, S + 8 digits + one of ' #$%+-.=?/' (digit, letter)
my $barcode_rgx = /^(S[0-9]{8}[ #\$%\+\-\.\/0-9A-Za-z_=\?])$/;

# maps PATH_INFO to a subroutine that performs the requisite call.
# to disable a function, simply comment out its entry in this hash

my %dispatch = (
	'/lookupUser'		=> \&lookup_user,
	'/cancelHold'		=> \&cancel_hold,
	'/placeHold'		=> \&place_hold,
	'/renewItem'		=> \&renew_item,
	'/checkIn'		=> \&check_in
);

# sample configuration values.

my $sirsi = Sirsi::API->new(
	sirsi_user_id => "CIRC",
	station_library => "MYLIBRARY",
	sirsi_access_code => "SOMEWACKYPASSWORD",
	log_path => '/var/log/ncip-api.log',
	debugFile => '/tmp/ncip-api.txt',
	jsonify => 1   # makes the output JSON-friendly
);

if ( exists $dispatch{$path_info} ) {
	my $result;
	eval {
		$result	 = &{ $dispatch{$path_info} }($q);
	};
	
	send_error(500, "An error ($@) occurred processing your request") if $@;

	if ( ref $result eq 'HASH' ) {
		my $json = JSON->new();
		my $out = marc8_to_utf8($json->pretty->encode($result));
		print $q->header(-type=>"application/json; charset=utf-8");
		$result->{command} = $path_info;
		print $out;
	} else {
		send_error(500, "Service did not return a response");
	}
	exit(0);
} else {
	send_error(404, "You have requested an unknown service"); 
}

# 'cleans' and retrieves parameters
# blows up if any parameter is not present; the goal
# is to make sure any values passed in to the Sirsi API call are
# 'untainted' (see perldoc perlsec)

# returns an arrayref in scalar context and an array in list context
sub get_params {
	my ($q, @params) = @_;
	my @rv = ();
	foreach my $p ( @params ) {
		my $v = $q->param($p);
		send_error(401,"The '$p' parameter is required")
			unless $v;

		# restrict to alphanumerics  except for item
		# IDs, which might contain wacky characters
		if ( $p ne 'item_id' ) {
			($v) = ($v =~ /([A-Za-z0-9\+#]+)/);
		} else {
			($v) = ($v =~ $barcode_rgx );
		}
		send_error(401, "The '$p' parameter is invalid")
			unless $v ne '';
		push @rv, $v;
	}
	# returns either (a copy of) the array or a reference to it
	# depending on the context in which the sub is called -- AC
	wantarray ? @rv :\@rv;
}

sub lookup_user {
	my ($q) = @_;
	my ($uid) = get_params($q,'uid');
	my $fiscal = $q->param('fiscalAccount') eq 'true';
	my $charges = $q->param('loanedItems') eq 'true';
	my $requests = $q->param('requestedItems') eq 'true';
	return $sirsi->lookup_user($uid,$fiscal,$charges,$requests);
}

sub check_in {
	my ($q) = @_;
	my $item_id = $q->param('item_id');

	return $sirsi->check_in($item_id);
}

sub cancel_hold {
	my ($q) = @_;
	my ($uid,$item_id,$hold_type) = get_params($q,'uid','item_id','hold_type');
	my $result  = $sirsi->cancel_hold($uid,$item_id,$hold_type);
	my %params = ( uid => $uid, hold_type => $hold_type, item => $item_id );
	$result->{query} = \%params;
	return $result;
}

sub place_hold {
	my ($q) = @_;
	my $uid = $q->param('uid');
	send_error(401, "The 'uid' (User ID) parameter is required") unless $uid;
	my $item_id = $q->param('item_id');
	send_error(401, "The 'item_id' (Item ID) parameter is required") unless $item_id;
	return $sirsi->place_hold($uid,$item_id);
}

sub renew_item {
	my ($q) = @_;
	my ( $item_id ) = get_params($q, 'item_id');
	return $sirsi->renew_item($item_id);
}

sub send_error {
	my ($status,$message) = @_;
	my %codes= (
		403 => "Forbidden",
		404 => "Not Found",
		500 => "Internal Server Error",
		401 => "Bad Request"
	);
	print $q->header(-type => "application/json",
			 -status=> "$status $codes{$status}"
	);
	print qq({"message" : "$message"});
	exit(0);
}
	
sub check_access {
	my ($ip) = @_;
	my @matches = grep { $_ eq $ip } @allowed_ips;
	if ( $#matches == 1 ) {
		send_error(403,"You are not permitted to access this service.");
	}
}
