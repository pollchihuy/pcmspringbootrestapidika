package com.juaracoding.service;

import com.juaracoding.model.GroupMenu;
import com.juaracoding.utils.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@SpringBootTest
public class TestGroupMenuService extends AbstractTestNGSpringContextTests{

    @Autowired
    private GroupMenuService groupMenuService;
    private DataGenerator dataGenerator ;
    private MockHttpServletRequest request;
    private ResponseEntity<Object> response = null;


    @BeforeTest
    public void initTest(){
        dataGenerator = new DataGenerator();
        request = new MockHttpServletRequest();
    }

    @Test
    public void save(){
        GroupMenu groupMenu = new GroupMenu();
        groupMenu.setName(dataGenerator.dataNamaTim());
        groupMenu.setCreatedBy(1L);
        int responseCode = 0;
        try{
            response = groupMenuService.save(groupMenu,request);
        }catch (Exception e){
            System.out.println("ERROR : " +e.getMessage());
        }
        responseCode = response.getStatusCodeValue();
        System.out.println(responseCode);
        Assert.assertNotNull(response,"GAGAL DISIMPAN ");
    }

    @Test
    public void findById(){
        int responseCode = 0;
        try{
            response = groupMenuService.findById(24L,request);
        }catch (Exception e){
            System.out.println("ERROR : " +e.getMessage());
        }
        System.out.println(response.getBody());
        responseCode = response.getStatusCodeValue();
        System.out.println(responseCode);
        Assert.assertEquals(200,responseCode,"TIDAK DITEMUKAN ");
    }

    @Test
    public void findAll(){
        Pageable pageable =  PageRequest.of(0,2, Sort.by("id").descending());
        try{
            response = groupMenuService.findAll(pageable,request);
        }catch (Exception e){
            System.out.println("ERROR : " +e.getMessage());
        }
        System.out.println(response.getBody());
        int responseCode = response.getStatusCodeValue();
        Assert.assertEquals(200,responseCode,"TIDAK DITEMUKAN ");
    }

    @AfterTest
    public void tearDown() {
        System.out.println("FINISH");
    }
}
