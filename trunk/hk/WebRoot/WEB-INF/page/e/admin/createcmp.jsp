<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form action="/e/admin/admin_createcmp.do">
			<hk:hide name="ch" value="1"/>
			当前城市:<br/>
			<hk:text name="zoneName" value="${zoneName}"/><br/><br/>
			<hk:data key="company.name"/>:<br/>
			<hk:text name="name" value="${name}"/><br/>
			<span class="ruo s"><hk:data key="company.name.tip"/></span><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<div class="hang">
	<hk:a href="/more.do">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>