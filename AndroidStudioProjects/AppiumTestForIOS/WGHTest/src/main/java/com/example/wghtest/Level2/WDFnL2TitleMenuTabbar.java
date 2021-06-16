package com.example.wghtest.Level2;

import io.appium.java_client.ios.IOSElement;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL2TitleMenuTabbar {
    protected static IOSElement _btnTitlePersonal,_btnTitleQR;
    protected static IOSElement _btnMenuWeight,_btnMenuSport,_btnMenuDiet;
    protected static IOSElement _btnMenuBloodPressure,_btnMenuBloodGlucose,_btnMenuSmartWristband;
    protected static IOSElement _btnMenuThermometer,_btnMenuStressMood,_btnMenuAdvice;
    protected static IOSElement _btnMenuShop,_btnMenuOuendan,_btnMenuProgress;
    protected static IOSElement _btnTabbarProgress,_btnTabbarPoints,_btnTabbarShop,_btnTabbarMenu;

    private static String __strTitlePersonalID = "btn profile";
    private static String __strTitleQRID = "btn qrcode";

    private static String __strMenuWeightID = "menuitem 02";
    private static String __strMenuSportID = "menuitem 03";
    private static String __strMenuDietID = "menuitem 04";
    private static String __strMenuBloodPressureID = "menuitem 07";
    private static String __strMenuBloodGlucoseID = "menuitem 10";
    private static String __strMenuSmartWristbandID = "menuitem 13";
    private static String __strMenuThermometerID = "menuitem 15";
    private static String __strMenuStressMoodID = "menuitem 14 ecg";
    private static String __strMenuAdviceID = "menuitem 06";
    private static String __strMenuShopID = "menuitem 12";
    private static String __strMenuOuendanID = "menuitem 08";
    private static String __strMenuProgressID = "menuitem 01";

    private static String __strTabbarProgressXpath = "//XCUIElementTypeButton[@name=\"Progress\"]";
    private static String __strTabbarPointsXpath = "//XCUIElementTypeButton[@name=\"WoＷ Points\"]";
    private static String __strTabbarShopXpath = "//XCUIElementTypeButton[@name=\"Store\"]";
    private static String __strTabbarMenuXapth = "//XCUIElementTypeButton[@name=\"Menu\"]";
    private static String __strTabbarProgressChXpath = "//XCUIElementTypeButton[@name=\"我的進度\"]";
    private static String __strTabbarPointsChXpath = "//XCUIElementTypeButton[@name=\"點數\"]";
    private static String __strTabbarShopChXpath = "//XCUIElementTypeButton[@name=\"商店\"]";
    private static String __strTabbarMenuChXapth = "//XCUIElementTypeButton[@name=\"功能\"]";


    public void clickTitlePersonal(){
        this._btnTitlePersonal = (IOSElement) iosDriver.findElementByAccessibilityId(__strTitlePersonalID);
        _btnTitlePersonal.click();
    }

    public void clickTitleQR(){
        this._btnTitleQR = (IOSElement) iosDriver.findElementByAccessibilityId(__strTitleQRID);
        _btnTitleQR.click();
    }


    public void clickMenuWeight(){
        this._btnMenuWeight = (IOSElement) iosDriver.findElementByAccessibilityId(__strMenuWeightID);
        _btnMenuWeight.click();
    }

    public void clickMenuSport(){
        this._btnMenuSport = (IOSElement) iosDriver.findElementByAccessibilityId(__strMenuSportID);
        _btnMenuSport.click();
    }

    public void clickMenuDiet(){
        this._btnMenuDiet = (IOSElement) iosDriver.findElementByAccessibilityId(__strMenuDietID);
        _btnMenuDiet.click();
    }

    public void clickMenuBloodPressure(){
        this._btnMenuBloodPressure = (IOSElement) iosDriver.findElementByAccessibilityId(__strMenuBloodPressureID);
        _btnMenuBloodPressure.click();
    }

    public void clickMenuBloodGlucose(){
        this._btnMenuBloodGlucose = (IOSElement) iosDriver.findElementByAccessibilityId(__strMenuBloodGlucoseID);
        _btnMenuBloodGlucose.click();
    }

    public void clickMenuSmartWristband(){
        this._btnMenuSmartWristband = (IOSElement) iosDriver.findElementByAccessibilityId(__strMenuSmartWristbandID);
        _btnMenuSmartWristband.click();
    }

    public void clickMenuThermometer(){
        this._btnMenuThermometer = (IOSElement) iosDriver.findElementByAccessibilityId(__strMenuThermometerID);
        _btnMenuThermometer.click();
    }

    public void clickMenuStressMood(){
        this._btnMenuStressMood = (IOSElement) iosDriver.findElementByAccessibilityId(__strMenuStressMoodID);
        _btnMenuStressMood.click();
    }

    public void clickMenuAdvice(){
        this._btnMenuAdvice = (IOSElement) iosDriver.findElementByAccessibilityId(__strMenuAdviceID);
        _btnMenuAdvice.click();
    }

    public void clickMenuShop(){
        this._btnMenuShop = (IOSElement) iosDriver.findElementByAccessibilityId(__strMenuShopID);
        _btnMenuShop.click();
    }

    public void clickMenuOuendan(){
        this._btnMenuOuendan = (IOSElement) iosDriver.findElementByAccessibilityId(__strMenuOuendanID);
        _btnMenuOuendan.click();
    }

    public void clickMenuProgress(){
        this._btnMenuProgress = (IOSElement) iosDriver.findElementByAccessibilityId(__strMenuProgressID);
        _btnMenuProgress.click();
    }


    public void clickTabbarProgress(){
        try {
            this._btnTabbarProgress = (IOSElement) iosDriver.findElementByXPath(__strTabbarProgressXpath);
        }catch (Exception e){
            this._btnTabbarProgress = (IOSElement) iosDriver.findElementByXPath(__strTabbarProgressChXpath);
        }

        _btnTabbarProgress.click();
    }

    public void clickTabbarPoints(){
        try {
            this._btnTabbarPoints = (IOSElement) iosDriver.findElementByXPath(__strTabbarPointsXpath);
        }catch (Exception e){
            this._btnTabbarPoints = (IOSElement) iosDriver.findElementByXPath(__strTabbarPointsChXpath);
        }

        _btnTabbarPoints.click();
    }

    public void clickTabbarShop(){
        try {
            this._btnTabbarShop = (IOSElement) iosDriver.findElementByXPath(__strTabbarShopXpath);
        }catch (Exception e){
            this._btnTabbarShop = (IOSElement) iosDriver.findElementByXPath(__strTabbarShopChXpath);
        }

        _btnTabbarShop.click();
    }

    public void clickTabbarMenu(){
        try {
            this._btnTabbarMenu = (IOSElement) iosDriver.findElementByXPath(__strTabbarMenuXapth);
        }catch (Exception e){
            this._btnTabbarMenu = (IOSElement) iosDriver.findElementByXPath(__strTabbarMenuChXapth);
        }

        _btnTabbarMenu.click();
    }

}
