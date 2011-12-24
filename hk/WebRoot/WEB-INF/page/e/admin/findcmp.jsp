<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpAdminHkbLog"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="查询足迹 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
	<hk:form action="/e/admin/admin_findcmp.do">
		名字:<br/>
		<hk:text name="name"/><br/>
		<hk:submit value="view.search" res="true"/>
	</hk:form>
	</div>
	<div class="hang">
		<c:if test="${company!=null}">
			正在为${company.name}充值<br/>
			足迹当前酷币为${company.hkb}<br/>
		</c:if>
		<c:if test="${company==null && f!=1}"><hk:data key="nodataview"/></c:if>
	</div>
	<div class="hang">
		<c:if test="${company!=null}">
			<hk:form action="/e/admin/admin_addhkb.do">
				<hk:hide name="companyId" value="${company.companyId}"/>
				<hk:data key="adminhkb.addcount"/>:<br/>
				<hk:text name="addCount" value="${o.addCount}"/><br/><br/>
				<hk:data key="adminhkb.addflg"/>:<br/>
				<hk:radioarea name="addflg" checkedvalue="${o.addflg}" forcecheckedvalue="<%=CmpAdminHkbLog.ADDFLG_MONEYBUY+"" %>">
					<hk:radio value="<%=CmpAdminHkbLog.ADDFLG_MONEYBUY+"" %>" data="adminhkb.addflg0" res="true"/>
					<hk:radio value="<%=CmpAdminHkbLog.ADDFLG_PRESENT+"" %>" data="adminhkb.addflg1" res="true"/>
				</hk:radioarea><br/><br/>
				<hk:data key="adminhkb.money"/>:<br/>
				<hk:text name="money" maxlength="10" value="${o.money}"/><br/><br/>
				<hk:data key="adminhkb.content"/>(<span><hk:data key="adminhkb.content.tip"/></span>):<br/>
				<hk:textarea name="remark" value="${o.remark}"/><br/>
				<hk:submit value="view.submit" res="true"/>
			</hk:form>
		</c:if>
	</div>
	<div class="hang"><hk:a href="/more.do"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>