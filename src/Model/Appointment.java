package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Helper.DBConnection;

public class Appointment {
	
	DBConnection conn=new DBConnection();
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	PreparedStatement preparedStatement=null;
	
	private int id,doktorId,hastaId;
	private String doktorName,hastaName,appDate;
	
	public Appointment() {}
	public Appointment(int id, int doktorId, int hastaId, String doktorName, String hastaName, String appDate) {
		super();
		this.id = id;
		this.doktorId = doktorId;
		this.hastaId = hastaId;
		this.doktorName = doktorName;
		this.hastaName = hastaName;
		this.appDate = appDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDoktorId() {
		return doktorId;
	}
	public void setDoktorId(int doktorId) {
		this.doktorId = doktorId;
	}
	public int getHastaId() {
		return hastaId;
	}
	public void setHastaId(int hastaId) {
		this.hastaId = hastaId;
	}
	public String getDoktorName() {
		return doktorName;
	}
	public void setDoktorName(String doktorName) {
		this.doktorName = doktorName;
	}
	public String getHastaName() {
		return hastaName;
	}
	public void setHastaName(String hastaName) {
		this.hastaName = hastaName;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	
	
	

}
