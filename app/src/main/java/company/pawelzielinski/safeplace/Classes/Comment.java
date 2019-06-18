package company.pawelzielinski.safeplace.Classes;

import android.content.Context;

public class Comment {
    public String comment;
    public String userId;
    public String username;

    public Comment() {

    }

    public Comment(String comment, String userId, String username) {
        this.comment = comment;
        this.userId = userId;
        this.username = username;

    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
