package com.example.wghtest.Level2.Personal;

import com.example.wghtest.Level2.WDFnBaseInfoSetting;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.ios.IOSElement;

import static com.example.wghtest.WGHTestBase.iosDriver;
import static com.example.wghtest.WGHTestBase.timeOut;


public class WDFnL2Measure extends WDFnBaseInfoSetting {
    protected IOSElement _etBPMeasure,_etBGMeasure;
    protected IOSElement _btnMgdl,_btnMmolL;

    private static String __strBPMeasureXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeTextField[1]";
    private static String __strBGMeasureXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeTextField[2]";
    private static String __strMgdlID = "name == 'mg/dl'";
    private static String __strMmolLID = "name == 'mmol/L'";


    public void setMeasurements() throws InterruptedException {
        String times = String.valueOf((int)(Math.random()*30+1));

        _etBPMeasure = (IOSElement) iosDriver.findElementByXPath(__strBPMeasureXpath);
        _etBGMeasure = (IOSElement) iosDriver.findElementByXPath(__strBGMeasureXpath);

        _etBPMeasure.clear();
        _etBPMeasure.setValue(times+"\n");
        _etBGMeasure.clear();
        _etBGMeasure.setValue(times+"\n");


        clickSave();
        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);

    }

    public int getBPMeasurements(){
        return Integer.parseInt(_etBPMeasure.getText());
    }

    public int getBGMeasurements(){
        return Integer.parseInt(_etBGMeasure.getText());
    }

    public void clickUnitsMgdl(){
        _btnMgdl = (IOSElement) iosDriver.findElementByIosNsPredicate(__strMgdlID);
        _btnMgdl.click();
    }

    public void clickUnitsMmolL(){
        _btnMmolL = (IOSElement) iosDriver.findElementByIosNsPredicate(__strMmolLID);
        _btnMmolL.click();
    }

}
