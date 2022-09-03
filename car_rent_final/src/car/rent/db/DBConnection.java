package car.rent.db;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {   // jdbc 연결

	private static Connection conn;
	//생성자를 private 으로 바꾼다.
	//생성자를 통한 객체화를 막기 위해서
	DBConnection() {}
	
	//데이터베이스 커넥션을 전달해주는 메서드
	public static void getConnection() throws Exception {
		if(conn == null || conn.isClosed()) {
			connectDB(); // 디비 접속 실행
		}
		
	
	}
	
	//내부에서만 필요한 메서드라서 private 으로 만든다.
	private  static void connectDB() throws Exception {
		DBInformation db=new DBInformation();
		
		
		 Class.forName(db.getDbDriver());
		 conn = db.getConn(); 
		 System.out.println("==== DB 접속이 실행되었습니다. ======");
		 closeConnection(conn);
	}
	
	
	//connection 만 닫기 메서드
	public static void closeConnection(Connection conn) {
		try {
			
			if(conn != null) {
				conn.close();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void closeConnection(Connection conn,PreparedStatement pstmt ) {
		try {
			
			if(conn != null) {
				conn.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void closeConnection(PreparedStatement pstmt , ResultSet res) {
		try {
			
			if(res != null) {
				res.close();
			}
			
			if(pstmt != null) {
				pstmt.close();
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void closeConnection(Connection conn, PreparedStatement pstmt , ResultSet res) {
		try {
			
			if(res != null) {
				res.close();
			}
			
			if(pstmt != null) {
				pstmt.close();
			}
			
			if(conn != null) {
				conn.close();
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

