package use.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;



public class TfIdf {
    static final String PATH = "D:\\FileRecv\\aila20-task1\\dataset\\Object_casedocs";
    // 语料库路径
    public static void main(String[] args) throws Exception {
        String test = "Supreme Court of India";
        // 要计算的候选词
        computeTFIDF(PATH, test);
    }
    /**
     * @param @param path 语料路经
     * @param @param word 候选词
     * @param @throws Exception
     * @return void
     */
    static void computeTFIDF(String path, String word) throws Exception {
        File fileDir = new File(path);
        File[] files = fileDir.listFiles();
        // 每个领域出现候选词的文档数
        Map<String, Integer> containsKeyMap = new HashMap<>();
        // 每个领域的总文档数
        Map<String, Integer> totalDocMap = new HashMap<>();
        // TF = 候选词出现次数/总词数
        Map<String, Double> tfMap = new HashMap<>();
        // scan files
        for (File f : files) {
            // 候选词词频
            double termFrequency = 0;
            // 文本总词数
            double totalTerm = 0;
            // 包含候选词的文档数
            int containsKeyDoc = 0;
            // 词频文档计数
            int totalCount = 0;
            int fileCount = 0;
            // 标记文件中是否出现候选词
            Boolean flag = false;
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String s = "";
            // 计算词频和总词数
            while ((s = br.readLine()) != null) {
                if (s.equals(word)) {
                    termFrequency++;
                    flag = true;
                }
                // 文件标识符
                if (s.equals("$$$")) {
                    if (flag) {
                        containsKeyDoc++;
                    }
                    fileCount++;
                    flag = false;
                }
                totalCount++;
            }
            // 减去文件标识符的数量得到总词数
            totalTerm += totalCount - fileCount;
            br.close();
            // key都为领域的名字
            containsKeyMap.put(f.getName(), containsKeyDoc);
            totalDocMap.put(f.getName(), fileCount);
            tfMap.put(f.getName(), (double) termFrequency / totalTerm);
            System.out.println("----------" + f.getName() + "----------");
            System.out.println("该领域文档数：" + fileCount);
            System.out.println("候选词出现词数：" + termFrequency);
            System.out.println("总词数：" + totalTerm);
            System.out.println("出现候选词文档总数：" + containsKeyDoc);
            System.out.println();
        }
        //计算TF*IDF
        for (File f : files) {
            // 其他领域包含候选词文档数
            int otherContainsKeyDoc = 0;
            // 其他领域文档总数
            int otherTotalDoc = 0;
            double idf = 0;
            double tfidf = 0;
            System.out.println("~~~~~" + f.getName() + "~~~~~");
            Set<Map.Entry<String, Integer>> containsKeyset = containsKeyMap.entrySet();
            Set<Map.Entry<String, Integer>> totalDocset = totalDocMap.entrySet();
            Set<Map.Entry<String, Double>> tfSet = tfMap.entrySet();
            // 计算其他领域包含候选词文档数
            for (Map.Entry<String, Integer> entry : containsKeyset) {
                if (!entry.getKey().equals(f.getName())) {
                    otherContainsKeyDoc += entry.getValue();
                }
            }
            // 计算其他领域文档总数
            for (Map.Entry<String, Integer> entry : totalDocset) {
                if (!entry.getKey().equals(f.getName())) {
                    otherTotalDoc += entry.getValue();
                }
            }
            // 计算idf
            idf = log((float) otherTotalDoc / (otherContainsKeyDoc + 1), 2);
            // 计算tf*idf并输出
            for (Map.Entry<String, Double> entry : tfSet) {
                if (entry.getKey().equals(f.getName())) {
                    tfidf = (double) entry.getValue() * idf;
                    System.out.println("tfidf:" + tfidf);
                }
            }
        }
    }
    static float log(float value, float base) {
        return (float) (Math.log(value) / Math.log(base));
    }
}