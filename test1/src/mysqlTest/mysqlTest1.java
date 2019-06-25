package mysqlTest;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class mysqlTest1 {
	static Connection con;
	static Statement stmt;
	static PreparedStatement prestate;
	static ResultSet result;
	public static void main(String[] arg) throws SQLException{
		mysqlTest1 mt = new mysqlTest1();
		mt.userss();
	}
	
	public void connection() throws SQLException{
		// 1.加载数据访问驱动
        try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/uushop?characterEncoding=utf8", "root", "huang");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //2.连接到数据"库"上去
        
	}
	
	public void users() throws SQLException{
		connection();
		stmt = con.createStatement();
		String sql = "select *from user";
		result  = stmt.executeQuery(sql);
		while(result.next()){
			System.out.println(result.getString("account"));
		}
	}
	
	public void userss() throws SQLException{
		connection();
		String sql = "select *from user";
		prestate = con.prepareStatement(sql);
		result  = prestate.executeQuery(sql);
		while(result.next()){
			System.out.println(result.getString("account"));
		}
	}
}
