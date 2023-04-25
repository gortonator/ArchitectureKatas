package book.demo.java.dto;

import lombok.Data;

@Data
public class DraftChapterDTO {

    private Integer chapterIndex;
    private String title;
    private String content;

    public DraftChapterDTO(Integer chapterIndex, String title, String content) {
        this.chapterIndex = chapterIndex;
        this.title = title;
        this.content = content;
    }
}
