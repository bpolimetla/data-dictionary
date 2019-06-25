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


package com.ddgen.datadictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.ddgen.datadictionary.oracle.NonTableDataBuilder;
import com.ddgen.datadictionary.oracle.TableDataBuilder;
import com.ddgen.datadictionary.printer.PrintReport;
import com.ddgen.datadictionary.utils.LogWriter;
import com.ddgen.datadictionary.vo.TableVO;

/**
 * This utility helps to prepare data dictionary. This generates three types of
 * reports. sc: Spell Checker dd: Data Dictionary diff: Used to compare
 * difference between dev, qa and Prod
 *
 * @author Bhavani P Polimetla
 * @since Dec-25-2010
 *
 */
public class DDGen {

	private static final Logger LOG = Logger.getLogger(DDGen.class);

	public DDGen() {
		LogWriter.initializeLogger();
	}

	/**
	 * Returns application help information.
	 *
	 * @return
	 */
	private static String getAbout() {
		StringBuffer sb = new StringBuffer();

		sb.append("\nUsage:java -jar DDGen.jar <reportName> <testMode>");
		sb
				.append("\n<reportName> sc:Spell Check, dd:Data Dictionary, "
						+ "diff:Compare two schemas, counts:Table row counts all:for all three");
		sb.append("\n<testMode> TRUE:runs for first 5 Tables, "
				+ "FALSE: Runs for all tables");
		sb.append("\nExample:java -jar DDGen.jar all true");

		sb.append("\n\nAuthor: Bhavani P Polimetla");
		sb.append("\nVersion 1.0");
		sb.append("\nwww.polimetla.com");
		return sb.toString();
	}

	/**
	 * Entry point for the project.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			// for(String arg:args)
			// System.out.println(arg);

			// Quick Test from Eclipse -- Un-comment following two lines.
			//String[] args2 = { "counts", "false" };
			//args = args2;

			if (args == null || args.length != 2) {
				System.out.println(getAbout());
				System.exit(-1);
			}

			String reportName = args[0];
			String testMode = args[1];

			if (!("sc".equalsIgnoreCase(reportName)
					|| "all".equalsIgnoreCase(reportName)
					|| "non".equalsIgnoreCase(reportName)
					|| "counts".equalsIgnoreCase(reportName)
					|| "dd".equalsIgnoreCase(reportName) || "diff"
					.equalsIgnoreCase(reportName))) {
				System.out.println(getAbout());
				System.exit(-1);
			}

			boolean isTest = true;
			if ("FALSE".equalsIgnoreCase(testMode))
				isTest = false;

			if ("non".equalsIgnoreCase(reportName)
					|| "all".equalsIgnoreCase(reportName)) {
				PrintReport.doPrintNonTableData(NonTableDataBuilder
						.getNonTableData());

				if ("non".equalsIgnoreCase(reportName)) {
					LOG.info("DDGen Process Completed.");
					System.exit(0);
				}
			}

			HashMap<String, TableVO> Result1 = TableDataBuilder
					.getTables(isTest);

			if (null == Result1 || Result1.size() <= 0) {
				System.out
						.println("Result set is zero. Check the configuration.");
				System.exit(-1);
			}

			Map<String, TableVO> Result2 = new TreeMap(Result1);

			if ("sc".equalsIgnoreCase(reportName)
					|| "all".equalsIgnoreCase(reportName))
				PrintReport.doSpellCheck(Result1);

			if ("dd".equalsIgnoreCase(reportName)
					|| "all".equalsIgnoreCase(reportName))
				PrintReport.printDataDictionaryHTML(Result2);

			if ("diff".equalsIgnoreCase(reportName)
					|| "all".equalsIgnoreCase(reportName))
				PrintReport.doPrintCompare(Result2);

			if ("counts".equalsIgnoreCase(reportName)
					|| "all".equalsIgnoreCase(reportName))
				PrintReport.doPrintTableRowCounts(Result2);

			LOG.info("DDGen Process Completed.");

		} catch (Exception ex) {
			LOG.error("Application failed.", ex);
		}
	}

}
