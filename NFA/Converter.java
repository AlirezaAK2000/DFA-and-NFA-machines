package NFA;

import DFA.DFAMachine;
import DFA.State;

import java.util.HashMap;
import java.util.HashSet;

public class Converter {
    private HashMap<HashSet<NFAState>, HashMap<String, HashSet<NFAState>>> states;
    private NFAState first_state;
    private HashSet<String> actions;
    private HashMap<HashSet<NFAState>, State> new_states;
    private HashSet<State> finale_states;


    public Converter(NFAState first_state, HashSet<String> actions) {
        this.first_state = first_state;
        this.actions = actions;
        states = new HashMap<>();
        new_states = new HashMap<>();
        finale_states = new HashSet<>();
    }


    public DFAMachine craeteDfaMachine() {
        HashSet<NFAState> first_set = new HashSet<>();
        first_set.add(first_state);
        convertToDFA(first_set, true);
        HashSet<HashSet<NFAState>> all_states = new HashSet<>(states.keySet());
        for (HashSet<NFAState> state : all_states) {
            String name = createNameForState(state);
            State new_state = new State(name);
            if (isFinale(state)) {
                finale_states.add(new_state);
                new_state.setAsFinalState();
            }
            new_states.put(state, new_state);
        }

        for (HashSet<NFAState> state : all_states) {
            HashMap<String, HashSet<NFAState>> action_states = states.get(state);
            HashSet<String> actions = new HashSet<>(action_states.keySet());
            for (String action : actions) {
                State DFAState = new_states.get(state);
                DFAState.addAction(action, new_states.get(action_states.get(action)));
            }
        }

        DFAMachine machine = new DFAMachine(new_states.get(first_state));
        return machine;
    }

    private void convertToDFA(HashSet<NFAState> first_s, boolean addable) {
        if (states.containsKey(first_s))
            return;


        HashSet<NFAState> new_state = new HashSet<>();
        new_state.addAll(first_s);
        for (NFAState state : first_s) {
            if (state.getActions().contains("λ")) {
                new_state.addAll(state.getAction_ns().get("λ"));
            }
        }
        if (!new_state.equals(first_s)) {
            convertToDFA(new_state, false);
        }

        HashMap<String, HashSet<NFAState>> state_action = null;
        state_action = new HashMap<>();

//        System.out.println(addable);
        if (addable)
            states.put(first_s, state_action);


        for (NFAState state : first_s) {
            HashMap<String, HashSet<NFAState>> state_data = state.getAction_ns();
            HashSet<String> state_actions = state.getActions();
            boolean null_string = false;
            if (state_actions.contains("λ")) {
                null_string = true;
                state_actions.remove("λ");
            }
            for (String action : actions) {
                if (!state_action.containsKey(action))
                    state_action.put(action, new HashSet<>());


                HashSet<NFAState> states1 = new HashSet<>();
                if (state_actions.contains(action)) {
                    states1.addAll(state_data.get(action));
                    for (NFAState s : states1) {
                        HashSet<String> actions = s.getActions();
                        HashMap<String, HashSet<NFAState>> action_ns = s.getAction_ns();
                        if (actions.contains("λ")) {
                            state_action.get(action).addAll(action_ns.get(action));
                        }
                    }
                }
                if (null_string) {
                    states1.addAll(nullPath(state , action));
                }
                state_action.get(action).addAll(states1);

//                    state_data.get(action)
            }
        }

        for (String action : state_action.keySet()) {
            convertToDFA(state_action.get(action), true);
        }

    }


    private HashSet<NFAState> nullPath(NFAState state, String action) {
            HashSet<NFAState> states = state.getAction_ns().get("λ");
            HashSet<NFAState> finale_states = new HashSet<>();
            for (NFAState s : states){
                HashSet<String> actions = s.getActions();
                HashMap<String, HashSet<NFAState>> action_ns = s.getAction_ns();
                if (actions.contains("λ")) {
                    finale_states.addAll(nullPath(s, action));
                    actions.remove("λ");
                }
                finale_states.addAll(action_ns.get(action));
            }
            return finale_states;
    }


    private String createNameForState(HashSet<NFAState> state) {
        String name = "{";
        for (NFAState s : state)
            name += s.getName();

        name += "}";
        return name;
    }

    private boolean isFinale(HashSet<NFAState> state) {
        for (NFAState s : state) {
            if (s.isIs_des()) {

                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String data = "";
        for (String action : actions){
            data += action + " ";
        }
        data += "\n";
        HashSet<State> states = new HashSet<>(new_states.values());
        for (State state : states){
            data += state + " ";
        }
        data += "\n";
        data += first_state + "\n";
        for (State state : finale_states){
            data += state + " ";
        }
        data += "\n";


        for (State state : new_states.values()) {
            HashSet<String> actions = state.getActions();
            HashMap<String, State> next_states = state.getAction_ns();
            for (String action : actions) {
                data += state + " " + action + " " + next_states.get(action) + "\n";
            }

        }

        return data;
    }
}

