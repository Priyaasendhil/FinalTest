package testpackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Xlread 
{
	 public static Object[][] getExceldata(int sheetind) throws IOException, FileNotFoundException
	  {
		 
		  FileInputStream excel=new FileInputStream("C:\\Users\\priya\\IdeaProjects\\FinalTest\\src\\test\\testData\\FinalExamExcel.xlsx");
		
		  @SuppressWarnings("resource")
		XSSFWorkbook workb=new XSSFWorkbook(excel);
			
		  //load the sheet from above excel workbook
			XSSFSheet sheet=workb.getSheetAt(sheetind);
			
			// how many total rows in my excel sheet
					int rowCount=sheet.getLastRowNum();
					
					int cellCount=sheet.getRow(0).getLastCellNum();
					

					Object[][] testdata= new Object[rowCount][cellCount];
					
					for(int i=1; i<rowCount+1; i++) 
					{
							Row r=sheet.getRow(i);
							
						for(int j=0; j<cellCount; j++) 
						{
							testdata[i-1][j]=r.getCell(j).toString();
							System.out.println(testdata[i-1][j]=r.getCell(j).toString());
						}
					}
			
		  
		  return testdata;
	  }
}
