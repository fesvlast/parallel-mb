package ua.mb.wd;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverWrapper {

    private WebDriver driver;

    private WebDriverWait wait;



    public WebDriverWrapper(String deviceId){
        this.init(deviceId, null);
    }

    public WebDriverWrapper(String deviceId, String userAgent){
       this.init(deviceId, userAgent);
    }

    private void init(String deviceId, String userAgent){
        ChromeOptions options = new ChromeOptions();
        if (userAgent != null && !userAgent.isEmpty()){
            options.addArguments("user-agent="+ userAgent);
        }
        options.addArguments("disable-infobars");
        options.addArguments("start-maximized");
        options.addArguments("--disable-notifications");
        options.setExperimentalOption("androidPackage", "com.android.chrome");
        options.setExperimentalOption("androidDeviceSerial", deviceId);
        options.setCapability("applicationCacheEnabled", false);
        this.driver = new ChromeDriver(options);
        this.wait = new WebDriverWait(driver, 15);
    }


    public WebDriver getDriver(){
        return driver;
    }

    public WebDriverWait getWait(){
        return wait;
    }

    public void quit(){
        driver.quit();
        driver = null;
    }
}
