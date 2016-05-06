

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class UploadingMaster {



 int  upMasterId =0;
 String  upImage ="" ;
 int  upType =0;
 String  lastModifiedDate ="" ;
 public void setUpMasterId(int upMasterId) {
        this.upMasterId = upMasterId;
    }
 public int getUpMasterId() {
        return upMasterId;
    }

public void setUpImage(String upImage) {
        this.upImage = upImage;
    }
 public String getUpImage() {
        return upImage;
    }

 public void setUpType(int upType) {
        this.upType = upType;
    }
 public int getUpType() {
        return upType;
    }

public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
 public String getLastModifiedDate() {
        return lastModifiedDate;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("upMasterId")) { 
 try {
 upMasterId= Integer.parseInt(jsonObject.getString("upMasterId"));
 } catch (Exception e) {}
 }

if (jsonObject.has("upImage")) { 
 try {
 upImage= jsonObject.getString("upImage");
 } catch (Exception e) {}
 }

if (jsonObject.has("upType")) { 
 try {
 upType= Integer.parseInt(jsonObject.getString("upType"));
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