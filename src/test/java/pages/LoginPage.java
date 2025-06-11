package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Login action
    public void login(String username, String password) {
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys(username);

        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(password);

        driver.findElement(By.xpath("//button[text()='Login']")).click();
    }

    // Checks for login error message like "Your email or password is incorrect!"
    public boolean isLoginErrorDisplayed() {
        return !driver.findElements(By.xpath("//p[contains(text(),'incorrect')]")).isEmpty();
    }

    // Checks if user is redirected to login page after logout
    public boolean isLoggedOut() {
        return driver.getCurrentUrl().contains("/login") ||
                !driver.findElements(By.cssSelector("form[action='/login']")).isEmpty();
    }
}
