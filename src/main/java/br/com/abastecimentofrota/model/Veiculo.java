package br.com.abastecimentofrota.model;

import br.com.abastecimentofrota.util.TipoCombustivel;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "veiculos")
@Data  
@NoArgsConstructor @AllArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private String frota;

    @Column(nullable = false)
    private String modelo;   
   
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCombustivel tipoCombustivel;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // Evita loop no toString()
    private List<Abastecimento> abastecimentos = new ArrayList<>();
}
