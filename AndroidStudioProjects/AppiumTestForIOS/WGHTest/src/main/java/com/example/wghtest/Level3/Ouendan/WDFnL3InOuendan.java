package com.example.wghtest.Level3.Ouendan;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3InOuendan {
    //General
    protected IOSElement _btnBack;
    protected IOSElement _btnOption,_btnLatestNews,_btnPartner,_btnRank;

    private String __strBackToOuendanID = "Create/Join Ouendan";
    private String __strTitleType = "type == 'XCUIElementTypeNavigationBar'";
    private String __strOptionXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[4]";
    private String __strOptionLatestNewsID = "Latest News";
    private String __strOptionPartnerID = "Partner";
    private String __strRankID = "ranking";


    String strTitleName;

    //Latest News part
    protected IOSElement _btnEncourage;
    protected IOSElement _etMessage;
    protected IOSElement _btnReply,_btnSubmit;

    private String __strEncourageID = "Encourage";
    private String __strTextView = "type == 'XCUIElementTypeTextView'";
    private String __strGoodBtnID = "name LIKE 'icon hand good f'";
    private String __strBadBtnID = "name LIKE 'icon hand bad f'";
    private String __strMessageBtnID1 = "name LIKE 'icon message f'";
    private String __strMessageBtnID2 = "name LIKE 'icon message'";
    private String __strReplyID = "回覆";
    private String __strSubmitID = "Submit";

    //Partner part
    protected IOSElement _btnLeave;
    protected IOSElement _tvOuendanInfoL1,_tvOuendanInfoL2;

    private String __strLeaveID = "Leave";
    private String __strOuendanInfoL1 = "";
    private String __strOuendanInfoL2 = "name LIKE '*Code*Password*'";

    //Other
    TouchAction touchAction = new TouchAction(iosDriver);
    ArrayList<IOSElement> iosElements;


    public WDFnL3InOuendan(){
        //iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        this.strTitleName = iosDriver.findElementByIosNsPredicate(__strTitleType).getAttribute("name");
    }


    public void clickBackToOuendan(){
        this._btnBack = (IOSElement) iosDriver.findElementByAccessibilityId(__strBackToOuendanID);
        _btnBack.click();
    }

    public void clickBackToInOuendan(){
        this._btnBack = (IOSElement) iosDriver.findElementByAccessibilityId(strTitleName);
        _btnBack.click();
    }

    public void clickLatestNews(){
        try {
            this._btnLatestNews = (IOSElement) iosDriver.findElementByAccessibilityId(__strOptionLatestNewsID);
        }catch (Exception eNoPoint){
            this._btnPartner = (IOSElement) iosDriver.findElementByAccessibilityId(__strOptionPartnerID);
            int pointX = _btnPartner.getLocation().getX() + _btnPartner.getSize().getWidth()/2;
            int pointY = _btnPartner.getLocation().getY() + _btnPartner.getSize().getHeight()/2;
            int sizeX = _btnPartner.getSize().getWidth();
            touchAction.tap(PointOption.point(pointX-sizeX,pointY)).release().perform();
        }
    }

    public void clickPartner(){
        try {
            this._btnPartner = (IOSElement) iosDriver.findElementByAccessibilityId(__strOptionPartnerID);
        }catch (Exception eNoPoint){
            this._btnLatestNews = (IOSElement) iosDriver.findElementByAccessibilityId(__strOptionLatestNewsID);
            int pointX = _btnLatestNews.getLocation().getX() + _btnLatestNews.getSize().getWidth()/2;
            int pointY = _btnLatestNews.getLocation().getY() + _btnLatestNews.getSize().getHeight()/2;
            int sizeX = _btnLatestNews.getSize().getWidth();
            touchAction.tap(PointOption.point(pointX+sizeX,pointY)).release().perform();
        }
    }

    public void clickRank(){
        this._btnRank = (IOSElement) iosDriver.findElementByAccessibilityId(__strRankID);
        _btnRank.click();
    }



    public void encourage(String strMessage){
        this._btnEncourage = (IOSElement) iosDriver.findElementByAccessibilityId(__strEncourageID);
        _btnEncourage.click();
        this._etMessage = (IOSElement) iosDriver.findElementByIosNsPredicate(__strTextView);
        this._btnSubmit = (IOSElement) iosDriver.findElementByAccessibilityId(__strSubmitID);
        _etMessage.sendKeys(strMessage+"\n");
        _btnSubmit.click();
    }

    public ArrayList getLatestNewsContent() throws InterruptedException {
        Thread.sleep(3000);
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strTextView);
        ArrayList alContent = new ArrayList();
        for (int i=0 ;i<iosElements.size(); i++){
            alContent.add(iosElements.get(i).getText());
        }
        //索引最後為 Partner的個人內容
        alContent.remove(iosElements.size()-1);
        return alContent;
    }

    public void clickGoodAndBad() throws InterruptedException {
        //click good
        Thread.sleep(2000);
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strGoodBtnID);
        iosElements.get(0).click();
        //click bad
        Thread.sleep(2000);
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strBadBtnID);
        iosElements.get(0).click();
    }

    public void clickReplyContent() throws InterruptedException {
        Thread.sleep(2000);
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strMessageBtnID2);
        iosElements.get(0).click();
    }

    public void reply(String strMessage){
        this._btnReply = (IOSElement) iosDriver.findElementByAccessibilityId(__strReplyID);
        _btnReply.click();
        this._etMessage = (IOSElement) iosDriver.findElementByIosNsPredicate(__strTextView);
        this._btnSubmit = (IOSElement) iosDriver.findElementByAccessibilityId(__strSubmitID);
        _etMessage.sendKeys(strMessage+"\n");
        _btnSubmit.click();
    }

    public ArrayList getReplyContent() throws InterruptedException {
        Thread.sleep(3000);
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strTextView);
        ArrayList alContent = new ArrayList();
        for (int i=0 ;i<iosElements.size(); i++){
            alContent.add(iosElements.get(i).getText());
        }
        //索引第一為打氣內容
        alContent.remove(0);
        return alContent;
    }




    public ArrayList getOuendanInfo(){
        //取得加油團名
        ArrayList alInfo = new ArrayList();

        alInfo.add(strTitleName);

        //取得加油團代碼與密碼
        this._tvOuendanInfoL2 = (IOSElement) iosDriver.findElementByIosNsPredicate(__strOuendanInfoL2);
        String strOuendanL2Info = _tvOuendanInfoL2.getText();

        char[] charArray = strOuendanL2Info.toCharArray();
        String strInfo = "";
        for (int i=0; i<charArray.length; i++){
            if (charArray[i] == '「'){
                for (int j=i+1; ;j++){

                    if (charArray[j] == '」'){
                        alInfo.add(strInfo);
                        strInfo = "";
                        break;
                    }else {
                        strInfo += charArray[j];
                    }

                }
            }
        }

        return alInfo;
    }

    public String getPersonalContent() throws InterruptedException {
        Thread.sleep(3000);
        iosElements = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strTextView);
        String strContent = iosElements.get(iosElements.size()-1).getText();

        return strContent;
    }

    public void leaveOuendan(){
        clickPartner();
        this._btnLeave = (IOSElement) iosDriver.findElementByAccessibilityId(__strLeaveID);
        _btnLeave.click();

        iosDriver.switchTo().alert().accept();
    }

}

