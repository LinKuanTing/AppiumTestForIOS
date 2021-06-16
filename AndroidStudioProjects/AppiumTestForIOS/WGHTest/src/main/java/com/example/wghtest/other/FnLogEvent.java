package com.example.wghtest.other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FnLogEvent {
    public static String strPassMsg = "PASS";
    public static String strFailMsg = "(FAIL)";
    private static String  __levelInfo = "INFO", __levelWarn = "WARN", __levelDebug = "DEBUG";

    private static String __strFilePath = "/Users/ting.lin/TestData/TestLog/Log.log";

    ArrayList alLogContent = new ArrayList();
    private static int __numPASS = 0, __numFAIL = 0;

    public void logFormat() throws IOException {
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"Big5"));

        while (br.ready()){
            String strReadLine = br.readLine().replaceAll(".java","").replaceAll("excute","");
            alLogContent.add(strReadLine);
        }

        //delete DEBUG date and file location message
        for (int i=0; i<alLogContent.size(); i++){
            if (alLogContent.get(i).equals(__levelDebug)){
                alLogContent.remove(i+1);
                alLogContent.remove(i+1);
            }
        }

        //count numner PASS and FAIL
        for (int i=0; i<alLogContent.size(); i++){
            if (alLogContent.get(i).equals(__levelInfo)){
                __numPASS++;
            }else if (alLogContent.get(i).equals(__levelWarn)){
                __numFAIL++;
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");
        Date date = new Date();
        String strDate = dateFormat.format(date);
        File file = new File("/Users/ting.lin/TestData/TestLog/"+ strDate +".log");
        file.createNewFile();

        FileWriter fw = new FileWriter(file);
        fw.write(new String().format("%-30s%-60s%s%n","Time","Method","Result"));
        fw.write("----------------------------------------------------------------------------------------------------\r\n");

        //display test case time . name and result
        for (int i=0; i<alLogContent.size(); i++){
            String str = "";
            switch (alLogContent.get(i).toString()){
                case "INFO":
                case "WARN":
                    str = String.format("%-30s%-60s%s%n",alLogContent.get(i+1),alLogContent.get(i+2),alLogContent.get(i+3));
                    fw.write(str);
                    break;
                case "DEBUG":
                    //str = String.format("%s%n",alLogContent.get(i+1));
                    //fw.write(str);
                    break;
                default:
                    break;
            }
        }

        fw.write("----------------------------------------------------------------------------------------------------\r\n");

        fw.write("Performance : \r\n");

        for (int i=0; i<alLogContent.size(); i++){
            String str = "";
            switch (alLogContent.get(i).toString()){
                case "DEBUG":
                    if (alLogContent.get(i-4).equals(__levelInfo)){
                        str = String.format("    %-45@%s%n        %s%n",alLogContent.get(i-2),alLogContent.get(i-3),alLogContent.get(i+1));
                    }
                default:
                    break;
            }
        }

        fw.write("\r\nTotal test cases FAILED : " + __numFAIL + " / " + (__numFAIL+__numPASS) + "\r\n");

        for (int i=0; i<alLogContent.size(); i++){
            String str = "";
            switch (alLogContent.get(i).toString()){
                case "WARN":
                    str = String.format("    %-45s@%s%n",alLogContent.get(i+2),alLogContent.get(i+1));
                    fw.write(str);
                    break;
                case "DEBUG":
                    if (alLogContent.get(i-4).equals(__levelWarn) || alLogContent.get(i-2).equals(__levelDebug)){
                        str = String.format("        %s%n",alLogContent.get(i+1));
                        fw.write(str);
                    }
                    break;
                default:
                    break;
            }
        }

        fw.write("\r\nTotal test cases PASSED : " + __numPASS + " / " + (__numFAIL+__numPASS) + "\r\n");

        fw.flush();
        fw.close();

    }

}
