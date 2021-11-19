package MemberManagement;

public class Dao {
	Member[] members = new Member[30];
	int cnt;
	
	//�ּ� �ϳ� �߰�
	void insert(Member m) {
		if (cnt == members.length) {
			System.out.println("�߰� �Ұ�");
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
//					System.out.println("�α��� ����");
				} else {
					flag = false;
					return flag;
//					System.out.println("��й�ȣ�� Ʋ�Ƚ��ϴ�.");
				}
			} else {
				flag = false;
				return flag;
//				System.out.println("�������� �ʴ� ID �Դϴ�.");

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
	

