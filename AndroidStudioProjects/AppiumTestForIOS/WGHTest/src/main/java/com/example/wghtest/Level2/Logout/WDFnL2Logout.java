package com.example.wghtest.Level2.Logout;

import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;

import java.time.Duration;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class WDFnL2Logout {
    int height = iosDriver.manage().window().getSize().height;
    int width = iosDriver.manage().window().getSize().width;

    private static String __strLogoutXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther[2]/XCUIElementTypeScrollView/XCUIElementTypeButton[4]";

    TouchAction touchAction = new TouchAction(iosDriver);

    public void logout() throws InterruptedException {
        new WDFnL2TitleMenuTabbar().clickTitlePersonal();

        Thread.sleep(8000);

        touchAction.press(PointOption.point(width/2,height*5/6)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(width/2,height/6)).release().perform();
        Thread.sleep(3000);

        iosDriver.findElementByXPath(__strLogoutXpath).click();

        iosDriver.switchTo().alert().dismiss();
    }


    public void logoutByCancel(){
        new WDFnL2TitleMenuTabbar().clickTitlePersonal();

        touchAction.press(PointOption.point(width/2,height*5/6)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(width/2,height/6)).release().perform();
        iosDriver.findElementByXPath(__strLogoutXpath).click();

        iosDriver.switchTo().alert().accept();
    }
}
