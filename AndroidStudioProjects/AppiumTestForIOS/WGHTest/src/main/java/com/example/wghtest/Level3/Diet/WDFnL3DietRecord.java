package com.example.wghtest.Level3.Diet;

import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3DietRecord {
    protected int _index;
    protected IOSElement _lvRecordIdx,_tvRecordContent,_tvRecordMemo;


    private static String __strFirstRecord = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]";
    private String __strIdx = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable";
    private static String __strRecordContentXpath = "/XCUIElementTypeStaticText[1]";
    private static String __strRecordMemoXpath = "/XCUIElementTypeStaticText[2]";
    private String __strBackToRecordID = "90days Record";
    private String __strBackToDietID = "Eating";

    private static String __strRecordDateID = "name LIKE '????/??/?? *'";
    private static String __strRecordContentID = "name LIKE '??:?? *'";


    TouchAction touchAction = new TouchAction(iosDriver);

    ArrayList<IOSElement> iosElements;


    public WDFnL3DietRecord(int index){
        this._index = index;
        __strIdx += "/XCUIElementTypeCell["+_index+"]";
        this._lvRecordIdx = (IOSElement) iosDriver.findElementByXPath(__strIdx);
    }

    public WDFnL3DietRecord(){ }


    public void clickBack(){
        iosDriver.findElementByAccessibilityId(__strBackToDietID).click();
    }

    public String getCategory(){
        this._tvRecordContent = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strRecordContentXpath);
        return _tvRecordContent.getText();
    }

    public String getMemo (){
        this._tvRecordMemo = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strRecordMemoXpath);
        return _tvRecordMemo.getText();
    }



    public ArrayList get90DaysRecord(){
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("name LIKE '????/??/?? *' OR name LIKE '??:?? *'");
        //確保抓取資料完全
        if (!(iosElements.get(0).getText().contains("/"))){
            iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("name LIKE '????/??/?? *' OR name LIKE '??:?? *'");
        }
        ArrayList al90DaysRecord = new ArrayList();
        for (int i=0; i<iosElements.size(); ++i){
            if (iosElements.get(i).getText().contains(":") || iosElements.get(i).getText().contains("/"))
            al90DaysRecord.add(iosElements.get(i).getText());
        }

        return al90DaysRecord;
    }

    public ArrayList getDateRecord(String strDate){
        ArrayList al90DaysRecord = get90DaysRecord();
        ArrayList alDateRecord = new ArrayList();

        boolean isFind = false;
        for (int i=0; i<al90DaysRecord.size(); i++){
            if (al90DaysRecord.get(i).toString().contains("/")){
                if (al90DaysRecord.get(i).toString().contains(strDate)){
                    alDateRecord.add(al90DaysRecord.get(i));
                    isFind = true;
                }else {
                    isFind = false;
                }
            }else if (al90DaysRecord.get(i).toString().contains(":")){
                if (isFind){
                    alDateRecord.add(al90DaysRecord.get(i));
                }
            }
        }

        return alDateRecord;
    }


    public void update() throws InterruptedException {
        int sizeX = _lvRecordIdx.getSize().getWidth();
        int sizeY = _lvRecordIdx.getSize().getHeight();
        int pointX = _lvRecordIdx.getLocation().getX() + sizeX/2;
        int pointY = _lvRecordIdx.getLocation().getY() + sizeY/2;
        touchAction.tap(PointOption.point(pointX,pointY)).release().perform();

        WDFnL3Diet wdFnL3Diet = new WDFnL3Diet();
        wdFnL3Diet.setValue("Beverage");
    }

    //由於紀錄比較與其他類別（血糖血壓等）方法不同   因此在TestCase直接比較
    //血糖血壓體溫體重 皆是取前３筆記錄做比較
    //飲食紀錄 為取記錄日期內的飲食類別做比較

}
