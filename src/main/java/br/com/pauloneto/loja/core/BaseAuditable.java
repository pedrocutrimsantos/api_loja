package br.com.pauloneto.loja.core;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public abstract class BaseAuditable {

    @CreatedBy
    @Column(name = "usuario_inclusao", length = 120, updatable = false)
    private String usuarioInclusao;

    @CreatedDate
    @Column(name = "data_inclusao", nullable = false, updatable = false)
    private LocalDateTime dataInclusao;

    @LastModifiedBy
    @Column(name = "usuario_alteracao", length = 120)
    private String usuarioAlteracao;

    @LastModifiedDate
    @Column(name = "data_alteracao")
    private LocalDateTime dataAlteracao;


    @Column(name = "usuario_exclusao", length = 120)
    private String usuarioExclusao;

    @Column(name = "data_exclusao")
    private LocalDateTime dataExclusao;

    @Column(name = "excluido", nullable = false)
    private boolean excluido = false;

    public void marcarExcluido(String usuario) {
        this.excluido = true;
        this.usuarioExclusao = usuario;
        this.dataExclusao = LocalDateTime.now();
    }
}
