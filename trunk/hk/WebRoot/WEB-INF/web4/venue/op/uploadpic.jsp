<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.uploadpicforcompany" arg0="${company.name}"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 700px;">
<h1><hk:data key="view2.uploadpicforcompany" arg0="${company.name}"/></h1>
<div class="b">图片格式只支持gif,png,jpg，且每张图片大小不能超过5M</div> <br/>
<form id="picfrm" method="post" enctype="multipart/form-data" onsubmit="return subpicfrm(this.id)" action="<%=path %>/h4/op/user/venue_uploadpic.do" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<c:forEach var="i" begin="0" end="4">
		<div class="row"><input type="file" name="f${i }" size="50"/></div>
	</c:forEach>
	<div style="padding-left: 150px;">
	<div id="info" class="infowarn"></div>
	<hk:submit value="view2.submit" res="true" clazz="btn split-r"/> 
	<a href="/venue/${companyId }/"><hk:data key="view2.return"/></a>
	</div>
</form>
</div>
<div class="hide">
<form id="editpicfrm" method="get" action="<%=path %>/h4/op/user/venue_editpic.do">
<hk:hide name="companyId" value="${companyId}"/>
<div id="hidecon">
</div>
</form>
</div>
<script type="text/javascript">
function subpicfrm(frmid){
	showGlass(frmid);
	return true;
}
function uploaderror(error,msg,v){
	hideGlass();
	setHtml('info',msg);
}
function upload(error,msg,v){
	var pic=v.split(',');
	var s='';
	for(var i=0;i<pic.length;i++){
		s+='<input type="hidden" name="photoId" value="'+pic[i]+'"/>';
	}
	setHtml('hidecon',s);
	getObj('editpicfrm').submit();
}
</script>
</c:set><jsp:include page="../../inc/frame.jsp"></jsp:include>