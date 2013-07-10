package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.ToolkitException;

/**
 * A marker interface for Toolkit components.
 */
public interface ToolkitComponentConfigurationFactory {

    public ToolkitConfiguration getConfiguration() throws ToolkitException;

}
