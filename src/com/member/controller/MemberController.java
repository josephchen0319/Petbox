package com.member.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.member.model.MemberService;
import com.member.model.MemberVO;

@MultipartConfig()
@WebServlet("/member/controller")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService mbSvc;

	public void init() {
		mbSvc = new MemberService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		MemberVO memberVO = null;

		if ("login".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String email = req.getParameter("email");
				String emailReg = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";

//				if (email == null || (email.trim().length()) == 0) {
				
				if("".equals(email)) {
//					errorMsgs.add("請輸入電子郵件");
					errorMsgs.add("Please enter your email");
				} else if (!email.trim().matches(emailReg)) {
//					errorMsgs.add("電子郵件格式有誤");
					errorMsgs.add("Invalid email format");
				}

				String password = req.getParameter("password");
				if ("".equals(password)) {
					errorMsgs.add("Please enter your password");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/login.jsp");
					failureView.forward(req, res);
					return;
				}

//				boolean login = mbSvc.login(email, password);
				memberVO = mbSvc.login(email, password);
				
				if (memberVO == null) {
					errorMsgs.add("Email or Password incorrect");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/login.jsp");
					failureView.forward(req, res);
					return;
				}

//				if (login == false) {
//					errorMsgs.add("Email or Password incorrect");
//					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/login.jsp");
//					failureView.forward(req, res);
//					return;
//				}

				req.getSession().setAttribute("memberVO", memberVO);
//				req.getSession().setAttribute("email", email);
				RequestDispatcher view = req.getRequestDispatcher("/front-end/member/updateInfo.jsp");
				view.forward(req, res);
				return;

			} catch (Exception e) {
				errorMsgs.add("Error occurred");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/updateInfo.jsp");
				failureView.forward(req, res);
				return;
			}
		}

		if ("signup".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String emailReg = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";
				String phoneReg = "^09[0-9]{8}$";
				String nameReg = "^[(\u4e00-\u9fa5)\\s(a-zA-Z)]{1,}$";
				String chineseReg = "^[(\u4e00-\u9fa5)]+";
				String englishReg = "^[(a-zA-Z)]+";
				String addressReg = "^[(\u4e00-\u9fa5) (0-9a-zA-Z).,]+";
//				String bdayReg = "^(18||19||20){2}[0-9]{2}-[0-1]{1}[0-9]{1}-[0-3]{1}[0-9]{1}$";

				String name = req.getParameter("name");
				String email = req.getParameter("email");
				String phone_num = req.getParameter("phone_num");
				String password = req.getParameter("password");
				String confirm = req.getParameter("confirm");
				String address = req.getParameter("address");
				String sex = req.getParameter("sex");
				String birthday = req.getParameter("birthday");

				// use ajax to check valid field in real time
				if ("".equals(name)) { // check if name field is empty
					errorMsgs.add("You must enter a name");
				} else if (!name.trim().matches(nameReg)) {
					errorMsgs.add("Name field does not allow special characters");
					name = "";
				} else if(name.trim().getBytes().length > 20) {
					errorMsgs.add("Chinese name must less than 8 words, english name must less than 20 words");
				}
				
				if ("".equals(email)) { // Check if email field is empty
					errorMsgs.add("You must enter an email address");
				} else if (!email.trim().matches(emailReg)) { // check if email format is incorrect(50 chars)
					errorMsgs.add("Please enter a valid email address");
					email = "";
				} else if (email.trim().matches(emailReg) && email.trim().length() > 50) { // check if email length > 50
					errorMsgs.add("Email field only allows 50 characters");
					email = "";
				}

				// check if phone_num field is valid, it can be left empty(10 chars)
				if (!phone_num.trim().matches(phoneReg) && phone_num.trim().length() != 0) {
					errorMsgs.add("Please enter a valid phone number");
					phone_num = "";
				}

				if ("".equals(password)) { // check if password field is empty
					errorMsgs.add("You must enter a password");
					password = "";
				} else if (!password.equals(confirm)) {
					errorMsgs.add("The password and confirmation password do not match");
					password = "";
				}

				if ("".equals(confirm)) { // check if confirm password field is empty
					errorMsgs.add("You must confirm your password");
					confirm = "";
				}

				if (!address.trim().matches(addressReg) && address.trim().length() != 0) { // check if address is invalid
					errorMsgs.add("Invalid address");
					address = "";
				}

				// Some browsers do not support <input type="date">, so we need to use js to solve this problem

//				if(birthday.trim().matches(bdayReg)) {
//					errorMsgs.add("Birthday field format must be YYYY-mm-dd");
//					birthday = "";
//				}

				memberVO = new MemberVO();
				java.sql.Date bday = null;

				try {
					bday = java.sql.Date.valueOf(birthday.trim());
				} catch (Exception e) {
					bday = null;
				}

				memberVO.setName(name);
				memberVO.setEmail(email);
				memberVO.setAddress(address);
				memberVO.setPhone_num(phone_num);
				memberVO.setSex(sex);
				memberVO.setBirthday(bday);
				memberVO.setPassword(password);

				req.setAttribute("memberVO", memberVO);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/signUp.jsp");
					failureView.forward(req, res);
					return;
				}

				memberVO = mbSvc.signUp(memberVO);

				if ("".equals(memberVO.getEmail())) {
					errorMsgs.add("This email has been registered");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/signUp.jsp");
					failureView.forward(req, res);
					return;
				}

				RequestDispatcher view = req.getRequestDispatcher("/front-end/member/login.jsp");
				view.forward(req, res);
				return;

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/updateInfo.jsp");
				failureView.forward(req, res);
				return;
			}
		}

		if ("update_info".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String name = req.getParameter("name");
				String address = req.getParameter("address");
				String phone_num = req.getParameter("phone_num");
				String birthday = req.getParameter("birthday");
				String sex = req.getParameter("sex");
				String password = req.getParameter("password");
				String new_password = req.getParameter("new_password");
				String confirm = req.getParameter("confirm");
				byte[] profile_image = null;

				try {
					Part part = req.getPart("profile_image");
					InputStream in = part.getInputStream();
					profile_image = new byte[in.available()];
					in.read(profile_image);
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
					profile_image = null;
				}

				memberVO = (MemberVO) req.getSession().getAttribute("memberVO");
//				email = (String) req.getSession().getAttribute("email");
				java.sql.Date bday = null;

				try {
					bday = java.sql.Date.valueOf(birthday.trim());
				} catch (Exception e) {
					bday = null;
				}

				if (!new_password.equals(confirm)) {
					errorMsgs.add("The new password and confirmation password do not match");
					// check if what you input matches current password
				} else {
					memberVO.setName(name);
					memberVO.setAddress(address);
					memberVO.setPhone_num(phone_num);
					memberVO.setSex(sex);
					memberVO.setBirthday(bday);
					memberVO.setProfile_image(profile_image);
					memberVO.setPassword("");
					if (password.trim().length() != 0) {
						memberVO.setPassword(password);
					}
				}

				memberVO = mbSvc.updateInfo(memberVO, new_password);

				//error
				if (memberVO.getPassword().trim().length() == 0) {
					errorMsgs.add("Your current password is incorrect");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/updateInfo.jsp");
					failureView.forward(req, res);
					return;
				}

				RequestDispatcher view = req.getRequestDispatcher("/front-end/member/updateInfo.jsp");
				view.forward(req, res);
				return;

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/updateInfo.jsp");
				failureView.forward(req, res);
				return;
			}
		}

		if ("getOrders".equals(action)) {

		}

		if ("notification".equals(action)) {

		}

		if ("charity".equals(action)) {

		}

	}
}
