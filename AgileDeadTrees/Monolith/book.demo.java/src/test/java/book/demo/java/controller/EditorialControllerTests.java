package book.demo.java.controller;

import book.demo.java.dto.CommentDTO;
import book.demo.java.dto.DraftChapterDTO;
import book.demo.java.entity.account.external.Writer;
import book.demo.java.entity.account.internal.User;
import book.demo.java.entity.book.Author;
import book.demo.java.entity.book.Genre;
import book.demo.java.entity.book.editorial.*;
import book.demo.java.entity.book.published.PublishedBook;
import book.demo.java.service.EditorialService;
import book.demo.java.util.AuthUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EditorialController.class)
public class EditorialControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EditorialService editorialService;

    private final String baseUrl = "/api/editorial";

    private Author author1;

    private DraftBook draftBook1;

    private DraftChapter draftChapter1;

    private AcceptedChapter acceptedChapter1;

    private Review review1;

    private EditorComment editorComment1;
    private WriterComment writerComment1;

    private User editor;
    private Writer writer1;
    private MockedStatic<AuthUtil> authUtil;


    @BeforeEach
    void setUp() {
        authUtil = Mockito.mockStatic(AuthUtil.class);

        String authorName = "author";
        String one = "1";
        String two = "2";
        String content = "content";
        String title = "title";

        author1 = new Author(authorName + one, authorName + one);
        Author author2 = new Author(authorName + two, authorName + two);

        Set<Author> authors2 = Stream.of(author1, author2).collect(Collectors.toSet());

        draftBook1 = new DraftBook(authorName + one, Genre.ROMANCE, authors2);

        draftChapter1 = new DraftChapter(3, title, content, draftBook1, author1);

        acceptedChapter1 = new AcceptedChapter(1, title, content, draftBook1, author1);


        editor = new User("name", "name", "username", "password", "email");
        review1 = new Review(draftChapter1, editor, content);
        editorComment1 = new EditorComment(content, review1, editor);

        writer1 = new Writer("username", "password", "email", author1);
        writerComment1 = new WriterComment(content, review1, writer1);
    }

    @AfterEach
    void cleanup() {
        authUtil.close();
    }

    @Test
    public void createDraftChapterTest() throws Exception {
        DraftChapterDTO draftChapterDTO = new DraftChapterDTO(draftChapter1.getChapterIndex(), "title", "content");
        String username = writer1.getUsername();
        authUtil.when(AuthUtil::getAuthenticatedUsername).thenReturn(username);

        int draftBookId = draftBook1.getId();
        when(editorialService.createDraftChapter(draftChapterDTO, draftBookId, username))
                .thenReturn(draftChapter1);

        String url = baseUrl + "/draft-chapters/create/{draftBookId}";

        MockHttpServletRequestBuilder request = post(url, draftBookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(draftChapterDTO));

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();

        verify(editorialService).createDraftChapter(draftChapterDTO, draftBookId, username);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(draftChapter1);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void editDraftChapterByAuthorTest() throws Exception {
        String username = writer1.getUsername();
        authUtil.when(AuthUtil::getAuthenticatedUsername).thenReturn(username);

        int draftChapterId = draftChapter1.getId();
        DraftChapter editedDraftChapter = new DraftChapter(1, "title", "New content", draftBook1, author1);
        DraftChapterDTO editDTO = new DraftChapterDTO(1, "title", "New content");
        when(editorialService.editDraftChapterByAuthor(draftChapterId, editDTO, username))
                .thenReturn(editedDraftChapter);

        String url = baseUrl + "/draft-chapters/{draftChapterId}/edit/by-author";

        MockHttpServletRequestBuilder request = put(url, draftChapterId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editedDraftChapter));

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        verify(editorialService).editDraftChapterByAuthor(draftChapterId, editDTO, username);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(editedDraftChapter);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void editDraftChapterByEditorTest() throws Exception {
        String username = editor.getUsername();
        authUtil.when(AuthUtil::getAuthenticatedUsername).thenReturn(username);

        int draftChapterId = draftChapter1.getId();
        DraftChapter editedDraftChapter = new DraftChapter(1, "title", "New content", draftBook1, author1);
        DraftChapterDTO editDTO = new DraftChapterDTO(1, "title", "New content");
        when(editorialService.editDraftChapterByEditor(draftChapterId, editDTO))
                .thenReturn(editedDraftChapter);

        String url = baseUrl + "/draft-chapters/{draftChapterId}/edit/by-editor";

        MockHttpServletRequestBuilder request = put(url, draftChapterId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editedDraftChapter));

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        verify(editorialService).editDraftChapterByEditor(draftChapterId, editDTO);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(editedDraftChapter);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void submitDraftChapterTest() throws Exception {
        String username = writer1.getUsername();
        authUtil.when(AuthUtil::getAuthenticatedUsername).thenReturn(username);

        int draftChapterId = draftChapter1.getId();
        when(editorialService.submitDraftChapter(draftChapterId, username))
                .thenReturn(draftChapter1);

        String url = baseUrl + "/draft-chapters/{draftChapterId}/submit";

        MvcResult mvcResult = mockMvc.perform(put(url, draftChapterId))
                .andExpect(status().isOk())
                .andReturn();

        verify(editorialService).submitDraftChapter(draftChapterId, username);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(draftChapter1);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void setPendingContentTest() throws Exception {
        String username = writer1.getUsername();
        authUtil.when(AuthUtil::getAuthenticatedUsername).thenReturn(username);

        int draftChapterId = draftChapter1.getId();
        draftChapter1.setPendingContent("pending");
        DraftChapter postPendingDraftChapter = new DraftChapter(1, "title", "pending", draftBook1, author1);
        boolean isAccepted = true;
        when(editorialService.setPendingContentStatusByAuthor(draftChapterId, isAccepted, username))
                .thenReturn(postPendingDraftChapter);

        String url = baseUrl + "/draft-chapters/{draftChapterId}/pending";

        MvcResult mvcResult = mockMvc.perform(put(url, draftChapterId)
                        .param("isAccepted", String.valueOf(isAccepted)))
                .andExpect(status().isOk())
                .andReturn();

        verify(editorialService).setPendingContentStatusByAuthor(draftChapterId, isAccepted, username);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(postPendingDraftChapter);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void createReviewTest() throws Exception {
        String username = editor.getUsername();
        authUtil.when(AuthUtil::getAuthenticatedUsername).thenReturn(username);

        int draftChapterId = draftChapter1.getId();
        String content = review1.getContent();
        when(editorialService.createReview(draftChapterId, username, content))
                .thenReturn(review1);

        String url = baseUrl + "/draft-chapters/{draftChapterId}/review/create";

        MvcResult mvcResult = mockMvc.perform(post(url, draftChapterId)
                        .param("content", content))
                .andExpect(status().isCreated())
                .andReturn();

        verify(editorialService).createReview(draftChapterId, username, content);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(review1);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void updateChapterStatusTest() throws Exception {
        String username = editor.getUsername();
        authUtil.when(AuthUtil::getAuthenticatedUsername).thenReturn(username);

        int draftChapterId = draftChapter1.getId();
        DraftChapterStatus draftChapterStatus = DraftChapterStatus.ACCEPTED;
        draftChapter1.setStatus(draftChapterStatus);
        when(editorialService.updateDraftChapterStatus(draftChapterId, draftChapterStatus))
                .thenReturn(draftChapter1);

        String url = baseUrl + "/draft-chapters/{draftChapterId}";

        MvcResult mvcResult = mockMvc.perform(put(url, draftChapterId)
                        .param("status", draftChapterStatus.name()))
                .andExpect(status().isOk())
                .andReturn();

        verify(editorialService).updateDraftChapterStatus(draftChapterId, draftChapterStatus);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(draftChapter1);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void publishDraftChapterTest() throws Exception {
        String username = editor.getUsername();
        authUtil.when(AuthUtil::getAuthenticatedUsername).thenReturn(username);

        int draftChapterId = draftChapter1.getId();
        when(editorialService.publishDraftChapter(draftChapterId))
                .thenReturn(acceptedChapter1);

        String url = baseUrl + "/draft-chapters/publish/{draftChapterId}";

        MvcResult mvcResult = mockMvc.perform(post(url, draftChapterId))
                .andExpect(status().isCreated())
                .andReturn();

        verify(editorialService).publishDraftChapter(draftChapterId);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(acceptedChapter1);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void createCommentByEditorTest() throws Exception {
        String username = editor.getUsername();
        authUtil.when(AuthUtil::getAuthenticatedUsername).thenReturn(username);

        int draftChapterId = draftChapter1.getId();
        String content = "content";
        when(editorialService.createEditorComment(draftChapterId, username, content))
                .thenReturn(editorComment1);

        String url = baseUrl + "/draft-chapters/{draftChapterId}/comments/by-editor";

        MvcResult mvcResult = mockMvc.perform(post(url, draftChapterId)
                        .param("content", "content"))
                .andExpect(status().isCreated())
                .andReturn();

        verify(editorialService).createEditorComment(draftChapterId, username, content);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(editorComment1);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void createCommentByWriterTest() throws Exception {
        String username = writer1.getUsername();
        authUtil.when(AuthUtil::getAuthenticatedUsername).thenReturn(username);

        int draftChapterId = draftChapter1.getId();
        String content = "content";
        when(editorialService.createWriterComment(draftChapterId, username, content))
                .thenReturn(writerComment1);

        String url = baseUrl + "/draft-chapters/{draftChapterId}/comments/by-writer";

        MvcResult mvcResult = mockMvc.perform(post(url, draftChapterId)
                        .param("content", content))
                .andExpect(status().isCreated())
                .andReturn();

        verify(editorialService).createWriterComment(draftChapterId, username, content);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(writerComment1);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void getReviewCommentsTest() throws Exception {
        int reviewId = review1.getId();
        boolean byAsc = true;

        editorComment1.setTimestamp(LocalDateTime.of(1997, 2, 6, 5, 6, 17));
        writerComment1.setTimestamp(LocalDateTime.of(1998, 10, 6, 5, 6, 17));


        CommentDTO dto1 = new CommentDTO(editorComment1.getContent(), editorComment1.getCommenterType(),
                editorComment1.getCommenterUsername(), editorComment1.getTimestamp());
        CommentDTO dto2 = new CommentDTO(writerComment1.getContent(), writerComment1.getCommenterType(),
                writerComment1.getCommenterUsername(), writerComment1.getTimestamp());
        List<CommentDTO> comments = Arrays.asList(dto1, dto2);
        when(editorialService.getCommentsByReviewId(reviewId, byAsc))
                .thenReturn(comments);

        String url = baseUrl + "/reviews/comments/{reviewId}";

        MvcResult mvcResult = mockMvc.perform(get(url, reviewId))
                .andExpect(status().isOk())
                .andReturn();

        verify(editorialService).getCommentsByReviewId(reviewId, byAsc);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(comments);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void publishDraftBookTest() throws Exception {
        PublishedBook publishedBook = new PublishedBook(draftBook1.getTitle(),
                draftBook1.getGenre(), draftBook1.getAuthors());
        int draftBookId = draftBook1.getId();
        when(editorialService.publishDraftBook(draftBookId))
                .thenReturn(publishedBook);

        String url = baseUrl + "/draft-books/{draftBookId}/publish";

        MvcResult mvcResult = mockMvc.perform(post(url, draftBookId))
                .andExpect(status().isCreated())
                .andReturn();

        verify(editorialService).publishDraftBook(draftBookId);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(publishedBook);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }
}
