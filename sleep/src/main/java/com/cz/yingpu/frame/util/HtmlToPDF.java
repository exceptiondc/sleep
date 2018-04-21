package com.cz.yingpu.frame.util;


import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;

import java.awt.*;
import java.io.*;
import java.net.URL;

/**
 * Created by Administrator on 2017/6/19 0019.
 */
public class HtmlToPDF {
    protected int topValue = 10;
    protected int leftValue = 20;
    protected int rightValue = 10;
    protected int bottomValue = 10;
    protected int userSpaceWidth = 1300;

    // 手动构造HTML代码
    public static void generatePDF_1(File outputPDFFile, StringReader strReader) throws Exception {
        FileOutputStream fos = new FileOutputStream(outputPDFFile);
        PD4ML pd4ml = new PD4ML();
        
        pd4ml.setPageInsets(new Insets(20, 10, 10, 10));
        pd4ml.setHtmlWidth(950);
        pd4ml.setPageSize(pd4ml.changePageOrientation(PD4Constants.A4));
        pd4ml.useTTF("D:\\fonts", true);
        pd4ml.setDefaultTTFs("KaiTi_GB2312", "KaiTi_GB2312", "KaiTi_GB2312");
        pd4ml.enableDebugInfo();
        pd4ml.render(strReader, fos);
    }

    // 将HTML文件转换成PDF
    public static void generatePDF_2(File outputPDFFile, String inputHTMLFileName) throws Exception {
        FileOutputStream fos = new FileOutputStream(outputPDFFile);
        PD4ML pd4ml = new PD4ML();
        pd4ml.setPageInsets(new Insets(20, 10, 10, 10));
        pd4ml.setHtmlWidth(950);
        pd4ml.setPageSize(pd4ml.changePageOrientation(PD4Constants.A4));
//        pd4ml.useTTF("java:fonts", true);
//        String path = request.getSession().getServletContext().getRealPath("");
        pd4ml.useTTF("C:\\Users\\Administrator\\Desktop\\pd4\\fonts", true);
        pd4ml.setDefaultTTFs("KaiTi_GB2312", "KaiTi_GB2312", "KaiTi_GB2312");
        pd4ml.enableDebugInfo();
        pd4ml.render("file:" + inputHTMLFileName, fos);
    }

    public  void doConversion( String urlstring, String outputPath ) throws Exception {
        URL url = new URL(urlstring);
        url.openConnection();
        File output = new File(outputPath);
        java.io.FileOutputStream fos = new java.io.FileOutputStream(output);

        PD4ML pd4ml = new PD4ML();

        pd4ml.setHtmlWidth(userSpaceWidth); // set frame width of "virtual web browser"

        // choose target paper format and "rotate" it to landscape orientation
        pd4ml.setPageSize(pd4ml.changePageOrientation(PD4Constants.A4));

        // de fine PDF page margins
        pd4ml.setPageInsetsMM(new Insets(topValue, leftValue, bottomValue, rightValue));

        // source HTML document also may have margins, could be suppressed this way
        // (PD4ML *Pro* feature):
        pd4ml.addStyle("BODY {margin: 0}", true);

        // If built-in basic PDF fonts are not sufficient or
        // if you need to output non-Latin texts,
        // TTF embedding feature should help (PD4ML *Pro*)
        pd4ml.useTTF("C:\\Users\\Administrator\\Desktop\\pd4\\fonts", true);
        pd4ml.setDefaultTTFs("KaiTi_GB2312", "KaiTi_GB2312", "KaiTi_GB2312");
        pd4ml.render(url, fos); // actual document conversion from URL to file
        fos.close();

        System.out.println( outputPath + "\ndone." );
    }

    /**
     * 通过读取HTML生成PDF
     * @param realName 真实姓名
     * @param idcard 身份证号
     * @param number 合同编号
     * @param yfname 乙方名称
     * @param yfidcard 乙方身份证
     * @param productnumber 项目编号
     * @param xxbj 小写本金
     * @param dxbj 大写本金
     * @param nhlv 年化利率
     * @param hkfs 还款方式
     * @param bjh 本息和
     * @param starttime 开始时间
     * @param endtime  结束时间
     * @param time 页面地址
     * @param outputFile PDF输出地址
     * @throws Exception
     */
    public static void createPDF(String realName,String idcard,String number,
    		String yfname,String yfidcard,String productnumber,String xxbj,String dxbj,String nhlv,
    		String hkfs,String bjh,String starttime,String endtime,String time,String protocolTemplate,String inputFile,String outputFile)throws Exception {
        File file=new File(inputFile);
        InputStreamReader fis = new InputStreamReader(new FileInputStream(file), "utf-8");// 创建文件输入流
        char[] data = new char[1024];// 创建缓冲字符数组
        int rn = 0;
        StringBuilder sb = new StringBuilder();// 创建字符串构建器
        while ((rn = fis.read(data)) > 0) {// 读取文件内容到字符串构建器
            String str = String.valueOf(data, 0, rn);
            sb.append(str);
        }
        fis.close();// 关闭输入流
        // 从构建器中生成字符串，并替换搜索文本
      String str = sb.toString();
       
        String[] kvs = {
        		"jiafang", realName, "jfshenfenzheng", idcard, "bianhao", number, "yifang", yfname,
        		"yfshenfenzheng", yfidcard, "productno", productnumber, "bjxx", xxbj, "bjdx", dxbj,
        		"nhll", nhlv, "hkfs", hkfs, "dqyh", bjh, "starttime", starttime, "endtime", endtime,
        		"lkrq", time
        };

        for (int i = 0; i < kvs.length; i += 2) {
        	String key = kvs[i];
        	String val = kvs[i + 1];
        	str = str.replaceAll("<span id=\"" + key + "\">([^<>]*)</span>", val);
        }
 
        StringReader strReader = new StringReader(str);
        generatePDF_1(new File(outputFile), strReader);
    }



    public static void main(String[] args) throws Exception {
        HtmlToPDF htmlToPDF = new HtmlToPDF();
//        htmlToPDF.generatePDF_2(new File("C:\\Users\\Administrator\\Desktop\\pd4.pdf"),"C:\\Users\\Administrator\\Desktop\\template.html");
//        htmlToPDF.doConversion("C:\\\\Users\\\\Administrator\\\\Desktop\\\\news.html","C:\\Users\\Administrator\\Desktop\\pd5.pdf");
        File file=new File("C:\\Users\\Administrator\\Desktop\\template.html");
        FileReader fis = new FileReader(file);// 创建文件输入流
        char[] data = new char[1024];// 创建缓冲字符数组
        int rn = 0;
        StringBuilder sb = new StringBuilder();// 创建字符串构建器
        while ((rn = fis.read(data)) > 0) {// 读取文件内容到字符串构建器
            String str = String.valueOf(data, 0, rn);
            sb.append(str);
        }
        fis.close();// 关闭输入流
        String str = sb.toString();
        String[] kvs = {
        		"jiafang", "jfshenfenzheng", "bianhao", "yifang",
        		"yfshenfenzheng", "productno", "bjxx", "bjdx",
        		"nhll", "hkfs", "dqyh", "starttime", "endtime",
        		"lkrq"
        };
		
		
		for (int i = 0; i < kvs.length; i += 1) {
			String key = kvs[i];
			str = str.replaceAll("<span id=\"" + key + "\">([^<>]*)</span>", "asdfasdfas");
		}
		
	       
        StringReader strReader = new StringReader(str);
        generatePDF_1(new File("C:\\Users\\Administrator"),strReader);
    }
}
