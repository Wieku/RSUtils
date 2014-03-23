package pl.redstonefun.rsutils.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.yaml.YAML;

public class ConnectionGetter {
	public static Connection get()
	{
		Connection con = null;
		String url = "jdbc:" + YAML.getString(YAML.type.DATABASE,"mysql.url");
		String user = YAML.getString(YAML.type.DATABASE,"mysql.user");
		String password = YAML.getString(YAML.type.DATABASE,"mysql.password");
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			RSUtils.logger.info(e.getMessage());
		}
		return con;
	}
}
