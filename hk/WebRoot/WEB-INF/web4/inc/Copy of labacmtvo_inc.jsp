<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<ul class="labalist">
	<c:forEach var="cmtvo" items="${labacmtvolist}">
	<li id="cmt${cmtvo.labaCmt.cmtId }" style="height: 100%;width: 100%;">
		<div class="status" style="padding-left: 60px;height: 100%;">
			<span class="user" style="margin-left: -60px">
				<a href="/user/${cmtvo.labaCmt.userId}/"><img src="${cmtvo.labaCmt.user.head32Pic }" style="vertical-align: top;"/></a>
			</span>
			<div>
				<a href="/user/${cmtvo.labaCmt.userId}/">${cmtvo.labaCmt.user.nickName }</a>：<br/>
				${cmtvo.content } <span class="ruo s"><fmt:formatDate value="${cmtvo.labaCmt.createTime}" pattern="yy-MM-dd HH:mm"/></span>
				<c:if test="${userLogin}">
					<div align="right">
					<c:if test="${laba.userId==loginUser.userId || cmtvo.labaCmt.userId==loginUser.userId}">
					<a class="split-r" href="javascript:dellabacmt(${cmtvo.labaCmt.cmtId })">删除</a>
					</c:if>
					<a class="" href="javascript:replylabacmt(${labaId },${cmtvo.labaCmt.cmtId },'${cmtvo.labaCmt.user.nickName }')">回复</a>
					</div>
				</c:if>
			</div>
		</div>
	</li>
	</c:forEach>
	<c:if test="${userLogin}">
		<li id="labacmtcon" style="padding-bottom: 20px;margin-top: 10px;">
			<hk:form oid="labacmtfrm${labaId}" onsubmit="return sublabacmtfrm(this.id)" action="/op/labacmt_createweb.do" target="hideframe">
				<input id="replyLabaId${labaId }" type="hidden" name="labaId" value="${labaId }"/>
				<input id="replyCmtId${labaId }" type="hidden" name="replyCmtId"/>
				<div>
					<div style="padding-left: 10px;">
					<textarea id="cmt_content${labaId }" name="content" onkeydown="checkSubCmt(event,'labacmtfrm${labaId}');" class="cmtipt"></textarea>
					<br/>
					<div align="right" style="width: 350px;"><input id="cmtandlaba${labaId }" type="checkbox" name="cmtandlaba" value="1"/><label class="pointer" for="cmtandlaba${labaId }">同时发一条喇叭</label>
					<hk:submit clazz="btn" value="提交"/>
					<c:if test="${hide_close_cmt}">
						<a href="javascript:void(0)" onclick="showcmt(${labaId })">关闭</a>
					</c:if>
					</div>
					</div>
				</div>
			</hk:form>
		</li>
	</c:if>
</ul>