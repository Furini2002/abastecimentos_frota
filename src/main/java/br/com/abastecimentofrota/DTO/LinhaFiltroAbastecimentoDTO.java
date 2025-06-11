package br.com.abastecimentofrota.DTO;

import br.com.abastecimentofrota.model.Abastecimento;
import br.com.abastecimentofrota.service.NotaFiscalService;
import br.com.abastecimentofrota.service.VeiculoService;
import br.com.abastecimentofrota.util.TipoCombustivel;
import br.com.abastecimentofrota.util.TipoRegistro;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LinhaFiltroAbastecimentoDTO {

    private Long idAbastecimento;
    private LocalDate data;
    private String frota;
    private String notaFiscal;
    private String cupomFiscal;
    private TipoCombustivel tipoCombustivel;
    private BigDecimal preco;
    private BigDecimal precoMin;
    private BigDecimal precoMax;
    private BigDecimal quantidade;
    private BigDecimal quantidadeMin;
    private BigDecimal quantidadeMax;

    public LinhaFiltroAbastecimentoDTO() {
        this.data = null;
        this.frota = null;
        this.precoMin = null;
        this.precoMax = null;
        this.notaFiscal = null;
        this.cupomFiscal = null;
        this.quantidadeMin = null;
        this.quantidadeMax = null;
    }

    public LinhaFiltroAbastecimentoDTO(LocalDate data, String frota, String notaFiscal, String cupomFiscal, TipoCombustivel tipoCombustivel, BigDecimal precoMin, BigDecimal precoMax, BigDecimal quantidadeMin, BigDecimal quantidadeMax) {
        this.data = data;
        this.frota = frota;
        this.notaFiscal = notaFiscal;
        this.cupomFiscal = cupomFiscal;
        this.tipoCombustivel = tipoCombustivel;
        this.precoMin = precoMin;
        this.precoMax = precoMax;
        this.quantidadeMin = quantidadeMin;
        this.quantidadeMax = quantidadeMax;
    }

    @Override
    public String toString() {
        return "LinhaFiltroAbastecimentoDTO{" + "data=" + data + ", frota=" + frota + ", notaFiscal=" + notaFiscal + ", cupomFiscal=" + cupomFiscal + ", tipoCombustivel=" + tipoCombustivel + ", preco=" + preco + ", precoMin=" + precoMin + ", precoMax=" + precoMax + ", quantidade=" + quantidade + ", quantidadeMin=" + quantidadeMin + ", quantidadeMax=" + quantidadeMax + '}';
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getFrota() {
        return frota;
    }

    public void setFrota(String frota) {
        this.frota = frota;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public String getCupomFiscal() {
        return cupomFiscal;
    }

    public void setCupomFiscal(String cupomFiscal) {
        this.cupomFiscal = cupomFiscal;
    }

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getPrecoMin() {
        return precoMin;
    }

    public void setPrecoMin(BigDecimal precoMin) {
        this.precoMin = precoMin;
    }

    public BigDecimal getPrecoMax() {
        return precoMax;
    }

    public void setPrecoMax(BigDecimal precoMax) {
        this.precoMax = precoMax;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getQuantidadeMin() {
        return quantidadeMin;
    }

    public void setQuantidadeMin(BigDecimal quantidadeMin) {
        this.quantidadeMin = quantidadeMin;
    }

    public BigDecimal getQuantidadeMax() {
        return quantidadeMax;
    }

    public void setQuantidadeMax(BigDecimal quantidadeMax) {
        this.quantidadeMax = quantidadeMax;
    }

    public Long getIdAbastecimento() {
        return idAbastecimento;
    }

    public void setIdAbastecimento(Long idAbastecimento) {
        this.idAbastecimento = idAbastecimento;
    }

    public Abastecimento converterParaAbastecimento(LinhaFiltroAbastecimentoDTO dto, VeiculoService veiculoService, NotaFiscalService notaService) {
        Abastecimento ab = new Abastecimento();

        ab.setId(dto.getIdAbastecimento());
        ab.setVeiculo(veiculoService.buscarPorFrota(dto.getFrota()));
        ab.setData(dto.getData());
        ab.setQuantidade(dto.getQuantidade());
        ab.setPreco(dto.getPreco() != null ? dto.getPreco() : null);
        ab.setTipoRegistro(TipoRegistro.DIARIO);
        ab.setCupomFiscal(!(dto.getCupomFiscal().isEmpty()) ? dto.getCupomFiscal() : null);
        ab.setNotaFiscal(
                notaService.buscarPorNUmero(dto.getNotaFiscal()).orElse(null)
        );
        return ab;
    }

}
