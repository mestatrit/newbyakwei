<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<input type="hidden" id="caneditpic" value="${caneditpic }"/>
<input type="hidden" id="votestat" value="${votestat }"/>
<c:if test="${fn:length(list)>0}">
<input type="hidden" id="show_pci_cmt" value="1"/>
</c:if>
<c:if test="${fn:length(list)==0}">
<input type="hidden" id="show_pci_cmt" value="0"/>
</c:if>
<br id="cmtline" class="linefix"/>
<c:forEach var="cmt" items="${list}" varStatus="idx">
<div id="cmt${cmt.cmtId }" class="block <c:if test="${idx.index%2!=0}">bg1</c:if> <c:if test="${force_me && loginUser.userId==vo.cmpTip.userId}">bg2</c:if>">
	<div class="pic">
		<a href="/user/${cmt.userId }/"><img src="${cmt.user.head32Pic }"/></a>
	</div>
	<c:if test="${userLogin && cmt.userId==loginUser.userId}">
		<div class="action">
			<a href="javascript:delcmt(${cmt.cmtId })"><hk:data key="view2.delete"/></a>
		</div>
	</c:if>
	<div class="content">
	<a class="b" href="/user/${cmt.userId }/">${cmt.user.nickName }</a><br/>
	${cmt.content } <span class="ruo2"><fmt:formatDate value="${cmt.createTime}" pattern="yy-MM-dd HH:mm"/></span>
	</div>
</div>
</c:forEach>
