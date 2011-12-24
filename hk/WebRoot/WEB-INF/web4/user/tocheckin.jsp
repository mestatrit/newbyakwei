<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.zuji"/></c:set>
<c:set var="html_body_content" scope="request">
<div>
	<div class="lcon">
		<div class="inner">
			<div class="mod">
				<div class="mod_title"><hk:data key="view2.user.cmpforcheckin"/></div>
				<div class="mod_content">
					<ul class="venuelist">
						<c:forEach var="u" items="${list}">
							<li onmouseover="this.className='bg2 vis_show';" onmouseout="this.className='';">
								<div class="cc">
									<div class="venue-body">
										<a href="/venue/${u.companyId }/" class="b split-r">${u.company.name }</a>
										<input class="btn vis" type="button" value="<hk:data key="view2.user.tocheckin"/>" onclick="tourl('/venue/${u.companyId}')"/>
										<br/>
										${u.company.pcity.name } 
									</div>
									<c:if test="${not empty u.company.headPath}">
										<div class="cmpimg">
											<a href="/venue/${u.companyId }/" class="b"><img src="${u.company.head60 }"></a>
										</div>
									</c:if>
									<div class="clr"></div>
								</div>
							</li>
						</c:forEach>
					</ul>
					<c:set var="url_rewrite" value="true" scope="request"/>
					<c:set var="page_url" scope="request">/user/tocheckin</c:set>
					<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	<div class="rcon">
		<div class="inner">
			<div class="mod">
				<input type="button" value="<hk:data key="view2.addvenueandtip"/>" onclick="tourl('/venue/search')" class="btn"/>
			</div>
			<div class="mod">
				<div class="mod_title"><hk:data key="view2.user.friend.where"/></div>
				<div class="mod_content">
					<c:forEach var="log" items="${friendloglist}">
					<div class="divrow bdtm">
						<a class="b" href="/user/${log.userId }/">${log.user.nickName }</a>
						<hk:data key="view2.user.on"/>
						<a class="b" href="/venue/${log.companyId }/">${log.company.name }</a> 
						<c:set var="createtime" scope="request" value="${log.createTime}"/>
						<span class="ruo"><%=JspDataUtil.outLabaTime(request,"createtime")%></span>
					</div>
				</c:forEach>
				</div>
			</div>
		</div>
	</div>
	<div class="clr"></div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>