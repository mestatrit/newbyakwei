<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="epp.user.setting"/>  - ${o.name}</c:set>
<c:set var="js_value" scope="request">
<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/mod/pub/css/imgcut/imgareaselect-default.css" />
<script type="text/javascript" src="<%=path %>/webst4/mod/pub/js/imgcut/jquery.imgareaselect.min.js"></script>
</c:set>
<c:set var="html_body_content" scope="request">
<style>
.preview{
	position: relative;
	overflow: hidden;
	width: 180px;
	height: 180px;
}
</style>
<div class="hcenter" style="width: 800px">
	<div class="userop_r" style="width: 750px">
		<div>
		<h2><hk:data key="view.user.mgr.head"/></h2>
		<div class="bdbtm"></div>
		<div style="padding:10px" class="text_14">
		<strong><hk:data key="view.user.mgr.head.tip"/></strong>
		</div>
		<div class="current">
		<table style="width:100%">
			<tr>
				<td width="50%">
					<img id="pp" src="${picurl }"/>
				</td>
				<td width="50%">
					<div style="margin-left: 20px;">
						<div class="preview">
							<img id="pp2" src="${picurl }" style="position: relative;" />
						</div>
						<br/>
						<hk:form oid="op_frm" onsubmit="return checkopfrm(this.id)" action="/epp/web/op/user/set_sethead2.do" target="hideframe">
						<hk:hide name="companyId" value="${companyId}"/>
						<hk:hide name="ch" value="1"/>
						<hk:hide name="picurl" value="${picurl}"/>
						<hk:hide name="x1" value=""/>
						<hk:hide name="x2" value=""/>
						<hk:hide name="y1" value=""/>
						<hk:hide name="y2" value=""/>
						<hk:submit value="epp.user.heas.save" clazz="btn" res="true"/>
						</hk:form>
					</div>
				</td>
			</tr>
		</table>
		</div>
		</div>
	</div>
	<div class="clr"></div>
</div>
<script type="text/javascript">
function checkopfrm(frmid){
	showGlass(frmid);
	return true;
}
function setheadok(err,error_msg,v){
	tourl('<%=path%>/epp/web/op/user/set_sethead.do?companyId=${companyId}');
}
function sethederror(err,error_msg,v){
	alert(error_msg);
}
function preview(img, selection) {
    var scaleX = 180 / (selection.width || 1);
    var scaleY = 180 / (selection.height || 1);
  
    $('#pp2').css({
        width: Math.round(scaleX * ${width}) + 'px',
        height: Math.round(scaleY * ${height}) + 'px',
        marginLeft: '-' + Math.round(scaleX * selection.x1) + 'px',
        marginTop: '-' + Math.round(scaleY * selection.y1) + 'px'
    });
}
$(function(){
    $('#pp').imgAreaSelect({
        onSelectEnd: function(img, selection){
            $('input[name=x1]').val(selection.x1);
            $('input[name=y1]').val(selection.y1);
            $('input[name=x2]').val(selection.x2);
            $('input[name=y2]').val(selection.y2);
        },
        x1: 0,
        y1: 0,
        x2: 180,
        y2: 180,
        minWidth: 180,
        minHeight: 180,
        aspectRatio: '1:1',
        onSelectChange: preview
    });
});
</script>
</c:set>
<jsp:include page="../../inc/frame.jsp"></jsp:include>