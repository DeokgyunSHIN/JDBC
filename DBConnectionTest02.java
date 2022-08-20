package dbConnectiontest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnectionTest02 {
	 
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
		PreparedStatement ptsmt=null;
		ResultSet res=null;
		
		try {
			Class.forName(dbDriver);
			conn=DriverManager.getConnection(url,id,passwd);
			
			if(conn!=null) {
				System.out.println("DB 연결 성공");
			}else {
				 System.out.println("DB 연결 실패");
			}
			
			String sql="select *from test";
			ptsmt=conn.prepareStatement(sql);
			res=ptsmt.executeQuery();
			
			if(res!=null) {
				while(res.next()) {
					System.out.print(res.getInt("std_num")+" ");
					System.out.print(res.getString("std_name")+" ");
					System.out.print(res.getString("std_gender")+" ");
					System.out.print(res.getInt("std_level")+" ");
					System.out.println();
				}
			}else {
				System.out.println("DB에 데이터가 없습니다.");
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
				if(ptsmt!=null) {
					ptsmt.close();
				}
				if(res!=null) {
					res.close();
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
