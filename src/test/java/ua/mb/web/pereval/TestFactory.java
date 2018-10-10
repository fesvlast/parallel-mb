package ua.mb.web.pereval;

import org.testng.annotations.Factory;
import ua.mb.core.MobileFactory;
import ua.mb.uag.UserAgentParser;

import java.util.List;

public class TestFactory {


    @Factory
    public Object[] factoryMethod() throws Exception {
        MobileFactory factory = new MobileFactory();
        List<String> list = factory.getAllRegisteredDeviceID();
        Object[] methods = new Object[list.size()];

        for (int i = 0; i <list.size() ; i++) {
            String userAgentStr = UserAgentParser
                    .getUserAgentParser()
                    .getRandomUserAgent()
                    .getAgentStr();
            methods[i] = new LinksRoutingTest(list.get(i), userAgentStr);
        }

        return methods;
    }
}
