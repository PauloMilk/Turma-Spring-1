
CREATE TABLE if not exists aluno(
                         id int not null AUTO_INCREMENT PRIMARY KEY,
                         nome varchar(255) not null,
                         cpf varchar(255) not null unique
);

CREATE TABLE if not exists professor(
                         id int not null AUTO_INCREMENT PRIMARY KEY,
                         nome varchar(255) not null,
                         cpf varchar(255) not null unique,
                         especialidade varchar(255)
);

CREATE TABLE if not exists diretor(
                         id int not null AUTO_INCREMENT PRIMARY KEY,
                         nome varchar(255) not null,
                         cpf varchar(255) not null unique
);

CREATE TABLE if not exists curso(
                         id int not null AUTO_INCREMENT PRIMARY KEY,
                         nome varchar(255) not null,
                         professor int not null,
                         FOREIGN KEY (professor) REFERENCES professor(id)
);

CREATE TABLE if not exists curso_aluno_nota(
                         aluno_id int not null,
                         curso_id int not null,
                         nota double not null,
                         PRIMARY key (aluno_id, curso_id),
                          FOREIGN KEY (aluno_id) REFERENCES aluno(id),
                          FOREIGN KEY (curso_id) REFERENCES professor(id)
);