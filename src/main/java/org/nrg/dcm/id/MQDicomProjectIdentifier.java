/**
 * Modified from the FixedDicomProjectIdentifier file by Matt Sanderson <matt.sanderson@mq.edu.au>
 * Original information:
 * web: org.nrg.dcm.id.FixedDicomProjectIdentifier
 * XNAT http://www.xnat.org
 * Copyright (c) 2005-2017, Washington University School of Medicine and Howard Hughes Medical Institute
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.nrg.dcm.id;

import java.util.SortedSet;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.nrg.xdat.om.XnatProjectdata;
import org.nrg.xft.security.UserI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.ImmutableSortedSet;

public final class MQDicomProjectIdentifier implements DicomProjectIdentifier
{

    private static final ImmutableSortedSet<Integer> TAGS = ImmutableSortedSet.of();
    private String name = null;
    // The current name of the project. This is used to track if it changes to return a new XNAT project object
    private String proj_name = null;
    private XnatProjectdata _project;

    //public static org.apache.log4j.Logger logger = Logger.getLogger(MQDicomProjectIdentifier.class);

    // Define the regex patterns reuired to extract the required information from the descriptions

    // this pattern will match (anything)^[info we want].
    // [info we want] must consist of letters and numbers, the matching stops once a non-alphanumeric character is hit
    private static final Pattern SIEMENS_PATTERN = Pattern.compile("(?:[^\\^]+)\\^((?:[a-zA-Z\\d]+))(.*?)");
    // this pattern will match (anything)[one of TOTAL, DINAD, BRAINPROJECT, or VAD](anything)
    private static final Pattern GE_PATTERN = Pattern.compile("(.*?)(TOTAL|DINAD|BRAINPROJECT|VAD|CHROME)(.*?)");

    // This doesn't seem to be called at any point, but removing it seems to cause the plugin to not work...
    // So best to just keep it...
    @SuppressWarnings("unused")
    public MQDicomProjectIdentifier(final XnatProjectdata project) {
        _project = project;
    }

    /**
     * Main constructor.
     * For this project it is empty because we have no arguments or parameters we need to pass
     * to this class, however you can pass whatever you want here.
     * The value is passed as an argument in the Spring xml file like:
     * <constructor-arg ref=""/>
     * or
     * <constructor-arg value=""/>
     * in order of the declared arguments.
     * 
     * It should be noted that this constructor is only called when the identifier is first instantiated
     * (ie. when it receives it's first file.)
     * For this reason it's probably better if you need to do any logic based on data in the dicom file itself
     * to have this in the `apply` method.
     */
    public MQDicomProjectIdentifier()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<Integer> getTags() {
        return TAGS;
    }

    /**
     * Main function which gets called by the CompositeDicomObjectIdentifier for each Dicom file.
     * Here we simply apply the regex expressions to the StudyDescription extracted from the Dicom file.
     * @return
     * XnatProjectdata as determined by whatever conditions are laid out in this method.
     */
    @Override
    public XnatProjectdata apply(final UserI user, final DicomObject o)
    {
        final String description = o.getString(Tag.StudyDescription);
        Matcher siemens_matcher = SIEMENS_PATTERN.matcher(description);
        Matcher ge_matcher = GE_PATTERN.matcher(description);
        // First, check for a match with the SIEMENS regex
        if (siemens_matcher.matches())
        {
            name = siemens_matcher.group(1);
        }
        // If no match move on to the GE regex
        else if (ge_matcher.matches())
        {
            name = ge_matcher.group(2);
        }
        // If *still* no match we will just set the name as null which will result in the data buing unassigned in the pre-archive
        else
        {
            name = null;
        }
        
        //logger.info(String.format("putting MRI files into project %s", name.toString()));

        // only find the new project if the name has changed, otherwise we simply return the cached _project
        // This differs from the FixedDicomProjectIdentifier which assigned _project permanently.
        // We are expecting the project to change depending on the file
        if (name != null)
        {
            if (name != proj_name)
            {
                _project = XnatProjectdata.getProjectByIDorAlias(name, user, false);
            }
        }
        else
        {
            _project = null;
        }
        return _project;
    }


    @Override
    public void reset() {
        // Nothing to do here since this is just set at initialization.
    }
}
