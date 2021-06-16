package com.example.wghtest.Level1.Login;

import com.example.wghtest.other.FnFileEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.iosDriver;
import static com.example.wghtest.WGHTestBase.timeOut;


public class TestCaseFailLoginByWrongAct extends WDFnL1Login{
    private static String __strUserName,__strUserPwd;

    private static String __strENMessage = "Account does not exist";
    private static String __strCHMessage = "此會員帳號不存在，請確認後再重新登入";

    private static String __strFileName = "TestDataFailLoginByWrongAct";
    private static String __strFilePath;

    //private static String __strAlertMsgXpath = "//XCUIElementTypeStaticText[@name=\"Account does not exist\"]";
    private static String __strAlertMsgXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeOther[4]/XCUIElementTypeOther[2]/XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeStaticText";


    public void excute() throws IOException {
        Logger logger = LoggerFactory.getLogger(TestCaseFailLoginByWrongAct.class);

        __strFilePath = new FnFileEvent().getPath(__strFileName);
        ArrayList alData = new FnFileEvent().getContent(__strFilePath);

        __strUserName = alData.get(0).toString();
        __strUserPwd = alData.get(1).toString();



        login(__strUserName,__strUserPwd);

        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);

        if (iosDriver.findElementByXPath(__strAlertMsgXpath).getText().equals(__strENMessage)){
            logger.info(strPassMsg);
        }else if (iosDriver.findElementByXPath(__strAlertMsgXpath).getText().equals(__strCHMessage)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
        }

        iosDriver.switchTo().alert().accept();

    }
}
