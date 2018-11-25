package ua.mb.web.traffim.model;

        import com.google.common.io.Files;
        import org.apache.commons.codec.Charsets;
        import org.apache.commons.lang3.ArrayUtils;
        import org.openqa.selenium.*;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import ua.mb.wd.WebDriverWrapper;

        import java.io.File;
        import java.io.IOException;
        import java.util.*;

public class LinkModel {
    public final String url = "https://ua.traffim.com/j.php";
    private WebDriver driver;
    private WebDriverWait wait;

    private WebElement currentLinkElement;

    public LinkModel(WebDriverWrapper wdw) {
        this.driver = wdw.getDriver();
        this.wait = wdw.getWait();
    }

    public void waitForPageLoaded() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       /* ExpectedCondition<Boolean> expectation = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(expectation);
        } catch (Throwable error) {
           System.out.println("Failed to wait page loaded");
        }*/
    }

    //To do Needs refactoring
    public void scrollToBottomAndUp() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        int divider = 5;
        int acc = 0;
        int[] arr = new int[divider];

        for (int i = 0; i < divider; i++) {
            acc +=1000;
            arr[i] = acc;
            js.executeScript("window.scrollTo(0,"+acc+");");
            Thread.sleep(800);
        }
        js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
        Thread.sleep(800);
        ArrayUtils.reverse(arr);
        for (int i = 0; i < divider ; i++) {
            js.executeScript("window.scrollTo(0,"+arr[i]+");");
            Thread.sleep(800);
        }
        js.executeScript("window.scrollTo(0,0)");
        Thread.sleep(800);
    }



    public String getCurrentHostName(){
        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        return js.executeScript("return window.location.hostname;").toString();
    }

    public void executeScriptFromFile() throws InterruptedException {
        Thread.sleep(1000);
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource("HomeLinkFinder.js").getFile());
        //File is found
        System.out.println("File Found : " + file.exists());

        String fileContents = null;
        try {
            fileContents = Files.toString(file, Charsets.UTF_8);
            JavascriptExecutor js = (JavascriptExecutor)driver;
            js.executeScript(fileContents);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread.sleep(10000);
    }

    public void findRandomLink() throws InterruptedException{
        driver.get(url);
        List<WebElement> linksElements = driver.findElements(By.cssSelector("a.traffim-title-link"));

        Random rand = new Random();
        int number = rand.nextInt(linksElements.size());
        this.currentLinkElement = linksElements.get(number);
        this.currentLinkElement.click();
    }


    public void switchToOpenBrowserTab() throws InterruptedException{
        String mainWindowId = driver.getWindowHandle();
        String newWinId = "";
       // this.currentLinkElement.click();
        //wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> allWindows = driver.getWindowHandles();

        for (String winId: allWindows) {
            if (!mainWindowId.equals(winId)){
                newWinId = winId;
            }
        }

        System.out.println("ID: " + newWinId);
        if( allWindows.size()> 1){
            driver.close();
            driver.switchTo().window(newWinId);
        }
        System.out.println("Old win: " + driver.getTitle());
    }

    public void getLinkOnPage() {
        String currentHost = this.getCurrentHostName();
        System.out.println("Host: " + currentHost);

        try {
            this.scrollToBottomAndUp();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> listOfAllLinks = driver.findElements(By.tagName("a"));
        List<WebElement> allHomeLinks = new ArrayList<>();
        List<WebElement> allForeignLinks = new ArrayList<>();
        System.out.println("All links: "+listOfAllLinks.size());
        for (WebElement currentElement: listOfAllLinks) {
           try{
               if ( currentElement.isDisplayed()&& currentElement.isEnabled() && currentElement.getAttribute("href").contains("http") ){
                   System.out.println(currentElement.getAttribute("href"));

                   if(currentElement.getAttribute("href").contains(currentHost)){
                       allHomeLinks.add(currentElement);
                       wait.until(ExpectedConditions.elementToBeClickable(currentElement));
                       System.out.println(currentElement.getAttribute("href"));
                       // currentElement.click();
                       // break;
                   }else {
                       allForeignLinks.add(currentElement);
                       //  System.out.println(currentElement.getAttribute("href"));
                   }

               }
           }catch (NullPointerException ex){
               System.out.println("NullPointerException occured");
           }
        }

       // return new LinkPageStorage(currentHost, allHomeLinks, allForeignLinks);
        System.out.println("All home links: "+allHomeLinks.size());
        System.out.println("All foreign links: "+allForeignLinks.size());



      /*  for (int i = 0; i < 10 ; i++) {
            try {
                Random rand = new Random();
                int number = rand.nextInt(allHomeLinks.size());
                WebElement randomLink = allHomeLinks.get(number);
                Actions actions = new Actions(driver);
                actions.moveToElement(randomLink);
                System.out.println(randomLink.getAttribute("href"));
                actions.perform();
                randomLink.click();
            }catch (StaleElementReferenceException ex){
                System.out.println("StaleElementReferenceException occured");
            }
        }


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
    }
}

