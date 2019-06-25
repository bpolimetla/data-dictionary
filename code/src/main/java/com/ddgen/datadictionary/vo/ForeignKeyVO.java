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
public class ForeignKeyVO {
	private String PKTABLE_NAME;
	private String PKCOLUMN_NAME;
	private String FKTABLE_NAME;
	private String FKCOLUMN_NAME;
	int KEY_SEQ;
	int UPDATE_RULE;
	int DELETE_RULE;
	private String FK_NAME;
	private String PK_NAME;

	public String getPKTABLE_NAME() {
		return PKTABLE_NAME;
	}

	public void setPKTABLE_NAME(String pktable_name) {
		PKTABLE_NAME = pktable_name;
	}

	public String getPKCOLUMN_NAME() {
		return PKCOLUMN_NAME;
	}

	public void setPKCOLUMN_NAME(String pkcolumn_name) {
		PKCOLUMN_NAME = pkcolumn_name;
	}

	public String getFKTABLE_NAME() {
		return FKTABLE_NAME;
	}

	public void setFKTABLE_NAME(String fktable_name) {
		FKTABLE_NAME = fktable_name;
	}

	public String getFKCOLUMN_NAME() {
		return FKCOLUMN_NAME;
	}

	public void setFKCOLUMN_NAME(String fkcolumn_name) {
		FKCOLUMN_NAME = fkcolumn_name;
	}

	public int getKEY_SEQ() {
		return KEY_SEQ;
	}

	public void setKEY_SEQ(int key_seq) {
		KEY_SEQ = key_seq;
	}

	public int getUPDATE_RULE() {
		return UPDATE_RULE;
	}

	public void setUPDATE_RULE(int update_rule) {
		UPDATE_RULE = update_rule;
	}

	public int getDELETE_RULE() {
		return DELETE_RULE;
	}

	public void setDELETE_RULE(int delete_rule) {
		DELETE_RULE = delete_rule;
	}

	public String getFK_NAME() {
		return FK_NAME;
	}

	public void setFK_NAME(String fk_name) {
		FK_NAME = fk_name;
	}

	public String getPK_NAME() {
		return PK_NAME;
	}

	public void setPK_NAME(String pk_name) {
		PK_NAME = pk_name;
	}

}
