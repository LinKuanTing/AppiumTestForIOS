package com.example.wghtest.Level3.BloodGlucose;

import com.example.wghtest.other.FnFileEvent;

import java.io.IOException;
import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3BloodGlucoseRecord {
    protected int _index;
    protected IOSElement _lvRecordIdx;
    protected IOSElement _tvDate,_tvSession,_tvGLU;

    private static String __strFirstRecord = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]";

    private String __strIdx = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable";
    private String __strDateXpath = "/XCUIElementTypeStaticText[1]";
    private String __strSessionXpath = "/XCUIElementTypeStaticText[2]";
    private String __strGLUXpath = "/XCUIElementTypeStaticText[5]";
    private String __strBackToRecordID = "90days Record";
    private String __strBackToBGID = "Blood Glucose";

    private static String __strFileName = "BloodGlucoseRecord";
    private static String __strFilePath;

    TouchAction touchAction = new TouchAction(iosDriver);


    public WDFnL3BloodGlucoseRecord(int index){
        this._index = index;
        this.__strIdx += "/XCUIElementTypeCell["+_index+"]";
        this._lvRecordIdx = (IOSElement) iosDriver.findElementByXPath(__strIdx);
    }

    public WDFnL3BloodGlucoseRecord(){}


    public void clickBack(){
        iosDriver.findElementByAccessibilityId(__strBackToBGID).click();
    }

    public String getDate(){
        this._tvDate = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strDateXpath);
        return _tvDate.getText();
    }

    public String getSession(){
        this._tvSession = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strSessionXpath);
        return _tvSession.getText();
    }

    public String getGLU(){
        this._tvGLU = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strGLUXpath);
        return _tvGLU.getText();
    }

    public void update() throws InterruptedException {
        int sizeX = _lvRecordIdx.getSize().getWidth();
        int sizeY = _lvRecordIdx.getSize().getHeight();
        int pointX = _lvRecordIdx.getLocation().getX() + sizeX/2;
        int pointY = _lvRecordIdx.getLocation().getY() + sizeY/2;
        touchAction.tap(PointOption.point(pointX,pointY)).release().perform();
        WDFnL3BloodGlucose wdFnL3BloodGlucose = new WDFnL3BloodGlucose();
        wdFnL3BloodGlucose.setValue();

    }

    ArrayList alReadLine = new ArrayList();
    public boolean compare(ArrayList alBG) throws IOException {
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        alReadLine = fnFileEvent.getContent(__strFilePath);

        int sameNum = 0;
        for (int i=0; i<alReadLine.size(); i++){
            if (alReadLine.get(i).equals(alBG.get(i))){
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
