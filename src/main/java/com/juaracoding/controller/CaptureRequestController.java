//package com.juaracoding.controller;
//
//import com.juaracoding.config.OtherConfig;
//import com.juaracoding.util.LoggingFile;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.Arrays;
//import java.util.Enumeration;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("api")
//public class CaptureRequestController {
////    private static final Logger logger = LogManager.getLogger(CaptureRequestController.class);
//
//    @PostMapping("/req/{id}/{nama}")
//    public ResponseEntity<String> getAllRequest(
//            HttpServletRequest request,
//                            @PathVariable(value = "id")  Long id,
//                            @PathVariable(value = "nama")  String nama,
//                            @RequestBody Contoh contoh,
//                              @RequestParam(value = "p1") String p1,
//                              @RequestParam(value = "p2") String p2){
//        StringBuilder sb = new StringBuilder();
//        // Mengambil semua header
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            String headerValue = request.getHeader(headerName);
//            sb.append(headerName).append(" : ").append(headerValue).append("\n");
//        }
//
//        // Mengambil semua parameter
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
//            sb.append(entry.getKey()).append(": ").append(Arrays.toString(entry.getValue())).append("\n");
//        }
//
//        // Mengambil body (JSON atau plain text)
//        String body = null;
//        try {
//            int ix= 1/0;
//            body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        } catch (Exception e) {
//            System.out.println("Error Message : "+e.getMessage());
//            LoggingFile.exceptionStringz("CaptureRequestController",
//                    "getAllRequest"+sb.toString(),
//                    e, OtherConfig.getFlagLogging());
//        }
//        sb.append("Body: ").append(body).append("\n");
////        Exception e = new Exception("SENGAJA ERROR");
////        LoggingFile.exceptionStringz("CaptureRequestController","getAllRequest"+sb.toString(),e, OtherConfig.getFlagLogging());
//        return ResponseEntity.ok(sb.toString());
//    }
//}