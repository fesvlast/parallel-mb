package ua.mb.core;

import ua.mb.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileFactory {

    private List<Device> listOfDevices;

    public MobileFactory() throws Exception{
        listOfDevices = new ArrayList<>();
        this.init();
    }

    /**
     *
     * @return listOfDevices
     */
    public List<Device> getListOfDevices() {
        return listOfDevices;
    }

    /**
     *
     * @throws Exception
     */
    private void init() throws Exception{
        List<String> deviceIdList = this.getAllRegisteredDeviceID();
        if(deviceIdList.size() == 0){
            throw new Exception("No devices were connected");
        }
        for (String id: deviceIdList) {
            this.listOfDevices.add(new Device(id, null));
        }
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public List<String> getAllRegisteredDeviceID() throws Exception {
        CommandExecutor exec = new CommandExecutor();
        String buffer = exec.executeCommand(new CommandBuilder().getListOfConnectedDevices());

        List<String> list = new ArrayList<>();
        Matcher m = Pattern.compile("\\w{10,40}\\s+(offline|device)").matcher(buffer);
        while (m.find()) {
            if(m.group().contains("offline")){
              System.err.println("Device is offline: " +m.group());
            }else {
                list.add(m.group().replace("device", "").trim());
            }
        }
        return list;
    }
}
