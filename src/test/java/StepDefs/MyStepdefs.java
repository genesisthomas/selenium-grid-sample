package StepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyStepdefs {
    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--no-sandbox"); // Bypass OS security model
//        driver = new ChromeDriver(options);
        driver = new RemoteWebDriver(new URL("http://10.60.27.26:4444/wd/hub"), options);
        driver.manage().window().maximize();
    }

    @When("Open kaholo on your browser")
    public void hitKaholoOnYourBrowser() {
        driver.get("https://kaholo.io/join-kaholo-community/");
    }

    @Then("Enter {string} in first name")
    public void enterInTheFirstNameTextBox(String arg0) {
        By firstName = By.id("input_6_7_3");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).click();
        driver.findElement(firstName).sendKeys(arg0);
    }

    @Then("Enter {string} in last name")
    public void enterInTheLastNameTextBox(String arg0) {
        By lastName = By.id("input_6_8_6");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastName)).click();
        driver.findElement(lastName).sendKeys(arg0);
    }

    @Then("Enter {string} in email")
    public void enterInTheEmailTextBox(String arg0) {
        By email = By.id("input_6_4");
        WebElement element = driver.findElement(email);
        element.sendKeys(arg0);
    }

    @And("Verify alert")
    public void verifyAlert() {
        By joinBtn = By.xpath("//input[@value='Join now']");
        WebElement element = driver.findElement(joinBtn);
        element.click();
        By alert = By.xpath("//div[contains(text(),\'The email address entered is invalid, please check\')]");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(alert));
        List<WebElement> elements = driver.findElements(alert);
        assert (elements.size() > 0);
    }

    @And("Take screenshot")
    public void takeScreenshot() {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File("headless_screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("click something")
    public void clickSomething() {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.name("q2")).click();
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
