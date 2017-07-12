// Getter Setter

package io.github.sanjay555.firebase_ex;

/**
 * Created by sanjayshr on 1/7/17.
 */

public class Customers {

    String customerId;
    String customerFName;
    String customerLName;
    String customerPhNo;
    String customerAddress;

    public Customers(){

    }

    public Customers(String customerId, String customerFName, String customerLName, String customerPhNo, String customerAddress){
        this.customerId = customerId;
        this.customerFName = customerFName;
        this.customerLName = customerLName;
        this.customerPhNo = customerPhNo;
        this.customerAddress = customerAddress;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerFName() {
        return customerFName;
    }

    public String getCustomerLName() {
        return customerLName;
    }

    public String getCustomerPhNo() {
        return customerPhNo;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setCustomerFName(String customerFName) {
        this.customerFName = customerFName;
    }

    public void setCustomerLName(String customerLName) {
        this.customerLName = customerLName;
    }

    public void setCustomerPhNo(String customerPhNo) {
        this.customerPhNo = customerPhNo;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
}
