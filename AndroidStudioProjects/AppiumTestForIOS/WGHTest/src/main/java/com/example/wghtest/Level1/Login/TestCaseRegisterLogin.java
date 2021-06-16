package com.example.wghtest.Level1.Login;

import com.example.wghtest.other.FnFileEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.Level1.Register.WDFnL1Register.strPrefixWord;
import static com.example.wghtest.WGHTestBase.iosDriver;
import static com.example.wghtest.WGHTestBase.timeOut;

public class TestCaseRegisterLogin extends WDFnL1Login{
    private String __strUserName,__strUserPwd;

    private String __strFileName = "AccountByRegistered";
    private String __strFilePath;

    private String __strPlanTitleXpath = "//XCUIElementTypeOther[@name=\"Plan\"]";
    private String __strHeightXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeTextField[2]";
    private String __strInitialWeightXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeTextField[3]";
    private String __strTargetWeightXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeTextField[4]";
    private String __strSaveXpath = "//XCUIElementTypeButton[@name=\"Save\"]";
    private String __strMenuTitleXpath = "//XCUIElementTypeOther[@name=\"Menu\"]";



    public void excute() throws IOException, InterruptedException {
        Logger logger = LoggerFactory.getLogger(TestCaseRegisterLogin.class);
        __strFilePath = new FnFileEvent().getPath(__strFileName);

        ArrayList alData = new FnFileEvent().getContent(__strFilePath);

        //取檔案內最後註冊的帳號登入
        int idx = alData.size();
        __strUserName = strPrefixWord + alData.get(idx-2).toString();
        __strUserPwd = alData.get(idx-1).toString();


        login(__strUserName,__strUserPwd);

        Thread.sleep(3000);

        //跳出是否刪除原先帳號資料
        try {
            iosDriver.switchTo().alert().accept();
        }catch (Exception eNoFindElement){ }

        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);

        //輸入剛註冊帳號資料
        try {
            iosDriver.findElementByXPath(__strPlanTitleXpath);

            //設定註冊後的會員資料
            iosDriver.findElementByXPath(__strHeightXpath).sendKeys("170");
            iosDriver.findElementByXPath(__strInitialWeightXpath).sendKeys("70");
            iosDriver.findElementByXPath(__strTargetWeightXpath).sendKeys("60");
            iosDriver.findElementByXPath(__strSaveXpath).click();
            iosDriver.switchTo().alert().accept();
        }catch (Exception eNoFindElement){ }


        try {
            iosDriver.findElementByXPath(__strMenuTitleXpath);
            logger.info(strPassMsg);
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
        }


    }


    public void registerLogin() throws IOException, InterruptedException {
        __strFilePath = new FnFileEvent().getPath(__strFileName);

        ArrayList alData = new FnFileEvent().getContent(__strFilePath);

        //取檔案內最後註冊的帳號登入
        int idx = alData.size();
        __strUserName = strPrefixWord + alData.get(idx-2).toString();
        __strUserPwd = alData.get(idx-1).toString();


        login(__strUserName,__strUserPwd);

        Thread.sleep(3000);

        //跳出是否刪除原先帳號資料
        try {
            iosDriver.switchTo().alert().accept();
        }catch (Exception eNoFindElement){ }

        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);

        //輸入剛註冊帳號資料
        try {
            iosDriver.findElementByXPath(__strPlanTitleXpath);

            //設定註冊後的會員資料
            iosDriver.findElementByXPath(__strHeightXpath).sendKeys("170");
            iosDriver.findElementByXPath(__strInitialWeightXpath).sendKeys("70");
            iosDriver.findElementByXPath(__strTargetWeightXpath).sendKeys("60");
            iosDriver.findElementByXPath(__strSaveXpath).click();
            iosDriver.switchTo().alert().accept();
        }catch (Exception eNoFindElement){ }



    }


}
