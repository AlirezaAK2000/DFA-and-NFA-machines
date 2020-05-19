package NFA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class NFADataReader {
    private String path;
    private HashMap<String, NFAState> states;
    private HashSet<String> actions;
    private NFAState first_state;


    public NFADataReader(String path) {
        this.path = path;
        states = new HashMap<>();
        actions = new HashSet<>();
    }


    public HashSet<String> getActions() {
        return actions;
    }

    public NFAState getFirst_state() {
        return first_state;
    }

    public void readData(){
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader);
            setActions(br.readLine().trim().split(" "));
            createStates(br.readLine().trim().split(" "));
            setFirst_state(br.readLine().trim());
            setFinalStates(br.readLine().trim().split(" "));
            String str;
            while((str=br.readLine())!=null && str.length()!=0){
                addAction(str.trim().split(" "));
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setActions(String [] actions){
        for(String action : actions){
            this.actions.add(action);
        }
    }

    private void createStates(String [] names){
        for(String name : names){
            states.put(name , new NFAState(name));
        }
    }

    private void setFirst_state(String name){
        first_state = states.get(name);
    }

    private void setFinalStates(String [] names){
        for (String name : names){
            states.get(name).setAsFinalState();
        }
    }

    private void addAction(String [] data){
        NFAState cur_state = states.get(data[0]);
        String action = data[1];
        NFAState next_state = states.get(data[2]);
        cur_state.addAction(action  , next_state);
    }
}
