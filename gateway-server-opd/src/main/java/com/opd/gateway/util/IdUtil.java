package com.opd.gateway.util;

import java.util.UUID;

public class IdUtil {

	public static String id() {
		return UUID.randomUUID().toString();
	}

}
