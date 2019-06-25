package company.pawelzielinski.safeplace.Classes;

public class Rating {
    public String userId;
    public int rating;


    public Rating(String userId, int rating) {
        this.userId = userId;
        this.rating = rating;
    }

    public Rating() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
