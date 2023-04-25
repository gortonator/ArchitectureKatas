package book.demo.java.controller;

import book.demo.java.dto.CommentDTO;
import book.demo.java.dto.DraftChapterDTO;
import book.demo.java.entity.book.editorial.*;
import book.demo.java.entity.book.published.PublishedBook;
import book.demo.java.service.EditorialService;
import book.demo.java.util.AuthUtil;
import book.demo.java.util.PredefinedRole;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/editorial")
public class EditorialController {

    @Autowired
    private EditorialService editorialService;

    /*
    Draft Chapters Section
     */

    @Operation(summary = "Author creates a draft chapter.")
    @PostMapping("/draft-chapters/create/{draftBookId}")
    @RequiresRoles(PredefinedRole.WRITER_ROLE)
    public ResponseEntity<DraftChapter> createDraftChapter(@PathVariable int draftBookId,
                                                           @RequestBody DraftChapterDTO draftChapterDTO) {
        String writerUsername = AuthUtil.getAuthenticatedUsername();
        DraftChapter draftChapter = editorialService.createDraftChapter(draftChapterDTO, draftBookId, writerUsername);
        return new ResponseEntity<>(draftChapter, HttpStatus.CREATED);
    }

    @Operation(summary = "Author edits a draft chapter.")
    @PutMapping("/draft-chapters/{draftChapterId}/edit/by-author")
    @RequiresRoles(PredefinedRole.WRITER_ROLE)
    public ResponseEntity<DraftChapter> editDraftChapterByAuthor(@PathVariable int draftChapterId,
                                                                 @RequestBody DraftChapterDTO draftChapterDTO) {
        String writerUsername = AuthUtil.getAuthenticatedUsername();
        DraftChapter draftChapter = editorialService
                .editDraftChapterByAuthor(draftChapterId, draftChapterDTO, writerUsername);
        return new ResponseEntity<>(draftChapter, HttpStatus.OK);
    }

    @Operation(summary = "Editor edits a draft chapter.")
    @PutMapping("/draft-chapters/{draftChapterId}/edit/by-editor")
    @RequiresRoles(PredefinedRole.EDITOR_ROLE)
    public ResponseEntity<DraftChapter> editPendingContentByEditor(@PathVariable int draftChapterId,
                                                                   @RequestBody DraftChapterDTO draftChapterDTO) {
        DraftChapter draftChapter = editorialService
                .editDraftChapterByEditor(draftChapterId, draftChapterDTO);
        return new ResponseEntity<>(draftChapter, HttpStatus.OK);
    }

    @Operation(summary = "Author submits a draft chapter for pending review.")
    @PutMapping("/draft-chapters/{draftChapterId}/submit")
    @RequiresRoles(PredefinedRole.WRITER_ROLE)
    public ResponseEntity<DraftChapter> submitDraftChapter(@PathVariable int draftChapterId) {
        String writerUsername = AuthUtil.getAuthenticatedUsername();
        DraftChapter draftChapter = editorialService.submitDraftChapter(draftChapterId, writerUsername);
        return new ResponseEntity<>(draftChapter, HttpStatus.OK);
    }

    @Operation(summary = "Author accept or reject editor's edit on draft chapter content.")
    @PutMapping("/draft-chapters/{draftChapterId}/pending")
    @RequiresRoles(PredefinedRole.WRITER_ROLE)
    public ResponseEntity<DraftChapter> setPendingContentStatus(@PathVariable int draftChapterId,
                                                                @RequestParam boolean isAccepted) {
        String writerUsername = AuthUtil.getAuthenticatedUsername();
        DraftChapter draftChapter = editorialService
                .setPendingContentStatusByAuthor(draftChapterId, isAccepted, writerUsername);
        return new ResponseEntity<>(draftChapter, HttpStatus.OK);
    }

    @Operation(summary = "Editor makes review for a draft chapter.")
    @PostMapping("/draft-chapters/{draftChapterId}/review/create")
    @RequiresRoles(PredefinedRole.EDITOR_ROLE)
    public ResponseEntity<Review> createReview(@PathVariable int draftChapterId, String content) {
        String editorUsername = AuthUtil.getAuthenticatedUsername();
        Review review = editorialService.createReview(draftChapterId, editorUsername, content);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @Operation(summary = "Editor modifies draft chapter status: accept, reject, close.")
    @PutMapping("/draft-chapters/{draftChapterId}")
    @RequiresRoles(PredefinedRole.EDITOR_ROLE)
    public ResponseEntity<DraftChapter> updateChapterStatus(@PathVariable int draftChapterId,
                                                            DraftChapterStatus status) {
        DraftChapter draftChapter = editorialService.updateDraftChapterStatus(draftChapterId, status);
        return new ResponseEntity<>(draftChapter, HttpStatus.OK);
    }

    @Operation(summary = "Editor publishes a draft chapter.")
    @PostMapping("/draft-chapters/publish/{draftChapterId}")
    @RequiresRoles(PredefinedRole.EDITOR_ROLE)
    public ResponseEntity<AcceptedChapter> publishDraftChapter(@PathVariable int draftChapterId) {
        AcceptedChapter acceptedChapter = editorialService.publishDraftChapter(draftChapterId);
        return new ResponseEntity<>(acceptedChapter, HttpStatus.CREATED);
    }

    @Operation(summary = "Editor comments on a review.")
    @PostMapping("/draft-chapters/{draftChapterId}/comments/by-editor")
    @RequiresRoles(PredefinedRole.EDITOR_ROLE)
    public ResponseEntity<EditorComment> createCommentByEditor(@PathVariable int draftChapterId, String content) {
        String editorUsername = AuthUtil.getAuthenticatedUsername();
        EditorComment comment = editorialService.createEditorComment(draftChapterId, editorUsername, content);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @Operation(summary = "Writer comments on a review.")
    @PostMapping("/draft-chapters/{draftChapterId}/comments/by-writer")
    @RequiresRoles(PredefinedRole.WRITER_ROLE)
    public ResponseEntity<WriterComment> createCommentByWriter(@PathVariable int draftChapterId, String content) {
        String writerUsername = AuthUtil.getAuthenticatedUsername();
        WriterComment comment = editorialService.createWriterComment(draftChapterId, writerUsername, content);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @Operation(summary = "List all the comments of a review.")
    @GetMapping("/reviews/comments/{reviewId}")
    @RequiresRoles(value = {PredefinedRole.WRITER_ROLE, PredefinedRole.EDITOR_ROLE}, logical = Logical.OR)
    public ResponseEntity<List<CommentDTO>> getReviewComments(@PathVariable int reviewId,
                                                              @RequestParam(defaultValue = "true") boolean byAsc) {
        List<CommentDTO> comments = editorialService.getCommentsByReviewId(reviewId, byAsc);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    /*
    Draft Books Section
     */
    @Operation(summary = "Publish a DraftBook.")
    @PostMapping("/draft-books/{draftBookId}/publish")
    @RequiresRoles(PredefinedRole.EDITOR_ROLE)
    public ResponseEntity<PublishedBook> publishDraftBook(@PathVariable int draftBookId) {
        PublishedBook publishedBook = editorialService.publishDraftBook(draftBookId);
        return new ResponseEntity<>(publishedBook, HttpStatus.CREATED);
    }

}
