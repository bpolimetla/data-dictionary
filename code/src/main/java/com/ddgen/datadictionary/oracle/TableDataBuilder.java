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
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleResultSetMetaData;

import org.apache.log4j.Logger;

import com.ddgen.datadictionary.utils.PropertyReader;
import com.ddgen.datadictionary.vo.ColumnVO;
import com.ddgen.datadictionary.vo.ForeignKeyVO;
import com.ddgen.datadictionary.vo.PrimaryKeyVO;
import com.ddgen.datadictionary.vo.TableVO;

/**
 * Build the meta data for Oracle Schema
 *
 * @author Bhavani P Polimetla
 * @since Dec-25-2010
 */
public class TableDataBuilder {

	private static final Logger LOG = Logger.getLogger(TableDataBuilder.class);
	private static boolean isTest = true;
	private static int TEST_TABLES = 5;

	// private static String DB_SHOW_TABLES =
	// "select * from tab where tabtype = 'TABLE'";
	private static String DB_SHOW_TABLES = "select * from all_tables where owner=";
	private static String DB_SCHEMA;
	static {
		DB_SCHEMA = PropertyReader.getKeyValue("SOURCE_DB_SCHEMA");
	}

	/**
	 * Return all tables for given connection.
	 *
	 * @param conGiven
	 * @return
	 */
	public static HashMap<String, TableVO> getTables(final boolean isTestable) {
		HashMap<String, TableVO> tableList = new HashMap<String, TableVO>();
		isTest = isTestable;
		Connection conGiven = ConnectionManager.getDriverConnection();
		try {

			Statement stmt = conGiven.createStatement();
			ResultSet rs = stmt.executeQuery(DB_SHOW_TABLES + "'" + DB_SCHEMA
					+ "'");

			String tableName = "";
			int i = 0;
			while (rs.next()) {

				if (isTest) {
					i++;
					if (i > TEST_TABLES)
						break;
				}

				// TableName = rs.getString("TNAME");
				tableName = rs.getString("TABLE_NAME");
				// for (int i = 1; i <= columnList.size(); i++) {
				// String colName = (String) columnList.get(i - 1);
				// Object data = rs.getObject(i);

				if (null != tableName) {
					LOG.info("processing==>" + tableName + new Date());
					// TableInfo table = getTableInfo(conGiven, TableName);

					// This helps to test specific table.
					// if(! "RATE_CD_SELL_EXCL".equalsIgnoreCase(tableName))
					// continue;

					TableVO table = getComment(conGiven, tableName);

					// Set Primary Keys
					Map<String, PrimaryKeyVO> pkList = getPrimaryKeys(conGiven,
							tableName);
					table.setPks(pkList);

					// Set Foreign Keys
					Map<String, ForeignKeyVO> fkList = getImportedKeys(
							conGiven, tableName);
					table.setFks(fkList);

					// Set Columns information
					List<ColumnVO> list2 = getColumns(conGiven, tableName,
							table);

					for (ColumnVO colInfo : list2) {
						ColumnVO temp = getColumnComments(conGiven, tableName,
								colInfo.getCOLUMN_NAME(), table);

						if (null != temp) {
							colInfo.setREMARKS(temp.getREMARKS());
							colInfo.setCOLUMN_DEF(temp.getCOLUMN_DEF());
						}
					}

					table.setColumns(list2);
					table.setRowCount(getTableRowCountApproximate(conGiven,
							tableName));

					tableList.put(tableName, table);

				}
			}

			rs.close();
			rs = null;
			stmt.close();
			stmt = null;

		} catch (Exception ex) {
			LOG.info("", ex);
		}
		ConnectionManager.closeConnection();
		return tableList;
	}

	/**
	 * Return column names for given table.
	 *
	 * @param conGiven
	 * @return
	 */
	private static List<ColumnVO> getColumns(final Connection conGiven,
			final String TableName, TableVO table) {
		LOG.info("========= getColumns =========" + TableName);
		List<ColumnVO> tableList = new ArrayList<ColumnVO>();
		try {
			Statement stmt = conGiven.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + DB_SCHEMA + "."
					+ TableName + " where rownum<=-1");

			// Get the column names; column indices start from 1

			// ====================================
			OracleResultSetMetaData rmd = (OracleResultSetMetaData) rs
					.getMetaData();

			int columnCount = rmd.getColumnCount();

			// Display number of Columns in the ResultSet
			LOG.debug("Number of Columns in the table : " + columnCount);

			for (int j = 1; j <= columnCount; j++) {

				String Column_Name = rmd.getColumnName(j);
				ColumnVO ci = new ColumnVO();

				ci.setTABLE_NAME(TableName);

				// Display number of Column name
				ci.setCOLUMN_NAME(rmd.getColumnName(j));
				LOG.info("Column Name :" + Column_Name);

				// Display number of Column Type
				LOG.info("   Column Type :" + rmd.getColumnTypeName(j));
				ci.setTYPE_NAME(rmd.getColumnTypeName(j));

				ci.setCOLUMN_SIZE(rmd.getPrecision(j));
				ci.setDECIMAL_DIGITS(rmd.getScale(j));

				// Display if Column can be NOT NULL
				switch (rmd.isNullable(j)) {

				case OracleResultSetMetaData.columnNoNulls:
					ci.setIS_NULLABLE("N");
					LOG.debug("  NOT NULL");
					break;
				case OracleResultSetMetaData.columnNullable:
					ci.setIS_NULLABLE("Y");
					LOG.debug("  NULLABLE");
					break;
				case OracleResultSetMetaData.columnNullableUnknown:
					ci.setIS_NULLABLE("?");
					LOG.debug("  NULLABLE Unkown");
				}

				if (table.getPks().containsKey(Column_Name))
					ci.setIS_PK("PK");
				if (table.getFks().containsKey(Column_Name))
					ci.setIS_FK("FK");

				tableList.add(ci);
			}

			// ======================

			rs.close();
			rs = null;
			stmt.close();
			stmt = null;

		} catch (Exception ex) {
			LOG.info("", ex);
		}

		return tableList;
	}

	/**
	 * SELECT count(*) from TABLE_NAME Pros – Provides exact number Cons: Takes
	 * longer time, based on total number of records
	 *
	 * @param conGiven
	 * @param TableName
	 * @return
	 */
	private static long getTableRowCountExact(final Connection conGiven,
			final String TableName) {
		LOG.info("========= getTableRowCount =========" + TableName);
		long rowCount = 0;
		try {
			Statement stmt = conGiven.createStatement();
			ResultSet rs = stmt.executeQuery("select count(*) from "
					+ DB_SCHEMA + "." + TableName);

			rs.next();
			rowCount = rs.getLong(1);

			rs.close();
			rs = null;
			stmt.close();
			stmt = null;

		} catch (Exception ex) {
			LOG.info("", ex);
		}

		return rowCount;
	}

	/**
	 * SELECT table_name, num_rows FROM ALL_TABLES where table_name =
	 * ‘TABLE_NAME’ Cons: Not accurate (may be 1%) numbers in run time, but good
	 * enough for business purpose in many cases. Pros: Pretty fast
	 *
	 * @param conGiven
	 * @param TableName
	 * @return
	 */
	private static long getTableRowCountApproximate(final Connection conGiven,
			final String TableName) {
		LOG.info("========= getTableRowCount =========" + TableName);
		long rowCount = 0;
		try {
			Statement stmt = conGiven.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT num_rows FROM ALL_TABLES where table_name = '"
							+ TableName + "'");

			rs.next();
			rowCount = rs.getLong(1);

			rs.close();
			rs = null;
			stmt.close();
			stmt = null;

		} catch (Exception ex) {
			LOG.info("", ex);
		}

		return rowCount;
	}

	/**
	 * Returns column commants.
	 *
	 * http://java.sun.com/j2se/1.4.2/docs/api/java/sql/DatabaseMetaData.html
	 *
	 * @param conGiven
	 * @param TableName
	 * @param ColumnName
	 * @return
	 */
	private static ColumnVO getColumnComments(final Connection conGiven,
			final String TableName, final String ColumnName, TableVO table) {
		LOG.info("========= getColumnComments =========" + TableName + "==>"
				+ ColumnName);
		ColumnVO rec = new ColumnVO();
		try {

			((oracle.jdbc.driver.OracleConnection) conGiven)
					.setRemarksReporting(true);

			DatabaseMetaData dmd = conGiven.getMetaData();
			ResultSet columnResultSet = dmd.getColumns(null, null, TableName,
					ColumnName);

			while (columnResultSet.next()) {
				rec.setCOLUMN_DEF(columnResultSet.getString("COLUMN_DEF"));
				// String ColName = columnResultSet.getString("COLUMN_NAME");
				// rec.setCOLUMN_NAME(ColName);
				// rec.setCOLUMN_SIZE(columnResultSet.getInt("COLUMN_SIZE"));
				// rec.setDECIMAL_DIGITS(columnResultSet.getInt("DECIMAL_DIGITS"));
				// rec.setIS_NULLABLE(columnResultSet.getString("IS_NULLABLE"));
				rec.setREMARKS(columnResultSet.getString("REMARKS"));
				LOG.info(TableName + " COL==>" + ColumnName
						+ "Column Comments==>" + rec.getREMARKS());
				// rec.setTABLE_NAME(columnResultSet.getString("TABLE_NAME"));
				// rec.setTYPE_NAME(columnResultSet.getString("TYPE_NAME"));
				//
				// if (table.getPks().containsKey(ColName))
				// rec.setIS_PK("PK");
				// if (table.getFks().containsKey(ColName))
				// rec.setIS_FK("FK");
			}
			columnResultSet.close();
			columnResultSet = null;

		} catch (Exception ex) {
			LOG.error("", ex);
		}

		return rec;
	}

	/**
	 * Get List of primery keys for given table.
	 *
	 * @param conGiven
	 * @param TableName
	 * @return
	 */
	private static Map<String, PrimaryKeyVO> getPrimaryKeys(
			final Connection conGiven, final String TableName) {
		LOG.info("========= getPrimaryKeys =========" + TableName);
		Map<String, PrimaryKeyVO> listPK = new HashMap<String, PrimaryKeyVO>();

		try {

			DatabaseMetaData dbMetaData = conGiven.getMetaData();
			ResultSet pkResultSet = dbMetaData.getPrimaryKeys(null, null,
					TableName);

			while (pkResultSet.next()) {
				PrimaryKeyVO rec = new PrimaryKeyVO();
				rec.setCOLUMN_NAME(pkResultSet.getString("COLUMN_NAME"));
				rec.setKEY_SEQ(pkResultSet.getString("KEY_SEQ"));
				rec.setPK_NAME(pkResultSet.getString("PK_NAME"));
				rec.setTABLE_NAME(pkResultSet.getString("TABLE_NAME"));
				listPK.put(pkResultSet.getString("COLUMN_NAME"), rec);
			}

			pkResultSet.close();
			pkResultSet = null;
			dbMetaData = null;

		} catch (Exception ex) {
			LOG.error("", ex);
		}

		return listPK;
	}

	/**
	 * Get FK Keys We are not printing exported keys and cross reference keys.
	 *
	 * @param conGiven
	 * @param TableName
	 * @return
	 */
	private static Map<String, ForeignKeyVO> getImportedKeys(
			final Connection conGiven, final String TableName) {

		LOG.info("========= getImportedKeys =========" + TableName);
		Map<String, ForeignKeyVO> listFK = new HashMap<String, ForeignKeyVO>();

		try {

			DatabaseMetaData dbMetaData = conGiven.getMetaData();
			// ResultSet fkResultSet = dbMetaData.getImportedKeys(null,
			// null,TableName);
			ResultSet fkResultSet = dbMetaData.getImportedKeys(null, null,
					TableName);

			while (fkResultSet.next()) {
				ForeignKeyVO rec = new ForeignKeyVO();
				rec.setDELETE_RULE(fkResultSet.getInt("DELETE_RULE"));
				rec.setFK_NAME(fkResultSet.getString("FK_NAME"));
				rec.setFKCOLUMN_NAME(fkResultSet.getString("FKCOLUMN_NAME"));
				rec.setFKTABLE_NAME(fkResultSet.getString("FKTABLE_NAME"));
				rec.setKEY_SEQ(fkResultSet.getInt("KEY_SEQ"));
				rec.setPK_NAME(fkResultSet.getString("PK_NAME"));
				rec.setPKCOLUMN_NAME(fkResultSet.getString("PKCOLUMN_NAME"));
				rec.setPKTABLE_NAME(fkResultSet.getString("PKTABLE_NAME"));
				rec.setUPDATE_RULE(fkResultSet.getInt("UPDATE_RULE"));

				listFK.put(fkResultSet.getString("FKCOLUMN_NAME"), rec);
			}

			fkResultSet.close();
			fkResultSet = null;
			dbMetaData = null;

		} catch (Exception ex) {
			LOG.error("", ex);
		}

		return listFK;
	}

	/**
	 * getTables is not giving comments. Due to this using sql query to get it.
	 *
	 * @param conGiven
	 * @param TableName
	 * @return
	 */
	private static TableVO getComment(final Connection conGiven,
			final String TableName) {
		LOG.info("========= getComment =========" + TableName);
		TableVO tableInfo = new TableVO();
		try {

			Statement stmt = conGiven.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT COMMENTS FROM ALL_TAB_COMMENTS WHERE TABLE_NAME = UPPER('"
							+ TableName + "')");

			rs.next();
			tableInfo.setTABLE_NAME(TableName);
			tableInfo.setREMARKS(rs.getString("COMMENTS"));
			// tableInfo.setEngine(rs.getString("Engine"));
			// tableInfo.setCreateDate(rs.getDate("Create_time"));
			// tableInfo.setUpdateDate(rs.getDate("Update_time"));
			tableInfo.print();

		} catch (Exception ex) {
			LOG.error("", ex);
		}

		return tableInfo;

	}
}
