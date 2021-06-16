package com.example.wghtest.Level3.Thermometer;

import com.example.wghtest.other.FnFileEvent;

import java.io.IOException;
import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3ThermometerRecord {
    protected int _index;
    protected IOSElement _lvRecordIdx;
    protected IOSElement _tvDate,_tvType,_tvTemp;

    private static String __strFirstRecord = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]";

    private String __strIdx = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable";
    private String __strDateXpath = "/XCUIElementTypeStaticText[1]";
    private String __strInfoXpath = "/XCUIElementTypeStaticText[2]";
    private String __strBackToRecordID = "arrow left";
    private String __strBackToTempID = "arrow left";

    private static String __strFileName = "ThermometerRecord";
    private static String __strFilePath;

    TouchAction touchAction = new TouchAction(iosDriver);


    public WDFnL3ThermometerRecord(int index){
        this._index = index;
        this.__strIdx += "/XCUIElementTypeCell["+_index+"]";
        this._lvRecordIdx = (IOSElement) iosDriver.findElementByXPath(__strIdx);
    }

    public WDFnL3ThermometerRecord(){ }


    public void clickBack(){
        iosDriver.findElementByAccessibilityId(__strBackToTempID).click();
    }

    public String getDate(){
        this._tvDate = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strDateXpath);
        return _tvDate.getText();
    }

    public String getType(){
        this._tvType = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strInfoXpath);
        String[] info = _tvType.getText().split(" ");
        return info[0];
    }

    public String getTemp(){
        this._tvTemp = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strInfoXpath);
        String[] info = _tvTemp.getText().split("  ");
        return info[1].replaceAll("℃","");
    }


    public void update() throws InterruptedException {
        int sizeX = _lvRecordIdx.getSize().getWidth();
        int sizeY = _lvRecordIdx.getSize().getHeight();
        int pointX = _lvRecordIdx.getLocation().getX() + sizeX/2;
        int pointY = _lvRecordIdx.getLocation().getY() + sizeY/2;
        touchAction.tap(PointOption.point(pointX,pointY)).release().perform();
        WDFnL3Thermometer wdFnL3Thermometer = new WDFnL3Thermometer();
        wdFnL3Thermometer.setValue();

        //點擊儲存後 仍然在修改體重頁面  必須在點擊左上角返回  才回到紀錄列表
        //  (等待往後版本修改)
        Thread.sleep(5000);
        iosDriver.findElementByAccessibilityId(__strBackToRecordID).click();
        Thread.sleep(5000);
        try {
            iosDriver.switchTo().alert().accept();
        }catch (Exception eNoFindElement){ }
    }

    ArrayList alReadLine = new ArrayList();
    public boolean compare(ArrayList alTemp) throws IOException {
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        alReadLine = fnFileEvent.getContent(__strFilePath);

        int sameNum = 0;
        for (int i=0; i<alReadLine.size(); i++){
            if (alReadLine.get(i).equals(alTemp.get(i))){
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
