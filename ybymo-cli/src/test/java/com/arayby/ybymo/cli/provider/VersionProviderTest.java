package com.arayby.ybymo.cli.provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VersionProviderTest {

    private VersionProvider provider;

    @BeforeEach
    void setUp() {
        provider = new VersionProvider();
    }

    @Test
    void getVersion_whenCalled_returnsNonEmptyArray() {
        String[] version = provider.getVersion();

        assertThat(version).isNotEmpty();
    }

    @Test
    void getVersion_whenCalled_returnsSingleElementArray() {
        String[] version = provider.getVersion();

        assertThat(version).hasSize(1);
    }

    @Test
    void getVersion_whenCalled_returnsStringWithYbymoPrefix() {
        String[] version = provider.getVersion();

        assertThat(version[0]).startsWith("ybymo ");
    }

    @Test
    void getVersion_whenCalled_includesVersionOrUnknown() {
        String[] version = provider.getVersion();
        String versionString = version[0];

        assertThat(versionString).satisfies(v -> {
            String[] parts = v.split(" ", 2);
            assertThat(parts).hasSize(2);
            assertThat(parts[1]).isNotEmpty().isNotBlank();
        });
    }

    @Test
    void getVersion_whenCalled_hasNoDoubleSpaces() {
        String[] version = provider.getVersion();

        assertThat(version[0]).doesNotContain("  ");
    }

    @Test
    void getVersion_whenCalled_hasVersionNotEmptyAfterPrefix() {
        String[] version = provider.getVersion();
        String versionString = version[0];

        assertThat(versionString).isNotEqualTo("ybymo ");
        assertThat(versionString.length()).isGreaterThan("ybymo ".length());
    }

    @Test
    void getVersion_versionNotNull_returnsVersion() {
        VersionProvider stubProvider = new VersionProvider() {
            @Override
            protected String getPackageImplementationVersion() {
                return "1.2.3";
            }
        };

        String[] version = stubProvider.getVersion();

        assertThat(version[0]).isEqualTo("ybymo 1.2.3");
    }
}
