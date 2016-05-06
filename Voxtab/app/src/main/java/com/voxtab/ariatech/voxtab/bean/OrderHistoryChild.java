/*
 * Copyright (C) 2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.voxtab.ariatech.voxtab.bean;

import java.io.Serializable;

public class OrderHistoryChild implements Serializable {

    private long id;
    private String img_order_status;
    private String txt_status_detail, txt_order_date;
    private String img_upload_status;
    private String txt_upload_date;
    private String img_delivered_status;
    private String txt_delivered_status_details, txt_delivery_date;
    private String txt_total_fee, txt_delivered_date;


    private  String orderConfirmDate="";
    private  String orderDeliveryDate="";

    private OrderDetails orderDetails=new OrderDetails();


    public OrderHistoryChild(long id, String txt_total_fee, String txt_delivery_date, String img_order_status,
                             String txt_status_detail, String txt_order_date, String img_upload_status,
                             String txt_upload_date, String img_delivered_status,
                             String txt_delivered_status_details, String txt_delivered_date,
                             String orderConfirmDate,
                             String orderDeliveryDate,OrderDetails orderDetails



    ) {
        this.id = id;
        this.img_order_status = img_order_status;
        this.txt_status_detail = txt_status_detail;
        this.txt_order_date = txt_order_date;
        this.img_upload_status = img_upload_status;
        this.txt_upload_date = txt_upload_date;
        this.img_delivered_status = img_delivered_status;
        this.txt_delivered_status_details = txt_delivered_status_details;
        this.txt_delivered_date = txt_delivered_date;
        this.txt_delivery_date = txt_delivery_date;
        this.txt_total_fee = txt_total_fee;

        this.orderConfirmDate = orderConfirmDate;
        this.orderDeliveryDate = orderDeliveryDate;
        this.orderDetails=orderDetails;

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg_order_status() {
        return img_order_status;
    }

    public void setImg_order_status(String img_order_status) {
        this.img_order_status = img_order_status;
    }


    public String getTxt_status_detail() {
        return txt_status_detail;
    }

    public void setTxt_status_detail(String txt_status_detail) {
        this.txt_status_detail = txt_status_detail;
    }

    public String getTxt_order_date() {
        return txt_order_date;
    }

    public void setTxt_order_date(String txt_order_date) {
        this.txt_order_date = txt_order_date;
    }

    public String getImg_upload_status() {
        return img_upload_status;
    }

    public void setImg_upload_status(String img_upload_status) {
        this.img_upload_status = img_upload_status;
    }


    public String getTxt_upload_date() {
        return txt_upload_date;
    }

    public void setTxt_upload_date(String txt_upload_date) {
        this.txt_upload_date = txt_upload_date;
    }

    public String getImg_delivered_status() {
        return img_delivered_status;
    }

    public void setImg_delivered_status(String img_delivered_status) {
        this.img_delivered_status = img_delivered_status;
    }


    public String getTxt_delivered_status_details() {
        return txt_delivered_status_details;
    }

    public void setTxt_delivered_status_details(String txt_delivered_status_details) {
        this.txt_delivered_status_details = txt_delivered_status_details;
    }

    public String getTxt_delivery_date() {
        return txt_delivery_date;
    }

    public void setTxt_delivery_date(String txt_delivery_date) {
        this.txt_delivery_date = txt_delivery_date;
    }

    public String getTxt_total_fee() {
        return txt_total_fee;
    }

    public void setTxt_total_fee(String txt_total_fee) {
        this.txt_total_fee = txt_total_fee;
    }

    public String getTxt_delivered_date() {
        return txt_delivered_date;
    }

    public void setTxt_delivered_date(String txt_delivered_date) {
        this.txt_delivered_date = txt_delivered_date;
    }

    public String getOrderConfirmDate() {
        return orderConfirmDate;
    }

    public void setOrderConfirmDate(String orderConfirmDate) {
        this.orderConfirmDate = orderConfirmDate;
    }

    public String getOrderDeliveryDate() {
        return orderDeliveryDate;
    }

    public void setOrderDeliveryDate(String orderDeliveryDate) {
        this.orderDeliveryDate = orderDeliveryDate;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }
}
