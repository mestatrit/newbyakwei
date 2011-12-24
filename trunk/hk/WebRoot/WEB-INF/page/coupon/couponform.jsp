<%@ page language="java" pageEncoding="UTF-8"%><%@page import="java.util.Date"%><%@page import="com.hk.bean.Coupon"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:form action="${coupon_form_action}">
	<hk:hide name="ch" value="1"/>
	<%request.setAttribute("systime",new Date()); %>
	<c:set var="year_begin" value="2009"/>
	<c:set var="year_end"><fmt:formatDate value="${systime}" pattern="yyyy"/></c:set>
	<hk:hide name="couponId" value="${couponId}"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:data key="coupon.name"/>(<span class="ruo s"><hk:data key="coupon.name.tip"/></span>):<br/>
	<hk:text name="name" value="${coupon.name}" maxlength="20"/><br/><br/>
	发布地区(<span class="ruo s">为空时，全球发布</span>):<br/>
	<hk:text name="zoneName" value="${zoneName}"/><br/><br/>
	<hk:data key="coupon.content"/>(<span class="ruo s"><hk:data key="coupon.content.tip"/></span>):<br/>
	<hk:textarea name="content" value="${coupon.content}"/><br/><br/>
	<hk:data key="coupon.amount"/>:<br/>
	<hk:text name="amount" value="${coupon.amount}"/><br/><br/>
	<hk:data key="coupon.overdueflg"/>:<br/>
	<hk:radioarea name="overdueflg" checkedvalue="${coupon.overdueflg}" forcecheckedvalue="0">
		<hk:radio value="<%=Coupon.OVERDUEFLG_DAY %>" data="coupon.overdueflg0.tip" res="true"/><br/>
		<hk:text name="limitDay" value="${coupon.limitDay}" maxlength="10"/><br/>
		<hk:radio value="<%=Coupon.OVERDUEFLG_TIME %>" data="coupon.overdueflg1.tip" res="true"/><br/>
		<div style="padding-left: 20px">
			<div class="ha2">
			<c:set var="year"><fmt:formatDate value="${coupon.endTime}" pattern="yyyy"/></c:set>
			<c:set var="month"><fmt:formatDate value="${coupon.endTime}" pattern="M"/></c:set>
			<c:set var="date"><fmt:formatDate value="${coupon.endTime}" pattern="d"/></c:set>
			<hk:select name="year" checkedvalue="${year}">
				<c:forEach var="y" begin="${year_begin}" end="${year_end+10}" step="1">
					<hk:option value="${y}" data="${y}"/>
				</c:forEach>
			</hk:select>年
			</div>
			<div class="ha2">
				<hk:select name="month" checkedvalue="${month}">
					<c:forEach var="y" begin="1" end="12" step="1">
						<hk:option value="${y}" data="${y}"/>
					</c:forEach>
				</hk:select>月
			</div>
			<div class="ha2">
				<hk:text name="date" value="${date}" maxlength="2"/>日
			</div>
		</div>
	</hk:radioarea><br/>
	<hk:data key="coupon.remark"/>:<br/>
	<hk:textarea name="remark" value="${coupon.remark}"/><br/>
	<hk:submit value="view.submit" res="true"/>
</hk:form>