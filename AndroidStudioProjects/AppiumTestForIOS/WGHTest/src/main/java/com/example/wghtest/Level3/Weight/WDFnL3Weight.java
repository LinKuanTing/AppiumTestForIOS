package com.example.wghtest.Level3.Weight;

import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;

import org.openqa.selenium.By;

import java.time.Duration;
import java.util.ArrayList;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.AppHeight;
import static com.example.wghtest.WGHTestBase.AppWidth;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3Weight extends WDFnBaseMeasureMenuItems {
    protected IOSElement _btnTips;
    protected IOSElement _imgWeight,_imgBodyFat,_imgWater,_imgMuscleMass,_imgBone,_imgVFL;
    protected IOSElement _btnPickChart,_btnPickDays;
    protected IOSElement _lsChartChoose;

    private static String __strTipsID = "Tips";
    private static String __strBMIID = "name LIKE 'BMI*'";

    private static String __strPickChartID = "Chart";
    private static String __strCancelID = "Cancel";
    private static String __strBackToWeightID = "Weight";
    private static String __strPickDaysXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]";
    private static String __strWeightMsgXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeStaticText[8]";

    private String[] __strChartChooseID = {"Weight","Bodyfat","Water","Muscle mass(MM)","Bone","Visceral fat Level(VFL)","Protein"};


    ArrayList<IOSElement> iosElements;

    public WDFnL3Weight(){
        try {
            clickClose();
        }catch (Exception eNoFindElement){ }

        try {
            clickBack();
        }catch (Exception eNoFindElement){ }
    }

    public void setValue () throws InterruptedException {
        setTime();
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeScrollView'");
        this._imgWeight = iosElements.get(2);
        int weightX = _imgWeight.getLocation().getX() + _imgWeight.getSize().getWidth()-10;
        int weightY = _imgWeight.getLocation().getY() + _imgWeight.getSize().getHeight()/2;
        touchAction.press(PointOption.point(weightX,weightY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point((int)(weightX-Math.random()*462),weightY)).release().perform();
        this._imgBodyFat = iosElements.get(3);
        int bodyfatX = _imgBodyFat.getLocation().getX() + _imgBodyFat.getSize().getWidth()-10;
        int bodyfatY = _imgBodyFat.getLocation().getY() + _imgBodyFat.getSize().getHeight()/2;
        touchAction.press(PointOption.point(bodyfatX,bodyfatY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point((int)(bodyfatX-Math.random()*462),bodyfatY)).release().perform();

        Thread.sleep(1500);
        touchAction.press(PointOption.point(AppWidth/2,AppHeight*3/5)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(AppWidth/2,AppHeight/5)).release().perform();

        this._imgWater = iosElements.get(4);
        int waterX = _imgWater.getLocation().getX() + _imgWater.getSize().getWidth()-10;
        int waterY = _imgWater.getLocation().getY() + _imgWater.getSize().getHeight()/2;
        touchAction.press(PointOption.point(waterX,waterY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point((int)(waterX-Math.random()*462),waterY)).release().perform();
        this._imgMuscleMass = iosElements.get(5);
        int muscleMassX = _imgMuscleMass.getLocation().getX() + _imgMuscleMass.getSize().getWidth()-10;
        int muscleMassY = _imgMuscleMass.getLocation().getY() + _imgMuscleMass.getSize().getHeight()/2;
        touchAction.press(PointOption.point(muscleMassX,muscleMassY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point((int)(muscleMassX-Math.random()*462),muscleMassY)).release().perform();

        Thread.sleep(1500);
        touchAction.press(PointOption.point(AppWidth/2,AppHeight*4/5)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(AppWidth/2,AppHeight/5)).release().perform();

        this._imgBone = iosElements.get(6);
        int boneX = _imgBone.getLocation().getX() + _imgBone.getSize().getWidth()-10;
        int boneY = _imgBone.getLocation().getY() + _imgBone.getSize().getHeight()/2;
        touchAction.press(PointOption.point(boneX,boneY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point((int)(boneX-Math.random()*462),boneY)).release().perform();
        this._imgVFL = iosElements.get(7);
        int VFLX = _imgVFL.getLocation().getX() + _imgVFL.getSize().getWidth()-10;
        int VFLY = _imgVFL.getLocation().getY() + _imgVFL.getSize().getHeight()/2;
        touchAction.press(PointOption.point(VFLX,VFLY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point((int)(VFLX-Math.random()*462),VFLY)).release().perform();

        clickSave();

    }

    public void weightSwipeLeft() throws InterruptedException {
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeScrollView'");
        this._imgWeight = iosElements.get(2);
        int weightX = _imgWeight.getLocation().getX() + _imgWeight.getSize().getWidth()-10;
        int weightY = _imgWeight.getLocation().getY() + _imgWeight.getSize().getHeight()/2;
        touchAction.press(PointOption.point(weightX,weightY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(weightX-462,weightY)).release().perform();
        Thread.sleep(1500);
    }

    public void bodyfatSwipeLeft() throws InterruptedException {
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeScrollView'");
        this._imgBodyFat = iosElements.get(3);
        int bodyfatX = _imgBodyFat.getLocation().getX() + _imgBodyFat.getSize().getWidth()-10;
        int bodyfatY = _imgBodyFat.getLocation().getY() + _imgBodyFat.getSize().getHeight()/2;
        touchAction.press(PointOption.point(bodyfatX,bodyfatY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(bodyfatX-462,bodyfatY)).release().perform();
        Thread.sleep(1500);
    }

    public String getWeightMsg(){
        return iosDriver.findElementByXPath(__strWeightMsgXpath).getText();
    }

    public double getBMI(){
        return Double.parseDouble(iosDriver.findElementByIosNsPredicate(__strBMIID).getText().substring(4));
    }

    public double getBodyfat(){
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeStaticText'");
        return Double.parseDouble(iosElements.get(15).getText());
    }

    public void checkChart() throws InterruptedException {
        clickChart();
        this._btnPickDays = (IOSElement) iosDriver.findElementByXPath(__strPickDaysXpath);
        int locDaysX = _btnPickDays.getLocation().getX();
        int locDaysY = _btnPickDays.getLocation().getY();
        int sizeDaysX = _btnPickDays.getSize().getWidth();
        int sizeDaysY = _btnPickDays.getSize().getHeight();
        this._btnPickChart = (IOSElement) iosDriver.findElementByAccessibilityId(__strPickChartID);

        for (int i=0; i<7; i++){
            _btnPickChart.click();

            iosDriver.findElementByAccessibilityId(__strChartChooseID[i]).click();

            Thread.sleep(3000);
            touchAction.tap(PointOption.point(locDaysX+sizeDaysX*1/6,locDaysY+sizeDaysY/2)).release().perform();
            Thread.sleep(3000);
            touchAction.tap(PointOption.point(locDaysX+sizeDaysX*3/6,locDaysY+sizeDaysY/2)).release().perform();;
            Thread.sleep(3000);
            touchAction.tap(PointOption.point(locDaysX+sizeDaysX*5/6,locDaysY+sizeDaysY/2)).release().perform();
            Thread.sleep(3000);

        }
    }


    public void clickTips() {
        this._btnTips = (IOSElement) iosDriver.findElementByAccessibilityId(__strTipsID);
        _btnTips.click();
    }

    public void clickBack(){
        iosDriver.findElement(By.id(__strBackToWeightID)).click();
    }


}
