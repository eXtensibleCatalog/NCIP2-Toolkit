package Sirsi::API;

=pod
	Module that provides an object-oriented API for making calls
	to the Sirsi API.  Originally written in support of NCIP-related
	operations.
=cut

use 5.010000;
use strict;
use warnings;

use Parse::RecDescent;


# Items to export into callers namespace by default. Note: do not export
# names by default without a very good reason. Use EXPORT_OK instead.
# Do not simply export all your public functions/methods/constants.

our $VERSION = '0.01';

# Base data structures

my %commands = (
	placeHold  => {
		command => "JZ",
		fullName => "Create Hold",
		description => "Place a hold on an item for a user",
		params => [ 
			{ userID => "UO" },
			{ itemID => "NQ" },
			{ holdType => "HK" } 
		],	
		messageCodes => {
			"209" => "Hold Placed",
			"218" => "User Blocked.",
			"774" => "User may not place any holds.",
			"444" => "User has too many holds.",
			"447" => "No items qualified for hold -- please see a librarian for help.",
			"722" => "User already has hold on this material",
			"753" => "User already has this material charged out.",
			"17286" => "Items at this location may not be put on hold."
		}
	},

	showRequests => {
		command => "Dt",
		fullName => "Display1 Request",
		description  => "Show requests for user",
		params => [ { "userID" => "UO" }],
		messageCodes => {
			"635" => "No Requests Found.",
			"9395" => "Unable to complete request"
		}
	},

	cancelHold => {
		command => "FZ",
		fullName => "Remove Hold, Part B",
		description => "Cancels a hold (without confirmation)",
		params => [ 	{ "userID" => "UO"},
				{"itemID" => "NQ"},
			    	{ "holdType" => "HK"}
			],
		messageCodes => {
			"649" => "Hold Cancelled"
		}
	},

	renewItem => { 
		"command" => "RV",
		fullName => "Renew Item",
		"params" => [ { "itemID" => "NQ" } ],
		"messageCodes" => { 
			"218" => "User Blocked",
			"238" => "User Cannot Check Out This Item",
			"141" => "Item is not renewable",
			"252" => "Item has holds",
			"809" => "Override required to change due date.  Please See the circulation desk"
		}
	},

	lookupUser => {
		command => "GC",
		fullName => "Display2 User",
		description => "Provides detailed information about a user, including circulation activities",
		params => [ {"userID" => "UO"} ],
		messageCodes => {
			"2" => "User not found."
		}
	},
	checkIn => {
		command => "EV",
		fullName => "Discharge Item",
		params => [ { "itemID" => "NQ" } ],
	},
	checkOut => {
		command => "CV",
		fullName => "Charge Item, Part B",
		params => [ { "userID" => "UO"},
                            { "itemID" => "NQ" } ]
	}

);

#$::RD_ERRORS =1;
#$::RD_HINT  =1;

=pod
we namespace things so that the 'compress' subroutine can combine
many keys into one object, e.g. instead of having an object that looks like

{ 	'user.name' : (name),
	'user.ID' : (ID), 
	'item.title' : (title) 
}

# we want to end up with  something more like
{
	user : { 
		name : (name), 
		ID : (id) 
	},
	item : (title)
}

This results in a generally 'nicer' JSON serialization whcih is easier to reconstitute
# into familiar objects.

=cut

my %codes = (
	DB => "admin.transactionTime",
	FE => "admin.stationLibrary",
	FF => "admin.loginAccessCode",

	UO => "user.ID",
	U6 => "user.userKey",
	Uk => "user.altID",
	UC => "user.groupID",
	UF => "user.responsibilityCode",
	UA => "user.name",
	uF => "user.firstName",
	uM => "user.middleName",
	uL => "user.surname",
	UL => "user.mailingAddress", # the *position* among addresses
	UT => "user.location",
	Ut => "user.createDate",
	Uf => "user.pin",
	Ib => "user.language",
	UR => "user.title",
	PG => "user.cat1",
	PH => "user.cat2",
	UZ => "user.birthYear",
	uY => "user.age",
	UM => "user.library",
	UN => "user.department",
	PE => "user.profileName",
	UV => "user.accessPolicy",
	UX => "user.environmentPolicy",
	UD => "user.datePrivsGranted",
	UQ => "user.datePrivsExpire",
	UK => "user.lastActivity",
	UH => "user.numCharges",
	UG => "user.numHolds",
	UI => "user.numBills",
	Uo => "user.numClaimsReturned",
	Uv => "user.numBills",
	UE => "user.extendedInfoFlag",
	UJ => "user.delinquencyCode",
	dA => "user.hasDelinquency",  # present only if there are; is empty
	Q5 => "user.webauthID",
	aD => "user.statusType",
	aC => "user.statusMessage",
	Ue => "user.numOpenOrders",
	Uy => "user.numOpenDistributions",
	US => "user.chargesAllowed",
	Lb => "user.listStatusAlerts",
	Ur => "user.numAvailableHolds",
	Uo => "user.numClaimsReturned",
	Uq => "user.estimatedOverdues",
	Up => "user.estimatedFines",
	U1 => "user.numRequests",
	PM => "user.routingsActive",
	PN => "user.numUnansweredRequests",
	Z5 => "user.creditAccountBalance",

	LV => "user.addresses.first",
	Lk => "user.addresses.second",
	Ll => "user.addresses.third",
	
	LD => "holds",
	PX => "hold.count", # only present if it's not 0
	HO => "hold.pickupLibrary",
	IS => "item.copyNumber",
	HG => "hold.comment",
	h7 => "item.unknown.h7",
	DH => 'hold.recallStatus',
	HB => "hold.dateExpires",
	HK => 'hold.type',
	'1h' => "hold.unknown.1h", # some kind of date?
	'2h' => "hold.queueLength",
	'3h' => "hold.inTransit",
	'4M' => "hold.mailFlag",
	tJ => "item.catalogKey",
	HA => "hold.datePlaced",
	GB => "hold.dateExpires",
	HF => "hold.position",
	IA => "item.authorName",
	IB => "item.title",
	I4 => "item.callNumberDisplay",
	NQ => "item.itemID", # aka "barcode"
	Jb => "item.type",
	NS => "item.library.shortName",
	ns => "item.library.displayName",
	Av => "unknown.Av", # not documented but shows up in holds,
	h6 => "hold.suspendStatus",
	h7 => "hold.noHoldOverrideFlag",
	HH => "hold.holdNumber",
	DX => "item.displayLibrary",
	HE => "hold.range", # SYSTEM, GROUP, or LIBRARY

	HJ => "hold.availableFlag",
		
	LE => "charges", # a given charge will consist of charge data + item
	 
	Lc => "bookedItems.bookings",

	LF => "bills", # a given bill will be associated with an Item, e.g.
	LL => "orders.orderUnit", # shouldn't show up for patrons!
	LN => "payments",
	LM => "payments", # found as a child of LF entries!
	Ls => "userInfoDistributions",
	IQ => "item.callNumber",
	IG => "item.type",
	IL => "item.currentLocation",
	CH => "charge.number",
	CF => "charge.dateDueDisplay",
	IP => "item.price",
	
	CI => "charge.dueDate",
	CA => "charge.dateCharged",

	CX => "charge.circRule",
	CV => "charge.recallNoticeCount",

	MA => "admin.message",
	MN => "admin.messageNumber",
	CI => "item.dateDueDisplay",
	Cd => "charge.numRenewalsRemaining",
	

	KA => "request.ID",

	LC => "extendedInfo.entries",
	nK => "extendedInfo.entryType",
	S1 => "extendedInfo.entryRequired",
	ND => "extendedInfo.entryNumber",
	NP => "extendedInfo.entryName",
	NH => "extendedInfo.entryID",
	NE => "extendedInfo.data",

	MN => "error.messageNumber",
	DT => "admin.alertCode",

	Bt => "user.datePrivsResume",
	Uz => "user.numOpenOrderLines",
	Um => "user.numOpenDistributions",
	CT => "user.fineTotal",
	Uw => "user.numCharges",

	BH => "bill.total",
	BD => "bill.reason",
	BW => "bill.numNotices",
	YS => "bill.reasonDisplay",
	BK => "bill.dateBilled",
	BJ => "bill.billNumber",
	BL => "payment.datePaid",
	BA => "payment.amountPaid",
	BE => "payment.paymentType",
	BF => "bill.numPayments",
	BI => "bill.amountBilled",
	PY => "payment.unknown.PY(tax policy?)",
);

# some of the things above (generally, if its data code starts with an L)
# are list containers; when we go to compress 'em, this would be nice to know.

my %collections =(
	holds => "hold",
	charges => "charge",
	bills => "bill",
	payments => "payment",
	addresses => "address",
);

# JSON module turns *references* to 0 and 1 to JSON false/true,
# respectively; everything else passes through untouched.

sub to_bool {
	my ($x) = @_;
	if ( !defined $x ) {
		return \0;
	} else {
		if ( $x eq 'N' or $x eq 'n' ) {
			return \0;
		} elsif ( $x eq 'y' or $x eq 'Y') {
			return \1;
		}
	}
	return $x ? \1 : \0;
}

sub to_int {
	my ($x) = @_;
	return defined($x) ? int($x) : 0;
}

# alias command names to commands

for my $cmd ( keys %commands ) {
	my $entry = $commands{$cmd};
	if ( exists $commands{$entry->{command}} ) {
		die "Entry $cmd has a command that clashes with a full command name " . $entry->{command};
	}
	$commands{$entry->{command}} = $entry;
}
		
sub map_codes {
	my ($code) = shift;
	if ( $codes{$code}  ) {
		return $codes{$code};
	} else {
		return "unknown.$code";
	}
};


# maps known names to conversion functions
my %json_conversions = (
	'error' => \&to_bool,
	'hold.availableFlag' => \&to_bool,
	'user.numCharges' => \&to_int,
	'user.numHolds' => \&to_int,
	'user.numBills' => \&to_int,
);

sub map_value {
	my ($k,$v) = @_;
	return exists $json_conversions{$k} ? &{$json_conversions{$k}}($v) : $v;
}


my %ei_map = (
	"extendedInfo.entryID" => "field.Name",
	"extendedInfo.data" => "field.Value"
);

# called by parser to 'compress' extendedInfo entries to 
# just the field name and value

sub map_extended_info_entry {
	my ($ref) = @_;	
	if ( !$ref->{"extendedInfo.entryID"} ) {
		return $ref;
	}
	my %entry = map { $ei_map{$_} => $ref->{$_} } keys %ei_map;
	return \%entry;
}
		
# Grammar definition; the grammar processes the Sirsi output and
# yields up what it finds as a hashref where the keys are 
# more human readable.

my $grammar =<<'END_OF_GRAMMAR';

	{
		#open ERROR, "> /tmp/sapierr.txt" or die "Can't open error:$!";
		my %results = ();
	}

	startrule 		: preamble '^' result_data transaction_id /\Z/
	{
		$return = \%results;
	} | <error>
	alnum			: /[A-Za-z0-9]/
	letter			: /[A-Za-z]/
	not_carets		: /[^\^]*/
	not_l			: /[A-KM-Za-z1234]/   # match things that aren't capital Ls
	caret			: '^'
	
	preamble 		: '^@' /[0-9]{2}/ command_result 
	{ 
		$results{outcome} = $item{command_result}; 
	}
	
	command_result  : /[^\^]+/ ... caret 
	{ 
		$results{responseCode} = substr($item[1],0,2); 
		$results{error} = $results{responseCode} eq 'ZA';
		my $out = $item[1]; 
		$results{outcome} = $out; 
		if ( $out =~ /MA\$\((\d+)\)/ ) { 
			$results{messageCode} = $1; 
		} 
		$item[1];
	} | <error> 

	data_element	: (list | scalar_field)  {  $item[1]; } |  <error>
	result_data	: data_element(s?) { 
		$return = $item[1]; 
		my %data = ();
		# merges all the individual hashrefs into a big hash
		# ignores multiple values!
		foreach my $ref ( @{$item[1]} ) {
			%data = (%data, %$ref);			
		}
		$results{data} = \%data;
	} | <error>

	list			: caret(?) list_start list_entry(s?)  '^5' 
	{ 
			my $k = Sirsi::API::map_codes($item[2]);
			my $v = $item[3];
			$return = { $k => Sirsi::API::map_value($k,$v) };
	} | <error>
	list_start		: 'L' letter { $return = 'L' . $item{letter}; }
	list_entry		: (list | scalar_field)(s?) '^^Z' { 
		my %entry = ();
		foreach my $field ( @{ $item[1] } ) {
			$entry{$_} = $field->{$_} foreach keys %$field;
		}
		if (exists $entry{'extendedInfo.entryID'} ) {
			$return = Sirsi::API::map_extended_info_entry(\%entry);
		} else {
			$return = \%entry;
		}
	} | <error>
			
	scalar_field	: caret(?) field_name field_value ... caret { 
		my $k =	Sirsi::API::map_codes($item{field_name});
		my $v = Sirsi::API::map_value($k, $item{field_value});
		$return = { $k => $v };
	} | <error>
	field_name		: not_l alnum { 
		$return = $item{not_l} . $item{alnum};
		#print ERROR "Found field $return  at $thiscolumn\n";
		1;
	}
	field_value		: not_carets

	transaction_id 	: caret(?) caret(?) /O\d{5}/ { $results{transaction_id} = $item{__PATTERN1__}; 1; } | <error>
END_OF_GRAMMAR

# this sub is responsible for adding useful information to the structures
# returned by the parser.
# if the second parameter is true, values matching keys in the json_conversion
# hash will be processed into JSON-friendly ones.

sub translate {
	my ($r,$json,$command) = @_;

	$r->{data} = compress($r->{data},'data');

	# dirty hack to make addresses nest like
	if ( $r->{data}->{user} ) {
		my %user = %{$r->{data}->{user}};
		my @addresses = ();
		foreach my $addr ( sort grep { /^addresses\./ } keys %user ) {
			if ( %{ $user{$addr} } ) {
				# only add addresses with entries
				push @addresses, $user{$addr};
			}
			delete $user{$addr};
		}
		$user{'addresses'} = \@addresses;
		if ( $user{mailingAddress} ) {
			my $idx = int( $user{mailingAddress} );
			$addresses[$idx-1]{mailing} = \1;
		}
			
		
		$r->{data}->{user} = \%user;
	}
		

	if ($json) {
		foreach my $key ( %{ $r->{data} } ) {
			if ( exists $json_conversions{$key} ) {
				$r->{data}->{$key} = &{ $json_conversions{$key} }($r->{data}->{$key});
			}
		}
	}
	$r;
}

# compresses a [type]Unit hashref containing multiple objects
# into a single object where the original [type] object's key/value pairs
# are at the top level and all other key/values are reparented to the
# [type] object, viz. a chargeUnit object containing charge and item keys
# would end up containing all of the charge objects keys
# this makes deserialization a bit easier on the backend, and also matches
# a reasonable mental model for bills, charges, and holds

sub combine_units {
	my ($r,$parent) = @_;
	my $base = $r->{$parent};
	if (!defined $base) {
		print STDERR "Did not find $parent\n";
		return $r;
	}
		
	foreach my $key ( keys %$r ) {
		if ( $parent ne $key ) {
			my $sub = $r->{$key};
			$base->{$key} = $sub;
			delete $r->{$key};
		}
	}
	$base; 
}

sub is_collection {
	$collections{$_[0]};
}

sub compress_address {
	my ($r) = @_;
	my %addr = ();
	for my $obj ( @$r ) {
		my $name = delete $obj->{'field.Name'};
		my $value = delete $obj->{'field.Value'};
		$addr{$name} = $value;
	}
	return \%addr;
}

sub compress {
	my ($r,$fieldName) = @_;
	if ( ref $r eq 'ARRAY' ) {
		my $ctype = $collections{$fieldName};
		my $compressed = [map { compress($_, $fieldName) } @$r];
		if ( $ctype ) {
			return [ map { combine_units($_,$ctype) } @$compressed ];
		} elsif ( $fieldName =~ /^addresses\./ ) {
			return compress_address($r);
		}

		return $compressed;
	} elsif ( ref $r eq 'HASH' ) {
		my %compressed = ();
		my @keys = keys %$r;
		my %prefixes = map { $_ => 1 } ( map { ( split /\./ )[0] } keys %$r );
		for my $p ( keys %prefixes ) {
			my %obj = ();
			my @props = grep { $_ =~ /^$p\.?/ } @keys;
			foreach my $prop ( @props ) {
				my $rest = (split /\./, $prop, 2)[1];
				$obj{$rest} = compress($r->{$prop}, $rest) if $rest;
			}
			if ( %obj ) {
				$compressed{$p} = \%obj;
			} else {
				if ( ref $r->{$p} eq 'ARRAY' )  {
					$compressed{$p} = compress($r->{$p}, $p);
				}
			}
		}
		return \%compressed;
	}
	return $r;
}

sub process_result {
	my ($response,$json, $command) = @_;
	$response->{error} = $response->{error} ? \1 : \0;
	translate($response,$json, $command);
}

# Request builders

sub execute_request {
	my ($req) = @_;
	# execute it
	my $resp = <DATA>;
	chomp($resp);
	return $resp;
}

## METHOD DEFINITIONS ONLY ABOVE

sub test_file {
	my ($file) = @_;
	local $/;
	open FILE, "< $file" or die "Cannot open $file:$!\n";
	my $data = <FILE>;
	close FILE;
	chomp $data;
	my $parser = Parse::RecDescent->new($grammar)
		or die "Can't parse grammar";
	$::RD_ERRORS =1;
	$::RD_HINT  =1;
	# the following is very verbose, so use with caution
	#$::RD_TRACE =1;
	my $tree = $parser->startrule($data)
		or die "Parsing failed.";
	return $tree;
}

sub run_example {

	my $parser = Parse::RecDescent->new($grammar) or die "Can't parse grammar";
	my $req = "";
	my $response = execute_request($req);
	my $tree = $parser->startrule($response);
	if ( $tree ) {
		return process_result($tree, 1);
		exit(0);
	} else {
		 die "Unable to process request; try running with $::RD_ERRORS and $::RD_HINTS set to see why.";
	}
}

# methods for object-oriented interface.

sub new {
	my $class = shift;
	my (%args) = @_;
	my $self = {
		sirsi_user_id => "SIRSI",
		sirsi_access_code => "SOMEPASSWORD",
		station_library => "SOMELIBRARY",
		station_login_user_access => 'CIRC',
		command_start => "^S84",
		apiserver_path => "/s/sirsi/Unicorn/Bin/apiserver",
		log_path => "/tmp/nciplogs",
		command_prefix => "%s^FF%s^FE%s^FW%s^FcNone^UMALL_LIBS^QTALL",
		parser => Parse::RecDescent->new($grammar),
		debugFile => 0, # non-false value interpreted as path
		jsonify => 0 # whether to prepare output for JSON
	};
	foreach my $key ( keys %args ) {
		$self->{$key} = $args{$key} if exists $self->{$key};
	}
	
	my $prefix = sprintf($self->{command_prefix}, $self->{station_login_user_access}, $self->{sirsi_access_code}, $self->{station_library}, $self->{sirsi_user_id});
	$self->{command_prefix} = $prefix;
	bless($self,$class);
	return $self;
}

sub format_command {
	my ($self,$code, @values) = @_;
	die "Unknown command code $code" unless exists $commands{$code};
	my %command =  %{ $commands{$code} };
	if ( %command ) {
		$self->{last_command} = \%command;
	}
	my $cmdstr = $self->{command_start} . $command{command} . $self->{command_prefix};
	my $i = 0;
	for my $val ( @values ) {
		my $param = $command{params}[$i++];
		if ( $param ) {
			my ($k) = keys %$param;
			$cmdstr .= "^$param->{$k}" . $val;
		}
	}
	if ( $self->{debugFile} ) {
		open DEBUG, ">> " . $self->{debugFile};
		print DEBUG "$cmdstr\n\n";
		close DEBUG;
	}
	return ( $cmdstr, \%command );
}

sub get_flag {
	my ($var) = @_;
	return $var ? "Y" : "N";
}
		
sub lookup_user {
	my ($self,$uid,$show_account,$charges, $requests) = @_;
	($uid) = ( $uid =~ /(\d+)/ );
	my ($cmdstr, $command) = $self->format_command('GC', $uid);
	my @flags = ( 
		["DD", get_flag($show_account) ], # bills
		["DF", get_flag($charges) ], 
		["DG", get_flag($requests) ],
		["By", get_flag($show_account)] # display bill payments
	);
	foreach my $fc ( @flags ) {
		$cmdstr .= "^$fc->[0]$fc->[1]";
	}
	
	my $result = $self->_execute_command("$cmdstr^^O");
	my $rv = $self->_process_result($result);
	return $rv;
}

sub check_in {
	my ($self, $item_id) = @_;
	my ($cmdstr,$command) = $self->format_command("checkIn",$item_id);
	my $result = $self->_execute_command($cmdstr);
	return $self->_process_result($result);	
}

sub renew_item {
	my ($self,$item_id) = @_;
	my ($cmdstr,$command) = $self->format_command('renewItem',$item_id);
	my $result = $self->_execute_command($cmdstr);
	return $self->_process_result($result);
}

# item_id = barcode

sub place_hold {
	my ($self,$uid,$item_id) = @_;
	my ($cmdstr, $command) =  $self->format_command('placeHold',$uid,$item_id, 'COPY');
	my $result = $self->_execute_command("$cmdstr^^O");
	return $self->_process_result($result);	
}

# item_id => "barcode"

sub cancel_hold {
	my ($self,$uid,$item_id, $hold_type) = @_;
	my ($cmdstr,$command) = $self->format_command('cancelHold',
		$uid, $item_id, $hold_type);
	my $result = $self->_execute_command("$cmdstr^^O");
	return $self->_process_result($result);
}

sub translate_error {
	my ($self,$sirsi_response) = @_;
	open DEBUG, ">>/tmp/ncip-error.txt";
	my $shell = "echo '$sirsi_response' | translate";
	my $response = `$shell`;
	print DEBUG "$response\n\n";
	close DEBUG;
	if ( $response =~ /(?:ZA|\^)MA([^\^]+)\^/ ) {
		my $msg = $1;
		if ( $msg =~ /^#(\w\w)Required/ )  {
			return "Required field ($1) missing";
		}
		return $msg;
	}
	return "Unknown error";
}
	
	

sub _process_result {
	my ($self,$output) = @_;
	#$::RD_ERRORS 	= 1;
	#$::RD_HINT 	= 1;
	my $tree = $self->{parser}->startrule($output);
	if ( $tree ) {
		if ( $tree->{error} ) {
			$tree->{message} = $self->translate_error($output);
		}
		return process_result($tree, $self->{jsonify}, $self->{last_command});
	}
	return { "message" => "Unable to read output" };
}

sub _execute_command {
	my ($self, $cmd) = @_;
	if ( $cmd !~ /\^\^O$/ ) {
		$cmd .= '^^O';
	}
	my $response = "";
	my $apicall = "echo \"$cmd\" | $self->{apiserver_path} -e $self->{log_path}";
	my $r = `$apicall`;
	# ha !  ha!  sometimes API output does not end with ^^O(transactionid)
	# but only ^O(transid) -- a single caret.
	
	if ( exists $self->{debugFile} ) {
		open DEBUG, ">> " . $self->{debugFile} or die "Can't open debug output $self->{debugOutput}: $!\n";
		print DEBUG "$r\n";
		close DEBUG;
	}
	$r;
}

1;
__DATA__
^@01DCfG45^U6[REDACTED]^UO[REDACTED]^UkAJCONSTA^UC^Q5^Uf[REDACTED]^uFADAM^uMJOHN^uLCONSTABARIS^UR^uS^uU^uV0^UACONSTABARIS, ADAM JOHN^PHSTAFF^UZNEVER^uY0^UMDHHILL^UN250101^PESTAFF^IbENGLISH^UD5/1/2010^UQ10/28/2012^Ut5/1/2010^UK5/19/2011^UG0^UE1^UJOK^aDOK^aC^USUNLIMITED^PMN^UL1^LVnK^ND396^NP$<uadr_othr_street>^NHSTREET^S1N^NEBOX 7111^^ZnK^ND397^NP$<uadr_othr_cityst>^NHCITY/STATE^S1N^NERALEIGH, NC^^ZnKz^ND398^NP$<uadr_othr_zip>^NHZIP^S1N^NE27695^^ZnKh^ND399^NP$<uadr_othr_phone>^NHPHONE^S1N^NE^^ZnKm^ND400^NP$<uadr_othr_email>^NHEMAIL^S1N^NEADAM_CONSTABARIS@NCSU.EDU^^Z^5Lk^5Ll^5HB5/2/2012^^O0058
^@01ZAMA$(635)^MN635^DT1^^O00027
^@01GCfG45^U6[REDACTED]^UO[REDACTED]^UkAJCONSTA^UC^Q5^Uf[REDACTED]^uFADAM^uMJOHN^uLCONSTABARIS^UR^uS^uU^uV0^UACONSTABARIS, ADAM JOHN^PHSTAFF^UZNEVER^uY0^UMDHHILL^UN250101^PESTAFF^IbENGLISH^UD5/1/2010^UQ11/4/2012^Ut5/1/2010^UK5/19/2011^UG0^UE1^UJOK^aDOK^aC^USUNLIMITED^PMN^UL1^LVnK^ND401^NP$<uadr_othr_street>^NHSTREET^S1N^NEBOX 7111^^ZnK^ND402^NP$<uadr_othr_cityst>^NHCITY/STATE^S1N^NERALEIGH, NC^^ZnKz^ND403^NP$<uadr_othr_zip>^NHZIP^S1N^NE27695^^ZnKh^ND404^NP$<uadr_othr_phone>^NHPHONE^S1N^NE^^ZnKm^ND405^NP$<uadr_othr_email>^NHEMAIL^S1N^NEADAM_CONSTABARIS@NCSU.EDU^^Z^5Lk^5Ll^5Bt^LN^5Bx0^LC^5Ls^5Um0^LL^5Uz0^^O00601
END






^@01DCfG45
	^U6[REDACTED]
	^UO[REDACTED]
	^UkAJCONSTA
	^UC
	^Q5
	^Uf[REDACTED]
	^uFADAM^uMJOHN
	^uLCONSTABARIS
	^UR
	^uS
	^uU
	^uV0
	^UACONSTABARIS, ADAM JOHN
	^PHSTAFF
	^UZNEVER
	^uY0
	^UMDHHILL
	^UN250101
	^PESTAFF
	^IbENGLISH
	^UD5/1/2010
	^UQ10/28/2012
	^Ut5/1/2010^UK5/19/2011
	^UG0
	^UE1
	^UJOK
	^aDOK
	^aC
	^USUNLIMITED
	^PMN
	^UL1
	^LV
		nK^ND396^NP$<uadr_othr_street>^NHSTREET^S1N^NEBOX 7111^^Z
		nK^ND397^NP$<uadr_othr_cityst>^NHCITY/STATE^S1N^NERALEIGH, NC^^Z
		nKz^ND398^NP$<uadr_othr_zip>^NHZIP^S1N^NE27695^^Z
		nKh^ND399^NP$<uadr_othr_phone>^NHPHONE^S1N^NE^^Z
		nKm^ND400^NP$<uadr_othr_email>^NHEMAIL^S1N^NEADAM_CONSTABARIS@NCSU.EDU^^Z
	^5
	Lk
	^5
	Ll
	^5
	HB5/2/2012
	
^^O0058

1;
__END__
# Below is stub documentation for your module. You'd better edit it!

=head1 NAME

Sirsi::API - Perl extension for executing and parsing Sirsi Symphony
apiserver commands.

=head1 SYNOPSIS

  use Sirsi::API;

  my $resp = Sirsi::API::lookup_user("100508509");
  return 

=head1 DESCRIPTION

Stub documentation for Sirsi::API, created by h2xs. It looks like the
author of the extension was negligent enough to leave the stub
unedited.

Blah blah blah.

=head2 EXPORT

None by default.

=head1 SEE ALSO

Mention other useful documentation such as the documentation of
related modules or operating system documentation (such as man pages
in UNIX), or any relevant external documentation such as RFCs or
standards.

If you have a mailing list set up for your module, mention it here.

If you have a web site set up for your module, mention it here.

=head1 AUTHOR

Adam Constabaris, E<lt>adam_constabaris@ncsu.eduE<gt>

=head1 COPYRIGHT AND LICENSE

Copyright (C) 2011 by North Carolina State University

This library is free software; you can redistribute it and/or modify
it under the same terms as Perl itself, either Perl version 5.10.0 or,
at your option, any later version of Perl 5 you may have available.


=cut
