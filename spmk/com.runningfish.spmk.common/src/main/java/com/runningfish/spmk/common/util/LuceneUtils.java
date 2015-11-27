package com.runningfish.spmk.common.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeFilter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.runningfish.spmk.common.LuceneInfo;

public class LuceneUtils
{
    //私有常量仅供类内部使用
    private final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static Integer TOTAL_HITS = Integer.MAX_VALUE;
    
    
    /** 
     * 获得indexwriter对象 
     * @param dir 
     * @throws IOException 
     */  
    private static IndexWriter getIndexWriter(Directory dir, Analyzer analyzer) throws IOException {  
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_42, analyzer);
        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
        
        return new IndexWriter(dir, iwc);  
    }
    
    /** 
     * 关闭indexwriter对象 
     * @throws IOException 
     */  
    private static void closeWriter(IndexWriter indexWriter) throws IOException {  
        if (indexWriter != null) {  
            indexWriter.close();  
            //indexWriter = null;
        }
    }
    
    /** 
     * 创建索引
     * @param path 建立索引文件的目录
     * @param info 要建立索引的对象
     * @return true:创建成功   false: 创建失败
     */
    public static boolean createIndex(String path, LuceneInfo info){   
        @SuppressWarnings("deprecation")
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
        
        IndexWriter indexWriter = null;  
        Directory directory = null;  
        
        try {
            if (path == null || "".equals(path.trim()))
            {
                return false;
            }
            
            //文件目录不存在自动创建目录
            File file = new File(path);
            if (!file.exists())
            {
               boolean result = file.mkdirs();
               if (!result)
               {
                   return false;    
               }
            }
            
            //打开索引文件
            directory = FSDirectory.open(file);  
            indexWriter = getIndexWriter(directory, analyzer); 

            // 添加索引  
            if(info.getGrade()==0)
            {
                info.setGrade(5);
            }
            info.setContent(Html2TextUtils.html2Text(info.getContent()));
            Document document = new Document();
            document.add(new StringField("docId", info.getId() + "_" + info.getModuleId() + "_" + info.getSubModuleId(), Store.YES));            
            document.add(new StringField("moduleId", String.valueOf(info.getModuleId()), Store.YES));
            document.add(new IntField("grade", info.getGrade(), Store.YES));
            document.add(new TextField("title", info.getTitle(), Store.YES));
            document.add(new StringField("url", info.getUrl()==null ? "" : info.getUrl(), Store.YES));             
            document.add(new TextField("content", info.getContent() == null ? "" : info.getContent(), Store.YES));  
            document.add(new StringField("date", formatDate(info.getDate()) , Store.YES));              
            indexWriter.addDocument(document);   
            indexWriter.commit(); 
        } catch (IOException e) {  
            e.printStackTrace();
            return false;
        }  catch (Exception e) {  
            e.printStackTrace();
            return false;
        }  finally {
            try
            {
                closeWriter(indexWriter);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        return true;
    }
    
    /** 
     * 更新索引
     * @param path 建立索引文件的目录
     * @param info 要更新索引的对象
     * @return true:更新成功   false: 更新失败
     */
    public static boolean updateIndex(String path, LuceneInfo info){  
        deleteIndex(path, info.getId(),info.getModuleId(),info.getSubModuleId(),info.getGrade());
        return createIndex(path, info);
    }
    
    /** 
     * 删除索引
     * @param path 索引文件路径
     * @param recordId 记录ID
     * @param moduleId 模块ID 
     * @return true:删除成功 false:删除失败 
     */
    public static boolean deleteIndex(String path, String recordId, Integer moduleId,Integer grade) {  
        return deleteIndex(path , recordId, moduleId, 1,grade);
    }
    
    /** 
     * 删除索引 
     * @param path 索引文件路径
     * @param recordId 记录ID
     * @param subModuleId 子模块ID 
     * @return true:删除成功 false:删除失败 
     */
    public static boolean deleteIndex(String path, String recordId, String subModuleId,String grade){  
        return deleteIndex(path , recordId, 1, Integer.parseInt(subModuleId),Integer.parseInt(grade));
    }
    
    /**
     * 私有方法
     * @param path 索引文件路径
     * @param recordId 记录ID
     * @param moduleId 模块ID 
     * @param subModuleId 子模块ID
     * @return true:删除成功 false:删除失败 
     */
    private static boolean deleteIndex(String path, String recordId, Integer moduleId, Integer subModuleId,Integer grade) {  
        try
        {
            if (path == null || "".equals(path.trim()))
            {
                return false;
            }
            
            //文件目录不存在自动创建目录
            File file = new File(path);
            if (!file.exists())
            {
                boolean result = file.mkdirs();
                if (!result)
                {
                    return false;    
                }
            }
            
            @SuppressWarnings("deprecation")
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30); 
      
            //打开索引文件
            Directory directory = FSDirectory.open(file);  
            IndexWriter indexWriter = getIndexWriter(directory, analyzer); 
            Term term = new Term("docId" , recordId + "_" + moduleId + "_" + subModuleId); 
            indexWriter.deleteDocuments(term);

            indexWriter.commit(); 
            closeWriter(indexWriter);
        } catch (IOException e)
        {
            e.printStackTrace();
            
            return false;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    /** 
     * 搜索
     * @param path 建立索引文件的目录
     * @param keyword 搜索关键字
     * @param pages 第几页,序号从1开始
     * @param rows 每页显示几条数据
     * @throws Exception 
     */  
    @SuppressWarnings("deprecation")  
    public static Map<String, Object> searchIndex(String path, String keyword, Integer page, Integer rows,Integer grade,Integer getModuleId) throws Exception {  
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", 0);
        
        // 默认IKAnalyzer()-false:实现最细粒度切分算法,true:分词器采用智能切分  
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);  
        
        if (path == null || "".equals(path.trim()))
        {
            return map;
        }
        
        //文件目录不存在自动创建目录
        File file = new File(path);
        if (!file.exists())
        {
            boolean result = file.mkdirs();
            if (!result)
            {
                return map;    
            }
        }
        
        Directory directory = FSDirectory.open(file);  
        
        IndexReader ireader = IndexReader.open(directory);  
        IndexSearcher isearcher = new IndexSearcher(ireader);  
        
        String[] fields = { "title", "content"}; 
        String[] fields2 = { "moduleId"}; 
        BooleanQuery query = new BooleanQuery(); 
        // 使用QueryParser查询分析器构造Query对象
        QueryParser qp1 = new MultiFieldQueryParser(Version.LUCENE_42, fields, analyzer);
        // 搜索相似度最高的TOTAL_HITS条记录        
        qp1.setDefaultOperator(QueryParser.AND_OPERATOR);
        Query query1 = qp1.parse(keyword);        
        QueryParser qp2 = new MultiFieldQueryParser(Version.LUCENE_42, fields2, analyzer);
        qp2.setDefaultOperator(QueryParser.AND_OPERATOR);
        Query query2 = qp2.parse(String.valueOf(getModuleId));
        query.add(new BooleanClause(query1, BooleanClause.Occur.SHOULD));
        query.add(new BooleanClause(query2, BooleanClause.Occur.MUST_NOT));
        NumericRangeFilter<Integer> filter=NumericRangeFilter.newIntRange("grade", grade, 5,true, true);           
        
        TopDocs topDocs = isearcher.search(query,filter,TOTAL_HITS);
//        TopDocs topDocs = isearcher.search(query,TOTAL_HITS);
        map.put("total", topDocs.totalHits);
        if (topDocs.totalHits > 0)
        {
            //分页，高亮显示 
            Integer startIndex = (page - 1 ) * rows ;
            map.put("list" , higherIndex(analyzer, isearcher, query,filter, topDocs, startIndex, rows));
        }
        
        return map;
    }
  
    /** 
     * 分页，高亮显示 
     * @param analyzer 
     * @param isearcher 
     * @param query 
     * @param topDocs 
     * @param startIndex 下标值
     * @param rows 每页显示几条数据
     * @throws Exception 
     */  
    private static List<LuceneInfo> higherIndex(Analyzer analyzer, IndexSearcher isearcher, Query query,Filter filter,TopDocs topDocs, Integer startIndex, Integer rows) throws Exception {  
        //返回结果
        List<LuceneInfo> list = new ArrayList<LuceneInfo>();
        LuceneInfo info = null;
        
        TopScoreDocCollector results = TopScoreDocCollector.create(topDocs.totalHits, false);
        isearcher.search(query, filter,results);  
        
        //分页取出指定的doc(开始条数, 取几条)  
        ScoreDoc[] docs = results.topDocs(startIndex, rows).scoreDocs;  
        
        // 关键字高亮显示的html标签
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color=#cf98232>", "</font>");  
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));  
        
        for (int i = 0; i < docs.length; i++) {
            Document doc = isearcher.doc(docs[i].doc);
            //获取文档主键
            String docId = doc.get("docId");
            String []values = docId.split("_");
            
            //获取ID
            String strId = values[0];
            
            //获取模块ID
            String strModuleId = values[1];
            
            //获取子模块ID
            String strSubModuleId = values[2];
            
            String grade = doc.get("grade");
            
            info = new LuceneInfo(strId, parseInt(strModuleId),parseInt(strSubModuleId),parseInt(grade));
            
            //网站地址url
            String url = doc.get("url");
            info.setUrl(url);
            
            //标题增加高亮显示 
            String strTitle = doc.get("title");
            TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(strTitle));  
            String title = highlighter.getBestFragment(tokenStream, strTitle);
            info.setTitle(title);
            if (title==null)
            {
                info.setTitle(strTitle);
            }
            
            //内容高亮显示 
            String content = doc.get("content");
            tokenStream = analyzer.tokenStream("content", new StringReader(content));  
            String con = highlighter.getBestFragment(tokenStream, content);
            info.setContent(con);
            if (con==null)
            {
              info.setContent(content);
            }
               
            //时间
            String strDate = doc.get("date");
            info.setDate(parseDate(strDate));

            list.add(info);
        }
        
        return list;
    }
    
    /**
     * @param number 字符串数字
     * @return
     */
    private static Integer parseInt(String number)
    {
        try
        {
            return Integer.parseInt(number);
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            return -1;
        }
    }
    
    /**
     * @param date
     * @param pattern
     * @return
     */
    private static String formatDate(Date date)
    {
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
            return dateFormat.format(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * @param date
     * @param pattern
     * @return
     */
    private static Date parseDate(String date)
    {
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
            return dateFormat.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
}

