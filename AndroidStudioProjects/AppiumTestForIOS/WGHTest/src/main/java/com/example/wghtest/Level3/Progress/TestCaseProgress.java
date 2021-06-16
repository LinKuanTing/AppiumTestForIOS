package com.example.wghtest.Level3.Progress;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class TestCaseProgress extends WDFnL3Progress {

    private static String __strFileName;
    private static String __strFilePath;

    Logger logger;

    public TestCaseProgress(){
        logger = LoggerFactory.getLogger(TestCaseProgress.class);
    }

    public void CheckContentData() throws IOException, InterruptedException {
        Thread.sleep(10000);
        contextWebview();
        String strHtmlSource = iosDriver.getPageSource();

        while (true){
            if (strHtmlSource.contains("－－")){
                contextNative();
                new WDFnL2TitleMenuTabbar().clickTabbarMenu();
                new WDFnL2TitleMenuTabbar().clickTabbarProgress();
                contextWebview();
                strHtmlSource = iosDriver.getPageSource();

            }else if (strHtmlSource.contains("商店街")){
                contextNative();
                new WDFnL2TitleMenuTabbar().clickTabbarMenu();
                new WDFnL2TitleMenuTabbar().clickTabbarProgress();
                contextWebview();
                strHtmlSource = iosDriver.getPageSource();

            }
            else {
                break;
            }

        }

        contextNative();

        //取預期資料
        String strNickName = "", strCurrentWeight = "", strBMI = "", strTargetWeight = "", strRemainderWeight = "", strWeeklyExercise = "";
        __strFileName = "AccountInfo";
        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
        while (br.ready()){
            String strReadLine = br.readLine();
            if (strReadLine.contains("Nick Name")){
                strNickName = br.readLine();
            }else if (strReadLine.contains("Current Weight")){
                strCurrentWeight = br.readLine();
            }else if (strReadLine.contains("BMI")){
                strBMI = br.readLine();
            }else if (strReadLine.contains("Target Weight")){
                strTargetWeight = br.readLine();
            }else if (strReadLine.contains("exercise time")){
                strWeeklyExercise = br.readLine();
            }
        }

        DecimalFormat df = new DecimalFormat("#.#");
        strRemainderWeight = df.format(Double.parseDouble(strCurrentWeight) - Double.parseDouble(strTargetWeight));


        if (strNickName.equals("") || strCurrentWeight.equals("") || strBMI.equals("") || strTargetWeight.equals("") || strWeeklyExercise.equals("")){
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't find enough data to compare.");
            return;
        }

        //System.out.println(strHtmlSource);


        //取將比較的資料
        Document doc = Jsoup.parse(strHtmlSource);
        Elements blkPickup = doc.select("span[class =\"word_bu\"]");
        try {
            if (blkPickup.size()==0){
                contextWebview();
                strHtmlSource = iosDriver.getPageSource();
                contextNative();
                doc = Jsoup.parse(strHtmlSource);
                blkPickup = doc.select("span[class =\"word_bu\"]");
            }
            //idx[0]->空白資料  移除
            blkPickup.remove(0);
        }catch (Exception e){
            if (strHtmlSource.contains("商店街")){
                System.out.println("Context wrong webview. Get shop content");
            }
        }



        //idx[0]->計畫倒數天數 [1]->暱稱 [2]->當前體重 [3]->ＢＭＩ [4]->與目標體重差 [5]->本週運動時間 [6]->飲食定時比例
        //比較[1] [2] [3] [4] [5]
        int sameNum = 0;
        for (int i=0; i<blkPickup.size(); i++){
            String strData = blkPickup.get(i).text();
            switch (i){
                case 1:
                    if (strData.equals(strNickName)){
                        sameNum++;
                    }else {
                        System.out.println(strData +" , "+strNickName);
                    }
                    break;
                case 2:
                    if (strData.equals(strCurrentWeight)){
                        sameNum++;
                    }else {
                        System.out.println(strData +" , "+strCurrentWeight);
                    }
                    break;
                case 3:
                    if (strData.equals(strBMI)){
                        sameNum++;
                    }else {
                        System.out.println(strData +" , "+strBMI);
                    }
                    break;
                case 4:
                    if (strData.equals(strRemainderWeight)){
                        sameNum++;
                    }else {
                        System.out.println(strData +" , "+strRemainderWeight);
                    }
                    break;
                case 5:
                    if (strData.equals(strWeeklyExercise)){
                        sameNum++;
                    }else {
                        System.out.println(strData +" , "+strWeeklyExercise);
                    }
                    break;
                default:
                    break;
            }
        }

        if (sameNum == 5){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Progress data is different with expect data.");
        }

    }

    public void CheckAwardsDescription()throws Exception{
        clickAwards();

        clickInfo();

        ArrayList alRead = getDescription();

        __strFileName = "AwardsDescription";
        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF8"));

        ArrayList alExpect = new ArrayList();
        while (br.ready()){
            alExpect.add(br.readLine());
        }

        //System.out.println(alExpect);
        //System.out.println(alRead);


        int sameNum = 0;
        for (int i=0; i<alRead.size(); i++){
            for (int j=0; j<alExpect.size(); j++){
                if (alExpect.get(j).toString().contains(alRead.get(i).toString())){
                    sameNum++;
                }
            }
        }
        Thread.sleep(3000);
        clickBack();

        if (sameNum == 12){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: some awards descriptions are wrong.");
        }

        Thread.sleep(5000);
        clickBack();



    }

}
