package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Clinic {
	private int id;
	private String name;
	
	DBConnection conn=new DBConnection();
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	PreparedStatement preparedStatement=null;
	
	public Clinic() {}
	
	public Clinic(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Clinic [id=" + id + ", name=" + name + "]";
	}
	public ArrayList<Clinic> getList() throws SQLException {//doktor yönetimi sayfasına veritabanından doktorları getirecek olan yapı
		ArrayList<Clinic>list =new ArrayList<>();
		Clinic obj;
		try {//veritabanında ki clinice ait columnları java nesnelerine aktarma komutu
			con=conn.connDb();
			st=con.createStatement();
			rs=st.executeQuery("SELECT * FROM clinic");
			while(rs.next()) {
				obj=new Clinic();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			st.close();
			rs.close();
			con.close();  
		}
		
		return list;
	} 
	//klinkleri bashekimguıden updateclinic arayüzüne gönderme prosesi
	public Clinic getFetch(int id)  {
		Clinic c=new Clinic();
		try {
		con=conn.connDb();
		st=con.createStatement();
		rs=st.executeQuery("SELECT * FROM clinic WHERE id =" + id);
		while(rs.next()) {
			c.setId(rs.getInt("id"));
			c.setName(rs.getString("name"));
			break;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return c;
	}
	public boolean addclinic(String name) throws SQLException {
		String query="INSERT INTO clinic"+"(name) VALUES "+"(?)";
		boolean key=false;
		try {
			con=conn.connDb();
			st=con.createStatement();
			preparedStatement=con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.executeUpdate();
			key=true;
			//hazır bir statement olduğu için soru işareti yerine denk gelen verileri eşleştirdik
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(key) 
			return true;
	
		else
			return false;	
	}
	public boolean deleteclinic(int id) throws SQLException {
		String query="DELETE FROM clinic WHERE id=?";
		boolean key=false;
		try {
			con=conn.connDb();
			st=con.createStatement();
			preparedStatement=con.prepareStatement(query);
			preparedStatement.setInt(1,id);
			preparedStatement.executeUpdate();
			key=true;
			//hazır bir statement olduğu için soru işareti yerine denk gelen verileri eşleştirdik
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(key) 
			return true;
	
		else
			return false;	
	}
	public boolean updateclinic(int id,String name) throws SQLException {
		String query="UPDATE clinic SET name = ? WHERE id = ?";
		boolean key=false;
		con=conn.connDb();
		try {
			st=con.createStatement();
			preparedStatement=con.prepareStatement(query);
			preparedStatement.setString(1,name);
			preparedStatement.setInt(2,id);
			preparedStatement.executeUpdate();
			key=true;
			//hazır bir statement olduğu için soru işareti yerine denk gelen verileri eşleştirdik
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(key) 
			return true;
	
		else
			return false;
		
		
		
	}
	public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException {//doktor yönetimi sayfasına veritabanından doktorları getirecek olan yapı
		ArrayList<User>list =new ArrayList<>();
		User obj;
		try {
		con=conn.connDb();
		st=con.createStatement();
		rs=st.executeQuery("SELECT u.id,u.tcno,u.type,u.name,u.password FROM worker w LEFT JOIN user u ON w.user_id = u.id WHERE clinic_id="+clinic_id);
		while(rs.next()) {
			obj=new User(rs.getInt("u.id"),rs.getString("u.tcno"),rs.getString("u.name"),rs.getString("u.password"),rs.getString("u.type"));
			list.add(obj);
			
		}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			st.close();
			rs.close();
			//con.close();
		}
		return list;
	}
	
	
	

}
