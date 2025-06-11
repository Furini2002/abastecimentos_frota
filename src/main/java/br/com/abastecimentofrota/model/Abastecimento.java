package br.com.abastecimentofrota.model;

import br.com.abastecimentofrota.util.TipoRegistro;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Administração01
 */
@Entity
@Table(name = "abastecimentos")
@Data  
@NoArgsConstructor @AllArgsConstructor
@Getter
@Setter
public class Abastecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_veiculo", nullable = false)
    @ToString.Exclude 
    private Veiculo veiculo;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false, precision = 8, scale = 4)
    private BigDecimal quantidade;
    
    @Column(nullable = false)
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "id_posto", nullable = false)
    @ToString.Exclude
    private Posto posto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRegistro tipoRegistro;
    
    @Column(nullable = false)
    private String cupomFiscal;
    
    @ManyToOne
    @JoinColumn(name = "nota_fiscal_id")
    private NotaFiscal notaFiscal;       
     
}
