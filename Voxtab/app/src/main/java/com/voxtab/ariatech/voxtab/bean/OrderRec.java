


package com.voxtab.ariatech.voxtab.bean;


import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

import java.io.Serializable;

public class OrderRec implements Serializable {


    int orderId = 0;
    String assignmentNo = "";
    String clientInstruction = "";
    int numberOfFiles = 0;
    int serviceTypeId = 0;
    int transcriptionTypeId = 0;
    int deliveryOptionId = 0;
    int orderStatusId = 0;
    int valueAddedservId = 0;
    int timestampId = 0;
    int invoiceTypeId = 0;
    int outputFormatId = 0;
    String subjectOfFile = "";
    String instructions = "";
    int connectionType = 0;
    String createDate = "";
    String totalDuration = "";
    String deliveryDate = "";
    String totalFees = "";
    String transcriptionLink = "";
    String orderdate = "";
    String modifiedDate = "";
    String fileExtension = "";
    String orderPlaced = "";
    String completeOrderDate = "";
    String completeOrderDetails = "";
    String excludedTATdate = "";
    int flag=0;// Success = 1, Edit=2, Cancel=3;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


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

    public void setClientInstruction(String clientInstruction) {
        this.clientInstruction = clientInstruction;
    }

    public String getClientInstruction() {
        return clientInstruction;
    }

    public void setNumberOfFiles(int numberOfFiles) {
        this.numberOfFiles = numberOfFiles;
    }

    public int getNumberOfFiles() {
        return numberOfFiles;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }
    public void setTranscriptionTypeId(int transcriptionTypeId) {
        this.transcriptionTypeId = transcriptionTypeId;
    }

    public int getTranscriptionTypeId() {
        return transcriptionTypeId;
    }

    public void setDeliveryOptionId(int deliveryOptionId) {
        this.deliveryOptionId = deliveryOptionId;
    }

    public int getDeliveryOptionId() {
        return deliveryOptionId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setValueAddedservId(int valueAddedservId) {
        this.valueAddedservId = valueAddedservId;
    }

    public int getValueAddedservId() {
        return valueAddedservId;
    }

    public void setTimestampId(int timestampId) {
        this.timestampId = timestampId;
    }

    public int getTimestampId() {
        return timestampId;
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

    public void setSubjectOfFile(String subjectOfFile) {
        this.subjectOfFile = subjectOfFile;
    }

    public String getSubjectOfFile() {
        return subjectOfFile;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setConnectionType(int connectionType) {
        this.connectionType = connectionType;
    }

    public int getConnectionType() {
        return connectionType;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setTotalFees(String totalFees) {
        this.totalFees = totalFees;
    }

    public String getTotalFees() {
        return totalFees;
    }

    public void setTranscriptionLink(String transcriptionLink) {
        this.transcriptionLink = transcriptionLink;
    }

    public String getTranscriptionLink() {
        return transcriptionLink;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setOrderPlaced(String orderPlaced) {
        this.orderPlaced = orderPlaced;
    }

    public String getOrderPlaced() {
        return orderPlaced;
    }

    public void setCompleteOrderDate(String completeOrderDate) {
        this.completeOrderDate = completeOrderDate;
    }

    public String getCompleteOrderDate() {
        return completeOrderDate;
    }

    public void setCompleteOrderDetails(String completeOrderDetails) {
        this.completeOrderDetails = completeOrderDetails;
    }

    public String getCompleteOrderDetails() {
        return completeOrderDetails;
    }

    public void setExcludedTATdate(String excludedTATdate) {
        this.excludedTATdate = excludedTATdate;
    }

    public String getExcludedTATdate() {
        return excludedTATdate;
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

            if (jsonObject.has("clientInstruction")) {
                try {
                    clientInstruction = jsonObject.getString("clientInstruction");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("numberOfFiles")) {
                try {
                    numberOfFiles = Integer.parseInt(jsonObject.getString("numberOfFiles"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("serviceTypeId")) {
                try {
                    serviceTypeId = Integer.parseInt(jsonObject.getString("serviceTypeId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("transcriptionTypeId")) {
                try {
                    transcriptionTypeId = Integer.parseInt(jsonObject.getString("transcriptionTypeId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("deliveryOptionId")) {
                try {
                    deliveryOptionId = Integer.parseInt(jsonObject.getString("deliveryOptionId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("orderStatusId")) {
                try {
                    orderStatusId = Integer.parseInt(jsonObject.getString("orderStatusId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("valueAddedservId")) {
                try {
                    valueAddedservId = Integer.parseInt(jsonObject.getString("valueAddedservId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("timestampId")) {
                try {
                    timestampId = Integer.parseInt(jsonObject.getString("timestampId"));
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

            if (jsonObject.has("subjectOfFile")) {
                try {
                    subjectOfFile = jsonObject.getString("subjectOfFile");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("instructions")) {
                try {
                    instructions = jsonObject.getString("instructions");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("connectionType")) {
                try {
                    connectionType = Integer.parseInt(jsonObject.getString("connectionType"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("createDate")) {
                try {
                    createDate = jsonObject.getString("createDate");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("totalDuration")) {
                try {
                    totalDuration = jsonObject.getString("totalDuration");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("deliveryDate")) {
                try {
                    deliveryDate = jsonObject.getString("deliveryDate");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("totalFees")) {
                try {
                    totalFees = jsonObject.getString("totalFees");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("transcriptionLink")) {
                try {
                    transcriptionLink = jsonObject.getString("transcriptionLink");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("orderdate")) {
                try {
                    orderdate = jsonObject.getString("orderdate");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("modifiedDate")) {
                try {
                    modifiedDate = jsonObject.getString("modifiedDate");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("fileExtension")) {
                try {
                    fileExtension = jsonObject.getString("fileExtension");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("orderPlaced")) {
                try {
                    orderPlaced = jsonObject.getString("orderPlaced");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("completeOrderDate")) {
                try {
                    completeOrderDate = jsonObject.getString("completeOrderDate");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("completeOrderDetails")) {
                try {
                    completeOrderDetails = jsonObject.getString("completeOrderDetails");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("excludedTATdate")) {
                try {
                    excludedTATdate = jsonObject.getString("excludedTATdate");
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }

    }


}//END