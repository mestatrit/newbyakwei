<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="epp_other_value" scope="request">
<meta name="keywords" content="${cmpNav.name }|${o.name}"/>
<meta name="description" content="${cmpNav.name }|${o.name}"/>
</c:set>
<c:set var="html_body_content" scope="request">
<jsp:include page="../inc/publeftinc.jsp"></jsp:include>
<div class="page_r">
	<div class="mod">
		<h1 class="s1">${cmpNav.name }</h1>
		<div class="mod_content">
			<c:if test="${fn:length(list)==0}">
				<div class="divrow b"><hk:data key="epp.boxlist.nodata"/></div>
			</c:if>
			<c:if test="${fn:length(list)>0}">
				<c:forEach var="box" items="${list}" varStatus="idx">
					<div class="boxlist bdtm" onmouseover="this.className='boxlist bdtm bg3 mouseover'" onmouseout="this.className='boxlist bdtm'">
						<table cellpadding="0" cellspacing="0" class="nt">
							<tr>
								<td>
									<div class="name"><a class="b" href="<%=path %>/epp/web/box_view.do?companyId=${companyId}&boxId=${box.boxId}&navId=${navId}">${box.name }</a></div>
									<div class="remain"><hk:data key="epp.box.remain" arg0="${box.totalCount-box.openCount }"/></div>
									<div class="clr"></div>
									${box.simpleIntro }
									<div class="open"><input type="button" class="btn" value="<hk:data key="epp.box.openbox"/>"/></div>
								</td>
								<td width="60">
									<c:if test="${empty box.path}">
										<a href="<%=path %>/epp/web/box_view.do?companyId=${companyId}&boxId=${box.boxId}&navId=${navId}"><img alt="${box.name }" src="<%=path %>/webst4/img/baoxiang_s.png"/></a>
									</c:if>
									<c:if test="${not empty box.path}">
										<a href="<%=path %>/epp/web/box_view.do?companyId=${companyId}&boxId=${box.boxId}&navId=${navId}"><img alt="${box.name }" src="${box.pic }" width="60" height="60"/></a>
									</c:if>
								</td>
							</tr>
						</table>
					</div>
				</c:forEach>
			</c:if>
			<c:set var="page_url" scope="request"><%=path%>/epp/web/box.do?companyId=${companyId}&navId=${navId}</c:set>
			<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
		</div>
	</div>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>