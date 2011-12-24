<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.zuji"/></c:set>
<c:set var="html_body_content" scope="request">
<div>
	<div class="lcon">
		<div class="inner">
			<div style="font-size: 20px" class="bdtm b">
			<c:if test="${all==0}">
				<c:if test="${sys_zone_pcity!=null}"><hk:data key="view2.current_city"/> | </c:if>
				<a href="/venues/all/"><hk:data key="view2.venue.all"/></a>
			</c:if>
			<c:if test="${all==1}">
				<c:if test="${sys_zone_pcity!=null}"><a href="/venues"><hk:data key="view2.current_city"/></a> | </c:if>
				<hk:data key="view2.venue.all"/>
			</c:if>
			</div>
			<ul class="venuelist">
				<c:forEach var="c" items="${list}">
					<li onmouseover="this.className='bg2';" onmouseout="this.className='';">
						<div class="cc">
							<div class="venue-body">
								<a href="/venue/${c.companyId }/" class="b">${c.name }</a><br/>
								${c.pcity.name } ${c.addr }
							</div>
							<c:if test="${not empty c.headPath}">
								<div class="cmpimg">
									<a href="/venue/${c.companyId }/" class="b"><img src="${c.head60 }"></a>
								</div>
							</c:if>
							<div class="clr"></div>
						</div>
					</li>
				</c:forEach>
			</ul>
			<c:set var="url_rewrite" value="true" scope="request"/>
			<c:if test="${all==1}">
				<c:set var="page_url" scope="request">/venues/all</c:set>
			</c:if>
			<c:if test="${all==0}">
				<c:set var="page_url" scope="request">/venues</c:set>
			</c:if>
			<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
		</div>
	</div>
	<div class="rcon">
		<div class="inner">
			<div class="mod">
				<a href="javascript:toauthcmp()">申请认证足迹</a>
			</div>
			<div class="mod">
				<input type="button" value="<hk:data key="view2.addvenueandtip"/>" onclick="tourl('/venue/search')" class="btn"/>
			</div>
			<jsp:include page="../inc/rcon_usertip_inc.jsp"></jsp:include>
		</div>
	</div>
	<div class="clr"></div>
</div>
<script type="text/javascript">
function toauthcmp(){
	tourl('<%=path %>/h4/op/authcmp.do?return_url='+encodeLocalURL());
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>