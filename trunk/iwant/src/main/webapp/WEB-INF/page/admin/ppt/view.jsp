<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request"><hk:value value="${project.name}"/></c:set>
<c:set scope="request" var="mgr_body_content">
<div class="mod">
	<div class="mod_title">
		查看项目 - <hk:value value="${project.name}"/>
	</div>
	<div class="mod_content">
		<div>
			<table class="formt" cellpadding="0" cellspacing="0">
				<tr>
					<td width="90" align="right">名称</td>
					<td>
						<hk:value value="${project.name }" onerow="true"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right"></td>
					<td>
						<a href="javascript:toupdate()" class="split-r">修改</a>
						<a href="${appctx_path }/mgr/ppt_back.do?projectid=${ppt.projectid}">返回</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<div class="mod">
	<div class="mod_title">
		Slide
	</div>
	<div class="mod_content">
		<ul class="rowlist">
			<c:forEach var="slide" items="${list }" varStatus="idx">
			<li>
				<div class="f_l" style="width: 150px;margin-right: 20px">
					<a href="javascript:viewslide(${slide.slideid})"><hk:value value="${slide.title }"/></a>
				</div>
				<div class="f_l">
					<a href="javascript:toupdate(${slide.slideid })" class="split-r" id="op_update_${slide.slideid }">修改</a>
					<a href="javascript:opdel(${slide.slideid })" class="split-r" id="op_delete_${slide.slideid }">删除</a>
				</div>
				<div class="clr"></div>
			</li>
			</c:forEach>
			<c:if test="${(fn:length(list)==0) }">
				<li><div class="nodata">本页没有数据</div></li>
			</c:if>
		</ul>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).addClass('enter');
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
	});
});
function toupdate(projectid){
	tourl('${appctx_path}/mgr/ppt_update.do?pptid=${pptid}&back_url='+encodeLocalURL());
}
</script>
</c:set><jsp:include page="../inc/mgrframe.jsp"></jsp:include>