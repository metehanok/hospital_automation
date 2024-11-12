package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Helper.Helper;

public class Hasta extends User {
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	PreparedStatement preparedStatement=null; 

	public Hasta() {
	}

	public Hasta(int id, String tcno, String name, String pasword, String type) {
		super(id, tcno, name, pasword, type);
		// TODO Auto-generated constructor stub
	}
	public boolean register( String tcno, String password,String name) throws SQLException {
		int key=0;
		boolean duplicate =false; 
		String query="INSERT INTO user"+"(tcno,name,password,type) VALUES "+"(?,?,?,?)";
		try {
		con=conn.connDb();
		st=con.createStatement();
		rs=st.executeQuery("SELECT * FROM user WHERE tcno = '" + tcno + "'" );
		while(rs.next()) {
			duplicate=true;//aynı saatler aralığında randevu alınmasının önlemek için yapıldı
			Helper.showMessage("Bu TC Numarası ile kayıt bulunmaktadır!");
			break;
			
		}
		if(!duplicate)
		{	
		preparedStatement=con.prepareStatement(query);
		preparedStatement.setString(1,tcno);
		preparedStatement.setString(2,password);
		preparedStatement.setString(3,name);
		preparedStatement.setString(4,"hasta");
		preparedStatement.executeUpdate();
		key=1;
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		if(key==1) {
			return true;
			
		}
		else return false;
	
		
	}
	public boolean addAppointment(int doktor_id,String doktor_name,int hasta_id,String hasta_name,String app_date) throws SQLException {
		int key=0;
		
		String query="INSERT INTO appointment"+"(doktor_id,doktor_name,hasta_id,hasta_name,app_date) VALUES "+"(?,?,?,?,?)";
		try {
		con=conn.connDb();
		st=con.createStatement();
		preparedStatement=con.prepareStatement(query);
		preparedStatement.setInt(1,doktor_id);
		preparedStatement.setString(2,doktor_name);
		preparedStatement.setInt(3,hasta_id);
		preparedStatement.setString(4,hasta_name);
		preparedStatement.setString(5,app_date);
		preparedStatement.executeUpdate();
		key=1;
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		if(key==1) {
			return true;
			
		}
		else return false;
	
		
	}
	public boolean updatewhourStatus(int doktor_id,String app_date) throws SQLException {
		int key=0;
		
		String query="UPDATE whour SET status = ? WHERE doctor_id = ? AND  wdate = ?";
		try {
		con=conn.connDb();
		st=con.createStatement();
		preparedStatement=con.prepareStatement(query);
		preparedStatement.setString(1,"p");
		preparedStatement.setInt(2,doktor_id);
		preparedStatement.setString(2,app_date);
		preparedStatement.executeUpdate();
		key=1;
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		if(key==1) {
			return true;
			
		}
		else return false;
	
		
	}
	
	

}
