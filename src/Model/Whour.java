package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Whour {
	private int id,doctor_id;
	private String doctor_name,wdate,status;
	
	DBConnection conn=new DBConnection();
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	PreparedStatement preparedStatement=null;
	
	public Whour() {}
	
	public Whour(int id, int doctor_id, String doctor_name, String wdate, String status) {
		super();
		this.id = id;
		this.doctor_id = doctor_id;
		this.doctor_name = doctor_name;
		this.wdate = wdate;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public ArrayList<Whour> getwhourList(int doctor_id) throws SQLException {//doktor yönetimi sayfasına veritabanından doktorları getirecek olan yap ı
		ArrayList<Whour>list =new ArrayList<>();
		Whour obj;
		try {
		con=conn.connDb();
		st=con.createStatement();
		rs=st.executeQuery("SELECT * FROM whour WHERE status ='a' AND doctor_id = "+doctor_id );
		while(rs.next()) {
			//burada veritabanında ki whhour nesnesi ile javada ki whour nesnesi eşleşiyo.
			obj=new Whour();
			obj.setId(rs.getInt("id"));
			obj.setDoctor_id(rs.getInt("doctor_id"));
			obj.setDoctor_name(rs.getString("doctor_name"));
			obj.setStatus(rs.getString("status"));
			obj.setWdate(rs.getString("wdate_"));
			
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
