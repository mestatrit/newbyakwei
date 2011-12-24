<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:if test="${fn:length(bizCircleList)>0}">
	<div class="mod">
		<div class="mod-1">
			<%=Hkcss2Util.rd_bg %>
			<div class="tit"><hk:data key="view.company.bizcircle"/></div>
			<div class="cont">
				<div class="pad">
					<ul class="userset text_14">
						<c:forEach var="bc" items="${bizCircleList}">
							<li>
								<a class="n1" href="<%=path %>/cmp_bzcmplist.do?circleId=${bc.circleId }&parentId=${parentId }&kindId=${kindId }&${url_add }">${bc.name } <span>(${bc.cmpCount })</span> </a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<%=Hkcss2Util.rd_bg_bottom %>
		</div>
		<div class="clr"></div>
	</div>
</c:if>