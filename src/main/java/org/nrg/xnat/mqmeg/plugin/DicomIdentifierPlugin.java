package org.nrg.xnat.mqmeg.plugin;
import org.apache.log4j.Logger;
import org.nrg.framework.annotations.XnatPlugin;
import org.springframework.context.annotation.ImportResource;

@XnatPlugin(value = "dicomidentifier",
            name = "XNAT 1.7 DICOM Identifier Plugin",
            description = "This is the XNAT 1.7 DICOM Identifier Plugin.",
            entityPackages = "org.nrg.framework.orm.hibernate.HibernateEntityPackageList",
            log4jPropertiesFile = "module-log4j.properties")
@ImportResource("WEB-INF/conf/dicom-import-context.xml")
public class DicomIdentifierPlugin {

	/** The logger. */
	public static Logger logger = Logger.getLogger(DicomIdentifierPlugin.class);


    /**
     * Instantiates a new MEG importer plugin
     */
    public DicomIdentifierPlugin() {
        logger.info("Configuring MEG importer plugin");
    }
    
}