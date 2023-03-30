
CREATE TABLE TB_CURSO(
                         CD_CURSO varchar(255),
                         nm_curso varchar(255) not null,
                         cd_professor varchar(255) not null,
                         constraint PK_CURSO
                             PRIMARY KEY (CD_CURSO),
                         constraint FK_CURSO_PROFESSOR
                             FOREIGN KEY (cd_professor)
                                 REFERENCES TB_PROFESSOR(cd_professor)
);


CREATE TABLE CURSO_ALUNO_NOTA(
                                 CD_CURSO varchar(255),
                                 CD_ALUNO varchar(255),
                                 VL_NOTA INT,
                                 CONSTRAINT FK_CURSO_ALUNO_NOTA_ALUNO
                                     FOREIGN KEY (CD_ALUNO)
                                         REFERENCES TB_ALUNO(CD_ALUNO),
                                 CONSTRAINT FK_CURSO_ALUNO_NOTA_CURSO
                                     FOREIGN KEY (CD_CURSO)
                                         REFERENCES TB_CURSO(CD_CURSO),
                                 CONSTRAINT PK_CURSO_ALUNO_NOTA
                                     PRIMARY KEY (CD_CURSO,CD_ALUNO)
);