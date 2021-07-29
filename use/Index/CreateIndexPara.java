package use.Index;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class CreateIndexPara {

    HashSet<String> stopWords = new HashSet<String> ();


    public void loadStopWord(String stopWordPath) throws FileNotFoundException{
        Scanner input = new Scanner(new FileInputStream(stopWordPath));

        while(input.hasNextLine()){
            String word = input.nextLine().trim();
            stopWords.add(word);
        }

        input.close();


    }


    public void createIndexPara(String paraPath) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(new FileOutputStream(paraPath));
        pw.println("<parameters>");
        pw.println("<memory>512M</memory>");

        pw.println("<metadata>");
        pw.println("<forward>docno</forward>");
        pw.println("<backward>docno</backward>");
        pw.println("</metadata>");

        pw.println("<field>");
        pw.println("<name>TEXT</name>");
        pw.println("</field>");

        pw.println("<stemmer>");
        pw.println("<name>krovetz</name>");
        pw.println("</stemmer>");

        pw.println("<index>C:\\Users\\Me\\Desktop\\aila_xu\\target\\index\\fl_index</index>");

        pw.println("<corpus>");
        pw.println("<path>D:\\FileRecv\\Object_casedocs_2020</path>");
        pw.println("<class>txt</class>");
        pw.println("</corpus>");


        pw.println("<stopper>");
        for(String stopword:stopWords){

            pw.println("<word>"+stopword+"</word>");
        }
        pw.println("</stopper>");

        pw.println("</parameters>");
        pw.close();

    }


    public static void main(String[] args ) throws FileNotFoundException{

        String stopWordPath = "D:\\语言模型\\新建文件夹\\exam\\stoplist.dft";
        String paraPath = "C:\\Users\\Me\\Desktop\\aila_xu\\target\\index\\para\\index.para";

        CreateIndexPara c = new CreateIndexPara();
        c.loadStopWord(stopWordPath);
        c.createIndexPara(paraPath);


    }




}

