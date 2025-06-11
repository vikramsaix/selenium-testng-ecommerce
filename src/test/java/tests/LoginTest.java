package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {

    @Test(groups = {"smoke", "sanity"})
    public void validLoginTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Signup / Login"))).click();
        loginPage.login("vikramsai.x@gmail.com", "123");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Logout")));
        assertTrue(homePage.isLoggedIn(), "Login failed for valid credentials");
    }

    @Test(groups = {"sanity"})
    public void invalidLoginTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        LoginPage loginPage = new LoginPage(driver);

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Signup / Login"))).click();
        loginPage.login("invalid@example.com", "wrongpass");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".login-form p")));
        assertTrue(loginPage.isLoginErrorDisplayed(), "Error message not shown for invalid login");
    }
}
