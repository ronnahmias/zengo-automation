import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Zengo {
	WebDriver webDriver;

	public Zengo() {
		System.setProperty("webdriver.chrome.driver", System.getenv("pathDriver"));
		webDriver = new ChromeDriver();
	}

	public void lunchSite() {
		webDriver.get("http://www.google.com/");
	}

	public static void main(String[] args) {
		Zengo zengo = new Zengo();
		zengo.lunchSite();
	}

}
