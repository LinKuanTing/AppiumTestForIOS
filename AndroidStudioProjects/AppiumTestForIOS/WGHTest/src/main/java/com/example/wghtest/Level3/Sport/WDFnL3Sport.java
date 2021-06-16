package com.example.wghtest.Level3.Sport;

import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;

import java.time.Duration;
import java.util.ArrayList;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3Sport extends WDFnBaseMeasureMenuItems {
    protected IOSElement _tvSport,_tvDuration,_etStep,_tvCalories,_tvPhoto,_etMemo;

    protected String _strAddSportContent;

    private static String __strNotUploadMsgID = "name LIKE 'Waiting for upload：* records'";

    private static String __strCellXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther[2]/XCUIElementTypeTable/XCUIElementTypeCell";
    private static String __strCellTextViewXpath = "/XCUIElementTypeStaticText[2]";
    private static String __strCellTextFieldXpath = "/XCUIElementTypeTextField";
    private static String __strMemoID = "type == 'XCUIElementTypeTextField' AND value LIKE 'Memo'";
    private static String __strDoneID = "Done";

    private static String __strModifyCellXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell";

    private static String __strRecordID = "btn calendar";

    private static String __strSaveAndUploadID = "Save and upload";
    private static String __strSaveUploadLaterID = "Save and upload later";
    private static String __strSaveToPhoneAlbumiD = "Save to phone album";
    private static String __strCancelID = "Cancel";

    ArrayList<IOSElement> iosElements;

    public WDFnL3Sport() { }


    public void setValue() throws InterruptedException {
        _strAddSportContent = "";
        setTime();
        //set sport item
        try {
            this._tvSport = (IOSElement) iosDriver.findElementByXPath(__strCellXpath+"[2]"+__strCellTextViewXpath);
        }catch (Exception eNoFindElement){
            //在修改記錄頁面 Xpath變動  因此當修改記錄時 必須執行此段程式碼
            this._tvSport = (IOSElement) iosDriver.findElementByXPath(__strModifyCellXpath+"[2]"+__strCellTextViewXpath);
        }
        _tvSport.click();
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypePickerWheel'");
        //index 0 -> sport type    index 1 -> sport item
        int type_X = iosElements.get(0).getLocation().getX()+iosElements.get(0).getSize().getWidth()/2;
        int type_Y = iosElements.get(0).getLocation().getY()+iosElements.get(0).getSize().getHeight()/2;
        touchAction.press(PointOption.point(type_X,type_Y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(type_X,type_Y-120-(int)(Math.random()*300))).release().perform();
        int item_X = iosElements.get(1).getLocation().getX()+iosElements.get(1).getSize().getWidth()/2;
        int item_Y = iosElements.get(1).getLocation().getY()+iosElements.get(1).getSize().getHeight()/2;
        touchAction.press(PointOption.point(item_X,item_Y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(item_X,item_Y-(int)(Math.random()*660))).release().perform();
        iosDriver.findElementByAccessibilityId(__strDoneID).click();

        //set exercise time
        while (true){
            try {
                this._tvDuration = (IOSElement) iosDriver.findElementByXPath(__strCellXpath+"[3]"+__strCellTextViewXpath);
            }catch (Exception eNoFindElement){
                //在修改記錄頁面 Xpath變動  因此當修改記錄時 必須執行此段程式碼
                this._tvDuration = (IOSElement) iosDriver.findElementByXPath(__strModifyCellXpath+"[3]"+__strCellTextViewXpath);
            }
            _tvDuration.click();
            iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypePickerWheel'");
            //index 0 -> hours    index  -> minutes
            int hour_X = iosElements.get(0).getLocation().getX()+iosElements.get(0).getSize().getWidth()/2;
            int hour_Y = iosElements.get(0).getLocation().getY()+iosElements.get(0).getSize().getHeight()/2;
            touchAction.press(PointOption.point(hour_X,hour_Y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(hour_X,hour_Y-(int)(Math.random()*540))).release().perform();
            int min_X = iosElements.get(1).getLocation().getX()+iosElements.get(1).getSize().getWidth()/2;
            int min_Y = iosElements.get(1).getLocation().getY()+iosElements.get(1).getSize().getHeight()/2;
            touchAction.press(PointOption.point(min_X,min_Y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(min_X,min_Y-(int)(Math.random()*300))).release().perform();
            iosDriver.findElementByAccessibilityId(__strDoneID).click();

            if (!getCalories().equals("0 Kcal")){
                break;
            }
        }

        _strAddSportContent += getSport()+"/"+getDuration().replaceAll("Hour0min","Hour")+"/"+getCalories();
        //System.out.println(_strAddSportContent);

        clickSave();
        iosDriver.findElementByAccessibilityId(__strSaveAndUploadID).click();

    }

    public void setPedometer() throws InterruptedException {
        _strAddSportContent = "";
        setTime();
        try {
            this._tvSport = (IOSElement) iosDriver.findElementByXPath(__strCellXpath+"[2]"+__strCellTextViewXpath);
        }catch (Exception eNoFindElement){
            //在修改記錄頁面 Xpath變動  因此當修改記錄時 必須執行此段程式碼
            this._tvSport = (IOSElement) iosDriver.findElementByXPath(__strModifyCellXpath+"[2]"+__strCellTextViewXpath);
        }

        _tvSport.click();
        iosDriver.findElementByAccessibilityId(__strDoneID).click();

        try {
            this._etStep = (IOSElement) iosDriver.findElementByXPath(__strCellXpath+"[3]"+__strCellTextFieldXpath);
        }catch (Exception eNoFindElement){
            //在修改記錄頁面 Xpath變動  因此當修改記錄時 必須執行此段程式碼
            this._etStep = (IOSElement) iosDriver.findElementByXPath(__strModifyCellXpath+"[3]"+__strCellTextFieldXpath);
        }

        if (_etStep.getText().equals("")){
            _etStep.setValue((int)(Math.random()*2000+1000)+"\n");
        }else {
            _etStep.setValue("0"+"\n");
        }


        _strAddSportContent += getSport()+"/"+getSteps()+"steps/"+getCalories();
        //System.out.println(_strAddSportContent);

        clickSave();
        iosDriver.findElementByAccessibilityId(__strSaveAndUploadID).click();
    }

    public void saveAndUploadLater(){
        this._tvSport = (IOSElement) iosDriver.findElementByXPath(__strCellXpath+"[2]"+__strCellTextViewXpath);
        _tvSport.click();
        iosDriver.findElementByAccessibilityId(__strDoneID).click();

        this._etStep = (IOSElement) iosDriver.findElementByXPath(__strCellXpath+"[3]"+__strCellTextFieldXpath);
        _etStep.sendKeys((int)(Math.random()*2000+1000)+"\n");

        clickSave();
        iosDriver.findElementByAccessibilityId(__strSaveUploadLaterID).click();
    }



    public String getSport(){
        try{
            this._tvSport = (IOSElement) iosDriver.findElementByXPath(__strCellXpath+"[2]"+__strCellTextViewXpath);
        }catch (Exception eNoFindElement){
            //修改記錄頁面
            this._tvSport = (IOSElement) iosDriver.findElementByXPath(__strModifyCellXpath+"[2]"+__strCellTextViewXpath);
        }
        return _tvSport.getText();
    }

    public String getDuration(){
        try{
            this._tvDuration = (IOSElement) iosDriver.findElementByXPath(__strCellXpath+"[3]"+__strCellTextViewXpath);
        }catch (Exception eNoFindElement){
            //修改記錄頁面
            this._tvDuration = (IOSElement) iosDriver.findElementByXPath(__strModifyCellXpath+"[3]"+__strCellTextViewXpath);
        }
        return _tvDuration.getText();
    }

    public String getCalories(){
        try{
            this._tvCalories = (IOSElement) iosDriver.findElementByXPath(__strCellXpath+"[4]"+__strCellTextViewXpath);
        }catch (Exception eNoFindElement){
            //修改記錄頁面
            this._tvCalories = (IOSElement) iosDriver.findElementByXPath(__strModifyCellXpath+"[4]"+__strCellTextViewXpath);
        }
        return _tvCalories.getText();
    }

    public String getSteps(){
        try{
            this._etStep = (IOSElement) iosDriver.findElementByXPath(__strCellXpath+"[3]"+__strCellTextFieldXpath);
        }catch (Exception eNoFindElement){
            //修改記錄頁面
            this._etStep = (IOSElement) iosDriver.findElementByXPath(__strModifyCellXpath+"[3]"+__strCellTextFieldXpath);
        }
        return _etStep.getText();
    }

    public String getNotUploadRecordMsg(){
        return iosDriver.findElementByIosNsPredicate(__strNotUploadMsgID).getText();
    }

    public void clickRecord(){
        this._btnRecord = (IOSElement) iosDriver.findElementByAccessibilityId(__strRecordID);
        _btnRecord.click();
        try{
            iosDriver.switchTo().alert().accept();
        }catch (Exception eNoFindElement){ }
    }

}


