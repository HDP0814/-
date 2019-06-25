package main;

import java.text.SimpleDateFormat;
import java.util.Date;

public class timetempTest1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long currentTime = (new Date()).getTime()/1000;
		int intTime = (int)currentTime;
		System.out.println("currentTime:"+currentTime+" intTime:"+intTime);
		long a = 1000;
		int b = 10;
		System.out.println("b:"+b);
		long times = 1559093165;
		System.out.println("b-times:"+(b-times));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
	}

}
