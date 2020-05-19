package NFA;

import DFA.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class NFAState {
    private String name;
    private HashMap<String, HashSet<NFAState>> action_ns;
    private boolean is_des;

    public String getName() {
        return name;
    }

    public HashMap<String, HashSet<NFAState>> getAction_ns() {
        return action_ns;
    }

    public boolean isIs_des() {
        return is_des;
    }

    public NFAState(String name) {
        this.name = name;
        this.is_des = false;
        this.action_ns = new HashMap<>();
    }

    public void setAsFinalState(){
        this.is_des = true;
    }

    public void addAction(String action , NFAState next_state){
        if (action_ns.containsKey(action)){
            action_ns.get(action).add(next_state);
        }else {
            HashSet<NFAState> states = new HashSet<>();
            states.add(next_state);
            action_ns.put(action , states);
        }
    }

    public HashSet<String> getActions(){
        return new HashSet<>(action_ns.keySet());
    }

    @Override
    public String toString() {
        return name;
    }

}
