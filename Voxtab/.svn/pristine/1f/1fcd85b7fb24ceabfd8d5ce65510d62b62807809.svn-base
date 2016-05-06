

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Assessment_report {



 int  ass_report_id =0;
 int  order_id =0;
 String  ass_date ="" ;
 String  audio_quality ="" ;
 String  subjectarea ="" ;
 String  result ="" ;
 String  notesfrom ="" ;
 String  notesfrom_trasaction ="" ;
 public void setAss_report_id(int ass_report_id) {
        this.ass_report_id = ass_report_id;
    }
 public int getAss_report_id() {
        return ass_report_id;
    }

 public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
 public int getOrder_id() {
        return order_id;
    }

public void setAss_date(String ass_date) {
        this.ass_date = ass_date;
    }
 public String getAss_date() {
        return ass_date;
    }

public void setAudio_quality(String audio_quality) {
        this.audio_quality = audio_quality;
    }
 public String getAudio_quality() {
        return audio_quality;
    }

public void setSubjectarea(String subjectarea) {
        this.subjectarea = subjectarea;
    }
 public String getSubjectarea() {
        return subjectarea;
    }

public void setResult(String result) {
        this.result = result;
    }
 public String getResult() {
        return result;
    }

public void setNotesfrom(String notesfrom) {
        this.notesfrom = notesfrom;
    }
 public String getNotesfrom() {
        return notesfrom;
    }

public void setNotesfrom_trasaction(String notesfrom_trasaction) {
        this.notesfrom_trasaction = notesfrom_trasaction;
    }
 public String getNotesfrom_trasaction() {
        return notesfrom_trasaction;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("ass_report_id")) { 
 try {
 ass_report_id= Integer.parseInt(jsonObject.getString("ass_report_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("order_id")) { 
 try {
 order_id= Integer.parseInt(jsonObject.getString("order_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("ass_date")) { 
 try {
 ass_date= jsonObject.getString("ass_date");
 } catch (Exception e) {}
 }

if (jsonObject.has("audio_quality")) { 
 try {
 audio_quality= jsonObject.getString("audio_quality");
 } catch (Exception e) {}
 }

if (jsonObject.has("subjectarea")) { 
 try {
 subjectarea= jsonObject.getString("subjectarea");
 } catch (Exception e) {}
 }

if (jsonObject.has("result")) { 
 try {
 result= jsonObject.getString("result");
 } catch (Exception e) {}
 }

if (jsonObject.has("notesfrom")) { 
 try {
 notesfrom= jsonObject.getString("notesfrom");
 } catch (Exception e) {}
 }

if (jsonObject.has("notesfrom_trasaction")) { 
 try {
 notesfrom_trasaction= jsonObject.getString("notesfrom_trasaction");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END