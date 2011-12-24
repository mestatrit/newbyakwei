<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">
<a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${cmpNav.oid}">${cmpNav.name }</a>
&gt; <a href="<%=path %>/epp/web/cmpbbs_wapkind.do?companyId=${companyId}&kindId=${kindId}&navId=${navId}">${cmpBbsKind.name }</a>
</div>
<c:if test="${fn:length(list)==0}">
<div class="row ${clazz_var }">
<hk:data key="nodatalist"/>
</div>
</c:if>
<c:if test="${fn:length(list)>0}">
	<c:forEach var="bbs" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
	<div class="row ${clazz_var }">
		<a href="<%=path %>/epp/web/cmpbbs_wapview.do?companyId=${companyId}&navId=${navId }&bbsId=${bbs.bbsId}">${bbs.title }</a>
		<c:set var="createtime" scope="request" value="${bbs.createTime}"></c:set>
		<span class="ruo s">
		<c:if test="${bbs.replyCount>0}"><hk:data key="epp.bbs.replycount" arg0="${bbs.replyCount}"/></c:if>
		<c:if test="${bbs.replyCount==0}"><hk:data key="epp.bbs.user_create_on"/> <%=EppViewUtil.outBbsFmtTime(request,"createtime") %></c:if>
		</span>
	</div>
	</c:forEach>
</c:if>
<div class="row">
	<hk:simplepage2 href="/epp/web/cmpbbs_wapkind.do?companyId=${companyId}&kindId=${kindId}&navId=${navId}"/>
</div>
<jsp:include page="../inc/foot_inc.jsp"></jsp:include>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>