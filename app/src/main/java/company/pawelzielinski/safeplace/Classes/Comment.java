package company.pawelzielinski.safeplace.Classes;

public class Comment {
    public String comment;
    public String userId;

    public Comment(String comment, String userId) {
        this.comment = comment;
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
