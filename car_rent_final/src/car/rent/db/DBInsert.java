package car.rent.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import car.rent.contrl.ManageClassControl;
    
public class DBInsert {   //db Insert 할수 있는 메소드 
	private DBInformation db=new DBInformation();
	private DBConnection dbconn;
	Connection conn=null;
    PreparedStatement ps=null;
    ResultSet res=null;
    ManageClassControl ctrl=new ManageClassControl();
	public void DBInsert(String id,String name,int age,String phone,String Licence) throws SQLException { //1.고객 정보 새로 추가 할 메소드 
		conn=db.getConn();
		if(conn!=null) {
		conn.setAutoCommit(false);
       }
		
		StringBuilder sb=new StringBuilder();
		sb.append("select user_id from user_info");
		ps=conn.prepareStatement(sb.toString());
		res=ps.executeQuery();
		if(res!=null) {
			while(res.next()) {
				if (id.equals(res.getString("user_id"))) {
					System.out.println("존재하는 아이디 입니다... 다시 입력해주세요 ");
					ctrl.userAdd();
				}
			}
			}
		sb=new StringBuilder();
		sb.append("insert into user_info(user_id,user_name,user_age,user_phone,user_licence_number)");
		sb.append("values(?,?,?,?,?)");
		
		ps=conn.prepareStatement(sb.toString());
		
		int count=1;
		ps.setString(count++,id);
		ps.setString(count++,name);
		ps.setInt(count++,age);
		ps.setString(count++,phone);
		ps.setString(count++,Licence);
		
		int n=ps.executeUpdate();
		if(n>0) {
			conn.commit();
			System.out.println("등록 완료!");
			
		}else {
			conn.rollback();
			System.out.println("등록 실패!");
		}
		DBConnection.closeConnection(conn,ps);
		end();
		
	}
	public void end() {  // 메인메소드로 이동 
		ctrl.printMain();
	}
}
