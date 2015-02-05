package com.acc.tools.ed.integration.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.acc.tools.ed.integration.dto.ExcelData;

public class ExcelFileReadUtil {
	
	public static ExcelData convertExcelDataToObject(InputStream ips){
		final ExcelData excelData=new ExcelData();
	     try {
    	     
	    	    //Get the workbook instance for XLSX file 
	    	 	XSSFWorkbook workbook = new XSSFWorkbook(ips);
	    	 	
	    	 	DataFormatter objDefaultFormat = new DataFormatter();
	    	 	FormulaEvaluator objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
	    	 
	    	    //Get first sheet from the workbook
	    	    XSSFSheet sheet = workbook.getSheetAt(0);
	    	     
	    	    int rowCount=0;
	    	    final List<String[]> rowsData=new ArrayList<String[]>();
	    	    for(Row row:sheet){
	    	        if(rowCount==0){
	    	        	excelData.setHeader(getRowData(row,objDefaultFormat,objFormulaEvaluator));
	    	        	rowCount++;
	    	        } else {
	    	        	rowsData.add(getRowData(row,objDefaultFormat,objFormulaEvaluator));
	    	        }	    	    
	    	   }
	    	   excelData.setRowsData(rowsData); 
	    	     
	    	} catch (FileNotFoundException e) {
	    	    e.printStackTrace();
	    	} catch (IOException e) {
	    	    e.printStackTrace();
	    	}
	     return excelData;
	}
	
	private static String[] getRowData(Row row,DataFormatter objDefaultFormat,FormulaEvaluator objFormulaEvaluator){
		
		String[] rowData=new String[row.getPhysicalNumberOfCells()];
        //For each row, iterate through each columns
        Iterator<Cell> cellIterator = row.cellIterator();
        int columnCount=0;
        while(cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            objFormulaEvaluator.evaluate(cell); // This will evaluate the cell, And any type of cell will return string value
            rowData[columnCount]= objDefaultFormat.formatCellValue(cell,objFormulaEvaluator);
            columnCount++;
        }
        return rowData;
	}

}