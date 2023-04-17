package hu.szakdolgozat.hajo;

import hu.szakdolgozat.Eszkoztar;
import hu.szakdolgozat.Jatekos;
import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.dao.SzintAdatDao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Hajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "hajo")
    Jatekos jatekos;

    @Embedded
    private Pozicio pozicio; // bal felső sarok pozíciója [3x3 hajó]
    
    private int szint;

    @ManyToOne
    @JoinColumn(name = "szint_id")
    private SzintAdat szintAdat;

    public Hajo(Pozicio pozicio, int szint) {
        this.pozicio = pozicio;
        this.szint = szint;
        this.szintAdat = new SzintAdatDao().getSzintAdat(szint);
    }

    public void szintlepes(Eszkoztar eszkoztar) {
        int[] minTargyak = szintAdat.getSzuksegesTargyak();
        if (minTargyak == null || !eszkoztar.targyCsokkentes(minTargyak)) {
            return;
        }

        this.szint++;
        szintBeallitas(szint);
    }

    public void halott() {
        this.szint = 0;
        szintBeallitas(szint);
    }

    private void szintBeallitas(int szint) {
        this.szintAdat = new SzintAdatDao().getSzintAdat(szint);
        this.jatekos.getEroforrasok().etelNoveles(szintAdat.getItalNoveles());
        this.jatekos.getEroforrasok().italNoveles(szintAdat.getItalNoveles());
    }

    public boolean viztisztito() {
        return szintAdat.isItalVisszatoltes();
    }

    public boolean tuzhely() {
        return szintAdat.isEtelVisszatoltes();

    }
}
