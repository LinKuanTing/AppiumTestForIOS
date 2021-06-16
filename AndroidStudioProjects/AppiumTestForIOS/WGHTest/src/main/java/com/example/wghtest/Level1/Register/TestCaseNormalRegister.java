package com.example.wghtest.Level1.Register;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level1.FnMailEvent;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.iosDriver;
import static com.example.wghtest.WGHTestBase.timeOut;

public class TestCaseNormalRegister extends WDFnL1Register {
    protected static String _strSN,_strRegisterName,_strNickName,_strMail,_strPhone,_strRegisterPwd,_strRegisterPwdCfm;

    protected static String _strCHRegisterTitleMsg = "註冊完成";
    protected static String _strCHRegisterMsg = "請至您註冊帳號時所填寫的電子信箱中收取會員確認信，依信中指示啟用帳號";
    protected static String _strENRegisterTitleMsg = "Registration Successful";
    protected static String _strENRegisterMsg = "In order to activate your account,please go to the mailbox to receive account confirmation.";

    private static String __strFileActTxt = "AccountByRegister";
    private static String __strFileName = "TestDataNormalRegister";
    private static String __strFilePath;

    private static String __strTitleMsgXpath = "//XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeStaticText[1]";
    private static String __strMsgXpath = "//XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeStaticText[2]";



    public void excute() throws Exception{
        Logger logger = LoggerFactory.getLogger(TestCaseNormalRegister.class);


        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        _strSN = alData.get(0).toString();
        _strRegisterName = alData.get(1).toString();
        _strNickName = alData.get(2).toString();
        _strMail = alData.get(3).toString();
        _strPhone = alData.get(4).toString();
        _strRegisterPwd = alData.get(5).toString();
        _strRegisterPwdCfm = alData.get(6).toString();

        while (true){
            //設定註冊帳號為 GSHTesterLin000 ~ GSHTesterLin999
            String idx = String.format("%03d",(int)(Math.random()*1000));
            _strRegisterName += idx;
            __strFilePath = fnFileEvent.getPath(__strFileActTxt);
            //判斷帳號是否曾創過
            boolean isExist = false;
            FileInputStream fis = new FileInputStream(__strFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
            while (br.ready()){
                if (_strRegisterName.equals(br.readLine())){
                    //帳號若已存在過 將設為初始值 等待下次迴圈抽取流水號
                    _strRegisterName = (String) alData.get(1);
                    isExist = true;
                    break;
                }
            }
            //帳號重再重新回圈
            //帳號不存在 離開迴圈
            if (isExist){
                continue;
            }
            else {
                break;
            }
        }


        register(_strSN,_strRegisterName,_strNickName,_strMail,_strPhone,_strRegisterPwd,_strRegisterPwdCfm);
        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);

        //跳出使用條款同意訊息 點擊同意
        iosDriver.switchTo().alert().accept();
        //等待註冊完成跳出成功訊息
        Thread.sleep(20000);


        FnMailEvent fnMailEvent = new FnMailEvent();
        if (iosDriver.findElementByXPath(__strTitleMsgXpath).getText().equals(_strCHRegisterTitleMsg) && iosDriver.findElementByXPath(__strMsgXpath).getText().equals(_strCHRegisterMsg)) {

            FileWriter fw = new FileWriter(__strFilePath,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(_strRegisterName + "\r\n");
            bw.write(_strRegisterPwd + "\r\n");
            bw.flush();
            bw.close();

            //啟動帳號
            fnMailEvent.getVerification();

            iosDriver.switchTo().alert().accept();
            logger.info(strPassMsg);
        }else if (iosDriver.findElementByXPath(__strTitleMsgXpath).getText().equals(_strENRegisterTitleMsg) && iosDriver.findElementByXPath(__strMsgXpath).getText().equals(_strENRegisterMsg)){

            FileWriter fw = new FileWriter(__strFilePath,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(_strRegisterName + "\r\n");
            bw.write(_strRegisterPwd + "\r\n");
            bw.flush();
            bw.close();

            //啟動帳號
            fnMailEvent.getVerification();

            iosDriver.switchTo().alert().accept();
            logger.info(strPassMsg);
        }else {
            iosDriver.switchTo().alert().accept();
            clickTitleCancel();
            logger.warn(strFailMsg);
        }




    }


}
