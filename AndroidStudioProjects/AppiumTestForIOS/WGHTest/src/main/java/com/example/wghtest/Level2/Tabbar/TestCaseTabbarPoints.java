package com.example.wghtest.Level2.Tabbar;

import com.example.wghtest.other.FnFileEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class TestCaseTabbarPoints extends WDFnL2TabbarPoint {

    private static String __strFileName;
    private static String __strFilePath;

    private static String __strCloseID = "Back";

    Logger logger = LoggerFactory.getLogger(TestCaseTabbarPoints.class);

    public void CheckTermOfUse() throws InterruptedException, IOException {
        clickTerms();
        Thread.sleep(20000);

        //取得目前所有窗口
        Set winContext = iosDriver.getContextHandles();
        //System.out.println(winContext);

        //切換窗口取得webview內文
        iosDriver.context(winContext.toArray()[winContext.size()-1].toString());
        String strReadContent = iosDriver.getPageSource();
        iosDriver.context(winContext.toArray()[0].toString());


        //get File Webview Content
        __strFileName = "points_en";
        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
        String strExpectContent = "";
        while (br.ready()){
            String strReadLine = br.readLine();
            strExpectContent += strReadLine;
        }

        Document docRead = Jsoup.parse(strReadContent);
        Document docExpect = Jsoup.parse(strExpectContent);

        strReadContent = docRead.text().trim();
        strExpectContent = docExpect.text().trim();

        if (strReadContent.equals(strExpectContent)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Terms of Use's content is different expect data and read one");
            System.out.println("Expect: " + strExpectContent );
            System.out.println(" Read : " + strReadContent);
        }

        iosDriver.findElementByAccessibilityId(__strCloseID).click();

    }


    public void CheckWowPoints(){
        int wowPoints = getWowPoints();
        int earnedPoints = getEarnedPoints();
        int usefPoints = getUsedPoints();

        if (wowPoints == (earnedPoints + usefPoints)) {
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Points display is wrong");
            logger.debug("Wow Points: " + wowPoints);
            logger.debug("Earned cut Used Points are: " + (earnedPoints - usefPoints));
        }
    }

    public void CheckEarnedPoints() throws InterruptedException {
        clickEarned();
        int titleEarnedPoints = getEarnedPoints();
        int earnedPoints = getEarnedTotalPoints();

        if (titleEarnedPoints == earnedPoints){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Points data is different");
            logger.debug("Title Earned Points: " + titleEarnedPoints);
            logger.debug(" ScrollView Points : " + earnedPoints);
        }

    }

    public void CheckUsedPoint(){
        clickUsed();
        int titleUsedPoints = getUsedPoints();
        int usedPoints = getUsedTotalPoints();

        if (titleUsedPoints == usedPoints){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Points data is different");
            logger.debug("Title Used Points: " + titleUsedPoints);
            logger.debug("ScrollView Points: "+ usedPoints);
        }

    }

    public void CheckDeadlinePoints(){
        clickDeadline();
        int titleTotalPoints = getWowPoints();
        int deadlinePoints = getDeadlineTotalPoints();

        if (titleTotalPoints == deadlinePoints){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Points data is different");
            logger.debug("WowGoHealth Points: " + titleTotalPoints);
            logger.debug(" Deadline  Points : " + deadlinePoints);
        }
        
    }


}
