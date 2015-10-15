The wiki pages in this section will provide an overview of each of the NCIP Services supported.  These pages can be used by the following groups of people:

  * Connector Developers - who are writing response to NCIP requests from their ILS.    The ILS Interoperability group has specified a minimal level of data request and response that each ILS connector developer should try to respond to.
  * NCIP Initiators or client developers - these represent applications that will be starting the NCIP dialog (e.g. web discovery layers such as VUFind, XC Drupal Toolkit).  The goal of specifying a minimal data reponse from each ILS connector is so that Initiator applications can "know" what to expect from any completed connector.
  * Other interested parties - anyone interested in learning more about what the NCIP Toolkit does in greater detail

### Definitions ###

**Recommended** fields mean that we feel these fields are helpful to support but recognize not all connector developers will be able to support these.

Each service will have two sections, a **Request** (for the NCIP Initiation Message) and a **Response** (for the NCIP Response Message)section that will provide some general information, list out the required data elements, and list key information for each service.  Greater details in some cases can be found on the associated linked Excel spreadsheets.

### Special note about Agency ###

The NCIP protocol allows for the use of AgencyID in the InitiationHeader of many NCIP services.  Use or non-use of this will depend on the specific implementation details.  In many cases (e.g. one to one communications) this will not need to be used.  Essentially, the Agency is the organization or system that you are meaning to communicate with.   Possible uses of what an Agency **COULD** be are below:

  * MARC Organization Code List that is maintained by the Library of Congress
  * Canadian Library symbols
  * Internal Consortial system code that would identify one library from another in the consortium
  * Internal library system code that would identify one branch from another (e.g. in a local Public Library system)