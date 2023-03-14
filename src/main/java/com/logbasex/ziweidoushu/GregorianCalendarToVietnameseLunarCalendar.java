package com.logbasex.ziweidoushu;

import com.logbasex.ziweidoushu.enums.DiaChi;
import com.logbasex.ziweidoushu.enums.ThienCan;
import org.springframework.core.convert.converter.Converter;

/**
 * <a href="http://lichhuongque.com/PressRelease.html">...</a>
 * <a href="https://vn-z.vn/threads/c-chuyen-doi-duong-lich-sang-am-lich.17478/">...</a>
 * <a href="https://www.informatik.uni-leipzig.de/~duc/amlich/">...</a>
 */
public class GregorianCalendarToVietnameseLunarCalendar implements Converter<String, String> {
	@Override
	public String convert(String value) {
		return null;
	}
	
	
	public String canChiNam(int yy) {
		int diaChiNumber = (yy + 6) % 10;
		int thienCanNumber = (yy + 8) % 12;
		return ThienCan.getNameById(diaChiNumber) + " " + DiaChi.getNameById(thienCanNumber) + " (" + yy + ")";
		//Trả về kết quả vd: Tân Mùi (1991)
	}
	
	public String canChiThang(int mm, int yy) //mm, yy = tháng, năm âm lịch
	{
		return ThienCan.getNameById((yy * 12 + mm + 3) % 10) + " " + DiaChi.getNameById((mm + 1) % 12);
	}
	
	public static int doubleToMathFloor(double d) {
		return (int) Math.floor(d);
	}
	
	public static int mod(int x, int y) {
		int z = x - (int) (y * Math.floor(((double) x / y)));
		if (z == 0) {
			z = y;
		}
		return z;
	}
	
	//Đổi ngày dương lịch ra số ngày Julius
	public static double solarDayToJuliusDay(int day, int month, int year) {
		double juliusDay;
		if (year > 1582 || (year == 1582 && month > 10) || (year == 1582 && month == 10 && day > 14)) {
			juliusDay = 367 * year - doubleToMathFloor(7 * (year + doubleToMathFloor((month + 9) / 12)) / 4) - doubleToMathFloor(3 * (doubleToMathFloor((year + (month - 9) / 7) / 100) + 1) / 4) + doubleToMathFloor(275 * month / 9) + day + 1721028.5;
		} else {
			juliusDay = 367 * year - doubleToMathFloor(7 * (year + 5001 + doubleToMathFloor((month - 9) / 7)) / 4) + doubleToMathFloor(275 * month / 9) + day + 1729776.5;
		}
		return juliusDay;
	}
	
	//Đổi số ngày Julius ra ngày dương lịch
	public static int[] solarFromJD(double juliusDay) {
		int z;
		int a;
		int alpha;
		int b;
		int c;
		int d;
		int e;
		int dd;
		int mm;
		int yyyy;
		double f;
		
		z = doubleToMathFloor(juliusDay + 0.5);
		f = (juliusDay + 0.5) - z;
		if (z < 2299161) {
			a = z;
		} else {
			alpha = doubleToMathFloor((z - 1867216.25) / 36524.25);
			a = z + 1 + alpha - doubleToMathFloor(alpha / 4);
		}
		b = a + 1524;
		c = doubleToMathFloor((b - 122.1) / 365.25);
		d = doubleToMathFloor(365.25 * c);
		e = doubleToMathFloor((b - d) / 30.6001);
		dd = doubleToMathFloor(b - d - doubleToMathFloor(30.6001 * e) + f);
		if (e < 14) {
			mm = e - 1;
		} else {
			mm = e - 13;
		}
		if (mm < 3) {
			yyyy = c - 4715;
		} else {
			yyyy = c - 4716;
		}
		return new int[]{dd, mm, yyyy};
	}
	
	//Chuyển đổi số ngày Julius / ngày dương lịch theo giờ địa phương LOCAL_TIMEZONE, Việt Nam: LOCAL_TIMEZONE = 7.0
	public static int[] localFromJD(double juliusDay) {
		return solarFromJD(juliusDay + (7.0 / 24.0));
	}
	
	public static double localToJD(int day, int month, int year) {
		return solarDayToJuliusDay(day, month, year) - (7.0 / 24.0);
	}
	
	//Tính thời điểm Sóc
	public static double newMoon(int k) {
		double t = k / 1236.85;
		double t2 = t * t;
		double t3 = t2 * t;
		double dr = Math.PI / 180;
		double jd1 = 2415020.75933 + 29.53058868 * k + 0.0001178 * t2 - 0.000000155 * t3;
		jd1 = jd1 + 0.00033 * Math.sin((166.56 + 132.87 * t - 0.009173 * t2) * dr);
		double m = 359.2242 + 29.10535608 * k - 0.0000333 * t2 - 0.00000347 * t3;
		double mpr = 306.0253 + 385.81691806 * k + 0.0107306 * t2 + 0.00001236 * t3;
		double f = 21.2964 + 390.67050646 * k - 0.0016528 * t2 - 0.00000239 * t3;
		double c1 = (0.1734 - 0.000393 * t) * Math.sin(m * dr) + 0.0021 * Math.sin(2 * dr * m);
		c1 = c1 - 0.4068 * Math.sin(mpr * dr) + 0.0161 * Math.sin(dr * 2 * mpr);
		c1 = c1 - 0.0004 * Math.sin(dr * 3 * mpr);
		c1 = c1 + 0.0104 * Math.sin(dr * 2 * f) - 0.0051 * Math.sin(dr * (m + mpr));
		c1 = c1 - 0.0074 * Math.sin(dr * (m - mpr)) + 0.0004 * Math.sin(dr * (2 * f + m));
		c1 = c1 - 0.0004 * Math.sin(dr * (2 * f - m)) - 0.0006 * Math.sin(dr * (2 * f + mpr));
		c1 = c1 + 0.0010 * Math.sin(dr * (2 * f - mpr)) + 0.0005 * Math.sin(dr * (2 * mpr + m));
		double deltat;
		if (t < -11) {
			deltat = 0.001 + 0.000839 * t + 0.0002261 * t2 - 0.00000845 * t3 - 0.000000081 * t * t3;
		} else {
			deltat = -0.000278 + 0.000265 * t + 0.000262 * t2;
		}
		return jd1 + c1 - deltat;
	}
	
	//Tính vị trí của mặt trời
	public static double sunLongitude(double jdn) {
		double t = (jdn - 2451545.0) / 36525;
		double t2 = t * t;
		double dr = Math.PI / 180;
		double m = 357.52910 + 35999.05030 * t - 0.0001559 * t2 - 0.00000048 * t * t2;
		double l0 = 280.46645 + 36000.76983 * t + 0.0003032 * t2;
		double dl = (1.914600 - 0.004817 * t - 0.000014 * t2) * Math.sin(dr * m);
		dl = dl + (0.019993 - 0.000101 * t) * Math.sin(dr * 2 * m) + 0.000290 * Math.sin(dr * 3 * m);
		double l = l0 + dl;
		l = l * dr;
		l = l - Math.PI * 2 * (doubleToMathFloor(l / (Math.PI * 2)));
		return l;
	}
	
	//Tính tháng âm lịch chứa ngày Đông chí
	public static int[] lunarMonth11(int year) {
		double off = localToJD(31, 12, year) - 2415021.076998695;
		int k = doubleToMathFloor(off / 29.530588853);
		double jd = newMoon(k);
		int[] ret = localFromJD(jd);
		double sunLong = sunLongitude(localToJD(ret[0], ret[1], ret[2]));
		if (sunLong > 3 * Math.PI / 2) {
			jd = newMoon(k - 1);
		}
		return localFromJD(jd);
	}
	
	//Tính năm âm lịch
	public static int[][] lunarYear(int year) {
		int[][] ret;
		int[] month11A = lunarMonth11(year - 1);
		double jdMonth11A = localToJD(month11A[0], month11A[1], month11A[2]);
		int k = (int) Math.floor(0.5 + (jdMonth11A - 2415021.076998695) / 29.530588853);
		int[] month11B = lunarMonth11(year);
		double off = localToJD(month11B[0], month11B[1], month11B[2]) - jdMonth11A;
		boolean leap = off > 365.0;
		if (!leap) {
			ret = new int[13][];
		} else {
			ret = new int[14][];
		}
		ret[0] = new int[]{month11A[0], month11A[1], month11A[2], 0, 0};
		ret[ret.length - 1] = new int[]{month11B[0], month11B[1], month11B[2], 0, 0};
		for (int i = 1; i < ret.length - 1; i++) {
			double nm = newMoon(k + i);
			int[] a = localFromJD(nm);
			ret[i] = new int[]{a[0], a[1], a[2], 0, 0};
		}
		for (int i = 0; i < ret.length; i++) {
			ret[i][3] = mod(i + 11, 12);
		}
		if (leap) {
			initLeapYear(ret);
		}
		return ret;
	}
	
	//Tính tháng nhuận
	public static void initLeapYear(int[][] ret) {
		double[] sunLongitudes = new double[ret.length];
		for (int i = 0; i < ret.length; i++) {
			int[] a = ret[i];
			double jdAtMonthBegin = localToJD(a[0], a[1], a[2]);
			sunLongitudes[i] = sunLongitude(jdAtMonthBegin);
		}
		boolean found = false;
		for (int i = 0; i < ret.length; i++) {
			if (found) {
				ret[i][3] = mod(i + 10, 12);
				continue;
			}
			double sl1 = sunLongitudes[i];
			double sl2 = sunLongitudes[i + 1];
			boolean hasMajorTerm = Math.floor(sl1 / Math.PI * 6) != Math.floor(sl2 / Math.PI * 6);
			if (!hasMajorTerm) {
				found = true;
				ret[i][4] = 1;
				ret[i][3] = mod(i + 10, 12);
			}
		}
	}
	
	//Đổi ngày dương lịch ra âm lịch
	public static int[] solar2Lunar(int day, int month, int year) {
		int yy = year;
		int[][] ly = lunarYear(year);
		int[] month11 = ly[ly.length - 1];
		double jdToday = localToJD(day, month, year);
		double jdMonth11 = localToJD(month11[0], month11[1], month11[2]);
		if (jdToday >= jdMonth11) {
			ly = lunarYear(year + 1);
			yy = year + 1;
		}
		int i = ly.length - 1;
		while (jdToday < localToJD(ly[i][0], ly[i][1], ly[i][2])) {
			i--;
		}
		int dd = (int) (jdToday - localToJD(ly[i][0], ly[i][1], ly[i][2])) + 1;
		int mm = ly[i][3];
		if (mm >= 11) {
			yy--;
		}
		return new int[]{dd, mm, yy, ly[i][4]};
		//Nếu ly[i][4] == 1 => tháng mm Nhuận
	}
}
