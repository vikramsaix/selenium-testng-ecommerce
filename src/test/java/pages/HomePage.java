package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLoggedIn() {
        return !driver.findElements(By.linkText("Logout")).isEmpty();
    }

    public void searchProduct(String product) {
        driver.findElement(By.name("search")).clear();  // ✅ Clear field before typing
        driver.findElement(By.name("search")).sendKeys(product);
        driver.findElement(By.id("submit_search")).click();
    }

    public void logout() {
        driver.findElement(By.linkText("Logout")).click();
    }
}
