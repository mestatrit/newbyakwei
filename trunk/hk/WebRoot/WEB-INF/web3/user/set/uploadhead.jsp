<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="html_title" scope="request"><hk:data key="view.user.mgr.head"/></c:set>
<c:set var="mgr_content" scope="request">
<style>
.current img{
margin:10px 10px;
}
</style>
<div>
<h2>用户当前头像</h2>
<div class="bdbtm"></div>
<div class="current">
<img src="${loginUser.head32Pic }"/>
<img src="${loginUser.head48Pic }"/>
<img src="${loginUser.head80Pic }"/>
<span id="imgcondiv"></span>
</div>
</div>
<script type="text/javascript">
function checkfrm(frmid){
	return showSubmitDiv(frmid);
}
function afterSuccess(value,op_func){
	var t=value.split(";");
	getObj("okfrm").picurl.value=t[0];
	getObj("okfrm").width.value=t[1];
	getObj("okfrm").height.value=t[2];
	getObj("okfrm").submit();
}
function afterSubmit(err,error_msg,op_func,obj_id_param){
	if(err!=0){
		hideSubmitDiv();
		alert(error_msg);
	}
}
function showdefimg(obj){
	obj.src='${loginUser.head80Pic}';
}
</script>
<h2>上传新头像</h2>
<hk:form oid="upload_frm" enctype="multipart/form-data" onsubmit="return checkfrm(this.id)" action="/user/set/set_uploadheadpic.do" target="hideframe">
<input type="file" name="f" size="50"/>
<hk:submit value="view.submit" res="true" clazz="btn"/>
</hk:form>
<div class="hide">
<hk:form oid="okfrm" action="/user/set/set_tosetheadweb2.do">
<hk:hide name="picurl" value=""/>
<hk:hide name="width" value=""/>
<hk:hide name="height" value=""/>
</hk:form>
</div>
</c:set>
<jsp:include page="../../inc/usermgr_inc.jsp"></jsp:include>