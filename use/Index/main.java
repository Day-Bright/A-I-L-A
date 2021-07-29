package use.Index;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.LMDirichletSimilarity;
import org.apache.lucene.search.similarities.LMJelinekMercerSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import use.NGram.NGramAnalyzer;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class main {

    public void createIndex(String cretePath,String task1Path) throws IOException {

        Directory directory = FSDirectory.open(Paths.get(cretePath));
        Analyzer analyzer = new StandardAnalyzer();
//        Analyzer analyzer = new NGramAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);

        try {
            File file = new File(task1Path);
            File[] tempList = file.listFiles();
            if (tempList == null) {
                System.out.println("路径错误or文件夹无文件");
            } else {
                for (File file_list : tempList) {
                    File f = new File(file_list.getPath());
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    StringBuilder stringBuffer = new StringBuilder();
                    String s;
                    while ((s=br.readLine())!=null){
                        stringBuffer.append(s);
                    }
                    String new_String = stringBuffer.toString();
                    new_String = new_String.replaceAll("[^a-zA-Z0-9]", " ");  //去除数字，英文，汉字  之外的内容
                    new_String = new_String.replaceAll("OR"," ").replaceAll("or"," ")
                            .replaceAll("NOT"," ").replaceAll("not"," ")
                            .replaceAll("AND"," ").replaceAll("and"," ");
                    new_String=new_String.replaceAll(" +"," ");
                    Document document = new Document();
                    String filename = file_list.getName().replace(".txt","");
//                    System.out.println(writter);
                    document.add(new TextField("content",new_String,Field.Store.YES));
                    document.add(new TextField("filename",filename,Field.Store.YES));
                    indexWriter.addDocument(document);
                    br.close();
                }
                indexWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void query(String saveIndexPath,String scoreFile) throws IOException, ParseException {

        ReadFile readFile =new ReadFile();
        File file = new File(scoreFile);
//        FileWriter fileWriter=new FileWriter(file.getName(),true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
        Directory directory = FSDirectory.open(Paths.get(saveIndexPath));
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        indexSearcher.setSimilarity(new LMJelinekMercerSimilarity((float)0.999999));
//        indexSearcher.setSimilarity(new LMDirichletSimilarity());
        BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
        HashMap<String, String> queryMap = readFile.read_f();
        for(Map.Entry<String, String> entry : queryMap.entrySet()){
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            mapValue = mapValue.replaceAll("[^a-zA-Z0-9]", " ");  //去除数字，英文，汉字  之外的内容
            mapValue = mapValue.replaceAll("OR"," ").replaceAll("or"," ")
                    .replaceAll("NOT"," ").replaceAll("not"," ")
                    .replaceAll("AND"," ").replaceAll("and"," ");
            mapValue=mapValue.replaceAll(" +"," ");
            QueryParser queryParser = new QueryParser("content",new StandardAnalyzer());
            Query query = queryParser.parse(mapValue);
            TopDocs topDocs = indexSearcher.search(query,3000);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            int rank = 1;
            for(ScoreDoc scoreDoc : scoreDocs) {
                int doc = scoreDoc.doc;
                Document document = indexSearcher.doc(doc);
                String queryFileName = document.get("filename");
                double score = scoreDoc.score;
                rank++;
                String writeStr = mapKey+" Q0 "+queryFileName+" "+rank+" "+score+" 22"+"\n";
//                System.out.println(writeStr);
                bw.write(writeStr);
            }
        }
        bw.close();
    }


    public static void main(String[] args) throws IOException, ParseException {

        String scoreFilePath = "D:\\FileRecv\\aila20-task1\\relevance_judgements\\22.txt"; //结果文件路径，随便填
        String savePath = "C:\\Users\\Me\\Desktop\\task1a_index_22";//索引文件路径，随便填
        String tagPath ="D:\\FileRecv\\aila20-task1\\dataset\\Object_statutes";
        main m = new main();
        m.createIndex(savePath,tagPath);
        m.query(savePath,scoreFilePath);
    }

}
