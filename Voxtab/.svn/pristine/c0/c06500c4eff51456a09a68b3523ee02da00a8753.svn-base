

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class AssessmentSpeaker {



 int  assessmentSpeakerId =0;
 int  assessmentReportId =0;
 String  speakerName ="" ;
 public void setAssessmentSpeakerId(int assessmentSpeakerId) {
        this.assessmentSpeakerId = assessmentSpeakerId;
    }
 public int getAssessmentSpeakerId() {
        return assessmentSpeakerId;
    }

 public void setAssessmentReportId(int assessmentReportId) {
        this.assessmentReportId = assessmentReportId;
    }
 public int getAssessmentReportId() {
        return assessmentReportId;
    }

public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }
 public String getSpeakerName() {
        return speakerName;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("assessmentSpeakerId")) { 
 try {
 assessmentSpeakerId= Integer.parseInt(jsonObject.getString("assessmentSpeakerId"));
 } catch (Exception e) {}
 }

if (jsonObject.has("assessmentReportId")) { 
 try {
 assessmentReportId= Integer.parseInt(jsonObject.getString("assessmentReportId"));
 } catch (Exception e) {}
 }

if (jsonObject.has("speakerName")) { 
 try {
 speakerName= jsonObject.getString("speakerName");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END