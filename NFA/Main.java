package NFA;

import DFA.DFAMachine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String path = "src/NFA/NFA_Input_2.txt";
        NFADataReader dataReader = new NFADataReader(path);
        dataReader.readData();
        Converter converter = new Converter(dataReader.getFirst_state(), dataReader.getActions());
        DFAMachine machine = converter.craeteDfaMachine();
        fileWriter("src/NFA/DFA_Output_2.txt" , converter.toString());
    }

    public static void fileWriter(String path , String data){
        try {
            FileWriter writer = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(data);
            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
