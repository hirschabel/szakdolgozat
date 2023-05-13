package hu.szakdolgozat.capa;

import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.util.TerkepKodokUtil;

import java.security.SecureRandom;
import java.util.*;

public class Utkereses {
    private final long[][] terkep;
    private final int szelesseg;
    private final int magassag;
    private static final Random random = new SecureRandom();

    public Utkereses(long[][] terkep) {
        this.terkep = terkep;
        this.szelesseg = terkep.length;
        this.magassag = terkep[0].length;
    }

    public Pozicio utKereses(Pozicio start, Pozicio end) {
        PriorityQueue<Node> nyitottLista = new PriorityQueue<>();
        Set<Node> zartLista = new HashSet<>();

        Node startNode = new Node(start, null, 0, heurisztika(start, end));
        nyitottLista.add(startNode);

        while (!nyitottLista.isEmpty()) {
            Node jelenlegi = nyitottLista.poll();

            if (jelenlegi.pozicio.isRajta(end)) {
                if (jelenlegi.szulo == null) {
                    return jelenlegi.pozicio;
                }

                while (!jelenlegi.szulo.pozicio.isRajta(start)) {
                    jelenlegi = jelenlegi.szulo;
                }
                return jelenlegi.pozicio;
            }

            zartLista.add(jelenlegi);

            for (Node neighbor : szomszedok(jelenlegi, end)) {
                if (zartLista.contains(neighbor)) {
                    continue;
                }

                int kiserletiKoltseg = jelenlegi.koltseg + 1;

                if (!nyitottLista.contains(neighbor) || kiserletiKoltseg < neighbor.koltseg) {
                    neighbor.szulo = jelenlegi;
                    neighbor.koltseg = kiserletiKoltseg;
                    neighbor.heurisztika = neighbor.koltseg + heurisztika(neighbor.pozicio, end);

                    if (!nyitottLista.contains(neighbor)) {
                        nyitottLista.add(neighbor);
                    }
                }
            }
        }
        return new Pozicio(0, 0).randomizalas();
    }

    public Pozicio randomPoz(Pozicio start, Pozicio end) {
        List<Node> neighbors = szomszedok(new Node(start, null, 0, heurisztika(start, end)), end);
        if (neighbors.isEmpty()) {
            return start;
        }
        return neighbors.get(random.nextInt((neighbors.size()))).pozicio;
    }

    private List<Node> szomszedok(Node node, Pozicio end) {
        List<Node> szomszedok = new ArrayList<>();

        int sor = node.pozicio.getSorPozicio();
        int oszlop = node.pozicio.getOszlopPozicio();

        if (ervenyes(sor - 1, oszlop)) {
            Pozicio pozicio = new Pozicio(sor - 1, oszlop);
            Node szomszed = new Node(pozicio, node, node.koltseg + 1, heurisztika(pozicio, end));
            szomszedok.add(szomszed);
        }

        if (ervenyes(sor + 1, oszlop)) {
            Pozicio pozicio = new Pozicio(sor + 1, oszlop);
            Node szomszed = new Node(pozicio, node, node.koltseg + 1, heurisztika(pozicio, end));
            szomszedok.add(szomszed);
        }

        if (ervenyes(sor, oszlop - 1)) {
            Pozicio pozicio = new Pozicio(sor, oszlop - 1);
            Node szomszed = new Node(pozicio, node, node.koltseg + 1, heurisztika(pozicio, end));
            szomszedok.add(szomszed);
        }

        if (ervenyes(sor, oszlop + 1)) {
            Pozicio pozicio = new Pozicio(sor, oszlop + 1);
            Node szomszed = new Node(pozicio, node, node.koltseg + 1, heurisztika(pozicio, end));
            szomszedok.add(szomszed);
        }

        return szomszedok;
    }

    private boolean ervenyes(int sor, int oszlop) {
        return sor >= 0 && sor < szelesseg && oszlop >= 0 && oszlop < magassag && (terkep[sor][oszlop] & TerkepKodokUtil.HAJO) == 0;
    }

    private int heurisztika(Pozicio a, Pozicio b) {
        return Math.abs(a.getSorPozicio() - b.getSorPozicio()) + Math.abs(a.getOszlopPozicio() - b.getOszlopPozicio());
    }


    private static class Node implements Comparable<Node> {
        public Pozicio pozicio;
        public Node szulo;
        public int koltseg;
        public int heurisztika;

        public Node(Pozicio pozicio, Node szulo, int koltseg, int heurisztika) {
            this.pozicio = pozicio;
            this.szulo = szulo;
            this.koltseg = koltseg;
            this.heurisztika = heurisztika;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(heurisztika, o.heurisztika);
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 89 * hash + pozicio.getSorPozicio();
            hash = 89 * hash + pozicio.getOszlopPozicio();
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Pozicio masik = ((Node)obj).pozicio;
            return pozicio.getOszlopPozicio() == masik.getOszlopPozicio() && pozicio.getSorPozicio() == masik.getSorPozicio();
        }
    }
}
