package com.juaracoding.util;


import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.juaracoding.config.OtherConfig;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Untuk Generate dari HTML (Thymeleaf) ke PDF
 */
@Component
public class PdfGenerator {
    private String [] strExceptionArr = new String[2];

    public PdfGenerator() {
        strExceptionArr[0] = "PdfGenerator";
    }
    private StringBuilder sBuild = new StringBuilder();

    /**
     *
     * @param html - thymeleaf yang sudah dirender menjadi html (Object-object nya sudah dimapping)
     * @param path - path tempat file akan tersimpan yang di set di otherconfig.properties , saya menggunakan C:/ karena semua pasti ada drive itu, kalau tidak ada tinggal dirubah saja arahkan kemana file nya nanti dan pastikan foldernya terbentuk dulu yah
     * @param prefixFile - awalan dari nama file nanti selanjutnya digunakan timestamp agar ada rekam jejak nya
     * @param request - data request dari front end
     * @return seluruh data ini akan diproses
     * @throws IOException - untuk error nya akan di throws ke function pemanggilnya jadi di handle disana saja agar bisa di response ke Front End
     */
    public Map<String,Object> htmlToPdf(String html, String prefixFile) throws IOException {
        Map<String,Object> map = new HashMap<>();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        ConverterProperties converterProperties = new ConverterProperties();
        DefaultFontProvider defaultFontProvider = new DefaultFontProvider();
        FileOutputStream fileOutputStream = null;

        try{
            converterProperties.setFontProvider(defaultFontProvider);
            HtmlConverter.convertToPdf(html,pdfWriter,converterProperties);
            sBuild.setLength(0);
            String fileName = sBuild.append(prefixFile).append("_").
                    append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())).
                    append(".pdf").toString();
            System.out.println("Full Path : "+ OtherConfig.getPathGeneratePDF()+fileName);
            fileOutputStream = new FileOutputStream(OtherConfig.getPathGeneratePDF()+fileName);
            byteArrayOutputStream.writeTo(fileOutputStream);
        }
        finally {

                byteArrayOutputStream.close();
                byteArrayOutputStream.flush();
                fileOutputStream.close();
        }
        return map;
    }
}