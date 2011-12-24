<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">${tbItem.title }
</c:set><c:set var="html_head_value" scope="request">
<meta name="description" content="${tbItem.title }" />
<meta name="keywords"  content="${tbItem.title }|${tbItemCat.name }|${tbUser.location }|顾问家" />
<script type="text/javascript" src="${ctx_path }/webtb/js/hovertip.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	  window.setTimeout(hovertipInit, 1);
   });
</script>
</c:set><c:set var="html_body_content" scope="request">
<div class="pl">
	<div class="mod">
		<div class="mod_title">大家也在说<a name="cmt_area"></a></div>
		<div class="mod_content">
			<jsp:include page="itemcmtlist_inc.jsp"></jsp:include>
			<div>
			<c:set var="page_url" scope="request">${ctx_path}/tb/itemcmt_list?itemid=${itemid}</c:set>
			<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
<div class="pr">
	<div class="mod">
		<div class="fr">
		<script type="text/javascript">
		obj_width=265;
		</script>
		<jsp:include page="../inc/share.jsp"></jsp:include>
		</div>
		<div class="clr"></div>
	</div>
	<jsp:include page="loaditemdata_inc.jsp"></jsp:include>
</div>
<div class="clr"></div>
<script type="text/javascript" src="${ctx_path }/webtb/js/item.js"></script>
<script type="text/javascript">
var itemid=${itemid};
$(document).ready(function(){
	$('ul.itemcmtlist li').bind('mouseenter', function(){
		if($(this).attr('status')=="0"){
			$(this).addClass('enter');
		}
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
	});
});
<c:if test="${tocmt==1}">
window.location.hash="cmt_area";
</c:if>
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>