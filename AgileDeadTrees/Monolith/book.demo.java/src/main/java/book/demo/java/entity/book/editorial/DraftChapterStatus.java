package book.demo.java.entity.book.editorial;

public enum DraftChapterStatus {
    CREATED(false, false),
    PENDING_APPROVAL_FROM_EDITOR(true, false),
    PENDING_APPROVAL_FROM_AUTHOR(true, false),
    ACCEPTED(false, true),
    REJECTED(false, true),
    PUBLISHED(false, false);

    // Indicates whether the content of the draft chapter could be edited by an editor currently.
    // Only when the draftChapter is submitted by Writer for pending approval from an editor,
    // then an editor could edit the content of the draft chapter.
    private final boolean isEditableByEditor;

    // Indicates whether this status is the final approval status (accepted or rejected) from an editor.
    private final boolean isApprovalStatus;


    DraftChapterStatus(boolean isEditableByEditor, boolean isApprovalStatus) {
        this.isEditableByEditor = isEditableByEditor;
        this.isApprovalStatus = isApprovalStatus;
    }

    public boolean isEditableByEditor() {
        return isEditableByEditor;
    }

    public boolean isApprovalStatus() {
        return isApprovalStatus;
    }

}
