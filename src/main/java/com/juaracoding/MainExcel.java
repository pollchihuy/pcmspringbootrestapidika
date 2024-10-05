package com.juaracoding;

import com.juaracoding.util.ExcelReader;

import java.util.List;
import java.util.Map;

public class MainExcel {

    public static void main(String[] args) {
        List<Map<String,String>> list = new ExcelReader(System.getProperty("user.dir")+"\\data\\contoh-excel-workbook-YANG-BENAR.xlsx","Sheet1").getDataMap();
        for(int i=0;i<list.size();i++){
            Map<String,String> m = list.get(i);
            for (Map.Entry<String,String> entry : m.entrySet()) {
                System.out.println(entry.getKey()+":"+entry.getValue());
            }
        }
    }
}
