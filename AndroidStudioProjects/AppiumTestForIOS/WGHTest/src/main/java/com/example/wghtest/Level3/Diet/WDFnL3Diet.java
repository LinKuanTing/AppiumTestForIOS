package com.example.wghtest.Level3.Diet;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3Diet extends WDFnBaseMeasureMenuItems {
    protected IOSElement _tvCategory, _tvDate, _tvPhoto, _etMemo;

    protected static String _strAddDietContent;

    private static String __strRecordID = "BtnList";

    private static String __strNotUploadMsgID = "name LIKE 'Waiting for upload：* records'";

    private static String __strCellXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell";
    private static String __strDateWarningXpath = "/XCUIElementTypeStaticText[1]";
    private static String __strCellTextViewXpath = "/XCUIElementTypeStaticText[2]";
    private static String __strCellTextFieldXpath = "/XCUIElementTypeTextField";
    private static String __strMemoID = "type == 'XCUIElementTypeTextField' AND value LIKE 'Memo'";
    private static String __strDoneID = "Done";

    private static String __strTimeID = "name LIKE '??/?? *'";
    private static String __strDietSuggestionID = "Diet Suggestions";
    private static String __strBackToDiet = "Eating";

    private static String __strSaveAndUploadID = "Save and upload";
    private static String __strSaveUploadLaterID = "Save and upload later";
    private static String __strSaveToPhoneAlbumiD = "Save to phone album";
    private static String __strCancelID = "Cancel";

    private static String __strWeightMsg1 = "weight_under";
    private static String __strWeightMsg2 = "weight_good";
    private static String __strWeightMsg3 = "weight_over";
    private static String __strWeightMsg4 = "weight_obesity";

    private static String __strFileName;
    private static String __strFilePath;


    ArrayList<IOSElement> iosElements;

    public WDFnL3Diet() {
        set_btnTime();
    }

    public void set_btnTime() {
        this._btnTime = (IOSElement) iosDriver.findElementByIosNsPredicate(__strTimeID);
        this._tvDate = _btnTime;
    }



    public void setValue(String strDietCategory) throws InterruptedException {
        _strAddDietContent = "";

        //選擇用餐類別
        this._tvCategory = (IOSElement) iosDriver.findElementByXPath(__strCellXpath+"[2]"+__strCellTextViewXpath);
        _tvCategory.click();

        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypePickerWheel'");
        int pointX = iosElements.get(0).getLocation().getX()+iosElements.get(0).getSize().getWidth()/2;
        int pointY = iosElements.get(0).getLocation().getY()+iosElements.get(0).getSize().getHeight()/2;

        touchAction.press(PointOption.point(pointX,pointY+60)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(pointX,pointY-180)).release().perform();

        switch (strDietCategory){
            case "Dessert":
            case "點心":
                touchAction.press(PointOption.point(pointX,pointY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(pointX,pointY)).release().perform();
                break;
            case "Beverage":
            case "含糖飲料":
                touchAction.press(PointOption.point(pointX,pointY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(pointX,pointY+65)).release().perform();
                break;
            case "Dinner":
            case "晚餐":
                touchAction.press(PointOption.point(pointX,pointY-60)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(pointX,pointY+60)).release().perform();
                break;
            case "Lunch":
            case "午餐":
                touchAction.press(PointOption.point(pointX,pointY-120)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(pointX,pointY+60)).release().perform();
                break;
            case "Breakfast":
            case "早餐":
                touchAction.press(PointOption.point(pointX,pointY-180)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(pointX,pointY+60)).release().perform();
                break;
        }
        iosDriver.findElementByAccessibilityId(__strDoneID).click();


        //設定時間
        setTime();
        String[] strDate = getDate().split(" ");    //[0]->date  [1]->time


        //備註
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String strToday = dateFormat.format(date);
        this._etMemo = (IOSElement) iosDriver.findElementByIosNsPredicate("type == 'XCUIElementTypeTextField'");
        if (_etMemo.getText().contains("Add")){
            _etMemo.clear();
            _etMemo.sendKeys("TestDemo_Update_"+strToday+"\n");
        }else {
            _etMemo.sendKeys("TestDemo_Add_"+strToday+"\n");
        }




        //儲存
        _strAddDietContent = strDate[0] +","+ strDate[1] +" "+ getCategory()+","+strToday;

        clickSave();
        iosDriver.findElementByAccessibilityId(__strSaveAndUploadID).click();
    }

    public void setCategory(String strDietCategory){
        this._tvCategory = (IOSElement) iosDriver.findElementByXPath(__strCellXpath+"[2]"+__strCellTextViewXpath);
        _tvCategory.click();

        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypePickerWheel'");
        int pointX = iosElements.get(0).getLocation().getX()+iosElements.get(0).getSize().getWidth()/2;
        int pointY = iosElements.get(0).getLocation().getY()+iosElements.get(0).getSize().getHeight()/2;

        touchAction.press(PointOption.point(pointX,pointY+60)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(pointX,pointY-180)).release().perform();

        switch (strDietCategory){
            case "Dessert":
            case "點心":
                touchAction.press(PointOption.point(pointX,pointY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(pointX,pointY)).release().perform();
                break;
            case "Beverage":
            case "含糖飲料":
                touchAction.press(PointOption.point(pointX,pointY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(pointX,pointY+70)).release().perform();
                break;
            case "Dinner":
            case "晚餐":
                touchAction.press(PointOption.point(pointX,pointY-60)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(pointX,pointY+60)).release().perform();
                break;
            case "Lunch":
            case "午餐":
                touchAction.press(PointOption.point(pointX,pointY-120)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(pointX,pointY+60)).release().perform();
                break;
            case "Breakfast":
            case "早餐":
                touchAction.press(PointOption.point(pointX,pointY-180)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(pointX,pointY+60)).release().perform();
                break;
        }
        iosDriver.findElementByAccessibilityId(__strDoneID).click();
    }


    public void setValueBySaveUploadLater(){
        setCategory("Dessert");
        clickSave();
        iosDriver.findElementByAccessibilityId(__strSaveUploadLaterID).click();
    }



    public String getDateWarningMsg(){
        return iosDriver.findElementByXPath(__strCellXpath+"[3]"+__strDateWarningXpath).getText();
    }

    public String getCategory(){
        this._tvCategory = (IOSElement) iosDriver.findElementByXPath(__strCellXpath+"[2]"+__strCellTextViewXpath);
        return _tvCategory.getText();
    }

    public String getDate(){
        return _tvDate.getText();
    }

    public String getNotUploadRecordMsg(){
        return iosDriver.findElementByIosNsPredicate(__strNotUploadMsgID).getText();
    }

    public int getDailyCal() throws IOException {
        __strFileName = "AccountInfo";
        int age = -1, gender = -1, exerciseNo = -1, reduceCal = -1;
        double bmi, height = -1, weight = -1;

        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
        while (br.ready()) {
            String strReadLine = br.readLine();
            if (strReadLine.contains("Age")) {
                age = Integer.parseInt(br.readLine());
            } else if (strReadLine.contains("Gender")) {
                gender = Integer.parseInt(br.readLine());
            } else if (strReadLine.contains("Height")) {
                height = Double.parseDouble(br.readLine());
            } else if (strReadLine.contains("Current Weight")) {
                weight = Double.parseDouble(br.readLine());
            } else if (strReadLine.contains("ExerciseNo")) {
                exerciseNo = Integer.parseInt(br.readLine());
            } else if (strReadLine.contains("Reduce")) {
                reduceCal = Integer.parseInt(br.readLine());
            }
        }

        if (age == -1 || gender == -1 || exerciseNo == -1 || reduceCal == -1 || height == -1 || weight == -1){
            return -1;
        }

        height /= 100;
        bmi = Double.parseDouble(new DecimalFormat("#.#").format(weight / (height * height)));
        //將計算的BMI許入檔案  在之後的測試會用到
        FileWriter fw = new FileWriter(__strFilePath,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("###BMI"+"\r\n");
        bw.write(bmi+"\r\n");
        bw.flush();
        bw.close();

        String bmiMessage = "";
        //取得體重BMI範圍
        if (age >= 18) {
            if (bmi < 18.5) {
                bmiMessage += __strWeightMsg1;
            } else if (bmi >= 18.5 && bmi < 24.0) {
                bmiMessage += __strWeightMsg2;
            } else if (bmi >= 24.0 && bmi < 27.0) {
                bmiMessage += __strWeightMsg3;
            } else if (bmi >= 27.0) {
                bmiMessage += __strWeightMsg4;
            }
        } else {
            if (gender == 1) { //未滿18歲男生
                switch (age) {
                    case 2:
                        if (bmi < 15.2) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 15.2 && bmi < 17.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.7 && bmi < 19.0) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 19.0) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 3:
                        if (bmi < 14.8) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.8 && bmi < 17.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.7 && bmi < 19.1) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 19.1) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 4:
                        if (bmi < 14.4) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.4 && bmi < 17.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.7 && bmi < 19.3) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 19.3) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 5:
                        if (bmi < 14.0) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.0 && bmi < 17.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.7 && bmi < 19.4) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 19.4) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 6:
                        if (bmi < 13.9) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 13.9 && bmi < 17.9) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.9 && bmi < 19.7) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 19.7) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 7:
                        if (bmi < 14.7) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.7 && bmi < 18.6) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 18.6 && bmi < 21.2) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 21.2) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 8:
                        if (bmi < 15.0) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 15.0 && bmi < 19.3) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 19.3 && bmi < 22.0) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 22.0) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 9:
                        if (bmi < 15.2) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 15.2 && bmi < 19.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 19.7 && bmi < 22.5) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 22.5) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 10:
                        if (bmi < 15.4) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 15.4 && bmi < 20.3) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 20.3 && bmi < 22.9) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 22.9) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 11:
                        if (bmi < 15.8) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 15.8 && bmi < 21.0) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 21.0 && bmi < 23.5) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 23.5) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 12:
                        if (bmi < 16.4) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 16.4 && bmi < 21.5) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 21.5 && bmi < 24.2) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 24.2) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 13:
                        if (bmi < 17.0) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 17.0 && bmi < 22.2) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 22.2 && bmi < 24.8) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 24.8) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 14:
                        if (bmi < 17.6) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 17.6 && bmi < 22.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 22.7 && bmi < 25.2) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.2) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 15:
                        if (bmi < 18.2) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 18.2 && bmi < 23.1) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 23.1 && bmi < 25.5) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.5) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 16:
                        if (bmi < 18.6) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 18.6 && bmi < 23.4) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 23.4 && bmi < 25.6) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.6) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 17:
                        if (bmi < 19.0) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 19.0 && bmi < 23.6) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 23.6 && bmi < 25.6) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.6) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    default:
                        break;
                }
            } else {// 未滿18歲女生
                switch (age) {
                    case 2:
                        if (bmi < 14.9) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.9 && bmi < 17.3) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.3 && bmi < 18.3) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 18.3) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 3:
                        if (bmi < 14.5) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.5 && bmi < 17.2) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.2 && bmi < 18.5) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 18.5) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 4:
                        if (bmi < 14.2) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.2 && bmi < 17.1) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.1 && bmi < 18.6) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 18.6) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 5:
                        if (bmi < 13.9) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 13.9 && bmi < 17.1) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.1 && bmi < 18.9) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 18.9) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 6:
                        if (bmi < 13.6) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 13.6 && bmi < 17.2) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.2 && bmi < 19.1) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 19.1) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 7:
                        if (bmi < 14.4) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.4 && bmi < 18.0) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 18.0 && bmi < 20.3) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 20.3) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 8:
                        if (bmi < 14.6) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.6 && bmi < 18.8) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 18.8 && bmi < 21.0) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 21.0) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 9:
                        if (bmi < 14.9) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.9 && bmi < 19.3) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 19.3 && bmi < 21.6) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 21.6) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 10:
                        if (bmi < 15.2) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 15.2 && bmi < 20.1) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 20.1 && bmi < 22.3) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 22.3) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 11:
                        if (bmi < 15.8) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 15.8 && bmi < 20.9) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 20.9 && bmi < 23.1) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 23.1) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 12:
                        if (bmi < 16.4) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 16.4 && bmi < 21.6) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 21.6 && bmi < 23.9) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 23.9) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 13:
                        if (bmi < 17.0) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 17.0 && bmi < 22.2) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 22.2 && bmi < 24.6) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 24.6) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 14:
                        if (bmi < 17.6) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 17.6 && bmi < 22.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 22.7 && bmi < 25.1) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.1) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 15:
                        if (bmi < 18.0) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 18.0 && bmi < 22.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 22.7 && bmi < 25.3) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.3) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 16:
                        if (bmi < 18.2) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 18.2 && bmi < 22.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 22.7 && bmi < 25.3) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.3) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 17:
                        if (bmi < 18.3) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 18.3 && bmi < 22.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 22.7 && bmi < 25.3) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.3) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    default:
                        break;

                }
            }
        }


        int dayneedkal = 1200;
        int mHealthkal;

        if (bmiMessage.equals(__strWeightMsg1)){
            switch (exerciseNo){
                case 1:
                    dayneedkal = (int) Math.round((35 * weight) / 100) * 100;
                    break;
                case 2:
                    dayneedkal = (int) Math.round((40 * weight) / 100) * 100;
                    break;
                case 3:
                    dayneedkal = (int) Math.round((45 * weight) / 100) * 100;
                    break;
            }
        }else if (bmiMessage.equals(__strWeightMsg2)) {
            switch (exerciseNo) {
                case 1:
                    dayneedkal = (int) Math.round((30 * weight) / 100) * 100;
                    break;
                case 2:
                    dayneedkal = (int) Math.round((35 * weight) / 100) * 100;
                    break;
                case 3:
                    dayneedkal = (int) Math.round((40 * weight) / 100) * 100;
                    break;
            }
        }else if(bmiMessage.equals(__strWeightMsg3) || bmiMessage.equals(__strWeightMsg4)){
            switch (exerciseNo) {
                case 1:
                    dayneedkal = (int) Math.round((25 * weight) / 100) * 100;
                    break;
                case 2:
                    dayneedkal = (int) Math.round((30 * weight) / 100) * 100;
                    break;
                case 3:
                    dayneedkal = (int) Math.round((35 * weight) / 100) * 100;
                    break;
            }
        }else {
            System.out.println("unknown error");
            System.out.println("BMI Message: "+ bmiMessage);
            System.out.println("Daily exerciseNo: " + exerciseNo);
        }

        if (dayneedkal < 1200){
            dayneedkal = 1200;
        }

        mHealthkal = dayneedkal - reduceCal;
        if (mHealthkal < 1200){
            mHealthkal = 1200;
        }


        return mHealthkal;

    }




    public void clickRecord() throws InterruptedException {
        _btnRecord = (IOSElement) iosDriver.findElementByAccessibilityId(__strRecordID);
        _btnRecord.click();
        Thread.sleep(3000);
    }

    public void clickDataMean(){
        _btnDataMean = (IOSElement) iosDriver.findElementByAccessibilityId(__strDietSuggestionID);
        _btnDataMean.click();
    }


}
