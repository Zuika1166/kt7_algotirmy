import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.PriorityQueue;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

    private static final class State implements Comparable<State> {
        final int vertex;
        final long distance;

        State(int vertex, long distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public int compareTo(State other) {
            return Long.compare(distance, other.distance);
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
        int source = scanner.nextInt();
        int[] head = new int[n + 1];
        java.util.Arrays.fill(head, -1);
        int[] to = new int[m];
        int[] next = new int[m];
        long[] weight = new long[m];

        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt();
            to[i] = scanner.nextInt();
            weight[i] = scanner.nextInt();
            next[i] = head[u];
            head[u] = i;
        }

        long[] distance = new long[n + 1];
        java.util.Arrays.fill(distance, INF);
        distance[source] = 0;
        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(new State(source, 0));

        while (!queue.isEmpty()) {
            State state = queue.remove();
            if (state.distance != distance[state.vertex]) continue;
            for (int edge = head[state.vertex]; edge != -1; edge = next[edge]) {
                int vertex = to[edge];
                long candidate = state.distance + weight[edge];
                if (candidate < distance[vertex]) {
                    distance[vertex] = candidate;
                    queue.add(new State(vertex, candidate));
                }
            }
        }

        StringBuilder output = new StringBuilder();
        for (int vertex = 1; vertex <= n; vertex++) {
            if (vertex > 1) output.append(' ');
            if (distance[vertex] == INF) output.append("INF");
            else output.append(distance[vertex]);
        }
        System.out.println(output);
    }
}
