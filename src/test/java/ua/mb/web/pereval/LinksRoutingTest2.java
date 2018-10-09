package ua.mb.web.pereval;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ua.mb.Device;
import ua.mb.web.pereval.model.LinkModel;

public class LinksRoutingTest2 {

    private Device device;


    @BeforeClass
    @Parameters({ "device_id", "user_agent" })
    public void beforeClass(String deviceId, String userAgent) {
        this.device = new Device(deviceId, userAgent);
        this.device.turnOnMobileInternet();
        this.device.isConnectedToInternet();
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
