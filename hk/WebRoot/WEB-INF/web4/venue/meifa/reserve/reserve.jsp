<%@ page language="java" pageEncoding="UTF-8"%><%@page import="java.util.Date"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">选择服务并进行预约 - ${company.name}</c:set>
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
			dateFormat: 'yy-mm-dd',
			onSelect: function(dateText){
				loadreserveinfo(${actorId},dateText);
				$('#frm_day').attr('value',dateText);
			}
		});
	});
});
</script>
<style type="text/css">
body{
overflow-y: scroll;
}
</style>
</c:set>
<c:set var="html_body_content" scope="request">
<c:if test="${update==1}">
	<c:set var="form_action"><%=path%>/h4/op/reserve_updatereserve.do</c:set>
</c:if>
<c:if test="${!(update==1)}">
	<c:set var="form_action"><%=path%>/h4/op/reserve_createreserve.do</c:set>
</c:if>
<form id="reservefrm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<div class="mod">
		<div class="mod_title">选择服务并进行预约
		<a class="more" href="/cmp/${companyId }/actor/list">返回服务人员列表</a>
		</div>
		<div class="mod_content">
			<div style="width: 500px">
				<hk:hide name="actorId" value="${actorId}" />
				<hk:hide name="companyId" value="${companyId}" />
				<hk:hide name="reserveId" value="${reserveId}" />
				<span class="b"></span>
				<ul class="rowlist actor_svr_list">
					<c:if test="${fn:length(svrreflist)>0}">
						<c:forEach var="svrref" items="${svrreflist}">
							<li svrid="${svrref.svrId }" svrmin="${svrref.cmpSvr.svrmin }">
								<hk:checkbox clazz="chx" name="svrId" value="${svrref.svrId }" checkedvalues="${sel_svrId}" />
								<span>
									<span class="split-r">${svrref.cmpSvr.name }</span> 
									<span class="split-r">时长${svrref.cmpSvr.svrmin }分钟</span> 
									<span class="split-r">￥${svrref.cmpSvr.price }</span> 
								</span>
							</li>
						</c:forEach>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
	<div class="mod">
		<div class="mod_title">
			${cmpActor.name }的预约时刻表
		</div>
		<div class="mod_content">
			<input id="frm_day" type="hidden" name="day" />
			<div id="user_date" class="divrow">
				<c:if test="${cmpReserve==null}">
					<fmt:formatDate var="day" pattern="yyyy-MM-dd" value="<%=new Date() %>" />
				</c:if>
				<c:if test="${cmpReserve!=null}">
					<fmt:formatDate var="day" pattern="yyyy-MM-dd" value="${cmpReserve.reserveTime}" />
				</c:if>
				<c:if test="${repeat_reserveId>0}">
					<fmt:formatDate var="day" pattern="yyyy-MM-dd" value="<%=new Date() %>" />
				</c:if>
				<span class="split-r">
				预约人姓名：<hk:text name="username" clazz="text_short" value="${cmpReserve.username}" maxlength="30"/>
				</span>
				<span class="split-r">
				手机号码：<hk:text name="mobile" clazz="text_short" value="${cmpReserve.mobile}" maxlength="20"/>
				</span>
				<span class="split-r">
				日期：
				<input id="seldate" type="text" name="datestr" class="text_short datepicker" value="${day }" />
				</span>
				<span>
					时间：
					<fmt:formatDate var="begintime" value="${cmpReserve.reserveTime}" pattern="HH:mm"/>
					<fmt:formatDate var="endtime" value="${cmpReserve.endTime}" pattern="HH:mm"/>
					<input id="begint" name="begintime" type="text" value="${begintime }" class="text_short" readonly="readonly" />
					到
					<input id="endt" name="endtime" type="text" value="${endtime }" class="text_short" readonly="readonly" />
				</span>
			</div>
			<div id="_dateinfo" class="infowarn"></div>
			<div class="divrow">
				<span class="b" style="font-size: 18px">点击绿色区域选择时间并预约</span>
			</div>
			<div class="divrow">
				<span class="mod_block inuse"></span><div class="f_l split-r">表示不能预约</div> 
				<span class="mod_block free"></span><div class="f_l split-r">表示可以预约</div>
				<span class="mod_block usersel"></span><div class="f_l">表示用户已经预约的时间</div>
				<div class="clr"></div>
			</div>
			<div id="dateinfo">
			</div>
			<div class="divrow" align="center">
				<c:if test="${!(update==1)}">
					<hk:submit clazz="btn split-r" value="创建预约"/>
				</c:if>
				<c:if test="${(update==1)}">
					<hk:submit clazz="btn split-r" value="保存预约"/>
					<a href="<%=path %>/h4/op/reserve_myreserve.do">回到我的预约</a>
				</c:if>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
var svr_time=0;//毫秒，服务总时长
var begin_time=null;
<c:if test="${(update==1)}">
begin_time=new Date();
begin_time.setHours(<fmt:formatDate value="${cmpReserve.reserveTime}" pattern="H"/>);
begin_time.setMinutes(<fmt:formatDate value="${cmpReserve.reserveTime}" pattern="m"/>);
</c:if>
var current_svr_id = -1;
var svrinfo=new Array();
<c:forEach var="svrref" items="${svrreflist}" varStatus="idx">
svrinfo[${svrref.svrId }]="${svrref.cmpSvr.intro }";
</c:forEach>
function loadreserveinfo(actorId,datestr){
	$('#dateinfo').html('数据加载中 ... ...');
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/op/reserve_loadinfoforreserve.do?companyId=${companyId}&reserveId=${reserveId}&actorId="+actorId+"&datestr="+datestr,
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
				begin_time=date;
				var endTime=createEndTime();
				$('#endt').attr('value',endTime.getHours()+":"+endTime.getMinutes());
				$('#user_date').effect('highlight',{},3000,function(){});
			});
		}
	});
}
$(document).ready(function(){
	$('ul.svrlist li').bind('mouseover', function(){
		$(this).addClass('cur');
	}).bind('mouseout', function(){
		$(this).removeClass('cur');
	});
	$('ul.actor_svr_list li').bind('mouseenter',function(e){
		$(this).css('background-color', '#ffffcc');
		var svrId=$(this).attr('svrid');
		$("body").append('<div id="tooltip" class="tooltip"><div><span class="b">服务介绍</span>：<br/>' + svrinfo[svrId] + '</div></div>');
		$('#tooltip').css({
			top:e.pageY - 35,
			left:e.pageX +30
		}).show();
	}).bind('mouseleave',function(e){
		$(this).css('background-color', '#ffffff');
		$('#tooltip').remove();
	});
	$('ul.actor_svr_list li .chx').bind('click',function(){
		if($(this).attr('checked')==true){
			svr_time+=parseInt($(this).parent().attr('svrmin'))*60000;
		}
		else{
			svr_time-=parseInt($(this).parent().attr('svrmin'))*60000;
		}
		if(begin_time!=null){
			var endTime=createEndTime();
			$('#endt').attr('value',endTime.getHours()+":"+endTime.getMinutes());
			$('#user_date').effect('highlight',{},3000,function(){});
		}
	});
	$('ul.actor_svr_list li .chx').each(function(i,el){
		if($(this).attr('checked')==true){
			svr_time+=parseInt($(this).parent().attr('svrmin'))*60000;
		}
	});
});
function createEndTime(){
	var longtime=begin_time.getTime();
	if(svr_time==0){
		longtime=longtime+1800000;
	}
	else{
		longtime=longtime+svr_time;
	}
	var date=new Date();
	date.setTime(longtime);
	return date;
}
function showsvrinfo(svrId){
	$('#svr_intro').html(svrinfo[svrId]);
}
function subfrm(frmid){
	showGlass(frmid);
	setHtml('_dateinfo','');
	return true;
}
function createok(error,msg,v){
	tourl('<%=path%>/h4/op/reserve_myreserve.do');
}
function createerror(error,msg,v){
	setHtml('_dateinfo',msg);
	hideGlass();
}
loadreserveinfo(${actorId},'${day}');
getObj('reservefrm').reset();
</script>
</c:set><jsp:include page="../../../inc/frame.jsp"></jsp:include>