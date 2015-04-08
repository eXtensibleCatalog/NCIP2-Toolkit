#!/bin/bash
cd /home/xcncip2toolkit/xcncip2toolkit/core/trunk 
mvn install
mvn install -Dmaven.test.skip

cd ../../connectors/aleph/22/trunk
mvn install -Dmaven.test.skip

cd ../../../../connectors/koha/3.xx/trunk/web/src/main/resources

config='toolkit.properties'
cp toolkit.properties.example $config

echo
echo "Koha's configuration is on schedule.."
echo ""
read -p "Enter your library full address: " address
echo ""
read -p "Enter your library name: " libraryName
echo ""
read -p "Enter your SIGLA: " sigla
echo ""
read -p "Enter URL of your online registration form (optional): " registrationLink
echo ""
read -p "Enter IP address of your OPAC: " opac
echo ""
read -p "Enter Admin name to intranet: " adminName
echo ""
read -s -p "Enter Admin password: " adminPass

sed -i "s_LibraryAddressHere_$(echo $address)_g" $config
sed -i "s_LibraryNameHere_$(echo $libraryName)_g" $config
sed -i "s_LibrarySIGLAHere_$(echo $sigla)_g" $config
sed -i "s_LibraryRegistrationLinkHere_$(echo $registrationLink)_g" $config
sed -i "s_KohaIPHere_$(echo $opac)_g" $config

sed -i "s_IntranetAdministratorNameHere_$(echo $adminName)_g" $config
sed -i "s_IntranetAdministratorPassHere_$(echo $adminPass)_g" $config

cd ../../../..
mvn install -Dmaven.test.skip
