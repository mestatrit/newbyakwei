<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="内容管理 - 火酷" rm="false" style="/page/css/b.css">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">内容管理</div>
	<div class="hang"><hk:a href="/adminbomb/bomb_toadd.do">添加爆破手</hk:a></div>
	<div class="hang"><hk:a href="/adminbomb/bomb_toupdate.do">补充弹药</hk:a></div>
	<div class="hang"><hk:a href="/adminbomb/bomb_list.do">爆破手管理</hk:a></div>
	<div class="hang"><hk:a href="/bomb/bomb_list.do">爆破日志</hk:a></div>
	<div class="hang"><hk:a href="/bomb/bomb_tips.do">爆破tips日志</hk:a></div>
	<div class="hang"><hk:a href="/bomb/bomb_pinklabalist.do">精华日志</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>