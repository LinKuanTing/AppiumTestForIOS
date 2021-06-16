package com.example.wghtest;

import com.example.wghtest.Level1.FnMailEvent;
import com.example.wghtest.Level1.ForgetPwd.TestCaseFailForgetPwdByWrongFormat;
import com.example.wghtest.Level1.ForgetPwd.TestCaseFailForgetPwdByWrongMail;
import com.example.wghtest.Level1.ForgetPwd.TestCaseNormalForgetPwd;
import com.example.wghtest.Level1.Login.TestCaseFailLoginByWrongAct;
import com.example.wghtest.Level1.Login.TestCaseFailLoginByWrongPwd;
import com.example.wghtest.Level1.Login.TestCaseNormalLogin;
import com.example.wghtest.Level1.Login.TestCaseRegisterLogin;
import com.example.wghtest.Level1.Login.WDFnL1Login;
import com.example.wghtest.Level1.Register.TestCaseCompareRegisterTerms;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByCharNickName;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByCharacterAct;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByChineseAct;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByExistAct;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByWrongMail;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByWrongPwd;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByWrongPwdCfm;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByWrongSN;
import com.example.wghtest.Level2.Logout.TestCaseFailLogoutByClickCancel;
import com.example.wghtest.Level2.Logout.TestCaseNormalLogout;
import com.example.wghtest.Level2.Logout.WDFnL2Logout;
import com.example.wghtest.Level2.Personal.TestCaseHealthPlanSetInfo;
import com.example.wghtest.Level2.Personal.TestCaseMeasure;
import com.example.wghtest.Level2.Personal.TestCasePersonalSetInfo;
import com.example.wghtest.Level2.QRcode.TestCaseQRcode;
import com.example.wghtest.Level2.Tabbar.TestCaseTabbarPoints;
import com.example.wghtest.Level2.WDFnBaseInfoSetting;
import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;
import com.example.wghtest.Level3.Advice.TestCaseAdvice;
import com.example.wghtest.Level3.Advice.WDFnL3Advice;
import com.example.wghtest.Level3.BloodGlucose.TestCaseBloodGlucose;
import com.example.wghtest.Level3.BloodGlucose.WDFnL3BloodGlucose;
import com.example.wghtest.Level3.BloodGlucose.WDFnL3BloodGlucoseRecord;
import com.example.wghtest.Level3.BloodPressure.TestCaseBloodPressure;
import com.example.wghtest.Level3.BloodPressure.WDFnL3BloodPressure;
import com.example.wghtest.Level3.BloodPressure.WDFnL3BloodPressureRecord;
import com.example.wghtest.Level3.Diet.TestCaseDiet;
import com.example.wghtest.Level3.Diet.WDFnL3Diet;
import com.example.wghtest.Level3.Diet.WDFnL3DietRecord;
import com.example.wghtest.Level3.Ouendan.TestCaseOuendan;
import com.example.wghtest.Level3.Ouendan.WDFnL3Ouendan;
import com.example.wghtest.Level3.Progress.TestCaseProgress;
import com.example.wghtest.Level3.Progress.WDFnL3Progress;
import com.example.wghtest.Level3.Shop.TestCaseShop;
import com.example.wghtest.Level3.Sport.TestCaseSport;
import com.example.wghtest.Level3.Sport.WDFnL3Sport;
import com.example.wghtest.Level3.Sport.WDFnL3SportRecord;
import com.example.wghtest.Level3.StressMood.TestCaseStressMood;
import com.example.wghtest.Level3.StressMood.WDFnL3StressMood;
import com.example.wghtest.Level3.Thermometer.TestCaseThermometer;
import com.example.wghtest.Level3.Thermometer.WDFnL3Thermometer;
import com.example.wghtest.Level3.Thermometer.WDFnL3ThermometerRecord;
import com.example.wghtest.Level3.Weight.TestCaseWeight;
import com.example.wghtest.Level3.Weight.WDFnL3Weight;
import com.example.wghtest.Level3.Weight.WDFnL3WeightRecord;
import com.example.wghtest.other.FnFileEncrypt;
import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.other.FnLogEvent;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileBy;



public class WGHTestManager extends WGHTestBase{


    public WGHTestManager() throws IOException {
        super();
    }

    public static void TestLevel1() throws Exception {
        WebDriverWait wait = new WebDriverWait(iosDriver,timeOut);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(new MobileBy.ByAccessibilityId("WGHlogo.png")));

        System.out.println("Test for Register");
        //Test Case Of Register
        TestCaseCompareRegisterTerms tcCompareRegisterTerms = new TestCaseCompareRegisterTerms();
        tcCompareRegisterTerms.excute();

        TestCaseFailRegisterByWrongSN tcFailRegisterByWrongSN = new TestCaseFailRegisterByWrongSN();
        tcFailRegisterByWrongSN.execute();

        TestCaseFailRegisterByCharacterAct tcFailRegisterByCharacterAct = new TestCaseFailRegisterByCharacterAct();
        tcFailRegisterByCharacterAct.excute();

        TestCaseFailRegisterByChineseAct tcFailRegisterByChineseAct = new TestCaseFailRegisterByChineseAct();
        tcFailRegisterByChineseAct.excute();

        TestCaseFailRegisterByExistAct tcFailRegisterByExistAct = new TestCaseFailRegisterByExistAct();
        tcFailRegisterByExistAct.excute();

        TestCaseFailRegisterByCharNickName tcFailRegisterByCharNickName = new TestCaseFailRegisterByCharNickName();
        tcFailRegisterByCharNickName.execute();

        TestCaseFailRegisterByWrongMail tcFailRegisterByWrongMail = new TestCaseFailRegisterByWrongMail();
        tcFailRegisterByWrongMail.execute();

        TestCaseFailRegisterByWrongPwd tcFailRegisterByWrongPwd = new TestCaseFailRegisterByWrongPwd();
        tcFailRegisterByWrongPwd.execute();

        TestCaseFailRegisterByWrongPwdCfm tcFailRegisterByWrongPwdCfm = new TestCaseFailRegisterByWrongPwdCfm();
        tcFailRegisterByWrongPwdCfm.execute();

        //TestCaseNormalRegister tcNormalRegister = new TestCaseNormalRegister();
        //tcNormalRegister.excute();

        System.out.println("Test for Login");
        //Test Case Of Login
        TestCaseFailLoginByWrongAct tcFailLoginByWrongAct = new TestCaseFailLoginByWrongAct();
        tcFailLoginByWrongAct.excute();

        TestCaseFailLoginByWrongPwd tcFailLoginByWrongPwd = new TestCaseFailLoginByWrongPwd();
        tcFailLoginByWrongPwd.excute();

        TestCaseRegisterLogin tcRegisterLogin = new TestCaseRegisterLogin();
        tcRegisterLogin.excute();

        new WDFnL2Logout().logout();

        System.out.println("Test for ForgetPassword");
        //Test Case Of Forget Password
        new WDFnL1Login().clickForgetPwd();
        TestCaseFailForgetPwdByWrongFormat tcFailForgetPwdByWrongFormat = new TestCaseFailForgetPwdByWrongFormat();
        tcFailForgetPwdByWrongFormat.excute();

        TestCaseFailForgetPwdByWrongMail tcFailForgetPwdByWrongMail = new TestCaseFailForgetPwdByWrongMail();
        tcFailForgetPwdByWrongMail.excute();

        TestCaseNormalForgetPwd tcNormalForgetPwd = new TestCaseNormalForgetPwd();
        tcNormalForgetPwd.excute();

    }

    public static void TestLevel2() throws Exception {
        //Login
        try {
            new TestCaseNormalLogin().excute();
            iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
        }catch (Exception eNoFindElement) { }


        System.out.println("Test for Personal");
        //Click TitlePersonal
        new WDFnL2TitleMenuTabbar().clickTitlePersonal();

        TestCasePersonalSetInfo tcPersonalSetInfo = new TestCasePersonalSetInfo();
        tcPersonalSetInfo.excute();

        TestCaseHealthPlanSetInfo tcHealthPlanSetInfo = new TestCaseHealthPlanSetInfo();
        tcHealthPlanSetInfo.excute();

        TestCaseMeasure tcMeasure = new TestCaseMeasure();
        tcMeasure.SetInfo();
        tcMeasure.SwitchBGUnits();

        //Back to Menu
        new WDFnBaseInfoSetting().backToMenu();

        System.out.println("Test for QRcode");
        TestCaseQRcode tcCompareQRcode = new TestCaseQRcode();
        tcCompareQRcode.Compare();

        System.out.println("Test for Logout");
        TestCaseFailLogoutByClickCancel tcFailLogoutByClickCancel = new TestCaseFailLogoutByClickCancel();
        tcFailLogoutByClickCancel.excute();

        TestCaseNormalLogout tcNormalLogout = new TestCaseNormalLogout();
        tcNormalLogout.excute();


    }

    public static void TestL3Weight() throws Exception {
        System.out.println("Test for Weight");

        new WDFnL2TitleMenuTabbar().clickMenuWeight();
        TestCaseWeight tcWeight = new TestCaseWeight();
        tcWeight.LoadRecord();
        tcWeight.AddRecord();
        new WDFnL3Weight().clickRecord();
        tcWeight.UpdateRecord();
        tcWeight.DeleteRecord();
        tcWeight.CompareRecord();
        new WDFnL3WeightRecord().clickBack();
        tcWeight.CheckWarningMsg();
        tcWeight.CheckDataMean();
        tcWeight.CheckTips();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3BloodPressure() throws Exception {
        System.out.println("Test for BloodPressure");

        new WDFnL2TitleMenuTabbar().clickMenuBloodPressure();
        TestCaseBloodPressure tcBloodPressure = new TestCaseBloodPressure();
        tcBloodPressure.LoadRecord();
        tcBloodPressure.AddRecord();
        new WDFnL3BloodPressure().clickRecord();
        tcBloodPressure.UpdateRecord();
        tcBloodPressure.CompareRecord();
        new WDFnL3BloodPressureRecord().clickBack();
        tcBloodPressure.CheckDataMean();
        tcBloodPressure.CheckAchieved();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3BloodGlucose() throws Exception {
        System.out.println("Test for BloodGlucose");

        new WDFnL2TitleMenuTabbar().clickMenuBloodGlucose();
        TestCaseBloodGlucose tcBloodGlucose = new TestCaseBloodGlucose();
        tcBloodGlucose.LoadRecord();
        tcBloodGlucose.AddRecord();
        new WDFnL3BloodGlucose().clickRecord();
        tcBloodGlucose.UpdateRecord();
        tcBloodGlucose.CompareRecord();
        new WDFnL3BloodGlucoseRecord().clickBack();
        tcBloodGlucose.CheckDataMean();
        tcBloodGlucose.CheckAchieved();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3Thermometer() throws Exception {
        System.out.println("Test for Thermometer");

        new WDFnL2TitleMenuTabbar().clickMenuThermometer();
        TestCaseThermometer tcThermometer = new TestCaseThermometer();
        tcThermometer.LoadRecord();
        tcThermometer.AddRecord();
        new WDFnL3Thermometer().clickRecord();
        tcThermometer.UpdateRecord();
        tcThermometer.CompareRecord();
        new WDFnL3ThermometerRecord().clickBack();
        tcThermometer.CheckDataMean();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3Sport() throws Exception {
        System.out.println("Test for Sport");

        new WDFnL2TitleMenuTabbar().clickMenuSport();
        TestCaseSport tcSport = new TestCaseSport();
        tcSport.LoadRecord();
        tcSport.AddRecord();
        new WDFnL3Sport().clickRecord();
        tcSport.UpdateRecord();
        tcSport.CompareRecord();
        new WDFnL3SportRecord().clickBack();
        tcSport.CheckNotUploadMsg();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3Diet() throws Exception {
        System.out.println("Test for Diet");

        new WDFnL2TitleMenuTabbar().clickMenuDiet();
        TestCaseDiet tcDiet = new TestCaseDiet();
        tcDiet.LoadRecord();
        tcDiet.CheckEatingTimeMsg();
        tcDiet.AddRecord();
        tcDiet.CheckExistMainMeal();
        new WDFnL3Diet().clickRecord();
        tcDiet.UpdateRecord();
        tcDiet.CompareRecord();
        new WDFnL3DietRecord().clickBack();
        tcDiet.CheckNotUploadMag();
        tcDiet.CheckDataMean();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3StressMood() throws Exception {
        System.out.println("Test for StressMood");

        new WDFnL2TitleMenuTabbar().clickMenuStressMood();
        TestCaseStressMood tcStressMood = new TestCaseStressMood();
        tcStressMood.LoadWellnessRecord();
        tcStressMood.CompareWellnessRecord();
        new WDFnL3StressMood().clickBreathTraining();
        tcStressMood.LoadBreathingRecord();
        tcStressMood.CompareBreathingRecord();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();

    }

    public static void TestL3Advice() throws Exception {
        System.out.println("Test for Advice");

        //換帳號登入測試此Case
        //因為原帳號在ＡＩ推播資料過多，導致加載頁面失敗 無法定位元素
        new WDFnL2Logout().logout();
        new TestCaseRegisterLogin().registerLogin();
        new WDFnL3Advice().AddAIBrowse();

        new WDFnL2TitleMenuTabbar().clickMenuAdvice();
        TestCaseAdvice tcAdvice = new TestCaseAdvice();
        tcAdvice.CheckBrowseAI();
        tcAdvice.CheckStarComment();
        new WDFnL3Advice().clickServices();
        tcAdvice.CheckServices();
        new WDFnL3Advice().clickBack();

        new WDFnL2Logout().logout();
        new TestCaseNormalLogin().normalLogin();

    }

    public static void TestL3Ouendan() throws Exception {
        System.out.println("Test for Ouendan");

        new WDFnL2TitleMenuTabbar().clickMenuOuendan();
        TestCaseOuendan tcOuendan = new TestCaseOuendan();
        tcOuendan.CreateOuendan();
        tcOuendan.QuitOuendan();
        tcOuendan.JoinOuendan();
        tcOuendan.LatestNewsEncourage();
        tcOuendan.LatestNewsClickGoodAndBad();
        tcOuendan.LatestNewsReply();
        tcOuendan.MyPartnerCheckOuendanInfo();
        tcOuendan.MyPartnerCheckPersonalInfo();
        new WDFnL3Ouendan().clickBack();
    }

    public static void TestL3Progress() throws Exception {
        System.out.println("Test for Progress");

        new WDFnL2TitleMenuTabbar().clickMenuProgress();
        TestCaseProgress tcProgress = new TestCaseProgress();
        tcProgress.CheckContentData();
        tcProgress.CheckAwardsDescription();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3WowPoints() throws Exception {
        System.out.println("Test for WowPoint");

        new WDFnL2TitleMenuTabbar().clickTabbarPoints();
        TestCaseTabbarPoints tcTabbarPoints = new TestCaseTabbarPoints();
        tcTabbarPoints.CheckTermOfUse();
        tcTabbarPoints.CheckWowPoints();
        tcTabbarPoints.CheckEarnedPoints();
        tcTabbarPoints.CheckUsedPoint();
        tcTabbarPoints.CheckDeadlinePoints();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3Shop() throws Exception {
        System.out.println("Test for Shop");

        new WDFnL2TitleMenuTabbar().clickMenuShop();
        TestCaseShop tcShop = new TestCaseShop();
        tcShop.CheckShopNickName();
        tcShop.CheckShopPoints();
        tcShop.AddShoppingCart();
        tcShop.RemoveShoppingCart();
        tcShop.SearchCommodity();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }


    public static void TestLevel3() throws Exception {
        //Login
        try {
            new TestCaseNormalLogin().excute();
            iosDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
        }catch (Exception eNoFindElement) { }


        TestL3Weight();
        TestL3BloodPressure();
        TestL3BloodGlucose();
        TestL3Thermometer();
        TestL3Sport();
        TestL3Diet();
        TestL3StressMood();
        TestL3Advice();
        TestL3Ouendan();
        TestL3Shop();
        TestL3Progress();
        TestL3WowPoints();
    }


    public static void End() throws Exception {
        FnLogEvent fnLogEvent = new FnLogEvent();
        fnLogEvent.logFormat();
        new WDFnL2Logout().logout();
        iosDriver.quit();
    }

    public static void main(String[] argv) throws Exception {
        new WGHTestManager();


        TestLevel1();

        TestLevel2();

        TestLevel3();



        End();





    }
}
