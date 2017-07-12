
// Getter Setter

package io.github.sanjay555.firebase_ex;

/**
 * Created by sanjayshr on 2/7/17.
 */

public class PurchaseDetails {

     String purchaseId;
     String purchaseAmount;
     String dates;
    String custid;


//    Create blank constructor for retrieving the values

    /*public PurchaseDetails(String id, String purchaseAmount, String dates){

    }*/
    PurchaseDetails(){

    }



    public PurchaseDetails(String purchaseId, String purchaseAmount, String dates, String custid) {
        this.purchaseId = purchaseId;
        this.purchaseAmount = purchaseAmount;
        this.dates=dates;
        this.custid = custid;
    }




    public String getPurchaseId() {
        return purchaseId;
    }

    public String getPurchaseAmount() {
        return purchaseAmount;
    }

    public String getDates() {
        return dates;
    }

    public String getCustid() {
        return custid;
    }
}
