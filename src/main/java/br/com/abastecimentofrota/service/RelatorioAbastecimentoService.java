package br.com.abastecimentofrota.service;

import br.com.abastecimentofrota.DTO.LinhaFiltroAbastecimentoDTO;
import br.com.abastecimentofrota.DTO.LinhaRelatorioCupomDTO;
import br.com.abastecimentofrota.model.Abastecimento;
import br.com.abastecimentofrota.model.NotaFiscalCupom;
import br.com.abastecimentofrota.repository.AbastecimentoRepository;
import br.com.abastecimentofrota.repository.NotaFiscalCupomRepository;
import br.com.abastecimentofrota.util.TipoCombustivel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administração01
 */
@Service
public class RelatorioAbastecimentoService {

    @Autowired
    private NotaFiscalCupomRepository notaFiscalCupomRepository;

    @Autowired
    private AbastecimentoRepository abastecimentoRepository;

    /**
     * Cupons que estão vinculados a uma nota e possuem abastecimento
     * cadastrado.
     */
    public List<NotaFiscalCupom> buscarCuponsPagos(int mes, int ano, TipoCombustivel tipoCombustivel) {
        return notaFiscalCupomRepository.findCuponsPagosPorMesAnoTipoCombustivel(mes, ano, tipoCombustivel);
    }

    public List<LinhaRelatorioCupomDTO> buscarCuponsPagosDTO(int mes, int ano, TipoCombustivel tipoCombustivel) {
        List<NotaFiscalCupom> cupons = buscarCuponsPagos(mes, ano, tipoCombustivel);
        return cupons.stream().map(this::converterParaDTO).toList();
    }

    /**
     * Cupons que estão na nota fiscal mas ainda não têm abastecimento
     * cadastrado.
     */
    public List<NotaFiscalCupom> buscarCuponsNaoCadastrados(int mes, int ano, TipoCombustivel tipoCombustivel) {
        return notaFiscalCupomRepository.findCuponsPendentesPorMesAnoTipoCombustivel(mes, ano, tipoCombustivel);
    }

    public List<LinhaRelatorioCupomDTO> buscarCuponsNaoCadastradosDTO(int mes, int ano, TipoCombustivel tipoCombustivel) {
        List<NotaFiscalCupom> cupons = buscarCuponsNaoCadastrados(mes, ano, tipoCombustivel);
        return cupons.stream().map(this::converterParaDTO).toList();
    }

    /**
     * Abastecimentos cadastrados no sistema, mas que não estão vinculados a
     * nenhuma nota fiscal.
     */
    public List<Abastecimento> buscarAbastecimentosSemNota(int mes, int ano, TipoCombustivel tipoCombustivel) {
        return abastecimentoRepository.findAbastecimentosSemNotaPorMesAnoTipoCombustivel(mes, ano, tipoCombustivel);
    }

    public List<LinhaRelatorioCupomDTO> buscarAbastecimentosSemNotaDTO(int mes, int ano, TipoCombustivel tipoCombustivel) {
        List<Abastecimento> abastecimentos = buscarAbastecimentosSemNota(mes, ano, tipoCombustivel);
        return abastecimentos.stream().map(this::converterAbastecimentoParaDTO).toList();
    }

    //converter para ser aceito dados na tabela do relatorio
    private LinhaRelatorioCupomDTO converterParaDTO(NotaFiscalCupom cupom) {
        LinhaRelatorioCupomDTO dto = new LinhaRelatorioCupomDTO();
        dto.setNumeroNota(cupom.getNotaFiscal() != null ? cupom.getNotaFiscal().getNumero() : null);
        dto.setDataNota(cupom.getNotaFiscal() != null ? cupom.getNotaFiscal().getData() : null);
        dto.setNumeroCupom(cupom.getNumeroCupom());
        dto.setDataCupom(cupom.getAbastecimentoCadastrado() != null ? cupom.getAbastecimentoCadastrado().getData() : null);
        dto.setLitros(cupom.getAbastecimentoCadastrado() != null ? cupom.getAbastecimentoCadastrado().getQuantidade() : null);
        dto.setValor(cupom.getAbastecimentoCadastrado() != null ? cupom.getAbastecimentoCadastrado().getPreco() : null);
        dto.setTipoCombustivel(cupom.getAbastecimentoCadastrado() != null ? cupom.getAbastecimentoCadastrado().getVeiculo().getTipoCombustivel() : null);
        return dto;
    }

    private LinhaRelatorioCupomDTO converterAbastecimentoParaDTO(Abastecimento ab) {
        LinhaRelatorioCupomDTO dto = new LinhaRelatorioCupomDTO();
        dto.setNumeroNota(null);
        dto.setDataNota(null);
        dto.setNumeroCupom(ab.getCupomFiscal());
        dto.setDataCupom(ab.getData());
        dto.setLitros(ab.getQuantidade());
        dto.setValor(ab.getPreco());
        dto.setTipoCombustivel(ab.getVeiculo().getTipoCombustivel());
        return dto;
    }

    public List<LinhaRelatorioCupomDTO> agruparComTotais(List<LinhaRelatorioCupomDTO> linhasOriginais) {
        Map<String, List<LinhaRelatorioCupomDTO>> agrupado = linhasOriginais.stream()
                .collect(Collectors.groupingBy(LinhaRelatorioCupomDTO::getNumeroNota));

        List<LinhaRelatorioCupomDTO> resultado = new ArrayList<>();

        for (Map.Entry<String, List<LinhaRelatorioCupomDTO>> entry : agrupado.entrySet()) {
            String numeroNota = entry.getKey();
            List<LinhaRelatorioCupomDTO> cuponsDaNota = entry.getValue();

            resultado.addAll(cuponsDaNota); // Adiciona os cupons

            BigDecimal totalLitros = cuponsDaNota.stream()
                    .map(LinhaRelatorioCupomDTO::getLitros)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalValor = cuponsDaNota.stream()
                    .map(c -> c.getLitros().multiply(c.getValor()).setScale(2, RoundingMode.HALF_UP))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Adiciona a linha de total
            LinhaRelatorioCupomDTO linhaTotal = new LinhaRelatorioCupomDTO(numeroNota, totalLitros, totalValor);
            //linhaTotal.setTotal(true);

            resultado.add(linhaTotal);
        }

        return resultado;
    }
    
    public List<LinhaFiltroAbastecimentoDTO> buscarComFiltro(LinhaFiltroAbastecimentoDTO filtro) {
        List<Abastecimento> abastecimentos = abastecimentoRepository.buscarComFiltro(filtro);
        return abastecimentos.stream().map(this::converterParaLinhaFiltroDTO).toList();
    }    
       
    public LinhaFiltroAbastecimentoDTO converterParaLinhaFiltroDTO (Abastecimento ab) {
        LinhaFiltroAbastecimentoDTO dto = new LinhaFiltroAbastecimentoDTO();
        dto.setIdAbastecimento(ab.getId());
        dto.setData(ab.getData());
        dto.setFrota(ab.getVeiculo().getFrota());
        dto.setQuantidade(ab.getQuantidade());
        dto.setPreco(ab.getPreco());
        dto.setNotaFiscal(ab.getNotaFiscal() != null ? ab.getNotaFiscal().getNumero() : "");
        dto.setCupomFiscal(ab.getCupomFiscal());
        
        return dto;
    }

}
