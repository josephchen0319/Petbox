package com.member.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.member.security.HashPassword;

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
		String algorithm = "SHA-256";
		if (memberVO != null) {
			if (password != null && password.length() != 0) {
				HashPassword hp = new HashPassword();
				String hashedPassword = hp.generateHash(password, algorithm);
				String pass = memberVO.getPassword();
				if (hashedPassword.equals(pass)) {
					return memberVO;
				}
			}
		}
		return null;
	}

	public MemberVO signUp(String name, String email, String password, String phone_num, String address, String sex,
			String birthday) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN);
		Date bd_format;
		MemberVO memberVO = new MemberVO();
		java.sql.Date bday = null;
		String algorithm = "SHA-256";

		try {
			bd_format = sdf.parse(birthday);
			bday = new java.sql.Date(bd_format.getTime());
		} catch (Exception e) {
			bday = null;
		}

		HashPassword hp = new HashPassword();
		String hashedPassword = hp.generateHash(password, algorithm);
		List<MemberVO> members = dao.getAll();
		for (MemberVO mv : members) {
			if (email.equals(mv.getEmail())) {
				return null;
			}
			;
		}

		memberVO.setName(name);
		memberVO.setEmail(email);
		memberVO.setAddress(address);
		memberVO.setPhone_num(phone_num);
		memberVO.setSex(sex);
		memberVO.setBirthday(bday);
		if (hashedPassword == null) {
			memberVO.setPassword(password);
		} else {
			memberVO.setPassword(hashedPassword);
		}
		memberVO.setMember_state(0);
		dao.insert(memberVO);

		return memberVO;
	}

	public MemberVO updatePersonalInfo(MemberVO memberVO) {

		return memberVO;
	}

	public MemberVO submitApplication(MemberVO memberVO) {
		return memberVO;
	}

	public boolean forgotPassword(String email) {

		return true;
	}

}
