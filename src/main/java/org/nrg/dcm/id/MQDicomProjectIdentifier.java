/*
 * web: org.nrg.dcm.id.FixedDicomProjectIdentifier
 * XNAT http://www.xnat.org
 * Copyright (c) 2005-2017, Washington University School of Medicine and Howard Hughes Medical Institute
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.nrg.dcm.id;

import java.util.SortedSet;
import java.lang.ProcessBuilder;
import java.io.IOException;

import org.dcm4che2.data.DicomObject;
import org.nrg.xdat.om.XnatProjectdata;
import org.nrg.xft.security.UserI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.nrg.xdat.preferences.SiteConfigPreferences;

import com.google.common.collect.ImmutableSortedSet;

public final class MQDicomProjectIdentifier implements DicomProjectIdentifier
{

    private static final ImmutableSortedSet<Integer> TAGS = ImmutableSortedSet.of();
    private final String _name;
    private XnatProjectdata _project;

    private static final Pattern SIEMANS_PATTERN = Pattern.compile("(?:[^\\^]+)\\^((?:[a-zA-Z\\d]+))(.*?)"); 

    @SuppressWarnings("unused")
    public MQDicomProjectIdentifier(final XnatProjectdata project) {
        _project = project;
        String tempname = project.getName();
        final Matcher matcher = SIEMANS_PATTERN.matcher(tempname);
        if (matcher.find())
        {
            _name = matcher.group().substring(1);
        }
        else
        {
            _name = "Error";
        }
    }

    public MQDicomProjectIdentifier(final String name)
    {
        try {
            Process process = new ProcessBuilder("/usr/bin/python3", "/home/ec2-user/pylog.py", "-m", "hello matt!!!", "-sid", "SCP DICOM").start();
            Process processb = new ProcessBuilder("/usr/bin/python3", "/home/ec2-user/pylog.py", "-m", name, "-sid", "SCP DICOM-name").start();
		}
		catch (IOException e){};
        _project = null;
        final Matcher matcher = SIEMANS_PATTERN.matcher(name);
        if (matcher.find())
        {
            _name = matcher.group().substring(1);
        }
        else
        {
            _name = "Error";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<Integer> getTags() {
        return TAGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public XnatProjectdata apply(final UserI user, final DicomObject o)
    {
        try
        {
            Process process = new ProcessBuilder("/usr/bin/python3", "/home/ec2-user/pylog.py", "-m", "boo", "-sid", "apply DICOM").start();
        }
        catch (IOException e){};
        if (null == _project) {
            _project = XnatProjectdata.getProjectByIDorAlias(_name, user, false);
        }
        return _project;
    }

    @Override
    public void reset() {
        // Nothing to do here since this is just set at initialization.
    }
}
