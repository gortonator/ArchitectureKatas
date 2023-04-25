package book.demo.java.service;

import book.demo.java.dto.CommentDTO;
import book.demo.java.dto.DraftChapterDTO;
import book.demo.java.entity.book.editorial.*;
import book.demo.java.entity.book.published.PublishedBook;

import java.util.List;

public interface EditorialService {

    DraftChapter createDraftChapter(DraftChapterDTO draftChapterDTO, int draftBookId, String username);

    DraftChapter editDraftChapterByAuthor(int draftChapterId, DraftChapterDTO draftChapterDTO, String username);

    DraftChapter editDraftChapterByEditor(int draftChapterId, DraftChapterDTO draftChapterDTO);

    DraftChapter submitDraftChapter(int draftChapterId, String writerUsername);

    DraftChapter setPendingContentStatusByAuthor(int draftChapterId, boolean isAccepted, String username);

    Review createReview(int draftChapterId, String editorUsername, String content);

    DraftChapter updateDraftChapterStatus(int draftChapterId, DraftChapterStatus status);

    AcceptedChapter publishDraftChapter(int draftChapterId);

    EditorComment createEditorComment(int draftChapterId, String username, String content);

    WriterComment createWriterComment(int draftChapterId, String username, String content);

    List<CommentDTO> getCommentsByReviewId(int reviewId, boolean byAsc);

    PublishedBook publishDraftBook(int draftBookId);
}
