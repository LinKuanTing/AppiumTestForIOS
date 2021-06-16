package com.example.wghtest.Level3.Thermometer;

import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;



import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import sun.awt.windows.ThemeReader;

import static com.example.wghtest.WGHTestBase.AppHeight;
import static com.example.wghtest.WGHTestBase.AppWidth;
import static com.example.wghtest.WGHTestBase.iosDriver;
import static com.example.wghtest.WGHTestBase.timeOut;

public class WDFnL3Thermometer extends WDFnBaseMeasureMenuItems {
    protected IOSElement _pwType,_pwTempTen,_pwTempDigit,_pwTempPoint;
    protected IOSElement _etMemo;

    private static String __strMemoValue = "type == 'XCUIElementTypeTextField'";

    ArrayList<IOSElement> iosElements;

    public WDFnL3Thermometer(){
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypePickerWheel'");
        this._pwType = iosElements.get(0);
        this._pwTempTen = iosElements.get(1);
        this._pwTempDigit = iosElements.get(2);
        this._pwTempPoint = iosElements.get(3);

    }

    public void setValue() throws InterruptedException {

        //setTime();
        int type_X = _pwType.getLocation().getX()+_pwType.getSize().getWidth()/2;
        int type_Y = _pwType.getLocation().getY()+_pwType.getSize().getHeight()/2;
        touchAction.press(PointOption.point(type_X,type_Y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(type_X,type_Y+(int)(Math.random()*80-40))).release().perform();


        int tempTen_X = _pwTempTen.getLocation().getX()+_pwTempTen.getSize().getWidth()/2;
        int tempTen_Y = _pwTempTen.getLocation().getY()+_pwTempTen.getSize().getHeight()/2;
        touchAction.press(PointOption.point(tempTen_X,tempTen_Y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(tempTen_X,tempTen_Y+(int)(Math.random()*100-40))).release().perform();

        int tempDigit_X = _pwTempDigit.getLocation().getX()+_pwTempDigit.getSize().getWidth()/2;
        int tempDigit_Y = _pwTempDigit.getLocation().getY()+_pwTempDigit.getSize().getHeight()/2;
        touchAction.press(PointOption.point(tempDigit_X,tempDigit_Y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(tempDigit_X,tempDigit_Y+(int)(Math.random()*200-120))).release().perform();

        int tempPoint_X = _pwTempPoint.getLocation().getX()+_pwTempPoint.getSize().getWidth()/2;
        int tempPoint_Y = _pwTempPoint.getLocation().getY()+_pwTempPoint.getSize().getHeight()/2;
        touchAction.press(PointOption.point(tempPoint_X,tempPoint_Y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(tempPoint_X,tempPoint_Y-(int)(Math.random()*500))).release().perform();

       try {
           Thread.sleep(3000);
           iosDriver.switchTo().alert().accept();
       }catch (Exception eNoFindElement){ }


        this._etMemo = (IOSElement) iosDriver.findElementByIosNsPredicate(__strMemoValue);
        _etMemo.clear();
        _etMemo.setValue("testdemo\n");

        clickSave();

    }

    @Override
    public void clickRecord() throws InterruptedException {
        super.clickRecord();
        try {
            Thread.sleep(5000);
            iosDriver.switchTo().alert().accept();
        }catch (Exception eNoFindElement) { }
    }
}

