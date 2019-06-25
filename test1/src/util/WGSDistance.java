package util;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * WGS坐标系两点间的距离计算
 * @author huangdongping
 *
 */
public class WGSDistance {
	double R = 6378.137;//单位km
	public static void main(String[] arg){
		GlobalCoordinates source = new GlobalCoordinates(25.861181, 114.935762);
        GlobalCoordinates target = new GlobalCoordinates(25.860506, 114.935951);

        double meter1 = getDistanceMeter(source, target, Ellipsoid.Sphere);
        double meter2 = getDistanceMeter(source, target, Ellipsoid.WGS84);
        System.out.println("Sphere坐标系计算结果："+meter1 + "米");
        System.out.println("WGS84坐标系计算结果："+meter2 + "米");
	}
	public double calculateDistance(double lngA,double latA,double lngB,double latB){
		return (this.R*Math.cos(lngA)*Math.cos(latA)+R*Math.cos(lngB)*Math.cos(latB))/(Math.pow(lngA,2)*Math.pow(lngB, 2)+Math.pow(latA,2)*Math.pow(latB, 2));
	}
	public static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid)
    {
        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);

        return geoCurve.getEllipsoidalDistance();
    }
}
