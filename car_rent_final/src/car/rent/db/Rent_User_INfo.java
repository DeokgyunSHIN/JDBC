package car.rent.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import car.rent.contrl.ManageClassControl;

public class Rent_User_INfo {  //db table rent_user_info에 대한 쿼리문들 이용하는(곳) 메소드

	private DBInformation db;
	private Connection conn;
	private ResultSet res;
	private PreparedStatement ps;
	private ManageClassControl ctrl;
	public void rent_suer_info(int time,int car_number,String ID,int car_price) throws SQLException {
		db=new DBInformation();
		conn=db.getConn();
		String car_num=Integer.toString(car_number);
		conn.setAutoCommit(false);
		StringBuilder sb=new StringBuilder();
		sb.append("insert into rent_user_info(rent_user_id,rent_car_id,rent_days,rent_price,rent_status)");
		sb.append("values (?,?,?,?,?)");   // 차량을 빌리니 rent_user_info 테이블에 데이터를 저장해준다.
		ps=conn.prepareStatement(sb.toString());
		int count=1; // db 에 넣을 순서
		ps.setString(count++,ID);   // 유저 아이디
		ps.setString(count++,car_num); // 차량번호
		ps.setInt(count++,time);   //빌릴 시간
		ps.setInt(count++,(time*car_price)); // 가격
		ps.setString(count++,"O");  // 빌리니깐 O
		
		int n=ps.executeUpdate();
		if(n>0) {
			conn.commit();
			String sql="update car_info set car_count=car_count-1 where car_id=?"; // 차량을 빌리니깐 차량 대수에 -1
			ps=conn.prepareStatement(sql);
			ps.setInt(1,car_number);
			 int r = ps.executeUpdate();
			 if(r>0) {
				 conn.commit();
			 }else {
				 conn.rollback();
				 System.out.println("에러가 발생했습니다. 메인페이지로 넘어갑니다...");
				 ctrl.printMain();
			 }
			
			String sql1="update car_info set rent_count=rent_count+1 where car_id=?";  // 차량을 빌리니깐 렌트대수에 +1
			ps=conn.prepareStatement(sql1);
			ps.setInt(1,car_number);
			 int s = ps.executeUpdate();
			 if(s>0) {
				 conn.commit();
			 }else {
				 conn.rollback();
				 System.out.println("에러");
			 }
			 DBConnection.closeConnection(conn,ps,res);   // 다썻으니 종료
		}
		
	
		
		
		
	}
		
	public void rent_return(String ID) throws SQLException {  // 4. 차량 반납 db에 데이터 update 작업할수 있는 메소드
		ctrl=new ManageClassControl();
		db=new DBInformation();
		conn=db.getConn();
		if(conn!=null) {
		conn.setAutoCommit(false);
		}
		int price=0;
		int count=0;
		StringBuilder sql =new StringBuilder();
		sql.append("select ui2.user_name ,ci2.car_name ,ci2.car_fuel,rui2.rent_days ,rui2.rent_price,ci2.car_id from rent_user_info rui2 left join car_info ci2 on rui2.rent_car_id =ci2.car_id ");
		sql.append("left join user_info ui2 on rui2.rent_user_id = ui2.user_id where ui2.user_id =? and rui2.rent_status ='O'"); //반납하기전 정보 출력하기 위한 쿼리
		ps=conn.prepareStatement(sql.toString());
		ps.setString(1,ID);   // 유저 아이디 쿼리 '? 에 넣기
		String car_number = null;
		res=ps.executeQuery();
		if(res!=null) {
			while(res.next()) {
				count++;    // 데이터가 있으면 count++;
				System.out.println("===============================고객님 리스트===========================");
				System.out.print("이름: " +res.getString("user_name")+",  ");
				System.out.print("차 종류(자종): " +res.getString("car_name")+",  ");
				System.out.print("차 연료: " +res.getString("car_fuel")+",  ");
				System.out.print("빌린 시간 : " +res.getString("rent_days")+"시간  ");
			    System.out.println();
				System.out.println("===================================================================");
				price=res.getInt("rent_price");  //가격을 알려주기 위해서 price 변수에 넣음
				car_number=res.getString("car_id"); // 차량의 번호를 밑에 사용해야하기때문에 car_number변수에 넣음
		  }
			}else {
				System.out.println("시스템오류 !!");
				ctrl.printMain();
		}
			if(count>0) {  // while문을 이용한적이 있으면 빌린적이 있음.
		 System.out.println("정보 확인되었습니다.");
		 System.out.println("금액은: "+price +"입니다. 감사합니다. 또이용해주세요 ..(3초 뒤 메인페이지도 이동)");
		  }else if(count==0) {  // while문을 들어갔다가 온적이 없으면 빌린적이 없는 유저이기 떄문에 안내해줌.
				System.out.println("차량을 빌린 데이터가 없습니다...");
				ctrl.printMain();
			}
			
		 String sql3="update rent_user_info set rent_status='x' where rent_user_id=?";  // 반납을 하였으므로 대여현황을 O에서 x로 바꿔준다.
		 conn.setAutoCommit(false);
			ps=conn.prepareStatement(sql3);
			ps.setString(1,ID);

			int a1=ps.executeUpdate();
			if(a1>0) {
				conn.commit();
				String sql2="update car_info set car_count=car_count+1 where car_id=?";  //반납하였으니 자동차 대수에 +1
				//conn.setAutoCommit(false);
				ps=conn.prepareStatement(sql2);
				ps.setString(1,car_number);
				
				int a=ps.executeUpdate();
				if(a>0){
					conn.commit();
				}else {
					conn.rollback();
					System.out.println("시스템 에러!!! 메인페이지로 넘어갑니다..");
				}
				
				String sql1="update car_info set rent_count=rent_count-1 where car_id=?";  // 반납하였으니 렌트대수에 -1
				//conn.setAutoCommit(false);
				ps=conn.prepareStatement(sql1);
				ps.setString(1, car_number);
				
				int b=ps.executeUpdate();
				if(b>0){
					conn.commit();
				}else {
					conn.rollback();
					System.out.println("시스템 에러!! 메인페이지로 넘어갑니다..");
				}
			}else {
				conn.rollback();
				System.out.println("시스템!!! 에러");
			}
			
			
			
		try {
			DBConnection.closeConnection(conn,ps,res);
		Thread.sleep(3000);
		ctrl.printMain();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {	
		
	}
	}
	
	
     public void rent_Car_info(int menu) throws SQLException {    //5. 현재 렌트 현황 
    	 db=new DBInformation();
 		conn=db.getConn();
 		ctrl=new ManageClassControl();	
 		StringBuilder sql=new StringBuilder();
 		
 		if (menu==1) {   // 1. 전체 렌트 현황이라면 
 		sql.append("select ui2.user_id,ui2.user_name ,ci2.car_name ,ci2.car_fuel,rui2.rent_days,rui2.rent_status from rent_user_info rui2 left join car_info ci2 on rui2.rent_car_id =ci2.car_id");
 		sql.append(" left join user_info ui2 on rui2.rent_user_id = ui2.user_id ");  // 대여 현황의 데이터를 가져오기위한 쿼리
 		ps=conn.prepareStatement(sql.toString());
 		res=ps.executeQuery();
 		
 		if(res!=null) {
 			while (res.next()) {
 				System.out.println("============= 대여 현황 ===============");
 				System.out.print("아이디: "+res.getString("user_id")+" " );
 				System.out.print("이름: "+res.getString("user_name")+" " );
 				System.out.print("차종: "+res.getString("car_name")+" " );
 				System.out.print("연료: "+res.getString("car_fuel")+" " );
 				System.out.print("대여시간: "+res.getString("rent_days")+" " );
 				System.out.print("대여현황: "+res.getString("rent_status")+" " );
 				System.out.println();
 				System.out.println("======================================");
 				System.out.println();
 				
 			}
 		}else {
 			System.out.println("시스템 에러 !");
 		}
 		
 		
 		}else if(menu==2) {  //2. 대여완료 현황이라면 
 			sql.append("select ui2.user_id,ui2.user_name ,ci2.car_name ,ci2.car_fuel,rui2.rent_days,rui2.rent_status from rent_user_info rui2 left join car_info ci2 on rui2.rent_car_id =ci2.car_id");
 	 		sql.append(" left join user_info ui2 on rui2.rent_user_id = ui2.user_id where rui2.rent_status ='x'");
 			ps=conn.prepareStatement(sql.toString());
 			res=ps.executeQuery();
 			if(res!=null) {
 				while(res.next()) {
 					System.out.println("============= 대여 현황 ===============");
 	 				System.out.print("아이디: "+res.getString("user_id")+" " );
 	 				System.out.print("이름: "+res.getString("user_name")+" " );
 	 				System.out.print("차종: "+res.getString("car_name")+" " );
 	 				System.out.print("연료: "+res.getString("car_fuel")+" " );
 	 				System.out.print("대여시간: "+res.getString("rent_days")+" " );
 	 				System.out.print("대여현황: "+res.getString("rent_status")+" " );
 	 				System.out.println();
 	 				System.out.println("======================================");
 	 				System.out.println();
 				}
 			}else {
 				System.out.println("시스템 에러 !");
 			}
 		}else if (menu==3) {  // 3. 대여중 현황이라면 
 			sql.append("select ui2.user_id,ui2.user_name ,ci2.car_name ,ci2.car_fuel,rui2.rent_days,rui2.rent_status from rent_user_info rui2 left join car_info ci2 on rui2.rent_car_id =ci2.car_id");
 	 		sql.append(" left join user_info ui2 on rui2.rent_user_id = ui2.user_id where rui2.rent_status ='O'");
 			ps=conn.prepareStatement(sql.toString());
 			res=ps.executeQuery();
 			if(res!=null) {
 				while(res.next()) {
 					System.out.println("============= 대여 현황 ===============");
 	 				System.out.print("아이디: "+res.getString("user_id")+" " );
 	 				System.out.print("이름: "+res.getString("user_name")+" " );
 	 				System.out.print("차종: "+res.getString("car_name")+" " );
 	 				System.out.print("연료: "+res.getString("car_fuel")+" " );
 	 				System.out.print("대여시간: "+res.getString("rent_days")+" " );
 	 				System.out.print("대여현황: "+res.getString("rent_status")+" " );
 	 				System.out.println();
 	 				System.out.println("======================================");
 	 				System.out.println();
 				}
 		}
		
	}else if (menu==4) { // 4. 메인페이라면  
		try {
 			System.out.println("5초 후 메인페이지로 돌아갑니다..");
 			Thread.sleep(5000);// 5초 동안 기다린다.
 			ctrl.printMain();
 		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}else {
		System.out.println("번호를 잘못 입력하셨습니다. 다시입력해주세요");
	}
 		
 		ctrl.rent_user_infomation();
	
	
     }
}

