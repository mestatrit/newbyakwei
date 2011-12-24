<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="宝箱管理 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">宝箱管理</div>
	<div class="hang"><hk:a href="/box/op/op_openboxlist.do">当前宝箱</hk:a></div>
	<div class="hang"><hk:a href="/box/op/op_unopenboxlist.do">未开始宝箱</hk:a></div>
	<div class="hang"><hk:a href="/box/op/op_preboxlist.do">待审宝箱</hk:a></div>
	<div class="hang"><hk:a href="/box/op/op_overboxlist.do">往期宝箱</hk:a></div>
	<div class="hang"><hk:a href="/more.do">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>