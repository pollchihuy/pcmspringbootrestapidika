package com.juaracoding.controller;

import com.juaracoding.repo.GroupMenuRepo;
import com.juaracoding.util.FileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("hello")
public class CobaController {


    @Autowired
    private GroupMenuRepo groupMenuRepo;

    @PostMapping("/kirim-file")
    public String uploadFile(@RequestParam(value = "kiriman") MultipartFile file){
//        LoggingFile.print(OtherConfig.getFlagLogging(),"apa kek disini");
        String strFileName = file.getOriginalFilename();
        try {
            FileUtility.saveFile("user-files",strFileName,file);
        } catch (IOException e) {
//            throw new RuntimeException(e);
//            LoggingFile.exceptionStringz("CaptureRequestController",
//                    "getAllRequest"+sb.toString(),
//                    e, OtherConfig.getFlagLogging());
        }
        return strFileName;
    }
}