


package com.voxtab.ariatech.voxtab.bean;


import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

public class OrderMedia {


    int orderId = 0;
    String assignmentNo = "";
    int serviceTypeId = 0;
    int deliveryOptionId = 0;
    int valueAddedServId = 0;
    int invoiceTypeId = 0;
    int outputFormatId = 0;
    String subjectofFile = "";
    String instructions = "";
    int coneectionType = 0;
    String createdDate = "";
    String oderDate = "";

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setAssignmentNo(String assignmentNo) {
        this.assignmentNo = assignmentNo;
    }

    public String getAssignmentNo() {
        return assignmentNo;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setDeliveryOptionId(int deliveryOptionId) {
        this.deliveryOptionId = deliveryOptionId;
    }

    public int getDeliveryOptionId() {
        return deliveryOptionId;
    }

    public void setValueAddedServId(int valueAddedServId) {
        this.valueAddedServId = valueAddedServId;
    }

    public int getValueAddedServId() {
        return valueAddedServId;
    }

    public void setInvoiceTypeId(int invoiceTypeId) {
        this.invoiceTypeId = invoiceTypeId;
    }

    public int getInvoiceTypeId() {
        return invoiceTypeId;
    }

    public void setOutputFormatId(int outputFormatId) {
        this.outputFormatId = outputFormatId;
    }

    public int getOutputFormatId() {
        return outputFormatId;
    }

    public void setSubjectofFile(String subjectofFile) {
        this.subjectofFile = subjectofFile;
    }

    public String getSubjectofFile() {
        return subjectofFile;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setConeectionType(int coneectionType) {
        this.coneectionType = coneectionType;
    }

    public int getConeectionType() {
        return coneectionType;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setOderDate(String oderDate) {
        this.oderDate = oderDate;
    }

    public String getOderDate() {
        return oderDate;
    }


    public void parseJSON(JSONObject jsonObject) {
        try {
            if (jsonObject.has("orderId")) {
                try {
                    orderId = Integer.parseInt(jsonObject.getString("orderId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("assignmentNo")) {
                try {
                    assignmentNo = jsonObject.getString("assignmentNo");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("serviceTypeId")) {
                try {
                    serviceTypeId = Integer.parseInt(jsonObject.getString("serviceTypeId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("deliveryOptionId")) {
                try {
                    deliveryOptionId = Integer.parseInt(jsonObject.getString("deliveryOptionId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("valueAddedServId")) {
                try {
                    valueAddedServId = Integer.parseInt(jsonObject.getString("valueAddedServId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("invoiceTypeId")) {
                try {
                    invoiceTypeId = Integer.parseInt(jsonObject.getString("invoiceTypeId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("outputFormatId")) {
                try {
                    outputFormatId = Integer.parseInt(jsonObject.getString("outputFormatId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("subjectofFile")) {
                try {
                    subjectofFile = jsonObject.getString("subjectofFile");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("instructions")) {
                try {
                    instructions = jsonObject.getString("instructions");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("coneectionType")) {
                try {
                    coneectionType = Integer.parseInt(jsonObject.getString("coneectionType"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("createdDate")) {
                try {
                    createdDate = jsonObject.getString("createdDate");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("oderDate")) {
                try {
                    oderDate = jsonObject.getString("oderDate");
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }

    }


}//END