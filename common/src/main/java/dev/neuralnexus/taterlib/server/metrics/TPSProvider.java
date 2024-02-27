package dev.neuralnexus.taterlib.server.metrics;

/*
 * Represents a TPS provider.
 */
public interface TPSProvider {
    /**
     * Get the server's Current TPS.
     *
     * @return The server's Current TPS.
     */
    default double currentTPS() {
        return tpsLast1Min();
    }

    /**
     * Get the server's TPS for the last 5 seconds.
     *
     * @return The server's TPS for the last 5 seconds.
     */
    double tpsLast5Secs();

    /**
     * Get the server's TPS for the last 1 minute.
     *
     * @return The server's TPS for the last 1 minute.
     */
    double tpsLast1Min();

    /**
     * Get the server's TPS for the last 5 minutes.
     *
     * @return The server's TPS for the last 5 minutes.
     */
    double tpsLast5Min();

    /**
     * Get the server's TPS for the last 15 minutes.
     *
     * @return The server's TPS for the last 15 minutes.
     */
    double tpsLast15Min();
}
