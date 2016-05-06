package com.voxtab.ariatech.voxtab.database;

import java.util.LinkedList;

/**
 * Created by MAC 2 on 2/3/2016.
 */
public class CreateStatements {


    //Ver 0.1

    public static LinkedList<String> getCreateStatements(int version) {

        LinkedList<String> statements = new LinkedList<>();

        switch (version) {


            case 1:

                statements.add("CREATE TABLE access_elements (  elm_id INTEGER PRIMARY KEY,  elm_name TEXT,  elm_created_on TEXT);");
                statements.add("CREATE TABLE assessment_report (  ass_report_id INTEGER PRIMARY KEY,  order_id NUMERIC,  ass_date TEXT,  audio_quality TEXT,  subjectarea TEXT,  result TEXT,  notesfrom TEXT,  notesfrom_trasaction TEXT  );");
                statements.add("CREATE TABLE assessment_speaker (  ass_speaker_id INTEGER PRIMARY KEY,  ass_report_id NUMERIC,  speaker_name TEXT );");
                statements.add("CREATE TABLE company_info (  membership_id TEXT PRIMARY KEY,  org_name_eng TEXT,  org_name_fn TEXT,  status TEXT,  client_type TEXT,  date_of_invoice TEXT,  name_of_invoice TEXT,  business_type TEXT,  template TEXT,  client_instruction TEXT,  payment_instruction TEXT,  modified_date TEXT,  created_date TEXT,  soft_del TEXT);");
                statements.add("CREATE TABLE company_location (  client_location_id INTEGER PRIMARY KEY,  membership_id TEXT,  city TEXT,  state TEXT,  country TEXT,  pincode TEXT,  landline TEXT,  email TEXT,  mobile TEXT,  website TEXT,  currency TEXT,  address TEXT,  modified_date TEXT,  created_date TEXT,  soft_del TEXT);");
                statements.add("CREATE TABLE core_group (  group_id INTEGER PRIMARY KEY,  group_name TEXT,  arr_group_name TEXT,  pos_level TEXT,  pos_created TEXT);");
                statements.add("CREATE TABLE core_user (  user_id INTEGER PRIMARY KEY,  group_id NUMERIC,  cat_id NUMERIC,  username TEXT,  last_name TEXT,  password TEXT,  first_name TEXT,  join_date TEXT,  photo TEXT,  status TEXT,  app_user_status TEXT,  email TEXT,  gender TEXT,  mobile TEXT,  auth_key TEXT,  reg_date TEXT,  modified_date TEXT,  user_type TEXT,  session_key TEXT,  dob TEXT,  city TEXT);");
                statements.add("CREATE TABLE delivery_option (  delivery_opt_id TEXT  PRIMARY KEY,  del_days TEXT,  del_option TEXT,  status TEXT,  created_date TEXT,  modified_date TEXT,  soft_del TEXT);");
                statements.add("CREATE TABLE delivery_slot (  deliveryslot_id TEXT PRIMARY KEY,  slot_from TEXT,  slot_to TEXT,  modified_date TEXT,  created_date TEXT,  soft_del TEXT,  sat_flag TEXT);");
                statements.add("CREATE TABLE delivery_speed (  delivery_speed_id TEXT PRIMARY KEY,  service_type_id TEXT,  time_slab_id TEXT,  delivery_option_id TEXT,  rate TEXT,  status TEXT,  created_date TEXT,  modified_date TEXT,  soft_del TEXT);");
                statements.add("CREATE TABLE discount (  discount_id INTEGER PRIMARY KEY,  discount_type_id TEXT,  service_type_id TEXT,  delivery_opt_id TEXT,  vas_id TEXT,  min_time TEXT,  max_time TEXT,  start_date TEXT,  end_date TEXT,  discount TEXT,  status TEXT,  modified_date TEXT,  created_date TEXT,  soft_del TEXT);");
                statements.add("CREATE TABLE discount_type (  discount_type_id TEXT PRIMARY KEY,  discount_txt TEXT,  modified_date TEXT,  created_date TEXT,  soft_del TEXT);");
                statements.add("CREATE TABLE eng_local_resources (  resource_id INTEGER PRIMARY KEY,  resource_name TEXT,  resource_value text,  soft_del TEXT,  created_date TEXT,  modified_date TEXT);");
                statements.add("CREATE TABLE error_log (  logid INTEGER PRIMARY KEY,  logtime TEXT,  username NUMERIC,  dbname NUMERIC,  actionmsg TEXT,  errormsg TEXT);");
                statements.add("CREATE TABLE feedback (  feedback_id INTEGER PRIMARY KEY,  user_id NUMERIC,  order_id NUMERIC,  feed_txt TEXT,  created_date TEXT,  feedback_type TEXT,  assignment_no TEXT,  files_nams TEXT);");
                statements.add("CREATE TABLE file_source_type (  source_type TEXT PRIMARY KEY,  source_name TEXT,  active TEXT,  soft_del TEXT,  created_date TEXT,  modified_date TEXT);");

                statements.add("CREATE TABLE free_services (  free_service_id TEXT PRIMARY KEY,  free_service_txt TEXT,  default_set TEXT,  service_flag TEXT,  trans_flag TEXT,  timeslab_flag TEXT,  vas_flag TEXT,  status TEXT,  created_date TEXT,  modified_date TEXT,  soft_del TEXT);");
                statements.add("CREATE TABLE holidays (  holiday_id INTEGER PRIMARY KEY,  holiday_date TEXT, year TEXT,  month TEXT,  day TEXT,  status TEXT,  soft_del TEXT,  created_date TEXT,  modified_date TEXT);");
                statements.add("CREATE TABLE invoice_type (  invoice_type_id TEXT PRIMARY KEY,  invoice_txt TEXT,  created_date TEXT,  modified_date TEXT,  soft_del TEXT);");
                statements.add("CREATE TABLE local_resources (  resource_id INTEGER PRIMARY KEY, lng_id TEXT, resource_name TEXT,  resource_value TEXT,  soft_del TEXT,  created_date TEXT,  modified_date TEXT);");
                statements.add("CREATE TABLE mail_config (  config_id INTEGER PRIMARY KEY,  user_id NUMERIC,  purpose_name TEXT,  mail_server TEXT,  port TEXT,  username TEXT,  email TEXT,  password TEXT,  alt_email TEXT,  mail_ssl TEXT,  alert TEXT,  subject TEXT,  body TEXT,  other_email TEXT );");
                statements.add("CREATE TABLE msg_logs (  log_id INTEGER PRIMARY KEY,  user_id NUMERIC,  client_id NUMERIC,  app_id NUMERIC,  msg TEXT,  msg_date TEXT,  title TEXT);");
                statements.add("CREATE TABLE msg_type_logs (  ref_id INTEGER PRIMARY KEY,  log_id NUMERIC,  type_id NUMERIC);");
                statements.add("CREATE TABLE notification (  noti_id INTEGER PRIMARY KEY,  noti_type TEXT,  notifi_txt TEXT,  order_id NUMERIC,  status TEXT,  created_date TEXT,  assignment_no TEXT,  soft_del TEXT,noti_title TEXT);");
                statements.add("CREATE TABLE notification_type (  noti_type_id TEXT PRIMARY KEY,  notification_name TEXT,  template_desc TEXT,  created_on TEXT,  modified_date TEXT,  soft_del TEXT);");
                statements.add("CREATE TABLE orderDetails (  order_id INTEGER PRIMARY KEY, server_Id  NUMERIC,  assignment_no TEXT,  client_instruction TEXT,  total_files TEXT,  service_type_id TEXT,  trans_type_id TEXT, delivery_opt_id TEXT,  order_status_id TEXT,  vas_id TEXT,  time_slab_id TEXT,  invoice_type_id TEXT,  subject_of_file TEXT,  connection_type TEXT,  total_duration TEXT,  delivery_date TEXT,  transcription_link TEXT,  order_date TEXT,  modified_date TEXT,  total_fees TEXT,  order_placed_details TEXT,  order_complete_details TEXT,  days_excluded_tat TEXT,  output_format_id TEXT,  user_id NUMERIC,flag NUMERIC,upTime TEXT," +
                        " premium_type TEXT,discount_type  TEXT,gross_fees  TEXT,premium_per_hour  TEXT,total_premium  TEXT,discount  TEXT,total_discount  TEXT ,totalDurationMin TEXT " +

                        ");");
                statements.add("CREATE TABLE order_free_services (  order_free_serid INTEGER PRIMARY KEY,  order_id NUMERIC,  free_serv_id NUMERIC,  modified_date TEXT);");
                statements.add("CREATE TABLE order_media (  order_media_id INTEGER PRIMARY KEY,  user_id NUMERIC,  order_id NUMERIC,  file_status TEXT,  source_type TEXT,  sourcelink TEXT,  file_name TEXT,  file_description TEXT,  file_duration TEXT,  file_localpath TEXT,  file_size TEXT,  file_upload_duration TEXT,  file_up_conn_mode TEXT,  created_date TEXT,  modified_date TEXT);");
                statements.add("CREATE TABLE order_recording (  order_rec_id NUMERIC,  order_id NUMERIC,  recording_id NUMERIC);");
                statements.add("CREATE TABLE order_transaction (  order_trasnaction_id NUMERIC,  order_id NUMERIC,  rec_id NUMERIC,  status_id TEXT,  trans_date TEXT);");
                statements.add("CREATE TABLE order_vas (  order_vas_id TEXT PRIMARY KEY,  order_id NUMERIC,  vas_id NUMERIC);");
                statements.add("CREATE TABLE output_format (  output_format_id TEXT PRIMARY KEY,  output_format_txt TEXT,  status TEXT,  modified_date TEXT,  created_date TEXT,  soft_del TEXT);");
                statements.add("CREATE TABLE premium_type (  premium_type_id TEXT PRIMARY KEY,  premium_type TEXT,  status TEXT,  created_date TEXT,  modified_date TEXT,  soft_del TEXT);");
                statements.add("CREATE TABLE price (  price_id INTEGER PRIMARY KEY,  price TEXT,  tat TEXT,  min_charges TEXT,  delivery_opt_id TEXT,  service_type_id TEXT,  time_slab_id TEXT,  modified_date TEXT,  created_date TEXT,  soft_del TEXT);");
                statements.add("CREATE TABLE security_permission (  elm_id NUMERIC,  group_id NUMERIC,  lastmodifed_date TEXT);");
                statements.add("CREATE TABLE service_type (  service_type_id TEXT PRIMARY KEY,  service_text TEXT,  status TEXT,  default_select TEXT,  soft_del TEXT,  modified_date TEXT,  created_date TEXT);");
                statements.add("CREATE TABLE status_type (  order_status_id TEXT PRIMARY KEY,  status_txt TEXT,  is_active TEXT,  created_date TEXT,  modified_date TEXT,  soft_del TEXT);");
                statements.add("CREATE TABLE sys_log (  logid INTEGER PRIMARY KEY,  logtime TEXT,  username NUMERIC,  dbname NUMERIC,  logmsg TEXT,  log_type NUMERIC,  user_id NUMERIC);");
                statements.add("CREATE TABLE tat_fee (  tat_fee_id INTEGER PRIMARY KEY,  delivery_opt_id TEXT,  time_slab_id TEXT,  min_charges TEXT,  tat TEXT,  feepermin TEXT,  status TEXT,  created_date TEXT,  modified_date TEXT,  soft_del TEXT);");
                statements.add("CREATE TABLE time_slab (  time_slab_id TEXT PRIMARY KEY,  slab_from TEXT,  default_set TEXT,  status TEXT,  modified_date TEXT,  created_date TEXT,  slab_to TEXT,  soft_del TEXT,is_last TEXT);");
                statements.add("CREATE TABLE transcription_type (  trans_type_id TEXT PRIMARY KEY,  trans_type_txt TEXT,  default_set TEXT,  soft_del TEXT,  modified_date TEXT,  created_date TEXT,  is_active TEXT);");
                statements.add("CREATE TABLE user_device (  device_id NUMERIC,  user_id NUMERIC,  membership_id TEXT,  device_type TEXT,  imei TEXT,  api_key TEXT,  user_type TEXT, device_name TEXT,  device_os TEXT,  systemdate TEXT);");
                statements.add("CREATE TABLE users ( user_id INTEGER PRIMARY KEY,  membership_id TEXT,  location_id NUMERIC,  email TEXT,  first_name TEXT,  first_name_fn TEXT,  last_name TEXT,  last_name_fn TEXT,  password TEXT,  join_date TEXT,  photo TEXT,  gender TEXT,  auth_key TEXT,  reg_date TEXT,  modified_date TEXT,  dob TEXT,  designation TEXT,  department TEXT,  email_2 TEXT,  mobile_no TEXT,  mobile_no_2 TEXT,  extension TEXT,  landline TEXT,  noti_setting_type varTEXT,  upload_setting_type varTEXT,  username TEXT,  imei TEXT,  is_email_verified TEXT);");
                statements.add("CREATE TABLE vas (  vas_id TEXT PRIMARY KEY,  vas_text TEXT,  default_set TEXT,  service_flag TEXT,  transcription_flag TEXT,  timeslab_flag TEXT,  status TEXT,  created_date TEXT,  modified_date TEXT,  soft_del TEXT);");
                statements.add("CREATE TABLE timestamb (  timestamp_id TEXT PRIMARY KEY,  timestamp_txt TEXT,  default_set TEXT,  status TEXT,  modified_date TEXT,  created_date TEXT, soft_del TEXT,premium_type_id TEXT);");
                statements.add("CREATE TABLE vas_rate(vas_rate TEXT PRIMARY KEY, time_slab_id TEXT, vas_id TEXT, tat TEXT, price TEXT, min_charges TEXT, modified_date TEXT, created_date TEXT, soft_del TEXT, service_type_id TEXT);");
                statements.add("CREATE TABLE Recording(recId INTEGER PRIMARY KEY, serverId NUMERIC, userId NUMERIC, upMasterId TEXT, sourceTypeId TEXT, sourceLink TEXT, recName TEXT, recDesc TEXT, recDuration TEXT, recLocalPath TEXT, recSize TEXT, recUploadDuration TEXT, uploadingConnectionMode TEXT, createdDate TEXT,  assignment_no TEXT);");
                statements.add("CREATE TABLE SchemeDetails (scheme_name TEXT, scheme_duration TEXT, price TEXT, date_time TEXT); \n");
            //    statements.add(" CREATE TABLE TimeStampCalculation (timeStampCalculationId INTEGER PRIMARY KEY, service_type_id TEXT, PercentageValue NUMERIC, delivery_opt_id TEXT, timestamp_id INTEGER FORIEGN KEY);\n");
                statements.add("create table timestamps_cal (timestamp_calculation_id INTEGER PRIMARY KEY,service_type_id TEXT,percentage_value TEXT,delivery_opt_id TEXT,timestamp_id TEXT,soft_del TEXT,created_date TEXT,modified_date TEXT);\n");
                statements.add("CREATE TABLE file_status_type ( file_status TEXT PRIMARY KEY,  file_status_msg TEXT,  is_active TEXT,  sort_order NUMERIC,  soft_del TEXT,  modified_date TEXT,  created_date TEXT);");
                statements.add("CREATE TABLE pages ( page_id TEXT PRIMARY KEY,  page_title TEXT,  page_content TEXT,  status NUMERIC,  soft_del TEXT,  created_date TEXT,  modified_date TEXT,show_in_menu TEXT, page_link_id TEXT);");

                break;
        }


        return statements;


    }


}
