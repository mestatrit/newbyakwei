<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">${keyTag.name}竞价清单</c:set>
<c:set var="mgr_content" scope="request">
<div>
<h3>${keyTag.name}竞价清单</h3>
	<table class="infotable" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="4">
				<hk:form method="get" action="/e/op/cmporder_keytagorderlist.do">
					<hk:hide name="companyId" value="${companyId}"/>
					<hk:hide name="tagId" value="${tagId}"/>
					<hk:select oid="id_provinceId" name="provinceId" onchange="initcity(this.value)">
						<hk:option value="-1" data="请选择"/>
						<hk:option value="0" data="全国"/>
						<c:forEach var="province" items="${provincelist}">
							<hk:option value="${province.provinceId}" data="${province.province}"/>
						</c:forEach>
					</hk:select>
					<hk:select oid="id_pcityId" name="cityId">
						<hk:option value="-1" data="请选择"/>
					</hk:select>
					<hk:submit value="搜索" clazz="btn size1"/> 
					<a href="<%=path %>/e/op/cmporder_findkeytag.do?companyId=${companyId }">返回</a>
				</hk:form>
			</td>
		</tr>
		<tr>
			<td width="80px"><strong>价格</strong></td>
			<td width="60px"><strong>位置</strong></td>
			<td width="80px"><strong>竞标价</strong></td>
			<td width="80px"></td>
		</tr>
		<c:forEach var="order" items="${list}" varStatus="idx">
			<c:set var="pos">${idx.count+base_page_size}</c:set>
			<tr>
				<td>${order.money }</td>
				<td>${pos }</td>
				<td>${order.jingJia }</td>
				<td><a href="<%=path %>/e/op/cmporder_tobuykeytag.do?move_oid=${order.oid }&companyId=${companyId }&cityId=${order.cityId }&name=${enc_name }">竞价</a></td>
			</tr>
		</c:forEach>
	</table>
	<c:if test="${fn:length(list)==0}">
		<strong class="text_14">此页没有数据</strong><br/><br/>
		<hk:button value="马上购买" clazz="btn size4" onclick="continuebuy()"/>
	</c:if>
</div>
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
		oo.options[0]=new Option("请选择",-1);
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
	tourl('<%=path %>/e/op/cmporder_tobuykeytag.do?move_oid=${order.oid }&companyId=${companyId }&cityId=${cityId }&name=${enc_name }');
}
</script>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>