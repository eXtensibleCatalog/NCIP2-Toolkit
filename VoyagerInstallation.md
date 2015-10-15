# Voyager Installation #

Download the Voyager connector war file from [Google Code](http://code.google.com/p/xcncip2toolkit/downloads/list)

## Steps to install: ##
Follow the steps in  [Installation/General Installation Instructions](http://code.google.com/p/xcncip2toolkit/wiki/CoreInstallation). During the Preparation stage follow these steps:
  1. **Download/Install the Oracle JDBC driver**
    * The Voyager connector requires the Oracle JDBC driver.  This driver is not redistributable so you must download it manually.  Download the OJDBC driver for your specific version of Oracle from here:  http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html
    * For Tomcat users: Place the driver into the $CATALINA\_HOME/lib directory.
    * For other users: place it into the particular directory suitable for your application server.
  1. **Set up the Oracle user**
    * The Voyager connector currently requires the account used to be the schema owner of the database.  If it is not, you can circumvent this by creating a new account and a synonym for every table in the owner account.
      * Create a new account
      * Give it "select any table" permissions
      * Get a list of the owner account's tables
      * Create a synonym for every table in the the owner account
      * Below would be the basic commands to create such an account (assuming the instance was called (ARU) and the owner account was (ARUDB))

```
create user ncip_readuser_aru identified by ncippw;
grant connect,select any table to ncip_readuser_aru;

spool cre_aru_syn.sql
set pages 0
set feedback off
select 'CREATE SYNONYM ' || table_name || 'FOR ' || 'ARUDB.' || table_name || ';' from dba_tables where owner = 'ARUDB';
quit


      Then run the script created by the above dynamic sql:

sqlplus "/ as sysdba" @cre_aru_syn.sql
```