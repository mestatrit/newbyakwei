<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="js_value" scope="request">
<link type="text/css" href="<%=path%>/cmpwebst4/css/smoothness/jquery-ui-1.7.custom.css" rel="stylesheet" />
<script type="text/javascript" src="<%=path%>/cmpwebst4/js/jquery-ui-1.7.custom.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(function(){
		$('.datepicker').datepicker({
			numberOfMonths: 1,
			showButtonPanel: false,
			dateFormat: 'yy-mm-dd'
		});
	});
});
</script>
</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">预约管理</div>
		<div class="mod_content">
			<div class="divrow">
				<form method="get" action="<%=path %>/epp/web/op/webadmin/reserve.do">
					<hk:hide name="companyId" value="${companyId}"/>
					工作人员：<hk:text name="name" clazz="text2" value="${name}"/>
					日期：<hk:text name="day" clazz="text2 datepicker" value="${day}"/>
					<hk:submit clazz="btn" value="查询"/>
				</form>
			</div>
			<c:if test="${fn:length(list)>0}">
				<ul class="datalist">
					<li>
						<div class="f_l b" style="width:150px">
							工作人员
						</div>
						<div class="f_l b" style="width:100px">
							状态
						</div>
						<div class="f_l b" style="width:150px">
							预约时间
						</div>
						<div class="clr"></div>
					</li>
					<c:forEach var="reserve" items="${list}">
						<li>
							<div class="f_l" style="width:150px">
								${reserve.cmpActor.name }
							</div>
							<div class="f_l" style="width:100px">
								<hk:data key="epp.cmpreserve.reservestatus${reserve.reserveStatus}"/>
							</div>
							<div class="f_l" style="width:150px">
								<fmt:formatDate value="${reserve.reserveTime}" pattern="yyyy-MM-dd HH:mm"/>
							</div>
							<div class="f_l">
							<a href="javascript:view(${reserve.reserveId })">查看</a>
							</div>
							<div class="clr"></div>
						</li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">本页没有预约数据</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/reserve.do?companyId=${companyId}&name=${enc_name}&day=${day}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
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
function view(reserveId){
	tourl("<%=path%>/epp/web/op/webadmin/reserve_view.do?companyId=${companyId}&reserveId="+reserveId+"&return_url="+encodeLocalURL());
}
function setwork(reserveId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/reserve_setwork.do?companyId=${companyId}&reserveId="+reserveId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function setworkok(reserveId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/reserve_setworkok.do?companyId=${companyId}&reserveId="+reserveId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>