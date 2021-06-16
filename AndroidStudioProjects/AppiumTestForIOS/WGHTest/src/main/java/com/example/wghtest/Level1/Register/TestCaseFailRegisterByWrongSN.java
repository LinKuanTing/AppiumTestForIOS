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

public class TestCaseFailRegisterByWrongSN extends WDFnL1Register {
    protected static String _strSN,_strRegisterName,_strNickName,_strMail,_strPhone,_strRegisterPwd,_strRegisterPwdCfm;

    protected static String _strCHRegisterMsg = "無相關資料，請確認是否輸入正確。";
    protected static String _strENRegisterMsg = "No information, please confirm whether the input is correct.";

    protected static String _strFileName = "TestDataFailRegisterByWrongSN";
    protected static String _strFilePath;


    public void execute() throws IOException {
        Logger logger = LoggerFactory.getLogger(TestCaseFailRegisterByWrongSN.class);

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

        //輸入錯誤序號確認後 跳出訊息
        _etSN.sendKeys(_strSN+"\n");

        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);


        if (iosDriver.findElementByXPath(__strAlertMsgXpath).getText().equals(_strCHRegisterMsg)) {
            logger.info(strPassMsg);
        }else if (iosDriver.findElementByXPath(__strAlertMsgXpath).getText().equals(_strENRegisterMsg)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
        }

        iosDriver.switchTo().alert().accept();

        clickTitleCancel();

    }
}
