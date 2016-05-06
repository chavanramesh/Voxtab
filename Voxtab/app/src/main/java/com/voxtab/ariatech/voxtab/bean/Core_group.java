

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Core_group {



 int  group_id =0;
 String  group_name ="" ;
 String  arr_group_name ="" ;
 String  pos_level ="" ;
 String  pos_created ="" ;
 public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }
 public int getGroup_id() {
        return group_id;
    }

public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
 public String getGroup_name() {
        return group_name;
    }

public void setArr_group_name(String arr_group_name) {
        this.arr_group_name = arr_group_name;
    }
 public String getArr_group_name() {
        return arr_group_name;
    }

public void setPos_level(String pos_level) {
        this.pos_level = pos_level;
    }
 public String getPos_level() {
        return pos_level;
    }

public void setPos_created(String pos_created) {
        this.pos_created = pos_created;
    }
 public String getPos_created() {
        return pos_created;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("group_id")) { 
 try {
 group_id= Integer.parseInt(jsonObject.getString("group_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("group_name")) { 
 try {
 group_name= jsonObject.getString("group_name");
 } catch (Exception e) {}
 }

if (jsonObject.has("arr_group_name")) { 
 try {
 arr_group_name= jsonObject.getString("arr_group_name");
 } catch (Exception e) {}
 }

if (jsonObject.has("pos_level")) { 
 try {
 pos_level= jsonObject.getString("pos_level");
 } catch (Exception e) {}
 }

if (jsonObject.has("pos_created")) { 
 try {
 pos_created= jsonObject.getString("pos_created");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END