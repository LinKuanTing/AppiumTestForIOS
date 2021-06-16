package com.example.wghtest.Level1.ForgetPwd;

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

public class TestCaseFailForgetPwdByWrongFormat extends WDFnL1ForgetPwd {
    private String __strMail;
    private String __strCHMessage = "Need Valid E-mail";
    private String __strENMEssage = "請輸入正確的Email";

    private static String __strMessageXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText[1]";

    private static String __strFileName = "TestDataFailForgetPwdByWrongFormat";
    private static String __strFilePath;

    public void excute() throws IOException {
        Logger logger = LoggerFactory.getLogger(TestCaseFailForgetPwdByWrongFormat.class);
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);

        ArrayList alDataContent = fnFileEvent.getContent(__strFilePath);

        __strMail = alDataContent.get(0).toString();

        query(__strMail);

        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);


        if (iosDriver.findElementByXPath(__strMessageXpath).getText().equals(__strCHMessage)){
            logger.info(strPassMsg);
        }else if (iosDriver.findElementByXPath(__strMessageXpath).getText().equals(__strENMEssage)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
        }


    }



}
