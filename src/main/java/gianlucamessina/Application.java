package gianlucamessina;


import gianlucamessina.dao.EventoDao;
import gianlucamessina.dao.LocationDao;
import gianlucamessina.entities.Concerto;
import gianlucamessina.entities.Location;
import gianlucamessina.enums.GenereConcerto;
import gianlucamessina.enums.TipoEvento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.UUID;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BE_U1_S3_L4");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        LocationDao ld = new LocationDao(em);
        EventoDao ed = new EventoDao(em);
        //*********************** SALVATAGGIO LOCATIONS ****************************************

        Location sanSiroFromDb = ld.getById(UUID.fromString("329e5baf-7a97-4bb3-b560-bcafdce52999"));
        Location circoMassimoFromDb = ld.getById(UUID.fromString("f2ba3829-e0bf-42f0-b78b-420e6c5d19c1"));

        Location sanSiro = new Location("San Siro", "Milano");
        //ld.save(sanSiro);

        Location circoMassimo = new Location("Circo Massimo", "Roma");
        //ld.save(circoMassimo);

        //*********************** SALVATAGGIO CONCERTI ****************************************
        Concerto coldPlay = new Concerto("Concerto dei Coldplay", LocalDate.now(), "seconda data del tour", TipoEvento.PUBBLICO, 10000, sanSiroFromDb, GenereConcerto.POP, true);
        //ed.save(coldPlay);

        Concerto kanyeWest = new Concerto("Concerto di Kanye West", LocalDate.now().plusYears(1), "Prima data del tour", TipoEvento.PUBBLICO, 16000, circoMassimoFromDb, GenereConcerto.ROCK, false);
        //ed.save(kanyeWest);

        System.out.println("LISTA CONCERTI STREAMING");
        ed.getConcertiInStreaming(true).forEach(System.out::println);
        System.out.println("LISTA CONCERTI ROCK");
        ed.getConcertiPerGenere(GenereConcerto.ROCK).forEach(System.out::println);
    }
}
