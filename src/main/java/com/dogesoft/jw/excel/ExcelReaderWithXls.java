package com.dogesoft.jw.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ExcelReaderWithXls {
	
	private Sheet sheet;
	private LinkedList<String>[] result;
	
	public void loadExcel(String filepath){
		FileInputStream fis=null;
		
		try {
			fis=new FileInputStream(new File(filepath));
			Workbook wb=WorkbookFactory.create(fis);
			
			sheet=wb.getSheetAt(0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
				try {
					if(fis!=null){
						fis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public String getCellValue(Cell cell){
		String cellvalue="";
		
		DataFormatter df=new DataFormatter();
		
		if(cell!=null){
			switch(cell.getCellType()){
			case Cell.CELL_TYPE_NUMERIC:
				if(DateUtil.isCellDateFormatted(cell)){
					cellvalue=df.formatCellValue(cell);
				}else{
					double value=cell.getNumericCellValue();
					int intValue=(int) value;
					cellvalue=value-intValue==0?String.valueOf(intValue):String.valueOf(value);
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cellvalue=cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellvalue=String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				try{
					
					cellvalue=String.valueOf(cell.getNumericCellValue());
				}catch(IllegalStateException e){
					cellvalue=String.valueOf(cell.getRichStringCellValue());
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				cellvalue="";
				break;
			case Cell.CELL_TYPE_ERROR:
				cellvalue="";
				break;
			default:
				cellvalue=cell.toString().trim();
				break;
			}
		}
		return cellvalue.trim();
	}
	
    public void init(){
    	int rowNum=sheet.getLastRowNum()+1;
    	result=new LinkedList[rowNum];
    	for(int i=0;i<rowNum;i++){
    		Row row=sheet.getRow(i);
    		result[i]=new LinkedList<String>();
    		for(int j=0;j<row.getLastCellNum();j++){
    			Cell cell=row.getCell(j);
    			String str=getCellValue(cell);
    			result[i].add(str);
    		}
    	}
    }
    
    public void show(){
    	for(int i=0;i<result.length;i++){
    		for(int j=0;j<result[i].size();j++){
    			System.out.println(result[i].get(j)+"\t");
    		}
    		System.out.println();
    	}
    }

	public static void main(String[] args) {
		
		ExcelReaderWithXls erwx=new ExcelReaderWithXls();
		erwx.init();
		erwx.show();

	}

}
