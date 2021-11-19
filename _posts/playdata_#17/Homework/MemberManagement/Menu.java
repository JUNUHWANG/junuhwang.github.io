package MemberManagement;

import java.util.Scanner;

//메뉴 돌리는 클래스
//사용자가 선택한 메뉴 기능을 수행하는 
public class Menu {
	Service service = new Service();
	
	void run(Scanner sc) {
		boolean flag = true;
		int menu;
		while(flag) {
			System.out.println("1.회원가입 2.로그인 3.비밀번호 수정 4.종료");
			menu = sc.nextInt();
			switch(menu) {
			case 1:
				service.addMember(sc);
				break;
			case 2:
				service.login(sc);
				break;
			case 3:
				service.editID(sc);
				break;
			case 4:
				flag = false;
				break;
			}
		}
	}
}
