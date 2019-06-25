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
import org.apache.log4j.PropertyConfigurator;

/**
 * @author Bhavani P Polimetla
 * @since Dec-25-2010
 *
 */
public class LogWriter {
	static final Logger LOG = Logger.getLogger(LogWriter.class);
	static final String LOG_PROPERTIES_FILE = "log4j.properties";

	public static void initializeLogger() {
		Properties logProperties = new Properties();

		try {
			// load log4j properties / configuration file
			logProperties.load(new FileInputStream(LOG_PROPERTIES_FILE));
			PropertyConfigurator.configure(logProperties);
			LOG.info("Logging initialized.");
		} catch (IOException e) {
			throw new RuntimeException("Failed to read log4j properties file"
					+ LOG_PROPERTIES_FILE);
		}
	}

}
