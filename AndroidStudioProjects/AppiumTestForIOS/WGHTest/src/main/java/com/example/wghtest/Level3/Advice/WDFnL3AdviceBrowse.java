package com.example.wghtest.Level3.Advice;

import org.openqa.selenium.By;

import java.time.Duration;
import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.AppHeight;
import static com.example.wghtest.WGHTestBase.AppWidth;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3AdviceBrowse {
    protected IOSElement _lvAdviceIdx,_tvDate,_tvContent;


    private String __strFirstAdvice = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]";
    private String __strFirstStarBtn = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeButton[1]";

    private String __strIdx = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeTable";
    private String __strDateXpath = "/XCUIElementTypeStaticText[2]";
    private String __strContentXpath = "/XCUIElementTypeStaticText[3]";

    private String __strDateName = "name LIKE '????-??-?? *'";
    private String __strContentName = "name LIKE '*建議對策'";
    private String __strStarLevelName = "name LIKE 'btn star*'";
    private String __strStarXpath = "(//XCUIElementTypeButton[@name=\"btn star unselect\"])[5]";
    private String __strSubmitID = "Submit";
    private String __strBackToAdviceID = "Online Advice";


    ArrayList<IOSElement> iosElements;
    ArrayList<IOSElement> elDate;
    ArrayList<IOSElement> elContent;

    TouchAction touchAction = new TouchAction(iosDriver);

    public WDFnL3AdviceBrowse(int index){
        this.__strIdx += "/XCUIElementTypeCell["+index+"]";
        this._lvAdviceIdx = (IOSElement) iosDriver.findElementByXPath(__strIdx);
        this._tvDate = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strDateXpath);
        this._tvContent = (IOSElement) iosDriver.findElementByXPath(__strIdx+__strContentXpath);
    }

    public WDFnL3AdviceBrowse(){
        elDate = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strDateName);
        elContent = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strContentName);
    }


    public void clickBrowseIdx(){
        iosDriver.findElement(By.xpath(__strIdx)).click();
    }

    public void clickBtnStar() throws InterruptedException {
        Thread.sleep(8000);
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strStarLevelName);

        //touchAction.press(PointOption.point(AppWidth/2,AppHeight/2)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(AppWidth/2,AppHeight/5)).release().perform();
        Thread.sleep(5000);
        int pX = iosElements.get(iosElements.size()-1).getLocation().getX();
        int pY = iosElements.get(iosElements.size()-1).getLocation().getY();
        int sX = iosElements.get(iosElements.size()-1).getSize().width;
        int sY = iosElements.get(iosElements.size()-1).getSize().height;


        touchAction.tap(PointOption.point((pX+(sX/2)),(pY+(sY/2)))).release().perform();

        iosDriver.findElementByAccessibilityId(__strSubmitID).click();


    }

    public void clickBackToAdvice(){
        iosDriver.findElementByAccessibilityId(__strBackToAdviceID).click();
    }

    public String getDate(){
        return _tvDate.getText();
    }

    public String getContent(){
        return _tvContent.getText();
    }

    public String getStarText(){
        return iosDriver.findElement(By.xpath(__strFirstStarBtn)).getText();
    }

    //getAllRecord function
    public ArrayList getAllContent(){
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strContentName);
        return iosElements;
    }

    public boolean isExistBrowse(String strDate,String strContent){

        this.__strDateName = strDate;
        this.__strContentName = strContent;

        if (elDate.size() != elContent.size()){
            System.out.println("get browse size have question");
            return false;
        }

        for (int i=0; i<elDate.size(); i++){
            //System.out.println(elDate.get(i).getText()+ " , " +elContent.get(i).getText());
            //System.out.println(__strDateName + " , " +__strContentName);
            if (elDate.get(i).getText().contains(__strDateName) && elContent.get(i).getText().contains(__strContentName)){
                return true;
            }
        }

        return false;
    }

    public ArrayList getAllDate(){
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strDateName);

        return iosElements;
    }

    public boolean isAIRecordExist(){
        try {
            iosDriver.findElement(By.xpath(__strFirstAdvice));
            return true;
        }catch (Exception eNoFindElement){
            return false;
        }
    }

}
