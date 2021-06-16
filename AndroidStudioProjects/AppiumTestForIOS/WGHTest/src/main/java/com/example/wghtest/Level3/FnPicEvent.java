package com.example.wghtest.Level3;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.io.FileHandler;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import io.appium.java_client.ios.IOSElement;

import static com.example.wghtest.WGHTestBase.iosDriver;

public class FnPicEvent {
    private IOSElement __winApp;

    int appLocX,appLocY,appSizeX,appSizeY;

    private static String __winAppXpath = "//XCUIElementTypeApplication[@name=\"WGH\"]/XCUIElementTypeWindow[1]";
    //File Path -> /Users/ting.lin/TestData/TestPic

    public FnPicEvent(){
        this.__winApp = (IOSElement) iosDriver.findElementByXPath(__winAppXpath);
        appLocX = __winApp.getLocation().getX();
        appLocY = __winApp.getLocation().getY();
        appSizeX = __winApp.getSize().getWidth();
        appSizeY = __winApp.getSize().getHeight();
    }

    public void screenShot() throws InterruptedException, IOException {
        File tstPic = new File("/Users/ting.lin/TestData/TestPic/test.png");
        if (!tstPic.exists()){
            tstPic.createNewFile();
            Thread.sleep(3000);
        }

        File screenshot = iosDriver.getScreenshotAs(OutputType.FILE);
        try {
            FileHandler .copy(screenshot,tstPic);
        }catch (Exception eNoFindFile){

            System.out.println("Can't find picture file path");
        }
    }

    public void logShot() throws InterruptedException, IOException {
        File tstPic = new File("/Users/ting.lin/TestData/TestPic/log.png");
        if (!tstPic.exists()){
            tstPic.createNewFile();
            Thread.sleep(3000);
        }

        File screenshot = iosDriver.getScreenshotAs(OutputType.FILE);
        try {
            FileHandler .copy(screenshot,tstPic);
        }catch (Exception eNoFindFile){
            System.out.println("Can't find picture file path");
        }
    }

    public void cutImage(){
        File tstPic = new File("/Users/ting.lin/TestData/TestPic/test.png");
        try {
            BufferedImage imgTest = ImageIO.read(tstPic);
            imgTest = imgTest.getSubimage(appLocX*2,appLocY*2,appSizeX*2,appSizeY*2);
            ImageIO.write(imgTest,"png",tstPic);
        } catch (Exception eNoFindFile) {

            System.out.println("Can't find picture file path");
        }
    }

    public void getPicPixel() throws IOException {
        File tstPic = new File("/Users/ting.lin/TestData/TestPic/test.png");
        BufferedImage imgTest = ImageIO.read(tstPic);
        System.out.println(imgTest.getWidth() + "," + imgTest.getHeight());
    }

    public boolean compareImages(BufferedImage imgSample) throws IOException {
        int sameNum = 0;
        double totalPixel = 0, similarity = 0;
        BufferedImage imgTest = ImageIO.read((new File("/Users/ting.lin/TestData/TestPic/test.png")));

        //get image's width and height
        int[][] pxSample = new int[imgSample.getWidth()][imgSample.getHeight()];
        int[][] pxTest = new int[imgTest.getWidth()][imgTest.getHeight()];
        int[][] pxResult = new int[imgSample.getWidth()][imgSample.getHeight()];

        //image's size different return false
        if (imgSample.getWidth() != imgTest.getWidth() || imgSample.getHeight() != imgTest.getHeight()){
            System.out.println("images' is different, so image can't compare");
        }

        //put each pixel's RGB into array
        for (int x=0; x<imgSample.getWidth(); x++){
            for (int y=0; y<imgSample.getHeight();y++){
                pxSample[x][y] = imgSample.getRGB(x,y);
            }
        }
        for (int x=0; x<imgTest.getWidth(); x++){
            for (int y=0; y<imgTest.getHeight(); y++){
                pxTest[x][y] = imgTest.getRGB(x,y);
            }
        }

        //compare two images, and put the result into array
        //if images are the same, the result are all 0
        for (int x=0; x<imgSample.getWidth(); x++){
            for (int y=0; y<imgSample.getHeight(); y++){
                pxResult[x][y] = pxSample[x][y] - pxTest[x][y];
            }
        }
        //count the same number
        for (int x=0; x<imgSample.getWidth(); x++){
            for (int y=0; y<imgSample.getHeight(); y++){
                if (pxResult[x][y] == 0){
                    sameNum++;
                }
            }
        }

        totalPixel = imgSample.getWidth() * imgSample.getHeight();
        DecimalFormat df = new DecimalFormat("#.##");
        similarity = Double.parseDouble(df.format( (sameNum/totalPixel)*100 ));
        if (similarity == 100.0){
            return true;
        }else {
            System.out.println("Image similarity: "+similarity);
            return false;
        }



    }
}
