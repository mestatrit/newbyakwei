<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.OrderForm"%><%@page import="com.hk.web.util.Hkcss2Util"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">${cmpTable.tableNum}</c:set>
<c:set var="body_hk_content" scope="request">
<style type="text/css">
ul.tablelist li{
font-size:14px;
position:relative;
color:#ffffff;
float: left;
width: 80px;
height: 70px;
border: 3px solid #e5e5e5;
margin: 5px;
text-align: center;
padding: 5px 0px;
overflow: hidden;
}
ul.tablelist li a{
color:#ffffff;
height: 25px;
display: block;
font-weight: bold;
text-decoration: underline;
}
ul.tablelist li a:hover{
text-decoration: none;
}
ul.tablelist li.free{
background: #006600;
}
ul.tablelist li.inuse{
background: #CC0000;
}
.tableimgcon{
width:750px;
}
.tableimg{
margin: 10px 0px;
height: 240px;
text-align: center;
}
.tablesmallimgcon{
width:350px;
margin: 10px auto;
text-align: center;
}
.tableimghref{
float: left;
margin: 5px;
display: block;
text-align: center;
}
.tableimghref img{
width: 60px;
height: 60px;
}
.intro{
font-size: 14px;
}
.action{
text-align: center;
}
</style>
<div class="mod_left">
	<div class="mod-1">
		<%=Hkcss2Util.rd_bg%>
		<div class="tit">座位分类</div>
		<div class="cont">
		<ul class="userset">
			<li><a class="n1" href="<%=path %>/op/orderform_tablelist.do?companyId=${companyId }&oid=${oid}"><hk:data key="view.company.allcmptable"/></a></li>
		</ul>
		<c:forEach var="cmpTableSort" items="${sortlist}">
			<div class="subtit">${cmpTableSort.name }</div>
			<ul class="userset">
				<c:forEach var="cpt" items="${cmpTableSort.cmpPersonTableList}">
					<c:set var="css_class"><c:if test="${sortId==cpt.sortId && cpt.personNum==num}">active</c:if></c:set>
					<li>
						<a id="num_${cpt.sortId }_${cpt.personNum}" class="n1 ${css_class }" href="<%=path %>/op/orderform_tablelist.do?companyId=${companyId }&sortId=${cpt.sortId }&num=${cpt.personNum}&oid=${oid}"><hk:data key="view.company.cmptable.personnum" arg0="${cpt.personNum}"/> <span>(${cpt.totalCount })</span></a>
					</li>
					<c:set var="css_class"></c:set>
				</c:forEach>
			</ul>
		</c:forEach>
		</div>
		<%=Hkcss2Util.rd_bg_bottom%>
	</div>
</div>
<div class="mod_primary">
	<h3 class="line">${company.name } 桌号：${cmpTable.tableNum }</h3>
	<div class="tableimgcon">
		<div id="tableimg" class="tableimg">
		<c:if test="${fn:length(photolist)==0}">
		<strong>暂无图片</strong>
		</c:if>
		</div>
		<div class="tablesmallimgcon">
			<c:forEach var="photo" items="${photolist}">
				<a href="javascript:showimg('${photo.pic240 }')" class="tableimghref"><img src="${photo.pic60 }"/></a>
			</c:forEach>
			<div class="clr"></div>
			<div class="intro">
			${cmpTable.intro }<br/>
			${cmpTablePhotoSet.intro }
			</div>
			<div class="action">
				<c:if test="${cmpTable.canNetBooked}">
					<hk:button clazz="btn" value="选定这个座位" onclick="toordertable()"/>
				</c:if>
				<c:if test="${!cmpTable.canNetBooked}">
					<span class="text_16 heavy"><hk:data key="view.company.table.netorder_not"/></span>
				</c:if>
			</div>
		</div>
	</div>
</div>
<div class="clr"></div>
<script type="text/javascript">
var img=new Array();
<c:forEach var="photo" items="${photolist}">
img[img.length]=new Array('${photo.pic60 }','${photo.pic240 }');
</c:forEach>
function showimg(path){
	setHtml('tableimg','<img src="'+path+'"/>');
}
if(img.length>0){
	showimg(img[0][1]);
}
function toordertable(){
	createBg();
	var html='<hk:form oid="tablefrm" onsubmit="return subtablefrm(this.id)" action="/op/orderform_ordertable.do" target="hideframe"> <hk:hide name="oid" value="${oid}"/> <hk:hide name="tableId" value="${tableId}"/> <hk:hide name="companyId" value="${companyId}"/> <table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="90px">就餐人数</td> <td> <hk:text name="num" clazz="text_short_2"/> <div class="error" id="num_error"></div> </td> </tr> <tr> <td width="90px">预计结束时间</td> <td> <hk:text name="time" clazz="text_short_2"/>时间格式为HH:mm <div class="error" id="time_error"></div> </td> </tr> <tr> <td></td> <td> <hk:submit value="view.submit" res="true" clazz="btn"/> </td> </tr> </table> </hk:form>';
	createCenterWindow("table_win",450,250,'预定餐桌',html,"hideWindow('table_win');clearBg();");
}
var err_code_<%=Err.TIME_ERROR %>={objid:"time"};
var err_code_<%=Err.CMPORDERTABLE_PERSONNUM_ERROR%>={objid:"num"};
function subtablefrm(frmid){
	validateClear('time');
	validateClear('num');
	showSubmitDiv(frmid);
	return true;
}
function onordertableerror(error,error_msg,respValue){
	validateErr(getoidparam(error),error_msg);
	hideSubmitDiv();
}
function onordertablesuccess(error,error_msg,respValue){
	tourl("<%=path%>/op/orderform_createok.do?oid=${oid}&companyId=${companyId}");
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>