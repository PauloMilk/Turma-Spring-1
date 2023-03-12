package br.com.escola.admin.config;

import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.DiretorRepository;
import br.com.escola.admin.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile(value = "test")
public class TesteConfig implements CommandLineRunner {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private DiretorRepository diretorRepository;

    @Override
    public void run(String... args) throws Exception {

        /*var aluno1 = new Aluno("06065002003", "Maria de Andrade");
        var aluno2 = new Aluno("16345356000", "João Pedro Lima");
        var aluno3 = new Aluno("54067661045", "Rodrigo Silva Pereira");*/

        var professor1 = new Professor("Marcelo", "12780604808", "Engenharia de Software");
        var professor2 = new Professor("Álvaro", "99411419836", "Engenharia de Produção");
        var professor3 = new Professor("Jair", "10082664854", "Filosofia (Lógica)");

        professorRepository.saveAll(Arrays.asList(professor1, professor2, professor3));

        var diretor1 = new Diretor("Angela", "70826057810");
        var diretor2 = new Diretor("Gustavo", "26281971892");

        diretorRepository.saveAll(Arrays.asList(diretor1, diretor2));

    }

}
