package pl.redstonefun.rsutils.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;

import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.yaml.YAML;

public class ConnectionGetter {
	
	private Deque<Connection> pool = null;
	
	private ConnectionGetter()
	{
		pool = new ArrayDeque<Connection>();
	}
	
	private static Connection establish()
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
	
	public void clean()
	{
		for(Connection c : pool)
		{
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		pool = null;
	}
	
	public synchronized Connection get()
	{
		if(pool.isEmpty())
		{
			return establish();
		}
		return pool.pop();
	}
	
	public void release(Connection c)
	{
		pool.push(c);
	}
	
	public void ping()
	{
		for(Connection c : pool)
		{
			try {
				PreparedStatement st = c.prepareStatement("SELECT 1;");
				st.execute();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static ConnectionGetter instance = null;
	public static ConnectionGetter getInstance()
	{
		return instance;
	}
	
	public static void start()
	{
		instance = new ConnectionGetter();
	}
	public static void stop()
	{
		instance.clean();
		instance = null;
	}
}
