//package com.juaracoding.httpclient;
//
//import io.restassured.RestAssured;
//import io.restassured.config.HttpClientConfig;
//import io.restassured.config.RestAssuredConfig;
//import io.restassured.http.Method;
//import io.restassured.response.Response;
//import io.restassured.response.ResponseBody;
//import io.restassured.specification.RequestSpecification;
//
//import java.util.concurrent.Callable;
//
//import static io.restassured.RestAssured.config;
//import static io.restassured.RestAssured.given;
//
//public class CallServer implements Callable<String> {
//
//    public CallServer() {
//        System.out.println("MASUK CLASS CALL SERVER");
//        RestAssured.baseURI = "http://localhost:8989/example-server";
//    }
//
//    @Override
//    public String call() throws Exception {
//        System.out.println(getData());
//        return "Hello World";
//    }
//
//    public String getData(){
//        Response response;
//        RequestSpecification httpRequest;
//        ResponseBody responseBody = null;
//        try {
//            System.out.println("BASE URI : "+RestAssured.baseURI);
//            HttpClientConfig httpClientConfig = HttpClientConfig.httpClientConfig().
//                    setParam("http.socket.timeout",6000).
//                    setParam("http.connection.timeout",6000);
//            config = RestAssuredConfig.config().httpClient(httpClientConfig);
//            responseBody = given().
//                    header("Content-Type","application/json").
//                    header("accept","*/*").request(Method.GET, "/test-thread").body();
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        return responseBody.asPrettyString();
//    }
//}
