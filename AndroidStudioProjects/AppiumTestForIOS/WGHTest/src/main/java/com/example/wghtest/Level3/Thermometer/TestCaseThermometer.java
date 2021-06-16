package com.example.wghtest.Level3.Thermometer;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level3.FnPicEvent;

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
import java.util.ArrayList;
import java.util.Set;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class TestCaseThermometer extends WDFnL3Thermometer {

    private String __strFileName;
    private String __strFilePath;

    Logger logger;

    public TestCaseThermometer(){
        logger = LoggerFactory.getLogger(TestCaseThermometer.class);
    }

    public void LoadRecord(){
        try {
            for (int i=1; i<=10; i++){
                clickRecord();
                try {
                    new WDFnL3ThermometerRecord(1);
                    logger.info(strPassMsg);
                    new WDFnL3ThermometerRecord().clickBack();
                    return;
                }catch (Exception eWeakNetwork){
                    if (i==10){
                        logger.warn(strFailMsg);
                        logger.debug("Reason: repeat click record to load data fail with 10 times");
                    }
                    Thread.sleep(3000);
                    new WDFnL3ThermometerRecord().clickBack();
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
            //new WDFnL3Advice().alMeasureTime.add(strDate+",體溫");

            Thread.sleep(3000);
            clickRecord();


            if (strDate.equals(new WDFnL3ThermometerRecord(1).getDate())){
                logger.info(strPassMsg);
            }else {
                logger.warn(strFailMsg);
                logger.debug("Reason: The data which be added is different with first record");
                System.out.println(strDate+" , "+new WDFnL3ThermometerRecord(1).getDate());
            }
            new WDFnL3ThermometerRecord().clickBack();
        }catch (Exception eWeakNetwork){
            eWeakNetwork.printStackTrace();
            logger.warn(strFailMsg);
            logger.debug("Reason: Fail by weak network");
            //判斷目前是否再新增頁面
            try {
                //record page
                new WDFnL3ThermometerRecord().clickBack();
            }catch (Exception eNoFindElement){
                //Temp page
            }
        }
    }

    public void UpdateRecord(){
        try {
            WDFnL3ThermometerRecord wdFnL3ThermometerRecord = new WDFnL3ThermometerRecord(1);
            wdFnL3ThermometerRecord.update();
            logger.info(strPassMsg);
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
        }

    }

    public void CompareRecord() throws IOException, InterruptedException {
        ArrayList alTemp = new ArrayList();
        try {
            Thread.sleep(5000);
            //儲存到第４個紀錄 因為第一筆記錄為剛剛新增的紀錄
            for (int i=1; i<=4; i++){

                //確保紀錄順利找到
                while (true){
                    try {
                        new WDFnL3ThermometerRecord(i);
                        break;
                    }catch (Exception eNoFindElement){
                        new WDFnL3ThermometerRecord().clickBack();
                        clickRecord();
                    }
                }

                WDFnL3ThermometerRecord wdFnL3ThermometerRecord = new WDFnL3ThermometerRecord(i);
                String recordData = "";
                recordData += wdFnL3ThermometerRecord.getType()+"/"+wdFnL3ThermometerRecord.getTemp();
                alTemp.add(recordData);
            }
        }catch (Exception eNoFindElement){
            eNoFindElement.printStackTrace();
            new FnPicEvent().logShot();
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't find compare record because of weak network");
            new WDFnL3ThermometerRecord().clickBack();
            return;
        }

        ArrayList alCompareData = new ArrayList(alTemp);
        alCompareData.remove(0);
        WDFnL3ThermometerRecord wdFnL3ThermometerRecord = new WDFnL3ThermometerRecord();
        if (wdFnL3ThermometerRecord.compare(alCompareData)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Expect data: " + wdFnL3ThermometerRecord.alReadLine);
            logger.debug("Read  data : " + alCompareData);
        }

        //將記錄前３筆資料存入檔案
        __strFileName = "ThermometerRecord";
        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileWriter fw = new FileWriter(__strFilePath);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i=0; i<3; i++){
            bw.write(alTemp.get(i)+"\r\n");
        }
        bw.flush();
        bw.close();

    }

    public void CheckDataMean() throws InterruptedException, IOException {
        __strFileName = "temp_colormean_en";

        clickDataMean();
        Thread.sleep(8000);
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

        clickClose();
    }

}
