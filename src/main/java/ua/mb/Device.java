package ua.mb;

import ua.mb.core.CommandBuilder;
import ua.mb.core.CommandExecutor;
import ua.mb.wd.WebDriverWrapper;

public class Device {

    private String deviceId;

    private String userAgent;

    private CommandExecutor executor;

    private CommandBuilder command;

    private WebDriverWrapper wdw;


    public Device(String deviceId, String userAgent){
        this.deviceId = deviceId;
        this.userAgent = userAgent;
        this.executor = new CommandExecutor();
        this.command = new CommandBuilder();
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

    public void forceStopChrome(){
        String command = this.command.forceStopChrome(this.deviceId);
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
                Thread.sleep(1000);
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
        //this.forceStopChrome();
    }

    public void start() {
        this.turnOnMobileInternet();
        if(!this.isConnectedToInternet()){
            try {
                throw new Exception("Failed to connect to mobile internet!!");
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    public WebDriverWrapper getWebDriverWrapper (){
        if(this.wdw == null){
            this.wdw = new WebDriverWrapper(deviceId, userAgent);
        }
        return this.wdw;
    }

}
