package MemberManagement;

import java.util.Scanner;

//�޴� ������ Ŭ����
//����ڰ� ������ �޴� ����� �����ϴ� 
public class Menu {
	Service service = new Service();
	
	void run(Scanner sc) {
		boolean flag = true;
		int menu;
		while(flag) {
			System.out.println("1.ȸ������ 2.�α��� 3.��й�ȣ ���� 4.����");
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
