package use.QueryPara;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadFile {

    public HashMap<String,String> read_f(String fileName){

        File file = new File(fileName);
        BufferedReader reader = null;

        HashMap<String,String> queryMap = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
//                System.out.println(tempString);
                String[] split = tempString.split("\\|\\|");
                queryMap.put(split[0],split[1]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(queryMap);
        return queryMap;
    }

    public static void main(String[] args) {
        ReadFile readFile = new ReadFile();
        readFile.read_f("D:\\FileRecv\\aila20-test\\TestData_release\\Task1_test_data.txt");
    }

}
