package car.rent.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import car.rent.contrl.ManageClassControl;

public class DBSelect {  // db를 select 하는 메소드 

	Connection conn=null;
	DBInformation db;
	ResultSet res=null;
	PreparedStatement ps=null;
	ManageClassControl ctrl=null;
	
	public void userlist() throws SQLException {  // 1. 고객 정보 출력 메소드 
		ctrl=new ManageClassControl();
		db=new DBInformation();
		conn=db.getConn();
		String sql ="select *from user_info";
		
		ps=conn.prepareStatement(sql);
		res=ps.executeQuery();
		
		if(res!=null) {
			while(res.next()) {
				System.out.println("===============================고객님 리스트===========================");
				System.out.print("   아이디: "+res.getString("user_id")+"  ");	
				System.out.print("이름: " +res.getString("user_name")+"  ");
				System.out.print("나이: "+res.getInt("user_age")+"  ");
				System.out.print("전화번호: "+res.getString("user_phone")+"  ");
				System.out.print("라이선스 번호: "+res.getString("user_licence_number")+" ");
			    System.out.println();
				System.out.println("===================================================================");
			}
		}else {
			System.out.println("고객 정보 없음");
		}
		DBConnection.closeConnection(conn,ps,res);
		ctrl.mainDisplay();  // 메인 페이지 
	}
}
