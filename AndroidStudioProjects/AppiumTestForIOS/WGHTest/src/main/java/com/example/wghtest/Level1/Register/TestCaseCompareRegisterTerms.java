package com.example.wghtest.Level1.Register;

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

public class TestCaseCompareRegisterTerms extends WDFnL1Register {

    private static String __strFileName = "conditions_en-us";
    private static String __strFilePath;

    private static String __strUseTermsBackXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeNavigationBar/XCUIElementTypeButton";

    public void excute() throws InterruptedException, IOException {
        Logger logger = LoggerFactory.getLogger(TestCaseCompareRegisterTerms.class);

        clickUserTerm();

        Thread.sleep(5000);
        //取得目前所有窗口
        Set winContext = iosDriver.getContextHandles();
        //System.out.println(winContext);

        //切換窗口取得webview內文
        iosDriver.context(winContext.toArray()[1].toString());
        Document doc = Jsoup.parse(iosDriver.getPageSource());
        iosDriver.context(winContext.toArray()[0].toString());

        //消除HTML標籤  只取文字部分
        String strWebviewContent = doc.text();
        //System.out.println(strWebviewContent);

        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath =fnFileEvent.getPath(__strFileName);
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

        iosDriver.findElementByXPath(__strUseTermsBackXpath).click();
        clickTitleCancel();
    }
}
