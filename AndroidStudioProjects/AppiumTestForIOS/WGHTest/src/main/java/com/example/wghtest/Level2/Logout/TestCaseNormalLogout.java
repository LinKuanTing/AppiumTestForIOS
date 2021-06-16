package com.example.wghtest.Level2.Logout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.iosDriver;

public class TestCaseNormalLogout extends WDFnL2Logout {

    private String __strWGHLogoID = "WGHlogo.png";

    public void excute() throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(TestCaseNormalLogout.class);
        logout();

        try {

            try{
                //有時跳出 Please check network ability 訊息
                iosDriver.switchTo().alert().accept();
            }catch (Exception e){}

            iosDriver.findElementByAccessibilityId(__strWGHLogoID);
            logger.info(strPassMsg);
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
        }
    }

}
