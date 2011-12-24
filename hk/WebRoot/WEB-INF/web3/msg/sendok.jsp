<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="iteraotrmsg">
	<c:forEach var="msg" items="${list}"><c:set var="mainId" value="${msg.mainId}"></c:set>
		<li id="msg${mainId }" class="pvt" onmouseout="this.className='pvt';" onmouseover="this.className='pvt show';">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="head">
						<a href="<%=path %>/home_web.do?userId=${msg.user2Id }"><img src="${msg.user2.head48Pic }" title="${msg.user2.nickName}"/><br/>${msg.user2.nickName}</a>
					</td>
					<td class="content content-all">
						<div class="msg-body">
							${msg.msg } <a class="text_12" href="<%=path %>/msg/chat_web.do?mainId=${mainId }">展开</a>
							<div class="ruo"><fmt:formatDate value="${msg.createTime}"/></div>
						</div>
						<ul id="msgaction${mainId }" class="action">
							<li>
								<a href="javascript:replymsg(${mainId })" class="reply">回复</a>
							</li>
							<li>
								<a href="javascript:delpvt(${mainId })" class="delete">删除</a>
							</li>
						</ul>
					</td>
				</tr>
			</table>
		</li>
	</c:forEach>
</c:set>
<div id="content" style="display: none"></div>
<script type="text/javascript">
var html=document.getElementById("content");
parent.getNewMsg(html);
</script>