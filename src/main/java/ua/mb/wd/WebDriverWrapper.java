package ua.mb.wd;


import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

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
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        options.addArguments("disable-infobars");
        options.addArguments("start-maximized");
        options.addArguments("disable-notifications");
        options.addArguments("disable-web-security");
        options.setExperimentalOption("androidPackage", "com.android.chrome");
        options.setExperimentalOption("androidDeviceSerial", deviceId);
        options.setCapability("applicationCacheEnabled", false);
        this.driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        this.wait = new WebDriverWait(driver, 60);
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
