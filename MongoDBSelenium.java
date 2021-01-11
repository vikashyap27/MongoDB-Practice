package MongoDBPractice;

import org.apache.log4j.LogManager;
import org.bson.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import AutomationContacts.Xls_Reader;
import io.github.bonigarcia.wdm.WebDriverManager;

import com.mongodb.client.MongoCollection;
import java.util.logging.*;
import java.util.*;

public class MongoDBSelenium {
	
	WebDriver driver;
	MongoCollection<Document> webCollection;

	//Logger log = LogManager.getLogger(MongoDBSelenium.class.getName());
	
	@BeforeSuite
	public void connectMongoDB() {
		
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		
		// MongoClient mongoClient = new MongoClient("localhost", 27017);
		
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		
		//MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://127.0.0.1:27017"));
		
		//DB db = new DB(mongoClient, "testdb");
		
		MongoDatabase database= mongoClient.getDatabase("Project");
		
		webCollection = database.getCollection("PAT");
	}
	
	@BeforeTest
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions Co= new ChromeOptions();
		
		Co.addArguments("--headless");
		driver = new ChromeDriver(Co);
		
	}
	
	@Test
	public void getdatafromExcel() {
		
		
		Xls_Reader excelUtil = new Xls_Reader("/Users/vikashkashyap/Desktop/TestLeaf/TestLeaf/src/main/java/MongoDBPractice/Sample.xlsx");
		
		String  First_Name =  excelUtil.getCellData("Sheet1", "First Name", 1);
		String  Last_Name = excelUtil.getCellData("Sheet1", "Last Name", 1);
		String  Gender = excelUtil.getCellData("Sheet1", "Gender", 1);
		String  Country = excelUtil.getCellData("Sheet1", "Country", 1);
		String  Age = excelUtil.getCellData("Sheet1", "Age", 1);
		String  Date = excelUtil.getCellData("Sheet1", "Date", 1);
		String  Id = excelUtil.getCellData("Sheet1", "Id", 1);
		
		Document d1 = new Document();
		d1.append(First_Name, First_Name);
		d1.append(Last_Name, Last_Name);
		d1.append(Gender, Gender);
		d1.append(Country, Country);
		d1.append(Age, Age);
		d1.append(Date, Date);
		d1.append(Id, Id);
		
		List<Document> doclist = new ArrayList<Document>();
		doclist.add(d1);
		
		webCollection.insertMany(doclist);
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
