package br.com.abastecimentofrota.DTO;

import java.math.BigDecimal;

/**
 *
 * @author Administração01
 */
public class LinhaAbastecimentoDTO {

    private Integer dia;
    private String posto;
    private BigDecimal quantidade;
    private String cupomFiscal;
    private BigDecimal preco;

    public LinhaAbastecimentoDTO() {
        this.dia = null;
        this.posto = "";
        this.quantidade = BigDecimal.ZERO;
        this.cupomFiscal = "";
        this.preco = BigDecimal.ZERO;
    }

    public String getCupomFiscal() {
        return cupomFiscal;
    }

    public Integer getDia(){
        return dia;
    }

    public String getPosto() {
        return posto;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public void setCupomFiscal(String cupom) {
        this.cupomFiscal = cupom;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }    
    
}
