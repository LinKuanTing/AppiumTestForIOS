package com.example.wghtest.Level3.Advice;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class TestCaseAdvice extends WDFnL3Advice {

    private static String __strFileName;
    private static String __strFilePath;

    Logger logger;


    public TestCaseAdvice(){
        logger = LoggerFactory.getLogger(TestCaseAdvice.class);

    }


    public void CheckBrowseAI() throws ParseException, InterruptedException {

        iosDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);


        while (true){
            Thread.sleep(10000);
            if (new WDFnL3AdviceBrowse().isAIRecordExist()){
                break;
            }else {
                clickBack();
                new WDFnL2TitleMenuTabbar().clickMenuAdvice();
            }
        }


        //確認測量記錄大小不為０
        if (alMeasureTime.size() == 0){
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't get measure time with weight or BP ...and so on");
            return;
        }

/*
        //修改alMeasureTime裡的日期格式 (與線上指導格式相同
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mma", Locale.ENGLISH);
        for (int i=0; i<alMeasureTime.size(); i++){
            //String轉為Date()
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String strTime = alMeasureTime.get(i).toString();
            Date date = sdf.parse(strTime);
            strTime = dateFormat.format(date);
            alMeasureTime.set(i,strTime);
        }
        //System.out.println(alMeasureTime);
*/



        int sameNum = 0;
        for (int i=0; i<alMeasureTime.size(); i++){
            //[0] time   [1] item
            String[] strData = alMeasureTime.get(i).toString().split(",");

            //System.out.println(alMeasureTime.get(i).toString());

            //變更日期格式
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mma", Locale.ENGLISH);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = sdf.parse(strData[0]);
            strData[0] = dateFormat.format(date);
            //[0] yyyy-MM-dd   [1] hh:mma
            String strDate[] = strData[0].split(" ");


            //確認alMeasureTime資料 是否出現在Advice內
            WDFnL3AdviceBrowse wdFnL3AdviceBrowse = new WDFnL3AdviceBrowse();
            if (wdFnL3AdviceBrowse.isExistBrowse(strDate[0],strDate[1]+strData[1]) == true){
                sameNum++;
            }

        }

        if (sameNum >= alMeasureTime.size()){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Total measure data "+alMeasureTime.size()+" records, but only find "+sameNum+" records");

        }
    }


    public void CheckStarComment() throws InterruptedException {
        WDFnL3AdviceBrowse wdFnL3AdviceBrowse = new WDFnL3AdviceBrowse(1);
        wdFnL3AdviceBrowse.clickBrowseIdx();

        wdFnL3AdviceBrowse.clickBtnStar();

        wdFnL3AdviceBrowse.clickBackToAdvice();

        String strStarLevel = wdFnL3AdviceBrowse.getStarText();

        //星級評論為５
        if (strStarLevel.equals("5")){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Fail to click star comment");
        }
    }


    public void CheckServices() throws IOException, InterruptedException {
        __strFileName = "services_en";

        new WDFnL3AdviceServices().clickMore();

        Thread.sleep(8000);
        Set contextWebview = iosDriver.getContextHandles();
        //System.out.println(contextWebview);

        //switch to webview and get html
        iosDriver.context((String) contextWebview.toArray()[contextWebview.size()-1]);
        Document doc = Jsoup.parse(iosDriver.getPageSource());
        iosDriver.context((String) contextWebview.toArray()[0]);

        //消除HTML標籤  只取文字部分
        String strWebviewContent = doc.text();

        //取得預期以較資料
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
        String strExpectContent = "";
        while (br.ready()){
            strExpectContent += br.readLine();
        }



        doc = Jsoup.parse(strExpectContent);
        strExpectContent = doc.text();
        //System.out.println(strExpectContent);

        if(strExpectContent.equals(strWebviewContent)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Webview contents are different.");
            System.out.println(strExpectContent);
            System.out.println(strWebviewContent);
        }

        new WDFnL3AdviceServices().clickBack();
    }

}
