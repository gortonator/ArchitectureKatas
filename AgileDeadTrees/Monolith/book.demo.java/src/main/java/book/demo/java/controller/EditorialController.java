/**
 * This is the controller class for handling HTTP requests related to editorial functionalities, such as creating draft
 * chapters, editor (internal user) submitting review, writer and editor adding comments to reviews, etc.
 * <p>
 * Endpoints:
 * POST /api/editorial/draft-chapters/create/{draftBookId}: Author using their corresponding Writer account to create a draft chapter.
 * PUT /api/editorial/draft-chapters/{draftChapterId}/edit/by-author: Author edits a draft chapter.
 * PUT /api/editorial/draft-chapters/{draftChapterId}/edit/by-editor: Editor edits a draft chapter.
 * PUT /api/editorial/draft-chapters/{draftChapterId}/submit: Author submits a draft chapter for pending review.
 * PUT /api/editorial/draft-chapters/{draftChapterId}/pending: Author accept or reject editor's edit on draft chapter content.
 * POST /api/editorial/draft-chapters/{draftChapterId}/review/create: Editor creates a review for a draft chapter.
 * PUT /api/editorial/draft-chapters/{draftChapterId}: Editor modifies draft chapter status: accepted or rejected.
 * POST /api/editorial/draft-chapters/publish/{draftChapterId}: Editor publishes a draft chapter.
 * POST /api/editorial/draft-chapters/{draftChapterId}/comments/by-editor: Editor comments on a review.
 * POST /api/editorial/draft-chapters/{draftChapterId}/comments/by-writer: Writer comments on a review.
 * GET /api/editorial/reviews/comments/{reviewId}: List all the comments of a review.
 * POST /api/editorial/draft-books/{draftBookId}/publish: Editor publish a DraftBook.
 *
 * @author Tong
 * @see AuthUtil
 * @see PredefinedRole
 */

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
     * Draft Chapters Section
     */

    /**
     * The Author using their corresponding Writer account to create a DraftChapter for a DraftBook using DraftBook id.
     *
     * @param draftBookId     the id of the draft book.
     * @param draftChapterDTO the DTO object that contains basic DraftChapter information.
     * @return A ResponseEntity containing the DraftChapter created and an HTTP status code.
     */
    @Operation(summary = "Authors using Writer account to create a draft chapter.")
    @PostMapping("/draft-chapters/create/{draftBookId}")
    @RequiresRoles(PredefinedRole.WRITER_ROLE)
    public ResponseEntity<DraftChapter> createDraftChapter(@PathVariable int draftBookId,
                                                           @RequestBody DraftChapterDTO draftChapterDTO) {
        String writerUsername = AuthUtil.getAuthenticatedUsername();
        DraftChapter draftChapter = editorialService.createDraftChapter(draftChapterDTO, draftBookId, "writer1");
        return new ResponseEntity<>(draftChapter, HttpStatus.CREATED);
    }

    /**
     * Author using corresponding Writer account to edit a DraftChapter before submission.
     *
     * @param draftChapterId  the id of the DraftChapter to be edited.
     * @param draftChapterDTO the DTO that contains the content for DraftChapter editing.
     * @return A ResponseEntity containing the edited DraftChapter object and an HTTP status code.
     */
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

    /**
     * The internal User with an "Editor" role edits the submitted DraftChapter content and sets the DraftChapter
     * status to be pending for approval from Writer.
     *
     * @param draftChapterId  the id of the DraftChapter to be edited.
     * @param draftChapterDTO the DTO that contains the content for DraftChapter editing.
     * @return A ResponseEntity containing the edited DraftChapter object and an HTTP status code.
     */
    @Operation(summary = "Editor edits a draft chapter.")
    @PutMapping("/draft-chapters/{draftChapterId}/edit/by-editor")
    @RequiresRoles(PredefinedRole.EDITOR_ROLE)
    public ResponseEntity<DraftChapter> editPendingContentByEditor(@PathVariable int draftChapterId,
                                                                   @RequestBody DraftChapterDTO draftChapterDTO) {
        DraftChapter draftChapter = editorialService
                .editDraftChapterByEditor(draftChapterId, draftChapterDTO);
        return new ResponseEntity<>(draftChapter, HttpStatus.OK);
    }

    /**
     * Author using Writer account to submit a DraftChapter for pending review from Editor.
     *
     * @param draftChapterId the id of the DraftChapter object to be submitted.
     * @return A ResponseEntity containing the DraftChapter with updated status of PENDING_APPROVAL_FROM_EDITOR
     * and an HTTP status code.
     */
    @Operation(summary = "Author submits a draft chapter for pending review.")
    @PutMapping("/draft-chapters/{draftChapterId}/submit")
    @RequiresRoles(PredefinedRole.WRITER_ROLE)
    public ResponseEntity<DraftChapter> submitDraftChapter(@PathVariable int draftChapterId) {
        String writerUsername = AuthUtil.getAuthenticatedUsername();
        DraftChapter draftChapter = editorialService.submitDraftChapter(draftChapterId, writerUsername);
        return new ResponseEntity<>(draftChapter, HttpStatus.OK);
    }

    /**
     * Once Editor edits the content of a DraftChapter, Author gets to decide whether the pending content is
     * accepted or not.
     *
     * @param draftChapterId the id of the DraftChapter for pending approval from Author.
     * @param isAccepted     a boolean value that indicates whether the pending content is accepted or not.
     * @return A ResponseEntity containing the DraftChapter after approval decision with updated or unchanged content
     * and updated status and an HTTP status code.
     */
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

    /**
     * Editor creates Review for a submitted DraftChapter.
     *
     * @param draftChapterId the id of the submitted DraftChapter object.
     * @param content        the content of the Review made.
     * @return A ResponseEntity containing the Review created by Editor and an HTTP status code.
     */
    @Operation(summary = "Editor makes review for a draft chapter.")
    @PostMapping("/draft-chapters/{draftChapterId}/review/create")
    @RequiresRoles(PredefinedRole.EDITOR_ROLE)
    public ResponseEntity<Review> createReview(@PathVariable int draftChapterId, String content) {
        String editorUsername = AuthUtil.getAuthenticatedUsername();
        Review review = editorialService.createReview(draftChapterId, editorUsername, content);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    /**
     * Editor sets the status of the DraftChapter to be Accepted or Rejected.
     *
     * @param draftChapterId the id of the DraftChapter.
     * @param status         the Status of the DraftChapter to be set.
     * @return A ResponseEntity containing the DraftChapter with updated status and an HTTP status code.
     */
    @Operation(summary = "Editor modifies draft chapter status: accepted or reject.")
    @PutMapping("/draft-chapters/{draftChapterId}")
    @RequiresRoles(PredefinedRole.EDITOR_ROLE)
    public ResponseEntity<DraftChapter> updateChapterStatus(@PathVariable int draftChapterId,
                                                            DraftChapterStatus status) {
        DraftChapter draftChapter = editorialService.updateDraftChapterStatus(draftChapterId, status);
        return new ResponseEntity<>(draftChapter, HttpStatus.OK);
    }

    /**
     * Editor publishes a DraftChapter, which creates an AcceptedChapter object.
     *
     * @param draftChapterId the id of the DraftChapter object for pending publication.
     * @return A ResponseEntity containing an AcceptedChapter object and an HTTP status code.
     */
    @Operation(summary = "Editor publishes a draft chapter.")
    @PostMapping("/draft-chapters/publish/{draftChapterId}")
    @RequiresRoles(PredefinedRole.EDITOR_ROLE)
    public ResponseEntity<AcceptedChapter> publishDraftChapter(@PathVariable int draftChapterId) {
        AcceptedChapter acceptedChapter = editorialService.publishDraftChapter(draftChapterId);
        return new ResponseEntity<>(acceptedChapter, HttpStatus.CREATED);
    }

    /**
     * This method creates a Comment for a Review from Editor.
     *
     * @param draftChapterId the id of the DraftChapter that has been reviewed.
     * @param content        the content of the Comment
     * @return A ResponseEntity containing an EditorComment object and an HTTP status code.
     */
    @Operation(summary = "Editor comments on a review.")
    @PostMapping("/draft-chapters/{draftChapterId}/comments/by-editor")
    @RequiresRoles(PredefinedRole.EDITOR_ROLE)
    public ResponseEntity<EditorComment> createCommentByEditor(@PathVariable int draftChapterId, String content) {
        String editorUsername = AuthUtil.getAuthenticatedUsername();
        EditorComment comment = editorialService.createEditorComment(draftChapterId, editorUsername, content);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    /**
     * This method creates a Comment for a Review from Writer.
     *
     * @param draftChapterId the id of the DraftChapter that has been reviewed.
     * @param content        the content of the Comment
     * @return A ResponseEntity containing an WriterComment object and an HTTP status code.
     */
    @Operation(summary = "Writer comments on a review.")
    @PostMapping("/draft-chapters/{draftChapterId}/comments/by-writer")
    @RequiresRoles(PredefinedRole.WRITER_ROLE)
    public ResponseEntity<WriterComment> createCommentByWriter(@PathVariable int draftChapterId, String content) {
        String writerUsername = AuthUtil.getAuthenticatedUsername();
        WriterComment comment = editorialService.createWriterComment(draftChapterId, writerUsername, content);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    /**
     * This method would retrieve all the comments of a Review by using the Review id.
     *
     * @param reviewId the id of the Review
     * @param byAsc    a boolean value indicating whether the Comments are sorted in ASC or DESC order.
     * @return A ResponseEntity containing a list of CommentDTO, which saves the content of both EditorComment
     * and WriterComment and an HTTP status code.
     */
    @Operation(summary = "List all the comments of a review.")
    @GetMapping("/reviews/comments/{reviewId}")
    @RequiresRoles(value = {PredefinedRole.WRITER_ROLE, PredefinedRole.EDITOR_ROLE}, logical = Logical.OR)
    public ResponseEntity<List<CommentDTO>> getReviewComments(@PathVariable int reviewId,
                                                              @RequestParam(defaultValue = "true") boolean byAsc) {
        List<CommentDTO> comments = editorialService.getCommentsByReviewId(reviewId, byAsc);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    /*
     * Draft Books Section
     */

    /**
     * The editor publishes a DraftBook and therefore a PublishedBook is created.
     *
     * @param draftBookId the id of the DraftBook to be published.
     * @return A ResponseEntity containing a PublishedBook object and an HTTP status code.
     */
    @Operation(summary = "Editor publish a DraftBook.")
    @PostMapping("/draft-books/{draftBookId}/publish")
    @RequiresRoles(PredefinedRole.EDITOR_ROLE)
    public ResponseEntity<PublishedBook> publishDraftBook(@PathVariable int draftBookId) {
        PublishedBook publishedBook = editorialService.publishDraftBook(draftBookId);
        return new ResponseEntity<>(publishedBook, HttpStatus.CREATED);
    }

}
