package MemberManagement;

import java.util.Scanner;

//ȸ�� ���� id pwd name email �޾Ƽ� ��ü�� ����
//ȸ������ ���
//�α��� ���� ���� ���� ���
public class Service {
	Dao dao = new Dao(); // ������ ó�� ��ü
	
	// �߰����
	void addMember(Scanner sc) {
		System.out.println("===ȸ������===");
		System.out.print("ID:");
		String id = sc.next();

		System.out.print("��й�ȣ:");
		String pwd = sc.next();
		
		System.out.print("�̸�:");
		String name = sc.next();
		
		System.out.print("email:");
		String email = sc.next();

		
		// ������ p ��ü�� ���� // ����� �׸��� dao �� �迭�� ���� ����
		// Ŭ���� �ҷ��� ���, Ŭ���� �� ����� ��� ���� �ҷ���
		Member m = new Member(id, pwd, name, email);
		
		dao.insert(m);
		
		}
	
	void login(Scanner sc) {
		System.out.println("===�α���===");
		System.out.print("ID:");
		String id = sc.next();

		System.out.print("��й�ȣ:");
		String pwd = sc.next();
		
		boolean flag = dao.login(id, pwd);
		
		if (flag == true) {
			System.out.println("�α��� ����");
		} else{
			System.out.println("�α��� ����");
		}
		
		}
	
	void editID(Scanner sc) {
		System.out.println("===������ ID�� �Է����ּ���===");
		System.out.print("ID:");
		String id = sc.next();
		
		int idx = dao.IDidx(id);
		
		if (idx>=0) {
			Member m = dao.findID(idx);
			System.out.println("===������ ��й�ȣ�� �Է����ּ���===");
			System.out.print("��й�ȣ:");
			String pwd = sc.next();
			m.pwd = pwd;
			System.out.println("===������ �Ϸ�Ǿ����ϴ�===");
			System.out.println("ID: " + m.id);
			System.out.println("����� ��й�ȣ: " + m.pwd);
			System.out.println("�̸�: " + m.name);
			System.out.println("Email: " + m.email);
			
		} else {
			System.out.println("===�ش� ID�� �������� �ʽ��ϴ�===");
		}}}