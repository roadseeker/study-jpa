package com.innotree.bcs.bp.study.jpa.demo.order.repository;

import com.innotree.bcs.bp.study.jpa.demo.member.domain.Member;
import com.innotree.bcs.bp.study.jpa.demo.order.domain.Order;
import com.innotree.bcs.bp.study.jpa.demo.order.domain.OrderSearch;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long orderId){
        return em.find(Order.class, orderId);
    }

    public List<Order> findByCriteria(OrderSearch orderSearch) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> o = criteriaQuery.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();
        //주문상태 검색
        if(orderSearch.getOrderStatus() != null) {
            Predicate status = criteriaBuilder.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = criteriaBuilder.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }
        criteriaQuery.where(criteriaBuilder.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(criteriaQuery).setMaxResults(1000);
        return query.getResultList();

    }
}
