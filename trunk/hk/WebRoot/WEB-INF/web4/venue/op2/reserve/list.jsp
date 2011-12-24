<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.web.util.HkWebUtil"%>
<%@page import="com.hk.bean.CmpReserve"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
HkWebUtil.loadCompany(request);
HkWebUtil.loadCmpActorForReserve(request);
%>
<c:set var="js_value" scope="request">
<link type="text/css" href="<%=path%>/webst4/css/smoothness/jquery-ui-1.7.custom.css" rel="stylesheet" />
<script type="text/javascript" src="<%=path%>/webst4/js/jquery-ui-1.7.custom.min.js"></script>
<script type="text/javascript" src="<%=path%>/webst4/js/ui.datepicker-zh-CN.js"></script>
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
<c:set var="mgr_body_content" scope="request">
<div class="mod">
<div class="mod_title">预约管理</div>
<div class="mod_content">
	<c:if test="${undo_count>0 || arrive_count>0 || doing_count>0}">
		<div class="divrow">
			<div class="alerts_notice">
				<c:if test="${undo_count>0}"><a class="split-r" href="<%=path %>/h4/op/venue/reserve_list.do?companyId=${companyId}&ignoredate=1&reserveStatus=<%=CmpReserve.RESERVESTATUS_DEF %>">有${undo_count }个未处理预约</a></c:if>
				<c:if test="${arrive_count>0}"><a class="split-r" href="<%=path %>/h4/op/venue/reserve_list.do?companyId=${companyId}&ignoredate=1&reserveStatus=<%=CmpReserve.RESERVESTATUS_ARRIVE %>">有${arrive_count }个已经到达预约</a></c:if>
				<c:if test="${doing_count>0}"><a class="split-r" href="<%=path %>/h4/op/venue/reserve_list.do?companyId=${companyId}&ignoredate=1&reserveStatus=<%=CmpReserve.RESERVESTATUS_DOING %>">有${doing_count }个正在服务的预约</a></c:if>
			</div>
		</div>
	</c:if>
	<div class="divrow">
	<form method="get" action="<%=path %>/h4/op/venue/reserve_list.do">
		<hk:hide name="companyId" value="${companyId}"/>
		日期：<hk:text name="day" value="${day}" clazz="text_yzm datepicker"/>
		工作人员：
		<hk:select name="actorId" checkedvalue="${actorId}">
			<hk:option value="0" data=""/>
			<c:forEach var="actor" items="${actorlist}"><hk:option value="${actor.actorId}" data="${actor.name}"/></c:forEach>
		</hk:select>
		预约状态：
		<hk:select name="reserveStatus" checkedvalue="${reserveStatus}">
			<hk:option value="<%=CmpReserve.RESERVESTATUS_DEF %>" data="已预约"/>
			<hk:option value="<%=CmpReserve.RESERVESTATUS_ARRIVE %>" data="已到达"/>
			<hk:option value="<%=CmpReserve.RESERVESTATUS_DOING %>" data="正在服务"/>
			<hk:option value="<%=CmpReserve.RESERVESTATUS_SUCCESS %>" data="服务完成"/>
			<hk:option value="<%=CmpReserve.RESERVESTATUS_CANCEL %>" data="已取消"/>
		</hk:select><br/>
		预约人姓名：<hk:text name="username" value="${username}" clazz="text2"/>
		预约人电话：<hk:text name="mobile" value="${mobile}" clazz="text2"/>
		<hk:submit value="搜索" clazz="btn"/>
	</form>
	</div>
		<c:if test="${fn:length(list)>0}">
			<ul class="reservelist">
				<c:forEach var="reserve" items="${list}">
					<li id="reserve_${reserve.reserveId }">
						<div class="head">
							<c:if test="${not empty reserve.cmpActor.picPath}">
								<a href="/cmp/${reserve.companyId }/actor/${reserve.cmpActor.actorId }"><img src="${reserve.cmpActor.pic150Url }" /></a>
							</c:if>
						</div>
						<div class="info">
							<a href="/cmp/${reserve.companyId }/actor/${reserve.cmpActor.actorId }" class="b">${reserve.cmpActor.name }</a>
							<a href="<%=path %>/h4/venue.do?companyId=${reserve.companyId}&mf=1">${reserve.company.name }</a>
							<br />
							<table class="nt all" cellpadding="0" cellspacing="0">
								<tr>
									<td align="right" width="90px" class="b">
										客户姓名
									</td>
									<td><span class="split-r">${reserve.username }</span><hk:data key="epp.cmpreserve.reservestatus${reserve.reserveStatus}" /></td>
								</tr>
								<tr>
									<td align="right" width="90px" class="b">
										预约时间
									</td>
									<td>
										<span class="split-r"><fmt:formatDate value="${reserve.reserveTime}" pattern="yyyy-MM-dd HH:mm" /></span>
										<span class="split-r">
											<a href="javascript:showop(${reserve.reserveId })">更改状态</a>
										</span>
										<div id="span_${reserve.reserveId}" class="split-r" style="display: none;">
											<hk:select oid="reserveStatus_${reserve.reserveId}" name="reserveStatus">
												<hk:option value="<%=CmpReserve.RESERVESTATUS_DEF %>" data="已预约"/>
												<hk:option value="<%=CmpReserve.RESERVESTATUS_ARRIVE %>" data="已到达"/>
												<hk:option value="<%=CmpReserve.RESERVESTATUS_DOING %>" data="正在服务"/>
												<hk:option value="<%=CmpReserve.RESERVESTATUS_SUCCESS %>" data="服务完成"/>
												<hk:option value="<%=CmpReserve.RESERVESTATUS_CANCEL %>" data="已取消"/>
											</hk:select>
											<input type="button" class="btn split-r" value="更改" onclick="opreservestatus(${reserve.reserveId})"/><a href="javascript:hideop(${reserve.reserveId })">取消</a>
										</div>
									</td>
								</tr>
								<c:if test="${fn:length(reserve.cmpSvrList)>0}">
									<tr>
										<td align="right" width="90px" class="b">
											所选服务
										</td>
										<td>
											<div class="usersvr">
												<c:forEach var="svr" items="${reserve.cmpSvrList}">
													<div class="divrow">${svr.name }</div>
												</c:forEach>
											</div>
										</td>
									</tr>
								</c:if>
							</table>
						</div>
						<div class="clr"></div>
					</li>
				</c:forEach>
			</ul>
		</c:if>
	<c:if test="${fn:length(list)==0}">
		<div class="nodata">本页没有数据</div>
	</c:if>
	<div>
		<c:set var="page_url" scope="request"><%=path %>/h4/op/reserve_list.do?companyId=${companyId}&actorId=${actorId}&reserveStatus=${reserveStatus}&mobile=${mobile}&username=${enc_username}</c:set>
		<jsp:include page="../../../inc/pagesupport_inc.jsp"></jsp:include>
	</div>
</div>
</div>
<script type="text/javascript">
function setsvr(actorId){
	tourl("<%=path%>/h4/op/venue/svr_addsvrforactor.do?companyId=${companyId}&actorId="+actorId);
}
$(document).ready(function(){
	$('ul.reservelist li').bind('mouseenter', function(){
		$(this).css('background-color','#ffffcc');
	}).bind('mouseleave', function(){
		$(this).css('background-color','#ffffff');
	});
});
function showop(reserveId){
	$('#span_'+reserveId).css('display','block');
}
function hideop(reserveId){
	$('#span_'+reserveId).css('display','none');
}
function opreservestatus(reserveId){
	var glass_id=addGlass('span_'+reserveId,true);
	var url='<%=path%>/h4/op/venue/reserve_updatereservestatus.do?companyId=${companyId}&reserveId='+reserveId+'&reserveStatus='+getObj('reserveStatus_'+reserveId).value;
	doAjax(url,function(data){
		refreshurl();
	});
}
</script>
</c:set>
<jsp:include page="../mgr.jsp"></jsp:include>