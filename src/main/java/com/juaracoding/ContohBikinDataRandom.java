package com.juaracoding;

import java.util.Random;

public class ContohBikinDataRandom {


    public static void main(String[] args) {
        String strHurufBesar = "ABCDEFGHIJKLMNOPQRSTU";
        String strHurufKecil = "abcdefghijklmnopqrstu";
        String strHurufVokal = "aiueo";
        String strHurufKonsonan = "bcdfghjklmnpqrstvwxyz";
        Integer intArr[] = {1,2,3,4};
        Integer execIntArr = 0;
        Random r = new Random();
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sBuilder.setLength(0);
            for (int j = 0; j < 16; j++) {
                execIntArr = r.nextInt(0,intArr.length);
                System.out.print("Random Data nya : "+execIntArr);
                switch (execIntArr) {
                    case 0:sBuilder.append(strHurufBesar.charAt(r.nextInt(strHurufBesar.length())));break;
                    case 1:sBuilder.append(strHurufKecil.charAt(r.nextInt(strHurufKecil.length())));break;
                    case 2:sBuilder.append(strHurufKecil.charAt(r.nextInt(strHurufKecil.length())));break;
                    default:sBuilder.append(strHurufKonsonan.charAt(r.nextInt(strHurufKonsonan.length())));break;
                }
            }
            System.out.println("\n"+sBuilder.append("@gmail.com").toString());

        }
    }
}
