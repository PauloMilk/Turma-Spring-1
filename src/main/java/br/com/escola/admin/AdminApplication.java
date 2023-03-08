package br.com.escola.admin;

import br.com.escola.admin.repositories.AlunoRepository;
import br.com.escola.admin.repositories.DatabaseAlunoRepository;
import br.com.escola.admin.repositories.EmMemoriaProfessorRepository;
import br.com.escola.admin.repositories.ProfessorRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

    @Bean("alunoRepository")
    public AlunoRepository alunoRepository() {
//        return new EmMemoriaAlunoRepository();
        return new DatabaseAlunoRepository();
    }

    @Bean(name = "professorRepository")
    public ProfessorRepository professorRepository() {
    	return new EmMemoriaProfessorRepository();
    }
}

