package com.juaracoding.repo;

import com.juaracoding.model.GroupMenu;
import com.juaracoding.utils.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.Random;

@SpringBootTest
public class TestGroupMenuRepo extends AbstractTestNGSpringContextTests {
    @Autowired
    private GroupMenuRepo groupMenuRepo;
    private DataGenerator dataGenerator ;
    private Random rand;

    @BeforeTest
    public void initTest(){
        rand = new Random();
        dataGenerator = new DataGenerator();
    }

    @Test(priority = 0)
    public void save(){
        GroupMenu groupMenu = new GroupMenu();
        groupMenu.setName(dataGenerator.dataNamaTim());
        groupMenu.setCreatedBy(1L);
        try {
            groupMenuRepo.save(groupMenu);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(groupMenu.getId());
        Assert.assertNotNull(groupMenu.getId());
    }

    @Test(priority = 1)
    public void update(){
        Optional<GroupMenu> opNext = groupMenuRepo.findTopByOrderByIdDesc();
        if(!opNext.isPresent()){
            Assert.assertNotNull(null);
        }
        GroupMenu groupMenu = opNext.get();
        System.out.println(groupMenu.getName());
        System.out.println(groupMenu.getId());
        try {
            groupMenu.setName(dataGenerator.dataNamaTim());
            groupMenu.setUpdatedBy(1L);
            groupMenuRepo.save(groupMenu);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(groupMenu.getId());
        Assert.assertNotNull(groupMenu.getId());
    }

    @Test(priority = 2)
    public void delete(){
        Optional<GroupMenu> opNext = groupMenuRepo.findTopByOrderByIdDesc();
        if(!opNext.isPresent()){
            Assert.assertNotNull(null);
        }
        GroupMenu groupMenu = opNext.get();
        System.out.println(groupMenu.getName());
        System.out.println(groupMenu.getId());
        try {
            groupMenu.setName(dataGenerator.dataNamaTim());
            groupMenu.setUpdatedBy(1L);
            groupMenuRepo.deleteById(groupMenu.getId());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(groupMenu.getId());
        Assert.assertNotNull(groupMenu.getId());
    }
}