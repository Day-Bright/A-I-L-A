package use.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import use.NGram.NGramAnalyzer;
import use.QueryPara.CreateQueryPara;

import java.io.*;
import java.util.HashMap;

public class AnalyzLaw {

    public void readWriteLaw(String fileName,String filesave) throws IOException {

        try{
            File file = new File(fileName);
            File[] tempList = file.listFiles();

            if(tempList == null){
                System.out.println("路径错误or文件夹无文件");
            }else{
                for(File file_list:tempList){
//
                    File f = new File(file_list.getPath());
                    File savefile = new File(filesave+file_list.getName());
                    FileWriter fileWriter = new FileWriter(savefile);
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    String allS="";
                    String s;
                    while((s = br.readLine())!=null){
                        allS+=s;
                    }
                    String analyzeStr = NGramStr(AnalyzeStr(allS));
                    String processString = CreateQueryPara.processString(analyzeStr);
                    fileWriter.write(processString);
                    fileWriter.close();
//                    System.out.println(processString);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static String AnalyzeStr(String allS) throws FileNotFoundException,IOException {

        Analyzer analyzer=new StandardAnalyzer(
                new FileReader(new File("stopwords.txt"))
        );
        StringReader reader=new StringReader(allS);
        TokenStream tokenStream=analyzer.tokenStream("", reader);
        CharTermAttribute term=tokenStream.getAttribute(CharTermAttribute.class);
        tokenStream.reset();
        String QueryString="";
        while(tokenStream.incrementToken()){
            QueryString=term.toString()+" "+QueryString;
        }
        reader.close();
        analyzer.close();
        return QueryString;
    }


    public static String NGramStr(String allS) throws FileNotFoundException,IOException {

        Analyzer analyzer=new NGramAnalyzer();
        StringReader reader=new StringReader(allS);
        TokenStream tokenStream=analyzer.tokenStream("", reader);
        CharTermAttribute term=tokenStream.getAttribute(CharTermAttribute.class);
        tokenStream.reset();
        String QueryString="";
        while(tokenStream.incrementToken()){
            QueryString=term.toString()+" "+QueryString;
        }
        reader.close();
        analyzer.close();
        return QueryString;
    }


    public static void main(String[] args) throws IOException {

        String filePath = "D:\\FileRecv\\aila20-task1\\dataset\\Object_casedocs";
        String savePath = "D:\\FileRecv\\aila20-task1\\dataset\\new\\";


        AnalyzLaw analyzLaw = new AnalyzLaw();
        analyzLaw.readWriteLaw(filePath,savePath);

    }


}
