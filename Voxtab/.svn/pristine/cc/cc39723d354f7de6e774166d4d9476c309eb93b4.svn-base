

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Assessment_speaker {



 int  ass_speaker_id =0;
 int  ass_report_id =0;
 String  speaker_name ="" ;
 public void setAss_speaker_id(int ass_speaker_id) {
        this.ass_speaker_id = ass_speaker_id;
    }
 public int getAss_speaker_id() {
        return ass_speaker_id;
    }

 public void setAss_report_id(int ass_report_id) {
        this.ass_report_id = ass_report_id;
    }
 public int getAss_report_id() {
        return ass_report_id;
    }

public void setSpeaker_name(String speaker_name) {
        this.speaker_name = speaker_name;
    }
 public String getSpeaker_name() {
        return speaker_name;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("ass_speaker_id")) { 
 try {
 ass_speaker_id= Integer.parseInt(jsonObject.getString("ass_speaker_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("ass_report_id")) { 
 try {
 ass_report_id= Integer.parseInt(jsonObject.getString("ass_report_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("speaker_name")) { 
 try {
 speaker_name= jsonObject.getString("speaker_name");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END