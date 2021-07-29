package use.NGram;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class WordBreak {


    //英文分词
    public List<String> EWordBreak(String ENstr) throws IOException {
        List<String> EList = new ArrayList<>();
        Analyzer analyzer=new IKAnalyzer(true);
        StringReader reader=new StringReader(ENstr);
        TokenStream tokenStream=analyzer.tokenStream("", reader);
        CharTermAttribute term=tokenStream.getAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while(tokenStream.incrementToken()){
            EList.add(term.toString());
        }
        reader.close();
        analyzer.close();
        return EList;
    }



    public String StandardStr(String QueryStr) throws IOException {

        Analyzer analyzer=new StandardAnalyzer(
                new FileReader(new File("src\\use\\stopwords.txt"))
        );
        StringReader reader=new StringReader(QueryStr);
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


    public List<String> StandardList(String QueryStr) throws IOException {
//        QueryStr = QueryStr.replaceAll("[1234567890]"," ");
        List<String> QueryList = new ArrayList<>();
        Analyzer analyzer=new StandardAnalyzer(
                new FileReader(new File("src\\use\\stopwords.txt"))
        );
        StringReader reader=new StringReader(QueryStr);
        TokenStream tokenStream=analyzer.tokenStream("", reader);
        CharTermAttribute term=tokenStream.getAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while(tokenStream.incrementToken()){
            QueryList.add(term.toString());
        }
        reader.close();
        analyzer.close();
        return QueryList;
    }

    public static void main(String[] args) throws IOException {
        String test_str = "IK Analyzer也支持自定义词典，在IKAnalyzer.cfg.xml同一目录新建ext.dic，把新的词语按行写入文件，编辑IKAnalyzer.cfg.xml把新增的停用词字典写入配置文件，多个字典用空格隔开";
        WordBreak wordBreak = new WordBreak();

    }
}
