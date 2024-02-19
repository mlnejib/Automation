import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class LoginPage {
    WebDriver driver;
    String url = "https://www.saucedemo.com/";

    //String username ="standard_user";
    //String password ="secret_sauce";
    @Parameters("Browser")
    @BeforeMethod
    public void setUpTest(String Browser) {
        if (Browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (Browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (Browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Test
    public void LoginTest_nominal() throws InterruptedException {
        By login = By.xpath("//input[@id='user-name']");
        By passwordpath = By.xpath("//input[@id='password']");
        By btn_login = By.xpath("//*[@id=\"login-button\"]");
        By products_page = By.xpath("//span[@class='title']");
        List<String> usernames = Arrays.asList("standard_user", "problem_user", "performance_glitch_user", "error_user", "visual_user");
        String password = "secret_sauce";
        By menu = By.xpath("//button[@id=\'react-burger-menu-btn\']");
        By logout = By.xpath("//a[@id='logout_sidebar_link']");

        for (String username : usernames) {
                driver.findElement(login).clear();
                driver.findElement(passwordpath).clear();
                driver.findElement(login).sendKeys(username);
                driver.findElement(passwordpath).sendKeys(password);
                driver.findElement(btn_login).click();
                Assert.assertTrue(driver.findElement(products_page).isDisplayed());
                WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
                wait.until(ExpectedConditions.elementToBeClickable(menu));
                driver.findElement(menu).click();
                wait.until(ExpectedConditions.elementToBeClickable(logout));
                driver.findElement(logout).click();
        }
    }
    @Test
    public void LoginTest_invalides(){
        By login = By.xpath("//input[@id='user-name']");
        By passwordpath = By.xpath("//input[@id='password']");
        By btn_login = By.xpath("//*[@id=\"login-button\"]");
        By Msg_erreur = By.xpath("//h3[@data-test=\'error\']");
        List<String> usernames = Arrays.asList("", "problem_user", "performance_glitch_user", "error_user", "visual_user");
        List<String> passwords = Arrays.asList("secret_sauce","","secret","sauce","secret_","_sauce");
        By menu = By.xpath("//button[@id=\'react-burger-menu-btn\']");
        By logout = By.xpath("//a[@id='logout_sidebar_link']");

        for (String username : usernames) {
            driver.findElement(login).clear();
            driver.findElement(passwordpath).clear();
            driver.findElement(login).sendKeys(username);
            for (String password : passwords) {
                driver.findElement(passwordpath).sendKeys(password);
                driver.findElement(btn_login).click();
                driver.findElement(passwordpath).clear();
            };
            Assert.assertTrue(driver.findElement(Msg_erreur).isDisplayed());

        }
    }
    @AfterMethod
    public void TearDownTest() {
        driver.quit();
    }

}
