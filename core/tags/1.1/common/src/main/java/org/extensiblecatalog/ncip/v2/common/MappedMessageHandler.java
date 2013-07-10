/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Processes a message by determining the appropriate Service implementation and
 * calling the performService method on that.
 */
public class MappedMessageHandler implements MessageHandler {

    private static final Logger LOG = Logger.getLogger(MappedMessageHandler.class);

    protected static final int NUMBER_OF_PARAMETERS_TO_PERFORM_SERVICE_METHOD = 3;

    /**
     * Map of supported Services
     */
    protected Map<String, NCIPService<NCIPInitiationData, NCIPResponseData>> supportedServices;

    /**
     * Provides access to services
     */
    protected RemoteServiceManager serviceManager;

    /**
     * Construct a new MappedMessageHandler using the default MessageHandlerConfiguration object.
     * @throws ToolkitException rethrown from {@link MessageHandlerConfigurationFactory#getConfiguration()}.
     */
    public MappedMessageHandler() throws ToolkitException {

        this(MessageHandlerConfigurationFactory.getConfiguration());

    }

    /**
     * Construct a new MappedMessageHandler from the provided configuration.
     * @param config the {@link MessageHandlerConfiguration} used to configure this instance
     * @throws ToolkitException if the configuration is incorrect (e.g. undefined classes are referenced)
     */
    public MappedMessageHandler(MessageHandlerConfiguration config) throws ToolkitException {

        supportedServices = new HashMap<String, NCIPService<NCIPInitiationData, NCIPResponseData>>();
        Properties properties = config.getProperties();
        for (Enumeration enumeration = properties.keys(); enumeration.hasMoreElements(); /**/) {
          String key = (String)enumeration.nextElement();
          LOG.debug("Property key=" + key);
          if ( key.compareToIgnoreCase("java.version") == 0 ) {
              LOG.debug("Java version is " + properties.get(key));
          }
          if ( key.matches("(?i)[A-Za-z]+Service\\.Class")) {

              String className = (String)properties.get(key);
              LOG.debug("Class name=" + className);

              if ( className == null || className.isEmpty() || className.compareToIgnoreCase("null") == 0 ) {

                  continue;

              }

              try {

                  Class<?> clazz = Class.forName(className);
                  Class<? extends NCIPService> xc = clazz.asSubclass(NCIPService.class);
                  LOG.debug("Class as subclass=" + xc.getName());

                  try {
                      NCIPService<NCIPInitiationData, NCIPResponseData> serviceInstance = xc.newInstance();
                      Method[] methods = clazz.getMethods();
                      Class initDataClass = null;
                      // TODO: Try replacing this with getMethod("performService", NCIPInitiationData.class, ServiceContext.class, RemoteServiceManager.class); 
                      for ( Method method : methods ) {

                          LOG.debug("Testing method " + method.getName());

                          if ( method.getName().compareToIgnoreCase("performService") == 0 ) {

                              LOG.debug("Method is performService.");

                              Class[] parameterTypes = method.getParameterTypes();

                              // This assumes there will only be one performService method on any class,
                              // i.e. the one required by the NCIPService interface definition.
                              // While the definition of NCIPService doesn't actually enforce this,
                              // it would be perverse to define an additional method with that name
                              // taking similar parameter types.
                              if ( parameterTypes.length == NUMBER_OF_PARAMETERS_TO_PERFORM_SERVICE_METHOD) {

                                  LOG.debug("Method takes " + parameterTypes.length + " parameters.");

                                  Class parameterClass = parameterTypes[0];
                                  LOG.debug("ParameterType[0] is " + parameterClass.getName());

                                  if ( parameterClass.getName().compareTo(Class.class.getName()) != 0 ) {

                                      initDataClass = parameterClass;
                                      if ( initDataClass.getName().compareTo(Object.class.getName()) == 0 ) {
                                          LOG.warn("Class first parameter is 'Object'; trying to use property key to get actual initDataClass.");
                                          String serviceName = key.substring(0, key.length() - "Service.Class".length());
                                          String initDataClassName = NCIPService.class.getPackage().getName() + "." + serviceName + "InitiationData";
                                          LOG.debug("InitDataClassName: " + initDataClassName);
                                          initDataClass = Class.forName(initDataClassName);
                                      } else {
                                          LOG.debug("The initDataClass is not 'Object'.");
                                      }
                                      LOG.debug("Selecting " + initDataClass.getName() + " as initDataClass.");
                                      break;

                                  } else {

                                      LOG.debug("The performService method for class " + clazz.getName() 
                                          + " takes a Class parameter, which is disallowed.");

                                  }

                              } else {

                                  LOG.debug("The performService method for class " + clazz.getName()
                                      + " has no parameters.");

                              }

                          } else {

                              LOG.debug("Method is not performService.");

                          }
                      }
                      LOG.debug("Finished checking methods.");

                      if ( initDataClass != null ) {

                          LOG.debug("Putting " + serviceInstance.getClass().getName() + " into supportedServices map "
                              + "with key of " + initDataClass.getName() + ".");
                          supportedServices.put(initDataClass.getName(), serviceInstance);

                      } else {

                          throw new ToolkitException("Unable to determine NCIPInitiationData sub-class with "
                          + "name '" + className + "' for " + key);

                      }

                  } catch (InstantiationException e) {
                      throw new ToolkitException("Exception while populating supported services.", e);
                  } catch (IllegalAccessException e) {
                      throw new ToolkitException("Exception while populating supported services.", e);
                  }

              } catch (ClassNotFoundException e) {
                  throw new ToolkitException("Class not found exception while populating supported services.", e);
              }
          } else if ( key.matches("(?i)RemoteServiceManager\\.Class") ) {

              try {
                  Class<?> clazz = Class.forName(properties.getProperty(key));
                  Constructor ctor = clazz.getConstructor(Properties.class);
                  serviceManager = (RemoteServiceManager)ctor.newInstance(properties);
              } catch (ClassNotFoundException e) {
                  throw new ToolkitException("Exception while populating supported services.", e);
              } catch (NoSuchMethodException e) {
                  throw new ToolkitException("Exception while populating supported services.", e);
              } catch (InvocationTargetException e) {
                  throw new ToolkitException("Exception while populating supported services.", e);
              } catch (InstantiationException e) {
                  throw new ToolkitException("Exception while populating supported services.", e);
              } catch (IllegalAccessException e) {
                  throw new ToolkitException("Exception while populating supported services.", e);
              }

          }
        }
        LOG.debug("Finished with properties.");
        if ( LOG.isDebugEnabled() ) {

            LOG.debug("Service map for " + this + ":");
            for ( Map.Entry<String, NCIPService<NCIPInitiationData, NCIPResponseData>> entry
                : supportedServices.entrySet() ) {

                LOG.debug(entry.getKey() + "=" + entry.getValue());
            }
        }

    }

    /**
     * Construct a new MappedMessageHandler with the provided map of services and ServiceManager.
     *
     * @param supportedServices a map of supported services
     * @param serviceManager    provides access to remove services
     */
    public MappedMessageHandler(Map<String, NCIPService<NCIPInitiationData, NCIPResponseData>> supportedServices,
                          RemoteServiceManager serviceManager) {
        this.supportedServices = supportedServices;
        this.serviceManager = serviceManager;
    }

    public void setRemoteServiceManager(RemoteServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    public void setSupportedServices(Map<String, NCIPService<NCIPInitiationData, NCIPResponseData>> supportedServices) {
        this.supportedServices = supportedServices;
    }

    @Override
    public NCIPResponseData performService(NCIPInitiationData initiationData, ServiceContext serviceContext) {

        NCIPResponseData responseData;

        NCIPService<NCIPInitiationData, NCIPResponseData> service = null;

        if ( LOG.isDebugEnabled() ) {

            LOG.debug("Looking in supported services map (" + this + ") for service to handle "
                + initiationData.getClass().getName());
            for ( Map.Entry<String, NCIPService<NCIPInitiationData, NCIPResponseData>> entry
                : supportedServices.entrySet() ) {

                LOG.debug(entry.getKey() + "=" + entry.getValue());
            }
        }
        
        if (supportedServices != null) {

            service = supportedServices.get(initiationData.getClass().getName());

            LOG.debug("service is " + service);

        } else {

            LOG.debug("supportedServices is null.");

        }

        // Try a "wildcard" match.
        if ( service == null ) {

            LOG.debug("service is null, trying wildcard match.");
            service = supportedServices.get(NCIPInitiationData.class.getName());
            LOG.debug("service is " + service);

        }

        if (service != null ) {

            LOG.debug("service is " + service + ", calling performService method.");
            try {

                responseData = service.performService(initiationData, serviceContext, serviceManager);
                LOG.debug("Result from performService call is " + responseData);

            } catch (ServiceException e) {

                List<Problem> problems = ServiceHelper.generateProblems(
                    Version1GeneralProcessingError.TEMPORARY_PROCESSING_FAILURE, "NCIPMessage", null, "Exception:", e);
                ProblemResponseData problemResponseData = new ProblemResponseData();
                problemResponseData.setProblems(problems);
                responseData = problemResponseData;

            } catch (ValidationException e) {

                ProblemResponseData problemResponseData = new ProblemResponseData();
                problemResponseData.setProblems(e.getProblems());
                responseData = problemResponseData;
            }

        } else {

            LOG.debug("service is null, returning Unsupported Service response.");
            List<Problem> problems = ServiceHelper.generateProblems(
                Version1GeneralProcessingError.UNSUPPORTED_SERVICE, "NCIPMessage", null, null);
            ProblemResponseData problemResponseData = new ProblemResponseData();
            problemResponseData.setProblems(problems);
            responseData = problemResponseData;

        }
        return responseData;
    }

}
