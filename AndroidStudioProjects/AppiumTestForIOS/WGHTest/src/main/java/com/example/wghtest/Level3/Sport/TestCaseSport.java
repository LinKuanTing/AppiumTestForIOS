package com.example.wghtest.Level3.Sport;

import com.example.wghtest.other.FnFileEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class TestCaseSport extends WDFnL3Sport {

    private static String __strFileName;
    private static String __strFilePath;

    Logger logger = LoggerFactory.getLogger(TestCaseSport.class);

    public void LoadRecord(){
        try {
            clickRecord();
            try {
                Thread.sleep(10000);
                //是否下載紀錄訊息
                iosDriver.switchTo().alert().accept();
            }catch (Exception eNoFindElement){ }

            new WDFnL3SportRecord().clickToday();

            logger.info(strPassMsg);
        }catch (Exception eTimeOut){
            logger.warn(strFailMsg);
            logger.debug("Reason: Time out for load record.");
        }

        new WDFnL3SportRecord().clickBack();
    }

    public void AddRecord(){
        ArrayList alAddRecord = new ArrayList();
        try {
            setValue();
            alAddRecord.add(_strAddSportContent);
            Thread.sleep(3000);
            setPedometer();
            alAddRecord.add(_strAddSportContent);


            Thread.sleep(3000);
            clickRecord();
            Thread.sleep(5000);

            WDFnL3SportRecord wdFnL3SportRecord = new WDFnL3SportRecord();
            wdFnL3SportRecord.clickToday();
            ArrayList alDateRecord = wdFnL3SportRecord.getDateRecord();

            if (wdFnL3SportRecord.isExistRecord(alAddRecord,alDateRecord)){
                logger.info(strPassMsg);
            }else {
                logger.warn(strFailMsg);
                logger.debug("Reason: Can't find add record from record.");
                logger.debug("Add  record: " + alAddRecord);
                logger.debug("Read record: " + alDateRecord);
            }

            wdFnL3SportRecord.clickBack();
        }catch (Exception eWeekNetWork){
            eWeekNetWork.printStackTrace();
            logger.warn(strFailMsg);
            logger.debug("Reason: Weak network");
        }
    }

    public void UpdateRecord(){
        try{
            WDFnL3SportRecord wdFnL3SportRecord = new WDFnL3SportRecord();
            wdFnL3SportRecord.updata();
            Thread.sleep(5000);
            new WDFnL3SportRecord().clickBackToRecord();
            logger.info(strPassMsg);
        } catch (Exception eNoFindElement) {
            eNoFindElement.printStackTrace();
            logger.warn(strFailMsg);
            
        }
    }

    public void CompareRecord() throws IOException {
        //取得本週的運動時間並寫入檔案 後面測試會使用到
        __strFileName = "AccountInfo";
        String strExerciseTime = String.valueOf(new WDFnL3SportRecord().getWeeklySportTime());
        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileWriter fw = new FileWriter(__strFilePath,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("###Weekly exercise time\r\n");
        bw.write(strExerciseTime+"\r\n");
        bw.flush();
        bw.close();


        __strFileName = "SportRecord";
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        ArrayList alReadData = fnFileEvent.getContent(__strFilePath);

        WDFnL3SportRecord wdFnL3SportRecord = new WDFnL3SportRecord();
        if (wdFnL3SportRecord.isExistRecord(alReadData)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Compare Data can't be found.");
        }


    }

    public void CheckNotUploadMsg() {
        saveAndUploadLater();
        String strNotUploadMsg = getNotUploadRecordMsg();
        int index = strNotUploadMsg.indexOf("：")+1;

        if (strNotUploadMsg.charAt(index) == '1'){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Records waiting for upload number is wrong.");
        }
    }

}
