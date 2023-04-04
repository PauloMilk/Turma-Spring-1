CREATE TABLE IF NOT EXISTS TB_ALUNO(
    cd_aluno SERIAL,
    nm_aluno varchar(255) not null,
    cd_cpf varchar(14) not null,
    constraint PK_ALUNO
    PRIMARY KEY (cd_aluno)
);

CREATE TABLE IF NOT EXISTS TB_DIRETOR(
    cd_diretor SERIAL,
    nm_diretor varchar(255) not null,
    cd_cpf varchar(14) not null,
    constraint PK_DIRETOR
    PRIMARY KEY (cd_diretor)
);

CREATE TABLE IF NOT EXISTS TB_PROFESSOR(
    cd_professor SERIAL,
	nm_professor varchar(255) not null,
    cd_cpf varchar(14) not null,
    ds_especialidade varchar(255) not null,
    constraint PK_PROFESSOR
    PRIMARY KEY (cd_professor)
);

CREATE TABLE IF NOT EXISTS TB_CURSO(
    cd_curso SERIAL,
    nm_curso varchar(255) not null,
	ds_curso varchar(255) not null,
	url_imagem varchar(255) not null,
    cd_professor SERIAL not null,
    constraint PK_CURSO
        PRIMARY KEY (cd_curso),
    constraint FK_CURSO_PROFESSOR
        FOREIGN KEY (cd_professor)
        REFERENCES TB_PROFESSOR(cd_professor)
);


CREATE TABLE IF NOT EXISTS TB_CURSO_ALUNO_NOTA(
    cd_curso SERIAL,
    cd_aluno SERIAL,
    vl_nota integer,
    CONSTRAINT FK_CURSO_ALUNO_NOTA_ALUNO
        FOREIGN KEY (cd_aluno)
        REFERENCES TB_ALUNO(cd_aluno),
    CONSTRAINT FK_CURSO_ALUNO_NOTA_CURSO
        FOREIGN KEY (cd_curso)
        REFERENCES TB_CURSO(cd_curso),
    CONSTRAINT PK_CURSO_ALUNO_NOTA
        PRIMARY KEY (cd_curso,cd_aluno)
);