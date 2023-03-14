package com.logbasex.ziweidoushu.enums;

import com.logbasex.ziweidoushu.consts.StringConst;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ThienCan {
	GIAP(0, "Giáp"),
	AT(1, "Ất"),
	BINH(2, "Bính"),
	DINH(3, "Đinh"),
	MAU(4, "Mậu"),
	KY(5, "Kỷ"),
	CANH(6, "Canh"),
	TAN(7, "Tân"),
	NHAM(8, "Nhâm"),
	QUY(9, "Quý");
	
	private final Integer id;
	private final String name;
	
	public static String getNameById(Integer id) {
		if (id == null) return null;
		return Arrays
				.stream(ThienCan.values())
				.filter(i -> i.getId().equals(id))
				.map(ThienCan::getName)
				.findFirst()
				.orElse(StringConst.EMPTY);
	}
}
