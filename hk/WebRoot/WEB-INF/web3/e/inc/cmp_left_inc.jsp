<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.JspDataUtil"%><%@page import="com.hk.web.util.Hkcss2Util"%>
<%@page import="com.hk.bean.Company"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
JspDataUtil.loadCmpLeftData(request,(Company)request.getAttribute("jsp_company"));%>
<div class="mod_left">
	<jsp:include page="../../inc/left_search.jsp"></jsp:include>
	<jsp:include page="../../inc/companykind_inc.jsp"></jsp:include>
	<c:if test="${fn:length(nearbylist)>0}">
		<div class="mod">
			<div class="mod-1">
				<%=Hkcss2Util.rd_bg%>
				<div class="tit">附近的足迹</div>
				<div class="cont">
					<br style="line-height: 0px;" />
					<c:forEach var="c" items="${nearbylist}">
						<div class="simple_product">
							<div class="image">
								<a href="<%=path%>/cmp.do?companyId=${c.companyId }"><img src="${c.head60 }" />
								</a>
							</div>
							<div class="content">
								<img src="<%=path%>/webst3/img/stars/star${c.starsLevel }.gif" />
								<br />
								<a href="<%=path%>/cmp.do?companyId=${c.companyId }">${c.name }</a>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
					<c:if test="${more_nearbylist}">
						<a class="more" href="#">更多</a>
					</c:if>
				</div>
				<%=Hkcss2Util.rd_bg_bottom%>
			</div>
			<div class="clr"></div>
		</div>
	</c:if>
	<c:if test="${fn:length(othercmplist)>0}">
		<div class="mod">
			<div class="mod-1">
				<%=Hkcss2Util.rd_bg%>
				<div class="tit">最新足迹</div>
				<div class="cont">
					<br style="line-height: 0px;" />
					<c:forEach var="c" items="${othercmplist}">
						<div class="simple_product">
							<div class="image">
								<a href="<%=path%>/cmp.do?companyId=${c.companyId }"><img src="${c.head60 }" />
								</a>
							</div>
							<div class="content">
								<c:if test="${c.starsLevel>0 }"><img src="<%=path%>/webst3/img/stars/star${c.starsLevel }.gif" /><br /></c:if>
								<a href="<%=path%>/cmp.do?companyId=${c.companyId }">${c.name }</a>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
					<c:if test="${more_othercmplist}">
						<a class="more" href="#">更多</a>
					</c:if>
				</div>
				<%=Hkcss2Util.rd_bg_bottom%>
			</div>
			<div class="clr"></div>
		</div>
	</c:if>
</div>