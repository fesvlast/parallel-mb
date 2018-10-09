package ua.mb.web.pereval;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ua.mb.Device;
import ua.mb.web.pereval.model.LinkModel;

public class LinksRoutingTest1 {

    private Device device;


    @BeforeClass
    @Parameters({"device_id"})
    public void beforeClass(String deviceId) {
        this.device = new Device(deviceId, null);
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
