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
@Table(name = "mensagem")
public class Mensagem {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_programado")
    private LocalDateTime dataProgramado;

    private String assunto;

    private String conteudo;


    @Column(name = "foi_enviado")
    private Boolean foiEnviado;
}
