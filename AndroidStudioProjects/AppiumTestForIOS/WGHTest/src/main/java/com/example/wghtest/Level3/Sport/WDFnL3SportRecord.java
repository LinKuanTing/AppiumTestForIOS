package com.example.wghtest.Level3.Sport;

import com.example.wghtest.other.FnFileEvent;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3SportRecord {
    protected IOSElement _btnToday;
    protected IOSElement _btnPreviousYear,_btnNextYear,_tvYearMonth;
    protected IOSElement _lvRecordIdx;
    protected IOSElement _tvSportItem,_tvSportData;

    private static String __strTodayID = "Today";
    private static String __strPreviousYearID = "Month Calendar Left Arrow";
    private static String __strNextYearID = "Month Calendar Right Arrow";
    private static String __strYearMonthID = "name LIKE '????-*'";
    private String __strBackToRecordID = "90days Record";
    private String __strBackToSportID = "Sport";

    private static String __strFirstRecord = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]";
    private static String __strIdx = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable";
    private static String __strSportItemXpath = "/XCUIElementTypeStaticText[1]";
    private static String __strSportDataXpath = "/XCUIElementTypeStaticText[2]";

    TouchAction touchAction = new TouchAction(iosDriver);

    ArrayList<IOSElement> iosElements;
    private String __strFileName;
    private String __strFilePath;


    public WDFnL3SportRecord(){ }

    public void clickBack(){
        iosDriver.findElementByAccessibilityId(__strBackToSportID).click();
    }

    public void clickBackToRecord(){
        iosDriver.findElementByAccessibilityId(__strBackToRecordID).click();
        try{
            iosDriver.switchTo().alert().accept();
        }catch (Exception eNoFindElement){ }
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

    public String getDate(){
        IOSElement tvDate;
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeStaticText'");
        if (iosElements.size()>9){
            tvDate = iosElements.get(iosElements.size()-2);
        }else {
            tvDate = iosElements.get(iosElements.size()-1);
        }
        return tvDate.getText();
    }

    public void clickDate(String strYearMonth, String strDate){
        //月份處理
        for (int i=0; i<4; i++){
            if (strYearMonth.equals(getYearMonth())){
                break;
            }else if (i==3){
                return;
            }
            else {
                clickPreviousYear();
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





    public String getYearMonth(){
        this._tvYearMonth = (IOSElement) iosDriver.findElementByIosNsPredicate(__strYearMonthID);
        return _tvYearMonth.getText();
    }

    //取得當日日期裡的運動紀錄
    public ArrayList getDateRecord(){
        ArrayList alRecordData = new ArrayList();
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeCell'");
        for (int i=1; i<=iosElements.size(); i++){
            this._lvRecordIdx = iosElements.get(i-1);
            this._tvSportItem = (IOSElement) iosDriver.findElementByXPath(__strIdx+"/XCUIElementTypeCell["+i+"]"+__strSportItemXpath);
            this._tvSportData = (IOSElement) iosDriver.findElementByXPath(__strIdx+"/XCUIElementTypeCell["+i+"]"+__strSportDataXpath);
            String strSportData[] = _tvSportData.getText().replace("(","").replace(")","").split(" ");
            String strData = _tvSportItem.getText()+"/"+strSportData[0]+"/"+strSportData[1].replace("Kcal"," Kcal");
            alRecordData.add(strData);
        }

        return alRecordData;
    }

    //用來比較新增紀錄是否出現在記錄欄位裡
    public boolean isExistRecord(ArrayList alAddRecord, ArrayList alRecord){
        int sameNum = 0;
        for (int i=0; i<alAddRecord.size(); i++){
            for (int j=0; j<alRecord.size(); j++){
                if (alAddRecord.get(i).equals(alRecord.get(j))){
                    sameNum++;
                }
            }
        }

        if (sameNum == alAddRecord.size()){
            return true;
        }else {
            return false;
        }
    }

    //檢查歷史資料（compare data）是否出現在記錄裡
    public boolean isExistRecord(ArrayList alReadData){
        int sameNum = 0;

        for (int i=0; i<alReadData.size(); i++){
            String[] strTxtData = alReadData.get(i).toString().split("/");
            String strYearMonth = strTxtData[0];
            String strDate = strTxtData[1];
            String strSportItem = strTxtData[2];
            clickDate(strYearMonth,strDate);
            ArrayList alDateRecord = getDateRecord();
            for (int j=0; j<alDateRecord.size(); j++){
                if (alDateRecord.get(j).toString().contains(strSportItem)){
                    sameNum++;
                    break;
                }
            }
        }

        if (sameNum == alReadData.size()){
            return true;
        }else {
            return false;
        }

    }

    public void updata() throws InterruptedException {
        this._lvRecordIdx = (IOSElement) iosDriver.findElementByXPath(__strFirstRecord);
        this._tvSportItem = (IOSElement) iosDriver.findElementByXPath(__strFirstRecord+__strSportItemXpath);
        String strSportItem = _tvSportItem.getText();
        int pointX = _lvRecordIdx.getLocation().getX()+_lvRecordIdx.getSize().getWidth()/2;
        int pointY = _lvRecordIdx.getLocation().getY()+_lvRecordIdx.getSize().getHeight()/2;
        touchAction.tap(PointOption.point(pointX,pointY)).release().perform();
        WDFnL3Sport wdFnL3Sport = new WDFnL3Sport();

        if (strSportItem.equals("Pedometer")){
            wdFnL3Sport.setPedometer();
        }else {
            wdFnL3Sport.setValue();
        }
    }

    public int getWeeklySportTime() throws IOException {
        __strFileName = "setCapabilities";
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        ArrayList alCaps = fnFileEvent.getContent(__strFilePath);
        String strLocal = "";
        for (int i=0; i<alCaps.size(); i++){
            if (alCaps.get(i).equals("locale")){
                strLocal = alCaps.get(i+1).toString();
                break;
            }
        }

        //日期格式處理
        SimpleDateFormat dateFormat;
        if (strLocal.equals("US")){
            dateFormat = new SimpleDateFormat("yyyy/MMMM/d", Locale.US);
        }else if (strLocal.equals("TW")){
            dateFormat = new SimpleDateFormat("yyyy/M月/d",Locale.TAIWAN);
        }else {
            dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        }

        //抓取今天日期
        Calendar cal = Calendar.getInstance();
        Date TodayDate = cal.getTime();
        //抓取今天星期
        int week = Integer.parseInt(new SimpleDateFormat("u").format(TodayDate));
        //推算 當週日～六 日期
        ArrayList alWeekDate = new ArrayList();
        cal.add(Calendar.DATE,-week);
        for (int i=0; i<7; i++){
            cal.add(Calendar.DATE,+i);
            alWeekDate.add(dateFormat.format(cal.getTime()));
            cal.add(Calendar.DATE,-i);
        }
        //將 Year/Month/Date 轉換成 Year-Month/Date 方便查詢
        for (int i=0; i<alWeekDate.size(); i++){
            String[] strWeekDate = alWeekDate.get(i).toString().split("/");
            alWeekDate.set(i , strWeekDate[0]+"-"+strWeekDate[1]+"/"+strWeekDate[2]);
        }

        //尋找一週內的紀錄
        ArrayList alWeekRecord = new ArrayList();
        for (int i=week; i>=0; i--){
            String[] strDay = alWeekDate.get(i).toString().split("/");
            //[0]->YearMonth [1]>Date
            clickDate(strDay[0],strDay[1]);

            ArrayList al = getDateRecord();
            if (al != null){
                alWeekRecord.addAll(al);
            }
        }

        //計算本週運動時間
        int weeklyExercise = 0;
        for (int i=0; i<alWeekRecord.size(); i++){
            String strRecordContent[] = alWeekRecord.get(i).toString().split("/");
            //[0]->sport item  [1]->Duration  [2]->Calories
            //運動記錄為計步器則不計算
            if (strRecordContent[0].equals("Pedometer")){
                continue;
            }
            //處理 X hour Y min
            if (strRecordContent[1].contains("min")){
                String strExerciseTime[] = strRecordContent[1].replaceAll("Hour","/").replaceAll("min","").split("/");
                weeklyExercise += Integer.parseInt(strExerciseTime[0])*60 + Integer.parseInt(strExerciseTime[1]);
            }
            else {
                //處理 X hour
                String strExerciseTime = strRecordContent[1].replaceAll("Hour","");
                weeklyExercise += Integer.parseInt(strExerciseTime)*60;
            }
        }

        //System.out.println(weeklyExercise);
        return weeklyExercise;
    }


}
