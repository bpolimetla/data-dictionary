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


package com.ddgen.datadictionary.printer;

import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ddgen.datadictionary.utils.PropertyReader;
import com.ddgen.datadictionary.vo.ColumnVO;
import com.ddgen.datadictionary.vo.ForeignKeyVO;
import com.ddgen.datadictionary.vo.TableVO;

/**
 * used to print given data in required format.
 *
 * @author Bhavani P Polimetla
 * @since Dec-25-2010
 *
 */
public class PrintReport {
	private static final Logger LOG = Logger.getLogger(PrintReport.class);
	private static FileWriter fileHandle = null;
	private static DateFormat dfm = new SimpleDateFormat("yyyyMMdd_HHmmss");

	// private static final String LINEBREAK = "<br>";

	private static String getHeaderString(final String LINEBREAK) {
		StringBuffer sb = new StringBuffer();

		if (LINEBREAK.contains("BR"))
			sb.append("<b>Data Dictionary</b>").append(LINEBREAK);
		else
			sb.append("Data Dictionary").append(LINEBREAK);

		sb.append("Date: ").append(new Date()).append(LINEBREAK);
		sb
				.append(
						"-------------------- Database Info ------------------------")
				.append(LINEBREAK);
		String DB_SCHEMA = PropertyReader.getKeyValue("SOURCE_DB_SCHEMA");
		String URL = PropertyReader.getKeyValue("SOURCE_DB_URL");
		String USER = PropertyReader.getKeyValue("SOURCE_DB_USER_ID");
		sb.append("Database URL:").append(URL).append(LINEBREAK);
		sb.append("Schema:").append(DB_SCHEMA).append(LINEBREAK);
		sb.append("User:").append(USER).append(LINEBREAK);
		sb
				.append(
						"--------------------- Software Info -----------------------")
				.append(LINEBREAK);
		if (LINEBREAK.contains("BR"))
			sb
					.append(
							"Visit <a href=\"http://ddgen.wordpress.com/\" "
									+ "target=\"_blank\">http://ddgen.wordpress.com</a>  "
									+ "for latest updates.").append(LINEBREAK);
		else
			sb.append("Visit http://ddgen.wordpress.com for latest updates.")
					.append(LINEBREAK);

		sb.append("Version:").append("1.0").append(LINEBREAK);
		sb.append("Author:").append("Bhavani P Polimetla").append(LINEBREAK);
		sb.append("Date:").append("Sep 2008").append(LINEBREAK);
		sb.append("-----------------------------------------------------")
				.append(LINEBREAK);

		return sb.toString();
	}

	private static void printTextHeader() {
		printToFile(getHeaderString("\n"));
	}

	private static void printHTMLHeader(Map<String, TableVO> listGiven) {
		StringBuffer sb = new StringBuffer();

		sb.append(getHeaderString("<br>"));

		if (null == listGiven || listGiven.size() <= 0) {
			sb
					.append(
							"Make sure that the following statement is returing results in database for above information.")
					.append("<br>");
			String DB_SCHEMA = PropertyReader.getKeyValue("SOURCE_DB_SCHEMA");
			sb.append("select * from all_tables where owner='").append(
					DB_SCHEMA).append("'").append("<br><br>");
		}
		printToFile(sb.toString());
	}

	/**
	 * Use this to spell check column names.
	 *
	 * @param listGiven
	 */
	public static void doSpellCheck(Map<String, TableVO> listGiven) {
		LOG.info("================= Spell Check Begin ================");

		try {

			String timeStamp = dfm.format(new Date());
			fileHandle = new FileWriter("db_spell_check_" + timeStamp + ".txt",
					true);
		} catch (Exception ex) {
			LOG.error("", ex);
		}

		String columnName = "";
		for (Iterator it = listGiven.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			TableVO table = listGiven.get(key);
			List<ColumnVO> r1 = table.getColumns();
			for (int j = 0; j < r1.size(); j++) {
				ColumnVO rec = r1.get(j);
				columnName = rec.getCOLUMN_NAME();
				columnName = columnName.replace("_", " ");
				printToFile("\n" + rec.getTABLE_NAME() + "==>" + columnName);
			}
		}

		try {
			fileHandle.close();
		} catch (Exception ex) {
			LOG.error("", ex);
		}
		LOG.info("================= Spell Check End ================");
	}

	/**
	 * Use this to compare two database schemas
	 *
	 * @param listGiven
	 */
	public static void doPrintCompare(Map<String, TableVO> listGiven) {

		try {
			String timeStamp = dfm.format(new Date());
			fileHandle = new FileWriter("db_diff_" + timeStamp + ".txt", true);
			printTextHeader();
			printToFile("\nTABLE_NAME|COLUMN_NAME|Column Type|COLUMN_SIZE|DECIMAL_DIGITS|Default Data|IS_PK|IS_FK\n");
		} catch (Exception ex) {
			LOG.error("", ex);
		}

		printToFile("================= DB Diff Begin ================\n");

		for (Iterator it = listGiven.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			TableVO table = listGiven.get(key);
			List<ColumnVO> r1 = table.getColumns();
			StringBuffer sb = new StringBuffer();
			String SEPARATOR = "==";
			for (int j = 0; j < r1.size(); j++) {
				ColumnVO rec = r1.get(j);
				sb = new StringBuffer();
				sb.append(rec.getTABLE_NAME() + SEPARATOR);
				sb.append(rec.getCOLUMN_NAME() + SEPARATOR);
				sb.append(rec.getTYPE_NAME() + SEPARATOR);
				sb.append(rec.getCOLUMN_SIZE() + SEPARATOR);
				sb.append(rec.getDECIMAL_DIGITS() + SEPARATOR);
				sb.append(rec.getCOLUMN_DEF() + SEPARATOR);
				sb.append(rec.getIS_PK() + SEPARATOR);
				sb.append(rec.getIS_FK() + SEPARATOR);

				printToFile(sb.toString() + "\n");
			}
		}
		printToFile("\n================= DB Diff End ================");

		try {
			fileHandle.close();
		} catch (Exception ex) {
			LOG.error("", ex);
		}
	}

	/**
	 * Use this to compare two database schemas
	 *
	 * @param listGiven
	 */
	public static void doPrintNonTableData(List<String> listGiven) {

		try {
			String timeStamp = dfm.format(new Date());
			fileHandle = new FileWriter("db_NonTableData" + timeStamp + ".txt",
					true);
			printTextHeader();
			printToFile("================= DB Non Table Data Begin ================\n");

		} catch (Exception ex) {
			LOG.error("", ex);
		}

		for (String line : listGiven) {
			printToFile(line + "\n");
		}
		printToFile("\n================= DB Non Table Data End ================");

		try {
			fileHandle.close();
		} catch (Exception ex) {
			LOG.error("", ex);
		}
	}

	/**
	 * Use this to compare two database schemas
	 *
	 * @param listGiven
	 */
	public static void doPrintTableRowCounts(Map<String, TableVO> listGiven) {

		try {
			String timeStamp = dfm.format(new Date());
			fileHandle = new FileWriter(
					"db_TableRowCount" + timeStamp + ".txt", true);
			printTextHeader();
			printToFile("================= Table Row Count Begin ================\n");

		} catch (Exception ex) {
			LOG.error("", ex);
		}

		for (Iterator it = listGiven.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			TableVO table = listGiven.get(key);
			printToFile(key + "==" + table.getRowCount() + "\n");
		}
		printToFile("\n================= Table Row Count  End ================");

		try {
			fileHandle.close();
		} catch (Exception ex) {
			LOG.error("", ex);
		}
	}

	/**
	 * Print Data Dictionary in text format
	 *
	 * @param listGiven
	 */
	public static void printDataDictionaryHTML(Map<String, TableVO> listGiven) {
		LOG.info("============= printDataDictionaryHTML Begin =============");
		try {
			String timeStamp = dfm.format(new Date());
			fileHandle = new FileWriter("Data_Dictionary_" + timeStamp
					+ ".html", true);
			printCSS();
		} catch (Exception ex) {
			LOG.error("", ex);
		}

		printHTMLHeader(listGiven);
		String columnName = "";
		StringBuffer sbTemp = new StringBuffer();
		String recordSeparatorBegin = "<td>";
		String recordSeparatorEnd = "</td>";
		String TABLEHEADING = "<table BORDER=1 CELLSPACING = 0 BORDERCOLOR=E0E0E0>";

		for (Iterator it = listGiven.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			TableVO table = listGiven.get(key);
			printToFile(TABLEHEADING);
			LOG.info("processing==>" + table.getTABLE_NAME());
			printToFile("<tr><td COLSPAN='7' align='left'>Table:<b>"
					+ table.getTABLE_NAME() + "</b>");
			String remarks = table.getREMARKS();
			if (null != remarks) {
				String array[] = remarks.split(";");
				remarks = array[0];
				if (remarks.contains("free:"))
					remarks = "";
			}
			printToFile("<br>Table Remarks:" + remarks);
			printToFile("<br>Table Type:" + table.getEngine() + "</td></tr>");
			printToFile("<tr class='head1'><td><b>Column Name</b></td>"
					+ "<td><b>Type</b></td>" + "<td><b>PK</b></td>"
					+ "<td><b>FK</b></td>" + "<td><b>Is Nullabe</b></td>"
					+ "<td><b>Default</b></td>"
					+ "<td><b>Comment</b></td></tr></b>");
			// Print Columns
			List<ColumnVO> r1 = table.getColumns();
			String PKValue = "";
			String FKValue = "";
			String DefValue = "";
			for (int j = 0; j < r1.size(); j++) {
				ColumnVO rec = r1.get(j);

				if (j % 2 == 0)
					sbTemp.append("<tr class='row0'>");
				else
					sbTemp.append("<tr class='row1'>");
				// sbTemp.append(recordSeparatorBegin + rec.getTABLE_NAME()+
				// recordSeparatorEnd);
				sbTemp.append(recordSeparatorBegin + rec.getCOLUMN_NAME()
						+ recordSeparatorEnd);

				if (0 == rec.getCOLUMN_SIZE() && 0 == rec.getDECIMAL_DIGITS())
					sbTemp.append(recordSeparatorBegin + rec.getTYPE_NAME()
							+ recordSeparatorEnd);
				else if (0 != rec.getCOLUMN_SIZE()
						&& 0 == rec.getDECIMAL_DIGITS())
					sbTemp.append(recordSeparatorBegin + rec.getTYPE_NAME()
							+ "(" + rec.getCOLUMN_SIZE() + ")"
							+ recordSeparatorEnd);
				else
					sbTemp.append(recordSeparatorBegin + rec.getTYPE_NAME()
							+ "(" + rec.getCOLUMN_SIZE() + ","
							+ rec.getDECIMAL_DIGITS() + ")"
							+ recordSeparatorEnd);

				PKValue = rec.getIS_PK();
				if (PKValue == null || PKValue.equalsIgnoreCase("null"))
					PKValue = "&nbsp;";
				sbTemp.append(recordSeparatorBegin + PKValue
						+ recordSeparatorEnd);

				FKValue = rec.getIS_FK();
				if (FKValue == null || FKValue.equalsIgnoreCase("null"))
					FKValue = "&nbsp;";

				sbTemp.append(recordSeparatorBegin + FKValue
						+ recordSeparatorEnd);
				sbTemp.append(recordSeparatorBegin + rec.getIS_NULLABLE()
						+ recordSeparatorEnd);

				DefValue = rec.getCOLUMN_DEF();
				if (DefValue == null || DefValue.equalsIgnoreCase("null"))
					DefValue = "&nbsp;";

				sbTemp.append(recordSeparatorBegin + DefValue
						+ recordSeparatorEnd);
				String Remarks = "";
				if (null != rec.getREMARKS())
					Remarks = rec.getREMARKS().replace("\n", " ");

				sbTemp.append(recordSeparatorBegin + Remarks + "&nbsp;"
						+ recordSeparatorEnd);
				sbTemp.append("</tr>\n");
				printToFile(sbTemp.toString());
				sbTemp = new StringBuffer();
			}

			/*
			 * // Print Primary Keys print("<tr>"); print(TABLEHEADING);
			 * List<PrimaryKeyInfo> pkList = table.getPks(); for (int j = 0; j <
			 * pkList.size(); j++) { sbTemp = new StringBuffer(); PrimaryKeyInfo
			 * rec = pkList.get(j); sbTemp.append("<tr>");
			 * //sbTemp.append(recordSeparatorBegin + rec.getTABLE_NAME()+
			 * recordSeparatorEnd); sbTemp.append(recordSeparatorBegin +
			 * rec.getCOLUMN_NAME() + recordSeparatorEnd);
			 * //sbTemp.append(recordSeparatorBegin + rec.getKEY_SEQ()+
			 * recordSeparatorEnd); sbTemp.append(recordSeparatorBegin +
			 * rec.getPK_NAME() + recordSeparatorEnd); sbTemp.append("</tr>");
			 *
			 * print(sbTemp.toString()); sbTemp = new StringBuffer(); }
			 * print("</table>"); print("</tr>");
			 */

			// Print Foreign Keys
			printToFile("<tr>");

			printToFile(TABLEHEADING);
			printToFile("<tr><td COLSPAN='5' align='left'><b>Foreign Keys</b></td></tr>");
			printToFile("<tr class='head_fk'><td><b>FK</b></td>"
					+ "<td><b>From</b></td>" + "<td><b>To</b></td>"
					+ "<td><b>Delete Rule</b></td>"
					+ "<td><b>Update Rule</b></td>" + "</tr>\n");
			Map<String, ForeignKeyVO> fkList = table.getFks();
			int rowCount = 0;
			if (null != fkList)
				for (Iterator it2 = fkList.keySet().iterator(); it2.hasNext();) {
					String key2 = (String) it2.next();
					ForeignKeyVO rec = (ForeignKeyVO) fkList.get(key2);
					sbTemp = new StringBuffer();
					if (rowCount % 2 == 0)
						sbTemp.append("<tr class='row0'>");
					else
						sbTemp.append("<tr class='row1'>");
					rowCount++;
					sbTemp.append(recordSeparatorBegin + rec.getFK_NAME()
							+ recordSeparatorEnd);
					sbTemp
							.append(recordSeparatorBegin
									+ rec.getFKTABLE_NAME() + "."
									+ rec.getFKCOLUMN_NAME()
									+ recordSeparatorEnd);
					// sbTemp.append(recordSeparatorBegin + rec.getPK_NAME()+
					// recordSeparatorEnd);
					sbTemp
							.append(recordSeparatorBegin
									+ rec.getPKTABLE_NAME() + "."
									+ rec.getPKCOLUMN_NAME()
									+ recordSeparatorEnd);
					sbTemp.append(recordSeparatorBegin + rec.getDELETE_RULE()
							+ recordSeparatorEnd);
					sbTemp.append(recordSeparatorBegin + rec.getUPDATE_RULE()
							+ recordSeparatorEnd);
					// sbTemp.append(recordSeparatorBegin + rec.getKEY_SEQ()+
					// recordSeparatorEnd);
					sbTemp.append("</tr>\n");
					printToFile(sbTemp.toString());
					sbTemp = new StringBuffer();
				}
			printToFile("</table>");
			printToFile("</tr>\n");

			printToFile("</table>\n\n");
			printToFile("</br></br>");
			LOG.info("============= printDataDictionaryHTML End =============");
		}

		printToFile("================= Data Dictionary End ================");
		printToFile("</body>");

		try {
			fileHandle.close();
		} catch (Exception ex) {
			LOG.error("", ex);
		}

	}

	private static void printToFile(String givenText) {
		try {
			fileHandle.write(givenText);
		} catch (Exception ex) {
			LOG.error("", ex);
		}
	}

	/**
	 * Print CSS Header in begenning
	 */
	private static void printCSS() {
		printToFile("<html>");
		printToFile("<head>");
		printToFile("");

		printToFile("<style type='text/css'>");
		printToFile("tr.row0 td {background-color: #FFFFFF; color: black;}");
		printToFile("tr.row1 td {background-color: #CCE6FF; color: black;}");
		printToFile("tr.head1 td {background-color: #99CCFF; color: black;}");
		printToFile("tr.head_fk td {background-color: #CCFFFF; color: black;}");
		printToFile("</style>");

		printToFile("</head><body>");
	}

}
