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

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ddgen.datadictionary.DDGen;

/**
 * getTables
 * http://java.sun.com/j2se/1.4.2/docs/api/java/sql/DatabaseMetaData.html
 *
 * @author Bhavani P Polimetla
 * @since Dec-25-2010
 */
public class TableVO {

	private static final Logger LOG = Logger.getLogger(TableVO.class);

	private String TABLE_NAME;
	private String TABLE_TYPE;
	private String REMARKS;
	private Date createDate;
	private Date updateDate;
	private String Engine;

	private long rowCount;

	private List<ColumnVO> columns;
	private Map<String, PrimaryKeyVO> pks;
	private Map<String, ForeignKeyVO> fks;

	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}

	public String getTABLE_TYPE() {
		return TABLE_TYPE;
	}

	public void setTABLE_TYPE(String table_type) {
		TABLE_TYPE = table_type;
	}

	public String getREMARKS() {
		return REMARKS;
	}

	public void setREMARKS(String remarks) {
		REMARKS = remarks;
	}

	public List<ColumnVO> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnVO> columns) {
		this.columns = columns;
	}

	public void print() {
		LOG.debug("TABLE_NAME:" + TABLE_NAME);
		LOG.debug("TABLE_TYPE:" + TABLE_TYPE);
		LOG.debug("REMARKS:" + REMARKS);
		LOG.debug("createDate:" + createDate);
		LOG.debug("updateDate:" + updateDate);
		LOG.debug("Engine:" + Engine);

	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getEngine() {
		return Engine;
	}

	public void setEngine(String engine) {
		Engine = engine;
	}

	public Map<String, PrimaryKeyVO> getPks() {
		return pks;
	}

	public void setPks(Map<String, PrimaryKeyVO> pks) {
		this.pks = pks;
	}

	public Map<String, ForeignKeyVO> getFks() {
		return fks;
	}

	public void setFks(Map<String, ForeignKeyVO> fks) {
		this.fks = fks;
	}

}
