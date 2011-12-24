<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.HkObjArticle"%>
<%@page import="com.hk.bean.UserCmpFunc"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="开通企业功能" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">开通企业功能</div>
	<div class="hang">
		<hk:form method="get" action="/admin/admin_seluserforcmpfunc.do">
			<hk:hide name="ch" value="1"/>
			昵称：<hk:text name="nickName" value="${nickName}"/>
			<hk:submit value="搜索"/>
		</hk:form>
	</div>
	<c:if test="${user!=null}">
		<div class="hang">
			<img src="${user.head48Pic }" />
			<hk:a href="/home.do?userId=${user.userId}">${user.nickName}</hk:a>
		</div>
		<div class="hang">
			<hk:form action="/admin/admin_setcmpfunc.do">
				<hk:hide name="userId" value="${user.userId}"/>
				<c:if test="${userCmpFunc.boxflg==1}"><c:set var="box_check">checked="checked"</c:set></c:if>
				<c:if test="${userCmpFunc.couponflg==1}"><c:set var="coupon_check">checked="checked"</c:set></c:if>
				<c:if test="${userCmpFunc.adflg==1}"><c:set var="ad_check">checked="checked"</c:set></c:if>
				<input id="_boxflg" type="checkbox" name="boxflg" value="<%=UserCmpFunc.FLG_OPEN %>" ${box_check }/><label for="_boxflg">宝箱</label><br/>
				<input id="_couponflg" type="checkbox" name="couponflg" value="<%=UserCmpFunc.FLG_OPEN %>" ${coupon_check }/><label for="_couponflg">优惠券</label><br/>
				<input id="_adflg" type="checkbox" name="adflg" value="<%=UserCmpFunc.FLG_OPEN %>" ${ad_check }/><label for="_adflg">广告</label><br/>
				<hk:submit value="提交"/>
			</hk:form>
		</div>
	</c:if>
	<c:if test="${user==null && ch==1}">
		<div class="hang">
			没有找到此用户
		</div>
	</c:if>
	<div class="hang"><hk:a href="/more.do"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>