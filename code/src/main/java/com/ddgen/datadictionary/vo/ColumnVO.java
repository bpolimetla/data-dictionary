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
 * @author Bhavani P Polimetla
 * @since Dec-25-2010
 *
 */
public class ColumnVO {

	String COLUMN_DEF;
	String COLUMN_NAME;
	int COLUMN_SIZE;
	int DECIMAL_DIGITS;
	String IS_NULLABLE;
	String REMARKS;
	String TABLE_NAME;
	String TYPE_NAME;

	String IS_PK;
	String IS_FK;

	String PK_INFO;
	String FK_INFO;

	public String getCOLUMN_DEF() {
		return COLUMN_DEF;
	}

	public void setCOLUMN_DEF(String column_def) {
		COLUMN_DEF = column_def;
	}

	public String getCOLUMN_NAME() {
		return COLUMN_NAME;
	}

	public void setCOLUMN_NAME(String column_name) {
		COLUMN_NAME = column_name;
	}

	public int getCOLUMN_SIZE() {
		return COLUMN_SIZE;
	}

	public void setCOLUMN_SIZE(int column_size) {
		COLUMN_SIZE = column_size;
	}

	public int getDECIMAL_DIGITS() {
		return DECIMAL_DIGITS;
	}

	public void setDECIMAL_DIGITS(int decimal_digits) {
		DECIMAL_DIGITS = decimal_digits;
	}

	public String getIS_NULLABLE() {
		return IS_NULLABLE;
	}

	public void setIS_NULLABLE(String is_nullable) {
		IS_NULLABLE = is_nullable;
	}

	public String getREMARKS() {
		return REMARKS;
	}

	public void setREMARKS(String remarks) {
		REMARKS = remarks;
	}

	public String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}

	public String getTYPE_NAME() {
		return TYPE_NAME;
	}

	public void setTYPE_NAME(String type_name) {
		TYPE_NAME = type_name;
	}

	public String getIS_PK() {
		return IS_PK;
	}

	public void setIS_PK(String is_pk) {
		IS_PK = is_pk;
	}

	public String getIS_FK() {
		return IS_FK;
	}

	public void setIS_FK(String is_fk) {
		IS_FK = is_fk;
	}

	public String getPK_INFO() {
		return PK_INFO;
	}

	public void setPK_INFO(String pk_info) {
		PK_INFO = pk_info;
	}

	public String getFK_INFO() {
		return FK_INFO;
	}

	public void setFK_INFO(String fk_info) {
		FK_INFO = fk_info;
	}

}
