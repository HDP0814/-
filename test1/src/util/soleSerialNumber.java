package util;

import java.util.UUID;

public class soleSerialNumber {
	public static void main(String[] arg){
//		System.out.println("encode:"+new soleSerialNumber().getSerial());
	}
	public String getSerial(){
		String encode = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		return encode;
	}
	public void hashSerial(int length){
		long timestamp = System.currentTimeMillis();
		String value = Long.toHexString(timestamp+length);
		
		
	}
}
