#!/bin/bash

# If you plan to deploy docker into other than Czech library, change this
export LC_ALL=cs_CZ.UTF-8
export LANGUAGE=cs_CZ.UTF-8
export LANG=cs_CZ.UTF-8

config='toolkit.properties'
cp toolkit.properties.example $config

echo
echo "Koha's configuration is on schedule.."
echo ""
read -e -p "Enter your library full address: " address
echo ""
read -e -p "Enter your library name: " libraryName
echo ""
read -e -p "Enter your SIGLA: " sigla
echo ""
read -e -p "Enter URL of your online registration form (optional): " registrationLink
echo ""
echo "Enter the hostname of your OPAC (e.g. http://ceska-trebova.cz or https://188.166.14.82)"
echo "PLEASE INCLUDE http:// OR https:// ! It is also important not to add the backslash at the end"
read -e -p "Hostname of your OPAC:" opac
echo ""
echo "Please note that toolkit's koha connector cannot work without access to intranet, thus it is needed to provide."
echo ""
read -e -p "Enter username to intranet: " adminName
echo ""
read -s -p "Enter password for specified user: " adminPass
echo ""

sed -i "s_LibraryAddressHere_$(echo $address)_g" $config
sed -i "s_LibraryNameHere_$(echo $libraryName)_g" $config
sed -i "s_LibrarySIGLAHere_$(echo $sigla)_g" $config
sed -i "s_LibraryRegistrationLinkHere_$(echo $registrationLink)_g" $config
sed -i "s_KohaIPHere_$(echo $opac)_g" $config

sed -i "s_IntranetAdministratorNameHere_$(echo $adminName)_g" $config
sed -i "s_IntranetAdministratorPassHere_$(echo $adminPass)_g" $config

native2ascii -encoding utf8 toolkit.properties toolkit.properties
