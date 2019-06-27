package company.pawelzielinski.safeplace.Classes;

public class Rating {
    public String userId;
    public Integer rating;


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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
