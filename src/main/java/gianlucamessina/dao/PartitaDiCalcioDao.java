package gianlucamessina.dao;

import gianlucamessina.entities.PartitaDiCalcio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class PartitaDiCalcioDao {
    private final EntityManager em;

    public PartitaDiCalcioDao(EntityManager em) {
        this.em = em;
    }

    public void save(PartitaDiCalcio partita) {
        //NEL PROCESSO DI SCRITTURA BISOGNA UTILIZZARE UNA TRANSAZIONE PER ASSICURARSI CHE AVVENGA IN SICUREZZA

        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        //2.avviamo la transazione
        transaction.begin();

        //3.aggiungo l'evento al persistence context
        em.persist(partita);

        //4.concludiamo la transazione salvando l'evento nel DB
        transaction.commit();

        System.out.println("La partita tra : " + partita.getSquadraDiCasa() + " e " + partita.getSquadraOspite() + " è stata salvata con successo!");
    }


}
