# Lookup User #

  * User with ILS Authentication
  * User with LDAP Authentication, if appropriate for your ILS
  * User with 1 checked out out item
  * Request optional field - User Name
  * Request optional field - Address.
  * Test different scenarios with different address types, Permanent vs. temporary.
  * With and without and email address.
  * If your ILS supports temporary address "valid date ranges."

# Lookup Item #

  * Item is on Hold
  * Item is Available
  * Item has temp location
  * User optional fields - Circulation Status
  * User optional fields - Location
  * User optional fields - Bibliographic Description


# Lookup Item Set (Coming Soon) #


  * Bibliographic ID request with one bib that has one HoldingSet and one item
  * Bibliographic ID request with one bib that has multiple HoldingSets and multiple items
  * Bibliographic ID request with multiple bibs that have multiple HoldingSets and multiple item, where Holding sets have different locations
  * Bibliographic ID request with one bib that can't be found and return problem element
  * Bibliographic ID request with multiple bibs where one bib id can't be found and returns problem element