package member.vo;

import lombok.Data;

/**
 * 
 * @author 서재진
 * 
 */

@Data
public class Member {

	private String email;
	private String pw;
	private String name;
	private String address;
	private String tel;
	private int rating; // 0 : 일반회원 // 1 : 기업회원 // 2 : 관리자 (admin@b.com)
    private String joindate;
    private boolean auth;

}
