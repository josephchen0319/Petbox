package com.member.model;

public class MemberService {
	private MemberDAO_interface dao;

	public MemberService() {
		dao = new MemberDAO();
	}

	public MemberVO getMemberByEmail(String email) {
		return dao.findByEmail(email);
	}

	public MemberVO login(String email, String password) {
		MemberVO memberVO = dao.findByEmail(email);
		if (memberVO != null) {
			if (password != null && password.length() != 0) {
				String pass = memberVO.getPassword();
				if (password.equals(pass)) {
					return memberVO;
				}
			}
		}
		return null;
	}

}
