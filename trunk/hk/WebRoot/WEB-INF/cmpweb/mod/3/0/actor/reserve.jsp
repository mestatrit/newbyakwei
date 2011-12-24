<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://www.opensymphony.com/oscache" prefix="cache"%>
<%String path = request.getContextPath();%>
<c:set var="epp_other_value" scope="request">
<link type="text/css" href="<%=path%>/cmpwebst4/css/smoothness/jquery-ui-1.7.custom.css" rel="stylesheet" />
<script type="text/javascript" src="<%=path%>/cmpwebst4/js/jquery-ui-1.7.custom.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(function(){
		$('.datepicker').datepicker({
			numberOfMonths: 1,
			showButtonPanel: false,
			dateFormat: 'yy-mm-dd',
			onSelect: function(dateText){
				loadreserveinfo(${actorId},dateText);
				$('#frm_day').attr('value',dateText);
			}
		});
	});
});
</script>
</c:set>
<c:set scope="request" var="html_body_content">
<div class="tpad">
	<div class="p_l">
	<div id="svrintro">
	</div>
	<div id="svrphoto">
	</div>
	</div>
	<div class="p_r">
	<c:if test="${update==1}">
		<c:set var="form_action"><%=path %>/epp/web/op/reserve_updatereserve.do</c:set>
	</c:if>
	<c:if test="${!(update==1)}">
		<c:set var="form_action"><%=path %>/epp/web/op/reserve_createreserve.do</c:set>
	</c:if>
		<form id="reservefrm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
			<div class="mod">
				<div class="mod_tit">
				</div>
				<div class="cont">
					<div style="width: 400px">
						<a name="hidereserve"></a>
						<hk:hide name="actorId" value="${actorId}"/>
						<hk:hide name="companyId" value="${companyId}"/>
						<hk:hide name="reserveId" value="${reserveId}"/>
						<span class="b">选择服务</span><br/>
						<ul class="svrlist">
							<c:if test="${fn:length(svrreflist)>0}">
								<c:forEach var="svrref" items="${svrreflist}">
									<li id="svr_${svrref.oid }" svrid="${svrref.svrId }" setid="${svrref.cmpSvr.photosetId }">
										<span class="name">
										<hk:checkbox name="svrId" value="${svrref.svrId }" checkedvalues="${cmpReserve.svrdata}"/>
										${svrref.cmpSvr.name }</span>
										<span class="price">￥${svrref.cmpSvr.price }</span>
										<div class="clr"></div>
									</li>
								</c:forEach>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
			<div class="mod">
				<h1><a href="/actor/${companyId }/${cmpActor.actorId }">${cmpActor.name }</a>的预约时刻表</h1>
				<div class="cont">
					<input id="frm_day" type="hidden" name="day"/>
					<div id="user_date" class="divrow">
					<fmt:formatDate var="day" pattern="yyyy-MM-dd" value="<%=new Date() %>"/>
						选择日期：<input id="seldate" type="text" name="datestr" class="text datepicker" value="${day }"/>
						<c:if test="${(update==1)}">
						<input type="button" class="btn" value="返回" onclick="tourl('${denc_return_url}')"/>
						</c:if>
					</div>
					<div id="user_time" class="divrow" style="display: none;">
						您选择的时间：
						<input id="begint" name="begintime" type="text" class="text2" readonly="readonly"/> 
						到
						<input id="endt" name="endtime" type="text" class="text2" readonly="readonly"/> 
						<c:if test="${update==1}">
						<hk:submit value="保存" clazz="btn"/>
						</c:if>
						<c:if test="${!(update==1)}">
						<hk:submit value="预约" clazz="btn"/>
						</c:if>
						<div id="_dateinfo" class="infowarn"></div>
					</div>
					<div class="divrow">
						<span class="notice_t">点击绿色区域选择时间并预约</span><br/>
						
					</div>
					<div id="dateinfo" style="height: 810px;">
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="clr"></div>
</div>
<script type="text/javascript">
function loadreserveinfo(actorId,datestr){
	$('#dateinfo').html('数据加载中 ... ...');
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/reserve_loadinfoforreserve.do?companyId=${companyId}&actorId="+actorId+"&datestr="+datestr,
		cache:false,
    	dataType:"html",
		success:function(data){
			$('#dateinfo').html(data);
			$('div.timelist div.free').bind('mouseover', function(){
				$(this).css('position', 'relative');
				$('#tip' + $(this).attr('id')).css("display", "block");
			}).bind('mouseout', function(){
				$(this).css('position', 'static');
				$('#tip' + $(this).attr('id')).css("display", "none");
			}).bind('click', function(){
				var hour=$(this).attr('hour');
				var min=$(this).attr('min');
				var time=hour+":"+min;
				$('#begint').attr('value',time);
				var date=new Date();
				date.setHours(hour);
				date.setMinutes(min);
				var longtime=date.getTime();
				longtime=longtime+1800*1000;
				date.setTime(longtime);
				$('#endt').attr('value',date.getHours()+":"+date.getMinutes());
				$('#user_time').css('display','block');
				$('#user_time').effect('highlight',{},3000,function(){});
			});
		}
	});
}
var current_svr_id = -1;
var svrinfo=new Array();
<c:forEach var="svrref" items="${svrreflist}" varStatus="idx">
svrinfo[${svrref.svrId }]="${svrref.cmpSvr.intro }";
</c:forEach>
$(document).ready(function(){
	$('ul.svrlist li').bind('mouseover', function(){
		$(this).addClass('cur');
	}).bind('mouseout', function(){
		$(this).removeClass('cur');
	}).bind('click', function(){
		if (current_svr_id != -1) {
			$('#' + current_svr_id).removeClass('cur2');
		}
		current_svr_id = $(this).attr('id');
		currentsvrid=$(this).attr('svrid');
		$(this).addClass('cur2');
		$('#info_txt').html('service info introduce');
		showsvrinfo(currentsvrid);
		loadsvrimg($(this).attr('setid'));
	});
});
function showsvrinfo(svrId){
	$('#svrintro').html(svrinfo[svrId]);
}
function loadsvrimg(setId){
	$('#svrphoto').html('');
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/svr_loadphotoset.do?companyId=${companyId}&setId="+setId,
		cache:false,
    	dataType:"html",
		success:function(data){
			if(data=='0'){
				return;
			}
			$('#svrphoto').html(data);
		}
	});
}
function subfrm(frmid){
	showGlass(frmid);
	setHtml('_dateinfo','');
	return true;
}
function createok(error,msg,v){
	tourl('<%=path %>/epp/web/op/reserve_view.do?companyId=${companyId}&reserveId='+v);
}
function createerror(error,msg,v){
	setHtml('_dateinfo',msg);
	hideGlass();
}
loadreserveinfo(${actorId},'${day}');
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>