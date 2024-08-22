package gianlucamessina.entities;

import gianlucamessina.enums.GenereConcerto;
import gianlucamessina.enums.TipoEvento;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("concerto")
@Table(name = "concerti")
public class Concerto extends Evento {
    @Column(name = "genere")
    @Enumerated(EnumType.STRING)
    private GenereConcerto genere;
    @Column(name = "in_streaming")
    private boolean streaming;

    public Concerto() {
    }

    public Concerto(String titolo, LocalDate dataEvento, String descrizione, TipoEvento tipoEvento, Integer numeroMassimoPartecipanti, Location locationId, GenereConcerto genere, boolean streaming) {
        super(titolo, dataEvento, descrizione, tipoEvento, numeroMassimoPartecipanti, locationId);
        this.genere = genere;
        this.streaming = streaming;
    }

    public GenereConcerto getGenere() {
        return genere;
    }

    public void setGenere(GenereConcerto genere) {
        this.genere = genere;
    }

    public boolean isStreaming() {
        return streaming;
    }

    public void setStreaming(boolean streaming) {
        this.streaming = streaming;
    }

    @Override
    public String toString() {
        return "Concerto{" +
                "genere=" + genere +
                ", streaming=" + streaming +
                '}';
    }
}
