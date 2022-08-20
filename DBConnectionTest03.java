package dbConnectiontest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DBConnectionTest03 {

	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
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
		
		try {
			Class.forName(dbDriver);
			conn=DriverManager.getConnection(url,id,passwd);
			if(conn!=null) {
				// 자동으로 데이터 베이스에 적용 금지 
				conn.setAutoCommit(false);
				System.out.println("DB 연결 성공");
			}else {
				System.out.println("DB 연결 실패");
			}
			
			StringBuilder sb=new StringBuilder();
			sb.append("insert into test(std_num,std_name,std_gender,std_level)");
			sb.append("values(?,?,?,?)");
			System.out.print("번호>>");
			int number=scan.nextInt();
			System.out.print("이름>>");
			String name=scan.next();
			System.out.print("성별>>");
			String gender=scan.next();
			System.out.print("학년>>");
			int level=scan.nextInt();
			
			ptsmt=conn.prepareStatement(sb.toString());
			int val=1;
			
			ptsmt.setInt(val++,number);
			ptsmt.setString(val++,name);
			ptsmt.setString(val++,gender);
			ptsmt.setInt(val++,level);
			
			int n=ptsmt.executeUpdate();
			if(n>0) {
				conn.commit();
				System.out.println("학생 정보 저장되었습니다.");
			}
			else {
				conn.rollback();
				System.out.println("학정 정보 저장 실패했습니다.");
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
		}catch (Exception e) {
			// TODO: handle exception
		}
		}
		    
		
		
	}
}
