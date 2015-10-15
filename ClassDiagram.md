# Introduction #

Class diagram overview for NCIP services.


# Interacting with Translator and Message Handler #

http://www.extensiblecatalog.org/doc/MST/class1.JPG


This diagram shows the relationship between the NCIPServlet class, the Translator interface and the MessageHandler class. The essence of the relationship is shown by the following (simplified code) from the NCIPServlet’s doPost method:

```
// Get the input stream from the Servlet Request object.
ServletInputStream inputStream = request.getInputStream();

// Convert that to an NCIPInitiationData object via the Translator.
NCIPInitiationData initiationData = translator.createInitiationData(inputStream);

// Perform the service associated with the concrete sub-class of the NCIPInitiationData object
// via the MessageHandler.
NCIPResponseData responseData = messageHandler.performService(initiationData);

// Convert the NCIPResponseData object to an input stream.
InputStream responseMsgInputStream = translator.createResponseMessageStream(responseData);

// Read the bytes from the stream and write them to the Servlet Response object (code omitted).
```


The process in the MessageHandler is as follows (again, the code is simplified):

```
// Get the concrete NCIPService associated (in the supportedServices map) with the 
// concrete sub-class of the NCIPInitiationData object.
NCIPService<NCIPInitiationData, NCIPResponseData> service 
    = (NCIPService<NCIPInitiationData, NCIPResponseData>)
        supportedServices.get(initiationData.getClass().getName());

// Call the performService method on that instance of the NCIPService.
NCIPResponseData responseData = service.performService(initiationData, serviceManager);

// Return the responseData object (code omitted).
```

# Initiation and Response interfaces #

http://www.extensiblecatalog.org/doc/MST/class2.JPG

The above class diagram shows the relationship between NCIPService, NCIPInitiationData, NCIPResponseData, and their implementations. The only point I would add is that the NCIPInitiationData and NCIPResponseData interfaces are “tag interfaces”, i.e. they have no methods and serve only to declare that an implementing class can serve as an instance of that interface. This just allows us to define particular NCIPService implementations (e.g. LookupUserService, LookupItemService, etc.) as instances of NCIPService that have a performService method that accepts the associated implementation of NCIPInitiationData and return the associated implementation of NCIPResponseData. This “association” is made when we populate the supportedServices map in the MessageHandler (see previous pages).

# NCIP Sub classes and Remote Service Manager #

http://www.extensiblecatalog.org/doc/MST/class3.JPG


This diagram shows the relationship between the (possibly many) NCIPService sub-classes and the particular implementation of RemoteServiceManager that provides access to the ILS itself.

Important points to note:
1)	The RemoteServiceManager interface has no methods; that means it imposes no requirements on the behavior of its implementing classes. The methods that are written for the RemoteServiceManager implementation class are whatever is needed by the implementations of NCIPService  (e.g. LookupItemService, RequestItemService, etc.). It’s certainly possible to put all of the functionality required to access the ILS in the implementations of NCIPService, and that might make sense. But what the RemoteServiceManager provides is a shared object for accessing the ILS, in case you need that to maintain state, cache objects, etc.
2)	The implementations of the RemoteServiceManager is a singleton – meaning the Spring container will only create one object of this class. You must ensure that your implementation ensures thread-safety, or you must use it as a factory to return unique instances of some class that actually provides the access to the ILS.
3)	The implementations of NCIPService are also singletons. As they have only one method (processService) it should be fairly straight-forward to use automatic variables rather than instance variables to ensure thread-safety, but if your design requires instance variables (e.g. you want to keep a reference to another singleton object) you will again have to ensure thread-safety.