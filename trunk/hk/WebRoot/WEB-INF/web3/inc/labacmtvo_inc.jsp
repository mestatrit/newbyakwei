<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<ul class="labacmtlist">
	<c:if test="${morecmt}">
	<li>
	<div align="right"><a class="func" href="<%=path %>/laba.do?labaId=${labaId}&page=2">查看更多评论</a></div>
	</li>
	</c:if>
	<c:forEach var="cmtvo" items="${labacmtvolist}">
	<li id="cmt${cmtvo.labaCmt.cmtId }">
		<table cellpadding="0" cellspacing="0">
		<tr>
			<td width="48px">
			<a href="<%=path %>/home_web.do?userId=${cmtvo.labaCmt.userId}"><img src="${cmtvo.labaCmt.user.head32Pic }" style="vertical-align: top;"/></a>
			</td>
			<td>
				<a href="<%=path %>/home_web.do?userId=${cmtvo.labaCmt.userId}">${cmtvo.labaCmt.user.nickName }</a>：<br/>
				${cmtvo.content } <span class="ruo s"><fmt:formatDate value="${cmtvo.labaCmt.createTime}" pattern="yy-MM-dd HH:mm"/></span>
				<div align="right">
				<c:if test="${laba.userId==loginUser.userId || cmtvo.labaCmt.userId==loginUser.userId}">
				<a class="func" href="javascript:dellabacmt(${cmtvo.labaCmt.cmtId })">删除</a>
				</c:if>
				<a class="func" href="javascript:replylabacmt(${labaId },${cmtvo.labaCmt.cmtId },'${cmtvo.labaCmt.user.nickName }')">回复</a>
				</div>
			</td>
		</tr>
		</table>
	</li>
	</c:forEach>
	<li id="labacmtcon">
		<hk:form oid="labacmtfrm${labaId}" onsubmit="return sublabacmtfrm(this.id)" action="/op/labacmt_createweb.do" target="hideframe">
			<input id="replyLabaId${labaId }" type="hidden" name="labaId"/>
			<input id="replyCmtId${labaId }" type="hidden" name="replyCmtId"/>
			<div>
				<textarea id="cmt_content${labaId }" name="content" onkeydown="checkSubCmt(event,'labacmtfrm${labaId}');" class="cmtipt"></textarea>
				<hk:submit clazz="btn" value="提交"/>
				<div class="clr"></div>
			</div>
			<input id="cmtandlaba${labaId }" type="checkbox" name="cmtandlaba" value="1"/><label class="pointer" for="cmtandlaba${labaId }">同时发一条喇叭</label>
		</hk:form>
	</li>
</ul>