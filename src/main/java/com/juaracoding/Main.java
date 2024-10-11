package com.juaracoding;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
//        Security.addProvider(new BouncyCastleProvider());
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