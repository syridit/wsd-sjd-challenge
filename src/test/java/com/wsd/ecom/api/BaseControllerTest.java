package com.wsd.ecom.api;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 04 July, 2025
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
public class BaseControllerTest {

    protected final ObjectMapper objectMapper;
    protected final MockMvc mockMvc;

    @Autowired
    public BaseControllerTest(MockMvc mockMvc) {
        this.objectMapper = new ObjectMapper();
        this.mockMvc = mockMvc;
    }

    public <T> ResultActions performMockMvc(HttpMethod httpMethod,
                                            String url,
                                            MultiValueMap<String, String> queryParams,
                                            T requestBody,
                                            Object... pathVariables) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .request(httpMethod, url, pathVariables)
                .contentType(MediaType.APPLICATION_JSON);

        if (!ObjectUtils.isEmpty(requestBody)) {
            if (requestBody instanceof String) {
                requestBuilder.content((String) requestBody);
            } else {
                requestBuilder.content(objectMapper.writeValueAsString(requestBody));
            }
        }
        if (!CollectionUtils.isEmpty(queryParams)) {
            requestBuilder.queryParams(queryParams);
        }
        return mockMvc.perform(requestBuilder).andDo(print());
    }

    protected MultiValueMap<String, String> toMultiMap(Map<String, String> map) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        if (!CollectionUtils.isEmpty(map)) {
            map.forEach(multiValueMap::add);
        }
        return multiValueMap;
    }

    public ResultActions get(String url, Map<String, String> queryParams, Object... pathVariables) throws Exception {
        return performMockMvc(HttpMethod.GET, url, toMultiMap(queryParams), null, pathVariables);
    }

    public void assertHttpStatus(ResultActions resultActions, HttpStatus expectedHttpStatus) throws Exception {
        resultActions.andExpect(status().is(expectedHttpStatus.value()));
    }

    public void assertJsonResponseBody(ResultActions resultActions, String expectedJsonResponse) throws Exception {
        resultActions.andExpectAll(
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(expectedJsonResponse)
        );
    }
}
