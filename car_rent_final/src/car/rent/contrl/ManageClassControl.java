package car.rent.contrl;



import java.sql.SQLException;

import java.util.Scanner;

import car.rent.db.CarImpormation;
import car.rent.db.DBConnection;
import car.rent.db.DBInsert;
import car.rent.db.DBLook;
import car.rent.db.DBSelect;
import car.rent.db.Rent_User_INfo;
import car.rent.utils.CartRentConstants;

/**
 * 메뉴 및 프로그램에서 보여지는 
 * 화면단을 만드는 클래스 
 * @author shin
 *
 */
public class ManageClassControl {

	  private Scanner scan =new Scanner(System.in);
	  private int inputNum;      //번호 입력 변수 
	  private DBInsert dbinsert;      // DBInsert 객체 선언
	  private DBSelect dbselect=new DBSelect();  // DBSelect 객체 생성및 선언
	  private DBLook look;     //BDLook 객체 선언
	  private CarImpormation car;   // CarImpromation 객체 선언
	  private Rent_User_INfo rent_user_info;   // RentUserInfo 객체 선언 
	  
	  
	 
	   //최종본 !!!
	 
	  /**
	   * 렌트카프로그램 시작 메서드
	 * @throws Exception 
	   */
	  public void mainDisplay() {
		
		  printMain();
	  }
	 
	  /**
	   * 메인메뉴 시작 
	   */
	  public void printMain(){		
		  try {
		  System.out.println("\n====================MENU======================");
		  System.out.println("==             1. 고객정보                     ==");
		  System.out.println("==             2. 차량정보                     ==");
		  System.out.println("==             3. 차량대여                     ==");
		  System.out.println("==             4. 차량반납                     ==");
		  System.out.println("==             5. 차량대여현황                  ==");
		  System.out.println("==             6. 종료                        ==");
		  System.out.println("===============================================");
		  System.out.print("원하는 메뉴를 입력하시오   : ");
		
		  inputNum  = scan.nextInt();  // 메뉴 번호 입력
		  printSubMenu(inputNum);  // 번호에 따른 하위 메뉴 실행 
	  }catch (Exception e) {
		e.printStackTrace();
	}
	  }
	 
	  
	  /**
	    * sub  메뉴 depth -1
	 * @throws Exception 
	  */
	  public  void printSubMenu(int menu_num) throws Exception{  //메인 메뉴 메소드
		  car=new CarImpormation();
	
		  rent_user_info=new Rent_User_INfo();
		  if (menu_num!=CartRentConstants.Main_END_PROGRAM) { // 6종료 버튼은 db 연결이 필요없기 떄문
			  
		  DBConnection.getConnection();  // db 연결
		  }
		  switch(menu_num){
		   case CartRentConstants.Main_CUS_INFO:   //1.고객정보
			   printCustomerSubMenu(); 
			   break;
		   case CartRentConstants.Main_CAR_INFO:   //2.차량정보   
			  car.CarImpromation();
			   break; 
		   case CartRentConstants.Main_RENT_CAR:   //3.차량대여  
			   car.carrental();
			   break; // 차량정보
		   case CartRentConstants.Main_Return_CAR:   //4.차량반납  
			   rent_return();
			   break; // 차량정보
		   case CartRentConstants.Main_Show_RENT_STATUS:   //5.대여현황보기  
			   rent_user_infomation();
			   break; // 차량정보
		   case CartRentConstants.Main_END_PROGRAM:     // 6. 프로그램 종료
			   System.out.println("종료합니다..");
			   scan.close();
			   System.exit(0);
			   break;
		   default:
			   errorMessage(menu_num);   // 1~6 번 외에 번호를 눌렀을 시 
			   break;	
		  }
	  }
	  
	  public void rent_user_infomation() throws SQLException {  // 5.대여현황중 보고싶은 종류 고르는 메소드
		  rent_user_info =new Rent_User_INfo();
		  System.out.println("============= 번호를 입력하세요======================");
		  System.out.println("1.전체 렌트 현황 2.대여완료 현황 3. 대여중 현황 4. 메인페이지");
	 		int menu=scan.nextInt();
	 		 rent_user_info.rent_Car_info(menu);
	  }
	  
	  
	  public void rent_return() throws SQLException {   // 차량 반납 메서드
		  rent_user_info=new Rent_User_INfo();
		  System.out.print("등록된 ID를 입력하세요 >>>");
		  String ID=scan.next();
		  rent_user_info.rent_return(ID);
		  
		  
	  }
	   
	  /**
	   * 고객정보 서브메뉴
	 * @throws SQLException 
	   */
	  public void printCustomerSubMenu() throws SQLException{   // 1번의 서브 메인 메소드
			
		  System.out.println("\n=============MENU=============");
		  System.out.println("== 1.고객등록 2.고객목록 3.고객검색(수정및 삭제) 6. 메인메뉴  ==");

		  inputNum  = scan.nextInt();  // 메뉴 번호 입력		 
			
		  switch(inputNum){
			
		  case CartRentConstants.INSERT_CUSTOMER:    // 1.고객 등록
			  userAdd();
			  break; 
		  case CartRentConstants.SHOW_CUS_INFO:   //2. 고객목록
			  dbselect.userlist();
			  break; 
		  case CartRentConstants.SEARCH_CUSTOMER:   //3. 고객 검색
			  userlook();
			  break;
		  case CartRentConstants.MODIFIY_CUSTOMER:   //4. 고객정보수정
			  System.out.println("기존 고객님인지 확인하기 위해서");
			  userlook();
			  break;
		  case CartRentConstants.DELTE_CUSTOMER:   //5. 고객정보삭제 
			  userlook();
			  break;
		  case CartRentConstants.RETURN_MAIN:   // 6. 메인메뉴
			  mainDisplay();
			  break;
		  default: 
			  errorMessage(inputNum);    // 1~6 외에 다른 번호를 입력할시 
			  
		  }		
		
	  }
	  public void userAdd() throws SQLException{     // 고객 등록
		  dbinsert =new DBInsert();
		  System.out.print("등록할 아이디>>");
		  String id=scan.next();
		  System.out.print("등록할 이름>>");
		  String name=scan.next();
		  System.out.print("등록할 나이>>");
		  int age=scan.nextInt();
		  System.out.print("등록할 전화번호>>");
		  String phone=scan.next();
		  System.out.print("등록할 라이선스 번호>>");
		  String licence=scan.next();
		  dbinsert.DBInsert(id,name,age,phone,licence);
		  
	  }
	  public void userlook() throws SQLException {   // 고객 검색 
		  look=new DBLook();
		 System.out.print("ID 를 입력하세요 >>");
		 String id=scan.next();
		 look.userinformation(id);
		
		 
	  }
	  
	  
	  

	////////////에러 메세지 ///////////////////////////////////
	  private  void  errorMessage(int errorMenu){			
		  String errorLine = errorMenu + " 번은 메뉴에 없습니다.다시한번 정확한 입력 바랍니다!";
		  System.out.println();
		  System.out.println(errorLine);
		  System.out.println();
		  mainDisplay();
	  }
	 
}
