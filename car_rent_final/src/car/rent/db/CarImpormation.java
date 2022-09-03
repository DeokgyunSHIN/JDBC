package car.rent.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import car.rent.contrl.ManageClassControl;

public class CarImpormation {      //2. 차량 정보
    private DBInformation db; 
    private PreparedStatement ps;
    private ResultSet res;
    private Connection conn;
    private ManageClassControl ctrl;
    private DBLook look;
    private Rent_User_INfo info;

     Scanner scan=new Scanner(System.in);
     
     public void CarImpromation() throws SQLException { // 2.차량 정보 쿼리문을 통해서 출력하는 메소드
    	 db=new DBInformation();
    	 conn=db.getConn();
    	 ctrl=new ManageClassControl();
    	 String sql="select *from car_info";
    	 
    	 ps=conn.prepareStatement(sql);
    	 res=ps.executeQuery();
    	 
    	 if(res!=null) {
    		  while(res.next()) {
    			  System.out.println("=============================================================차량 정보 =================="
    			  		+ "====================================");
    			  System.out.print("자동차 번호 : "+res.getString("car_id")+",  ");
    			  System.out.print("자동차 회사 : "+res.getString("car_company")+",  ");
    			  System.out.print("자동차 이름 : "+res.getString("car_name")+",  ");
    			  System.out.print("자동차 연식 : "+res.getString("car_year")+",  ");
    			  System.out.print("자동차 연료 : "+res.getString("car_fuel")+", \n");
    			  System.out.print("자동차 수유대수 : "+res.getInt("car_count")+",  ");
    			  System.out.print("자동차 렌트대수 : "+res.getInt("rent_count")+",  ");
    			  System.out.print("자동차 현재 남은 대수 : "+(res.getInt("car_count")+",  "));
    			  System.out.print("한시간 대여 비용 : "+res.getInt("rent_money"));
    			  System.out.println();
    			  System.out.println("============================================"
    			  		+ "==============================================================================");
    		  }
    	 }else {
			  System.out.println("에러 발생!or 자동차 정보 없음");
		  }
    	
    		 ctrl.printMain();  // 메인페이지 이동 
    	 }
    	
       public void carrental() throws SQLException{ //3. 차량 대여하기전 차량종류 및 대수 확인 할수 있는 메소드 
    	   db=new DBInformation();
      	 conn=db.getConn();
      	 ctrl=new ManageClassControl();
      	 String sql="select *from car_info";
      	 
      	 ps=conn.prepareStatement(sql);
      	 res=ps.executeQuery();
  
      	 
      	 if(res!=null) {
      		  while(res.next()) {
      			  System.out.println("===============================================================================차량 정보 =================="
      			  		+ "===========================================================");
      			  System.out.print("자동차 번호 : "+res.getString("car_id")+",  ");
      			  System.out.print("자동차 회사 : "+res.getString("car_company")+",  ");
      			  System.out.print("자동차 이름 : "+res.getString("car_name")+",  ");
      			  System.out.print("자동차 연식 : "+res.getString("car_year")+",  ");
      			  System.out.print("자동차 연료 : "+res.getString("car_fuel")+", \n");
      			  System.out.print("자동차 수유대수 : "+res.getInt("car_count")+",  ");
      			  System.out.print("자동차 렌트대수 : "+res.getInt("rent_count")+",  ");
      			  System.out.print("자동차 현재 남은 대수 : "+(res.getInt("car_count")-res.getInt("rent_count"))+" ");
      			  System.out.print("한시간 대여 비용 : "+res.getInt("rent_money"));
      			  System.out.println();
      			  System.out.println("==============================================================="
      			  		+ "====================================================================================================");
      		  }
      	 }else {
  			  System.out.println("에러 발생!or 자동차 정보 없음");
  		  }
      	
      	 System.out.print("등록된 ID를 입력하세요>>>");
      	 String ID=scan.next();
      	 look=new DBLook();
      	 look.suerinformation(ID);
      	 
     	 
      	 System.out.println("고객님 확인 완료!!");
      	 System.out.print("빌릴 차량 번호를 입력하세요 >>");
      	 int number=scan.nextInt();
      	 String sql1="select *from car_info where car_id=? ";
      	 ps=conn.prepareStatement(sql1);
      	 ps.setInt(1, number);
      	 res=ps.executeQuery();
    	 ps=null;
      	int car_price=0;
      	 if(res!=null){
      	 while(res.next()){
      		System.out.println("===============================================================================차량 정보 =================="
  			  		+ "===========================================================");
  			  System.out.print("자동차 번호 : "+res.getString("car_id")+",  ");
  			  System.out.print("자동차 회사 : "+res.getString("car_company")+",  ");
  			  System.out.print("자동차 이름 : "+res.getString("car_name")+",  ");
  			  System.out.print("자동차 연식 : "+res.getString("car_year")+",  ");
  			  System.out.print("자동차 연료 : "+res.getString("car_fuel")+", ");
  			  System.out.print("자동차 수유대수 : "+res.getInt("car_count")+",  ");
  			  System.out.print("자동차 렌트대수 : "+res.getInt("rent_count")+",  ");
  			  System.out.print("자동차 현재 남은 대수 : "+(res.getInt("car_count")-res.getInt("rent_count"))+" ");
  			  System.out.print("한시간 대여 비용 : "+res.getInt("rent_money"));
  			  System.out.println();
  			  System.out.println("==============================================================="
  			  		+ "====================================================================================================");
      	        car_price=res.getInt("rent_money");
      	 }
      	 }else {
      		 System.out.println("시스템 에러!!! 메인페이지로 넘어갑니다..");
      		 ctrl.printMain();
      	 }
      
      	info=new Rent_User_INfo();    //생성 
      	 System.out.println("빌리시겠습니까(yes/no) >>");
      	 String rental=scan.next();
      	 if(rental.equals("yes")|| rental.equals("YES")) {  //대문자,소문자 둘다 가능 
      		 System.out.print("몇시간을 빌리시나요>>>");
      		 int time=scan.nextInt();
      		DBConnection.closeConnection(conn,ps,res);
      		 try {
      			 System.out.println("잠시만 기다려주세요 ");
      			Thread.sleep(3000); //3초 대기
      			info.rent_suer_info(time, number,ID,car_price);
     		} catch (Exception e) {
     		e.printStackTrace();
     		}finally {
				System.out.println("대여 성공했습니다!!!!");
				
			System.out.println("메인페이지로 갑니다.");
			}
      		 
      	 }else if (rental.equals("no")|| rental.equals("NO")) {
      		 System.out.println("메인페이지로 갑니다..");
      		 
      	 }else {
      		 System.out.println("잘못입력하셨습니다. 메인페이지로 이동합니다!");
      	 }
      	DBConnection.closeConnection(conn,ps,res);  // 닫기 
      	ctrl.printMain();       //메인 페이지 이동 
       }
       }

