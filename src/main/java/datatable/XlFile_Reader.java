package datatable;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

public class XlFile_Reader {	
			
		FileInputStream fis = null;
		XSSFWorkbook wb = null;
		
		public XlFile_Reader(String Path) throws IOException{
			fis = new FileInputStream(Path);
			wb = new XSSFWorkbook(fis);
			fis.close();
		}		
		
		public int getRowCount(String sheetname){
			XSSFSheet sheet = wb.getSheet(sheetname);
			int rownum = sheet.getLastRowNum();
			return rownum+1;		
		}
		
		public int getColCount(String sheetname){
			XSSFSheet sheet = wb.getSheet(sheetname);
			Row row = sheet.getRow(0);
			int colnum = row.getLastCellNum();
			return colnum+1;	
		}
		
		public String getCellData(String sheetname,int rownum,int colnum){
			XSSFSheet sheet = wb.getSheet(sheetname);
			Row row = sheet.getRow(rownum);
			String value = row.getCell(colnum).getStringCellValue();
			return value;
		}

		public String getCellData(String sheetname,String testname,int colnum){
			XSSFSheet sheet = wb.getSheet(sheetname);
			int rownum=0;
			for(int r=1;r<getRowCount(sheetname);r++){
				String rowtext = sheet.getRow(r).getCell(1).getStringCellValue();
				if(rowtext==testname){
					rownum=r;
					break;
				}
			}
			Row row = sheet.getRow(rownum);
			String value = row.getCell(colnum).getStringCellValue();
			return value;
		}
		
		public ArrayList<String> getKeyWords(String sheetname,String testname){
			XSSFSheet sheet = wb.getSheet(sheetname);
			ArrayList<String> keywords = new ArrayList<String>();
			int rownum=0;
			for(int r=1;r<getRowCount(sheetname);r++){
				String rowtext = sheet.getRow(r).getCell(0).getStringCellValue();
				if(rowtext.equals(testname)){
					rownum=r;
					break;
				}
			}
			Row row = sheet.getRow(rownum);
			Iterator<Cell> celliterator = row.cellIterator();
			while(celliterator.hasNext()){
				Cell cell = celliterator.next();
				keywords.add(cell.getStringCellValue());				
			}
			keywords.remove(0);
			return keywords;
		}
		
		public Object[][] getCellData(String sheetname){
			Object [][] objArr = new Object [getRowCount(sheetname)][getColCount(sheetname)];
			XSSFSheet sheet = wb.getSheet(sheetname);
			Iterator<Row> rowIterator = sheet.iterator();
			int rowid =0;		
			while(rowIterator.hasNext()){			
				XSSFRow row = (XSSFRow) rowIterator.next();
				Iterator <Cell> cellIterator = row.cellIterator();
				int colid =0;
				while(cellIterator.hasNext()){				
					Cell cell = cellIterator.next();
					objArr [rowid][colid] = cell.getStringCellValue();				
					colid++;
				}
				rowid++;
			}		
			return objArr;
		}

}
