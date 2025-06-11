package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class LogoutTest extends BaseTest {

    @Test(groups = {"sanity"})
    public void verifyLogoutFunctionality() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        // Step 1: Go to Login page
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Signup / Login"))).click();

        // Step 2: Log in
        loginPage.login("vikramsai.x@gmail.com", "123");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Logout")));
        assertTrue(homePage.isLoggedIn(), "Login failed");

        // Step 3: Log out
        homePage.logout();

        // Step 4: Confirm redirection to login page
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("form[action='/login']")
        ));
        assertTrue(loginPage.isLoggedOut(), "Logout failed or redirection incorrect");
    }
}
