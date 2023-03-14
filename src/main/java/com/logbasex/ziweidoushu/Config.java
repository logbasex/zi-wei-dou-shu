package com.logbasex.ziweidoushu;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

public class Config {
	@Bean
	public ConversionService conversionService() {
		DefaultConversionService service = new DefaultConversionService();
		service.addConverter(new GregorianCalendarToVietnameseLunarCalendar());
		return service;
	}
}
