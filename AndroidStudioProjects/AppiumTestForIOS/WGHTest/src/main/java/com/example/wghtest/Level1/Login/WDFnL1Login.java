package com.example.wghtest.Level1.Login;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.ios.IOSElement;

import static com.example.wghtest.WGHTestBase.iosDriver;
import static com.example.wghtest.WGHTestBase.timeOut;

public class WDFnL1Login {
    protected IOSElement _etUserName,_etUserPwd;
    protected IOSElement _btnLogin,_btnRegister,_btnForgetPwd;

    private static String __strUserNameXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTextField";
    private static String __strUserPwdXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeSecureTextField\n";
    private static String __strLoginID = "Login";
    private static String __strRegisterID = "Register";
    private static String __strForgePwdID = "Forgot";
    private static String __strAlertOkID = "Ok";


    public WDFnL1Login(){
        this._etUserName = (IOSElement) iosDriver.findElementByXPath(__strUserNameXpath);
        this._etUserPwd = (IOSElement) iosDriver.findElementByXPath(__strUserPwdXpath);
        this._btnLogin = (IOSElement) iosDriver.findElementByAccessibilityId(__strLoginID);
        this._btnRegister = (IOSElement) iosDriver.findElementByAccessibilityId(__strRegisterID);
        this._btnForgetPwd = (IOSElement) iosDriver.findElementByAccessibilityId(__strForgePwdID);
    }

    public void login(String strName,String strPwd){
        _etUserName.clear();
        _etUserName.sendKeys(strName);
        _etUserPwd.clear();
        _etUserPwd.sendKeys(strPwd);

        _btnLogin.click();

    }



    public void clickRegister(){
        _btnRegister.click();
    }

    public void clickForgetPwd(){
        _btnForgetPwd.click();
    }


}
