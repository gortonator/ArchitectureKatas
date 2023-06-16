package book.demo.java.graphql_controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import book.demo.java.dto.CommentDTO;
import book.demo.java.dto.DraftChapterDTO;
import book.demo.java.entity.book.editorial.AcceptedChapter;
import book.demo.java.entity.book.editorial.DraftChapter;
import book.demo.java.entity.book.editorial.DraftChapterStatus;
import book.demo.java.entity.book.editorial.EditorComment;
import book.demo.java.entity.book.editorial.Review;
import book.demo.java.entity.book.editorial.WriterComment;
import book.demo.java.entity.book.published.PublishedBook;
import book.demo.java.service.EditorialService;

@Controller
public class EditorialControllers {
    private EditorialService editorialService;

    public EditorialControllers(EditorialService editorialService) {
        this.editorialService = editorialService;
    }

    @QueryMapping
    public List<CommentDTO> getCommentsByReviewId(@Argument Integer reviewId, @Argument Boolean byAsc) {
        return editorialService.getCommentsByReviewId(reviewId, byAsc);
    }

    @MutationMapping
    public DraftChapter createDraftChapter(@Argument DraftChapterDTO draftChapterDTO,@Argument Integer draftBookId,@Argument String username) {
        return editorialService.createDraftChapter(draftChapterDTO, draftBookId, username);
    }

    @MutationMapping
    public DraftChapter editDraftChapterByAuthor(@Argument Integer draftChapterId,@Argument DraftChapterDTO draftChapterDTO,@Argument String username) {
        return editorialService.editDraftChapterByAuthor(draftChapterId,draftChapterDTO, username);
    }

    @MutationMapping
    public DraftChapter editDraftChapterByEditor(@Argument Integer draftChapterId,@Argument DraftChapterDTO draftChapterDTO) {
        return editorialService.editDraftChapterByEditor(draftChapterId,draftChapterDTO);
    }

    @MutationMapping
    public DraftChapter submitDraftChapter(@Argument Integer draftChapterId,@Argument String writerUsername) {
        return editorialService.submitDraftChapter(draftChapterId,writerUsername);
    }

    @MutationMapping
    public DraftChapter setPendingContentStatusByAuthor(@Argument Integer draftChapterId,@Argument Boolean isAccepted,@Argument String username) {
        return editorialService.setPendingContentStatusByAuthor(draftChapterId,isAccepted, username);
    }

    @MutationMapping
    public Review createReview(@Argument Integer draftChapterId,@Argument String editorUsername,@Argument String content) {
        return editorialService.createReview(draftChapterId,editorUsername, content);
    }

    @MutationMapping
    public DraftChapter updateDraftChapterStatus(@Argument Integer draftChapterId,@Argument DraftChapterStatus status) {
        return editorialService.updateDraftChapterStatus(draftChapterId,status);
    }

    @MutationMapping
    public AcceptedChapter publishDraftChapter(@Argument Integer draftChapterId) {
        return editorialService.publishDraftChapter(draftChapterId);
    }

    @MutationMapping
    public EditorComment createEditorComment(@Argument Integer draftChapterId, @Argument String username, @Argument String content) {
        return editorialService.createEditorComment(draftChapterId, username, content);
    }

    @MutationMapping
    public WriterComment createWriterComment(@Argument Integer draftChapterId, @Argument String username, @Argument String content) {
        return editorialService.createWriterComment(draftChapterId, username, content);
    }

    @MutationMapping
    public PublishedBook publishDraftBook(@Argument Integer draftBookId) {
        return editorialService.publishDraftBook(draftBookId);
    }
}
