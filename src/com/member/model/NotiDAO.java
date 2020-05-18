package com.member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class NotiDAO implements NotiDAO_interface {

	private static DataSource ds;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PetDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

//	private static final String UPDATE = "";
//	private static final String GET_ALL = "";
	private static final String INSERT = "INSERT INTO notification (notification_id, "
			+ " member_id, notification_class) VALUES (noti_seq.NEXTVAL, ?, ?)";
	private static final String GET_ALL = "SELECT notification_class, notification_id, create_time FROM notification where member_id = ?";
//	private static final String GET_ONE = "SELECT notification_class FROM notification where notification_id = ?";
	private static final String DELETE = "DELETE FROM notification where notification_id = ?";

	public void insert(NotiVO notiVO) {
		try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(INSERT);) {
			ps.setString(1, notiVO.getMember_id());
			ps.setInt(2, notiVO.getNotification_class());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("A database error occured. " + e.getMessage());
		}

	};

	public void delete(String notification_id) {
		try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE);) {

			ps.setString(1, notification_id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("A database error occured. " + e.getMessage());
		}
	};

	public List<NotiVO> findByMembId(String member_id) {
		List<NotiVO> notis = new ArrayList<>();
		NotiVO notiVO = null;
		ResultSet rs = null;
		try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(GET_ALL);) {

			ps.setString(1, member_id);
			rs = ps.executeQuery();

			while (rs.next()) {
				notiVO = new NotiVO();
				notiVO.setNotification_id(rs.getString("notification_id"));
				notiVO.setNotification_class(rs.getInt("notification_class"));
				notiVO.setCreate_time(rs.getTimestamp("create_time"));
				notis.add(notiVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return notis;
	}
}
