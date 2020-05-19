package DFA;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String path = "src/DFA/DFA_Input_1.txt";
        DataReader dataReader = new DataReader(path);
        dataReader.readData();
        State first = dataReader.getFirst_state();
        DFAMachine machine  = new DFAMachine(first);
        Scanner scanner = new Scanner(System.in);
        while (true){
            String input = scanner.next();
            System.out.println(machine.evaluate(input ,first ));
        }
    }

}
