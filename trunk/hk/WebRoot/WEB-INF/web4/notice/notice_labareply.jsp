<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view2.notice"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 700px">
	<div class="f_l" style="width:700px;">
		<div class="active_tips_tab"><a href="<%=path %>/h4/op/notice_type2.do">喇叭的评论</a></div>
		<div class="inactive_tips_tab"><a href="<%=path %>/h4/op/notice_type4.do">提到你的喇叭</a></div>
		<div class="inactive_tips_tab"><a href="<%=path %>/h4/op/notice_type3.do">关注</a></div>
		<div class="inactive_tips_tab"><a href="<%=path %>/h4/op/notice_type5.do">邀请</a></div>
		<div class="clr"></div>
		<div id="listbox" class="listbox">
			<c:forEach var="n" items="${list}" varStatus="idx">
				<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2';document.getElementById('view_${n.noticeId }').className='';" onmouseout="this.className='divrow bdtm';document.getElementById('view_${n.noticeId }').className='hide';">
					<table>
						<tr>
							<td><a href="/user/${n.sender.userId }/"><img src="${n.sender.head32Pic }"/></a>
							</td>
							<td>
								<a class="b" href="/user/${n.sender.userId }/">${n.sender.nickName }</a>：
								${n.labaCmtContent } <span class="ruo2"><fmt:formatDate value="${n.createTime}" pattern="yy-MM-dd HH:mm"/></span>
								<a id="view_${n.noticeId }" class="hide" href="/laba/${n.labaId }">查看</a>
								<div style="font-size: 12px;margin-left: 40px;margin-top: 10px;">
									<span class="split-r">→</span> ${n.labaContent }
								</div>
							</td>
						</tr>
					</table>
				</div>
			</c:forEach>
			<c:set var="page_url" scope="request"><%=path %>/h4/op/notice_type2.do?v=1</c:set>
			<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>