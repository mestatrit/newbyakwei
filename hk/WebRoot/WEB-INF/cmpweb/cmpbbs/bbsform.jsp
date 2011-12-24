<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="bbsfrm" method="post" enctype="multipart/form-data" onsubmit="return subbbsfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="ch" value="1"/>
	<hk:hide name="bbsId" value="${bbsId}"/>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="120" align="right">
				<hk:data key="cmpbbs.title"/>：
			</td>
			<td>
				<hk:text name="title" clazz="text" style="width:400px" value="${cmpBbs.title}" maxlength="30"/>
				<div class="infowarn" id="_title"></div>
			</td>
		</tr>
		<tr>
			<td align="right">
				<hk:data key="cmpbbs.kind"/>：
			</td>
			<td>
				<%EppViewUtil.loadCmpBbsKindList(request); %>
				<hk:select name="kindId" checkedvalue="${cmpBbs.kindId}" forcecheckedvalue="${kindId}">
					<c:forEach var="kind" items="${cmpbbskindlist}">
						<hk:option value="${kind.kindId}" data="${kind.name}"/>
					</c:forEach>
				</hk:select>
				<div class="infowarn" id="_kindid"></div>
			</td>
		</tr>
		<tr>
			<td align="right">
				<hk:data key="cmpbbs.content"/>：
			</td>
			<td>
				<hk:textarea oid="bbs_content" name="content" style="width: 700px;height: 300px" value="${cmpBbsContent.content}" />
				<div class="infowarn" id="_content"></div>
			</td>
		</tr>
		<tr>
			<td align="right">
				<hk:data key="epp.imgfile"/>：
			</td>
			<td>
				<c:if test="${not empty cmpBbs.picpath}">
					<div class="divrow" id="imgcon">
						<img src="${cmpBbs.pic120Url }"/> 
						<a href="javascript:clearpic(${bbsId })"><hk:data key="epp.deletepic"/></a>
					</div>
				</c:if>
				<input type="file" name="f" size="50"/>
				<div class="b"><hk:data key="epp.cmpbbs.uploadimg.tip"/></div>
				<div class="infowarn" id="_file"></div>
			</td>
		</tr>
		<tr>
			<td>
			</td>
			<td>
				<div align="center">
				<hk:submit clazz="btn split-r" res="true" value="epp.submit"/> 
				<a href="${denc_return_url }"><hk:data key="epp.return"/></a>
				</div>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPBBS_TITLE_ERROR %>={objid:"_title"};
var err_code_<%=Err.CMPBBSCONTENT_CONTENT_ERROR %>={objid:"_content"};
var err_code_<%=Err.CMPBBS_KINDID_ERROR %>={objid:"_kindid"};
var err_code_<%=Err.UPLOAD_ERROR %>={objid:"_file"};
var err_code_<%=Err.IMG_FMT_ERROR %>={objid:"_file"};
var err_code_<%=Err.IMG_OUTOFSIZE_ERROR %>={objid:"_file"};
var err_code_<%=Err.MUST_PIC_UPLOAD %>={objid:"_file"};
var err_code_<%=Err.ONLY_PIC_UPLOAD %>={objid:"_file"};
function subbbsfrm(frmid){
	setHtml('_title','');
	setHtml('_content','');
	setHtml('_file','');
	showGlass(frmid);
	return true;
}
function clearpic(bbsId){
	if(window.confirm('<hk:data key="epp.delete.confirm"/>')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/cmpbbs_clearpic.do?companyId=${companyId}&bbsId=${bbsId}",
			cache:false,
	    	dataType:"html",
			success:function(data){
				setHtml('imgcon','');
			}
		});
	}
}
function keysubmit(event){
	if((event.ctrlKey)&&(event.keyCode==13)){
		if(subbbsfrm('bbsfrm')){
			getObj('bbsfrm').submit();
		}
	}
}
$(document).ready(function() {
	$('#bbs_content').bind('keydown',function(event){
		keysubmit(event);
		});
});
</script>