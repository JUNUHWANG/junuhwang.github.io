package MemberManagement;

public class Member {

	String id;
	String pwd;
	String name;
	String email;
	
	// �Է� �޼���
	Member(String id, String pwd, String name, String email){
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
	}
	
	// ��� �޼ҵ�
	void printData() {
		System.out.println(id);
		System.out.println(pwd);
		System.out.println(name);
		System.out.println(email);
	}
	
}
