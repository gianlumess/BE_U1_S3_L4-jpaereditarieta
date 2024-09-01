package gianlucamessina;


import com.github.javafaker.Faker;
import gianlucamessina.dao.EventoDao;
import gianlucamessina.dao.LocationDao;
import gianlucamessina.dao.PartitaDiCalcioDao;
import gianlucamessina.dao.PersonaDao;
import gianlucamessina.entities.Concerto;
import gianlucamessina.entities.Location;
import gianlucamessina.entities.PartitaDiCalcio;
import gianlucamessina.entities.Persona;
import gianlucamessina.enums.GenereConcerto;
import gianlucamessina.enums.PersonaSesso;
import gianlucamessina.enums.TipoEvento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BE_U1_S3_L4");

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();
        Random random = new Random();
        Faker faker = new Faker(Locale.ITALY);

        LocationDao ld = new LocationDao(em);
        EventoDao ed = new EventoDao(em);
        PersonaDao persDao = new PersonaDao(em);
        PartitaDiCalcioDao partitaDao = new PartitaDiCalcioDao(em);
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

        Supplier<Persona> randomPersonaSupplier = () -> {
            String nome = faker.name().firstName();
            String cognome = faker.name().lastName();
            String email = faker.internet().emailAddress();
            LocalDate dataNascita = LocalDate.now().minusYears(23);
            PersonaSesso[] sessoPersone = PersonaSesso.values();
            PersonaSesso sesso = sessoPersone[random.nextInt(sessoPersone.length)];
            return new Persona(nome, cognome, email, dataNascita, sesso);
        };
        for (int i = 0; i < 20; i++) {
            //persDao.save(randomPersonaSupplier.get());
        }

        Supplier<PartitaDiCalcio> randomPartiteCalcioSupplier = () -> {
            String titolo = "Partita di calcio";
            LocalDate dataEvento = LocalDate.now().plusWeeks(random.nextInt(1, 4));
            String descrizione = "una bella partita";
            TipoEvento[] tipiEventi = TipoEvento.values();
            TipoEvento tipoEv = tipiEventi[random.nextInt(tipiEventi.length)];
            int numPartecipanti = random.nextInt(1000);
            Location location = sanSiroFromDb;
            String squadraCasa = faker.esports().team();
            String squadraOspite = faker.esports().team();
            List<String> squadre = new ArrayList<>();
            squadre.add(squadraCasa);
            squadre.add(squadraOspite);
            String squadraVincente = squadre.get(random.nextInt(squadre.size()));
            int golCasa = random.nextInt(5);
            int golOspite = random.nextInt(5);
            return new PartitaDiCalcio(titolo, dataEvento, descrizione, tipoEv, numPartecipanti, location, squadraCasa, squadraOspite, squadraVincente, golCasa, golOspite);

        };

        for (int i = 0; i < 5; i++) {
            //partitaDao.save(randomPartiteCalcioSupplier.get());
        }

        System.out.println("LISTA CONCERTI STREAMING");
        ed.getConcertiInStreaming(true).forEach(System.out::println);
        System.out.println("LISTA CONCERTI ROCK");
        ed.getConcertiPerGenere(GenereConcerto.ROCK).forEach(System.out::println);

        System.out.println("LISTA PARITE VINTE IN CASA");
        partitaDao.getPartiteVinteInCasa().forEach(System.out::println);
        System.out.println("LISTA PARTITE VINTE IN TRASFERTA");
        partitaDao.getPartiteVinteInTrasferta().forEach(System.out::println);

        System.out.println("LISTA PARTITE PAREGGIATE");
        if (partitaDao.getPartitePareggiate().isEmpty()) {
            System.out.println("NON CI SONO PARITE TERMINATE IN PAREGGIO");
        } else {
            partitaDao.getPartitePareggiate().forEach(System.out::println);
        }

    }
}
