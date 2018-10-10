package ua.mb.uag;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class UserAgentParser {

    List<UserAgent> userAgentList;



    private List<UserAgent> readlUserAgents() throws IOException{

        this.userAgentList = new ArrayList<>();

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        File file = new File(classLoader.getResource("userAgents.json").getFile());

        //File is found
        System.out.println("File Found : " + file.exists());

        byte[] jsonData = Files.readAllBytes(file.toPath());


        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        //convert json string to object
        List<LinkedHashMap> userAgentsMap = objectMapper.readValue(jsonData, List.class);

        for (LinkedHashMap map: userAgentsMap) {
            userAgentList.add(
                    new UserAgent(
                            map.get("name").toString(),
                            map.get("agent_str").toString()));
        }
        return userAgentList;
    }

    public List<UserAgent> getAllUserAgents(){
        if (this.userAgentList == null){
            try{
                this.userAgentList = this.readlUserAgents();
            }catch (IOException e){
                System.out.println("Can't parse json file!   "+e);
            }
        }

        return this.userAgentList;
    }

    public UserAgent getRandomUserAgent(){
        Random rand = new Random();
        int randomNumber = rand.nextInt(this.getAllUserAgents().size());
        return this.getAllUserAgents().get(randomNumber);
    }




}
