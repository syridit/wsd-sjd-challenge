package com.wsd.ecom;

import com.wsd.ecom.config.TestcontainerConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Import(TestcontainerConfiguration.class)
class ApplicationTests {

    @Test
    void contextLoads() {
    }

}
