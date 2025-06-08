package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.*;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    // Each test will receive its own browser instance
    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) {

        System.out.println("Launching browser: " + browser);

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless=new"); // headless Chrome 109+
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");
                firefoxOptions.addArguments("--width=1920");
                firefoxOptions.addArguments("--height=1080");
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--headless=new"); // headless Edge (Chromium-based)
                edgeOptions.addArguments("--window-size=1920,1080");
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        // Common settings
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://automationexercise.com/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
