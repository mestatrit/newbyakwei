<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.mgrsite.cmp"/></c:set>
<hk:wap title="${title} - ${o.name }" rm="false">
<c:set var="zoneName" value="${o.pcity.name}"></c:set>
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:form action="/epp/mgr/mgr_edit.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:data key="company.name"/>:<br/>
			<hk:text name="name" value="${o.name}"/><br/><span class="ruo s"><hk:data key="company.name.tip"/></span><br/><br/>
			<hk:data key="company.tel"/>:<br/>
			<hk:text name="tel" value="${o.tel}" maxlength="30"/><br/>
			<span class="ruo s"><hk:data key="company.tel.tip"/></span><br/><br/>
			<hk:data key="company.addr"/>(<span class="ruo s"><hk:data key="company.addr.tip"/></span>):<br/>
			<hk:textarea name="addr" value="${o.addr}"/><br/><br/>
			<hk:data key="company.traffic"/>(<span class="ruo s"><hk:data key="company.traffic.tip"/></span>):<br/>
			<hk:textarea name="traffic" value="${o.traffic}"/><br/><br/>
			<hk:data key="company.intro"/>(<span class="ruo s"><hk:data key="company.intro.tip"/></span>):<br/>
			<hk:textarea name="intro" value="${o.intro}" /><br/><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<div class="hang even"><hk:a href="/epp/mgr/mgr.do?companyId=${companyId}"><hk:data key="view.returnmain"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>