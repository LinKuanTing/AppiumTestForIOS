package com.example.wghtest.Level3.StressMood;

import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3StressMood extends WDFnBaseMeasureMenuItems {
    protected IOSElement _btnOption;

    private static String __strOptionModeXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[3]";
    private static String __strTrainingRecordID = "calendar icon";


    public WDFnL3StressMood(){
        this._btnOption = (IOSElement) iosDriver.findElementByXPath(__strOptionModeXpath);
    }

    public void clickWellnessIndexes(){
        int pointX = _btnOption.getLocation().getX()+_btnOption.getSize().getWidth()/4;
        int pointY = _btnOption.getLocation().getY()+_btnOption.getSize().getHeight()/2;
        touchAction.tap(PointOption.point(pointX,pointY)).release().perform();
    }

    public void clickBreathTraining(){
        int pointX = _btnOption.getLocation().getX()+_btnOption.getSize().getWidth()*3/4;
        int pointY = _btnOption.getLocation().getY()+_btnOption.getSize().getHeight()/2;
        touchAction.tap(PointOption.point(pointX,pointY)).release().perform();
    }

    public void clickIndexesRecord() throws InterruptedException {
        clickRecord();
        try{
            iosDriver.switchTo().alert().accept();
        }catch (Exception eNoFindElement){ }
    }

    public void clickTrainingRecord(){
        iosDriver.findElementByAccessibilityId(__strTrainingRecordID).click();
        try{
            iosDriver.switchTo().alert().accept();
        }catch (Exception eNoFindElement){ }
    }
}
