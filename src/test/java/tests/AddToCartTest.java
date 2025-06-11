package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductPage;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class AddToCartTest extends BaseTest {

    @Test(groups = {"sanity"})
    public void addProductToCartAndVerify() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Step 1: Go to Products tab
        WebElement productsTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Products')]")
        ));
        productsTab.click();

        // Step 2: Click on a product
        WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href*='/product_details']")
        ));
        productLink.click();

        // Step 3: Add to cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-information h2")));
        String expectedName = productPage.getProductName();

        productPage.addToCart();
        productPage.goToCart();

        // Step 4: Verify product name in cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("td.cart_description > h4 > a")
        ));
        String actualName = cartPage.getProductNameInCart();

        assertEquals(actualName.trim(), expectedName.trim(), "Product name in cart does not match selected product");
    }
}
