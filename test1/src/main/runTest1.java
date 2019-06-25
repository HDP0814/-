package main;

import javax.annotation.Resource;

import testService.test1Service;
import testServiceImpl.test1Impl2;

public class runTest1 {
	@Resource test1Service test1service = new test1Impl2();
	public static void main(String[] arg){
		runTest1 rr = new runTest1();
		System.out.println(rr.test1service.getString());
	}
}
