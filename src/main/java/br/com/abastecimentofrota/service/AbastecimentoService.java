package br.com.abastecimentofrota.service;

import br.com.abastecimentofrota.model.Abastecimento;
import br.com.abastecimentofrota.repository.AbastecimentoRepository;
import java.util.List;
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

    /*public List<Abastecimento> converterLinhasParaAbastecimentos(
            ObservableList<LinhaAbastecimentoDTO> linhas,
            Veiculo veiculo,
            String mes,
            String tipoRegistro) {

        return linhas.stream().map(dto -> {
            Abastecimento abastecimento = new Abastecimento();
            abastecimento.setVeiculo(veiculo);
            abastecimento.setData(dto.getDia());
            abastecimento.setLitros(dto.getQuantidade());
            abastecimento.set(mes);
            abastecimento.setTipoRegistro(tipoRegistro);
            return abastecimento;
        }).collect(Collectors.toList());
    }*/
}


