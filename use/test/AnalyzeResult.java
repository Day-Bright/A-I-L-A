package use.test;

import com.sun.jdi.Value;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalyzeResult {

    public void createFile(String fieldPath,String newField) throws IOException {

        File writefile =new File(newField);
        Writer out =new FileWriter(writefile);

        File readfile = new File(fieldPath);
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(readfile));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                String[] split = tempString.split(" ");
                String reg ="([^<>/\\\\|:\"\"\\*\\?]+)\\.\\w+$+";
                String docName="";
                Matcher matcher =Pattern.compile(reg).matcher(split[2]);
                while (matcher.find()) {
                    docName = matcher.group();
                }

                String new_str = split[0]+" "+split[1]+" "+docName.substring(0,docName.length()-4)+" "+split[3]+" "+split[4]+" "+split[5]+"\n";
                out.write(new_str);

            }
            reader.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {

        String saveNewField="D:\\FileRecv\\aila20-task1\\relevance_judgements\\task1a.txt";
        String fieldname="C:\\Users\\Me\\Desktop\\aila_xu\\target\\query\\lm_ts.score";
        AnalyzeResult t=new AnalyzeResult();
        t.createFile(fieldname,saveNewField);
    }

}

