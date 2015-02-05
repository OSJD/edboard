package com.acc.tools.ed.integration.dto;

import java.util.List;

public class ExcelData {
	
	private String[] header;
	private List<String[]> rowsData;
	public String[] getHeader() {
		return header;
	}
	public void setHeader(String[] header) {
		this.header = header;
	}
	public List<String[]> getRowsData() {
		return rowsData;
	}
	public void setRowsData(List<String[]> rowsData) {
		this.rowsData = rowsData;
	}
	
	public int getHeaderIndex(String label){
		int index=0;
		for(String labelName:header){
			if(labelName.equalsIgnoreCase(label)){
				return index;
			}
			index++;
		}
		return 0;
	}


}
