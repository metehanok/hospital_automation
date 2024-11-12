package Helper;
import java.sql.*;

public class DBConnection {
	Connection c=null;
	
	public DBConnection () {
		//boş constructor
	}
	public Connection connDb() throws SQLException {
		return this.c=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_automation?user=root&password=12345");
		//veritabanı bağlantı adresi
	}
	

} 
