package com.dogesoft.jw.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.dogesoft.jw.entity.User;

public class DBOUtils {
	
	public static Connection getLocalConnection(){
		Connection conn=null;
		String url="jdbc:mysql://localhost:3306/medtronic?useUnicode=true&characterEncoding=utf-8";
		String user="root";
		String password="root";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(url, user, password);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	public static boolean insertEmpData(Connection conn,List<Object> empList){
		boolean insertRes=false;
		PreparedStatement ps=null;
		String sql="INSERT INTO employee"
				+ "(adaccount,empname,mobile,emprole,managebrand,org,dlmaccount,jwuid,empstatus)"
				+ "values"
				+ "(?,?,?,?,?,?,?,?,?)";
		try {
			for(Object emp:empList){
				
				ps=conn.prepareStatement(sql);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return insertRes;
	}
	
	public static boolean insertEmployeeData(Connection conn,List<User> userList){
		boolean rs=false;
		PreparedStatement ps=null;
		String str="                             ";
		String sql="INSERT INTO user_spine "
				+ " (Network_ID,Employee_ID,Email_ID,Email,Last_Name_CN,First_Name_CN,Last_Name_EN,"
				+ " First_Name_EN,Legal_Name,Preferred_Name,Age,Original_Hire_Date,Last_Hire_Date,"
				+ " Gender_ID,Gender,Gender_Code,Mobile_Number,Business_Group,"
				+ " Business_Group_ID,  Business_Organization_Reference_ID,"
				+ "  Business_Custom_Organization_Reference_ID,  Division_ID,  Division_Name,"
				+ "  Division_Name_ID,  Division_Organization_Reference_ID ,"
				+ " Division_Custom_Organization_Reference_ID,  Cost_Center,  Cost_Center_Name,"
				+ "  Company_ID,  Company_Name_EN , Worker_Type,  Worker_Type_ID,  Employee_Type_ID,"
				+ "  Work_Country,  Work_Province,  Work_Address_City,  Work_Location,"
				+ "  Location_Code,  Job_Function , Job_Function_ID , Job_Family_ID ,"
				+ " Job_Family , Job_Family_WID,  Job_Family_ID_Ref,  Job_Code,  Job_Title,"
				+ "  Job_Level,  Business_Title,  Status,  Is_People_Manager,  Manager_ID,"
				+ "  Manager_Name , Sup_Org_ID , Sup_Org,  Sup_Org_WID,"
				+ "  Sup_Org_Organization_Reference_ID,  Sup_Org_Superior_ID,"
				+ "  Sup_Org_Superior,  Sup_Org_Superior_WID,  Sup_Org_Superior_Organization_Reference_ID,Job_Func_Family_ID )"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			for(User user:userList){
				ps=conn.prepareStatement(sql);
				
				ps.setString(1, user.getNetworkId());
				ps.setString(2, user.getEmployeeId());
				ps.setString(3, user.getEmailWId());
				ps.setString(4, user.getEmail());
				ps.setString(5, user.getLastnamecn());
				ps.setString(6, user.getFirstnamecn());
				ps.setString(7, user.getLastnameen());
				ps.setString(8, user.getFirstnameen());
				ps.setString(9, user.getLegalname());
				ps.setString(10, user.getPreferredname());
				ps.setString(11, user.getAge());
				ps.setString(12, user.getOriginalHireDate());
				ps.setString(13, user.getLastHireDate());
				ps.setString(14, user.getGenderWId());
				ps.setString(15, user.getGender());
				ps.setString(16, user.getGenderCode());
				ps.setString(17, user.getMobileNumber());
				ps.setString(18, user.getBusinessGroup());
				ps.setString(19, user.getBusinessGroupWId());
				ps.setString(20, user.getBusOrgRefId());
				ps.setString(21, user.getBusCusOrgRefId());
				ps.setString(22, user.getDivisionId());
				ps.setString(23, user.getDivisionName());
				ps.setString(24, user.getDivisionWId());
				ps.setString(25, user.getDivOrgRefId());
				ps.setString(26, user.getDivCusOrgRefId());
				ps.setString(27, user.getCostCenter());
				ps.setString(28, user.getCostCenterName());
				ps.setString(29, user.getCompanyId());
				ps.setString(30, user.getCompanyNameEn());
				ps.setString(31, user.getWorkerType());
				ps.setString(32, user.getWorkerTypeWId());
				ps.setString(33, user.getWorkTypeEmpId());
				ps.setString(34, user.getWorkCountry());
				ps.setString(35, user.getWorkProvince());
				ps.setString(36, user.getWorkAddrCity());
				ps.setString(37, user.getWrokLocation());
				ps.setString(38, user.getLocationCode());
				ps.setString(39, user.getJobFunction());
				ps.setString(40, user.getJobFunctionWId());
				ps.setString(41, user.getJobFamilyId());
				ps.setString(42, user.getJobFamily());
				ps.setString(43, user.getJobFamilyWId());
				ps.setString(44, user.getJobFamilyIdJobFamId());
				ps.setString(45, user.getJobCode());
				ps.setString(46, user.getJobTitle());
				ps.setString(47, user.getJobLevel());
				ps.setString(48, user.getBusinessTitle());
				ps.setString(49, user.getStatus());
				ps.setString(50, user.getIsPepManager());
				ps.setString(51, user.getManagerId());
				ps.setString(52, user.getManagerName());
				ps.setString(53, user.getSupOrgId());
				ps.setString(54, user.getSupOrg());
				ps.setString(55, user.getSupOrgWId());
				ps.setString(56, user.getSupOrgRefId());
				ps.setString(57, user.getSupOrgSuperiorId());
				ps.setString(58, user.getSupOrgSuperior());
				ps.setString(59, user.getSupOrgSuperiorWId());
				ps.setString(60, user.getSupOrgSuperOrgRefId());
				ps.setString(61, user.getJobFuncIdJobFamId());
				
				rs=ps.execute();
				rs=true;
				//}
				System.out.println("insert user success! name: "+user.getPreferredname());
			}
		} catch (SQLException e) {
			rs=false;
			e.printStackTrace();
		}finally{
			closeConnection(conn, ps);
			
		}
		return rs;
	}
	
	public static void closeConnection(Connection conn,PreparedStatement ps){
		try {
			if(ps!=null){
				ps.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}

}
