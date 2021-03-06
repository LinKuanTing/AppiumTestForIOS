package com.example.wghtest.Level3.BloodGlucose;

import com.example.wghtest.other.FnFileEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import io.appium.java_client.ios.IOSElement;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class TestCaseBloodGlucose extends WDFnL3BloodGlucose {

    private String __strFileName;
    private String __strFilePath;

    Logger logger;

    public TestCaseBloodGlucose(){
        logger = LoggerFactory.getLogger(TestCaseBloodGlucose.class);
    }


    public void LoadRecord(){
        try {
            for (int i=1; i<=10; i++){
                clickRecord();
                try {
                    new WDFnL3BloodGlucoseRecord(1);
                    logger.info(strPassMsg);
                    new WDFnL3BloodGlucoseRecord().clickBack();
                    return;
                }catch (Exception eWeakNetwork){
                    if (i==10){
                        logger.warn(strFailMsg);
                        logger.debug("Reason: repeat click record to load data fail with 10 times");
                    }
                    new WDFnL3BloodGlucoseRecord().clickBack();
                }
            }

        }catch (Exception eTimeOut){
            logger.warn(strFailMsg);
            logger.debug("Reason: Time out for load record");
        }
    }

    public void AddRecord(){
        String strDate;

        try {
            strDate = getDate();

            setValue();

            //推波測試所需資料 (由於測試推播換切換帳號測試 不會用原帳號測試)
            //new WDFnL3Advice().alMeasureTime.add(strDate+",血糖");

            clickRecord();
            if (strDate.equals(new WDFnL3BloodGlucoseRecord(1).getDate())){
                logger.info(strPassMsg);
            }else {
                logger.warn(strFailMsg);
                logger.debug("Reason: The data which be added is different with first record");
            }
            new WDFnL3BloodGlucoseRecord().clickBack();
        }catch (Exception eWeakNetwork){
            logger.warn(strFailMsg);
            logger.debug("Reason: Fail by weak network");
            //判斷目前是否再新增頁面
            try {
                //record page
                new WDFnL3BloodGlucoseRecord().clickBack();
            }catch (Exception eNoFindElement){
                //BG page
            }
        }
    }

    public void UpdateRecord(){
        try {
            WDFnL3BloodGlucoseRecord wdFnL3BloodGlucoseRecord = new WDFnL3BloodGlucoseRecord(1);
            wdFnL3BloodGlucoseRecord.update();
            logger.info(strPassMsg);
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
        }
    }

    public void CompareRecord() throws IOException {
        ArrayList alBG = new ArrayList();
        try {
            //儲存到第４個紀錄 因為第一筆記錄為剛剛新增的紀錄
            for (int i=1; i<=4; i++){
                WDFnL3BloodGlucoseRecord wdFnL3BloodGlucoseRecord = new WDFnL3BloodGlucoseRecord(i);
                String recordData = "";
                recordData += wdFnL3BloodGlucoseRecord.getSession()+"/"+wdFnL3BloodGlucoseRecord.getGLU();
                alBG.add(recordData);
            }
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't find compare record because of weak network");
            return;
        }

        ArrayList alCompareData = new ArrayList(alBG);
        alCompareData.remove(0);
        WDFnL3BloodGlucoseRecord wdFnL3BloodGlucoseRecord = new WDFnL3BloodGlucoseRecord();
        if (wdFnL3BloodGlucoseRecord.compare(alCompareData)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Expect data: " + wdFnL3BloodGlucoseRecord.alReadLine);
            logger.debug("Read  data : " + alCompareData);

        }


        //將記錄前３筆資料存入檔案
        __strFileName = "BloodGlucoseRecord";
        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileWriter fw = new FileWriter(__strFilePath);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i=0; i<3; i++){
            bw.write(alBG.get(i)+"\r\n");
        }
        bw.flush();
        bw.close();
    }

    public void CheckDataMean() throws InterruptedException, IOException {
        __strFileName = "glucose_colormean_en";

        clickDataMean();
        Thread.sleep(5000);
        Set contextWebview = iosDriver.getContextHandles();
        //System.out.println(contextWebview);

        //switch to webview and get html
        iosDriver.context((String) contextWebview.toArray()[contextWebview.size()-1]);
        Document doc = Jsoup.parse(iosDriver.getPageSource());
        iosDriver.context((String) contextWebview.toArray()[0]);

        //消除HTML標籤  只取文字部分
        String strWebviewContent = doc.text();
        //System.out.println(strWebviewContent);

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
        }

        clickBackToBG();
    }

    public void CheckAchieved() throws InterruptedException, IOException {
        __strFileName = "AccountInfo";

        //日期處理 格式設定
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        //取得今天日期
        Calendar cal = Calendar.getInstance();
        Date todayDate = cal.getTime();
        //取得今天星期(0~6)
        int week = Integer.parseInt(new SimpleDateFormat("u").format(todayDate));   //u 為星期
        //推算當週 日~六 日期
        ArrayList alWeekDate = new ArrayList();
        cal.add(Calendar.DATE,-week);
        for (int i=0; i<7; i++){
            cal.add(Calendar.DATE,+i);
            alWeekDate.add(dateFormat.format(cal.getTime()));
            cal.add(Calendar.DATE,-i);
        }

        //取得本週範圍內的日期紀錄
        clickRecord();
        Thread.sleep(5000);

        int numRecordInThisWeek = 0;
        ArrayList<IOSElement> iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("name LIKE '????/??/?? ??:??'");
        for (int i=0; i<iosElements.size(); i++){
            boolean isFind = false;
            String strDate[] = iosElements.get(i).getText().split(" ");
            for (int j=0; j<alWeekDate.size(); j++){
                if (strDate[0].equals(alWeekDate.get(j))){
                    numRecordInThisWeek++;
                    isFind = true;
                    break;
                }
            }
            if (!isFind){
                break;
            }
        }

        //讀檔抓取BG一週測量次數
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
        int measureBG = -1;
        while (br.ready()){
            if (br.readLine().indexOf("BG measurements") != -1){
                measureBG = Integer.parseInt(br.readLine());
                break;
            }
        }
        new WDFnL3BloodGlucoseRecord().clickBack();

        //計算達成率
        double dbAchieved = 0;
        if (measureBG != -1){
            DecimalFormat df = new DecimalFormat("##.0");
            dbAchieved = numRecordInThisWeek*1.0 / measureBG*1.0 * 100.0;

            dbAchieved = Double.parseDouble(df.format(dbAchieved));
            if (dbAchieved >= 100.0){
                dbAchieved = 100.0;
            }
        }
        else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't find TestData file with BG measurements.");
            return;
        }

        //比較達成率是否正確
        String strAchievedBG = getAchievedMsg();
        if (strAchievedBG.contains(String.valueOf(dbAchieved))){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("BG " + strAchievedBG);
            logger.debug("Calculation Achieved: "+ dbAchieved + "%");
        }

        //寫入檔案 在其他的測試項目會使用到 (加油團...)
        FileWriter fw = new FileWriter(__strFilePath,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("###BG achieved (%)\r\n");
        bw.write(dbAchieved+"\r\n");
        bw.flush();
        bw.close();
    }






}
