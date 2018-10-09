package ua.mb.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandExecutor {

    private String ADB_HOME ;


    public CommandExecutor() {
        this.ADB_HOME = System.getenv("ADB_HOME");
    }

    /**
     * Execute ADB command
     * @param command
     * @return
     */
    public String executeCommand(String command){
        String s;
        String buffer = "";
        try{
            Process p = Runtime.getRuntime().exec(  this.ADB_HOME + command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((s = stdInput.readLine()) != null) {
                buffer += s+"\n";
            }
            stdInput.close();

        }catch (IOException ex){
            System.out.println(ex);
        }
        return buffer;
    }

}
