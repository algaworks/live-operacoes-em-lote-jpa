package com.algaworks.emailmarketing.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "envio")
public class Envio {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    @ManyToOne
    @JoinColumn(name = "contato_id")
    private Contato contato;

    @ManyToOne
    @JoinColumn(name = "mensagem_id")
    private Mensagem mensagem;

    private Boolean abriu;

    private Boolean clicou;
}
