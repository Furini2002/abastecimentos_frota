package br.com.abastecimentofrota.service;

import br.com.abastecimentofrota.model.NotaFiscalCupom;
import br.com.abastecimentofrota.repository.NotaFiscalCupomRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administração01
 */
@Service
public class NotaFiscalCupomService {

    private final NotaFiscalCupomRepository repository;

    public NotaFiscalCupomService(NotaFiscalCupomRepository repository) {
        this.repository = repository;
    }

    public List<NotaFiscalCupom> listarTodos() {
        return repository.findAll();
    }

    @Transactional
    public NotaFiscalCupom salvar(NotaFiscalCupom nota) {
        return repository.save(nota);
    }

    public NotaFiscalCupom buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrado!"));
    }

    public void excluirCupomPorAbastecimentoId(Long abastecimentoId) {
        repository.deleteByAbastecimentoCadastradoId(abastecimentoId);
    }    
}
