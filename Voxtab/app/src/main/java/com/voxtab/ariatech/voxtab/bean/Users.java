


package com.voxtab.ariatech.voxtab.bean;


import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

public class Users {


    int user_id = 0;
    String membership_id = "";
    int location_id = 0;
    String email = "";
    String first_name = "";
    String first_name_fn = "";
    String last_name = "";
    String last_name_fn = "";
    String password = "";
    String join_date = "";
    String photo = "";
    String gender = "";
    String auth_key = "";
    String reg_date = "";
    String modified_date = "";
    String dob = "";
    String designation = "";
    String department = "";
    String email_2 = "";
    String mobile_no = "";
    String mobile_no_2 = "";
    String extension = "";
    String landline = "";
    String noti_setting_type = "";
    String upload_setting_type = "";
    String username = "";
    String imei = "";
    String is_email_verified = "";

    String imgBase64="";

    Company_info company_info=new Company_info();
    Company_location company_location=new Company_location();

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setMembership_id(String membership_id) {
        this.membership_id = membership_id;
    }

    public String getMembership_id() {
        return membership_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name_fn(String first_name_fn) {
        this.first_name_fn = first_name_fn;
    }

    public String getFirst_name_fn() {
        return first_name_fn;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name_fn(String last_name_fn) {
        this.last_name_fn = last_name_fn;
    }

    public String getLast_name_fn() {
        return last_name_fn;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public String getAuth_key() {
        return auth_key;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setEmail_2(String email_2) {
        this.email_2 = email_2;
    }

    public String getEmail_2() {
        return email_2;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no_2(String mobile_no_2) {
        this.mobile_no_2 = mobile_no_2;
    }

    public String getMobile_no_2() {
        return mobile_no_2;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getLandline() {
        return landline;
    }

    public void setNoti_setting_type(String noti_setting_type) {
        this.noti_setting_type = noti_setting_type;
    }

    public String getNoti_setting_type() {
        return noti_setting_type;
    }

    public void setUpload_setting_type(String upload_setting_type) {
        this.upload_setting_type = upload_setting_type;
    }

    public String getUpload_setting_type() {
        return upload_setting_type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImei() {
        return imei;
    }

    public void setIs_email_verified(String is_email_verified) {
        this.is_email_verified = is_email_verified;
    }

    public String getIs_email_verified() {
        return is_email_verified;
    }

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

    public Company_info getCompany_info() {
        return company_info;
    }

    public void setCompany_info(Company_info company_info) {
        this.company_info = company_info;
    }

    public Company_location getCompany_location() {
        return company_location;
    }

    public void setCompany_location(Company_location company_location) {
        this.company_location = company_location;
    }

    public void parseJSON(JSONObject jsonObject) {
        try {
            if (jsonObject.has("user_id")) {
                try {
                    user_id = Integer.parseInt(jsonObject.getString("user_id"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("membership_id")) {
                try {
                    membership_id = jsonObject.getString("membership_id");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("location_id")) {
                try {
                    location_id = Integer.parseInt(jsonObject.getString("location_id"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("email")) {
                try {
                    email = jsonObject.getString("email");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("first_name")) {
                try {
                    first_name = jsonObject.getString("first_name");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("first_name_fn")) {
                try {
                    first_name_fn = jsonObject.getString("first_name_fn");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("last_name")) {
                try {
                    last_name = jsonObject.getString("last_name");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("last_name_fn")) {
                try {
                    last_name_fn = jsonObject.getString("last_name_fn");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("password")) {
                try {
                    password = jsonObject.getString("password");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("join_date")) {
                try {
                    join_date = jsonObject.getString("join_date");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("photo")) {
                try {
                    photo = jsonObject.getString("photo");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("gender")) {
                try {
                    gender = jsonObject.getString("gender");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("auth_key")) {
                try {
                    auth_key = jsonObject.getString("auth_key");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("reg_date")) {
                try {
                    reg_date = jsonObject.getString("reg_date");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("modified_date")) {
                try {
                    modified_date = jsonObject.getString("modified_date");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("dob")) {
                try {
                    dob = jsonObject.getString("dob");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("designation")) {
                try {
                    designation = jsonObject.getString("designation");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("department")) {
                try {
                    department = jsonObject.getString("department");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("email_2")) {
                try {
                    email_2 = jsonObject.getString("email_2");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("mobile_no")) {
                try {
                    mobile_no = jsonObject.getString("mobile_no");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("mobile_no_2")) {
                try {
                    mobile_no_2 = jsonObject.getString("mobile_no_2");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("extension")) {
                try {
                    extension = jsonObject.getString("extension");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("landline")) {
                try {
                    landline = jsonObject.getString("landline");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("noti_setting_type")) {
                try {
                    noti_setting_type = jsonObject.getString("noti_setting_type");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("upload_setting_type")) {
                try {
                    upload_setting_type = jsonObject.getString("upload_setting_type");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("username")) {
                try {
                    username = jsonObject.getString("username");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("imei")) {
                try {
                    imei = jsonObject.getString("imei");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("is_email_verified")) {
                try {
                    is_email_verified = jsonObject.getString("is_email_verified");
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }

    }


}//END