package com.dogesoft.jw.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dogesoft.jw.entity.User;
import com.dogesoft.jw.utils.DBOUtils;
import com.dogesoft.jw.utils.StringUtils;


public class ParseXML {
	

	public static void main(String[] args) {
//		readXMLFileFromGivenPath();
		parseXMLWithDom4j();
		

		System.exit(1);
	}
	public static void parseXMLWithDom4j(){
		// 解析books.xml文件
        // 创建SAXReader的对象reader
        SAXReader reader = new SAXReader();
        Map<String,String> eleMap=new HashMap<String,String>();
        List<Object> jsonList=new ArrayList<Object>();
        try {
            // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
            Document document = reader.read(new File("src/resources/med_wd_infohub.xml"));
            // 通过document对象获取根节点bookstore
            
            Element userDetail = document.getRootElement();
            // 通过element对象的elementIterator方法获取迭代器
            Iterator<Element> it = userDetail.elementIterator();
            // 遍历迭代器，获取根节点中的信息（书籍）
            while (it.hasNext()) {
                System.out.println("=====开始遍历某个人=====");
                User nodeUser=new User();
                List<String> nodeAttrList=new ArrayList<String>();
                Element user = (Element) it.next();
                // 获取book的属性名以及 属性值
                List<Attribute> userAttrs = user.attributes();
//                for (Attribute attr : userAttrs) {
//                    System.out.println("属性名：" + attr.getName() + " --属性值："
//                            + attr.getValue());
//                }
                Iterator<Element> itt = user.elementIterator();
                while (itt.hasNext()) {
                    Element userChild = (Element) itt.next();
                    if(userChild.elements().size()>0){
                    	
                    	System.out.println("节点名：" + userChild.getName());
                    	List<Attribute> userChildList=userChild.attributes();
                        
                        if(userChildList!=null&&userChildList.size()>0){
                        	for(Attribute userCChild:userChildList){
                        		System.out.println("----节点属性：" + userCChild.getName() + " --节点属性值：" + userCChild.getStringValue());
                        		eleMap.put(userChild.getName(), userCChild.getStringValue());
                        	}
                        }
                    	Iterator<Element> userElement=userChild.elements().iterator();
                    	int i=1;
                    	while(userElement.hasNext()){
                    		Element userEmt=userElement.next();
                    		
                    		System.out.println("--子节点" + (i++)+" :");
                    		List<Attribute> userEmtAttrList=userEmt.attributes();
                    		if(userEmtAttrList!=null&& userEmtAttrList.size()>0){
                    			for(Attribute userEmtAttr:userEmtAttrList){
                    				System.out.println("----子节点类型：" + userEmtAttr.getStringValue() + " --子节点类型值：" + userEmt.getStringValue());
                    				eleMap.put(userChild.getName()+"_"+userEmtAttr.getStringValue(), userEmt.getStringValue());
                    			}
                    		}
//                    		System.out.println("----节点值：" + userEmt.getStringValue());
                    	}
                    }else{
                    	 List<Attribute> userChildList=userChild.attributes();
                    	 System.out.println("节点名：" + userChild.getName());
                         if(userChildList!=null&&userChildList.size()>0){
                         	for(Attribute userCChild:userChildList){
                         		System.out.println("----属性：" + userCChild.getName() + " --属性值：" + userCChild.getStringValue());
                         		eleMap.put(userCChild.getName(), userCChild.getStringValue());
                         	}
                         }
                    	System.out.println(" --节点值：" + userChild.getStringValue());
                    	eleMap.put(userChild.getName(), userChild.getStringValue());
                    }
                    
                   
                }
                System.out.println("=====结束遍历某个人=====");
                JSONObject nodeJson=JSONObject.fromObject(eleMap);
                System.out.println(nodeJson.toString());
                jsonList.add(nodeJson);
                
                
            }
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONArray nodeArr=JSONArray.fromObject(jsonList.toArray());
        System.out.println(nodeArr.size());
        
        setUserInfoByGivenJsonObject(nodeArr);
	}
	
	public static void setUserInfoByGivenJsonObject(Object obj){
		JSONArray nodeArr=JSONArray.fromObject(obj);
		List<Object> jsonList=Arrays.asList(nodeArr.toArray());
		List<User> userList=new ArrayList<User>();
		for(Object object:jsonList){
			JSONObject json=JSONObject.fromObject(object);
			User user=new User();
			
			if(json.containsKey("Age")){
				user.setAge(json.getString("Age"));
			}
           if(json.containsKey("Business_Group_Custom_Organization_Reference_ID")){
				
        	   user.setBusCusOrgRefId(json.getString("Business_Group_Custom_Organization_Reference_ID"));
			}
           if(json.containsKey("Business_Group")){
        	   user.setBusinessGroup(json.getString("Business_Group"));
			}
			
           if(json.containsKey("Business_Group_WID")){
				
        	   user.setBusinessGroupWId(json.getString("Business_Group_WID"));
			}
           if(json.containsKey("Business_Title")){
        	   user.setBusinessTitle(json.getString("Business_Title"));
			}
			
           if(json.containsKey("Business_Group_Organization_Reference_ID")){
        	   user.setBusOrgRefId(json.getString("Business_Group_Organization_Reference_ID"));
			}
           
           if(json.containsKey("Company_ID")){
        	   user.setCompanyId(json.getString("Company_ID"));
			}
			
           if(json.containsKey("Company_Name_EN")){
        	   user.setCompanyNameEn(json.getString("Company_Name_EN"));
			}
		if(json.containsKey("Cost_Center")){
			user.setCostCenter(json.getString("Cost_Center"));
		}
		if(json.containsKey("Cost_Center_Name")){
			user.setCostCenterName(json.getString("Cost_Center_Name"));
		}
		if(json.containsKey("Division_Name_Custom_Organization_Reference_ID")){
			user.setDivCusOrgRefId(json.getString("Division_Name_Custom_Organization_Reference_ID"));
		}
		if(json.containsKey("Division_ID")){
			user.setDivisionId(json.getString("Division_ID"));
		}
		if(json.containsKey("Division_Name")){
			user.setDivisionName(json.getString("Division_Name"));
		}
		if(json.containsKey("Division_Name_WID")){
			user.setDivisionWId(json.getString("Division_Name_WID"));
		}
		if(json.containsKey("Division_Name_Organization_Reference_ID")){
			user.setDivOrgRefId(json.getString("Division_Name_Organization_Reference_ID"));
		}
		if(json.containsKey("Email_ID")){
			user.setEmail(json.getString("Email_ID"));
		}
		if(json.containsKey("Email_ID_WID")){
			user.setEmailWId(json.getString("Email_ID_WID"));
		}
		if(json.containsKey("Employee_ID")){
			user.setEmployeeId(json.getString("Employee_ID"));
		}
		if(json.containsKey("First_Name_CN")){
			user.setFirstnamecn(json.getString("First_Name_CN"));
		}
		if(json.containsKey("First_Name_EN")){
			user.setFirstnameen(json.getString("First_Name_EN"));
		}
		if(json.containsKey("Gender")){
			user.setGender(json.getString("Gender"));
		}
		if(json.containsKey("Gender_Gender_Code")){
			user.setGenderCode(json.getString("Gender_Gender_Code"));
		}
		if(json.containsKey("Gender_WID")){
			user.setGenderWId(json.getString("Gender_WID"));
		}
		if(json.containsKey("Is_People_Manager")){
			user.setIsPepManager(json.getString("Is_People_Manager"));
		}
		if(json.containsKey("Job_Code")){
			user.setJobCode(json.getString("Job_Code"));
		}
			if(json.containsKey("Job_Family")){
				user.setJobFamily(json.getString("Job_Family"));
			}
			if(json.containsKey("Job_Family_ID")){
				user.setJobFamilyId(json.getString("Job_Family_ID"));
			}
			if(json.containsKey("Job_Family_Job_Family_ID")){
				user.setJobFamilyIdJobFamId(json.getString("Job_Family_Job_Family_ID"));
			}
			if(json.containsKey("Job_Family_WID")){
				user.setJobFamilyWId(json.getString("Job_Family_WID"));
			}
			if(json.containsKey("Job_Function")){
				user.setJobFunction(json.getString("Job_Function"));
			}
			if(json.containsKey("Job_Function_Job_Family_ID")){
				user.setJobFuncIdJobFamId(json.getString("Job_Function_Job_Family_ID"));
			}
			if(json.containsKey("Job_Function_WID")){
				user.setJobFunctionWId(json.getString("Job_Function_WID"));
			}
			if(json.containsKey("Job_Level")){
				user.setJobLevel(json.getString("Job_Level"));
			}
			if(json.containsKey("Job_Title")){
				user.setJobTitle(json.getString("Job_Title"));
			}
			if(json.containsKey("Last_Hire_Date")){
				user.setLastHireDate(json.getString("Last_Hire_Date"));
			}
			if(json.containsKey("Last_Name_CN")){
				user.setLastnamecn(json.getString("Last_Name_CN"));
			}
			if(json.containsKey("Last_Name_EN")){
				user.setLastnameen(json.getString("Last_Name_EN"));
			}
			if(json.containsKey("Legal_Name")){
				user.setLegalname(json.getString("Legal_Name"));
			}
			if(json.containsKey("Location_Code")){
				user.setLocationCode(json.getString("Location_Code"));
			}
			if(json.containsKey("Manager_ID")){
				user.setManagerId(json.getString("Manager_ID"));
			}
			if(json.containsKey("Manager_Name")){
				user.setManagerName(json.getString("Manager_Name"));
			}
			if(json.containsKey("Mobile_Number")){
				user.setMobileNumber(json.getString("Mobile_Number"));
			}
			if(json.containsKey("Network_ID")){
				user.setNetworkId(json.getString("Network_ID"));
			}
			if(json.containsKey("Original_Hire_Date")){
				user.setOriginalHireDate(json.getString("Original_Hire_Date"));
			}
			if(json.containsKey("Preferred_Name")){
				user.setPreferredname(json.getString("Preferred_Name"));
			}
			if(json.containsKey("Status")){
				user.setStatus(json.getString("Status"));
			}
			if(json.containsKey("Sup_Org")){
				user.setSupOrg(json.getString("Sup_Org"));
			}
			if(json.containsKey("Sup_Org_ID")){
				user.setSupOrgId(json.getString("Sup_Org_ID"));
			}
			if(json.containsKey("Sup_Org_Organization_Reference_ID")){
				user.setSupOrgRefId(json.getString("Sup_Org_Organization_Reference_ID"));
			}
			if(json.containsKey("Sup_Org_Superior")){
				user.setSupOrgSuperior(json.getString("Sup_Org_Superior"));
			}
			if(json.containsKey("Sup_Org_Superior_ID")){
				user.setSupOrgSuperiorId(json.getString("Sup_Org_Superior_ID"));
			}
			if(json.containsKey("Sup_Org_Superior_WID")){
				user.setSupOrgSuperiorWId(json.getString("Sup_Org_Superior_WID"));
			}
			if(json.containsKey("Sup_Org_Superior_Organization_Reference_ID")){
				user.setSupOrgSuperOrgRefId(json.getString("Sup_Org_Superior_Organization_Reference_ID"));
			}
			if(json.containsKey("Sup_Org_WID")){
				user.setSupOrgWId(json.getString("Sup_Org_WID"));
			}
			if(json.containsKey("Work_Address_-_City")){
				user.setWorkAddrCity(json.getString("Work_Address_-_City"));
			}
			if(json.containsKey("Work_Country")){
				user.setWorkCountry(json.getString("Work_Country"));
			}
			if(json.containsKey("Worker_Type")){
				user.setWorkerType(json.getString("Worker_Type"));
			}
			if(json.containsKey("Worker_Type_WID")){
				user.setWorkerTypeWId(json.getString("Worker_Type_WID"));
			}
			if(json.containsKey("Work_Province")){
				user.setWorkProvince(json.getString("Work_Province"));
			}
			if(json.containsKey("Work_Location")){
				user.setWrokLocation(json.getString("Work_Location"));
			}
			if(json.containsKey("Worker_Type_Employee_Type_ID")){
				user.setWorkTypeEmpId(json.getString("Worker_Type_Employee_Type_ID"));
			}
			userList.add(user);
			
		}
		System.out.println(userList.size());
		
		boolean result=addUserByUserList(userList);
		System.out.println(result);
	}
	
	public static boolean addUserByUserList(List<User> userList){
		
		Connection conn=DBOUtils.getLocalConnection();
		boolean result=DBOUtils.insertEmployeeData(conn, userList);
		return result;
		
	}
	
	public static void readXMLFileFromGivenPath(){
//			file = new FileInputStream("src/resources/med_oneuser.xml");
					File file=new File("src/resources/med_infohub.xml");
			        //字符流输出
			        BufferedReader reader;
			        String string=null;
			        StringBuffer sb = new StringBuffer();
					try {
						reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
						 while((string = reader.readLine())!=null){
					            sb.append(string);
					        }
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			       
			       
			        XMLSerializer xmlSerializer=new XMLSerializer();
			        JSON json=xmlSerializer.read(sb.toString());
			        JSONObject xmlJson=JSONObject.fromObject(json);
			        String xmlStr=xmlJson.toString();
			        //这一句的输出,也许你很快的就知道原理了,其实原理很简单的！
			        System.out.println("重点处：\n"+xmlStr+"\n");
//			        
//			        JSONObject jsonObject=JSONObject.fromObject((json.toString()).substring(1, json.toString().length()-1));    
//			        System.out.println("截取后：\n"+jsonObject.toString(1)+"\n");
		
		
	}

}
