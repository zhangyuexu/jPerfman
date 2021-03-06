package com.jperfman.html;

// Autogenerated Jamon implementation
// /opt/kai.zhang/jamon/./ResultPage.jamon

// 2, 2
import com.jperfman.result.ResultAnalysis;
// 3, 2
import java.io.BufferedReader;
// 4, 2
import java.io.File;
// 5, 2
import java.io.FileInputStream;
// 6, 2
import java.io.FileNotFoundException;
// 7, 2
import java.io.IOException;
// 8, 2
import java.io.InputStreamReader;
// 9, 2
import java.util.List;
// 10, 2
import java.util.Map;

public class ResultPageImpl
  extends org.jamon.AbstractTemplateImpl
  implements ResultPage.Intf

{
  private final ResultAnalysis resultAnalysis;
  private final String resultDir;
  protected static ResultPage.ImplData __jamon_setOptionalArguments(ResultPage.ImplData p_implData)
  {
    return p_implData;
  }
  public ResultPageImpl(org.jamon.TemplateManager p_templateManager, ResultPage.ImplData p_implData)
  {
    super(p_templateManager, __jamon_setOptionalArguments(p_implData));
    resultAnalysis = p_implData.getResultAnalysis();
    resultDir = p_implData.getResultDir();
  }
  
  public void renderNoFlush(@SuppressWarnings({"unused","hiding"}) final java.io.Writer jamonWriter)
    throws java.io.IOException
  {
    // 18, 1
    
	String totalTransactions = String.valueOf(resultAnalysis.getTotalTransactions());
	String totalErrors = String.valueOf(resultAnalysis.getTotalErrors());
	String startTime = resultAnalysis.getStartTime();
	String endTime = resultAnalysis.getEndTime();
	String runtime = String.valueOf(resultAnalysis.getRunTime() / 1000);
	String interval = String.valueOf(resultAnalysis.getInterval());

    // 27, 1
    
	File file = new File(resultDir + File.separator + "analyse_max.log");
    String line = null;
    String lastLine = null;
    String use=null, sys=null, idle=null, free=null, buff=null, cache=null, iowait=null, swapsi=null, swapso=null, iobi=null, iobo=null, loadavg=null;
    try {
		FileInputStream isr = new FileInputStream(file);
		InputStreamReader is = new InputStreamReader(isr);
		BufferedReader in = new BufferedReader(is);
		
		while ((line = in.readLine()) != null) {
			lastLine = line;
		}
		String[] cols = lastLine.split("\t");
		use = cols[0];
		sys = cols[1];
		idle = cols[2];
		free = cols[3];
		buff = cols[4];
		cache = cols[5];
		iowait = cols[6];
		swapsi = cols[7];
		swapso = cols[8];
		iobi = cols[9];
		iobo = cols[10];
		loadavg = cols[11];
		in.close();
		is.close();
		isr.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    // 65, 1
    
	Map<String,Double> scriptTimeMap = resultAnalysis.getScriptTimeSummary();
	Double passNum = scriptTimeMap.get("pass_num");
	Double minResp = scriptTimeMap.get("min_resp");
	Double maxResp = scriptTimeMap.get("max_resp");
	Double avgResp = scriptTimeMap.get("avg_resp");
	Double pct80 = scriptTimeMap.get("perc_80");
	Double pct90 = scriptTimeMap.get("perc_90");
	Double pct95 = scriptTimeMap.get("perc_95");
	Double standard = scriptTimeMap.get("standard");
	
	Map<String,Double> userTimeMap = resultAnalysis.getUserTimeSummary();

    // 79, 1
    
	Map<Integer,List<Double>> details = resultAnalysis.getScriptTimeDetail();
	Map<Integer,List<Double>> userDetails = resultAnalysis.getUserTimeDetail();

    // 84, 1
    jamonWriter.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\r\n    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\r\n<head>\r\n    <title>PerfResults</title>\r\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n    <meta http-equiv=\"Content-Language\" content=\"en\" />\r\n    <script src=\"../../themes/performancev1/js/jquery-1.10.1.min.js\"></script>\r\n    <script src=\"../../themes/performancev1/js/perf_report.js\"></script>\r\n    <style type=\"text/css\">\r\n        body {\r\n            background-color: #FFFFFF;\r\n            color: #000000;\r\n            font-family: Microsoft YaHei, sans-serif;\r\n            font-size: 11px;\r\n            padding: 5px;\r\n        }\r\n        h1 {\r\n            font-size: 16px;\r\n            background: #FF9933;\r\n            margin-bottom: 0;\r\n            padding-left: 5px;\r\n            padding-top: 2px;\r\n        }\r\n        h2 {\r\n            font-size: 13px;\r\n            background: #C0C0C0;\r\n            padding-left: 5px;\r\n            margin-top: 2em;\r\n            margin-bottom: .75em;\r\n        }\r\n        h3 {\r\n            font-size: 12px;\r\n            background: #EEEEEE;\r\n            padding-left: 5px;\r\n            margin-bottom: 0.5em;\r\n        }\r\n        h4 {\r\n            font-size: 11px;\r\n            padding-left: 20px;\r\n            margin-bottom: 0;\r\n        }\r\n        p {\r\n            margin: 0;\r\n            padding: 0;\r\n        }\r\n        table {\r\n            margin-left: 10px;\r\n        }\r\n        td {\r\n            text-align: left;\r\n            color: #000000;\r\n            background: #FFFFFF;\r\n            padding-left: 10px;\r\n            padding-right: 10px;\r\n            padding-bottom: 0;\r\n        }\r\n        th {\r\n            text-align: center;\r\n            padding-right: 10px;\r\n            padding-left: 10px;\r\n            color: #000000;\r\n            background: #FFFFFF;\r\n        }\r\n        div.summary {\r\n            padding-left: 20px;\r\n        }\r\n    </style>\r\n</head>\r\n<body>\r\n<h1>\u767e\u5206\u70b9\u6027\u80fd\u6d4b\u8bd5\u62a5\u544a </h1>\r\n<h2>\u6280\u672f\u7814\u53d1\u90e8 </h2>\r\n<div class=\"summary\">\r\n<b>\u603b\u8bf7\u6c42\u6570  :</b> ");
    // 157, 16
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(totalTransactions), jamonWriter);
    // 157, 39
    jamonWriter.write("<br />\r\n<b>\u5f02\u5e38\u6570\u76ee :</b><span id=\"data_analysis_error\"> ");
    // 158, 46
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(totalErrors), jamonWriter);
    // 158, 63
    jamonWriter.write(" </span>  <a href=\"error.log\" target=\"_blank\">\u67e5\u770b\u8be6\u7ec6</a><br />\r\n<b>\u8fd0\u884c\u65f6\u95f4 :</b> ");
    // 159, 15
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(runtime), jamonWriter);
    // 159, 28
    jamonWriter.write(" secs<br />\r\n<b>\u5f00\u59cb\u8fd0\u884c\u65f6\u95f4 :</b> ");
    // 160, 17
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(startTime), jamonWriter);
    // 160, 32
    jamonWriter.write(" <br />\r\n<b>\u7ed3\u675f\u8fd0\u884c\u65f6\u95f4 :</b> ");
    // 161, 17
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(endTime), jamonWriter);
    // 161, 30
    jamonWriter.write("<br />\r\n<b>\u523b\u5ea6 :</b> ");
    // 162, 13
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(interval), jamonWriter);
    // 162, 27
    jamonWriter.write(" secs<br /><br />\r\n<b id=\"case_info\">\u7528\u4f8b\u57fa\u672c\u4fe1\u606f:</b><br /><br />\r\n<table>\r\n<tr><th>\u7ec4\u540d</th><th>\u7ebf\u7a0b\u6570</th></tr>\r\n<tr><td>automation</td><td id=\"data_analysis_threads\">150</td>\r\n</table>\r\n</div>\r\n<h2>\u76d1\u63a7\u670d\u52a1\u5668</h2>\r\n<h3>\u8d44\u6e90\u6570\u636e(\u6700\u5927\u503c)</h3>\r\n<table>\r\n<tr><th>%use</th><th>%sys</th><th>%idle</th><th>%free</th><th>%buff</th><th>%cache</th><th>%iowait</th><th>swapsi</th><th>swapso</th><th>io_bi</th><th>io_bo</th><th>loadevage</th>\r\n<tr><td id=data_analysis_use>");
    // 173, 30
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(use), jamonWriter);
    // 173, 39
    jamonWriter.write("</td>\t\t\t\t\t\t\t   <td id=data_analysis_sys>");
    // 173, 79
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(sys), jamonWriter);
    // 173, 88
    jamonWriter.write("</td>\t\t\t\t\t\t\t   <td id=data_analysis_idle>");
    // 173, 129
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(idle), jamonWriter);
    // 173, 139
    jamonWriter.write("</td>\t\t\t\t\t\t\t   <td id=data_analysis_free>");
    // 173, 180
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(free), jamonWriter);
    // 173, 190
    jamonWriter.write("</td>\t\t\t\t\t\t\t   <td id=data_analysis_buff>");
    // 173, 231
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(buff), jamonWriter);
    // 173, 241
    jamonWriter.write("</td>\t\t\t\t\t\t\t   <td id=data_analysis_cache>");
    // 173, 283
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(buff), jamonWriter);
    // 173, 293
    jamonWriter.write("</td>\t\t\t\t\t\t\t   <td id=data_analysis_iowait>");
    // 173, 336
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(iowait), jamonWriter);
    // 173, 348
    jamonWriter.write("</td>\t\t\t\t\t\t\t   <td id=data_analysis_swapsi>");
    // 173, 391
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(swapsi), jamonWriter);
    // 173, 403
    jamonWriter.write("</td>                                                           <td id=data_analysis_swapso>");
    // 173, 495
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(swapso), jamonWriter);
    // 173, 507
    jamonWriter.write("</td>                                                           <td id=data_analysis_io_bi>");
    // 173, 598
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(iobi), jamonWriter);
    // 173, 608
    jamonWriter.write("</td>                                                           <td id=data_analysis_io_bo>");
    // 173, 699
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(iobo), jamonWriter);
    // 173, 709
    jamonWriter.write("</td>\t\t\t\t\t\t\t   <td id=data_analysis_loadevage>");
    // 173, 755
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(loadavg), jamonWriter);
    // 173, 768
    jamonWriter.write("</td>\r\n</table>\r\n<h2>\u8be6\u7ec6\u6570\u636e\u4fe1\u606f</h2>\r\n<h3>\u4e8b\u52a1\u54cd\u5e94\u65f6\u95f4\uff08\u79d2\uff09</h3>\r\n<table>\r\n<tr><th>count</th><th>min</th><th>avg</th><th>80pct</th><th>90pct</th><th>95pct</th><th>max</th><th>stdev</th></tr>\r\n<tr><td id=\"data_analysis_count\">");
    // 179, 34
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(totalTransactions), jamonWriter);
    // 179, 57
    jamonWriter.write("</td>                            <td id=\"data_analysis_min\">");
    // 179, 117
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",minResp)), jamonWriter);
    // 179, 152
    jamonWriter.write("</td>                            <td id=\"data_analysis_avg\">");
    // 179, 212
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",avgResp)), jamonWriter);
    // 179, 247
    jamonWriter.write("</td>                            <td id=\"data_analysis_80pct\">");
    // 179, 309
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",pct80)), jamonWriter);
    // 179, 342
    jamonWriter.write("</td>                            <td id=\"data_analysis_90pct\">");
    // 179, 404
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",pct90)), jamonWriter);
    // 179, 437
    jamonWriter.write("</td>                            <td id=\"data_analysis_95pct\">");
    // 179, 499
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",pct95)), jamonWriter);
    // 179, 532
    jamonWriter.write("</td>                            <td id=\"data_analysis_max\">");
    // 179, 592
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",maxResp)), jamonWriter);
    // 179, 627
    jamonWriter.write("</td>                            <td id=\"data_analysis_stdev\">");
    // 179, 689
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",standard)), jamonWriter);
    // 179, 725
    jamonWriter.write("</td></tr>\r\n</table>\r\n<h3>\u5e76\u53d1\u7528\u6237\u6570\u5bf9\u5e94TPS\u3001\u54cd\u5e94\u65f6\u95f4\u5206\u6790\u533a\u57df</h3>\r\n<table>\r\n<tr><th>Thread_num</th><th>TPS_passed_avg</th><th>TPS_all_avg</th><th>RespTime_avg:Example_Timer(ms)</th></tr>\r\n");
    // 184, 1
    for (int threadNum : resultAnalysis.getThreadTpsResp().keySet() )
    {
      // 184, 68
      jamonWriter.write("\r\n\t<tr><th>");
      // 185, 10
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(threadNum), jamonWriter);
      // 185, 25
      jamonWriter.write("</th><th>");
      // 185, 34
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(resultAnalysis.getThreadTpsResp().get(threadNum).get(0)), jamonWriter);
      // 185, 95
      jamonWriter.write("</th><th>");
      // 185, 104
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(resultAnalysis.getThreadTpsResp().get(threadNum).get(1)), jamonWriter);
      // 185, 165
      jamonWriter.write("</th><th>");
      // 185, 174
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(resultAnalysis.getThreadTpsResp().get(threadNum).get(2)), jamonWriter);
      // 185, 235
      jamonWriter.write("</th></tr>\r\n");
    }
    // 186, 8
    jamonWriter.write("\r\n\r\n</table>\r\n<h3>\u6570\u636e\u5206\u6790\u533a\u57df(5\u79d2)</h3>\r\n<table id=\"alldata_part\" style=\"display:block\">\r\n<tr><th>interval</th><th>count</th><th>rate</th><th>min</th><th>avg</th><th>80pct</th><th>90pct</th><th>95pct</th><th>max</th><th>stdev</th></tr>\r\n");
    // 192, 1
    
	int rowCnt=30;
	double rateSum=0.0D;
	int rateCalNum=0;

    // 197, 1
    for (int key : details.keySet() )
    {
      // 197, 36
      jamonWriter.write("\r\n");
      // 198, 1
      
	if (rowCnt <= 0) {
		break;
	}
	List<Double> list = details.get(key);
	rowCnt--;

      // 205, 1
      jamonWriter.write("<tr><td>");
      // 205, 9
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(key), jamonWriter);
      // 205, 18
      jamonWriter.write("</td><td>");
      // 205, 27
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%d",list.get(0).intValue())), jamonWriter);
      // 205, 75
      jamonWriter.write("</td><td>");
      // 205, 84
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(1))), jamonWriter);
      // 205, 123
      jamonWriter.write("</td><td>");
      // 205, 132
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(2))), jamonWriter);
      // 205, 171
      jamonWriter.write("</td><td>");
      // 205, 180
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(3))), jamonWriter);
      // 205, 219
      jamonWriter.write("</td><td>");
      // 205, 228
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(4))), jamonWriter);
      // 205, 267
      jamonWriter.write("</td><td>");
      // 205, 276
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(5))), jamonWriter);
      // 205, 315
      jamonWriter.write("</td><td>");
      // 205, 324
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(6))), jamonWriter);
      // 205, 363
      jamonWriter.write("</td><td>");
      // 205, 372
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(7))), jamonWriter);
      // 205, 411
      jamonWriter.write("</td><td>");
      // 205, 420
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(8))), jamonWriter);
      // 205, 459
      jamonWriter.write("</td></tr>\r\n");
    }
    // 206, 8
    jamonWriter.write("\r\n</table>\r\n\r\n<table id=\"alldata_all\" style=\"display:none\">\r\n<tr><th>interval</th><th>count</th><th>rate</th><th>min</th><th>avg</th><th>80pct</th><th>90pct</th><th>95pct</th><th>max</th><th>stdev</th></tr>\r\n");
    // 211, 1
    for (int key : details.keySet() )
    {
      // 211, 36
      jamonWriter.write("\r\n");
      // 212, 1
      
	rateCalNum++;
	List<Double> list = details.get(key);
	rateSum += list.get(1);

      // 217, 1
      jamonWriter.write("<tr><td>");
      // 217, 9
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(key), jamonWriter);
      // 217, 18
      jamonWriter.write("</td><td>");
      // 217, 27
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%d",list.get(0).intValue())), jamonWriter);
      // 217, 75
      jamonWriter.write("</td><td>");
      // 217, 84
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(1))), jamonWriter);
      // 217, 123
      jamonWriter.write("</td><td>");
      // 217, 132
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(2))), jamonWriter);
      // 217, 171
      jamonWriter.write("</td><td>");
      // 217, 180
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(3))), jamonWriter);
      // 217, 219
      jamonWriter.write("</td><td>");
      // 217, 228
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(4))), jamonWriter);
      // 217, 267
      jamonWriter.write("</td><td>");
      // 217, 276
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(5))), jamonWriter);
      // 217, 315
      jamonWriter.write("</td><td>");
      // 217, 324
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(6))), jamonWriter);
      // 217, 363
      jamonWriter.write("</td><td>");
      // 217, 372
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(7))), jamonWriter);
      // 217, 411
      jamonWriter.write("</td><td>");
      // 217, 420
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(8))), jamonWriter);
      // 217, 459
      jamonWriter.write("</td></tr>\r\n");
    }
    // 218, 8
    jamonWriter.write("\r\n</table>\r\n\r\n<a href=\"javascrip:\" id=\"btnswitch1\" onclick=\"show_more()\">\u663e\u793a\u5168\u90e8</a>\r\n<script>\r\nfunction show_more(){\r\nif(document.getElementById(\"btnswitch1\").innerHTML==\"\u663e\u793a\u5168\u90e8\"){\r\ndocument.getElementById(\"alldata_part\").style.display=\"none\";\r\ndocument.getElementById(\"alldata_all\").style.display=\"block\";\r\ndocument.getElementById(\"btnswitch1\").innerHTML=\"\u6536\u8d77\";\r\nreturn;\r\n}\r\nif(document.getElementById(\"btnswitch1\").innerHTML==\"\u6536\u8d77\"){\r\ndocument.getElementById(\"alldata_all\").style.display=\"none\";\r\ndocument.getElementById(\"alldata_part\").style.display=\"block\";\r\ndocument.getElementById(\"btnswitch1\").innerHTML=\"\u663e\u793a\u5168\u90e8\";\r\nreturn;\r\n}\r\n}\r\n</script>\r\n");
    // 238, 1
    
	double rateAvg = 0.0D;
	if (rateCalNum != 0) {
		rateAvg = rateSum / rateCalNum;
	}

    // 244, 1
    jamonWriter.write("<br/>rate\u5e73\u5747\u503c\uff1a<span id='data_analysis_rate_avg'>");
    // 244, 48
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",rateAvg)), jamonWriter);
    // 244, 83
    jamonWriter.write("</span>\r\n<h3>\u62a5\u8868\u533a\u57df</h3>\r\n<h4>\u670d\u52a1\u5668\u8d44\u6e90(CPU): </h4>\r\n<img src=\"TOPCPU.png\" width=\"900\"></img>\r\n<h4>\u670d\u52a1\u5668\u8d44\u6e90(MEM): </h4>\r\n<img src=\"TOPMEM.png\" width=\"900\"></img>\r\n<h4>\u670d\u52a1\u5668\u8d44\u6e90(IO): </h4>\r\n<img src=\"TOPIO.png\" width=\"900\"></img>\r\n<h4>\u670d\u52a1\u5668\u8d44\u6e90(NET): </h4>\r\n<img src=\"TOPNET.png\" width=\"900\"></img>\r\n<h4>\u670d\u52a1\u5668\u8d44\u6e90(SWAP): </h4>\r\n<img src=\"TOPSWAP.png\" width=\"900\"></img>\r\n\r\n");
    // 257, 1
    
	File errNumPic = new File(resultDir + File.separator + "Error_num.png");
	File errConPic = new File(resultDir + File.separator + "Errors_contrast.png");

    // 261, 1
    if (errNumPic.exists() )
    {
      // 261, 27
      jamonWriter.write("\r\n\t<h4>Error\u7edf\u8ba1\u6570\uff1a\u523b\u5ea6\uff085\u79d2\uff09</h4>\r\n\t<img src=\"Error_num.png\" width=\"900\"></img>\r\n");
    }
    // 264, 7
    jamonWriter.write("\r\n");
    // 265, 1
    if (errConPic.exists() )
    {
      // 265, 27
      jamonWriter.write("\r\n\t<h4>Errors\u5bf9\u6bd4\u56fe\uff1a\u523b\u5ea6\uff085\u79d2\uff09</h4>\r\n\t<img src=\"Errors_contrast.png\" width=\"900\"></img>\r\n");
    }
    // 268, 7
    jamonWriter.write("\r\n<h4>\u5e76\u53d1\u7528\u6237\u6570\uff1a\u523b\u5ea6\uff085\u79d2\uff09</h4>\r\n<img src=\"Threads_num.png\" width=\"900\"></img>\r\n<h4>\u54cd\u5e94\u65f6\u95f4\u5206\u5e03\u5206\u6790\u56fe: 5 sec time-series</h4>\r\n<img src=\"All_Transactions_responsetime_linechart.png\" width=\"900\"></img>\r\n<h4>\u54cd\u5e94\u65f6\u95f4: raw data (all points)</h4>\r\n<img src=\"All_Transactions_responsetime_dotchart.png\" width=\"900\"></img>\r\n<h4>\u4e8b\u52a1\u541e\u5410\u91cf-Passed(\u4ec5\u5305\u542b\u65e0error\u7684\u8bf7\u6c42):5 sec time-series</h4>\r\n<img src=\"All_Transactions_TPS_Passed.png\" width=\"900\"></img>\r\n<h4>\u4e8b\u52a1\u541e\u5410\u91cf-All(\u6240\u6709\u7684\u8bf7\u6c42\uff0c\u5305\u62ec\u6709error\u7684\u8bf7\u6c42):5 sec time-series</h4>\r\n<img src=\"All_Transactions_TPS_All.png\" width=\"900\"></img>\r\n<h4>\u4e8b\u52a1\u541e\u5410\u91cf-Contrast(paseed\u4e0eall\u4e24\u79cd\u60c5\u51b5\u7684\u5bf9\u6bd4):5 sec time-series</h4>\r\n<img src=\"All_Transactions_TPS_Contrast.png\" width=\"900\"></img>\r\n<hr />\r\n\r\n<h2>\u8ba1\u65f6\u5668\u533a\u57df: ");
    // 283, 12
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(resultAnalysis.getUserTimer()), jamonWriter);
    // 283, 47
    jamonWriter.write("</h2>\r\n<h3>\u8ba1\u65f6\u5668\u54cd\u5e94\u65f6\u95f4 (\u79d2)</h3>\r\n<table>\r\n<tr><th>count</th><th>min</th><th>avg</th><th>80pct</th><th>90pct</th><th>95pct</th><th>max</th><th>stdev</th></tr>\r\n<tr><td>");
    // 287, 9
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%d",userTimeMap.get("pass_num").intValue())), jamonWriter);
    // 287, 73
    jamonWriter.write("</td><td>");
    // 287, 82
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",userTimeMap.get("min_resp"))), jamonWriter);
    // 287, 137
    jamonWriter.write("</td><td>");
    // 287, 146
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",userTimeMap.get("avg_resp"))), jamonWriter);
    // 287, 201
    jamonWriter.write("</td><td>");
    // 287, 210
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",userTimeMap.get("perc_80"))), jamonWriter);
    // 287, 264
    jamonWriter.write("</td><td>");
    // 287, 273
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",userTimeMap.get("perc_90"))), jamonWriter);
    // 287, 327
    jamonWriter.write("</td><td>");
    // 287, 336
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",userTimeMap.get("perc_95"))), jamonWriter);
    // 287, 390
    jamonWriter.write("</td><td>");
    // 287, 399
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",userTimeMap.get("max_resp"))), jamonWriter);
    // 287, 454
    jamonWriter.write("</td><td>");
    // 287, 463
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",userTimeMap.get("standard"))), jamonWriter);
    // 287, 518
    jamonWriter.write("</td></tr>\r\n</table>\r\n<h3>\u6570\u636e\u5206\u6790\u533a\u57df(5\u79d2)</h3>\r\n<table id=\"");
    // 290, 12
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(resultAnalysis.getUserTimer()), jamonWriter);
    // 290, 47
    jamonWriter.write("_part\" style=\"display:block\">\r\n<tr><th>interval</th><th>count</th><th>rate</th><th>min</th><th>avg</th><th>80pct</th><th>90pct</th><th>95pct</th><th>max</th><th>stdev</th></tr>\r\n");
    // 292, 1
    
	int userRowCnt=30;

    // 295, 1
    for (int key : userDetails.keySet() )
    {
      // 295, 40
      jamonWriter.write("\r\n");
      // 296, 1
      
	if (userRowCnt <= 0) {
		break;
	}
	List<Double> userlist = userDetails.get(key);
	userRowCnt--;

      // 303, 1
      jamonWriter.write("<tr><td>");
      // 303, 9
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(key), jamonWriter);
      // 303, 18
      jamonWriter.write("</td><td>");
      // 303, 27
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%d",userlist.get(0).intValue())), jamonWriter);
      // 303, 79
      jamonWriter.write("</td><td>");
      // 303, 88
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",userlist.get(1))), jamonWriter);
      // 303, 131
      jamonWriter.write("</td><td>");
      // 303, 140
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",userlist.get(2))), jamonWriter);
      // 303, 183
      jamonWriter.write("</td><td>");
      // 303, 192
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",userlist.get(3))), jamonWriter);
      // 303, 235
      jamonWriter.write("</td><td>");
      // 303, 244
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",userlist.get(4))), jamonWriter);
      // 303, 287
      jamonWriter.write("</td><td>");
      // 303, 296
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",userlist.get(5))), jamonWriter);
      // 303, 339
      jamonWriter.write("</td><td>");
      // 303, 348
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",userlist.get(6))), jamonWriter);
      // 303, 391
      jamonWriter.write("</td><td>");
      // 303, 400
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",userlist.get(7))), jamonWriter);
      // 303, 443
      jamonWriter.write("</td><td>");
      // 303, 452
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",userlist.get(8))), jamonWriter);
      // 303, 495
      jamonWriter.write("</td></tr>\r\n");
    }
    // 304, 8
    jamonWriter.write("\r\n\r\n</table>\r\n<table id=\"");
    // 307, 12
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(resultAnalysis.getUserTimer()), jamonWriter);
    // 307, 47
    jamonWriter.write("_all\" style=\"display:none\">\r\n<tr><th>interval</th><th>count</th><th>rate</th><th>min</th><th>avg</th><th>80pct</th><th>90pct</th><th>95pct</th><th>max</th><th>stdev</th></tr>\r\n");
    // 309, 1
    for (int key : userDetails.keySet() )
    {
      // 309, 40
      jamonWriter.write("\r\n");
      // 310, 1
      
	List<Double> list = userDetails.get(key);

      // 313, 1
      jamonWriter.write("<tr><td>");
      // 313, 9
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(key), jamonWriter);
      // 313, 18
      jamonWriter.write("</td><td>");
      // 313, 27
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%d",list.get(0).intValue())), jamonWriter);
      // 313, 75
      jamonWriter.write("</td><td>");
      // 313, 84
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(1))), jamonWriter);
      // 313, 123
      jamonWriter.write("</td><td>");
      // 313, 132
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(2))), jamonWriter);
      // 313, 171
      jamonWriter.write("</td><td>");
      // 313, 180
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(3))), jamonWriter);
      // 313, 219
      jamonWriter.write("</td><td>");
      // 313, 228
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(4))), jamonWriter);
      // 313, 267
      jamonWriter.write("</td><td>");
      // 313, 276
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(5))), jamonWriter);
      // 313, 315
      jamonWriter.write("</td><td>");
      // 313, 324
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(6))), jamonWriter);
      // 313, 363
      jamonWriter.write("</td><td>");
      // 313, 372
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(7))), jamonWriter);
      // 313, 411
      jamonWriter.write("</td><td>");
      // 313, 420
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%.3f",list.get(8))), jamonWriter);
      // 313, 459
      jamonWriter.write("</td></tr>\r\n");
    }
    // 314, 8
    jamonWriter.write("\r\n</table>\r\n<a href=\"javascrip:\" id=\"btnswitch_Example_Timer\" onclick=\"show_more_Example_Timer()\">\u663e\u793a\u5168\u90e8</a>\r\n<script>\r\nfunction show_more_Example_Timer(){\r\nif(document.getElementById(\"btnswitch_Example_Timer\").innerHTML==\"\u663e\u793a\u5168\u90e8\"){\r\ndocument.getElementById(\"Example_Timer_part\").style.display=\"none\";\r\ndocument.getElementById(\"Example_Timer_all\").style.display=\"block\";\r\ndocument.getElementById(\"btnswitch_Example_Timer\").innerHTML=\"\u6536\u8d77\";\r\nreturn;\r\n}\r\nif(document.getElementById(\"btnswitch_Example_Timer\").innerHTML==\"\u6536\u8d77\"){\r\ndocument.getElementById(\"Example_Timer_all\").style.display=\"none\";\r\ndocument.getElementById(\"Example_Timer_part\").style.display=\"block\";\r\ndocument.getElementById(\"btnswitch_Example_Timer\").innerHTML=\"\u663e\u793a\u5168\u90e8\";\r\nreturn;\r\n}\r\n}\r\n</script>\r\n<h3>\u62a5\u8868\u533a\u57df</h3>\r\n<h4>\u54cd\u5e94\u65f6\u95f4\u5206\u5e03\u5206\u6790\u56fe: 5 sec time-series</h4>\r\n<img src=\"");
    // 335, 11
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(resultAnalysis.getUserTimer()), jamonWriter);
    // 335, 46
    jamonWriter.write("_responsetime_linechart.png\" width=\"900\"></img>\r\n<h4>\u54cd\u5e94\u65f6\u95f4: raw data (all points)</h4>\r\n<img src=\"");
    // 337, 11
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(resultAnalysis.getUserTimer()), jamonWriter);
    // 337, 46
    jamonWriter.write("_responsetime_dotchart.png\" width=\"900\"></img>\r\n<h4>\u4e8b\u52a1\u541e\u5410\u91cf-Passed(\u4ec5\u5305\u542b\u65e0error\u7684\u8bf7\u6c42):5 sec time-series</h4>\r\n<img src=\"");
    // 339, 11
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(resultAnalysis.getUserTimer()), jamonWriter);
    // 339, 46
    jamonWriter.write("_TPS_Passed.png\" width=\"900\"></img>\r\n<h4>\u4e8b\u52a1\u541e\u5410\u91cf-All(\u6240\u6709\u7684\u8bf7\u6c42\uff0c\u5305\u62ec\u6709error\u7684\u8bf7\u6c42):5 sec time-series</h4>\r\n<img src=\"");
    // 341, 11
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(resultAnalysis.getUserTimer()), jamonWriter);
    // 341, 46
    jamonWriter.write("_TPS_All.png\" width=\"900\"></img>\r\n<h4>\u4e8b\u52a1\u541e\u5410\u91cf-Contrast(paseed\u4e0eall\u4e24\u79cd\u60c5\u51b5\u7684\u5bf9\u6bd4):5 sec time-series</h4>\r\n<img src=\"");
    // 343, 11
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(resultAnalysis.getUserTimer()), jamonWriter);
    // 343, 46
    jamonWriter.write("_TPS_Contrast.png\" width=\"900\"></img>\r\n<hr />\r\n</body>\r\n</html>\r\n");
  }
  
  
}
