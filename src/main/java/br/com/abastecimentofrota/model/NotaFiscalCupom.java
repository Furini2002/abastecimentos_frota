package br.com.abastecimentofrota.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Administração01
 */
@Table(name = "notaFiscal_cupom")
@Data  
@NoArgsConstructor @AllArgsConstructor
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
    
    
}
