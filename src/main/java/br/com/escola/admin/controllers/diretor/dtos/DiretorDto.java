package br.com.escola.admin.controllers.diretor.dtos;

import br.com.escola.admin.models.Diretor;

//public class DiretorDto implements Serializable {
public record DiretorDto(
        Long id,
        String nome,
        String cpf
) {

    public static DiretorDto from(Diretor diretor) {
        return new DiretorDto(diretor.getId(), diretor.getNome(), diretor.getCpf());
    }

    public static Diretor to(DiretorDto diretorDto) {
        return new Diretor(diretorDto.nome(), diretorDto.cpf());
    }

}
