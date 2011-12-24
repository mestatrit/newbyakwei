<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="epp.user.setting"/> - ${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<style>
.current img{
margin:10px 10px;
}
</style>
<div class="hcenter" style="width: 800px">
	<div class="userop_l">
		<jsp:include page="set_inc.jsp"></jsp:include>
	</div>
	<div class="userop_r">
		<div>
			<h2 class="bdtm"><hk:data key="epp.user.headpic"/></h2>
			<div class="bdbtm"></div>
			<div class="current">
				<img src="${loginUser.head32Pic }?<%=Math.random() %>"/>
				<img src="${loginUser.head48Pic }?<%=Math.random() %>"/>
				<img src="${loginUser.head80Pic }?<%=Math.random() %>"/>
			</div>
			<h2><hk:data key="epp.user.uploadnewimage"/></h2>
			<div class="b"><hk:data key="epp.cmpbbs.uploadimg.tip"/></div>
			<hk:form oid="upload_frm" enctype="multipart/form-data" onsubmit="return checkfrm(this.id)" action="/epp/web/op/user/set_sethead.do" target="hideframe">
				<hk:hide name="ch" value="1"/>
				<hk:hide name="companyId" value="${companyId}"/>
				<input type="file" name="f" size="50"/>
				<hk:submit value="epp.submit" res="true" clazz="btn"/>
			</hk:form>
			<div class="infowarn" id="uploadimg_msg"></div>
			<div class="hide">
				<hk:form method="get" oid="okfrm" action="/epp/web/op/user/set_sethead2.do">
					<hk:hide name="companyId" value="${companyId}"/>
					<hk:hide name="picurl" value=""/>
					<hk:hide name="width" value=""/>
					<hk:hide name="height" value=""/>
				</hk:form>
			</div>
		</div>
	</div>
	<div class="clr"></div>
</div>
<script type="text/javascript">
function checkfrm(frmid){
	showGlass(frmid);
	setHtml('uploadimg_msg','');
	return true;
}
function onheadok(err,msg,v){
	var t=v.split(";");
	getObj("okfrm").picurl.value=t[0];
	getObj("okfrm").width.value=t[1];
	getObj("okfrm").height.value=t[2];
	getObj("okfrm").submit();
}
function onheaderror(err,error_msg,op_func,obj_id_param){
	setHtml('uploadimg_msg',error_msg);
	hideGlass();
}
function showdefimg(obj){
	obj.src='${loginUser.head80Pic}';
}
</script>
</c:set>
<jsp:include page="../../inc/frame.jsp"></jsp:include>