
public class Main {
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
