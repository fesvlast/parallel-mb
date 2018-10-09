package ua.mb.web.pereval;

import org.testng.annotations.Factory;
import ua.mb.core.MobileFactory;

import java.util.List;

public class TestFactory {


    @Factory
    public Object[] factoryMethod() throws Exception {
        MobileFactory factory = new MobileFactory();
        List<String> list = factory.getAllRegisteredDeviceID();
        Object[] methods = new Object[list.size()];

        for (int i = 0; i <list.size() ; i++) {
            methods[i] = new LinksRoutingTest1(list.get(i));
        }

        return methods;
    }
}
