<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">${cmpTablePhotoSet.title}</c:set>
<c:set var="mgr_content" scope="request">
<div class="text_14">
${cmpTablePhotoSet.title }<br/>
${cmpTablePhotoSet.intro }<br/>
<c:if test="${fn:length(photolist)==0}">
</c:if>
<c:if test="${fn:length(photolist)<5}">
<hk:button value="上传图片" clazz="btn split-r" onclick="touploadphoto()"/> 
</c:if>
<hk:button value="回到图集" clazz="btn split-r" onclick="tophotoset()"/>
<c:if test="${tableId>0}">
<hk:button value="选中该图集" clazz="btn split-r" onclick="selset(${setId})"/>
</c:if>
<c:if test="${fn:length(photolist)>0}">
	<table class="infotable" cellpadding="0" cellspacing="0">
		<c:forEach var="p" items="${photolist}">
			<tr onmouseover="this.className='bg2';" onmouseout="this.className='';">
				<td width="200px" style="padding:5px 0px;">
				<a href="javascript:showbig('${p.pic240 }')"><img src="${p.pic60 }"/></a><br/>
				${p.name } <a id="edit${p.oid }" href="javascript:toedit(${p.oid })">修改标题</a>
				</td>
				<td width="150px">
					<a id="del${p.oid }" href="javascript:delphoto(${p.oid })">删除</a>
					<c:if test="${cmpTablePhotoSet.path!=p.path}">
					/ <a id="head${p.oid }" href="javascript:setphotosethead(${p.oid })">设为头图</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<hk:form clazz="hide" method="get" action="/e/op/auth/table/photo_toedit.do">
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="setId" value="${setId}"/>
	<div id="photoids"></div>
</hk:form>
</div>
<script type="text/javascript">
var err_code_<%=Err.CMPTABLEPHOTO_NMAE_ERROR %>={objid:"name"};
function setphotosethead(oid){
	showSubmitDivForObj('head'+oid);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/table/photo_setphotosethead.do?companyId=${companyId}&setId=${setId}&oid='+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function tophotoset(){
	tourl("<%=path%>/e/op/auth/table/photo_photosetlist.do?companyId=${companyId}&tableId=${tableId}");
}
function selset(setId){
	tourl("<%=path%>/e/op/auth/table/photo_selset.do?companyId=${companyId}&tableId=${tableId}&setId="+setId);
}
function toedit(oid){
	showSubmitDivForObj('edit'+oid);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/table/photo_loadphoto.do?companyId=${companyId}&oid='+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			hideSubmitDiv();
			createBg();
			createCenterWindow("photo_win",500,200,'上传图片',data,"hideWindow('photo_win');clearBg();");
		}
	});
}
function subphotofrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function onupdatephotonameerror(error,error_msg,respValue){
	validateErr(getoidparam(error), error_msg);
	clearBg();
}
function onupdatephotonamesuccess(error,error_msg,respValue){
	refreshurl();
}
function touploadphoto(){
	createBg();
	var html='<div><strong>图集最多只能上传5张图片且每张图片大小不能超过1M，否则不能上传成功</strong> <hk:form oid="photofrm" onsubmit="return subphotofrm(this.id)" enctype="multipart/form-data" action="/e/op/auth/table/photo_upload.do" target="hideframe"> <hk:hide name="companyId" value="${companyId}"/><hk:hide name="setId" value="${setId}"/> <hk:hide name="tableId" value="${tableId}"/> <strong>产品最多上传5张图片</strong><br/> <%for(int i=0;i<5;i++){ %> <input type="file" size="50" name="f<%=i %>" class="fileipt"/><br/> <%} %> <div align="center"><hk:submit value="view.upload" res="true" clazz="btn"/></div> </hk:form> </div>';
	createCenterWindow("photo_win",500,400,'上传图片',html,"hideWindow('photo_win');clearBg();");
}
function subphotofrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function onuploadsuccess(error,error_msg,respValue){
	if(parseInt(respValue)>0){
		alert("有"+respValue+"张图片没有上传成功");
	}
	refreshurl();
}
function onuploaderror(error,error_msg,respValue){
	if(error==<%=Err.CMPTABLEPHOTO_OUT_OF_LIMIT %>){
		alert("由于图片超过5张，只有"+respValue+"张上传成功");
	}
	refreshurl();
}
function showbig(url){
	var html='<div><img src="'+url+'"/></div>';
	createCenterWindow('img_win',300,350,'',html,"hideWindow('img_win')");
}
function delphoto(oid){
	if(window.confirm('确实要删除？')){
		showSubmitDivForObj('del'+oid);
		$.ajax({
			type:"POST",
			url:'<%=path %>/e/op/auth/table/photo_del.do?companyId=${companyId}&oid='+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
</script>
</c:set>
<jsp:include page="../../../inc/mgr_inc.jsp"></jsp:include>