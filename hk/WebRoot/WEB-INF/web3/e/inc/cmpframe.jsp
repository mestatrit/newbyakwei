<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();
JspDataUtil.loadCompany(request);%>
<c:set var="meta_value" scope="request">
<meta name="keywords" content="${jsp_company.name}"/>
<meta name="description" content="${jsp_company.name}"/>
</c:set>
<c:set var="body_hk_content" scope="request">
	<table class="frame-3" cellpadding="0" cellspacing="0">
		<tr>
			<td class="l"><jsp:include page="cmp_left_inc.jsp"></jsp:include></td>
			<td class="mid">
				<div class="mid_con">
					<div class="mod">
						<c:set var="nav_2_short_content" scope="request"><a href="<%=path %>/cmpreview.do?companyId=${companyId }" class="nav-a"><hk:data key="view.company.review" /></a></c:set>
						<jsp:include page="../../inc/nav-2-short-cmp.jsp"></jsp:include>
					</div>
					<div class="mod">${cmp_content }</div>
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
						<div class="clr"></div>
					</div>
				</div>
			</td>
		</tr>
	</table>
</c:set>
<jsp:include page="../../inc/frame.jsp"></jsp:include>