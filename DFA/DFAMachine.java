package DFA;


import java.util.HashMap;

public class DFAMachine {
    private State first_state;

    public DFAMachine(State first_state) {
        this.first_state = first_state;
    }

    public boolean evaluate(String input , State current_state){
        if (input.length() == 0 ){
            if (current_state.isIs_des())
                return true;
            else
                return false;

        }else {
            String action = input.substring(0,1);
            HashMap<String , State> next_setps = current_state.getAction_ns();
            if (next_setps.containsKey(action)){
                return evaluate(input.substring(1) , next_setps.get(action));
            }else {
                return false;
            }
        }
    }


}
