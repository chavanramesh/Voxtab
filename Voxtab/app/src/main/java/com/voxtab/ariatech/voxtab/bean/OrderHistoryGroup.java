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
import java.util.ArrayList;
import java.util.List;


public class OrderHistoryGroup implements Serializable {

    private long id;
    private String assignment_num;
    private String status_title;
    private String order_status_icon;
    int order_id;
    private  String orderConfirmDate="";
    private  String orderDeliveryDate="";
    private  String orderPlacedDate="";

    OrderDetails orderDetails=new OrderDetails();



    private List<OrderHistoryChild> itemList = new ArrayList<OrderHistoryChild>();

    public OrderHistoryGroup(long id, String assignment_num, String status_title, String order_status_icon, int order_id,
                             String orderConfirmDate,
                             String orderDeliveryDate,String orderPlacedDate,OrderDetails orderDetails) {
        this.id = id;
        this.assignment_num = assignment_num;
        this.status_title = status_title;
        this.order_status_icon = order_status_icon;
        this.order_id=order_id;
        this.orderConfirmDate = orderConfirmDate;
        this.orderDeliveryDate = orderDeliveryDate;
        this.orderPlacedDate = orderPlacedDate;
        this.orderDetails =orderDetails;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOorder_id(int oorder_id) {
        this.order_id = oorder_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getAssignment_num() {
        return assignment_num;
    }

    public void setAssignment_num(String assignment_num) {
        this.assignment_num = assignment_num;
    }

    public String getStatus_title() {
        return status_title;
    }

    public void setStatus_title(String status_title) {
        this.status_title = status_title;
    }

    public String getOrder_status_icon() {
        return order_status_icon;
    }

    public void setOrder_status_icon(String order_status_icon) {
        this.order_status_icon = order_status_icon;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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

    public List<OrderHistoryChild> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderHistoryChild> itemList) {
        this.itemList = itemList;
    }

    public String getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public void setOrderPlacedDate(String orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }
}
