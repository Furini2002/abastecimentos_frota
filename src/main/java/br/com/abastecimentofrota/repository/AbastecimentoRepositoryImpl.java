/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abastecimentofrota.repository;

import br.com.abastecimentofrota.DTO.LinhaFiltroAbastecimentoDTO;
import br.com.abastecimentofrota.model.Abastecimento;
import br.com.abastecimentofrota.model.NotaFiscal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administração01
 */
@Repository
public class AbastecimentoRepositoryImpl implements AbastecimentoRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Abastecimento> buscarComFiltro(LinhaFiltroAbastecimentoDTO filtro) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Abastecimento> cq = cb.createQuery(Abastecimento.class);
        Root<Abastecimento> root = cq.from(Abastecimento.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filtro.getData() != null) {
            Expression<Integer> mesExpr = cb.function("month", Integer.class, root.get("data"));
            int mes = filtro.getData().getMonthValue();
            predicates.add(cb.equal(mesExpr, mes));
        }

        if (filtro.getTipoCombustivel() != null) {
            predicates.add(cb.equal(root.get("veiculo").get("tipoCombustivel"), filtro.getTipoCombustivel()));
        }

        if (filtro.getFrota() != null && !filtro.getFrota().isEmpty()) {
            predicates.add(cb.equal(root.get("veiculo").<String>get("frota"), filtro.getFrota()));
        }

        Join<Abastecimento, NotaFiscal> notaJoin = root.join("notaFiscal", JoinType.LEFT);
        if (filtro.getNotaFiscal() != null && !filtro.getNotaFiscal().isEmpty()) {
            predicates.add(cb.equal(notaJoin.get("numero"), filtro.getNotaFiscal()));
        }

        if (filtro.getCupomFiscal() != null && !filtro.getCupomFiscal().isEmpty()) {
            predicates.add(cb.equal(root.get("cupomFiscal"), filtro.getCupomFiscal()));
        }

        if (filtro.getPrecoMin() != null) {
            predicates.add(cb.greaterThanOrEqualTo(
                    cb.prod(root.get("preco"), root.get("quantidade")),
                    filtro.getPrecoMin()
            ));
        }

        if (filtro.getPrecoMax() != null) {
            predicates.add(cb.lessThanOrEqualTo(
                    cb.prod(root.get("preco"), root.get("quantidade")),
                    filtro.getPrecoMax()
            ));
        }

        if (filtro.getQuantidadeMin() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.<BigDecimal>get("quantidade"), filtro.getQuantidadeMin()));
        }

        if (filtro.getQuantidadeMax() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.<BigDecimal>get("quantidade"), filtro.getQuantidadeMax()));
        }

        cq.where(cb.and(predicates.toArray(Predicate[]::new)));
        cq.orderBy(cb.asc(root.get("data")));

        return em.createQuery(cq).getResultList();
    }
}
