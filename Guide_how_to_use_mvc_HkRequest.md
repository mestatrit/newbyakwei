
```

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("key", "value");
		int num = req.getInt("num");// 获得int 数据
		num = req.getInt("num", 1);// 获得int 数据，如果没有num参数，则默认返回 值1
		long num_long = req.getInt("sysid");
		num_long = req.getLong("num", 9L);
		double num_double = req.getDouble("num");
		num_double = req.getDouble("num", 4.0d);
		float num_float = req.getFloat("num");
		num_float = req.getFloat("num", 89f);
		byte num_byte = req.getByte("num");
		num_byte = req.getByte("num", (byte) 8);
		boolean bool = req.getBoolean("bool");
		String value = req.getString("name");// 获得String 参数值
		value = req.getString("name", "default");
		value = req.getStringRow("name");// 获得String值，并强制取消回车与换行
		String[] values = req.getStrings("name");// 获得一组String 值
		int[] nums = req.getInts("nums");
		long[] num_longs = req.getLongs("nums");
		Cookie cookie = req.getCookie("cookie_name");// 根据name获得cookie
		File file = req.getFile("file_name");// 根据name获得上传的文件
		File[] files = req.getFiles();// 获得所有上传的文件
		String fileName = req.getOriginalFileName("file_name");// 获得文件存储的原文件名称
		Object obj_in_session = req.getSessionValue("key");// 获得存储到session中的数据
		req.setSessionValue("key", obj_in_session);// 存储数据到session;
		return null;
	}

```