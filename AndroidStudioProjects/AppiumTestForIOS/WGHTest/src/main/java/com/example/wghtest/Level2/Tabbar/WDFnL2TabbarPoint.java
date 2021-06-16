package com.example.wghtest.Level2.Tabbar;

import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL2TabbarPoint {
    protected IOSElement _btnDescription, _btnEarned, _btnUsed, _btnDeadline;
    protected IOSElement _stWowPoints, _stGetPoints, _stUsePoints;

    private static String __strWowPointsXpath;
    private static String __strGetPointsXpath;
    private static String __strUsePointsXpath;

    //Earned msg
    private static String __strShowMoreID = "Show more";
    private static String __strNoMoreID = "No more information";
    //Used msg
    private static String __strNoRecordID = "There is no recent record";

    private static String __strDescriptionID = "Description";
    private static String __strEarnedID = " Get Records ";
    private static String __strUsedID = " Use records ";
    private static String __strDeadlineID = "Period of use";

    TouchAction touchAction = new TouchAction(iosDriver);

    public WDFnL2TabbarPoint() {
        ArrayList<IOSElement> iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeStaticText'");

        _stWowPoints = iosElements.get(1);
        _stGetPoints = iosElements.get(4);
        _stUsePoints = iosElements.get(6);

        _btnDescription = (IOSElement) iosDriver.findElementByAccessibilityId(__strDescriptionID);
        _btnEarned = (IOSElement) iosDriver.findElementByAccessibilityId(__strEarnedID);
        _btnUsed = (IOSElement) iosDriver.findElementByAccessibilityId(__strUsedID);
        _btnDeadline = (IOSElement) iosDriver.findElementByAccessibilityId(__strDeadlineID);

    }

    public int getWowPoints() {
        return Integer.parseInt(_stWowPoints.getText());
    }

    public int getEarnedPoints() {
        return Integer.parseInt(_stGetPoints.getText());
    }

    public int getUsedPoints() {
        return Integer.parseInt(_stUsePoints.getText());
    }


    public void clickTerms() {
        _btnDescription.click();
    }

    public void clickEarned() {
        _btnEarned.click();
    }

    public void clickUsed() {
        _btnUsed.click();
    }

    public void clickDeadline(){
        _btnDeadline.click();
    }



    public int getEarnedTotalPoints() throws InterruptedException {
        while (true){
            try {
                iosDriver.findElementByAccessibilityId(__strShowMoreID).click();
                Thread.sleep(3000);
            }catch (Exception eNoFindElement){
                iosDriver.findElementByAccessibilityId(__strNoMoreID);
                break;
            }
        }


        int totalPoints = 0;
        ArrayList<IOSElement> iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("name IN {'1','2','10','300','-1','-2','-10','-300'}");

        for (int i=0; i<iosElements.size(); i++){
            totalPoints += Integer.parseInt(iosElements.get(i).getText());

        }

        return totalPoints;
    }

    public int getUsedTotalPoints(){
        try {
            iosDriver.findElementByAccessibilityId(__strNoRecordID);
            return 0;
        }catch (Exception eNoFindElement){ }

        int totalPoints = 0;
        ArrayList<IOSElement> iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeStaticText'");
        for (int i=9; i< iosElements.size(); i+=3){
            totalPoints += Integer.parseInt(iosElements.get(i).getText());
        }

        return totalPoints;
    }

    public int getDeadlineTotalPoints(){
        int totalPoints = 0;

        ArrayList<IOSElement> iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeStaticText'");
        for (int i=7; i< iosElements.size(); i+=3){
            totalPoints += Integer.parseInt(iosElements.get(i).getText());
        }

        return totalPoints;
    }



}