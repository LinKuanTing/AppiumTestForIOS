package com.example.wghtest.Level3.Weight;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level3.FnPicEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;

import javax.imageio.ImageIO;

import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.AppHeight;
import static com.example.wghtest.WGHTestBase.AppWidth;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class TestCaseWeight extends WDFnL3Weight {

    private String __strFileName;
    private String __strFilePath;

    private int __intGender, __intAge;
    private Double __dbHeight;

    private Double __dbWarnTopBMI;
    private Double __dbYoungMaleTopBodyfat,__dbOldMaleTopBodyfat;
    private Double __dbYoungFemaleTopBodyfat,__dbOldFemaleTopBodyfat;

    private String __strENBodyfatGoodNote = "Bodyfat good,";
    private String __strENBodyfatBadNote = "Bodyfat over,";
    private String __strENWeightGoodNote = "weight and BMI good";
    private String __strENWeightBadNote = "weight and BMI over";
    private String __strCHBodtfatGoodNote = "體脂符合標準,";
    private String __strCHBodyfatBadNote = "體脂超標,";
    private String __strCHWeightGoodNote = "體重和BMI符合標準";
    private String __strCHWeightBadNote = "體重和BMI超標";

    private static String __imgTestDir = "/Users/ting.lin/TestData/TestPic/";
    private static String[] __imgTestPNG = {"Tips1.png","Tips2.png","Tips3.png","Tips4.png"};

    Logger logger;


    public TestCaseWeight(){
        logger = LoggerFactory.getLogger(TestCaseWeight.class);
    }

    public void LoadRecord(){
        try {
            for (int i=1; i<=10; i++){
                clickRecord();
                try {
                    new WDFnL3WeightRecord(1);
                    logger.info(strPassMsg);
                    new WDFnL3WeightRecord().clickBack();
                    return;
                }catch (Exception eWeakNetwork){
                    if (i==10){
                        logger.warn(strFailMsg);
                        logger.debug("Reason: Repeat click record to load data fail with 10 times");
                    }
                    new WDFnL3WeightRecord().clickBack();
                }
            }

        }catch (Exception eTimeOut){
            logger.warn(strFailMsg);
            logger.debug("Reason: Time out for load record");
        }
    }

    public void AddRecord(){
        String strDate ;

        try {

            setValue();
            strDate = getDate();

            //推波測試所需資料 (由於測試推播換切換帳號測試 不會用原帳號測試)
            //new WDFnL3Advice().alMeasureTime.add(strDate+",體重體脂");

            clickRecord();
            if (strDate.equals(new WDFnL3WeightRecord(1).getDate())){
                logger.info(strPassMsg);
            }else {
                logger.warn(strFailMsg);
                logger.debug("Reason: The data which be added is different from first record");
                logger.debug("Expect: " + strDate);
                logger.debug("Read  : " + new WDFnL3WeightRecord(1).getDate());
            }
            new WDFnL3WeightRecord().clickBack();
        }catch (Exception eWeakNetwork){
            eWeakNetwork.printStackTrace();
            logger.warn(strFailMsg);
            logger.debug("Reason: Fail by weak network");
            //判斷目前是否再新增體重頁面
            try {
                //record page
                new WDFnL3WeightRecord().clickBack();
            }catch (Exception eNoFindElement){
                //weight page
            }
        }
    }

    public void UpdateRecord(){
        try {
            WDFnL3WeightRecord wdFnL3WeightRecord = new WDFnL3WeightRecord(1);
            wdFnL3WeightRecord.update();
            logger.info(strPassMsg);
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
        }
    }

    public void DeleteRecord(){

        try {
            WDFnL3WeightRecord wdFnL3WeightRecord = new WDFnL3WeightRecord(1);
            wdFnL3WeightRecord.delete();
            logger.info(strPassMsg);
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
        }
    }

    public void CompareRecord() throws IOException {

        ArrayList alWeight = new ArrayList();
        try {
            for (int i=1; i<=3; i++){
                alWeight.add(new WDFnL3WeightRecord(i).getWeight());
            }
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
            if (alWeight.size()>0){
                logger.debug("Reason: Can't find compare record (Maybe over 90 days)");
            }else {
                logger.debug("Reason: Can't find compare record because of weak network");
            }
            return;
        }

        WDFnL3WeightRecord wdFnL3WeightRecord = new WDFnL3WeightRecord();
        if (wdFnL3WeightRecord.compare(alWeight)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Expect data: " + wdFnL3WeightRecord.alReadLine);
            logger.debug("Read  data : " + alWeight);

        }

        __strFileName = "AccountInfo";
        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileWriter fw = new FileWriter(__strFilePath,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("###Current Weight\r\n");
        bw.write(alWeight.get(0)+"\r\n");
        bw.flush();
        bw.close();

    }

    public void CheckWarningMsg() throws IOException, InterruptedException {
        String strExpectMsg;
        String strReadMsg;

        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFileName = "TestDataWeightCheckWarningMsg";
        __strFilePath = fnFileEvent.getPath(__strFileName);
        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        __dbWarnTopBMI = Double.valueOf(alData.get(0).toString());
        __dbYoungMaleTopBodyfat = Double.valueOf(alData.get(1).toString());
        __dbOldMaleTopBodyfat = Double.valueOf(alData.get(2).toString());
        __dbYoungFemaleTopBodyfat = Double.valueOf(alData.get(3).toString());
        __dbOldFemaleTopBodyfat = Double.valueOf(alData.get(4).toString());


        __strFileName = "AccountInfo";
        __strFilePath = fnFileEvent.getPath(__strFileName);
        alData = fnFileEvent.getContent(__strFilePath);

        __intGender = Integer.parseInt(alData.get(2).toString());
        __intAge = Integer.parseInt(alData.get(3).toString());

        //判斷BMI訊息是否正確
        while (true){
            strReadMsg = getWeightMsg();
            if (getBMI() >= __dbWarnTopBMI){
                if (strReadMsg.contains(__strENWeightBadNote) || strReadMsg.contains(__strCHWeightBadNote)){
                    break;
                }else {
                    logger.warn(strFailMsg);
                    logger.debug("Reason: Weight message display wrong(1)");
                    return;
                }
            }
            else if (getBMI() < __dbWarnTopBMI){
                if (!(strReadMsg.contains(__strENWeightGoodNote) || strReadMsg.contains(__strCHWeightGoodNote))){
                    logger.warn(strFailMsg);
                    logger.debug("Reason: Weight message display wrong(2)");
                    return;
                }
            }
            weightSwipeLeft();
        }

        //判斷Bodyfat訊息是否正確
        while (true){
            strReadMsg = getWeightMsg();
            if (__intGender == 1){
                if (__intAge < 30){
                    if (getBodyfat() > __dbYoungMaleTopBodyfat){
                        if (strReadMsg.contains(__strENBodyfatBadNote) || strReadMsg.contains(__strCHBodyfatBadNote)){
                            logger.info(strPassMsg);
                            break;
                        }else {
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Weight message display wrong(3)");
                            return;
                        }
                    }
                    else if (getBodyfat() < __dbYoungMaleTopBodyfat){
                        if (!(strReadMsg.contains(__strENBodyfatGoodNote) || strReadMsg.contains(__strCHBodtfatGoodNote))){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Weight message display wrong(4)");
                            return;
                        }
                    }
                }
                //Age >=30
                else {
                    if (getBodyfat() > __dbOldMaleTopBodyfat){
                        if (strReadMsg.contains(__strENBodyfatBadNote) || strReadMsg.contains(__strCHBodyfatBadNote)){
                            logger.info(strPassMsg);
                            break;
                        }else {
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Weight message display wrong(5)");
                            return;
                        }
                    }
                    else if (getBodyfat() < __dbOldMaleTopBodyfat){
                        if (!(strReadMsg.contains(__strENBodyfatGoodNote) || strReadMsg.contains(__strCHBodtfatGoodNote))){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Weight message display wrong(6)");
                            return;
                        }
                    }

                }
            }
            // Gender == 0
            else {
                if (__intAge < 30){
                    if (getBodyfat() > __dbYoungFemaleTopBodyfat){
                        if (strReadMsg.contains(__strENBodyfatBadNote) || strReadMsg.contains(__strCHBodyfatBadNote)){
                            logger.info(strPassMsg);
                            break;
                        }else {
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Weight message display wrong(7)");
                            return;
                        }
                    }
                    else if (getBodyfat() < __dbYoungFemaleTopBodyfat){
                        if (!(strReadMsg.contains(__strENBodyfatGoodNote) || strReadMsg.contains(__strCHBodtfatGoodNote))){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Weight message display wrong(8)");
                            return;
                        }
                    }
                }
                //Age >=30
                else {
                    if (getBodyfat() > __dbOldFemaleTopBodyfat){
                        if (strReadMsg.contains(__strENBodyfatBadNote) || strReadMsg.contains(__strCHBodyfatBadNote)){
                            logger.info(strPassMsg);
                            break;
                        }else {
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Weight message display wrong(9)");
                            return;
                        }
                    }
                    else if (getBodyfat() < __dbOldFemaleTopBodyfat){
                        if (!(strReadMsg.contains(__strENBodyfatGoodNote) || strReadMsg.contains(__strCHBodtfatGoodNote))){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Weight message display wrong(10)");
                            return;
                        }
                    }

                }
            }
            bodyfatSwipeLeft();
        }
    }


    public void CheckDataMean() throws IOException, InterruptedException {

        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFileName = "AccountInfo";
        __strFilePath = fnFileEvent.getPath(__strFileName);
        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        __intGender = Integer.parseInt(alData.get(2).toString());
        __intAge = Integer.parseInt(alData.get(3).toString());
        __dbHeight = Double.parseDouble(alData.get(4).toString());

        //go record page and refresh
        clickRecord();
        touchAction.press(PointOption.point(AppWidth/2,AppHeight/4)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(AppWidth/2,AppHeight*3/4)).release().perform();
        //get first record to get data to compare
        WDFnL3WeightRecord wdFnL3WeightRecord = new WDFnL3WeightRecord(1);
        double dbWeight = Double.parseDouble(wdFnL3WeightRecord.getWeight());
        double dbBodyfat = Double.parseDouble(wdFnL3WeightRecord.getBodyfat());
        double dbBMI = Double.parseDouble(wdFnL3WeightRecord.getBMI());
        double dbWater = Double.parseDouble(wdFnL3WeightRecord.getWater());
        double dbMM = Double.parseDouble(wdFnL3WeightRecord.getMM());
        double dbBone = Double.parseDouble(wdFnL3WeightRecord.getBone());
        double dbBMR = Double.parseDouble(wdFnL3WeightRecord.getBMR());
        double dbVFL = Double.parseDouble(wdFnL3WeightRecord.getVFL());
        double dbProtein = Double.parseDouble(wdFnL3WeightRecord.getProtein());
        double dbMP = Double.parseDouble(wdFnL3WeightRecord.getMP());
        wdFnL3WeightRecord.clickBack();


        //click Figures
        clickDataMean();
        Thread.sleep(15000);
        Set contextWebview = iosDriver.getContextHandles();
        //System.out.println(contextWebview);
        //switch to webview and get html
        iosDriver.context((String) contextWebview.toArray()[contextWebview.size()-1]);
        String strWeightFiguresHtml = iosDriver.getPageSource();
        iosDriver.context((String) contextWebview.toArray()[0]);

        Document doc = Jsoup.parse(strWeightFiguresHtml);

        //find the colored grids
        Elements blkPickUp = doc.select("table").select("tbody").select("tr").select("td[bgcolor=\"#00FF00\"]");
        //System.out.println(strWeightFiguresHtml);

        //save the colored grids to array
        String[] strPickUp = new String[blkPickUp.size()];
        for (int i=0; i<blkPickUp.size(); i++){
            strPickUp[i] = String.valueOf(blkPickUp.get(i).id());
        }

        //webview data
        Elements wvBMR = doc.select("font[id=\"1_1_0\"]");
        Elements wvMP = doc.select("font[id=\"9_1_0\"]");
        Elements wvBodyfat = doc.select("font[id=\"2_1_0\"]");
        Elements wvWater = doc.select("font[id=\"3_1_0\"]");
        Elements wvMM = doc.select("font[id=\"4_1_0\"]");
        Elements wvBone = doc.select("font[id=\"5_1_0\"]");
        Elements wvBMI = doc.select("font[id=\"6_1_0\"]");
        Elements wvVFL = doc.select("font[id=\"7_1_0\"]");
        Elements wvProtein = doc.select("font[id=\"8_1_0\"]");

        //compare webview data and record data result which is the same
        try{
            if (dbBMR == Double.parseDouble(wvBMR.get(0).text()) &&
                    dbMP == Double.parseDouble(wvMP.get(0).text()) &&
                    dbBodyfat == Double.parseDouble(wvBodyfat.get(0).text()) &&
                    dbWater == Double.parseDouble(wvWater.get(0).text()) &&
                    dbMM == Double.parseDouble(wvMM.get(0).text()) &&
                    dbBone == Double.parseDouble(wvBone.get(0).text()) &&
                    dbBMI == Double.parseDouble(wvBMI.get(0).text()) &&
                    dbVFL == Double.parseDouble(wvVFL.get(0).text()) &&
                    dbProtein == Double.parseDouble(wvProtein.get(0).text()) ){ }
            else {
                logger.warn(strFailMsg);
                logger.debug("Reason: Webview data is different from the record first data");
                return;
            }
        }catch (Exception eIndexOutOf){
            eIndexOutOf.printStackTrace();
            System.out.println(strWeightFiguresHtml);
            logger.warn(strFailMsg);
            logger.debug("Reason: Webview data get fail");
            return;
        }



        boolean isPass = true;
        //determine the colored grids if are on the correct grids
        for (String str : strPickUp){
            switch (str.charAt(0)){
                case '1':
                case '9':
                    switch (str.charAt(2)) {
                        case '1':
                            if (__intGender != 1){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by gender of metabolism");
                                isPass = false; }
                            break;
                        case '2':
                            if (__intGender != 0){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by gender of metabolism");
                                isPass = false;}
                            break;
                    }
                    switch (str.charAt(4)){
                        case '1':
                            if ( !(__intAge >= 18 && __intAge < 30) ){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by age of metabolism");
                                isPass = false;}
                            break;
                        case '2':
                            if ( !(__intAge >= 30 && __intAge < 49) ){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by age of metabolism");
                                isPass = false;}
                            break;
                        case '3':
                            if ( !(__intAge >= 50 && __intAge < 69) ){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by age of metabolism");
                                isPass = false;}
                            break;
                        case '4':
                            if ( !(__intAge >= 70) ){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by age of metabolism");
                                isPass = false;}
                            break;
                    }
                    break;
                case '2':
                    switch (str.charAt(2)){
                        case '1':
                            if (__intGender != 1){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by gender of fat ratio");
                                isPass = false;
                            }
                            if (!(__intAge < 30)){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by age of fat ratio");
                                isPass = false;
                            }
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbBodyfat < 20) ){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbBodyfat >= 20 && dbBodyfat < 25) ){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbBodyfat >= 25) ){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                            }
                            break;
                        case '2':
                            if (__intGender != 1){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by gender of fat ratio");
                                isPass = false;
                            }
                            if (!(__intAge >= 30)){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by age of fat ratio");
                                isPass = false;
                            }
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbBodyfat < 25) ){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbBodyfat >= 25 && dbBodyfat < 30) ){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbBodyfat >= 30) ){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                            }
                            break;
                        case '3':
                            if (__intGender != 0){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by gender of fat ratio");
                                isPass = false;
                            }
                            if (!(__intAge < 30)){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by age of fat ratio");
                                isPass = false;
                            }
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbBodyfat < 25) ){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbBodyfat >= 25 && dbBodyfat < 30) ){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbBodyfat >= 30) ){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                            }
                            break;
                        case '4':
                            if (__intGender != 0){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by gender of fat ratio");
                                isPass = false;
                            }
                            if (!(__intAge >= 30)){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by age of fat ratio");
                                isPass = false;
                            }
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbBodyfat < 30) ){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbBodyfat >= 30 && dbBodyfat < 35) ){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbBodyfat >= 35) ){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                            }
                            break;
                    }
                    break;
                case '3':
                    switch (str.charAt(4)){
                        case '1':
                            if (__intGender != 1){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by gender of water ratio");
                                isPass = false;}
                            break;
                        case '2':
                            if (__intGender != 0){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by gender of water ratio");
                                isPass = false;}
                            break;
                    }
                    break;
                case '4':
                    switch (str.charAt(2)){
                        case '1':
                            if (__intGender != 1){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by gender of muscle mass");
                                isPass = false;}
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(__dbHeight < 160)){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by height of muscle mass");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(__dbHeight >= 160 && dbWeight < 170)){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by height of muscle mass");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(__dbHeight >= 170)){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by height of muscle mass");
                                        isPass = false;}
                                    break;
                            }
                            break;
                        case '2':
                            if (__intGender != 0){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by gender of muscle mass");
                                isPass = false;}
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(__dbHeight < 150)){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by height of muscle mass");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(__dbHeight >= 150 && dbWeight < 160)){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by height of muscle mass");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(__dbHeight >= 160)){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by height of muscle mass");
                                        isPass = false;}
                                    break;
                            }
                            break;
                    }
                    break;
                case '5':
                    switch (str.charAt(2)){
                        case '1':
                            if (__intGender != 1){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by gender of bone mineral mass");
                                isPass = false;}
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbWeight < 60)){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by weight of bone mineral mass");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbWeight >= 60 && dbWeight < 75)){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by weight of bone mineral mass");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbWeight >= 75)){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by weight of bone mineral mass");
                                        isPass = false;}
                                    break;
                            }
                            break;
                        case '2':
                            if (__intGender != 0){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by gender of bone mineral mass");
                                isPass = false;}
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbWeight < 45)){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by weight of bone mineral mass");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbWeight >= 45 && dbWeight < 60)){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by weight of bone mineral mass");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbWeight >= 60)){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by weight of bone mineral mass");
                                        isPass = false;}
                                    break;
                            }
                            break;
                    }
                    break;
                case '6':
                    switch (str.charAt(4)){
                        case '1':
                            if ( !(dbBMI < 18.5)){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by range of BMI");
                                isPass = false;}
                            break;
                        case '2':
                            if ( !(dbBMI >= 18.5 && dbBMI < 24)){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by range of BMI");
                                isPass = false;}
                            break;
                        case '3':
                            if ( !(dbBMI >= 24 && dbBMI < 27)){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by range of BMI");
                                isPass = false;}
                            break;
                        case '4':
                            if ( !( dbBMI <= 27)){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by range of BMI");
                                isPass = false;}
                            break;
                    }
                    break;
                case '7':
                    switch (str.charAt(4)){
                        case '1':
                            if ( !(dbVFL >= 0 && dbVFL < 10)){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by range of VFL");
                                isPass = false;}
                            break;
                        case '2':
                            if ( !(dbVFL >= 10 && dbVFL < 15)){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by range of VFL");
                                isPass = false;}
                            break;
                        case '3':
                            if ( !(dbVFL >= 15)){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by range of VFL");
                                isPass = false;}
                            break;
                    }
                    break;
                case '8':
                    switch (str.charAt(2)) {
                        case '1':
                            if (__intGender != 1){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by gender of Protein");
                                isPass = false;}
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbProtein < 20) || dbProtein == 0){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of Protein");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbProtein >= 20 && dbProtein < 30) && dbProtein != 0){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of Protein");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbProtein >= 30)){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of Protein");
                                        isPass = false;}
                                    break;
                            }
                            break;
                        case '2':
                            if (__intGender != 0){
                                logger.warn(strFailMsg);
                                logger.debug("Reason: data compare fail by gender of Protein");
                                isPass = false;}
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbProtein < 15)){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of Protein");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbProtein >= 15 && dbProtein < 30) && dbProtein != 0){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of Protein");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbProtein >= 30)){
                                        logger.warn(strFailMsg);
                                        logger.debug("Reason: data compare fail by range of Protein");
                                        isPass = false;}
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }

        if (isPass) {
            logger.info(strPassMsg);
        }
        clickBack();
    }

    public void CheckTips() throws Exception {
        clickTips();

        int countPass = 0;
        FnPicEvent fnPicEvent= new FnPicEvent();
        ArrayList<BufferedImage> alExpectImg = new ArrayList<>();
        for (int i=0; i<__imgTestPNG.length; i++){
            alExpectImg.add(ImageIO.read(new File(__imgTestDir+__imgTestPNG[i])));
        }

        //compare Tips by screenshot
        for (int i=0; i<alExpectImg.size(); i++){
            fnPicEvent.screenShot();
            fnPicEvent.cutImage();

            if (fnPicEvent.compareImages(alExpectImg.get(i))){
                countPass ++;
            }
            touchAction.press(PointOption.point(AppWidth*2/3,AppHeight/2)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(AppWidth/3,AppHeight/2)).release().perform();
            Thread.sleep(2000);
        }

        if (countPass == alExpectImg.size()){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Pictures are different from expect data");
        }
        clickBack();
    }


}
