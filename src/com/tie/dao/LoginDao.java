package com.tie.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tie.util.DbUtil;
import com.tie.model.TieUser;

public class LoginDao {
	// Not extend basedao, instead, make one db conn via basedao and access to
	// the db by conn from basedao
	public boolean validate(String name, String pass) throws SQLException {
		Connection conn = BaseDao.getInstance().getConnection();
		boolean status = false;
		ResultSet rs = null;
		PreparedStatement pst = null;
		pst = conn.prepareStatement("select * from mx.tieuser where name=? and code=?");
		pst.setString(1, name);
		pst.setString(2, pass);

		rs = pst.executeQuery();
		status = rs.next();

		return status;
	}
}