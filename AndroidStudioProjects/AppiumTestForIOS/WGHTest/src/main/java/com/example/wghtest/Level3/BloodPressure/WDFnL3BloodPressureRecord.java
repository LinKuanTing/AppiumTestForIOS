package com.example.wghtest.Level3.BloodPressure;

import com.example.wghtest.other.FnFileEvent;

import java.io.IOException;
import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3BloodPressureRecord {
    protected int _index;
    protected IOSElement _lvRecordIdx;
    protected IOSElement _tvDate,_tvSYS,_tvDIA,_tvPulse;

    private static String __strFirstRecord = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]";

    private String __strIdx = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable";
    private String __strDateXpath = "/XCUIElementTypeStaticText[1]";
    private String __strSYSXpath = "/XCUIElementTypeStaticText[4]";
    private String __strDIAXpath = "/XCUIElementTypeStaticText[5]";
    private String __strPulseXpath = "/XCUIElementTypeStaticText[6]";
    private String __strBackToRecordID = "90days Record";
    private String __strBackToBPID = "Blood Pressure";

    private static String __strFileName = "BloodPressureRecord";
    private static String __strFilePath;

    TouchAction touchAction = new TouchAction(iosDriver);


    public WDFnL3BloodPressureRecord(int index){
        this._index = index;
        this.__strIdx += "/XCUIElementTypeCell["+_index+"]";
        this._lvRecordIdx = (IOSElement) iosDriver.findElementByXPath(__strIdx);
    }

    public WDFnL3BloodPressureRecord(){ }


    public void clickBack(){
        iosDriver.findElementByAccessibilityId(__strBackToBPID).click();
    }

    public String getDate(){
        this._tvDate = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strDateXpath);
        return _tvDate.getText();
    }

    public String getSYS(){
        this._tvSYS = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strSYSXpath);
        return _tvSYS.getText().replaceAll(" ","").replaceAll("/","");
    }

    public String getDIA(){
        this._tvDIA = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strDIAXpath);
        return _tvDIA.getText();
    }

    public String getPulse(){
        this._tvPulse = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strPulseXpath);
        return _tvPulse.getText();
    }

    public void update() throws InterruptedException {
        int sizeX = _lvRecordIdx.getSize().getWidth();
        int sizeY = _lvRecordIdx.getSize().getHeight();
        int pointX = _lvRecordIdx.getLocation().getX() + sizeX/2;
        int pointY = _lvRecordIdx.getLocation().getY() + sizeY/2;
        touchAction.tap(PointOption.point(pointX,pointY)).release().perform();
        WDFnL3BloodPressure wdFnL3BloodPressure = new WDFnL3BloodPressure();
        wdFnL3BloodPressure.setValue();

    }

    ArrayList alReadLine = new ArrayList();
    public boolean compare(ArrayList alBP) throws IOException {
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        alReadLine = fnFileEvent.getContent(__strFilePath);

        int sameNum = 0;
        for (int i=0; i<alReadLine.size(); i++){
            if (alReadLine.get(i).equals(alBP.get(i))){
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
