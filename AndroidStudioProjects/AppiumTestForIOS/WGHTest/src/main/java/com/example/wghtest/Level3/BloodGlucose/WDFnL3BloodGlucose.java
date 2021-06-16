package com.example.wghtest.Level3.BloodGlucose;

import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;

import java.time.Duration;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.AppHeight;
import static com.example.wghtest.WGHTestBase.AppWidth;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3BloodGlucose extends WDFnBaseMeasureMenuItems {
    protected IOSElement _tvAchieve;
    protected IOSElement _pwSession,_pwGLU100,_pwGLU10,_pwGLU1,_pwGLUpoint1;
    protected IOSElement _etMemo;

    private String __strBackToBG = "Blood Glucose";

    ArrayList<IOSElement> iosElements;

    public WDFnL3BloodGlucose(){
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypePickerWheel'");
        this._pwSession = iosElements.get(0);
        this._pwGLU100 = iosElements.get(1);
        this._pwGLU10 = iosElements.get(2);
        this._pwGLU1 = iosElements.get(3);
        this._pwGLUpoint1 =iosElements.get(4);
        this._etMemo = (IOSElement) iosDriver.findElementByIosNsPredicate("type == 'XCUIElementTypeTextField'");
    }

    public void setValue() throws InterruptedException {
        //setTime();
        int session_X = _pwSession.getLocation().getX()+_pwSession.getSize().getWidth()/2;
        int session_Y = _pwSession.getLocation().getY()+_pwSession.getSize().getHeight()/2;
        touchAction.press(PointOption.point(session_X,session_Y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(session_X,session_Y-(int)(Math.random()*100))).release().perform();
        int GLU100_X = _pwGLU100.getLocation().getX()+_pwGLU100.getSize().getWidth()/2;
        int GLU100_Y = _pwGLU100.getLocation().getY()+_pwGLU100.getSize().getHeight()/2;
        touchAction.press(PointOption.point(GLU100_X,GLU100_Y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(GLU100_X,GLU100_Y+(int)(Math.random()*50+50))).release().perform();
        int GLU10_X = _pwGLU10.getLocation().getX()+_pwGLU10.getSize().getWidth()/2;
        int GLU10_Y = _pwGLU10.getLocation().getY()+_pwGLU10.getSize().getHeight()/2;
        touchAction.press(PointOption.point(GLU10_X,GLU10_Y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(GLU10_X,GLU10_Y+(int)(Math.random()*400-300))).release().perform();
        int GLU1_X = _pwGLU1.getLocation().getX()+_pwGLU1.getSize().getWidth()/2;
        int GLU1_Y = _pwGLU1.getLocation().getY()+_pwGLU1.getSize().getHeight()/2;
        touchAction.press(PointOption.point(GLU1_X,GLU1_Y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(GLU1_X,GLU1_Y+(int)(Math.random()*400-300))).release().perform();
        //只有在單位為mol時 才會使用到
        int GLUpoint1_X = _pwGLUpoint1.getLocation().getX()+_pwGLUpoint1.getSize().getWidth()/2;
        int GLUpoint1_Y = _pwGLUpoint1.getLocation().getY()+_pwGLUpoint1.getSize().getHeight()/2;

        touchAction.press(PointOption.point(AppWidth/2,AppHeight*7/8)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(AppWidth/2,AppHeight/3)).release().perform();
        _etMemo.clear();
        _etMemo.setValue("testdemo\n");

        clickSave();
    }

    public String getAchievedMsg(){
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeStaticText'");
        this._tvAchieve = iosElements.get(1);
        return _tvAchieve.getText();
    }

    public void clickBackToBG(){
        iosDriver .findElementByAccessibilityId(__strBackToBG).click();
    }
}
