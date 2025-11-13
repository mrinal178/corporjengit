package Jenkinsgit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Extractdataexceleclipse {

	public WebDriver driver;
	
	@BeforeClass
	public void openbrowser() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://mail.rediff.com/cgi-bin/login.cgi");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}
	
	@DataProvider(name ="dp")
	public Object[][] datafetch() throws IOException {
		FileInputStream fis = new FileInputStream("D:\\Git\\TestProject\\Githubtest\\TestData\\Book1.xlsx");
		XSSFWorkbook book = new XSSFWorkbook(fis);
	Sheet sheet=	book.getSheetAt(0);
int rows=	sheet.getPhysicalNumberOfRows();
int cells=	sheet.getRow(0).getPhysicalNumberOfCells();

ArrayList<Object[]> list = new ArrayList<Object[]>();

for(int i=0;i<rows;i++) {
	if(sheet.getRow(i) == null) continue;
Row row= sheet.getRow(i);

Object[] coldata = new Object[cells];

for(int j=0;j<cells;j++) {
	if(row.getCell(j)== null || row.getCell(j).getCellType().equals("Blank"))continue;
Cell cell=	row.getCell(j);
	switch (cell.getCellType()){
	case STRING:
		System.out.println(cell.getStringCellValue());
		coldata[j] = cell.getStringCellValue();
		break;
	case NUMERIC:
		System.out.println(cell.getNumericCellValue());
		coldata[j] = cell.getNumericCellValue();
		break;
		default:
			System.out.println("_");
			break;
	} 
	
}

list.add(coldata);
}

Object[][] datatoprop = new Object[list.size()][];
for(int i=0;i<list.size();i++) {
	datatoprop[i] = list.get(i);
}
		return datatoprop;
	
	}//datafetch
	
	@Test(dataProvider = "dp")
	public void Fillusername(String username, String password) {
		driver.findElement(By.xpath("//input[@type=\"text\"]")).sendKeys(username);
		driver.findElement(By.xpath("//input[@type=\"password\"]")).sendKeys(password);
	}
	
}
