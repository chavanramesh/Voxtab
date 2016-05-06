package com.voxtab.ariatech.voxtab.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.voxtab.ariatech.voxtab.bean.Access_elements;
import com.voxtab.ariatech.voxtab.bean.Assessment_report;
import com.voxtab.ariatech.voxtab.bean.Assessment_speaker;
import com.voxtab.ariatech.voxtab.bean.Company_info;
import com.voxtab.ariatech.voxtab.bean.Company_location;
import com.voxtab.ariatech.voxtab.bean.Core_group;
import com.voxtab.ariatech.voxtab.bean.Core_user;
import com.voxtab.ariatech.voxtab.bean.Delivery_option;
import com.voxtab.ariatech.voxtab.bean.Delivery_slot;
import com.voxtab.ariatech.voxtab.bean.Delivery_speed;
import com.voxtab.ariatech.voxtab.bean.Discount;
import com.voxtab.ariatech.voxtab.bean.Discount_type;
import com.voxtab.ariatech.voxtab.bean.Eng_local_resources;
import com.voxtab.ariatech.voxtab.bean.Error_log;
import com.voxtab.ariatech.voxtab.bean.Feedback;
import com.voxtab.ariatech.voxtab.bean.File_source_type;
import com.voxtab.ariatech.voxtab.bean.File_status_type;
import com.voxtab.ariatech.voxtab.bean.Free_services;
import com.voxtab.ariatech.voxtab.bean.Holidays;
import com.voxtab.ariatech.voxtab.bean.Invoice_type;
import com.voxtab.ariatech.voxtab.bean.Local_resources;
import com.voxtab.ariatech.voxtab.bean.Mail_config;
import com.voxtab.ariatech.voxtab.bean.Msg_logs;
import com.voxtab.ariatech.voxtab.bean.Msg_type_logs;
import com.voxtab.ariatech.voxtab.bean.MyRecording;
import com.voxtab.ariatech.voxtab.bean.Notification;
import com.voxtab.ariatech.voxtab.bean.Notification_type;
import com.voxtab.ariatech.voxtab.bean.OrderDetails;
import com.voxtab.ariatech.voxtab.bean.Order_free_services;
import com.voxtab.ariatech.voxtab.bean.Order_media;
import com.voxtab.ariatech.voxtab.bean.Order_recording;
import com.voxtab.ariatech.voxtab.bean.Order_transaction;
import com.voxtab.ariatech.voxtab.bean.Order_vas;
import com.voxtab.ariatech.voxtab.bean.Output_format;
import com.voxtab.ariatech.voxtab.bean.Pages;
import com.voxtab.ariatech.voxtab.bean.Premium_type;
import com.voxtab.ariatech.voxtab.bean.Price;
import com.voxtab.ariatech.voxtab.bean.Recording;
import com.voxtab.ariatech.voxtab.bean.Security_permission;
import com.voxtab.ariatech.voxtab.bean.Service_type;
import com.voxtab.ariatech.voxtab.bean.Status_type;
import com.voxtab.ariatech.voxtab.bean.Sys_log;
import com.voxtab.ariatech.voxtab.bean.Tat_fee;
import com.voxtab.ariatech.voxtab.bean.TimeStamb;
import com.voxtab.ariatech.voxtab.bean.Time_slab;
import com.voxtab.ariatech.voxtab.bean.Timestamps_cal;
import com.voxtab.ariatech.voxtab.bean.Transcription_type;
import com.voxtab.ariatech.voxtab.bean.User_device;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.bean.VAS_Rate;
import com.voxtab.ariatech.voxtab.bean.Vas;
import com.voxtab.ariatech.voxtab.beanwebservice.File_Meta_JSON;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import java.io.File;
import java.sql.SQLException;
import java.util.LinkedList;

public class DatabaseHandlerNew {
    public static final String DATABASE_NAME = "voxtab_new.db";
    public static final int DATABASE_VERSION = 1;
    private static String DB_PATH = "/data/data/com.voxtab.ariatech.voxtab/databases/";
    public static final String ROW_COUNT = "count";

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public String softDeleChk= " soft_del !=0 ";
    public String whrsoftDeleChk= " where soft_del !=0 ";


    private class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub

           /* try {
                copyDataBase();
                GlobalData.printMessage("Database Created");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                GlobalData.printError(e);
            }*/

            LinkedList<String> sqlList = CreateStatements.getCreateStatements(DATABASE_VERSION);

            for (int i = 0; i < sqlList.size(); i++) {

                try {
                    db.execSQL(sqlList.get(i));
                } catch (Exception e) {
                    GlobalData.printError(e);
                }
            }
        }




        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }

    }

    public DatabaseHandlerNew(Context c) {
        ourContext = c;
    }

    public DatabaseHandlerNew open() throws SQLException {
        try {
            ourHelper = new DbHelper(ourContext);
            // ourHelper.createDataBase();
            ourDatabase = ourHelper.getWritableDatabase();
            GlobalData
                    .printMessage("DATA BASE FILE : " + ourDatabase.getPath());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            GlobalData.printError(e);
        }
        return this;
        // return null;
    }

    public void close() {
        ourHelper.close();
    }


    // Clear Method
    public String setClearData(String string) {

        if (string == null) {

            string = "";

        } else if (string.equalsIgnoreCase("null")) {
            string = "";
        }

        // if(string.contains("\"")){
        // GlobalData.printMessage("Old String:"+string);
        // string=string.replace("\"", "\"");
        // }

        if (string.contains("'")) {
            string = string.replace("'", "''");
        }

        return string.trim();

    }

    public String addClearData(String string) {

        GlobalData.printMessage("NULL String:" + string);

        if (string == null) {

            GlobalData.printMessage("String: object is NULL:" + string);

            string = "";
            GlobalData.printMessage("String: object is NULL:" + string);

        } else if (string.equalsIgnoreCase("null")) {
            string = "";
            GlobalData.printMessage("String is NULL String: " + string);
        } else {

        }

        if (string.contains("'")) {
            string = string.replace("'", "''");
        }

        return string.trim();

    }

    public String getClearData(String string) {

        GlobalData.printMessage("NULL String:" + string);

        if (string == null) {

            GlobalData.printMessage("String: object is NULL:" + string);

            string = "";
            GlobalData.printMessage("String: object is NULL:" + string);

        } else {

        }

        if (string.equalsIgnoreCase("null")) {
            string = "";
            GlobalData.printMessage("String is NULL String: " + string);
        } else {

        }

//		string = string.replace("-", " ");

        return string.trim();

    }


    //VAS Price
    public static final String VAS_KEY_Vas_Rate = "vas_rate";
    public static final String VAS_KEY_TimeSlabId = "time_slab_id";
    public static final String VAS_KEY_VASId = "vas_id";
    public static final String VAS_KEY_TAT = "tat";
    public static final String VAS_KEY_Price = "price";
    public static final String VAS_KEY_Min_Charges = "min_charges";
    public static final String VAS_KEY_Modified_date = "modified_date";
    public static final String VAS_KEY_Created_date = "created_date";
    public static final String VAS_KEY_Soft_Del = "soft_del";


    //SchemeDetails
    public static final String Price = "price";
    public static final String Date_Time = "date_time";
    public static final String Scheme_Name = "scheme_name";
    public static final String Scheme_Duration = "scheme_duration";


    // Access_elements
    public static final String Access_elements_KEY_elm_id = "elm_id";
    public static final String Access_elements_KEY_elm_name = "elm_name";
    public static final String Access_elements_KEY_elm_created_on = "elm_created_on";


    // Assessment_report
    public static final String Assessment_report_KEY_ass_report_id = "ass_report_id";
    public static final String Assessment_report_KEY_order_id = "order_id";
    public static final String Assessment_report_KEY_ass_date = "ass_date";
    public static final String Assessment_report_KEY_audio_quality = "audio_quality";
    public static final String Assessment_report_KEY_subjectarea = "subjectarea";
    public static final String Assessment_report_KEY_result = "result";
    public static final String Assessment_report_KEY_notesfrom = "notesfrom";
    public static final String Assessment_report_KEY_notesfrom_trasaction = "notesfrom_trasaction";


    // Assessment_speaker
    public static final String Assessment_speaker_KEY_ass_speaker_id = "ass_speaker_id";
    public static final String Assessment_speaker_KEY_ass_report_id = "ass_report_id";
    public static final String Assessment_speaker_KEY_speaker_name = "speaker_name";


    // Company_info
    public static final String Company_info_KEY_membership_id = "membership_id";
    public static final String Company_info_KEY_org_name_eng = "org_name_eng";
    public static final String Company_info_KEY_org_name_fn = "org_name_fn";
    public static final String Company_info_KEY_status = "status";
    public static final String Company_info_KEY_client_type = "client_type";
    public static final String Company_info_KEY_date_of_invoice = "date_of_invoice";
    public static final String Company_info_KEY_name_of_invoice = "name_of_invoice";
    public static final String Company_info_KEY_business_type = "business_type";
    public static final String Company_info_KEY_template = "template";
    public static final String Company_info_KEY_client_instruction = "client_instruction";
    public static final String Company_info_KEY_payment_instruction = "payment_instruction";
    public static final String Company_info_KEY_modified_date = "modified_date";
    public static final String Company_info_KEY_created_date = "created_date";
    public static final String Company_info_KEY_soft_del = "soft_del";


    // Company_location
    public static final String Company_location_KEY_client_location_id = "client_location_id";
    public static final String Company_location_KEY_membership_id = "membership_id";
    public static final String Company_location_KEY_city = "city";
    public static final String Company_location_KEY_state = "state";
    public static final String Company_location_KEY_country = "country";
    public static final String Company_location_KEY_pincode = "pincode";
    public static final String Company_location_KEY_landline = "landline";
    public static final String Company_location_KEY_email = "email";
    public static final String Company_location_KEY_mobile = "mobile";
    public static final String Company_location_KEY_website = "website";
    public static final String Company_location_KEY_currency = "currency";
    public static final String Company_location_KEY_address = "address";
    public static final String Company_location_KEY_modified_date = "modified_date";
    public static final String Company_location_KEY_created_date = "created_date";
    public static final String Company_location_KEY_soft_del = "soft_del";


    // Core_group
    public static final String Core_group_KEY_group_id = "group_id";
    public static final String Core_group_KEY_group_name = "group_name";
    public static final String Core_group_KEY_arr_group_name = "arr_group_name";
    public static final String Core_group_KEY_pos_level = "pos_level";
    public static final String Core_group_KEY_pos_created = "pos_created";


    // Core_user
    public static final String Core_user_KEY_user_id = "user_id";
    public static final String Core_user_KEY_group_id = "group_id";
    public static final String Core_user_KEY_cat_id = "cat_id";
    public static final String Core_user_KEY_username = "username";
    public static final String Core_user_KEY_last_name = "last_name";
    public static final String Core_user_KEY_password = "password";
    public static final String Core_user_KEY_first_name = "first_name";
    public static final String Core_user_KEY_join_date = "join_date";
    public static final String Core_user_KEY_photo = "photo";
    public static final String Core_user_KEY_status = "status";
    public static final String Core_user_KEY_app_user_status = "app_user_status";
    public static final String Core_user_KEY_email = "email";
    public static final String Core_user_KEY_gender = "gender";
    public static final String Core_user_KEY_mobile = "mobile";
    public static final String Core_user_KEY_auth_key = "auth_key";
    public static final String Core_user_KEY_reg_date = "reg_date";
    public static final String Core_user_KEY_modified_date = "modified_date";
    public static final String Core_user_KEY_user_type = "user_type";
    public static final String Core_user_KEY_session_key = "session_key";
    public static final String Core_user_KEY_dob = "dob";
    public static final String Core_user_KEY_city = "city";


    // Delivery_option
    public static final String Delivery_option_KEY_delivery_opt_id = "delivery_opt_id";
    public static final String Delivery_option_KEY_del_days = "del_days";
    public static final String Delivery_option_KEY_del_option = "del_option";
    public static final String Delivery_option_KEY_status = "status";
    public static final String Delivery_option_KEY_created_date = "created_date";
    public static final String Delivery_option_KEY_modified_date = "modified_date";
    public static final String Delivery_option_KEY_soft_del = "soft_del";


    // Delivery_slot
    public static final String Delivery_slot_KEY_deliveryslot_id = "deliveryslot_id";
    public static final String Delivery_slot_KEY_slot_from = "slot_from";
    public static final String Delivery_slot_KEY_slot_to = "slot_to";
    public static final String Delivery_slot_KEY_modified_date = "modified_date";
    public static final String Delivery_slot_KEY_created_date = "created_date";
    public static final String Delivery_slot_KEY_soft_del = "soft_del";


    // Delivery_speed
    public static final String Delivery_speed_KEY_delivery_speed_id = "delivery_speed_id";
    public static final String Delivery_speed_KEY_service_type_id = "service_type_id";
    public static final String Delivery_speed_KEY_time_slab_id = "time_slab_id";
    public static final String Delivery_speed_KEY_delivery_option_id = "delivery_option_id";
    public static final String Delivery_speed_KEY_rate = "rate";
    public static final String Delivery_speed_KEY_status = "status";
    public static final String Delivery_speed_KEY_created_date = "created_date";
    public static final String Delivery_speed_KEY_modified_date = "modified_date";
    public static final String Delivery_speed_KEY_soft_del = "soft_del";


    // Discount
    public static final String Discount_KEY_discount_id = "discount_id";
    public static final String Discount_KEY_discount_type_id = "discount_type_id";
    public static final String Discount_KEY_service_type_id = "service_type_id";
    public static final String Discount_KEY_delivery_opt_id = "delivery_opt_id";
    public static final String Discount_KEY_vas_id = "vas_id";
    public static final String Discount_KEY_min_time = "min_time";
    public static final String Discount_KEY_max_time = "max_time";
    public static final String Discount_KEY_start_date = "start_date";
    public static final String Discount_KEY_end_date = "end_date";
    public static final String Discount_KEY_discount = "discount";
    public static final String Discount_KEY_status = "status";
    public static final String Discount_KEY_modified_date = "modified_date";
    public static final String Discount_KEY_created_date = "created_date";
    public static final String Discount_KEY_soft_del = "soft_del";


    // Discount_type
    public static final String Discount_type_KEY_discount_type_id = "discount_type_id";
    public static final String Discount_type_KEY_discount_txt = "discount_txt";
    public static final String Discount_type_KEY_modified_date = "modified_date";
    public static final String Discount_type_KEY_created_date = "created_date";
    public static final String Discount_type_KEY_soft_del = "soft_del";


    // Eng_local_resources
    public static final String Eng_local_resources_KEY_resource_id = "resource_id";
    public static final String Eng_local_resources_KEY_resource_name = "resource_name";
    public static final String Eng_local_resources_KEY_resource_value = "resource_value";
    public static final String Eng_local_resources_KEY_soft_del = "soft_del";
    public static final String Eng_local_resources_KEY_created_date = "created_date";
    public static final String Eng_local_resources_KEY_modified_date = "modified_date";


    // Error_log
    public static final String Error_log_KEY_logid = "logid";
    public static final String Error_log_KEY_logtime = "logtime";
    public static final String Error_log_KEY_username = "username";
    public static final String Error_log_KEY_dbname = "dbname";
    public static final String Error_log_KEY_actionmsg = "actionmsg";
    public static final String Error_log_KEY_errormsg = "errormsg";


    // Feedback
    public static final String Feedback_KEY_feedback_id = "feedback_id";
    public static final String Feedback_KEY_user_id = "user_id";
    public static final String Feedback_KEY_order_id = "order_id";
    public static final String Feedback_KEY_feed_txt = "feed_txt";
    public static final String Feedback_KEY_created_date = "created_date";
    public static final String Feedback_KEY_feedback_type = "feedback_type";
    public static final String Feedback_KEY_assignment_no = "assignment_no";
    public static final String Feedback_KEY_files_nams = "files_nams";


    // File_source_type
    public static final String File_source_type_KEY_source_type = "source_type";
    public static final String File_source_type_KEY_source_name = "source_name";
    public static final String File_source_type_KEY_active = "active";
    public static final String File_source_type_KEY_soft_del = "soft_del";
    public static final String File_source_type_KEY_created_date = "created_date";
    public static final String File_source_type_KEY_modified_date = "modified_date";


    // File_status_type
    public static final String File_status_type_KEY_file_status_msg = "file_status_msg";
    public static final String File_status_type_KEY_file_status = "file_status";
    public static final String File_status_type_KEY_is_active = "is_active";
    public static final String File_status_type_KEY_sort_order = "sort_order";
    public static final String File_status_type_KEY_soft_del = "soft_del";
    public static final String File_status_type_KEY_modified_date = "modified_date";
    public static final String File_status_type_KEY_created_date = "created_date";


    // Free_services
    public static final String Free_services_KEY_free_service_id = "free_service_id";
    public static final String Free_services_KEY_free_service_txt = "free_service_txt";
    public static final String Free_services_KEY_default_set = "default_set";
    public static final String Free_services_KEY_service_flag = "service_flag";
    public static final String Free_services_KEY_trans_flag = "trans_flag";
    public static final String Free_services_KEY_timeslab_flag = "timeslab_flag";
    public static final String Free_services_KEY_vas_flag = "vas_flag";
    public static final String Free_services_KEY_status = "status";
    public static final String Free_services_KEY_created_date = "created_date";
    public static final String Free_services_KEY_modified_date = "modified_date";
    public static final String Free_services_KEY_soft_del = "soft_del";


    // Holidays
    public static final String Holidays_KEY_holiday_id = "holiday_id";
    public static final String Holidays_KEY_holiday_date = "holiday_date";
    public static final String Holidays_KEY_year = "year";
    public static final String Holidays_KEY_month = "month";
    public static final String Holidays_KEY_day = "day";
    public static final String Holidays_KEY_status = "status";
    public static final String Holidays_KEY_soft_del = "soft_del";
    public static final String Holidays_KEY_created_date = "created_date";
    public static final String Holidays_KEY_modified_date = "modified_date";


    // Invoice_type
    public static final String Invoice_type_KEY_invoice_type_id = "invoice_type_id";
    public static final String Invoice_type_KEY_invoice_txt = "invoice_txt";
    public static final String Invoice_type_KEY_created_date = "created_date";
    public static final String Invoice_type_KEY_modified_date = "modified_date";
    public static final String Invoice_type_KEY_soft_del = "soft_del";

    // Recording
    public static final String Recording_KEY_recId = "recId";
    public static final String Recording_KEY_serverId = "serverId";
    public static final String Recording_KEY_userId = "userId";
    public static final String Recording_KEY_upMasterId = "upMasterId";
    public static final String Recording_KEY_sourceTypeId = "sourceTypeId";
    public static final String Recording_KEY_sourceLink = "sourceLink";
    public static final String Recording_KEY_recName = "recName";
    public static final String Recording_KEY_recDesc = "recDesc";
    public static final String Recording_KEY_recDuration = "recDuration";
    public static final String Recording_KEY_recLocalPath = "recLocalPath";
    public static final String Recording_KEY_recSize = "recSize";
    public static final String Recording_KEY_recUploadDuration = "recUploadDuration";
    public static final String Recording_KEY_uploadingConnectionMode = "uploadingConnectionMode";
    public static final String Recording_KEY_createdDate = "createdDate";



    // Local_resources
    public static final String Local_resources_KEY_resource_id = "resource_id";
    public static final String Local_resources_KEY_lng_id = "lng_id";
    public static final String Local_resources_KEY_resource_name = "resource_name";
    public static final String Local_resources_KEY_resource_value = "resource_value";
    public static final String Local_resources_KEY_soft_del = "soft_del";
    public static final String Local_resources_KEY_created_date = "created_date";
    public static final String Local_resources_KEY_modified_date = "modified_date";


    // Mail_config
    public static final String Mail_config_KEY_config_id = "config_id";
    public static final String Mail_config_KEY_user_id = "user_id";
    public static final String Mail_config_KEY_purpose_name = "purpose_name";
    public static final String Mail_config_KEY_mail_server = "mail_server";
    public static final String Mail_config_KEY_port = "port";
    public static final String Mail_config_KEY_username = "username";
    public static final String Mail_config_KEY_email = "email";
    public static final String Mail_config_KEY_password = "password";
    public static final String Mail_config_KEY_alt_email = "alt_email";
    public static final String Mail_config_KEY_mail_ssl = "mail_ssl";
    public static final String Mail_config_KEY_alert = "alert";
    public static final String Mail_config_KEY_subject = "subject";
    public static final String Mail_config_KEY_body = "body";
    public static final String Mail_config_KEY_other_email = "other_email";


    // Msg_logs
    public static final String Msg_logs_KEY_log_id = "log_id";
    public static final String Msg_logs_KEY_user_id = "user_id";
    public static final String Msg_logs_KEY_client_id = "client_id";
    public static final String Msg_logs_KEY_app_id = "app_id";
    public static final String Msg_logs_KEY_msg = "msg";
    public static final String Msg_logs_KEY_msg_date = "msg_date";
    public static final String Msg_logs_KEY_title = "title";


    // Msg_type_logs
    public static final String Msg_type_logs_KEY_ref_id = "ref_id";
    public static final String Msg_type_logs_KEY_log_id = "log_id";
    public static final String Msg_type_logs_KEY_type_id = "type_id";


    // Notification
    public static final String Notification_KEY_noti_id = "noti_id";
    public static final String Notification_KEY_noti_type = "noti_type";
    public static final String Notification_KEY_notifi_txt = "notifi_txt";
    public static final String Notification_KEY_order_id = "order_id";
    public static final String Notification_KEY_status = "status";
    public static final String Notification_KEY_created_date = "created_date";
    public static final String Notification_KEY_assignment_no = "assignment_no";
    public static final String Notification_KEY_soft_del = "soft_del";


    // Notification_type
    public static final String Notification_type_KEY_noti_type_id = "noti_type_id";
    public static final String Notification_type_KEY_notification_name = "notification_name";
    public static final String Notification_type_KEY_template_desc = "template_desc";
    public static final String Notification_type_KEY_created_on = "created_on";
    public static final String Notification_type_KEY_modified_date = "modified_date";
    public static final String Notification_type_KEY_soft_del = "soft_del";


    // OrderDetails
    public static final String OrderDetails_KEY_order_id = "order_id";
    public static final String OrderDetails_KEY_assignment_no = "assignment_no";
    public static final String OrderDetails_KEY_client_instruction = "client_instruction";
    public static final String OrderDetails_KEY_total_files = "total_files";
    public static final String OrderDetails_KEY_service_type_id = "service_type_id";
    public static final String OrderDetails_KEY_trans_type_id = "trans_type_id";
    public static final String OrderDetails_KEY_delivery_opt_id = "delivery_opt_id";
    public static final String OrderDetails_KEY_order_status_id = "order_status_id";
    public static final String OrderDetails_KEY_vas_id = "vas_id";
    public static final String OrderDetails_KEY_time_slab_id = "time_slab_id";
    public static final String OrderDetails_KEY_invoice_type_id = "invoice_type_id";
    public static final String OrderDetails_KEY_subject_of_file = "subject_of_file";
    public static final String OrderDetails_KEY_connection_type = "connection_type";
    public static final String OrderDetails_KEY_total_duration = "total_duration";
    public static final String OrderDetails_KEY_delivery_date = "delivery_date";
    public static final String OrderDetails_KEY_transcription_link = "transcription_link";
    public static final String OrderDetails_KEY_order_date = "order_date";
    public static final String OrderDetails_KEY_modified_date = "modified_date";
    public static final String OrderDetails_KEY_total_fees = "total_fees";
    public static final String OrderDetails_KEY_order_placed_details = "order_placed_details";
    public static final String OrderDetails_KEY_order_complete_details = "order_complete_details";
    public static final String OrderDetails_KEY_days_excluded_tat = "days_excluded_tat";
    public static final String OrderDetails_KEY_output_format_id = "output_format_id";
    public static final String OrderDetails_KEY_user_id = "user_id";
    public static final String OrderDetails_KEY_flag = "flag";


    // Order_free_services
    public static final String Order_free_services_KEY_order_free_serid = "order_free_serid";
    public static final String Order_free_services_KEY_order_id = "order_id";
    public static final String Order_free_services_KEY_free_serv_id = "free_serv_id";
    public static final String Order_free_services_KEY_modified_date = "modified_date";


    // Order_media
    public static final String Order_media_KEY_order_media_id = "order_media_id";
    public static final String Order_media_KEY_user_id = "user_id";
    public static final String Order_media_KEY_order_id = "order_id";
    public static final String Order_media_KEY_file_status = "file_status";
    public static final String Order_media_KEY_source_type = "source_type";
    public static final String Order_media_KEY_sourcelink = "sourcelink";
    public static final String Order_media_KEY_file_name = "file_name";
    public static final String Order_media_KEY_file_description = "file_description";
    public static final String Order_media_KEY_file_duration = "file_duration";
    public static final String Order_media_KEY_file_localpath = "file_localpath";
    public static final String Order_media_KEY_file_size = "file_size";
    public static final String Order_media_KEY_file_upload_duration = "file_upload_duration";
    public static final String Order_media_KEY_file_up_conn_mode = "file_up_conn_mode";
    public static final String Order_media_KEY_created_date = "created_date";
    public static final String Order_media_KEY_modified_date = "modified_date";


    // Order_recording
    public static final String Order_recording_KEY_order_rec_id = "order_rec_id";
    public static final String Order_recording_KEY_order_id = "order_id";
    public static final String Order_recording_KEY_recording_id = "recording_id";


    // Order_transaction
    public static final String Order_transaction_KEY_order_trasnaction_id = "order_trasnaction_id";
    public static final String Order_transaction_KEY_order_id = "order_id";
    public static final String Order_transaction_KEY_rec_id = "rec_id";
    public static final String Order_transaction_KEY_status_id = "status_id";
    public static final String Order_transaction_KEY_trans_date = "trans_date";


    // Order_vas
    public static final String Order_vas_KEY_order_vas_id = "order_vas_id";
    public static final String Order_vas_KEY_order_id = "order_id";
    public static final String Order_vas_KEY_vas_id = "vas_id";


    //timestampcalculation

    public static final String TimeStampCalculation_KEY_timeStampCalculationId = "timestamp_calculation_id";
    public static final String TimeStampCalculation_KEY_ServiceTypeId = "service_type_id";
    public static final String TimeStampCalculation_KEY_PercentageValue = "percentage_value";
    public static final String TimeStampCalculation_KEY_DeliveryOptionId = "delivery_opt_id";
    public static final String TimeStampCalculation_KEY_timeStampId = "timestamp_id";
    public static final String TimeStampCalculation_KEY_modified_date = "modified_date";
    public static final String TimeStampCalculation_KEY_created_date = "created_date";
    public static final String TimeStampCalculation_KEY_soft_del = "soft_del";


    // Output_format
    public static final String Output_format_KEY_output_format_id = "output_format_id";
    public static final String Output_format_KEY_output_format_txt = "output_format_txt";
    public static final String Output_format_KEY_status = "status";
    public static final String Output_format_KEY_modified_date = "modified_date";
    public static final String Output_format_KEY_created_date = "created_date";
    public static final String Output_format_KEY_soft_del = "soft_del";


    // Premium_type
    public static final String Premium_type_KEY_premium_type_id = "premium_type_id";
    public static final String Premium_type_KEY_premium_type = "premium_type";
    public static final String Premium_type_KEY_status = "status";
    public static final String Premium_type_KEY_created_date = "created_date";
    public static final String Premium_type_KEY_modified_date = "modified_date";
    public static final String Premium_type_KEY_soft_del = "soft_del";


    // Price
    public static final String Price_KEY_price_id = "price_id";
    public static final String Price_KEY_price = "price";
    public static final String Price_KEY_tat = "tat";
    public static final String Price_KEY_min_charges = "min_charges";
    public static final String Price_KEY_delivery_opt_id = "delivery_opt_id";
    public static final String Price_KEY_service_type_id = "service_type_id";
    public static final String Price_KEY_time_slab_id = "time_slab_id";
    public static final String Price_KEY_modified_date = "modified_date";
    public static final String Price_KEY_created_date = "created_date";
    public static final String Price_KEY_soft_del = "soft_del";


    // Security_permission
    public static final String Security_permission_KEY_elm_id = "elm_id";
    public static final String Security_permission_KEY_group_id = "group_id";
    public static final String Security_permission_KEY_lastmodifed_date = "lastmodifed_date";


    // Service_type
    public static final String Service_type_KEY_service_type_id = "service_type_id";
    public static final String Service_type_KEY_service_text = "service_text";
    public static final String Service_type_KEY_status = "status";
    public static final String Service_type_KEY_default_select = "default_select";
    public static final String Service_type_KEY_soft_del = "soft_del";
    public static final String Service_type_KEY_modified_date = "modified_date";
    public static final String Service_type_KEY_created_date = "created_date";


    // Status_type
    public static final String Status_type_KEY_order_status_id = "order_status_id";
    public static final String Status_type_KEY_status_txt = "status_txt";
    public static final String Status_type_KEY_is_active = "is_active";
    public static final String Status_type_KEY_created_date = "created_date";
    public static final String Status_type_KEY_modified_date = "modified_date";
    public static final String Status_type_KEY_soft_del = "soft_del";


    // Sys_log
    public static final String Sys_log_KEY_logid = "logid";
    public static final String Sys_log_KEY_logtime = "logtime";
    public static final String Sys_log_KEY_username = "username";
    public static final String Sys_log_KEY_dbname = "dbname";
    public static final String Sys_log_KEY_logmsg = "logmsg";
    public static final String Sys_log_KEY_log_type = "log_type";
    public static final String Sys_log_KEY_user_id = "user_id";


    // Tat_fee
    public static final String Tat_fee_KEY_tat_fee_id = "tat_fee_id";
    public static final String Tat_fee_KEY_delivery_opt_id = "delivery_opt_id";
    public static final String Tat_fee_KEY_time_slab_id = "time_slab_id";
    public static final String Tat_fee_KEY_min_charges = "min_charges";
    public static final String Tat_fee_KEY_tat = "tat";
    public static final String Tat_fee_KEY_feepermin = "feepermin";
    public static final String Tat_fee_KEY_status = "status";
    public static final String Tat_fee_KEY_created_date = "created_date";
    public static final String Tat_fee_KEY_modified_date = "modified_date";
    public static final String Tat_fee_KEY_soft_del = "soft_del";


    // Time_slab
    public static final String Time_slab_KEY_time_slab_id = "time_slab_id";
    public static final String Time_slab_KEY_slab_from = "slab_from";
    public static final String Time_slab_KEY_default_set = "default_set";
    public static final String Time_slab_KEY_status = "status";
    public static final String Time_slab_KEY_modified_date = "modified_date";
    public static final String Time_slab_KEY_created_date = "created_date";
    public static final String Time_slab_KEY_slab_to = "slab_to";
    public static final String Time_slab_KEY_soft_del = "soft_del";
    public static final String Time_slab_KEY_is_last ="is_last";


    // Timestamp

    public static final String Timestamp_KEY_timestampId = "timestamp_id";
    public static final String Timestamp_KEY_tiimestamp_TEXT = "timestamp_txt";
    public static final String Timestamp_KEY_default_set = "default_set";
    public static final String Timestamp_KEY_status = "status";
    public static final String Timestamp_KEY_ModifiedDate = "modified_date";
    public static final String Timestamp_KEY_created_date = "created_date";
    public static final String Timestammp_KEY_soft_del = "soft_del";


    // Transcription_type
    public static final String Transcription_type_KEY_trans_type_id = "trans_type_id";
    public static final String Transcription_type_KEY_trans_type_txt = "trans_type_txt";
    public static final String Transcription_type_KEY_default_set = "default_set";
    public static final String Transcription_type_KEY_soft_del = "soft_del";
    public static final String Transcription_type_KEY_modified_date = "modified_date";
    public static final String Transcription_type_KEY_created_date = "created_date";
    public static final String Transcription_type_KEY_is_active = "is_active";


    // Users
    public static final String Users_KEY_user_id = "user_id";
    public static final String Users_KEY_membership_id = "membership_id";
    public static final String Users_KEY_location_id = "location_id";
    public static final String Users_KEY_email = "email";
    public static final String Users_KEY_first_name = "first_name";
    public static final String Users_KEY_first_name_fn = "first_name_fn";
    public static final String Users_KEY_last_name = "last_name";
    public static final String Users_KEY_last_name_fn = "last_name_fn";
    public static final String Users_KEY_password = "password";
    public static final String Users_KEY_join_date = "join_date";
    public static final String Users_KEY_photo = "photo";
    public static final String Users_KEY_gender = "gender";
    public static final String Users_KEY_auth_key = "auth_key";
    public static final String Users_KEY_reg_date = "reg_date";
    public static final String Users_KEY_modified_date = "modified_date";
    public static final String Users_KEY_dob = "dob";
    public static final String Users_KEY_designation = "designation";
    public static final String Users_KEY_department = "department";
    public static final String Users_KEY_email_2 = "email_2";
    public static final String Users_KEY_mobile_no = "mobile_no";
    public static final String Users_KEY_mobile_no_2 = "mobile_no_2";
    public static final String Users_KEY_extension = "extension";
    public static final String Users_KEY_landline = "landline";
    public static final String Users_KEY_noti_setting_type = "noti_setting_type";
    public static final String Users_KEY_upload_setting_type = "upload_setting_type";
    public static final String Users_KEY_username = "username";
    public static final String Users_KEY_imei = "imei";
    public static final String Users_KEY_is_email_verified = "is_email_verified";


    // User_device
    public static final String User_device_KEY_device_id = "device_id";
    public static final String User_device_KEY_user_id = "user_id";
    public static final String User_device_KEY_membership_id = "membership_id";
    public static final String User_device_KEY_device_type = "device_type";
    public static final String User_device_KEY_imei = "imei";
    public static final String User_device_KEY_api_key = "api_key";
    public static final String User_device_KEY_user_type = "user_type";
    public static final String User_device_KEY_device_name = "device_name";
    public static final String User_device_KEY_device_os = "device_os";
    public static final String User_device_KEY_systemdate = "systemdate";


    // Vas
    public static final String Vas_KEY_vas_id = "vas_id";
    public static final String Vas_KEY_vas_text = "vas_text";
    public static final String Vas_KEY_default_set = "default_set";
    public static final String Vas_KEY_service_flag = "service_flag";
    public static final String Vas_KEY_transcription_flag = "transcription_flag";
    public static final String Vas_KEY_timeslab_flag = "timeslab_flag";
    public static final String Vas_KEY_status = "status";
    public static final String Vas_KEY_created_date = "created_date";
    public static final String Vas_KEY_modified_date = "modified_date";
    public static final String Vas_KEY_soft_del = "soft_del";


//  Methods Of Access_elements

    public int getAccess_elementsCount() {
        String sql = "select count( Access_elementsId ) as total from Access_elements 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Access_elements:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Access_elements> getAccess_elements() {
        String sql = "select *  from Access_elements ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Access_elements> list = new LinkedList<Access_elements>();
        int i0 = c.getColumnIndex(Access_elements_KEY_elm_id);
        int i1 = c.getColumnIndex(Access_elements_KEY_elm_name);
        int i2 = c.getColumnIndex(Access_elements_KEY_elm_created_on);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Access_elements bean = new Access_elements();
                bean.setElm_id(c.getInt(i0));
                bean.setElm_name(getClearData(c.getString(i1)));
                bean.setElm_created_on(getClearData(c.getString(i2)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Access_elements Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Access_elements> getAccess_elementsList(int elm_id) {
        String sql = "select *  from Access_elements where elm_id=" + elm_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Access_elements> list = new LinkedList<Access_elements>();
        int i0 = c.getColumnIndex(Access_elements_KEY_elm_id);
        int i1 = c.getColumnIndex(Access_elements_KEY_elm_name);
        int i2 = c.getColumnIndex(Access_elements_KEY_elm_created_on);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Access_elements bean = new Access_elements();
                bean.setElm_id(c.getInt(i0));
                bean.setElm_name(getClearData(c.getString(i1)));
                bean.setElm_created_on(getClearData(c.getString(i2)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Access_elements Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addAccess_elements(Access_elements bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Access_elements  ( elm_name, elm_created_on  ) values (  '" + setClearData(bean.getElm_name()) + "',  '" + setClearData(bean.getElm_created_on()) + "' )";
                GlobalData.printMessage("Access_elements SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Access_elements ", e);
        }
    }

    public void addAccess_elementsList(LinkedList<Access_elements> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Access_elements bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Access_elements  ( elm_name, elm_created_on  ) values (  '" + setClearData(bean.getElm_name()) + "',  '" + setClearData(bean.getElm_created_on()) + "' )";
                        GlobalData.printMessage("Access_elements SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Access_elements ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Access_elements", e);
            }
        }
    }

    public void deleteAccess_elements() {
        String sql = "";
        try {


            sql = "delete from Access_elements  ";
            GlobalData.printMessage("Access_elements SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Access_elements ", e);
        }
    }

    public void deleteAccess_elements(int rowId) {
        String sql = "";
        try {


            sql = "delete from Access_elements where elm_id=" + rowId;
            GlobalData.printMessage("Access_elements SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Access_elements ", e);
        }
    }


//  Methods Of Assessment_report

    public int getAssessment_reportCount() {
        String sql = "select count( Assessment_reportId ) as total from Assessment_report 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Assessment_report:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Assessment_report> getAssessment_report() {
        String sql = "select *  from Assessment_report ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Assessment_report> list = new LinkedList<Assessment_report>();
        int i0 = c.getColumnIndex(Assessment_report_KEY_ass_report_id);
        int i1 = c.getColumnIndex(Assessment_report_KEY_order_id);
        int i2 = c.getColumnIndex(Assessment_report_KEY_ass_date);
        int i3 = c.getColumnIndex(Assessment_report_KEY_audio_quality);
        int i4 = c.getColumnIndex(Assessment_report_KEY_subjectarea);
        int i5 = c.getColumnIndex(Assessment_report_KEY_result);
        int i6 = c.getColumnIndex(Assessment_report_KEY_notesfrom);
        int i7 = c.getColumnIndex(Assessment_report_KEY_notesfrom_trasaction);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Assessment_report bean = new Assessment_report();
                bean.setAss_report_id(c.getInt(i0));
                bean.setOrder_id(c.getInt(i1));
                bean.setAss_date(getClearData(c.getString(i2)));
                bean.setAudio_quality(getClearData(c.getString(i3)));
                bean.setSubjectarea(getClearData(c.getString(i4)));
                bean.setResult(getClearData(c.getString(i5)));
                bean.setNotesfrom(getClearData(c.getString(i6)));
                bean.setNotesfrom_trasaction(getClearData(c.getString(i7)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Assessment_report Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Assessment_report> getAssessment_reportList(int ass_report_id) {
        String sql = "select *  from Assessment_report where ass_report_id=" + ass_report_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Assessment_report> list = new LinkedList<Assessment_report>();
        int i0 = c.getColumnIndex(Assessment_report_KEY_ass_report_id);
        int i1 = c.getColumnIndex(Assessment_report_KEY_order_id);
        int i2 = c.getColumnIndex(Assessment_report_KEY_ass_date);
        int i3 = c.getColumnIndex(Assessment_report_KEY_audio_quality);
        int i4 = c.getColumnIndex(Assessment_report_KEY_subjectarea);
        int i5 = c.getColumnIndex(Assessment_report_KEY_result);
        int i6 = c.getColumnIndex(Assessment_report_KEY_notesfrom);
        int i7 = c.getColumnIndex(Assessment_report_KEY_notesfrom_trasaction);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Assessment_report bean = new Assessment_report();
                bean.setAss_report_id(c.getInt(i0));
                bean.setOrder_id(c.getInt(i1));
                bean.setAss_date(getClearData(c.getString(i2)));
                bean.setAudio_quality(getClearData(c.getString(i3)));
                bean.setSubjectarea(getClearData(c.getString(i4)));
                bean.setResult(getClearData(c.getString(i5)));
                bean.setNotesfrom(getClearData(c.getString(i6)));
                bean.setNotesfrom_trasaction(getClearData(c.getString(i7)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Assessment_report Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addAssessment_report(Assessment_report bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Assessment_report  ( order_id, ass_date, audio_quality, subjectarea, result, notesfrom, notesfrom_trasaction  ) values (  " + bean.getOrder_id() + ",  '" + setClearData(bean.getAss_date()) + "',  '" + setClearData(bean.getAudio_quality()) + "',  '" + setClearData(bean.getSubjectarea()) + "',  '" + setClearData(bean.getResult()) + "',  '" + setClearData(bean.getNotesfrom()) + "',  '" + setClearData(bean.getNotesfrom_trasaction()) + "' )";
                GlobalData.printMessage("Assessment_report SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Assessment_report ", e);
        }
    }

    public void addAssessment_reportList(LinkedList<Assessment_report> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Assessment_report bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Assessment_report  ( order_id, ass_date, audio_quality, subjectarea, result, notesfrom, notesfrom_trasaction  ) values (  " + bean.getOrder_id() + ",  '" + setClearData(bean.getAss_date()) + "',  '" + setClearData(bean.getAudio_quality()) + "',  '" + setClearData(bean.getSubjectarea()) + "',  '" + setClearData(bean.getResult()) + "',  '" + setClearData(bean.getNotesfrom()) + "',  '" + setClearData(bean.getNotesfrom_trasaction()) + "' )";
                        GlobalData.printMessage("Assessment_report SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Assessment_report ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Assessment_report", e);
            }
        }
    }

    public void deleteAssessment_report() {
        String sql = "";
        try {


            sql = "delete from Assessment_report  ";
            GlobalData.printMessage("Assessment_report SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Assessment_report ", e);
        }
    }

    public void deleteAssessment_report(int rowId) {
        String sql = "";
        try {


            sql = "delete from Assessment_report where ass_report_id=" + rowId;
            GlobalData.printMessage("Assessment_report SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Assessment_report ", e);
        }
    }


//  Methods Of Assessment_speaker

    public int getAssessment_speakerCount() {
        String sql = "select count( Assessment_speakerId ) as total from Assessment_speaker ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Assessment_speaker:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Assessment_speaker> getAssessment_speaker() {
        String sql = "select *  from Assessment_speaker ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Assessment_speaker> list = new LinkedList<Assessment_speaker>();
        int i0 = c.getColumnIndex(Assessment_speaker_KEY_ass_speaker_id);
        int i1 = c.getColumnIndex(Assessment_speaker_KEY_ass_report_id);
        int i2 = c.getColumnIndex(Assessment_speaker_KEY_speaker_name);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Assessment_speaker bean = new Assessment_speaker();
                bean.setAss_speaker_id(c.getInt(i0));
                bean.setAss_report_id(c.getInt(i1));
                bean.setSpeaker_name(getClearData(c.getString(i2)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Assessment_speaker Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Assessment_speaker> getAssessment_speakerList(int ass_speaker_id) {
        String sql = "select *  from Assessment_speaker where ass_speaker_id=" + ass_speaker_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Assessment_speaker> list = new LinkedList<Assessment_speaker>();
        int i0 = c.getColumnIndex(Assessment_speaker_KEY_ass_speaker_id);
        int i1 = c.getColumnIndex(Assessment_speaker_KEY_ass_report_id);
        int i2 = c.getColumnIndex(Assessment_speaker_KEY_speaker_name);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Assessment_speaker bean = new Assessment_speaker();
                bean.setAss_speaker_id(c.getInt(i0));
                bean.setAss_report_id(c.getInt(i1));
                bean.setSpeaker_name(getClearData(c.getString(i2)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Assessment_speaker Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addAssessment_speaker(Assessment_speaker bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Assessment_speaker  ( ass_report_id, speaker_name  ) values (  " + bean.getAss_report_id() + ",  '" + setClearData(bean.getSpeaker_name()) + "' )";
                GlobalData.printMessage("Assessment_speaker SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Assessment_speaker ", e);
        }
    }

    public void addAssessment_speakerList(LinkedList<Assessment_speaker> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Assessment_speaker bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Assessment_speaker  ( ass_report_id, speaker_name  ) values (  " + bean.getAss_report_id() + ",  '" + setClearData(bean.getSpeaker_name()) + "' )";
                        GlobalData.printMessage("Assessment_speaker SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Assessment_speaker ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Assessment_speaker", e);
            }
        }
    }

    public void deleteAssessment_speaker() {
        String sql = "";
        try {


            sql = "delete from Assessment_speaker  ";
            GlobalData.printMessage("Assessment_speaker SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Assessment_speaker ", e);
        }
    }

    public void deleteAssessment_speaker(int rowId) {
        String sql = "";
        try {


            sql = "delete from Assessment_speaker where ass_speaker_id=" + rowId;
            GlobalData.printMessage("Assessment_speaker SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Assessment_speaker ", e);
        }
    }


//  Methods Of Company_info

    public int getCompany_infoCount() {
        String sql = "select count( Company_infoId ) as total from Company_info 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Company_info:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Company_info> getCompany_info() {
        String sql = "select *  from Company_info "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Company_info> list = new LinkedList<Company_info>();
        int i0 = c.getColumnIndex(Company_info_KEY_membership_id);
        int i1 = c.getColumnIndex(Company_info_KEY_org_name_eng);
        int i2 = c.getColumnIndex(Company_info_KEY_org_name_fn);
        int i3 = c.getColumnIndex(Company_info_KEY_status);
        int i4 = c.getColumnIndex(Company_info_KEY_client_type);
        int i5 = c.getColumnIndex(Company_info_KEY_date_of_invoice);
        int i6 = c.getColumnIndex(Company_info_KEY_name_of_invoice);
        int i7 = c.getColumnIndex(Company_info_KEY_business_type);
        int i8 = c.getColumnIndex(Company_info_KEY_template);
        int i9 = c.getColumnIndex(Company_info_KEY_client_instruction);
        int i10 = c.getColumnIndex(Company_info_KEY_payment_instruction);
        int i11 = c.getColumnIndex(Company_info_KEY_modified_date);
        int i12 = c.getColumnIndex(Company_info_KEY_created_date);
        int i13 = c.getColumnIndex(Company_info_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Company_info bean = new Company_info();
                bean.setMembership_id(getClearData(c.getString(i0)));
                bean.setOrg_name_eng(getClearData(c.getString(i1)));
                bean.setOrg_name_fn(getClearData(c.getString(i2)));
                bean.setStatus(getClearData(c.getString(i3)));
                bean.setClient_type(getClearData(c.getString(i4)));
                bean.setDate_of_invoice(getClearData(c.getString(i5)));
                bean.setName_of_invoice(getClearData(c.getString(i6)));
                bean.setBusiness_type(getClearData(c.getString(i7)));
                bean.setTemplate(getClearData(c.getString(i8)));
                bean.setClient_instruction(getClearData(c.getString(i9)));
                bean.setPayment_instruction(getClearData(c.getString(i10)));
                bean.setModified_date(getClearData(c.getString(i11)));
                bean.setCreated_date(getClearData(c.getString(i12)));
                bean.setSoft_del(getClearData(c.getString(i13)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Company_info Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Company_info> getCompany_infoList(int membership_id) {
        String sql = "select *  from Company_info where membership_id=" + membership_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Company_info> list = new LinkedList<Company_info>();
        int i0 = c.getColumnIndex(Company_info_KEY_membership_id);
        int i1 = c.getColumnIndex(Company_info_KEY_org_name_eng);
        int i2 = c.getColumnIndex(Company_info_KEY_org_name_fn);
        int i3 = c.getColumnIndex(Company_info_KEY_status);
        int i4 = c.getColumnIndex(Company_info_KEY_client_type);
        int i5 = c.getColumnIndex(Company_info_KEY_date_of_invoice);
        int i6 = c.getColumnIndex(Company_info_KEY_name_of_invoice);
        int i7 = c.getColumnIndex(Company_info_KEY_business_type);
        int i8 = c.getColumnIndex(Company_info_KEY_template);
        int i9 = c.getColumnIndex(Company_info_KEY_client_instruction);
        int i10 = c.getColumnIndex(Company_info_KEY_payment_instruction);
        int i11 = c.getColumnIndex(Company_info_KEY_modified_date);
        int i12 = c.getColumnIndex(Company_info_KEY_created_date);
        int i13 = c.getColumnIndex(Company_info_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Company_info bean = new Company_info();
                bean.setMembership_id(getClearData(c.getString(i0)));
                bean.setOrg_name_eng(getClearData(c.getString(i1)));
                bean.setOrg_name_fn(getClearData(c.getString(i2)));
                bean.setStatus(getClearData(c.getString(i3)));
                bean.setClient_type(getClearData(c.getString(i4)));
                bean.setDate_of_invoice(getClearData(c.getString(i5)));
                bean.setName_of_invoice(getClearData(c.getString(i6)));
                bean.setBusiness_type(getClearData(c.getString(i7)));
                bean.setTemplate(getClearData(c.getString(i8)));
                bean.setClient_instruction(getClearData(c.getString(i9)));
                bean.setPayment_instruction(getClearData(c.getString(i10)));
                bean.setModified_date(getClearData(c.getString(i11)));
                bean.setCreated_date(getClearData(c.getString(i12)));
                bean.setSoft_del(getClearData(c.getString(i13)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Company_info Read:", e);
            }
        }
        c.close();
        return list;
    }
    public Company_info  getCompany_info(String membership_id) {
        String sql = "select *  from company_info where membership_id= '"+membership_id+"' ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        Company_info bean = new Company_info();
        int i0 = c.getColumnIndex(Company_info_KEY_membership_id);
        int i1 = c.getColumnIndex(Company_info_KEY_org_name_eng);
        int i2 = c.getColumnIndex(Company_info_KEY_org_name_fn);
        int i3 = c.getColumnIndex(Company_info_KEY_status);
        int i4 = c.getColumnIndex(Company_info_KEY_client_type);
        int i5 = c.getColumnIndex(Company_info_KEY_date_of_invoice);
        int i6 = c.getColumnIndex(Company_info_KEY_name_of_invoice);
        int i7 = c.getColumnIndex(Company_info_KEY_business_type);
        int i8 = c.getColumnIndex(Company_info_KEY_template);
        int i9 = c.getColumnIndex(Company_info_KEY_client_instruction);
        int i10 = c.getColumnIndex(Company_info_KEY_payment_instruction);
        int i11 = c.getColumnIndex(Company_info_KEY_modified_date);
        int i12 = c.getColumnIndex(Company_info_KEY_created_date);
        int i13 = c.getColumnIndex(Company_info_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                bean.setMembership_id(c.getString(i0));
                bean.setOrg_name_eng(getClearData(c.getString(i1)));
                bean.setOrg_name_fn(getClearData(c.getString(i2)));
                bean.setStatus(getClearData(c.getString(i3)));
                bean.setClient_type(getClearData(c.getString(i4)));
                bean.setDate_of_invoice(getClearData(c.getString(i5)));
                bean.setName_of_invoice(getClearData(c.getString(i6)));
                bean.setBusiness_type(getClearData(c.getString(i7)));
                bean.setTemplate(getClearData(c.getString(i8)));
                bean.setClient_instruction(getClearData(c.getString(i9)));
                bean.setPayment_instruction(getClearData(c.getString(i10)));
                bean.setModified_date(getClearData(c.getString(i11)));
                bean.setCreated_date(getClearData(c.getString(i12)));
                bean.setSoft_del(getClearData(c.getString(i13)));

            } catch (Exception e) {
                GlobalData.printError("Company_info Read:", e);
            }
        }
        c.close();
        return bean;
    }
    public void addCompany_info(Company_info bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Company_info  ( org_name_eng, org_name_fn, status, client_type, date_of_invoice, name_of_invoice, business_type, template, client_instruction, payment_instruction, modified_date, created_date, soft_del  ) values (  '" + setClearData(bean.getOrg_name_eng()) + "',  '" + setClearData(bean.getOrg_name_fn()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getClient_type()) + "',  '" + setClearData(bean.getDate_of_invoice()) + "',  '" + setClearData(bean.getName_of_invoice()) + "',  '" + setClearData(bean.getBusiness_type()) + "',  '" + setClearData(bean.getTemplate()) + "',  '" + setClearData(bean.getClient_instruction()) + "',  '" + setClearData(bean.getPayment_instruction()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                GlobalData.printMessage("Company_info SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Company_info ", e);
        }
    }

    public void addCompany_infoList(LinkedList<Company_info> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Company_info bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Company_info  ( org_name_eng, org_name_fn, status, client_type, date_of_invoice, name_of_invoice, business_type, template, client_instruction, payment_instruction, modified_date, created_date, soft_del  ) values (  '" + setClearData(bean.getOrg_name_eng()) + "',  '" + setClearData(bean.getOrg_name_fn()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getClient_type()) + "',  '" + setClearData(bean.getDate_of_invoice()) + "',  '" + setClearData(bean.getName_of_invoice()) + "',  '" + setClearData(bean.getBusiness_type()) + "',  '" + setClearData(bean.getTemplate()) + "',  '" + setClearData(bean.getClient_instruction()) + "',  '" + setClearData(bean.getPayment_instruction()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                        GlobalData.printMessage("Company_info SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Company_info ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Company_info", e);
            }
        }
    }

    public void deleteCompany_info() {
        String sql = "";
        try {


            sql = "delete from Company_info  ";
            GlobalData.printMessage("Company_info SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Company_info ", e);
        }
    }

    public void deleteCompany_info(int rowId) {
        String sql = "";
        try {


            sql = "delete from Company_info where membership_id=" + rowId;
            GlobalData.printMessage("Company_info SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Company_info ", e);
        }
    }


//  Methods Of Company_location

    public int getCompany_locationCount() {
        String sql = "select count( Company_locationId ) as total from Company_location "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Company_location:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Company_location> getCompany_location() {
        String sql = "select *  from Company_location "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Company_location> list = new LinkedList<Company_location>();
        int i0 = c.getColumnIndex(Company_location_KEY_client_location_id);
        int i1 = c.getColumnIndex(Company_location_KEY_membership_id);
        int i2 = c.getColumnIndex(Company_location_KEY_city);
        int i3 = c.getColumnIndex(Company_location_KEY_state);
        int i4 = c.getColumnIndex(Company_location_KEY_country);
        int i5 = c.getColumnIndex(Company_location_KEY_pincode);
        int i6 = c.getColumnIndex(Company_location_KEY_landline);
        int i7 = c.getColumnIndex(Company_location_KEY_email);
        int i8 = c.getColumnIndex(Company_location_KEY_mobile);
        int i9 = c.getColumnIndex(Company_location_KEY_website);
        int i10 = c.getColumnIndex(Company_location_KEY_currency);
        int i11 = c.getColumnIndex(Company_location_KEY_address);
        int i12 = c.getColumnIndex(Company_location_KEY_modified_date);
        int i13 = c.getColumnIndex(Company_location_KEY_created_date);
        int i14 = c.getColumnIndex(Company_location_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Company_location bean = new Company_location();
                bean.setClient_location_id(c.getInt(i0));
                bean.setMembership_id(getClearData(c.getString(i1)));
                bean.setCity(getClearData(c.getString(i2)));
                bean.setState(getClearData(c.getString(i3)));
                bean.setCountry(getClearData(c.getString(i4)));
                bean.setPincode(getClearData(c.getString(i5)));
                bean.setLandline(getClearData(c.getString(i6)));
                bean.setEmail(getClearData(c.getString(i7)));
                bean.setMobile(getClearData(c.getString(i8)));
                bean.setWebsite(getClearData(c.getString(i9)));
                bean.setCurrency(getClearData(c.getString(i10)));
                bean.setAddress(getClearData(c.getString(i11)));
                bean.setModified_date(getClearData(c.getString(i12)));
                bean.setCreated_date(getClearData(c.getString(i13)));
                bean.setSoft_del(getClearData(c.getString(i14)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Company_location Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Company_location> getCompany_locationList(int client_location_id) {
        String sql = "select *  from Company_location where client_location_id=" + client_location_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Company_location> list = new LinkedList<Company_location>();
        int i0 = c.getColumnIndex(Company_location_KEY_client_location_id);
        int i1 = c.getColumnIndex(Company_location_KEY_membership_id);
        int i2 = c.getColumnIndex(Company_location_KEY_city);
        int i3 = c.getColumnIndex(Company_location_KEY_state);
        int i4 = c.getColumnIndex(Company_location_KEY_country);
        int i5 = c.getColumnIndex(Company_location_KEY_pincode);
        int i6 = c.getColumnIndex(Company_location_KEY_landline);
        int i7 = c.getColumnIndex(Company_location_KEY_email);
        int i8 = c.getColumnIndex(Company_location_KEY_mobile);
        int i9 = c.getColumnIndex(Company_location_KEY_website);
        int i10 = c.getColumnIndex(Company_location_KEY_currency);
        int i11 = c.getColumnIndex(Company_location_KEY_address);
        int i12 = c.getColumnIndex(Company_location_KEY_modified_date);
        int i13 = c.getColumnIndex(Company_location_KEY_created_date);
        int i14 = c.getColumnIndex(Company_location_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Company_location bean = new Company_location();
                bean.setClient_location_id(c.getInt(i0));
                bean.setMembership_id(getClearData(c.getString(i1)));
                bean.setCity(getClearData(c.getString(i2)));
                bean.setState(getClearData(c.getString(i3)));
                bean.setCountry(getClearData(c.getString(i4)));
                bean.setPincode(getClearData(c.getString(i5)));
                bean.setLandline(getClearData(c.getString(i6)));
                bean.setEmail(getClearData(c.getString(i7)));
                bean.setMobile(getClearData(c.getString(i8)));
                bean.setWebsite(getClearData(c.getString(i9)));
                bean.setCurrency(getClearData(c.getString(i10)));
                bean.setAddress(getClearData(c.getString(i11)));
                bean.setModified_date(getClearData(c.getString(i12)));
                bean.setCreated_date(getClearData(c.getString(i13)));
                bean.setSoft_del(getClearData(c.getString(i14)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Company_location Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addCompany_location(Company_location bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Company_location  ( membership_id, city, state, country, pincode, landline, email, mobile, website, currency, address, modified_date, created_date, soft_del  ) values (  '" + setClearData(bean.getMembership_id()) + "',  '" + setClearData(bean.getCity()) + "',  '" + setClearData(bean.getState()) + "',  '" + setClearData(bean.getCountry()) + "',  '" + setClearData(bean.getPincode()) + "',  '" + setClearData(bean.getLandline()) + "',  '" + setClearData(bean.getEmail()) + "',  '" + setClearData(bean.getMobile()) + "',  '" + setClearData(bean.getWebsite()) + "',  '" + setClearData(bean.getCurrency()) + "',  '" + setClearData(bean.getAddress()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                GlobalData.printMessage("Company_location SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Company_location ", e);
        }
    }

    public void addCompany_locationList(LinkedList<Company_location> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Company_location bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Company_location  ( membership_id, city, state, country, pincode, landline, email, mobile, website, currency, address, modified_date, created_date, soft_del  ) values (  '" + setClearData(bean.getMembership_id()) + "',  '" + setClearData(bean.getCity()) + "',  '" + setClearData(bean.getState()) + "',  '" + setClearData(bean.getCountry()) + "',  '" + setClearData(bean.getPincode()) + "',  '" + setClearData(bean.getLandline()) + "',  '" + setClearData(bean.getEmail()) + "',  '" + setClearData(bean.getMobile()) + "',  '" + setClearData(bean.getWebsite()) + "',  '" + setClearData(bean.getCurrency()) + "',  '" + setClearData(bean.getAddress()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                        GlobalData.printMessage("Company_location SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Company_location ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Company_location", e);
            }
        }
    }

    public void deleteCompany_location() {
        String sql = "";
        try {


            sql = "delete from Company_location  ";
            GlobalData.printMessage("Company_location SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Company_location ", e);
        }
    }

    public void deleteCompany_location(int rowId) {
        String sql = "";
        try {


            sql = "delete from Company_location where client_location_id=" + rowId;
            GlobalData.printMessage("Company_location SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Company_location ", e);
        }
    }


//  Methods Of Core_group

    public int getCore_groupCount() {
        String sql = "select count( Core_groupId ) as total from Core_group 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Core_group:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Core_group> getCore_group() {
        String sql = "select *  from Core_group "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Core_group> list = new LinkedList<Core_group>();
        int i0 = c.getColumnIndex(Core_group_KEY_group_id);
        int i1 = c.getColumnIndex(Core_group_KEY_group_name);
        int i2 = c.getColumnIndex(Core_group_KEY_arr_group_name);
        int i3 = c.getColumnIndex(Core_group_KEY_pos_level);
        int i4 = c.getColumnIndex(Core_group_KEY_pos_created);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Core_group bean = new Core_group();
                bean.setGroup_id(c.getInt(i0));
                bean.setGroup_name(getClearData(c.getString(i1)));
                bean.setArr_group_name(getClearData(c.getString(i2)));
                bean.setPos_level(getClearData(c.getString(i3)));
                bean.setPos_created(getClearData(c.getString(i4)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Core_group Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Core_group> getCore_groupList(int group_id) {
        String sql = "select *  from Core_group where group_id=" + group_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Core_group> list = new LinkedList<Core_group>();
        int i0 = c.getColumnIndex(Core_group_KEY_group_id);
        int i1 = c.getColumnIndex(Core_group_KEY_group_name);
        int i2 = c.getColumnIndex(Core_group_KEY_arr_group_name);
        int i3 = c.getColumnIndex(Core_group_KEY_pos_level);
        int i4 = c.getColumnIndex(Core_group_KEY_pos_created);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Core_group bean = new Core_group();
                bean.setGroup_id(c.getInt(i0));
                bean.setGroup_name(getClearData(c.getString(i1)));
                bean.setArr_group_name(getClearData(c.getString(i2)));
                bean.setPos_level(getClearData(c.getString(i3)));
                bean.setPos_created(getClearData(c.getString(i4)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Core_group Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addCore_group(Core_group bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Core_group  ( group_name, arr_group_name, pos_level, pos_created  ) values (  '" + setClearData(bean.getGroup_name()) + "',  '" + setClearData(bean.getArr_group_name()) + "',  '" + setClearData(bean.getPos_level()) + "',  '" + setClearData(bean.getPos_created()) + "' )";
                GlobalData.printMessage("Core_group SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Core_group ", e);
        }
    }

    public void addCore_groupList(LinkedList<Core_group> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Core_group bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Core_group  ( group_name, arr_group_name, pos_level, pos_created  ) values (  '" + setClearData(bean.getGroup_name()) + "',  '" + setClearData(bean.getArr_group_name()) + "',  '" + setClearData(bean.getPos_level()) + "',  '" + setClearData(bean.getPos_created()) + "' )";
                        GlobalData.printMessage("Core_group SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Core_group ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Core_group", e);
            }
        }
    }

    public void deleteCore_group() {
        String sql = "";
        try {


            sql = "delete from Core_group  ";
            GlobalData.printMessage("Core_group SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Core_group ", e);
        }
    }

    public void deleteCore_group(int rowId) {
        String sql = "";
        try {


            sql = "delete from Core_group where group_id=" + rowId;
            GlobalData.printMessage("Core_group SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Core_group ", e);
        }
    }


//  Methods Of Core_user

    public int getCore_userCount() {
        String sql = "select count( Core_userId ) as total from Core_user 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Core_user:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Core_user> getCore_user() {
        String sql = "select *  from Core_user "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Core_user> list = new LinkedList<Core_user>();
        int i0 = c.getColumnIndex(Core_user_KEY_user_id);
        int i1 = c.getColumnIndex(Core_user_KEY_group_id);
        int i2 = c.getColumnIndex(Core_user_KEY_cat_id);
        int i3 = c.getColumnIndex(Core_user_KEY_username);
        int i4 = c.getColumnIndex(Core_user_KEY_last_name);
        int i5 = c.getColumnIndex(Core_user_KEY_password);
        int i6 = c.getColumnIndex(Core_user_KEY_first_name);
        int i7 = c.getColumnIndex(Core_user_KEY_join_date);
        int i8 = c.getColumnIndex(Core_user_KEY_photo);
        int i9 = c.getColumnIndex(Core_user_KEY_status);
        int i10 = c.getColumnIndex(Core_user_KEY_app_user_status);
        int i11 = c.getColumnIndex(Core_user_KEY_email);
        int i12 = c.getColumnIndex(Core_user_KEY_gender);
        int i13 = c.getColumnIndex(Core_user_KEY_mobile);
        int i14 = c.getColumnIndex(Core_user_KEY_auth_key);
        int i15 = c.getColumnIndex(Core_user_KEY_reg_date);
        int i16 = c.getColumnIndex(Core_user_KEY_modified_date);
        int i17 = c.getColumnIndex(Core_user_KEY_user_type);
        int i18 = c.getColumnIndex(Core_user_KEY_session_key);
        int i19 = c.getColumnIndex(Core_user_KEY_dob);
        int i20 = c.getColumnIndex(Core_user_KEY_city);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Core_user bean = new Core_user();
                bean.setUser_id(c.getInt(i0));
                bean.setGroup_id(c.getInt(i1));
                bean.setCat_id(c.getInt(i2));
                bean.setUsername(getClearData(c.getString(i3)));
                bean.setLast_name(getClearData(c.getString(i4)));
                bean.setPassword(getClearData(c.getString(i5)));
                bean.setFirst_name(getClearData(c.getString(i6)));
                bean.setJoin_date(getClearData(c.getString(i7)));
                bean.setPhoto(getClearData(c.getString(i8)));
                bean.setStatus(getClearData(c.getString(i9)));
                bean.setApp_user_status(getClearData(c.getString(i10)));
                bean.setEmail(getClearData(c.getString(i11)));
                bean.setGender(getClearData(c.getString(i12)));
                bean.setMobile(getClearData(c.getString(i13)));
                bean.setAuth_key(getClearData(c.getString(i14)));
                bean.setReg_date(getClearData(c.getString(i15)));
                bean.setModified_date(getClearData(c.getString(i16)));
                bean.setUser_type(getClearData(c.getString(i17)));
                bean.setSession_key(getClearData(c.getString(i18)));
                bean.setDob(getClearData(c.getString(i19)));
                bean.setCity(getClearData(c.getString(i20)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Core_user Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Core_user> getCore_userList(int user_id) {
        String sql = "select *  from Core_user where user_id=" + user_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Core_user> list = new LinkedList<Core_user>();
        int i0 = c.getColumnIndex(Core_user_KEY_user_id);
        int i1 = c.getColumnIndex(Core_user_KEY_group_id);
        int i2 = c.getColumnIndex(Core_user_KEY_cat_id);
        int i3 = c.getColumnIndex(Core_user_KEY_username);
        int i4 = c.getColumnIndex(Core_user_KEY_last_name);
        int i5 = c.getColumnIndex(Core_user_KEY_password);
        int i6 = c.getColumnIndex(Core_user_KEY_first_name);
        int i7 = c.getColumnIndex(Core_user_KEY_join_date);
        int i8 = c.getColumnIndex(Core_user_KEY_photo);
        int i9 = c.getColumnIndex(Core_user_KEY_status);
        int i10 = c.getColumnIndex(Core_user_KEY_app_user_status);
        int i11 = c.getColumnIndex(Core_user_KEY_email);
        int i12 = c.getColumnIndex(Core_user_KEY_gender);
        int i13 = c.getColumnIndex(Core_user_KEY_mobile);
        int i14 = c.getColumnIndex(Core_user_KEY_auth_key);
        int i15 = c.getColumnIndex(Core_user_KEY_reg_date);
        int i16 = c.getColumnIndex(Core_user_KEY_modified_date);
        int i17 = c.getColumnIndex(Core_user_KEY_user_type);
        int i18 = c.getColumnIndex(Core_user_KEY_session_key);
        int i19 = c.getColumnIndex(Core_user_KEY_dob);
        int i20 = c.getColumnIndex(Core_user_KEY_city);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Core_user bean = new Core_user();
                bean.setUser_id(c.getInt(i0));
                bean.setGroup_id(c.getInt(i1));
                bean.setCat_id(c.getInt(i2));
                bean.setUsername(getClearData(c.getString(i3)));
                bean.setLast_name(getClearData(c.getString(i4)));
                bean.setPassword(getClearData(c.getString(i5)));
                bean.setFirst_name(getClearData(c.getString(i6)));
                bean.setJoin_date(getClearData(c.getString(i7)));
                bean.setPhoto(getClearData(c.getString(i8)));
                bean.setStatus(getClearData(c.getString(i9)));
                bean.setApp_user_status(getClearData(c.getString(i10)));
                bean.setEmail(getClearData(c.getString(i11)));
                bean.setGender(getClearData(c.getString(i12)));
                bean.setMobile(getClearData(c.getString(i13)));
                bean.setAuth_key(getClearData(c.getString(i14)));
                bean.setReg_date(getClearData(c.getString(i15)));
                bean.setModified_date(getClearData(c.getString(i16)));
                bean.setUser_type(getClearData(c.getString(i17)));
                bean.setSession_key(getClearData(c.getString(i18)));
                bean.setDob(getClearData(c.getString(i19)));
                bean.setCity(getClearData(c.getString(i20)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Core_user Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addCore_user(Core_user bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Core_user  ( group_id, cat_id, username, last_name, password, first_name, join_date, photo, status, app_user_status, email, gender, mobile, auth_key, reg_date, modified_date, user_type, session_key, dob, city  ) values (  " + bean.getGroup_id() + ",  " + bean.getCat_id() + ",  '" + setClearData(bean.getUsername()) + "',  '" + setClearData(bean.getLast_name()) + "',  '" + setClearData(bean.getPassword()) + "',  '" + setClearData(bean.getFirst_name()) + "',  '" + setClearData(bean.getJoin_date()) + "',  '" + setClearData(bean.getPhoto()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getApp_user_status()) + "',  '" + setClearData(bean.getEmail()) + "',  '" + setClearData(bean.getGender()) + "',  '" + setClearData(bean.getMobile()) + "',  '" + setClearData(bean.getAuth_key()) + "',  '" + setClearData(bean.getReg_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getUser_type()) + "',  '" + setClearData(bean.getSession_key()) + "',  '" + setClearData(bean.getDob()) + "',  '" + setClearData(bean.getCity()) + "' )";
                GlobalData.printMessage("Core_user SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Core_user ", e);
        }
    }

    public void addCore_userList(LinkedList<Core_user> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Core_user bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Core_user  ( group_id, cat_id, username, last_name, password, first_name, join_date, photo, status, app_user_status, email, gender, mobile, auth_key, reg_date, modified_date, user_type, session_key, dob, city  ) values (  " + bean.getGroup_id() + ",  " + bean.getCat_id() + ",  '" + setClearData(bean.getUsername()) + "',  '" + setClearData(bean.getLast_name()) + "',  '" + setClearData(bean.getPassword()) + "',  '" + setClearData(bean.getFirst_name()) + "',  '" + setClearData(bean.getJoin_date()) + "',  '" + setClearData(bean.getPhoto()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getApp_user_status()) + "',  '" + setClearData(bean.getEmail()) + "',  '" + setClearData(bean.getGender()) + "',  '" + setClearData(bean.getMobile()) + "',  '" + setClearData(bean.getAuth_key()) + "',  '" + setClearData(bean.getReg_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getUser_type()) + "',  '" + setClearData(bean.getSession_key()) + "',  '" + setClearData(bean.getDob()) + "',  '" + setClearData(bean.getCity()) + "' )";
                        GlobalData.printMessage("Core_user SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Core_user ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Core_user", e);
            }
        }
    }

    public void deleteCore_user() {
        String sql = "";
        try {


            sql = "delete from Core_user  ";
            GlobalData.printMessage("Core_user SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Core_user ", e);
        }
    }

    public void deleteCore_user(int rowId) {
        String sql = "";
        try {


            sql = "delete from Core_user where user_id=" + rowId;
            GlobalData.printMessage("Core_user SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Core_user ", e);
        }
    }


//  Methods Of Delivery_option

    public int getDelivery_optionCount() {
        String sql = "select count( delivery_opt_id ) as total from Delivery_option 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Delivery_option:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Delivery_option> getDelivery_option() {
        String sql = "select *  from Delivery_option "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Delivery_option> list = new LinkedList<Delivery_option>();
        int i0 = c.getColumnIndex(Delivery_option_KEY_delivery_opt_id);
        int i1 = c.getColumnIndex(Delivery_option_KEY_del_days);
        int i2 = c.getColumnIndex(Delivery_option_KEY_del_option);
        int i3 = c.getColumnIndex(Delivery_option_KEY_status);
        int i4 = c.getColumnIndex(Delivery_option_KEY_created_date);
        int i5 = c.getColumnIndex(Delivery_option_KEY_modified_date);
        int i6 = c.getColumnIndex(Delivery_option_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Delivery_option bean = new Delivery_option();
                bean.setDelivery_opt_id(getClearData(c.getString(i0)));
                bean.setDel_days(getClearData(c.getString(i1)));
                bean.setDel_option(getClearData(c.getString(i2)));
                bean.setStatus(getClearData(c.getString(i3)));
                bean.setCreated_date(getClearData(c.getString(i4)));
                bean.setModified_date(getClearData(c.getString(i5)));
                bean.setSoft_del(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Delivery_option Read:", e);
            }
        }
        c.close();
        return list;
    }

//    public LinkedList<Delivery_option> getDelivery_optionList(int delivery_opt_id) {
//        String sql = "select *  from Delivery_option where delivery_opt_id=" + delivery_opt_id;
//        Cursor c = ourDatabase.rawQuery(sql, null);
//        LinkedList<Delivery_option> list = new LinkedList<Delivery_option>();
//        int i0 = c.getColumnIndex(Delivery_option_KEY_delivery_opt_id);
//        int i1 = c.getColumnIndex(Delivery_option_KEY_del_days);
//        int i2 = c.getColumnIndex(Delivery_option_KEY_del_option);
//        int i3 = c.getColumnIndex(Delivery_option_KEY_status);
//        int i4 = c.getColumnIndex(Delivery_option_KEY_created_date);
//        int i5 = c.getColumnIndex(Delivery_option_KEY_modified_date);
//        int i6 = c.getColumnIndex(Delivery_option_KEY_soft_del);
//
//
//        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
//
//            try {
//                Delivery_option bean = new Delivery_option();
//                bean.setDelivery_opt_id(getClearData(c.getString(i0)));
//                bean.setDel_days(getClearData(c.getString(i1)));
//                bean.setDel_option(getClearData(c.getString(i2)));
//                bean.setStatus(getClearData(c.getString(i3)));
//                bean.setCreated_date(getClearData(c.getString(i4)));
//                bean.setModified_date(getClearData(c.getString(i5)));
//                bean.setSoft_del(getClearData(c.getString(i6)));
//
//                list.add(bean);
//            } catch (Exception e) {
//                GlobalData.printError("Delivery_option Read:", e);
//            }
//        }
//        c.close();
//        return list;
//    }

    public void addDelivery_option(Delivery_option bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert or replace into Delivery_option  ( delivery_opt_id,del_days, del_option, status, created_date, modified_date, soft_del  ) values (  '" + setClearData(bean.getDelivery_opt_id()) + "', '" + setClearData(bean.getDel_days()) + "',  '" + setClearData(bean.getDel_option()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                GlobalData.printMessage("Delivery_option SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Delivery_option ", e);
        }
    }

    public void addDelivery_optionList(LinkedList<Delivery_option> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Delivery_option bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into Delivery_option  ( delivery_opt_id,del_days, del_option, status, created_date, modified_date, soft_del  ) values (  '" + setClearData(bean.getDelivery_opt_id()) + "', '" + setClearData(bean.getDel_days()) + "',  '" + setClearData(bean.getDel_option()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                        GlobalData.printMessage("Delivery_option SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Delivery_option ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Delivery_option", e);
            }
        }
    }

    public void deleteDelivery_option() {
        String sql = "";
        try {


            sql = "delete from Delivery_option  ";
            GlobalData.printMessage("Delivery_option SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Delivery_option ", e);
        }
    }

    public void deleteDelivery_option(int rowId) {
        String sql = "";
        try {


            sql = "delete from Delivery_option where delivery_opt_id=" + rowId;
            GlobalData.printMessage("Delivery_option SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Delivery_option ", e);
        }
    }


//  Methods Of Delivery_slot

    public int getDelivery_slotCount() {
        String sql = "select count( Delivery_slotId ) as total from Delivery_slot 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Delivery_slot:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Delivery_slot> getDelivery_slot() {
        String sql = "select *  from Delivery_slot "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Delivery_slot> list = new LinkedList<Delivery_slot>();
        int i0 = c.getColumnIndex(Delivery_slot_KEY_deliveryslot_id);
        int i1 = c.getColumnIndex(Delivery_slot_KEY_slot_from);
        int i2 = c.getColumnIndex(Delivery_slot_KEY_slot_to);
        int i3 = c.getColumnIndex(Delivery_slot_KEY_modified_date);
        int i4 = c.getColumnIndex(Delivery_slot_KEY_created_date);
        int i5 = c.getColumnIndex(Delivery_slot_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Delivery_slot bean = new Delivery_slot();
                bean.setDeliveryslot_id(getClearData(c.getString(i0)));
                bean.setSlot_from(getClearData(c.getString(i1)));
                bean.setSlot_to(getClearData(c.getString(i2)));
                bean.setModified_date(getClearData(c.getString(i3)));
                bean.setCreated_date(getClearData(c.getString(i4)));
                bean.setSoft_del(getClearData(c.getString(i5)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Delivery_slot Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Delivery_slot> getDelivery_slotList(int deliveryslot_id) {
        String sql = "select *  from Delivery_slot where deliveryslot_id=" + deliveryslot_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Delivery_slot> list = new LinkedList<Delivery_slot>();
        int i0 = c.getColumnIndex(Delivery_slot_KEY_deliveryslot_id);
        int i1 = c.getColumnIndex(Delivery_slot_KEY_slot_from);
        int i2 = c.getColumnIndex(Delivery_slot_KEY_slot_to);
        int i3 = c.getColumnIndex(Delivery_slot_KEY_modified_date);
        int i4 = c.getColumnIndex(Delivery_slot_KEY_created_date);
        int i5 = c.getColumnIndex(Delivery_slot_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Delivery_slot bean = new Delivery_slot();
                bean.setDeliveryslot_id(getClearData(c.getString(i0)));
                bean.setSlot_from(getClearData(c.getString(i1)));
                bean.setSlot_to(getClearData(c.getString(i2)));
                bean.setModified_date(getClearData(c.getString(i3)));
                bean.setCreated_date(getClearData(c.getString(i4)));
                bean.setSoft_del(getClearData(c.getString(i5)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Delivery_slot Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addDelivery_slot(Delivery_slot bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert or replace into Delivery_slot  (deliveryslot_id , slot_from, slot_to, modified_date, created_date, soft_del ,sat_flag ) values (  '" + setClearData(bean.getDeliveryslot_id()) + "',   '" + setClearData(bean.getSlot_from()) + "',  '" + setClearData(bean.getSlot_to()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSoft_del()) + "','" + setClearData(bean.getSat_flag()) + "' )";
                GlobalData.printMessage("Delivery_slot SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Delivery_slot ", e);
        }
    }

    public void addDelivery_slotList(LinkedList<Delivery_slot> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Delivery_slot bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into Delivery_slot  (deliveryslot_id , slot_from, slot_to, modified_date, created_date, soft_del ,sat_flag ) values (  '" + setClearData(bean.getDeliveryslot_id()) + "',   '" + setClearData(bean.getSlot_from()) + "',  '" + setClearData(bean.getSlot_to()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSoft_del()) + "','" + setClearData(bean.getSat_flag()) + "' )";
                        GlobalData.printMessage("Delivery_slot SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Delivery_slot ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Delivery_slot", e);
            }
        }
    }

    public void deleteDelivery_slot() {
        String sql = "";
        try {


            sql = "delete from Delivery_slot  ";
            GlobalData.printMessage("Delivery_slot SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Delivery_slot ", e);
        }
    }

    public void deleteDelivery_slot(int rowId) {
        String sql = "";
        try {


            sql = "delete from Delivery_slot where deliveryslot_id=" + rowId;
            GlobalData.printMessage("Delivery_slot SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Delivery_slot ", e);
        }
    }


//  Methods Of Delivery_speed

    public int getDelivery_speedCount() {
        String sql = "select count( Delivery_speedId ) as total from Delivery_speed 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Delivery_speed:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Delivery_speed> getDelivery_speed() {
        String sql = "select *  from Delivery_speed "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Delivery_speed> list = new LinkedList<Delivery_speed>();
        int i0 = c.getColumnIndex(Delivery_speed_KEY_delivery_speed_id);
        int i1 = c.getColumnIndex(Delivery_speed_KEY_service_type_id);
        int i2 = c.getColumnIndex(Delivery_speed_KEY_time_slab_id);
        int i3 = c.getColumnIndex(Delivery_speed_KEY_delivery_option_id);
        int i4 = c.getColumnIndex(Delivery_speed_KEY_rate);
        int i5 = c.getColumnIndex(Delivery_speed_KEY_status);
        int i6 = c.getColumnIndex(Delivery_speed_KEY_created_date);
        int i7 = c.getColumnIndex(Delivery_speed_KEY_modified_date);
        int i8 = c.getColumnIndex(Delivery_speed_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Delivery_speed bean = new Delivery_speed();
                bean.setDelivery_speed_id(getClearData(c.getString(i0)));
                bean.setService_type_id(getClearData(c.getString(i1)));
                bean.setTime_slab_id(getClearData(c.getString(i2)));
                bean.setDelivery_option_id(getClearData(c.getString(i3)));
                bean.setRate(getClearData(c.getString(i4)));
                bean.setStatus(getClearData(c.getString(i5)));
                bean.setCreated_date(getClearData(c.getString(i6)));
                bean.setModified_date(getClearData(c.getString(i7)));
                bean.setSoft_del(getClearData(c.getString(i8)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Delivery_speed Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Delivery_speed> getDelivery_speedList(int delivery_speed_id) {
        String sql = "select *  from Delivery_speed where delivery_speed_id=" + delivery_speed_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Delivery_speed> list = new LinkedList<Delivery_speed>();
        int i0 = c.getColumnIndex(Delivery_speed_KEY_delivery_speed_id);
        int i1 = c.getColumnIndex(Delivery_speed_KEY_service_type_id);
        int i2 = c.getColumnIndex(Delivery_speed_KEY_time_slab_id);
        int i3 = c.getColumnIndex(Delivery_speed_KEY_delivery_option_id);
        int i4 = c.getColumnIndex(Delivery_speed_KEY_rate);
        int i5 = c.getColumnIndex(Delivery_speed_KEY_status);
        int i6 = c.getColumnIndex(Delivery_speed_KEY_created_date);
        int i7 = c.getColumnIndex(Delivery_speed_KEY_modified_date);
        int i8 = c.getColumnIndex(Delivery_speed_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Delivery_speed bean = new Delivery_speed();
                bean.setDelivery_speed_id(getClearData(c.getString(i0)));
                bean.setService_type_id(getClearData(c.getString(i1)));
                bean.setTime_slab_id(getClearData(c.getString(i2)));
                bean.setDelivery_option_id(getClearData(c.getString(i3)));
                bean.setRate(getClearData(c.getString(i4)));
                bean.setStatus(getClearData(c.getString(i5)));
                bean.setCreated_date(getClearData(c.getString(i6)));
                bean.setModified_date(getClearData(c.getString(i7)));
                bean.setSoft_del(getClearData(c.getString(i8)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Delivery_speed Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addDelivery_speed(Delivery_speed bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Delivery_speed  ( delivery_speed_id, service_type_id, time_slab_id, delivery_option_id, rate, status, created_date, modified_date, soft_del  ) values (  '" + setClearData(bean.getDelivery_speed_id()) + "', '" + setClearData(bean.getService_type_id()) + "',  '" + setClearData(bean.getTime_slab_id()) + "',  '" + setClearData(bean.getDelivery_option_id()) + "',  '" + setClearData(bean.getRate()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                GlobalData.printMessage("Delivery_speed SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Delivery_speed ", e);
        }
    }

    public void addDelivery_speedList(LinkedList<Delivery_speed> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Delivery_speed bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into Delivery_speed  ( delivery_speed_id, service_type_id, time_slab_id, delivery_option_id, rate, status, created_date, modified_date, soft_del  ) values (  '" + setClearData(bean.getDelivery_speed_id()) + "', '" + setClearData(bean.getService_type_id()) + "',  '" + setClearData(bean.getTime_slab_id()) + "',  '" + setClearData(bean.getDelivery_option_id()) + "',  '" + setClearData(bean.getRate()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                        GlobalData.printMessage("Delivery_speed SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Delivery_speed ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Delivery_speed", e);
            }
        }
    }

    public void deleteDelivery_speed() {
        String sql = "";
        try {


            sql = "delete from Delivery_speed  ";
            GlobalData.printMessage("Delivery_speed SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Delivery_speed ", e);
        }
    }

    public void deleteDelivery_speed(int rowId) {
        String sql = "";
        try {


            sql = "delete from Delivery_speed where delivery_speed_id=" + rowId;
            GlobalData.printMessage("Delivery_speed SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Delivery_speed ", e);
        }
    }


//  Methods Of Discount

    public int getDiscountCount() {
        String sql = "select count( DiscountId ) as total from Discount 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Discount:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Discount> getDiscount() {
        String sql = "select *  from Discount "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Discount> list = new LinkedList<Discount>();
        int i0 = c.getColumnIndex(Discount_KEY_discount_id);
        int i1 = c.getColumnIndex(Discount_KEY_discount_type_id);
        int i2 = c.getColumnIndex(Discount_KEY_service_type_id);
        int i3 = c.getColumnIndex(Discount_KEY_delivery_opt_id);
        int i4 = c.getColumnIndex(Discount_KEY_vas_id);
        int i5 = c.getColumnIndex(Discount_KEY_min_time);
        int i6 = c.getColumnIndex(Discount_KEY_max_time);
        int i7 = c.getColumnIndex(Discount_KEY_start_date);
        int i8 = c.getColumnIndex(Discount_KEY_end_date);
        int i9 = c.getColumnIndex(Discount_KEY_discount);
        int i10 = c.getColumnIndex(Discount_KEY_status);
        int i11 = c.getColumnIndex(Discount_KEY_modified_date);
        int i12 = c.getColumnIndex(Discount_KEY_created_date);
        int i13 = c.getColumnIndex(Discount_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Discount bean = new Discount();
                bean.setDiscount_id(c.getInt(i0));
                bean.setDiscount_type_id(c.getString(i1));
                bean.setService_type_id((c.getString(i2)));
                bean.setDelivery_opt_id((c.getString(i3)));
                bean.setVas_id(c.getString(i4));
                bean.setMin_time(getClearData(c.getString(i5)));
                bean.setMax_time(getClearData(c.getString(i6)));
                bean.setStart_date(getClearData(c.getString(i7)));
                bean.setEnd_date(getClearData(c.getString(i8)));
                bean.setDiscount(getClearData(c.getString(i9)));
                bean.setStatus(getClearData(c.getString(i10)));
                bean.setModified_date(getClearData(c.getString(i11)));
                bean.setCreated_date(getClearData(c.getString(i12)));
                bean.setSoft_del(getClearData(c.getString(i13)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Discount Read:", e);
            }
        }
        c.close();
        return list;
    }
//service_type_id,delivery_opt_id,vas_id,totalDurationinMin

    public Discount getDiscount(String service_type_id,String delivery_opt_id,String vas_id,float totalDurationinMin,String curdate) {
        String sql = "select *  from Discount where service_type_id ='"+service_type_id+"' and  delivery_opt_id ='"+delivery_opt_id+"' " +
                "" +
                "and vas_id='"+vas_id+"' and datetime(start_date) <= '"+curdate+"' and datetime( end_date) >= '"+curdate+"' and cast(min_time as float) <= "+totalDurationinMin+" and cast(max_time as float) >= "+totalDurationinMin+"";
        Cursor c = ourDatabase.rawQuery(sql, null);
        Discount bean = new Discount();
        int i0 = c.getColumnIndex(Discount_KEY_discount_id);
        int i1 = c.getColumnIndex(Discount_KEY_discount_type_id);
        int i2 = c.getColumnIndex(Discount_KEY_service_type_id);
        int i3 = c.getColumnIndex(Discount_KEY_delivery_opt_id);
        int i4 = c.getColumnIndex(Discount_KEY_vas_id);
        int i5 = c.getColumnIndex(Discount_KEY_min_time);
        int i6 = c.getColumnIndex(Discount_KEY_max_time);
        int i7 = c.getColumnIndex(Discount_KEY_start_date);
        int i8 = c.getColumnIndex(Discount_KEY_end_date);
        int i9 = c.getColumnIndex(Discount_KEY_discount);
        int i10 = c.getColumnIndex(Discount_KEY_status);
        int i11 = c.getColumnIndex(Discount_KEY_modified_date);
        int i12 = c.getColumnIndex(Discount_KEY_created_date);
        int i13 = c.getColumnIndex(Discount_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setDiscount_id(c.getInt(i0));
                bean.setDiscount_type_id(c.getString(i1));
                bean.setService_type_id((c.getString(i2)));
                bean.setDelivery_opt_id((c.getString(i3)));
                bean.setVas_id(c.getString(i4));
                bean.setMin_time(getClearData(c.getString(i5)));
                bean.setMax_time(getClearData(c.getString(i6)));
                bean.setStart_date(getClearData(c.getString(i7)));
                bean.setEnd_date(getClearData(c.getString(i8)));
                bean.setDiscount(getClearData(c.getString(i9)));
                bean.setStatus(getClearData(c.getString(i10)));
                bean.setModified_date(getClearData(c.getString(i11)));
                bean.setCreated_date(getClearData(c.getString(i12)));
                bean.setSoft_del(getClearData(c.getString(i13)));


            } catch (Exception e) {
                GlobalData.printError("Discount Read:", e);
            }
        }
        c.close();
        return bean;
    }


    public LinkedList<Discount> getDiscountList(int discount_id) {
        String sql = "select *  from Discount where discount_id=" + discount_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Discount> list = new LinkedList<Discount>();
        int i0 = c.getColumnIndex(Discount_KEY_discount_id);
        int i1 = c.getColumnIndex(Discount_KEY_discount_type_id);
        int i2 = c.getColumnIndex(Discount_KEY_service_type_id);
        int i3 = c.getColumnIndex(Discount_KEY_delivery_opt_id);
        int i4 = c.getColumnIndex(Discount_KEY_vas_id);
        int i5 = c.getColumnIndex(Discount_KEY_min_time);
        int i6 = c.getColumnIndex(Discount_KEY_max_time);
        int i7 = c.getColumnIndex(Discount_KEY_start_date);
        int i8 = c.getColumnIndex(Discount_KEY_end_date);
        int i9 = c.getColumnIndex(Discount_KEY_discount);
        int i10 = c.getColumnIndex(Discount_KEY_status);
        int i11 = c.getColumnIndex(Discount_KEY_modified_date);
        int i12 = c.getColumnIndex(Discount_KEY_created_date);
        int i13 = c.getColumnIndex(Discount_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Discount bean = new Discount();
                bean.setDiscount_id(c.getInt(i0));
                bean.setDiscount_type_id((c.getString(i1)));
                bean.setService_type_id((c.getString(i2)));
                bean.setDelivery_opt_id((c.getString(i3)));
                bean.setVas_id((c.getString(i4)));
                bean.setMin_time(getClearData(c.getString(i5)));
                bean.setMax_time(getClearData(c.getString(i6)));
                bean.setStart_date(getClearData(c.getString(i7)));
                bean.setEnd_date(getClearData(c.getString(i8)));
                bean.setDiscount(getClearData(c.getString(i9)));
                bean.setStatus(getClearData(c.getString(i10)));
                bean.setModified_date(getClearData(c.getString(i11)));
                bean.setCreated_date(getClearData(c.getString(i12)));
                bean.setSoft_del(getClearData(c.getString(i13)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Discount Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addDiscount(Discount bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert or replace into Discount  ( discount_id, discount_type_id, service_type_id, delivery_opt_id, vas_id, min_time, max_time, start_date, end_date, discount, status, modified_date, created_date, soft_del  ) values ( "+bean.getDiscount_id()+", " + (bean.getDiscount_type_id()) + ",  " + (bean.getService_type_id()) + ",  " + (bean.getDelivery_opt_id()) + ",  " + (bean.getVas_id()) + ",  '" + setClearData(bean.getMin_time()) + "',  '" + setClearData(bean.getMax_time()) + "',  '" + setClearData(bean.getStart_date()) + "',  '" + setClearData(bean.getEnd_date()) + "',  '" + setClearData(bean.getDiscount()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                GlobalData.printMessage("Discount SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Discount ", e);
        }
    }

    public void addDiscountList(LinkedList<Discount> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Discount bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into Discount  ( discount_id, discount_type_id, service_type_id, delivery_opt_id, vas_id, min_time, max_time, start_date, end_date, discount, status, modified_date, created_date, soft_del  ) values ( "+bean.getDiscount_id()+", '" + (bean.getDiscount_type_id()) + "',  '" + (bean.getService_type_id()) + "',  '" + (bean.getDelivery_opt_id()) + "',  '" + (bean.getVas_id()) + "',  '" + setClearData(bean.getMin_time()) + "',  '" + setClearData(bean.getMax_time()) + "',  '" + setClearData(bean.getStart_date()) + "',  '" + setClearData(bean.getEnd_date()) + "',  '" + setClearData(bean.getDiscount()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                        GlobalData.printMessage("Discount SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Discount ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Discount", e);
            }
        }
    }

    public void deleteDiscount() {
        String sql = "";
        try {


            sql = "delete from Discount  ";
            GlobalData.printMessage("Discount SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Discount ", e);
        }
    }

    public void deleteDiscount(int rowId) {
        String sql = "";
        try {


            sql = "delete from Discount where discount_id=" + rowId;
            GlobalData.printMessage("Discount SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Discount ", e);
        }
    }


//  Methods Of Discount_type

    public int getDiscount_typeCount() {
        String sql = "select count( Discount_typeId ) as total from Discount_type 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Discount_type:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Discount_type> getDiscount_type() {
        String sql = "select *  from Discount_type "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Discount_type> list = new LinkedList<Discount_type>();
        int i0 = c.getColumnIndex(Discount_type_KEY_discount_type_id);
        int i1 = c.getColumnIndex(Discount_type_KEY_discount_txt);
        int i2 = c.getColumnIndex(Discount_type_KEY_modified_date);
        int i3 = c.getColumnIndex(Discount_type_KEY_created_date);
        int i4 = c.getColumnIndex(Discount_type_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Discount_type bean = new Discount_type();
                bean.setDiscount_type_id(getClearData(c.getString(i0)));
                bean.setDiscount_txt(getClearData(c.getString(i1)));
                bean.setModified_date(getClearData(c.getString(i2)));
                bean.setCreated_date(getClearData(c.getString(i3)));
                bean.setSoft_del(getClearData(c.getString(i4)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Discount_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Discount_type> getDiscount_typeList(int discount_type_id) {
        String sql = "select *  from Discount_type where discount_type_id=" + discount_type_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Discount_type> list = new LinkedList<Discount_type>();
        int i0 = c.getColumnIndex(Discount_type_KEY_discount_type_id);
        int i1 = c.getColumnIndex(Discount_type_KEY_discount_txt);
        int i2 = c.getColumnIndex(Discount_type_KEY_modified_date);
        int i3 = c.getColumnIndex(Discount_type_KEY_created_date);
        int i4 = c.getColumnIndex(Discount_type_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Discount_type bean = new Discount_type();
                bean.setDiscount_type_id(getClearData(c.getString(i0)));
                bean.setDiscount_txt(getClearData(c.getString(i1)));
                bean.setModified_date(getClearData(c.getString(i2)));
                bean.setCreated_date(getClearData(c.getString(i3)));
                bean.setSoft_del(getClearData(c.getString(i4)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Discount_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addDiscount_type(Discount_type bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert or replace into Discount_type  ( discount_type_id,discount_txt, modified_date, created_date, soft_del  ) values ( '"+bean.getDiscount_type_id()+"',  '" + setClearData(bean.getDiscount_txt()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                GlobalData.printMessage("Discount_type SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Discount_type ", e);
        }
    }

    public void addDiscount_typeList(LinkedList<Discount_type> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Discount_type bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into Discount_type  ( discount_type_id,discount_txt, modified_date, created_date, soft_del  ) values ( '"+bean.getDiscount_type_id()+"',  '" + setClearData(bean.getDiscount_txt()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                        GlobalData.printMessage("Discount_type SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Discount_type ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Discount_type", e);
            }
        }
    }

    public void deleteDiscount_type() {
        String sql = "";
        try {


            sql = "delete from Discount_type  ";
            GlobalData.printMessage("Discount_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Discount_type ", e);
        }
    }

    public void deleteDiscount_type(int rowId) {
        String sql = "";
        try {


            sql = "delete from Discount_type where discount_type_id=" + rowId;
            GlobalData.printMessage("Discount_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Discount_type ", e);
        }
    }


//  Methods Of Eng_local_resources

    public int getEng_local_resourcesCount() {
        String sql = "select count( Eng_local_resourcesId ) as total from Eng_local_resources 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Eng_local_resources:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Eng_local_resources> getEng_local_resources() {
        String sql = "select *  from Eng_local_resources "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Eng_local_resources> list = new LinkedList<Eng_local_resources>();
        int i0 = c.getColumnIndex(Eng_local_resources_KEY_resource_id);
        int i1 = c.getColumnIndex(Eng_local_resources_KEY_resource_name);
        int i2 = c.getColumnIndex(Eng_local_resources_KEY_resource_value);
        int i3 = c.getColumnIndex(Eng_local_resources_KEY_soft_del);
        int i4 = c.getColumnIndex(Eng_local_resources_KEY_created_date);
        int i5 = c.getColumnIndex(Eng_local_resources_KEY_modified_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Eng_local_resources bean = new Eng_local_resources();
                bean.setResource_id(c.getInt(i0));
                bean.setResource_name(getClearData(c.getString(i1)));
                bean.setResource_value(getClearData(c.getString(i2)));
                bean.setSoft_del(getClearData(c.getString(i3)));
                bean.setCreated_date(getClearData(c.getString(i4)));
                bean.setModified_date(getClearData(c.getString(i5)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Eng_local_resources Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Eng_local_resources> getEng_local_resourcesList(int resource_id) {
        String sql = "select *  from Eng_local_resources where resource_id=" + resource_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Eng_local_resources> list = new LinkedList<Eng_local_resources>();
        int i0 = c.getColumnIndex(Eng_local_resources_KEY_resource_id);
        int i1 = c.getColumnIndex(Eng_local_resources_KEY_resource_name);
        int i2 = c.getColumnIndex(Eng_local_resources_KEY_resource_value);
        int i3 = c.getColumnIndex(Eng_local_resources_KEY_soft_del);
        int i4 = c.getColumnIndex(Eng_local_resources_KEY_created_date);
        int i5 = c.getColumnIndex(Eng_local_resources_KEY_modified_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Eng_local_resources bean = new Eng_local_resources();
                bean.setResource_id(c.getInt(i0));
                bean.setResource_name(getClearData(c.getString(i1)));
                bean.setResource_value(getClearData(c.getString(i2)));
                bean.setSoft_del(getClearData(c.getString(i3)));
                bean.setCreated_date(getClearData(c.getString(i4)));
                bean.setModified_date(getClearData(c.getString(i5)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Eng_local_resources Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addEng_local_resources(Eng_local_resources bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Eng_local_resources  ( resource_name, resource_value, soft_del, created_date, modified_date  ) values (  '" + setClearData(bean.getResource_name()) + "',  '" + setClearData(bean.getResource_value()) + "',  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "' )";
                GlobalData.printMessage("Eng_local_resources SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Eng_local_resources ", e);
        }
    }

    public void addEng_local_resourcesList(LinkedList<Eng_local_resources> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Eng_local_resources bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Eng_local_resources  ( resource_name, resource_value, soft_del, created_date, modified_date  ) values (  '" + setClearData(bean.getResource_name()) + "',  '" + setClearData(bean.getResource_value()) + "',  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "' )";
                        GlobalData.printMessage("Eng_local_resources SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Eng_local_resources ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Eng_local_resources", e);
            }
        }
    }

    public void deleteEng_local_resources() {
        String sql = "";
        try {


            sql = "delete from Eng_local_resources  ";
            GlobalData.printMessage("Eng_local_resources SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Eng_local_resources ", e);
        }
    }

    public void deleteEng_local_resources(int rowId) {
        String sql = "";
        try {


            sql = "delete from Eng_local_resources where resource_id=" + rowId;
            GlobalData.printMessage("Eng_local_resources SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Eng_local_resources ", e);
        }
    }


//  Methods Of Error_log

    public int getError_logCount() {
        String sql = "select count( Error_logId ) as total from Error_log 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Error_log:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Error_log> getError_log() {
        String sql = "select *  from Error_log ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Error_log> list = new LinkedList<Error_log>();
        int i0 = c.getColumnIndex(Error_log_KEY_logid);
        int i1 = c.getColumnIndex(Error_log_KEY_logtime);
        int i2 = c.getColumnIndex(Error_log_KEY_username);
        int i3 = c.getColumnIndex(Error_log_KEY_dbname);
        int i4 = c.getColumnIndex(Error_log_KEY_actionmsg);
        int i5 = c.getColumnIndex(Error_log_KEY_errormsg);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Error_log bean = new Error_log();
                bean.setLogid(c.getInt(i0));
                bean.setLogtime(getClearData(c.getString(i1)));
                bean.setUsername(c.getInt(i2));
                bean.setDbname(c.getInt(i3));
                bean.setActionmsg(getClearData(c.getString(i4)));
                bean.setErrormsg(getClearData(c.getString(i5)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Error_log Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Error_log> getError_logList(int logid) {
        String sql = "select *  from Error_log where logid=" + logid;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Error_log> list = new LinkedList<Error_log>();
        int i0 = c.getColumnIndex(Error_log_KEY_logid);
        int i1 = c.getColumnIndex(Error_log_KEY_logtime);
        int i2 = c.getColumnIndex(Error_log_KEY_username);
        int i3 = c.getColumnIndex(Error_log_KEY_dbname);
        int i4 = c.getColumnIndex(Error_log_KEY_actionmsg);
        int i5 = c.getColumnIndex(Error_log_KEY_errormsg);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Error_log bean = new Error_log();
                bean.setLogid(c.getInt(i0));
                bean.setLogtime(getClearData(c.getString(i1)));
                bean.setUsername(c.getInt(i2));
                bean.setDbname(c.getInt(i3));
                bean.setActionmsg(getClearData(c.getString(i4)));
                bean.setErrormsg(getClearData(c.getString(i5)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Error_log Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addError_log(Error_log bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Error_log  ( logtime, username, dbname, actionmsg, errormsg  ) values (  '" + setClearData(bean.getLogtime()) + "',  " + bean.getUsername() + ",  " + bean.getDbname() + ",  '" + setClearData(bean.getActionmsg()) + "',  '" + setClearData(bean.getErrormsg()) + "' )";
                GlobalData.printMessage("Error_log SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Error_log ", e);
        }
    }

    public void addError_logList(LinkedList<Error_log> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Error_log bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Error_log  ( logtime, username, dbname, actionmsg, errormsg  ) values (  '" + setClearData(bean.getLogtime()) + "',  " + bean.getUsername() + ",  " + bean.getDbname() + ",  '" + setClearData(bean.getActionmsg()) + "',  '" + setClearData(bean.getErrormsg()) + "' )";
                        GlobalData.printMessage("Error_log SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Error_log ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Error_log", e);
            }
        }
    }

    public void deleteError_log() {
        String sql = "";
        try {


            sql = "delete from Error_log  ";
            GlobalData.printMessage("Error_log SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Error_log ", e);
        }
    }

    public void deleteError_log(int rowId) {
        String sql = "";
        try {


            sql = "delete from Error_log where logid=" + rowId;
            GlobalData.printMessage("Error_log SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Error_log ", e);
        }
    }


//  Methods Of Feedback

    public int getFeedbackCount() {
        String sql = "select count( FeedbackId ) as total from Feedback 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Feedback:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Feedback> getFeedback() {
        String sql = "select *  from Feedback "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Feedback> list = new LinkedList<Feedback>();
        int i0 = c.getColumnIndex(Feedback_KEY_feedback_id);
        int i1 = c.getColumnIndex(Feedback_KEY_user_id);
        int i2 = c.getColumnIndex(Feedback_KEY_order_id);
        int i3 = c.getColumnIndex(Feedback_KEY_feed_txt);
        int i4 = c.getColumnIndex(Feedback_KEY_created_date);
        int i5 = c.getColumnIndex(Feedback_KEY_feedback_type);
        int i6 = c.getColumnIndex(Feedback_KEY_assignment_no);
        int i7 = c.getColumnIndex(Feedback_KEY_files_nams);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Feedback bean = new Feedback();
                bean.setFeedback_id(c.getInt(i0));
                bean.setUser_id(c.getInt(i1));
                bean.setOrder_id(c.getInt(i2));
                bean.setFeed_txt(getClearData(c.getString(i3)));
                bean.setCreated_date(getClearData(c.getString(i4)));
                bean.setFeedback_type(getClearData(c.getString(i5)));
                bean.setAssignment_no(getClearData(c.getString(i6)));
                bean.setFiles_nams(getClearData(c.getString(i7)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Feedback Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Feedback> getFeedbackList(int feedback_id) {
        String sql = "select *  from Feedback where feedback_id=" + feedback_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Feedback> list = new LinkedList<Feedback>();
        int i0 = c.getColumnIndex(Feedback_KEY_feedback_id);
        int i1 = c.getColumnIndex(Feedback_KEY_user_id);
        int i2 = c.getColumnIndex(Feedback_KEY_order_id);
        int i3 = c.getColumnIndex(Feedback_KEY_feed_txt);
        int i4 = c.getColumnIndex(Feedback_KEY_created_date);
        int i5 = c.getColumnIndex(Feedback_KEY_feedback_type);
        int i6 = c.getColumnIndex(Feedback_KEY_assignment_no);
        int i7 = c.getColumnIndex(Feedback_KEY_files_nams);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Feedback bean = new Feedback();
                bean.setFeedback_id(c.getInt(i0));
                bean.setUser_id(c.getInt(i1));
                bean.setOrder_id(c.getInt(i2));
                bean.setFeed_txt(getClearData(c.getString(i3)));
                bean.setCreated_date(getClearData(c.getString(i4)));
                bean.setFeedback_type(getClearData(c.getString(i5)));
                bean.setAssignment_no(getClearData(c.getString(i6)));
                bean.setFiles_nams(getClearData(c.getString(i7)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Feedback Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addFeedback(Feedback bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Feedback  ( user_id, order_id, feed_txt, created_date, feedback_type, assignment_no, files_nams  ) values (  " + bean.getUser_id() + ",  " + bean.getOrder_id() + ",  '" + setClearData(bean.getFeed_txt()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getFeedback_type()) + "',  '" + setClearData(bean.getAssignment_no()) + "',  '" + setClearData(bean.getFiles_nams()) + "' )";
                GlobalData.printMessage("Feedback SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Feedback ", e);
        }
    }

    public void addFeedbackList(LinkedList<Feedback> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Feedback bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Feedback  ( user_id, order_id, feed_txt, created_date, feedback_type, assignment_no, files_nams  ) values (  " + bean.getUser_id() + ",  " + bean.getOrder_id() + ",  '" + setClearData(bean.getFeed_txt()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getFeedback_type()) + "',  '" + setClearData(bean.getAssignment_no()) + "',  '" + setClearData(bean.getFiles_nams()) + "' )";
                        GlobalData.printMessage("Feedback SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Feedback ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Feedback", e);
            }
        }
    }

    public void deleteFeedback() {
        String sql = "";
        try {


            sql = "delete from Feedback  ";
            GlobalData.printMessage("Feedback SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Feedback ", e);
        }
    }

    public void deleteFeedback(int rowId) {
        String sql = "";
        try {


            sql = "delete from Feedback where feedback_id=" + rowId;
            GlobalData.printMessage("Feedback SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Feedback ", e);
        }
    }


//  Methods Of File_source_type

    public int getFile_source_typeCount() {
        String sql = "select count( File_source_typeId ) as total from File_source_type 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  File_source_type:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<File_source_type> getFile_source_type() {
        String sql = "select *  from File_source_type "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<File_source_type> list = new LinkedList<File_source_type>();
        int i0 = c.getColumnIndex(File_source_type_KEY_source_type);
        int i1 = c.getColumnIndex(File_source_type_KEY_source_name);
        int i2 = c.getColumnIndex(File_source_type_KEY_active);
        int i3 = c.getColumnIndex(File_source_type_KEY_soft_del);
        int i4 = c.getColumnIndex(File_source_type_KEY_created_date);
        int i5 = c.getColumnIndex(File_source_type_KEY_modified_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                File_source_type bean = new File_source_type();
                bean.setSource_type(getClearData(c.getString(i0)));
                bean.setSource_name(getClearData(c.getString(i1)));
                bean.setActive(getClearData(c.getString(i2)));
                bean.setSoft_del(getClearData(c.getString(i3)));
                bean.setCreated_date(getClearData(c.getString(i4)));
                bean.setModified_date(getClearData(c.getString(i5)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("File_source_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<File_source_type> getFile_source_typeList(int source_type) {
        String sql = "select *  from File_source_type where source_type=" + source_type;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<File_source_type> list = new LinkedList<File_source_type>();
        int i0 = c.getColumnIndex(File_source_type_KEY_source_type);
        int i1 = c.getColumnIndex(File_source_type_KEY_source_name);
        int i2 = c.getColumnIndex(File_source_type_KEY_active);
        int i3 = c.getColumnIndex(File_source_type_KEY_soft_del);
        int i4 = c.getColumnIndex(File_source_type_KEY_created_date);
        int i5 = c.getColumnIndex(File_source_type_KEY_modified_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                File_source_type bean = new File_source_type();
                bean.setSource_type(getClearData(c.getString(i0)));
                bean.setSource_name(getClearData(c.getString(i1)));
                bean.setActive(getClearData(c.getString(i2)));
                bean.setSoft_del(getClearData(c.getString(i3)));
                bean.setCreated_date(getClearData(c.getString(i4)));
                bean.setModified_date(getClearData(c.getString(i5)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("File_source_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addFile_source_type(File_source_type bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert or replace into File_source_type  ( source_type,source_name, active, soft_del, created_date, modified_date  ) values ( '" + setClearData(bean.getSource_type()) + "', '" + setClearData(bean.getSource_name()) + "',  '" + setClearData(bean.getActive()) + "',  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "' )";
                GlobalData.printMessage("File_source_type SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add File_source_type ", e);
        }
    }

    public void addFile_source_typeList(LinkedList<File_source_type> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                File_source_type bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into File_source_type  ( source_type,source_name, active, soft_del, created_date, modified_date  ) values ( '" + setClearData(bean.getSource_type()) + "', '" + setClearData(bean.getSource_name()) + "',  '" + setClearData(bean.getActive()) + "',  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "' )";
                        GlobalData.printMessage("File_source_type SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add File_source_type ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("File_source_type", e);
            }
        }
    }

    public void deleteFile_source_type() {
        String sql = "";
        try {


            sql = "delete from File_source_type  ";
            GlobalData.printMessage("File_source_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete File_source_type ", e);
        }
    }

    public void deleteFile_source_type(int rowId) {
        String sql = "";
        try {


            sql = "delete from File_source_type where source_type=" + rowId;
            GlobalData.printMessage("File_source_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete File_source_type ", e);
        }
    }


//  Methods Of File_status_type

    public int getFile_status_typeCount() {
        String sql = "select count( File_status_typeId ) as total from File_status_type 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  File_status_type:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<File_status_type> getFile_status_type() {
        String sql = "select *  from File_status_type "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<File_status_type> list = new LinkedList<File_status_type>();
        int i0 = c.getColumnIndex(File_status_type_KEY_file_status_msg);
        int i1 = c.getColumnIndex(File_status_type_KEY_file_status);
        int i2 = c.getColumnIndex(File_status_type_KEY_is_active);
        int i3 = c.getColumnIndex(File_status_type_KEY_sort_order);
        int i4 = c.getColumnIndex(File_status_type_KEY_soft_del);
        int i5 = c.getColumnIndex(File_status_type_KEY_modified_date);
        int i6 = c.getColumnIndex(File_status_type_KEY_created_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                File_status_type bean = new File_status_type();
                bean.setFile_status_msg(getClearData(c.getString(i0)));
                bean.setFile_status(getClearData(c.getString(i1)));
                bean.setIs_active(getClearData(c.getString(i2)));
                bean.setSort_order(c.getInt(i3));
                bean.setSoft_del(getClearData(c.getString(i4)));
                bean.setModified_date(getClearData(c.getString(i5)));
                bean.setCreated_date(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("File_status_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public File_status_type getFile_status_type(String file_status ) {
        String sql = "select *  from File_status_type where file_status='"+file_status+"'";
        Cursor c = ourDatabase.rawQuery(sql, null);
        File_status_type bean = new File_status_type();
        int i0 = c.getColumnIndex(File_status_type_KEY_file_status_msg);
        int i1 = c.getColumnIndex(File_status_type_KEY_file_status);
        int i2 = c.getColumnIndex(File_status_type_KEY_is_active);
        int i3 = c.getColumnIndex(File_status_type_KEY_sort_order);
        int i4 = c.getColumnIndex(File_status_type_KEY_soft_del);
        int i5 = c.getColumnIndex(File_status_type_KEY_modified_date);
        int i6 = c.getColumnIndex(File_status_type_KEY_created_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setFile_status_msg(getClearData(c.getString(i0)));
                bean.setFile_status(getClearData(c.getString(i1)));
                bean.setIs_active(getClearData(c.getString(i2)));
                bean.setSort_order(c.getInt(i3));
                bean.setSoft_del(getClearData(c.getString(i4)));
                bean.setModified_date(getClearData(c.getString(i5)));
                bean.setCreated_date(getClearData(c.getString(i6)));


            } catch (Exception e) {
                GlobalData.printError("File_status_type Read:", e);
            }
        }
        c.close();
        return bean;
    }

    public LinkedList<File_status_type> getFile_status_typeList(int file_status_msg) {
        String sql = "select *  from File_status_type where file_status_msg=" + file_status_msg;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<File_status_type> list = new LinkedList<File_status_type>();
        int i0 = c.getColumnIndex(File_status_type_KEY_file_status_msg);
        int i1 = c.getColumnIndex(File_status_type_KEY_file_status);
        int i2 = c.getColumnIndex(File_status_type_KEY_is_active);
        int i3 = c.getColumnIndex(File_status_type_KEY_sort_order);
        int i4 = c.getColumnIndex(File_status_type_KEY_soft_del);
        int i5 = c.getColumnIndex(File_status_type_KEY_modified_date);
        int i6 = c.getColumnIndex(File_status_type_KEY_created_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                File_status_type bean = new File_status_type();
                bean.setFile_status_msg(getClearData(c.getString(i0)));
                bean.setFile_status(getClearData(c.getString(i1)));
                bean.setIs_active(getClearData(c.getString(i2)));
                bean.setSort_order(c.getInt(i3));
                bean.setSoft_del(getClearData(c.getString(i4)));
                bean.setModified_date(getClearData(c.getString(i5)));
                bean.setCreated_date(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("File_status_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addFile_status_type(File_status_type bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert or replace into File_status_type  ( file_status_msg,file_status, is_active, sort_order, soft_del, modified_date, created_date  ) values (  '" + setClearData(bean.getFile_status_msg()) + "','" + setClearData(bean.getFile_status()) + "',  '" + setClearData(bean.getIs_active()) + "',  " + bean.getSort_order() + ",  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "' )";
                GlobalData.printMessage("File_status_type SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add File_status_type ", e);
        }
    }

    public void addFile_status_typeList(LinkedList<File_status_type> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                File_status_type bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into File_status_type  ( file_status_msg,file_status, is_active, sort_order, soft_del, modified_date, created_date  ) values (  '" + setClearData(bean.getFile_status_msg()) + "','" + setClearData(bean.getFile_status()) + "',  '" + setClearData(bean.getIs_active()) + "',  " + bean.getSort_order() + ",  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "' )";
                        GlobalData.printMessage("File_status_type SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add File_status_type ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("File_status_type", e);
            }
        }
    }

    public void deleteFile_status_type() {
        String sql = "";
        try {


            sql = "delete from File_status_type  ";
            GlobalData.printMessage("File_status_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete File_status_type ", e);
        }
    }

    public void deleteFile_status_type(int rowId) {
        String sql = "";
        try {


            sql = "delete from File_status_type where file_status_msg=" + rowId;
            GlobalData.printMessage("File_status_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete File_status_type ", e);
        }
    }


//  Methods Of Free_services

    public int getFree_servicesCount() {
        String sql = "select count( Free_servicesId ) as total from Free_services 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Free_services:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Free_services> getFree_services() {
        String sql = "select *  from Free_services "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Free_services> list = new LinkedList<Free_services>();
        int i0 = c.getColumnIndex(Free_services_KEY_free_service_id);
        int i1 = c.getColumnIndex(Free_services_KEY_free_service_txt);
        int i2 = c.getColumnIndex(Free_services_KEY_default_set);
        int i3 = c.getColumnIndex(Free_services_KEY_service_flag);
        int i4 = c.getColumnIndex(Free_services_KEY_trans_flag);
        int i5 = c.getColumnIndex(Free_services_KEY_timeslab_flag);
        int i6 = c.getColumnIndex(Free_services_KEY_vas_flag);
        int i7 = c.getColumnIndex(Free_services_KEY_status);
        int i8 = c.getColumnIndex(Free_services_KEY_created_date);
        int i9 = c.getColumnIndex(Free_services_KEY_modified_date);
        int i10 = c.getColumnIndex(Free_services_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Free_services bean = new Free_services();
                bean.setFree_service_id(c.getString(i0));
                bean.setFree_service_txt(getClearData(c.getString(i1)));
                bean.setDefault_set(getClearData(c.getString(i2)));
                bean.setService_flag(getClearData(c.getString(i3)));
                bean.setTrans_flag(getClearData(c.getString(i4)));
                bean.setTimeslab_flag(getClearData(c.getString(i5)));
                bean.setVas_flag(getClearData(c.getString(i6)));
                bean.setStatus(getClearData(c.getString(i7)));
                bean.setCreated_date(getClearData(c.getString(i8)));
                bean.setModified_date(getClearData(c.getString(i9)));
                bean.setSoft_del(getClearData(c.getString(i10)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Free_services Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Free_services> getFree_servicesList(int free_service_id) {
        String sql = "select *  from Free_services where free_service_id=" + free_service_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Free_services> list = new LinkedList<Free_services>();
        int i0 = c.getColumnIndex(Free_services_KEY_free_service_id);
        int i1 = c.getColumnIndex(Free_services_KEY_free_service_txt);
        int i2 = c.getColumnIndex(Free_services_KEY_default_set);
        int i3 = c.getColumnIndex(Free_services_KEY_service_flag);
        int i4 = c.getColumnIndex(Free_services_KEY_trans_flag);
        int i5 = c.getColumnIndex(Free_services_KEY_timeslab_flag);
        int i6 = c.getColumnIndex(Free_services_KEY_vas_flag);
        int i7 = c.getColumnIndex(Free_services_KEY_status);
        int i8 = c.getColumnIndex(Free_services_KEY_created_date);
        int i9 = c.getColumnIndex(Free_services_KEY_modified_date);
        int i10 = c.getColumnIndex(Free_services_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Free_services bean = new Free_services();
                bean.setFree_service_id(c.getString(i0));
                bean.setFree_service_txt(getClearData(c.getString(i1)));
                bean.setDefault_set(getClearData(c.getString(i2)));
                bean.setService_flag(getClearData(c.getString(i3)));
                bean.setTrans_flag(getClearData(c.getString(i4)));
                bean.setTimeslab_flag(getClearData(c.getString(i5)));
                bean.setVas_flag(getClearData(c.getString(i6)));
                bean.setStatus(getClearData(c.getString(i7)));
                bean.setCreated_date(getClearData(c.getString(i8)));
                bean.setModified_date(getClearData(c.getString(i9)));
                bean.setSoft_del(getClearData(c.getString(i10)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Free_services Read:", e);
            }
        }
        c.close();
        return list;
    }


    public Free_services getFree_servicesText(String free_service_text) {
        String sql = "select *  from Free_services where free_service_txt like  '" + free_service_text+"'";
        Cursor c = ourDatabase.rawQuery(sql, null);
        Free_services bean = new Free_services();
        int i0 = c.getColumnIndex(Free_services_KEY_free_service_id);
        int i1 = c.getColumnIndex(Free_services_KEY_free_service_txt);
        int i2 = c.getColumnIndex(Free_services_KEY_default_set);
        int i3 = c.getColumnIndex(Free_services_KEY_service_flag);
        int i4 = c.getColumnIndex(Free_services_KEY_trans_flag);
        int i5 = c.getColumnIndex(Free_services_KEY_timeslab_flag);
        int i6 = c.getColumnIndex(Free_services_KEY_vas_flag);
        int i7 = c.getColumnIndex(Free_services_KEY_status);
        int i8 = c.getColumnIndex(Free_services_KEY_created_date);
        int i9 = c.getColumnIndex(Free_services_KEY_modified_date);
        int i10 = c.getColumnIndex(Free_services_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setFree_service_id(c.getString(i0));
                bean.setFree_service_txt(getClearData(c.getString(i1)));
                bean.setDefault_set(getClearData(c.getString(i2)));
                bean.setService_flag(getClearData(c.getString(i3)));
                bean.setTrans_flag(getClearData(c.getString(i4)));
                bean.setTimeslab_flag(getClearData(c.getString(i5)));
                bean.setVas_flag(getClearData(c.getString(i6)));
                bean.setStatus(getClearData(c.getString(i7)));
                bean.setCreated_date(getClearData(c.getString(i8)));
                bean.setModified_date(getClearData(c.getString(i9)));
                bean.setSoft_del(getClearData(c.getString(i10)));


            } catch (Exception e) {
                GlobalData.printError("Free_services Read:", e);
            }
        }
        c.close();
        return bean;
    }

    public void addFree_services(Free_services bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert or replace into Free_services  ( free_service_id,free_service_txt, default_set, service_flag, trans_flag, timeslab_flag, vas_flag, status, created_date, modified_date, soft_del  ) values ( '" + bean.getFree_service_id() + "', '" + setClearData(bean.getFree_service_txt()) + "',  '" + setClearData(bean.getDefault_set()) + "',  '" + setClearData(bean.getService_flag()) + "',  '" + setClearData(bean.getTrans_flag()) + "',  '" + setClearData(bean.getTimeslab_flag()) + "',  '" + setClearData(bean.getVas_flag()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                GlobalData.printMessage("Free_services SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Free_services ", e);
        }
    }

    public void addFree_servicesList(LinkedList<Free_services> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Free_services bean = beanList.get(i);
                try {
                    if (bean != null) {

                        sql = "insert or replace into Free_services  ( free_service_id,free_service_txt, default_set, service_flag, trans_flag, timeslab_flag, vas_flag, status, created_date, modified_date, soft_del  ) values ( '" + bean.getFree_service_id() + "', '" + setClearData(bean.getFree_service_txt()) + "',  '" + setClearData(bean.getDefault_set()) + "',  '" + setClearData(bean.getService_flag()) + "',  '" + setClearData(bean.getTrans_flag()) + "',  '" + setClearData(bean.getTimeslab_flag()) + "',  '" + setClearData(bean.getVas_flag()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                        GlobalData.printMessage("Free_services SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Free_services ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Free_services", e);
            }
        }
    }

    public void deleteFree_services() {
        String sql = "";
        try {


            sql = "delete from Free_services  ";
            GlobalData.printMessage("Free_services SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Free_services ", e);
        }
    }

    public void deleteFree_services(int rowId) {
        String sql = "";
        try {


            sql = "delete from Free_services where free_service_id=" + rowId;
            GlobalData.printMessage("Free_services SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Free_services ", e);
        }
    }


//  Methods Of Holidays

    public boolean isHolidays(int day ,int month,int year) {
        String sql = "select  count( holiday_id )  as total from Holidays where day = "+day+" and  month="+month+" and year = "+year+"";

        Cursor c = ourDatabase.rawQuery(sql, null);
        boolean flag = false;
        int total=0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total=c.getInt(iCId);

                    GlobalData.printMessage("Total  Holidays:" + total);
                } catch (Exception e) {

                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");

        }

        if(total>0){
            flag= true;
        }else{
            flag= false;
        }
        return flag;
    }

    public LinkedList<Holidays> getHolidays() {
        String sql = "select *  from Holidays "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Holidays> list = new LinkedList<Holidays>();
        int i0 = c.getColumnIndex(Holidays_KEY_holiday_id);
        int i1 = c.getColumnIndex(Holidays_KEY_year);
        int i2 = c.getColumnIndex(Holidays_KEY_month);
        int i3 = c.getColumnIndex(Holidays_KEY_day);
        int i4 = c.getColumnIndex(Holidays_KEY_status);
        int i5 = c.getColumnIndex(Holidays_KEY_soft_del);
        int i6 = c.getColumnIndex(Holidays_KEY_created_date);
        int i7 = c.getColumnIndex(Holidays_KEY_modified_date);
        int i8 = c.getColumnIndex(Holidays_KEY_holiday_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Holidays bean = new Holidays();
                bean.setHoliday_id(c.getInt(i0));
                bean.setYear((c.getInt(i1)));
                bean.setMonth((c.getInt(i2)));
                bean.setDay((c.getInt(i3)));
                bean.setStatus(getClearData(c.getString(i4)));
                bean.setSoft_del(getClearData(c.getString(i5)));
                bean.setCreated_date(getClearData(c.getString(i6)));
                bean.setModified_date(getClearData(c.getString(i7)));
                bean.setHoliday_date(GlobalData.getDateFromDMY(bean.getDay(), bean.getMonth(), bean.getYear()));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Holidays Read:", e);
            }
        }
        c.close();
        return list;
    }




    public LinkedList<Holidays> getHolidaysList(int holiday_id) {
        String sql = "select *  from Holidays where holiday_id=" + holiday_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Holidays> list = new LinkedList<Holidays>();
        int i0 = c.getColumnIndex(Holidays_KEY_holiday_id);
        int i1 = c.getColumnIndex(Holidays_KEY_year);
        int i2 = c.getColumnIndex(Holidays_KEY_month);
        int i3 = c.getColumnIndex(Holidays_KEY_day);
        int i4 = c.getColumnIndex(Holidays_KEY_status);
        int i5 = c.getColumnIndex(Holidays_KEY_soft_del);
        int i6 = c.getColumnIndex(Holidays_KEY_created_date);
        int i7 = c.getColumnIndex(Holidays_KEY_modified_date);
        int i8 = c.getColumnIndex(Holidays_KEY_holiday_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Holidays bean = new Holidays();
                bean.setHoliday_id(c.getInt(i0));
                bean.setYear((c.getInt(i1)));
                bean.setMonth((c.getInt(i2)));
                bean.setDay((c.getInt(i3)));
                bean.setStatus(getClearData(c.getString(i4)));
                bean.setSoft_del(getClearData(c.getString(i5)));
                bean.setCreated_date(getClearData(c.getString(i6)));
                bean.setModified_date(getClearData(c.getString(i7)));
                bean.setHoliday_date(GlobalData.getDateFromDMY(bean.getDay(), bean.getMonth(), bean.getYear()));


                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Holidays Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addHolidays(Holidays bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert or replace into Holidays  ( holiday_id,year, month, day, status, soft_del, created_date, modified_date  ) values ( " + bean.getHoliday_id() + ", '" + (bean.getYear()) + "',  '" + (bean.getMonth()) + "',  '" + (bean.getDay()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "' )";
                GlobalData.printMessage("Holidays SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Holidays ", e);
        }
    }

    public void addHolidaysList(LinkedList<Holidays> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Holidays bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into Holidays  ( holiday_id,year, month, day, status, soft_del, created_date, modified_date  ) values ( " + bean.getHoliday_id() + ", '" + (bean.getYear()) + "',  '" + (bean.getMonth()) + "',  '" + (bean.getDay()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "' )";
                        GlobalData.printMessage("Holidays SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Holidays ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Holidays", e);
            }
        }
    }

    public void deleteHolidays() {
        String sql = "";
        try {


            sql = "delete from Holidays  ";
            GlobalData.printMessage("Holidays SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Holidays ", e);
        }
    }

    public void deleteHolidays(int rowId) {
        String sql = "";
        try {


            sql = "delete from Holidays where holiday_id=" + rowId;
            GlobalData.printMessage("Holidays SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Holidays ", e);
        }
    }


//  Methods Of Invoice_type

    public int getInvoice_typeCount() {
        String sql = "select count( Invoice_typeId ) as total from Invoice_type 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Invoice_type:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Invoice_type> getInvoice_type() {
        String sql = "select *  from Invoice_type "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Invoice_type> list = new LinkedList<Invoice_type>();
        int i0 = c.getColumnIndex(Invoice_type_KEY_invoice_type_id);
        int i1 = c.getColumnIndex(Invoice_type_KEY_invoice_txt);
        int i2 = c.getColumnIndex(Invoice_type_KEY_created_date);
        int i3 = c.getColumnIndex(Invoice_type_KEY_modified_date);
        int i4 = c.getColumnIndex(Invoice_type_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Invoice_type bean = new Invoice_type();
                bean.setInvoice_type_id(getClearData(c.getString(i0)));
                bean.setInvoice_txt(getClearData(c.getString(i1)));
                bean.setCreated_date(getClearData(c.getString(i2)));
                bean.setModified_date(getClearData(c.getString(i3)));
                bean.setSoft_del(getClearData(c.getString(i4)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Invoice_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Invoice_type> getInvoice_typeList(int invoice_type_id) {
        String sql = "select *  from Invoice_type where invoice_type_id=" + invoice_type_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Invoice_type> list = new LinkedList<Invoice_type>();
        int i0 = c.getColumnIndex(Invoice_type_KEY_invoice_type_id);
        int i1 = c.getColumnIndex(Invoice_type_KEY_invoice_txt);
        int i2 = c.getColumnIndex(Invoice_type_KEY_created_date);
        int i3 = c.getColumnIndex(Invoice_type_KEY_modified_date);
        int i4 = c.getColumnIndex(Invoice_type_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Invoice_type bean = new Invoice_type();
                bean.setInvoice_type_id(getClearData(c.getString(i0)));
                bean.setInvoice_txt(getClearData(c.getString(i1)));
                bean.setCreated_date(getClearData(c.getString(i2)));
                bean.setModified_date(getClearData(c.getString(i3)));
                bean.setSoft_del(getClearData(c.getString(i4)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Invoice_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addInvoice_type(Invoice_type bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Invoice_type  ( invoice_txt, created_date, modified_date, soft_del  ) values (  '" + setClearData(bean.getInvoice_txt()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                GlobalData.printMessage("Invoice_type SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Invoice_type ", e);
        }
    }

    public void addInvoice_typeList(LinkedList<Invoice_type> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Invoice_type bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Invoice_type  ( invoice_txt, created_date, modified_date, soft_del  ) values (  '" + setClearData(bean.getInvoice_txt()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                        GlobalData.printMessage("Invoice_type SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Invoice_type ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Invoice_type", e);
            }
        }
    }

    public void deleteInvoice_type() {
        String sql = "";
        try {


            sql = "delete from Invoice_type  ";
            GlobalData.printMessage("Invoice_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Invoice_type ", e);
        }
    }

    public void deleteInvoice_type(int rowId) {
        String sql = "";
        try {


            sql = "delete from Invoice_type where invoice_type_id=" + rowId;
            GlobalData.printMessage("Invoice_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Invoice_type ", e);
        }
    }


//  Methods Of Local_resources

    public int getLocal_resourcesCount() {
        String sql = "select count( Local_resourcesId ) as total from Local_resources 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Local_resources:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Local_resources> getLocal_resources() {
        String sql = "select *  from Local_resources ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Local_resources> list = new LinkedList<Local_resources>();
        int i0 = c.getColumnIndex(Local_resources_KEY_resource_id);
        int i1 = c.getColumnIndex(Local_resources_KEY_lng_id);
        int i2 = c.getColumnIndex(Local_resources_KEY_resource_name);
        int i3 = c.getColumnIndex(Local_resources_KEY_resource_value);
        int i4 = c.getColumnIndex(Local_resources_KEY_soft_del);
        int i5 = c.getColumnIndex(Local_resources_KEY_created_date);
        int i6 = c.getColumnIndex(Local_resources_KEY_modified_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Local_resources bean = new Local_resources();
                bean.setResource_id(c.getInt(i0));
                bean.setLng_id(getClearData(c.getString(i1)));
                bean.setResource_name(getClearData(c.getString(i2)));
                bean.setResource_value(getClearData(c.getString(i3)));
                bean.setSoft_del(getClearData(c.getString(i4)));
                bean.setCreated_date(getClearData(c.getString(i5)));
                bean.setModified_date(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Local_resources Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Local_resources> getLocal_resourcesList(int resource_id) {
        String sql = "select *  from Local_resources where resource_id=" + resource_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Local_resources> list = new LinkedList<Local_resources>();
        int i0 = c.getColumnIndex(Local_resources_KEY_resource_id);
        int i1 = c.getColumnIndex(Local_resources_KEY_lng_id);
        int i2 = c.getColumnIndex(Local_resources_KEY_resource_name);
        int i3 = c.getColumnIndex(Local_resources_KEY_resource_value);
        int i4 = c.getColumnIndex(Local_resources_KEY_soft_del);
        int i5 = c.getColumnIndex(Local_resources_KEY_created_date);
        int i6 = c.getColumnIndex(Local_resources_KEY_modified_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Local_resources bean = new Local_resources();
                bean.setResource_id(c.getInt(i0));
                bean.setLng_id(getClearData(c.getString(i1)));
                bean.setResource_name(getClearData(c.getString(i2)));
                bean.setResource_value(getClearData(c.getString(i3)));
                bean.setSoft_del(getClearData(c.getString(i4)));
                bean.setCreated_date(getClearData(c.getString(i5)));
                bean.setModified_date(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Local_resources Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addLocal_resources(Local_resources bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Local_resources  ( lng_id, resource_name, resource_value, soft_del, created_date, modified_date  ) values (  '" + setClearData(bean.getLng_id()) + "',  '" + setClearData(bean.getResource_name()) + "',  '" + setClearData(bean.getResource_value()) + "',  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "' )";
                GlobalData.printMessage("Local_resources SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Local_resources ", e);
        }
    }

    public void addLocal_resourcesList(LinkedList<Local_resources> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Local_resources bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Local_resources  ( lng_id, resource_name, resource_value, soft_del, created_date, modified_date  ) values (  '" + setClearData(bean.getLng_id()) + "',  '" + setClearData(bean.getResource_name()) + "',  '" + setClearData(bean.getResource_value()) + "',  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "' )";
                        GlobalData.printMessage("Local_resources SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Local_resources ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Local_resources", e);
            }
        }
    }

    public void deleteLocal_resources() {
        String sql = "";
        try {


            sql = "delete from Local_resources  ";
            GlobalData.printMessage("Local_resources SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Local_resources ", e);
        }
    }

    public void deleteLocal_resources(int rowId) {
        String sql = "";
        try {


            sql = "delete from Local_resources where resource_id=" + rowId;
            GlobalData.printMessage("Local_resources SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Local_resources ", e);
        }
    }


//  Methods Of Mail_config

    public int getMail_configCount() {
        String sql = "select count( Mail_configId ) as total from Mail_config 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Mail_config:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Mail_config> getMail_config() {
        String sql = "select *  from Mail_config ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Mail_config> list = new LinkedList<Mail_config>();
        int i0 = c.getColumnIndex(Mail_config_KEY_config_id);
        int i1 = c.getColumnIndex(Mail_config_KEY_user_id);
        int i2 = c.getColumnIndex(Mail_config_KEY_purpose_name);
        int i3 = c.getColumnIndex(Mail_config_KEY_mail_server);
        int i4 = c.getColumnIndex(Mail_config_KEY_port);
        int i5 = c.getColumnIndex(Mail_config_KEY_username);
        int i6 = c.getColumnIndex(Mail_config_KEY_email);
        int i7 = c.getColumnIndex(Mail_config_KEY_password);
        int i8 = c.getColumnIndex(Mail_config_KEY_alt_email);
        int i9 = c.getColumnIndex(Mail_config_KEY_mail_ssl);
        int i10 = c.getColumnIndex(Mail_config_KEY_alert);
        int i11 = c.getColumnIndex(Mail_config_KEY_subject);
        int i12 = c.getColumnIndex(Mail_config_KEY_body);
        int i13 = c.getColumnIndex(Mail_config_KEY_other_email);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Mail_config bean = new Mail_config();
                bean.setConfig_id(c.getInt(i0));
                bean.setUser_id(c.getInt(i1));
                bean.setPurpose_name(getClearData(c.getString(i2)));
                bean.setMail_server(getClearData(c.getString(i3)));
                bean.setPort(getClearData(c.getString(i4)));
                bean.setUsername(getClearData(c.getString(i5)));
                bean.setEmail(getClearData(c.getString(i6)));
                bean.setPassword(getClearData(c.getString(i7)));
                bean.setAlt_email(getClearData(c.getString(i8)));
                bean.setMail_ssl(getClearData(c.getString(i9)));
                bean.setAlert(getClearData(c.getString(i10)));
                bean.setSubject(getClearData(c.getString(i11)));
                bean.setBody(getClearData(c.getString(i12)));
                bean.setOther_email(getClearData(c.getString(i13)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Mail_config Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Mail_config> getMail_configList(int config_id) {
        String sql = "select *  from Mail_config where config_id=" + config_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Mail_config> list = new LinkedList<Mail_config>();
        int i0 = c.getColumnIndex(Mail_config_KEY_config_id);
        int i1 = c.getColumnIndex(Mail_config_KEY_user_id);
        int i2 = c.getColumnIndex(Mail_config_KEY_purpose_name);
        int i3 = c.getColumnIndex(Mail_config_KEY_mail_server);
        int i4 = c.getColumnIndex(Mail_config_KEY_port);
        int i5 = c.getColumnIndex(Mail_config_KEY_username);
        int i6 = c.getColumnIndex(Mail_config_KEY_email);
        int i7 = c.getColumnIndex(Mail_config_KEY_password);
        int i8 = c.getColumnIndex(Mail_config_KEY_alt_email);
        int i9 = c.getColumnIndex(Mail_config_KEY_mail_ssl);
        int i10 = c.getColumnIndex(Mail_config_KEY_alert);
        int i11 = c.getColumnIndex(Mail_config_KEY_subject);
        int i12 = c.getColumnIndex(Mail_config_KEY_body);
        int i13 = c.getColumnIndex(Mail_config_KEY_other_email);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Mail_config bean = new Mail_config();
                bean.setConfig_id(c.getInt(i0));
                bean.setUser_id(c.getInt(i1));
                bean.setPurpose_name(getClearData(c.getString(i2)));
                bean.setMail_server(getClearData(c.getString(i3)));
                bean.setPort(getClearData(c.getString(i4)));
                bean.setUsername(getClearData(c.getString(i5)));
                bean.setEmail(getClearData(c.getString(i6)));
                bean.setPassword(getClearData(c.getString(i7)));
                bean.setAlt_email(getClearData(c.getString(i8)));
                bean.setMail_ssl(getClearData(c.getString(i9)));
                bean.setAlert(getClearData(c.getString(i10)));
                bean.setSubject(getClearData(c.getString(i11)));
                bean.setBody(getClearData(c.getString(i12)));
                bean.setOther_email(getClearData(c.getString(i13)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Mail_config Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addMail_config(Mail_config bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Mail_config  ( user_id, purpose_name, mail_server, port, username, email, password, alt_email, mail_ssl, alert, subject, body, other_email  ) values (  " + bean.getUser_id() + ",  '" + setClearData(bean.getPurpose_name()) + "',  '" + setClearData(bean.getMail_server()) + "',  '" + setClearData(bean.getPort()) + "',  '" + setClearData(bean.getUsername()) + "',  '" + setClearData(bean.getEmail()) + "',  '" + setClearData(bean.getPassword()) + "',  '" + setClearData(bean.getAlt_email()) + "',  '" + setClearData(bean.getMail_ssl()) + "',  '" + setClearData(bean.getAlert()) + "',  '" + setClearData(bean.getSubject()) + "',  '" + setClearData(bean.getBody()) + "',  '" + setClearData(bean.getOther_email()) + "' )";
                GlobalData.printMessage("Mail_config SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Mail_config ", e);
        }
    }

    public void addMail_configList(LinkedList<Mail_config> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Mail_config bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Mail_config  ( user_id, purpose_name, mail_server, port, username, email, password, alt_email, mail_ssl, alert, subject, body, other_email  ) values (  " + bean.getUser_id() + ",  '" + setClearData(bean.getPurpose_name()) + "',  '" + setClearData(bean.getMail_server()) + "',  '" + setClearData(bean.getPort()) + "',  '" + setClearData(bean.getUsername()) + "',  '" + setClearData(bean.getEmail()) + "',  '" + setClearData(bean.getPassword()) + "',  '" + setClearData(bean.getAlt_email()) + "',  '" + setClearData(bean.getMail_ssl()) + "',  '" + setClearData(bean.getAlert()) + "',  '" + setClearData(bean.getSubject()) + "',  '" + setClearData(bean.getBody()) + "',  '" + setClearData(bean.getOther_email()) + "' )";
                        GlobalData.printMessage("Mail_config SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Mail_config ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Mail_config", e);
            }
        }
    }

    public void deleteMail_config() {
        String sql = "";
        try {


            sql = "delete from Mail_config  ";
            GlobalData.printMessage("Mail_config SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Mail_config ", e);
        }
    }

    public void deleteMail_config(int rowId) {
        String sql = "";
        try {


            sql = "delete from Mail_config where config_id=" + rowId;
            GlobalData.printMessage("Mail_config SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Mail_config ", e);
        }
    }


//  Methods Of Msg_logs

    public int getMsg_logsCount() {
        String sql = "select count( Msg_logsId ) as total from Msg_logs 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Msg_logs:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Msg_logs> getMsg_logs() {
        String sql = "select *  from Msg_logs ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Msg_logs> list = new LinkedList<Msg_logs>();
        int i0 = c.getColumnIndex(Msg_logs_KEY_log_id);
        int i1 = c.getColumnIndex(Msg_logs_KEY_user_id);
        int i2 = c.getColumnIndex(Msg_logs_KEY_client_id);
        int i3 = c.getColumnIndex(Msg_logs_KEY_app_id);
        int i4 = c.getColumnIndex(Msg_logs_KEY_msg);
        int i5 = c.getColumnIndex(Msg_logs_KEY_msg_date);
        int i6 = c.getColumnIndex(Msg_logs_KEY_title);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Msg_logs bean = new Msg_logs();
                bean.setLog_id(c.getInt(i0));
                bean.setUser_id(c.getInt(i1));
                bean.setClient_id(c.getInt(i2));
                bean.setApp_id(c.getInt(i3));
                bean.setMsg(getClearData(c.getString(i4)));
                bean.setMsg_date(getClearData(c.getString(i5)));
                bean.setTitle(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Msg_logs Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Msg_logs> getMsg_logsList(int log_id) {
        String sql = "select *  from Msg_logs where log_id=" + log_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Msg_logs> list = new LinkedList<Msg_logs>();
        int i0 = c.getColumnIndex(Msg_logs_KEY_log_id);
        int i1 = c.getColumnIndex(Msg_logs_KEY_user_id);
        int i2 = c.getColumnIndex(Msg_logs_KEY_client_id);
        int i3 = c.getColumnIndex(Msg_logs_KEY_app_id);
        int i4 = c.getColumnIndex(Msg_logs_KEY_msg);
        int i5 = c.getColumnIndex(Msg_logs_KEY_msg_date);
        int i6 = c.getColumnIndex(Msg_logs_KEY_title);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Msg_logs bean = new Msg_logs();
                bean.setLog_id(c.getInt(i0));
                bean.setUser_id(c.getInt(i1));
                bean.setClient_id(c.getInt(i2));
                bean.setApp_id(c.getInt(i3));
                bean.setMsg(getClearData(c.getString(i4)));
                bean.setMsg_date(getClearData(c.getString(i5)));
                bean.setTitle(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Msg_logs Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addMsg_logs(Msg_logs bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Msg_logs  ( user_id, client_id, app_id, msg, msg_date, title  ) values (  " + bean.getUser_id() + ",  " + bean.getClient_id() + ",  " + bean.getApp_id() + ",  '" + setClearData(bean.getMsg()) + "',  '" + setClearData(bean.getMsg_date()) + "',  '" + setClearData(bean.getTitle()) + "' )";
                GlobalData.printMessage("Msg_logs SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Msg_logs ", e);
        }
    }

    public void addMsg_logsList(LinkedList<Msg_logs> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Msg_logs bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Msg_logs  ( user_id, client_id, app_id, msg, msg_date, title  ) values (  " + bean.getUser_id() + ",  " + bean.getClient_id() + ",  " + bean.getApp_id() + ",  '" + setClearData(bean.getMsg()) + "',  '" + setClearData(bean.getMsg_date()) + "',  '" + setClearData(bean.getTitle()) + "' )";
                        GlobalData.printMessage("Msg_logs SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Msg_logs ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Msg_logs", e);
            }
        }
    }

    public void deleteMsg_logs() {
        String sql = "";
        try {


            sql = "delete from Msg_logs  ";
            GlobalData.printMessage("Msg_logs SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Msg_logs ", e);
        }
    }

    public void deleteMsg_logs(int rowId) {
        String sql = "";
        try {


            sql = "delete from Msg_logs where log_id=" + rowId;
            GlobalData.printMessage("Msg_logs SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Msg_logs ", e);
        }
    }


//  Methods Of Msg_type_logs

    public int getMsg_type_logsCount() {
        String sql = "select count( Msg_type_logsId ) as total from Msg_type_logs 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Msg_type_logs:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Msg_type_logs> getMsg_type_logs() {
        String sql = "select *  from Msg_type_logs ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Msg_type_logs> list = new LinkedList<Msg_type_logs>();
        int i0 = c.getColumnIndex(Msg_type_logs_KEY_ref_id);
        int i1 = c.getColumnIndex(Msg_type_logs_KEY_log_id);
        int i2 = c.getColumnIndex(Msg_type_logs_KEY_type_id);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Msg_type_logs bean = new Msg_type_logs();
                bean.setRef_id(c.getInt(i0));
                bean.setLog_id(c.getInt(i1));
                bean.setType_id(c.getInt(i2));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Msg_type_logs Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Msg_type_logs> getMsg_type_logsList(int ref_id) {
        String sql = "select *  from Msg_type_logs where ref_id=" + ref_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Msg_type_logs> list = new LinkedList<Msg_type_logs>();
        int i0 = c.getColumnIndex(Msg_type_logs_KEY_ref_id);
        int i1 = c.getColumnIndex(Msg_type_logs_KEY_log_id);
        int i2 = c.getColumnIndex(Msg_type_logs_KEY_type_id);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Msg_type_logs bean = new Msg_type_logs();
                bean.setRef_id(c.getInt(i0));
                bean.setLog_id(c.getInt(i1));
                bean.setType_id(c.getInt(i2));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Msg_type_logs Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addMsg_type_logs(Msg_type_logs bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Msg_type_logs  ( log_id, type_id  ) values (  " + bean.getLog_id() + ",  " + bean.getType_id() + " )";
                GlobalData.printMessage("Msg_type_logs SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Msg_type_logs ", e);
        }
    }

    public void addMsg_type_logsList(LinkedList<Msg_type_logs> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Msg_type_logs bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Msg_type_logs  ( log_id, type_id  ) values (  " + bean.getLog_id() + ",  " + bean.getType_id() + " )";
                        GlobalData.printMessage("Msg_type_logs SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Msg_type_logs ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Msg_type_logs", e);
            }
        }
    }

    public void deleteMsg_type_logs() {
        String sql = "";
        try {


            sql = "delete from Msg_type_logs  ";
            GlobalData.printMessage("Msg_type_logs SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Msg_type_logs ", e);
        }
    }

    public void deleteMsg_type_logs(int rowId) {
        String sql = "";
        try {


            sql = "delete from Msg_type_logs where ref_id=" + rowId;
            GlobalData.printMessage("Msg_type_logs SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Msg_type_logs ", e);
        }
    }


//  Methods Of Notification

    public int getNotificationCount() {
        String sql = "select count( noti_id ) as total from Notification where soft_del='0'	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Notification:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Notification> getNotification(int limit) {
        String sql="select *  from Notification order by  noti_id desc";

        if(limit>0) {
             sql = "select *  from Notification order by  noti_id desc limit " + limit;
        }

        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Notification> list = new LinkedList<Notification>();
        int i0 = c.getColumnIndex(Notification_KEY_noti_id);
        int i1 = c.getColumnIndex(Notification_KEY_noti_type);
        int i2 = c.getColumnIndex(Notification_KEY_notifi_txt);
        int i3 = c.getColumnIndex(Notification_KEY_order_id);
        int i4 = c.getColumnIndex(Notification_KEY_status);
        int i5 = c.getColumnIndex(Notification_KEY_created_date);
        int i6 = c.getColumnIndex(Notification_KEY_assignment_no);
        int i7 = c.getColumnIndex(Notification_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Notification bean = new Notification();
                bean.setNoti_id(c.getInt(i0));
                bean.setNoti_type(getClearData(c.getString(i1)));
                bean.setNotifi_txt(getClearData(c.getString(i2)));
                bean.setOrder_id(c.getInt(i3));
                bean.setStatus(getClearData(c.getString(i4)));
                bean.setCreated_date(getClearData(c.getString(i5)));
                bean.setAssignment_no(getClearData(c.getString(i6)));
                bean.setSoft_del(getClearData(c.getString(i7)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Notification Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Notification> getNotificationList(int noti_id) {
        String sql = "select *  from Notification where noti_id=" + noti_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Notification> list = new LinkedList<Notification>();
        int i0 = c.getColumnIndex(Notification_KEY_noti_id);
        int i1 = c.getColumnIndex(Notification_KEY_noti_type);
        int i2 = c.getColumnIndex(Notification_KEY_notifi_txt);
        int i3 = c.getColumnIndex(Notification_KEY_order_id);
        int i4 = c.getColumnIndex(Notification_KEY_status);
        int i5 = c.getColumnIndex(Notification_KEY_created_date);
        int i6 = c.getColumnIndex(Notification_KEY_assignment_no);
        int i7 = c.getColumnIndex(Notification_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Notification bean = new Notification();
                bean.setNoti_id(c.getInt(i0));
                bean.setNoti_type(getClearData(c.getString(i1)));
                bean.setNotifi_txt(getClearData(c.getString(i2)));
                bean.setOrder_id(c.getInt(i3));
                bean.setStatus(getClearData(c.getString(i4)));
                bean.setCreated_date(getClearData(c.getString(i5)));
                bean.setAssignment_no(getClearData(c.getString(i6)));
                bean.setSoft_del(getClearData(c.getString(i7)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Notification Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addNotification(Notification bean) {
        String sql = "";
        try {
            if (bean != null) {
                sql = "insert into Notification  ( noti_type, notifi_txt, order_id, status, created_date, assignment_no, soft_del,noti_title  ) values (  '" + setClearData(bean.getNoti_type()) + "',  '" + setClearData(bean.getNotifi_txt()) + "',  " + bean.getOrder_id() + ",  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getAssignment_no()) + "',  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getNoti_title()) + "' )";
                GlobalData.printMessage("Notification SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Notification ", e);
        }
    }

    public void addNotificationList(LinkedList<Notification> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Notification bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Notification  ( noti_type, notifi_txt, order_id, status, created_date, assignment_no, soft_del,noti_title  ) values (  '" + setClearData(bean.getNoti_type()) + "',  '" + setClearData(bean.getNotifi_txt()) + "',  " + bean.getOrder_id() + ",  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getAssignment_no()) + "',  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getNoti_title()) + "' )";
                        GlobalData.printMessage("Notification SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Notification ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Notification", e);
            }
        }
    }


    public void updateReadNotifications(LinkedList<Notification> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Notification bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "update Notification  set soft_del ='1' where noti_type_id ="+bean.getNoti_id();
                        GlobalData.printMessage("Notification SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Notification ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Notification", e);
            }
        }
    }

    public void deleteNotification() {
        String sql = "";
        try {


            sql = "delete from Notification  ";
            GlobalData.printMessage("Notification SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Notification ", e);
        }
    }

    public void deleteNotification(int rowId) {
        String sql = "";
        try {


            sql = "delete from Notification where noti_id=" + rowId;
            GlobalData.printMessage("Notification SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Notification ", e);
        }
    }


//  Methods Of Notification_type

    public int getNotification_typeCount() {
        String sql = "select count( Notification_typeId ) as total from Notification_type 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Notification_type:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Notification_type> getNotification_type() {
        String sql = "select *  from Notification_type ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Notification_type> list = new LinkedList<Notification_type>();
        int i0 = c.getColumnIndex(Notification_type_KEY_noti_type_id);
        int i1 = c.getColumnIndex(Notification_type_KEY_notification_name);
        int i2 = c.getColumnIndex(Notification_type_KEY_template_desc);
        int i3 = c.getColumnIndex(Notification_type_KEY_created_on);
        int i4 = c.getColumnIndex(Notification_type_KEY_modified_date);
        int i5 = c.getColumnIndex(Notification_type_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Notification_type bean = new Notification_type();
                bean.setNoti_type_id(getClearData(c.getString(i0)));
                bean.setNotification_name(getClearData(c.getString(i1)));
                bean.setTemplate_desc(getClearData(c.getString(i2)));
                bean.setCreated_on(getClearData(c.getString(i3)));
                bean.setModified_date(getClearData(c.getString(i4)));
                bean.setSoft_del(getClearData(c.getString(i5)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Notification_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Notification_type> getNotification_typeList(int noti_type_id) {
        String sql = "select *  from Notification_type where noti_type_id=" + noti_type_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Notification_type> list = new LinkedList<Notification_type>();
        int i0 = c.getColumnIndex(Notification_type_KEY_noti_type_id);
        int i1 = c.getColumnIndex(Notification_type_KEY_notification_name);
        int i2 = c.getColumnIndex(Notification_type_KEY_template_desc);
        int i3 = c.getColumnIndex(Notification_type_KEY_created_on);
        int i4 = c.getColumnIndex(Notification_type_KEY_modified_date);
        int i5 = c.getColumnIndex(Notification_type_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Notification_type bean = new Notification_type();
                bean.setNoti_type_id(getClearData(c.getString(i0)));
                bean.setNotification_name(getClearData(c.getString(i1)));
                bean.setTemplate_desc(getClearData(c.getString(i2)));
                bean.setCreated_on(getClearData(c.getString(i3)));
                bean.setModified_date(getClearData(c.getString(i4)));
                bean.setSoft_del(getClearData(c.getString(i5)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Notification_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addNotification_type(Notification_type bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Notification_type  ( notification_name, template_desc, created_on, modified_date, soft_del  ) values (  '" + setClearData(bean.getNotification_name()) + "',  '" + setClearData(bean.getTemplate_desc()) + "',  '" + setClearData(bean.getCreated_on()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                GlobalData.printMessage("Notification_type SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Notification_type ", e);
        }
    }

    public void addNotification_typeList(LinkedList<Notification_type> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Notification_type bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Notification_type  ( notification_name, template_desc, created_on, modified_date, soft_del  ) values (  '" + setClearData(bean.getNotification_name()) + "',  '" + setClearData(bean.getTemplate_desc()) + "',  '" + setClearData(bean.getCreated_on()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                        GlobalData.printMessage("Notification_type SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Notification_type ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Notification_type", e);
            }
        }
    }

    public void deleteNotification_type() {
        String sql = "";
        try {


            sql = "delete from Notification_type  ";
            GlobalData.printMessage("Notification_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Notification_type ", e);
        }
    }

    public void deleteNotification_type(int rowId) {
        String sql = "";
        try {


            sql = "delete from Notification_type where noti_type_id=" + rowId;
            GlobalData.printMessage("Notification_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Notification_type ", e);
        }
    }


//  Methods Of OrderDetails
        public int getMaxOrderId() {
    String sql = "select max( order_id ) as total from orderDetails 	";
    Cursor c = ourDatabase.rawQuery(sql, null);
    int total = 0;
    try {
        int iCId = c.getColumnIndex("total");
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            try {
                total = c.getInt(iCId);
                GlobalData.printMessage("Total  Notification_type:" + total);
            } catch (Exception e) {
                total = 0;
            }
        }
        c.close();
    } catch (Exception e) {
        GlobalData.printMessage("Error:" + e);
        GlobalData.printError(e, "");
        total = 0;
    }
    return total;
}

    public OrderDetails getOrderDetailsFromAssignId (String assignment_no) {
        String sql = "select *  from orderDetails where assignment_no='"+assignment_no+"'" ;
        Cursor c = ourDatabase.rawQuery(sql, null);
        OrderDetails bean = new OrderDetails();
        int i0 = c.getColumnIndex(OrderDetails_KEY_order_id);
        int i1 = c.getColumnIndex(OrderDetails_KEY_assignment_no);
        int i2 = c.getColumnIndex(OrderDetails_KEY_client_instruction);
        int i3 = c.getColumnIndex(OrderDetails_KEY_total_files);
        int i4 = c.getColumnIndex(OrderDetails_KEY_service_type_id);
        int i5 = c.getColumnIndex(OrderDetails_KEY_trans_type_id);
        int i6 = c.getColumnIndex(OrderDetails_KEY_delivery_opt_id);
        int i7 = c.getColumnIndex(OrderDetails_KEY_order_status_id);
        int i8 = c.getColumnIndex(OrderDetails_KEY_vas_id);
        int i9 = c.getColumnIndex(OrderDetails_KEY_time_slab_id);
        int i10 = c.getColumnIndex(OrderDetails_KEY_invoice_type_id);
        int i11 = c.getColumnIndex(OrderDetails_KEY_subject_of_file);
        int i12 = c.getColumnIndex(OrderDetails_KEY_connection_type);
        int i13 = c.getColumnIndex(OrderDetails_KEY_total_duration);
        int i14 = c.getColumnIndex(OrderDetails_KEY_delivery_date);
        int i15 = c.getColumnIndex(OrderDetails_KEY_transcription_link);
        int i16 = c.getColumnIndex(OrderDetails_KEY_order_date);
        int i17 = c.getColumnIndex(OrderDetails_KEY_modified_date);
        int i18 = c.getColumnIndex(OrderDetails_KEY_total_fees);
        int i19 = c.getColumnIndex(OrderDetails_KEY_order_placed_details);
        int i20 = c.getColumnIndex(OrderDetails_KEY_order_complete_details);
        int i21 = c.getColumnIndex(OrderDetails_KEY_days_excluded_tat);
        int i22 = c.getColumnIndex(OrderDetails_KEY_output_format_id);
        int i23 = c.getColumnIndex(OrderDetails_KEY_user_id);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setOrder_id(c.getInt(i0));
                bean.setAssignment_no(getClearData(c.getString(i1)));
                bean.setClient_instruction(getClearData(c.getString(i2)));
                bean.setTotal_files(getClearData(c.getString(i3)));
                bean.setService_type_id(getClearData(c.getString(i4)));
                bean.setTrans_type_id(getClearData(c.getString(i5)));
                bean.setDelivery_opt_id(getClearData(c.getString(i6)));
                bean.setOrder_status_id(getClearData(c.getString(i7)));
                bean.setVas_id(getClearData(c.getString(i8)));
                bean.setTime_slab_id(getClearData(c.getString(i9)));
                bean.setInvoice_type_id(getClearData(c.getString(i10)));
                bean.setSubject_of_file(getClearData(c.getString(i11)));
                bean.setConnection_type(getClearData(c.getString(i12)));
                bean.setTotal_duration(getClearData(c.getString(i13)));
                bean.setDelivery_date(getClearData(c.getString(i14)));
                bean.setTranscription_link(getClearData(c.getString(i15)));
                bean.setOrder_date(getClearData(c.getString(i16)));
                bean.setModified_date(getClearData(c.getString(i17)));
                bean.setTotal_fees(getClearData(c.getString(i18)));
                bean.setOrder_placed_details(getClearData(c.getString(i19)));
                bean.setOrder_complete_details(getClearData(c.getString(i20)));
                bean.setDays_excluded_tat(getClearData(c.getString(i21)));
                bean.setOutput_format_id(getClearData(c.getString(i22)));
                bean.setUser_id(c.getInt(i23));


            } catch (Exception e) {
                GlobalData.printError("OrderDetails Read:", e);
            }
        }
        c.close();
        return bean;
    }
    public OrderDetails getOrderDetails(int orderId) {
        String sql = "select *  from orderDetails where order_id="+orderId ;
        Cursor c = ourDatabase.rawQuery(sql, null);
        OrderDetails bean = new OrderDetails();
        int i0 = c.getColumnIndex(OrderDetails_KEY_order_id);
        int i1 = c.getColumnIndex(OrderDetails_KEY_assignment_no);
        int i2 = c.getColumnIndex(OrderDetails_KEY_client_instruction);
        int i3 = c.getColumnIndex(OrderDetails_KEY_total_files);
        int i4 = c.getColumnIndex(OrderDetails_KEY_service_type_id);
        int i5 = c.getColumnIndex(OrderDetails_KEY_trans_type_id);
        int i6 = c.getColumnIndex(OrderDetails_KEY_delivery_opt_id);
        int i7 = c.getColumnIndex(OrderDetails_KEY_order_status_id);
        int i8 = c.getColumnIndex(OrderDetails_KEY_vas_id);
        int i9 = c.getColumnIndex(OrderDetails_KEY_time_slab_id);
        int i10 = c.getColumnIndex(OrderDetails_KEY_invoice_type_id);
        int i11 = c.getColumnIndex(OrderDetails_KEY_subject_of_file);
        int i12 = c.getColumnIndex(OrderDetails_KEY_connection_type);
        int i13 = c.getColumnIndex(OrderDetails_KEY_total_duration);
        int i14 = c.getColumnIndex(OrderDetails_KEY_delivery_date);
        int i15 = c.getColumnIndex(OrderDetails_KEY_transcription_link);
        int i16 = c.getColumnIndex(OrderDetails_KEY_order_date);
        int i17 = c.getColumnIndex(OrderDetails_KEY_modified_date);
        int i18 = c.getColumnIndex(OrderDetails_KEY_total_fees);
        int i19 = c.getColumnIndex(OrderDetails_KEY_order_placed_details);
        int i20 = c.getColumnIndex(OrderDetails_KEY_order_complete_details);
        int i21 = c.getColumnIndex(OrderDetails_KEY_days_excluded_tat);
        int i22 = c.getColumnIndex(OrderDetails_KEY_output_format_id);
        int i23 = c.getColumnIndex(OrderDetails_KEY_user_id);
        int i24 = c.getColumnIndex("upTime");

        int i25 = c.getColumnIndex("premium_type");
        int i26 = c.getColumnIndex("discount_type");
        int i27 = c.getColumnIndex("gross_fees");
        int i28 = c.getColumnIndex("premium_per_hour");
        int i29 = c.getColumnIndex("total_premium");
        int i30 = c.getColumnIndex("discount");
        int i31 = c.getColumnIndex("total_discount");





        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setOrder_id(c.getInt(i0));
                bean.setAssignment_no(getClearData(c.getString(i1)));
                bean.setClient_instruction(getClearData(c.getString(i2)));
                bean.setTotal_files(getClearData(c.getString(i3)));
                bean.setService_type_id(getClearData(c.getString(i4)));
                bean.setTrans_type_id(getClearData(c.getString(i5)));
                bean.setDelivery_opt_id(getClearData(c.getString(i6)));
                bean.setOrder_status_id(getClearData(c.getString(i7)));
                bean.setVas_id(getClearData(c.getString(i8)));
                bean.setTime_slab_id(getClearData(c.getString(i9)));
                bean.setInvoice_type_id(getClearData(c.getString(i10)));
                bean.setSubject_of_file(getClearData(c.getString(i11)));
                bean.setConnection_type(getClearData(c.getString(i12)));
                bean.setTotal_duration(getClearData(c.getString(i13)));
                bean.setDelivery_date(getClearData(c.getString(i14)));
                bean.setTranscription_link(getClearData(c.getString(i15)));
                bean.setOrder_date(getClearData(c.getString(i16)));
                bean.setModified_date(getClearData(c.getString(i17)));
                bean.setTotal_fees(getClearData(c.getString(i18)));
                bean.setOrder_placed_details(getClearData(c.getString(i19)));
                bean.setOrder_complete_details(getClearData(c.getString(i20)));
                bean.setDays_excluded_tat(getClearData(c.getString(i21)));
                bean.setOutput_format_id(getClearData(c.getString(i22)));
                bean.setUser_id(c.getInt(i23));
                bean.setTo_date(getClearData(c.getString(i24)));

                bean.setPremium_type(getClearData(c.getString(i25)));;
                bean.setDiscount_type(getClearData(c.getString(i26)));;
                bean.setGross_fees(getClearData(c.getString(i27)));;
                bean.setPremium_per_hour(getClearData(c.getString(i28)));;
                bean.setTotal_premium(getClearData(c.getString(i29)));;
                bean.setDiscount(getClearData(c.getString(i30)));;
                bean.setTotal_discount(getClearData(c.getString(i31)));;


                LinkedList<MyRecording> recList=new LinkedList<>();
                try {


                     recList=getRecordingList(bean.getAssignment_no());
                }catch (Exception e){
                    GlobalData.printError(e);
                }

                bean.setRecList(recList);


            } catch (Exception e) {
                GlobalData.printError("OrderDetails Read:", e);
            }
        }
        c.close();
        return bean;
    }


    public OrderDetails getOrderDetails(String assignment_no) {
        String sql = "select *  from orderDetails where assignment_no = '"+assignment_no+"'" ;
        Cursor c = ourDatabase.rawQuery(sql, null);
        OrderDetails bean = new OrderDetails();
        int i0 = c.getColumnIndex(OrderDetails_KEY_order_id);
        int i1 = c.getColumnIndex(OrderDetails_KEY_assignment_no);
        int i2 = c.getColumnIndex(OrderDetails_KEY_client_instruction);
        int i3 = c.getColumnIndex(OrderDetails_KEY_total_files);
        int i4 = c.getColumnIndex(OrderDetails_KEY_service_type_id);
        int i5 = c.getColumnIndex(OrderDetails_KEY_trans_type_id);
        int i6 = c.getColumnIndex(OrderDetails_KEY_delivery_opt_id);
        int i7 = c.getColumnIndex(OrderDetails_KEY_order_status_id);
        int i8 = c.getColumnIndex(OrderDetails_KEY_vas_id);
        int i9 = c.getColumnIndex(OrderDetails_KEY_time_slab_id);
        int i10 = c.getColumnIndex(OrderDetails_KEY_invoice_type_id);
        int i11 = c.getColumnIndex(OrderDetails_KEY_subject_of_file);
        int i12 = c.getColumnIndex(OrderDetails_KEY_connection_type);
        int i13 = c.getColumnIndex(OrderDetails_KEY_total_duration);
        int i14 = c.getColumnIndex(OrderDetails_KEY_delivery_date);
        int i15 = c.getColumnIndex(OrderDetails_KEY_transcription_link);
        int i16 = c.getColumnIndex(OrderDetails_KEY_order_date);
        int i17 = c.getColumnIndex(OrderDetails_KEY_modified_date);
        int i18 = c.getColumnIndex(OrderDetails_KEY_total_fees);
        int i19 = c.getColumnIndex(OrderDetails_KEY_order_placed_details);
        int i20 = c.getColumnIndex(OrderDetails_KEY_order_complete_details);
        int i21 = c.getColumnIndex(OrderDetails_KEY_days_excluded_tat);
        int i22 = c.getColumnIndex(OrderDetails_KEY_output_format_id);
        int i23 = c.getColumnIndex(OrderDetails_KEY_user_id);
        int i24 = c.getColumnIndex("upTime");

        int i25 = c.getColumnIndex("premium_type");
        int i26 = c.getColumnIndex("discount_type");
        int i27 = c.getColumnIndex("gross_fees");
        int i28 = c.getColumnIndex("premium_per_hour");
        int i29 = c.getColumnIndex("total_premium");
        int i30 = c.getColumnIndex("discount");
        int i31 = c.getColumnIndex("total_discount");
        int i32 = c.getColumnIndex("totalDurationMin");



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setOrder_id(c.getInt(i0));
                bean.setAssignment_no(getClearData(c.getString(i1)));
                bean.setClient_instruction(getClearData(c.getString(i2)));
                bean.setTotal_files(getClearData(c.getString(i3)));
                bean.setService_type_id(getClearData(c.getString(i4)));
                bean.setTrans_type_id(getClearData(c.getString(i5)));
                bean.setDelivery_opt_id(getClearData(c.getString(i6)));
                bean.setOrder_status_id(getClearData(c.getString(i7)));
                bean.setVas_id(getClearData(c.getString(i8)));
                bean.setTime_slab_id(getClearData(c.getString(i9)));
                bean.setInvoice_type_id(getClearData(c.getString(i10)));
                bean.setSubject_of_file(getClearData(c.getString(i11)));
                bean.setConnection_type(getClearData(c.getString(i12)));
                bean.setTotal_duration(getClearData(c.getString(i13)));
                bean.setDelivery_date(getClearData(c.getString(i14)));
                bean.setTranscription_link(getClearData(c.getString(i15)));
                bean.setOrder_date(getClearData(c.getString(i16)));
                bean.setModified_date(getClearData(c.getString(i17)));
                bean.setTotal_fees(getClearData(c.getString(i18)));
                bean.setOrder_placed_details(getClearData(c.getString(i19)));
                bean.setOrder_complete_details(getClearData(c.getString(i20)));
                bean.setDays_excluded_tat(getClearData(c.getString(i21)));
                bean.setOutput_format_id(getClearData(c.getString(i22)));
                bean.setUser_id(c.getInt(i23));
                bean.setTo_date(getClearData(c.getString(i24)));

                bean.setPremium_type(getClearData(c.getString(i25)));
                ;
                bean.setDiscount_type(getClearData(c.getString(i26)));
                ;
                bean.setGross_fees(getClearData(c.getString(i27)));
                ;
                bean.setPremium_per_hour(getClearData(c.getString(i28)));
                ;
                bean.setTotal_premium(getClearData(c.getString(i29)));
                ;
                bean.setDiscount(getClearData(c.getString(i30)));
                ;
                bean.setTotal_discount(getClearData(c.getString(i31)));
                ;
                bean.setTotalDurationMin(c.getString(i32));

                LinkedList<MyRecording> recList=new LinkedList<>();
                try {


                    recList=getRecordingList(bean.getAssignment_no());
                }catch (Exception e){
                    GlobalData.printError(e);
                }

                bean.setRecList(recList);


            } catch (Exception e) {
                GlobalData.printError("OrderDetails Read:", e);
            }
        }
        c.close();
        return bean;
    }


    public void addOrderDetailsListForHistory(LinkedList<OrderDetails> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                OrderDetails bean = beanList.get(i);
                try {
                    if (bean != null) {

                        OrderDetails order=new OrderDetails();
                        try {

                            if(bean.getAssignment_no().length()>0)
                                order= getOrderDetailsFromAssignId(bean.getAssignment_no());

                        }catch (Exception e){
                            GlobalData.printError(e);
                        }


                        if(order.getOrder_id() > 0){
                            sql = "insert or replace into orderDetails  ( order_id, assignment_no, client_instruction, total_files, service_type_id, trans_type_id, delivery_opt_id, order_status_id, vas_id, time_slab_id, invoice_type_id, subject_of_file, connection_type, total_duration, delivery_date, transcription_link, order_date, modified_date, total_fees, order_placed_details, order_complete_details, days_excluded_tat, output_format_id, user_id  ) values ( "+bean.getOrder_id()+", '"+ setClearData(bean.getAssignment_no()) +"',  '"+ setClearData(bean.getClient_instruction()) +"',  '"+ setClearData(bean.getTotal_files()) +"',  '"+ setClearData(bean.getService_type_id()) +"',  '"+ setClearData(bean.getTrans_type_id()) +"',  '"+ setClearData(bean.getDelivery_opt_id()) +"',  '"+ setClearData(bean.getOrder_status_id()) +"',  '"+ setClearData(bean.getVas_id()) +"',  '"+ setClearData(bean.getTime_slab_id()) +"',  '"+ setClearData(bean.getInvoice_type_id()) +"',  '"+ setClearData(bean.getSubject_of_file()) +"',  '"+ setClearData(bean.getConnection_type()) +"',  '"+ setClearData(bean.getTotal_duration()) +"',  '"+ setClearData(bean.getDelivery_date()) +"',  '"+ setClearData(bean.getTranscription_link()) +"',  '"+ setClearData(bean.getOrder_date()) +"',  '"+ setClearData(bean.getModified_date()) +"',  '"+ setClearData(bean.getTotal_fees()) +"',  '"+ setClearData(bean.getOrder_placed_details()) +"',  '"+ setClearData(bean.getOrder_complete_details()) +"',  '"+ setClearData(bean.getDays_excluded_tat()) +"',  '"+ setClearData(bean.getOutput_format_id()) +"',  "+ bean.getUser_id() +" )";

                        }else{
                            sql = "insert or replace into orderDetails  ( assignment_no, client_instruction, total_files, service_type_id, trans_type_id, delivery_opt_id, order_status_id, vas_id, time_slab_id, invoice_type_id, subject_of_file, connection_type, total_duration, delivery_date, transcription_link, order_date, modified_date, total_fees, order_placed_details, order_complete_details, days_excluded_tat, output_format_id, user_id  ) values (  '"+ setClearData(bean.getAssignment_no()) +"',  '"+ setClearData(bean.getClient_instruction()) +"',  '"+ setClearData(bean.getTotal_files()) +"',  '"+ setClearData(bean.getService_type_id()) +"',  '"+ setClearData(bean.getTrans_type_id()) +"',  '"+ setClearData(bean.getDelivery_opt_id()) +"',  '"+ setClearData(bean.getOrder_status_id()) +"',  '"+ setClearData(bean.getVas_id()) +"',  '"+ setClearData(bean.getTime_slab_id()) +"',  '"+ setClearData(bean.getInvoice_type_id()) +"',  '"+ setClearData(bean.getSubject_of_file()) +"',  '"+ setClearData(bean.getConnection_type()) +"',  '"+ setClearData(bean.getTotal_duration()) +"',  '"+ setClearData(bean.getDelivery_date()) +"',  '"+ setClearData(bean.getTranscription_link()) +"',  '"+ setClearData(bean.getOrder_date()) +"',  '"+ setClearData(bean.getModified_date()) +"',  '"+ setClearData(bean.getTotal_fees()) +"',  '"+ setClearData(bean.getOrder_placed_details()) +"',  '"+ setClearData(bean.getOrder_complete_details()) +"',  '"+ setClearData(bean.getDays_excluded_tat()) +"',  '"+ setClearData(bean.getOutput_format_id()) +"',  "+ bean.getUser_id() +" )";

                        }

                        // Order Rec
                        addOrderRec(bean);



                        GlobalData.printMessage("OrderDetails SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                }
                catch (Exception e) {
                    GlobalData.printError("Add OrderDetails ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("OrderDetails", e);
            }
        }
    }

    public int getOrderDetailsCount() {
        String sql = "select count( OrderDetailsId ) as total from OrderDetails 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  OrderDetails:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }



    public LinkedList<OrderDetails> getOrderDetails() {
        String sql = "select *  from OrderDetails ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<OrderDetails> list = new LinkedList<OrderDetails>();
        int i0 = c.getColumnIndex(OrderDetails_KEY_order_id);
        int i1 = c.getColumnIndex(OrderDetails_KEY_assignment_no);
        int i2 = c.getColumnIndex(OrderDetails_KEY_client_instruction);
        int i3 = c.getColumnIndex(OrderDetails_KEY_total_files);
        int i4 = c.getColumnIndex(OrderDetails_KEY_service_type_id);
        int i5 = c.getColumnIndex(OrderDetails_KEY_trans_type_id);
        int i6 = c.getColumnIndex(OrderDetails_KEY_delivery_opt_id);
        int i7 = c.getColumnIndex(OrderDetails_KEY_order_status_id);
        int i8 = c.getColumnIndex(OrderDetails_KEY_vas_id);
        int i9 = c.getColumnIndex(OrderDetails_KEY_time_slab_id);
        int i10 = c.getColumnIndex(OrderDetails_KEY_invoice_type_id);
        int i11 = c.getColumnIndex(OrderDetails_KEY_subject_of_file);
        int i12 = c.getColumnIndex(OrderDetails_KEY_connection_type);
        int i13 = c.getColumnIndex(OrderDetails_KEY_total_duration);
        int i14 = c.getColumnIndex(OrderDetails_KEY_delivery_date);
        int i15 = c.getColumnIndex(OrderDetails_KEY_transcription_link);
        int i16 = c.getColumnIndex(OrderDetails_KEY_order_date);
        int i17 = c.getColumnIndex(OrderDetails_KEY_modified_date);
        int i18 = c.getColumnIndex(OrderDetails_KEY_total_fees);
        int i19 = c.getColumnIndex(OrderDetails_KEY_order_placed_details);
        int i20 = c.getColumnIndex(OrderDetails_KEY_order_complete_details);
        int i21 = c.getColumnIndex(OrderDetails_KEY_days_excluded_tat);
        int i22 = c.getColumnIndex(OrderDetails_KEY_output_format_id);
        int i23 = c.getColumnIndex(OrderDetails_KEY_user_id);
        int i24 = c.getColumnIndex(OrderDetails_KEY_flag);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                OrderDetails bean = new OrderDetails();
                bean.setOrder_id(c.getInt(i0));
                bean.setAssignment_no(getClearData(c.getString(i1)));
                bean.setClient_instruction(getClearData(c.getString(i2)));
                bean.setTotal_files(getClearData(c.getString(i3)));
                bean.setService_type_id(getClearData(c.getString(i4)));
                bean.setTrans_type_id(getClearData(c.getString(i5)));
                bean.setDelivery_opt_id(getClearData(c.getString(i6)));
                bean.setOrder_status_id(getClearData(c.getString(i7)));
                bean.setVas_id(getClearData(c.getString(i8)));
                bean.setTime_slab_id(getClearData(c.getString(i9)));
                bean.setInvoice_type_id(getClearData(c.getString(i10)));
                bean.setSubject_of_file(getClearData(c.getString(i11)));
                bean.setConnection_type(getClearData(c.getString(i12)));
                bean.setTotal_duration(getClearData(c.getString(i13)));
                bean.setDelivery_date(getClearData(c.getString(i14)));
                bean.setTranscription_link(getClearData(c.getString(i15)));
                bean.setOrder_date(getClearData(c.getString(i16)));
                bean.setModified_date(getClearData(c.getString(i17)));
                bean.setTotal_fees(getClearData(c.getString(i18)));
                bean.setOrder_placed_details(getClearData(c.getString(i19)));
                bean.setOrder_complete_details(getClearData(c.getString(i20)));
                bean.setDays_excluded_tat(getClearData(c.getString(i21)));
                bean.setOutput_format_id(getClearData(c.getString(i22)));
                bean.setUser_id(c.getInt(i23));
                bean.setFlag(c.getInt(i24));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("OrderDetails Read:", e);
            }
        }
        c.close();
        return list;
    }


    public void updateorderdetails(OrderDetails bean) {
        String sql = "";
        try {
            if (bean != null) {
                sql = "update orderDetails  set totalDurationMin='"+bean.getTotalDurationMin()+"',  assignment_no='" +bean.getAssignment_no() + "', client_instruction='" + bean.getClient_instruction() + "', total_files= '" + bean.getTotal_files() + "', service_type_id= '" + bean.getService_type_id() + "', trans_type_id='" + bean.getTrans_type_id() + "',  delivery_opt_id='" + bean.getDelivery_opt_id() + "',  order_status_id='" + bean.getOrder_status_id() + "',  vas_id='" + bean.getVas_id() + "', time_slab_id='" + bean.getTime_slab_id() + "', invoice_type_id= '" + bean.getInvoice_type_id() + "', subject_of_file='" + bean.getSubject_of_file() + "',  connection_type='" + bean.getConnection_type() + "', total_duration='" + bean.getTotal_duration() + "', delivery_date='" + bean.getDelivery_date() + "', transcription_link='" + bean.getTranscription_link() + "', order_date='" + bean.getOrder_date() + "', modified_date='" + bean.getModified_date() + "',total_fees='" + bean.getTotal_fees() + "', order_placed_details='" + bean.getOrder_placed_details() + "',order_complete_details='" + bean.getOrder_complete_details() + "',days_excluded_tat='" + bean.getDays_excluded_tat() + "', output_format_id='" + bean.getOutput_format_id() + "',user_id=" + bean.getUser_id() + ",flag=" + bean.getFlag() + " , upTime ='"+bean.getTo_date()+"' where order_id=" + bean.getOrder_id()+" ";
                GlobalData.printMessage("OrderRec SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add OrderRec ", e);
        }
    }

    public void updateorderdetailFlags(OrderDetails bean) {
        String sql = "";
        try {
            if (bean != null) {
                sql = "update orderDetails  set  flag=" + bean.getFlag() + " where order_id=" + bean.getOrder_id()+" ";
                GlobalData.printMessage("OrderRec SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add OrderRec ", e);
        }
    }

    public void addOrderRec(OrderDetails bean) {
        String sql = "";
        try {
            if (bean != null) {
                sql = "insert into orderDetails  (totalDurationMin, assignment_no, client_instruction, total_files, service_type_id, trans_type_id, delivery_opt_id, order_status_id, vas_id, time_slab_id, invoice_type_id, subject_of_file, connection_type, total_duration, delivery_date, transcription_link, order_date, modified_date,total_fees, order_placed_details,order_complete_details,days_excluded_tat , output_format_id,user_id,flag,upTime,premium_type  ,discount_type   ,gross_fees   ,premium_per_hour   ,total_premium   ,discount   ,total_discount   ) values ('"+bean.getTotalDurationMin()+"',  '" + setClearData(bean.getAssignment_no()) + "',  '" + setClearData(bean.getClient_instruction()) + "',  '" + bean.getTotal_files() + "',  '" + bean.getService_type_id() + "',  '" + bean.getTrans_type_id() + "',  '" + bean.getDelivery_opt_id() + "',  '" + bean.getOrder_status_id() + "',  '" + bean.getVas_id() + "', ' " + bean.getTime_slab_id() + "', ' " + bean.getInvoice_type_id() + "', '" + bean.getSubject_of_file() + "',  '" + bean.getConnection_type() + "',  '" + bean.getTotal_duration() + "',    '" + setClearData(bean.getDelivery_date()) + "',   '" + bean.getTranscription_link() + "',  '" + setClearData(bean.getOrder_date()) + "',  '" + setClearData(bean.getModified_date()) + "','" + bean.getTotal_fees() + "',   '" + setClearData(bean.getOrder_placed_details()) + "',  '" + setClearData(bean.getOrder_complete_details()) + "',  '" + setClearData(bean.getDays_excluded_tat()) + "',' " + bean.getOutput_format_id() + "',  " + bean.getUser_id() + ", " + bean.getFlag() + " ,'"+bean.getTo_date()+"' , '"+bean.getPremium_type()+"' ,'"+bean.getDiscount_type()+"','"+bean.getGross_fees()+"','"+bean.getPremium_per_hour()+"','"+bean.getTotal_premium()+"','"+bean.getDiscount()+"','"+bean.getTotal_discount()+"')";

                GlobalData.printMessage("OrderRec SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add OrderRec ", e);
        }
    }


    public OrderDetails getOrderDetailsList(int order_id) {
        String sql = "select *  from orderDetails where order_id=" + order_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        OrderDetails bean = new OrderDetails();
        int i0 = c.getColumnIndex(OrderDetails_KEY_order_id);
        int i1 = c.getColumnIndex(OrderDetails_KEY_assignment_no);
        int i2 = c.getColumnIndex(OrderDetails_KEY_client_instruction);
        int i3 = c.getColumnIndex(OrderDetails_KEY_total_files);
        int i4 = c.getColumnIndex(OrderDetails_KEY_service_type_id);
        int i5 = c.getColumnIndex(OrderDetails_KEY_trans_type_id);
        int i6 = c.getColumnIndex(OrderDetails_KEY_delivery_opt_id);
        int i7 = c.getColumnIndex(OrderDetails_KEY_order_status_id);
        int i8 = c.getColumnIndex(OrderDetails_KEY_vas_id);
        int i9 = c.getColumnIndex(OrderDetails_KEY_time_slab_id);
        int i10 = c.getColumnIndex(OrderDetails_KEY_invoice_type_id);
        int i11 = c.getColumnIndex(OrderDetails_KEY_subject_of_file);
        int i12 = c.getColumnIndex(OrderDetails_KEY_connection_type);
        int i13 = c.getColumnIndex(OrderDetails_KEY_total_duration);
        int i14 = c.getColumnIndex(OrderDetails_KEY_delivery_date);
        int i15 = c.getColumnIndex(OrderDetails_KEY_transcription_link);
        int i16 = c.getColumnIndex(OrderDetails_KEY_order_date);
        int i17 = c.getColumnIndex(OrderDetails_KEY_modified_date);
        int i18 = c.getColumnIndex(OrderDetails_KEY_total_fees);
        int i19 = c.getColumnIndex(OrderDetails_KEY_order_placed_details);
        int i20 = c.getColumnIndex(OrderDetails_KEY_order_complete_details);
        int i21 = c.getColumnIndex(OrderDetails_KEY_days_excluded_tat);
        int i22 = c.getColumnIndex(OrderDetails_KEY_output_format_id);
        int i23 = c.getColumnIndex(OrderDetails_KEY_user_id);
        int i24 = c.getColumnIndex(OrderDetails_KEY_flag);
        int i25=c.getColumnIndex("totalDurationMin");




        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setOrder_id(c.getInt(i0));
                bean.setAssignment_no(c.getString(i1));
                bean.setClient_instruction(c.getString(i2));
                bean.setTotal_files(c.getString(i3));
                bean.setService_type_id(c.getString(i4));
                bean.setTrans_type_id(c.getString(i5));
                bean.setDelivery_opt_id(c.getString(i6));
                bean.setOrder_status_id(c.getString(i7));
                bean.setVas_id(c.getString(i8));
                bean.setTime_slab_id(c.getString(i9));
                bean.setInvoice_type_id(c.getString(i10));
                bean.setSubject_of_file(c.getString(i11));
                bean.setConnection_type(c.getString(i12));
                bean.setTotal_duration(c.getString(i13));
                bean.setDelivery_date(c.getString(i14));
                bean.setTranscription_link(c.getString(i15));
                bean.setOrder_date(c.getString(i16));
                bean.setModified_date(c.getString(i17));
                bean.setTotal_fees(c.getString(i18));
                bean.setOrder_placed_details(c.getString(i19));
                bean.setOrder_complete_details(c.getString(i20));
                bean.setDays_excluded_tat(c.getString(i21));
                bean.setOutput_format_id(c.getString(i22));
                bean.setUser_id(c.getInt(i23));
                bean.setFlag(c.getInt(i24));

                bean.setTotalDurationMin(c.getString(i25));
            } catch (Exception e) {
                GlobalData.printError("OrderDetails Read:", e);
            }
        }
        c.close();
        return bean;
    }

    public void addOrderDetails(OrderDetails bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into orderDetails  (totalDurationMin, assignment_no, client_instruction, total_files, service_type_id, trans_type_id, delivery_opt_id, order_status_id, vas_id, time_slab_id, invoice_type_id, subject_of_file, connection_type, total_duration, delivery_date, transcription_link, order_date, modified_date, total_fees, order_placed_details, order_complete_details, days_excluded_tat, output_format_id, user_id  ) values (  '"+bean.getTotalDurationMin()+"', '" + setClearData(bean.getAssignment_no()) + "',  '" + setClearData(bean.getClient_instruction()) + "',  '" + setClearData(bean.getTotal_files()) + "',  '" + setClearData(bean.getService_type_id()) + "',  '" + setClearData(bean.getTrans_type_id()) + "',  '" + setClearData(bean.getDelivery_opt_id()) + "',  '" + setClearData(bean.getOrder_status_id()) + "',  '" + setClearData(bean.getVas_id()) + "',  '" + setClearData(bean.getTime_slab_id()) + "',  '" + setClearData(bean.getInvoice_type_id()) + "',  '" + setClearData(bean.getSubject_of_file()) + "',  '" + setClearData(bean.getConnection_type()) + "',  '" + setClearData(bean.getTotal_duration()) + "',  '" + setClearData(bean.getDelivery_date()) + "',  '" + setClearData(bean.getTranscription_link()) + "',  '" + setClearData(bean.getOrder_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getTotal_fees()) + "',  '" + setClearData(bean.getOrder_placed_details()) + "',  '" + setClearData(bean.getOrder_complete_details()) + "',  '" + setClearData(bean.getDays_excluded_tat()) + "',  '" + setClearData(bean.getOutput_format_id()) + "',  " + bean.getUser_id() + " )";
                GlobalData.printMessage("OrderDetails SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add OrderDetails ", e);
        }
    }

    public void addOrderDetailsList(LinkedList<OrderDetails> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                OrderDetails bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into orderDetails  ( totalDurationMin,assignment_no, client_instruction, total_files, service_type_id, trans_type_id, delivery_opt_id, order_status_id, vas_id, time_slab_id, invoice_type_id, subject_of_file, connection_type, total_duration, delivery_date, transcription_link, order_date, modified_date, total_fees, order_placed_details, order_complete_details, days_excluded_tat, output_format_id, user_id  ) values ( '"+bean.getTotalDurationMin()+"', '" + setClearData(bean.getAssignment_no()) + "',  '" + setClearData(bean.getClient_instruction()) + "',  '" + setClearData(bean.getTotal_files()) + "',  '" + setClearData(bean.getService_type_id()) + "',  '" + setClearData(bean.getTrans_type_id()) + "',  '" + setClearData(bean.getDelivery_opt_id()) + "',  '" + setClearData(bean.getOrder_status_id()) + "',  '" + setClearData(bean.getVas_id()) + "',  '" + setClearData(bean.getTime_slab_id()) + "',  '" + setClearData(bean.getInvoice_type_id()) + "',  '" + setClearData(bean.getSubject_of_file()) + "',  '" + setClearData(bean.getConnection_type()) + "',  '" + setClearData(bean.getTotal_duration()) + "',  '" + setClearData(bean.getDelivery_date()) + "',  '" + setClearData(bean.getTranscription_link()) + "',  '" + setClearData(bean.getOrder_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getTotal_fees()) + "',  '" + setClearData(bean.getOrder_placed_details()) + "',  '" + setClearData(bean.getOrder_complete_details()) + "',  '" + setClearData(bean.getDays_excluded_tat()) + "',  '" + setClearData(bean.getOutput_format_id()) + "',  " + bean.getUser_id() + " )";
                        GlobalData.printMessage("OrderDetails SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add OrderDetails ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("OrderDetails", e);
            }
        }
    }

    public void deleteOrderDetails() {
        String sql = "";
        try {


            sql = "delete from orderDetails  ";
            GlobalData.printMessage("OrderDetails SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete OrderDetails ", e);
        }
    }

    public void deleteOrderDetails(int rowId) {
        String sql = "";
        try {


            sql = "delete from orderDetails where order_id=" + rowId;
            GlobalData.printMessage("OrderDetails SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete OrderDetails ", e);
        }
    }


//  Methods Of Order_free_services

    public int getOrder_free_servicesCount() {
        String sql = "select count( Order_free_servicesId ) as total from order_free_services 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Order_free_services:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Order_free_services> getOrder_free_services() {
        String sql = "select *  from order_free_services ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Order_free_services> list = new LinkedList<Order_free_services>();
        int i0 = c.getColumnIndex(Order_free_services_KEY_order_free_serid);
        int i1 = c.getColumnIndex(Order_free_services_KEY_order_id);
        int i2 = c.getColumnIndex(Order_free_services_KEY_free_serv_id);
        int i3 = c.getColumnIndex(Order_free_services_KEY_modified_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Order_free_services bean = new Order_free_services();
                bean.setOrder_free_serid(c.getInt(i0));
                bean.setOrder_id(c.getInt(i1));
                bean.setFree_serv_id(c.getInt(i2));
                bean.setModified_date(getClearData(c.getString(i3)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Order_free_services Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Order_free_services> getOrder_free_servicesList(int order_free_serid) {
        String sql = "select *  from order_free_services where order_free_serid=" + order_free_serid;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Order_free_services> list = new LinkedList<Order_free_services>();
        int i0 = c.getColumnIndex(Order_free_services_KEY_order_free_serid);
        int i1 = c.getColumnIndex(Order_free_services_KEY_order_id);
        int i2 = c.getColumnIndex(Order_free_services_KEY_free_serv_id);
        int i3 = c.getColumnIndex(Order_free_services_KEY_modified_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Order_free_services bean = new Order_free_services();
                bean.setOrder_free_serid(c.getInt(i0));
                bean.setOrder_id(c.getInt(i1));
                bean.setFree_serv_id(c.getInt(i2));
                bean.setModified_date(getClearData(c.getString(i3)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Order_free_services Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addOrder_free_services(Order_free_services bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into order_free_services  ( order_id, free_serv_id, modified_date  ) values (  " + bean.getOrder_id() + ",  " + bean.getFree_serv_id() + ",  '" + setClearData(bean.getModified_date()) + "' )";
                GlobalData.printMessage("Order_free_services SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Order_free_services ", e);
        }
    }

    public void addOrder_free_servicesList(LinkedList<Order_free_services> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Order_free_services bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into order_free_services  ( order_id, free_serv_id, modified_date  ) values (  " + bean.getOrder_id() + ",  " + bean.getFree_serv_id() + ",  '" + setClearData(bean.getModified_date()) + "' )";
                        GlobalData.printMessage("Order_free_services SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Order_free_services ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Order_free_services", e);
            }
        }
    }

    public void deleteOrder_free_services() {
        String sql = "";
        try {


            sql = "delete from order_free_services  ";
            GlobalData.printMessage("Order_free_services SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Order_free_services ", e);
        }
    }

    public void deleteOrder_free_services(int rowId) {
        String sql = "";
        try {


            sql = "delete from order_free_services where order_free_serid=" + rowId;
            GlobalData.printMessage("Order_free_services SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Order_free_services ", e);
        }
    }


//  Methods Of Order_media

    public int getOrder_mediaCount() {
        String sql = "select count( Order_mediaId ) as total from Order_media 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Order_media:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Order_media> getOrder_media() {
        String sql = "select *  from Order_media ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Order_media> list = new LinkedList<Order_media>();
        int i0 = c.getColumnIndex(Order_media_KEY_order_media_id);
        int i1 = c.getColumnIndex(Order_media_KEY_user_id);
        int i2 = c.getColumnIndex(Order_media_KEY_order_id);
        int i3 = c.getColumnIndex(Order_media_KEY_file_status);
        int i4 = c.getColumnIndex(Order_media_KEY_source_type);
        int i5 = c.getColumnIndex(Order_media_KEY_sourcelink);
        int i6 = c.getColumnIndex(Order_media_KEY_file_name);
        int i7 = c.getColumnIndex(Order_media_KEY_file_description);
        int i8 = c.getColumnIndex(Order_media_KEY_file_duration);
        int i9 = c.getColumnIndex(Order_media_KEY_file_localpath);
        int i10 = c.getColumnIndex(Order_media_KEY_file_size);
        int i11 = c.getColumnIndex(Order_media_KEY_file_upload_duration);
        int i12 = c.getColumnIndex(Order_media_KEY_file_up_conn_mode);
        int i13 = c.getColumnIndex(Order_media_KEY_created_date);
        int i14 = c.getColumnIndex(Order_media_KEY_modified_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Order_media bean = new Order_media();
                bean.setOrder_media_id(c.getInt(i0));
                bean.setUser_id(c.getInt(i1));
                bean.setOrder_id(c.getInt(i2));
                bean.setFile_status(getClearData(c.getString(i3)));
                bean.setSource_type(getClearData(c.getString(i4)));
                bean.setSourcelink(getClearData(c.getString(i5)));
                bean.setFile_name(getClearData(c.getString(i6)));
                bean.setFile_description(getClearData(c.getString(i7)));
                bean.setFile_duration(getClearData(c.getString(i8)));
                bean.setFile_localpath(getClearData(c.getString(i9)));
                bean.setFile_size(getClearData(c.getString(i10)));
                bean.setFile_upload_duration(getClearData(c.getString(i11)));
                bean.setFile_up_conn_mode(getClearData(c.getString(i12)));
                bean.setCreated_date(getClearData(c.getString(i13)));
                bean.setModified_date(getClearData(c.getString(i14)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Order_media Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Order_media> getOrder_mediaList(int order_media_id) {
        String sql = "select *  from Order_media where order_media_id=" + order_media_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Order_media> list = new LinkedList<Order_media>();
        int i0 = c.getColumnIndex(Order_media_KEY_order_media_id);
        int i1 = c.getColumnIndex(Order_media_KEY_user_id);
        int i2 = c.getColumnIndex(Order_media_KEY_order_id);
        int i3 = c.getColumnIndex(Order_media_KEY_file_status);
        int i4 = c.getColumnIndex(Order_media_KEY_source_type);
        int i5 = c.getColumnIndex(Order_media_KEY_sourcelink);
        int i6 = c.getColumnIndex(Order_media_KEY_file_name);
        int i7 = c.getColumnIndex(Order_media_KEY_file_description);
        int i8 = c.getColumnIndex(Order_media_KEY_file_duration);
        int i9 = c.getColumnIndex(Order_media_KEY_file_localpath);
        int i10 = c.getColumnIndex(Order_media_KEY_file_size);
        int i11 = c.getColumnIndex(Order_media_KEY_file_upload_duration);
        int i12 = c.getColumnIndex(Order_media_KEY_file_up_conn_mode);
        int i13 = c.getColumnIndex(Order_media_KEY_created_date);
        int i14 = c.getColumnIndex(Order_media_KEY_modified_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Order_media bean = new Order_media();
                bean.setOrder_media_id(c.getInt(i0));
                bean.setUser_id(c.getInt(i1));
                bean.setOrder_id(c.getInt(i2));
                bean.setFile_status(getClearData(c.getString(i3)));
                bean.setSource_type(getClearData(c.getString(i4)));
                bean.setSourcelink(getClearData(c.getString(i5)));
                bean.setFile_name(getClearData(c.getString(i6)));
                bean.setFile_description(getClearData(c.getString(i7)));
                bean.setFile_duration(getClearData(c.getString(i8)));
                bean.setFile_localpath(getClearData(c.getString(i9)));
                bean.setFile_size(getClearData(c.getString(i10)));
                bean.setFile_upload_duration(getClearData(c.getString(i11)));
                bean.setFile_up_conn_mode(getClearData(c.getString(i12)));
                bean.setCreated_date(getClearData(c.getString(i13)));
                bean.setModified_date(getClearData(c.getString(i14)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Order_media Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addOrder_media(Order_media bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Order_media  ( user_id, order_id, file_status, source_type, sourcelink, file_name, file_description, file_duration, file_localpath, file_size, file_upload_duration, file_up_conn_mode, created_date, modified_date  ) values (  " + bean.getUser_id() + ",  " + bean.getOrder_id() + ",  '" + setClearData(bean.getFile_status()) + "',  '" + setClearData(bean.getSource_type()) + "',  '" + setClearData(bean.getSourcelink()) + "',  '" + setClearData(bean.getFile_name()) + "',  '" + setClearData(bean.getFile_description()) + "',  '" + setClearData(bean.getFile_duration()) + "',  '" + setClearData(bean.getFile_localpath()) + "',  '" + setClearData(bean.getFile_size()) + "',  '" + setClearData(bean.getFile_upload_duration()) + "',  '" + setClearData(bean.getFile_up_conn_mode()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "' )";
                GlobalData.printMessage("Order_media SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Order_media ", e);
        }
    }

    public void addOrder_mediaList(LinkedList<Order_media> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Order_media bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Order_media  ( user_id, order_id, file_status, source_type, sourcelink, file_name, file_description, file_duration, file_localpath, file_size, file_upload_duration, file_up_conn_mode, created_date, modified_date  ) values (  " + bean.getUser_id() + ",  " + bean.getOrder_id() + ",  '" + setClearData(bean.getFile_status()) + "',  '" + setClearData(bean.getSource_type()) + "',  '" + setClearData(bean.getSourcelink()) + "',  '" + setClearData(bean.getFile_name()) + "',  '" + setClearData(bean.getFile_description()) + "',  '" + setClearData(bean.getFile_duration()) + "',  '" + setClearData(bean.getFile_localpath()) + "',  '" + setClearData(bean.getFile_size()) + "',  '" + setClearData(bean.getFile_upload_duration()) + "',  '" + setClearData(bean.getFile_up_conn_mode()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "' )";
                        GlobalData.printMessage("Order_media SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Order_media ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Order_media", e);
            }
        }
    }

    public void deleteOrder_media() {
        String sql = "";
        try {


            sql = "delete from Order_media  ";
            GlobalData.printMessage("Order_media SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Order_media ", e);
        }
    }

    public void deleteOrder_media(int rowId) {
        String sql = "";
        try {


            sql = "delete from Order_media where order_media_id=" + rowId;
            GlobalData.printMessage("Order_media SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Order_media ", e);
        }
    }


//  Methods Of Order_recording

    public int getOrder_recordingCount() {
        String sql = "select count( Order_recordingId ) as total from Order_recording 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Order_recording:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Order_recording> getOrder_recording() {
        String sql = "select *  from Order_recording ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Order_recording> list = new LinkedList<Order_recording>();
        int i0 = c.getColumnIndex(Order_recording_KEY_order_rec_id);
        int i1 = c.getColumnIndex(Order_recording_KEY_order_id);
        int i2 = c.getColumnIndex(Order_recording_KEY_recording_id);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Order_recording bean = new Order_recording();
                bean.setOrder_rec_id(c.getInt(i0));
                bean.setOrder_id(c.getInt(i1));
                bean.setRecording_id(c.getInt(i2));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Order_recording Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Order_recording> getOrder_recordingList(int order_rec_id) {
        String sql = "select *  from Order_recording where order_rec_id=" + order_rec_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Order_recording> list = new LinkedList<Order_recording>();
        int i0 = c.getColumnIndex(Order_recording_KEY_order_rec_id);
        int i1 = c.getColumnIndex(Order_recording_KEY_order_id);
        int i2 = c.getColumnIndex(Order_recording_KEY_recording_id);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Order_recording bean = new Order_recording();
                bean.setOrder_rec_id(c.getInt(i0));
                bean.setOrder_id(c.getInt(i1));
                bean.setRecording_id(c.getInt(i2));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Order_recording Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addOrder_recording(Order_recording bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Order_recording  ( order_id, recording_id  ) values (  " + bean.getOrder_id() + ",  " + bean.getRecording_id() + " )";
                GlobalData.printMessage("Order_recording SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Order_recording ", e);
        }
    }

    public void addOrder_recordingList(LinkedList<Order_recording> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Order_recording bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Order_recording  ( order_id, recording_id  ) values (  " + bean.getOrder_id() + ",  " + bean.getRecording_id() + " )";
                        GlobalData.printMessage("Order_recording SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Order_recording ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Order_recording", e);
            }
        }
    }

    public void deleteOrder_recording() {
        String sql = "";
        try {


            sql = "delete from Order_recording  ";
            GlobalData.printMessage("Order_recording SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Order_recording ", e);
        }
    }

    public void deleteOrder_recording(int rowId) {
        String sql = "";
        try {


            sql = "delete from Order_recording where order_rec_id=" + rowId;
            GlobalData.printMessage("Order_recording SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Order_recording ", e);
        }
    }


//  Methods Of Order_transaction

    public int getOrder_transactionCount() {
        String sql = "select count( Order_transactionId ) as total from Order_transaction 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Order_transaction:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Order_transaction> getOrder_transaction() {
        String sql = "select *  from Order_transaction ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Order_transaction> list = new LinkedList<Order_transaction>();
        int i0 = c.getColumnIndex(Order_transaction_KEY_order_trasnaction_id);
        int i1 = c.getColumnIndex(Order_transaction_KEY_order_id);
        int i2 = c.getColumnIndex(Order_transaction_KEY_rec_id);
        int i3 = c.getColumnIndex(Order_transaction_KEY_status_id);
        int i4 = c.getColumnIndex(Order_transaction_KEY_trans_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Order_transaction bean = new Order_transaction();
                bean.setOrder_trasnaction_id(c.getInt(i0));
                bean.setOrder_id(c.getInt(i1));
                bean.setRec_id(c.getInt(i2));
                bean.setStatus_id(getClearData(c.getString(i3)));
                bean.setTrans_date(getClearData(c.getString(i4)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Order_transaction Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Order_transaction> getOrder_transactionList(int order_trasnaction_id) {
        String sql = "select *  from Order_transaction where order_trasnaction_id=" + order_trasnaction_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Order_transaction> list = new LinkedList<Order_transaction>();
        int i0 = c.getColumnIndex(Order_transaction_KEY_order_trasnaction_id);
        int i1 = c.getColumnIndex(Order_transaction_KEY_order_id);
        int i2 = c.getColumnIndex(Order_transaction_KEY_rec_id);
        int i3 = c.getColumnIndex(Order_transaction_KEY_status_id);
        int i4 = c.getColumnIndex(Order_transaction_KEY_trans_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Order_transaction bean = new Order_transaction();
                bean.setOrder_trasnaction_id(c.getInt(i0));
                bean.setOrder_id(c.getInt(i1));
                bean.setRec_id(c.getInt(i2));
                bean.setStatus_id(getClearData(c.getString(i3)));
                bean.setTrans_date(getClearData(c.getString(i4)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Order_transaction Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addOrder_transaction(Order_transaction bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Order_transaction  ( order_id, rec_id, status_id, trans_date  ) values (  " + bean.getOrder_id() + ",  " + bean.getRec_id() + ",  '" + setClearData(bean.getStatus_id()) + "',  '" + setClearData(bean.getTrans_date()) + "' )";
                GlobalData.printMessage("Order_transaction SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Order_transaction ", e);
        }
    }

    public void addOrder_transactionList(LinkedList<Order_transaction> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Order_transaction bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Order_transaction  ( order_id, rec_id, status_id, trans_date  ) values (  " + bean.getOrder_id() + ",  " + bean.getRec_id() + ",  '" + setClearData(bean.getStatus_id()) + "',  '" + setClearData(bean.getTrans_date()) + "' )";
                        GlobalData.printMessage("Order_transaction SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Order_transaction ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Order_transaction", e);
            }
        }
    }

    public void deleteOrder_transaction() {
        String sql = "";
        try {


            sql = "delete from Order_transaction  ";
            GlobalData.printMessage("Order_transaction SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Order_transaction ", e);
        }
    }

    public void deleteOrder_transaction(int rowId) {
        String sql = "";
        try {


            sql = "delete from Order_transaction where order_trasnaction_id=" + rowId;
            GlobalData.printMessage("Order_transaction SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Order_transaction ", e);
        }
    }


//  Methods Of Order_vas

    public int getOrder_vasCount() {
        String sql = "select count( Order_vasId ) as total from Order_vas 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Order_vas:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Order_vas> getOrder_vas() {
        String sql = "select *  from Order_vas "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Order_vas> list = new LinkedList<Order_vas>();
        int i0 = c.getColumnIndex(Order_vas_KEY_order_vas_id);
        int i1 = c.getColumnIndex(Order_vas_KEY_order_id);
        int i2 = c.getColumnIndex(Order_vas_KEY_vas_id);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Order_vas bean = new Order_vas();
                bean.setOrder_vas_id(getClearData(c.getString(i0)));
                bean.setOrder_id(c.getInt(i1));
                bean.setVas_id(c.getInt(i2));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Order_vas Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Order_vas> getOrder_vasList(int order_vas_id) {
        String sql = "select *  from Order_vas where order_vas_id=" + order_vas_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Order_vas> list = new LinkedList<Order_vas>();
        int i0 = c.getColumnIndex(Order_vas_KEY_order_vas_id);
        int i1 = c.getColumnIndex(Order_vas_KEY_order_id);
        int i2 = c.getColumnIndex(Order_vas_KEY_vas_id);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Order_vas bean = new Order_vas();
                bean.setOrder_vas_id(getClearData(c.getString(i0)));
                bean.setOrder_id(c.getInt(i1));
                bean.setVas_id(c.getInt(i2));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Order_vas Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addOrder_vas(Order_vas bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Order_vas  ( order_id, vas_id  ) values (  " + bean.getOrder_id() + ",  " + bean.getVas_id() + " )";
                GlobalData.printMessage("Order_vas SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Order_vas ", e);
        }
    }

    public void addOrder_vasList(LinkedList<Order_vas> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Order_vas bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Order_vas  ( order_id, vas_id  ) values (  " + bean.getOrder_id() + ",  " + bean.getVas_id() + " )";
                        GlobalData.printMessage("Order_vas SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Order_vas ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Order_vas", e);
            }
        }
    }

    public void deleteOrder_vas() {
        String sql = "";
        try {


            sql = "delete from Order_vas  ";
            GlobalData.printMessage("Order_vas SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Order_vas ", e);
        }
    }

    public void deleteOrder_vas(int rowId) {
        String sql = "";
        try {


            sql = "delete from Order_vas where order_vas_id=" + rowId;
            GlobalData.printMessage("Order_vas SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Order_vas ", e);
        }
    }


//  Methods Of Output_format

    public int getOutput_formatCount() {
        String sql = "select count( Output_formatId ) as total from Output_format 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Output_format:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Output_format> getOutput_format() {
        String sql = "select *  from Output_format "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Output_format> list = new LinkedList<Output_format>();
        int i0 = c.getColumnIndex(Output_format_KEY_output_format_id);
        int i1 = c.getColumnIndex(Output_format_KEY_output_format_txt);
        int i2 = c.getColumnIndex(Output_format_KEY_status);
        int i3 = c.getColumnIndex(Output_format_KEY_modified_date);
        int i4 = c.getColumnIndex(Output_format_KEY_created_date);
        int i5 = c.getColumnIndex(Output_format_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Output_format bean = new Output_format();
                bean.setOutput_format_id(getClearData(c.getString(i0)));
                bean.setOutput_format_txt(getClearData(c.getString(i1)));
                bean.setStatus(getClearData(c.getString(i2)));
                bean.setModified_date(getClearData(c.getString(i3)));
                bean.setCreated_date(getClearData(c.getString(i4)));
                bean.setSoft_del(getClearData(c.getString(i5)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Output_format Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Output_format> getOutput_formatList(int output_format_id) {
        String sql = "select *  from Output_format where output_format_id=" + output_format_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Output_format> list = new LinkedList<Output_format>();
        int i0 = c.getColumnIndex(Output_format_KEY_output_format_id);
        int i1 = c.getColumnIndex(Output_format_KEY_output_format_txt);
        int i2 = c.getColumnIndex(Output_format_KEY_status);
        int i3 = c.getColumnIndex(Output_format_KEY_modified_date);
        int i4 = c.getColumnIndex(Output_format_KEY_created_date);
        int i5 = c.getColumnIndex(Output_format_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Output_format bean = new Output_format();
                bean.setOutput_format_id(getClearData(c.getString(i0)));
                bean.setOutput_format_txt(getClearData(c.getString(i1)));
                bean.setStatus(getClearData(c.getString(i2)));
                bean.setModified_date(getClearData(c.getString(i3)));
                bean.setCreated_date(getClearData(c.getString(i4)));
                bean.setSoft_del(getClearData(c.getString(i5)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Output_format Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addOutput_format(Output_format bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Output_format  ( output_format_txt, status, modified_date, created_date, soft_del  ) values (  '" + setClearData(bean.getOutput_format_txt()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                GlobalData.printMessage("Output_format SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Output_format ", e);
        }
    }

    public void addOutput_formatList(LinkedList<Output_format> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Output_format bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Output_format  ( output_format_txt, status, modified_date, created_date, soft_del  ) values (  '" + setClearData(bean.getOutput_format_txt()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                        GlobalData.printMessage("Output_format SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Output_format ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Output_format", e);
            }
        }
    }

    public void deleteOutput_format() {
        String sql = "";
        try {


            sql = "delete from Output_format  ";
            GlobalData.printMessage("Output_format SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Output_format ", e);
        }
    }

    public void deleteOutput_format(int rowId) {
        String sql = "";
        try {


            sql = "delete from Output_format where output_format_id=" + rowId;
            GlobalData.printMessage("Output_format SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Output_format ", e);
        }
    }


//  Methods Of Premium_type

    public int getPremium_typeCount() {
        String sql = "select count( Premium_typeId ) as total from Premium_type 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Premium_type:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Premium_type> getPremium_type() {
        String sql = "select *  from Premium_type "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Premium_type> list = new LinkedList<Premium_type>();
        int i0 = c.getColumnIndex(Premium_type_KEY_premium_type_id);
        int i1 = c.getColumnIndex(Premium_type_KEY_premium_type);
        int i2 = c.getColumnIndex(Premium_type_KEY_status);
        int i3 = c.getColumnIndex(Premium_type_KEY_created_date);
        int i4 = c.getColumnIndex(Premium_type_KEY_modified_date);
        int i5 = c.getColumnIndex(Premium_type_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Premium_type bean = new Premium_type();
                bean.setPremium_type_id(getClearData(c.getString(i0)));
                bean.setPremium_type(getClearData(c.getString(i1)));
                bean.setStatus(getClearData(c.getString(i2)));
                bean.setCreated_date(getClearData(c.getString(i3)));
                bean.setModified_date(getClearData(c.getString(i4)));
                bean.setSoft_del(getClearData(c.getString(i5)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Premium_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public Premium_type getPremium_type(String premium_type_id) {
        String sql = "select  premium_type from Premium_type, timestamb where  timestamb.premium_type_id= Premium_type.premium_type_id  and  timestamb.timestamp_id ='"+premium_type_id+"'";
        Cursor c = ourDatabase.rawQuery(sql, null);
        Premium_type bean = new Premium_type();
//        int i0 = c.getColumnIndex(Premium_type_KEY_premium_type_id);
        int i1 = c.getColumnIndex(Premium_type_KEY_premium_type);
//        int i2 = c.getColumnIndex(Premium_type_KEY_status);
//        int i3 = c.getColumnIndex(Premium_type_KEY_created_date);
//        int i4 = c.getColumnIndex(Premium_type_KEY_modified_date);
//        int i5 = c.getColumnIndex(Premium_type_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

//                bean.setPremium_type_id(getClearData(c.getString(i0)));
                bean.setPremium_type(getClearData(c.getString(i1)));
//                bean.setStatus(getClearData(c.getString(i2)));
//                bean.setCreated_date(getClearData(c.getString(i3)));
//                bean.setModified_date(getClearData(c.getString(i4)));
//                bean.setSoft_del(getClearData(c.getString(i5)));


            } catch (Exception e) {
                GlobalData.printError("Premium_type Read:", e);
            }
        }
        c.close();
        return bean;
    }

    public LinkedList<Premium_type> getPremium_typeList(int premium_type_id) {
        String sql = "select *  from Premium_type where premium_type_id=" + premium_type_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Premium_type> list = new LinkedList<Premium_type>();
        int i0 = c.getColumnIndex(Premium_type_KEY_premium_type_id);
        int i1 = c.getColumnIndex(Premium_type_KEY_premium_type);
        int i2 = c.getColumnIndex(Premium_type_KEY_status);
        int i3 = c.getColumnIndex(Premium_type_KEY_created_date);
        int i4 = c.getColumnIndex(Premium_type_KEY_modified_date);
        int i5 = c.getColumnIndex(Premium_type_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Premium_type bean = new Premium_type();
                bean.setPremium_type_id(getClearData(c.getString(i0)));
                bean.setPremium_type(getClearData(c.getString(i1)));
                bean.setStatus(getClearData(c.getString(i2)));
                bean.setCreated_date(getClearData(c.getString(i3)));
                bean.setModified_date(getClearData(c.getString(i4)));
                bean.setSoft_del(getClearData(c.getString(i5)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Premium_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addPremium_type(Premium_type bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Premium_type  ( premium_type, status, created_date, modified_date, soft_del  ) values (  '" + setClearData(bean.getPremium_type()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                GlobalData.printMessage("Premium_type SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Premium_type ", e);
        }
    }

    public void addPremium_typeList(LinkedList<Premium_type> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Premium_type bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into Premium_type  ( premium_type_id,premium_type, status, created_date, modified_date, soft_del  ) values ( '"+bean.getPremium_type_id()+"', '" + setClearData(bean.getPremium_type()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                        GlobalData.printMessage("Premium_type SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Premium_type ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Premium_type", e);
            }
        }
    }

    public void deletePremium_type() {
        String sql = "";
        try {


            sql = "delete from Premium_type  ";
            GlobalData.printMessage("Premium_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Premium_type ", e);
        }
    }

    public void deletePremium_type(int rowId) {
        String sql = "";
        try {


            sql = "delete from Premium_type where premium_type_id=" + rowId;
            GlobalData.printMessage("Premium_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Premium_type ", e);
        }
    }


//  Methods Of Price

    public int getPriceCount() {
        String sql = "select count( PriceId ) as total from price 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Price:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Price> getPrice() {

        String sql = "select *  from price "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Price> list = new LinkedList<Price>();
        int i0 = c.getColumnIndex(Price_KEY_price_id);
        int i1 = c.getColumnIndex(Price_KEY_price);
        int i2 = c.getColumnIndex(Price_KEY_tat);
        int i3 = c.getColumnIndex(Price_KEY_min_charges);
        int i4 = c.getColumnIndex(Price_KEY_delivery_opt_id);
        int i5 = c.getColumnIndex(Price_KEY_service_type_id);
        int i6 = c.getColumnIndex(Price_KEY_time_slab_id);
        int i7 = c.getColumnIndex(Price_KEY_modified_date);
        int i8 = c.getColumnIndex(Price_KEY_created_date);
        int i9 = c.getColumnIndex(Price_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Price bean = new Price();
                bean.setPrice_id(c.getInt(i0));
                bean.setPrice((c.getString(i1)));
                bean.setTat((c.getString(i2)));
                bean.setMin_charges((c.getString(i3)));
                bean.setDelivery_opt_id((c.getString(i4)));
                bean.setService_type_id((c.getString(i5)));
                bean.setTime_slab_id((c.getString(i6)));
                bean.setModified_date(getClearData(c.getString(i7)));
                bean.setCreated_date(getClearData(c.getString(i8)));
                bean.setSoft_del(getClearData(c.getString(i9)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Price Read:", e);
            }
        }
        c.close();
        return list;
    }



    public Price getPrice(String delivery_opt_id, String service_type_id,String time_slab_id) {

        String sql = "select *  from price where delivery_opt_id = '"+delivery_opt_id+"' and service_type_id = '"+service_type_id+"' and time_slab_id='"+time_slab_id+"' and "+softDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        Price bean = new Price();
        int i0 = c.getColumnIndex(Price_KEY_price_id);
        int i1 = c.getColumnIndex(Price_KEY_price);
        int i2 = c.getColumnIndex(Price_KEY_tat);
        int i3 = c.getColumnIndex(Price_KEY_min_charges);
        int i4 = c.getColumnIndex(Price_KEY_delivery_opt_id);
        int i5 = c.getColumnIndex(Price_KEY_service_type_id);
        int i6 = c.getColumnIndex(Price_KEY_time_slab_id);
        int i7 = c.getColumnIndex(Price_KEY_modified_date);
        int i8 = c.getColumnIndex(Price_KEY_created_date);
        int i9 = c.getColumnIndex(Price_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setPrice_id(c.getInt(i0));
                bean.setPrice((c.getString(i1)));
                bean.setTat((c.getString(i2)));
                bean.setMin_charges((c.getString(i3)));
                bean.setDelivery_opt_id((c.getString(i4)));
                bean.setService_type_id((c.getString(i5)));
                bean.setTime_slab_id((c.getString(i6)));
                bean.setModified_date(getClearData(c.getString(i7)));
                bean.setCreated_date(getClearData(c.getString(i8)));
                bean.setSoft_del(getClearData(c.getString(i9)));


            } catch (Exception e) {
                GlobalData.printError("Price Read:", e);
            }
        }
        c.close();
        return bean;
    }

    public LinkedList<Price> getPriceList(int price_id) {
        String sql = "select *  from price where price_id=" + price_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Price> list = new LinkedList<Price>();
        int i0 = c.getColumnIndex(Price_KEY_price_id);
        int i1 = c.getColumnIndex(Price_KEY_price);
        int i2 = c.getColumnIndex(Price_KEY_tat);
        int i3 = c.getColumnIndex(Price_KEY_min_charges);
        int i4 = c.getColumnIndex(Price_KEY_delivery_opt_id);
        int i5 = c.getColumnIndex(Price_KEY_service_type_id);
        int i6 = c.getColumnIndex(Price_KEY_time_slab_id);
        int i7 = c.getColumnIndex(Price_KEY_modified_date);
        int i8 = c.getColumnIndex(Price_KEY_created_date);
        int i9 = c.getColumnIndex(Price_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Price bean = new Price();
                bean.setPrice_id(c.getInt(i0));


                bean.setPrice(getClearData(c.getString(i1)));


                bean.setTat(getClearData(c.getString(i2)));


                bean.setMin_charges(c.getString(i3));


                bean.setDelivery_opt_id((c.getString(i4)));


                bean.setService_type_id((c.getString(i5)));


                bean.setTime_slab_id((c.getString(i6)));


                bean.setModified_date(getClearData(c.getString(i7)));
                bean.setCreated_date(getClearData(c.getString(i8)));
                bean.setSoft_del(getClearData(c.getString(i9)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Price Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addPrice(Price bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert or replace into price  ( price_id,price, tat, min_charges, delivery_opt_id, service_type_id, time_slab_id, modified_date, created_date, soft_del  ) values ( " + bean.getPrice_id() + ", '" + setClearData("" + bean.getPrice()) + "',  '" + setClearData("" + bean.getTat()) + "',  '" + setClearData("" + bean.getMin_charges()) + "',  '" + setClearData("" + bean.getDelivery_opt_id()) + "',  '" + setClearData("" + bean.getService_type_id()) + "',  '" + setClearData("" + bean.getTime_slab_id()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                GlobalData.printMessage("Price SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Price ", e);
        }
    }

    public void addPriceList(LinkedList<Price> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Price bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into price  ( price_id,price, tat, min_charges, delivery_opt_id, service_type_id, time_slab_id, modified_date, created_date, soft_del  ) values ( " + bean.getPrice_id() + ", '" + setClearData("" + bean.getPrice()) + "',  '" + setClearData("" + bean.getTat()) + "',  '" + setClearData("" + bean.getMin_charges()) + "',  '" + setClearData("" + bean.getDelivery_opt_id()) + "',  '" + setClearData("" + bean.getService_type_id()) + "',  '" + setClearData("" + bean.getTime_slab_id()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                        GlobalData.printMessage("Price SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Price ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Price", e);
            }
        }
    }

    public void deletePrice() {
        String sql = "";
        try {


            sql = "delete from price  ";
            GlobalData.printMessage("Price SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Price ", e);
        }
    }

    public void deletePrice(int rowId) {
        String sql = "";
        try {


            sql = "delete from price where price_id=" + rowId;
            GlobalData.printMessage("Price SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Price ", e);
        }
    }


    public Price getPrice(int DeliveryOptionId, int ServiceTypeId, int TimeSlabId) {
        Price bean = null;
        String sql = "select * from price where " + Price_KEY_delivery_opt_id + "='" + DeliveryOptionId + "' AND " + Price_KEY_service_type_id + "='" + ServiceTypeId + "' AND " + Price_KEY_time_slab_id + "='" + TimeSlabId+"' and "+softDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int i0 = c.getColumnIndex(Price_KEY_price_id);
        int i1 = c.getColumnIndex(Price_KEY_price);
        int i2 = c.getColumnIndex(Price_KEY_tat);
        int i3 = c.getColumnIndex(Price_KEY_min_charges);
        int i4 = c.getColumnIndex(Price_KEY_delivery_opt_id);
        int i5 = c.getColumnIndex(Price_KEY_service_type_id);
        int i6 = c.getColumnIndex(Price_KEY_time_slab_id);
        int i7 = c.getColumnIndex(Price_KEY_modified_date);
        int i8 = c.getColumnIndex(Price_KEY_created_date);
        int i9 = c.getColumnIndex(Price_KEY_soft_del);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                bean = new Price();
                bean.setPrice_id(c.getInt(i0));


                bean.setPrice(c.getString(i1));


                bean.setTat(c.getString(i2));


                bean.setMin_charges(c.getString(i3));


                bean.setDelivery_opt_id((c.getString(i4)));


                bean.setService_type_id((c.getString(i5)));


                bean.setTime_slab_id((c.getString(i6)));
                bean.setModified_date(c.getString(i7));
                bean.setCreated_date(c.getString(i8));
                bean.setSoft_del(c.getString(i9));

                return bean;
            } catch (Exception e) {
                GlobalData.printError("Price Read:", e);
            }
        }
        c.close();
        return bean;
    }


//  Methods Of Security_permission

    public int getSecurity_permissionCount() {
        String sql = "select count( Security_permissionId ) as total from Security_permission 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Security_permission:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Security_permission> getSecurity_permission() {
        String sql = "select *  from Security_permission ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Security_permission> list = new LinkedList<Security_permission>();
        int i0 = c.getColumnIndex(Security_permission_KEY_elm_id);
        int i1 = c.getColumnIndex(Security_permission_KEY_group_id);
        int i2 = c.getColumnIndex(Security_permission_KEY_lastmodifed_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Security_permission bean = new Security_permission();
                bean.setElm_id(c.getInt(i0));
                bean.setGroup_id(c.getInt(i1));
                bean.setLastmodifed_date(getClearData(c.getString(i2)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Security_permission Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Security_permission> getSecurity_permissionList(int elm_id) {
        String sql = "select *  from Security_permission where elm_id=" + elm_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Security_permission> list = new LinkedList<Security_permission>();
        int i0 = c.getColumnIndex(Security_permission_KEY_elm_id);
        int i1 = c.getColumnIndex(Security_permission_KEY_group_id);
        int i2 = c.getColumnIndex(Security_permission_KEY_lastmodifed_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Security_permission bean = new Security_permission();
                bean.setElm_id(c.getInt(i0));
                bean.setGroup_id(c.getInt(i1));
                bean.setLastmodifed_date(getClearData(c.getString(i2)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Security_permission Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addSecurity_permission(Security_permission bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Security_permission  ( group_id, lastmodifed_date  ) values (  " + bean.getGroup_id() + ",  '" + setClearData(bean.getLastmodifed_date()) + "' )";
                GlobalData.printMessage("Security_permission SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Security_permission ", e);
        }
    }

    public void addSecurity_permissionList(LinkedList<Security_permission> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Security_permission bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Security_permission  ( group_id, lastmodifed_date  ) values (  " + bean.getGroup_id() + ",  '" + setClearData(bean.getLastmodifed_date()) + "' )";
                        GlobalData.printMessage("Security_permission SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Security_permission ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Security_permission", e);
            }
        }
    }

    public void deleteSecurity_permission() {
        String sql = "";
        try {


            sql = "delete from Security_permission  ";
            GlobalData.printMessage("Security_permission SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Security_permission ", e);
        }
    }

    public void deleteSecurity_permission(int rowId) {
        String sql = "";
        try {


            sql = "delete from Security_permission where elm_id=" + rowId;
            GlobalData.printMessage("Security_permission SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Security_permission ", e);
        }
    }


//  Methods Of Service_type

    public int getService_typeCount() {
        String sql = "select count( Service_typeId ) as total from Service_type 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Service_type:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Service_type> getService_type() {
        String sql = "select *  from Service_type "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Service_type> list = new LinkedList<Service_type>();
        int i0 = c.getColumnIndex(Service_type_KEY_service_type_id);
        int i1 = c.getColumnIndex(Service_type_KEY_service_text);
        int i2 = c.getColumnIndex(Service_type_KEY_status);
        int i3 = c.getColumnIndex(Service_type_KEY_default_select);
        int i4 = c.getColumnIndex(Service_type_KEY_soft_del);
        int i5 = c.getColumnIndex(Service_type_KEY_modified_date);
        int i6 = c.getColumnIndex(Service_type_KEY_created_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Service_type bean = new Service_type();
                bean.setService_type_id(getClearData(c.getString(i0)));
                bean.setService_text(getClearData(c.getString(i1)));
                bean.setStatus(getClearData(c.getString(i2)));
                bean.setDefault_select(getClearData(c.getString(i3)));
                bean.setSoft_del(getClearData(c.getString(i4)));
                bean.setModified_date(getClearData(c.getString(i5)));
                bean.setCreated_date(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Service_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Service_type> getService_typeList(int service_type_id) {
        String sql = "select *  from Service_type where service_type_id=" + service_type_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Service_type> list = new LinkedList<Service_type>();
        int i0 = c.getColumnIndex(Service_type_KEY_service_type_id);
        int i1 = c.getColumnIndex(Service_type_KEY_service_text);
        int i2 = c.getColumnIndex(Service_type_KEY_status);
        int i3 = c.getColumnIndex(Service_type_KEY_default_select);
        int i4 = c.getColumnIndex(Service_type_KEY_soft_del);
        int i5 = c.getColumnIndex(Service_type_KEY_modified_date);
        int i6 = c.getColumnIndex(Service_type_KEY_created_date);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Service_type bean = new Service_type();
                bean.setService_type_id(getClearData(c.getString(i0)));
                bean.setService_text(getClearData(c.getString(i1)));
                bean.setStatus(getClearData(c.getString(i2)));
                bean.setDefault_select(getClearData(c.getString(i3)));
                bean.setSoft_del(getClearData(c.getString(i4)));
                bean.setModified_date(getClearData(c.getString(i5)));
                bean.setCreated_date(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Service_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addService_type(Service_type bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert or replace into Service_type  ( service_type_id,service_text, status, default_select, soft_del, modified_date, created_date  ) values ( " + bean.getService_type_id() + ", '" + setClearData(bean.getService_text()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getDefault_select()) + "',  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "' )";
                GlobalData.printMessage("Service_type SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Service_type ", e);
        }
    }

    public void addService_typeList(LinkedList<Service_type> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Service_type bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into Service_type  ( service_type_id,service_text, status, default_select, soft_del, modified_date, created_date  ) values ( '" + bean.getService_type_id() + "', '" + setClearData(bean.getService_text()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getDefault_select()) + "',  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "' )";
                        GlobalData.printMessage("Service_type SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Service_type ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Service_type", e);
            }
        }
    }

    public void deleteService_type() {
        String sql = "";
        try {


            sql = "delete from Service_type  ";
            GlobalData.printMessage("Service_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Service_type ", e);
        }
    }

    public void deleteService_type(int rowId) {
        String sql = "";
        try {


            sql = "delete from Service_type where service_type_id=" + rowId;
            GlobalData.printMessage("Service_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Service_type ", e);
        }
    }


//  Methods Of Status_type

    public int getStatus_typeCount() {
        String sql = "select count( Status_typeId ) as total from Status_type 	"+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Status_type:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Status_type> getStatus_type() {
        String sql = "select *  from Status_type "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Status_type> list = new LinkedList<Status_type>();
        int i0 = c.getColumnIndex(Status_type_KEY_order_status_id);
        int i1 = c.getColumnIndex(Status_type_KEY_status_txt);
        int i2 = c.getColumnIndex(Status_type_KEY_is_active);
        int i3 = c.getColumnIndex(Status_type_KEY_created_date);
        int i4 = c.getColumnIndex(Status_type_KEY_modified_date);
        int i5 = c.getColumnIndex(Status_type_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Status_type bean = new Status_type();
                bean.setOrder_status_id(getClearData(c.getString(i0)));
                bean.setStatus_txt(getClearData(c.getString(i1)));
                bean.setIs_active(getClearData(c.getString(i2)));
                bean.setCreated_date(getClearData(c.getString(i3)));
                bean.setModified_date(getClearData(c.getString(i4)));
                bean.setSoft_del(getClearData(c.getString(i5)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Status_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public  Status_type getStatus_type(String order_status_id) {
        String sql = "select *  from Status_type where order_status_id = '" + order_status_id+"'";
        Cursor c = ourDatabase.rawQuery(sql, null);
        Status_type bean = new Status_type();
        int i0 = c.getColumnIndex(Status_type_KEY_order_status_id);
        int i1 = c.getColumnIndex(Status_type_KEY_status_txt);
        int i2 = c.getColumnIndex(Status_type_KEY_is_active);
        int i3 = c.getColumnIndex(Status_type_KEY_created_date);
        int i4 = c.getColumnIndex(Status_type_KEY_modified_date);
        int i5 = c.getColumnIndex(Status_type_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setOrder_status_id(getClearData(c.getString(i0)));
                bean.setStatus_txt(getClearData(c.getString(i1)));
                bean.setIs_active(getClearData(c.getString(i2)));
                bean.setCreated_date(getClearData(c.getString(i3)));
                bean.setModified_date(getClearData(c.getString(i4)));
                bean.setSoft_del(getClearData(c.getString(i5)));


            } catch (Exception e) {
                GlobalData.printError("Status_type Read:", e);
            }
        }
        c.close();
        return bean;
    }

    public void addStatus_type(Status_type bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert or replace into Status_type  ( order_status_id,status_txt, is_active, created_date, modified_date, soft_del  ) values ( '" + setClearData(bean.getOrder_status_id()) + "',  '" + setClearData(bean.getStatus_txt()) + "',  '" + setClearData(bean.getIs_active()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                GlobalData.printMessage("Status_type SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Status_type ", e);
        }
    }

    public void addStatus_typeList(LinkedList<Status_type> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Status_type bean = beanList.get(i);
                try {
                    if (bean != null) {

                        sql = "insert or replace into Status_type  ( order_status_id,status_txt, is_active, created_date, modified_date, soft_del  ) values ( '" + setClearData(bean.getOrder_status_id()) + "',  '" + setClearData(bean.getStatus_txt()) + "',  '" + setClearData(bean.getIs_active()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";

                        GlobalData.printMessage("Status_type SQl:" + sql);

                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Status_type ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Status_type", e);
            }
        }
    }

    public void deleteStatus_type() {
        String sql = "";
        try {


            sql = "delete from Status_type  ";
            GlobalData.printMessage("Status_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Status_type ", e);
        }
    }

    public void deleteStatus_type(int rowId) {
        String sql = "";
        try {


            sql = "delete from Status_type where order_status_id=" + rowId;
            GlobalData.printMessage("Status_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Status_type ", e);
        }
    }


//  Methods Of Sys_log

    public int getSys_logCount() {
        String sql = "select count( Sys_logId ) as total from Sys_log 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Sys_log:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Sys_log> getSys_log() {
        String sql = "select *  from Sys_log ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Sys_log> list = new LinkedList<Sys_log>();
        int i0 = c.getColumnIndex(Sys_log_KEY_logid);
        int i1 = c.getColumnIndex(Sys_log_KEY_logtime);
        int i2 = c.getColumnIndex(Sys_log_KEY_username);
        int i3 = c.getColumnIndex(Sys_log_KEY_dbname);
        int i4 = c.getColumnIndex(Sys_log_KEY_logmsg);
        int i5 = c.getColumnIndex(Sys_log_KEY_log_type);
        int i6 = c.getColumnIndex(Sys_log_KEY_user_id);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Sys_log bean = new Sys_log();
                bean.setLogid(c.getInt(i0));
                bean.setLogtime(getClearData(c.getString(i1)));
                bean.setUsername(c.getInt(i2));
                bean.setDbname(c.getInt(i3));
                bean.setLogmsg(getClearData(c.getString(i4)));
                bean.setLog_type(c.getInt(i5));
                bean.setUser_id(c.getInt(i6));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Sys_log Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Sys_log> getSys_logList(int logid) {
        String sql = "select *  from Sys_log where logid=" + logid;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Sys_log> list = new LinkedList<Sys_log>();
        int i0 = c.getColumnIndex(Sys_log_KEY_logid);
        int i1 = c.getColumnIndex(Sys_log_KEY_logtime);
        int i2 = c.getColumnIndex(Sys_log_KEY_username);
        int i3 = c.getColumnIndex(Sys_log_KEY_dbname);
        int i4 = c.getColumnIndex(Sys_log_KEY_logmsg);
        int i5 = c.getColumnIndex(Sys_log_KEY_log_type);
        int i6 = c.getColumnIndex(Sys_log_KEY_user_id);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Sys_log bean = new Sys_log();
                bean.setLogid(c.getInt(i0));
                bean.setLogtime(getClearData(c.getString(i1)));
                bean.setUsername(c.getInt(i2));
                bean.setDbname(c.getInt(i3));
                bean.setLogmsg(getClearData(c.getString(i4)));
                bean.setLog_type(c.getInt(i5));
                bean.setUser_id(c.getInt(i6));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Sys_log Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addSys_log(Sys_log bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Sys_log  ( logtime, username, dbname, logmsg, log_type, user_id  ) values (  '" + setClearData(bean.getLogtime()) + "',  " + bean.getUsername() + ",  " + bean.getDbname() + ",  '" + setClearData(bean.getLogmsg()) + "',  " + bean.getLog_type() + ",  " + bean.getUser_id() + " )";
                GlobalData.printMessage("Sys_log SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Sys_log ", e);
        }
    }

    public void addSys_logList(LinkedList<Sys_log> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Sys_log bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Sys_log  ( logtime, username, dbname, logmsg, log_type, user_id  ) values (  '" + setClearData(bean.getLogtime()) + "',  " + bean.getUsername() + ",  " + bean.getDbname() + ",  '" + setClearData(bean.getLogmsg()) + "',  " + bean.getLog_type() + ",  " + bean.getUser_id() + " )";
                        GlobalData.printMessage("Sys_log SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Sys_log ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Sys_log", e);
            }
        }
    }

    public void deleteSys_log() {
        String sql = "";
        try {


            sql = "delete from Sys_log  ";
            GlobalData.printMessage("Sys_log SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Sys_log ", e);
        }
    }

    public void deleteSys_log(int rowId) {
        String sql = "";
        try {


            sql = "delete from Sys_log where logid=" + rowId;
            GlobalData.printMessage("Sys_log SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Sys_log ", e);
        }
    }


//  Methods Of Tat_fee

    public int getTat_feeCount() {
        String sql = "select count( Tat_feeId ) as total from Tat_fee "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Tat_fee:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Tat_fee> getTat_fee() {
        String sql = "select *  from Tat_fee "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Tat_fee> list = new LinkedList<Tat_fee>();
        int i0 = c.getColumnIndex(Tat_fee_KEY_tat_fee_id);
        int i1 = c.getColumnIndex(Tat_fee_KEY_delivery_opt_id);
        int i2 = c.getColumnIndex(Tat_fee_KEY_time_slab_id);
        int i3 = c.getColumnIndex(Tat_fee_KEY_min_charges);
        int i4 = c.getColumnIndex(Tat_fee_KEY_tat);
        int i5 = c.getColumnIndex(Tat_fee_KEY_feepermin);
        int i6 = c.getColumnIndex(Tat_fee_KEY_status);
        int i7 = c.getColumnIndex(Tat_fee_KEY_created_date);
        int i8 = c.getColumnIndex(Tat_fee_KEY_modified_date);
        int i9 = c.getColumnIndex(Tat_fee_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Tat_fee bean = new Tat_fee();
                bean.setTat_fee_id(c.getInt(i0));
                bean.setDelivery_opt_id(getClearData(c.getString(i1)));
                bean.setTime_slab_id(getClearData(c.getString(i2)));
                bean.setMin_charges(getClearData(c.getString(i3)));
                bean.setTat(getClearData(c.getString(i4)));
                bean.setFeepermin(getClearData(c.getString(i5)));
                bean.setStatus(getClearData(c.getString(i6)));
                bean.setCreated_date(getClearData(c.getString(i7)));
                bean.setModified_date(getClearData(c.getString(i8)));
                bean.setSoft_del(getClearData(c.getString(i9)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Tat_fee Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Tat_fee> getTat_feeList(int tat_fee_id) {
        String sql = "select *  from Tat_fee where tat_fee_id=" + tat_fee_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Tat_fee> list = new LinkedList<Tat_fee>();
        int i0 = c.getColumnIndex(Tat_fee_KEY_tat_fee_id);
        int i1 = c.getColumnIndex(Tat_fee_KEY_delivery_opt_id);
        int i2 = c.getColumnIndex(Tat_fee_KEY_time_slab_id);
        int i3 = c.getColumnIndex(Tat_fee_KEY_min_charges);
        int i4 = c.getColumnIndex(Tat_fee_KEY_tat);
        int i5 = c.getColumnIndex(Tat_fee_KEY_feepermin);
        int i6 = c.getColumnIndex(Tat_fee_KEY_status);
        int i7 = c.getColumnIndex(Tat_fee_KEY_created_date);
        int i8 = c.getColumnIndex(Tat_fee_KEY_modified_date);
        int i9 = c.getColumnIndex(Tat_fee_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Tat_fee bean = new Tat_fee();
                bean.setTat_fee_id(c.getInt(i0));
                bean.setDelivery_opt_id(getClearData(c.getString(i1)));
                bean.setTime_slab_id(getClearData(c.getString(i2)));
                bean.setMin_charges(getClearData(c.getString(i3)));
                bean.setTat(getClearData(c.getString(i4)));
                bean.setFeepermin(getClearData(c.getString(i5)));
                bean.setStatus(getClearData(c.getString(i6)));
                bean.setCreated_date(getClearData(c.getString(i7)));
                bean.setModified_date(getClearData(c.getString(i8)));
                bean.setSoft_del(getClearData(c.getString(i9)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Tat_fee Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addTat_fee(Tat_fee bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Tat_fee  ( delivery_opt_id, time_slab_id, min_charges, tat, feepermin, status, created_date, modified_date, soft_del  ) values (  '" + setClearData(bean.getDelivery_opt_id()) + "',  '" + setClearData(bean.getTime_slab_id()) + "',  '" + setClearData(bean.getMin_charges()) + "',  '" + setClearData(bean.getTat()) + "',  '" + setClearData(bean.getFeepermin()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                GlobalData.printMessage("Tat_fee SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Tat_fee ", e);
        }
    }

    public void addTat_feeList(LinkedList<Tat_fee> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Tat_fee bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Tat_fee  ( delivery_opt_id, time_slab_id, min_charges, tat, feepermin, status, created_date, modified_date, soft_del  ) values (  '" + setClearData(bean.getDelivery_opt_id()) + "',  '" + setClearData(bean.getTime_slab_id()) + "',  '" + setClearData(bean.getMin_charges()) + "',  '" + setClearData(bean.getTat()) + "',  '" + setClearData(bean.getFeepermin()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                        GlobalData.printMessage("Tat_fee SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Tat_fee ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Tat_fee", e);
            }
        }
    }

    public void deleteTat_fee() {
        String sql = "";
        try {


            sql = "delete from Tat_fee  ";
            GlobalData.printMessage("Tat_fee SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Tat_fee ", e);
        }
    }

    public void deleteTat_fee(int rowId) {
        String sql = "";
        try {


            sql = "delete from Tat_fee where tat_fee_id=" + rowId;
            GlobalData.printMessage("Tat_fee SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Tat_fee ", e);
        }
    }


//  Methods Of Time_slab

    public int getTime_slabCount() {
        String sql = "select count( Time_slabId ) as total from Time_slab 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Time_slab:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Time_slab> getTime_slab() {
        String sql = "select *  from Time_slab "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Time_slab> list = new LinkedList<Time_slab>();
        int i0 = c.getColumnIndex(Time_slab_KEY_time_slab_id);
        int i1 = c.getColumnIndex(Time_slab_KEY_slab_from);
        int i2 = c.getColumnIndex(Time_slab_KEY_default_set);
        int i3 = c.getColumnIndex(Time_slab_KEY_status);
        int i4 = c.getColumnIndex(Time_slab_KEY_modified_date);
        int i5 = c.getColumnIndex(Time_slab_KEY_created_date);
        int i6 = c.getColumnIndex(Time_slab_KEY_slab_to);
        int i7 = c.getColumnIndex(Time_slab_KEY_soft_del);
        int i8 = c.getColumnIndex(Time_slab_KEY_is_last);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Time_slab bean = new Time_slab();
                bean.setTime_slab_id(getClearData(c.getString(i0)));
                bean.setSlab_from(getClearData(c.getString(i1)));
                bean.setDefault_set(getClearData(c.getString(i2)));
                bean.setStatus(getClearData(c.getString(i3)));
                bean.setModified_date(getClearData(c.getString(i4)));
                bean.setCreated_date(getClearData(c.getString(i5)));
                bean.setSlab_to(getClearData(c.getString(i6)));
                bean.setSoft_del(getClearData(c.getString(i7)));
                bean.setIs_last(getClearData(c.getString(i8)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Time_slab Read:", e);
            }
        }
        c.close();
        return list;
    }


    public Time_slab getTime_slab(float totalDur) {
        String sql = "select *  from Time_slab where  cast( slab_from as float) <= "+totalDur+" and cast(slab_to as float) >=  "+totalDur+"";
        Cursor c = ourDatabase.rawQuery(sql, null);
        Time_slab bean = new Time_slab();
        int i0 = c.getColumnIndex(Time_slab_KEY_time_slab_id);
        int i1 = c.getColumnIndex(Time_slab_KEY_slab_from);
        int i2 = c.getColumnIndex(Time_slab_KEY_default_set);
        int i3 = c.getColumnIndex(Time_slab_KEY_status);
        int i4 = c.getColumnIndex(Time_slab_KEY_modified_date);
        int i5 = c.getColumnIndex(Time_slab_KEY_created_date);
        int i6 = c.getColumnIndex(Time_slab_KEY_slab_to);
        int i7 = c.getColumnIndex(Time_slab_KEY_soft_del);
        int i8 = c.getColumnIndex(Time_slab_KEY_is_last);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setTime_slab_id(getClearData(c.getString(i0)));
                bean.setSlab_from(getClearData(c.getString(i1)));
                bean.setDefault_set(getClearData(c.getString(i2)));
                bean.setStatus(getClearData(c.getString(i3)));
                bean.setModified_date(getClearData(c.getString(i4)));
                bean.setCreated_date(getClearData(c.getString(i5)));
                bean.setSlab_to(getClearData(c.getString(i6)));
                bean.setSoft_del(getClearData(c.getString(i7)));


                bean.setIs_last(getClearData(c.getString(i8)));
            } catch (Exception e) {
                GlobalData.printError("Time_slab Read:", e);
            }
        }
        c.close();
        return bean;
    }

    public LinkedList<Time_slab> getTime_slabList(int time_slab_id) {
        String sql = "select *  from Time_slab where time_slab_id=" + time_slab_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Time_slab> list = new LinkedList<Time_slab>();
        int i0 = c.getColumnIndex(Time_slab_KEY_time_slab_id);
        int i1 = c.getColumnIndex(Time_slab_KEY_slab_from);
        int i2 = c.getColumnIndex(Time_slab_KEY_default_set);
        int i3 = c.getColumnIndex(Time_slab_KEY_status);
        int i4 = c.getColumnIndex(Time_slab_KEY_modified_date);
        int i5 = c.getColumnIndex(Time_slab_KEY_created_date);
        int i6 = c.getColumnIndex(Time_slab_KEY_slab_to);
        int i7 = c.getColumnIndex(Time_slab_KEY_soft_del);
        int i8 = c.getColumnIndex(Time_slab_KEY_is_last);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Time_slab bean = new Time_slab();
                bean.setTime_slab_id(getClearData(c.getString(i0)));
                bean.setSlab_from(getClearData(c.getString(i1)));
                bean.setDefault_set(getClearData(c.getString(i2)));
                bean.setStatus(getClearData(c.getString(i3)));
                bean.setModified_date(getClearData(c.getString(i4)));
                bean.setCreated_date(getClearData(c.getString(i5)));
                bean.setSlab_to(getClearData(c.getString(i6)));
                bean.setSoft_del(getClearData(c.getString(i7)));

                bean.setIs_last(getClearData(c.getString(i8)));
                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Time_slab Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addTime_slab(Time_slab bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert or replace into Time_slab  (time_slab_id, slab_from, default_set, status, modified_date, created_date, slab_to, soft_del ,is_last ) values ( '" + bean.getTime_slab_id() + "', '" + setClearData(bean.getSlab_from()) + "',  '" + setClearData(bean.getDefault_set()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSlab_to()) + "',  '" + setClearData(bean.getSoft_del()) + "', '" + setClearData(bean.getIs_last()) + "' )";
                GlobalData.printMessage("Time_slab SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Time_slab ", e);
        }
    }

    public void addTime_slabList(LinkedList<Time_slab> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Time_slab bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into Time_slab  (time_slab_id, slab_from, default_set, status, modified_date, created_date, slab_to, soft_del ,is_last ) values ( '" + bean.getTime_slab_id() + "', '" + setClearData(bean.getSlab_from()) + "',  '" + setClearData(bean.getDefault_set()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getSlab_to()) + "',  '" + setClearData(bean.getSoft_del()) + "', '" + setClearData(bean.getIs_last()) + "' )";
                        GlobalData.printMessage("Time_slab SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Time_slab ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Time_slab", e);
            }
        }
    }

    public void deleteTime_slab() {
        String sql = "";
        try {


            sql = "delete from Time_slab  ";
            GlobalData.printMessage("Time_slab SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Time_slab ", e);
        }
    }

    public void deleteTime_slab(int rowId) {
        String sql = "";
        try {


            sql = "delete from Time_slab where time_slab_id=" + rowId;
            GlobalData.printMessage("Time_slab SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Time_slab ", e);
        }
    }


//  Methods Of Transcription_type

    public int getTranscription_typeCount() {
        String sql = "select count( Transcription_typeId ) as total from Transcription_type 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Transcription_type:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Transcription_type> getTranscription_type() {
        String sql = "select *  from Transcription_type "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Transcription_type> list = new LinkedList<Transcription_type>();
        int i0 = c.getColumnIndex(Transcription_type_KEY_trans_type_id);
        int i1 = c.getColumnIndex(Transcription_type_KEY_trans_type_txt);
        int i2 = c.getColumnIndex(Transcription_type_KEY_default_set);
        int i3 = c.getColumnIndex(Transcription_type_KEY_soft_del);
        int i4 = c.getColumnIndex(Transcription_type_KEY_modified_date);
        int i5 = c.getColumnIndex(Transcription_type_KEY_created_date);
        int i6 = c.getColumnIndex(Transcription_type_KEY_is_active);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Transcription_type bean = new Transcription_type();
                bean.setTrans_type_id(getClearData(c.getString(i0)));
                bean.setTrans_type_txt(getClearData(c.getString(i1)));
                bean.setDefault_set(getClearData(c.getString(i2)));
                bean.setSoft_del(getClearData(c.getString(i3)));
                bean.setModified_date(getClearData(c.getString(i4)));
                bean.setCreated_date(getClearData(c.getString(i5)));
                bean.setIs_active(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Transcription_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Transcription_type> getTranscription_typeList(int trans_type_id) {
        String sql = "select *  from Transcription_type where trans_type_id=" + trans_type_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Transcription_type> list = new LinkedList<Transcription_type>();
        int i0 = c.getColumnIndex(Transcription_type_KEY_trans_type_id);
        int i1 = c.getColumnIndex(Transcription_type_KEY_trans_type_txt);
        int i2 = c.getColumnIndex(Transcription_type_KEY_default_set);
        int i3 = c.getColumnIndex(Transcription_type_KEY_soft_del);
        int i4 = c.getColumnIndex(Transcription_type_KEY_modified_date);
        int i5 = c.getColumnIndex(Transcription_type_KEY_created_date);
        int i6 = c.getColumnIndex(Transcription_type_KEY_is_active);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Transcription_type bean = new Transcription_type();
                bean.setTrans_type_id(getClearData(c.getString(i0)));
                bean.setTrans_type_txt(getClearData(c.getString(i1)));
                bean.setDefault_set(getClearData(c.getString(i2)));
                bean.setSoft_del(getClearData(c.getString(i3)));
                bean.setModified_date(getClearData(c.getString(i4)));
                bean.setCreated_date(getClearData(c.getString(i5)));
                bean.setIs_active(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Transcription_type Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addTranscription_type(Transcription_type bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert or replace into Transcription_type  ( trans_type_id,trans_type_txt, default_set, soft_del, modified_date, created_date, is_active  ) values ( " + bean.getTrans_type_id() + ",  '" + setClearData(bean.getTrans_type_txt()) + "',  '" + setClearData(bean.getDefault_set()) + "',  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getIs_active()) + "' )";
                GlobalData.printMessage("Transcription_type SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Transcription_type ", e);
        }
    }

    public void addTranscription_typeList(LinkedList<Transcription_type> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Transcription_type bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into Transcription_type  ( trans_type_id,trans_type_txt, default_set, soft_del, modified_date, created_date, is_active  ) values ( '" + bean.getTrans_type_id() + "',  '" + setClearData(bean.getTrans_type_txt()) + "',  '" + setClearData(bean.getDefault_set()) + "',  '" + setClearData(bean.getSoft_del()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getIs_active()) + "' )";
                        GlobalData.printMessage("Transcription_type SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Transcription_type ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Transcription_type", e);
            }
        }
    }

    public void deleteTranscription_type() {
        String sql = "";
        try {


            sql = "delete from Transcription_type  ";
            GlobalData.printMessage("Transcription_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Transcription_type ", e);
        }
    }

    public void deleteTranscription_type(int rowId) {
        String sql = "";
        try {


            sql = "delete from Transcription_type where trans_type_id=" + rowId;
            GlobalData.printMessage("Transcription_type SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Transcription_type ", e);
        }
    }


//  Methods Of Users

    public int getUsersCount() {
        String sql = "select count( UsersId ) as total from Users 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Users:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Users> getUsers() {
        String sql = "select *  from Users ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Users> list = new LinkedList<Users>();
        int i0 = c.getColumnIndex(Users_KEY_user_id);
        int i1 = c.getColumnIndex(Users_KEY_membership_id);
        int i2 = c.getColumnIndex(Users_KEY_location_id);
        int i3 = c.getColumnIndex(Users_KEY_email);
        int i4 = c.getColumnIndex(Users_KEY_first_name);
        int i5 = c.getColumnIndex(Users_KEY_first_name_fn);
        int i6 = c.getColumnIndex(Users_KEY_last_name);
        int i7 = c.getColumnIndex(Users_KEY_last_name_fn);
        int i8 = c.getColumnIndex(Users_KEY_password);
        int i9 = c.getColumnIndex(Users_KEY_join_date);
        int i10 = c.getColumnIndex(Users_KEY_photo);
        int i11 = c.getColumnIndex(Users_KEY_gender);
        int i12 = c.getColumnIndex(Users_KEY_auth_key);
        int i13 = c.getColumnIndex(Users_KEY_reg_date);
        int i14 = c.getColumnIndex(Users_KEY_modified_date);
        int i15 = c.getColumnIndex(Users_KEY_dob);
        int i16 = c.getColumnIndex(Users_KEY_designation);
        int i17 = c.getColumnIndex(Users_KEY_department);
        int i18 = c.getColumnIndex(Users_KEY_email_2);
        int i19 = c.getColumnIndex(Users_KEY_mobile_no);
        int i20 = c.getColumnIndex(Users_KEY_mobile_no_2);
        int i21 = c.getColumnIndex(Users_KEY_extension);
        int i22 = c.getColumnIndex(Users_KEY_landline);
        int i23 = c.getColumnIndex(Users_KEY_noti_setting_type);
        int i24 = c.getColumnIndex(Users_KEY_upload_setting_type);
        int i25 = c.getColumnIndex(Users_KEY_username);
        int i26 = c.getColumnIndex(Users_KEY_imei);
        int i27 = c.getColumnIndex(Users_KEY_is_email_verified);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Users bean = new Users();
                bean.setUser_id(c.getInt(i0));
                bean.setMembership_id(getClearData(c.getString(i1)));
                bean.setLocation_id(c.getInt(i2));
                bean.setEmail(getClearData(c.getString(i3)));
                bean.setFirst_name(getClearData(c.getString(i4)));
                bean.setFirst_name_fn(getClearData(c.getString(i5)));
                bean.setLast_name(getClearData(c.getString(i6)));
                bean.setLast_name_fn(getClearData(c.getString(i7)));
                bean.setPassword(getClearData(c.getString(i8)));
                bean.setJoin_date(getClearData(c.getString(i9)));
                bean.setPhoto(getClearData(c.getString(i10)));
                bean.setGender(getClearData(c.getString(i11)));
                bean.setAuth_key(getClearData(c.getString(i12)));
                bean.setReg_date(getClearData(c.getString(i13)));
                bean.setModified_date(getClearData(c.getString(i14)));
                bean.setDob(getClearData(c.getString(i15)));
                bean.setDesignation(getClearData(c.getString(i16)));
                bean.setDepartment(getClearData(c.getString(i17)));
                bean.setEmail_2(getClearData(c.getString(i18)));
                bean.setMobile_no(getClearData(c.getString(i19)));
                bean.setMobile_no_2(getClearData(c.getString(i20)));
                bean.setExtension(getClearData(c.getString(i21)));
                bean.setLandline(getClearData(c.getString(i22)));
                bean.setNoti_setting_type(getClearData(c.getString(i23)));
                bean.setUpload_setting_type(getClearData(c.getString(i24)));
                bean.setUsername(getClearData(c.getString(i25)));
                bean.setImei(getClearData(c.getString(i26)));
                bean.setIs_email_verified(getClearData(c.getString(i27)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Users Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Users> getUsersList(int user_id) {
        String sql = "select *  from Users where user_id=" + user_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Users> list = new LinkedList<Users>();
        int i0 = c.getColumnIndex(Users_KEY_user_id);
        int i1 = c.getColumnIndex(Users_KEY_membership_id);
        int i2 = c.getColumnIndex(Users_KEY_location_id);
        int i3 = c.getColumnIndex(Users_KEY_email);
        int i4 = c.getColumnIndex(Users_KEY_first_name);
        int i5 = c.getColumnIndex(Users_KEY_first_name_fn);
        int i6 = c.getColumnIndex(Users_KEY_last_name);
        int i7 = c.getColumnIndex(Users_KEY_last_name_fn);
        int i8 = c.getColumnIndex(Users_KEY_password);
        int i9 = c.getColumnIndex(Users_KEY_join_date);
        int i10 = c.getColumnIndex(Users_KEY_photo);
        int i11 = c.getColumnIndex(Users_KEY_gender);
        int i12 = c.getColumnIndex(Users_KEY_auth_key);
        int i13 = c.getColumnIndex(Users_KEY_reg_date);
        int i14 = c.getColumnIndex(Users_KEY_modified_date);
        int i15 = c.getColumnIndex(Users_KEY_dob);
        int i16 = c.getColumnIndex(Users_KEY_designation);
        int i17 = c.getColumnIndex(Users_KEY_department);
        int i18 = c.getColumnIndex(Users_KEY_email_2);
        int i19 = c.getColumnIndex(Users_KEY_mobile_no);
        int i20 = c.getColumnIndex(Users_KEY_mobile_no_2);
        int i21 = c.getColumnIndex(Users_KEY_extension);
        int i22 = c.getColumnIndex(Users_KEY_landline);
        int i23 = c.getColumnIndex(Users_KEY_noti_setting_type);
        int i24 = c.getColumnIndex(Users_KEY_upload_setting_type);
        int i25 = c.getColumnIndex(Users_KEY_username);
        int i26 = c.getColumnIndex(Users_KEY_imei);
        int i27 = c.getColumnIndex(Users_KEY_is_email_verified);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Users bean = new Users();
                bean.setUser_id(c.getInt(i0));
                bean.setMembership_id(getClearData(c.getString(i1)));
                bean.setLocation_id(c.getInt(i2));
                bean.setEmail(getClearData(c.getString(i3)));
                bean.setFirst_name(getClearData(c.getString(i4)));
                bean.setFirst_name_fn(getClearData(c.getString(i5)));
                bean.setLast_name(getClearData(c.getString(i6)));
                bean.setLast_name_fn(getClearData(c.getString(i7)));
                bean.setPassword(getClearData(c.getString(i8)));
                bean.setJoin_date(getClearData(c.getString(i9)));
                bean.setPhoto(getClearData(c.getString(i10)));
                bean.setGender(getClearData(c.getString(i11)));
                bean.setAuth_key(getClearData(c.getString(i12)));
                bean.setReg_date(getClearData(c.getString(i13)));
                bean.setModified_date(getClearData(c.getString(i14)));
                bean.setDob(getClearData(c.getString(i15)));
                bean.setDesignation(getClearData(c.getString(i16)));
                bean.setDepartment(getClearData(c.getString(i17)));
                bean.setEmail_2(getClearData(c.getString(i18)));
                bean.setMobile_no(getClearData(c.getString(i19)));
                bean.setMobile_no_2(getClearData(c.getString(i20)));
                bean.setExtension(getClearData(c.getString(i21)));
                bean.setLandline(getClearData(c.getString(i22)));
                bean.setNoti_setting_type(getClearData(c.getString(i23)));
                bean.setUpload_setting_type(getClearData(c.getString(i24)));
                bean.setUsername(getClearData(c.getString(i25)));
                bean.setImei(getClearData(c.getString(i26)));
                bean.setIs_email_verified(getClearData(c.getString(i27)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Users Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addUsers(Users bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert or replace into Users  (user_id, membership_id, location_id, email, first_name, first_name_fn, last_name, last_name_fn, password, join_date, photo, gender, auth_key, reg_date, modified_date, dob, designation, department, email_2, mobile_no, mobile_no_2, extension, landline, noti_setting_type, upload_setting_type, username, imei, is_email_verified  ) values (  "+bean.getUser_id()+",'" + setClearData(bean.getMembership_id()) + "',  " + bean.getLocation_id() + ",  '" + setClearData(bean.getEmail()) + "',  '" + setClearData(bean.getFirst_name()) + "',  '" + setClearData(bean.getFirst_name_fn()) + "',  '" + setClearData(bean.getLast_name()) + "',  '" + setClearData(bean.getLast_name_fn()) + "',  '" + setClearData(bean.getPassword()) + "',  '" + setClearData(bean.getJoin_date()) + "',  '" + setClearData(bean.getPhoto()) + "',  '" + setClearData(bean.getGender()) + "',  '" + setClearData(bean.getAuth_key()) + "',  '" + setClearData(bean.getReg_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getDob()) + "',  '" + setClearData(bean.getDesignation()) + "',  '" + setClearData(bean.getDepartment()) + "',  '" + setClearData(bean.getEmail_2()) + "',  '" + setClearData(bean.getMobile_no()) + "',  '" + setClearData(bean.getMobile_no_2()) + "',  '" + setClearData(bean.getExtension()) + "',  '" + setClearData(bean.getLandline()) + "',  '" + setClearData(bean.getNoti_setting_type()) + "',  '" + setClearData(bean.getUpload_setting_type()) + "',  '" + setClearData(bean.getUsername()) + "',  '" + setClearData(bean.getImei()) + "',  '" + setClearData(bean.getIs_email_verified()) + "' )";
                GlobalData.printMessage("Users SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Users ", e);
        }
    }

    public void addUsersList(LinkedList<Users> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Users bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into Users  (user_id, membership_id, location_id, email, first_name, first_name_fn, last_name, last_name_fn, password, join_date, photo, gender, auth_key, reg_date, modified_date, dob, designation, department, email_2, mobile_no, mobile_no_2, extension, landline, noti_setting_type, upload_setting_type, username, imei, is_email_verified  ) values (  "+bean.getUser_id()+",'" + setClearData(bean.getMembership_id()) + "',  " + bean.getLocation_id() + ",  '" + setClearData(bean.getEmail()) + "',  '" + setClearData(bean.getFirst_name()) + "',  '" + setClearData(bean.getFirst_name_fn()) + "',  '" + setClearData(bean.getLast_name()) + "',  '" + setClearData(bean.getLast_name_fn()) + "',  '" + setClearData(bean.getPassword()) + "',  '" + setClearData(bean.getJoin_date()) + "',  '" + setClearData(bean.getPhoto()) + "',  '" + setClearData(bean.getGender()) + "',  '" + setClearData(bean.getAuth_key()) + "',  '" + setClearData(bean.getReg_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getDob()) + "',  '" + setClearData(bean.getDesignation()) + "',  '" + setClearData(bean.getDepartment()) + "',  '" + setClearData(bean.getEmail_2()) + "',  '" + setClearData(bean.getMobile_no()) + "',  '" + setClearData(bean.getMobile_no_2()) + "',  '" + setClearData(bean.getExtension()) + "',  '" + setClearData(bean.getLandline()) + "',  '" + setClearData(bean.getNoti_setting_type()) + "',  '" + setClearData(bean.getUpload_setting_type()) + "',  '" + setClearData(bean.getUsername()) + "',  '" + setClearData(bean.getImei()) + "',  '" + setClearData(bean.getIs_email_verified()) + "' )";
                        GlobalData.printMessage("Users SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Users ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Users", e);
            }
        }
    }

    public void deleteUsers() {
        String sql = "";
        try {


            sql = "delete from Users  ";
            GlobalData.printMessage("Users SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Users ", e);
        }
    }

    public void deleteUsers(int rowId) {
        String sql = "";
        try {


            sql = "delete from Users where user_id=" + rowId;
            GlobalData.printMessage("Users SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Users ", e);
        }
    }


//  Methods Of User_device

    public int getUser_deviceCount() {
        String sql = "select count( User_deviceId ) as total from User_device 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  User_device:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<User_device> getUser_device() {
        String sql = "select *  from User_device ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<User_device> list = new LinkedList<User_device>();
        int i0 = c.getColumnIndex(User_device_KEY_device_id);
        int i1 = c.getColumnIndex(User_device_KEY_user_id);
        int i2 = c.getColumnIndex(User_device_KEY_membership_id);
        int i3 = c.getColumnIndex(User_device_KEY_device_type);
        int i4 = c.getColumnIndex(User_device_KEY_imei);
        int i5 = c.getColumnIndex(User_device_KEY_api_key);
        int i6 = c.getColumnIndex(User_device_KEY_user_type);
        int i7 = c.getColumnIndex(User_device_KEY_device_name);
        int i8 = c.getColumnIndex(User_device_KEY_device_os);
        int i9 = c.getColumnIndex(User_device_KEY_systemdate);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                User_device bean = new User_device();
                bean.setDevice_id(c.getInt(i0));
                bean.setUser_id(c.getInt(i1));
                bean.setMembership_id(getClearData(c.getString(i2)));
                bean.setDevice_type(getClearData(c.getString(i3)));
                bean.setImei(getClearData(c.getString(i4)));
                bean.setApi_key(getClearData(c.getString(i5)));
                bean.setUser_type(getClearData(c.getString(i6)));
                bean.setDevice_name(getClearData(c.getString(i7)));
                bean.setDevice_os(getClearData(c.getString(i8)));
                bean.setSystemdate(getClearData(c.getString(i9)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("User_device Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<User_device> getUser_deviceList(int device_id) {
        String sql = "select *  from User_device where device_id=" + device_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<User_device> list = new LinkedList<User_device>();
        int i0 = c.getColumnIndex(User_device_KEY_device_id);
        int i1 = c.getColumnIndex(User_device_KEY_user_id);
        int i2 = c.getColumnIndex(User_device_KEY_membership_id);
        int i3 = c.getColumnIndex(User_device_KEY_device_type);
        int i4 = c.getColumnIndex(User_device_KEY_imei);
        int i5 = c.getColumnIndex(User_device_KEY_api_key);
        int i6 = c.getColumnIndex(User_device_KEY_user_type);
        int i7 = c.getColumnIndex(User_device_KEY_device_name);
        int i8 = c.getColumnIndex(User_device_KEY_device_os);
        int i9 = c.getColumnIndex(User_device_KEY_systemdate);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                User_device bean = new User_device();
                bean.setDevice_id(c.getInt(i0));
                bean.setUser_id(c.getInt(i1));
                bean.setMembership_id(getClearData(c.getString(i2)));
                bean.setDevice_type(getClearData(c.getString(i3)));
                bean.setImei(getClearData(c.getString(i4)));
                bean.setApi_key(getClearData(c.getString(i5)));
                bean.setUser_type(getClearData(c.getString(i6)));
                bean.setDevice_name(getClearData(c.getString(i7)));
                bean.setDevice_os(getClearData(c.getString(i8)));
                bean.setSystemdate(getClearData(c.getString(i9)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("User_device Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addUser_device(User_device bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into User_device  ( user_id, membership_id, device_type, imei, api_key, user_type, device_name, device_os, systemdate  ) values (  " + bean.getUser_id() + ",  '" + setClearData(bean.getMembership_id()) + "',  '" + setClearData(bean.getDevice_type()) + "',  '" + setClearData(bean.getImei()) + "',  '" + setClearData(bean.getApi_key()) + "',  '" + setClearData(bean.getUser_type()) + "',  '" + setClearData(bean.getDevice_name()) + "',  '" + setClearData(bean.getDevice_os()) + "',  '" + setClearData(bean.getSystemdate()) + "' )";
                GlobalData.printMessage("User_device SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add User_device ", e);
        }
    }

    public void addUser_deviceList(LinkedList<User_device> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                User_device bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into User_device  ( user_id, membership_id, device_type, imei, api_key, user_type, device_name, device_os, systemdate  ) values (  " + bean.getUser_id() + ",  '" + setClearData(bean.getMembership_id()) + "',  '" + setClearData(bean.getDevice_type()) + "',  '" + setClearData(bean.getImei()) + "',  '" + setClearData(bean.getApi_key()) + "',  '" + setClearData(bean.getUser_type()) + "',  '" + setClearData(bean.getDevice_name()) + "',  '" + setClearData(bean.getDevice_os()) + "',  '" + setClearData(bean.getSystemdate()) + "' )";
                        GlobalData.printMessage("User_device SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add User_device ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("User_device", e);
            }
        }
    }

    public void deleteUser_device() {
        String sql = "";
        try {


            sql = "delete from User_device  ";
            GlobalData.printMessage("User_device SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete User_device ", e);
        }
    }

    public void deleteUser_device(int rowId) {
        String sql = "";
        try {


            sql = "delete from User_device where device_id=" + rowId;
            GlobalData.printMessage("User_device SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete User_device ", e);
        }
    }


    //methods for vasrate


    public LinkedList<VAS_Rate> getVASPrice(String timeSlabId, String VASId ) {

        String sql = "select *  from vas_rate where " + VAS_KEY_TimeSlabId + "='" + timeSlabId + "' AND " + VAS_KEY_VASId + "='" + VASId + "' and "+softDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<VAS_Rate> list = new LinkedList<VAS_Rate>();
        int i0 = c.getColumnIndex(VAS_KEY_Vas_Rate);
        int i1 = c.getColumnIndex(VAS_KEY_TimeSlabId);
        int i2 = c.getColumnIndex(VAS_KEY_VASId);
        int i3 = c.getColumnIndex(VAS_KEY_TAT);
        int i4 = c.getColumnIndex(VAS_KEY_Price);
        int i5 = c.getColumnIndex(VAS_KEY_Min_Charges);
        int i6 = c.getColumnIndex(VAS_KEY_Modified_date);
        int i7 = c.getColumnIndex(VAS_KEY_Created_date);
        int i8 = c.getColumnIndex(VAS_KEY_Soft_Del);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                VAS_Rate bean = new VAS_Rate();
                bean.setVas_rate(c.getString(i0));
                bean.setTime_slab_id(c.getString(i1));
                bean.setVas_id(c.getString(i2));
                bean.setTat(c.getString(i3));
                bean.setPrice(c.getString(i4));
                bean.setMin_charges(c.getString(i5));
                bean.setModified_date(c.getString(i6));
                bean.setCreated_date(c.getString(i7));
                bean.setSoft_del(c.getString(i8));
                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Price Read:", e);
            }
        }
        c.close();
        return list;
    }

    public VAS_Rate getVASPrice(String timeSlabId, String VASId,String service_type_id) {

        String sql = "select *  from vas_rate where " + VAS_KEY_TimeSlabId + "='" + timeSlabId + "' AND " + VAS_KEY_VASId + "='" + VASId + "' and service_type_id = '"+service_type_id+"' and "+softDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        VAS_Rate bean = new VAS_Rate();
        int i0 = c.getColumnIndex(VAS_KEY_Vas_Rate);
        int i1 = c.getColumnIndex(VAS_KEY_TimeSlabId);
        int i2 = c.getColumnIndex(VAS_KEY_VASId);
        int i3 = c.getColumnIndex(VAS_KEY_TAT);
        int i4 = c.getColumnIndex(VAS_KEY_Price);
        int i5 = c.getColumnIndex(VAS_KEY_Min_Charges);
        int i6 = c.getColumnIndex(VAS_KEY_Modified_date);
        int i7 = c.getColumnIndex(VAS_KEY_Created_date);
        int i8 = c.getColumnIndex(VAS_KEY_Soft_Del);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setVas_rate(c.getString(i0));
                bean.setTime_slab_id(c.getString(i1));
                bean.setVas_id(c.getString(i2));
                bean.setTat(c.getString(i3));
                bean.setPrice(c.getString(i4));
                bean.setMin_charges(c.getString(i5));
                bean.setModified_date(c.getString(i6));
                bean.setCreated_date(c.getString(i7));
                bean.setSoft_del(c.getString(i8));

            } catch (Exception e) {
                GlobalData.printError("Price Read:", e);
            }
        }
        c.close();
        return bean;
    }


//  Methods Of Vas

    public int getVasCount() {
        String sql = "select count( VasId ) as total from Vas 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Vas:" + total);
                } catch (Exception e) {
                    total = 0;
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = 0;
        }
        return total;
    }

    public LinkedList<Vas> getVas() {
        String sql = "select *  from Vas "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Vas> list = new LinkedList<Vas>();
        int i0 = c.getColumnIndex(Vas_KEY_vas_id);
        int i1 = c.getColumnIndex(Vas_KEY_vas_text);
        int i2 = c.getColumnIndex(Vas_KEY_default_set);
        int i3 = c.getColumnIndex(Vas_KEY_service_flag);
        int i4 = c.getColumnIndex(Vas_KEY_transcription_flag);
        int i5 = c.getColumnIndex(Vas_KEY_timeslab_flag);
        int i6 = c.getColumnIndex(Vas_KEY_status);
        int i7 = c.getColumnIndex(Vas_KEY_created_date);
        int i8 = c.getColumnIndex(Vas_KEY_modified_date);
        int i9 = c.getColumnIndex(Vas_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Vas bean = new Vas();
                bean.setVas_id(getClearData(c.getString(i0)));
                bean.setVas_text(getClearData(c.getString(i1)));
                bean.setDefault_set(getClearData(c.getString(i2)));
                bean.setService_flag(getClearData(c.getString(i3)));
                bean.setTranscription_flag(getClearData(c.getString(i4)));
                bean.setTimeslab_flag(getClearData(c.getString(i5)));
                bean.setStatus(getClearData(c.getString(i6)));
                bean.setCreated_date(getClearData(c.getString(i7)));
                bean.setModified_date(getClearData(c.getString(i8)));
                bean.setSoft_del(getClearData(c.getString(i9)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Vas Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Vas> getVasList(int vas_id) {
        String sql = "select *  from Vas where vas_id=" + vas_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Vas> list = new LinkedList<Vas>();
        int i0 = c.getColumnIndex(Vas_KEY_vas_id);
        int i1 = c.getColumnIndex(Vas_KEY_vas_text);
        int i2 = c.getColumnIndex(Vas_KEY_default_set);
        int i3 = c.getColumnIndex(Vas_KEY_service_flag);
        int i4 = c.getColumnIndex(Vas_KEY_transcription_flag);
        int i5 = c.getColumnIndex(Vas_KEY_timeslab_flag);
        int i6 = c.getColumnIndex(Vas_KEY_status);
        int i7 = c.getColumnIndex(Vas_KEY_created_date);
        int i8 = c.getColumnIndex(Vas_KEY_modified_date);
        int i9 = c.getColumnIndex(Vas_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Vas bean = new Vas();
                bean.setVas_id(getClearData(c.getString(i0)));
                bean.setVas_text(getClearData(c.getString(i1)));
                bean.setDefault_set(getClearData(c.getString(i2)));
                bean.setService_flag(getClearData(c.getString(i3)));
                bean.setTranscription_flag(getClearData(c.getString(i4)));
                bean.setTimeslab_flag(getClearData(c.getString(i5)));
                bean.setStatus(getClearData(c.getString(i6)));
                bean.setCreated_date(getClearData(c.getString(i7)));
                bean.setModified_date(getClearData(c.getString(i8)));
                bean.setSoft_del(getClearData(c.getString(i9)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Vas Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addVas(Vas bean) {
        String sql = "";
        try {
            if (bean != null) {
                sql = "insert or replace into Vas  ( vas_id,vas_text, default_set, service_flag, transcription_flag, timeslab_flag, status, created_date, modified_date, soft_del  ) values ( '" + bean.getVas_id() + "', '" + setClearData(bean.getVas_text()) + "',  '" + setClearData(bean.getDefault_set()) + "',  '" + setClearData(bean.getService_flag()) + "',  '" + setClearData(bean.getTranscription_flag()) + "',  '" + setClearData(bean.getTimeslab_flag()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                GlobalData.printMessage("Vas SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Vas ", e);
        }
    }

    public void addVasList(LinkedList<Vas> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Vas bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into Vas  ( vas_id,vas_text, default_set, service_flag, transcription_flag, timeslab_flag, status, created_date, modified_date, soft_del  ) values ('" + bean.getVas_id() + "','" + setClearData(bean.getVas_text()) + "',  '" + setClearData(bean.getDefault_set()) + "',  '" + setClearData(bean.getService_flag()) + "',  '" + setClearData(bean.getTranscription_flag()) + "',  '" + setClearData(bean.getTimeslab_flag()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getSoft_del()) + "' )";
                        GlobalData.printMessage("Vas SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Vas ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Vas", e);
            }
        }
    }

    public void deleteVas() {
        String sql = "";
        try {


            sql = "delete from Vas  ";
            GlobalData.printMessage("Vas SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Vas ", e);
        }
    }

    public void deleteVas(int rowId) {
        String sql = "";
        try {


            sql = "delete from Vas where vas_id=" + rowId;
            GlobalData.printMessage("Vas SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Vas ", e);
        }
    }


    //Last Modified Date


    /// Check the last modified Date
    /// Get Last ModifiedDate
    public String getLastModDate(String tableName) {
        String sql = "select MAX( modified_date ) as total from " + tableName;

        String total = "";
        try {

            Cursor c = ourDatabase.rawQuery(sql, null);
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getString(iCId);
                    GlobalData.printMessage("Total  :" + total);
                } catch (Exception e) {
                    total = "";
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + tableName + " \n" + e);
            GlobalData.printError(e, "");
            total = "";
        }

        if (total == "null" || total == null) {
            total = "";
        }

        return total;
    }

    public String getLastModDateType2(String tableName) {
        String sql = "select MAX( modified_date ) as total from " + tableName;
        Cursor c = ourDatabase.rawQuery(sql, null);
        String total = "";
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getString(iCId);
                    GlobalData.printMessage("Total  Hospital:" + total);
                } catch (Exception e) {
                    total = "";
                }
            }
            c.close();
        } catch (Exception e) {
            GlobalData.printMessage("Error:" + e);
            GlobalData.printError(e, "");
            total = "";
        }

        if (total == "null" || total == null) {
            total = "";
        }

        return total;
    }

    //methods of timestamp calculation
    public LinkedList<Timestamps_cal> getTimeStampCalculation(String serviceTypid, String deliveryOptionId, String timeStampId) {
        String sql = "select *  from timestamps_cal where " + TimeStampCalculation_KEY_ServiceTypeId + "='" + serviceTypid + "' AND " + TimeStampCalculation_KEY_DeliveryOptionId + "='" + deliveryOptionId + "' AND " + TimeStampCalculation_KEY_timeStampId + "='" + timeStampId + "' and "+softDeleChk;

        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Timestamps_cal> list = new LinkedList<Timestamps_cal>();
        int i0 = c.getColumnIndex(TimeStampCalculation_KEY_timeStampCalculationId);
        int i1 = c.getColumnIndex(TimeStampCalculation_KEY_ServiceTypeId);
        int i2 = c.getColumnIndex(TimeStampCalculation_KEY_PercentageValue);
        int i3 = c.getColumnIndex(TimeStampCalculation_KEY_DeliveryOptionId);
        int i4 = c.getColumnIndex(TimeStampCalculation_KEY_timeStampId);
        int i5 = c.getColumnIndex(TimeStampCalculation_KEY_modified_date);
        int i6 = c.getColumnIndex(TimeStampCalculation_KEY_created_date);
        int i7 = c.getColumnIndex(TimeStampCalculation_KEY_soft_del);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Timestamps_cal bean = new Timestamps_cal();
                bean.setTimestamp_calculation_id(c.getInt(i0));
                bean.setService_type_id(c.getString(i1));
                bean.setPercentage_value(c.getString(i2));
                bean.setDelivery_opt_id(c.getString(i3));
                bean.setTimestamp_id(c.getString(i4));
                bean.setModified_date(c.getString(i5));
                bean.setCreated_date(c.getString(i6));
                bean.setSoft_del(c.getString(i7));
                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("TimeStampCal Read:", e);
            }
        }
        c.close();
        return list;
    }

    public Timestamps_cal getTimeStampCalculationData(String serviceTypid, String deliveryOptionId, String timeStampId) {
        String sql = "select *  from timestamps_cal where " + TimeStampCalculation_KEY_ServiceTypeId + "='" + serviceTypid + "' AND " + TimeStampCalculation_KEY_DeliveryOptionId + "='" + deliveryOptionId + "' AND " + TimeStampCalculation_KEY_timeStampId + "='" + timeStampId + "' and "+softDeleChk;

        Cursor c = ourDatabase.rawQuery(sql, null);
        Timestamps_cal bean = new Timestamps_cal();
        int i0 = c.getColumnIndex(TimeStampCalculation_KEY_timeStampCalculationId);
        int i1 = c.getColumnIndex(TimeStampCalculation_KEY_ServiceTypeId);
        int i2 = c.getColumnIndex(TimeStampCalculation_KEY_PercentageValue);
        int i3 = c.getColumnIndex(TimeStampCalculation_KEY_DeliveryOptionId);
        int i4 = c.getColumnIndex(TimeStampCalculation_KEY_timeStampId);
        int i5 = c.getColumnIndex(TimeStampCalculation_KEY_modified_date);
        int i6 = c.getColumnIndex(TimeStampCalculation_KEY_created_date);
        int i7 = c.getColumnIndex(TimeStampCalculation_KEY_soft_del);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setTimestamp_calculation_id(c.getInt(i0));
                bean.setService_type_id(c.getString(i1));
                bean.setPercentage_value(c.getString(i2));
                bean.setDelivery_opt_id(c.getString(i3));
                bean.setTimestamp_id(c.getString(i4));
                bean.setModified_date(c.getString(i5));
                bean.setCreated_date(c.getString(i6));
                bean.setSoft_del(c.getString(i7));

            } catch (Exception e) {
                GlobalData.printError("TimeStampCal Read:", e);
            }
        }
        c.close();
        return bean;
    }


    //TimeStamp
    public void addTime_stamp(LinkedList<TimeStamb> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                TimeStamb bean = beanList.get(i);
                try {
                    if (bean != null) {
                        sql = "insert or replace into timestamb  (timestamp_id ,  timestamp_txt ,  default_set ,  status ,  modified_date ,  created_date , soft_del , premium_type_id ) values ( '" + bean.getTimestamp_id() + "', '" + setClearData(bean.getTimestamp_txt()) + "',  '" + setClearData(bean.getDefault_set()) + "',  '" + setClearData(bean.getStatus()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "', '" + setClearData(bean.getSoft_del()) + "','"+bean.getPremium_type_id()+"' )";
                        GlobalData.printMessage("Time_slab SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Time_slab ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Time_slab", e);
            }
        }
    }

    public void deleteTime_stamb() {
        String sql = "";
        try {


            sql = "delete from timestamb  ";
            GlobalData.printMessage("Time_slab SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Time_slab ", e);
        }
    }




    public LinkedList<TimeStamb> getTimestamp() {
        String sql = "select *  from timestamb "+whrsoftDeleChk;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<TimeStamb> list = new LinkedList<TimeStamb>();
        int i0 = c.getColumnIndex(Timestamp_KEY_timestampId);
        int i1 = c.getColumnIndex(Timestamp_KEY_tiimestamp_TEXT);
        int i2 = c.getColumnIndex(Timestamp_KEY_default_set);
        int i3 = c.getColumnIndex(Timestamp_KEY_status);
        int i4 = c.getColumnIndex(Timestamp_KEY_ModifiedDate);
        int i5 = c.getColumnIndex(Timestamp_KEY_created_date);
        int i6 = c.getColumnIndex(Timestammp_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                TimeStamb bean = new TimeStamb();
                bean.setTimestamp_id(c.getString(i0));
                bean.setTimestamp_txt(getClearData(c.getString(i1)));
                bean.setDefault_set(getClearData(c.getString(i2)));
                bean.setStatus(getClearData(c.getString(i3)));
                bean.setModified_date(getClearData(c.getString(i4)));
                bean.setCreated_date(getClearData(c.getString(i5)));
                bean.setSoft_del(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Timestamp Read:", e);
            }
        }
        c.close();
        return list;
    }

    public Service_type  getService_type(String service_type_id) {
        String sql = "select *  from Service_type where service_type_id= '"+service_type_id+"' ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        Service_type bean = new Service_type();
        int i0 = c.getColumnIndex(Service_type_KEY_service_type_id);
        int i1 = c.getColumnIndex(Service_type_KEY_service_text);
        int i2 = c.getColumnIndex(Service_type_KEY_status);
        int i3 = c.getColumnIndex(Service_type_KEY_default_select);
        int i4 = c.getColumnIndex(Service_type_KEY_soft_del);
        int i5 = c.getColumnIndex(Service_type_KEY_modified_date);
        int i6 = c.getColumnIndex(Service_type_KEY_created_date);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                bean.setService_type_id(getClearData(c.getString(i0)));
                bean.setService_text(getClearData(c.getString(i1)));
                bean.setStatus(getClearData(c.getString(i2)));
                bean.setDefault_select(getClearData(c.getString(i3)));
                bean.setSoft_del(getClearData(c.getString(i4)));
                bean.setModified_date(getClearData(c.getString(i5)));
                bean.setCreated_date(getClearData(c.getString(i6)));
            } catch (Exception e) {
                GlobalData.printError("Service_type Read:", e);
            }
        }
        c.close();
        return bean;
    }


    //VAS_Rate
    public void addVAS_Rate(LinkedList<VAS_Rate> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                VAS_Rate bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into vas_rate  (vas_rate , service_type_id,  time_slab_id ,  vas_id ,  tat ,  price ,  min_charges , modified_date ,  created_date , soft_del ) values ( '" + bean.getVas_rate() + "','"+bean.getService_type_id()+"', '" + setClearData(bean.getTime_slab_id()) + "',  '" + setClearData(bean.getVas_id()) + "',  '" + setClearData(bean.getTat()) + "',  " + bean.getPrice() + ",  '" + setClearData(bean.getMin_charges()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getCreated_date()) + "', '" + setClearData(bean.getSoft_del()) + "' )";
                        GlobalData.printMessage("Time_slab SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Time_slab ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Time_slab", e);
            }
        }
    }

    public void deleteVAS_Rate() {
        String sql = "";
        try {


            sql = "delete from VAS_Rate  ";
            GlobalData.printMessage("Time_slab SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Time_slab ", e);
        }
    }


    public void addTimestamp_calculation(LinkedList<Timestamps_cal> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Timestamps_cal bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into timestamps_cal  (timestamp_calculation_id ,service_type_id ,percentage_value ,delivery_opt_id ,timestamp_id ,soft_del ,created_date ,modified_date )  values ( "+bean.getTimestamp_calculation_id()+", '"+ setClearData(bean.getService_type_id()) +"',  '"+ setClearData(bean.getPercentage_value()) +"',  '"+ setClearData(bean.getDelivery_opt_id()) +"',  '"+ setClearData(bean.getTimestamp_id()) +"',  '"+ setClearData(bean.getSoft_del()) +"',  '"+ setClearData(bean.getCreated_date()) +"',  '"+ setClearData(bean.getModified_date()) +"')";
                        GlobalData.printMessage("Time_slab SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                }
                catch (Exception e) {
                    GlobalData.printError("Add Time_slab ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Time_slab", e);
            }
        }
    }

// Updated Methods

    public void updateOrderDetails(int orderId, String assignment_no,int serverId ) {
        String sql = "";
        try {
            if (orderId != 0) {


                sql = "update OrderDetails set  assignment_no = '"+assignment_no+"' , server_Id = "+serverId+" where order_id = "+orderId;
                GlobalData.printMessage("OrderDetails SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        }
        catch (Exception e) {
            GlobalData.printError("update OrderDetails ", e);
        }
    }

    public void updateRecording(LinkedList<Recording> recList) {
        String sql = "";
        try {

            for (int i = 0 ;i < recList.size();i++) {
                Recording bean =recList.get(i);

                if (bean.getRecId() > 0) {
                    sql = "update  Recording set serverId = " + bean.getServerId() + ", upMasterId = '" + bean.getUpMasterId() + "', assignment_no = '"+bean.getAssignment_no()+"' where recId = " + bean.getRecId();
                    GlobalData.printMessage("Recording SQl:" + sql);
                    ourDatabase.execSQL(sql);
                }

            }
        } catch (Exception e) {
            GlobalData.printError("update Recording ", e);
        }
    }


    // Update My Recording Status
    public void updatingRecording(File_Meta_JSON bean) {
        String sql = "";
        try {
            if (bean != null) {
                sql = "update Recording set upMasterId = '"+bean.getFile_status()+"' where  serverId = '"+bean.getOrder_media_id()+"';";
                GlobalData.printMessage("update SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("update Recording ", e);
        }
    }


    // Delete Recording
    public void deleteRecording(LinkedList<MyRecording> beanList) {
        String sql = "";
        try {

            for (int i = 0; i < beanList.size(); i++) {

                try {

                    MyRecording bean = beanList.get(i);

                    if (bean != null) {
                        sql = "delete from  Recording where  upMasterId != 'uploading' and recId =" + bean.getRecId();
                        GlobalData.printMessage("Recording SQl:" + sql);
                        ourDatabase.execSQL(sql);

                        // delete file from sdcard
                        File file = new File(bean.getRecLocalPath());
                        boolean deleted = file.delete();
                    }

                } catch (Exception e) {
                    GlobalData.printError(e);
                }

            }
        } catch (Exception e) {
            GlobalData.printError("Add Recording ", e);
        }
    }

    public Vas getVas(String vas_id) {
        String sql = "select *  from Vas where vas_id= '" + vas_id +"' ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        Vas bean = new Vas();
        int i0 = c.getColumnIndex(Vas_KEY_vas_id);
        int i1 = c.getColumnIndex(Vas_KEY_vas_text);
        int i2 = c.getColumnIndex(Vas_KEY_default_set);
        int i3 = c.getColumnIndex(Vas_KEY_service_flag);
        int i4 = c.getColumnIndex(Vas_KEY_transcription_flag);
        int i5 = c.getColumnIndex(Vas_KEY_timeslab_flag);
        int i6 = c.getColumnIndex(Vas_KEY_status);
        int i7 = c.getColumnIndex(Vas_KEY_created_date);
        int i8 = c.getColumnIndex(Vas_KEY_modified_date);
        int i9 = c.getColumnIndex(Vas_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setVas_id(getClearData(c.getString(i0)));
                bean.setVas_text(getClearData(c.getString(i1)));
                bean.setDefault_set(getClearData(c.getString(i2)));
                bean.setService_flag(getClearData(c.getString(i3)));
                bean.setTranscription_flag(getClearData(c.getString(i4)));
                bean.setTimeslab_flag(getClearData(c.getString(i5)));
                bean.setStatus(getClearData(c.getString(i6)));
                bean.setCreated_date(getClearData(c.getString(i7)));
                bean.setModified_date(getClearData(c.getString(i8)));
                bean.setSoft_del(getClearData(c.getString(i9)));

            } catch (Exception e) {
                GlobalData.printError("Vas Read:", e);
            }
        }
        c.close();
        return bean;
    }



    public TimeStamb getTimestamp(String timestamp_id) {
        String sql = "select *  from timestamb where timestamp_id= '"+ timestamp_id+"' ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        TimeStamb bean = new TimeStamb();
        int i0 = c.getColumnIndex(Timestamp_KEY_timestampId);
        int i1 = c.getColumnIndex(Timestamp_KEY_tiimestamp_TEXT);
        int i2 = c.getColumnIndex(Timestamp_KEY_default_set);
        int i3 = c.getColumnIndex(Timestamp_KEY_status);
        int i4 = c.getColumnIndex(Timestamp_KEY_ModifiedDate);
        int i5 = c.getColumnIndex(Timestamp_KEY_created_date);
        int i6 = c.getColumnIndex(Timestammp_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setTimestamp_id(c.getString(i0));
                bean.setTimestamp_txt(getClearData(c.getString(i1)));
                bean.setDefault_set(getClearData(c.getString(i2)));
                bean.setStatus(getClearData(c.getString(i3)));
                bean.setModified_date(getClearData(c.getString(i4)));
                bean.setCreated_date(getClearData(c.getString(i5)));
                bean.setSoft_del(getClearData(c.getString(i6)));

            } catch (Exception e) {
                GlobalData.printError("Timestamp Read:", e);
            }
        }
        c.close();
        return bean;
    }

    public Delivery_option  getDelivery_option(String delivery_option_id) {
        String sql = "select *  from Delivery_option where delivery_opt_id = '"+ delivery_option_id + "' ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        Delivery_option bean = new Delivery_option();
        int i0 = c.getColumnIndex(Delivery_option_KEY_delivery_opt_id);
        int i1 = c.getColumnIndex(Delivery_option_KEY_del_days);
        int i2 = c.getColumnIndex(Delivery_option_KEY_del_option);
        int i3 = c.getColumnIndex(Delivery_option_KEY_status);
        int i4 = c.getColumnIndex(Delivery_option_KEY_created_date);
        int i5 = c.getColumnIndex(Delivery_option_KEY_modified_date);
        int i6 = c.getColumnIndex(Delivery_option_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setDelivery_opt_id(getClearData(c.getString(i0)));
                bean.setDel_days(getClearData(c.getString(i1)));
                bean.setDel_option(getClearData(c.getString(i2)));
                bean.setStatus(getClearData(c.getString(i3)));
                bean.setCreated_date(getClearData(c.getString(i4)));
                bean.setModified_date(getClearData(c.getString(i5)));
                bean.setSoft_del(getClearData(c.getString(i6)));

            } catch (Exception e) {
                GlobalData.printError("Delivery_option Read:", e);
            }
        }
        c.close();
        return bean;
    }
    public LinkedList<Delivery_option> getDelivery_optionList(int delivery_opt_id) {
        String sql = "select *  from Delivery_option where delivery_opt_id=" + delivery_opt_id;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Delivery_option> list = new LinkedList<Delivery_option>();
        int i0 = c.getColumnIndex(Delivery_option_KEY_delivery_opt_id);
        int i1 = c.getColumnIndex(Delivery_option_KEY_del_days);
        int i2 = c.getColumnIndex(Delivery_option_KEY_del_option);
        int i3 = c.getColumnIndex(Delivery_option_KEY_status);
        int i4 = c.getColumnIndex(Delivery_option_KEY_created_date);
        int i5 = c.getColumnIndex(Delivery_option_KEY_modified_date);
        int i6 = c.getColumnIndex(Delivery_option_KEY_soft_del);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Delivery_option bean = new Delivery_option();
                bean.setDelivery_opt_id(getClearData(c.getString(i0)));
                bean.setDel_days(getClearData(c.getString(i1)));
                bean.setDel_option(getClearData(c.getString(i2)));
                bean.setStatus(getClearData(c.getString(i3)));
                bean.setCreated_date(getClearData(c.getString(i4)));
                bean.setModified_date(getClearData(c.getString(i5)));
                bean.setSoft_del(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Delivery_option Read:", e);
            }
        }
        c.close();
        return list;
    }


    //VAS_Rate
    public void addPages(LinkedList<Pages> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Pages bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert or replace into pages  (page_id ,  page_title ,  page_content ,  status ,  soft_del ,  created_date ,  modified_date ,show_in_menu , page_link_id ) values ( '" + bean.getPage_id() + "', '" + setClearData(bean.getPage_title()) + "',  '" + setClearData(bean.getPage_content()) + "',  " + bean.getStatus() + ",  '" + bean.getSoft_del() + "',  '" + setClearData(bean.getCreated_date()) + "',  '" + setClearData(bean.getModified_date()) + "',  '" + setClearData(bean.getShow_in_menu()) + "', '" + setClearData(bean.getPage_link_id()) + "' )";
                        GlobalData.printMessage("Time_slab SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Time_slab ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Time_slab", e);
            }
        }
    }

    public void deletePages() {
        String sql = "";
        try {


            sql = "delete from pages  ";
            GlobalData.printMessage("pages SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete pages ", e);
        }
    }


    public Pages getPages(int pageId) {
        String sql = "select *  from pages where page_id=" + pageId;
        Cursor c = ourDatabase.rawQuery(sql, null);






        Pages bean = new Pages();


        int i1 = c.getColumnIndex("page_id");
        int i2 = c.getColumnIndex("page_title");
        int i3 = c.getColumnIndex("page_content");
        int i4 = c.getColumnIndex("status");
        int i5 = c.getColumnIndex("soft_del");
        int i6 = c.getColumnIndex("created_date");
        int i7 = c.getColumnIndex("modified_date");
        int i8 = c.getColumnIndex("show_in_menu");
        int i9 = c.getColumnIndex("page_link_id");


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setPage_id(getClearData(c.getString(i1)));
                bean.setPage_title(getClearData(c.getString(i2)));
                bean.setPage_content(getClearData(c.getString(i3)));
                bean.setStatus(c.getInt(i4));
                bean.setSoft_del(getClearData(c.getString(i5)));
                bean.setCreated_date(getClearData(c.getString(i6)));
                bean.setModified_date(getClearData(c.getString(i7)));
                bean.setShow_in_menu(getClearData(c.getString(i8)));
                bean.setPage_link_id(getClearData(c.getString(i9)));


            } catch (Exception e) {
                GlobalData.printError("Delivery_option Read:", e);
            }
        }
        c.close();
        return bean;
    }


    public Delivery_slot getDelivery_slot(float cutHHMM, int satFlag) {
        String sql = "SELECT * FROM delivery_slot WHERE (slot_from + 0.30 ) <= "+cutHHMM+" AND (slot_to + 0.30) >= "+cutHHMM+" and sat_Flag ='"+satFlag+"'";
        Cursor c = ourDatabase.rawQuery(sql, null);
        Delivery_slot bean = new Delivery_slot();
        int i0 = c.getColumnIndex(Delivery_slot_KEY_deliveryslot_id);
        int i1 = c.getColumnIndex(Delivery_slot_KEY_slot_from);
        int i2 = c.getColumnIndex(Delivery_slot_KEY_slot_to);
        int i3 = c.getColumnIndex(Delivery_slot_KEY_modified_date);
        int i4 = c.getColumnIndex(Delivery_slot_KEY_created_date);
        int i5 = c.getColumnIndex(Delivery_slot_KEY_soft_del);


        if(c.getCount()<=0){
            int delSlotId=0;
            try {
                sql = "select    *   from delivery_slot  where sat_Flag ='"+satFlag+"' order by  cast (slot_from as float)  desc limit 1";
                c = ourDatabase.rawQuery(sql, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    delSlotId = c.getInt(i0);
                }


            }catch (Exception e){
                GlobalData.printError(e);
            }

            try {
                sql = "select  *   from delivery_slot  where   sat_Flag ='"+satFlag+"' and ( cast( slot_to as float) >= "+cutHHMM+"  or cast( slot_from as float) <= "+cutHHMM+"  ) and deliveryslot_id ="+delSlotId;
                c = ourDatabase.rawQuery(sql, null);



            }catch (Exception e){
                GlobalData.printError(e);
            }

        }




        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setDeliveryslot_id(getClearData(c.getString(i0)));
                bean.setSlot_from(getClearData(c.getString(i1)));
                bean.setSlot_to(getClearData(c.getString(i2)));
                bean.setModified_date(getClearData(c.getString(i3)));
                bean.setCreated_date(getClearData(c.getString(i4)));
                bean.setSoft_del(getClearData(c.getString(i5)));


            } catch (Exception e) {
                GlobalData.printError("Delivery_slot Read:", e);
            }
        }
        c.close();
        return bean;
    }


    public Delivery_slot getNextDelivery_slot(Delivery_slot deliverySlot, int satFlag ) {

        float nextToVal= 0;
        try {
            nextToVal =Float.parseFloat(deliverySlot.getSlot_to()) ;
            nextToVal=nextToVal+ (float) 0.2;
        }catch (Exception e){
            GlobalData.printError(e);
        }

        String sql = "SELECT * FROM delivery_slot WHERE  sat_Flag ='"+satFlag+"' and cast (slot_from  as float ) <= "+nextToVal+" AND cast(slot_to as float ) >= "+nextToVal+"";
        Cursor c = ourDatabase.rawQuery(sql, null);
        Delivery_slot bean = new Delivery_slot();
        int i0 = c.getColumnIndex(Delivery_slot_KEY_deliveryslot_id);
        int i1 = c.getColumnIndex(Delivery_slot_KEY_slot_from);
        int i2 = c.getColumnIndex(Delivery_slot_KEY_slot_to);
        int i3 = c.getColumnIndex(Delivery_slot_KEY_modified_date);
        int i4 = c.getColumnIndex(Delivery_slot_KEY_created_date);
        int i5 = c.getColumnIndex(Delivery_slot_KEY_soft_del);


        if(c.getCount()<=0){
            int delSlotId=0;
            try {
                sql = "select    *   from delivery_slot where  sat_Flag ='"+satFlag+"' order by  cast (slot_from as float)  desc limit 1";
                c = ourDatabase.rawQuery(sql, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    delSlotId = c.getInt(i0);
                }


            }catch (Exception e){
                GlobalData.printError(e);
            }

            try {
                sql = "select  *   from delivery_slot  where   sat_Flag ='"+satFlag+"' and ( cast( slot_to as float) >= "+nextToVal+"  or cast( slot_from as float) <= "+nextToVal +"  ) and deliveryslot_id ="+delSlotId;
                c = ourDatabase.rawQuery(sql, null);



            }catch (Exception e){
                GlobalData.printError(e);
            }

        }



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {

                bean.setDeliveryslot_id(getClearData(c.getString(i0)));
                bean.setSlot_from(getClearData(c.getString(i1)));
                bean.setSlot_to(getClearData(c.getString(i2)));
                bean.setModified_date(getClearData(c.getString(i3)));
                bean.setCreated_date(getClearData(c.getString(i4)));
                bean.setSoft_del(getClearData(c.getString(i5)));


            } catch (Exception e) {
                GlobalData.printError("Delivery_slot Read:", e);
            }
        }
        c.close();
        return bean;
    }


    public LinkedList<MyRecording> getRecordingList(String assignment_no) {
        String sql = "select *  from Recording where assignment_no= '" + assignment_no+"'";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<MyRecording> list = new LinkedList<MyRecording>();
        int i0 = c.getColumnIndex(Recording_KEY_recId);
        int i1 = c.getColumnIndex(Recording_KEY_serverId);
        int i2 = c.getColumnIndex(Recording_KEY_userId);
        int i3 = c.getColumnIndex(Recording_KEY_upMasterId);
        int i4 = c.getColumnIndex(Recording_KEY_sourceTypeId);
        int i5 = c.getColumnIndex(Recording_KEY_sourceLink);
        int i6 = c.getColumnIndex(Recording_KEY_recName);
        int i7 = c.getColumnIndex(Recording_KEY_recDesc);
        int i8 = c.getColumnIndex(Recording_KEY_recDuration);
        int i9 = c.getColumnIndex(Recording_KEY_recLocalPath);
        int i10 = c.getColumnIndex(Recording_KEY_recSize);
        int i11 = c.getColumnIndex(Recording_KEY_recUploadDuration);
        int i12 = c.getColumnIndex(Recording_KEY_uploadingConnectionMode);
        int i13 = c.getColumnIndex(Recording_KEY_createdDate);
        int i14 = c.getColumnIndex("assignment_no");

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                MyRecording bean = new MyRecording();
                bean.setRecId(c.getInt(i0));
                bean.setServerId(c.getInt(i1));
                bean.setUserId(c.getInt(i2));
                bean.setUpMasterId(c.getString(i3));
                bean.setSourceTypeId(c.getString(i4));
                bean.setSourceLink(getClearData(c.getString(i5)));
                bean.setRecName(getClearData(c.getString(i6)));
                bean.setRecDesc(getClearData(c.getString(i7)));
                bean.setRecDuration(getClearData(c.getString(i8)));
                bean.setRecLocalPath(getClearData(c.getString(i9)));
                bean.setRecSize(getClearData(c.getString(i10)));
                bean.setRecUploadDuration(getClearData(c.getString(i11)));
                bean.setUploadingConnectionMode(getClearData(c.getString(i12)));
                bean.setCreatedDate(getClearData(c.getString(i13)));
                bean.setAssignment_no(getClearData(c.getString(i14)));
                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Recording Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void updateReadNotification(int noti_id) {
        String sql = "";
        try {
            sql = "update Notification  set soft_del ='1' where noti_id =" + noti_id;
            GlobalData.printMessage("Notification SQl:" + sql);
            ourDatabase.execSQL(sql);
        } catch (Exception e) {
            GlobalData.printError("Notification", e);
        }
    }

}
