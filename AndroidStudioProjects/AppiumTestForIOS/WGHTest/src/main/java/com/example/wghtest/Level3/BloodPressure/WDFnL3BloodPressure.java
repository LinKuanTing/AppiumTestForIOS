package com.example.wghtest.Level3.BloodPressure;

import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;

import java.time.Duration;
import java.util.ArrayList;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3BloodPressure extends WDFnBaseMeasureMenuItems {
    protected IOSElement _tvAchieve;
    protected IOSElement _pwSYS,_pwDIA,_pwPulse;
    protected IOSElement _etMemo;

    private String __strBackToBP = "Blood Pressure";

    ArrayList<IOSElement> iosElements;

    public WDFnL3BloodPressure(){
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypePickerWheel'");
        this._pwSYS = iosElements.get(0);
        this._pwDIA = iosElements.get(1);
        this._pwPulse = iosElements.get(2);
        this._etMemo = (IOSElement) iosDriver.findElementByIosNsPredicate("type == 'XCUIElementTypeTextField'");
    }

    public void setValue() throws InterruptedException {
        //setTime();
        int sys_X = _pwSYS.getLocation().getX()+_pwSYS.getSize().getWidth()/2;
        int sys_Y = _pwSYS.getLocation().getY()+_pwSYS.getSize().getHeight()/2;
        touchAction.press(PointOption.point(sys_X,sys_Y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(sys_X,sys_Y+(int)(Math.random()*600-300))).release().perform();
        int dia_X = _pwDIA.getLocation().getX()+_pwDIA.getSize().getWidth()/2;
        int dia_Y = _pwDIA.getLocation().getY()+_pwDIA.getSize().getHeight()/2;
        touchAction.press(PointOption.point(dia_X,dia_Y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(dia_X,dia_Y+(int)(Math.random()*600-300))).release().perform();
        int pulse_X = _pwPulse.getLocation().getX()+_pwPulse.getSize().getWidth()/2;
        int pulse_Y = _pwPulse.getLocation().getY()+_pwPulse.getSize().getHeight()/2;
        touchAction.press(PointOption.point(pulse_X,pulse_Y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(pulse_X,pulse_Y+(int)(Math.random()*600-300))).release().perform();

        _etMemo.clear();
        _etMemo.setValue("testdemo\n");

        clickSave();

    }

    public String getAchievedMsg(){
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeStaticText'");
        this._tvAchieve = iosElements.get(1);
        return _tvAchieve.getText();
    }

    public void clickBackToBP(){
        iosDriver.findElementByAccessibilityId(__strBackToBP).click();
    }
}
