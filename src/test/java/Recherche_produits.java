import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Recherche_produits {
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
    public void Verifier_produits_trier() {
        String username= "standard_user";
        String password ="secret_sauce";
        By login = By.xpath("//input[@id='user-name']");
        By passwordpath = By.xpath("//input[@id='password']");
        By btn_login = By.xpath("//*[@id=\"login-button\"]");
        By liste_produits = By.xpath("//div[@id=\'inventory_container\']/div/div/div");
        driver.findElement(login).clear();
        driver.findElement(passwordpath).clear();
        driver.findElement(login).sendKeys(username);
        driver.findElement(passwordpath).sendKeys(password);
        driver.findElement(btn_login).click();
        List<WebElement> elements = driver.findElements(By.xpath("//a/div[@class='inventory_item_name ']"));
        // Créer un tableau pour stocker les textes
        String[] texttab = new String[elements.size()];

        // Récupérer le texte de chaque élément et le stocker dans le tableau
        for (int i = 0; i < elements.size(); i++) {
            texttab[i] = elements.get(i).getText();
        }String[] actualTextTab = Arrays.copyOf(texttab, texttab.length);

        // Trier le tableau
        Arrays.sort(texttab);

        // Comparer le tableau trié avec la version triée
        Assert.assertTrue(Arrays.equals(actualTextTab, texttab), "Les tableaux ne sont pas identiques après le tri.");

    }
    @AfterMethod
    public void TearDownTest() {
        driver.quit();
    }

}