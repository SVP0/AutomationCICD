package sharan.SeleniumFrameworkDesign;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import sharan.pageobjects.LandingPage;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String productName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client/");

		LandingPage landingPage = new LandingPage(driver);
		
		driver.findElement(By.id("userEmail")).sendKeys("chanduv@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("1Chandu@");
		driver.findElement(By.id("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".col-lg-4"))));
		
		List<WebElement> products = driver.findElements(By.cssSelector(".col-lg-4"));
		
		WebElement prod = products.stream().filter(product-> 
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("toast-container"))));
		
		driver.findElement(By.cssSelector("button[routerlink='/dashboard/cart']")).click();
		
	    List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
	    Boolean match = cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equals(productName));
	    Assert.assertTrue(match);
	    
	    driver.findElement(By.cssSelector(".subtotal button")).click();
	    
	    Actions a = new Actions(driver);
	    
	    a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform()  ;  
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
	    driver.findElement(By.xpath("//button[contains(@class ,'ta-item')][2]")).click();
	    driver.findElement(By.cssSelector(".action__submit")).click();
	    
	Boolean confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText().equalsIgnoreCase("THANKYOU FOR THE ORDER.");
	  Assert.assertTrue(confirmMessage);
	  
	  driver.close();
	    
		
	}

}
