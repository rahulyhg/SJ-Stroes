package io.github.sanjay555.firebase_ex;

/**
 * Created by sanjayshr on 5/7/17.
 * Getter Setter
 * need this class for setting values to database
 */

public class GivenAmountDetails {

    String givenamountId;
    String givenGivenamountDate;
    Double todayGiveAmount;



    GivenAmountDetails(){

    }

    public GivenAmountDetails(String givenGivenamountDate, Double todayGiveAmount){
        this.givenamountId = givenamountId;
        this.givenGivenamountDate = givenGivenamountDate;
        this.todayGiveAmount = todayGiveAmount;
    }

    public String getGivenamountId() {
        return givenamountId;
    }

    public String getGivenGivenamountDate() {
        return givenGivenamountDate;
    }

    public Double getTodayGiveAmount() {
        return todayGiveAmount;
    }

    public void setGivenamountId(String givenamountId) {
        this.givenamountId = givenamountId;
    }

    public void setGivenGivenamountDate(String givenGivenamountDate) {
        this.givenGivenamountDate = givenGivenamountDate;
    }

    public void setTodayGiveAmount(Double todayGiveAmount) {
        this.todayGiveAmount = todayGiveAmount;
    }
}
