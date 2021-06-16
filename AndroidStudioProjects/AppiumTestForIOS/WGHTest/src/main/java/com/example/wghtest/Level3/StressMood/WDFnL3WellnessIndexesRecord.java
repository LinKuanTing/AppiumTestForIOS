package com.example.wghtest.Level3.StressMood;

import com.example.wghtest.other.FnFileEvent;

import java.io.IOException;
import java.util.ArrayList;

import io.appium.java_client.ios.IOSElement;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3WellnessIndexesRecord {
    protected int _index;
    protected IOSElement _lvRecordIdx;
    protected IOSElement _tvDate,_tvHR;

    private static String __strFirstRecord = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]";

    private String __strIdx = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable";
    private String __strDateXpath = "/XCUIElementTypeStaticText[5]";
    private String __strHRXpath = "/XCUIElementTypeStaticText[1]";
    private String __strDateID = "name LIKE '????-??-?? *'";
    private String __strHRID = "name LIKE '* BPM'";
    private String __strBackToStressMood = "Add indicator";

    private static String __strFileName = "WellnessIndexesRecord";
    private static String __strFilePath;

    ArrayList<IOSElement> iosElements;


    public WDFnL3WellnessIndexesRecord(int index){
        this._index = index;
        this.__strIdx += "/XCUIElementTypeCell[" + _index + "]";
        this._lvRecordIdx = (IOSElement) iosDriver.findElementByXPath(__strIdx);
        this._tvDate = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strDateXpath);
        this._tvHR = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strHRXpath);
    }

    public WDFnL3WellnessIndexesRecord(){ }


    public void clickBack(){
        iosDriver.findElementByAccessibilityId(__strBackToStressMood).click();
    }

    public String getDate(){
        return _tvDate.getText();
    }

    public String getHR(){
        return  _tvHR.getText();
    }

    public ArrayList getAllRecordDate(){
        ArrayList alDate = new ArrayList();
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strDateID);
        for (int i=0; i<iosElements.size(); i++){
            alDate.add(iosElements.get(i).getText());
        }
        return alDate;
    }

    public ArrayList getAllRecordHeartRate(){
        ArrayList alHR = new ArrayList();
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strHRID);
        for (int i=0; i<iosElements.size(); i++){
            alHR.add(iosElements.get(i).getText().replaceAll(" ","").replaceAll("BPM",""));
        }
        return alHR;
    }


    ArrayList alReadLine = new ArrayList();
    public boolean compare(ArrayList alWellnessIndexes) throws IOException {
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        alReadLine = fnFileEvent.getContent(__strFilePath);

        int sameNum = 0;
        for (int i=0; i<alReadLine.size(); i++){
            if (alReadLine.get(i).equals(alWellnessIndexes.get(i))){
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
