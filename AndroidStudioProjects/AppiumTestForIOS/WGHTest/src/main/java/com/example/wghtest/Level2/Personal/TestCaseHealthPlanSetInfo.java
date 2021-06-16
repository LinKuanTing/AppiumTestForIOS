package com.example.wghtest.Level2.Personal;

import com.example.wghtest.other.FnFileEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;

import static com.example.wghtest.WGHTestBase.iosDriver;
import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;

public class TestCaseHealthPlanSetInfo extends WDFnL2HealthPlan {

    private String __strFileName = "AccountInfo";
    private String __strFilePath;

    private String __strMsgText = "name IN {'Plan Saved！','健康管理計畫儲存成功！'}";

    public void excute() throws Exception {
        Logger logger = LoggerFactory.getLogger(TestCaseHealthPlanSetInfo.class);
        __strFilePath = new FnFileEvent().getPath(__strFileName);

        clickHealthPlan();

        setHealthPlanInfo();

        try{
            iosDriver.findElementByIosNsPredicate(__strMsgText);
            iosDriver.switchTo().alert().accept();

            logger.info(strPassMsg);

            FileWriter fw = new FileWriter(__strFilePath,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("###Age\r\n");
            bw.write(getAge()+"\r\n");
            bw.write("###Height\r\n");
            bw.write(getHeight()+"\r\n");
            bw.write("###Initial Weight\r\n");
            bw.write(getInitialWeight()+"\r\n");
            bw.write("###Target Weight\r\n");
            bw.write(getTargetWeight()+"\r\n");
            bw.write("###Daily ExerciseNo (Sed->1, Mod->2, Vig->3)\r\n");
            bw.write(getActivityLevel()+"\r\n");
            bw.write("###Calories Reduce\r\n");
            bw.write(getReduceCal()+"\r\n");
            bw.write("###Breakfast mealtime\r\n");
            bw.write(_strBreakfastStart+"~"+_strBreakfastEnd+"\r\n");
            bw.write("###Lunch mealtime\r\n");
            bw.write(_strLunchStart+"~"+_strLunchEnd+"\r\n");
            bw.write("###Dinner mealtime\r\n");
            bw.write(_strDinnerStart+"~"+_strDinnerEnd+"\r\n");
            bw.write("###Fasting mealtime\r\n");
            bw.write(_strFastingStart+"~"+_strFastingEnd+"\r\n");
            bw.flush();
            bw.close();

        }catch (Exception eNoFindElement){
            iosDriver.switchTo().alert().accept();

            logger.warn(strFailMsg);
            logger.debug("Reason: Alert message is different from expect data.");

        }



    }
}
