package com.example.wghtest.Level2.Logout;

import com.example.wghtest.Level2.WDFnBaseInfoSetting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class TestCaseFailLogoutByClickCancel extends WDFnL2Logout {

    private static String __strTitleID = "Profile";

    public void excute(){
        Logger logger = LoggerFactory.getLogger(TestCaseFailLogoutByClickCancel.class);
        logoutByCancel();

        try{
            iosDriver.findElementByAccessibilityId(__strTitleID);
            logger.info(strPassMsg);
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
        }

        new WDFnBaseInfoSetting().backToMenu();
    }
}
