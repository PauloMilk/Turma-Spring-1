package br.com.escola.admin.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AlunoRepositoryIntegrationTest {

    @Autowired
    private AlunoRepository alunoRepository;

}