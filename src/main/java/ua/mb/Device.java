package ua.mb;

import ua.mb.core.CommandBuilder;
import ua.mb.core.CommandExecutor;
import ua.mb.wd.WebDriverWrapper;

public class Device {

    private String deviceId;

    private CommandExecutor executor;

    private CommandBuilder command;

    private WebDriverWrapper wdw;


    public Device(String deviceId, String userAgent){
        this.deviceId = deviceId;
        this.executor = new CommandExecutor();
        this.command = new CommandBuilder();
        this.wdw = new WebDriverWrapper(deviceId, userAgent);
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void turnOnMobileInternet(){
       String command = this.command.manageMobileInternet(this.deviceId, CommandBuilder.ENABLE);
       executor.executeCommand(command);
    }

    public void turnOffMobileInternet(){
        String command = this.command.manageMobileInternet(this.deviceId, CommandBuilder.DISABLE);
        executor.executeCommand(command);
    }

    public boolean isConnectedToInternet(){
        String command = this.command.isConnectedToInternet(this.deviceId);
        String errorMsg = "ping: unknown host www.google.com";
        for (int i = 0; i < 10 ; i++) {
            if (!executor.executeCommand(command).contains(errorMsg)){
                return true;
            }
            try {
                Thread.sleep(500);
            }catch (InterruptedException ex){
                System.out.println(ex);
                return false;
            }
        }

        return false;
    }

    public void stop(){
        this.wdw.quit();
        this.turnOffMobileInternet();

    }

    public void start(){
        this.turnOnMobileInternet();
        this.isConnectedToInternet();
    }

    public WebDriverWrapper getWebDriverWrapper (){
        return this.wdw;
    }

}
