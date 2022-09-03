package car.rent.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBInformation {   //DB information(db와 관련된 정보 ) 위한 클래스 
	private String dbDriver = "org.mariadb.jdbc.Driver";
	private String url = "jdbc:mariadb://localhost:13306/rend_car";
	private String id = "root";
	private String passwd = "tkfkd#486";
	public String getDbDriver() {
		return dbDriver;
	}
	public String getUrl() {
		return url;
	}
	public String getId() {
		return id;
	}
	public String getPasswd() {
		return passwd;
	}
	
	public Connection getConn() throws SQLException { 
		return DriverManager.getConnection(getUrl(),getId(),getPasswd());
	}
	
}
