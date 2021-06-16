package com.example.wghtest.Level3.StressMood;

import com.example.wghtest.other.FnFileEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class TestCaseStressMood extends WDFnL3StressMood{

    private static String __strFileName;
    private static String __strFilePath;

    Logger logger;


    public TestCaseStressMood(){
        logger = LoggerFactory.getLogger(TestCaseStressMood.class);
    }

    public void LoadWellnessRecord() throws InterruptedException {
        clickIndexesRecord();
        Thread.sleep(3000);
        try {
            new WDFnL3WellnessIndexesRecord(1);
            logger.info(strPassMsg);
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't find record.");
        }
        new WDFnL3WellnessIndexesRecord().clickBack();
    }

    public void CompareWellnessRecord() throws IOException, InterruptedException {
        clickIndexesRecord();
        ArrayList alWellness = new  ArrayList();

        WDFnL3WellnessIndexesRecord wdFnL3WellnessIndexesRecord = new WDFnL3WellnessIndexesRecord();
        //取得紀錄列表前３筆資料
        try {
            ArrayList alDate = wdFnL3WellnessIndexesRecord.getAllRecordDate();
            ArrayList alHR = wdFnL3WellnessIndexesRecord.getAllRecordHeartRate();
            for (int i=0; i<3; i++){
                String strData = alDate.get(i)+"/"+alHR.get(i);
                alWellness.add(strData);
            }
        }catch (Exception eWeakNetwork){
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't find record data");

            new WDFnL3WellnessIndexesRecord().clickBack();
            return;
        }

        if (wdFnL3WellnessIndexesRecord.compare(alWellness)){
            logger.info(strPassMsg);
        }else{
            logger.warn(strFailMsg);
            logger.debug("Expect data: " + wdFnL3WellnessIndexesRecord.alReadLine);
            logger.debug("Read   data: " + alWellness);
        }

        new WDFnL3WellnessIndexesRecord().clickBack();
    }


    public void LoadBreathingRecord() throws InterruptedException, IOException {
        clickTrainingRecord();
        //載入９０天紀錄
        WDFnL3BreathingTrainRecord wdFnL3BreathingTrainRecord = new WDFnL3BreathingTrainRecord();
        for (int i=0; i<=4; ++i){
            Thread.sleep(2000);
            try {
                iosDriver.switchTo().alert().accept();
            }catch (Exception eNoFindElement){}

            if (i==4){ break; }

            wdFnL3BreathingTrainRecord.clickPreviousYear();
        }

        wdFnL3BreathingTrainRecord.clickToday();
        Thread.sleep(2000);

        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFileName = "BreathingTrainRecord";
        __strFilePath = fnFileEvent.getPath(__strFileName);
        //比較檔案中第3筆資料  因為點擊'今天'按鈕  年月TexView格式改變  因此呼叫Method時 會找不到月份而Fail  (等待ＢＵＧ修復好...)
        String[] strRecord = fnFileEvent.getContent(__strFilePath).get(2).toString().split("/");


        wdFnL3BreathingTrainRecord.clickDate(strRecord[0],strRecord[1]);
        int size = wdFnL3BreathingTrainRecord.getDateRecord().size();

        if (size != 0){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't find record data.");
        }

        wdFnL3BreathingTrainRecord.clickBack();
    }

    public void CompareBreathingRecord() throws IOException {
        clickTrainingRecord();

        WDFnL3BreathingTrainRecord wdFnL3BreathingTrainRecord = new WDFnL3BreathingTrainRecord();
        ArrayList alExpectData;
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFileName = "BreathingTrainRecord";
        __strFilePath = fnFileEvent.getPath(__strFileName);
        alExpectData = fnFileEvent.getContent(__strFilePath);

        int sameNum = 0;
        for (int i=0; i<alExpectData.size(); i++){
            String[] strExpectData = alExpectData.get(i).toString().split("/");
            //[0]->YearMonth [1]->Date [2]->Time [3]->Level
            wdFnL3BreathingTrainRecord.clickDate(strExpectData[0],strExpectData[1]);
            ArrayList alDateRecord = wdFnL3BreathingTrainRecord.getDateRecord();

            for (int j=0; j<alDateRecord.size(); j++){
                if (alExpectData.get(i).toString().contains(alDateRecord.get(j).toString())){
                    sameNum++;
                    break;
                }
            }
        }

        if (sameNum == alExpectData.size()){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Need find "+alExpectData.size()+" records, but only find "+sameNum+" records.");
        }

        new WDFnL3BreathingTrainRecord().clickBack();
    }

}



