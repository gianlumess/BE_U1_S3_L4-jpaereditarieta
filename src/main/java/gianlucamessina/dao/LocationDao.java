package gianlucamessina.dao;

import gianlucamessina.entities.Location;
import gianlucamessina.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class LocationDao {
    private final EntityManager em;

    public LocationDao(EntityManager em) {
        this.em = em;
    }

    public void save(Location location) {
        //NEL PROCESSO DI SCRITTURA BISOGNA UTILIZZARE UNA TRANSAZIONE PER ASSICURARSI CHE AVVENGA IN SICUREZZA

        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        //2.avviamo la transazione
        transaction.begin();

        //3.aggiungo l'evento al persistence context
        em.persist(location);

        //4.concludiamo la transazione salvando l'evento nel DB
        transaction.commit();

        System.out.println("La location: " + location.getNome() + " è stata salvata con successo!");
    }

    public Location getById(UUID locationId) {
        Location found = em.find(Location.class, locationId);
        if (found == null) throw new NotFoundException(locationId);
        return found;
    }
}
