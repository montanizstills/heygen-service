package io.github.montanizstills.heygenAPI;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class HeygenUtils {

    private static final String API_KEY = "HEYGEN_API_KEY";
    private static final Path DOT_ENV = Path.of(".env");

    private final String heygen_api_key;

    // The environment variable wins; otherwise fall back to .env in the working directory (the project root).
    public HeygenUtils() {
        String key = System.getenv(API_KEY);
        this.heygen_api_key = key != null && !key.isBlank() ? key : readDotEnv(DOT_ENV, API_KEY);
    }

    public String getHeygen_api_key() {
        return this.heygen_api_key;
    }

    // Minimal .env parser: NAME=value lines, optional "export ", optional surrounding quotes, # comments.
    static String readDotEnv(Path file, String name) {
        if (!Files.isRegularFile(file)) {
            return null;
        }
        try {
            String value = null;
            for (String line : Files.readAllLines(file)) {
                line = line.strip();
                if (line.startsWith("export ")) line = line.substring("export ".length()).strip();
                int eq = line.indexOf('=');
                if (line.startsWith("#") || eq < 0 || !line.substring(0, eq).strip().equals(name)) continue;
                value = line.substring(eq + 1).strip();
                if (value.length() >= 2 && (value.charAt(0) == '"' || value.charAt(0) == '\'') && value.charAt(value.length() - 1) == value.charAt(0)) {
                    value = value.substring(1, value.length() - 1);
                }
            }
            return value == null || value.isEmpty() ? null : value;
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read " + file.toAbsolutePath(), e);
        }
    }

}
