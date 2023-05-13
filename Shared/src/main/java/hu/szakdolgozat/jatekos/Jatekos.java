package hu.szakdolgozat.jatekos;

import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.util.TerkepKodokUtil;
import hu.szakdolgozat.capa.Capa;
import hu.szakdolgozat.dao.SzintAdatDao;
import hu.szakdolgozat.hajo.Hajo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Jatekos {
    @Id
    private String name;

    @Embedded
    private Pozicio pozicio;

    @Embedded
    private Eszkoztar eszkoztar;

    @OneToOne(cascade = CascadeType.ALL)
    private Hajo hajo;

    @Embedded
    private Eroforras eroforrasok;

    @Transient
    Capa capa;

    public Jatekos(String name, Pozicio pozicio) {
        this.name = name;
        this.pozicio = pozicio;
        this.eszkoztar = new Eszkoztar(0, 0, 0);
        this.hajo = new Hajo(new Pozicio(pozicio.getSorPozicio() - 1, pozicio.getOszlopPozicio() - 1), 0, new SzintAdatDao());
        this.eroforrasok = new Eroforras(100, 100, 100);
    }

    public String toString() {
        return this.name + " - " + this.getPozicio();
    }

    public boolean tudVisszatolteni() {
        return this.hajo.getSzintAdat().tudVisszatolteni();
    }

    public void visszatolt() {
        if (this.hajo.getSzintAdat().isEtelVisszatoltes()) {
            this.eroforrasok.etelVisszatoltes();
        }
        if (this.hajo.getSzintAdat().isItalVisszatoltes()) {
            this.eroforrasok.italVisszatoltes();
        }
    }

    public void pozicioRandomizalas() {
        this.pozicio.randomizalas();
        this.hajo.getPozicio().setPozicio(pozicio.getSorPozicio(), pozicio.getOszlopPozicio());
    }

    public boolean halott() {
        if (!eroforrasok.halott()) {
            return false;
        }
        this.hajo.halott();
        this.eroforrasok.setMax(100, 100, 100);
        this.eszkoztar.urites();

        // Random pozícióban újraéledés
        pozicioRandomizalas();
        this.capa.getPozicio().randomizalas();
        return true;
    }

    public void eletVisszatolt() {
        this.eroforrasok.eletVisszatoltes();
    }

    public void targyGeneralas() {
        boolean[] targyGeneralas = hajo.getSzintAdat().getTargyGeneralas();
        if (targyGeneralas[0]) {
            eszkoztar.addTargy(TerkepKodokUtil.LEVEL);
        }
        if (targyGeneralas[1]) {
            eszkoztar.addTargy(TerkepKodokUtil.BOT);
        }
        if (targyGeneralas[2]) {
            eszkoztar.addTargy(TerkepKodokUtil.UVEG);
        }
    }
}
