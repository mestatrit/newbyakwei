<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view.user_company.title" arg0="${user.nickName}" /></c:set>
<c:set var="meta_value" scope="request">
<meta name="keywords" content="${user.nickName}"/>
<meta name="description" content="${user.nickName}"/>
</c:set>
<c:set var="body_hk_content" scope="request">
	<table class="frame-3" cellpadding="0" cellspacing="0">
		<tr>
			<td class="l">
				<jsp:include page="../inc/userleftnav_inc.jsp"></jsp:include>
			</td>
			<td class="mid">
				<div class="mid_con">
					<div class="mod">
						<c:set var="nav_2_short_content" scope="request"><a href="<%=path %>/usercmp.do?userId=${userId }" class="nav-a"><hk:data key="view.user_company.title" arg0="${user.nickName}" /></a></c:set>
						<jsp:include page="../inc/nav-2-short.jsp"></jsp:include>
					</div>
					<div class="mod">
						<c:set var="page_url" scope="request"><%=path%>/usercmp.do?userId=${userId}</c:set>
						<jsp:include page="../inc/usercmplist_inc.jsp"></jsp:include>
					</div>
				</div>
			</td>
			<td class="r">
				<div class="f_r">
					<div class="mod">
						<div class="mod-4 r_mod3">
							<%=Hkcss2Util.rd_bg %>
							<div class="tit">发布足迹的广告</div>
							<div class="cont">
								发布足迹的广告
							</div>
							<%=Hkcss2Util.rd_bg_bottom %>
						</div>
						<div class="clr">
						</div>
					</div>
					<c:if test="${fn:length(hotcmplist)>0}">
						<div class="mod">
							<div class="mod-4 r_mod3">
								<%=Hkcss2Util.rd_bg %>
								<div class="tit"><hk:data key="view.user.zonehotcmp.title"/></div>
								<div class="cont">
									<div class="imglist-1">
										<br class="linefix" />
										<c:forEach var="cmp" items="${hotcmplist}">
											<a class="imgref" href="<%=path %>/cmp.do?companyId=${cmp.companyId }"><img src="${cmp.head60 }" title="${cmp.name }"/></a>
										</c:forEach>
										<div class="clr">
										</div>
									</div>
								</div>
								<%=Hkcss2Util.rd_bg_bottom %>
							</div>
							<div class="clr">
							</div>
						</div>
					</c:if>
				</div>
			</td>
		</tr>
	</table>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>