<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpMyBbs"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="epp.user.setting"/> - ${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter">
<div class="divrow" style="width: 80px;text-align: center;">
<div>
<c:if test="${loginUser.userId==userId}">
<a href="<%=path %>/epp/web/op/user/set_sethead.do?companyId=${companyId}"><img alt="${user.nickName }" title="${user.nickName }" src="${user.head80Pic }"/></a>
</c:if>
<c:if test="${loginUser.userId!=userId}">
<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${userId}"><img alt="${user.nickName }" title="${user.nickName }" src="${user.head80Pic }"/></a>
</c:if>
</div>
<a class="b" href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${userId}">${user.nickName }</a>
</div>
<div class="divrow">
<c:if test="${fn:length(mycreatelist)>0}">
<c:set var="create_url"><%=path %>/epp/web/user_bbs.do?companyId=${companyId}&userId=${userId}&bbsflg=<%=CmpMyBbs.BBSFLG_CREATE %></c:set>
<div class="mod">
	<div class="mod_title">
		<a href="${create_url }"><hk:data key="epp.user.cmpbbs.create" arg0="${user.nickName}"/></a>
	</div>
	<div class="mod_content">
		<ul class="bbslist">
			<c:forEach var="mybbs" items="${mycreatelist}" varStatus="idx">
				<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="bg1" /></c:if>
				<c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="odd" /></c:if>
				<li class="bbslist_js rd<c:if test="${idx.index%2==0}"> bg1</c:if>">
					<div class="inbbs">
						<div class="title">
							<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${mybbs.cmpBbs.userId}"><img src="${mybbs.cmpBbs.user.head32Pic }"/> ${mybbs.cmpBbs.user.nickName }</a>：
							<a href="<%=path %>/epp/web/cmpbbs_view.do?companyId=${companyId}&bbsId=${mybbs.cmpBbs.bbsId}">${mybbs.cmpBbs.title }</a>
							<span class="ruo"><hk:data key="epp.bbs.replycount" arg0="${mybbs.cmpBbs.replyCount}"/></span>
						</div>
						<div class="replycon">
							<c:if test="${mybbs.cmpBbs.lastReplyUserId>0}">
								<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${mybbs.cmpBbs.lastReplyUserId}"><img src="${mybbs.cmpBbs.lastReplyUser.head32Pic }"/> ${mybbs.cmpBbs.lastReplyUser.nickName }</a>
								<c:set var="replytime" scope="request" value="${mybbs.cmpBbs.lastReplyTime}"></c:set>
								<span class="ruo"><hk:data key="epp.bbs.user_reply_on"/> <%=EppViewUtil.outBbsFmtTime(request,"replytime") %></span>
							</c:if>
							<c:if test="${mybbs.cmpBbs.lastReplyUserId==0}">
								<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${mybbs.cmpBbs.userId}"><img src="${mybbs.cmpBbs.user.head32Pic }"/> ${mybbs.cmpBbs.user.nickName }</a>
								<c:set var="createtime" scope="request" value="${mybbs.cmpBbs.createTime}"></c:set>
								<span class="ruo"><hk:data key="epp.bbs.user_create_on"/> <%=EppViewUtil.outBbsFmtTime(request,"createtime") %></span>
							</c:if>
						</div>
						<div class="clr"></div>
					</div>
				</li>
			</c:forEach>
		</ul>
		<c:if test="${more_create}">
			<div class="divrow">
				<a href="${create_url }"><hk:data key="epp.more"/></a>
			</div>
		</c:if>
		<div>
		</div>
	</div>
</div>
</c:if>
<c:if test="${fn:length(myreplylist)>0}">
<c:set var="reply_url"><%=path %>/epp/web/user_bbs.do?companyId=${companyId}&userId=${userId}&bbsflg=<%=CmpMyBbs.BBSFLG_REPLY %></c:set>
<div class="mod">
	<div class="mod_title">
		<a href="${reply_url }"><hk:data key="epp.user.cmpbbs.reply" arg0="${user.nickName}"/></a>
	</div>
	<div class="mod_content">
		<ul class="bbslist">
			<c:forEach var="mybbs" items="${myreplylist}" varStatus="idx">
				<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="bg1" /></c:if>
				<c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="odd" /></c:if>
				<li class="bbslist_js rd<c:if test="${idx.index%2==0}"> bg1</c:if>">
					<div class="inbbs">
					<div class="title">
						<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${mybbs.cmpBbs.userId}"><img src="${mybbs.cmpBbs.user.head32Pic }"/> ${mybbs.cmpBbs.user.nickName }</a>：
						<a href="<%=path %>/epp/web/cmpbbs_view.do?companyId=${companyId}&bbsId=${mybbs.cmpBbs.bbsId}">${mybbs.cmpBbs.title }</a>
						<span class="ruo"><hk:data key="epp.bbs.replycount" arg0="${mybbs.cmpBbs.replyCount}"/></span>
					</div>
					<div class="replycon">
						<c:if test="${mybbs.cmpBbs.lastReplyUserId>0}">
							<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${mybbs.cmpBbs.lastReplyUserId}"><img src="${mybbs.cmpBbs.lastReplyUser.head32Pic }"/> ${mybbs.cmpBbs.lastReplyUser.nickName }</a>
							<c:set var="replytime" scope="request" value="${mybbs.cmpBbs.lastReplyTime}"></c:set>
							<span class="ruo"><hk:data key="epp.bbs.user_reply_on"/> <%=EppViewUtil.outBbsFmtTime(request,"replytime") %></span>
						</c:if>
						<c:if test="${mybbs.cmpBbs.lastReplyUserId==0}">
							<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${mybbs.cmpBbs.userId}"><img src="${mybbs.cmpBbs.user.head32Pic }"/> ${mybbs.cmpBbs.user.nickName }</a>
							<c:set var="createtime" scope="request" value="${mybbs.cmpBbs.createTime}"></c:set>
							<span class="ruo"><hk:data key="epp.bbs.user_create_on"/> <%=EppViewUtil.outBbsFmtTime(request,"createtime") %></span>
						</c:if>
					</div>
					<div class="clr"></div>
					</div>
				</li>
			</c:forEach>
		</ul>
		<c:if test="${more_reply}">
			<div class="divrow">
				<a href="${reply_url }"><hk:data key="epp.more"/></a>
			</div>
		</c:if>
	</div>
</div>
</c:if>
</div>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>