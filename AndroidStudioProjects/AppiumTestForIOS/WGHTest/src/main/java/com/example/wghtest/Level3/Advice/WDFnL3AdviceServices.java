package com.example.wghtest.Level3.Advice;

import io.appium.java_client.ios.IOSElement;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3AdviceServices {
    protected IOSElement _btnMore;
    protected IOSElement _btnBack;

    private String __strCellTitleID = "Golden Smart Home Technology Crop.";
    private String __strMoreInfoID = "More Info";

    private String __strBackToServicesID = "Back";


    public WDFnL3AdviceServices(){ }

    public void clickMore(){
        this._btnMore = (IOSElement) iosDriver.findElementByAccessibilityId(__strMoreInfoID);
        _btnMore.click();
    }

    public void clickBack(){
        this._btnBack = (IOSElement) iosDriver.findElementByAccessibilityId(__strBackToServicesID);
        _btnBack.click();
    }

}
