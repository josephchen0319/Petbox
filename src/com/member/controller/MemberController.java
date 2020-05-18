package com.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberService;
import com.member.model.MemberVO;

//test
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
		PrintWriter out = res.getWriter();
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		MemberVO memberVO = null;

		if ("login".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String email = req.getParameter("email");
				String emailReg = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";

				if (email == null || (email.trim().length()) == 0) {
//					errorMsgs.add("請輸入電子郵件");
					errorMsgs.add("Please enter your email");

				} else if (!email.trim().matches(emailReg)) {
//					errorMsgs.add("電子郵件格式有誤");
					errorMsgs.add("Invalid email format");
				}

				String password = req.getParameter("password");
				if (password == null || (password.trim().length()) == 0) {
					errorMsgs.add("Please enter your password");
				}

				memberVO = mbSvc.login(email, password);

				if (memberVO == null) {
					errorMsgs.add("Email or Password not correct");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/login.jsp");
					failureView.forward(req, res);
					return;
				}

				req.setAttribute("memberVO", memberVO);

				errorMsgs.add("welcome home " + memberVO.getEmail());
				RequestDispatcher view = req.getRequestDispatcher("/front-end/member/login.jsp");
				view.forward(req, res);
				return;

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		if ("signup".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String emailReg = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";
				String phoneReg = "^09[0-9]{8}$";
				String nameReg = "^[(\u4e00-\u9fa5) (a-zA-Z)]{2,20}$";
				String chineseReg = "^[(\u4e00-\u9fa5)]+";
				String englishReg = "^[(a-zA-Z)]+";
				String addressReg = "^[(\u4e00-\u9fa5) (0-9a-zA-Z).,]+";

				String name = req.getParameter("name");
				String email = req.getParameter("email");

				String phone_num = req.getParameter("phone_num");
				String password = req.getParameter("password");
				String confirm = req.getParameter("confirm");
				String address = req.getParameter("address");
				String sex = req.getParameter("sex");

				String birthday = req.getParameter("birthday");

				// use ajax to check valid field in real time
				if (name == null || (name.trim().length() == 0)) { // check if name field is empty
					errorMsgs.add("You must enter a name");
				} else if (!name.trim().matches(nameReg)) {
					errorMsgs.add("Name field does not allow special characters");
					// check if name field is invalid
				} else if (name.trim().matches(chineseReg) && name.trim().matches(englishReg)) {
					errorMsgs.add("Name field only allows Chinese or English， you can't mix them");
				} else if (name.trim().matches(chineseReg) && name.trim().length() >= 7) { // check if chinese name(length > 7)
					errorMsgs.add("Name field only allows 6 chinese characters");
				} else if (name.trim().matches(englishReg) && name.trim().length() > 20) { // check if english name(length > 20)
					errorMsgs.add("Name field only allows 20 english characters");
				}

				if (email == null || (email.trim().length()) == 0) { // Check if email field is empty
//					errorMsgs.add("請輸入電子郵件");
					errorMsgs.add("You must enter an email");
				} else if (!email.trim().matches(emailReg)) { // check if email format is incorrect(50 chars)
//					errorMsgs.add("電子郵件格式有誤");
					errorMsgs.add("Invalid email");
				} else if (email.trim().matches(emailReg) && email.trim().length() > 50) { // check if email length > 50
					errorMsgs.add("Email field only allows 50 characters");
				}

				if (!phone_num.trim().matches(phoneReg) && phone_num.trim().length() != 0) { // check if phone_num field is
																																											// valid, it can be left empty(10
																																											// chars)
					errorMsgs.add("Please enter valid phone number");
				}

				if (password == null || (password.trim().length()) == 0) { // check if password field is empty
					errorMsgs.add("You must enter a password");
				}

				if (confirm == null || (password.trim().length()) == 0) { // check if confirm password field is empty
					errorMsgs.add("You must confirm your password");
				}

				if (!address.trim().matches(addressReg) && address.trim().length() != 0) {
					errorMsgs.add("Invalid address");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/signUp.jsp");
					failureView.forward(req, res);
					return;
				}

				req.setAttribute("memberVO", memberVO);

				memberVO = mbSvc.signUp(name, email, password, phone_num, address, sex, birthday);

				if (memberVO == null) {
					errorMsgs.add("This email has been registered");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/signUp.jsp");
					failureView.forward(req, res);
					return;
				}
				RequestDispatcher view = req.getRequestDispatcher("/front-end/member/login.jsp");
				view.forward(req, res);
				return;

			} catch (Exception e) {
				e.printStackTrace();
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
