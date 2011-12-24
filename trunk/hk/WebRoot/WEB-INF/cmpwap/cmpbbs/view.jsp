<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">
<a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${cmpNav.oid}">${cmpNav.name }</a>
&gt; <a href="<%=path %>/epp/web/cmpbbs_wapkind.do?companyId=${companyId}&kindId=${cmpBbsKind.kindId}&navId=${navId}">${cmpBbsKind.name }</a>
<br/>
${bbsUser.nickName }：${cmpBbs.title }
<span class="s ruo">
<c:if test="${cmpBbs.replyCount>0}"><hk:data key="epp.bbs.replycount" arg0="${cmpBbs.replyCount}"/></c:if>
<c:if test="${bbs.replyCount==0}"><hk:data key="epp.bbs.user_create_on"/> <%=EppViewUtil.outBbsFmtTime(request,"createtime") %></c:if>
</span>
</div>
<c:if test="${page==1}">
<div class="row">
${cmpBbsContent.content }
<c:if test="${not empty cmpBbs.picpath}">
<div><img src="${cmpBbs.pic240Url }"/></div>
</c:if>
</div>
</c:if>
<c:forEach var="reply" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
<div class="row ${clazz_var }">
${(page-1)*10+(idx.index+1)}. ${reply.user.nickName }：<span class="s ruo"><fmt:formatDate value="${reply.createTime}" pattern="yyyy-MM-dd HH:mm"/></span><br/>
${reply.content }
</div>
</c:forEach>
<div class="row">
	<hk:simplepage2 href="/epp/web/cmpbbs_wapview.do?companyId=${companyId}&bbsId=${bbsId}&navId=${navId}"/>
</div>
<jsp:include page="../inc/foot_inc.jsp"></jsp:include>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>