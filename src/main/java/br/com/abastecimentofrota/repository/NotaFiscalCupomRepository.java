package br.com.abastecimentofrota.repository;

import br.com.abastecimentofrota.model.Abastecimento;
import br.com.abastecimentofrota.model.NotaFiscalCupom;
import br.com.abastecimentofrota.util.TipoCombustivel;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Administração01
 */
public interface NotaFiscalCupomRepository extends JpaRepository<NotaFiscalCupom, Long> {

    @Query("SELECT nfc FROM NotaFiscalCupom nfc "
            + "JOIN nfc.abastecimentoCadastrado a "
            + "JOIN nfc.notaFiscal nf "
            + "WHERE FUNCTION('MONTH', a.data) = :mes "
            + "AND FUNCTION('YEAR', a.data) = :ano "
            + "AND nf.tipoCombustivel = :tipoCombustivel")
    List<NotaFiscalCupom> findCuponsPagosPorMesAnoTipoCombustivel(
            @Param("mes") int mes,
            @Param("ano") int ano,
            @Param("tipoCombustivel") TipoCombustivel tipoCombustivel);

    @Query("SELECT nfc FROM NotaFiscalCupom nfc "
            + "LEFT JOIN nfc.abastecimentoCadastrado a "
            + "JOIN nfc.notaFiscal nf "
            + "WHERE a IS NULL "
            + "AND FUNCTION('MONTH', nfc.notaFiscal.data) = :mes "
            + "AND FUNCTION('YEAR', nfc.notaFiscal.data) = :ano "
            + "AND nf.tipoCombustivel = :tipoCombustivel")
    List<NotaFiscalCupom> findCuponsPendentesPorMesAnoTipoCombustivel(
            @Param("mes") int mes,
            @Param("ano") int ano,
            @Param("tipoCombustivel") TipoCombustivel tipoCombustivel);

}
