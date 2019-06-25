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


package com.ddgen.datadictionary.oracle;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

import com.ddgen.datadictionary.utils.PropertyReader;

/**
 * This manages Oracle connections.
 *
 * @author Bhavani P Polimetla
 * @since Dec-25-2010
 *
 */
public final class ConnectionManager {

	private static Connection dbConnection = null;
	private static final Logger LOG = Logger.getLogger(ConnectionManager.class);

	/**
	 * Close connection at the end.
	 */
	public static void closeConnection() {
		try {
			if (null != dbConnection && !dbConnection.isClosed()) {
				dbConnection.close();
				dbConnection = null;
				LOG.info("closeConnection successfull");
			}
		} catch (Exception ex) {
			LOG.error("==>", ex);
		}
	}

	/**
	 * Get Oracle Connection from parameters in DDGen.properties
	 *
	 * @return
	 */
	public static Connection getDriverConnection() {
		try {

			if (null != dbConnection && !dbConnection.isClosed())
				return dbConnection;

			String url = PropertyReader.getKeyValue("SOURCE_DB_URL");
			String user = PropertyReader.getKeyValue("SOURCE_DB_USER_ID");
			String password = PropertyReader.getKeyValue("SOURCE_DB_PWD");

			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();

			dbConnection = DriverManager.getConnection(url, user, password);
			dbConnection.setReadOnly(true);

		} catch (Exception ex) {
			LOG.error("Failed to get Oracle Connection", ex);
			ex.printStackTrace();
			System.exit(-1);
		}
		return dbConnection;
	}

}
