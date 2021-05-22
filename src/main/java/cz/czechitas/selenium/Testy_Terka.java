package cz.czechitas.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Testy_Terka {

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

    @Test
    public void naAutentizacniStranceMohuVytvoritUcet(){
        prohlizec.navigate().to(URL_APLIKACE+"login?back=my-account");
        WebElement polickoEmailRegistrace = prohlizec.findElement(By.id("email_create"));
        Assertions.assertTrue(polickoEmailRegistrace.isDisplayed());
        Assertions.assertTrue(polickoEmailRegistrace.isEnabled());
        WebElement tlacitkoVytvorUcet = prohlizec.findElement(By.id("SubmitCreate"));
        Assertions.assertTrue(tlacitkoVytvorUcet.isDisplayed());
        Assertions.assertTrue(tlacitkoVytvorUcet.isEnabled());
    }

    @Test
    public void naAutentizacniStranceSeMohuPrihlasit(){
        prohlizec.navigate().to(URL_APLIKACE+"login?back=my-account");
        WebElement polickoEmailPrihlaseni = prohlizec.findElement(By.id("email"));
        Assertions.assertTrue(polickoEmailPrihlaseni.isDisplayed());
        Assertions.assertTrue(polickoEmailPrihlaseni.isEnabled());
        WebElement polickoHeslo = prohlizec.findElement(By.id("passwd"));
        Assertions.assertTrue(polickoHeslo.isDisplayed());
        Assertions.assertTrue(polickoHeslo.isEnabled());
        WebElement tlacitkoPrihlasit = prohlizec.findElement(By.id("SubmitLogin"));
        Assertions.assertTrue(tlacitkoPrihlasit.isDisplayed());
        Assertions.assertTrue(tlacitkoPrihlasit.isEnabled());
    }

    @Test
    public void naAutentizacniStranceMohuZresetovatHeslo(){
        prohlizec.navigate().to(URL_APLIKACE+"login?back=my-account");
        WebElement zapomenuteHesloOdkaz = prohlizec.findElement(By.linkText("Forgot your password?"));
        Assertions.assertTrue(zapomenuteHesloOdkaz.isDisplayed());
    }

    @Test
    public void naHlavniStraneSeMohuPrihlasit(){
        prohlizec.navigate().to(URL_APLIKACE);
        prihlaseniZHlavniStrany("qacustomer@gmail.com", "Asdf1234");
        Assertions.assertNotNull(prohlizec.findElement(By.xpath("//h1[contains(text(), 'My account')]")));
    }

    @Test
    public void mohuSeOdhlasit(){
        prohlizec.navigate().to(URL_APLIKACE);
        prihlaseniZHlavniStrany("qacustomer@gmail.com", "Asdf1234");

        WebElement uzivatelUcet = prohlizec.findElement(By.id("user_info_acc"));
        uzivatelUcet.click();
        WebElement odhlasitLink = prohlizec.findElement(By.linkText("Logout"));
        odhlasitLink.click();
        WebElement prihlaseni = prohlizec.findElement(By.xpath("//a[@title = 'Log in to your customer account']"));
        Assertions.assertNotNull(prihlaseni);
    }

    @Test
    public void mohuPrepnoutJazyk(){
        prohlizec.navigate().to(URL_APLIKACE);
        WebElement prepinacJazyku = prohlizec.findElement(By.id("languages-block-top"));
        prepinacJazyku.click();
        WebElement anglictina = prohlizec.findElement(By.xpath("//ul[@id = 'first-languages']/li/span"));
        Assertions.assertEquals("English",anglictina.getText());
        WebElement cestina = prohlizec.findElement(By.xpath("//ul[@id = 'first-languages']/li/a/span"));
        Assertions.assertEquals("Čeština", cestina.getText());
    }

    @Test
    public void mohuSePrepnoutDoCestiny(){
        prohlizec.navigate().to(URL_APLIKACE);
        WebElement prepinacJazyku = prohlizec.findElement(By.id("languages-block-top"));
        prepinacJazyku.click();
        WebElement cestinaLink = prohlizec.findElement(By.xpath("//ul[@id = 'first-languages']/li/a"));
        cestinaLink.click();
        String urlCestina =  prohlizec.getCurrentUrl();
        Assertions.assertEquals("http://czechitas-datestovani-hackathon.cz/cs/", urlCestina);
    }

    @Test
    public void mohuSeZeZahlaviDostatNaAutentizacniStranku(){
        prohlizec.navigate().to(URL_APLIKACE);
        WebElement linkPrihlaseni = prohlizec.findElement(By.xpath("//a[contains(@class, 'user_login')]"));
        linkPrihlaseni.click();
        String urlAutentizace = prohlizec.getCurrentUrl();
        Assertions.assertEquals("http://czechitas-datestovani-hackathon.cz/en/login?back=my-account",urlAutentizace);
    }


 //   @Test
//    public void MyPersonalInformation(){
//        prohlizec.navigate().to(URL_APLIKACE);
//        prihlaseniZHlavniStrany("qacustomer@gmail.com", "Asdf1234");
//
//        WebElement osobniInformace = prohlizec.findElement(By.xpath("//a[@title = 'information']"));
//        osobniInformace.click();
//
//        WebElement title = prohlizec.findElement(By.xpath("//label[text(), Social title] "));
//        title.isDisplayed();
//        WebElement jmenoInput = prohlizec.findElement(By.id("firstname"));
//        jmenoInput.isDisplayed();
//        jmenoInput.isEnabled();
//        WebElement prijmeniInput = prohlizec.findElement(By.id("lastname"));
//        prijmeniInput.isDisplayed();
//        prijmeniInput.isEnabled();
//    }

    @Test
    public void mohuNajitPolozkuMenuHome(){
        prohlizec.navigate().to(URL_APLIKACE);
        WebElement menuTlacitko = prohlizec.findElement(By.xpath("/html/body/div[1]/div[1]/header/div[3]/div/div/div[2]/button"));
        menuTlacitko.click();
        WebElement homeLink = prohlizec.findElement(By.linkText("Home"));
        Assertions.assertNotNull(homeLink);
        homeLink.click();
        String urlHome = prohlizec.getCurrentUrl();
        Assertions.assertEquals(URL_APLIKACE, urlHome);
    }

    @Test
    public void mohuNajitPolozkuMenuInterior(){
        najdiPolozkuMenu("Interior","#hotelInteriorBlock" );
    }

    @Test
    public void mohuNajitPolozkuMenuAmenities(){
        najdiPolozkuMenu("Amenities","#hotelAmenitiesBlock" );
    }

    @Test
    public void mohuNajitPolozkuMenuRooms(){
        najdiPolozkuMenu("Rooms","#hotelRoomsBlock" );
    }

    @Test
    public void mohuNajitPolozkuMenuTestimonials(){
        najdiPolozkuMenu("Testimonials","#hotelTestimonialBlock" );
    }

    @Test
    public void mohuNajitPolozkuMenuLegalNotice(){
        najdiPolozkuMenu("Legal Notice","#legalNotice" );
    }

    @Test
    public void mohuNajitPolozkuMenuAboutUs(){
        najdiPolozkuMenu("About us","#aboutUs" );
    }

    @Test
    public void mohuNajitPolozkuMenuContatct(){
        najdiPolozkuMenu("Contact","#contact" );
    }

    @Test
    public void mohuZavritMenu(){
        prohlizec.navigate().to(URL_APLIKACE);
        WebElement menuTlacitko = prohlizec.findElement(By.xpath("/html/body/div[1]/div[1]/header/div[3]/div/div/div[2]/button"));
        menuTlacitko.click();
        WebElement zaviraciTlacitko = prohlizec.findElement(By.xpath("/html/body/div[1]/div[1]/header/div[3]/div/div/div[3]/div/div/div[1]/span"));
        zaviraciTlacitko.click();
        WebElement menu = prohlizec.findElement(By.id("menu_cont"));
        String aktualniUmisteniClass = menu.getAttribute("class");
        Assertions.assertEquals("menu_cont_right", aktualniUmisteniClass);
    }

    @AfterEach
    public void tearDown() {
        prohlizec.close();
    }

    public void najdiPolozkuMenu(String polozka, String mainPageAnchor){
        prohlizec.navigate().to(URL_APLIKACE);
        WebElement menuTlacitko = prohlizec.findElement(By.xpath("/html/body/div[1]/div[1]/header/div[3]/div/div/div[2]/button"));
        menuTlacitko.click();
        WebElement interiorLink = prohlizec.findElement(By.linkText(polozka));
        Assertions.assertNotNull(interiorLink, "Nelze nalezt link");
        String anchor = interiorLink.getAttribute("href");
        Assertions.assertEquals("http://czechitas-datestovani-hackathon.cz/" + mainPageAnchor, anchor);
        interiorLink.click();
        String urlHome = prohlizec.getCurrentUrl();
        Assertions.assertEquals(URL_APLIKACE, urlHome, "Polozka neni na hlavni strane");
    }

    public void prihlaseniZHlavniStrany(String email, String heslo) {
        WebElement linkPrihlaseni = prohlizec.findElement(By.xpath("//a[contains(@class, 'user_login')]"));
        linkPrihlaseni.click();
        WebElement polickoEmailPrihlaseni = prohlizec.findElement(By.id("email"));
        polickoEmailPrihlaseni.sendKeys(email);
        WebElement polickoHeslo = prohlizec.findElement(By.id("passwd"));
        polickoHeslo.sendKeys(heslo);
        WebElement tlacitkoPrihlasit = prohlizec.findElement(By.id("SubmitLogin"));
        tlacitkoPrihlasit.click();
    }
}
