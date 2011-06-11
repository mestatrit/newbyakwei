package com.dev3g.cactus.dao.transaction;

public class ServiceTr {

	public Object execute(TranscationMaker transcationMaker, Object... objects) {
		return transcationMaker.proccess(objects);
	}
}