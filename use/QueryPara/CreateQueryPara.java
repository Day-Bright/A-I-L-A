package use.QueryPara;

import use.test.AnalyzLaw;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreateQueryPara {


    public static String processString(String originalString){
        originalString = originalString.replaceAll("[^a-zA-Z0-9\\u4E00-\\u9FA5]", " ");
        originalString = originalString.replaceAll(" +"," ");
        String new_String = originalString;
        return new_String;
    }

    public void cqp(String queryPath,String paraPath) throws IOException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(paraPath));
        pw.println("<parameters>");

        pw.println("<index>C:\\Users\\Me\\Desktop\\aila_xu\\target\\index\\fl_index</index>");

        ReadFile readFile = new ReadFile();
        HashMap<String, String> query_map = readFile.read_f(queryPath);

        for(Map.Entry<String, String> entry : query_map.entrySet()){
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            pw.println("<query>");
            pw.println("<type>indri</type>");
            pw.println("<number>"+mapKey+"</number>");
            String s = processString(mapValue);
            pw.println("<text>"+AnalyzLaw.AnalyzeStr(processString(s))+"</text>");
//            System.out.println(AnalyzLaw.AnalyzeStr(processString(s)));
            pw.println("</query>");
        }

        pw.println("</parameters>");
        pw.close();

    }

    public static void main(String[] args) throws IOException {

        String queryFile ="D:\\FileRecv\\aila20-test\\TestData_release\\Task1_test_data.txt";
        String savePath = "C:\\Users\\Me\\Desktop\\aila_xu\\target\\query\\para\\query.para";

        CreateQueryPara createQueryPara = new CreateQueryPara();
        createQueryPara.cqp(queryFile,savePath);

    }
}
