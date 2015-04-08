#!/bin/bash
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
read -e -p "Enter IP address of your OPAC: " opac
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
