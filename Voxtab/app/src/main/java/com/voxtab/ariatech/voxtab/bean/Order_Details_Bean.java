package com.voxtab.ariatech.voxtab.bean;

import com.voxtab.ariatech.voxtab.beanwebservice.File_Meta_JSON;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created by AriaTech on 4/14/2016.
 */
public class Order_Details_Bean {

    int order_id = 0;
    int server_Id = 0;
    String assignment_no = "";
    String client_instruction = "";
    String total_files = "";
    String service_type_id = "";
    String trans_type_id = "";
    String delivery_opt_id = "";
    String order_status_id = "";
    String vas_id = "";
    String time_slab_id = "";
    String invoice_type_id = "";
    String subject_of_file = "";
    String connection_type = "";
    String total_duration = "";
    String delivery_date = "";
    String transcription_link = "";
    String order_date = "";
    String modified_date = "";
    String total_fees = "";
    String order_placed_details = "";
    String order_complete_details = "";
    String days_excluded_tat = "";
    String output_format_id = "";
    int user_id = 0;
    int flag = 0;// Success = 1, Edit=2, Cancel=3;


    String orderConfirmDate = "";
    String orderDeliveryDate = "";
    String orderPlacedDate = "";

    String orderDeliveryInProgressDate = "";
    String orderRejectedDate = "";
    String orderCancelDate = "";
    String orderAudioPenDate = "";


    LinkedList<MyRecording> recList = new LinkedList<>();
    LinkedList<File_Meta_JSON> file_meta_jsonsList = new LinkedList<>();

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setAssignment_no(String assignment_no) {
        this.assignment_no = assignment_no;
    }

    public String getAssignment_no() {
        return assignment_no;
    }

    public void setClient_instruction(String client_instruction) {
        this.client_instruction = client_instruction;
    }

    public String getClient_instruction() {
        return client_instruction;
    }

    public void setTotal_files(String total_files) {
        this.total_files = total_files;
    }

    public String getTotal_files() {
        return total_files;
    }

    public void setService_type_id(String service_type_id) {
        this.service_type_id = service_type_id;
    }

    public String getService_type_id() {
        return service_type_id;
    }

    public void setTrans_type_id(String trans_type_id) {
        this.trans_type_id = trans_type_id;
    }

    public String getTrans_type_id() {
        return trans_type_id;
    }

    public void setDelivery_opt_id(String delivery_opt_id) {
        this.delivery_opt_id = delivery_opt_id;
    }

    public String getDelivery_opt_id() {
        return delivery_opt_id;
    }

    public void setOrder_status_id(String order_status_id) {
        this.order_status_id = order_status_id;
    }

    public String getOrder_status_id() {
        return order_status_id;
    }

    public void setVas_id(String vas_id) {
        this.vas_id = vas_id;
    }

    public String getVas_id() {
        return vas_id;
    }

    public void setTime_slab_id(String time_slab_id) {
        this.time_slab_id = time_slab_id;
    }

    public String getTime_slab_id() {
        return time_slab_id;
    }

    public void setInvoice_type_id(String invoice_type_id) {
        this.invoice_type_id = invoice_type_id;
    }

    public String getInvoice_type_id() {
        return invoice_type_id;
    }

    public void setSubject_of_file(String subject_of_file) {
        this.subject_of_file = subject_of_file;
    }

    public String getSubject_of_file() {
        return subject_of_file;
    }

    public void setConnection_type(String connection_type) {
        this.connection_type = connection_type;
    }

    public String getConnection_type() {
        return connection_type;
    }

    public void setTotal_duration(String total_duration) {
        this.total_duration = total_duration;
    }

    public String getTotal_duration() {
        return total_duration;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setTranscription_link(String transcription_link) {
        this.transcription_link = transcription_link;
    }

    public String getTranscription_link() {
        return transcription_link;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setTotal_fees(String total_fees) {
        this.total_fees = total_fees;
    }

    public String getTotal_fees() {
        return total_fees;
    }

    public void setOrder_placed_details(String order_placed_details) {
        this.order_placed_details = order_placed_details;
    }

    public String getOrder_placed_details() {
        return order_placed_details;
    }

    public void setOrder_complete_details(String order_complete_details) {
        this.order_complete_details = order_complete_details;
    }

    public String getOrder_complete_details() {
        return order_complete_details;
    }

    public void setDays_excluded_tat(String days_excluded_tat) {
        this.days_excluded_tat = days_excluded_tat;
    }

    public String getDays_excluded_tat() {
        return days_excluded_tat;
    }

    public void setOutput_format_id(String output_format_id) {
        this.output_format_id = output_format_id;
    }

    public String getOutput_format_id() {
        return output_format_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getServer_Id() {
        return server_Id;
    }

    public void setServer_Id(int server_Id) {
        this.server_Id = server_Id;
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

    public String getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public void setOrderPlacedDate(String orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }

    public void parseJSON(JSONObject jsonObject) {
        try {
            if (jsonObject.has("order_id")) {
                try {
                    order_id = Integer.parseInt(jsonObject.getString("order_id"));
                } catch (Exception e) {
                }
            }


            if (jsonObject.has("order_id")) {
                try {
                    server_Id = Integer.parseInt(jsonObject.getString("order_id"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("assignment_no")) {
                try {
                    assignment_no = jsonObject.getString("assignment_no");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("client_instruction")) {
                try {
                    client_instruction = jsonObject.getString("client_instruction");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("total_files")) {
                try {
                    total_files = jsonObject.getString("total_files");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("service_type_id")) {
                try {
                    service_type_id = jsonObject.getString("service_type_id");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("trans_type_id")) {
                try {
                    trans_type_id = jsonObject.getString("trans_type_id");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("delivery_opt_id")) {
                try {
                    delivery_opt_id = jsonObject.getString("delivery_opt_id");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("order_status_id")) {
                try {
                    order_status_id = jsonObject.getString("order_status_id");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("vas_id")) {
                try {
                    vas_id = jsonObject.getString("vas_id");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("time_slab_id")) {
                try {
                    time_slab_id = jsonObject.getString("time_slab_id");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("invoice_type_id")) {
                try {
                    invoice_type_id = jsonObject.getString("invoice_type_id");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("subject_of_file")) {
                try {
                    subject_of_file = jsonObject.getString("subject_of_file");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("connection_type")) {
                try {
                    connection_type = jsonObject.getString("connection_type");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("total_duration")) {
                try {
                    total_duration = jsonObject.getString("total_duration");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("delivery_date")) {
                try {
                    delivery_date = jsonObject.getString("delivery_date");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("transcription_link")) {
                try {
                    transcription_link = jsonObject.getString("transcription_link");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("order_date")) {
                try {
                    order_date = jsonObject.getString("order_date");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("modified_date")) {
                try {
                    modified_date = jsonObject.getString("modified_date");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("total_fees")) {
                try {
                    total_fees = jsonObject.getString("total_fees");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("order_placed_details")) {
                try {
                    order_placed_details = jsonObject.getString("order_placed_details");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("order_complete_details")) {
                try {
                    order_complete_details = jsonObject.getString("order_complete_details");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("days_excluded_tat")) {
                try {
                    days_excluded_tat = jsonObject.getString("days_excluded_tat");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("output_format_id")) {
                try {
                    output_format_id = jsonObject.getString("output_format_id");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("user_id")) {
                try {
                    user_id = Integer.parseInt(jsonObject.getString("user_id"));
                } catch (Exception e) {
                }
            }


            recList = new LinkedList<>();
            if (jsonObject.has("orders_files")) {
                try {
                    JSONArray orders_filesArray = (jsonObject.getJSONArray("orders_files"));

                    for (int i = 0; i < orders_filesArray.length(); i++) {
                        try {
                            JSONObject obj = orders_filesArray.getJSONObject(i);

                            MyRecording rec = new MyRecording();
                            rec.parseJSON(obj);
                            recList.add(rec);
                        } catch (Exception e) {
                            GlobalData.printError(e);
                        }

                    }


                } catch (Exception e) {
                }
            }

            file_meta_jsonsList = new LinkedList<>();
            if (jsonObject.has("orders_files")) {
                try {
                    JSONArray orders_filesArray = (jsonObject.getJSONArray("orders_files"));

                    for (int i = 0; i < orders_filesArray.length(); i++) {
                        try {
                            JSONObject obj = orders_filesArray.getJSONObject(i);

                            File_Meta_JSON rec = new File_Meta_JSON();
                            rec.parseJSON(obj);
                            file_meta_jsonsList.add(rec);
                        } catch (Exception e) {
                            GlobalData.printError(e);
                        }

                    }


                } catch (Exception e) {
                }
            }


            ///
            try {
                if (jsonObject.has("order_status_all")) {
                    try {
                        JSONArray orders_filesArray = (jsonObject.getJSONArray("orders_files"));

                        for (int i = 0; i < orders_filesArray.length(); i++) {
                            try {
                                JSONObject obj = orders_filesArray.getJSONObject(i);

                                if (obj.getString("status_id").equalsIgnoreCase("conf")) {
                                    orderConfirmDate = obj.getString("trans_date");
                                }


                                if (obj.getString("status_id").equalsIgnoreCase("del")) {
                                    orderDeliveryDate = obj.getString("trans_date");
                                }

                                if (obj.getString("status_id").equalsIgnoreCase("aud-pen")) {
                                    orderAudioPenDate = obj.getString("aud-pen");
                                }


                                if (obj.getString("status_id").equalsIgnoreCase("del-prog")) {
                                    orderDeliveryInProgressDate = obj.getString("trans_date");
                                }

                                if (obj.getString("status_id").equalsIgnoreCase("rej")) {
                                    orderRejectedDate = obj.getString("trans_date");
                                }


                                if (obj.getString("status_id").equalsIgnoreCase("can")) {
                                    orderCancelDate = obj.getString("trans_date");
                                }


                            } catch (Exception e) {
                                GlobalData.printError(e);
                            }

                        }


                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }
                }

            } catch (Exception e) {
                GlobalData.printError(e);
            }


        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }

    }


}
