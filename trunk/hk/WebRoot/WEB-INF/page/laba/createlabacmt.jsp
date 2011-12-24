<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="回复${user.nickName} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang" onkeydown="submitLaba(event)">
		<hk:form name="labafrm" onsubmit="return confirmCreate();" method="post" action="/op/labacmt_create.do">
			<hk:hide name="replyCmtId" value="${cmtId}"/>
			<hk:hide name="labaId" value="${labaId }"/>
			回复@${user.nickName}<br/>
			<hk:textarea oid="status" name="content" value="${content}" clazz="ipt2" rows="2"/><br/>
			<hk:submit value="回复" name="cmt" clazz="sub"/> 
			<hk:submit value="回复并转发" name="cmtandlaba" clazz="sub"/> 
			<span id="remaining" class="ruo s">140</span><span class="ruo s"><hk:data key="view.char"/></span>
		</hk:form>
	</div>
	<div class="hang">
		<hk:a href="/laba/laba.do?labaId=${labaId}">返回评论列表</hk:a>
	</div>
	<jsp:include page="../inc/labainputjs.jsp"></jsp:include>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>