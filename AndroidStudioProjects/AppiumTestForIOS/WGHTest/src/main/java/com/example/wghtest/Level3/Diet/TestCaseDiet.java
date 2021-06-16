package com.example.wghtest.Level3.Diet;

import com.example.wghtest.other.FnFileEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class TestCaseDiet extends WDFnL3Diet {

    private static String __strFileName = "DietRecord";
    private static String __strFilePath;

    Logger logger;

    public TestCaseDiet(){
        logger = LoggerFactory.getLogger(TestCaseDiet.class);
    }


    public void LoadRecord(){
        try {
            clickRecord();
            try {
                //跳出是否下載紀錄訊息
                iosDriver.switchTo().alert().accept();
            }catch (Exception eNoFindElement){ }

            new WDFnL3DietRecord(1);

            logger.info(strPassMsg);

        }catch (Exception eTimeOut){
            logger.warn(strFailMsg);
            logger.debug("Reason: Time out for load record.");
        }

        new WDFnL3DietRecord().clickBack();
    }


    public void AddRecord() throws InterruptedException {
        ArrayList alAddRecord = new ArrayList();

        try{
            setValue("Breakfast");
            alAddRecord.add(_strAddDietContent);
        }catch (Exception eNoFindElement){
            //重複用餐類型紀錄
            iosDriver.switchTo().alert().accept();
        }


        setValue("Dessert");
        alAddRecord.add(_strAddDietContent);

        clickRecord();
        Thread.sleep(5000);

        //取得今天日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String strTodayDate = dateFormat.format(Calendar.getInstance().getTime());

        //取得日期內所有紀錄
        ArrayList alDateRecord = new WDFnL3DietRecord().getDateRecord(strTodayDate);
        if (alDateRecord.size()==0){
            Thread.sleep(5000);
            alDateRecord = new WDFnL3DietRecord().getDateRecord(strTodayDate);
        }


        //新增資料是否出現在日期記錄裡
        int sameNum = 0;
        for (int i=0; i<alAddRecord.size(); i++){
            for (int j=1; j<alDateRecord.size(); j++){
                String[] strAddRecord = alAddRecord.get(i).toString().split(",");
                if (alDateRecord.get(j).toString().contains(strAddRecord[1])){
                    sameNum++;
                    break;
                }
            }
        }

        if (sameNum == alAddRecord.size()){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't find add data");
            logger.debug("add data: " + alAddRecord);
            logger.debug(" record : " + alDateRecord);
        }

        new WDFnL3DietRecord().clickBack();
    }


    public void CheckExistMainMeal() throws InterruptedException {
        try{
            try {
                setValue("Lunch");
            }catch (Exception e){}
            Thread.sleep(3000);
            iosDriver.switchTo().alert().accept();
            logger.info(strPassMsg);
            return;
        }catch (Exception eNoFindElEment){
            //eNoFindElEment.printStackTrace();
        }


        try{
            try {
                setValue("Lunch");
            }catch (Exception e){}
            Thread.sleep(3000);
            iosDriver.switchTo().alert().accept();
            logger.info(strPassMsg);
        }catch (Exception eNoFindElEment){
            eNoFindElEment.printStackTrace();
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't display exist meal");
        }


    }


    public void UpdateRecord() throws InterruptedException{
        try{
            WDFnL3DietRecord wdFnL3DietRecord = new WDFnL3DietRecord(1);
            wdFnL3DietRecord.update();
            logger.info(strPassMsg);
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);

        }

    }


    public void CompareRecord() throws IOException {
        ArrayList alExpectData;
        ArrayList alReadData;
        __strFileName = "DietRecord";

        FnFileEvent fnFileEvent= new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        alExpectData = fnFileEvent.getContent(__strFilePath);

        //比較資料
        int sameNum = 0;
        WDFnL3DietRecord wdFnL3DietRecord = new WDFnL3DietRecord();
        for (int i=0; i<alExpectData.size(); i++){
            String[] strCompareData = alExpectData.get(i).toString().split(",");

            boolean isExistDate = false;
            ArrayList al90DayRecord = wdFnL3DietRecord.get90DaysRecord();
            for (int k=0; k<al90DayRecord.size(); k++){
                if (al90DayRecord.get(k).toString().contains(strCompareData[0])){
                    isExistDate = true;
                    break;
                }
            }

            alReadData = wdFnL3DietRecord.getDateRecord(strCompareData[0]);
            if (isExistDate && alReadData.size()==0){
                alReadData = wdFnL3DietRecord.getDateRecord(strCompareData[0]);
            }


           //System.out.println(alExpectData +" , "+ alReadData);
            for (int j=0; j<alReadData.size(); j++){
                if (alReadData.get(j).toString().contains(strCompareData[1])){
                    sameNum++;
                    break;
                }
            }
        }


        if (sameNum == alExpectData.size()){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't find Expect Data. Only find " + sameNum +" records");
        }

    }

    public void CheckNotUploadMag(){
        setValueBySaveUploadLater();
        String strWaitForUploadMsg = getNotUploadRecordMsg();
        int index = strWaitForUploadMsg.indexOf(":")+1;

        if (strWaitForUploadMsg.charAt(index) != '0'){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Waiting for upload number is wrong.");
        }

    }

    public void CheckDataMean() throws InterruptedException, IOException {
        __strFileName = "DietSuggestionData";

        clickDataMean();
        Thread.sleep(8000);

        //取得切換窗口
        Set contextWebview = iosDriver.getContextHandles();
        //System.out.println(contextWebview);

        //switch to webview and get html
        iosDriver.context((String) contextWebview.toArray()[contextWebview.size()-1]);
        Document doc = Jsoup.parse(iosDriver.getPageSource());
        iosDriver.context((String) contextWebview.toArray()[0]);

        //取得被塗色字體
        Elements category = doc.select("span[class =\"word\"]");
        String strSuggestionData = "";
        for (int i=0; i<category.size(); i++){
            if (i != category.size()-1){
                strSuggestionData += category.get(i).text() + "/";
            }else {
                strSuggestionData += category.get(i).text();
            }
        }

        //取得當前體重每日所需熱量
        String strDailyCal = String.valueOf(getDailyCal());

        if (strDailyCal.equals("-1")){
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't get Daily calories.");
            System.out.println(strDailyCal);
            new WDFnL3DietRecord().clickBack();
            return;
        }

        //取預期資料
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));

        String strExpectData = "";
        while(br.ready()){
            strExpectData = br.readLine();
            if (strExpectData.contains(strDailyCal)) {
                break;
            }
        }

        if (strSuggestionData.equals(strExpectData)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Expect Data: " + strExpectData);
            logger.debug(" Read  Data: " + strSuggestionData);
        }

        new WDFnL3DietRecord().clickBack();
    }

    public void CheckEatingTimeMsg() throws IOException {
        __strFileName = "AccountInfo";
        ArrayList alEatingTime = new ArrayList();

        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));

        while (br.ready()){
            String strReadLine = br.readLine();
            if (strReadLine.contains("mealtime")){
                alEatingTime.add(br.readLine());
            }
        }

        //轉換成分鐘
        String strMealMinute = "";
        for (int i=0; i<alEatingTime.size(); i++){
            String[] strMealTime = alEatingTime.get(i).toString().split("~");
            //[0]->start time   [1]->end time
            for (int j=0; j<strMealTime.length; j++){
                String[] strTime = strMealTime[j].split(":");
                //[0]->hour    [1]->minute
                int timeMinutes = Integer.parseInt(strTime[0])*60+Integer.parseInt(strTime[1]);
                strMealMinute += timeMinutes;
                if (j==0){
                    strMealMinute += "~";
                }else if (j!=0 && i!=alEatingTime.size()-1){
                    strMealMinute += ",";
                }
            }
        }

        String[] strDateMsg = {"irregular","Fasting","非正餐","禁食"};
        String[] strCatrgory = {"Dessert","Beverage","Dinner","Lunch","Breakfast"};
        String strMealTime[] = strMealMinute.split(",");
        String[] timeBreakfast = strMealTime[0].split("~");
        String[] timeLunch = strMealTime[1].split("~");
        String[] timeDinner = strMealTime[2].split("~");
        String[] timeFasting = strMealTime[3].split("~");

        for (int i=0; i<strCatrgory.length; i++){
            setCategory(strCatrgory[i]);

            try {
                //跳出重複三餐訊息
                iosDriver.switchTo().alert().accept();
            }catch (Exception eNoFindElement){ }

            String[] strDateTime = getDate().split(" ");
            //[0]日期 [1]時間
            String[] strDate = strDateTime[1].split(":");
            //[0]時 [1]分
            int time = Integer.parseInt(strDate[0])*60 + Integer.parseInt(strDate[1]);
            switch(i){
                case 0:
                    if (time > Integer.parseInt(timeFasting[0]) && time < Integer.parseInt(timeFasting[1])){
                        if ( !(getDateWarningMsg().contains(strDateMsg[1]) || getDateWarningMsg().contains(strDateMsg[3])) ){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Fasting time message is wrong");
                            return;
                        }
                    }else if(time > Integer.parseInt(timeBreakfast[0]) && time < Integer.parseInt(timeBreakfast[1])){
                        if ( (getDateWarningMsg().contains(strDateMsg[0]) || getDateWarningMsg().contains(strDateMsg[2])) ){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Breakfast time message is wrong");
                            return;
                        }
                    }
                    break;
                case 1:
                    if (time > Integer.parseInt(timeFasting[0]) && time < Integer.parseInt(timeFasting[1])){
                        if ( !(getDateWarningMsg().contains(strDateMsg[1]) || getDateWarningMsg().contains(strDateMsg[3])) ){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Fasting time message is wrong");
                            return;
                        }
                    }else if(time > Integer.parseInt(timeLunch[0]) && time < Integer.parseInt(timeLunch[1])){
                        if ( (getDateWarningMsg().contains(strDateMsg[0]) || getDateWarningMsg().contains(strDateMsg[2])) ){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Lunch time message is wrong");
                            return;
                        }
                    }
                    break;
                case 2:
                    if (time > Integer.parseInt(timeFasting[0]) && time < Integer.parseInt(timeFasting[1])){
                        if ( !(getDateWarningMsg().contains(strDateMsg[1]) || getDateWarningMsg().contains(strDateMsg[3])) ){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Fasting time message is wrong");
                            return;
                        }
                    }else if(time > Integer.parseInt(timeDinner[0]) && time < Integer.parseInt(timeDinner[1])){
                        if ( (getDateWarningMsg().contains(strDateMsg[0]) || getDateWarningMsg().contains(strDateMsg[2])) ){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Dinner time message is wrong");
                            return;
                        }
                    }
                    break;
                default:
                    if (time > Integer.parseInt(timeFasting[0]) && time < Integer.parseInt(timeFasting[1])) {
                        if (!(getDateWarningMsg().contains(strDateMsg[1]) || getDateWarningMsg().contains(strDateMsg[3]))) {
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Fasting time message is wrong");
                            return;
                        }
                    }
            }

            if (i==strCatrgory.length-1){
                logger.info(strPassMsg);
            }

        }
    }


}
