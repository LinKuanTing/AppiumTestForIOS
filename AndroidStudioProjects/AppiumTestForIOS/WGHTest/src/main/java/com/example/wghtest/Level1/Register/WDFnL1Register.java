package com.example.wghtest.Level1.Register;


import com.example.wghtest.Level1.Login.WDFnL1Login;

import java.time.Duration;
import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL1Register {
    protected IOSElement _etSN,_etRegisterName, _etNickName,_etBirthYear,_etMail,_etPhone,_etRegisterPwd,_etRegisterPwdCfm;
    protected IOSElement _btnGender,_btnPersonalPic,_btnUesTerms,_btnTitleRegister,_btnTitleCancel;

    public static String strPrefixWord = "GSH";

    //inspect locate by predicate or Xpath
    //inspect locate fast to slow : ios_predicate >> accessibility_id >> class_name >> xpath
    private static String __strRegisterID = "Go";
    private static String __strCancelID = "Cancel";

    private static String __strSNID = "Need Valid Key";
    private static String __strRegisterNameXpath = "";
    private static String __strNickNameValue = "Max 20";
    private static String __strBirthYearXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeButton[2]";
    private static String __strMailValue = "xxx@xxxx.xxx";
    private static String __strPhoneValue = "09XXXXXXXX";
    private static String __strGenderXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther";
    private static String __strPersinalPicValue = "Set your photo";
    private static String __strRegisterPwdXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeSecureTextField[1]";
    private static String __strRegisterPwdCfmXpatn = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeSecureTextField[2]";

    private static String __strUseTermsID = "Privacy policy and terms of use";

    protected static String __strAlertMsgXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeOther[4]/XCUIElementTypeOther[2]/XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeStaticText";


    TouchAction touchAction = new TouchAction(iosDriver);

    protected static int _winWidth = iosDriver.manage().window().getSize().width;
    protected static int _winHeight = iosDriver.manage().window().getSize().height;

    public WDFnL1Register(){
        clickRegister();
        _btnTitleCancel = (IOSElement) iosDriver.findElementByAccessibilityId(__strCancelID);
        _btnTitleRegister = (IOSElement) iosDriver.findElementByAccessibilityId(__strRegisterID);

        ArrayList<IOSElement> iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate("type == 'XCUIElementTypeTextField'");
        _etSN = iosElements.get(0);
        _etRegisterName = iosElements.get(1);
        _etNickName = iosElements.get(2);
        _etBirthYear = (IOSElement) iosDriver.findElementByXPath(__strBirthYearXpath);
        _etMail = iosElements.get(4);
        _etPhone = iosElements.get(5);
        _btnGender = (IOSElement) iosDriver.findElementByXPath(__strGenderXpath);
        _btnPersonalPic = (IOSElement) iosDriver.findElementByAccessibilityId(__strPersinalPicValue);

    }

    public void register(String strSN,String strRegisterName,String strNickName,String strMail,String strPhone,String strRegisterPwd,String strRegisterPwdCfm){
        _etSN.sendKeys(strSN+"\n");
        if (_etSN.getText().equals("62167687")){
            strPrefixWord = _etRegisterName.getText();
            if (!strPrefixWord.equals("GSH")){
                System.out.println(strPrefixWord+","+strPrefixWord.equals("GSH"));
                System.out.println("PrefixWord is wrong");
            }
        }else {
            iosDriver.switchTo().alert().accept();
        }

        _etRegisterName.setValue(strRegisterName+"\n");
        _etNickName.sendKeys(strNickName+"\n");

        _etBirthYear.click();
        iosDriver.findElementByAccessibilityId("Done").click();

        _etMail.sendKeys(strMail+"\n");
        _etPhone.sendKeys(strPhone+"\n");
        _btnGender.click();

        _btnPersonalPic.click();
        iosDriver.findElementByAccessibilityId("Cancel").click();

        touchAction.press(PointOption.point(_winWidth/2,_winHeight*3/4)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(_winWidth/2,_winHeight/4)).release().perform();

        _etRegisterPwd = (IOSElement) iosDriver.findElementByXPath(__strRegisterPwdXpath);
        _etRegisterPwdCfm = (IOSElement) iosDriver.findElementByXPath(__strRegisterPwdCfmXpatn);
        _etRegisterPwd.setValue(strRegisterPwd+"\n");
        _etRegisterPwdCfm.setValue(strRegisterPwdCfm+"\n");

        clickTitleRegister();


    }

    public void clickUserTerm(){
        touchAction.press(PointOption.point(_winWidth/2,_winHeight*3/4)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(_winWidth/2,_winHeight/4)).release().perform();
        iosDriver.findElementByAccessibilityId(__strUseTermsID).click();
    }

    public void clickTitleCancel(){
        _btnTitleCancel.click();
    }

    public void clickTitleRegister(){
        _btnTitleRegister.click();
    }

    public void clickRegister(){
        new WDFnL1Login().clickRegister();
    }

}