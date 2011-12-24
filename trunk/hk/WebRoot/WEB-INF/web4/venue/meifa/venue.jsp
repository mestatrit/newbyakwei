<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CompanyUserStatus"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${company.name}</c:set>
<c:set var="js_value" scope="request">
<meta name="keywords" content="${company.name }|<hk:data key="view2.website.title"/>" />
<script type="text/javascript" language="javascript" src="<%=path %>/webst4/js/reserve.js"></script>
<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/hovertip.js"></script>
</c:set>
<c:set var="html_body_content" scope="request">
<div class="pl">
	<div class="mod">
		<h1>
			${company.name }<c:if test="${not empty company.sname}">（${company.sname}）</c:if>
		</h1>
		<div>
		<script type="text/javascript">
		obj_width=265;
		</script>
		<jsp:include page="../../inc/share.jsp"></jsp:include>
		</div>
		${country.country} ${province.province} ${company.pcity.name} - ${company.addr }<br /> ${company.tel }
		<c:if test="${canedit}">
			<a href="<%=path %>/h4/op/venue/cmp.do?companyId=${companyId}">管理</a>
		</c:if>
		<div>
		${company.intro }
		</div>
	</div>
	<div class="mod m2">
		<ul class="actorlist">
			<c:forEach var="actor" items="${cmpactorlist}">
				<li>
					<div class="p" idx="${actor.actorId }">
						<div id="actor_${actor.actorId }" class="reserve">
							<a href="javascript:reserve_actor(${companyId },${actor.actorId })">预约</a>
						</div>
						<a href="/cmp/${companyId }/actor/${actor.actorId }"><img src="${actor.pic150Url }" width="120" height="150"/></a>
					</div>
					<p><a href="/cmp/${companyId }/actor/${actor.actorId }">${actor.name }</a></p>
					<p>${actor.cmpActorRole.name }</p>
				</li>
			</c:forEach>
		</ul>
		<div class="clr"></div>
	</div>
	<div class="mod">
		<div class="mod_title">点评</div>
		<div class="mod_content">
			<ul class="reviewlist">
				<li>
					<div class="head">
						<a href="#"><img src="img/h48.jpg"/></a><a href="#">昵称</a>
					</div>
					<div class="review">点评内容点评内容点评内容点评内容点评内容点评内容点评内容点评内容
						点评内容点评内容点评内容点评内容点评内容点评内容点评内容点评内容
						点评内容点评内容点评内容点评内容点评内容点评内容点评内容点评内容</div>
					<div class="clr"></div>
				</li>
			</ul>
		</div>
	</div>
</div>
<div class="pr">
	<div class="mod">
		<div class="f_r">
			<input type="button" class="btn" value="预约" onclick="tourl('/cmp/${companyId}/actor/list')" style="margin-bottom: 5px;clear: both;"/>
			<c:if test="${companyUserStatus==null}">
				<div id="div_cmpuserstatus_done" class="tip_todo_unchecked"><input id="cmpuserstatus_done" type="checkbox" value="<%=CompanyUserStatus.USERSTATUS_DONE %>" onclick="setcmpuserstatusdone(this)"/><label for="cmpuserstatus_done"><hk:data key="view2.didthis"/></label></div>
				<div id="div_cmpuserstatus_want" class="tip_todo_unchecked"><input id="cmpuserstatus_want" type="checkbox" value="<%=CompanyUserStatus.USERSTATUS_WANT %>" onclick="setcmpuserstatuswant(this)"/><label for="cmpuserstatus_want"><hk:data key="view2.wantto"/></label></div>
			</c:if>
			<c:if test="${companyUserStatus!=null}">
				<c:if test="${companyUserStatus.done}">
					<div id="div_cmpuserstatus_done" class="tip_checked"><input id="cmpuserstatus_done" type="checkbox" value="<%=CompanyUserStatus.USERSTATUS_DONE %>" onclick="setcmpuserstatusdone(this)" checked="checked"/><label for="cmpuserstatus_done"><hk:data key="view2.didthis"/></label></div>
				</c:if>
				<c:if test="${!companyUserStatus.done}">
					<div id="div_cmpuserstatus_done" class="tip_todo_unchecked"><input id="cmpuserstatus_done" type="checkbox" value="<%=CompanyUserStatus.USERSTATUS_DONE %>" onclick="setcmpuserstatusdone(this)"/><label for="cmpuserstatus_done"><hk:data key="view2.didthis"/></label></div>
				</c:if>
				<c:if test="${companyUserStatus.want}">
					<div id="div_cmpuserstatus_want" class="tip_checked"><input id="cmpuserstatus_want" type="checkbox" value="<%=CompanyUserStatus.USERSTATUS_WANT %>" onclick="setcmpuserstatuswant(this)" checked="checked"/><label for="cmpuserstatus_want"><hk:data key="view2.wantto"/></label></div>
				</c:if>
				<c:if test="${!companyUserStatus.want}">
					<div id="div_cmpuserstatus_want" class="tip_todo_unchecked"><input id="cmpuserstatus_want" type="checkbox" value="<%=CompanyUserStatus.USERSTATUS_WANT %>" onclick="setcmpuserstatuswant(this)"/><label for="cmpuserstatus_want"><hk:data key="view2.wantto"/></label></div>
				</c:if>
			</c:if>
		</div>
		<div class="clr"></div>
	</div>
	<c:if test="${fn:length(cmpsvrlist)>0}">
		<div class="mod">
			<div class="mod_title">产品服务</div>
			<div class="mod_content">
				<ul class="datalist svrlist">
					<c:forEach var="svr" items="${cmpsvrlist}">
						<li idx="${svr.svrId }">
							<div class="svrname2">
								<a href="/cmp/${companyId }/service/${svr.svrId}">${svr.name }</a>
							</div>
							<div class="svrprice2">￥${svr.price }</div>
							<div class="clr"></div>
							<div id="svr_reserve_${svr.svrId }" class="svr_reserve2">
								<input type="button" class="btn_reserve" value="预约" onclick="reserve_svr(${companyId},${svr.svrId })"/>
							</div>
							<div class="clr"></div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</c:if>
	<c:if test="${company.markerX!=0 && company.markerY!=0}">
		<div class="mod" style="width:260px;height:260px;">
			<img src="http://ditu.google.cn/staticmap?center=${company.markerX },${company.markerY }&zoom=16&size=260x260&markers=${company.markerX },${company.markerY }&format=jpg&maptype=mobile&key=<%=HkWebConfig.getGoogleApiKey() %>&sensor=false"/>
		</div>
	</c:if>
</div>
<div class="clr"></div>
<script type="text/javascript">
var companyId=${companyId};
var CompanyUserStatus_USERSTATUS_WANT=<%=CompanyUserStatus.USERSTATUS_WANT %>;
var CompanyUserStatus_USERSTATUS_DONE=<%=CompanyUserStatus.USERSTATUS_DONE %>;
	$(document).ready(function(){
		window.setTimeout(hovertipInit, 1);
		$('ul.actorlist li .p').bind('mouseenter', function(){
			var idx = $(this).attr('idx');
			$('#actor_' + idx).css('display', 'block');
		}).bind('mouseleave', function(){
			var idx = $(this).attr('idx');
			$('#actor_' + idx).css('display', 'none');
		});
		$('ul.svrlist li').bind('mouseenter', function(){
			$(this).css('background-color','#ffffcc');
			var idx = $(this).attr('idx');
			$('#svr_reserve_' + idx).css('display', 'block');
		}).bind('mouseleave', function(){
			$(this).css('background-color','#ffffff');
			var idx = $(this).attr('idx');
			$('#svr_reserve_' + idx).css('display', 'none');
		});
		$('ul.reviewlist li').bind('mouseenter', function(){
			$(this).css('background-color','#ffffcc');
		}).bind('mouseleave', function(){
			$(this).css('background-color','#ffffff');
		});
	});
	function setcmpuserstatusdone(obj){
		if(!userLogin){
			alertLoginAndReg();
			return;
		}
		var acturl='';
		if(obj.checked==true){
			acturl=path+"/h4/op/user/venue_updateusercmpstatus.do?companyId="+companyId+"&status="+CompanyUserStatus_USERSTATUS_DONE;
		}
		else{
			acturl=path+"/h4/op/user/venue_deleteusercmpstatus.do?companyId="+companyId+"&status="+CompanyUserStatus_USERSTATUS_DONE;
		}
		$.ajax({
			type:"POST",
			url:acturl,
			cache:false,
	    	dataType:"html",
			success:function(data){
			changestatusdonehtml(obj);
			}
		});
	}
	function changestatusdonehtml(obj){
		if(obj.checked==true){
			getObj('div_cmpuserstatus_done').className='tip_checked';
		}
		else{
			getObj('div_cmpuserstatus_done').className='tip_todo_unchecked';
		}
	}
	function setcmpuserstatuswant(obj){
		if(!userLogin){
			alertLoginAndReg();
			return;
		}
		var acturl='';
		if(obj.checked==true){
			acturl=path+"/h4/op/user/venue_updateusercmpstatus.do?companyId="+companyId+"&status="+CompanyUserStatus_USERSTATUS_WANT;
		}
		else{
			acturl=path+"/h4/op/user/venue_deleteusercmpstatus.do?companyId="+companyId+"&status="+CompanyUserStatus_USERSTATUS_WANT;
		}
		$.ajax({
			type:"POST",
			url:acturl,
			cache:false,
	    	dataType:"html",
			success:function(data){
				changestatuswanthtml(obj);
			}
		});
	}
	function changestatuswanthtml(obj){
		if(obj.checked==true){
			getObj('div_cmpuserstatus_want').className='tip_checked';
		}
		else{
			getObj('div_cmpuserstatus_want').className='tip_todo_unchecked';
		}
	}
</script>
</c:set><jsp:include page="../../inc/frame.jsp"></jsp:include>