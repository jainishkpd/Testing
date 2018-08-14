package Testing.Testing;

import org.testng.annotations.Test;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.ant.types.resources.Last;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Testing {

	@Test
	public void createExcel() {

		System.out.println("Started TestCase One");

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
		Object[][] datatypes = {
				{"TestCaseID", "Module", "TestCase Name", "Expected Result", "Actual Result"},
				{"001", "Login", "Pass Invalid username and password", "Login should not allow", "NA"},
				{"002", "Login", "Pass Invalid username and Valid password", "Login should not allow", "NA"},
				{"003", "Login", "Pass Valid username and Invalid password", "Login should not allow", "NA"},
				{"004", "Login", "Pass Valid username and password", "Login should allow and redirect to dashboard page.", "NA"}
		};

		int rowNum = 0;
		System.out.println("Creating excel");

		for (Object[] datatype : datatypes) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (Object field : datatype) {
				Cell cell = row.createCell(colNum++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				}
			}
		}

		try {
			FileOutputStream outputStream = new FileOutputStream("./testData/abc.xlsx");
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Test Case Created Successfully");
	}

	@Test
	public void readExcel() throws Exception, IOException {

		System.out.println("Started TestCase Two");
		// Creating a Workbook from an Excel file (.xls or .xlsx)
		Workbook workbook = WorkbookFactory.create(new File("./testData/abc.xlsx"));

		// Retrieving the number of sheets in the Workbook
		System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheet : ");

		System.out.println("Retrieving Sheets using for-each loop");
		for(Sheet sheet: workbook) {
			System.out.println("Get the Sheet Name => " + sheet.getSheetName());
		}

		// Getting the Sheet at index zero
		Sheet sheet = workbook.getSheetAt(0);

		// Create a DataFormatter to format and get each cell's value as String
		DataFormatter dataFormatter = new DataFormatter();

		for (Row row: sheet) {
			for(Cell cell: row) {
				String cellValue = dataFormatter.formatCellValue(cell);
				System.out.print(cellValue + "\t");
			}
			System.out.println();
		}
		// Closing the workbook
		workbook.close();
	}

	@Test
	public void updateExcel() throws Exception, IOException {

		System.out.println("Started TestCase Three");

		FileInputStream input = new FileInputStream(new File("./testData/abc.xlsx")); 

		XSSFWorkbook wb = new XSSFWorkbook(input); 

		XSSFSheet worksheet = wb.getSheetAt(0); 

		Cell cell = null;

		cell = worksheet.getRow(2).getCell(2); 

		cell.setCellValue("Update the Cell Value");  

		input.close();

		FileOutputStream output_file =new FileOutputStream(new File("./testData/abc.xlsx"));  

		wb.write(output_file);

		output_file.close(); 
	}

	@Test
	public void appendDataToExcel() throws Exception {

		System.out.println("Started TestCase Four");

		FileInputStream input = new FileInputStream(new File("./testData/abc.xlsx")); 

		XSSFWorkbook workbook = new XSSFWorkbook(input); 

		XSSFSheet worksheet = workbook.getSheetAt(0);

		Object[][] datatypes = {
				{"005", "Login", "Pass Invalid username and password", "Login should not allow", "NA"},
				{"006", "Login", "Pass Invalid username and Valid password", "Login should not allow", "NA"},
				{"007", "Login", "Pass Valid username and Invalid password", "Login should not allow", "NA"},
				{"008", "Login", "Pass Valid username and password", "Login should allow and redirect to dashboard page.", "NA"}
		};

		int lastRowNum = worksheet.getLastRowNum()+1;

		System.out.println("Append Data to excel");

		for (Object[] datatype : datatypes) {
			Row row = worksheet.createRow(lastRowNum++);
			int colNum = 0;
			for (Object field : datatype) {
				Cell cell = row.createCell(colNum++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				}
			}
		}

		try {
			FileOutputStream outputStream = new FileOutputStream("./testData/abc.xlsx");
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Test Case Append Successfully");
		readExcel();
	}

}
