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


public class TestCaseFailLoginByWrongPwd extends WDFnL1Login{
    private String __strUserName,__strUserPwd;

    private String __strENMessage = "Wrong password. Please login again.  (prompt：If you entered wrong password 5 times, your account will be locked)";
    private String __strCHMessage = "密碼錯誤，請重新輸入。  (提示：若密碼錯誤超過5次系統將自動鎖定)";

    private static String __strFileName = "TestDataFailLoginByWrongPwd";
    private static String __strFilePath;

    private static String __strAlertMsgXpah = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeOther[4]/XCUIElementTypeOther[2]/XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeStaticText";


    public void excute() throws IOException{
        Logger logger = LoggerFactory.getLogger(TestCaseFailLoginByWrongPwd.class);
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);

        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        __strUserName = alData.get(0).toString();
        __strUserPwd = alData.get(1).toString();


        login(__strUserName,__strUserPwd);

        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);


        if (iosDriver.findElementByXPath(__strAlertMsgXpah).getText().equals(__strENMessage)){
            logger.info(strPassMsg);
        }else if (iosDriver.findElementByXPath(__strAlertMsgXpah).getText().equals(__strCHMessage)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
        }

        iosDriver.switchTo().alert().accept();

    }


}
