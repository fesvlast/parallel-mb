package ua.mb.web.traffim;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ua.mb.Device;
import ua.mb.web.traffim.model.LinkModel;
import ua.mb.web.traffim.model.LinkPageStorage;

public class LinksRoutingTest {

    private Device device;

    private String deviceId;

    private String userAgent;

    public LinksRoutingTest(String deviceId, String userAgent){
        this.deviceId = deviceId;
        this.userAgent = userAgent;
    }


    @Test
    public void scrollNewsSiteTest() throws InterruptedException {
        this.device = new Device(this.deviceId, this.userAgent);
        this.device.start();
        LinkModel model = new LinkModel(device.getWebDriverWrapper());
        model.findRandomLink();
        model.switchToOpenBrowserTab();
        model.waitForPageLoaded();
        model.getLinkOnPage();
    }


    @AfterClass
    public void afterClass() {
        device.stop();
    }

}
