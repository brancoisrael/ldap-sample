package model;

import java.io.Serializable;

public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2748837857727564816L;

	private String cn;
	
	private String sn;
	
	private String email;
	
	private String uid;
	
	private String userPassword;

	public Usuario(){}
	
	public Usuario(String cn, String sn, String email, String uid, String userPassword) {
		
		this.cn = cn;
		this.sn = sn;
		this.email = email;
		this.uid = uid;
		this.userPassword = userPassword;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	 @Override
	public String toString() {
		
		return "cn "+cn + " sn " + sn + " email  " +email + " uid "+ uid + " userPassword " + userPassword;
	}
	
}
