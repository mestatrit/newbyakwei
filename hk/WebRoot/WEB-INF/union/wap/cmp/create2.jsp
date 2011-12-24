<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CompanyKindUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.bean.CompanyKind"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<%List<CompanyKind> list = CompanyKindUtil.getCompanKindList();
request.setAttribute("kindlist", list);%>
<c:set var="html_title" scope="request">创建商户</c:set>
<c:set var="html_main_content" scope="request">
	<div class="row">
		<hk:form action="/union/createcmp.do">
			<hk:hide name="uid" value="${uid}"/>
			<hk:hide name="pcityId" value="${pcityId}"/>
			当前地区:${pcity.name}<br/><br/>
			<hk:data key="company.name"/>:<br/>
			<hk:text name="name" value="${o.name}"/><br/>
			<span class="ruo s"><hk:data key="company.name.tip"/></span><br/><br/>
			<hk:data key="company.tel"/>:<br/>
			<hk:text name="tel" value="${o.tel}" maxlength="30"/><br/>
			<span class="ruo s"><hk:data key="company.tel.tip"/></span><br/><br/>
			<hk:data key="company.kindId"/>:<br/>
			<hk:select name="kindId" checkedvalue="${o.kindId}" forcecheckedvalue="${lastCmp.kindId}">
				<hk:option value="0" data=""/>
				<c:forEach var="k" items="${kindlist}">
					<hk:option value="${k.kindId}" data="${k.name}"/>
				</c:forEach>
			</hk:select><br/><br/>
			联盟分类:<br/>
			<hk:select name="unionKindId" checkedvalue="${o.unionKindId}">
				<hk:option value="0" data=""/>
				<c:forEach var="k" items="${cmpunionkindlist}">
					<hk:option value="${k.kindId}" data="${k.name}"/>
				</c:forEach>
			</hk:select><br/><br/>
			<hk:data key="company.addr"/>(<span class="ruo s"><hk:data key="company.addr.tip"/></span>):<br/>
			<hk:textarea name="addr" value="${o.addr}"/><br/><br/>
			<hk:data key="company.traffic"/>(<span class="ruo s"><hk:data key="company.traffic.tip"/></span>):<br/>
			<hk:textarea name="traffic" value="${o.traffic}"/><br/><br/>
			<hk:data key="company.intro"/>(<span class="ruo s"><hk:data key="company.intro.tip"/></span>):<br/>
			<hk:textarea name="intro" value="${o.intro}" /><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>