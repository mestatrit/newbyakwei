<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="引用回复符号设置 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
	<hk:form method="post" action="/user/set/set_setlabartflg.do">
		引用回复符号设置:<br/>
		<hk:select name="labartflg" checkedvalue="${userTool.labartflg}">
			<hk:option value="0" data="RT(Retweet)"/>
			<hk:option value="1" data="回复"/>
			<hk:option value="2" data="针对"/>
		</hk:select>
		<br/>
		<hk:submit value="保存"/>
	</hk:form>
	</div>
	<div class="hang"><hk:a href="/user/set/set.do">回到设置</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>