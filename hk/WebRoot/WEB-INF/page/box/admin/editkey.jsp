<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="修改短信开箱暗号 - 火酷" rm="false"  bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="c4">修改短信开箱暗号</div>
	<div class="hang">
		<hk:form action="/box/admin/adminbox_updateBoxKey.do">
			<hk:hide name="boxId" value="${boxId}"/>
			<hk:hide name="repage" value="${repage}"/>
			<hk:hide name="t" value="${t}"/>
			<div class="c4">宝箱名称</div>
			<div class="hang">${box.name}</div>
			<div class="c4">短信开箱暗号</div>
			<div class="hang"><hk:text name="boxKey" maxlength="6" value="${box.boxKey}"/></div>
			<div class="hang">
				<hk:submit value="保存" clazz="sub"/>
			</div>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/box/admin/adminbox_getbox.do?boxId=${boxId }&repage=${repage}&t=${t}">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>