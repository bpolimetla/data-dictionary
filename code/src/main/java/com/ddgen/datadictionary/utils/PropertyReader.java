/*
This file is part of "Data Dictionary Generator Application".

"Data Dictionary Generator Application" is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, version 3.

"Data Dictionary Generator Application" is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with "Data Dictionary Generator Application".  If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 */


package com.ddgen.datadictionary.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Read the values from DDGen.properties file
 *
 * @author Bhavani P Polimetla
 * @since Dec-25-2010
 *
 */
public class PropertyReader {

	private static final Logger LOG = Logger.getLogger(PropertyReader.class);
	private static final String PROPERTIES_FILE = "DDGen.properties";
	private static Properties props = null;

	public static String getKeyValue(String key) {
		if (null == props)
			readProperties();
		return props.getProperty(key);
	}

	private static void readProperties() {
		if (null != props)
			return;

		props = new Properties();
		try {

			props.load(new FileInputStream(PROPERTIES_FILE));
		} catch (IOException e) {
			LOG.error("Failed to load properties file" + PROPERTIES_FILE, e);
			props = null;
		}

	}

}
