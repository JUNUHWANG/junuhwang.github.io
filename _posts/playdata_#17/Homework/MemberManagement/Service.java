package MemberManagement;

import java.util.Scanner;

//회원 가입 id pwd name email 받아서 객체에 받으
//회원정보 출력
//로그인 성공 실패 여부 출력
public class Service {
	Dao dao = new Dao(); // 데이터 처리 객체
	
	// 추가기능
	void addMember(Scanner sc) {
		System.out.println("===회원가입===");
		System.out.print("ID:");
		String id = sc.next();

		System.out.print("비밀번호:");
		String pwd = sc.next();
		
		System.out.print("이름:");
		String name = sc.next();
		
		System.out.print("email:");
		String email = sc.next();

		
		// 인폼을 p 객체에 저장 // 저장된 항목을 dao 내 배열에 정보 저장
		// 클래스 불러올 경우, 클래스 내 저장된 모든 변수 불러옴
		Member m = new Member(id, pwd, name, email);
		
		dao.insert(m);
		
		}
	
	void login(Scanner sc) {
		System.out.println("===로그인===");
		System.out.print("ID:");
		String id = sc.next();

		System.out.print("비밀번호:");
		String pwd = sc.next();
		
		boolean flag = dao.login(id, pwd);
		
		if (flag == true) {
			System.out.println("로그인 성공");
		} else{
			System.out.println("로그인 실패");
		}
		
		}
	
	void editID(Scanner sc) {
		System.out.println("===변경할 ID를 입력해주세요===");
		System.out.print("ID:");
		String id = sc.next();
		
		int idx = dao.IDidx(id);
		
		if (idx>=0) {
			Member m = dao.findID(idx);
			System.out.println("===변경할 비밀번호를 입력해주세요===");
			System.out.print("비밀번호:");
			String pwd = sc.next();
			m.pwd = pwd;
			System.out.println("===변경이 완료되었습니다===");
			System.out.println("ID: " + m.id);
			System.out.println("변경된 비밀번호: " + m.pwd);
			System.out.println("이름: " + m.name);
			System.out.println("Email: " + m.email);
			
		} else {
			System.out.println("===해당 ID는 존재하지 않습니다===");
		}}}