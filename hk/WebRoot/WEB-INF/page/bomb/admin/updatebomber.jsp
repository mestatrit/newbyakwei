<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="补充弹药 - 火酷" rm="false" style="/page/css/b.css">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">补充弹药</div>
	<div class="hang even">
		<hk:form action="/adminbomb/bomb_update.do">
			昵称:<br/>
			<hk:text name="nickName" maxlength="10" value="${nickName}"/><br/><br/>
			炸弹数量:<br/>
			<hk:text name="count" maxlength="10" value="${count}"/><br/><br/>
			精华数量:<br/>
			<hk:text name="pinkCount" maxlength="10" value="${pinkCount}"/><br/>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/adminbomb/bomb.do">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>