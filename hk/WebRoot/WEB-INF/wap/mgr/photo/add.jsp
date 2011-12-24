<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.mgrsite.photo"/></c:set>
<hk:wap title="${title} - ${o.name }" rm="false">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang"><hk:data key="view.mgrsite.photo"/></div>
	<div class="hang">
		<hk:form enctype="multipart/form-data" action="/epp/mgr/photo_add.do">
			<hk:hide name="companyId" value="${companyId}"/>
			图片文件:<br/>
			<hk:file name="f"/><br/><br/>
			名称(<span class="ruo s">20字</span>):<br/>
			<hk:text name="name"/><br/>
			<hk:submit value="view.submit" res="true"/> 
		</hk:form>
	</div>
	<div class="hang even"><hk:a href="/epp/mgr/photo_list.do?companyId=${companyId}"><hk:data key="view.mgrsite.photo"/></hk:a></div>
	<div class="hang even"><hk:a href="/epp/mgr/mgr.do?companyId=${companyId}"><hk:data key="view.returnmain"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>