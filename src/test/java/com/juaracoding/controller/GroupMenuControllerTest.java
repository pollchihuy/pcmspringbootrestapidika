package com.juaracoding.controller;

import static io.restassured.RestAssured.given;
import com.juaracoding.model.GroupMenu;
import com.juaracoding.repo.GroupMenuRepo;
import com.juaracoding.utils.DataGenerator;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import java.util.Optional;
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GroupMenuControllerTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private GroupMenuRepo groupMenuRepo;
    private JSONObject req;
    private GroupMenu groupMenu;
    private DataGenerator dataGenerator;
    private Random rand ;

    @BeforeClass
    private void init(){
        rand = new Random();
        req = new JSONObject();
        groupMenu = new GroupMenu();
        dataGenerator = new DataGenerator();
        Optional<GroupMenu> opGroupMenu = groupMenuRepo.findTop1By();
        groupMenu = opGroupMenu.get();
//        noKTP = rand.nextLong(100000000000000L,9999999999999L);
    }

    @BeforeTest
    private void setup(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test(priority = 0)
    private void save(){
        req.put("name",dataGenerator.dataNamaTim());
        RequestSpecification httpRequest = given().
                header("Content-Type","application/json").
                header("accept","*/*").
                body(req);
//        given().
//                header("Content-Type","multipart/form-data").
//                header("accept","*/*").
//                header("Authorization","*/*").
//                header("XSRF","*/*").
//                param("param1",rand.nextLong(10000L)).
//                multiPart("files",new File(System.getProperty("user.dir")+"\\data\\nama_image.jpg")).
//                multiPart("files",new File(System.getProperty("user.dir")+"\\data\\nama_image.jpg")).
//                multiPart("file1doank",new File(System.getProperty("user.dir")+"\\data\\nama_image.jpg")).
//                body(req);
        String pathVariable = "/api/v1/group_menu";
        Response response = httpRequest.request(Method.POST, pathVariable);
        int resposeCode = response.statusCode();
        ResponseBody responseBody = response.getBody();// seluruh body dari response
        System.out.println("====================================START RESPONSE BODY =================================================");
        System.out.println(responseBody.asPrettyString());// untuk melihat isi dari response body dalam bentuk JSON
        Assert.assertEquals(resposeCode,201);
    }

//    APP_PORT=8080;CONTOH=OK-BOS;CONTOH_LAIN=HUE;DB_PWD=b381990e93da47d98266f459e749d3af;DB_URL=abb07b3e6ce49452eee7f5532759ade255405fb9712a08b8b79917eea06353738db3d72c7d41436421fc59ebf1ae02fbbbde46a1679ac3f55072998cbae86e9d9649f318ddbb8d8fbcd1fae37a29e357a97847d15ca393b305e550261883dfbd;DB_USN=f691d7c07971842c371c2a2dc899f811;DDL_AUTO=create-drop;EMAIL_USN=17d63b1126a9e9b307867db15245d8e7ab1442297b4e300640ed22a615eba3a0;JWT_SECRET=660a1e020c2fdc8c12043a5dd3321cf2c7e2da5b11c203f352901fe6770c319cca98bb7f0332964b2bde23046fc461b1;TEST_AUTO=y;FLAG_LOGGING=Y
    @Test(priority = 1)
    private void update(){
        req.put("name",dataGenerator.dataNamaTim());
        RequestSpecification httpRequest = given().
                header("Content-Type","application/json").
                header("accept","*/*").
                body(req);

        String pathVariable = "/api/v1/group_menu/"+groupMenu.getId();
        Response response = httpRequest.request(Method.PUT, pathVariable);
        ResponseBody responseBody = response.getBody();// seluruh body dari response
        System.out.println("====================================START RESPONSE BODY =================================================");
        System.out.println(responseBody.asPrettyString());// untuk melihat isi dari response body dalam bentuk JSON
    }

//    APP_PORT=8080;CONTOH=OK-BOS;CONTOH_LAIN=HUE;DB_PWD=b381990e93da47d98266f459e749d3af;DB_URL=abb07b3e6ce49452eee7f5532759ade255405fb9712a08b8b79917eea06353738db3d72c7d41436421fc59ebf1ae02fbbbde46a1679ac3f55072998cbae86e9d9649f318ddbb8d8fbcd1fae37a29e357a97847d15ca393b305e550261883dfbd;DB_USN=f691d7c07971842c371c2a2dc899f811;DDL_AUTO=create-drop;EMAIL_USN=17d63b1126a9e9b307867db15245d8e7ab1442297b4e300640ed22a615eba3a0;JWT_SECRET=660a1e020c2fdc8c12043a5dd3321cf2c7e2da5b11c203f352901fe6770c319cca98bb7f0332964b2bde23046fc461b1;TEST_AUTO=y;FLAG_LOGGING=Y
    @Test(priority = 2)
    private void findByIdTest(){
        RequestSpecification httpRequest = given().
        header("Content-Type","application/json").
        header("accept","*/*");
        String pathVariable = "/api/v1/group_menu/"+groupMenu.getId();
        Response response = httpRequest.request(Method.GET, pathVariable);
        ResponseBody responseBody = response.getBody();// seluruh body dari response
        Headers responseHeader = response.getHeaders();// seluruh headers dari response akan di tampung disini
        System.out.println("====================================START RESPONSE BODY =================================================");
        System.out.println(responseBody.asPrettyString());// untuk melihat isi dari response body dalam bentuk JSON
    }

//    APP_PORT=8080;CONTOH=OK-BOS;CONTOH_LAIN=HUE;DB_PWD=b381990e93da47d98266f459e749d3af;DB_URL=abb07b3e6ce49452eee7f5532759ade255405fb9712a08b8b79917eea06353738db3d72c7d41436421fc59ebf1ae02fbbbde46a1679ac3f55072998cbae86e9d9649f318ddbb8d8fbcd1fae37a29e357a97847d15ca393b305e550261883dfbd;DB_USN=f691d7c07971842c371c2a2dc899f811;DDL_AUTO=create-drop;EMAIL_USN=17d63b1126a9e9b307867db15245d8e7ab1442297b4e300640ed22a615eba3a0;JWT_SECRET=660a1e020c2fdc8c12043a5dd3321cf2c7e2da5b11c203f352901fe6770c319cca98bb7f0332964b2bde23046fc461b1;TEST_AUTO=y;FLAG_LOGGING=Y
    @Test(priority = 3)
    private void findAll(){
        RequestSpecification httpRequest = given().
                header("Content-Type","application/json").
                header("accept","*/*").
                param("size",5);

        String pathVariable = "/api/v1/group_menu/all/0/asc/id";
        Response response = httpRequest.request(Method.GET, pathVariable);
        ResponseBody responseBody = response.getBody();// seluruh body dari response
        Headers responseHeader = response.getHeaders();// seluruh headers dari response akan di tampung disini
        System.out.println("====================================START RESPONSE BODY =================================================");
        System.out.println(responseBody.asPrettyString());// untuk melihat isi dari response body dalam bentuk JSON
    }

//    APP_PORT=8080;CONTOH=OK-BOS;CONTOH_LAIN=HUE;DB_PWD=b381990e93da47d98266f459e749d3af;DB_URL=abb07b3e6ce49452eee7f5532759ade255405fb9712a08b8b79917eea06353738db3d72c7d41436421fc59ebf1ae02fbbbde46a1679ac3f55072998cbae86e9d9649f318ddbb8d8fbcd1fae37a29e357a97847d15ca393b305e550261883dfbd;DB_USN=f691d7c07971842c371c2a2dc899f811;DDL_AUTO=create-drop;EMAIL_USN=17d63b1126a9e9b307867db15245d8e7ab1442297b4e300640ed22a615eba3a0;JWT_SECRET=660a1e020c2fdc8c12043a5dd3321cf2c7e2da5b11c203f352901fe6770c319cca98bb7f0332964b2bde23046fc461b1;TEST_AUTO=y;FLAG_LOGGING=Y
    @Test(priority = 4)
    private void uploadSheet(){
        RequestSpecification httpRequest = given().
                header("Content-Type","multipart/form-data").
                header("accept","*/*").
                multiPart("xlsx-file",new File(System.getProperty("user.dir")+"/src/test/resources/data-test/groupmenu.xlsx"));
        String pathVariable = "/api/v1/group_menu/upload-sheet";
        Response response = httpRequest.request(Method.POST, pathVariable);
        ResponseBody responseBody = response.getBody();// seluruh body dari response
        System.out.println("====================================START RESPONSE BODY =================================================");
        System.out.println(responseBody.asPrettyString());// untuk melihat isi dari response body dalam bentuk JSON
    }

 //APP_PORT=8080;CONTOH=OK-BOS;CONTOH_LAIN=HUE;DB_PWD=b381990e93da47d98266f459e749d3af;DB_URL=abb07b3e6ce49452eee7f5532759ade255405fb9712a08b8b79917eea06353738db3d72c7d41436421fc59ebf1ae02fbbbde46a1679ac3f55072998cbae86e9d9649f318ddbb8d8fbcd1fae37a29e357a97847d15ca393b305e550261883dfbd;DB_USN=f691d7c07971842c371c2a2dc899f811;DDL_AUTO=create-drop;EMAIL_USN=17d63b1126a9e9b307867db15245d8e7ab1442297b4e300640ed22a615eba3a0;JWT_SECRET=660a1e020c2fdc8c12043a5dd3321cf2c7e2da5b11c203f352901fe6770c319cca98bb7f0332964b2bde23046fc461b1;TEST_AUTO=y;FLAG_LOGGING=Y
    @Test(priority = 5)
    private void downloadSheet(){
        RequestSpecification httpRequest = given().
                header("Content-Type","application/json").
                header("accept","*/*").
                param("col","name").
                param("val",groupMenu.getName());

        String pathVariable = "/api/v1/group_menu/download-sheet";
        Response response = httpRequest.request(Method.GET, pathVariable);
        ResponseBody responseBody = response.getBody();// seluruh body dari response
        System.out.println("====================================START RESPONSE BODY =================================================");
        System.out.println(responseBody.asPrettyString());// untuk melihat isi dari response body dalam bentuk JSON
    }

    //    APP_PORT=8080;CONTOH=OK-BOS;CONTOH_LAIN=HUE;DB_PWD=b381990e93da47d98266f459e749d3af;DB_URL=abb07b3e6ce49452eee7f5532759ade255405fb9712a08b8b79917eea06353738db3d72c7d41436421fc59ebf1ae02fbbbde46a1679ac3f55072998cbae86e9d9649f318ddbb8d8fbcd1fae37a29e357a97847d15ca393b305e550261883dfbd;DB_USN=f691d7c07971842c371c2a2dc899f811;DDL_AUTO=create-drop;EMAIL_USN=17d63b1126a9e9b307867db15245d8e7ab1442297b4e300640ed22a615eba3a0;JWT_SECRET=660a1e020c2fdc8c12043a5dd3321cf2c7e2da5b11c203f352901fe6770c319cca98bb7f0332964b2bde23046fc461b1;TEST_AUTO=y;FLAG_LOGGING=Y
    @Test(priority = 100)
    private void delete(){
        /** jika ingin menjalankan suite / integration test fungsional delete di taruh pada urutan paling akhir , karena data yang dipilih di awal di gunakan untuk validasi di fungsi-fungsi sebelumnya */
        req.put("name",dataGenerator.dataNamaTim());
        RequestSpecification httpRequest = given().
                header("Content-Type","application/json").
                header("accept","*/*").
                body(req);

        String pathVariable = "/api/v1/group_menu/"+groupMenu.getId();
        Response response = httpRequest.request(Method.DELETE, pathVariable);
        ResponseBody responseBody = response.getBody();// seluruh body dari response
        System.out.println("====================================START RESPONSE BODY =================================================");
        System.out.println(responseBody.asPrettyString());// untuk melihat isi dari response body dalam bentuk JSON
    }
}