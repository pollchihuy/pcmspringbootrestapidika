//package com.juaracoding.util;
//
//
//import com.juaracoding.config.OtherConfig;
//
//import java.util.concurrent.Callable;
//
//public class ExecuteSMTP implements Callable<String> {
//    private String mailAddress;
//    private String subject;
//    private String [] strVerification;
//    private String pathFile;
//
//    private StringBuilder stringBuilder = new StringBuilder();
//    private String [] strException = new String[2];
//
//    public ExecuteSMTP() {
//        strException[0] = "ExecuteSMTP";
//
//    }
//
//    public ExecuteSMTP(String mailAddress, String subject, String[] strVerification, String pathFile) {
//        this.mailAddress = mailAddress;
//        this.subject = subject;
//        this.strVerification = strVerification;
//        this.pathFile = pathFile;
//        strException[0] = "ExecuteSMTP";
//    }
//
//    /** Eksekusi email disini untuk menetapkan timeOut*/
//    @Override
//    public String call() throws Exception {
//        sendSMTPToken(mailAddress,subject,strVerification,pathFile);
//        return null;
//    }
//
//    /*
//            Untuk hit 1 User saja
//         */
//    public Boolean sendSMTPToken(String mailAddress,
//                                 String subject,
//                                 String [] strVerification,
//                                 String pathFile)
//    {
//        try
//        {
//            if(OtherConfig.getFlagSmtpActive().equalsIgnoreCase("y") && !mailAddress.equals(""))
//            {
//                String strContent = new ReadTextFileSB(pathFile).getContentFile();
//                strContent = strContent.replace("#JKVM3NH",strVerification[0]);//Kepentingan
//                strContent = strContent.replace("XF#31NN",strVerification[1]);//Nama Lengkap
//                strContent = strContent.replace("8U0_1GH$",strVerification[2]);//TOKEN
//
//                String [] strEmail = {mailAddress};
//                SMTPCore sc = new SMTPCore();
//                return  sc.sendMailWithAttachment(strEmail,
//                        subject,
//                        strContent,
//                        "SSL",null);
//            }
//        }
//        catch (Exception e)
//        {
//            strException[1]="sendSMTPToken(String mailAddress, String subject, String purpose, String token) -- LINE 38";
//            LoggingFile.exceptionStringz(strException,e, OtherConfig.getFlagLoging());
//            return false;
//        }
//        return true;
//    }
//
//    /*
//        Untuk hit User yang banyak
//     */
//    public Boolean sendSMTPToken(String[] mailAddress, String subject, String [] strVerification,String pathFile)
//    {
//        try
//        {
//
//            if(OtherConfig.getFlagSmtpActive().equalsIgnoreCase("y") && !mailAddress.equals(""))
//            {
//                String strContent = new ReadTextFileSB(pathFile).getContentFile();
//                strContent = strContent.replace("#JKVM3NH",strVerification[0]);//Kepentingan
//                strContent = strContent.replace("XF#31NN",strVerification[1]);//Nama Lengkap
//                strContent = strContent.replace("8U0_1GH$",strVerification[2]);//TOKEN
//
//                String [] strEmail = mailAddress;
//                SMTPCore sc = new SMTPCore();
//                return  sc.sendMailWithAttachment(strEmail,
//                        subject,
//                        strContent,
//                        "SSL",null);
//            }
//        }
//        catch (Exception e)
//        {
//            strException[1]="sendSMTPToken(String mailAddress, String subject, String purpose, String token) -- LINE 38";
//            LoggingFile.exceptionStringz(strException,e, OtherConfig.getFlagLoging());
//            return false;
//        }
//        return true;
//    }
//}