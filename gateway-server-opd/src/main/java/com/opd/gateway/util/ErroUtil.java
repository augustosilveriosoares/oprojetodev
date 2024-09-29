package com.opd.gateway.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.FieldError;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

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
	
	public static <F> Set<Object> valida(Validator validator, F form) {
		Set<Object> erros = null;

		Set<ConstraintViolation<F>> violations = validator.validate(form);
		if (!violations.isEmpty()) {
			erros = new HashSet<>();
			for (ConstraintViolation<F> violation : violations)
				erros.add(violation.getPropertyPath().toString());
		}

		return erros;
	}
	
}
