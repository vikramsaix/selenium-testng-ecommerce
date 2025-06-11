package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductPage {

    WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnProductLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement firstProductLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("a[href*='/product_details']")
        ));

        hideIframes();
        safeClick(firstProductLink);
        System.out.println("Clicked on first product link.");
    }

    public void addToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.product-information > h2")
        ));
        System.out.println("Product page is loaded.");

        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn.cart")
        ));
        addToCartBtn.click();
        System.out.println("Clicked Add to Cart button.");
    }

    public void goToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement cartModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartModal")));
        System.out.println("Cart modal is visible.");

        hideIframes();

        WebElement viewCartLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#cartModal a[href='/view_cart']")
        ));
        viewCartLink.click();
        System.out.println("Clicked View Cart link, navigating to Cart page.");
    }

    public String getProductName() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='product-information']/h2")
        ));
        String productName = productNameElement.getText();
        System.out.println("Selected Product Name: " + productName);
        return productName;
    }

    public void safeClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        js.executeScript("arguments[0].click();", element);
    }

    public void hideIframes() {
        try {
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            if (!iframes.isEmpty()) {
                System.out.println("Ad iframe(s) found → hiding them...");
                for (WebElement iframe : iframes) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", iframe);
                }
            }
        } catch (Exception e) {
            System.out.println("No iframe found or error while hiding iframe.");
        }
    }
}
