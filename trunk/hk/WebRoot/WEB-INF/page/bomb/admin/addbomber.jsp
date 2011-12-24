<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.Bomber"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="添加爆破手 - 火酷" rm="false" style="/page/css/b.css">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">添加爆破手</div>
	<div class="hang even">
		<hk:form action="/adminbomb/bomb_add.do">
			昵称:<br/>
			<hk:text name="nickName" maxlength="10" value="${nickName}"/><br/><br/>
			炸弹数量:<br/>
			<hk:text name="count" maxlength="10" value="${count}"/><br/><br/>
			精华数量:<br/>
			<hk:text name="pinkCount" maxlength="10" value="${pinkCount}"/><br/><br/>
			权限:<br/>
			<hk:select name="userLevel" checkedvalue="${userLevel}">
				<hk:option value="-1" data=""/>
				<hk:option value="<%=Bomber.USERLEVEL_NORMAL+"" %>" data="爆破手"/>
				<c:if test="${superAdmin}">
				<hk:option value="<%=Bomber.USERLEVEL_ADMIN+"" %>" data="管理员"/>
				</c:if>
			</hk:select>
			<br/>
			<hk:submit value="添加"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/adminbomb/bomb.do">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>