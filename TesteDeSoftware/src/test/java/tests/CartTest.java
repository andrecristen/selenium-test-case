package tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class CartTest {

    private ChromeDriver driver;

    private boolean oneSignalIsClosed = false;

    public CartTest() {
        //Define o chromedriver
        System.setProperty("webdriver.chrome.driver", "/Users/andrecristen/Downloads/chromedriver");
        //Instancia o driver
        this.driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testCart() {
        this.driver.get("https://www.panoramamoveis.com.br");
        waitCloseOneSignal();
        WebElement findBox = this.driver.findElement(By.id("buscaGlobal"));
        findBox.sendKeys("Panela");
        findBox.clear();
        findBox.sendKeys("Mesa");
        WebElement findButton = this.driver.findElement(By.className("searchForm-btn"));
        findButton.click();
        WebElement firstProduct = this.driver.findElement(By.className("product"));
        firstProduct.click();
        WebElement addProductOnCart = this.driver.findElement(By.className("addCarrinho"));
        addProductOnCart.click();
        sleep(1);
        this.driver.navigate().to("https://www.panoramamoveis.com.br/checkout");
    }

    @Test
    public void testLoginSuccess() {
        this.driver.get("https://www.panoramamoveis.com.br/login");
        waitCloseOneSignal();
        WebElement email = this.driver.findElement(By.name("email"));
        email.sendKeys(Credentials.loginSuccess);
        WebElement password = this.driver.findElement(By.name("password"));
        password.sendKeys(Credentials.passwordSuccess);
        WebElement submit = this.driver.findElement(By.className("call-to-action"));
        submit.click();
        //Wait
        sleep(3);
        String currentUrl = driver.getCurrentUrl();
        String expectedResult = "https://www.panoramamoveis.com.br/minha-conta";
        Assert.assertEquals(expectedResult, currentUrl);
    }
    @Test
    public void testComplete() {
        testLoginSuccess();
        testCart();
    }

    public void waitCloseOneSignal(){
        int loops = 0;
        boolean closedOneSignal = false;
        while (!closedOneSignal && loops < 2) {
            closedOneSignal = closeOneSignal();
            loops++;
            sleep(1);
        }
    }

    public boolean closeOneSignal(){
        if (this.oneSignalIsClosed) {
            return  true;
        }
        try {
            WebElement oneSignal = this.driver.findElement(By.id("onesignal-slidedown-cancel-button"));
            if (oneSignal.isDisplayed()) {
                oneSignal.click();
                this.oneSignalIsClosed = true;
                return true;
            }
        } catch (Exception exception) {
            return false;
        }
        return false;
    }

    public void sleep(long seconds) {
        try {
            Thread.sleep(secondsToMili(seconds));
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public long secondsToMili(long seconds){
        return seconds * 1000;
    }

}
