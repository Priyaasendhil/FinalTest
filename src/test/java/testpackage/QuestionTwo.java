package testpackage;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

public class QuestionTwo
{
    public static ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    public static ExtentTest test;
    WebDriver driver;
    int sheetindex=0;
    Object[][] data;

    @DataProvider(name="logindata")
    public Object[][] logindataprovider() throws IOException , FileNotFoundException
    {
        data=Xlread.getExceldata(sheetindex);
        return data;
    }

    public void initializer() {
        String FileSeparator = System.getProperty("file.separator");
        sparkReporter =  new ExtentSparkReporter(System.getProperty("user.dir")+FileSeparator+"Reports"+FileSeparator+"extentSparkReport.html");
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Test Execution Report");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    public static String captureScreenshot(WebDriver driver) throws IOException {
        String FileSeparator = System.getProperty("file.separator"); // "/" or "\"
        String Extent_report_path = System.getProperty("user.dir")+FileSeparator+"Reports";

        File Src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String Screenshotname = "screenshot"+Math.random()+".png";
        File Dst = new File(Extent_report_path+FileSeparator+"Screenshots"+FileSeparator+Screenshotname);
        FileUtils.copyFile(Src, Dst);
        String absPath = Dst.getAbsolutePath();
        //System.out.println("Absolute path is:"+absPath);
        return absPath;
    }
    public void txtclear(WebElement element)
    {
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
    }

  @Test(dataProvider = "logindata")
  public void newTest(String fname,String lname,String pno,String email,String address,String city,String state,String pcode,String country,String uname,String pword,String cpword) throws IOException {
      String methodName = new Exception().getStackTrace()[0].getMethodName();
      String className = new Exception().getStackTrace()[0].getClassName();
      test = extent.createTest(methodName,"Validate Register Page");
      test.log(Status.INFO, "Starting enter details");

     WebElement firstname = driver.findElement(By.name("firstName"));
     txtclear(firstname);
     firstname.sendKeys(fname);

     WebElement lastname= driver.findElement(By.name("lastName"));
      txtclear(lastname);
     lastname.sendKeys(lname);

     WebElement phone=driver.findElement(By.name("phone"));
      txtclear(phone);
     phone.sendKeys(pno);

     WebElement email1= driver.findElement(By.id("userName"));
      txtclear(email1);
     email1.sendKeys(email);

     WebElement address1=driver.findElement(By.name("address1"));
      txtclear(address1);
      address1.sendKeys(address);

     WebElement city1= driver.findElement(By.name("city"));
      txtclear(city1);
     city1.sendKeys(city);

      WebElement state1=driver.findElement(By.name("state"));
      txtclear(state1);
      state1.sendKeys(state);

     WebElement postcode= driver.findElement(By.name("postalCode"));
      txtclear(postcode);
     postcode.sendKeys(pcode);

      WebElement dropdown=driver.findElement(By.name("country"));
      Select select=new Select(dropdown);
      select.selectByVisibleText(country);

      WebElement email2=driver.findElement(By.id("email"));
      txtclear(email2);
      email2.sendKeys(uname);

      WebElement passwrd=driver.findElement(By.name("password"));
      txtclear(passwrd);
      passwrd.sendKeys(pword);

      WebElement cpwd=driver.findElement(By.name("confirmPassword"));
      txtclear(cpwd);
      cpwd.sendKeys(cpword);
      test.log(Status.INFO, "Entered all details");
      test.addScreenCaptureFromPath(captureScreenshot(driver));
    //  driver.findElement(By.name("submit")).click();
  }
  @BeforeTest
    public void beforeTest()
  {
      initializer();
      driver = new ChromeDriver();
      driver.get("https://demo.guru99.com/test/newtours/register.php");
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));



  }
  @AfterTest
    public void afterTest()
  {
  extent.flush();
  driver.close();
  }
}
