<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_body_content" scope="request">
	<div class="mod">
		<div class="mod_title">淘宝客商品搜索
		</div>
		<div class="mod_content">
		<div class="row">
		<a href="${ctx_path }/tb/admin/taokeitem">所有商品</a>
		<c:if test="${parent_cid>0}">&gt;<a href="javascript:showcat(0)">${parent.name }</a></c:if>
		<c:if test="${cid>0}">&gt;${item_cat.name }</c:if>
		</div>
			<div class="row">
				<form id="frm" method="get" action="${ctx_path }/tb/admin/taokeitem">
					<input type="hidden" id="_sort" name="sort" value="${sort }"/>
					<input type="hidden" id="_cid" name="cid" value="${cid }"/>
					<div class="row">
						名称：<input type="text" class="text" name="keyword" value="${taobaokeCdn.keyword }"/>
						<hk:select name="parent_cid" checkedvalue="${parent_cid}">
							<hk:option value="0" data="所有分类"/>
							<c:forEach var="cat" items="${root_catlist}">
								<hk:option value="${cat.cid}" data="${cat.name}"/>
							</c:forEach>
						</hk:select>
					</div>
					<div class="row">
					<span class="split-r">
						佣金比率：<input type="text" value="${start_commissionRate }" name="start_commissionRate" class="text" style="width:45px"/>
						- <input type="text" value="${end_commissionRate }" name="end_commissionRate" class="text" style="width:45px"/> %  
					</span>
					<span class="split-r">
						30天推广量：<input type="text" value="${start_commissionNum }" name="start_commissionNum" class="text" style="width:45px"/>
						- <input type="text" value="${end_commissionNum }" name="end_commissionNum" class="text" style="width:45px"/> 个  
					</span>
						价格：<input type="text" value="${start_price }" name="start_price" class="text" style="width:45px"/>
						-<input type="text" value="${end_price }" name="end_price" class="text" style="width:45px"/> 元
					</div>
					<div class="row">
						<span class="split-r">卖家信用：
						<hk:select name="start_credit" checkedvalue="${start_credit}">
							<hk:option value="" data=""/>
							<%for(int i=5;i>0;i--){request.setAttribute("i",i);%>
								<hk:option value="${i}goldencrown" data="${i}皇冠"/>
							<%} %>
							<%for(int i=5;i>0;i--){request.setAttribute("i",i);%>
								<hk:option value="${i}crown" data="${i}蓝冠"/>
							<%} %>
							<%for(int i=5;i>0;i--){request.setAttribute("i",i);%>
								<hk:option value="${i}diamond" data="${i}钻"/>
							<%} %>
							<%for(int i=5;i>0;i--){request.setAttribute("i",i);%>
								<hk:option value="${i}heart" data="${i}心"/>
							<%} %>
						</hk:select> 
						 -
						 <hk:select name="end_credit" checkedvalue="${end_credit}">
							<hk:option value="" data=""/>
							<%for(int i=5;i>0;i--){request.setAttribute("i",i);%>
								<hk:option value="${i}goldencrown" data="${i}皇冠"/>
							<%} %>
							<%for(int i=5;i>0;i--){request.setAttribute("i",i);%>
								<hk:option value="${i}crown" data="${i}蓝冠"/>
							<%} %>
							<%for(int i=5;i>0;i--){request.setAttribute("i",i);%>
								<hk:option value="${i}diamond" data="${i}钻"/>
							<%} %>
							<%for(int i=5;i>0;i--){request.setAttribute("i",i);%>
								<hk:option value="${i}heart" data="${i}心"/>
							<%} %>
						</hk:select> </span>
						所在地:
						<hk:select name="area" checkedvalue="${area}">
							<hk:option value="" data="所有地区"/>
							<hk:option value="江苏,浙江,上海" data="江浙沪"/>
							<hk:option value="广州,深圳,中山,珠海,佛山,东莞,惠州" data="珠三角"/>
							<hk:option value="北京" data="北京"/>
							<hk:option value="上海" data="上海"/>
							<hk:option value="杭州" data="杭州"/>
							<hk:option value="广州" data="广州"/>
							<hk:option value="深圳" data="深圳"/>
							<hk:option value="南京" data="南京"/>
							<hk:option value="武汉" data="武汉"/>
							<hk:option value="天津" data="天津"/>
							<hk:option value="成都" data="成都"/>
							<hk:option value="哈尔滨" data="哈尔滨"/>
							<hk:option value="重庆" data="重庆"/>
							<hk:option value="宁波" data="宁波"/>
							<hk:option value="无锡" data="无锡"/>
							<hk:option value="济南" data="济南"/>
							<hk:option value="苏州" data="苏州"/>
							<hk:option value="温州" data="温州"/>
							<hk:option value="青岛" data="青岛"/>
							<hk:option value="沈阳" data="沈阳"/>
							<hk:option value="福州" data="福州"/>
							<hk:option value="西安" data="西安"/>
							<hk:option value="长沙" data="长沙"/>
							<hk:option value="合肥" data="合肥"/>
							<hk:option value="南宁" data="南宁"/>
							<hk:option value="南昌" data="南昌"/>
							<hk:option value="郑州" data="郑州"/>
							<hk:option value="厦门" data="厦门"/>
							<hk:option value="大连" data="大连"/>
							<hk:option value="常州" data="常州"/>
							<hk:option value="石家庄" data="石家庄"/>
							<hk:option value="东莞" data="东莞"/>
							<hk:option value="安徽" data="安徽"/>
							<hk:option value="福建" data="福建"/>
							<hk:option value="甘肃" data="甘肃"/>
							<hk:option value="广东" data="广东"/>
							<hk:option value="广西" data="广西"/>
							<hk:option value="贵州" data="贵州"/>
							<hk:option value="海南" data="海南"/>
							<hk:option value="河北" data="河北"/>
							<hk:option value="黑龙江" data="黑龙江"/>
							<hk:option value="河南" data="河南"/>
							<hk:option value="湖北"  data="湖北"/>
							<hk:option value="湖南" data="湖南"/>
							<hk:option value="江苏" data="江苏"/>
							<hk:option value="江西" data="江西"/>
							<hk:option value="吉林" data="吉林"/>
							<hk:option value="辽宁" data="辽宁"/>
							<hk:option value="内蒙古" data="内蒙古"/>
							<hk:option value="宁夏" data="宁夏"/>
							<hk:option value="青海" data="青海"/>
							<hk:option value="山东" data="山东"/>
							<hk:option value="山西" data="山西"/>
							<hk:option value="陕西" data="陕西"/>
							<hk:option value="四川" data="四川"/>
							<hk:option value="新疆" data="新疆"/>
							<hk:option value="西藏" data="西藏"/>
							<hk:option value="云南" data="云南"/>
							<hk:option value="浙江" data="浙江"/>
							<hk:option value="香港" data="香港"/>
							<hk:option value="澳门" data="澳门"/>
							<hk:option value="台湾" data="台湾"/>
							<hk:option value="美国,英国,法国,瑞士,澳洲,新西兰,加拿大,奥地利,韩国,日本,德国,意大利,西班牙,俄罗斯,泰国,印度,荷兰,新加坡,其它国家" data="海外"/>
						</hk:select>
					</div>
					<div class="row">
						<span class="split-r"><hk:checkbox name="cash_coupon" oid="_cash_coupon" checkedvalue="${cash_coupon}" value="true"/><label for="_cash_coupon">抵价券</label></span>
						<span class="split-r"><hk:checkbox name="vip_card" oid="_vip_card" checkedvalue="${vip_card}" value="true"/><label for="_vip_card">VIP</label></span>
						<span class="split-r"><hk:checkbox name="overseas_item" oid="_overseas_item" checkedvalue="${overseas_item}" value="true"/><label for="_overseas_item">海外商品</label></span>
						<span class="split-r"><hk:checkbox name="sevendays_return" oid="_sevendays_return" checkedvalue="${sevendays_return}" value="true"/><label for="_sevendays_return">7天退换</label></span>
						<span class="split-r"><hk:checkbox name="real_describe" oid="_real_describe" checkedvalue="${real_describe}" value="true"/><label for="_real_describe">如实描述</label></span>
						<span class="split-r"><hk:checkbox name="onemonth_repair" oid="_onemonth_repair" checkedvalue="${onemonth_repair}" value="true"/><label for="_onemonth_repair">30天维修 </label></span>
						<span class="split-r"><hk:checkbox name="mall_item" oid="_mall_item" checkedvalue="${mall_item}" value="true"/><label for="_mall_item">淘宝商城 </label></span>
						<input type="submit" class="btn" value="搜索"/>
					</div>
				</form>
			</div>
			<c:if test="${fn:length(catlist)>0}">
				<div class="row">
				${parent.name } 二级类目：<br/>
					<c:forEach var="cat" items="${catlist}"><a class="split-r" href="javascript:showcat(${cat.cid })">${cat.name }</a></c:forEach>
				</div>
			</c:if>
			<c:if test="${fn:length(list)==0}">
			<div class="row b" align="center">本页没有数据</div>
			</c:if>
			<ul class="rowlist">
				<li>
					<div class="f_l" style="width: 80px">&nbsp;</div>
					<div class="f_l" style="width: 180px;padding-left: 10px;">名称</div>
					<div class="f_l" align="center" style="width: 80px">
					<a href="javascript:chgsort('price')">单价</a><c:if test="${sort=='price_desc'}">↓</c:if><c:if test="${sort=='price_asc'}">↑</c:if>
					</div>
					<div class="f_l" align="center" style="width: 80px">
					<a  href="javascript:chgsort('commissionRate')">佣金比率</a><c:if test="${sort=='commissionRate_desc'}">↓</c:if><c:if test="${sort=='commissionRate_asc'}">↑</c:if>
					</div>
					<div class="f_l" align="center" style="width: 80px">佣金</div>
					<div class="f_l" align="center" style="width: 100px">
					<a  href="javascript:chgsort('commissionVolume')">30天支出佣金</a><c:if test="${sort=='commissionVolume_desc'}">↓</c:if><c:if test="${sort=='commissionVolume_asc'}">↑</c:if>
					</div>
					<div class="f_l" align="center" style="width: 80px">
					<a  href="javascript:chgsort('commissionNum')">30天推广量</a><c:if test="${sort=='commissionNum_desc'}">↓</c:if><c:if test="${sort=='commissionNum_asc'}">↑</c:if>
					</div>
					<div class="clr"></div>
				</li>
				<c:forEach var="item" items="${list}">
				<li>
					<div class="f_l" style="width: 80px">
					<a target="_blank" href="http://item.taobao.com/item.htm?id=7${item.numIid }"><img src="${item.picUrl }_sum.jpg"/></a>
					</div>
					<div class="f_l" style="width: 180px;padding-left: 10px;"><a target="_blank" href="http://item.taobao.com/item.htm?id=${item.numIid }">${item.title }</a></div>
					<div class="f_l" align="center" style="width: 80px">${item.price }</div>
					<div class="f_l" align="center" style="width: 80px">${item.commissionRate }%</div>
					<div class="f_l" align="center" style="width: 80px">${item.commission }</div>
					<div class="f_l" align="center" style="width: 100px">${item.commissionVolume }</div>
					<div class="f_l" align="center" style="width: 80px">${item.commissionNum }</div>
					<div class="clr"></div>
				</li>
				</c:forEach>
			</ul>
			<div>
			<c:set var="page_url" scope="request">${ctx_path}/tb/admin/taokeitem?parent_cid=${parent_cid}&cid=${cid}&keyword=${enc_keyword}&start_commissionRate=${start_commissionRate}&end_commissionRate=${end_commissionRate}&start_commissionNum=${start_commissionNum}&end_commissionNum=${end_commissionNum}&start_price=${start_price}&end_price=${end_price}&start_credit=${start_credit}&end_credit=${end_credit}&area=${enc_area}&cash_coupon=${cash_coupon}&vip_card=${vip_card}&overseas_item={overseas_item}&sevendays_return=${sevendays_return}&real_describe=${real_describe}&onemonth_repair=${onemonth_repair}&mall_item=${mall_item}&sort=${sort}</c:set>
			<jsp:include page="../../webtb/inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).css('background-color','#ffffcc');
	}).bind('mouseleave', function(){
		$(this).css('background-color','#ffffff');
	});
});
function chgsort(field){
	var sort_value = getObj('_sort').value;
	if (sort_value == field + '_desc') {
		getObj('_sort').value = field + "_asc";
	}
	else {
		getObj('_sort').value = field + "_desc";
	}
	getObj('frm').submit();
}
function showcat(v){
	getObj('frm').cid.value = v;
	getObj('frm').submit();
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>