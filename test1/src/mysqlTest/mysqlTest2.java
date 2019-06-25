package mysqlTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mysqlTest2 {
	static Connection con;
	static Statement stmt;
	static PreparedStatement prestate;
	static ResultSet result;
	public static void main(String[] arg) throws SQLException{
		mysqlTest2 mt = new mysqlTest2();
		mt.position();
	}
	
	public void connection() throws SQLException{
		// 1.加载数据访问驱动
        try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://193.112.106.122:3306/washeritem?characterEncoding=utf8", "root", "zdh172cj");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //2.连接到数据"库"上去
        
	}
	
	public void position() throws SQLException{
		connection();
		stmt = con.createStatement();
		String sql = "select *from position";
		result  = stmt.executeQuery(sql);
		while(result.next()){
			System.out.println(result.getString("id"));
		}
	}
	
}
