package com.dogesoft.jw.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderWithXlsx {
	
	private XSSFWorkbook xwb;
	private XSSFSheet xsheet;
	public void load(String filepath){
		
			FileInputStream fis;
			try {
				fis = new FileInputStream(new File(filepath));
				xwb=new XSSFWorkbook(fis);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
	}

	public List<Object> getSheetData(String sheetname){
		
		xsheet=xwb.getSheet(sheetname);
		
		List<Object> excelDataList=new ArrayList<Object>();
		int rowstart=xsheet.getFirstRowNum();
		int rowlast=xsheet.getLastRowNum();
		for(int i=rowstart+1;i<=rowlast;i++){
			XSSFRow row=xsheet.getRow(i);
			if(row==null)continue;
			int cellstart=row.getFirstCellNum();
//			int celllast=row.getLastCellNum();
			int firstcellcount=xsheet.getRow(0).getLastCellNum();
			Map<String,String> empMap=new HashMap<String,String>();
			
			for(int j=cellstart;j<=firstcellcount;j++){
				XSSFCell cell=row.getCell(j);
				String title=null;
				if(cell==null && j<firstcellcount){
					title=xsheet.getRow(0).getCell(j).getStringCellValue();
					empMap.put(title, " ");
//					System.out.print(" "+ "\t");
					continue;
				}
				if(cell ==null && j==firstcellcount){
					continue;
				}
				switch(cell.getCellType()){
				case HSSFCell.CELL_TYPE_NUMERIC: // 数字  
					DecimalFormat format = new DecimalFormat("#");  
					Number value = cell.getNumericCellValue();
					String phone = format.format(value);
					title=xsheet.getRow(0).getCell(j).getStringCellValue();
					empMap.put(title, phone);
//                    System.out.print(phone+ "\t");  
                    break;  
                case HSSFCell.CELL_TYPE_STRING: // 字符串  
                	title=xsheet.getRow(0).getCell(j).getStringCellValue();
                	empMap.put(title, cell.getStringCellValue());
//                    System.out.print(cell.getStringCellValue()+ "\t");  
                    break;  
                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean 
                	title=xsheet.getRow(0).getCell(j).getStringCellValue();
                	empMap.put(title, String.valueOf(cell.getBooleanCellValue()));
//                    System.out.println(cell.getBooleanCellValue()+ "\t");  
                    break;  
                case HSSFCell.CELL_TYPE_FORMULA: // 公式  
                	title=xsheet.getRow(0).getCell(j).getStringCellValue();
                	empMap.put(title, String.valueOf(cell.getCellFormula()));
//                    System.out.print(cell.getCellFormula() + "\t");  
                    break;  
                case HSSFCell.CELL_TYPE_BLANK: // 空值  
                	title=xsheet.getRow(0).getCell(j).getStringCellValue();
                	empMap.put(title, " ");
//                    System.out.println(" ");  
                    break;  
                case HSSFCell.CELL_TYPE_ERROR: // 故障  
                	title=xsheet.getRow(0).getCell(j).getStringCellValue();
                	empMap.put(title, " ");
//                    System.out.println(" ");  
                    break;  
                default:  
                	title=xsheet.getRow(0).getCell(j).getStringCellValue();
                	empMap.put(title, " ");
//                    System.out.print("未知类型   ");  
                    break; 
				}
			}
			JSONObject empJson=JSONObject.fromObject(empMap);
//			System.out.println("\n");
			System.out.println(empJson);
//			System.out.println("\n");
			excelDataList.add(empJson);
		}
		
		return excelDataList;
		
	}
	
	public List<Object> getSheetDataByDLM(String sheetname){
		
		xsheet=xwb.getSheet(sheetname);
		
		List<Object> excelDataList=new ArrayList<Object>();
		int rowstart=xsheet.getFirstRowNum();
		int rowlast=xsheet.getLastRowNum();
		for(int i=rowstart+1;i<=rowlast;i++){
			XSSFRow row=xsheet.getRow(i);
			if(row==null)continue;
			int cellstart=row.getFirstCellNum();
//			int celllast=row.getLastCellNum();
			int firstcellcount=xsheet.getRow(0).getLastCellNum();
			Map<String,String> empMap=new HashMap<String,String>();
			
			for(int j=cellstart;j<=firstcellcount;j++){
				XSSFCell cell=row.getCell(j);
				String title=null;
				if(cell==null && j<firstcellcount){
					title=xsheet.getRow(0).getCell(j).getStringCellValue();
					empMap.put(title, " ");
//					System.out.print(" "+ "\t");
					continue;
				}
				if(cell ==null && j==firstcellcount){
					continue;
				}
				switch(cell.getCellType()){
				case HSSFCell.CELL_TYPE_NUMERIC: // 数字  
					DecimalFormat format = new DecimalFormat("#");  
					Number value = cell.getNumericCellValue();
					String phone = format.format(value);
					title=xsheet.getRow(0).getCell(j).getStringCellValue();
					empMap.put(title, phone);
//                    System.out.print(phone+ "\t");  
                    break;  
                case HSSFCell.CELL_TYPE_STRING: // 字符串  
                	title=xsheet.getRow(0).getCell(j).getStringCellValue();
                	empMap.put(title, cell.getStringCellValue());
//                    System.out.print(cell.getStringCellValue()+ "\t");  
                    break;  
                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean 
                	title=xsheet.getRow(0).getCell(j).getStringCellValue();
                	empMap.put(title, String.valueOf(cell.getBooleanCellValue()));
//                    System.out.println(cell.getBooleanCellValue()+ "\t");  
                    break;  
                case HSSFCell.CELL_TYPE_FORMULA: // 公式  
                	title=xsheet.getRow(0).getCell(j).getStringCellValue();
                	empMap.put(title, String.valueOf(cell.getCellFormula()));
//                    System.out.print(cell.getCellFormula() + "\t");  
                    break;  
                case HSSFCell.CELL_TYPE_BLANK: // 空值  
                	title=xsheet.getRow(0).getCell(j).getStringCellValue();
                	empMap.put(title, " ");
//                    System.out.println(" ");  
                    break;  
                case HSSFCell.CELL_TYPE_ERROR: // 故障  
                	title=xsheet.getRow(0).getCell(j).getStringCellValue();
                	empMap.put(title, " ");
//                    System.out.println(" ");  
                    break;  
                default:  
                	title=xsheet.getRow(0).getCell(j).getStringCellValue();
                	empMap.put(title, " ");
//                    System.out.print("未知类型   ");  
                    break; 
				}
			}
			JSONObject empJson=JSONObject.fromObject(empMap);
//			System.out.println("\n");
			System.out.println(empJson);
//			System.out.println("\n");
			excelDataList.add(empJson);
		}
		
		return excelDataList;
		
	}
	public static void main(String[] args) {

		String filepath="src/resources/DLMuser.xlsx";
		ExcelReaderWithXlsx erwx=new ExcelReaderWithXlsx();
		erwx.load(filepath);
		
		List<Object> excelDataList=erwx.getSheetDataByDLM("DLMUSER");
		
		System.out.println(excelDataList.size());
		
	}

}
