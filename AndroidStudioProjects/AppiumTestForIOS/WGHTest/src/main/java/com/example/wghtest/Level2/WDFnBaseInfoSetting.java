package com.example.wghtest.Level2;

import org.slf4j.Logger;

import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnBaseInfoSetting {
    protected IOSElement _btnSave,_btnMenu;
    protected IOSElement _btnSettingPage;

    protected int _locX,_locY;
    protected int _sizeW,_sizeH;

    private static String __strSettingPageXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther";

    private static String __strSaveID = "Save";
    private static String __strSaveName = "name == 'Save'";
    private static String __strMenuXpath = "(//XCUIElementTypeButton[@name=\"Menu\"])[1]";


    protected TouchAction touchAction = new TouchAction(iosDriver);


    public WDFnBaseInfoSetting() {
        this._btnSave = (IOSElement) iosDriver.findElementByAccessibilityId(__strSaveID);
        this._btnMenu = (IOSElement) iosDriver.findElementByXPath(__strMenuXpath);
        this._btnSettingPage = (IOSElement) iosDriver.findElementByXPath(__strSettingPageXpath);
        _locX = _btnSettingPage.getLocation().getX();
        _locY = _btnSettingPage.getLocation().getY();
        _sizeW = _btnSettingPage.getSize().width;
        _sizeH = _btnSettingPage.getSize().height;

    }


    public void clickSave() throws InterruptedException {
        try {
            _btnSave.click();
        }catch (Exception eNoFindElement){
            _btnSave = (IOSElement) iosDriver.findElementByIosNsPredicate(__strSaveName);
            _btnSave.click();
        }

        Thread.sleep(5000);
    }

    public void backToMenu(){
        _btnMenu.click();
    }

    public void clickPersonal(){
        int x = _locX + _sizeW/6;
        int y = _locY + _sizeH/2;
        touchAction.tap(PointOption.point(x,y)).release().perform();
    }

    public void clickHealthPlan(){
        int x = _locX + _sizeW/6*3;
        int y = _locX + _sizeH/2;
        touchAction.tap(PointOption.point(x,y)).release().perform();
    }

    public void clickMeasure(){
        int x = _locX + _sizeW/6*5;
        int y = _locY + _sizeH/2;
        touchAction.tap(PointOption.point(x,y)).release().perform();
    }
}
