package book.demo.java.controller;

import book.demo.java.entity.account.external.Reader;
import book.demo.java.service.ReaderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReaderController.class)
public class ReaderControllerTests {

    private final String baseUrl = "/api/readers";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ReaderService readerService;
    private Reader reader1;
    private List<Reader> readers;

    @BeforeEach
    void setup() {

        String placeholder = "reader";
        String one = "1";
        String two = "2";
        String passwordString = "password";
        String email = "email";
        String enPassword1 = new SimpleHash("MD5", passwordString + one, placeholder + one, 1024).toString();
        String enPassword2 = new SimpleHash("MD5", passwordString + two, placeholder + two, 1024).toString();
        reader1 = new Reader(placeholder + one, placeholder + one, placeholder + one, enPassword1, email);
        Reader reader2 = new Reader(placeholder + two, placeholder + two, placeholder + two, enPassword2, email);
        readers = Arrays.asList(reader1, reader2);
    }

    @Test
    public void getAllReadersTest() throws Exception {
        when(readerService.getAllReaders()).thenReturn(readers);
        String url = baseUrl + "/all";
        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        verify(readerService).getAllReaders();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(readers);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void getReaderByIdTest() throws Exception {
        int readerId = reader1.getId();
        when(readerService.getReaderById(readerId)).thenReturn(reader1);
        String url = baseUrl + "/{readerId}";
        MvcResult mvcResult = mockMvc.perform(get(url, readerId))
                .andExpect(status().isOk())
                .andReturn();

        verify(readerService).getReaderById(readerId);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(reader1);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }
}
