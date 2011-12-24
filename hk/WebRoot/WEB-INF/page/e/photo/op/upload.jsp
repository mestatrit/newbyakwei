<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${vo.company.name } - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form enctype="multipart/form-data" action="/e/op/photo/photo_add.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:data key="view.uploadimage"/>:<br/>
			<hk:file name="f"/><br/><br/>
			<hk:data key="photo.name"/>(<span class="ruo s"><hk:data key="photo.name.tip"/></span>):<br/>
			<hk:text name="name"/><br/>
			<hk:submit value="view.submit" res="true"/> 
			<hk:a href="/e/cmp.do?companyId=${companyId}"><hk:data key="view.cancel"/></hk:a>
		</hk:form>
	</div>
	<div class="hang"></div>
	<jsp:include page="../../../inc/foot.jsp"></jsp:include>
</hk:wap>