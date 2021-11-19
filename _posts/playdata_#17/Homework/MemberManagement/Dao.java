package MemberManagement;

public class Dao {
	Member[] members = new Member[30];
	int cnt;
	
	//주소 하나 추가
	void insert(Member m) {
		if (cnt == members.length) {
			System.out.println("추가 불가");
			return;
		}
		members[cnt++] = m;
	}
	
	Member[] selectAll() {
		Member[] tmp = new Member[cnt];
		System.arraycopy(members, 0, tmp, 0, cnt);
		return tmp;
	}
	
	boolean login(String id, String pwd) {
		boolean flag = true;
		for(int i = 0; i < cnt; i++) {
			if (id.equals(members[i].id)) {
				if(pwd.equals(members[i].pwd)) {
//					System.out.println("로그인 성공");
				} else {
					flag = false;
					return flag;
//					System.out.println("비밀번호가 틀렸습니다.");
				}
			} else {
				flag = false;
				return flag;
//				System.out.println("존재하지 않는 ID 입니다.");

			}
		}
		return flag;
	}
	
	int IDidx(String id) {
		for(int i = 0; i < cnt; i++) {
			if (id.equals(members[i].id)) {
				return i;
			}
			}
		return -1;
		}
	
	Member findID(int idx) {
		if (idx >= 0) {
			return members[idx];
		}
		return null;
	}
		
	}
	

