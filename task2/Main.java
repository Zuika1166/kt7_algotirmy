import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int length;

        int nextInt() throws IOException {
            int c;
            do c = read(); while (c <= 32 && c != -1);
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int value = 0;
            while (c > 32 && c != -1) {
                value = value * 10 + c - '0';
                c = read();
            }
            return value * sign;
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
        int[] from = new int[m];
        int[] to = new int[m];
        long[] weight = new long[m];
        for (int i = 0; i < m; i++) {
            from[i] = scanner.nextInt();
            to[i] = scanner.nextInt();
            weight[i] = scanner.nextInt();
        }

        long[] distance = new long[n + 1];
        java.util.Arrays.fill(distance, INF);
        distance[source] = 0;
        for (int iteration = 1; iteration < n; iteration++) {
            boolean changed = false;
            for (int edge = 0; edge < m; edge++) {
                if (distance[from[edge]] == INF) continue;
                long candidate = distance[from[edge]] + weight[edge];
                if (candidate < distance[to[edge]]) {
                    distance[to[edge]] = candidate;
                    changed = true;
                }
            }
            if (!changed) break;
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
