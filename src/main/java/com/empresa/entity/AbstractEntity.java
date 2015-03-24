package com.empresa.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * Created by root on 3/24/15.
 */
@MappedSuperclass
public abstract class AbstractEntity implements Persistable<Long> {

    @Column(nullable = false, updatable = false)
    private Date dataCriacao;

    @Column(nullable = false)
    private Date dataAlteracao;

    @Column
    private Date dataRemocao;

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(final Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(final Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Date getDataRemocao() {
        return dataRemocao;
    }

    public void setDataRemocao(Date dataRemocao) {
        this.dataRemocao = dataRemocao;
    }

    @PrePersist
    protected void prePersist() {
        dataCriacao = dataAlteracao = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        dataAlteracao = new Date();
    }

    @Override
    public abstract Long getId();

    @Override
    public boolean isNew() {
        return getId() == null;
    }

    public boolean isDeleted() {
        return getDataRemocao() != null;
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.getClass().getName(), getId());
    }

    public static Specification<AbstractEntity> isNotDeleted() {
        return new Specification<AbstractEntity>() {

            @Override
            public Predicate toPredicate(Root<AbstractEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
                return builder.isNull(root.get("dataRemocao"));
            }
        };
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof AbstractEntity)) {
            return false;
        }

        AbstractEntity rhs = (AbstractEntity) obj;
        return new EqualsBuilder().append(getId(), rhs.getId()).isEquals();
    }
}