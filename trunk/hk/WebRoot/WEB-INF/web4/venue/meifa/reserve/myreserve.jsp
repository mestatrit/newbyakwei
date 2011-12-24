<%@ page language="java" pageEncoding="UTF-8"%><%@page import="java.util.Date"%>
<%@page import="com.hk.bean.CmpReserve"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">我的预约</c:set>
<c:set var="html_body_content" scope="request">
	<div class="mod">
		<div class="<c:if test="${reserveStatus==RESERVESTATUS_DEF}">active_tips_tab</c:if><c:if test="${reserveStatus!=RESERVESTATUS_DEF}">inactive_tips_tab</c:if>">
			<a href="<%=path %>/h4/op/reserve_myreserve.do">我的预约</a>
		</div>
		<div class="<c:if test="${reserveStatus==RESERVESTATUS_SUCCESS}">active_tips_tab</c:if><c:if test="${reserveStatus!=RESERVESTATUS_SUCCESS}">inactive_tips_tab</c:if>">
			<a href="<%=path %>/h4/op/reserve_myreserve.do?reserveStatus=<%=CmpReserve.RESERVESTATUS_SUCCESS %>">完成的预约</a>
		</div>
		<div class="<c:if test="${reserveStatus==RESERVESTATUS_CANCEL}">active_tips_tab</c:if><c:if test="${reserveStatus!=RESERVESTATUS_CANCEL}">inactive_tips_tab</c:if>">
			<a href="<%=path %>/h4/op/reserve_myreserve.do?reserveStatus=<%=CmpReserve.RESERVESTATUS_CANCEL %>">取消的预约</a>
		</div>
		<div class="<c:if test="${un==true}">active_tips_tab</c:if><c:if test="${un==false}">inactive_tips_tab</c:if>">
			<a href="<%=path %>/h4/op/reserve_myreserve.do?reserveStatus=0&un=true">作废的预约</a>
		</div>
		<div class="clr"></div>
		<div class="listbox">
			<c:if test="${fn:length(list)==0}">
				<hk:data key="epp.cmpdata.nodatalist" />
			</c:if>
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
										<td>${reserve.username }</td>
									</tr>
									<tr>
										<td align="right" width="90px" class="b">
											预约时间
										</td>
										<td>
											<span class="split-r"><fmt:formatDate value="${reserve.reserveTime}" pattern="yyyy-MM-dd HH:mm" /></span>
											<span class="split-r">
												<c:if test="${reserve.cancel || reserve.success}">
													<hk:data key="epp.cmpreserve.reservestatus${reserve.reserveStatus}" />
												</c:if>
											</span>
											<span id="op_reserve_${reserve.reserveId }" style="visibility: hidden;">
												<c:if test="${!reserve.expire}">
													<c:if test="${!reserve.cancel && !reserve.success}">
														<a class="split-r" href="javascript:cancelreserve(${reserve.reserveId })">取消预约</a>
														<a class="split-r" href="javascript:update(${reserve.companyId},${reserve.reserveId },${reserve.actorId })">修改预约</a>
													</c:if>
												</c:if>
												<c:if test="${reserve.expire || reserve.success || reserve.cancel}">
													<a href="<%=path %>/h4/op/reserve.do?companyId=${reserve.companyId}&actorId=${reserve.actorId}&repeat_reserveId=${reserve.reserveId}">再次预约</a>
												</c:if>
											</span>
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
			<div>
				<c:set var="page_url" scope="request"><%=path%>/h4/op/reserve_myreserve.do?reserveStatus=${reserveStatus}&un=${un}</c:set>
				<jsp:include page="../../../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
<script type="text/javascript">
function cancelreserve(reserveId){
	if(window.confirm('确实要取消此预约？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/h4/op/reserve_cancelreserve.do?companyId=${companyId}&reserveId="+reserveId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function update(companyId,reserveId,actorId){
	tourl("<%=path%>/h4/op/reserve.do?companyId="+companyId+"&update=1&actorId="+actorId+"&reserveId="+reserveId);
}
$(document).ready(function(){
	$('ul.reservelist li').bind('mouseenter', function(){
		$(this).css('background-color', '#ffffcc');
		$('#op_'+$(this).attr('id')).css('visibility','visible');
	}).bind('mouseleave', function(){
		$(this).css('background-color', '#ffffff');
		$('#op_'+$(this).attr('id')).css('visibility','hidden');
	});
});
</script>
</c:set><jsp:include page="../../../inc/frame.jsp"></jsp:include>