package br.com.abastecimentofrota.repository;

import br.com.abastecimentofrota.model.Abastecimento;
import br.com.abastecimentofrota.util.TipoCombustivel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Administração01
 */
public interface AbastecimentoRepository extends JpaRepository<Abastecimento, Long>, AbastecimentoRepositoryCustom  {

    Optional<Abastecimento> findByCupomFiscal(String cupomFiscal);

    @Query("SELECT a FROM Abastecimento a "
            + "WHERE a.notaFiscal IS NULL "
            + "AND FUNCTION('MONTH', a.data) = :mes "
            + "AND FUNCTION('YEAR', a.data) = :ano "
            + "AND a.veiculo.tipoCombustivel = :tipoCombustivel")
    List<Abastecimento> findAbastecimentosSemNotaPorMesAnoTipoCombustivel(
            @Param("mes") int mes,
            @Param("ano") int ano,
            @Param("tipoCombustivel") TipoCombustivel tipoCombustivel);
}
