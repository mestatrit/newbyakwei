<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="宝箱超管 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">宝箱超管</div>
	<div class="hang odd"><hk:a href="/box/admin/adminbox.do">宝箱审核</hk:a></div>
	<div class="hang odd"><hk:a href="/box/admin/adminbox_openboxlist.do">当前宝箱</hk:a></div>
	<div class="hang odd"><hk:a href="/box/admin/adminbox_unopenboxlist.do">未开始宝箱</hk:a></div>
	<div class="hang odd"><hk:a href="/box/admin/adminbox_pauseboxlist.do">停止的宝箱</hk:a></div>
	<div class="hang odd"><hk:a href="/more.do">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>