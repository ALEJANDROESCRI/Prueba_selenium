package examenSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

public class prueba_mailchimp {

    private WebDriver driver;
    private String MAILCHIMP = "https://login.mailchimp.com/";
    private String MAILCHIMP_TITLE = "Login | Mailchimp";


    @BeforeTest
    public void setup() throws InterruptedException{
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver = new ChromeDriver();
        driver.get(MAILCHIMP);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("onetrust-group-container")));
        driver.findElement(By.id("onetrust-accept-btn-handler")).click();
        Thread.sleep(2000);

    }

    @Test(groups = {"successTest"}, priority = 4)
    public void validarTituloTest(){

        Assert.assertEquals(driver.getTitle(), MAILCHIMP_TITLE);
    }

    @Test(groups = {"successTest"}, priority = 3)
    public void iniciarSesionPageTest(){

        WebElement iniciarSesion = driver.findElement(By.xpath("//h1[contains(text(), 'Log In')]"));
        Assert.assertTrue(iniciarSesion.isDisplayed());

        WebElement needAccount = driver.findElement(By.xpath("//span[contains(text(), 'Need a Mailchimp account?')]"));
        Assert.assertTrue(needAccount.isDisplayed());

    }

    @Test(groups = {"failTests"}, priority = 2)
    public void loginErrorTest(){
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("xxx@gmail.com");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        WebElement errorLogin = driver.findElement(By.xpath("//p[contains(text(), 'Looks like you forgot your password there')]"));
        Assert.assertTrue(errorLogin.isDisplayed());

        WebElement keepLogin = driver.findElement(By.name("stay-signed-in"));
        Assert.assertFalse(keepLogin.isSelected());


    }

    @Test(groups = {"successTest"}, priority = 1)
    public void fakeEmailTest(){
        //No esta la validación
        driver.navigate().to("https://login.mailchimp.com/signup");
    }

    @Test(dataProvider = "emails", dataProviderClass = dataProvider.class, groups = {"failTests"}, priority = 0)
    public void dataProviderEmailTest(String email, String contraseña){
        driver.get(MAILCHIMP);
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(contraseña);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        //Me salta el captcha y cada vez me sale un error diferente... He puesto el que mas veces me ha aparecido.
        WebElement errorLogin = driver.findElement(By.xpath("//*[contains(text(), 'Sorry, that password isn't right')]"));
        Assert.assertTrue(errorLogin.isDisplayed());
    }

    @AfterTest
    public void closeTest(){
        driver.close();
    }
}
