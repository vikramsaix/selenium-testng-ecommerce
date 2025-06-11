package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.HomePage;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SearchProductTest extends BaseTest {

    @Test(groups = {"sanity"})
    public void searchForNonExistingProduct() {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigate to home and click "Products"
        HomePage homePage = new HomePage(driver);
        WebElement productsTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Products')]")
        ));
        productsTab.click();

        // Wait for search bar and search
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search")));
        homePage.searchProduct("someRandomInvalidProductName12345");

        // Wait for results section
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("features_items")));

        // Locate product result blocks
        List<WebElement> productBlocks = driver.findElements(By.cssSelector(".features_items .product-image-wrapper"));

        // Expect no products
        assertEquals(productBlocks.size(), 0, "Expected no products to be displayed for invalid search");
    }
}
