package com.juaracoding.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthControllerTest extends AbstractTestNGSpringContextTests {

    public static String authorization ;
    /** karena gak ada standard yang pasti untuk header key token
     * maka key nya dibuat variable agar suatu saat jika berubah semisal menjadi JwtToken atuapun jwt_token
     * tinggal diubah disini saja value AUTH_HEADER nya.....
     */
    public static final String AUTH_HEADER = "Authorization";
}
