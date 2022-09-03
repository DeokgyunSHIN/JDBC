package car.rent.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import car.rent.contrl.ManageClassControl;

public class DBLook {        // 차를 빌리기 전 user_id 가 db 에 있는지 확인하고 출력하는 메소드 
	private Connection conn=null;
	private PreparedStatement ps=null;
	private ResultSet res=null;
	private ManageClassControl ctrl;
	Scanner scan=new Scanner(System.in);
	DBInformation db;
	
	
	public void suerinformation(String ID) throws SQLException {  // 차량 빌리기전 고객정보 확인
		db=new DBInformation();
		ctrl=new ManageClassControl();
		
		conn=db.getConn();
		String sql="select * from user_info";
			
		ps=conn.prepareStatement(sql);
		
		int count=0;
		res=ps.executeQuery();
	
		if(res!=null) {
		while(res.next()) {
			
			if (res.getString("user_id").equals(ID)) {
				count++;
			System.out.println("===============================고객님 리스트===========================");
			System.out.print("   아이디: "+res.getString("user_id")+"  ");	
			System.out.print("이름: " +res.getString("user_name")+"  ");
			System.out.print("나이: "+res.getInt("user_age")+"  ");
			System.out.print("전화번호: "+res.getString("user_phone")+"  ");
			System.out.print("라이선스 번호: "+res.getString("user_licence_number")+" ");
		    System.out.println();
			System.out.println("===================================================================");
		}
		}
		}else {
			System.out.println("없는 ID 입니다.");
		}
		if(count==0) {
			System.out.println("없는 ID 입니다.");
			System.out.println("========== menu========");
			System.out.println(" 1.등록       2. 메인페이지");
			int menu=scan.nextInt();
			if(menu==1) {
			System.out.println("등록 페이지로 이동합니다.");
			ctrl.userAdd();
		}else {
			System.out.println("메인페이지로 이동합니다. ");
		   ctrl.printMain();	
		}
		}
	}
	public void userinformation(String id) throws SQLException { // 고객 검색
		db=new DBInformation();
		ctrl=new ManageClassControl();
		
		conn=db.getConn();
		String sql="select * from user_info";
			
		ps=conn.prepareStatement(sql);
		
		int count=0;
		res=ps.executeQuery();
	
		if(res!=null) {
		while(res.next()) {
			
			if (res.getString("user_id").equals(id)) {
				count++;
			System.out.println("===============================고객님 리스트===========================");
			System.out.print("   아이디: "+res.getString("user_id")+"  ");	
			System.out.print("이름: " +res.getString("user_name")+"  ");
			System.out.print("나이: "+res.getInt("user_age")+"  ");
			System.out.print("전화번호: "+res.getString("user_phone")+"  ");
			System.out.print("라이선스 번호: "+res.getString("user_licence_number")+" ");
		    System.out.println();
			System.out.println("===================================================================");
		}
		}
		}else {
			System.out.println("없는 id 입니다.");
		}
		if(count==0) {
			System.out.println("없는 ID 입니다.");
			System.out.print("회원 등록 하시겠습니까(1.등록 2.메인페이지)>> ");
			int menu=scan.nextInt();
			if(menu==1) {
			System.out.println("등록 페이지로 이동합니다.");
			ctrl.userAdd();
		}else {
			System.out.println("메인 페이지로 이동합니다.");
			ctrl.printMain();
		}
			
		}
		System.out.println("1.고객 정보 수정  2.고객정보 삭제  3.메인페이로가기");
		int a=scan.nextInt();
		switch (a) {
		case 1: 
			userchange(id);
			break;
		case 2:
			userdelect(id);
			break;
		case 3:
			Return();
			DBConnection.closeConnection(conn,ps,res);
			break;
		}
	}
	public void Return() {
		ctrl.printMain();
	}
	
   public void userchange(String ID) throws SQLException  {     //4. 고객정보수정
	   db=new DBInformation();
		ctrl=new ManageClassControl();
		
		conn=db.getConn();
		//String sql="uptade user_info set user_name =? where user_id= ? ";
	   for (;;) {
		   System.out.print("1.이름 2.나이 3.전화번호 4.메인화면 중에 어느것을 변경하시겠습니까?");
		   int menu=scan.nextInt();
		   if(menu==1) {
			   System.out.print("변경할 이름을 적어주세요>>");
			   String name=scan.next();
				String sql="update user_info set user_name =? where user_id= ? ";
				ps=conn.prepareStatement(sql);
				ps.setString(1,name);
				ps.setString(2, ID);
				res=ps.executeQuery();
				System.out.println("변경되었습니다.");
		   }else if(menu==2) {
			   System.out.print("변경할 나이 적어주세요>>");
			   String age=scan.next();
				String sql="update user_info set user_age =? where user_id= ? ";
				ps=conn.prepareStatement(sql);
				ps.setString(1,age);
				ps.setString(2, ID);
				res=ps.executeQuery();
				System.out.println("변경되었습니다.");
		   }else if (menu==3){
			   System.out.print("변경할 전화번호 적어주세요>>");
			   String phone=scan.next();
				String sql="update user_info set user_phone =? where user_id= ? ";
				ps=conn.prepareStatement(sql);
				ps.setString(1,phone);
				ps.setString(2, ID);
				res=ps.executeQuery();
				System.out.println("변경되었습니다.");
		   }else {
			   System.out.println("메인페이지로 갑니다..");
			   DBConnection.closeConnection(conn,ps,res);
			   Return();
		   }
		   
	   }
	   
   }
public void userdelect(String ID) throws SQLException {  // 5. 고객정보삭제
	db=new DBInformation();
	ctrl=new ManageClassControl();
	
	conn=db.getConn();
	String sql="delete from user_info where user_id= ?";
	
		
	ps=conn.prepareStatement(sql);
	ps.setString(1, ID);

	res=ps.executeQuery();
	System.out.println("삭제되었습니다.");
	DBConnection.closeConnection(conn,ps,res);
	Return();
   }
}
