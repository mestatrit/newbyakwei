<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${zone } - 设置分类排名竞价" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">${zone } - 足迹分类</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="k" items="${list}" varStatus="idx">
			<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/admin/orderdef_cmpkind.do?kindId=${k.kindId}&cityId=${cityId}&provinceId=${provinceId }">${k.name}</hk:a>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${fn:length(list)==0}"><div class="hang"><hk:data key="nodatalist"/></div></c:if>
	<div class="hang"><hk:a href="/index_tosearchcity.do?fn=3&show_country=true&hide_all=1"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>