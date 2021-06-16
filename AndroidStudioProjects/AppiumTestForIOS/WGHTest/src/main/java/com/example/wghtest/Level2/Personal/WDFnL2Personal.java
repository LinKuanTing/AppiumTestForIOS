package com.example.wghtest.Level2.Personal;

import com.example.wghtest.Level2.WDFnBaseInfoSetting;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.ios.IOSElement;

import static com.example.wghtest.WGHTestBase.iosDriver;
import static com.example.wghtest.WGHTestBase.timeOut;

public class WDFnL2Personal extends WDFnBaseInfoSetting {
    protected IOSElement _etAccount,_etNickname;

    private static String __strFemaleID = "Female";
    private static String __strMaleID = "Male";

    private static String __strAccountXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther[2]/XCUIElementTypeScrollView/XCUIElementTypeTextField[1]";
    private static String __strNicknameXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther[2]/XCUIElementTypeScrollView/XCUIElementTypeTextField[3]";


    public WDFnL2Personal(){
        _etAccount = (IOSElement) iosDriver.findElementByXPath(__strAccountXpath);
        _etNickname = (IOSElement) iosDriver.findElementByXPath(__strNicknameXpath);
    }

    public void setPersonalInfo() throws InterruptedException {
        clickSave();
        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
    }

    public String getAccount(){
        return _etAccount.getText();
    }

    public String getNickname(){
        return _etNickname.getText();
    }

    public int getGender(){
        try {
            iosDriver.findElementByAccessibilityId(__strFemaleID);
            return 0;
        }catch (Exception eNoFindElement){
            iosDriver.findElementByAccessibilityId(__strMaleID);
            return 1;
        }
    }

}
