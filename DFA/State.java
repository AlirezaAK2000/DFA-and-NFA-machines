package DFA;

import java.util.HashMap;
import java.util.HashSet;

public class State {
    private String name;
    private HashMap<String,State> action_ns;
    private boolean is_des;

    public String getName() {
        return name;
    }

    public HashMap<String, State> getAction_ns() {
        return action_ns;
    }

    public boolean isIs_des() {
        return is_des;
    }

    public State(String name) {
        this.name = name;
        this.is_des = false;
        this.action_ns = new HashMap<>();
    }

    public void setAsFinalState(){
        this.is_des = true;
    }

    public void addAction(String action , State next_state){
        action_ns.put(action , next_state);
    }

    public HashSet<String> getActions(){
        return new HashSet<>(action_ns.keySet());
    }

    @Override
    public String toString() {
        return name;
    }
}
