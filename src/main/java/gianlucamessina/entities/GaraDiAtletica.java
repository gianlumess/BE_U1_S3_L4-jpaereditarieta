package gianlucamessina.entities;

import gianlucamessina.enums.TipoEvento;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "gare_di_atletica")
public class GaraDiAtletica extends Evento {
    @ManyToMany
    @JoinTable(name = "gara_persona", joinColumns = @JoinColumn(name = "gara_id"), inverseJoinColumns = @JoinColumn(name = "atleta_id"))
    private Set<Persona> atleti;

    @ManyToOne
    @JoinColumn(name = "vincitore")
    private Persona vincitore;

    public GaraDiAtletica() {
    }

    public GaraDiAtletica(String titolo, LocalDate dataEvento, String descrizione, TipoEvento tipoEvento, Integer numeroMassimoPartecipanti, Location locationId, Set<Persona> atleti, Persona vincitore) {
        super(titolo, dataEvento, descrizione, tipoEvento, numeroMassimoPartecipanti, locationId);
        this.atleti = atleti;
        this.vincitore = vincitore;
    }

    public Set<Persona> getAtleti() {
        return atleti;
    }

    public void setAtleti(Set<Persona> atleti) {
        this.atleti = atleti;
    }

    public Persona getVincitore() {
        return vincitore;
    }

    public void setVincitore(Persona vincitore) {
        this.vincitore = vincitore;
    }

    @Override
    public String toString() {
        return "GaraDiAtletica{" +
                "atleti=" + atleti +
                ", vincitore=" + vincitore +
                '}';
    }
}
