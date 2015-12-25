#!/bin/bash

# If you plan to deploy docker into other than Czech library, change this
export LC_ALL=cs_CZ.UTF-8
export LANGUAGE=cs_CZ.UTF-8
export LANG=cs_CZ.UTF-8

dir=$(dirname $0)
configTmp=$dir/toolkit.properties.tmp
config=$dir/toolkit.properties

# Check if the configuration already exists ..
if [ -f $config ]; then
	echo
	read -e -p "Configuration $config already exists, do you wish to create new one ? [Y/n]" yn

	if [ "$yn" == "n" ]; then
		exit 0
	fi
fi

# Create temporary configuration to change the default values at
cp $dir/toolkit.properties.example $configTmp

echo
echo "Koha's configTmpuration is on schedule.."
echo ""
echo "Please note that toolkit's koha connector cannot work without access to intranet, thus it is needed to provide."
echo ""
read -e -p "Enter username to intranet: " adminName
echo ""
read -s -p "Enter password for specified user: " adminPass
echo ""
read -s -p "Confirm the password: " adminPass2
echo ""
while [ "$adminPass2" !=  "$adminPass" ]; do
	read -s -p "Wrong password, try again: " adminPass2
	echo ""
done
echo ""
echo "Now enter the hostname of your OPAC ( e.g. https://188.166.14.82)"
echo "Please include http:// OR https:// protocol specification!"
echo ""
read -e -p "Hostname of your Koha Intranet: " opac
# Purge the char '/' at the end if any ..
opac=$(echo $opac | sed 's_/$__g')
echo ""
read -e -p "Port of your intranet (probably 8080):" port
echo ""
read -e -p "Enter your library full address: " address
echo ""
read -e -p "Enter your library name: " libraryName
echo ""
read -e -p "Enter your SIGLA: " sigla
echo ""
read -e -p "Enter URL of your online registration form (optional): " registrationLink
echo ""

sed -i "s_LibraryAddressHere_$(echo $address)_g" $configTmp
sed -i "s_LibraryNameHere_$(echo $libraryName)_g" $configTmp
sed -i "s_LibrarySIGLAHere_$(echo $sigla)_g" $configTmp
sed -i "s_LibraryRegistrationLinkHere_$(echo $registrationLink)_g" $configTmp
sed -i "s_KohaIPHere_$(echo $opac)_g" $configTmp
sed -i "s_KohaPortHere_$(echo $port)_g" $configTmp

sed -i "s_IntranetAdministratorNameHere_$(echo $adminName)_g" $configTmp
sed -i "s_IntranetAdministratorPassHere_$(echo $adminPass)_g" $configTmp

native2ascii -encoding utf8 $configTmp $configTmp

# Move the temp config to the real config
mv $configTmp $config
