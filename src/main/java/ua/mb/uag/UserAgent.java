package ua.mb.uag;

public class UserAgent {
    private String name;
    private String agentStr;

    public UserAgent(String name, String agentStr) {
        this.name = name;
        this.agentStr = agentStr;
    }

    public String getName() {
        return name;
    }

    public String getAgentStr() {
        return agentStr;
    }
}
