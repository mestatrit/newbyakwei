<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://www.opensymphony.com/oscache" prefix="cache"%>
<%String path = request.getContextPath();%>
<c:set scope="request" var="html_body_content">
	<div class="tpad">
		<div class="mod">
			<div class="mod_tit">我的预约</div>
			<div class="mod_content">
				<c:if test="${fn:length(list)==0}"><hk:data key="epp.cmpdata.nodatalist"/></c:if>
				<c:if test="${fn:length(list)>0}">
					<ul class="actorlist">
						<c:forEach var="reserve" items="${list}">
							<li>
								<div class="head">
									<c:if test="${not empty reserve.cmpActor.picPath}">
									<a href="/actor/${companyId }/${reserve.cmpActor.actorId }/"><img src="${reserve.cmpActor.pic240Url }"/></a>
									</c:if>
								</div>
								<div class="info">
									<a href="/actor/${companyId }/${reserve.cmpActor.actorId }/" class="b">${reserve.cmpActor.name }</a><br/>
									<table class="nt all" cellpadding="0" cellspacing="0">
										<tr>
											<td align="right" width="90px" class="b">预约时间</td>
											<td>
												<span class="split-r"><fmt:formatDate value="${reserve.reserveTime}" pattern="yyyy-MM-dd HH:mm"/></span>
												<c:if test="${!reserve.expire}">
													<c:if test="${!reserve.cancel && !reserve.success}">
														<a class="split-r" href="javascript:cancelreserve(${reserve.reserveId })">取消预约</a>
														<a class="split-r" href="javascript:update(${reserve.reserveId },${reserve.actorId })">修改预约</a>
													</c:if>
												</c:if>
												<c:if test="${reserve.cancel || reserve.success}">
													<hk:data key="epp.cmpreserve.reservestatus${reserve.reserveStatus}"/>
												</c:if>
											</td>
										</tr>
										<c:if test="${fn:length(reserve.cmpSvrList)>0}">
											<tr>
												<td align="right" width="90px" class="b">预约的服务</td>
												<td>
													<div class="usersvr">
														<ul class="datalist">
															<c:forEach var="svr" items="${reserve.cmpSvrList}">
															<li>${svr.name }</li>
															</c:forEach>
														</ul>
													</div>
												</td>
											</tr>
										</c:if>
									</table>
									<div align="right">
									</div>
								</div>
								<div class="clr"></div>
							</li>
						</c:forEach>
					</ul>
				</c:if>
				<div>
				<c:set var="page_url" scope="request"><%=path%>/epp/web/op/reserve.do?companyId=${companyId}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">
function cancelreserve(reserveId){
	if(window.confirm('确实要取消此预约？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/reserve_cancelreserve.do?companyId=${companyId}&reserveId="+reserveId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function update(reserveId,actorId){
	tourl("<%=path%>/epp/web/op/reserve_sel.do?companyId=${companyId}&update=1&actorId="+actorId+"&reserveId="+reserveId+"&return_url="+encodeLocalURL());
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>