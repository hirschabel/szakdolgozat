package hu.szakdolgozat.hajo;

import hu.szakdolgozat.jatekos.Eszkoztar;
import hu.szakdolgozat.jatekos.Jatekos;
import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.dao.SzintAdatDao;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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

    @Transient
    private final SzintAdatDao szintAdatDao;

    public Hajo(Pozicio pozicio, int szint, SzintAdatDao szintAdatDao) {
        this.pozicio = pozicio;
        this.szint = szint;
        this.szintAdatDao = szintAdatDao;
        this.szintAdat = szintAdatDao.getSzintAdat(szint);
    }

    public Hajo() {
        this.szintAdatDao = new SzintAdatDao();
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
        this.szintAdat = szintAdatDao.getSzintAdat(szint);
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
