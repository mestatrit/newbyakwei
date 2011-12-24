<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="epp_other_value" scope="request">
<meta name="keywords" content="<hk:data key="epp.frlink"/>|${o.name}"/>
<meta name="description" content="<hk:data key="epp.frlink"/>|${o.name}"/>
</c:set>
<c:set var="html_body_content" scope="request">
<jsp:include page="../inc/publeftinc.jsp"></jsp:include>
<div class="page_r">
	<div class="mod">
		<h1 class="s1"><hk:data key="epp.frlink"/></h1>
		<div class="mod_content">
			<c:forEach var="n" items="${list}">
				<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg3'" onmouseout="this.className='divrow bdtm'">
					<span class="b split-r">${n.name }</span>
					<a target="_blank" href="http://${n.url }">${n.url }</a>
				</div>
			</c:forEach>
			<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg3'" onmouseout="this.className='divrow bdtm'">
				<span class="b split-r">火酷网</span>
				<a target="_blank" href="http://www.huoku.com">www.huoku.com</a>
			</div>
		</div>
	</div>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>