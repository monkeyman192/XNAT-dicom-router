package org.nrg.xnat.mqmeg.plugin;

import org.apache.log4j.Logger;
import org.nrg.framework.annotations.XnatPlugin;
import org.springframework.context.annotation.ImportResource;

@XnatPlugin(value = "dinadDicomIdentifier",
            name = "DINAD DICOM Identifier Plugin",
            description = "DINAD DICOM Identifier Plugin.",
            log4jPropertiesFile = "dinad-module-log4j.properties")
@ImportResource("WEB-INF/conf/dinad-import-context.xml")
public class DinadIdentifierPlugin {
    /**
     * The logger.
     */
    private static Logger logger = Logger.getLogger(DinadIdentifierPlugin.class);

    /**
     * Instantiates a new MEG importer plugin
     */
    public DinadIdentifierPlugin() {
        logger.info("Configuring MEG importer plugin");
    }
}