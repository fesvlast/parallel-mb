package ua.mb.web.traffim.model;

        import org.apache.commons.lang3.ArrayUtils;
        import org.openqa.selenium.*;
        import org.openqa.selenium.support.ui.ExpectedCondition;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import ua.mb.wd.WebDriverWrapper;

        import java.io.File;
        import java.io.IOException;
        import java.nio.charset.Charset;
        import java.nio.file.Files;
        import java.nio.file.Paths;
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
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
     // System.out.println("Wait completed");
    }

    public void waitInitialPageLoaded(){
        ExpectedCondition<Boolean> expectation = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, 15);
            wait.until(expectation);
        } catch (Throwable error) {
           System.out.println("Failed to wait page loaded");
        }
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
            Thread.sleep(500);
        }
        js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
        Thread.sleep(500);
        ArrayUtils.reverse(arr);
        for (int i = 0; i < divider ; i++) {
            js.executeScript("window.scrollTo(0,"+arr[i]+");");
            Thread.sleep(500);
        }
        js.executeScript("window.scrollTo(0,0)");
        Thread.sleep(500);
    }


    public String getCurrentHostName(){
        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        return js.executeScript("return window.location.hostname;").toString();
    }

    private String executeScriptFromFile(String fileName) throws InterruptedException {
        Thread.sleep(1000);
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000);
                String fileContents = this.readFile(file, Charset.forName("UTF-8"));
                JavascriptExecutor js = (JavascriptExecutor)driver;
                return js.executeScript(fileContents).toString();

            }catch (NullPointerException e){
                System.err.println(e);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
       // Thread.sleep(1000);
        return null;
    }

    private String readFile(File file, Charset encoding) throws IOException
    {
        String path = file.getPath();
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }


    public void findRandomLinkAndClick(){
        driver.get(this.url);
        this.waitInitialPageLoaded();
       List<WebElement> linksElements = driver.findElements(By.cssSelector("a.traffim-title-link"));

        Random rand = new Random();
        int number = 0;
        if(linksElements.size()> 0){
            number = rand.nextInt(linksElements.size());
        }
        this.currentLinkElement = linksElements.get(number);
        this.currentLinkElement.click();
    }


    public void switchToOpenBrowserTab(){
        String mainWindowId = driver.getWindowHandle();
        String newWinId = "";

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
        this.waitInitialPageLoaded();
        System.out.println("Current title: " + driver.getTitle());
    }

    public void findHomeLinkAndMove(){
        try {
            String link = this.executeScriptFromFile("HomeLinkFinder.js");
            System.out.println("This Home link was clicked: " + link);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void findForeignLinkAndMove(){
        try {
            String link = this.executeScriptFromFile("ForeignLinkFinder.js");
            System.out.println("This Foreign link was clicked: " + link);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

