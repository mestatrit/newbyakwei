<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="回应 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
		<c:if test="${labaVo.laba.userId!=loginUser.userId}">
			<div class="hang" onkeydown="submitLaba(event)">
				<hk:form name="labafrm" onsubmit="return confirmCreate();" method="post" action="/op/labacmt_create.do">
					<hk:hide name="userId" value="${labaVo.laba.userId}"/>
					<hk:hide name="lastUrl" value="/laba/laba.do?labaId=${labaVo.laba.labaId }"/>
					<hk:hide name="labaId" value="${labaId }"/>
					对@${labaVo.laba.user.nickName}的喇叭说两句<br/>
					<hk:textarea oid="status" name="content" value="${content}" clazz="ipt2" rows="2"/><br />
					<hk:submit value="评论" name="cmt" clazz="sub"/> 
					<c:if test="${cp!=1}"><hk:submit value="评论并转发" name="cmtandlaba" clazz="sub"/> </c:if>
					<c:if test="${labaVo.laba.userId!=loginUser.userId}">
					<hk:submit value="私信" name="msg_submit" clazz="sub"/> 
					</c:if>
					<span id="remaining" class="ruo s">140</span><span class="ruo s"><hk:data key="view.char"/></span>
				</hk:form>
			</div>
		</c:if>
		<jsp:include page="laba_reply_inc.jsp"></jsp:include>
		<jsp:include page="../inc/labainputjs.jsp"></jsp:include>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>