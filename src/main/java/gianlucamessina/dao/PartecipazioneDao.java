package gianlucamessina.dao;

import gianlucamessina.entities.Location;
import gianlucamessina.entities.Partecipazione;
import gianlucamessina.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class PartecipazioneDao {
    private final EntityManager em;

    public PartecipazioneDao(EntityManager em) {
        this.em = em;
    }

    public void save(Partecipazione partecipazione) {
        //NEL PROCESSO DI SCRITTURA BISOGNA UTILIZZARE UNA TRANSAZIONE PER ASSICURARSI CHE AVVENGA IN SICUREZZA

        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        //2.avviamo la transazione
        transaction.begin();

        //3.aggiungo l'evento al persistence context
        em.persist(partecipazione);

        //4.concludiamo la transazione salvando l'evento nel DB
        transaction.commit();

        System.out.println("La partecipazione a nome : " + partecipazione.getPersona().getCognome() + " è stata salvata con successo!");
    }

    public Location getById(UUID partecipazioneId) {
        Location found = em.find(Location.class, partecipazioneId);
        if (found == null) throw new NotFoundException(partecipazioneId);
        return found;
    }
}
