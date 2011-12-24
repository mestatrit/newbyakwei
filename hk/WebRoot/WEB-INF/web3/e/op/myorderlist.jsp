<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.HkOrder"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="html_title" scope="request"><hk:data key="view.company.order"/></c:set>
<%String path=request.getContextPath();%>
<c:set var="mgr_content" scope="request">
<div>
<table class="infotable" cellpadding="0" cellspacing="0" width="590px">
	<c:if test="${flg==1}">
	<tr>
		<td colspan="5">
			<a class="text_14" href="<%=path %>/e/op/cmporder_findkeytag.do?companyId=${companyId }">购买关键词</a>
		</td>
	</tr>
	</c:if>
	<tr>
		<td colspan="5">
			<hk:form oid="search_frm" method="get" action="/e/op/cmporder_myorderlist.do">
				<hk:hide name="flg" value="${flg}"/>
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:hide name="tagId" value="${tagId}"/>
				地区
				<hk:select oid="id_provinceId" name="provinceId" onchange="initcity(this.value);checkfrm()">
					<hk:option value="-1" data="全部"/>
					<hk:option value="0" data="全国"/>
					<c:forEach var="province" items="${provincelist}">
						<hk:option value="${province.provinceId}" data="${province.province}"/>
					</c:forEach>
				</hk:select>
				<hk:select oid="id_pcityId" name="cityId" onchange="subsearchfrm()">
					<hk:option value="-1" data="全部"/>
				</hk:select>
			</hk:form><br/>
		</td>
	</tr>
	<tr>
		<c:if test="${flg==0}">
			<td width="50px"><strong>位置</strong></td>
		</c:if>
		<c:if test="${flg==1}">
			<td width="80px"><strong>关键词</strong></td>
		</c:if>
		<c:if test="${flg==2}">
			<td width="80px"><strong>分类</strong></td>
		</c:if>
		<td width="100px"><strong>到期时间</strong></td>
		<td width="60px"><strong>价格</strong></td>
		<td width="80px"><strong>地区</strong></td>
		<td width="60px"><strong>状态</strong></td>
		<td width="150px"></td>
	</tr>
	<c:if test="${fn:length(list)==0}">
		<tr>
			<td colspan="5"><strong class="text_14"><hk:data key="nodatainthispage"/></strong></td>
		</tr>
	</c:if>
	<c:forEach var="order" items="${list}" varStatus="idx">
		<tr>
			<c:if test="${flg==0}"><td>首页</td></c:if>
			<c:if test="${flg==1}"><td>${order.keyTag.name }</td></c:if>
			<c:if test="${flg==2}"><td>${order.companyKind.name }</td></c:if>
			<td><fmt:formatDate value="${order.overTime}" pattern="yyyy-MM-dd"/></td>
			<td align="left">${order.money }</td>
			<td>
				<c:if test="${order.pcity!=null}">${order.pcity.name }</c:if>
				<c:if test="${order.pcity==null}"> <hk:data key="view.zone_all"/></c:if>
			</td>
			<td>
				<c:if test="${order.stop}">停止</c:if>
				<c:if test="${!order.stop}">运行中</c:if>
			</td>
			<td>
				<c:if test="${flg==0}">
					<a href="<%=path %>/index_cityweb.do?showorder=1&cityId=${order.cityId }">查看排名</a>
				</c:if>
				<c:if test="${flg==1}">
					<a href="#">查看排名</a>
				</c:if>
				<c:if test="${flg==2}"><a href="<%=path %>/cmp_listforkind.do?showorder=1&cityId=${order.pcity.ocityId }&provinceId=${order.pcity.provinceId }&kindId=${order.kindId }">查看排名</a></c:if>
				/
				<a href="<%=path %>/e/op/cmporder_toedit.do?flg=${flg }&oid=${order.oid }&companyId=${companyId }&cityId=${order.cityId }">修改</a>
				/
				<c:if test="${order.stop}">
					<a id="status_${order.oid }" href="javascript:setstatus(${order.oid },<%=HkOrder.STOPFLG_N %>);">启动</a>
				</c:if>
				<c:if test="${!order.stop}">
					<a id="status_${order.oid }" href="javascript:setstatus(${order.oid },<%=HkOrder.STOPFLG_Y %>);">停止</a>
				</c:if>
			</td>
		</tr>
	</c:forEach>
</table>
<script type="text/javascript">
var city=new Array();
<c:forEach var="city" items="${clist}" varStatus="idx">
city[${idx.index }]=new Array(${city.cityId },${city.provinceId},'${city.name }');
</c:forEach>
function initcity(pid){
	var oo=getObj("id_pcityId");
	oo.options.length=0;
	if(pid==0){
		oo.options[0]=new Option("全国",0);
	}
	else{
		oo.options[0]=new Option("全部",-1);
	}
	for(var i=0;i<city.length;i++){
		if(city[i][1]==pid){
			oo.options[oo.options.length]=new Option(city[i][2],city[i][0]);
		}
	}
	if(oo.options.length==2){
		oo.options[0]=oo.options[1];
		oo.options.length=1;
	}
}
function initselected(){
	var provinceId=-1;
	var cityId=${cityId};
	if(cityId==0){
		var oo=getObj("id_provinceId");
		oo.options[1].selected=true;
		initcity(0);
		return;
	}
	for(var i=0;i<city.length;i++){
		if(city[i][0]==cityId){
			provinceId=city[i][1];//找到povince
			break;
		}
	}
	var oo=getObj("id_provinceId");
	for(var k=0;k<oo.options.length;k++){
		if(provinceId==parseInt(oo.options[k].value)){
			oo.options[k].selected=true;//设定province selected
			break;
		}
	}
	initcity(provinceId);
	oo=getObj("id_pcityId");
	for(var i=0;i<oo.options.length;i++){
		if(cityId==parseInt(oo.options[i].value)){
			oo.options[i].selected=true;//设定city selected
			break;
		}
	}
}
initselected();
function tofindkeytag(){
	tourl('/e/op/cmporder_findkeytag.do?companyId=${companyId}');
}
function continuebuy(){
	tourl('<%=path %>/e/op/cmporder_tobuykeytag.do?move_oid=${order.oid }&companyId=${companyId }&pcityId=${pcityId }&name=${enc_name }');
}
function setstatus(oid,stopflg){
	if(stopflg==1){
		if(!window.confirm("确实要停止此竞拍？")){
			return;
		}
	}
	showSubmitDivForObj('status_'+oid);
	getObj("id_oid").value=oid;
	getObj("id_stopflg").value=stopflg;
	getObj("hidefrm").submit();
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	tourl(document.location);
}
function checkfrm(){
	var oo=getObj("id_pcityId");
	if(oo.options.length==1){
		subsearchfrm();
	}
}
function subsearchfrm(){
	getObj("search_frm").submit();
}
</script>
<hk:form clazz="hide" oid="hidefrm" action="/e/op/cmporder_setstatus.do" target="hideframe">
	<input id="id_oid" type="hidden" name="oid"/>
	<input id="id_flg" type="hidden" name="flg" value="${flg }"/>
	<input id="id_stopflg" type="hidden" name="stopflg"/>
</hk:form>
</div>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>