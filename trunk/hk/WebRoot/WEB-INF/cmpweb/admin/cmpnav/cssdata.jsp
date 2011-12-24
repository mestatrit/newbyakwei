<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpNav"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="js_value" scope="request">
<link rel="stylesheet" href="<%=path %>/cmpwebst4/js/colorpicker/css/colorpicker.css" type="text/css" />
<link rel="stylesheet" media="screen" type="text/css" href="<%=path %>/cmpwebst4/js/colorpicker/css/layout.css" />
<script type="text/javascript" src="<%=path %>/cmpwebst4/js/colorpicker/js/colorpicker.js"></script>
<script type="text/javascript" src="<%=path %>/cmpwebst4/js/colorpicker/js/eye.js"></script>
<script type="text/javascript" src="<%=path %>/cmpwebst4/js/colorpicker/js/utils.js"></script>
</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">${cmpNav.name} 页面颜色配置</div>
		<div class="mod_content">
			<br/>
			<div>
				<form id="stylefrm" onsubmit="return subfrm(this.id)" method="post" action="<%=path %>/epp/web/op/webadmin/admincmpnav_setcssdata.do" target="hideframe">
					<hk:hide name="companyId" value="${companyId}"/>
					<hk:hide name="oid" value="${oid}"/>
					<hk:hide name="ch" value="1"/>
				<table class="nt all" cellpadding="0" cellspacing="0" style="font-size: 14px;color: #5A5858;">
					<tr>
						<td align="right">
						背景颜色
						</td>
						<td>
							<div>
								<div id="block_bgColor" class="color_block" style="background-color: #${cmpNavPageCssObj.bgColor }"></div>
								<div class="color_text">
									#<input id="_bgColor" maxlength="6" type="text" class="text2" name="bgColor" value="${cmpNavPageCssObj.bgColor}"/>
								</div>
								<div class="clr"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td align="right">
						栏目链接颜色
						</td>
						<td>
							<div id="block_navLinkColor" class="color_block" style="background-color: #${cmpNavPageCssObj.navLinkColor }"></div>
							<div class="color_text">
								#<input id="_navLinkColor" maxlength="6" type="text" class="text2" name="navLinkColor" value="${cmpNavPageCssObj.navLinkColor}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						页面其他链接颜色
						</td>
						<td>
							<div id="block_linkColor" class="color_block" style="background-color: #${cmpNavPageCssObj.linkColor }"></div>
							<div class="color_text">
								#<input id="_linkColor" maxlength="6" type="text" class="text2" name="linkColor" value="${cmpNavPageCssObj.linkColor}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						</td>
						<td>
							<hk:submit clazz="btn split-r" value="保存"/>
							<a href="${denc_return_url }">返回</a>
						</td>
					</tr>
				</table>
				</form>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var tmp_show_name='';
$(document).ready(function() {
	$('#_linkColor,#_navLinkColor,#_bgColor').ColorPicker({
		onSubmit: function(hsb, hex, rgb, el) {
			$(el).val(hex);
			$('#block_'+tmp_show_name).css('background-color', '#' + hex);
			$(el).ColorPickerHide();
		},
		onBeforeShow: function () {
			tmp_show_name=this.name;
			$(this).ColorPickerSetColor(this.value);
		}
	})
	.bind('keyup', function(){
		$(this).ColorPickerSetColor(this.value);
	});
});
function subfrm(frmid){
	showGlass(frmid);
	return true;
}
function setok(error,msg,v){
	refreshurl();
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>