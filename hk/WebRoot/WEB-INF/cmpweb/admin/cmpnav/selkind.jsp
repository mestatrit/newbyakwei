<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%><c:set var="html_title" scope="request">${o.name}</c:set><c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">${cmpNav.name } 选择专业
			<a class="more" href="${denc_return_url }">返回</a>
		</div>
		<div class="mod_content">
			<c:if test="${parent!=null}">
			<span style="font-weight: bold;font-size: 18px;">${parent.name }</span>
			<a class="more" href="<%=path %>/epp/web/op/webadmin/admincmpnav_selkind.do?companyId=${companyId}&parentId=${parent.parentId }&oid=${oid }&return_url=${return_url}">上一级</a>
			</c:if>
			<c:if test="${fn:length(list)==0}"><div><hk:data key="epp.cmpdata.nodatalist"/></div></c:if>
			<c:if test="${fn:length(list)>0}">
				<ul class="datalist">
					<c:forEach var="kind" items="${list}">
						<li>
							<span class="f_l" style="width:400px">
								<a href="<%=path %>/epp/web/op/webadmin/admincmpnav_selkind.do?companyId=${companyId}&parentId=${kind.kindId }&oid=${oid }&return_url=${return_url}">${kind.name }</a>
							</span>
							<span class="f_l" style="width:100px">
								<a href="<%=path %>/epp/web/op/webadmin/admincmpnav_selkind.do?companyId=${companyId}&kindId=${kind.kindId }&oid=${oid }&ch=1&return_url=${return_url}">选择</a>
							</span>
							<div class="clr"></div>
						</li>
					</c:forEach>
				</ul>
			</c:if>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('.datalist li').each(function(i){
		$(this).bind('mouseover', function(){
			$(this).css('background-color', '#ffffcc');
		}).bind('mouseout', function(){
			$(this).css('background-color', '#ffffff');
		});
	});
});
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>