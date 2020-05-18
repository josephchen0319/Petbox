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

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/login.jsp");
					failureView.forward(req, res);
					return;
				}

				if (mbSvc.login(email, password) == null) {
					errorMsgs.add("Email or Password not correct");
				} else {
					memberVO = mbSvc.login(email, password);
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

		}

		if ("getOrders".equals(action)) {

		}

		if ("notification".equals(action)) {

		}

		if ("charity".equals(action)) {

		}

	}
}
