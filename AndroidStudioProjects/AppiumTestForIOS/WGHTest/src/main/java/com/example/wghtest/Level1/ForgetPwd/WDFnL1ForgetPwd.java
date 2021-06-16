package com.example.wghtest.Level1.ForgetPwd;

import io.appium.java_client.ios.IOSElement;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL1ForgetPwd {
    protected static IOSElement _etMail;
    protected static IOSElement _btnQuery;

    private static String __strMailXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTextField";
    private static String __strQueryXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeButton[2]";


    public WDFnL1ForgetPwd() {
        this._etMail = (IOSElement) iosDriver.findElementByXPath(__strMailXpath);
        this._btnQuery = (IOSElement) iosDriver.findElementByXPath(__strQueryXpath);
    }


    public void query(String strMail) {
        _etMail.clear();
        _etMail.sendKeys(strMail);
        _btnQuery.click();
    }
}
