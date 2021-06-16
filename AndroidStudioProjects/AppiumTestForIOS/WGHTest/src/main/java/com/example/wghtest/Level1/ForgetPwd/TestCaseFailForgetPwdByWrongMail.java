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

public class TestCaseFailForgetPwdByWrongMail extends WDFnL1ForgetPwd {
    private String __strMail;
    private String __strCHMessage = "Invalid E-mail";
    private String __strENMEssage = "此信箱並無註冊帳號";

    private static String __strMessageXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeOther[4]/XCUIElementTypeOther[2]/XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeStaticText";

    private static String __strFileName = "TestDataFailForgetPwdByWrongMail";
    private static String __strFilePath;

    public void excute() throws IOException {
        Logger logger = LoggerFactory.getLogger(TestCaseFailForgetPwdByWrongMail.class);
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);

        ArrayList alDataContent = fnFileEvent.getContent(__strFilePath);

        __strMail = alDataContent.get(0).toString();

        query(__strMail);

        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);


        if (iosDriver.findElementByXPath(__strMessageXpath).getText().equals(__strCHMessage)) {
            logger.info(strPassMsg);
        } else if (iosDriver.findElementByXPath(__strMessageXpath).getText().equals(__strENMEssage)) {
            logger.info(strPassMsg);
        } else {
            logger.warn(strFailMsg);
        }

        iosDriver.switchTo().alert().accept();
    }

}
