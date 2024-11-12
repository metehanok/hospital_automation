package Model;

import Helper.DBConnection;

public class User {
	private int id;
	private String tcno;
	private String name;
	private String pasword;
	private String type;
	DBConnection conn=new DBConnection();
	
	public User() {
		
	}
	public User(int id, String tcno, String name, String pasword, String type) {
		super();
		this.id = id;
		this.tcno = tcno;
		this.name = name;
		this.pasword = pasword;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTcno() {
		return tcno;
	}
	public void setTcno(String tcno) {
		this.tcno = tcno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasword() {
		return pasword;
	}
	public void setPasword(String pasword) {
		this.pasword = pasword;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", tcno=" + tcno + ", name=" + name + ", pasword=" + pasword + ", type=" + type + "]";
	}
	
	

}
