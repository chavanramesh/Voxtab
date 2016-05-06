

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Source {



 int  sourceId =0;
 String  sourceName ="" ;
 String  lastModifiedDate ="" ;
 public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
 public int getSourceId() {
        return sourceId;
    }

public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
 public String getSourceName() {
        return sourceName;
    }

public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
 public String getLastModifiedDate() {
        return lastModifiedDate;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("sourceId")) { 
 try {
 sourceId= Integer.parseInt(jsonObject.getString("sourceId"));
 } catch (Exception e) {}
 }

if (jsonObject.has("sourceName")) { 
 try {
 sourceName= jsonObject.getString("sourceName");
 } catch (Exception e) {}
 }

if (jsonObject.has("lastModifiedDate")) { 
 try {
 lastModifiedDate= jsonObject.getString("lastModifiedDate");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END