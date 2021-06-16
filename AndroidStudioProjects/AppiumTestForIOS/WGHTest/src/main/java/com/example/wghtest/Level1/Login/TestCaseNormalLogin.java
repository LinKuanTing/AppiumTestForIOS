package com.example.wghtest.Level1.Login;

import com.example.wghtest.other.FnFileEncrypt;
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

public class TestCaseNormalLogin extends WDFnL1Login{
    private String __strUserName,__strUserPwd;

    private String __strFileName = "TestDataNormalLogin";
    private String __strFilePath;

    private String __strMenuTitleXpath = "//XCUIElementTypeOther[@name=\"Menu\"]";


    public TestCaseNormalLogin() throws Exception{
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        FnFileEncrypt fnFileEncrypt = new FnFileEncrypt();
        byte[] bytes = fnFileEncrypt.loadFileContent(__strFilePath);
        String[] strContent = fnFileEncrypt.Decryptor(bytes).split("\n");
        __strUserName = strContent[0];
        __strUserPwd = strContent[1];


    }

    public void excute() throws IOException, InterruptedException {
        Logger logger = LoggerFactory.getLogger(TestCaseNormalLogin.class);


        login(__strUserName,__strUserPwd);

        Thread.sleep(5000);

        //跳出是否刪除原先帳號資料
        try {
            iosDriver.switchTo().alert().accept();
        }catch (Exception eNoFindElement){ }

        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);


        try {
            iosDriver.findElementByXPath(__strMenuTitleXpath);
            logger.info(strPassMsg);
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
        }

    }


    public void normalLogin() throws IOException, InterruptedException {
        __strFilePath = new FnFileEvent().getPath(__strFileName);


        login(__strUserName,__strUserPwd);

        Thread.sleep(5000);

        //跳出是否刪除原先帳號資料
        try {
            iosDriver.switchTo().alert().accept();
        }catch (Exception eNoFindElement){ }

        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);


    }

}
