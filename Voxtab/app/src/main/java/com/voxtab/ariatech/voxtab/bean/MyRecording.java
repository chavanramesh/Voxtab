


package com.voxtab.ariatech.voxtab.bean;


import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

import java.io.Serializable;

public class MyRecording implements Serializable {
    int recId = 0;
    int serverId = 0;
    int userId = 0;
    String upMasterId = "";
    String sourceTypeId = "";
    String sourceLink = "";
    String recName = "";
    String recDesc = "";
    String recDuration = "";
    String recLocalPath = "";
    String recSize = "";
    String recUploadDuration = "";
    String uploadingConnectionMode = "";
    String createdDate = "";
    boolean selectFlag = false;
    boolean actionFlag = false;
    boolean clickFlag = true;

    boolean openLayout = false;

    int isUploaded = 0;

    String assignment_no = "";

    public boolean isActionFlag() {
        return actionFlag;
    }

    public boolean isClickFlag() {
        return clickFlag;
    }

    public void setClickFlag(boolean clickFlag) {
        this.clickFlag = clickFlag;
    }

    public void setActionFlag(boolean actionFlag) {
        this.actionFlag = actionFlag;
    }

    public boolean isSelectFlag() {
        return selectFlag;
    }

    public void setSelectFlag(boolean selectFlag) {
        this.selectFlag = selectFlag;
    }

    public void setRecId(int recId) {
        this.recId = recId;
    }

    public int getRecId() {
        return recId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUpMasterId(String upMasterId) {
        this.upMasterId = upMasterId;
    }

    public String getUpMasterId() {
        return upMasterId;
    }

    public void setSourceTypeId(String sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }

    public String getSourceTypeId() {
        return sourceTypeId;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecDesc(String recDesc) {
        this.recDesc = recDesc;
    }

    public String getRecDesc() {
        return recDesc;
    }

    public void setRecDuration(String recDuration) {
        this.recDuration = recDuration;
    }

    public String getRecDuration() {
        return recDuration;
    }

    public void setRecLocalPath(String recLocalPath) {
        this.recLocalPath = recLocalPath;
    }

    public String getRecLocalPath() {
        return recLocalPath;
    }

    public void setRecSize(String recSize) {
        this.recSize = recSize;
    }

    public String getRecSize() {
        return recSize;
    }

    public void setRecUploadDuration(String recUploadDuration) {
        this.recUploadDuration = recUploadDuration;
    }

    public String getRecUploadDuration() {
        return recUploadDuration;
    }

    public void setUploadingConnectionMode(String uploadingConnectionMode) {
        this.uploadingConnectionMode = uploadingConnectionMode;
    }

    public String getUploadingConnectionMode() {
        return uploadingConnectionMode;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }


    public String getAssignment_no() {
        return assignment_no;
    }

    public void setAssignment_no(String assignment_no) {
        this.assignment_no = assignment_no;
    }

    public int getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(int isUploaded) {
        this.isUploaded = isUploaded;
    }

    public boolean isOpenLayout() {
        return openLayout;
    }

    public void setOpenLayout(boolean openLayout) {
        this.openLayout = openLayout;
    }

    public void parseJSON(JSONObject jsonObject) {
        try {
            if (jsonObject.has("recId")) {
                try {
                    recId = Integer.parseInt(jsonObject.getString("recId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("serverId")) {
                try {
                    serverId = Integer.parseInt(jsonObject.getString("serverId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("userId")) {
                try {
                    userId = Integer.parseInt(jsonObject.getString("userId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("upMasterId")) {
                try {
                    upMasterId = (jsonObject.getString("upMasterId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("sourceTypeId")) {
                try {
                    sourceTypeId = (jsonObject.getString("sourceTypeId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("sourceLink")) {
                try {
                    sourceLink = jsonObject.getString("sourceLink");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("recName")) {
                try {
                    recName = jsonObject.getString("recName");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("recDesc")) {
                try {
                    recDesc = jsonObject.getString("recDesc");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("recDuration")) {
                try {
                    recDuration = jsonObject.getString("recDuration");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("recLocalPath")) {
                try {
                    recLocalPath = jsonObject.getString("recLocalPath");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("recSize")) {
                try {
                    recSize = jsonObject.getString("recSize");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("recUploadDuration")) {
                try {
                    recUploadDuration = jsonObject.getString("recUploadDuration");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("uploadingConnectionMode")) {
                try {
                    uploadingConnectionMode = jsonObject.getString("uploadingConnectionMode");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("createdDate")) {
                try {
                    createdDate = jsonObject.getString("createdDate");
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }

    }


}//END