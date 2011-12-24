<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.AdminHkb"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
	<hk:form action="/admin/admin_toaddHkb.do">
		昵称:<br/>
		<hk:text name="nickName"/><br/>
		<hk:submit value="view.search" res="true"/>
	</hk:form>
	</div>
	<div class="hang">
		<c:if test="${user!=null}">
			<hk:data key="view.addhkbingforuser" arg0="${user.nickName}"/><br/>
			<hk:data key="view.addhkb.userhkb" arg0="${info.hkb}"/><br/>
			<img src="${user.head48Pic }"/>
			${user.nickName }
		</c:if>
		<c:if test="${user==null && f!=1}"><hk:data key="nodataview"/></c:if>
	</div>
	<div class="hang">
		<c:if test="${user!=null}">
			<hk:form action="/admin/admin_addHkb.do">
				<hk:hide name="userId" value="${user.userId}"/>
				<hk:data key="adminhkb.addcount"/>:<br/>
				<hk:text name="addCount" value="${o.addCount}"/><br/><br/>
				<hk:data key="adminhkb.addflg"/>:<br/>
				<hk:radioarea name="addflg" checkedvalue="${o.addflg}" forcecheckedvalue="<%=AdminHkb.ADDFLG_MONEYBUY+"" %>">
					<hk:radio value="<%=AdminHkb.ADDFLG_MONEYBUY+"" %>" data="adminhkb.addflg0" res="true"/>
					<hk:radio value="<%=AdminHkb.ADDFLG_PRESENT+"" %>" data="adminhkb.addflg1" res="true"/>
				</hk:radioarea><br/><br/>
				<hk:data key="adminhkb.money"/>:<br/>
				<hk:text name="money" value="${o.money}" maxlength="5"/><br/><br/>
				<hk:data key="adminhkb.content"/>(<span><hk:data key="adminhkb.content.tip"/></span>):<br/>
				<hk:textarea name="content" value="${o.content}"/><br/>
				<hk:submit value="view.submit" res="true"/>
			</hk:form>
		</c:if>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>