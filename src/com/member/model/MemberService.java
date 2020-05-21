package com.member.model;

import java.util.List;

import com.member.security.HashPassword;

public class MemberService {
	private MemberDAO_interface dao;

	public MemberService() {
		dao = new MemberDAO();
	}

	public MemberVO getMemberByEmail(String email) {
		return dao.findByEmail(email);
	}
	
	private static String hashPassword(String password) {
		String algorithm = "SHA-256";
		HashPassword hp = new HashPassword();
		String hashedPassword = hp.generateHash(password, algorithm);
		return hashedPassword;
	}

//	public boolean login(String email, String password) {
//		MemberVO memberVO = null;
//		if ((memberVO = dao.findByEmail(email)) != null) {
//			String hashedPassword = hashPassword(password);
//			String pass = memberVO.getPassword();
//			if (hashedPassword.equals(pass)) {
//				memberVO.setPassword(pass);
//				
//				return true;
//			}
//		}
//		return false;
//	}
	
	public MemberVO login(String email, String password) {
		MemberVO memberVO = null;
		if ((memberVO = dao.findByEmail(email)) != null) {
			String hashedPassword = hashPassword(password);
			String pass = memberVO.getPassword();
			if (hashedPassword.equals(pass)) {
				memberVO.setPassword(pass);
				return memberVO;
			}
		}
		return null;
	}


	public MemberVO signUp(MemberVO memberVO) {

		List<MemberVO> members = dao.getAll();
		for (MemberVO mv : members) {
			if (memberVO.getEmail().equals(mv.getEmail())) {
				memberVO.setEmail("");
				return memberVO;
			};
		}
		
		String hashedPassword = hashPassword(memberVO.getPassword());
		memberVO.setPassword(hashedPassword);			
		memberVO.setMember_state(0);
		dao.insert(memberVO);
		return memberVO;
	}
	
	public MemberVO updateInfo(MemberVO memberVO, String new_password) {
		String input_password = memberVO.getPassword();
		String old_password = hashPassword(input_password);
		String stored_password = dao.findByEmail(memberVO.getEmail()).getPassword();
		
		if (old_password.equals(stored_password)) {
			memberVO.setPassword(hashPassword(new_password));
			dao.update(memberVO);
		} else if (input_password.trim().length() == 0 || input_password == null) {
			memberVO.setPassword(stored_password);
			dao.update(memberVO);
		} else if (!old_password.equals(stored_password) && input_password.trim().length() != 0) {
			memberVO = dao.findByEmail(memberVO.getEmail());
			memberVO.setPassword("");
		}
		return memberVO;
	}
	
	public void updateState(MemberVO memberVO) {
		
	}
	
	public List<MemberVO> getAllMembers() {
		List<MemberVO> members = dao.getAll();
		return members;
	}

	public MemberVO submitApplication(MemberVO memberVO) {
		return memberVO;
	}

	public boolean forgotPassword(String email) {

		return true;
	}

}
