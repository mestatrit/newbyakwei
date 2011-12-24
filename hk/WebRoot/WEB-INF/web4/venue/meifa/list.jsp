<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">美容美发</c:set>
<c:set var="js_value" scope="request">
<meta name="keywords" content="美容美发|<hk:data key="view2.website.title"/>" />
</c:set>
<c:set var="html_body_content" scope="request">
<div>
	<div class="lcon">
		<div class="inner">
			<div style="font-size: 20px" class="bdtm b">
			<c:if test="${all==0}">
				<c:if test="${sys_zone_pcity!=null}"><hk:data key="view2.current_city"/> | </c:if>
				<a href="/venue/meifa/all"><hk:data key="view2.venue.all"/></a>
			</c:if>
			<c:if test="${all==1}">
				<c:if test="${sys_zone_pcity!=null}"><a href="/venue/meifa"><hk:data key="view2.current_city"/></a> | </c:if>
				<hk:data key="view2.venue.all"/>
			</c:if>
			</div>
			<ul class="venuelist">
				<c:forEach var="c" items="${list}">
					<li idx="${c.companyId }">
						<div id="cmp_reserve_${c.companyId }" class="svr_reserve2" style="top: 20%;">
							<input type="button" class="btn" value="预约" onclick="tourl('/cmp/${c.companyId}/actor/list')"/>
						</div>
						<div class="cc">
							<div class="venue-body">
								<a href="<%=path %>/h4/venue.do?companyId=${c.companyId }&mf=1" class="b">${c.name }</a><br/>
								${c.pcity.name } ${c.addr }
							</div>
							<c:if test="${not empty c.headPath}">
								<div class="cmpimg">
									<a href="<%=path %>/h4/venue.do?companyId=${c.companyId }&mf=1" class="b"><img src="${c.head60 }"></a>
								</div>
							</c:if>
							<div class="clr"></div>
						</div>
					</li>
				</c:forEach>
			</ul>
			<c:set var="url_rewrite" value="true" scope="request"/>
			<c:if test="${all==1}">
				<c:set var="page_url" scope="request">/venue/meifa/all</c:set>
			</c:if>
			<c:if test="${all==0}">
				<c:set var="page_url" scope="request">/venue/meifa</c:set>
			</c:if>
			<jsp:include page="../../inc/pagesupport_inc.jsp"></jsp:include>
		</div>
	</div>
	<div class="rcon">
		<div class="inner">
			<div class="mod">
			</div>
		</div>
	</div>
	<div class="clr"></div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.venuelist li').bind('mouseenter', function(){
		$(this).css('background-color','#ffffcc');
		$('#cmp_reserve_'+$(this).attr('idx')).css('display','block');
	}).bind('mouseleave', function(){
		$(this).css('background-color','#ffffff');
		$('#cmp_reserve_'+$(this).attr('idx')).css('display','none');
	});
});
</script>
</c:set><jsp:include page="../../inc/frame.jsp"></jsp:include>