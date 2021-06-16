package com.example.wghtest.Level2.QRcode;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL2QRcode {

    private static String __strOkID = "Ok";
    private static String __strOkXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeButton";
    private static String __strQRAccountXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeStaticText";


    public String getAccount(){
        return iosDriver.findElementByXPath(__strQRAccountXpath).getText();
    }

    public void clickOk(){
        iosDriver.findElementByAccessibilityId(__strOkID).click();
    }

}
