<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">${cmpProduct.name}</c:set>
<c:set var="mgr_content" scope="request">
<div class="text_14">
<c:if test="${fn:length(list)<30}">
<hk:button value="上传图片" clazz="btn" onclick="touploadphoto()"/> 
</c:if>
<hk:button value="返回产品" clazz="btn" onclick="toproduct()"/> 
<br/><br/>
<c:if test="${fn:length(list)==0}">
没有产品图片
</c:if>
<c:if test="${fn:length(list)>0}">
<table class="infotable" cellpadding="0" cellspacing="0">
<c:forEach var="p" items="${list}">
	<tr>
		<td width="90px" style="padding:5px 0px;"><a href="javascript:showbig('${p.pic240 }')"><img src="${p.pic60 }"/></a></td>
		<td>
			<c:if test="${!(headPath eq p.path)}"><a href="javascript:settop(${p.oid })">设为头图</a>/</c:if>
			<a href="javascript:delphoto(${p.oid })">删除</a>
		</td>
	</tr>
</c:forEach>
</table>
</c:if>
</div>
<script type="text/javascript">
function toproduct(){
	tourl('<%=path %>/e/op/product/op_productlistweb.do?companyId=${companyId }');
}
function touploadphoto(){
	createBg();
	var html='<div><strong>图片文件大小不能超过1M，否则不能上传成功</strong> <hk:form oid="photofrm" onsubmit="return subphotofrm(this.id)" enctype="multipart/form-data" action="/e/op/cmpproductphoto_upload.do" target="hideframe"> <hk:hide name="companyId" value="${companyId}"/> <hk:hide name="productId" value="${productId}"/> <strong>产品最多上传30张图片</strong><br/> <%for(int i=0;i<6;i++){ %> <input type="file" size="50" name="f<%=i %>" class="fileipt"/><br/> <%} %> <hk:submit value="view.upload" res="true" clazz="btn"/> </hk:form> </div>';
	createCenterWindow("photo_win",500,400,'上传图片',html,"hideWindow('photo_win');clearBg();");
}
function subphotofrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function onuploadok(error,error_msg,op_func,obj_id_param,respValue){
	if(parseInt(respValue)>0){
		alert("有"+respValue+"张图片没有上传成功");
	}
	refreshurl();
}
function onuploaderror(error,error_msg,op_func,obj_id_param,respValue){
	if(error==<%=Err.CMPPRODUCTPHOTO_OUT_OF_LIMIT %>){
		alert("由于图片超过30张，只有"+respValue+"张上传成功");
	}
	refreshurl();
}
function showbig(url){
	var html='<div><img src="'+url+'"/></div>';
	createCenterWindow('img_win',300,300,'',html,"hideWindow('img_win')");
}
function delphoto(oid){
	if(window.confirm('确实要删除？')){
		tourl('<%=path%>/e/op/cmpproductphoto_del.do?productId=${productId}&companyId=${companyId}&oid='+oid);
	}
}
function settop(oid){
	tourl('<%=path%>/e/op/cmpproductphoto_sethead.do?productId=${productId}&companyId=${companyId}&oid='+oid);
}
</script>
</c:set>
<jsp:include page="../../../inc/mgr_inc.jsp"></jsp:include>