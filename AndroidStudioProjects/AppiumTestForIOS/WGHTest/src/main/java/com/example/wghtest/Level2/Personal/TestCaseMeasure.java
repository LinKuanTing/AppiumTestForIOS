package com.example.wghtest.Level2.Personal;

import com.example.wghtest.Level2.WDFnBaseInfoSetting;
import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;
import com.example.wghtest.other.FnFileEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static com.example.wghtest.WGHTestBase.iosDriver;
import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;

public class TestCaseMeasure extends WDFnL2Measure {

    private String __strFileName = "AccountInfo";
    private String __strFilePath;

    private String __strMsgText = "name IN {'Successfully saved！','儲存成功！'}";

    private String __strMgUnits = "name == 'Mg/dl'";
    private String __strMmolUnits = "name == 'mmol/L'";

    Logger logger;


    public TestCaseMeasure(){
        logger = LoggerFactory.getLogger(TestCaseMeasure.class);
    }

    public void SetInfo() throws IOException, InterruptedException {

        __strFilePath = new FnFileEvent().getPath(__strFileName);

        clickMeasure();

        setMeasurements();

        try {
            iosDriver.findElementByIosNsPredicate(__strMsgText);
            iosDriver.switchTo().alert().accept();

            logger.info(strPassMsg);

            FileWriter fw = new FileWriter(__strFilePath,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("###BP measurements\r\n");
            bw.write(getBPMeasurements()+"\r\n");
            bw.write("###BG measurements\r\n");
            bw.write(getBGMeasurements()+"\r\n");
            bw.flush();
            bw.close();

        }catch (Exception eNoFindElement){
            iosDriver.switchTo().alert().accept();

            logger.warn(strFailMsg);
            logger.debug("Reason: Alert message is different from expect data.");
        }

    }

    public void SwitchBGUnits() throws InterruptedException {
        clickMeasure();

        clickUnitsMmolL();
        clickSave();
        iosDriver.switchTo().alert().accept();
        new WDFnBaseInfoSetting().backToMenu();

        new WDFnL2TitleMenuTabbar().clickMenuBloodGlucose();

        String strUnits;
        try {
            iosDriver.findElementByIosNsPredicate(__strMgUnits);
            strUnits = "mg/dl";
        }catch (Exception eNoFindElement){
            iosDriver.findElementByIosNsPredicate(__strMmolUnits);
            strUnits = "mmol/L";
        }

        if (strUnits.equals("mmol/L")){
            logger.info(strPassMsg);

            new WDFnL2TitleMenuTabbar().clickTabbarMenu();
            new WDFnL2TitleMenuTabbar().clickTitlePersonal();
            new WDFnL2Personal().clickMeasure();
            clickUnitsMgdl();
            clickSave();
            iosDriver.switchTo().alert().accept();


        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Blood Glucose unit is different from personal setting unit");

            new WDFnL2TitleMenuTabbar().clickTabbarMenu();

        }



    }

}
