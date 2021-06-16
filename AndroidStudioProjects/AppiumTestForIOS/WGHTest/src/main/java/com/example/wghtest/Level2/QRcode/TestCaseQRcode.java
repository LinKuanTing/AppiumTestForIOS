package com.example.wghtest.Level2.QRcode;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;

public class TestCaseQRcode extends WDFnL2QRcode {

    private String __strFileName = "AccountInfo";
    private String __strFilePath;

    public void Compare() throws IOException {
        Logger logger = LoggerFactory.getLogger(TestCaseQRcode.class);

        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);

        new WDFnL2TitleMenuTabbar().clickTitleQR();

        String expectAccount = (String) fnFileEvent.getContent(__strFilePath).get(0);
        String readAccount = getAccount();


        if (expectAccount.equals(readAccount)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Expect: " + expectAccount);
            logger.debug(" Read : " + readAccount);
        }

        clickOk();

    }
}
