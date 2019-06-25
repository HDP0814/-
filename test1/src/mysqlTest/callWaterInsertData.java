package mysqlTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.MakeOrderNumUtil;
import util.RandomCharData;
import util.RandomName;

public class callWaterInsertData {
	static Connection con;
	static Statement stmt;
	static PreparedStatement prestate;
	static ResultSet result;
//	static String[] name={"张诗涵","张雅婷","张雅涵","李天一","李文萱","刘慧娴",
//			"刘一鸣","刘萌萌","杨贺雯","杨文萱","杨芊慧","杨梦婷","陈欣怡","陈泽浩",
//			"陈晨","韩格","韩博文","韩雨宋","何琼","何轩","柴嘉玉"
//			};
//	static String[] phonenumber={"15219466201","17008803333","15036222256","18438888133",
//			"18876941131","18876622089","18889262767","13715150077","13849038741",
//			"13717033838","17703771999","18351078990","13467719111","15997693333","13261308888",
//			"17198296296","13730600607","13699051071","18352936688","17061560444",
//			"15627327777"
//	};
	static int[] balance ={60,100,200,300,400,500,600,1000,250,150};
	static String[] times = {"2019-01-02 10:30:20","2019-02-03 10:30:20","2019-03-04 10:30:20",
			"2019-04-10 10:30:20","2019-05-12 10:30:20","2019-05-16 10:30:20",
			"2019-06-01 10:30:20","2019-06-03 10:30:20","2018-06-05 10:30:20","2018-03-05 10:30:20",
			"2018-04-08 10:30:20","2018-05-05 10:30:20","2019-04-15 10:30:20"
	};
	public static void main(String[] args) throws SQLException, ParseException {
		// TODO Auto-generated method stub
		callWaterInsertData ci = new callWaterInsertData();
		ci.insert();
	}
	
	public void connection() throws SQLException{
		// 1.加载数据访问驱动
        try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://193.112.106.122:3306/callWater?characterEncoding=utf8", "root", "zdh172cj");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //2.连接到数据"库"上去
        
	}
	
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert() throws SQLException, ParseException{
		connection();
		PreparedStatement pre1;
		PreparedStatement pre2;
		PreparedStatement pre3;
		PreparedStatement pre4;
		String name = null;
		String phone = null;
		String openid = null;
		String school = "江西理工大学";
		String dormitory = null;
		String houseNumber = null;
		String model = null;
		int balance = 0;
		int hN_i = 1;
		int d_i = 101;
		String userSql="insert into user(openid,school,name,houseNumber,dormitory,phonenumber,balance) values(?,?,?,?,?,?,?)";
		String userBillSql="insert into userBill(ordernumber,money,dates,state,openid) values(?,?,?,?,?)";
		String orderInfoSql="insert into orderInfo(achieveState,foundTime,achieveTime,orderNumber,openid) values(?,?,?,?,?)";
		String modelSql="insert into equipmentInit(modelNumber,init,openid) values(?,?,?)";
		pre1 = (PreparedStatement) con.prepareStatement(userSql);
		pre2 = (PreparedStatement) con.prepareStatement(userBillSql);
		pre3 = (PreparedStatement) con.prepareStatement(orderInfoSql);
		pre4 = (PreparedStatement) con.prepareStatement(modelSql);
		for(int i = 0 ; i < 100; i++){
			houseNumber=((hN_i + (i/30))+"");
			dormitory=((d_i +((i/6)%5)*100 + (i%6)) +"");
			balance = this.balance[i%10];
			name=(RandomName.getName());
			openid=(RandomCharData.createRandomCharData(20));
			phone=(RandomCharData.getPhoneRandom());
			pre1.setString(1, openid);
			pre1.setString(2, school);
			pre1.setString(3, name);
			pre1.setString(4, houseNumber);
			pre1.setString(5, dormitory);
			pre1.setString(6, phone);
			pre1.setString(7, balance+"");
			pre1.addBatch();
			
			if(i < 10){
				model = "a-00"+i;
			}
			else if(i < 100){
				model = "a-0" + i;
			}
			else{
				model = "a-"+ i;
			}
			pre4.setString(1, model);
			pre4.setString(2, "1");
			pre4.setString(3, openid);
			pre4.addBatch();
			//一个人24条记录
			for(int j = 0 ;j < 15 ; j++){
//				String ordernumber = RandomCharData.createData(15);
				String state = "消费";
				String achieveState = "1";
				String orderNumber = MakeOrderNumUtil.makeOrderNum()+j;
				int money = 6;
				if(j%4 == 0){
					state ="充值";
					money = 100;
				}
				String time = this.times[j%this.times.length];
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = sdf.parse(time);
				pre2.setString(1, orderNumber);
				pre2.setString(2, money+"");
				pre2.setString(3, time);
				pre2.setString(4, state);
				pre2.setString(5, openid);
				pre2.addBatch();
				
				pre3.setString(1, achieveState);
				pre3.setString(2, time);
				pre3.setString(3, time);
				pre3.setString(4, orderNumber);
				pre3.setString(5, openid);
				pre3.addBatch();
			}
//			System.out.println("houseNumber:"+houseNumber+" dormitory:"+dormitory);
		}
		pre1.executeBatch();
		pre2.executeBatch();
		pre3.executeBatch();
		pre4.executeBatch();
		pre1.close();
		pre2.close();
		pre3.close();
		pre4.close();
		con.close();
	}
	
}
