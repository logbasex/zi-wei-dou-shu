package com.logbasex.ziweidoushu.enums;

import com.logbasex.ziweidoushu.consts.StringConst;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DiaChi {
	TY(1, "Tý"),
	SUU(2, "Sửu"),
	DAN(3, "Dần"),
	MAO(4, "Mão"),
	THIN(5, "Thìn"),
	TI(6, "Tỵ"),
	NGO(7, "Ngọ"),
	MUI(8, "Mùi"),
	THAN(9, "Thân"),
	DAU(10, "Dậu"),
	TUAT(11, "Tuất"),
	HOI(12, "Hợi");
	
	private final Integer id;
	private final String name;
	
	public static String getNameById(Integer id) {
		if (id == null) return null;
		return Arrays
				.stream(DiaChi.values())
				.filter(i -> i.getId().equals(id))
				.map(DiaChi::getName)
				.findFirst()
				.orElse(StringConst.EMPTY);
	}
}
