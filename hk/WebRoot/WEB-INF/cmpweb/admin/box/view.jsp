<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">宝箱</div>
		<div class="mod_content">
			<div class="hang">
				<div class="divrow">
				<a href="<%=path %>/epp/web/op/webadmin/box_uplist.do?companyId=${companyId}&boxId=${boxId}&navoid=${navoid }">管理兑换</a>
				</div>
				<div class="divrow">
				宝箱名称：
				${box.name}
				</div>
				<div class="divrow">
					地区：
					<c:if test="${box.cityId>0}">${zoneName }</c:if>
					<c:if test="${box.cityId==0}">全球</c:if>
				</div>
				<div class="divrow">
					宝箱介绍：
					${box.intro}
				</div>
				<div class="divrow">
					宝箱数量：
					${box.totalCount}
				</div>
				<div class="divrow">
					剩余数量：
					${box.totalCount-box.openCount}
				</div>
				<div class="divrow">
					时间：
					<span class="split-r"><fmt:formatDate value="${box.beginTime}" pattern="yyyy-MM-dd HH:mm"/> 开始 </span>
					<fmt:formatDate value="${box.endTime}" pattern="yyyy-MM-dd HH:mm"/> 结束
				</div>
				<div class="divrow">
					开箱限制：
					<c:if test="${box.pretype==0}">不限制</c:if>
					<c:if test="${box.pretype!=0}">${box.precount }个/每人每${boxPretype.name }</c:if>
				</div>
				<div class="divrow">
					奖品列表：
					<c:if test="${!overdue && pause }">
					<hk:a href="/epp/web/op/webadmin/box_createprize.do?companyId=${companyId }&boxId=${boxId}&navoid=${navoid }">添加新奖品</hk:a>
					</c:if>
					<br/>
					<div style="margin-left: 50px;">
						<c:forEach var="p" items="${list}" varStatus="idx">
							<div class="divrow">
								<c:if test="${not empty p.path}">
									<img src="${p.h_0Pic }"/>
								</c:if>
								<span class="split-r">${p.name}
								</span>
								<span class="split-r">${p.pcount}个
								</span>
								<span class="split-r">剩余${p.remain }个
								</span>
								<c:if test="${(!overdue)}">
									<a clazz="split-r" href="javascript:toupdateprize(${p.prizeId})">改</a>
									<c:if test="${p.pcount==p.remain}">
										<hk:a clazz="split-r" href="javascript:delprize(${p.prizeId})">删</hk:a>
									</c:if>
								</c:if>
								<c:if test="${not empty p.path}">
									<hk:a clazz="split-r" href="/epp/web/op/webadmin/box_selprizepic.do?companyId=${companyId }&prizeId=${p.prizeId}&boxId=${boxId }&navoid=${navoid }">更换图片</hk:a>
								</c:if>
								<c:if test="${empty p.path}">
									<hk:a clazz="split-r" href="/epp/web/op/webadmin/box_selprizepic.do?companyId=${companyId }&prizeId=${p.prizeId}&boxId=${boxId }&navoid=${navoid }">添加图片</hk:a>
								</c:if>
							</div>
						</c:forEach>
					</div>
				</div>
				<div>
					<c:if test="${normal && !overdue}">
						<input type="button" class="btn split-r" value="停止" onclick="pausebox()"/>
						<input type="button" class="btn" value="作废" onclick="stopbox()"/>
					</c:if>
					<c:if test="${pause && !overdue}">
						<input type="button" class="btn split-r" value="运行" onclick="runbox()"/>
						<input type="button" class="btn" value="作废" onclick="stopbox()"/>
					</c:if>
					<c:if test="${!overdue }">
						<input type="button" class="btn" value="修改箱子信息" onclick="toupdate()"/>
					</c:if>
					<div style="padding-top: 20px">
						<a class="more2" href="<%=path %>/epp/web/op/webadmin/box.do?companyId=${companyId }&navoid=${navoid }">返回</a>
					</div>
				</div>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var running=${!box.pause};
function delprize(prizeId){
	if(running){
		if(window.confirm("要删除宝箱奖品，需要暂停当前宝箱。确定要暂停？")){
			pausebox();
		}
	}
	else{
		if(window.confirm("确定要删除？")){
			$.ajax({
				type:"POST",
				url:"<%=path%>/epp/web/op/webadmin/box_delprize.do?companyId=${companyId}&boxId=${boxId}&prizeId="+prizeId,
				cache:false,
		    	dataType:"html",
				success:function(data){
					refreshurl();
				}
			});
		}
	}
}
function toupdateprize(prizeId){
	if(running){
		if(window.confirm("要修改宝箱奖品信息，需要暂停当前宝箱。确定要暂停？")){
			pausebox();
		}
	}
	else{
		tourl("<%=path%>/epp/web/op/webadmin/box_updateprize.do?companyId=${companyId }&boxId=${boxId }&navoid=${navoid }&prizeId="+prizeId);
	}
}
function toupdate(){
	if(running){
		if(window.confirm("要修改宝箱信息，需要暂停当前宝箱。确定要暂停？")){
			tourl("<%=path%>/epp/web/op/webadmin/box_update.do?companyId=${companyId}&boxId=${boxId}&navoid=${navoid }");
		}
	}
	else{
		tourl("<%=path%>/epp/web/op/webadmin/box_update.do?companyId=${companyId}&boxId=${boxId}&navoid=${navoid }");
	}
}
function stopbox(){
	if(window.confirm("确实要作废此宝箱？")){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/box_stop.do?companyId=${companyId}&boxId=${boxId}",
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function pausebox(){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/box_pause.do?companyId=${companyId}&boxId=${boxId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function runbox(){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/box_cont.do?companyId=${companyId}&boxId=${boxId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>