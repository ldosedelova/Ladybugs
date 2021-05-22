package cz.czechitas.selenium;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestyRegistrace {

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
    public void novyUzivatelseMusiZaregistrovat() {
        prohlizec.navigate().to(URL_APLIKACE);
        WebElement tlacitkoNaPrihlaseni = prohlizec.findElement(By.xpath("//span[text()='Sign in']"));
        tlacitkoNaPrihlaseni.click();

        registrace("Jarmila@gmail.com");


        WebElement registracniFormularJmeno = prohlizec.findElement(By.id("customer_firstname"));
        WebElement registracniFormularPrijmeni = prohlizec.findElement(By.id("customer_lastname"));
        WebElement registracniFormularEmail = prohlizec.findElement(By.id("email"));
        WebElement registracniFormularHeslo = prohlizec.findElement(By.id("passwd"));
        Assertions.assertNotNull(registracniFormularJmeno);
        Assertions.assertNotNull(registracniFormularPrijmeni);
        Assertions.assertNotNull(registracniFormularEmail);
        Assertions.assertNotNull(registracniFormularHeslo);
        WebElement oznaceniNovinek = prohlizec.findElement(By.xpath("//label[@for= 'newsletter']"));
        Assertions.assertNotNull(oznaceniNovinek);
        WebElement tlacitkoProRegistraci = prohlizec.findElement(By.id("submitAccount"));
        Assertions.assertNotNull(tlacitkoProRegistraci);

        vyplneniPrihlasky("Jarmila","Novákova", "JarmilaN");

        WebElement uzivatelPrihlasen = prohlizec.findElement(By.xpath("//span[text()='Jarmila']"));
        String potvrzeniPrihlaseni = uzivatelPrihlasen.getText();
        Assertions.assertEquals(potvrzeniPrihlaseni,"Jarmila");


        WebElement mujUcet = prohlizec.findElement(By.xpath("//h1[text() = 'My account']"));
        String textmujUcet = mujUcet.getText();
        Assertions.assertEquals(textmujUcet,"MY ACCOUNT");


    }

    private void vyplneniPrihlasky(String jmeno,String prijmeni, String heslo) {
        WebElement registracniFormularJmeno = prohlizec.findElement(By.id("customer_firstname"));
        registracniFormularJmeno.sendKeys(jmeno);
        WebElement registracniFormularPrijmeni = prohlizec.findElement(By.id("customer_lastname"));
        registracniFormularPrijmeni.sendKeys(prijmeni);
        WebElement registracniFormularHeslo = prohlizec.findElement(By.id("passwd"));
        registracniFormularHeslo.sendKeys(heslo);
        WebElement tlacitkoProRegistraci = prohlizec.findElement(By.id("submitAccount"));
        tlacitkoProRegistraci.click();
    }

    private void registrace(String email) {
        WebElement registraceEmail = prohlizec.findElement(By.id("email_create"));
        registraceEmail.sendKeys(email);
        WebElement tlacitkoPrihlaseni = prohlizec.findElement(By.id("SubmitCreate"));
        tlacitkoPrihlaseni.click();
    }

    @AfterEach
    public void tearDown() {
        prohlizec.close();
    }

}
