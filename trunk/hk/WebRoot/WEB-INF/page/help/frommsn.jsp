<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="通过MSN发送信息到火酷 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
	1、将火酷msn机器人<span class="orange">huoku.com@hotmail.com</span>添加成为自己的msn好友<br/>
	2、添加后,通过msn向火酷msn机器人发布的信息就变成火酷小喇叭了<br/>
	</div>
	<div class="hang"><hk:a href="/help_back.do?${query}">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>