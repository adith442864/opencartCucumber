package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountRegistrationPage {
	
	public WebDriver driver;
	
	public AccountRegistrationPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="//h1[normalize-space()='Register Account']")
	WebElement lblRegAccount;
	
	@FindBy(name="firstname")
	WebElement txtFirstName;
	
	@FindBy(name="lastname")
	WebElement txtLastName;
	
	@FindBy(name="email")
	WebElement txtEmail;
	
	@FindBy(name="telephone")
	WebElement txtTelephone;
	
	@FindBy(name="password")
	WebElement txtPassword;

	@FindBy(name="confirm")
	WebElement txtConfirmPassword;
	
	@FindBy(name="agree")
	WebElement chkdPolicy;
	
	@FindBy(xpath="//input[@value='Continue']")
	WebElement btnContinue;
	
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;
	
	
	public void setFirstName(String fname) {
		txtFirstName.sendKeys(fname);
	}
	
	public void setLastName(String lname) {
		txtLastName.sendKeys(lname);
	}
	
	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}
	
	public void setTelephone(String telePhone) {
		txtTelephone.sendKeys(telePhone);
	}
	
	public void setPassword(String password) {
		txtPassword.sendKeys(password);
	}
	
	public void setConfirmPassword(String confirmPassword) {
		txtConfirmPassword.sendKeys(confirmPassword);
	}
	
	public void setPrivacyPolicy() {
		chkdPolicy.click();
	}
	
	
	public void clickContinue() {
		btnContinue.click();
	}
	
	public String stringGetConfirmationMsg() {
		try {
			return(msgConfirmation.getText());
		}
		catch(Exception e) {
			return (e.getMessage());
		}
	}
	
	
	public boolean isRegisterAccountPageDiplayed()
	{
		try
		{
			return (lblRegAccount.isDisplayed());
		}
		catch(Exception e)
		{
			return(false);
		}
	}
	
	
	
}
