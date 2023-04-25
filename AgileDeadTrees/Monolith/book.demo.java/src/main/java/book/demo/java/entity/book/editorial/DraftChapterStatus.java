package book.demo.java.entity.book.editorial;

public enum DraftChapterStatus {
    CREATED(false, false),
    PENDING_APPROVAL_FROM_EDITOR(true, false),
    PENDING_APPROVAL_FROM_AUTHOR(true, false),
    ACCEPTED(false, true),
    REJECTED(false, true),
    PUBLISHED(false, false);

    private final boolean isEditableByEditor;

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
