package org.nrg.xnat.mq.plugin;
import org.apache.log4j.Logger;
import org.nrg.framework.annotations.XnatPlugin;
import org.springframework.context.annotation.ImportResource;

@XnatPlugin(value = "MQ_DicomIdentifier",
            name = "Macquarie University Custom Dicom SCP Routing Plugin",
            description = "Automatically routes Dicom files uploaded via SCP.",
            entityPackages = "org.nrg.framework.orm.hibernate.HibernateEntityPackageList",
            log4jPropertiesFile = "module-log4j.properties")
@ImportResource("WEB-INF/conf/MQ-import-context.xml")
public class MQDicomIdentifierPlugin {

	/** The logger. */
	public static Logger logger = Logger.getLogger(MQDicomIdentifierPlugin.class);


    /**
     * Instantiates a new SCP Dicom routing plugin
     */
    public MQDicomIdentifierPlugin() {
        logger.info("Configuring SCP Dicom routing plugin");
    }
    
}