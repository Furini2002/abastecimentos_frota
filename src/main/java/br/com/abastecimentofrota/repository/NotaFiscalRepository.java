package br.com.abastecimentofrota.repository;

import br.com.abastecimentofrota.model.NotaFiscal;
import br.com.abastecimentofrota.model.Posto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Administração01
 */
public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long> {

    @Query("SELECT n FROM NotaFiscal n LEFT JOIN FETCH n.cupons WHERE n.id = :id")
    Optional<NotaFiscal> findByIdWithCupons(@Param("id") Long id);
    
    @Query("SELECT n FROM NotaFiscal n WHERE n.numero = :numero")
    Optional<NotaFiscal> findWithNumber (@Param("numero") String id);
}
