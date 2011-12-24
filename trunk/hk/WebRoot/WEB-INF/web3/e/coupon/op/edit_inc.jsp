<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.Coupon"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%String path=request.getContextPath(); %>
<hk:form oid="coupon_frm" enctype="multipart/form-data" onsubmit="return subeditfrm(this.id)" action="/e/op/auth/coupon_update.do" target="hideframe">
	<hk:hide name="couponId" value="${couponId}"/>
	<hk:hide name="companyId" value="${companyId}"/>
		<table class="infotable" cellpadding="0" cellspacing="0" width="600px">
			<tr>
				<td width="100px">名称</td>
				<td>
					<hk:text name="name" value="${coupon.name}" clazz="text"/>
					<div class="error" id="name_error"></div>
				</td>
				
			</tr>
			<tr>
				<td>数量</td>
				<td>
					<hk:text name="amount" value="${coupon.amount}" clazz="text"/>
					<div class="error" id="amount_error"></div>
				</td>
			</tr>
			<tr>
				<td>内容</td>
				<td>
					<hk:textarea name="content" value="${coupon.content}" clazz="text_area"/>
					<div class="error" id="content_error"></div>
				</td>
			</tr>
			<tr>
				<td>期限</td>
				<td>
					<div>
						<c:set var="overdueflg_0_checked">${coupon.limitDayStyle}</c:set>
						<c:set var="overdueflg_1_checked">${!coupon.limitDayStyle}</c:set>
						<div class="pad">
							<input type="radio" id="overdueflg_0" name="overdueflg" value="<%=Coupon.OVERDUEFLG_DAY %>"/>
							自下载之日计算，<hk:text name="limitDay" value="${coupon.limitDay}" clazz="text_short_1"/>天后过期<br/>
						</div>
						<div class="pad">
							<input type="radio" id="overdueflg_1" name="overdueflg" value="<%=Coupon.OVERDUEFLG_TIME %>"/>
							<fmt:formatDate var="time" value="${coupon.endTime}" pattern="yyyy-MM-dd"/>
							本批优惠券到期时间为：<hk:text name="time" clazz="text_short_1 date-pick1" value="${time}"/>
						</div>
					</div>
					<div class="error" id="time_error"></div>
					<script type="text/javascript">
						if(${overdueflg_0_checked}){
							getObj('overdueflg_0').checked=true;
						}
						else{
							getObj('overdueflg_1').checked=true;
						}
					</script>
				</td>
			</tr>
			<tr>
				<td>图片</td>
				<td>
					<hk:file name="f"/>
				</td>
			</tr>
			<tr>
				<td>备注</td>
				<td>
					<hk:textarea name="remark" value="${coupon.remark}" clazz="text_area"/>
					<div class="error" id="remark_error"></div>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<div align="center"><hk:submit value="提交" clazz="btn"/></div>
				</td>
			</tr>
		</table>
</hk:form>