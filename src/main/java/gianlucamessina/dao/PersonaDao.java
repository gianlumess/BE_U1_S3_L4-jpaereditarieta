package gianlucamessina.dao;

import gianlucamessina.entities.Persona;
import gianlucamessina.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class PersonaDao {
    private final EntityManager em;

    public PersonaDao(EntityManager em) {
        this.em = em;
    }

    public void save(Persona persona) {
        //NEL PROCESSO DI SCRITTURA BISOGNA UTILIZZARE UNA TRANSAZIONE PER ASSICURARSI CHE AVVENGA IN SICUREZZA

        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        //2.avviamo la transazione
        transaction.begin();

        //3.aggiungo l'evento al persistence context
        em.persist(persona);

        //4.concludiamo la transazione salvando l'evento nel DB
        transaction.commit();

        System.out.println("La persona: " + persona.getCognome() + " è stato salvato con successo!");
    }

    public Persona getById(UUID personaId) {
        Persona found = em.find(Persona.class, personaId);
        if (found == null) throw new NotFoundException(personaId);
        return found;
    }
}
