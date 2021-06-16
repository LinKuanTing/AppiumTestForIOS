package com.example.wghtest.Level3.Ouendan;

import com.example.wghtest.other.FnFileEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class TestCaseOuendan extends WDFnL3Ouendan {
    private static String __strName,__strCode,__strPassword;

    private static String __strEncourageMsg = "Encourage TestDemo ";
    private static String __strReplyMsg = "Reply TestDemo ";

    private static String __strFileName;
    private static String __strFilePath;

    Logger logger;


    public TestCaseOuendan(){
        logger = LoggerFactory.getLogger(TestCaseOuendan.class);
        __strName = "1911261134";
        __strCode = "c90";
        __strPassword = "144";
    }

    public void CreateOuendan(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmm");
        Date date = new Date();
        String strDate = dateFormat.format(date);

        //創建加油團
        this.__strName = strDate;
        createOuendan(__strName);
        //取得創建成功訊息 並取擷取訊息中的團名 代碼 密碼
        char[] charArray = getAlertMsg().toCharArray();
        iosDriver.switchTo().alert().accept();

        ArrayList alInfo = new ArrayList();
        String strInfo = "";
        for (int i=0; i<charArray.length; i++){
            if (charArray[i] == '「'){
                for (int j=i+1; ;j++){

                    if (charArray[j] == '」'){
                        alInfo.add(strInfo);
                        strInfo = "";
                        break;
                    }else {
                        strInfo += charArray[j];
                    }

                }
            }
        }

        //儲存 團名 代碼 密碼
        if (!(__strName.equals(alInfo.get(0).toString()))){
            System.out.println("Create message ouendan name is different");
        }

        __strName = alInfo.get(0).toString();
        __strCode = alInfo.get(1).toString();
        __strPassword = alInfo.get(2).toString();

        //判斷團名是否出現在已加入加油團中
        ArrayList alOuendan = getOuendan();
        for (int i=0; i<alOuendan.size(); i++){
            if (alOuendan.get(i).toString().contains(__strName)){
                logger.info(strPassMsg);
                return;
            }
            if (i == alOuendan.size()-1){
                logger.warn(strFailMsg);
                logger.debug("Reason: Can't find Create Ouendan Name in the added Ouendan List");
            }
        }

    }

    public void QuitOuendan() throws InterruptedException {
        clickOuendan(__strName);
        new WDFnL3InOuendan().leaveOuendan();
        Thread.sleep(3000);
        iosDriver.switchTo().alert().accept();

        //判斷加油團列表是否存在退出的團名
        ArrayList alOuendan = getOuendan();
        if (alOuendan.size() == 0){
            logger.info(strPassMsg);
        }
        for (int i=0; i<alOuendan.size(); i++){
            if (alOuendan.get(i).toString().contains(__strName)) {
                logger.warn(strFailMsg);
                logger.debug("Reason: Leave Ouendan fail, because ouendan exist in listview.");
                return;
            }
            if (i == alOuendan.size()-1){
                logger.info(strPassMsg);
            }
        }



    }

    public void JoinOuendan() throws InterruptedException {
        clickOptionJoin();
        joinOuendan(__strCode,__strPassword);

        Thread.sleep(2000);
        char[] charArray = getAlertMsg().toCharArray();
        iosDriver.switchTo().alert().accept();

        String strName = "";
        for (int i=0; i<charArray.length; i++){
            if (charArray[i] == '「'){
                for (int j=i+1; ;j++){

                    if (charArray[j] == '」'){
                        break;
                    }else {
                        strName += charArray[j];
                    }

                }
            }
        }

        //判斷團名是否正確
        if (!strName.equals(__strName)){
            logger.warn(strFailMsg);
            logger.debug("Reason: Ouendan name is wrong with this code and password.");
        }

        //判斷是否正確加到已加入加油團列表中
        ArrayList alOuendan = getOuendan();
        for (int i=0; i<alOuendan.size(); i++){
            if (alOuendan.get(i).toString().contains(__strName)){
                logger.info(strPassMsg);
                return;
            }
            if (i == alOuendan.size()-1){
                logger.warn(strFailMsg);
                logger.debug("Reason: Join ouendan fail because ouendan listview can't find it.");
            }
        }
    }

    public void LatestNewsEncourage() throws InterruptedException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        String strDate = dateFormat.format(date);

        try {
            clickOuendan(__strName);
        }catch (Exception eNoFindElement){ }
        WDFnL3InOuendan wdFnL3InOuendan = new WDFnL3InOuendan();
        wdFnL3InOuendan.clickLatestNews();

        __strEncourageMsg += strDate;
        wdFnL3InOuendan.encourage(__strEncourageMsg);

        ArrayList alContent = wdFnL3InOuendan.getLatestNewsContent();
        for (int i=0; i<alContent.size(); i++){
            if (alContent.get(i).toString().equals(__strEncourageMsg)){
                logger.info(strPassMsg);
                return;
            }
            if (i == alContent.size()-1){
                logger.warn(strFailMsg);
                logger.debug("Reason: Can't find encourage message.");
            }
        }

    }

    public void LatestNewsClickGoodAndBad() throws InterruptedException {
        WDFnL3InOuendan wdFnL3InOuendan = new WDFnL3InOuendan();
        wdFnL3InOuendan.clickGoodAndBad();
        wdFnL3InOuendan.clickReplyContent();

        ArrayList alContent = wdFnL3InOuendan.getReplyContent();
        boolean isFindGoodMsg = false;
        boolean isFindBadMsg = false;
        for (int i=0; i<alContent.size(); i++){
            if (alContent.get(i).toString().contains("讚")){
                isFindGoodMsg = true;
            }
            if (alContent.get(i).toString().contains("噓")){
                isFindBadMsg = true;
            }
        }

        if (isFindGoodMsg && isFindBadMsg){
            logger.info(strPassMsg);
        } else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't find good or bad reply message.");
        }

        wdFnL3InOuendan.clickBackToInOuendan();
    }

    public void LatestNewsReply() throws InterruptedException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        String strDate = dateFormat.format(date);

        __strReplyMsg += strDate;
        WDFnL3InOuendan wdFnL3InOuendan = new WDFnL3InOuendan();
        wdFnL3InOuendan.clickReplyContent();
        wdFnL3InOuendan.reply(__strReplyMsg);

        ArrayList alContent = wdFnL3InOuendan.getReplyContent();
        for (int i=0; i<alContent.size(); i++){
            if (alContent.get(i).toString().equals(__strReplyMsg)){
                logger.info(strPassMsg);
                break;
            }
            if (i == alContent.size()-1){
                logger.warn(strFailMsg);
                logger.debug("Reason: Can't find reply message.");
            }
        }

        wdFnL3InOuendan.clickBackToInOuendan();
    }

    public void MyPartnerCheckOuendanInfo(){
        WDFnL3InOuendan wdFnL3InOuendan = new WDFnL3InOuendan();
        wdFnL3InOuendan.clickPartner();
        ArrayList alReadInfo = wdFnL3InOuendan.getOuendanInfo();
        //[0]->name  [1]->code  [2]->password
        ArrayList alExpectInfo = new ArrayList();
        alExpectInfo.add(__strName);
        alExpectInfo.add(__strCode);
        alExpectInfo.add(__strPassword);

        int sameNum = 0;
        for (int i=0; i<alReadInfo.size(); i++){
            if (alReadInfo.get(i).equals(alExpectInfo.get(i))){
                sameNum++;
            }
        }

        if (sameNum == 3){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Expect data: " + alExpectInfo);
            logger.debug(" Read data : " + alReadInfo);
        }
    }

    public void MyPartnerCheckPersonalInfo() throws IOException, InterruptedException {
        String strContent = new WDFnL3InOuendan().getPersonalContent();

        double dbInitialWeight = -1.0, dbTargetWeight = -1.0, dbCurrentWeight = -1.0;
        double dbBPAchieved = -1.0, dbBGAchieved = -1.0;

        __strFileName = "AccountInfo";
        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"Big5"));
        while (br.ready()){
            String strReadLine = br.readLine();
            if (strReadLine.contains("Initial Weight")){
                dbInitialWeight = Double.parseDouble(br.readLine());
            }else if (strReadLine.contains("Target Weight")){
                dbTargetWeight = Double.parseDouble(br.readLine());
            }else if (strReadLine.contains("Current Weight")){
                dbCurrentWeight = Double.parseDouble(br.readLine());
            }else if (strReadLine.contains("BP achieved")){
                dbBPAchieved = Double.parseDouble(br.readLine());
            }else if (strReadLine.contains("BG achieved")){
                dbBGAchieved = Double.parseDouble(br.readLine());
            }
        }

        if (dbInitialWeight<0 || dbTargetWeight<0 || dbCurrentWeight<0 || dbBPAchieved<0 || dbBGAchieved<0){
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't find enough data to compare personal information");
            new WDFnL3InOuendan().leaveOuendan();
            iosDriver.switchTo().alert().accept();
            return;
        }

        double dbRemainder = dbCurrentWeight - dbInitialWeight;
        double dbReachingRate = Math.abs(dbRemainder) / Math.abs(dbTargetWeight - dbInitialWeight) * 100;
        DecimalFormat df = new DecimalFormat("#.#");
        dbReachingRate = Double.parseDouble(df.format(dbReachingRate));

        String[] str = strContent.split("\n");
        //[0]->體重與達成率  [1]->今天步數與達成率  [2]->本週步數與達成率  [3]->本週血糖達成率  [4]->本週血糖達成率
        int sameNum = 0;
        for (int i=0; i<str.length;i++){
            switch(i){
                case 0:
                    String strUpDownMsg;
                    if (dbRemainder<0){
                        strUpDownMsg = "loss";
                    }else {
                        strUpDownMsg = "add";
                    }

                    if (str[i].contains(strUpDownMsg) && str[i].contains(df.format(dbRemainder).replace("-","")) && str[i].contains(String.valueOf(dbReachingRate))){
                        sameNum++;
                    }else {
                        System.out.print("Ouendan Personal Content Line 1 Expect Data: ");
                        System.out.println(strUpDownMsg +","+ dbRemainder +","+ dbReachingRate);
                    }

                    break;
                case 1:
                case 2:
                    break;
                case 3:
                    if (String.valueOf(dbBPAchieved).contains(".5")){
                        dbBPAchieved = Math.floor(dbBPAchieved);
                    }else {
                        dbBPAchieved = Math.round(dbBPAchieved);
                    }

                    if (str[i].contains(String.valueOf(dbBPAchieved))){
                        sameNum++;
                    }else {
                        System.out.print("Ouendan Personal Content Line 4 Expect Data: ");
                        System.out.println(dbBPAchieved);
                    }
                    break;
                case 4:
                    if (String.valueOf(dbBGAchieved).contains(".5")){
                        dbBGAchieved = Math.floor(dbBGAchieved);
                    }else {
                        dbBGAchieved = Math.round(dbBGAchieved);
                    }

                    if (str[i].contains(String.valueOf(dbBGAchieved))){
                        sameNum++;
                    }else {
                        System.out.print("Ouendan Personal Content Line 5 Expect Data: ");
                        System.out.println(dbBGAchieved);
                    }
                    break;
            }
        }

        if (sameNum == 3){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Personal content is different with expect data.");
        }

        new WDFnL3InOuendan().leaveOuendan();

        Thread.sleep(30000);
        iosDriver.switchTo().alert().accept();
    }


    public void test() throws IOException {
        double dbBPAchieved = -1.0, dbBGAchieved = -1.0;

        __strFileName = "AccountInfo";
        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"Big5"));
        while (br.ready()){
            String strReadLine = br.readLine();
            if (strReadLine.contains("BP achieved")){
                dbBPAchieved = Double.parseDouble(br.readLine());
            }else if (strReadLine.contains("BG achieved")){
                dbBGAchieved = Double.parseDouble(br.readLine());
            }
        }


        System.out.println(dbBGAchieved+","+dbBPAchieved);
        dbBGAchieved = Math.floor(dbBGAchieved);
        dbBPAchieved = Math.round(dbBPAchieved);
        System.out.println(dbBGAchieved+","+dbBPAchieved);
    }


}
