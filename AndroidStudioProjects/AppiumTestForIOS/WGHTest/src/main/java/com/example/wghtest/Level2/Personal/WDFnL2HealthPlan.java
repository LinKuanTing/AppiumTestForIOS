package com.example.wghtest.Level2.Personal;

import com.example.wghtest.Level2.WDFnBaseInfoSetting;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;
import static com.example.wghtest.WGHTestBase.timeOut;


public class WDFnL2HealthPlan extends WDFnBaseInfoSetting {
    protected IOSElement _txtCalories,_txtMeal;

    protected int _intAge, _intExerciseNo, _intReduceCal;
    protected double _dbHeight, _dbInitialWeight, _dbTargetWeight;
    protected String _strBreakfastStart, _strBreakfastEnd, _strLunchStart, _strLunchEnd, _strDinnerStart, _strDinnerEnd;
    protected String _strFastingStart, _strFastingEnd;

    private static String __strCaloriesXpath = "(//XCUIElementTypeStaticText[@name=\"Calories\"])[1]";
    private static String __strMealXpath = "//XCUIElementTypeStaticText[@name=\"Meal\"]";
    private static String __strActivityL1ID = "Sed";
    private static String __strActivityL2ID = "Mod";
    private static String __strActivityL3ID = "Vig";

    int pointStartX, pointStartY;
    int pointEndX, pointEndY;

    ArrayList<IOSElement> iosElements;

    public WDFnL2HealthPlan(){
        pointEndX = _locX;
        pointEndY = _locY + _sizeH -30;
    }

    public void setHealthPlanInfo() throws InterruptedException {
        //取得帳號與體重資料
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeTextField'");

        _intAge = Integer.parseInt(iosElements.get(0).getText());
        _dbHeight = Double.parseDouble(iosElements.get(1).getText());
        _dbInitialWeight = Double.parseDouble(iosElements.get(2).getText());
        _dbTargetWeight = Double.parseDouble(iosElements.get(3).getText());

        //下滑至卡路里設定 並取得卡路里資料
        _txtCalories = (IOSElement) iosDriver.findElementByXPath(__strCaloriesXpath);
        pointStartX = _txtCalories.getLocation().getX();
        pointStartY = _txtCalories.getLocation().getY();
        touchAction.press(PointOption.point(pointStartX,pointStartY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(pointEndX,pointEndY)).release().perform();

        //使用while可以省下一些時間 例如:exersiseNo = 1，就不必再接下去找
        while(true){
            try {
                iosDriver.findElementByAccessibilityId(__strActivityL1ID);
                _intExerciseNo = 1;
                break;
            }catch (Exception eNoFindElement){ }
            try {
                iosDriver.findElementByAccessibilityId(__strActivityL2ID);
                _intExerciseNo = 2;
                break;
            }catch (Exception eNoFindElement){ }
            try {
                iosDriver.findElementByAccessibilityId(__strActivityL3ID);
                _intExerciseNo = 3;
                break;
            }catch (Exception eNoFindElement){ }
        }

        _intReduceCal = Integer.parseInt(iosElements.get(6).getText());

        //下滑至用餐時間設定  並取得時間資料
        _txtMeal = (IOSElement) iosDriver.findElementByXPath(__strMealXpath);
        pointStartX = _txtMeal.getLocation().getX();
        pointStartY = _txtMeal.getLocation().getY();
        touchAction.press(PointOption.point(pointStartX,pointStartY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(pointEndX,pointEndY)).release().perform();

        _strBreakfastStart = iosElements.get(7).getText();
        _strBreakfastEnd = iosElements.get(8).getText();
        _strLunchStart = iosElements.get(9).getText();
        _strLunchEnd = iosElements.get(10).getText();
        _strDinnerStart = iosElements.get(11).getText();
        _strDinnerEnd = iosElements.get(12).getText();
        _strFastingStart = iosElements.get(13).getText();
        _strFastingEnd = iosElements.get(14).getText();

        clickSave();
        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);


    }

    public int getAge(){
        return _intAge;
    }

    public double getHeight(){
        return _dbHeight;
    }

    public double getInitialWeight(){
        return _dbInitialWeight;
    }

    public double getTargetWeight(){
        return _dbTargetWeight;
    }

    public int getActivityLevel(){
        return _intExerciseNo;
    }

    public int getReduceCal(){
        return _intReduceCal;
    }

}
