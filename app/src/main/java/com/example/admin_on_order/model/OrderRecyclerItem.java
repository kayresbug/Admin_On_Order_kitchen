package com.example.admin_on_order.model;

import android.util.Log;

import java.util.ArrayList;

public class OrderRecyclerItem {

    private String orderCode;
    private String menuNo;
    private String tableNo;
    private ArrayList<OrderModel> menu;
    private String count;
    private String price;
    //결제내역
    private String paymentStatus;
    //주문내역
    private String checkStatus;
    //결제관리
    private String paymentType;
    private String orderTime;
    private String authNum;
    private String authDate;
    private String vanTr;
    private String cardBin;
    private String dptId;
    private String printStatus;
    private String key;

    public OrderRecyclerItem(String tableNo, ArrayList<OrderModel> menu, String count, String price, String checkStatus, String orderTime, String menuNo) {
        this.tableNo = tableNo;
        this.menu = menu;
        this.count = count;
        this.price = price;
        this.checkStatus = checkStatus;
        this.orderTime = orderTime;
        this.menuNo = menuNo;
    }

    public OrderRecyclerItem(String tableNo, ArrayList<OrderModel> menu, String count, String price,
                             String paymentStatus, String paymentType, String orderTime, String authNum,
                             String authDate, String vanTr, String cardBin, String dptId) {
        this.tableNo = tableNo;
        this.menu = menu;
        this.count = count;
        this.price = price;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;
        this.orderTime = orderTime;
        this.authNum = authNum;
        this.authDate = authDate;
        this.vanTr = vanTr;
        this.cardBin = cardBin;
        this.dptId = dptId;
    }

    public OrderRecyclerItem(String tableNo, ArrayList<OrderModel> menu, String count, String price,
                             String paymentStatus, String paymentType, String orderTime, String authNum,
                             String authDate, String vanTr, String cardBin, String dptId, String printStatus) {
        this.tableNo = tableNo;
        this.menu = menu;
        this.count = count;
        this.price = price;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;
        this.orderTime = orderTime;
        this.authNum = authNum;
        this.authDate = authDate;
        this.vanTr = vanTr;
        this.cardBin = cardBin;
        this.dptId = dptId;
        this.printStatus = printStatus;
    }

    public String getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(String printStatus) {
        this.printStatus = printStatus;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public ArrayList<OrderModel> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<OrderModel> menu) {
        this.menu = menu;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getAuthNum() {
        return authNum;
    }

    public void setAuthNum(String authNum) {
        this.authNum = authNum;
    }

    public String getAuthDate() {
        return authDate;
    }

    public void setAuthDate(String authDate) {
        this.authDate = authDate;
    }

    public String getVanTr() {
        return vanTr;
    }

    public void setVanTr(String vanTr) {
        this.vanTr = vanTr;
    }

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }

    public String getDptId() {
        return dptId;
    }

    public void setDptId(String dptId) {
        this.dptId = dptId;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(String menuNo) {
        this.menuNo = menuNo;
    }
}
