package com.example.wghtest;


import com.example.wghtest.other.FnFileEvent;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import io.appium.java_client.ios.IOSDriver;

public class WGHTestBase {
    public static IOSDriver iosDriver;
    private static DesiredCapabilities __caps;
    private static String __strURL = "http://127.0.0.1:4730/wd/hub";
    public static long timeOut = 20;
    public static int AppWidth,AppHeight;

    private String __strFileName;
    private String __strFilePath;

    FnFileEvent fnFileEvent= new FnFileEvent();

    private static String __winAppXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]";


    public WGHTestBase() throws IOException {
        fnFileEvent.loadFilePath();
        this.iosDriver = getDriver();
        AppWidth = iosDriver.findElementByXPath(__winAppXpath).getSize().getWidth();
        AppHeight = iosDriver.findElementByXPath(__winAppXpath).getSize().getHeight();
    }


    public void setCaps() throws IOException {
        this.__caps = new DesiredCapabilities();
        __strFileName = "setCapabilities";
        __strFilePath = fnFileEvent.getPath(__strFileName);
        ArrayList alCapsSetting = fnFileEvent.getContent(__strFilePath);
        for (int i=0; i<alCapsSetting.size(); i+=2){
            __caps.setCapability(alCapsSetting.get(i).toString(),alCapsSetting.get(i+1).toString());
        }
    }


    public IOSDriver getDriver() throws IOException {
        this.setCaps();
        return new IOSDriver(new URL(__strURL),__caps);
    }
}
