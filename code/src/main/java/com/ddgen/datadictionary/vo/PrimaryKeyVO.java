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


package com.ddgen.datadictionary.vo;

/**
 * Holds primary key information
 *
 * @author Bhavani P Polimetla
 * @since Dec-25-2010
 *
 */
public class PrimaryKeyVO {

	private String TABLE_NAME;
	private String COLUMN_NAME;
	private String KEY_SEQ;
	private String PK_NAME;

	public String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}

	public String getCOLUMN_NAME() {
		return COLUMN_NAME;
	}

	public void setCOLUMN_NAME(String column_name) {
		COLUMN_NAME = column_name;
	}

	public String getKEY_SEQ() {
		return KEY_SEQ;
	}

	public void setKEY_SEQ(String key_seq) {
		KEY_SEQ = key_seq;
	}

	public String getPK_NAME() {
		return PK_NAME;
	}

	public void setPK_NAME(String pk_name) {
		PK_NAME = pk_name;
	}

}
