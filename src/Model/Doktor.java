package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Doktor extends User{
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	PreparedStatement preparedStatement=null;
	public Doktor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Doktor(int id, String tcno, String name, String pasword, String type) {
		super(id, tcno, name, pasword, type);
		// TODO Auto-generated constructor stub
	}
	public boolean addwhour(int doctor_id, String doctor_name, String wdate) throws SQLException {
		int key=0;
		int count =0; 
		String query="INSERT INTO whour"+"(doctor_id,doctor_name,wdate_) VALUES "+"(?,?,?)";
		try {
		con=conn.connDb();
		st=con.createStatement();
		rs=st.executeQuery("SELECT * FROM whour WHERE status='a' AND doctor_id = " + doctor_id + " AND wdate_ ='" + wdate + "'");
		while(rs.next()) {
			count++;//aynı saatler aralığında randevu alınmasının önlemek için yapıldı
			break;
			
		}
		if(count ==0)
		{	
		preparedStatement=con.prepareStatement(query);
		preparedStatement.setInt(1,doctor_id);
		preparedStatement.setString(2,doctor_name);
		preparedStatement.setString(3,wdate);
		preparedStatement.executeUpdate();
		
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		key=1;
		
		if(key==1) {
			return true;
			
		}
		else return false;
	
		
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
	public boolean deletewhour(int id) throws SQLException {
		String query="DELETE FROM whour WHERE id = ?";
		boolean key=false;
		try {
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
	

}
