package br.com.abastecimentofrota.service;

import br.com.abastecimentofrota.model.Abastecimento;
import br.com.abastecimentofrota.model.NotaFiscal;
import br.com.abastecimentofrota.model.NotaFiscalCupom;
import br.com.abastecimentofrota.model.Veiculo;
import br.com.abastecimentofrota.repository.AbastecimentoRepository;
import br.com.abastecimentofrota.repository.NotaFiscalCupomRepository;
import br.com.abastecimentofrota.repository.NotaFiscalRepository;
import br.com.abastecimentofrota.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AtualizarAbastecimentoService {

    private final AbastecimentoRepository repository;
    private final NotaFiscalRepository notaFiscalRepository;
    private final VeiculoRepository veiculoRepository;
    private final NotaFiscalCupomRepository notaFiscalCupomRepository;

    public AtualizarAbastecimentoService(
            AbastecimentoRepository repository,
            NotaFiscalRepository notaFiscalRepository,
            VeiculoRepository veiculoRepository,
            NotaFiscalCupomRepository notaFiscalCupomRepository
    ) {
        this.repository = repository;
        this.notaFiscalRepository = notaFiscalRepository;
        this.veiculoRepository = veiculoRepository;
        this.notaFiscalCupomRepository = notaFiscalCupomRepository;
    }

    public void atualizarAbastecimento(Abastecimento atualizado) {
        Abastecimento existente = repository.findById(atualizado.getId())
                .orElseThrow();

        existente.setQuantidade(atualizado.getQuantidade());
        existente.setData(atualizado.getData());
        existente.setCupomFiscal(atualizado.getCupomFiscal());

        NotaFiscal novaNota = atualizado.getNotaFiscal();
        if (novaNota != null && (existente.getNotaFiscal() == null
                || !Objects.equals(novaNota.getNumero(), existente.getNotaFiscal().getNumero()))) {

            NotaFiscal notaNoBanco = notaFiscalRepository.findWithNumber(novaNota.getNumero())
                    .orElseGet(() -> {
                        novaNota.setData(atualizado.getData()); 
                        novaNota.setTipoCombustivel(atualizado.getVeiculo().getTipoCombustivel());
                        return notaFiscalRepository.save(novaNota);
                    });

            existente.setNotaFiscal(notaNoBanco);
            associarNotaComAbastecimento(notaNoBanco, existente); // Chamada direta
        }

        Veiculo novoVeiculo = atualizado.getVeiculo();
        if (novoVeiculo != null && (existente.getVeiculo() == null
                || !Objects.equals(novoVeiculo.getFrota(), existente.getVeiculo().getFrota()))) {

            Veiculo veiculoEncontrado = veiculoRepository.findByFrota(novoVeiculo.getFrota())
                    .orElseThrow(() -> new EntityNotFoundException("Veículo com essa frota não encontrado."));

            existente.setVeiculo(veiculoEncontrado);
        }

        repository.save(existente);
    }

    private void associarNotaComAbastecimento(NotaFiscal nota, Abastecimento abastecimento) {
        Optional<NotaFiscalCupom> cupomExistente = notaFiscalCupomRepository.findByAbastecimentoCadastrado(abastecimento);

        if (cupomExistente.isPresent()) {
            NotaFiscalCupom cupom = cupomExistente.get();
            cupom.setNotaFiscal(nota);
            notaFiscalCupomRepository.save(cupom);
        } else {
            NotaFiscalCupom novo = new NotaFiscalCupom();
            novo.setNotaFiscal(nota);
            novo.setAbastecimentoCadastrado(abastecimento);
            novo.setNumeroCupom(abastecimento.getCupomFiscal());            
            notaFiscalCupomRepository.save(novo);
        }
    }
}
