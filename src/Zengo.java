import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Zengo {
	private WebDriver webDriver;
	private Actions webAction;
	private final String baseUrl = "https://zengo.com/";
	private final String ethereumRoute = "assets/ethereum-wallet/";
	private final String menuAssetsXPath = "//nav[@id='nav']//ul[contains(@class,'nav-menu')]//li[@id='menu-item-12609']";
	private final String subMenuAssetsXPath = "//nav[@id='nav']//ul[contains(@class,'nav-menu')]//li[@id='menu-item-12609']//ul[contains(@class,'sub-menu')]//li[contains(@id,'menu-item-13963')]";
	private final String logoXPath = "//header[@class='site-header']//div[@class='site-branding']//p[@class='site-title']//img[contains(@src,'zengologo')]";

	public Zengo() {
		System.setProperty("webdriver.chrome.driver", System.getenv("pathDriver"));
		webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		webAction = new Actions(webDriver);

	}

	/*
	 * waits for page to be fully loaded includes javascript
	 */
	public boolean lunchWebSite() {
		try {
			webDriver.get(baseUrl);
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			boolean pageLoaded = new WebDriverWait(webDriver, 10).until((ExpectedCondition<Boolean>) wd -> js
					.executeScript("return document.readyState").equals("complete"));
			if (pageLoaded) {
				return printSuccess("Page Loaded");
			}
			return false;

		} catch (Exception e) {
			return printErrors("Page not Loaded");
		}
	}

	/*
	 * first the function demonstrate hover on assets menu then checks that the
	 * assets sub menu has opened the clicked on Ethereum menu item
	 */
	public boolean navigateEthereum() {

		WebElement assetsMenu = webDriver.findElement(By.xpath(menuAssetsXPath));
		webAction.moveToElement(assetsMenu);
		webAction.build().perform();

		try {
			assetsMenu = new WebDriverWait(webDriver, 3)
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(menuAssetsXPath)));
		} catch (Exception e) {
			return printErrors("Assets menu not opened");
		}

		WebElement subAssetsMenu = webDriver.findElement(By.xpath(subMenuAssetsXPath));
		WebElement EthereumMenuItem = subAssetsMenu.findElement(By.xpath("//a[contains(text, ETH)]"));
		if (EthereumMenuItem == null) {
			return printErrors("Ethereum menu item not found");
		}
		webAction.moveToElement(subAssetsMenu);
		webAction.click().build().perform();
		return true;
	}

	/*
	 * check current url of the browser of ETH route
	 */
	public boolean verifyWebSite() {
		if (!verifyCurrentUrl(baseUrl + ethereumRoute)) {
			return printErrors("Route ETH");
		} else {
			return printSuccess("Route ETH");
		}
	}

	public boolean verifyLogo() {
		try {
			WebElement logo = new WebDriverWait(webDriver, 2)
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(logoXPath)));
			if (logo != null && logo.isDisplayed()) {
				return printSuccess("Logo is Displayed");
			}
		} catch (Exception e) {
		}
		return printErrors("Logo Not Displayed");
	}

	public void navigateBack() {
		webDriver.navigate().back();
	}

	public boolean verifyCurrentUrl(String url) {
		return webDriver.getCurrentUrl().equals(url) ? true : false;
	}

	private boolean printErrors(String err) {
		System.out.println("Error: " + err);
		return false;
	}

	private boolean printSuccess(String msg) {
		System.out.println("Success: " + msg);
		return true;
	}

	private void closeWebSite() {
		webDriver.quit();
	}

	public static void main(String[] args) {
		Zengo zengo = new Zengo();
		if (zengo.lunchWebSite())// lunch and check that the page was loaded success
			if (zengo.navigateEthereum())// navigate to eth url
				if (zengo.verifyWebSite())// verify current url address
					if (zengo.verifyLogo())// verify logo image web site
						zengo.navigateBack();

		zengo.closeWebSite(); // close the browser even if the all scenario didn't complete
	}

}
