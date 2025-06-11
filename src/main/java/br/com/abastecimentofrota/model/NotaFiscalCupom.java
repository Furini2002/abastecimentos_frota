package br.com.abastecimentofrota.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class NotaFiscalCupom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private NotaFiscal notaFiscal;

    @Column(nullable = false)
    private String numeroCupom;

    @ManyToOne(optional = true)
    private Abastecimento abastecimentoCadastrado;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public String getNumeroCupom() {
        return numeroCupom;
    }

    public void setNumeroCupom(String numeroCupom) {
        this.numeroCupom = numeroCupom;
    }

    public Abastecimento getAbastecimentoCadastrado() {
        return abastecimentoCadastrado;
    }

    public void setAbastecimentoCadastrado(Abastecimento abastecimentoCadastrado) {
        this.abastecimentoCadastrado = abastecimentoCadastrado;
    }
}
