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

        Location sanSiroFromDb = ld.getById(UUID.fromString("329e5baf-7a97-4bb3-b560-bcafdce52999"));

        Location sanSiro = new Location("San Siro", "Milano");
        Concerto coldPlay = new Concerto("Concerto dei Coldplay", LocalDate.now(), "seconda data del tour", TipoEvento.PUBBLICO, 10000, sanSiroFromDb, GenereConcerto.POP, true);

        //ld.save(sanSiro);
        //ed.save(coldPlay);

        ed.getConcertiInStreaming(true).forEach(System.out::println);
        ed.getConcertiPerGenere(GenereConcerto.POP).forEach(System.out::println);
    }
}
