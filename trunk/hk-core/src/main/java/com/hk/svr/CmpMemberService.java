package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpMember;
import com.hk.bean.CmpMemberGrade;

public interface CmpMemberService {
	/**
	 * 创建会员级别
	 * 
	 * @param cmpMemberGrade
	 * @return false 名字已经存在,创建失败. true 创建成功
	 */
	boolean createCmpMemberGrade(CmpMemberGrade cmpMemberGrade);

	/**
	 * 更新会员级别
	 * 
	 * @param cmpMemberGrade
	 * @return false 名字已经存在,更新失败. true 更新成功
	 */
	boolean updateCmpMemberGrade(CmpMemberGrade cmpMemberGrade);

	/**
	 * 获取某个足迹的会员级别信息集合
	 * 
	 * @param companyId
	 * @return 会员级别信息集合
	 */
	List<CmpMemberGrade> getCmpMemberGradeListByCompanyId(long companyId);

	/**
	 * 删除会员级别，把相应会员级别更新为无级别
	 * 
	 * @param gradeId
	 */
	void deleteCmpMemberGrade(long gradeId);

	/**
	 * 获得会员级别信息
	 * 
	 * @param gradeId
	 * @return
	 */
	CmpMemberGrade getCmpMemberGrade(long gradeId);

	/**
	 * 获得某个足迹的会员
	 * 
	 * @param companyId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpMember> getCmpMemberList(long companyId, int begin, int size);

	/**
	 * 根据查询条件,获取足迹会员信息
	 * 
	 * @param companyId
	 * @param name 为空时忽略此条件
	 * @param mobile 手机号码,精确查询.如果为空时,忽略此条件
	 * @param email email,精确查询.如果为空时,忽略此条件
	 * @param gradeId 会员级别id.如果为0时,忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpMember> getCmpMemberList(long companyId, String name,
			String mobile, String email, long gradeId, int begin, int size);

	/**
	 * @param companyId
	 * @param name 为空时忽略此条件
	 * @param mobile 手机号码,精确查询.如果为空时,忽略此条件
	 * @param email email,精确查询.如果为空时,忽略此条件
	 * @param gradeId 会员级别id.如果为0时,忽略此条件
	 * @return
	 */
	int countCmpMember(long companyId, String name, String mobile,
			String email, long gradeId);

	/**
	 * 创建会员信息
	 * 
	 * @param cmpMember
	 * @return 0:创建成功 ,1:mobile 已经存在 ,2:E-mail已经存在
	 */
	int createCmpMember(CmpMember cmpMember);

	/**
	 * 更新会员信息
	 * 
	 * @param cmpMember
	 * @return 0:更新成功 ,1:mobile 已经存在,创建失败 .2:E-mail已经存在,创建失败
	 */
	int updateCmpMember(CmpMember cmpMember);

	/**
	 * 获得会员信息
	 * 
	 * @param companyId
	 * @param userId
	 * @return
	 */
	CmpMember getCmpMember(long companyId, long userId);

	/**
	 * 获得会员信息
	 * 
	 * @param memberId
	 * @return
	 */
	CmpMember getCmpMember(long memberId);

	/**
	 * 删除会员信息
	 * 
	 * @param memberId
	 */
	void deleteCmpMember(long memberId);

	/**
	 * 更新账户余额
	 * 
	 * @param memberId
	 * @param money 要增加或减少的金额
	 * @return true 更新成功 .false,余额不足,更新失败
	 */
	boolean addMoney(long memberId, byte addflg, double money);
}