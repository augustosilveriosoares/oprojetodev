package com.opd.core.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.FieldError;

public class ErroUtil {

	public static Object formata(List<FieldError> list) {

		Set<String> fields = new HashSet<>();
		
		for (FieldError error : list) {
			fields.add(error.getField());
		}

		Map<String, Object> map = new HashMap<>();
		map.put("requiredFields", fields.toArray());
		return map;
	}
	
}
