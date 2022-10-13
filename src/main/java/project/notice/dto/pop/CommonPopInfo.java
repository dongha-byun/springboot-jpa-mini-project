package project.notice.dto.pop;

import lombok.Data;

@Data
public class CommonPopInfo {
    private final String message;
    private final String linkUrl;

    public static final String MODEL_NAME = "popInfo";
    public static final String POP_PAGE = "popup/commonAlert";
}
