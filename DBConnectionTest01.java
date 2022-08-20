package dbConnectiontest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionTest01 {

	public static void main(String[] args) {
		
		/*
		 * driver - 드라이버라는 이름을 가진 클래스 파일이 필요 from mariadb lib
		 * url - 데이터베이스 접근주소 jdbc: 데이터베이스서비스명://접근IP:포트번호/스키마(데이터베이스)명
		 * id - 데이터베이스 사용자id
		 * password - 데이터베이스 접근 비밀번호 
		 */
		String dbDriver ="org.mariadb.jdbc.Driver";
		String url="jdbc:mariadb://localhost:(1)/(2)"; //1.포트번호, 2.테이블명
		String id="(1)"; // 1.유저 id
		String passwd="(1)"; //1. 데이터베이스 접속하기위한 비밀번호
		
		Connection conn=null;
		
		try {
			//데이터 베이스와 연결할 드라이버 클래스를 찾아서 로드하는 역할
			Class.forName(dbDriver);
			
			conn=DriverManager.getConnection(url,id,passwd);
			
			if(conn!=null) {
				System.out.println("DB 연결 성공");
			}else {
				System.out.println("DB 연결 실패");
			}
			
		}catch (ClassNotFoundException e) {
			 e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn!=null) {
					conn.close();
				}
				
			}catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
	}
}
