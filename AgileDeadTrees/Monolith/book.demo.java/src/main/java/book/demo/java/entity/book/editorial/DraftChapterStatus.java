package book.demo.java.entity.book.editorial;

public enum DraftChapterStatus {
    CREATED(false, false),
    PENDING_APPROVAL_FROM_EDITOR(true, false),
    PENDING_APPROVAL_FROM_AUTHOR(true, false),
    ACCEPTED(false, true),
    REJECTED(false, true),
    PUBLISHED(false, false);

    // isEditableByEditor indicates whether the content of the draft chapter could be edited by an editor currently
    private final boolean isEditableByEditor;

    // isApprovalStatus indicates whether this status is the final approval status from an editor
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
