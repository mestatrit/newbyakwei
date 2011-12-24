<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();
JspDataUtil.loadHotTagList(request);%>
<c:if test="${fn:length(taglist)>0}">
<div class="mod">
	<div class="mod-1 r_mod2">
		<%=Hkcss2Util.rd_bg %>
		<div class="tit">热门标签</div>
		<div class="cont">
			<div class="atag"><c:forEach var="tag" items="${taglist}"><a href="<%=path %>/cmp_tagcmplist.do?tagId=${tag.tagId }&cityId=${cityId }">${tag.name }</a></c:forEach></div>
		</div>
		<%=Hkcss2Util.rd_bg_bottom %>
	</div>
	<div class="clr"></div>
</div>
</c:if>