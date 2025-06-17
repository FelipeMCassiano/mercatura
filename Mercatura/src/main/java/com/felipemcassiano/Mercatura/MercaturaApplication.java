package com.felipemcassiano.Mercatura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MercaturaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MercaturaApplication.class, args);
    }

}
