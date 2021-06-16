package com.example.wghtest.Level3.Shop;

import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;

import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Set;

import io.appium.java_client.ios.IOSElement;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL3Shop {


    private String __strToggleNavigationCN = "navbar-toggle";
    private String __strShoppingHomeLT = "首頁";
    private String __strShoppingMarketLT = "商店街";
    private String __strMyAccountLT = "我的帳戶";

    private String __strSearchInputID = "dnn_ctr481_Search_cmbSearch_Input";
    private String __strSearchButtonID = "dnn_ctr481_Search_imgbtnSearch";
    private String __strListCommodityNameCN = "CATListProductName";

    private String __strWowPointsXpath = "//*[@title='WoW Points - ']";
    private String __strMyOrderXpath = "//*[@title='我的訂單 - ']";
    private String __strShoppingCartXpath = "//*[@title='購物車 - ']";

    private String __strUpdataCartID = "dnn_ctr486_ShoppingCart_UpdateBtn";
    private String __strNowPointsID = "dnn_ctr491_LoyaltyPoints_dgBalancelist_lblPendingAmount2_0";


    public WDFnL3Shop(){ }

    //~~~~~以下必須切換至 NATIVE 才能夠被呼叫~~~~~
    public void contextNative(){
        Set context = iosDriver.getContextHandles();
        iosDriver.context((String) context.toArray()[0]);
    }

    public void clickTabbarMenu(){
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }


    //https://shop.wowgohealth.com.tw/gshshop/
    //~~~~~以下必須切換到 WEBVIEW 才能夠被呼叫~~~~~
    public void contextWebview() {
        Set context = iosDriver.getContextHandles();
        iosDriver.context((String) context.toArray()[context.size()-1]);
    }

    public void gotoShopMarket() throws InterruptedException {
        try {
            iosDriver.findElementByClassName( __strToggleNavigationCN).click();
            Thread.sleep(5000);
            iosDriver.findElement(By.partialLinkText(__strShoppingMarketLT)).click();
            Thread.sleep(5000);
        }catch (Exception eNoFindElement) {
            //Webview 畫面空白
        }

    }

    public void searchCommodity(String name) throws InterruptedException {
        iosDriver.findElementById(__strSearchInputID).click();
        iosDriver.findElementById(__strSearchInputID).sendKeys(name);
        iosDriver.findElementById(__strSearchButtonID).click();
        Thread.sleep(10000);
    }

    public ArrayList getAllCommodityName(){
        ArrayList<IOSElement> alCommodityName = (ArrayList<IOSElement>) iosDriver.findElementsByClassName(__strListCommodityNameCN);
        ArrayList alName = new ArrayList();
        for (int i=0; i<alCommodityName.size(); i++){
            alName.add(alCommodityName.get(i).getText());
        }
        return alName;
    }


    public void gotoWowPoints() throws InterruptedException {
        try{
            iosDriver.findElementByClassName( __strToggleNavigationCN).click();
            Thread.sleep(5000);
            iosDriver.findElement(By.partialLinkText(__strMyAccountLT)).click();
            Thread.sleep(5000);
            iosDriver.findElement(By.xpath(__strWowPointsXpath)).click();
            Thread.sleep(5000);
        }catch (Exception eNoFindElement) {
            //Webview 畫面空白
        }
    }

    public String getWowPoints(){
        return iosDriver.findElement(By.id(__strNowPointsID)).getText();
    }

    public void gotoMyOrder() throws InterruptedException {
        iosDriver.findElementByClassName( __strToggleNavigationCN).click();
        Thread.sleep(5000);
        iosDriver.findElement(By.partialLinkText(__strMyAccountLT)).click();
        Thread.sleep(5000);
        iosDriver.findElement(By.xpath(__strMyOrderXpath)).click();
        Thread.sleep(5000);
    }

    public void gotoShoppintCart() throws InterruptedException {
        try {
            iosDriver.findElementByClassName( __strToggleNavigationCN).click();
            Thread.sleep(8000);
            iosDriver.findElement(By.partialLinkText(__strMyAccountLT)).click();
            Thread.sleep(8000);
            iosDriver.findElement(By.xpath(__strShoppingCartXpath)).click();
            Thread.sleep(8000);
        }catch (Exception e){
            contextNative();
            clickTabbarMenu();
            new WDFnL2TitleMenuTabbar().clickMenuShop();
            contextWebview();
            iosDriver.findElementById("dnn_CartLink_hypLink").click();
        }

    }

    public void clickUpdataCart(){
        iosDriver.findElementById(__strUpdataCartID).click();
    }



}
