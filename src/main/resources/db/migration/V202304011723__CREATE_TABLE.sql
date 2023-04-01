
CREATE TABLE aluno(
                         id int not null AUTO_INCREMENT PRIMARY KEY,
                         nome varchar(255) not null,
                         cpf varchar(255) not null
);

CREATE TABLE professor(
                         id int not null AUTO_INCREMENT PRIMARY KEY,
                         nome varchar(255) not null,
                         cpf varchar(255) not null,
                         especialidade varchar(255)
);

CREATE TABLE diretor(
                         id int not null AUTO_INCREMENT PRIMARY KEY,
                         nome varchar(255) not null,
                         cpf varchar(255) not null
);

CREATE TABLE curso(
                         id int not null AUTO_INCREMENT PRIMARY KEY,
                         nome varchar(255) not null,
                         professor int not null,
                         FOREIGN KEY (professor) REFERENCES professor(id)
);

CREATE TABLE cursoalunonota(
                         aluno_id int not null,
                         curso_id int not null,
                         nota double not null,
                         PRIMARY key (aluno_id, curso_id),
                          FOREIGN KEY (aluno_id) REFERENCES aluno(id),
                          FOREIGN KEY (curso_id) REFERENCES professor(id)
);