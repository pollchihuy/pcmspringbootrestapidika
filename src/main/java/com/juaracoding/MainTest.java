package com.juaracoding;

import com.juaracoding.security.Crypto;
import org.springframework.stereotype.Component;

@Component
public class MainTest {


    //untuk log4j2
//    private static final Logger logger = LogManager.getLogger(MainTest.class);

    //untuk logback
//    private static final Logger logger = LoggerFactory.getLogger(MainTest.class);
    public static void main(String[] args) {
//        logger.info("hahahaha");
//        logger.debug("hahahaha");
//        logger.error("hahahaha");
//        logger.warn("hahahaha");
//        logger.fatal("hahahaha");
//        logger.trace("hahahaha");
//        System.out.println(System.currentTimeMillis());
//        String strEncrypt = Crypto.performEncrypt("HelloForm.C12."+System.currentTimeMillis());
//        String strEncrypt ="d9fddb00e945a1a8d010d949e622a090a019c003ac55eea85f28acb0cad3a4f7";
        String strEncrypt ="d9fddb00e945a1a8d010d949e622a090339208b69fdce47a6eaab54fc2f5b165";
//        System.out.println("TOKEN BARU "+strEncrypt);
//        System.out.println(strEncrypt);
//        System.out.println("================DISINI BALIK==============");
//        String strDecrypt=Crypto.performDecrypt(strEncrypt);
//        System.out.println(strDecrypt);
//        String [] strArr =strDecrypt.split("\\.");
//        for (int i = 0; i < strArr.length; i++) {
//            System.out.println(strArr[i]);
//        }
//        HelloForm.C12.1727857452508
//        System.out.println(Crypto.performDecrypt(strEncrypt));
        String [] strArr = Crypto.performDecrypt(strEncrypt).split("\\.");
        System.out.println(strArr[0]);// saya gak balikin token lagi ( gak ada refresh )
        System.out.println(strArr[1]);// saya gak balikin token lagi ( gak ada refresh )
        System.out.println(strArr[2]);
        if(!strArr[0].equals("HelloForm")){
            System.out.println("NO TOKEN FOR YOU");
        }
        if(!strArr[1].equals("C12")){
            System.out.println("NO TOKEN FOR YOU");
        }
        System.out.println("CURRENT TIME MILIS "+System.currentTimeMillis());
        System.out.println("CURRENT TIME MILIS "+strArr[2]);
        System.out.println((System.currentTimeMillis()-Long.parseLong(strArr[2]))/(1000));
//        System.out.println(Crypto.performDecrypt("d9fddb00e945a1a8d010d949e622a090a019c003ac55eea85f28acb0cad3a4f7"));
//        System.out.println((System.currentTimeMillis()-(Long.parseLong(strArr[2])))/1000);
//        System.out.println((System.currentTimeMillis()-1727857452508L)/(1000*60));
//        System.out.println(strArr[0]);
//        System.out.println((UUID.randomUUID()).toString().replaceAll("-", ""));
    }
}