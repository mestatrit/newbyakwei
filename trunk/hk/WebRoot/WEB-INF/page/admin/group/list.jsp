<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="足迹组管理 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">足迹组管理<br/>
	<hk:a href="/admin/cmpadmingroup_creategroup.do">创建组</hk:a>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="g" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/admin/cmpadmingroup_cmplist.do?groupId=${g.groupId}">${g.name}</hk:a> 
				<hk:a href="/admin/cmpadmingroup_editgroup.do?groupId=${g.groupId}">修改</hk:a>
			</div>
		</c:forEach>
		<hk:simplepage href="/admin/cmpadmingroup.do"/>
	</c:if>
	<c:if test="${fn:length(list)==0}"><hk:data key="nodataview"/></c:if>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>