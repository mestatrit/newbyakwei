<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
	<c:if test="${pageSupport.pageCount>pageSupport.page}">
		<div class="page2">
			<div id="next_page_div" class="p_l">
				<a href="${pagesupport_inc_page_url }&page=${pageSupport.page+1 }" onclick="load_next_page();return false;"><hk:data key="view.moreresult" /></a><span id="loading"></span>
			</div>
			<div class="p_r">
				<a class="gototop" href="#"><hk:data key="view.return_to_top" /> <img src="<%=path%>/webst3/img/goto-top.gif" /> </a>
			</div>
			<div class="clr"></div>
		</div>
	</c:if>
	<c:if test="${hasmore}">
		<div class="page2">
			<div id="next_page_div" class="p_l">
				<a href="${pagesupport_inc_page_url }&page=${page+1 }" onclick="load_next_page();return false;"><hk:data key="view.moreresult" /></a><span id="loading"></span>
			</div>
			<div class="p_r">
				<a class="gototop" href="#"><hk:data key="view.return_to_top" /> <img src="<%=path%>/webst3/img/goto-top.gif" /> </a>
			</div>
		</div>
		<div class="clr"></div>
	</c:if>
<script type="text/javascript">
var page_url="${pagesupport_inc_page_url }&ajax=1&page=";
<c:if test="${pageSupport!=null}">var page=${pageSupport.page+1};</c:if>
<c:if test="${hasmore}">var page=${page+1};</c:if>
function load_next_page(){
	setHtml("loading",'<img src="<%=path %>/webst3/img/spinner_16x16_blk.gif"/>');
	$.ajax({
		type:"POST",
		url:page_url+page,
		cache:false,
    	dataType:"html",
		success:function(data){
			$('#${append_con}').append(data);
			setHtml("loading","");
		}
	});
}
</script>