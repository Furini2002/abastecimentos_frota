package br.com.abastecimentofrota.service;

import br.com.abastecimentofrota.DTO.LinhaFiltroAbastecimentoDTO;
import br.com.abastecimentofrota.model.Abastecimento;
import br.com.abastecimentofrota.repository.AbastecimentoRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administração01
 */
@Service
public class AbastecimentoService {

    private final AbastecimentoRepository repository;

    public AbastecimentoService(AbastecimentoRepository repository) {
        this.repository = repository;
    }

    public List<Abastecimento> listarTodos() {
        return repository.findAll();
    }

    @Transactional
    public Abastecimento salvar(Abastecimento abastecimento) {
        return repository.save(abastecimento);
    }

    public Abastecimento buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Abastecimento não encontrado!"));
    }

    public void salvarTodos(List<Abastecimento> lista) {
        repository.saveAll(lista);
    }

    public Optional<Abastecimento> findByCupomFiscal(String cupomFiscal) {
        return repository.findByCupomFiscal(cupomFiscal);
    }

    public List<LinhaFiltroAbastecimentoDTO> converterAbastecimentoParaLinhaFiltroAbastecimentoDTO(List<Abastecimento> abastecimentos) {
        return abastecimentos.stream().map(abastecimento -> {
            LinhaFiltroAbastecimentoDTO dto = new LinhaFiltroAbastecimentoDTO();

            dto.setData(abastecimento.getData());
            dto.setFrota(abastecimento.getVeiculo().getFrota());
            dto.setPreco(abastecimento.getPreco());
            dto.setQuantidade(abastecimento.getQuantidade());
            dto.setCupomFiscal(abastecimento.getCupomFiscal());
            
            if (abastecimento.getNotaFiscal() != null) {
                dto.setNotaFiscal(abastecimento.getNotaFiscal().getNumero());
            }           

            return dto;
        }).collect(Collectors.toList());
    }
    
    public void excluir(Abastecimento abastecimento){
        repository.delete(abastecimento);
    }

}
