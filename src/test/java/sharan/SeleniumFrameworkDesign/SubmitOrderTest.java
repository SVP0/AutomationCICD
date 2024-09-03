package sharan.SeleniumFrameworkDesign;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import sharan.TestComponents.BaseTest;
import sharan.pageobjects.CartPage;
import sharan.pageobjects.CheckoutPage;
import sharan.pageobjects.ConfirmationPage;
import sharan.pageobjects.OrderPage;
import sharan.pageobjects.ProductCatalogue;
 

public class SubmitOrderTest extends BaseTest{
	
	String productName = "ZARA COAT 3";
	

	@Test(dataProvider="getData", groups="Purchase")
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products =  productCatalogue.getProductList();		
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();
		
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.SelectCountry("India");
		
		ConfirmationPage confirmationPage = checkoutPage.SubmitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();	    
		
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	  
		
	}
	
	@Test(dependsOnMethods = {"submitOrder"})
	public void OrderHistory()
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication("chanduv@gmail.com", "1Chandu@");
		OrderPage orderPage = productCatalogue.goToOrderPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName)); 
	}
	
	
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		
		List<HashMap<String, String>> data =  getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\sharan\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}

}

