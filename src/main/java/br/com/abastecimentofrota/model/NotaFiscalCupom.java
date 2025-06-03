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

/**
 *
 * @author Administração01
 */
@Data  
@Entity
@NoArgsConstructor @AllArgsConstructor
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
    
    
}
