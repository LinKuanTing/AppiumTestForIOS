package com.example.wghtest.Level1;

import com.example.wghtest.other.FnFileEncrypt;
import com.example.wghtest.other.FnFileEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.awt.Desktop;
import java.net.URL;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class FnMailEvent {
    private static String __strMailName;
    private static String __strPassword;
    private static String __strHost = "imap.gmail.com";

    private static String __strENMailRegister = "Activate your WowGoHealth account";
    private static String __strCHMailRegister = "我顧健康會員註冊驗證信";
    private static String __strENMailForgetPwd = "WowGoHealth Information (Forgot Password)";
    private static String __strCHMailForgetPwd = "我顧健康管理系統通知（忘記密碼)";

    private static String __strFileName = "MailAccount";
    private static String __strFilePath;


    public FnMailEvent() throws Exception {
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        FnFileEncrypt fnFileEncrypt = new FnFileEncrypt();
        byte[] bytes = fnFileEncrypt.loadFileContent(__strFilePath);
        String[] strContent = fnFileEncrypt.Decryptor(bytes).split("\n");
        __strMailName = strContent[0];
        __strPassword = strContent[1];

        //System.out.println(__strMailName + " , " + __strPassword);

    }

    public void getVerification() throws Exception {
        //use imap and set port
        Properties properties = System.getProperties();
        properties.setProperty("mail.store.protocol","imaps");
        properties.put("mail.imap.port","993");

        //connect imap
        Session session = Session.getDefaultInstance(properties,null);
        Store store = session.getStore("imaps");
        store.connect(__strHost,__strMailName,__strPassword);

        //get Inbox mail
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        //System.out.println("Total  Mail: " + folder.getMessageCount());
        //System.out.println("Unread Mail: " + folder.getUnreadMessageCount());

        //find unread mail
        FlagTerm flagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN),false);
        Message messages[] = folder.search(flagTerm);

        //find verification from unread mails
        String herfVerification = "";
        for (Message message : messages){
            if (message.getSubject().equals(__strCHMailRegister) || message.getSubject().equals(__strENMailRegister)) {
                //get HTML content
                Multipart multipart = (Multipart) message.getContent();
                BodyPart bodyPart = multipart.getBodyPart(0);
                //System.out.println(bodyPart.getContent().toString());

                //find href of verification
                Document doc = Jsoup.parse(bodyPart.getContent().toString());
                Elements href = doc.select("p").select("a[href]");

                //get herf
                String pickVerification = "";
                for (int i=0; i<href.size(); i++){
                    if (href.get(i).text().contains("[ 點此進行認證 ]") || href.get(i).text().contains("[ Click here for confirmation ]")){
                        pickVerification = href.get(i).toString();
                    }
                }

                String[] strAry = pickVerification.split("\"");
                herfVerification = strAry[1];
                System.out.println(herfVerification);
            }
        }

        //use default browser to surf href
        try {
            Desktop desktop = Desktop.getDesktop();
            URL url = new URL(herfVerification);
            desktop.browse(url.toURI());
            //close browse
            Thread.sleep(15000);
            Runtime.getRuntime().exec("killall firefox");
        } catch (Exception eNoFindMail){
            System.out.println(eNoFindMail);
            System.out.println("Reason: no find the verification mail");
        }

    }


    public boolean isExistForgetPwdMail() throws Exception {
        //use imap and set port
        Properties properties = System.getProperties();
        properties.setProperty("mail.store.protocol","imaps");
        properties.put("mail.imap.port","993");

        //connect imap
        Session session = Session.getDefaultInstance(properties,null);
        Store store = session.getStore("imaps");
        store.connect(__strHost,__strMailName,__strPassword);

        //get inbox mail
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        //System.out.println("Total  Mail: " + folder.getMessageCount());
        //System.out.println("Unread Mail: " + folder.getUnreadMessageCount());

        //find unread mail
        FlagTerm flagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN),false);
        Message messages[] = folder.search(flagTerm);

        //find forget password mail
        boolean isFindMail = false;
        for (Message message : messages) {
            if (message.getSubject().equals(__strCHMailForgetPwd) || message.getSubject().equals(__strENMailForgetPwd)){
                isFindMail = true;
                //find mail and set already read
                message.setFlag(Flags.Flag.SEEN,true);
            }
        }

        return isFindMail;

    }

}
