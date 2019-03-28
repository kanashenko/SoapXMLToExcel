package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {

	public static void writeXml(List<Country> countries) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		int rowNum = 0;
		int cellNum = 0;
		Row titleRow = sheet.createRow(rowNum++);
		Cell titleCell = titleRow.createCell(cellNum++);
		titleCell.setCellValue("Code");
		titleCell = titleRow.createCell(cellNum++);
		titleCell.setCellValue("Name");
		titleCell = titleRow.createCell(cellNum++);
		titleCell.setCellValue("Capital");
		titleCell = titleRow.createCell(cellNum++);
		titleCell.setCellValue("CurrencyCode");
		
		for(Country country: countries) {
			cellNum = 0;
			Row row = sheet.createRow(rowNum++);
			Cell cell = row.createCell(cellNum++);
			cell.setCellValue(country.getCode());
			cell= row.createCell(cellNum++);
			cell.setCellValue(country.getName());
			cell= row.createCell(cellNum++);
			cell.setCellValue(country.getCapital());
			cell= row.createCell(cellNum++);
			cell.setCellValue(country.getCurrencyCode());
		}
		
		try {
			FileOutputStream out = new FileOutputStream(new File("countries.xlsx"));
			workbook.write(out);
			out.close();
			workbook.close();
			System.out.println("Excel written successfully..");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}