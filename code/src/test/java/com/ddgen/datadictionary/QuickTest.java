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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is not Unit Test....just for quick testing.
 *
 * @author Bhavani P Polimetla
 * @since Dec-25-2010
 *
 */
public class QuickTest {
	public static void main(String[] args) {

		java.util.Date today = new java.util.Date();
		System.out.println(new java.sql.Timestamp(today.getTime()));

		DateFormat dfm = new SimpleDateFormat("yyyyMMdd_HHmmss");
		System.out.println("Result: " + dfm.format(new Date()));

	}
}
