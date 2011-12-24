<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">宝箱市场</c:set>
<c:set var="body_hk_content" scope="request">
	<table class="frame-3" cellpadding="0" cellspacing="0">
		<tr>
			<td class="l">
				<jsp:include page="../inc/userleftnav_inc.jsp"></jsp:include>
			</td>
			<td class="mid">
				<div class="mid_con">
					<div class="mod">
						<c:set var="nav_2_short_content" scope="request"><li><a class="nav-a" href="<%=path %>/box_list.do">宝箱市场</a></li></c:set>
						<jsp:include page="../inc/nav-2-short2.jsp"></jsp:include>
					</div>
					<div class="mod">
						<c:set var="page_url" scope="request"><%=path %>/box_list.do?v=1</c:set>
						<jsp:include page="boxlist_inc.jsp"></jsp:include>
					</div>
				</div>
			</td>
			<td class="r">
				<div class="f_r"></div>
			</td>
		</tr>
	</table>
	<hk:form oid="openfrm" action="/op/openbox.do" onsubmit="return openbox()" clazz="hide" target="hideframe">
		<hk:hide name="boxId" value="${boxId}"/>
		<hk:hide name="ajax" value="1"/>
	</hk:form>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>