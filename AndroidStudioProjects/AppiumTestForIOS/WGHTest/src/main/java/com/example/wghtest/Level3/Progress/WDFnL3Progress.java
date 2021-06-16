package com.example.wghtest.Level3.Progress;

import java.util.ArrayList;
import java.util.Set;

import io.appium.java_client.ios.IOSElement;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3Progress {

    protected IOSElement _btnAwards,_btnInfo;

    private String __strAwardsID = "name IN {'awards','1','2','3'}";
    private String __strAwardsType = "type == 'XCUIElementTypeLink'";
    private String __strAwardDescriptionType = "type == 'XCUIElementTypeStaticText'";
    private String __strInfoType = "type == 'XCUIElementTypeLink'";

    private String __btnBackID = "name IN {'My Trophy','Progress'} AND type == 'XCUIElementTypeButton'";


    public void contextNative(){
        Set context = iosDriver.getContextHandles();
        iosDriver.context((String) context.toArray()[0]);
    }

    public void contextWebview(){
        Set context = iosDriver.getContextHandles();
        iosDriver.context((String) context.toArray()[1]);

        for (int i=1; i<context.size(); i++){
            iosDriver.context((String)context.toArray()[i]);
            if (iosDriver.getPageSource().contains("Hello！")){
                break;
            }
            if (i == context.size()){
                System.out.println("Can't find correct context");
            }
        }

        //System.out.println(iosDriver.getPageSource());
    }

    public void clickAwards() throws InterruptedException {
        Thread.sleep(5000);
        try {
            _btnAwards = (IOSElement) iosDriver.findElementByIosNsPredicate(__strAwardsID);
        }catch (Exception eNoFindElement){
            ArrayList<IOSElement> alLink = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strAwardsType);
            _btnAwards = alLink.get(0);
        }

        _btnAwards.click();
    }

    public void clickInfo() throws InterruptedException {
        Thread.sleep(5000);
        _btnInfo = (IOSElement) iosDriver.findElementByIosNsPredicate(__strInfoType);

        _btnInfo.click();
    }

    public ArrayList getDescription() throws InterruptedException {
        Thread.sleep(5000);
        ArrayList<IOSElement> alStaticText = (ArrayList<IOSElement>) iosDriver.findElementsByIosNsPredicate(__strAwardDescriptionType);
        ArrayList alContent = new ArrayList();
        for (int i=0; i<alStaticText.size()-1; i++){
            String strContent = alStaticText.get(i).getAttribute("name");
            if (!(strContent.equals("") || strContent.equals("\n"))){
                alContent.add(strContent);
            }
        }
        return alContent;
    }

    public void clickBack(){
        iosDriver.findElementByIosNsPredicate(__btnBackID).click();
    }

}
