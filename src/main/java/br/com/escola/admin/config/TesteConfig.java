package br.com.escola.admin.config;

import br.com.escola.admin.models.*;
import br.com.escola.admin.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
@Profile(value = "test")
public class TesteConfig implements CommandLineRunner {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private DiretorRepository diretorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CursoAlunoNotaRepository cursoAlunoNotaRepository;

    @Override
    public void run(String... args) throws Exception {

        var aluno1 = new Aluno("Maria de Andrade", "06065002003");
        var aluno2 = new Aluno("João Pedro Lima", "16345356000");
        var aluno3 = new Aluno("Rodrigo Silva Pereira", "54067661045");

        alunoRepository.saveAll(Arrays.asList(aluno1, aluno2, aluno3));

        var professor1 = new Professor("Marcelo", "12780604808", "Engenharia de Software");
        var professor2 = new Professor("Álvaro", "99411419836", "Engenharia de Produção");
        var professor3 = new Professor("Jair", "10082664854", "Filosofia (Lógica)");

        professorRepository.saveAll(Arrays.asList(professor1, professor2, professor3));

        var diretor1 = new Diretor("Angela", "70826057810");
        var diretor2 = new Diretor("Gustavo", "26281971892");

        diretorRepository.saveAll(Arrays.asList(diretor1, diretor2));

        var curso1 = new Curso("Spring Boot", "Curso Spring Completo"
                , "https://www.google.com/imagens/spring-boot");
        curso1.setProfessor(professor1);
        professor1.adicionarCurso(curso1);

        var curso2 = new Curso("Docker", "Aprenda sobre containers e imagens docker"
                , "https://www.google.com/imagens/docker");
        curso2.setProfessor(professor2);
        professor2.adicionarCurso(curso2);

        var curso3 = new Curso("Git e Github", "Aprofunde seu conhecimento em git e github"
                , "https://www.google.com/imagens/git-e-github");
        curso3.setProfessor(professor1);
        professor1.adicionarCurso(curso3);

        cursoRepository.saveAll(List.of(curso1, curso2, curso3));

        aluno1.getCursos().add(curso1);
        aluno1.getCursos().add(curso2);
    
        aluno2.getCursos().add(curso1);
        aluno2.getCursos().add(curso2);
        aluno2.getCursos().add(curso3);

        aluno3.getCursos().add(curso2);

        alunoRepository.saveAll(Arrays.asList(aluno1, aluno2, aluno3));

        cursoAlunoNotaRepository.saveAll(List.of(
                new CursoAlunoNota(new CursoAlunoNotaID(aluno1, curso1), 0.0),
                new CursoAlunoNota(new CursoAlunoNotaID(aluno1, curso2), 0.0),
                new CursoAlunoNota(new CursoAlunoNotaID(aluno2, curso1), 0.0),
                new CursoAlunoNota(new CursoAlunoNotaID(aluno2, curso2), 0.0),
                new CursoAlunoNota(new CursoAlunoNotaID(aluno2, curso3), 0.0),
                new CursoAlunoNota(new CursoAlunoNotaID(aluno3, curso2), 0.0)
        ));
    }

}
