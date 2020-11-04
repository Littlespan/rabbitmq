import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author 作者：hyh
 * @version v.1.0 创建时间：2020/11/4 16:19
 * @email 邮箱：15205698133@163.com
 * @description 描述：
 */
public class Test1 {
    String[] a = {
            "3, 华为 - 华为电脑, 爆款",
            "4, 华为手机, 旗舰",
            "5, 联想 - Thinkpad, 商务本",
            "6, 联想手机, 自拍神器"
    };

    @Test
    public void test1() throws IOException {
        //存储索引文件的路径
        File path = new File("f:/lucene/");
        FSDirectory d = FSDirectory.open(path.toPath());
        //lucene提供的中文分词器
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        //通过配置对象来指定分词器
        IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
        //索引输出工具
        IndexWriter writer = new IndexWriter(d, cfg);

        for (String s : a) {
            String[] strs = s.split(",");

            //创建文档,文档中包含的是要索引的字段
            Document doc = new Document();
            doc.add(new LongPoint("id", Long.parseLong(strs[0])));
            doc.add(new StoredField("id", Long.parseLong(strs[0])));
            doc.add(new TextField("title", strs[1], Store.YES));
            doc.add(new TextField("sellPoint", strs[2], Store.YES));

            //将文档写入磁盘索引文件
            writer.addDocument(doc);
        }
        writer.flush();
        writer.close();
    }

    @Test
    public void test2() throws IOException {
        //文件夹
        FSDirectory d = FSDirectory.open(new File("f:/lucene").toPath());
        //读取器工具
        DirectoryReader reader = DirectoryReader.open(d);
        //创建搜索器工具
        IndexSearcher searcher = new IndexSearcher(reader);
        //封装搜索的关键词
        String s1 = new Scanner(System.in).nextLine();
        TermQuery query = new TermQuery(new Term("title", s1));
        //用搜索器执行搜索并得到结果： 排序的文档列表[{index:2},{index:0},{index:1}]
        TopDocs docs = searcher.search(query, 20);
        for(ScoreDoc s :docs.scoreDocs){
            Document doc = searcher.doc(s.doc);
            System.out.println(doc.get("id"));
            System.out.println(doc.get("title"));
            System.out.println(doc.get("sellPoint"));
            System.out.println("--------------");
        }
    }
}
