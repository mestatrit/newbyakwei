<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${cmpBbsKind.name} - ${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="">
	<div class="mod"><%EppViewUtil.loadCmpNavTop(request); %>
		<div class="mod_title">
			<a class="b" href="/column/${companyId }/${cmpnav_cmpbbs_column.oid}">${cmpnav_cmpbbs_column.name }</a> &gt; ${cmpBbsKind.name }
		</div>
		<div class="mod_content">
			<div class="divrow">
				<input type="button" class="btn" value="<hk:data key="epp.createbbs"/>" onclick="tourl('<%=path %>/epp/web/cmpbbs_create.do?companyId=${companyId}&kindId=${cmpBbsKind.kindId}&return_url=${return_url }')"/>
			</div>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata"><hk:data key="epp.bbs.nodata"/></div>
			</c:if>
			<c:if test="${fn:length(list)>0}">
				<ul class="bbslist">
					<c:forEach var="bbs" items="${list}" varStatus="idx">
						<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="bg1" /></c:if>
						<c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="odd" /></c:if>
						<li class="bbslist_js rd<c:if test="${idx.index%2==0}"> bg1</c:if>">
							<div class="inbbs">
							<div class="title">
								<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${bbs.userId}"><img src="${bbs.user.head32Pic }"/> ${bbs.user.nickName }</a>：
								<a href="<%=path %>/epp/web/cmpbbs_view.do?companyId=${companyId}&bbsId=${bbs.bbsId}">${bbs.title }</a>
								<span class="ruo"><hk:data key="epp.bbs.replycount" arg0="${bbs.replyCount}"/></span>
							</div>
							<div class="replycon">
								<c:if test="${bbs.lastReplyUserId>0}">
									<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${bbs.lastReplyUserId}"><img src="${bbs.lastReplyUser.head32Pic }"/> ${bbs.lastReplyUser.nickName }</a>
									<c:set var="replytime" scope="request" value="${bbs.lastReplyTime}"></c:set>
									<span class="ruo"><hk:data key="epp.bbs.user_reply_on"/> <%=EppViewUtil.outBbsFmtTime(request,"replytime") %></span>
								</c:if>
								<c:if test="${bbs.lastReplyUserId==0}">
									<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${bbs.userId}"><img src="${bbs.user.head32Pic }"/> ${bbs.user.nickName }</a>
									<c:set var="createtime" scope="request" value="${bbs.createTime}"></c:set>
									<span class="ruo"><hk:data key="epp.bbs.user_create_on"/> <%=EppViewUtil.outBbsFmtTime(request,"createtime") %></span>
								</c:if>
							</div>
							<div class="clr"></div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</c:if>
			<div>
			<c:set var="page_url" scope="request"><%=path%>/epp/web/cmpbbs_kind.do?companyId=${companyId}&kindId=${kindId}</c:set>
			<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$('.bbslist_js').bind('mouseout',function(){
		if(this.className.indexOf('bg1')!=-1){
			this.className='bbslist_js rd bg1';
		}
		else{
			this.className='bbslist_js rd';
		}
		});
	$('.bbslist_js').bind('mouseover',function(){
		this.className+=' bg3';
		});
});
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>