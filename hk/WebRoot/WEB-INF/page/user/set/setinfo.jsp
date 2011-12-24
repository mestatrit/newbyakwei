<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.User"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="修改信息 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
	<hk:form method="post" action="/user/set/set_setInfo.do">
		姓名:<br/>
		<hk:text name="name" maxlength="10" value="${info.name}"/><br/>
		<span class="s">姓名为2到10个汉字、字母或数字</span>
		<br/><br/>
		性别:<br/>
		<hk:radioarea name="sex" checkedvalue="${user.sex}">
		<hk:radio value="<%=User.SEX_MALE %>" data="男"/>
		<hk:radio value="<%=User.SEX_FEMALE %>" data="女"/>
		</hk:radioarea><br/><br/>
		生日:<br/>
		<c:if test="${info.birthdayMonth>0}">
		<hk:text name="birthdayMonth" size="2" maxlength="2" value="${info.birthdayMonth}" clazz="number"/>月
		</c:if>
		<c:if test="${info.birthdayMonth==0}">
		<hk:text name="birthdayMonth" size="2" maxlength="2" clazz="number"/>月
		</c:if>
		<c:if test="${info.birthdayMonth>0}">
		<hk:text name="birthdayDate" size="2" maxlength="2" value="${info.birthdayDate}" clazz="number"/>日
		</c:if>
		<c:if test="${info.birthdayMonth==0}">
		<hk:text name="birthdayDate" size="2" maxlength="2" clazz="number"/>日
		</c:if>
		<br/><br/>
		个人介绍:<br/>
		<hk:textarea name="intro" value="${info.intro}"/><br/>
		<span class="ruo s">字数不能超过200个字符</span><br/>
		<hk:submit value="保存"/>
	</hk:form>
	</div>
	<div class="hang"><hk:a href="/user/set/set.do">回到设置</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>