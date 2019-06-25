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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ddgen.datadictionary.utils.PropertyReader;

/**
 * Build data for Oracle Schema for other than tables.
 * This information is used to compare schemas
 *
 * @author Bhavani P Polimetla
 * @since Dec-25-2010
 */
public class NonTableDataBuilder {

	private static final Logger LOG = Logger
			.getLogger(NonTableDataBuilder.class);

	private static String DB_SCHEMA;
	static {
		DB_SCHEMA = PropertyReader.getKeyValue("SOURCE_DB_SCHEMA");
	}

	public static List<String> getNonTableData() {
		List<String> allData = new ArrayList<String>();
		allData.addAll(getConstraints());
		allData.addAll(getINDEXES());
		allData.addAll(getSEQUENCES());
		allData.addAll(getTRIGGERS());
		allData.addAll(getPRIVILEGES());
		return allData;
	}

	private static List<String> getConstraints() {

		List<String> listConstraints = new ArrayList<String>();
		listConstraints
				.add("\n=============================CONSTRAINTS=============================\n");
		listConstraints
				.add("\nCONSTRAINTS==constraint_name==constraint_type== delete_rule==status==index_name\n");
		try {
			Connection conGiven = ConnectionManager.getDriverConnection();
			Statement stmt = conGiven.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT 'CONSTRAINTS=='||table_name ||'=='||constraint_name||'=='||constraint_type||'=='|| "
							+ "delete_rule||'=='||status||'=='||index_name "
							+ "FROM all_CONSTRAINTS where owner = '"
							+ DB_SCHEMA
							+ "'  order by table_name, constraint_name");

			while (rs.next()) {
				listConstraints.add(rs.getString(1));
			}

			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
		} catch (Exception ex) {
			LOG.error("Failed to get Constraints", ex);
			ex.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}

		return listConstraints;

	}

	private static List<String> getINDEXES() {

		Connection conGiven = ConnectionManager.getDriverConnection();
		List<String> listConstraints = new ArrayList<String>();
		listConstraints
				.add("\n=============================INDEXES=============================\n");
		listConstraints
				.add("\nINDEXES==table_name==index_name==index_type==instances\n");
		try {

			Statement stmt = conGiven.createStatement();
			ResultSet rs = stmt
					.executeQuery("select 'INDEXES=='||table_name||'=='||index_name||'=='||index_type||'=='||instances "
							+ " from ALL_INDEXES where table_owner = '"
							+ DB_SCHEMA + "' order by table_name, index_name");

			while (rs.next()) {
				listConstraints.add(rs.getString(1));
			}

			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
		} catch (Exception ex) {
			LOG.error("Failed to get Constraints", ex);
		} finally {
			ConnectionManager.closeConnection();
		}

		return listConstraints;
	}

	private static List<String> getSEQUENCES() {

		Connection conGiven = ConnectionManager.getDriverConnection();
		List<String> listConstraints = new ArrayList<String>();
		listConstraints
				.add("\n=============================SEQUENCES=============================\n");
		listConstraints
				.add("\nSEQUENCES==sequence_name==min_value==max_value==increment_by==cycle_flag \n");
		try {

			Statement stmt = conGiven.createStatement();
			ResultSet rs = stmt
					.executeQuery("select 'SEQUENCES=='||sequence_name||'=='||min_value ||'=='||max_value||'=='||increment_by ||'=='||cycle_flag "
							+ " from ALL_SEQUENCES where sequence_owner = '"
							+ DB_SCHEMA + "' ORDER BY sequence_name");

			while (rs.next()) {
				listConstraints.add(rs.getString(1));
			}

			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
		} catch (Exception ex) {
			LOG.error("Failed to get Constraints", ex);
		} finally {
			ConnectionManager.closeConnection();
		}

		return listConstraints;
	}

	private static List<String> getTRIGGERS() {

		Connection conGiven = ConnectionManager.getDriverConnection();
		List<String> listConstraints = new ArrayList<String>();
		listConstraints
				.add("\n=============================TRIGGERS=============================\n");
		listConstraints
				.add("\nTRIGGERS==TRIGGER_NAME==trigger_type==triggering_event==table_owner==base_object_type==table_name==column_name==when_clause==status==action_type\n");
		try {

			Statement stmt = conGiven.createStatement();
			ResultSet rs = stmt
					.executeQuery("select 'TRIGGERS=='||TRIGGER_NAME||'=='||trigger_type||'=='||triggering_event||'=='||table_owner||'=='||base_object_type||'=='||table_name||'=='||column_name||'=='||when_clause||'=='||status||'=='||action_type "
							+ " from ALL_TRIGGERS where owner='"
							+ DB_SCHEMA
							+ "' order by table_name, trigger_name");

			while (rs.next()) {
				listConstraints.add(rs.getString(1));
			}

			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
		} catch (Exception ex) {
			LOG.error("Failed to get Constraints", ex);
		} finally {
			ConnectionManager.closeConnection();
		}

		return listConstraints;
	}

	private static List<String> getPRIVILEGES() {

		Connection conGiven = ConnectionManager.getDriverConnection();
		List<String> listConstraints = new ArrayList<String>();
		listConstraints
				.add("\n=============================PRIVILEGES=============================\n");
		listConstraints
				.add("\nPRIVILEGES==TABLE_NAME==GRANTEE==SELECT_PRIV==INSERT_PRIV==DELETE_PRIV==UPDATE_PRIV==REFERENCES_PRIV==ALTER_PRIV==INDEX_PRIV \n");
		try {

			Statement stmt = conGiven.createStatement();
			ResultSet rs = stmt
					.executeQuery("select 'PRIVILEGES=='||TABLE_NAME||'=='||GRANTEE  ||'=='||SELECT_PRIV||'=='||INSERT_PRIV||'=='||DELETE_PRIV||'=='||UPDATE_PRIV||'=='||REFERENCES_PRIV||'=='||ALTER_PRIV||'=='||INDEX_PRIV "
							+ " from TABLE_PRIVILEGES where owner='"
							+ DB_SCHEMA + "' order by table_name, GRANTEE");

			while (rs.next()) {
				listConstraints.add(rs.getString(1));
			}

			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
		} catch (Exception ex) {
			LOG.error("Failed to get Constraints", ex);
		} finally {
			ConnectionManager.closeConnection();
		}

		return listConstraints;
	}

}
