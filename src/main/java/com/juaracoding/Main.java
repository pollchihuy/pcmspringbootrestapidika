package com.juaracoding;


import com.juaracoding.model.GroupMenu;
import com.juaracoding.model.User;
import com.juaracoding.repo.GroupMenuRepo;
import com.juaracoding.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

//    @Bean
//	public CommandLineRunner initializeUser(UserRepo userRepository, GroupMenuRepo groupMenuRepository) {
//		return args -> {
//			User user = new User();
////			user.setId(UuidCreator.getTimeBased().toString().replaceAll("-",""));
//			user.setUsername("pollchihuy");
//			user.setAlamat("BogorBogorBogorBogorBogorBogorBogorBogorBogor");
//			user.setEmail("poll.chihuy@gmail.com");
//			user.setTanggalLahir(LocalDate.parse("1995-12-25"));
//			user.setNoHp("628128331213");
//			user.setPassword("paul123pollchihuy");
//			user.setRegis(true);
//			user.setActive(false);
//			user.setNama("Paul Christian");
//			userRepository.save(user);
//
//			GroupMenu groupMenu = new GroupMenu();
//			groupMenu.setName("USER MANAGEMENT");
//			groupMenuRepository.save(groupMenu);
//
//			groupMenu = new GroupMenu();
//			groupMenu.setName("REPORT");
//			groupMenuRepository.save(groupMenu);
//
//			groupMenu = new GroupMenu();
//			groupMenu.setName("TRANSACTION");
//			groupMenuRepository.save(groupMenu);
//
//			groupMenu = new GroupMenu();
//			groupMenu.setName("SALES");
//			groupMenuRepository.save(groupMenu);
//
//			groupMenu = new GroupMenu();
//			groupMenu.setName("CONFIG");
//			groupMenuRepository.save(groupMenu);
//
//
//		};
//	}
}