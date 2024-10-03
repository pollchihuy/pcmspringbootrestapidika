package com.juaracoding.repo;


import com.juaracoding.model.GroupMenu;
import com.juaracoding.utils.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.Random;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
/** kalau gak dipakai , semisal test berhasil tapi datanya gak dimasukin ke table */
@Transactional(propagation = Propagation.NOT_SUPPORTED)
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

    @Test
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

    @Test
    public void update(){
        Optional<GroupMenu> opNext = groupMenuRepo.findTop1By();
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

    @Test
    public void delete(){
        Optional<GroupMenu> opNext = groupMenuRepo.findTop1By();
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