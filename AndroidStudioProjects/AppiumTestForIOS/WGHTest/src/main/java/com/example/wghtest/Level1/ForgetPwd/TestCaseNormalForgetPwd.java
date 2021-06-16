package com.example.wghtest.Level1.ForgetPwd;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level1.FnMailEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.iosDriver;
import static com.example.wghtest.WGHTestBase.timeOut;

public class TestCaseNormalForgetPwd extends WDFnL1ForgetPwd {
    private static String __strMail;
    private String __strCHMessage = "Please go to the mailbox to receive account info";
    private String __strENMEssage = "請至信箱接收帳號密碼";

    private static String __strMessageXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeOther[4]/XCUIElementTypeOther[2]/XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeStaticText";

    private static String __strFileName = "TestDataNormalForgetPwd";
    private static String __strFilePath;

    public void excute() throws Exception {
        Logger logger = LoggerFactory.getLogger(TestCaseNormalForgetPwd.class);
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);

        ArrayList alDataContent = fnFileEvent.getContent(__strFilePath);

        __strMail = alDataContent.get(0).toString();

        query(__strMail);

        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);


        FnMailEvent fnMailEvent = new FnMailEvent();
        if (iosDriver.findElementByXPath(__strMessageXpath).getText().equals(__strCHMessage)) {
            if (fnMailEvent.isExistForgetPwdMail()) {
                logger.info(strPassMsg);
            }else {
                logger.warn(strFailMsg);
                logger.debug("Reason: Can't get the forgot password mail.");
            }
        } else if (iosDriver.findElementByXPath(__strMessageXpath).getText().equals(__strENMEssage)) {
            if (fnMailEvent.isExistForgetPwdMail()){
                logger.info(strPassMsg);
            }else {
                logger.warn(strFailMsg);
                logger.debug("Reason: Can't get the forgot password mail.");
            }
        } else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Message content is wrong.");
        }


        iosDriver.switchTo().alert().accept();
    }
}
