import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.PriorityQueue;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

    private static final class State implements Comparable<State> {
        final int vertex;
        final int station;
        final long distance;

        State(int vertex, int station, long distance) {
            this.vertex = vertex;
            this.station = station;
            this.distance = distance;
        }

        public int compareTo(State other) {
            int byDistance = Long.compare(distance, other.distance);
            if (byDistance != 0) return byDistance;
            int byStation = Integer.compare(station, other.station);
            if (byStation != 0) return byStation;
            return Integer.compare(vertex, other.vertex);
        }
    }

    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int length;

        int nextInt() throws IOException {
            int c;
            do c = read(); while (c <= 32 && c != -1);
            int value = 0;
            while (c > 32 && c != -1) {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }

        private int read() throws IOException {
            if (position == length) {
                length = input.read(buffer);
                position = 0;
                if (length == -1) return -1;
            }
            return buffer[position++];
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();
        int[] stations = new int[k];
        for (int i = 0; i < k; i++) stations[i] = scanner.nextInt();

        int[] head = new int[n + 1];
        java.util.Arrays.fill(head, -1);
        int[] to = new int[2 * m];
        int[] next = new int[2 * m];
        long[] weight = new long[2 * m];
        int edgeCount = 0;
        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            long w = scanner.nextInt();
            to[edgeCount] = v;
            weight[edgeCount] = w;
            next[edgeCount] = head[u];
            head[u] = edgeCount++;
            to[edgeCount] = u;
            weight[edgeCount] = w;
            next[edgeCount] = head[v];
            head[v] = edgeCount++;
        }

        long[] distance = new long[n + 1];
        int[] nearest = new int[n + 1];
        java.util.Arrays.fill(distance, INF);
        PriorityQueue<State> queue = new PriorityQueue<>();
        for (int station : stations) {
            distance[station] = 0;
            nearest[station] = station;
            queue.add(new State(station, station, 0));
        }

        while (!queue.isEmpty()) {
            State state = queue.remove();
            if (state.distance != distance[state.vertex] || state.station != nearest[state.vertex]) continue;
            for (int edge = head[state.vertex]; edge != -1; edge = next[edge]) {
                int vertex = to[edge];
                long candidate = state.distance + weight[edge];
                if (candidate < distance[vertex] || (candidate == distance[vertex] && state.station < nearest[vertex])) {
                    distance[vertex] = candidate;
                    nearest[vertex] = state.station;
                    queue.add(new State(vertex, state.station, candidate));
                }
            }
        }

        StringBuilder output = new StringBuilder();
        for (int vertex = 1; vertex <= n; vertex++) {
            if (distance[vertex] == INF) output.append("-1 -1");
            else output.append(nearest[vertex]).append(' ').append(distance[vertex]);
            output.append('\n');
        }
        System.out.print(output);
    }
}
