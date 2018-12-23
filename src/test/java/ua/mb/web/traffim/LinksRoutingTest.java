package ua.mb.web.traffim;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ua.mb.Device;
import ua.mb.statistic.StatisticSender;
import ua.mb.web.traffim.model.LinkModel;

public class LinksRoutingTest {

    private Device device;

    private String deviceId;

    private String userAgent;

    private StatisticSender sender;

    public LinksRoutingTest(String deviceId, String userAgent){
        this.deviceId = deviceId;
        this.userAgent = userAgent;
        this.sender = new StatisticSender(this.deviceId);
    }


    @Test
    public void siteClickerTest() throws InterruptedException {
        this.device = new Device(this.deviceId, this.userAgent);
        this.sender.sendStartStatistic();
        this.device.start();
        LinkModel model = new LinkModel(device.getWebDriverWrapper());
        model.waitInitialPageLoaded();
        model.findRandomLinkAndClick();
        model.switchToOpenBrowserTab();

        for (int i = 0; i <3 ; i++) {
            model.waitForPageLoaded();
            model.scrollToBottomAndUp();
            model.findHomeLinkAndMove();
            model.switchToOpenBrowserTab();
        }

        model.waitForPageLoaded();
        model.scrollToBottomAndUp();

        model.findForeignLinkAndMove();
        model.waitForPageLoaded();
        model.switchToOpenBrowserTab();
        model.scrollToBottomAndUp();
        model.scrollToBottomAndUp();
    }


    @AfterMethod
    public void afterClass(ITestResult result) {
        System.out.println("Status: " +result.getStatus());
        this.sender.sendFinalStatusStatistic(result.getStatus());
        device.stop();
    }

}
