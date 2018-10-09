package ua.mb.web.pereval;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ua.mb.Device;
import ua.mb.web.pereval.model.LinkModel;

public class LinksRoutingTest1 {

    private Device device;

    private String deviceId;

    public LinksRoutingTest1(String deviceId){
        this.deviceId = deviceId;
    }


    @BeforeClass
    public void beforeClass() {
        this.device = new Device(this.deviceId, null);
        this.device.start();
    }

    @Test
    public void scrollNewsSiteTest() throws InterruptedException {
        LinkModel model = new LinkModel(device.getWebDriverWrapper());
        model.findRandomLink();
        model.switchToOpenBrowserTab();
        model.scrollingUpToBottom();
    }

    @AfterClass
    public void afterClass() {
        device.stop();
    }

}
