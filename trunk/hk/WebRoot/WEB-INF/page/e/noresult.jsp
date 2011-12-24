<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:rmBlankLines rm="true">
			<hk:a href="/e/cmp_list.do?w=all"><hk:data key="view.all"/></hk:a>|
			<hk:a href="/e/cmp_list.do?w=city" clazz="nn"><hk:data key="view.mycity"/></hk:a>|
			<hk:a href="/e/cmp_tosearchcity.do"><hk:data key="view.selectzone"/></hk:a>
		</hk:rmBlankLines>
	</div>
	<div class="hang even"><hk:data key="nodataview"/></div>
	<div class="hang"><hk:a href="/e/cmp_tosearchcity.do"><hk:data key="view.search"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>