package gianlucamessina.dao;

import gianlucamessina.entities.Concerto;
import gianlucamessina.entities.Evento;
import gianlucamessina.enums.GenereConcerto;
import gianlucamessina.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class EventoDao {
    private final EntityManager em;

    public EventoDao(EntityManager em) {
        this.em = em;
    }

    public void save(Evento evento) {
        //NEL PROCESSO DI SCRITTURA BISOGNA UTILIZZARE UNA TRANSAZIONE PER ASSICURARSI CHE AVVENGA IN SICUREZZA

        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        //2.avviamo la transazione
        transaction.begin();

        //3.aggiungo l'evento al persistence context
        em.persist(evento);

        //4.concludiamo la transazione salvando l'evento nel DB
        transaction.commit();

        System.out.println("L'evento " + evento.getTitolo() + " è stato salvato con successo!");
    }

    public Evento getById(UUID eventoId) {
        Evento found = em.find(Evento.class, eventoId);
        if (found == null) throw new NotFoundException(eventoId);
        return found;
    }

    public void deleteById(UUID eventoId) {
        Evento found = this.getById(eventoId);

        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        em.remove(found);

        transaction.commit();

        System.out.println("L'evento con id: " + eventoId + " è stato rimosso con successo!");
    }

    public List<Concerto> getConcertiInStreaming(Boolean bool) {
        TypedQuery<Concerto> query = em.createQuery("SELECT c FROM Concerto c WHERE c.streaming = :bool", Concerto.class);

        query.setParameter("bool", bool);
        return query.getResultList();
    }

    public List<Concerto> getConcertiPerGenere(GenereConcerto genere) {
        TypedQuery<Concerto> query = em.createQuery("SELECT c FROM Concerto c WHERE c.genere = :genere", Concerto.class);

        query.setParameter("genere", genere);
        return query.getResultList();
    }
}
