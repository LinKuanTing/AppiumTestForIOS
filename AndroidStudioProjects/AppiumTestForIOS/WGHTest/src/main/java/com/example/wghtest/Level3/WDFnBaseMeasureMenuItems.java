package com.example.wghtest.Level3;

import java.io.IOException;
import java.time.Duration;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnBaseMeasureMenuItems {
    protected IOSElement _btnRecord,_btnChart,_btnShare,_btnSave,_btnBluetooth;
    protected IOSElement _btnDataMean,_btnTime;

    protected IOSElement _lvDays,_lvHours,_lvMinutes;

    private static String __strRecordID = "btn List";
    private static String __strChartID = "btn Chart";
    private static String __strShareID = "up arrow";
    private static String __strSaveID = "Save";
    private static String __strBLEID = "BluetoothOff";
    private static String __strDataMeanID = "Figures";
    private static String[] __strCloseID = {"close","Back"};

    private static String __strTimeID = "name LIKE '????/??/?? *'";
    private static String __strDoneID = "Done";
    private static String __strDaysValue = "value LIKE 'Today'";
    private static String __strHoursValue = "value LIKE '* o’clock'";
    private static String __strMinutesValue = "value LIKE '* minutes'";

    private static String __strCancel = "Cancel";    //share's

    protected TouchAction touchAction = new TouchAction(iosDriver);


    public void set_btnTime(){
        this._btnTime = (IOSElement) iosDriver.findElementByIosNsPredicate(__strTimeID);
    }


    public void setTime() throws InterruptedException {
        set_btnTime();
        _btnTime.click();
        Thread.sleep(3000);
        _lvDays = (IOSElement) iosDriver.findElementByIosNsPredicate(__strDaysValue);
        _lvHours = (IOSElement) iosDriver.findElementByIosNsPredicate(__strHoursValue);
        _lvMinutes = (IOSElement) iosDriver.findElementByIosNsPredicate(__strMinutesValue);
        int dayX = _lvDays.getLocation().getX()+_lvDays.getSize().getWidth()/2;
        int dayY = _lvDays.getLocation().getY()+_lvDays.getSize().getHeight()/2;
        int hourX = _lvHours.getLocation().getX()+_lvHours.getSize().getWidth()/2;
        int hourY = _lvHours.getLocation().getY()+_lvHours.getSize().getHeight()/2;
        int minX = _lvMinutes.getLocation().getX()+_lvMinutes.getSize().getWidth()/2;
        int minY = _lvMinutes.getLocation().getY()+_lvMinutes.getSize().getHeight()/2;
        //touchAction.press(PointOption.point(dayX,dayY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(dayX, (int) (dayY+Math.random()*200))).release().perform();
        //Thread.sleep(3000);
        //touchAction.press(PointOption.point(hourX,hourY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(dayX, (int) (dayY+Math.random()*200))).release().perform();
        //Thread.sleep(3000);
        touchAction.press(PointOption.point(minX,minY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(minX,minY+480)).release().perform();
        Thread.sleep(3000);
        iosDriver.findElementByAccessibilityId(__strDoneID).click();
    }

    public String getDate(){
        this._btnTime = (IOSElement) iosDriver.findElementByIosNsPredicate(__strTimeID);
        return _btnTime.getText();
    }

    public void clickRecord() throws InterruptedException {
        this._btnRecord = (IOSElement) iosDriver.findElementByAccessibilityId(__strRecordID);
        _btnRecord.click();
        Thread.sleep(5000);
        try {
            iosDriver.switchTo().alert().accept();
        }catch (Exception eNoFindelement){ }
    }

    public void clickChart(){
        this._btnChart = (IOSElement) iosDriver.findElementByAccessibilityId(__strChartID);
        _btnChart.click();
    }

    public void clickShare(){
        this._btnShare = (IOSElement) iosDriver.findElementByAccessibilityId(__strShareID);
        _btnShare.click();
    }

    public void clickSave(){
        this._btnSave = (IOSElement) iosDriver.findElementByAccessibilityId(__strSaveID);
        _btnSave.click();
    }

    public void clickDataMean(){
        this._btnDataMean = (IOSElement) iosDriver.findElementByAccessibilityId(__strDataMeanID);
        _btnDataMean.click();
    }

    public void clickClose(){
        for (int i=0 ;i<__strCloseID.length; i++){
            try {
                iosDriver.findElementByAccessibilityId(__strCloseID[i]).click();
                break;
            }catch (Exception eNoFindElement){

            }
        }


    }

    //Function
    public void setValue() throws InterruptedException { }



    //Test Case
    public void LoadRecord(){ }

    public void AddRecord() throws InterruptedException { }

    public void UpdateRecord() throws InterruptedException { }

    public void CompareRecord() throws IOException, InterruptedException { }

    public void CheckDataMean() throws InterruptedException, IOException { }


}
