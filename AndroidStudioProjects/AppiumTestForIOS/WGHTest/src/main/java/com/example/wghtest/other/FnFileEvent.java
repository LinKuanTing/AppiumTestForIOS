package com.example.wghtest.other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FnFileEvent {
    private  static String __strFilePath = "/Users/ting.lin/TestData/filePath";

    static ArrayList alDataPath = new ArrayList();


    //restore data file path to array
    public void loadFilePath() throws IOException {
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"Big5"));

        while (br.ready()){
            String strReadLine = br.readLine();
            if (!(strReadLine.contains("###"))){
                alDataPath.add(strReadLine);
            }
        }
    }


    //get file content by file path
    public ArrayList getContent(String strPath) throws IOException {
        FileInputStream fis = new FileInputStream(strPath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"Big5"));
        ArrayList al = new ArrayList();

        while (br.ready()){
            String strReadLine = br.readLine();
            if (!(strReadLine.contains("###"))){
                al.add(strReadLine);
            }
        }

        return al;
    }


    //get file path by file name
    public String getPath(String strFileName){
        for (int i=0; i<alDataPath.size(); i++){
            if (alDataPath.get(i).toString().contains(strFileName)){
                return alDataPath.get(i).toString();
            }
        }
        return null;
    }


    public String readToString(String strPath) {
        File file = new File(strPath);
        Long fileLength = file.length();
        byte[] fileContent = new byte[fileLength.intValue()];
        try{
            FileInputStream in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(fileContent);
    }
}
