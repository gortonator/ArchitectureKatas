package book.demo.java.service.impl;

import book.demo.java.dto.CommentDTO;
import book.demo.java.dto.DraftChapterDTO;
import book.demo.java.entity.account.external.Writer;
import book.demo.java.entity.account.internal.User;
import book.demo.java.entity.book.Author;
import book.demo.java.entity.book.editorial.*;
import book.demo.java.entity.book.published.PublishedBook;
import book.demo.java.repository.*;
import book.demo.java.service.EditorialService;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class EditorialServiceImpl implements EditorialService {

    @Autowired
    private DraftChapterRepository draftChapterRepo;

    @Autowired
    private DraftBookRepository draftBookRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private AcceptedChapterRepository acceptedChapterRepo;

    @Autowired
    private PublishedBookRepository publishedBookRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private WriterRepository writerRepo;

    @Autowired
    private EditorCommentRepository editorCommentRepo;

    @Autowired
    private WriterCommentRepository writerCommentRepo;

    @Override
    public DraftChapter createDraftChapter(DraftChapterDTO draftChapterDTO, int draftBookId, String username) {

        DraftBook draftBook = findDraftBook(draftBookId);

        Author author = findWriterByUsername(username).getAuthor();

        validateAuthorOfDraftBook(draftBook, author);

        DraftChapter draftChapter = new DraftChapter(draftChapterDTO.getChapterIndex(), draftChapterDTO.getTitle(),
                draftChapterDTO.getContent(), draftBook, author);

        return draftChapterRepo.save(draftChapter);
    }

    @Override
    public DraftChapter editDraftChapterByAuthor(int draftChapterId, DraftChapterDTO draftChapterDTO, String username) {

        DraftChapter draftChapter = findDraftChapter(draftChapterId);
        Author author = getAuthorByWriterUsername(username);

        if (draftChapter.getStatus() != DraftChapterStatus.CREATED) {
            throw new IllegalStateException("Draft chapter is submitted and cannot be modified.");
        }

        validateAuthorOfDraftChapter(draftChapter, author);

        draftChapter.setChapterIndex(draftChapterDTO.getChapterIndex());
        draftChapter.setTitle(draftChapterDTO.getTitle());
        draftChapter.setContent(draftChapterDTO.getContent());

        return draftChapterRepo.save(draftChapter);
    }

    @Override
    public DraftChapter editDraftChapterByEditor(int draftChapterId, DraftChapterDTO draftChapterDTO) {
        DraftChapter draftChapter = findDraftChapter(draftChapterId);

        if (!draftChapter.getStatus().isEditableByEditor()) {
            throw new IllegalStateException("Draft chapter cannot be edited.");
        }
        draftChapter.setChapterIndex(draftChapterDTO.getChapterIndex());
        draftChapter.setTitle(draftChapterDTO.getTitle());
        draftChapter.setPendingContent(draftChapterDTO.getContent());
        draftChapter.setStatus(DraftChapterStatus.PENDING_APPROVAL_FROM_AUTHOR);

        return draftChapterRepo.save(draftChapter);
    }

    @Override
    public DraftChapter submitDraftChapter(int draftChapterId, String writerUsername) {
        DraftChapter draftChapter = findDraftChapter(draftChapterId);
        Author author = getAuthorByWriterUsername(writerUsername);
        validateAuthorOfDraftChapter(draftChapter, author);

        if (draftChapter.getStatus() != DraftChapterStatus.CREATED) {
            throw new IllegalStateException("Draft Chapter is already submitted and pending for review.");
        }

        draftChapter.setStatus(DraftChapterStatus.PENDING_APPROVAL_FROM_EDITOR);
        return draftChapterRepo.save(draftChapter);
    }

    @Override
    public DraftChapter setPendingContentStatusByAuthor(int draftChapterId, boolean isAccepted, String username) {
        DraftChapter draftChapter = findDraftChapter(draftChapterId);
        Author author = getAuthorByWriterUsername(username);

        if (draftChapter.getStatus() != DraftChapterStatus.PENDING_APPROVAL_FROM_AUTHOR) {
            throw new IllegalStateException("Draft chapter is not pending approval from author.");
        }

        validateAuthorOfDraftChapter(draftChapter, author);

        if (draftChapter.getPendingContent() == null) {
            throw new IllegalStateException("There is no pending content from editor for approval.");
        }

        if (isAccepted) {
            String pendingContent = draftChapter.getPendingContent();
            draftChapter.setContent(pendingContent);
        }
        draftChapter.setPendingContent(null);
        draftChapter.setStatus(DraftChapterStatus.PENDING_APPROVAL_FROM_EDITOR);

        return draftChapterRepo.save(draftChapter);
    }

    @Override
    public Review createReview(int draftChapterId, String editorUsername, String content) {
        DraftChapter draftChapter = findDraftChapter(draftChapterId);
        User editor = findEditorByUsername(editorUsername);
        Review review = new Review(draftChapter, editor, content);
        return reviewRepo.save(review);
    }

    @Override
    public DraftChapter updateDraftChapterStatus(int draftChapterId, DraftChapterStatus status) {
        DraftChapter draftChapter = findDraftChapter(draftChapterId);

        if (!status.isApprovalStatus()) {
            throw new IllegalArgumentException("Incorrect draft chapter status.");
        }
        draftChapter.setStatus(status);
        return draftChapterRepo.save(draftChapter);
    }

    @Override
    public AcceptedChapter publishDraftChapter(int draftChapterId) {
        DraftChapter draftChapter = findDraftChapter(draftChapterId);

        if (draftChapter.getStatus() != DraftChapterStatus.ACCEPTED) {
            throw new IllegalStateException("Draft Chapter is not accepted, cannot be published.");
        }

        AcceptedChapter chapterToPublish = new AcceptedChapter(draftChapter.getChapterIndex(), draftChapter.getTitle(),
                draftChapter.getContent(), draftChapter.getDraftBook(), draftChapter.getAuthor());
        AcceptedChapter acceptedChapter = acceptedChapterRepo.save(chapterToPublish);

        draftChapter.setStatus(DraftChapterStatus.PUBLISHED);
        draftChapterRepo.save(draftChapter);
        return acceptedChapter;
    }

    @Override
    public EditorComment createEditorComment(int draftChapterId, String username, String content) {
        Review review = findReviewByChapterId(draftChapterId);
        User editor = findEditorByUsername(username);
        EditorComment editorComment = new EditorComment(content, review, editor);
        return editorCommentRepo.save(editorComment);
    }

    @Override
    public WriterComment createWriterComment(int draftChapterId, String username, String content) {
        Review review = findReviewByChapterId(draftChapterId);
        Writer writer = findWriterByUsername(username);
        WriterComment writerComment = new WriterComment(content, review, writer);
        return writerCommentRepo.save(writerComment);
    }

    @Override
    public List<CommentDTO> getCommentsByReviewId(int reviewId, boolean byAsc) {

        List<EditorComment> editorComments = editorCommentRepo.findByReviewId(reviewId);
        List<WriterComment> writerComments = writerCommentRepo.findByReviewId(reviewId);

        List<CommentDTO> comments = new ArrayList<>();

        addEditorCommentToDto(comments, editorComments);
        addWriterCommentToDto(comments, writerComments);

        if (byAsc) {
            comments.sort(Comparator.comparing(CommentDTO::timestamp));
        } else {
            comments.sort((c1, c2) -> c2.timestamp().compareTo(c1.timestamp()));
        }

        return comments;
    }

    @Override
    public PublishedBook publishDraftBook(int draftBookId) {
        DraftBook draftBook = findDraftBook(draftBookId);
        PublishedBook publishedBook = new PublishedBook(draftBook.getTitle(), draftBook.getGenre(),
                draftBook.getAuthors());
        return publishedBookRepo.save(publishedBook);
    }

    /*
     * This helper method is used to check whether the Author trying to update the draft book
     * is actually an author of the draft book.
     */
    private void validateAuthorOfDraftBook(DraftBook draftBook, Author author) {
        if (!draftBook.getAuthors().contains(author)) {
            throw new UnauthorizedException(author.getFullName() + " is not an author of the draft book.");
        }
    }

    /*
     * This helper method is used to check whether the Author trying to update the draft chapter
     * is actually an author of the draft chapter.
     */
    private void validateAuthorOfDraftChapter(DraftChapter draftChapter, Author author) {
        if (draftChapter.getAuthor().getId() != author.getId()) {
            throw new UnauthorizedException(author.getFullName() + " is not the author of the draft chapter.");
        }
    }

    private void addWriterCommentToDto(List<CommentDTO> comments, List<WriterComment> writerComments) {
        for (WriterComment comment : writerComments) {
            CommentDTO dto = new CommentDTO(comment.getContent(), comment.getCommenterType(),
                    comment.getCommenterUsername(), comment.getTimestamp());
            comments.add(dto);
        }
    }

    private void addEditorCommentToDto(List<CommentDTO> comments, List<EditorComment> editorComments) {
        for (EditorComment comment : editorComments) {
            CommentDTO dto = new CommentDTO(comment.getContent(), comment.getCommenterType(),
                    comment.getCommenterUsername(), comment.getTimestamp());
            comments.add(dto);
        }
    }

    private DraftChapter findDraftChapter(int draftChapterId) {
        return draftChapterRepo.findById(draftChapterId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Draft Chapter with id " + draftChapterId + " not found."));
    }

    private DraftBook findDraftBook(int draftBookId) {
        return draftBookRepo.findById(draftBookId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Draft Book with id " + draftBookId + " not found."));
    }

    private Writer findWriterByUsername(String username) {
        Writer writer = writerRepo.findByUsername(username);
        if (writer == null) {
            throw new EntityNotFoundException(
                    "Writer with username" + username + " not found.");
        }
        return writer;
    }

    private User findEditorByUsername(String username) {
        User editor = userRepo.findByUsername(username);
        if (editor == null) {
            throw new EntityNotFoundException(
                    "Editor with username" + username + " not found.");
        }
        return editor;
    }

    private Review findReviewByChapterId(int draftChapterId) {
        Review review = findDraftChapter(draftChapterId).getReview();
        if (review == null) {
            throw new EntityNotFoundException("Review for draft chapter with id " + draftChapterId + " not found.");
        }
        return review;
    }

    private Author getAuthorByWriterUsername(String username) {
        return findWriterByUsername(username).getAuthor();
    }

}
