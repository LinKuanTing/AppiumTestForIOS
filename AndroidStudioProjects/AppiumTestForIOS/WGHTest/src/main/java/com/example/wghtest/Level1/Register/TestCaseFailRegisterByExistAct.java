package com.example.wghtest.Level1.Register;

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

public class TestCaseFailRegisterByExistAct extends WDFnL1Register{
    protected static String _strSN,_strRegisterName,_strNickName,_strMail,_strPhone,_strRegisterPwd,_strRegisterPwdCfm;

    protected static String _strCHRegisterTitleMsg = "錯誤";
    protected static String _strCHRegisterMsg = "帳號重複";
    protected static String _strENRegisterTitleMsg = "Error";
    protected static String _strENRegisterMsg = "Account already exists";

    protected static String _strFileName = "TestDataFailRegisterByExistAct";
    protected static String _strFilePath;

    private static String __strTitleMsgXpath = "//XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeStaticText[1]";
    private static String __strMsgXpath = "//XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeStaticText[2]";


    public void excute() throws IOException, InterruptedException {
        Logger logger = LoggerFactory.getLogger(TestCaseFailRegisterByExistAct.class);

        FnFileEvent fnFileEvent = new FnFileEvent();
        _strFilePath =fnFileEvent.getPath(_strFileName);
        ArrayList alData = fnFileEvent.getContent(_strFilePath);

        _strSN = alData.get(0).toString();
        _strRegisterName = alData.get(1).toString();
        _strNickName = alData.get(2).toString();
        _strMail = alData.get(3).toString();
        _strPhone = alData.get(4).toString();
        _strRegisterPwd = alData.get(5).toString();
        _strRegisterPwdCfm = alData.get(6).toString();

        register(_strSN,_strRegisterName,_strNickName,_strMail,_strPhone,_strRegisterPwd,_strRegisterPwdCfm);

        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);


        //跳出隱私條款同意訊息
        iosDriver.switchTo().alert().accept();
        Thread.sleep(10000);


        if (iosDriver.findElementByXPath(__strTitleMsgXpath).getText().equals(_strCHRegisterTitleMsg) && iosDriver.findElementByXPath(__strMsgXpath).getText().equals(_strCHRegisterMsg)) {
            logger.info(strPassMsg);
        }else if (iosDriver.findElementByXPath(__strTitleMsgXpath).getText().equals(_strENRegisterTitleMsg) && iosDriver.findElementByXPath(__strMsgXpath).getText().equals(_strENRegisterMsg)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
        }

        iosDriver.switchTo().alert().accept();

        clickTitleCancel();

    }
}
