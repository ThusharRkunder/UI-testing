package Unit_Test_Case;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TC001 {
	  public static WebDriver driver;

	    // List of URLs to test
	    public static List<String> urls = List.of(
	            "https://www.getcalley.com/", 
	            "https://www.getcalley.com/calley-lifetime-offer/",
	            "https://www.getcalley.com/see-a-demo/",
	            "https://www.getcalley.com/calley-teams-features/",
	            "https://www.getcalley.com/calley-pro-features/"
	    );

	    // List of resolutions and devices
	    public static String[][] resolutions = {
	            {"Desktop", "1920x1080"},
	            {"Desktop", "1366x768"},
	            {"Desktop", "1536x864"},
	            {"Mobile", "360x640"},
	            {"Mobile", "414x896"},
	            {"Mobile", "375x667"}
	    };

	public static void main(String[] args) throws IOException, InterruptedException {
		runTests("chrome");
        runTests("firefox");
//        runTests("safari");

	}
	   public static void runTests(String browser) throws IOException, InterruptedException {
	        switch (browser) {
	            case "chrome":
	                driver = new ChromeDriver();
	                break;
	            case "firefox":
	                driver = new FirefoxDriver();
	                break;
//	            case "safari":
//	                driver = new SafariDriver();
//	                break;
	        }
	        // Loop through all URLs and resolutions
	        for (String url : urls) {
	            for (String[] resolution : resolutions) {
	                takeScreenshot(url, browser, resolution[0], resolution[1]);
	            }
	        }
	        driver.quit();

}
	   public static void takeScreenshot(String url, String browser, String device, String resolution) throws IOException, InterruptedException {
	        String[] res = resolution.split("x");
	        int width = Integer.parseInt(res[0]);
	        int height = Integer.parseInt(res[1]);
	        driver.manage().window().setSize(new Dimension(width, height));
	        driver.get(url);
	        Thread.sleep(3);

	        // Create the folder structure: Browser/Device/Resolution
	        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
	        String folderPath = String.format("%s/%s/%s", browser, device, resolution);
	        Files.createDirectories(Paths.get(folderPath));

	        // Take screenshot
	        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        String screenshotName = String.format("%s/screenshot-%s.png", folderPath, timestamp);
	        Files.copy(screenshot.toPath(), Paths.get(screenshotName));
	        
	        
	    }
	   
}
