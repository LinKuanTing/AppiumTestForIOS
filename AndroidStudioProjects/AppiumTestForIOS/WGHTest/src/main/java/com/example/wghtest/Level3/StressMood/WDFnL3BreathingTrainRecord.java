package com.example.wghtest.Level3.StressMood;

import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3BreathingTrainRecord {
    protected IOSElement _btnToday;
    protected IOSElement _btnPreviousYear,_btnNextYear,_tvYearMonth;

    protected IOSElement _tvRecordContent;


    private static String __strTodayID = "Today   ";
    private static String __strPreviousYearID = "Month Calendar Left Arrow";
    private static String __strNextYearID = "Month Calendar Right Arrow";
    private static String __strYearMonthID = "name LIKE '????-*'";
    private static String __strMonthYrarID = "name LIKE '* 20??'";
    private String __strBackToTrainingID = "arrow left";

    private static String __strIdx = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable";
    private static String __strRecordContentXpath = "/XCUIElementTypeStaticText[1]";

    private String __strFileName;
    private String __strFilePath;

    TouchAction touchAction = new TouchAction(iosDriver);

    ArrayList<IOSElement> iosElements;


    public WDFnL3BreathingTrainRecord(){ }

    public void clickBack(){
        iosDriver.findElementByAccessibilityId(__strBackToTrainingID).click();
    }

    public void clickToday(){
        this._btnToday = (IOSElement) iosDriver.findElementByAccessibilityId(__strTodayID);
        _btnToday.click();
    }

    public void clickPreviousYear(){
        this._btnPreviousYear = (IOSElement) iosDriver.findElementByAccessibilityId(__strPreviousYearID);
        _btnPreviousYear.click();
    }

    public void clickNextYear(){
        this._btnNextYear = (IOSElement) iosDriver.findElementByAccessibilityId(__strNextYearID);
        _btnNextYear.click();
    }

    public String getYearMonth(){
        try {
            this._tvYearMonth = (IOSElement) iosDriver.findElementByIosNsPredicate(__strYearMonthID);
        }catch (Exception eNoFindElement){
            this._tvYearMonth = (IOSElement) iosDriver.findElementByIosNsPredicate(__strMonthYrarID);
        }

        return _tvYearMonth.getText();
    }

    public void clickDate(String strYearMonth, String strDate){
        //月份處理
        for (int i=0; i<=4; i++){
            if (strYearMonth.equals(getYearMonth())){
                break;
            }else if (i==4){
                return;
            }
            else {
                clickPreviousYear();
                try {
                    Thread.sleep(5000);
                    iosDriver.switchTo().alert().accept();
                }catch (Exception eNoFindElement){}
            }

        }
        //日期處理
        IOSElement tvDate;
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeStaticText'");

        //取各星期座標
        ArrayList pointWeek = new ArrayList();
        for (int i=1; i<=7; i++){
            pointWeek.add(iosElements.get(i).getLocation().getX());
        }
        //取得目前日期
        if (iosElements.size()>9){
            tvDate = iosElements.get(iosElements.size()-2);
        }else {
            tvDate = iosElements.get(iosElements.size()-1);
        }
        //計算中心點位置
        int date_X = tvDate.getLocation().getX()+tvDate.getSize().getWidth()/2;
        int date_Y = tvDate.getLocation().getY()+tvDate.getSize().getHeight()/2;
        //與周圍日期中心點  Ｘ距離相差 Ｙ距離相差
        int distance_X = tvDate.getSize().getWidth();
        int distance_Y = tvDate.getSize().getHeight();

        //取得禮拜幾
        int week = 0;
        for(int i=0; i<pointWeek.size(); i++){
            if (tvDate.getLocation().getX() == (int)pointWeek.get(i)){
                week = i;
            }
        }

        //計算目標日期座標
        int startDate = Integer.parseInt(tvDate.getText());
        int endDate = Integer.parseInt(strDate);


        int multipleY = (endDate-startDate)/7;
        int remainder = endDate-((7*multipleY)+startDate);

        int multipleX;
        if ((endDate-startDate)>0){
            if (remainder>(6-week)){
                multipleY += 1;
                multipleX = remainder-7;
            }else {
                multipleX = remainder;
            }
        }else if ((endDate-startDate)<0){
            if (Math.abs(remainder)>week){
                multipleY -= 1;
                multipleX = remainder+7;
            }else {
                multipleX = remainder;
            }
        }else{
            return;
        }

        //System.out.println(multipleY+","+multipleX+","+remainder);
        //System.out.println((date_X+distance_X*multipleX)+","+(date_Y+distance_Y*multipleY));
        touchAction.tap(PointOption.point((date_X+distance_X*multipleX),(date_Y+distance_Y*multipleY))).release().perform();

    }

    public ArrayList getDateRecord(){
        ArrayList alRecordData = new ArrayList();
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeCell'");
        for (int i=1; i<=iosElements.size(); i++){
            this._tvRecordContent = (IOSElement) iosDriver.findElementByXPath(__strIdx+"/XCUIElementTypeCell["+i+"]"+__strRecordContentXpath);

            String[] strContent = _tvRecordContent.getText().split("\n");
            //[0]->Time    [1]->Level    [2]->Duration    [3]->HR data
            String[] strTime = strContent[0].replaceAll(" ","").split(":");
            String[] strLevel = strContent[1].split(":");
            String strData = strTime[1]+":"+strTime[2]+"/"+strLevel[1];
            alRecordData.add(strData);
        }
        return alRecordData;
    }

}
