package oracleTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class insertData {

	private static Connection con = null;
    private static Statement sql = null;
    private static ResultSet rs = null;
    private static PreparedStatement pstmt = null;
	public static void main(String[] args) throws SQLException, ParseException {
		// TODO Auto-generated method stub
		
//		System.out.println(con);
		
//		insertEnergyInfo("2");
		int idFlag = 684;
		for(int i = 9; i < 740 ; i++){
			con = dbConn("PLATFORM","PLATFORM");
			insertEnergyDetail(i+"",idFlag);
			idFlag += (24*60/15);
		}
		
	}

	public static Connection dbConn(String name, String pass) {
	       Connection c = null;
	       try {
	           Class.forName("oracle.jdbc.driver.OracleDriver");
	           // 要是导入驱动没有成功的话都是会出现classnotfoundException.自己看看是不是哪里错了,例如classpath这些设置
	       } catch (ClassNotFoundException e) {
	           e.printStackTrace();
	       }
	       try {
	           c = DriverManager.getConnection(
	                  "jdbc:oracle:thin:@192.168.14.93:1521:orcl", name, pass);
	           // 连接数据的方法有四种, 这个属于最简单的,一般用网页程序 chenh是你的数据库实例名称，在下载的文件test.sql中可以执行语句查看
	           // "jdbc:oracle:thin:@计算机名称:监听端口:系统实例名", username, password,
	           // 计算机名称,要是自己不知道可以在计算机属性查知.
	           // 监听端口一般默认是1521, 要是改变了就看自己的监听文件listener.ora
	           // 系统实例名一般是默认orcl, 要是不是的话就用 select name from v$database; 看看当前的实例名.在本程序中我更改了实例为chenh
	           // username,password,就是登陆数据库的用户名和密码.
	 

	       } catch (SQLException e) {
	           e.printStackTrace();
	       }
	       return c;
	    }
	
		public static void insertEnergyInfo(String userid) throws SQLException, ParseException{
			String sql =  "INSERT INTO t_tbp_userEnergyInfo VALUES(?,?,?,?)"; 
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			  pstmt = (PreparedStatement) con.prepareStatement(sql); 
			  String date = "2018-01-01";
			  String id = "373";
			  String elec = "";
			  String userNumber = userid;
			  con.setAutoCommit(false);//Turn off transaction auto-commit
			  for(int i = 373 ;i < (373+365) ; i++){
				  elec = Math.round(Math.random()*20)+"";
				  id = i + "";
				  pstmt.setString(1, id);
				  pstmt.setString(2, date);
				  pstmt.setString(3, elec);
				  pstmt.setString(4, userNumber);
				  pstmt.addBatch();//Adds an SQL command to the list of commands
				  
				  SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
				  Date dateStart = formats.parse(date);
				  long timess = (long)dateStart.getTime();
				  timess += 86400000;
				  date = formats.format(new Date(Long.valueOf(timess))); 
//				  System.out.println("date:"+date+"  elect:"+elec+" id:"+id);  
			  }
			  // 执行批量更新
			  pstmt.executeBatch();
			  // 语句执行完毕，提交本事务
			  con.commit();
			  pstmt.close();
			  con.close();
		}
		
		public static void insertEnergyDetail(String info_id,int idFlag) throws ParseException, SQLException{
			String sql =  "INSERT INTO t_tbp_userEnergyDetail VALUES(?,?,?,?,?,?,?)"; 
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			  pstmt = (PreparedStatement) con.prepareStatement(sql); 
			  String id = "11";
			  String time_elec = "";
			  String elec = "";
			  String voltage = "";
			  String mini_P = "";
			  String times = "00:00:00";
			  String info_ids = info_id;
			  
			  con.setAutoCommit(false);//Turn off transaction auto-commit
			  for(int i = idFlag ;i < (24*60/15)+idFlag ; i++){
				  id = i + "";
				  time_elec = String.format("%.2f", (Math.random()*20));
				  Double d_elec = (Math.random()*2);
				  Double d_vol = (Math.random()*2);
				  elec = String.format("%.2f", d_elec);
				  voltage = String.format("%.2f", d_vol);
				  mini_P = String.format("%.2f",d_elec*d_vol);
				  
				  pstmt.setString(1, id);
				  pstmt.setString(2, time_elec);
				  pstmt.setString(3, elec);
				  pstmt.setString(4, voltage);
				  pstmt.setString(5, mini_P);
				  pstmt.setString(6, times);
				  pstmt.setString(7, info_ids);
				  pstmt.addBatch();//Adds an SQL command to the list of commands
				  
				  SimpleDateFormat formats = new SimpleDateFormat("HH:mm:ss");
				  Date dateStart = formats.parse(times);
				  long timess = (long)dateStart.getTime();
				  timess += 900000;
				  times = formats.format(new Date(Long.valueOf(timess))); 
//				  System.out.println("date:"+times+"  d_elec:"+elec+" id:"+id+" info_ids:"+info_ids);  
			  }
			  // 执行批量更新
			  pstmt.executeBatch();
			  // 语句执行完毕，提交本事务
			  con.commit();
			  pstmt.close();
			  con.close();
		}
}
