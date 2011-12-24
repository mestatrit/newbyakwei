<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="e.op.addcompany.title"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form action="/e/op/op_add.do">
			当前城市:<br/>
			<hk:text name="zoneName" value="${zoneName}"/><br/><br/>
			<hk:data key="company.name"/>:<br/>
			<hk:text name="name" value="${name}"/><br/>
			<span class="ruo s"><hk:data key="company.name.tip"/></span><br/><br/>
			<hk:data key="company.tel"/>:<br/>
			<hk:text name="tel" value="${o.tel}" maxlength="30"/><br/>
			<span class="ruo s"><hk:data key="company.tel.tip"/></span><br/><br/>
			<hk:data key="company.addr"/>(<span class="ruo s"><hk:data key="company.addr.tip"/></span>):<br/>
			<hk:textarea name="addr" value="${o.addr}"/><br/><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<div class="hang">
	<hk:a href="/e/cmp_list.do?cityId=${sys_zone_pcityId}">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>