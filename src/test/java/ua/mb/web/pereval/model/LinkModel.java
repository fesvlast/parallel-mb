package ua.mb.web.pereval.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ua.mb.wd.WebDriverWrapper;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class LinkModel {
    public final String url = "http://www.pereval.net/te$t.html";
    private WebDriver driver;
    private WebDriverWait wait;

    private WebElement currentLinkElement;

    public LinkModel(WebDriverWrapper wdw) {
        this.driver = wdw.getDriver();
        this.wait = wdw.getWait();
    }

    public void findRandomLink() throws InterruptedException{
        driver.get(url);
        List<WebElement> linksElements = driver.findElements(By.cssSelector(".sele-test a"));
        Random rand = new Random();
        int number = rand.nextInt(linksElements.size());
        this.currentLinkElement = linksElements.get(number);
    }

    public void switchToOpenBrowserTab() throws InterruptedException{
        String mainWindowId = driver.getWindowHandle();
        String newWinId = "";
        this.currentLinkElement.click();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> allWindows = driver.getWindowHandles();
        for (String winId: allWindows) {
            if (!mainWindowId.equals(winId)){
                newWinId = winId;
            }
        }
        driver.switchTo().window(newWinId);

        System.out.println("Old win: " + driver.getTitle());
        //Thread.sleep(10000);
    }

    public void scrollingUpToBottom() throws InterruptedException{
        List<WebElement> listOfAllDivs = driver.findElements(By.cssSelector(".container div :nth-child(3)"));
        Actions actions = new Actions(driver);
        for (WebElement currentElement: listOfAllDivs) {
             // System.out.println(currentElement.getText());
           if ( currentElement.isDisplayed()){
               actions.moveToElement(currentElement);
               actions.perform();
           }
        }
    }
}
