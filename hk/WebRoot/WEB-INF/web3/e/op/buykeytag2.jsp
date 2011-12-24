<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">购买关键词</c:set>
<c:set var="mgr_content" scope="request">
<h3>您购买的关键词 ${name }</h3>
<div class="bdbtm"></div>
<div>
<form id="buykeytag_frm" onsubmit="return subbuyfrm(this.id)" method="post" action="<%=path%>/e/op/cmporder_buykeytag.do" target="hideframe">
	<hk:hide name="name" value="${name }" />
	<hk:hide name="companyId" value="${companyId}" />
	<hk:hide name="cityId" value="${cityId}" />
	<div style="padding-left: 20px;">
		<table class="infotable" cellpadding="0" cellspacing="0" width="100%">
			<c:if test="${cannotfmodify}">
				<tr>
					<td width="90px"></td>
					<td>
						<div class="errorlong">
							<strong class="text_14">您在今天已经修改过此内容，不能再次修改。每天只允许修改一次</strong>
						</div>
					</td>
				</tr>
			</c:if>
			<tr>
				<td width="90px"></td>
				<td>
					<strong class="text_14">目前余额${company.money }<br />到期时间<fmt:formatDate value="${o.overTime}" pattern="yyyy-MM-dd" />
					</strong>
					<br />
				</td>
			</tr>
			<tr>
				<td width="90px">选择地区</td>
				<td>
					<div class="f_l" style="width: 200px;">
						<hk:select oid="id_provinceId" name="provinceId" onchange="initcity(this.value)">
							<hk:option value="-1" data="请选择" />
							<hk:option value="0" data="全国" />
							<c:forEach var="province" items="${provincelist}">
								<hk:option value="${province.provinceId}" data="${province.province}" />
							</c:forEach>
						</hk:select>
						<hk:select oid="id_pcityId" name="cityId">
							<hk:option value="-1" data="请选择" />
						</hk:select>
						<br />
						<div id="zone_error" class="error"></div>
					</div>
					<div id="zone_flag" class="flag"></div>
					<div class="clr"></div>
				</td>
			</tr>
			<tr>
				<td width="90px">购买价格</td>
				<td>
					<div class="f_l" style="width: 200px;">
						<c:if test="${move_money!=null && move_money>0}">
							<hk:text name="money" value="${move_money}" maxlength="50" clazz="text_short_1" />元<br />
						</c:if>
						<c:if test="${move_money==null || move_money<=0}">
							<hk:text name="money" value="${o.money}" maxlength="50" clazz="text_short_1" />元<br />
						</c:if>
						<div id="hkobjkeytagorder_money_error" class="error"></div>
					</div>
					<div id="hkobjkeytagorder_money_flag" class="flag"></div>
					<div class="clr"></div>
				</td>
			</tr>
			<tr>
				<td width="90px">购买天数</td>
				<td>
					<div class="f_l" style="width: 200px;">
						<hk:radioarea name="addflg" checkedvalue="0">
							<hk:radio value="0" data="增加" />
							<hk:radio value="1" data="减少" />
						</hk:radioarea>
						<br />
						<hk:text name="pday" maxlength="10" clazz="text_short_1" />
						天
						<br />
						<div id="hkobjkeytagorder_pday_error" class="error"></div>
					</div>
					<div id="hkobjkeytagorder_pday_flag" class="flag"></div>
					<div class="clr"></div>
				</td>
			</tr>
			<tr>
				<td width="90px"></td>
				<td>
					<div id="other_error" class="error"></div>
				</td>
			</tr>
			<tr>
				<td width="90px"></td>
				<td>
					<div class="f_l">
						<c:if test="${cannotfmodify==null || !cannotfmodify}">
							<input type="submit" value="修改" class="btn size4" style="width: 120px" />
						</c:if>
						<a href="<%=path %>/e/op/cmporder_myorderlist.do?flg=1&companyId=${companyId}" class="text_14">返回</a>
					</div>
				</td>
			</tr>
		</table>
	</div>
</form>
</div>

<script type="text/javascript">
var morecmp=false;
<c:if test="${fn:length(list)>1}">
morecmp=true;
</c:if>
function clearValidate(){
	setHtml("zone_error","");
	getObj("zone_flag").className="flag";
	setHtml("hkobjkeytagorder_pday_error","");
	getObj("hkobjkeytagorder_money_flag").className="flag";
	setHtml("hkobjkeytagorder_money_error","");
	getObj("hkobjkeytagorder_money_flag").className="flag";
	setHtml("other_error","");
}
function subbuyfrm(frmid){
	if(morecmp){
		if(getObj("id_companyId").value==0){
			alert("请选择商家");
			return false;
		}
	}
	clearValidate();
	showSubmitDiv(frmid);
	return true;
}
function tofindkeytag(){
	tourl('/e/op/cmporder_findkeytag.do?companyId=${companyId}');
}
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
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error!=0){
		if(error==206){
			alert("余额不足，不能购买");
		}
		else if(error==209){
			alert("由于今天您已经更新过此关键词的竞价，不能再次更新");
		}
		else{
			$('#'+obj_id_param+'_error').html(error_msg);
			getObj(obj_id_param+'_flag').className="flag error";
		}
		hideSubmitDiv();
	}
	else{
		tourl("<%=path %>/e/op/cmporder_myorderlist.do?flg=1&companyId=${companyId}");
	}
}
function initselected(){
	var provinceId=-1;
	var cityId=${cityId};
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
</script>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>