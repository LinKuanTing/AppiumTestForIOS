package com.example.wghtest.Level3.Advice;

import com.example.wghtest.Level2.Tabbar.WDFnL2TabbarPoint;
import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;
import com.example.wghtest.Level3.BloodGlucose.WDFnL3BloodGlucose;
import com.example.wghtest.Level3.BloodPressure.WDFnL3BloodPressure;
import com.example.wghtest.Level3.Thermometer.WDFnL3Thermometer;
import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;
import com.example.wghtest.Level3.Weight.WDFnL3Weight;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.lang.model.util.Elements;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3Advice extends WDFnBaseMeasureMenuItems {
    protected IOSElement _btnOption;
    protected IOSElement _btnBroswer,_btnServices;

    //使用１８行程式碼定位  會time out -> 在此頁面時元素過多 因此使用Xpath時都會容易超時
    private static String __strOptionXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther";
    private static String __strBrowseName = "Browse";
    private static String __strServicesName = "Services";
    private static String __strBackToMenuID = "Main";

    public static ArrayList alMeasureTime = new ArrayList();


    public WDFnL3Advice(){
        try{
            iosDriver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
            this._btnBroswer = (IOSElement) iosDriver.findElementByAccessibilityId(__strBrowseName);
        }catch (Exception eNoFindElement){
            try {
                this._btnServices = (IOSElement) iosDriver.findElementByAccessibilityId(__strServicesName);
            }catch (Exception eNoFindElemeny){
                //代表空建構子 用在儲存AI推播資料時 呼叫的建構子
            }

        }
    }

    public void clickBack() {
        ArrayList<IOSElement> iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeButton'");

        for (int i=0; i<iosElements.size(); i++){
            if (iosElements.get(i).getText().equals(__strBackToMenuID)){
                iosElements.get(i).click();
                break;
            }
        }
    }

    public void clickBrowse(){
        try {
            _btnBroswer.click();
        }catch (Exception eNullPoint){
            int pointX = _btnServices.getLocation().getX() + _btnServices.getSize().getWidth()/2;
            int pointY = _btnServices.getLocation().getY() + _btnServices.getSize().getHeight()/2;
            int sizeX = _btnServices.getSize().getWidth();
            touchAction.tap(PointOption.point(pointX-sizeX,pointY)).release().perform();
        }

    }

    public void clickServices(){
        try {
            _btnServices.click();
        }catch (Exception eNullPoint){
            int pointX = _btnBroswer.getLocation().getX() + _btnBroswer.getSize().getWidth()/2;
            int pointY = _btnBroswer.getLocation().getY() + _btnBroswer.getSize().getHeight()/2;
            int sizeX = _btnBroswer.getSize().getWidth();
            touchAction.tap(PointOption.point(pointX+sizeX,pointY)).release().perform();
        }

    }


    public void AddAIBrowse() throws InterruptedException {
        WDFnL2TitleMenuTabbar wdFnL2TitleMenuTabbar = new WDFnL2TitleMenuTabbar();

        wdFnL2TitleMenuTabbar.clickMenuWeight();
        WDFnL3Weight wdFnL3Weight = new WDFnL3Weight();
        wdFnL3Weight.setValue();
        alMeasureTime.add(wdFnL3Weight.getDate()+",體重");
        wdFnL2TitleMenuTabbar.clickTabbarMenu();

        wdFnL2TitleMenuTabbar.clickMenuBloodPressure();
        WDFnL3BloodPressure wdFnL3BloodPressure = new WDFnL3BloodPressure();
        alMeasureTime.add(wdFnL3BloodPressure.getDate()+",血壓");
        wdFnL3BloodPressure.setValue();
        wdFnL2TitleMenuTabbar.clickTabbarMenu();

        wdFnL2TitleMenuTabbar.clickMenuBloodGlucose();
        WDFnL3BloodGlucose wdFnL3BloodGlucose = new WDFnL3BloodGlucose();
        alMeasureTime.add(wdFnL3BloodGlucose.getDate()+",血糖");
        wdFnL3BloodGlucose.setValue();
        wdFnL2TitleMenuTabbar.clickTabbarMenu();


        wdFnL2TitleMenuTabbar.clickMenuThermometer();
        WDFnL3Thermometer wdFnL3Thermometer = new WDFnL3Thermometer();
        alMeasureTime.add(wdFnL3Thermometer.getDate()+",體溫");
        wdFnL3Thermometer.setValue();
        wdFnL2TitleMenuTabbar.clickTabbarMenu();


    }



    /*
    public void clickBrowse(){
        int pointX = _btnOption.getLocation().getX() + _btnOption.getSize().getWidth()/4;
        int pointY = _btnOption.getLocation().getY() + _btnOption.getSize().getHeight()/2;
        touchAction.tap(PointOption.point(pointX,pointY)).release().perform();
    }

    public void cliclServices(){
        int pointX = _btnOption.getLocation().getX() + _btnOption.getSize().getWidth()*3/4;
        int pointY = _btnOption.getLocation().getY() + _btnOption.getSize().getHeight()/2;
        touchAction.tap(PointOption.point(pointX,pointY)).release().perform();
    }
    */

}
