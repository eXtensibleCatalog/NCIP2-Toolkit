use 5.010000;
use ExtUtils::MakeMaker;
# See lib/ExtUtils/MakeMaker.pm for details of how to influence
# the contents of the Makefile that is written.
WriteMakefile(
    NAME              => 'Sirsi::API',
    VERSION_FROM      => 'lib/Sirsi/API.pm', # finds $VERSION
    PREREQ_PM         => { Parse::RecDescent => 0,
			   JSON => 0,
			   MARC::Charset => 1.33,
			}, # not sure of versions!
    ($] >= 5.005 ?     ## Add these new keywords supported since 5.005
      (ABSTRACT_FROM  => 'lib/Sirsi/API.pm', # retrieve abstract from module
       AUTHOR         => 'Adam Constabaris <adam_constabaris@ncsu.edu>') : ()),
);
