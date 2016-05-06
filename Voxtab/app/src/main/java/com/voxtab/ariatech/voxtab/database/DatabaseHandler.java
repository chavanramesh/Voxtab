package com.voxtab.ariatech.voxtab.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.voxtab.ariatech.voxtab.bean.AssessmentReport;
import com.voxtab.ariatech.voxtab.bean.AssessmentSpeaker;
import com.voxtab.ariatech.voxtab.bean.ChangePrice;
import com.voxtab.ariatech.voxtab.bean.Device;
import com.voxtab.ariatech.voxtab.bean.Discount;
import com.voxtab.ariatech.voxtab.bean.Feedback;
import com.voxtab.ariatech.voxtab.bean.Holidays;
import com.voxtab.ariatech.voxtab.bean.MyRecording;
import com.voxtab.ariatech.voxtab.bean.Notification;
import com.voxtab.ariatech.voxtab.bean.OrderFreeServices;
import com.voxtab.ariatech.voxtab.bean.OrderHistory;
import com.voxtab.ariatech.voxtab.bean.OrderRec;
import com.voxtab.ariatech.voxtab.bean.OrderRecordings;
import com.voxtab.ariatech.voxtab.bean.OrderStatus;
import com.voxtab.ariatech.voxtab.bean.OrderValueAddedServices;
import com.voxtab.ariatech.voxtab.bean.Recording;
import com.voxtab.ariatech.voxtab.bean.Source;
import com.voxtab.ariatech.voxtab.bean.UploadingMaster;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import java.io.File;
import java.sql.SQLException;
import java.util.LinkedList;

public class DatabaseHandler {
    public static final String DATABASE_NAME = "voxtab_new.db";
    public static final int DATABASE_VERSION = 1;
    private static String DB_PATH = "/data/data/com.voxtab.ariatech.voxtab/databases/";
    public static final String ROW_COUNT = "count";

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;


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


     /*   private void copyDataBase() throws IOException {

            // Open your local db as the input stream
            InputStream myInput = ourContext.getResources().openRawResource(
                    R.raw.voxtab_new);
            // InputStream myInput =null;
            // Path to the just created empty db
            String outFileName = DB_PATH + DATABASE_NAME;

            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();

        }

        public void createDataBase() throws IOException {

            boolean dbExist = checkDataBase();

            GlobalData.printMessage("Table exist : " + dbExist);

            if (dbExist) {
                // do nothing - database already exist
            } else {
                GlobalData.printMessage("Not exist : ");
                // By calling this method and empty database will be created
                // into the default system path
                // of your application so we are gonna be able to overwrite that
                // database with our database.
                this.getReadableDatabase();

                try {
                    GlobalData.printMessage("Copy File : ");
                    copyDataBase();

                } catch (IOException e) {
                    GlobalData.printMessage("Error copying database");
                    throw new Error("Error copying database");
                }
            }
        }

        private boolean checkDataBase() {

            SQLiteDatabase checkDB = null;
            try {
                String myPath = DB_PATH + DATABASE_NAME;
                checkDB = SQLiteDatabase.openDatabase(myPath, null,
                        SQLiteDatabase.OPEN_READONLY);
            } catch (SQLiteException e) {

                GlobalData.printError(e);
                // database does't exist yet.
            }

            if (checkDB != null) {
                checkDB.close();
            }

            return checkDB != null ? true : false;
        }
*/

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }

    }

    public DatabaseHandler(Context c) {
        ourContext = c;
    }

    public DatabaseHandler open() throws SQLException {
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


    // AssessmentReport
    public static final String AssessmentReport_KEY_assessmentReportId = "assessmentReportId";
    public static final String AssessmentReport_KEY_orderId = "orderId";
    public static final String AssessmentReport_KEY_assDate = "assDate";
    public static final String AssessmentReport_KEY_AudioQuality = "AudioQuality";
    public static final String AssessmentReport_KEY_subjectArea = "subjectArea";
    public static final String AssessmentReport_KEY_result = "result";
    public static final String AssessmentReport_KEY_notesFrom = "notesFrom";
    public static final String AssessmentReport_KEY_notesFromTrans = "notesFromTrans";


    // AssessmentSpeaker
    public static final String AssessmentSpeaker_KEY_assessmentSpeakerId = "assessmentSpeakerId";
    public static final String AssessmentSpeaker_KEY_assessmentReportId = "assessmentReportId";
    public static final String AssessmentSpeaker_KEY_speakerName = "speakerName";


    // DeliveryOption
    public static final String DeliveryOption_KEY_deliveryOptionId = "deliveryOptionId";
    public static final String DeliveryOption_KEY_deliveryInDays = "deliveryInDays";
    public static final String DeliveryOption_KEY_deliveryOption = "deliveryOption";
    public static final String DeliveryOption_KEY_isActive = "isActive";
    public static final String DeliveryOption_KEY_lastModifiedDate = "lastModifiedDate";


    // DeliverySlot
    public static final String DeliverySlot_KEY_deliverySlotId = "deliverySlotId";
    public static final String DeliverySlot_KEY_slotFrom = "slotFrom";
    public static final String DeliverySlot_KEY_slotTo = "slotTo";


    // DeliverySpeed
    public static final String DeliverySpeed_KEY_DeliverySpeedId = "DeliverySpeedId";
    public static final String DeliverySpeed_KEY_serviceTypeId = "serviceTypeId";
    public static final String DeliverySpeed_KEY_timestampId = "timestampId";
    public static final String DeliverySpeed_KEY_deliveryTypeId = "deliveryTypeId";
    public static final String DeliverySpeed_KEY_rate = "rate";
    public static final String DeliverySpeed_KEY_activeFlag = "activeFlag";


    // Device
    public static final String Device_KEY_deviceId = "deviceId";
    public static final String Device_KEY_userId = "userId";
    public static final String Device_KEY_IMEI = "IMEI";
    public static final String Device_KEY_deviceModel = "deviceModel";
    public static final String Device_KEY_Key = "Key";
    public static final String Device_KEY_deveicetype = "deveicetype";


    // Discount
    public static final String Discount_KEY_discountId = "discountId";
    public static final String Discount_KEY_serviceTypeId = "serviceTypeId";
    public static final String Discount_KEY_deliveryTypeId = "deliveryTypeId";
    public static final String Discount_KEY_valuAddedServiceId = "valuAddedServiceId";
    public static final String Discount_KEY_minMin = "minMin";
    public static final String Discount_KEY_maxMin = "maxMin";
    public static final String Discount_KEY_startDate = "startDate";
    public static final String Discount_KEY_endDate = "endDate";
    public static final String Discount_KEY_discount = "discount";


    // DiscountType
    public static final String DiscountType_KEY_discountTypeId = "discountTypeId";
    public static final String DiscountType_KEY_discountType = "discountType";


    // Feedback
    public static final String Feedback_KEY_feedbackId = "feedbackId";
    public static final String Feedback_KEY_userId = "userId";
    public static final String Feedback_KEY_orderId = "orderId";
    public static final String Feedback_KEY_feedback = "feedback";
    public static final String Feedback_KEY_TEXT = "TEXT";


    // FreeServices
    public static final String FreeServices_KEY_freeServicesId = "freeServicesId";
    public static final String FreeServices_KEY_freeService = "freeService";
    public static final String FreeServices_KEY_defaultSelectionFlag = "defaultSelectionFlag";
    public static final String FreeServices_KEY_serviceTypeFlag = "serviceTypeFlag";
    public static final String FreeServices_KEY_transriptionTypeFlag = "transcriptionTypeFlag";
    public static final String FreeServices_KEY_timestampFlag = "timestampFlag";
    public static final String FreeServices_KEY_valueAddedServicesFlag = "valueAddedServicesFlag";
    public static final String FreeServices_KEY_activeFlag = "activeFlag";
    public static final String FreeServices_KEY_lastModifiedDate = "lastModifiedDate";


    // Holidays
    public static final String Holidays_KEY_holidayId = "holidayId";
    public static final String Holidays_KEY_holidayDate = "holidayDate";


    // InvoiceType
    public static final String InvoiceType_KEY_invoiceTypeId = "invoiceTypeId";
    public static final String InvoiceType_KEY_invoiceType = "invoiceType";
    public static final String InvoiceType_KEY_lastModifiedDate = "lastModifiedDate";


    // Notification
    public static final String Notification_KEY_notificationId = "notificationId";
    public static final String Notification_KEY_notificationTypeId = "notificationTypeId";
    public static final String Notification_KEY_notificationText = "notificationText";
    public static final String Notification_KEY_orderid = "orderid";
    public static final String Notification_KEY_activeFlag = "activeFlag";
    public static final String Notification_KEY_createdDate = "createdDate";
    public static final String Notification_KEY_modifiedDate = "modifiedDate";


    // NotificationType
    public static final String NotificationType_KEY_notificationTypeId = "notificationTypeId";
    public static final String NotificationType_KEY_notificationType = "notificationType";


    // OrderFreeServices
    public static final String OrderFreeServices_KEY_orderFreeServId = "orderFreeServId";
    public static final String OrderFreeServices_KEY_orderId = "orderId";
    public static final String OrderFreeServices_KEY_freeServId = "freeServId";


    // OrderHistory
    public static final String OrderHistory_KEY_orderHistoryId = "orderHistoryId";
    public static final String OrderHistory_KEY_orderId = "orderId";
    public static final String OrderHistory_KEY_uploadingMasterId = "uploadingMasterId";
    public static final String OrderHistory_KEY_orderStatus = "orderStatus";
    public static final String OrderHistory_KEY_price = "price";
    public static final String OrderHistory_KEY_deliveryDate = "deliveryDate";
    public static final String OrderHistory_KEY_orderHistoryNo = "orderHistoryNo";


    // OrderRec
    public static final String OrderRec_KEY_orderId = "orderId";
    public static final String OrderRec_KEY_assignmentNo = "assignmentNo";
    public static final String OrderRec_KEY_clientInstruction = "clientInstruction";
    public static final String OrderRec_KEY_numberOfFiles = "numberOfFiles";
    public static final String OrderRec_KEY_serviceTypeId = "serviceTypeId";
    public static final String OrderRec_KEY_transcriptionTypeId = "transcriptionTypeId";
    public static final String OrderRec_KEY_deliveryOptionId = "deliveryOptionId";
    public static final String OrderRec_KEY_orderStatusId = "orderStatusId";
    public static final String OrderRec_KEY_valueAddedservId = "valueAddedservId";
    public static final String OrderRec_KEY_timestampId = "timestampId";
    public static final String OrderRec_KEY_invoiceTypeId = "invoiceTypeId";
    public static final String OrderRec_KEY_outputFormatId = "outputFormatId";
    public static final String OrderRec_KEY_subjectOfFile = "subjectOfFile";
    public static final String OrderRec_KEY_instructions = "instructions";
    public static final String OrderRec_KEY_connectionType = "connectionType";
    public static final String OrderRec_KEY_createDate = "createDate";
    public static final String OrderRec_KEY_totalDuration = "totalDuration";
    public static final String OrderRec_KEY_deliveryDate = "deliveryDate";
    public static final String OrderRec_KEY_totalFees = "totalFees";
    public static final String OrderRec_KEY_transcriptionLink = "transcriptionLink";
    public static final String OrderRec_KEY_orderdate = "orderdate";
    public static final String OrderRec_KEY_modifiedDate = "modifiedDate";
    public static final String OrderRec_KEY_fileExtension = "fileExtension";
    public static final String OrderRec_KEY_orderPlaced = "orderPlaced";
    public static final String OrderRec_KEY_completeOrderDate = "completeOrderDate";
    public static final String OrderRec_KEY_completeOrderDetails = "completeOrderDetails";
    public static final String OrderRec_KEY_excludedTATdate = "excludedTATdate";


    // OrderRecordings
    public static final String OrderRecordings_KEY_orderRecId = "orderRecId";
    public static final String OrderRecordings_KEY_orderId = "orderId";
    public static final String OrderRecordings_KEY_recId = "recId";


    // OrderStatus
    public static final String OrderStatus_KEY_orderStatusId = "orderStatusId";
    public static final String OrderStatus_KEY_orderId = "orderId";
    public static final String OrderStatus_KEY_recId = "recId";
    public static final String OrderStatus_KEY_statusId = "statusId";
    public static final String OrderStatus_KEY_statusTEXT = "statusTEXT";


    // OrderValueAddedServices
    public static final String OrderValueAddedServices_KEY_orderValueAddedServicesId = "orderValueAddedServicesId";
    public static final String OrderValueAddedServices_KEY_orderId = "orderId";
    public static final String OrderValueAddedServices_KEY_valueAddedServiceId = "valueAddedServiceId";


    // OutputFormat
    public static final String OutputFormat_KEY_outputFormatId = "outputFormatId";
    public static final String OutputFormat_KEY_outputFormat = "outputFormat";
    public static final String OutputFormat_KEY_lastModifiedDate = "lastModifiedDate";


    // PremiumType
    public static final String PremiumType_KEY_PremiumTypeId = "PremiumTypeId";
    public static final String PremiumType_KEY_PremiumType = "PremiumType";
    public static final String PremiumType_KEY_activeFlag = "activeFlag";


    // Price

    public static final String Price_KEY_Price = "price";
    public static final String Price_KEY_priceId = "price_id";
    public static final String Price_KEY_TAT = "tat";
    public static final String Price_KEY_Min_Charges = "min_charges";
    public static final String Price_KEY_DeliveryOptionId = "delivery_opt_id";
    public static final String Price_KEY_ServiceTypeId = "service_type_id";
    public static final String Price_KEY_TimeSlabId = "time_slab_id";
    public static final String Price_KEY_Modified_date= "modified_date";
    public static final String Price_KEY_Created_date = "created_date";
    public static final String Price_KEY_Soft_del= "soft_del";

    //Timeslab_new
    public static final String Timeslab_KEY_timeSlabId = "timeSlabId";
    public static final String Timeslab_KEY_timeSlabName = "timeSlabName";
    public static final String Timeslab_KEY_fromTime = "fromTime";
    public static final String Timeslab_KEY_toTime = "toTime";

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


    // ServiceType
    public static final String ServiceType_KEY_serviceTypeId = "serviceTypeId";
    public static final String ServiceType_KEY_serviceType = "serviceType";
    public static final String ServiceType_KEY_activeFlag = "activeFlag";
    public static final String ServiceType_KEY_defaultSelectionFlag = "defaultSelectionFlag";
    public static final String ServiceType_KEY_lastModifiedDate = "lastModifiedDate";


    // Source
    public static final String Source_KEY_sourceId = "sourceId";
    public static final String Source_KEY_sourceName = "sourceName";
    public static final String Source_KEY_lastModifiedDate = "lastModifiedDate";


    // TATFEE
    public static final String TATFEE_KEY_tatFeeId = "tatFeeId";
    public static final String TATFEE_KEY_timeSlabId = "timeSlabId";
    public static final String TATFEE_KEY_deliveryTypeId = "deliveryTypeId";
    public static final String TATFEE_KEY_minimumcharges = "minimumcharges";
    public static final String TATFEE_KEY_tat = "tat";
    public static final String TATFEE_KEY_feePerMin = "feePerMin";
    public static final String TATFEE_KEY_activeFlag = "activeFlag";
    public static final String TATFEE_KEY_createddate = "createddate";
    public static final String TATFEE_KEY_modifiedDate = "modifiedDate";


    // Timestamp
    public static final String Timeslab_KEY_timestampId = "timestampId";
    public static final String Timeslab_KEY_timestampDuration = "timestampDuration";
    public static final String Timeslab_KEY_defaultSelectionFlag = "defaultSelectionFlag";
    public static final String Timeslab_KEY_activeFlag = "activeFlag";
    public static final String Timeslab_KEY_lastModifiedDate = "lastModifiedDate";


    // TranscriptionType
    public static final String TranscriptionType_KEY_transriptionTypeId = "transcriptionTypeId";
    public static final String TranscriptionType_KEY_transriptionType = "transcriptionType";
    public static final String TranscriptionType_KEY_defaultSelectionFlag = "defaultSelectionFlag";
    public static final String TranscriptionType_KEY_activeFlag = "activeFlag";
    public static final String TranscriptionType_KEY_lastModifiedDate = "lastModifiedDate";


    // UploadingMaster
    public static final String UploadingMaster_KEY_upMasterId = "upMasterId";
    public static final String UploadingMaster_KEY_upImage = "upImage";
    public static final String UploadingMaster_KEY_upType = "upType";
    public static final String UploadingMaster_KEY_lastModifiedDate = "lastModifiedDate";


    // User
    public static final String User_KEY_userId = "userId";
    public static final String User_KEY_userfirstName = "userfirstName";
    public static final String User_KEY_userEmail = "userEmail";
    public static final String User_KEY_userMobile = "userMobile";
    public static final String User_KEY_userName = "userName";
    public static final String User_KEY_notificationSettingType = "notificationSettingType";
    public static final String User_KEY_uploadingType = "uploadingType";
    public static final String User_KEY_MembershipID = "MembershipID";
    public static final String User_KEY_Clientsecondname = "Clientsecondname";
    public static final String User_KEY_NEWExisitingclient = "NEWExisitingclient";
    public static final String User_KEY_InvoicetypeId = "InvoicetypeId";
    public static final String User_KEY_invoiceDate = "invoiceDate";
    public static final String User_KEY_invoiceName = "invoiceName";
    public static final String User_KEY_Template = "Template";
    public static final String User_KEY_ClientInstructions = "ClientInstructions";
    public static final String User_KEY_PaymentIinstructionsonly = "PaymentIinstructionsonly";
    public static final String User_KEY_B2BB2C = "B2BB2C";
    public static final String User_KEY_FirstName = "FirstName";
    public static final String User_KEY_FirstNameForeign = "FirstNameForeign";
    public static final String User_KEY_LastNameEnglish = "LastNameEnglish";
    public static final String User_KEY_LastNameForeign = "LastNameForeign";
    public static final String User_KEY_OrganizationNameEnglish = "OrganizationNameEnglish";
    public static final String User_KEY_OrganizationNameForeignLanguage = "OrganizationNameForeignLanguage";
    public static final String User_KEY_Department = "Department";
    public static final String User_KEY_Designation = "Designation";
    public static final String User_KEY_Currency = "Currency";
    public static final String User_KEY_PrimaryEmail = "PrimaryEmail";
    public static final String User_KEY_SecondaryEmail = "SecondaryEmail";
    public static final String User_KEY_MobileNum = "MobileNum";
    public static final String User_KEY_Address = "Address";
    public static final String User_KEY_Landline = "Landline";
    public static final String User_KEY_Website = "Website";
    public static final String User_KEY_MobileNo = "MobileNo";
    public static final String User_KEY_City = "City";
    public static final String User_KEY_State = "State";
    public static final String User_KEY_Country = "Country";
    public static final String User_KEY_zipCode = "zipCode";
    public static final String User_KEY_ExtensionNo = "ExtensionNo";


    // ValueAddedServices
    public static final String ValueAddedServices_KEY_valueAddedServicesId = "valueAddedServicesId";
    public static final String ValueAddedServices_KEY_valueAddedService = "valueAddedService";
    public static final String ValueAddedServices_KEY_defaultSelectionFlag = "defaultSelectionFlag";
    public static final String ValueAddedServices_KEY_serviceTypeFlag = "serviceTypeFlag";
    public static final String ValueAddedServices_KEY_transriptionTypeFlag = "transcriptionTypeFlag";
    public static final String ValueAddedServices_KEY_timestampFlag = "timestampFlag";
    public static final String ValueAddedServices_KEY_activeFlag = "activeFlag";
    public static final String ValueAddedServices_KEY_lastModifiedDate = "lastModifiedDate";


    //timestamp
    public static final String TimeStampCalculation_KEY_timeStampCalculationId = "timeStampCalculationId";
    public static final String TimeStampCalculation_KEY_ServiceTypeId = "service_type";
    public static final String TimeStampCalculation_KEY_PercentageValue = "PercentageValue";
    public static final String TimeStampCalculation_KEY_DeliveryOptionId = "delivery_opt_id";
    public static final String TimeStampCalculation_KEY_timeStampId = "timestamp_id";


    //VAS Price
    public static final String VAS_KEY_PriceId = "VASPriceId";
    public static final String VAS_KEY_TimeSlabId = "TimeSlabId";
    public static final String VAS_KEY_VASId = "VASId";
    public static final String VAS_KEY_TAT = "TAT";
    public static final String VAS_KEY_Price = "Price";
    public static final String VAS_KEY_Min_Charges = "Min_Charges";


    //SchemeDetails
    public static final String Price = "price";
    public static final String Date_Time = "date_time";
    public static final String Scheme_Name = "scheme_name";
    public static final String Scheme_Duration = "scheme_duration";

    //TATForEnglish
    public static final String TatForEnglish_KEY_TimeSlabId = "";
    public static final String TatForEnglish_KEY_TimeSlabs = "";
    public static final String TatForEnglish_KEY_EconomyTAT = "";
    public static final String TatForEnglish_KEY_EconomyPrice = "";
    public static final String TatForEnglish_KEY_StandardTAT = "";
    public static final String TatForEnglish_KEY_StandardPrice = "";
    public static final String TatForEnglish_KEY_ExpressTAT = "";
    public static final String TatForEnglish_KEY_ExpressPrice = "";


//  Methods Of AssessmentReport

    public int getAssessmentReportCount() {
        String sql = "select count( AssessmentReportId ) as total from AssessmentReport 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  AssessmentReport:" + total);
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

    public LinkedList<AssessmentReport> getAssessmentReport() {
        String sql = "select *  from AssessmentReport ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<AssessmentReport> list = new LinkedList<AssessmentReport>();
        int i0 = c.getColumnIndex(AssessmentReport_KEY_assessmentReportId);
        int i1 = c.getColumnIndex(AssessmentReport_KEY_orderId);
        int i2 = c.getColumnIndex(AssessmentReport_KEY_assDate);
        int i3 = c.getColumnIndex(AssessmentReport_KEY_AudioQuality);
        int i4 = c.getColumnIndex(AssessmentReport_KEY_subjectArea);
        int i5 = c.getColumnIndex(AssessmentReport_KEY_result);
        int i6 = c.getColumnIndex(AssessmentReport_KEY_notesFrom);
        int i7 = c.getColumnIndex(AssessmentReport_KEY_notesFromTrans);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                AssessmentReport bean = new AssessmentReport();
                bean.setAssessmentReportId(c.getInt(i0));
                bean.setOrderId(c.getInt(i1));
                bean.setAssDate(getClearData(c.getString(i2)));
                bean.setAudioQuality(getClearData(c.getString(i3)));
                bean.setSubjectArea(getClearData(c.getString(i4)));
                bean.setResult(getClearData(c.getString(i5)));
                bean.setNotesFrom(getClearData(c.getString(i6)));
                bean.setNotesFromTrans(getClearData(c.getString(i7)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("AssessmentReport Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<AssessmentReport> getAssessmentReportList(int assessmentReportId) {
        String sql = "select *  from AssessmentReport where assessmentReportId=" + assessmentReportId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<AssessmentReport> list = new LinkedList<AssessmentReport>();
        int i0 = c.getColumnIndex(AssessmentReport_KEY_assessmentReportId);
        int i1 = c.getColumnIndex(AssessmentReport_KEY_orderId);
        int i2 = c.getColumnIndex(AssessmentReport_KEY_assDate);
        int i3 = c.getColumnIndex(AssessmentReport_KEY_AudioQuality);
        int i4 = c.getColumnIndex(AssessmentReport_KEY_subjectArea);
        int i5 = c.getColumnIndex(AssessmentReport_KEY_result);
        int i6 = c.getColumnIndex(AssessmentReport_KEY_notesFrom);
        int i7 = c.getColumnIndex(AssessmentReport_KEY_notesFromTrans);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                AssessmentReport bean = new AssessmentReport();
                bean.setAssessmentReportId(c.getInt(i0));
                bean.setOrderId(c.getInt(i1));
                bean.setAssDate(getClearData(c.getString(i2)));
                bean.setAudioQuality(getClearData(c.getString(i3)));
                bean.setSubjectArea(getClearData(c.getString(i4)));
                bean.setResult(getClearData(c.getString(i5)));
                bean.setNotesFrom(getClearData(c.getString(i6)));
                bean.setNotesFromTrans(getClearData(c.getString(i7)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("AssessmentReport Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addAssessmentReport(AssessmentReport bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into AssessmentReport  ( orderId, assDate, AudioQuality, subjectArea, result, notesFrom, notesFromTrans  ) values (  " + bean.getOrderId() + ",  '" + setClearData(bean.getAssDate()) + "',  '" + setClearData(bean.getAudioQuality()) + "',  '" + setClearData(bean.getSubjectArea()) + "',  '" + setClearData(bean.getResult()) + "',  '" + setClearData(bean.getNotesFrom()) + "',  '" + setClearData(bean.getNotesFromTrans()) + "' )";
                GlobalData.printMessage("AssessmentReport SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add AssessmentReport ", e);
        }
    }

    public void addAssessmentReportList(LinkedList<AssessmentReport> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                AssessmentReport bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into AssessmentReport  ( orderId, assDate, AudioQuality, subjectArea, result, notesFrom, notesFromTrans  ) values (  " + bean.getOrderId() + ",  '" + setClearData(bean.getAssDate()) + "',  '" + setClearData(bean.getAudioQuality()) + "',  '" + setClearData(bean.getSubjectArea()) + "',  '" + setClearData(bean.getResult()) + "',  '" + setClearData(bean.getNotesFrom()) + "',  '" + setClearData(bean.getNotesFromTrans()) + "' )";
                        GlobalData.printMessage("AssessmentReport SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add AssessmentReport ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("AssessmentReport", e);
            }
        }
    }

    public void deleteAssessmentReport() {
        String sql = "";
        try {


            sql = "delete from AssessmentReport  ";
            GlobalData.printMessage("AssessmentReport SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete AssessmentReport ", e);
        }
    }

    public void deleteAssessmentReport(int rowId) {
        String sql = "";
        try {


            sql = "delete from AssessmentReport where assessmentReportId=" + rowId;
            GlobalData.printMessage("AssessmentReport SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete AssessmentReport ", e);
        }
    }


//  Methods Of AssessmentSpeaker

    public int getAssessmentSpeakerCount() {
        String sql = "select count( AssessmentSpeakerId ) as total from AssessmentSpeaker 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  AssessmentSpeaker:" + total);
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

    public LinkedList<AssessmentSpeaker> getAssessmentSpeaker() {
        String sql = "select *  from AssessmentSpeaker ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<AssessmentSpeaker> list = new LinkedList<AssessmentSpeaker>();
        int i0 = c.getColumnIndex(AssessmentSpeaker_KEY_assessmentSpeakerId);
        int i1 = c.getColumnIndex(AssessmentSpeaker_KEY_assessmentReportId);
        int i2 = c.getColumnIndex(AssessmentSpeaker_KEY_speakerName);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                AssessmentSpeaker bean = new AssessmentSpeaker();
                bean.setAssessmentSpeakerId(c.getInt(i0));
                bean.setAssessmentReportId(c.getInt(i1));
                bean.setSpeakerName(getClearData(c.getString(i2)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("AssessmentSpeaker Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<AssessmentSpeaker> getAssessmentSpeakerList(int assessmentSpeakerId) {
        String sql = "select *  from AssessmentSpeaker where assessmentSpeakerId=" + assessmentSpeakerId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<AssessmentSpeaker> list = new LinkedList<AssessmentSpeaker>();
        int i0 = c.getColumnIndex(AssessmentSpeaker_KEY_assessmentSpeakerId);
        int i1 = c.getColumnIndex(AssessmentSpeaker_KEY_assessmentReportId);
        int i2 = c.getColumnIndex(AssessmentSpeaker_KEY_speakerName);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                AssessmentSpeaker bean = new AssessmentSpeaker();
                bean.setAssessmentSpeakerId(c.getInt(i0));
                bean.setAssessmentReportId(c.getInt(i1));
                bean.setSpeakerName(getClearData(c.getString(i2)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("AssessmentSpeaker Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addAssessmentSpeaker(AssessmentSpeaker bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into AssessmentSpeaker  ( assessmentReportId, speakerName  ) values (  " + bean.getAssessmentReportId() + ",  '" + setClearData(bean.getSpeakerName()) + "' )";
                GlobalData.printMessage("AssessmentSpeaker SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add AssessmentSpeaker ", e);
        }
    }

    public void addAssessmentSpeakerList(LinkedList<AssessmentSpeaker> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                AssessmentSpeaker bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into AssessmentSpeaker  ( assessmentReportId, speakerName  ) values (  " + bean.getAssessmentReportId() + ",  '" + setClearData(bean.getSpeakerName()) + "' )";
                        GlobalData.printMessage("AssessmentSpeaker SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add AssessmentSpeaker ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("AssessmentSpeaker", e);
            }
        }
    }

    public void deleteAssessmentSpeaker() {
        String sql = "";
        try {


            sql = "delete from AssessmentSpeaker  ";
            GlobalData.printMessage("AssessmentSpeaker SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete AssessmentSpeaker ", e);
        }
    }

    public void deleteAssessmentSpeaker(int rowId) {
        String sql = "";
        try {


            sql = "delete from AssessmentSpeaker where assessmentSpeakerId=" + rowId;
            GlobalData.printMessage("AssessmentSpeaker SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete AssessmentSpeaker ", e);
        }
    }


//  Methods Of DeliveryOption

    public int getDeliveryOptionCount() {
        String sql = "select count( DeliveryOptionId ) as total from DeliveryOption 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  DeliveryOption:" + total);
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


//  Methods Of DeliverySpeed

    public int getDeliverySpeedCount() {
        String sql = "select count( DeliverySpeedId ) as total from DeliverySpeed 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  DeliverySpeed:" + total);
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



//  Methods Of Device

    public int getDeviceCount() {
        String sql = "select count( DeviceId ) as total from Device 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Device:" + total);
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

    public LinkedList<Device> getDevice() {
        String sql = "select *  from Device ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Device> list = new LinkedList<Device>();
        int i0 = c.getColumnIndex(Device_KEY_deviceId);
        int i1 = c.getColumnIndex(Device_KEY_userId);
        int i2 = c.getColumnIndex(Device_KEY_IMEI);
        int i3 = c.getColumnIndex(Device_KEY_deviceModel);
        int i4 = c.getColumnIndex(Device_KEY_Key);
        int i5 = c.getColumnIndex(Device_KEY_deveicetype);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Device bean = new Device();
                bean.setDeviceId(c.getInt(i0));
                bean.setUserId(c.getInt(i1));
                bean.setIMEI(getClearData(c.getString(i2)));
                bean.setDeviceModel(getClearData(c.getString(i3)));
                bean.setKey(getClearData(c.getString(i4)));
                bean.setDeveicetype(c.getInt(i5));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Device Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Device> getDeviceList(int deviceId) {
        String sql = "select *  from Device where deviceId=" + deviceId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Device> list = new LinkedList<Device>();
        int i0 = c.getColumnIndex(Device_KEY_deviceId);
        int i1 = c.getColumnIndex(Device_KEY_userId);
        int i2 = c.getColumnIndex(Device_KEY_IMEI);
        int i3 = c.getColumnIndex(Device_KEY_deviceModel);
        int i4 = c.getColumnIndex(Device_KEY_Key);
        int i5 = c.getColumnIndex(Device_KEY_deveicetype);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Device bean = new Device();
                bean.setDeviceId(c.getInt(i0));
                bean.setUserId(c.getInt(i1));
                bean.setIMEI(getClearData(c.getString(i2)));
                bean.setDeviceModel(getClearData(c.getString(i3)));
                bean.setKey(getClearData(c.getString(i4)));
                bean.setDeveicetype(c.getInt(i5));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Device Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addDevice(Device bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Device  ( userId, IMEI, deviceModel, Key, deveicetype  ) values (  " + bean.getUserId() + ",  '" + setClearData(bean.getIMEI()) + "',  '" + setClearData(bean.getDeviceModel()) + "',  '" + setClearData(bean.getKey()) + "',  " + bean.getDeveicetype() + " )";
                GlobalData.printMessage("Device SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Device ", e);
        }
    }

    public void addDeviceList(LinkedList<Device> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Device bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Device  ( userId, IMEI, deviceModel, Key, deveicetype  ) values (  " + bean.getUserId() + ",  '" + setClearData(bean.getIMEI()) + "',  '" + setClearData(bean.getDeviceModel()) + "',  '" + setClearData(bean.getKey()) + "',  " + bean.getDeveicetype() + " )";
                        GlobalData.printMessage("Device SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Device ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Device", e);
            }
        }
    }

    public void deleteDevice() {
        String sql = "";
        try {


            sql = "delete from Device  ";
            GlobalData.printMessage("Device SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Device ", e);
        }
    }

    public void deleteDevice(int rowId) {
        String sql = "";
        try {


            sql = "delete from Device where deviceId=" + rowId;
            GlobalData.printMessage("Device SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Device ", e);
        }
    }


//  Methods Of Discount

    public int getDiscountCount() {
        String sql = "select count( DiscountId ) as total from Discount 	";
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


    int discount_id = 0;
    String discount_type_id = "";
    String service_type_id = "";
    String delivery_opt_id = "";
    String vas_id = "";
    String min_time = "";
    String max_time = "";
    String start_date = "";
    String end_date = "";
    String  discount ="" ;
    String status = "";
    String modified_date = "";
    String created_date = "";
    String soft_del = "";

    public LinkedList<Discount> getDiscount() {
        String sql = "select *  from Discount ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Discount> list = new LinkedList<Discount>();
        int i0 = c.getColumnIndex(Discount_KEY_discountId);
        int i1 = c.getColumnIndex(Discount_KEY_serviceTypeId);
        int i2 = c.getColumnIndex(Discount_KEY_deliveryTypeId);
        int i3 = c.getColumnIndex(Discount_KEY_valuAddedServiceId);
        int i4 = c.getColumnIndex(Discount_KEY_minMin);
        int i5 = c.getColumnIndex(Discount_KEY_maxMin);
        int i6 = c.getColumnIndex(Discount_KEY_startDate);
        int i7 = c.getColumnIndex(Discount_KEY_endDate);
        int i8 = c.getColumnIndex(Discount_KEY_discount);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Discount bean = new Discount();
                bean.setDiscount_id(c.getInt(i0));
                bean.setService_type_id(c.getString(i1));
                bean.setDelivery_opt_id(c.getString(i2));
                bean.setVas_id(c.getString(i3));
                bean.setMin_time(c.getString(i4));
                bean.setMax_time(c.getString(i5));
                bean.setStart_date(getClearData(c.getString(i6)));
                bean.setEnd_date(getClearData(c.getString(i7)));
                bean.setDiscount(getClearData(c.getString(i8)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Discount Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Discount> getDiscountList(int discountId) {
        String sql = "select *  from Discount where discountId=" + discountId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Discount> list = new LinkedList<Discount>();
        int i0 = c.getColumnIndex(Discount_KEY_discountId);
        int i1 = c.getColumnIndex(Discount_KEY_serviceTypeId);
        int i2 = c.getColumnIndex(Discount_KEY_deliveryTypeId);
        int i3 = c.getColumnIndex(Discount_KEY_valuAddedServiceId);
        int i4 = c.getColumnIndex(Discount_KEY_minMin);
        int i5 = c.getColumnIndex(Discount_KEY_maxMin);
        int i6 = c.getColumnIndex(Discount_KEY_startDate);
        int i7 = c.getColumnIndex(Discount_KEY_endDate);
        int i8 = c.getColumnIndex(Discount_KEY_discount);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Discount bean = new Discount();
                bean.setDiscount_id(c.getInt(i0));
                bean.setService_type_id(c.getString(i1));
                bean.setDelivery_opt_id(c.getString(i2));
                bean.setVas_id(c.getString(i3));
                bean.setMin_time(c.getString(i4));
                bean.setMax_time(c.getString(i5));
                bean.setStart_date(getClearData(c.getString(i6)));
                bean.setEnd_date(getClearData(c.getString(i7)));
                bean.setDiscount(getClearData(c.getString(i8)));

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


          //  sql = "insert into Discount  ( serviceTypeId, deliveryTypeId, valuAddedServiceId, minMin, maxMin, startDate, endDate, discount  ) values (  " + bean.getService_type_id() + ",  " + bean.getDeliveryTypeId() + ",  " + bean.getValuAddedServiceId() + ",  " + bean.getMinMin() + ",  " + bean.getMaxMin() + ",  '" + setClearData(bean.getStartDate()) + "',  '" + setClearData(bean.getEndDate()) + "',  '" + setClearData(bean.getDiscount()) + "' )";
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


                      //  sql = "insert into Discount  ( serviceTypeId, deliveryTypeId, valuAddedServiceId, minMin, maxMin, startDate, endDate, discount  ) values (  " + bean.getService_type_id() + ",  " + bean.getDeliveryTypeId() + ",  " + bean.getValuAddedServiceId() + ",  " + bean.getMinMin() + ",  " + bean.getMaxMin() + ",  '" + setClearData(bean.getStartDate()) + "',  '" + setClearData(bean.getEndDate()) + "',  '" + setClearData(bean.getDiscount()) + "' )";
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


            sql = "delete from Discount where discountId=" + rowId;
            GlobalData.printMessage("Discount SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Discount ", e);
        }
    }



//  Methods Of Feedback

    public int getFeedbackCount() {
        String sql = "select count( FeedbackId ) as total from Feedback 	";
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
        String sql = "select *  from Feedback ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Feedback> list = new LinkedList<Feedback>();
        int i0 = c.getColumnIndex(Feedback_KEY_feedbackId);
        int i1 = c.getColumnIndex(Feedback_KEY_userId);
        int i2 = c.getColumnIndex(Feedback_KEY_orderId);
        int i3 = c.getColumnIndex(Feedback_KEY_feedback);
        int i4 = c.getColumnIndex(Feedback_KEY_TEXT);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Feedback bean = new Feedback();
//                bean.setFeedbackId(c.getInt(i0));
//                bean.setUserId(c.getInt(i1));
//                bean.setOrderId(c.getInt(i2));
//                bean.setFeedback(getClearData(c.getString(i3)));
//                bean.setTEXT(getClearData(c.getString(i4)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Feedback Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Feedback> getFeedbackList(int feedbackId) {
        String sql = "select *  from Feedback where feedbackId=" + feedbackId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Feedback> list = new LinkedList<Feedback>();
        int i0 = c.getColumnIndex(Feedback_KEY_feedbackId);
        int i1 = c.getColumnIndex(Feedback_KEY_userId);
        int i2 = c.getColumnIndex(Feedback_KEY_orderId);
        int i3 = c.getColumnIndex(Feedback_KEY_feedback);
        int i4 = c.getColumnIndex(Feedback_KEY_TEXT);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Feedback bean = new Feedback();
//                bean.setFeedbackId(c.getInt(i0));
//                bean.setUserId(c.getInt(i1));
//                bean.setOrderId(c.getInt(i2));
//                bean.setFeedback(getClearData(c.getString(i3)));
//                bean.setTEXT(getClearData(c.getString(i4)));

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


           //     sql = "insert into Feedback  ( userId, orderId, feedback, TEXT  ) values (  " + bean.getUserId() + ",  " + bean.getOrderId() + ",  '" + setClearData(bean.getFeedback()) + "',  '" + setClearData(bean.getTEXT()) + "' )";
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


//                        sql = "insert into Feedback  ( userId, orderId, feedback, TEXT  ) values (  " + bean.getUserId() + ",  " + bean.getOrderId() + ",  '" + setClearData(bean.getFeedback()) + "',  '" + setClearData(bean.getTEXT()) + "' )";
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


            sql = "delete from Feedback where feedbackId=" + rowId;
            GlobalData.printMessage("Feedback SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Feedback ", e);
        }
    }


//  Methods Of Holidays

    public int getHolidaysCount() {
        String sql = "select count( holidayId ) as total from Holidays 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Holidays:" + total);
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

    public LinkedList<Holidays> getHolidays() {
        String sql = "select *  from Holidays ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Holidays> list = new LinkedList<Holidays>();
        int i0 = c.getColumnIndex(Holidays_KEY_holidayId);
        int i1 = c.getColumnIndex(Holidays_KEY_holidayDate);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Holidays bean = new Holidays();
//                bean.setHolidayId(c.getInt(i0));
//                bean.setHolidayDate(c.getString(i1));


                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Holidays Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Holidays> getHolidaysList(int holidayId) {
        String sql = "select *  from Holidays where holidayId=" + holidayId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Holidays> list = new LinkedList<Holidays>();
        int i0 = c.getColumnIndex(Holidays_KEY_holidayId);
        int i1 = c.getColumnIndex(Holidays_KEY_holidayDate);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Holidays bean = new Holidays();


//                bean.setHolidayId(c.getInt(i0));
//                bean.setHolidayDate(c.getString(i1));


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


                sql = "insert into Holidays  (holidayDate) values (  " + bean.getHoliday_date() + " )";
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


                        sql = "insert into Holidays  ( holidayDate  ) values (  " + bean.getHoliday_date() + " )";
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


            sql = "delete from Holidays where holidayId=" + rowId;
            GlobalData.printMessage("Holidays SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Holidays ", e);
        }
    }




//  Methods Of Notification

    public int getNotificationCount() {
        String sql = "select count( NotificationId ) as total from Notification 	";
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

    public LinkedList<Notification> getNotification() {
        String sql = "select *  from Notification ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Notification> list = new LinkedList<Notification>();
        int i0 = c.getColumnIndex(Notification_KEY_notificationId);
        int i1 = c.getColumnIndex(Notification_KEY_notificationTypeId);
        int i2 = c.getColumnIndex(Notification_KEY_notificationText);
        int i3 = c.getColumnIndex(Notification_KEY_orderid);
        int i4 = c.getColumnIndex(Notification_KEY_activeFlag);
        int i5 = c.getColumnIndex(Notification_KEY_createdDate);
        int i6 = c.getColumnIndex(Notification_KEY_modifiedDate);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Notification bean = new Notification();
//                bean.setNotificationId(c.getInt(i0));
//                bean.setNotificationTypeId(c.getInt(i1));
//                bean.setNotificationText(getClearData(c.getString(i2)));
//                bean.setOrderid(c.getInt(i3));
//                bean.setActiveFlag(c.getInt(i4));
//                bean.setCreatedDate(getClearData(c.getString(i5)));
//                bean.setModifiedDate(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Notification Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Notification> getNotificationList(int notificationId) {
        String sql = "select *  from Notification where notificationId=" + notificationId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Notification> list = new LinkedList<Notification>();
        int i0 = c.getColumnIndex(Notification_KEY_notificationId);
        int i1 = c.getColumnIndex(Notification_KEY_notificationTypeId);
        int i2 = c.getColumnIndex(Notification_KEY_notificationText);
        int i3 = c.getColumnIndex(Notification_KEY_orderid);
        int i4 = c.getColumnIndex(Notification_KEY_activeFlag);
        int i5 = c.getColumnIndex(Notification_KEY_createdDate);
        int i6 = c.getColumnIndex(Notification_KEY_modifiedDate);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Notification bean = new Notification();
//                bean.setNotificationId(c.getInt(i0));
//                bean.setNotificationTypeId(c.getInt(i1));
//                bean.setNotificationText(getClearData(c.getString(i2)));
//                bean.setOrderid(c.getInt(i3));
//                bean.setActiveFlag(c.getInt(i4));
//                bean.setCreatedDate(getClearData(c.getString(i5)));
//                bean.setModifiedDate(getClearData(c.getString(i6)));

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


//                sql = "insert into Notification  ( notificationTypeId, notificationText, orderid, activeFlag, createdDate, modifiedDate  ) values (  " + bean.getNotificationTypeId() + ",  '" + setClearData(bean.getNotificationText()) + "',  " + bean.getOrderid() + ",  " + bean.getActiveFlag() + ",  '" + setClearData(bean.getCreatedDate()) + "',  '" + setClearData(bean.getModifiedDate()) + "' )";
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


//                        sql = "insert into Notification  ( notificationTypeId, notificationText, orderid, activeFlag, createdDate, modifiedDate  ) values (  " + bean.getNotificationTypeId() + ",  '" + setClearData(bean.getNotificationText()) + "',  " + bean.getOrderid() + ",  " + bean.getActiveFlag() + ",  '" + setClearData(bean.getCreatedDate()) + "',  '" + setClearData(bean.getModifiedDate()) + "' )";
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


            sql = "delete from Notification where notificationId=" + rowId;
            GlobalData.printMessage("Notification SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Notification ", e);
        }
    }




//  Methods Of OrderFreeServices

    public int getOrderFreeServicesCount() {
        String sql = "select count( OrderFreeServicesId ) as total from OrderFreeServices 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  OrderFreeServices:" + total);
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

    public LinkedList<OrderFreeServices> getOrderFreeServices() {
        String sql = "select *  from OrderFreeServices ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<OrderFreeServices> list = new LinkedList<OrderFreeServices>();
        int i0 = c.getColumnIndex(OrderFreeServices_KEY_orderFreeServId);
        int i1 = c.getColumnIndex(OrderFreeServices_KEY_orderId);
        int i2 = c.getColumnIndex(OrderFreeServices_KEY_freeServId);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                OrderFreeServices bean = new OrderFreeServices();
                bean.setOrderFreeServId(c.getInt(i0));
                bean.setOrderId(c.getInt(i1));
                bean.setFreeServId(c.getInt(i2));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("OrderFreeServices Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<OrderFreeServices> getOrderFreeServicesList(int orderFreeServId) {
        String sql = "select *  from OrderFreeServices where orderFreeServId=" + orderFreeServId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<OrderFreeServices> list = new LinkedList<OrderFreeServices>();
        int i0 = c.getColumnIndex(OrderFreeServices_KEY_orderFreeServId);
        int i1 = c.getColumnIndex(OrderFreeServices_KEY_orderId);
        int i2 = c.getColumnIndex(OrderFreeServices_KEY_freeServId);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                OrderFreeServices bean = new OrderFreeServices();
                bean.setOrderFreeServId(c.getInt(i0));
                bean.setOrderId(c.getInt(i1));
                bean.setFreeServId(c.getInt(i2));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("OrderFreeServices Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addOrderFreeServices(OrderFreeServices bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into OrderFreeServices  ( orderId, freeServId  ) values (  " + bean.getOrderId() + ",  " + bean.getFreeServId() + " )";
                GlobalData.printMessage("OrderFreeServices SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add OrderFreeServices ", e);
        }
    }

    public void addOrderFreeServicesList(LinkedList<OrderFreeServices> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                OrderFreeServices bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into OrderFreeServices  ( orderId, freeServId  ) values (  " + bean.getOrderId() + ",  " + bean.getFreeServId() + " )";
                        GlobalData.printMessage("OrderFreeServices SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add OrderFreeServices ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("OrderFreeServices", e);
            }
        }
    }

    public void deleteOrderFreeServices() {
        String sql = "";
        try {


            sql = "delete from OrderFreeServices  ";
            GlobalData.printMessage("OrderFreeServices SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete OrderFreeServices ", e);
        }
    }

    public void deleteOrderFreeServices(int rowId) {
        String sql = "";
        try {


            sql = "delete from OrderFreeServices where orderFreeServId=" + rowId;
            GlobalData.printMessage("OrderFreeServices SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete OrderFreeServices ", e);
        }
    }


//  Methods Of OrderHistory

    public int getOrderHistoryCount() {
        String sql = "select count( OrderHistoryId ) as total from OrderHistory 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  OrderHistory:" + total);
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

    public LinkedList<OrderHistory> getOrderHistory() {
        String sql = "select *  from OrderHistory ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<OrderHistory> list = new LinkedList<OrderHistory>();
        int i0 = c.getColumnIndex(OrderHistory_KEY_orderHistoryId);
        int i1 = c.getColumnIndex(OrderHistory_KEY_orderId);
        int i2 = c.getColumnIndex(OrderHistory_KEY_uploadingMasterId);
        int i3 = c.getColumnIndex(OrderHistory_KEY_orderStatus);
        int i4 = c.getColumnIndex(OrderHistory_KEY_price);
        int i5 = c.getColumnIndex(OrderHistory_KEY_deliveryDate);
        int i6 = c.getColumnIndex(OrderHistory_KEY_orderHistoryNo);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                OrderHistory bean = new OrderHistory();
                bean.setOrderHistoryId(c.getInt(i0));
                bean.setOrderId(c.getInt(i1));
                bean.setUploadingMasterId(c.getInt(i2));
                bean.setOrderStatus(getClearData(c.getString(i3)));
                bean.setPrice(getClearData(c.getString(i4)));
                bean.setDeliveryDate(getClearData(c.getString(i5)));
                bean.setOrderHistoryNo(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("OrderHistory Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<OrderHistory> getOrderHistoryList(int orderHistoryId) {
        String sql = "select *  from OrderHistory where orderHistoryId=" + orderHistoryId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<OrderHistory> list = new LinkedList<OrderHistory>();
        int i0 = c.getColumnIndex(OrderHistory_KEY_orderHistoryId);
        int i1 = c.getColumnIndex(OrderHistory_KEY_orderId);
        int i2 = c.getColumnIndex(OrderHistory_KEY_uploadingMasterId);
        int i3 = c.getColumnIndex(OrderHistory_KEY_orderStatus);
        int i4 = c.getColumnIndex(OrderHistory_KEY_price);
        int i5 = c.getColumnIndex(OrderHistory_KEY_deliveryDate);
        int i6 = c.getColumnIndex(OrderHistory_KEY_orderHistoryNo);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                OrderHistory bean = new OrderHistory();
                bean.setOrderHistoryId(c.getInt(i0));
                bean.setOrderId(c.getInt(i1));
                bean.setUploadingMasterId(c.getInt(i2));
                bean.setOrderStatus(getClearData(c.getString(i3)));
                bean.setPrice(getClearData(c.getString(i4)));
                bean.setDeliveryDate(getClearData(c.getString(i5)));
                bean.setOrderHistoryNo(getClearData(c.getString(i6)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("OrderHistory Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addOrderHistory(OrderHistory bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into OrderHistory  ( orderId, uploadingMasterId, orderStatus, price, deliveryDate, orderHistoryNo  ) values (  " + bean.getOrderId() + ",  " + bean.getUploadingMasterId() + ",  '" + setClearData(bean.getOrderStatus()) + "',  '" + setClearData(bean.getPrice()) + "',  '" + setClearData(bean.getDeliveryDate()) + "',  '" + setClearData(bean.getOrderHistoryNo()) + "' )";
                GlobalData.printMessage("OrderHistory SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add OrderHistory ", e);
        }
    }

    public void addOrderHistoryList(LinkedList<OrderHistory> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                OrderHistory bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into OrderHistory  ( orderId, uploadingMasterId, orderStatus, price, deliveryDate, orderHistoryNo  ) values (  " + bean.getOrderId() + ",  " + bean.getUploadingMasterId() + ",  '" + setClearData(bean.getOrderStatus()) + "',  '" + setClearData(bean.getPrice()) + "',  '" + setClearData(bean.getDeliveryDate()) + "',  '" + setClearData(bean.getOrderHistoryNo()) + "' )";
                        GlobalData.printMessage("OrderHistory SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add OrderHistory ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("OrderHistory", e);
            }
        }
    }

    public void deleteOrderHistory() {
        String sql = "";
        try {


            sql = "delete from OrderHistory  ";
            GlobalData.printMessage("OrderHistory SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete OrderHistory ", e);
        }
    }

    public void deleteOrderHistory(int rowId) {
        String sql = "";
        try {


            sql = "delete from OrderHistory where orderHistoryId=" + rowId;
            GlobalData.printMessage("OrderHistory SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete OrderHistory ", e);
        }
    }


//  Methods Of OrderRec

    public int getOrderRecCount() {
        String sql = "select count( OrderRecId ) as total from OrderRec 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);

                    GlobalData.printMessage("Total  OrderRec:" + total);
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

    public LinkedList<OrderRec> getOrderRec() {
        String sql = "select *  from OrderRec ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<OrderRec> list = new LinkedList<OrderRec>();
        int i0 = c.getColumnIndex(OrderRec_KEY_orderId);
        int i1 = c.getColumnIndex(OrderRec_KEY_assignmentNo);
        int i2 = c.getColumnIndex(OrderRec_KEY_clientInstruction);
        int i3 = c.getColumnIndex(OrderRec_KEY_numberOfFiles);
        int i4 = c.getColumnIndex(OrderRec_KEY_serviceTypeId);
        int i5 = c.getColumnIndex(OrderRec_KEY_transcriptionTypeId);
        int i6 = c.getColumnIndex(OrderRec_KEY_deliveryOptionId);
        int i7 = c.getColumnIndex(OrderRec_KEY_orderStatusId);
        int i8 = c.getColumnIndex(OrderRec_KEY_valueAddedservId);
        int i9 = c.getColumnIndex(OrderRec_KEY_timestampId);
        int i10 = c.getColumnIndex(OrderRec_KEY_invoiceTypeId);
        int i11 = c.getColumnIndex(OrderRec_KEY_outputFormatId);
        int i12 = c.getColumnIndex(OrderRec_KEY_subjectOfFile);
        int i13 = c.getColumnIndex(OrderRec_KEY_instructions);
        int i14 = c.getColumnIndex(OrderRec_KEY_connectionType);
        int i15 = c.getColumnIndex(OrderRec_KEY_createDate);
        int i16 = c.getColumnIndex(OrderRec_KEY_totalDuration);
        int i17 = c.getColumnIndex(OrderRec_KEY_deliveryDate);
        int i18 = c.getColumnIndex(OrderRec_KEY_totalFees);
        int i19 = c.getColumnIndex(OrderRec_KEY_transcriptionLink);
        int i20 = c.getColumnIndex(OrderRec_KEY_orderdate);
        int i21 = c.getColumnIndex(OrderRec_KEY_modifiedDate);
        int i22 = c.getColumnIndex(OrderRec_KEY_fileExtension);
        int i23 = c.getColumnIndex(OrderRec_KEY_orderPlaced);
        int i24 = c.getColumnIndex(OrderRec_KEY_completeOrderDate);
        int i25 = c.getColumnIndex(OrderRec_KEY_completeOrderDetails);
        int i26 = c.getColumnIndex(OrderRec_KEY_excludedTATdate);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                OrderRec bean = new OrderRec();
                bean.setOrderId(c.getInt(i0));
                bean.setAssignmentNo(getClearData(c.getString(i1)));
                bean.setClientInstruction(getClearData(c.getString(i2)));
                bean.setNumberOfFiles(c.getInt(i3));
                bean.setServiceTypeId(c.getInt(i4));
                bean.setTranscriptionTypeId(c.getInt(i5));
                bean.setDeliveryOptionId(c.getInt(i6));
                bean.setOrderStatusId(c.getInt(i7));
                bean.setValueAddedservId(c.getInt(i8));
                bean.setTimestampId(c.getInt(i9));
                bean.setInvoiceTypeId(c.getInt(i10));
                bean.setOutputFormatId(c.getInt(i11));
                bean.setSubjectOfFile(getClearData(c.getString(i12)));
                bean.setInstructions(getClearData(c.getString(i13)));
                bean.setConnectionType(c.getInt(i14));
                bean.setCreateDate(getClearData(c.getString(i15)));
                bean.setTotalDuration(getClearData(c.getString(i16)));
                bean.setDeliveryDate(getClearData(c.getString(i17)));
                bean.setTotalFees(getClearData(c.getString(i18)));
                bean.setTranscriptionLink(getClearData(c.getString(i19)));
                bean.setOrderdate(getClearData(c.getString(i20)));
                bean.setModifiedDate(getClearData(c.getString(i21)));
                bean.setFileExtension(getClearData(c.getString(i22)));
                bean.setOrderPlaced(getClearData(c.getString(i23)));
                bean.setCompleteOrderDate(getClearData(c.getString(i24)));
                bean.setCompleteOrderDetails(getClearData(c.getString(i25)));
                bean.setExcludedTATdate(getClearData(c.getString(i26)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("OrderRec Read:", e);
            }
        }
        c.close();
        return list;
    }

    public OrderRec  getOrderRecList(int orderId) {
        String sql = "select *  from OrderRec where orderId=" + orderId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<OrderRec> list = new LinkedList<OrderRec>();
        int i0 = c.getColumnIndex(OrderRec_KEY_orderId);
        int i1 = c.getColumnIndex(OrderRec_KEY_assignmentNo);
        int i2 = c.getColumnIndex(OrderRec_KEY_clientInstruction);
        int i3 = c.getColumnIndex(OrderRec_KEY_numberOfFiles);
        int i4 = c.getColumnIndex(OrderRec_KEY_serviceTypeId);
        int i5 = c.getColumnIndex(OrderRec_KEY_transcriptionTypeId);
        int i6 = c.getColumnIndex(OrderRec_KEY_deliveryOptionId);
        int i7 = c.getColumnIndex(OrderRec_KEY_orderStatusId);
        int i8 = c.getColumnIndex(OrderRec_KEY_valueAddedservId);
        int i9 = c.getColumnIndex(OrderRec_KEY_timestampId);
        int i10 = c.getColumnIndex(OrderRec_KEY_invoiceTypeId);
        int i11 = c.getColumnIndex(OrderRec_KEY_outputFormatId);
        int i12 = c.getColumnIndex(OrderRec_KEY_subjectOfFile);
        int i13 = c.getColumnIndex(OrderRec_KEY_instructions);
        int i14 = c.getColumnIndex(OrderRec_KEY_connectionType);
        int i15 = c.getColumnIndex(OrderRec_KEY_createDate);
        int i16 = c.getColumnIndex(OrderRec_KEY_totalDuration);
        int i17 = c.getColumnIndex(OrderRec_KEY_deliveryDate);
        int i18 = c.getColumnIndex(OrderRec_KEY_totalFees);
        int i19 = c.getColumnIndex(OrderRec_KEY_transcriptionLink);
        int i20 = c.getColumnIndex(OrderRec_KEY_orderdate);
        int i21 = c.getColumnIndex(OrderRec_KEY_modifiedDate);
        int i22 = c.getColumnIndex(OrderRec_KEY_fileExtension);
        int i23 = c.getColumnIndex(OrderRec_KEY_orderPlaced);
        int i24 = c.getColumnIndex(OrderRec_KEY_completeOrderDate);
        int i25 = c.getColumnIndex(OrderRec_KEY_completeOrderDetails);
        int i26 = c.getColumnIndex(OrderRec_KEY_excludedTATdate);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                OrderRec bean = new OrderRec();
                bean.setOrderId(c.getInt(i0));
                bean.setAssignmentNo(getClearData(c.getString(i1)));
                bean.setClientInstruction(getClearData(c.getString(i2)));
                bean.setNumberOfFiles(c.getInt(i3));
                bean.setServiceTypeId(c.getInt(i4));
                bean.setTranscriptionTypeId(c.getInt(i5));
                bean.setDeliveryOptionId(c.getInt(i6));
                bean.setOrderStatusId(c.getInt(i7));
                bean.setValueAddedservId(c.getInt(i8));
                bean.setTimestampId(c.getInt(i9));
                bean.setInvoiceTypeId(c.getInt(i10));
                bean.setOutputFormatId(c.getInt(i11));
                bean.setSubjectOfFile(getClearData(c.getString(i12)));
                bean.setInstructions(getClearData(c.getString(i13)));
                bean.setConnectionType(c.getInt(i14));
                bean.setCreateDate(getClearData(c.getString(i15)));
                bean.setTotalDuration(getClearData(c.getString(i16)));
                bean.setDeliveryDate(getClearData(c.getString(i17)));
                bean.setTotalFees(getClearData(c.getString(i18)));
                bean.setTranscriptionLink(getClearData(c.getString(i19)));
                bean.setOrderdate(getClearData(c.getString(i20)));
                bean.setModifiedDate(getClearData(c.getString(i21)));
                bean.setFileExtension(getClearData(c.getString(i22)));
                bean.setOrderPlaced(getClearData(c.getString(i23)));
                bean.setCompleteOrderDate(getClearData(c.getString(i24)));
                bean.setCompleteOrderDetails(getClearData(c.getString(i25)));
                bean.setExcludedTATdate(getClearData(c.getString(i26)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("OrderRec Read:", e);
            }
        }
        c.close();
        return list.get(0);
    }

    public void addOrderRec(OrderRec bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into OrderRec  ( assignmentNo, clientInstruction, numberOfFiles, serviceTypeId, transcriptionTypeId, deliveryOptionId, orderStatusId, valueAddedservId, timestampId, invoiceTypeId, outputFormatId, subjectOfFile, instructions, connectionType, createDate, totalDuration, deliveryDate, totalFees, transcriptionLink, orderdate, modifiedDate, fileExtension, orderPlaced, completeOrderDate, completeOrderDetails, excludedTATdate ,flag) values (  '" + setClearData(bean.getAssignmentNo()) + "',  '" + setClearData(bean.getClientInstruction()) + "',  " + bean.getNumberOfFiles() + ",  " + bean.getServiceTypeId() + ",  " + bean.getTranscriptionTypeId() + ",  " + bean.getDeliveryOptionId() + ",  " + bean.getOrderStatusId() + ",  " + bean.getValueAddedservId() + ",  " + bean.getTimestampId() + ",  " + bean.getInvoiceTypeId() + ",  " + bean.getOutputFormatId() + ",  '" + bean.getSubjectOfFile() + "',  '" + setClearData(bean.getInstructions()) + "',  " + bean.getConnectionType() + ",  '" + bean.getCreateDate() + "',  '" + setClearData(bean.getTotalDuration()) + "',  '" + setClearData(bean.getDeliveryDate()) + "',  '" + setClearData(bean.getTotalFees()) + "',  '" + setClearData(bean.getTranscriptionLink()) + "',  '" + setClearData(bean.getOrderdate()) + "',  '" + setClearData(bean.getModifiedDate()) + "',  '" + setClearData(bean.getFileExtension()) + "',  '" + setClearData(bean.getOrderPlaced()) + "',  '" + setClearData(bean.getCompleteOrderDate()) + "',  '" + setClearData(bean.getCompleteOrderDetails()) + "',  '" + setClearData(bean.getExcludedTATdate()) + "'," + bean.getFlag() + " )";
                GlobalData.printMessage("OrderRec SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add OrderRec ", e);
        }
    }


    public void updateOrderRec(OrderRec bean) {
        String sql = "";
        try {
            if (bean != null) {
                sql = "update OrderRec  set assignmentNo='" + setClearData(bean.getAssignmentNo()) + "', clientInstruction='" + setClearData(bean.getClientInstruction()) + "', numberOfFiles= " + bean.getNumberOfFiles() + ", serviceTypeId= " + bean.getServiceTypeId() + ", transcriptionTypeId=" + bean.getTranscriptionTypeId() + ", deliveryOptionId=" + bean.getDeliveryOptionId() + ", orderStatusId=" + bean.getOrderStatusId() + ", valueAddedservId=" + bean.getValueAddedservId() + ", timestampId=" + bean.getTimestampId() + ", invoiceTypeId= " + bean.getInvoiceTypeId() + ", outputFormatId=" + bean.getOutputFormatId() + ", subjectOfFile='" + bean.getSubjectOfFile() + "', instructions='" + setClearData(bean.getInstructions()) + "', connectionType=" + bean.getConnectionType() + ", totalDuration='" + bean.getTotalDuration() + "', deliveryDate='" + bean.getDeliveryDate() + "', totalFees=" + bean.getTotalFees() + ", transcriptionLink='" + setClearData(bean.getTranscriptionLink()) + "', orderdate='" + setClearData(bean.getOrderdate()) + "', modifiedDate='" + setClearData(bean.getModifiedDate()) + "', completeOrderDate='" + setClearData(bean.getCompleteOrderDate()) + "', completeOrderDetails='" + setClearData(bean.getCompleteOrderDetails()) + "', excludedTATdate='" + setClearData(bean.getExcludedTATdate()) + "' ,flag=" + bean.getFlag(

                ) + " where orderId=" + bean.getOrderId();
                GlobalData.printMessage("OrderRec SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add OrderRec ", e);
        }
    }


    public void addOrderRecList(LinkedList<OrderRec> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                OrderRec bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into OrderRec  ( assignmentNo, clientInstruction, numberOfFiles, serviceTypeId, transcriptionTypeId, deliveryOptionId, orderStatusId, valueAddedservId, timestampId, invoiceTypeId, outputFormatId, subjectOfFile, instructions, connectionType, createDate, totalDuration, deliveryDate, totalFees, transcriptionLink, orderdate, modifiedDate, fileExtension, orderPlaced, completeOrderDate, completeOrderDetails, excludedTATdate,flag) values (  '" + setClearData(bean.getAssignmentNo()) + "',  '" + setClearData(bean.getClientInstruction()) + "',  " + bean.getNumberOfFiles() + ",  " + bean.getServiceTypeId() + ",  " + bean.getTranscriptionTypeId() + ",  " + bean.getDeliveryOptionId() + ",  " + bean.getOrderStatusId() + ",  " + bean.getValueAddedservId() + ",  " + bean.getTimestampId() + ",  " + bean.getInvoiceTypeId() + ",  " + bean.getOutputFormatId() + ",  '" + setClearData(bean.getSubjectOfFile()) + "',  '" + setClearData(bean.getInstructions()) + "',  " + bean.getConnectionType() + ",  '" + setClearData(bean.getCreateDate()) + "',  '" + setClearData(bean.getTotalDuration()) + "',  '" + setClearData(bean.getDeliveryDate()) + "',  '" + setClearData(bean.getTotalFees()) + "',  '" + setClearData(bean.getTranscriptionLink()) + "',  '" + setClearData(bean.getOrderdate()) + "',  '" + setClearData(bean.getModifiedDate()) + "',  '" + setClearData(bean.getFileExtension()) + "',  '" + setClearData(bean.getOrderPlaced()) + "',  '" + setClearData(bean.getCompleteOrderDate()) + "',  '" + setClearData(bean.getCompleteOrderDetails()) + "',  '" + setClearData(bean.getExcludedTATdate()) + "'," + bean.getFlag() + " )";
                        GlobalData.printMessage("OrderRec SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add OrderRec ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("OrderRec", e);
            }
        }
    }

    public void deleteOrderRec() {
        String sql = "";
        try {


            sql = "delete from OrderRec  ";
            GlobalData.printMessage("OrderRec SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete OrderRec ", e);
        }
    }

    public void deleteOrderRec(int rowId) {
        String sql = "";
        try {


            sql = "delete from OrderRec where orderId=" + rowId;
            GlobalData.printMessage("OrderRec SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete OrderRec ", e);
        }
    }


//  Methods Of OrderRecordings

    public int getOrderRecordingsCount() {
        String sql = "select count( OrderRecordingsId ) as total from OrderRecordings 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  OrderRecordings:" + total);
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

    public LinkedList<OrderRecordings> getOrderRecordings() {
        String sql = "select *  from OrderRecordings ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<OrderRecordings> list = new LinkedList<OrderRecordings>();
        int i0 = c.getColumnIndex(OrderRecordings_KEY_orderRecId);
        int i1 = c.getColumnIndex(OrderRecordings_KEY_orderId);
        int i2 = c.getColumnIndex(OrderRecordings_KEY_recId);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                OrderRecordings bean = new OrderRecordings();
                bean.setOrderRecId(c.getInt(i0));
                bean.setOrderId(c.getInt(i1));
                bean.setRecId(c.getInt(i2));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("OrderRecordings Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<OrderRecordings> getOrderRecordingsList(int orderRecId) {
        String sql = "select *  from OrderRecordings where orderRecId=" + orderRecId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<OrderRecordings> list = new LinkedList<OrderRecordings>();
        int i0 = c.getColumnIndex(OrderRecordings_KEY_orderRecId);
        int i1 = c.getColumnIndex(OrderRecordings_KEY_orderId);
        int i2 = c.getColumnIndex(OrderRecordings_KEY_recId);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                OrderRecordings bean = new OrderRecordings();
                bean.setOrderRecId(c.getInt(i0));
                bean.setOrderId(c.getInt(i1));
                bean.setRecId(c.getInt(i2));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("OrderRecordings Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addOrderRecordings(OrderRecordings bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into OrderRecordings  ( orderId, recId  ) values (  " + bean.getOrderId() + ",  " + bean.getRecId() + " )";
                GlobalData.printMessage("OrderRecordings SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add OrderRecordings ", e);
        }
    }

    public void addOrderRecordingsList(LinkedList<OrderRecordings> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                OrderRecordings bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into OrderRecordings  ( orderId, recId  ) values (  " + bean.getOrderId() + ",  " + bean.getRecId() + " )";
                        GlobalData.printMessage("OrderRecordings SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add OrderRecordings ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("OrderRecordings", e);
            }
        }
    }

    public void deleteOrderRecordings() {
        String sql = "";
        try {


            sql = "delete from OrderRecordings  ";
            GlobalData.printMessage("OrderRecordings SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete OrderRecordings ", e);
        }
    }

    public void deleteOrderRecordings(int rowId) {
        String sql = "";
        try {


            sql = "delete from OrderRecordings where orderRecId=" + rowId;
            GlobalData.printMessage("OrderRecordings SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete OrderRecordings ", e);
        }
    }


//  Methods Of OrderStatus

    public int getOrderStatusCount() {
        String sql = "select count( OrderStatusId ) as total from OrderStatus 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  OrderStatus:" + total);
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

    public LinkedList<OrderStatus> getOrderStatus() {
        String sql = "select *  from OrderStatus ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<OrderStatus> list = new LinkedList<OrderStatus>();
        int i0 = c.getColumnIndex(OrderStatus_KEY_orderStatusId);
        int i1 = c.getColumnIndex(OrderStatus_KEY_orderId);
        int i2 = c.getColumnIndex(OrderStatus_KEY_recId);
        int i3 = c.getColumnIndex(OrderStatus_KEY_statusId);
        int i4 = c.getColumnIndex(OrderStatus_KEY_statusTEXT);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                OrderStatus bean = new OrderStatus();
                bean.setOrderStatusId(c.getInt(i0));
                bean.setOrderId(c.getInt(i1));
                bean.setRecId(c.getInt(i2));
                bean.setStatusId(c.getInt(i3));
                bean.setStatusTEXT(getClearData(c.getString(i4)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("OrderStatus Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<OrderStatus> getOrderStatusList(int orderStatusId) {
        String sql = "select *  from OrderStatus where orderStatusId=" + orderStatusId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<OrderStatus> list = new LinkedList<OrderStatus>();
        int i0 = c.getColumnIndex(OrderStatus_KEY_orderStatusId);
        int i1 = c.getColumnIndex(OrderStatus_KEY_orderId);
        int i2 = c.getColumnIndex(OrderStatus_KEY_recId);
        int i3 = c.getColumnIndex(OrderStatus_KEY_statusId);
        int i4 = c.getColumnIndex(OrderStatus_KEY_statusTEXT);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                OrderStatus bean = new OrderStatus();
                bean.setOrderStatusId(c.getInt(i0));
                bean.setOrderId(c.getInt(i1));
                bean.setRecId(c.getInt(i2));
                bean.setStatusId(c.getInt(i3));
                bean.setStatusTEXT(getClearData(c.getString(i4)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("OrderStatus Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addOrderStatus(OrderStatus bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into OrderStatus  ( orderId, recId, statusId, statusTEXT  ) values (  " + bean.getOrderId() + ",  " + bean.getRecId() + ",  " + bean.getStatusId() + ",  '" + setClearData(bean.getStatusTEXT()) + "' )";
                GlobalData.printMessage("OrderStatus SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add OrderStatus ", e);
        }
    }

    public void addOrderStatusList(LinkedList<OrderStatus> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                OrderStatus bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into OrderStatus  ( orderId, recId, statusId, statusTEXT  ) values (  " + bean.getOrderId() + ",  " + bean.getRecId() + ",  " + bean.getStatusId() + ",  '" + setClearData(bean.getStatusTEXT()) + "' )";
                        GlobalData.printMessage("OrderStatus SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add OrderStatus ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("OrderStatus", e);
            }
        }
    }

    public void deleteOrderStatus() {
        String sql = "";
        try {


            sql = "delete from OrderStatus  ";
            GlobalData.printMessage("OrderStatus SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete OrderStatus ", e);
        }
    }

    public void deleteOrderStatus(int rowId) {
        String sql = "";
        try {


            sql = "delete from OrderStatus where orderStatusId=" + rowId;
            GlobalData.printMessage("OrderStatus SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete OrderStatus ", e);
        }
    }


//  Methods Of OrderValueAddedServices

    public int getOrderValueAddedServicesCount() {
        String sql = "select count( OrderValueAddedServicesId ) as total from OrderValueAddedServices 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  OrderValueAddedServices:" + total);
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

    public LinkedList<OrderValueAddedServices> getOrderValueAddedServices() {
        String sql = "select *  from OrderValueAddedServices ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<OrderValueAddedServices> list = new LinkedList<OrderValueAddedServices>();
        int i0 = c.getColumnIndex(OrderValueAddedServices_KEY_orderValueAddedServicesId);
        int i1 = c.getColumnIndex(OrderValueAddedServices_KEY_orderId);
        int i2 = c.getColumnIndex(OrderValueAddedServices_KEY_valueAddedServiceId);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                OrderValueAddedServices bean = new OrderValueAddedServices();
                bean.setOrderValueAddedServicesId(c.getInt(i0));
                bean.setOrderId(c.getInt(i1));
                bean.setValueAddedServiceId(c.getInt(i2));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("OrderValueAddedServices Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<OrderValueAddedServices> getOrderValueAddedServicesList(int orderValueAddedServicesId) {
        String sql = "select *  from OrderValueAddedServices where orderValueAddedServicesId=" + orderValueAddedServicesId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<OrderValueAddedServices> list = new LinkedList<OrderValueAddedServices>();
        int i0 = c.getColumnIndex(OrderValueAddedServices_KEY_orderValueAddedServicesId);
        int i1 = c.getColumnIndex(OrderValueAddedServices_KEY_orderId);
        int i2 = c.getColumnIndex(OrderValueAddedServices_KEY_valueAddedServiceId);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                OrderValueAddedServices bean = new OrderValueAddedServices();
                bean.setOrderValueAddedServicesId(c.getInt(i0));
                bean.setOrderId(c.getInt(i1));
                bean.setValueAddedServiceId(c.getInt(i2));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("OrderValueAddedServices Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addOrderValueAddedServices(OrderValueAddedServices bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into OrderValueAddedServices  ( orderId, valueAddedServiceId  ) values (  " + bean.getOrderId() + ",  " + bean.getValueAddedServiceId() + " )";
                GlobalData.printMessage("OrderValueAddedServices SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add OrderValueAddedServices ", e);
        }
    }

    public void addOrderValueAddedServicesList(LinkedList<OrderValueAddedServices> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                OrderValueAddedServices bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into OrderValueAddedServices  ( orderId, valueAddedServiceId  ) values (  " + bean.getOrderId() + ",  " + bean.getValueAddedServiceId() + " )";
                        GlobalData.printMessage("OrderValueAddedServices SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add OrderValueAddedServices ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("OrderValueAddedServices", e);
            }
        }
    }

    public void deleteOrderValueAddedServices() {
        String sql = "";
        try {


            sql = "delete from OrderValueAddedServices  ";
            GlobalData.printMessage("OrderValueAddedServices SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete OrderValueAddedServices ", e);
        }
    }

    public void deleteOrderValueAddedServices(int rowId) {
        String sql = "";
        try {


            sql = "delete from OrderValueAddedServices where orderValueAddedServicesId=" + rowId;
            GlobalData.printMessage("OrderValueAddedServices SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete OrderValueAddedServices ", e);
        }
    }




//  Methods Of PremiumType

    public int getPremiumTypeCount() {
        String sql = "select count( PremiumTypeId ) as total from PremiumType 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  PremiumType:" + total);
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




//  Methods Of Recording

 /*   public int getRecordingCount() {
        String sql = "select count( RecordingId ) as total from Recording 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Recording:" + total);
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

    public LinkedList<Recording> getRecording() {
        String sql = "select *  from Recording ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Recording> list = new LinkedList<Recording>();
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
        int i13 = c.getColumnIndex(Recording_KEY_createdTEXT);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Recording bean = new Recording();
                bean.setRecId(c.getInt(i0));
                bean.setServerId(c.getInt(i1));
                bean.setUserId(c.getInt(i2));
                bean.setUpMasterId(c.getInt(i3));
                bean.setSourceTypeId(c.getInt(i4));
                bean.setSourceLink(getClearData(c.getString(i5)));
                bean.setRecName(getClearData(c.getString(i6)));
                bean.setRecDesc(getClearData(c.getString(i7)));
                bean.setRecDuration(getClearData(c.getString(i8)));
                bean.setRecLocalPath(getClearData(c.getString(i9)));
                bean.setRecSize(getClearData(c.getString(i10)));
                bean.setRecUploadDuration(getClearData(c.getString(i11)));
                bean.setUploadingConnectionMode(getClearData(c.getString(i12)));
                bean.setCreatedTEXT(getClearData(c.getString(i13)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Recording Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Recording> getRecordingList(int recId) {
        String sql = "select *  from Recording where recId=" + recId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Recording> list = new LinkedList<Recording>();
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
        int i13 = c.getColumnIndex(Recording_KEY_createdTEXT);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Recording bean = new Recording();
                bean.setRecId(c.getInt(i0));
                bean.setServerId(c.getInt(i1));
                bean.setUserId(c.getInt(i2));
                bean.setUpMasterId(c.getInt(i3));
                bean.setSourceTypeId(c.getInt(i4));
                bean.setSourceLink(getClearData(c.getString(i5)));
                bean.setRecName(getClearData(c.getString(i6)));
                bean.setRecDesc(getClearData(c.getString(i7)));
                bean.setRecDuration(getClearData(c.getString(i8)));
                bean.setRecLocalPath(getClearData(c.getString(i9)));
                bean.setRecSize(getClearData(c.getString(i10)));
                bean.setRecUploadDuration(getClearData(c.getString(i11)));
                bean.setUploadingConnectionMode(getClearData(c.getString(i12)));
                bean.setCreatedTEXT(getClearData(c.getString(i13)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Recording Read:", e);
            }
        }
        c.close();
        return list;
    }*/

    public int getRecordingCount() {
        String sql = "select count( RecordingId ) as total from Recording 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Recording:" + total);
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

    public LinkedList<MyRecording> getRecording() {
        String sql = "select *  from Recording order by recId DESC ";
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


    public LinkedList<MyRecording> getRecordingList(int recId) {
        String sql = "select *  from Recording where recId=" + recId;
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



  /*  public void addRecording(Recording bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Recording  ( serverId, userId, upMasterId, sourceTypeId, sourceLink, recName, recDesc, recDuration, recLocalPath, recSize, recUploadDuration, uploadingConnectionMode, createdTEXT  ) values (  " + bean.getServerId() + ",  " + bean.getUserId() + ",  " + bean.getUpMasterId() + ",  " + bean.getSourceTypeId() + ",  '" + setClearData(bean.getSourceLink()) + "',  '" + setClearData(bean.getRecName()) + "',  '" + setClearData(bean.getRecDesc()) + "',  '" + setClearData(bean.getRecDuration()) + "',  '" + setClearData(bean.getRecLocalPath()) + "',  '" + setClearData(bean.getRecSize()) + "',  '" + setClearData(bean.getRecUploadDuration()) + "',  '" + setClearData(bean.getUploadingConnectionMode()) + "',  '" + setClearData(bean.getCreatedTEXT()) + "' )";
                GlobalData.printMessage("Recording SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Recording ", e);
        }
    }
*/


    public void addRecording(MyRecording bean) {
        String sql = "";
        try {
            if (bean != null) {
                sql = "insert into Recording  ( serverId, userId, upMasterId, sourceTypeId, sourceLink, recName, recDesc, recDuration, recLocalPath, recSize, recUploadDuration, uploadingConnectionMode, createdDate  ) values (  " + bean.getServerId() + ",  " + bean.getUserId() + ", '" + bean.getUpMasterId() + "',  '" + bean.getSourceTypeId() + "',  '" + setClearData(bean.getSourceLink()) + "',  '" + setClearData(bean.getRecName()) + "',  '" + setClearData(bean.getRecDesc()) + "',  '" + setClearData(bean.getRecDuration()) + "',  '" + setClearData(bean.getRecLocalPath()) + "',  '" + setClearData(bean.getRecSize()) + "',  '" + setClearData(bean.getRecUploadDuration()) + "',  '" + setClearData(bean.getUploadingConnectionMode()) + "',  '" + setClearData(bean.getCreatedDate()) + "' )";
                GlobalData.printMessage("Recording SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Recording ", e);
        }
    }

    public void addRecordingList(LinkedList<Recording> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Recording bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Recording  ( serverId, userId, upMasterId, sourceTypeId, sourceLink, recName, recDesc, recDuration, recLocalPath, recSize, recUploadDuration, uploadingConnectionMode, createdTEXT  ) values (  " + bean.getServerId() + ",  " + bean.getUserId() + ",  '" + bean.getUpMasterId() + "',  '" + bean.getSourceTypeId() + "',  '" + setClearData(bean.getSourceLink()) + "',  '" + setClearData(bean.getRecName()) + "',  '" + setClearData(bean.getRecDesc()) + "',  '" + setClearData(bean.getRecDuration()) + "',  '" + setClearData(bean.getRecLocalPath()) + "',  '" + setClearData(bean.getRecSize()) + "',  '" + setClearData(bean.getRecUploadDuration()) + "',  '" + setClearData(bean.getUploadingConnectionMode()) + "',  '" + setClearData(bean.getCreatedTEXT()) + "' )";
                        GlobalData.printMessage("Recording SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Recording ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Recording", e);
            }
        }
    }


    public void editRecording(MyRecording bean) {
        String sql = "";
        try {
            if (bean != null) {
                sql = "update Recording set recName ='" + bean.getRecName().trim() + "'  , recDesc='" + bean.getRecDesc().trim() + "' where recId =" + bean.getRecId();
                GlobalData.printMessage("Recording SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Recording ", e);
        }
    }

   /* public void deleteRecording() {
        String sql = "";
        try {


            sql = "delete from Recording  ";
            GlobalData.printMessage("Recording SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Recording ", e);
        }
    }

    public void deleteRecording(int rowId) {
        String sql = "";
        try {


            sql = "delete from Recording where recId=" + rowId;
            GlobalData.printMessage("Recording SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Recording ", e);
        }
    }*/

    public void deleteRecording(MyRecording bean) {
        String sql = "";
        try {
            if (bean != null) {
                sql = "delete from  Recording where recId =" + bean.getRecId();
                GlobalData.printMessage("Recording SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Recording ", e);
        }
    }





//  Methods Of Source

    public int getSourceCount() {
        String sql = "select count( SourceId ) as total from Source 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  Source:" + total);
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

    public LinkedList<Source> getSource() {
        String sql = "select *  from Source ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Source> list = new LinkedList<Source>();
        int i0 = c.getColumnIndex(Source_KEY_sourceId);
        int i1 = c.getColumnIndex(Source_KEY_sourceName);
        int i2 = c.getColumnIndex(Source_KEY_lastModifiedDate);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Source bean = new Source();
                bean.setSourceId(c.getInt(i0));
                bean.setSourceName(getClearData(c.getString(i1)));
                bean.setLastModifiedDate(getClearData(c.getString(i2)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Source Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<Source> getSourceList(int sourceId) {
        String sql = "select *  from Source where sourceId=" + sourceId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<Source> list = new LinkedList<Source>();
        int i0 = c.getColumnIndex(Source_KEY_sourceId);
        int i1 = c.getColumnIndex(Source_KEY_sourceName);
        int i2 = c.getColumnIndex(Source_KEY_lastModifiedDate);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                Source bean = new Source();
                bean.setSourceId(c.getInt(i0));
                bean.setSourceName(getClearData(c.getString(i1)));
                bean.setLastModifiedDate(getClearData(c.getString(i2)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Source Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addSource(Source bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into Source  ( sourceName, lastModifiedDate  ) values (  '" + setClearData(bean.getSourceName()) + "',  '" + setClearData(bean.getLastModifiedDate()) + "' )";
                GlobalData.printMessage("Source SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add Source ", e);
        }
    }

    public void addSourceList(LinkedList<Source> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                Source bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into Source  ( sourceName, lastModifiedDate  ) values (  '" + setClearData(bean.getSourceName()) + "',  '" + setClearData(bean.getLastModifiedDate()) + "' )";
                        GlobalData.printMessage("Source SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add Source ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("Source", e);
            }
        }
    }

    public void deleteSource() {
        String sql = "";
        try {


            sql = "delete from Source  ";
            GlobalData.printMessage("Source SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Source ", e);
        }
    }

    public void deleteSource(int rowId) {
        String sql = "";
        try {


            sql = "delete from Source where sourceId=" + rowId;
            GlobalData.printMessage("Source SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete Source ", e);
        }
    }


//  Methods Of UploadingMaster

    public int getUploadingMasterCount() {
        String sql = "select count( UploadingMasterId ) as total from UploadingMaster 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  UploadingMaster:" + total);
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

    public LinkedList<UploadingMaster> getUploadingMaster() {
        String sql = "select *  from UploadingMaster ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<UploadingMaster> list = new LinkedList<UploadingMaster>();
        int i0 = c.getColumnIndex(UploadingMaster_KEY_upMasterId);
        int i1 = c.getColumnIndex(UploadingMaster_KEY_upImage);
        int i2 = c.getColumnIndex(UploadingMaster_KEY_upType);
        int i3 = c.getColumnIndex(UploadingMaster_KEY_lastModifiedDate);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                UploadingMaster bean = new UploadingMaster();
                bean.setUpMasterId(c.getInt(i0));
                bean.setUpImage(getClearData(c.getString(i1)));
                bean.setUpType(c.getInt(i2));
                bean.setLastModifiedDate(getClearData(c.getString(i3)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("UploadingMaster Read:", e);
            }
        }
        c.close();
        return list;
    }

    public LinkedList<UploadingMaster> getUploadingMasterList(int upMasterId) {
        String sql = "select *  from UploadingMaster where upMasterId=" + upMasterId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<UploadingMaster> list = new LinkedList<UploadingMaster>();
        int i0 = c.getColumnIndex(UploadingMaster_KEY_upMasterId);
        int i1 = c.getColumnIndex(UploadingMaster_KEY_upImage);
        int i2 = c.getColumnIndex(UploadingMaster_KEY_upType);
        int i3 = c.getColumnIndex(UploadingMaster_KEY_lastModifiedDate);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                UploadingMaster bean = new UploadingMaster();
                bean.setUpMasterId(c.getInt(i0));
                bean.setUpImage(getClearData(c.getString(i1)));
                bean.setUpType(c.getInt(i2));
                bean.setLastModifiedDate(getClearData(c.getString(i3)));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("UploadingMaster Read:", e);
            }
        }
        c.close();
        return list;
    }

    public void addUploadingMaster(UploadingMaster bean) {
        String sql = "";
        try {
            if (bean != null) {


                sql = "insert into UploadingMaster  ( upImage, upType, lastModifiedDate  ) values (  '" + setClearData(bean.getUpImage()) + "',  " + bean.getUpType() + ",  '" + setClearData(bean.getLastModifiedDate()) + "' )";
                GlobalData.printMessage("UploadingMaster SQl:" + sql);
                ourDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            GlobalData.printError("Add UploadingMaster ", e);
        }
    }

    public void addUploadingMasterList(LinkedList<UploadingMaster> beanList) {
        String sql = "";
        for (int i = 0; i < beanList.size(); i++) {
            try {
                UploadingMaster bean = beanList.get(i);
                try {
                    if (bean != null) {


                        sql = "insert into UploadingMaster  ( upImage, upType, lastModifiedDate  ) values (  '" + setClearData(bean.getUpImage()) + "',  " + bean.getUpType() + ",  '" + setClearData(bean.getLastModifiedDate()) + "' )";
                        GlobalData.printMessage("UploadingMaster SQl:" + sql);
                        ourDatabase.execSQL(sql);
                    }
                } catch (Exception e) {
                    GlobalData.printError("Add UploadingMaster ", e);
                }
            } catch (Exception e) {
                GlobalData.printError("UploadingMaster", e);
            }
        }
    }

    public void deleteUploadingMaster() {
        String sql = "";
        try {


            sql = "delete from UploadingMaster  ";
            GlobalData.printMessage("UploadingMaster SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete UploadingMaster ", e);
        }
    }

    public void deleteUploadingMaster(int rowId) {
        String sql = "";
        try {


            sql = "delete from UploadingMaster where upMasterId=" + rowId;
            GlobalData.printMessage("UploadingMaster SQl:" + sql);
            ourDatabase.execSQL(sql);

        } catch (Exception e) {
            GlobalData.printError("delete UploadingMaster ", e);
        }
    }



//  Methods Of ValueAddedServices

    public int getValueAddedServicesCount() {
        String sql = "select count( ValueAddedServicesId ) as total from ValueAddedServices 	";
        Cursor c = ourDatabase.rawQuery(sql, null);
        int total = 0;
        try {
            int iCId = c.getColumnIndex("total");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try {
                    total = c.getInt(iCId);
                    GlobalData.printMessage("Total  ValueAddedServices:" + total);
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

    //methods of schemedetails
    public LinkedList<ChangePrice> getSchemeDetails() {
        String sql = "select *  from  SchemeDetails ";
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<ChangePrice> list = new LinkedList<ChangePrice>();
        int i0 = c.getColumnIndex(Scheme_Name);
        int i1 = c.getColumnIndex(Scheme_Duration);
        int i2 = c.getColumnIndex(Price);
        int i3 = c.getColumnIndex(Date_Time);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                ChangePrice bean = new ChangePrice();
                bean.setScheme_name(c.getString(i0));
                bean.setScheme_duration(c.getString(i1));
                bean.setPrice(c.getString(i2));
                bean.setDate_time(c.getString(i3));
                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("Details:", e);
            }
        }
        c.close();
        return list;
    }





    /// Check the last modified Date
    /// Get Last ModifiedDate
    public String getLastModDate(String tableName) {
        String sql = "select MAX( modifieddate ) as total from "+tableName;
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

        if (total == "null" || total == null){
            total = "";
        }

        return total;
    }
    public String getLastModDateType2(String tableName) {
        String sql = "select MAX( modified_date ) as total from "+tableName;
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

        if (total == "null" || total == null){
            total = "";
        }

        return total;
    }



}
