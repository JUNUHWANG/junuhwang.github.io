package MemberManagement;

public class Member {

	String id;
	String pwd;
	String name;
	String email;
	
	// 입력 메서드
	Member(String id, String pwd, String name, String email){
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
	}
	
	// 출력 메소드
	void printData() {
		System.out.println(id);
		System.out.println(pwd);
		System.out.println(name);
		System.out.println(email);
	}
	
}
