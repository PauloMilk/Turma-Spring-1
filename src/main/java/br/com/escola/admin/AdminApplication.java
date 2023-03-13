package br.com.escola.admin;

import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }


}

