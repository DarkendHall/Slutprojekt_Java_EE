package org.darkend.slutprojekt_java_ee.beans;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import springfox.documentation.spring.web.plugins.Docket;

import static org.assertj.core.api.Assertions.*;

class SpringFoxConfigTest {

    private final SpringFoxConfig springFoxConfig = new SpringFoxConfig();

    @Test
    void docketIsOpenApi() {
        var docket = springFoxConfig.docket();

        var docType = docket.getDocumentationType();

        assertThat(docket).isInstanceOf(Docket.class);
        assertThat(docType.getName()).isEqualTo("openApi");
        assertThat(docType.getVersion()).isEqualTo("3.0");
        assertThat(docType.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON);
    }
}
