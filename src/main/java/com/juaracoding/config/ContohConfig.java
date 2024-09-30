package com.juaracoding.config;

import com.juaracoding.security.Crypto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:contoh.properties")
public class ContohConfig {

    private static String flagContoh;
    private static String flagContohLain;

    public static String getFlagContoh() {
        return flagContoh;
    }

    @Value("${flag.contoh}")
    private void setFlagContoh(String flagContoh) {
        this.flagContoh = Crypto.performDecrypt(flagContoh);
    }

    public static String getFlagContohLain() {
        return flagContohLain;
    }

    @Value("${flag.contoh.lain}")
    private void setFlagContohLain(String flagContohLain) {
        this.flagContohLain = Crypto.performDecrypt(flagContohLain);
    }

    //    private static String flagContoh;
//    private static String flagContohLain;
//
//
//    public static String getFlagContoh() {
//        return flagContoh;
//    }
//
//    @Value("${flag.contoh}")
//    private void setFlagContoh(String flagContoh) {
//        this.flagContoh = Crypto.performDecrypt(flagContoh);
//    }
//
//    public static String getFlagContohLain() {
//        return flagContohLain;
//    }
//
//    @Value("${flag.contoh.lain}")
//    private void setFlagContohLain(String flagContohLain) {
//        this.flagContohLain = Crypto.performDecrypt(flagContohLain);
//    }
}
