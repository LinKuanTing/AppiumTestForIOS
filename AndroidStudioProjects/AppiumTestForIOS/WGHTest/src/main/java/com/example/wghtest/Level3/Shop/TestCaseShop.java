package com.example.wghtest.Level3.Shop;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.AppHeight;
import static com.example.wghtest.WGHTestBase.AppWidth;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class TestCaseShop extends WDFnL3Shop{
    private String __strSearchValue = "星空藍牙體重計";

    private String __strNickNameID = "dnn_dnnUser_enhancedRegisterLink";
    private String __strBtnAddCartID = "dnn_ctr483_ProductPage_lstProducts_btnlistaddtocart_1";
    //private String __strCheckBoxDeleteID = "dnn_ctr486_ShoppingCart_grdCartContent_RemoveItem_0";
    private String __strCheckBoxDeleteXpath = "//img[@src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAMAAAAoyzS7AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAAZQTFRFAAAAAAAApWe5zwAAAAF0Uk5TAEDm2GYAAAAMSURBVHjaYmAACDAAAAIAAU9tWeEAAAAASUVORK5CYII=']";
    private String __strPayNoteID = "dnn_ctr486_ShoppingCart_lblUnPaidHelp";
    private String __strNoteNoItemsCartID = "dnn_ctr486_ShoppingCart_lblCartError";

    private String __strFileName = "AccountInfo";
    private String __strFilePath;

    TouchAction touchAction = new TouchAction(iosDriver);
    Logger logger;

    public TestCaseShop() throws InterruptedException {
        logger = LoggerFactory.getLogger(TestCaseShop.class);
        Thread.sleep(15000);

    }

    public void CheckShopNickName() throws IOException {
        contextWebview();

        String strNickName = "";
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF8"));

        while (br.ready()){
            String strReadLine = br.readLine();
            if (strReadLine.contains("Nick Name")){
                strNickName = br.readLine();
            }
        }

        String strRead = iosDriver.findElement(By.id(__strNickNameID)).getText();
        if (strRead.equals(strNickName)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Expect: "+strNickName);
            logger.debug("Read  : "+strRead);
        }
    }

    public void CheckShopPoints() throws InterruptedException, IOException {
        contextWebview();

        gotoWowPoints();

        Thread.sleep(3000);
        String strReadPoints = getWowPoints();
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"Big5"));

        String strExpectData = "";
        while (br.ready()){
            String strReadLine = br.readLine();
            if (strReadLine.contains("Points")){
                strExpectData = br.readLine();
            }
        }

        if (strReadPoints.contains(strExpectData)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Expect data: " +strExpectData);
            logger.debug("Read   data: " +strReadPoints);
        }
    }

    public void AddShoppingCart() throws InterruptedException {
        contextWebview();

        gotoShopMarket();
        touchAction.press(PointOption.point(AppWidth/2,AppHeight*3/4)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(AppWidth/2,AppHeight/4)).release().perform();
        Thread.sleep(5000);
        iosDriver.findElementById(__strBtnAddCartID).click();
        Thread.sleep(10000);

        gotoShoppintCart();

        try {
            iosDriver.findElementById(__strPayNoteID);
            logger.info(strPassMsg);
        }catch (Exception e){
            logger.warn(strFailMsg);
            System.out.println(iosDriver.getPageSource());
            if (iosDriver.getPageSource().equals("")){
                logger.debug("Reason: Webview content load fail.");
            }
        }
    }

    public void RemoveShoppingCart() throws InterruptedException {
        contextWebview();

        gotoShoppintCart();
        Thread.sleep(3000);

        touchAction.press(PointOption.point(AppWidth/2,AppHeight*4/5)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(AppWidth/2,AppHeight/5)).release().perform();

        iosDriver.findElementByXPath(__strCheckBoxDeleteXpath).click();

        Thread.sleep(5000);
        clickUpdataCart();

        Thread.sleep(8000);

        try {
            iosDriver.findElementById(__strNoteNoItemsCartID);
            logger.info(strPassMsg);
        }catch (Exception e){
            logger.warn(strFailMsg);
            System.out.println(iosDriver.getPageSource());
            if (iosDriver.getPageSource().equals("")){
                logger.debug("Reason: Webview content load fail.");
            }
        }

    }

    public void SearchCommodity() throws InterruptedException {

        while (true){
            try {
                contextWebview();

                gotoShopMarket();
                searchCommodity(__strSearchValue);
                Thread.sleep(15000);
                break;
            }catch (Exception ePageLoadFail){
                contextNative();
                clickTabbarMenu();
                new WDFnL2TitleMenuTabbar().clickMenuShop();
            }
        }


        ArrayList alCommodityItems = getAllCommodityName();
        for (int i=0; i<alCommodityItems.size(); i++){
            if (alCommodityItems.get(i).equals(__strSearchValue)){
                logger.info(strPassMsg);
            }else {
                if (i == alCommodityItems.size()-1){
                    logger.warn(strFailMsg);
                    logger.debug("Expect: " + __strSearchValue);
                    logger.debug("Read  : " + alCommodityItems);
                }
            }
        }

        contextNative();

    }

}