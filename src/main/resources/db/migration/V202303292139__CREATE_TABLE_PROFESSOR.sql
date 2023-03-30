CREATE TABLE IF NOT EXISTS TB_PROFESSOR(
                             cd_professor varchar(255),
                             nm_professor varchar(255) not null,
                             constraint PK_PROFESSOR
                                 PRIMARY KEY (cd_professor)
);
