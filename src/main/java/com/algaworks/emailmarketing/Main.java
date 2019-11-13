package com.algaworks.emailmarketing;

import com.algaworks.emailmarketing.model.Contato;
import com.algaworks.emailmarketing.model.Envio;
import com.algaworks.emailmarketing.model.Mensagem;
import com.algaworks.emailmarketing.model.TemperaturaContato;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("Projetos-PU");
        EntityManager entityManager = factory.createEntityManager();

//        atualizacaoDeVariosRegistros(entityManager);
//        atualizacaoDeVariosRegistrosEmLote(entityManager);
//        atualizacaoDeVariosRegistrosEmLoteCriteria(entityManager);
//        remocaoDeVariosRegistrosEmLote(entityManager);

//        insercaoDeVariosRegistros(entityManager);
        insercaoDeVariosRegistrosEmLote(entityManager);

        entityManager.close();
        factory.close();
    }

    public static void insercaoDeVariosRegistrosEmLote(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        List<Mensagem> mensagens = entityManager
                .createQuery("select m from Mensagem m")
                .getResultList();

        List<Contato> contatos = entityManager
                .createQuery("select c from Contato c")
                .getResultList();

        int limiteInsercoesMemoria = 3;
        int contadorLimite = 1;

        for (Mensagem mensagem: mensagens) {
            for (Contato contato: contatos) {
                Envio envio = new Envio();
                envio.setMensagem(mensagem);
                envio.setContato(contato);
                envio.setDataEnvio(LocalDateTime.now());
                entityManager.persist(envio);

                if ((contadorLimite++) == limiteInsercoesMemoria) {
                    entityManager.flush();
                    entityManager.clear();

                    contadorLimite = 1;
                    System.out.println("----------------- flush");
                }
            }
        }

        entityManager.getTransaction().commit();
    }

    public static void insercaoDeVariosRegistros(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        List<Mensagem> mensagens = entityManager
                .createQuery("select m from Mensagem m")
                .getResultList();

        List<Contato> contatos = entityManager
                .createQuery("select c from Contato c")
                .getResultList();

        mensagens.forEach(mensagem -> {
            contatos.forEach(contato -> {
                Envio envio = new Envio();
                envio.setMensagem(mensagem);
                envio.setContato(contato);
                envio.setDataEnvio(LocalDateTime.now());
                entityManager.persist(envio);
            });
        });

        entityManager.getTransaction().commit();
    }

    public static void remocaoDeVariosRegistrosEmLote(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        entityManager
                .createQuery("delete from Contato c")
                .executeUpdate();

        entityManager.getTransaction().commit();
    }

    public static void atualizacaoDeVariosRegistrosEmLoteCriteria(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Contato> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Contato.class);
        Root<Contato> root = criteriaUpdate.from(Contato.class);

        criteriaUpdate.set(root.get("temperatura"), TemperaturaContato.FRIO);

        entityManager
                .createQuery(criteriaUpdate)
                .executeUpdate();

        entityManager.getTransaction().commit();
    }

    public static void atualizacaoDeVariosRegistrosEmLote(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        entityManager
                .createQuery("update Contato c set c.temperatura = :temperatura")
                .setParameter("temperatura", TemperaturaContato.FRIO)
                .executeUpdate();

        entityManager.getTransaction().commit();
    }

    public static void atualizacaoDeVariosRegistros(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        List<Contato> contatos = entityManager
                .createQuery("select c from Contato c")
                .getResultList();

        contatos.forEach(c -> c.setTemperatura(TemperaturaContato.FRIO));

        entityManager.getTransaction().commit();
    }


}
