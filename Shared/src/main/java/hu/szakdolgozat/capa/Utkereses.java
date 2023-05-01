package hu.szakdolgozat.capa;

import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.TerkepKodok;

import java.security.SecureRandom;
import java.util.*;

public class Utkereses {
    private final long[][] map;
    private final int width;
    private final int height;
    private static final Random random = new SecureRandom();

    public Utkereses(long[][] map) {
//        this.random = new SecureRandom();
        this.map = map;
        this.width = map.length;
        this.height = map[0].length;
    }

    public Pozicio findPath(Pozicio start, Pozicio end) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<Node> closedList = new HashSet<>();

        Node startNode = new Node(start, null, 0, heuristic(start, end));
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.pozicio.isRajta(end)) {
                if (current.parent == null) {
                    return current.pozicio;
                }

                while (!current.parent.pozicio.isRajta(start)) {
                    current = current.parent;
                }
                return current.pozicio;
            }

            closedList.add(current);

            for (Node neighbor : getNeighbors(current, end)) {
                if (closedList.contains(neighbor)) {
                    continue;
                }

                int tentativeGScore = current.gScore + 1;

                if (!openList.contains(neighbor) || tentativeGScore < neighbor.gScore) {
                    neighbor.parent = current;
                    neighbor.gScore = tentativeGScore;
                    neighbor.fScore = neighbor.gScore + heuristic(neighbor.pozicio, end);

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }
        return new Pozicio(0, 0).randomizalas();
    }

    public Pozicio randomPoz(Pozicio start, Pozicio end) {
        List<Node> neighbors = getNeighbors(new Node(start, null, 0, heuristic(start, end)), end);
        if (neighbors.isEmpty()) {
            return start;
        }
        return neighbors.get(random.nextInt((neighbors.size()))).pozicio;
    }

    private List<Node> getNeighbors(Node node, Pozicio end) {
        List<Node> neighbors = new ArrayList<>();

        int x = node.pozicio.getSorPozicio();
        int y = node.pozicio.getOszlopPozicio();

        if (isValid(x - 1, y)) {
            Pozicio pozicio = new Pozicio(x - 1, y);
            Node neighbor = new Node(pozicio, node, node.gScore + 1, heuristic(pozicio, end));
            neighbors.add(neighbor);
        }

        if (isValid(x + 1, y)) {
            Pozicio pozicio = new Pozicio(x + 1, y);
            Node neighbor = new Node(pozicio, node, node.gScore + 1, heuristic(pozicio, end));
            neighbors.add(neighbor);
        }

        if (isValid(x, y - 1)) {
            Pozicio pozicio = new Pozicio(x, y - 1);
            Node neighbor = new Node(pozicio, node, node.gScore + 1, heuristic(pozicio, end));
            neighbors.add(neighbor);
        }

        if (isValid(x, y + 1)) {
            Pozicio pozicio = new Pozicio(x, y + 1);
            Node neighbor = new Node(pozicio, node, node.gScore + 1, heuristic(pozicio, end));
            neighbors.add(neighbor);
        }

        return neighbors;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height && (map[x][y] & TerkepKodok.HAJO) == 0;
    }

    private int heuristic(Pozicio a, Pozicio b) {
        return (int)Math.sqrt(Math.pow(b.getSorPozicio() - a.getSorPozicio(), 2) + Math.pow(b.getOszlopPozicio() - a.getOszlopPozicio(), 2));
    }

    private static class Node implements Comparable<Node> {
        public Pozicio pozicio;
        public Node parent;
        public int gScore;
        public int fScore;

        public Node(Pozicio pozicio, Node parent, int gScore, int fScore) {
            this.pozicio = pozicio;
            this.parent = parent;
            this.gScore = gScore;
            this.fScore = fScore;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(fScore, o.fScore);
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
