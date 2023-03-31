package br.com.escola.admin.controllers.aluno.mapper;

import br.com.escola.admin.controllers.aluno.dto.AlunoPostRequest;
import br.com.escola.admin.models.Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AlunoMapper {
    AlunoMapper INSTANCE = Mappers.getMapper(AlunoMapper.class);

    Aluno alunoRequestToAluno(AlunoPostRequest request);

}
