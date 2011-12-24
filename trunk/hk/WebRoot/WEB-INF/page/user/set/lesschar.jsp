<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.UserTool"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="设置 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
		<div class="hang even">
		<hk:form action="/user/set/set_setlesscharfilter.do">
			为了提高信息浏览效率，任何人都可以根据回应字符数过滤喇叭列表，对于短回应，你可以到喇叭详细页面查看所有回应。<br/>
			开关如下:<br/>
			<hk:radioarea name="showReply" checkedvalue="${userTool.showReply}">
				<hk:radio value="<%=UserTool.SHOWREPLY_CHAR0+"" %>" data="usertool.showreply0" res="true"/><br/>
				<hk:radio value="<%=UserTool.SHOWREPLY_CHAR3+"" %>" data="usertool.showreply1" res="true"/><br/>
				<hk:radio value="<%=UserTool.SHOWREPLY_CHAR5+"" %>" data="usertool.showreply2" res="true"/><br/>
				<hk:radio value="<%=UserTool.SHOWREPLY_CHAR8+"" %>" data="usertool.showreply3" res="true"/><br/>
			</hk:radioarea>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
		</div>
		<div class="hang"><hk:a href="/square.do"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>