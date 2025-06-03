package br.com.abastecimentofrota.DTO;

import br.com.abastecimentofrota.util.TipoCombustivel;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LinhaRelatorioCupomDTO {

    private String numeroNota;
    private LocalDate dataNota;
    private String numeroCupom;
    private LocalDate dataCupom;
    private BigDecimal litros;
    private BigDecimal valor;
    private TipoCombustivel tipoCombustivel;
    private boolean isTotal;

    // Construtor para linhas normais
    public LinhaRelatorioCupomDTO(String numeroNota, LocalDate dataNota, String numeroCupom,
            LocalDate dataCupom, BigDecimal litros, BigDecimal valor,
            TipoCombustivel tipoCombustivel) {
        this.numeroNota = numeroNota;
        this.dataNota = dataNota;
        this.numeroCupom = numeroCupom;
        this.dataCupom = dataCupom;
        this.litros = litros;
        this.valor = valor;
        this.tipoCombustivel = tipoCombustivel;
        this.isTotal = false;
    }

    public LinhaRelatorioCupomDTO() {
    }   

    // Construtor para linha de total
    public LinhaRelatorioCupomDTO(String numeroNota, BigDecimal litros, BigDecimal valor) {
        this.numeroNota = numeroNota;
        this.litros = litros;
        this.valor = valor;
        this.isTotal = true;
    }

    public String getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(String numeroNota) {
        this.numeroNota = numeroNota;
    }

    public LocalDate getDataNota() {
        return dataNota;
    }

    public void setDataNota(LocalDate dataNota) {
        this.dataNota = dataNota;
    }

    public String getNumeroCupom() {
        return numeroCupom;
    }

    public void setNumeroCupom(String numeroCupom) {
        this.numeroCupom = numeroCupom;
    }

    public LocalDate getDataCupom() {
        return dataCupom;
    }

    public void setDataCupom(LocalDate dataCupom) {
        this.dataCupom = dataCupom;
    }

    public BigDecimal getLitros() {
        return litros;
    }

    public void setLitros(BigDecimal litros) {
        this.litros = litros;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public boolean isIsTotal() {
        return isTotal;
    }

    public void setIsTotal(boolean isTotal) {
        this.isTotal = isTotal;
    }
    
    

}
