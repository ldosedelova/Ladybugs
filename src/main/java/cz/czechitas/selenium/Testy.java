package cz.czechitas.selenium;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Testy {

    WebDriver prohlizec;

    private static final String URL_APLIKACE = "http://czechitas-datestovani-hackathon.cz/en/";

    @BeforeEach
    public void setUp() {
//      System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") + "/Java-Training/Selenium/geckodriver");
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        prohlizec = new FirefoxDriver();
        prohlizec.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    @Test
    public void naStranceMehoUctuByMeloBytZobrazeno() {
        //BDHL-17
        prohlizec.navigate().to(URL_APLIKACE);
        WebElement prihlasit = prohlizec.findElement(By.xpath("/html/body/div[1]/div[1]/header/div[3]/div/div/div[7]/ul/li/a"));
        prihlasit.click();
        WebElement polickoEmail = prohlizec.findElement(By.id("email"));
        polickoEmail.sendKeys("qacustomer@gmail.com");
        WebElement polickoHeslo = prohlizec.findElement(By.id("passwd"));
        polickoHeslo.sendKeys("Asdf1234");
        WebElement tlacitkoPrihlasit = prohlizec.findElement(By.id("SubmitLogin"));
        tlacitkoPrihlasit.click();
        List<WebElement> links = prohlizec.findElements(By.xpath("/html/body/div/div[2]/div/div[2]/div/div/div/ul/li/a/span"));

        Assertions.assertEquals("ADD MY FIRST ADDRESS", links.get(0).getText());
        Assertions.assertEquals("ORDER HISTORY AND DETAILS", links.get(1).getText());
        Assertions.assertEquals("MY CREDIT SLIPS", links.get(2).getText());
        Assertions.assertEquals("MY ADDRESSES", links.get(3).getText());
        Assertions.assertEquals("MY PERSONAL INFORMATION", links.get(4).getText());
    }

    @AfterEach
    public void tearDown() {
        prohlizec.close();
    }

}
