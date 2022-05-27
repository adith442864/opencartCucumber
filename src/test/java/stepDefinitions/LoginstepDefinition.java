package stepDefinitions;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataReader;

public class LoginstepDefinition {
	
	static WebDriver driver;
	HomePage hp;
	LoginPage lp;
	public List<HashMap<String,String>> datamap;
	MyAccountPage myaccpage;
	AccountRegistrationPage regpage;
	Logger logger; //for logging
	ResourceBundle rb;
	String br; //to store the config key into this variable
	
	@Before
	public void setup() {
		logger =LogManager.getLogger(this.getClass()); //for logging
		rb = ResourceBundle.getBundle("config");
		br = rb.getString("browser");
	}
	
	@Given("User Launch browser")
	public void user_launch_browser() {
	    //WebDriverManager.chromedriver().setup();
	    //driver = new ChromeDriver();
		if(br.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
		    driver = new ChromeDriver();
		}
		else if(br.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver= new FirefoxDriver();
		}
		else if (br.equals("firefox")) {
			WebDriverManager.edgedriver().setup();
			driver= new EdgeDriver();
		}
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
	   logger.info("Clicked on login link");
	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_email_as_and_password_as(String email, String pwd) {
	    lp = new LoginPage(driver);
	    lp.setEmail(email);
	    logger.info("provided email");
	    lp.setPassword(pwd);
	    logger.info("provided password");
	}

	@When("Click on Login button")
	public void click_on_login_button() {
	  lp.clickLogin();
	   logger.info("clicked on login button");
	}

	@Then("User navigates to MyAccount Page")
	public void user_navigates_to_my_account_page() {
	   boolean targetPage = lp.isMyAccountPageExists();
	   if(targetPage) {
		   logger.info("login successful");
		   Assert.assertTrue(true);
	   }
	   else {
		   Assert.assertTrue(false);
	   		logger.info("login failed");
	   }
	   driver.quit();  
	}
	
	//Data Driven test method
	@Then("User navigates to MyAccount Page by passing Email and Password with excel row {string}")
	public void user_navigates_to_my_account_page_by_passing_email_and_password_with_excel_row(String rows) {
	   
		datamap=DataReader.data("./testData/Opencart_LoginData.xlsx", "Sheet1");

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
                    myaccpage=new MyAccountPage(driver);
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
                    myaccpage=new MyAccountPage(driver);
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
	
	 //*******   Account Registration Methods    **************

    @When("click on Register")
    public void click_on_register() {
       hp.clickRegister();
    }

    @Then("user navigates to Register Account page")
    public void user_navigates_to_register_account_page() {
       
    	regpage=new AccountRegistrationPage(driver);

        if(regpage.isRegisterAccountPageDiplayed()) {
        	   logger.info("Register Account page displayed");
            Assert.assertTrue(true);
        }
        else {
        	logger.info("Register Account page not displayed");
            Assert.assertTrue(false);
        }

    }
    @When("user provide valid details")
    public void user_provide_valid_details() {
        regpage.setFirstName("John");
        logger.info("provided firstName");
        
        regpage.setLastName("Canedy");
        logger.info("provided lastName");
        
        regpage.setEmail(RandomStringUtils.randomAlphabetic(5)+"@gmail.com");// Random Email
        regpage.setTelephone("65656565");
        logger.info("provided telephone");
        
        regpage.setPassword("abcxyz");
        logger.info("provided password");
        
        regpage.setConfirmPassword("abcxyz");
        logger.info("provided confirmPassword");
        
        regpage.setPrivacyPolicy();
    }
   
    @When("click on continue")
    public void click_on_continue() {
        regpage.clickContinue();
        logger.info("clicked on continue");
    }
    
    @Then("User should see {string} message")
    public void user_should_see_message(String expmsg) {
        
    	String confmsg=regpage.stringGetConfirmationMsg();
        driver.close();
        if(confmsg.equals(expmsg))
            Assert.assertTrue(true);
        else
            Assert.assertTrue(false);
 

}


}
