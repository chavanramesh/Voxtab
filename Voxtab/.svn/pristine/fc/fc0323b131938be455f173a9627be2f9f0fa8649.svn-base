


package com.voxtab.ariatech.voxtab.bean;


import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

public class AssessmentReport {


    int assessmentReportId = 0;
    int orderId = 0;
    String assDate = "";
    String AudioQuality = "";
    String subjectArea = "";
    String result = "";
    String notesFrom = "";
    String notesFromTrans = "";

    public void setAssessmentReportId(int assessmentReportId) {
        this.assessmentReportId = assessmentReportId;
    }

    public int getAssessmentReportId() {
        return assessmentReportId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setAssDate(String assDate) {
        this.assDate = assDate;
    }

    public String getAssDate() {
        return assDate;
    }

    public void setAudioQuality(String AudioQuality) {
        this.AudioQuality = AudioQuality;
    }

    public String getAudioQuality() {
        return AudioQuality;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setNotesFrom(String notesFrom) {
        this.notesFrom = notesFrom;
    }

    public String getNotesFrom() {
        return notesFrom;
    }

    public void setNotesFromTrans(String notesFromTrans) {
        this.notesFromTrans = notesFromTrans;
    }

    public String getNotesFromTrans() {
        return notesFromTrans;
    }


    public void parseJSON(JSONObject jsonObject) {
        try {
            if (jsonObject.has("assessmentReportId")) {
                try {
                    assessmentReportId = Integer.parseInt(jsonObject.getString("assessmentReportId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("orderId")) {
                try {
                    orderId = Integer.parseInt(jsonObject.getString("orderId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("assDate")) {
                try {
                    assDate = jsonObject.getString("assDate");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("AudioQuality")) {
                try {
                    AudioQuality = jsonObject.getString("AudioQuality");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("subjectArea")) {
                try {
                    subjectArea = jsonObject.getString("subjectArea");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("result")) {
                try {
                    result = jsonObject.getString("result");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("notesFrom")) {
                try {
                    notesFrom = jsonObject.getString("notesFrom");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("notesFromTrans")) {
                try {
                    notesFromTrans = jsonObject.getString("notesFromTrans");
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }

    }


}//END