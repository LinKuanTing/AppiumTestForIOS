package com.example.wghtest.Level2.Personal;

import com.example.wghtest.other.FnFileEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static com.example.wghtest.WGHTestBase.iosDriver;
import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;

public class TestCasePersonalSetInfo extends WDFnL2Personal{

    private String __strFileName = "AccountInfo";
    private String __strFilePath ;

    private String __strMsgText = "name IN {'Successfully saved','個人資料儲存成功'}";


    public void excute() throws IOException, InterruptedException {
        Logger logger = LoggerFactory.getLogger(TestCasePersonalSetInfo.class);


        clickPersonal();

        setPersonalInfo();

        try {
            iosDriver.findElementByIosNsPredicate(__strMsgText);
            iosDriver.switchTo().alert().accept();

            logger.info(strPassMsg);

            __strFilePath = new FnFileEvent().getPath(__strFileName);
            FileWriter fw = new FileWriter(__strFilePath);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("###Account\r\n");
            bw.write(getAccount()+"\r\n");
            bw.write("###Nick Name\r\n");
            bw.write(getNickname()+"\r\n");
            bw.write("###Gender\r\n");
            bw.write(getGender()+"\r\n");
            bw.flush();
            bw.close();

        }catch (Exception eNoFindElements){
            iosDriver.switchTo().alert().accept();

            logger.warn(strFailMsg);
            logger.debug("Reason: Alert message is different from expect data.");
        }


    }
}
