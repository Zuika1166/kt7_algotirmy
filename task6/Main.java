import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;
    private static final int MOD = 1_000_000_007;

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
        int target = scanner.nextInt();
        int maxEdges = scanner.nextInt();
        int[] from = new int[m];
        int[] to = new int[m];
        long[] weight = new long[m];
        for (int i = 0; i < m; i++) {
            from[i] = scanner.nextInt();
            to[i] = scanner.nextInt();
            weight[i] = scanner.nextInt();
        }

        long[] previousDistance = new long[n + 1];
        long[] currentDistance = new long[n + 1];
        int[] previousWays = new int[n + 1];
        int[] currentWays = new int[n + 1];
        java.util.Arrays.fill(previousDistance, INF);
        previousDistance[source] = 0;
        previousWays[source] = 1;

        long answer = source == target ? 0 : INF;
        long count = source == target ? 1 : 0;
        for (int steps = 1; steps <= maxEdges; steps++) {
            java.util.Arrays.fill(currentDistance, INF);
            java.util.Arrays.fill(currentWays, 0);
            for (int edge = 0; edge < m; edge++) {
                if (previousDistance[from[edge]] == INF) continue;
                long candidate = previousDistance[from[edge]] + weight[edge];
                if (candidate < currentDistance[to[edge]]) {
                    currentDistance[to[edge]] = candidate;
                    currentWays[to[edge]] = previousWays[from[edge]];
                } else if (candidate == currentDistance[to[edge]]) {
                    currentWays[to[edge]] = (int) ((currentWays[to[edge]] + (long) previousWays[from[edge]]) % MOD);
                }
            }

            if (currentDistance[target] < answer) {
                answer = currentDistance[target];
                count = currentWays[target];
            } else if (currentDistance[target] == answer && answer != INF) {
                count = (count + currentWays[target]) % MOD;
            }

            long[] distanceSwap = previousDistance;
            previousDistance = currentDistance;
            currentDistance = distanceSwap;
            int[] waysSwap = previousWays;
            previousWays = currentWays;
            currentWays = waysSwap;
        }

        if (answer == INF) System.out.println("IMPOSSIBLE");
        else System.out.println(answer + " " + count);
    }
}
