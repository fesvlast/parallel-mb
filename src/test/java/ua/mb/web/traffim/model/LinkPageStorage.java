package ua.mb.web.traffim.model;

import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class LinkPageStorage {

    private List<WebElement> allHomeLinks;
    private List<WebElement> allForeignLinks;
    private String host;

    public LinkPageStorage(String host, List<WebElement> allHomeLinks, List<WebElement> allForeignLinks  ){
        this.allForeignLinks = allForeignLinks;
        this.allHomeLinks = allHomeLinks;
        this.host = host;
    }


    public List<WebElement> getAllHomeLinks() {
        return allHomeLinks;
    }

    public List<WebElement> getAllForeignLinks() {
        return allForeignLinks;
    }

    public String getHost() {
        return host;
    }

    public WebElement getRandomHomeLink(){
        Random rand = new Random();
        int number = rand.nextInt(allHomeLinks.size());
        return  allHomeLinks.get(number);
    }

    public WebElement getRandomForeignLink(){
        Random rand = new Random();
        int number = rand.nextInt(allForeignLinks.size());
        return  allForeignLinks.get(number);
    }
}
