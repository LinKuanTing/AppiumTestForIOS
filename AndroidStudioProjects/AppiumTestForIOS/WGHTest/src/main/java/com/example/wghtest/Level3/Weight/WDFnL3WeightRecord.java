package com.example.wghtest.Level3.Weight;

import com.example.wghtest.other.FnFileEvent;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;
import static com.example.wghtest.WGHTestBase.timeOut;

public class WDFnL3WeightRecord {
    protected int _index;
    protected IOSElement _lvRecordIdx,_btnDeleteIdx;
    protected IOSElement _tvDate,_tvWeight,_tvBodyfat,_tvBMI,_tvWater,_tvMM,_tvBone,_tvBMR,_tvVFL,_tvProtein,_tvMP;


    private static String __strFirstRecord = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]";

    private String __strIdx = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable";
    private String __strDateXpath = "/XCUIElementTypeStaticText[1]";
    private String __strWeightXpath = "/XCUIElementTypeStaticText[14]";
    private String __strBodyfatXpath = "/XCUIElementTypeStaticText[8]";
    private String __strBMIXpath = "/XCUIElementTypeStaticText[22]";
    private String __strWaterXpath = "/XCUIElementTypeStaticText[9]";
    private String __strMMXpath = "/XCUIElementTypeStaticText[23]";
    private String __strBoneXpath = "/XCUIElementTypeStaticText[10]";
    private String __strBMRXpath = "/XCUIElementTypeStaticText[20]";
    private String __strVFLXpath = "/XCUIElementTypeStaticText[13]";
    private String __strProteinXpath = "/XCUIElementTypeStaticText[24]";
    private String __strMPXpath = "/XCUIElementTypeStaticText[25]";
    private String __strDeleteXpath = "(//XCUIElementTypeButton[@name=\"Delete\"])[2]";

    private String __strBackToRecordID = "90days Record";
    private String __strBackToWeightID = "Weight";


    private static String __strFileName = "WeightRecord";
    private static String __strFilePath;

    TouchAction touchAction = new TouchAction(iosDriver);


    public WDFnL3WeightRecord(){ }

    public WDFnL3WeightRecord(int index){
        this._index = index;
        this.__strIdx += "/XCUIElementTypeCell["+_index+"]";
        this._lvRecordIdx = (IOSElement) iosDriver.findElementByXPath(__strIdx);

/*
        //spend too much time
        this._tvDate = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strDateXpath);
        this._tvWeight = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strWeightXpath);
        this._tvBodyfat = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strBodyfatXpath);
        this._tvBMI = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strBMIXpath);
        this._tvWater = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strWaterXpath);
        this._tvMM = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strMMXpath);
        this._tvBone = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strBoneXpath);
        this._tvBMR = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strBMRXpath);
        this._tvVFL = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strVFLXpath);
        this._tvProtein = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strProteinXpath);
        this._tvMP = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strMPXpath);

 */
    }

    public void clickBack(){
        iosDriver.findElementByAccessibilityId(__strBackToWeightID).click();
    }

    public String getDate(){
        this._tvDate = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strDateXpath);
        return _tvDate.getText();
    }

    public String getWeight(){
        this._tvWeight = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strWeightXpath);
        return  _tvWeight.getText();
    }

    public String getBodyfat(){
        this._tvBodyfat = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strBodyfatXpath);
        return _tvBodyfat.getText();
    }

    public String getBMI(){
        this._tvBMI = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strBMIXpath);
        return _tvBMI.getText();
    }

    public String getWater(){
        this._tvWater = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strWaterXpath);
        return _tvWater.getText();
    }

    public String getMM(){
        this._tvMM = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strMMXpath);
        return _tvMM.getText();
    }

    public String getBone(){
        this._tvBone = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strBoneXpath);
        return _tvBone.getText();
    }

    public String getBMR(){
        this._tvBMR = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strBMRXpath);
        return _tvBMR.getText();
    }

    public String getVFL(){
        this._tvVFL = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strVFLXpath);
        return _tvVFL.getText();
    }

    public String getProtein(){
        this._tvProtein = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strProteinXpath);
        return _tvProtein.getText();
    }

    public String getMP(){
        this._tvMP = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strMPXpath);
        return _tvMP.getText();
    }



    public void update() throws InterruptedException {
        int sizeX = _lvRecordIdx.getSize().getWidth();
        int sizeY = _lvRecordIdx.getSize().getHeight();
        int pointX = _lvRecordIdx.getLocation().getX() + sizeX/2;
        int pointY = _lvRecordIdx.getLocation().getY() + sizeY/2;
        touchAction.tap(PointOption.point(pointX,pointY)).release().perform();
        WDFnL3Weight wdFnL3Weight = new WDFnL3Weight();
        wdFnL3Weight.setValue();

        //點擊儲存後 仍然在修改體重頁面  必須在點擊左上角返回  才回到紀錄列表
        //  (等待往後版本修改)
        iosDriver.findElementByAccessibilityId(__strBackToRecordID).click();
    }

    public void delete(){
        int sizeX = _lvRecordIdx.getSize().getWidth();
        int sizeY = _lvRecordIdx.getSize().getHeight();
        int pointX = _lvRecordIdx.getLocation().getX() + sizeX/2;
        int pointY = _lvRecordIdx.getLocation().getY() + sizeY/2;
        touchAction.press(PointOption.point(pointX,pointY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(pointX-sizeX/2,pointY)).release().perform();
        this._btnDeleteIdx = (IOSElement) iosDriver.findElementByXPath(__strDeleteXpath);
        _btnDeleteIdx.click();

        iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
        iosDriver.switchTo().alert().accept();
    }


    ArrayList alReadLine = new ArrayList();
    public boolean compare(ArrayList alWeight) throws IOException {
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        alReadLine = fnFileEvent.getContent(__strFilePath);

        int sameNum = 0;
        for (int i=0; i<alReadLine.size(); i++){
            if (alReadLine.get(i).equals(alWeight.get(i))){
                sameNum++;
            }
        }
        if (sameNum == alReadLine.size()){
            return true;
        }else {
            return false;
        }

    }

}
