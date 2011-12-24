<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title">修改</c:set>
<hk:wap title="${title} - ${o.name }" rm="false">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:form action="/epp/mgr/bulletin_update.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:hide name="bulletinId" value="${cmpBulletin.bulletinId}"/>
			<hk:data key="cmpbulletin.title"/>(<span class="ruo s"><hk:data key="cmpbulletin.title.tip"/></span>):<br/>
			<hk:text name="title" value="${cmpBulletin.title}" maxlength="20"/><br/><br/>
			<hk:data key="cmpbulletin.content"/>(<span class="ruo s"><hk:data key="cmpbulletin.content.tip"/></span>):<br/>
			<hk:textarea name="content" value="${cmpBulletin.content}" rows="7" clazz="ipt"/><br/><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<div class="hang even"><hk:a href="/epp/mgr/bulletin_list.do?companyId=${companyId}"><hk:data key="view.mgrsite.bulletin"/></hk:a></div>
	<div class="hang even"><hk:a href="/epp/mgr/mgr.do?companyId=${companyId}"><hk:data key="view.returnmain"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>