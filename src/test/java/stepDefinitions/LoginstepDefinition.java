package stepDefinitions;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataReader;

public class LoginstepDefinition {
	
	static WebDriver driver;
	HomePage hp;
	LoginPage lp;
	public List<HashMap<String,String>> datamap;
	MyAccountPage myaccPage;
	
	@Given("User Launch browser")
	public void user_launch_browser() {
	    WebDriverManager.chromedriver().setup();
	    driver = new ChromeDriver();
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@Given("opens URL {string}")
	public void opens_url(String url) {
	  driver.get(url);
	}

	@When("User navigate to MyAccount menu")
	public void user_navigate_to_my_account_menu() {
	   hp = new HomePage(driver);
	   hp.clickMyAccount();
	}

	@When("click on Login")
	public void click_on_login() {
	   hp.clickLogin();
	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_email_as_and_password_as(String email, String pwd) {
	    lp = new LoginPage(driver);
	    lp.setEmail(email);
	    lp.setPassword(pwd);
	}

	@When("Click on Login button")
	public void click_on_login_button() {
	  lp.clickLogin();
	}

	@Then("User navigates to MyAccount Page")
	public void user_navigates_to_my_account_page() {
	   boolean targetPage = lp.isMyAccountPageExists();
	   if(targetPage)
		   Assert.assertTrue(true);
	   else
		   Assert.assertTrue(false);
	   
	   
	   driver.quit();  
	}
	
	//Data Driven test method
	@Then("User navigates to MyAccount Page by passing Email and Password with excel row {string}")
	public void user_navigates_to_my_account_page_by_passing_email_and_password_with_excel_row(String rows) {
	   
		datamap=DataReader.data(System.getProperty("user.dir")+"\\testData\\Opencart_LoginData.xlsx", "Sheet1");

		int index=Integer.parseInt(rows)-1;
	    String email= datamap.get(index).get("username");
	    String pwd= datamap.get(index).get("password");
	    String exp_res= datamap.get(index).get("res");

        lp=new LoginPage(driver);
        lp.setEmail(email);
        lp.setPassword(pwd);

        lp.clickLogin();
        try
        {
            boolean targetpage=lp.isMyAccountPageExists();

            if(exp_res.equals("Valid"))
            {
                if(targetpage==true)
                {
                    MyAccountPage myaccpage=new MyAccountPage(driver);
                    myaccpage.clickLogout();
                    Assert.assertTrue(true);
                }
                else
                {
                    Assert.assertTrue(false);
                }
            }

            if(exp_res.equals("Invalid"))
            {
                if(targetpage==true)
                {
                    MyAccountPage myaccpage=new MyAccountPage(driver);
                    myaccpage.clickLogout();
                    Assert.assertTrue(false);
                }
                else
                {
                    Assert.assertTrue(true);
                }
            }


        }
        catch(Exception e)
        {

            Assert.assertTrue(false);
        }
        driver.close();
 
		
	}


}
