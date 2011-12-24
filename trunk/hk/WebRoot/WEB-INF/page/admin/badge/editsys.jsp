<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.Badge"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="创建徽章 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form enctype="multipart/form-data" action="/admin/badge_editsys.do">
			<hk:hide name="badgeId" value="${badgeId}"/>
			<hk:hide name="ch" value="1"/>
			类型:<br/>
			<hk:select name="ruleflg" checkedvalue="${o.ruleflg}" style="width:200px">
				<hk:option value="<%=Badge.LIMITSYS_RULEFLG_ADVENTURER %>" data="view2.badge.syslimit.ruleflg1001" res="true"/>
				<hk:option value="<%=Badge.LIMITSYS_RULEFLG_EXPLORER %>" data="view2.badge.syslimit.ruleflg1002" res="true"/>
				<hk:option value="<%=Badge.LIMITSYS_RULEFLG_SUPERSTAR %>" data="view2.badge.syslimit.ruleflg1003" res="true"/>
				<hk:option value="<%=Badge.LIMITSYS_RULEFLG_PLAYA_PLEASE %>" data="view2.badge.syslimit.ruleflg1004" res="true"/>
				<hk:option value="<%=Badge.LIMITSYS_RULEFLG_CRUNKED %>" data="view2.badge.syslimit.ruleflg1005" res="true"/>
				<hk:option value="<%=Badge.LIMITSYS_RULEFLG_LOCAL %>" data="view2.badge.syslimit.ruleflg1006" res="true"/>
				<hk:option value="<%=Badge.LIMITSYS_RULEFLG_SUPER_USER %>" data="view2.badge.syslimit.ruleflg1007" res="true"/>
				<hk:option value="<%=Badge.LIMITSYS_RULEFLG_OVER_SHARE %>" data="view2.badge.syslimit.ruleflg1008" res="true"/>
				<hk:option value="<%=Badge.LIMITSYS_RULEFLG_BENDER %>" data="view2.badge.syslimit.ruleflg1009" res="true"/>
				<hk:option value="<%=Badge.LIMITSYS_RULEFLG_SCHOOL_NIGHT %>" data="view2.badge.syslimit.ruleflg1010" res="true"/>
				<hk:option value="<%=Badge.LIMITSYS_RULEFLG_SUPER_MAYOR %>" data="view2.badge.syslimit.ruleflg1011" res="true"/>
				<hk:option value="<%=Badge.LIMITSYS_RULEFLG_GYM_RAT %>" data="view2.badge.syslimit.ruleflg1012" res="true"/>
				<hk:option value="<%=Badge.LIMITSYS_RULEFLG_DONOT_STOP_BELIEVIN %>" data="view2.badge.syslimit.ruleflg1013" res="true"/>
				<hk:option value="<%=Badge.LIMITSYS_RULEFLG_CREATE_3_VENUE %>" data="view2.badge.syslimit.ruleflg1014" res="true"/>
			</hk:select><br/><br/>
			名称:<br/>
			<hk:text name="name" value="${o.name}"/><br/><br/>
			说明:<br/>
			<hk:textarea name="intro" value="${o.intro}"/><br/><br/>
			图片:<br/>
			<hk:file name="f"/><br/>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<div class="hang">
	<hk:a href="/admin/badge_view.do?badgeId=${badgeId}">返回</hk:a>
	</div>
	<div class="hang">
		<hk:a href="/admin/badge.do">回到列表</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>