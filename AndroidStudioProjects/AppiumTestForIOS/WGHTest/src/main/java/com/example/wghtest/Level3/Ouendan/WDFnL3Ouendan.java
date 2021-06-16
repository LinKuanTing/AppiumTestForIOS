package com.example.wghtest.Level3.Ouendan;

import org.seleniumhq.jetty9.util.IO;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3Ouendan {
    //General Part
    protected IOSElement _btnBack,_btnOption;

    private String __strBackToMenu = "type == 'XCUIElementTypeButton' AND name LIKE 'Menu'";
    private String __strOptionXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther";
    private String __strOptionCreateID = "Create Ouendan";
    private String __strOptionJoinID = "Join Ouendan";
    private String __strListOuendan = "type == 'XCUIElementTypeStaticText' AND name LIKE '*(*)'";

    //Create part
    protected IOSElement _etName,_btnCreate;

    private String __strNameValue = "value LIKE 'Group Name'";
    private String __strCreateID = "Create";
    private String __strCreateMessage = "name LIKE 'Ouendan*Code*password*'";

    //Join part
    private IOSElement _etCode,_etPassword,_btnJoin;

    private String __strTextField = "type == 'XCUIElementTypeTextField'";
    private String __strCodeValue = "value == 'Exist Code' AND type == 'XCUIElementTypeTextField'";
    private String __strPasswordValue = "value == 'Password' AND type == 'XCUIElementTypeTextField'";
    private String __strJoinID = "Join";
    private String __strJoinMessage = "name LIKE 'Successfully joined*'";

    ArrayList<IOSElement> iosElements;
    TouchAction touchAction = new TouchAction(iosDriver);


    public WDFnL3Ouendan(){
        //this._btnOption = (IOSElement) iosDriver.findElementByXPath(__strOptionXpath);
    }

    public void clickBack(){
        this._btnBack = (IOSElement) iosDriver.findElementByIosNsPredicate(__strBackToMenu);

        _btnBack.click();


    }

    public void clickOptionCreate(){
        try{
            iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            this._btnOption = (IOSElement) iosDriver.findElementByXPath(__strOptionXpath);
            iosDriver.findElementByAccessibilityId(__strOptionCreateID);
        }catch (Exception eNoFindElement){
            int pointX = _btnOption.getLocation().getX() + _btnOption.getSize().getWidth()/4;
            int pointY = _btnOption.getLocation().getY() + _btnOption.getSize().getHeight()/2;
            touchAction.tap(PointOption.point(pointX,pointY)).release().perform();
        }

    }

    public void clickOptionJoin(){
        try{
            iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            this._btnOption = (IOSElement) iosDriver.findElementByXPath(__strOptionXpath);
            iosDriver.findElementByAccessibilityId(__strOptionJoinID);
        }catch (Exception eNoFindElement){
            int pointX = _btnOption.getLocation().getX() + _btnOption.getSize().getWidth()*3/4;
            int pointY = _btnOption.getLocation().getY() + _btnOption.getSize().getHeight()/2;
            touchAction.tap(PointOption.point(pointX,pointY)).release().perform();
        }

    }


    public void createOuendan(String strName){
        clickOptionCreate();

        this._etName = (IOSElement) iosDriver.findElementByIosNsPredicate(__strNameValue);
        this._btnCreate = (IOSElement) iosDriver.findElementByAccessibilityId(__strCreateID);

        _etName.sendKeys(strName+"\n");
        _btnCreate.click();
    }

    public void joinOuendan(String strCode,String strPassword){
        clickOptionJoin();

        this._etCode = (IOSElement) iosDriver.findElementByIosNsPredicate(__strCodeValue);
        this._etPassword = (IOSElement) iosDriver.findElementByIosNsPredicate(__strPasswordValue);
        this._btnJoin = (IOSElement) iosDriver.findElementByAccessibilityId(__strJoinID);

        _etCode.setValue(strCode);
        _etPassword.sendKeys(strPassword);

        _btnJoin.click();
    }


    public String getAlertMsg(){
        iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        String strMessage;
        try {
            strMessage = iosDriver.findElementByIosNsPredicate(__strCreateMessage).getText();
        }catch (Exception eNoFindElement){
            strMessage = iosDriver.findElementByIosNsPredicate(__strJoinMessage).getText();
        }
        return strMessage;
    }

    public ArrayList getOuendan() {
        ArrayList alOuendan = new ArrayList();
        try {
            iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strListOuendan);
            for (int i=0; i<iosElements.size(); i++){
                alOuendan.add(iosElements.get(i).getText());
            }
        }catch (Exception eNoFindElement){}


        return alOuendan;
    }

    public void clickOuendan(String strOuendanID){
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strListOuendan);
        for (int i=0; i<iosElements.size(); i++){
            if (iosElements.get(i).getText().contains(strOuendanID)){
                iosElements.get(i).click();
                break;
            }
        }
    }

}
