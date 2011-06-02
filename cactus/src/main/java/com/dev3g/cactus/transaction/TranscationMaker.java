package com.dev3g.cactus.transaction;

/**
 * 特殊的事务处理模块使用，为了在service中缩小事务范围而使用
 * 
 * @author akwei
 */
public interface TranscationMaker {

	Object proccess(Object... objects);
}