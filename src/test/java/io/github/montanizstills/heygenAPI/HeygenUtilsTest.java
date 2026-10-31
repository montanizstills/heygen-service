package io.github.montanizstills.heygenAPI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class HeygenUtilsTest {
    @TempDir
    Path dir;

    private String read(String contents) throws Exception {
        Path env = dir.resolve(".env");
        Files.writeString(env, contents);
        return HeygenUtils.readDotEnv(env, "HEYGEN_API_KEY");
    }

    @Test
    void readsPlainValue() throws Exception {
        assertEquals("sk_test_123", read("HEYGEN_API_KEY=sk_test_123\n"));
    }

    @Test
    void handlesExportQuotesCommentsAndOtherKeys() throws Exception {
        assertEquals("sk_test_123", read("# heygen\nOTHER=x\nexport HEYGEN_API_KEY = \"sk_test_123\"\n"));
        assertEquals("sk_test_123", read("HEYGEN_API_KEY='sk_test_123'"));
        assertNull(read("# HEYGEN_API_KEY=commented_out\n"));
    }

    @Test
    void lastAssignmentWins() throws Exception {
        assertEquals("new", read("HEYGEN_API_KEY=old\nHEYGEN_API_KEY=new\n"));
    }

    @Test
    void emptyOrMissingIsNull() throws Exception {
        assertNull(read("HEYGEN_API_KEY=\n"));
        assertNull(HeygenUtils.readDotEnv(dir.resolve("missing.env"), "HEYGEN_API_KEY"));
    }

    @Test
    void doesNotMatchPrefixedNames() throws Exception {
        assertNull(read("MY_HEYGEN_API_KEY=nope\n"));
    }
}
