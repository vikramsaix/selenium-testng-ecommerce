package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ECommercePurchaseTest extends BaseTest {

    @Test
    public void testProductPurchaseFlow() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Initialize Page Objects
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        // 1. Click Signup/Login
        System.out.println("Step 1: Clicking Signup/Login...");
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Signup / Login"))).click();

        // 2. Login
        System.out.println("Step 2: Logging in...");
        loginPage.login("vikramsai.x@gmail.com", "123");

        // 3. Assert logged in
        System.out.println("Step 3: Verifying login...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Logout")));
        assertTrue(homePage.isLoggedIn(), "Login failed");

        // 4. Go to Products
        System.out.println("Step 4: Navigating to Products...");
        WebElement productsTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Products')]")
        ));
        productsTab.click();

        // 5. Search product
        System.out.println("Step 5: Searching for product...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search")));
        homePage.searchProduct("T-shirt");

        // 6. Click on a product
        System.out.println("Step 6: Clicking on a product...");
        WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href*='/product_details']")
        ));
        productLink.click();

        // 7. Add to cart and go to cart
        System.out.println("Step 7: Adding product to cart...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-information h2")));
        String expectedName = productPage.getProductName();

        productPage.addToCart();   // Your ProductPage must be updated to use CSS "button.cart"
        productPage.goToCart();

        // 8. Assert product in cart
        System.out.println("Step 8: Verifying product in cart...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("td.cart_description > h4 > a")
        ));
        String actualName = cartPage.getProductNameInCart();
        assertEquals(actualName.trim(), expectedName.trim(), "Product in cart does not match");

        // 9. Logout
        System.out.println("Step 9: Logging out...");
        homePage.logout();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("form[action='/login']")
        ));
        assertTrue(loginPage.isLoggedOut(), "Logout failed");

        System.out.println("Test completed successfully.");
    }
}
