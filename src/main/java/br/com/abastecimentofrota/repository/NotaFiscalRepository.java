package br.com.abastecimentofrota.repository;

import br.com.abastecimentofrota.model.NotaFiscal;
import br.com.abastecimentofrota.model.Posto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Administração01
 */
public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long>{
    
}
