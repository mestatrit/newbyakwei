<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="epp_other_value" scope="request">
<link rel="stylesheet" href="<%=path %>/cmpwebst4/mod/pub/js/colorpicker/css/colorpicker.css" type="text/css" />
<script type="text/javascript" src="<%=path %>/cmpwebst4/mod/pub/js/colorpicker/js/colorpicker.js"></script>
<script type="text/javascript" src="<%=path %>/cmpwebst4/mod/pub/js/colorpicker/js/eye.js"></script>
<script type="text/javascript" src="<%=path %>/cmpwebst4/mod/pub/js/colorpicker/js/utils.js"></script>
</c:set>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<div class="tit">${cmpOrgNav.name } 更改配色</div>
	<div class="mod_content">
		<div>
			<form id="stylefrm" onsubmit="return subfrm(this.id)" method="post" action="<%=path %>/epp/web/org/org_updatestyle.do" target="hideframe">
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:hide name="orgId" value="${orgId}"/>
				<hk:hide name="ch" value="1"/>
				<table class="nt all" cellpadding="0" cellspacing="0" style="font-size: 14px;color: #5A5858;">
					<tr>
						<td width="120" align="right">背景色</td>
						<td>
							<div>
								<div id="block_bgColor" class="color_block" style="background-color: #${cmpOrgStyle.bgColor }"></div>
								<div class="color_text">
									#<input id="_bgColor" maxlength="6" type="text" class="text2" name="bgColor" value="${cmpOrgStyle.bgColor}"/>
								</div>
								<div class="clr"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td width="120" align="right">标题颜色</td>
						<td>
							<div>
								<div id="block_titleColor" class="color_block" style="background-color: #${cmpOrgStyle.titleColor }"></div>
								<div class="color_text">
									#<input id="_titleColor" maxlength="6" type="text" class="text2" name="titleColor" value="${cmpOrgStyle.titleColor}"/>
								</div>
								<div class="clr"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td width="120" align="right">链接颜色</td>
						<td>
							<div>
								<div id="block_linkColor" class="color_block" style="background-color: #${cmpOrgStyle.linkColor }"></div>
								<div class="color_text">
									#<input id="_linkColor" maxlength="6" type="text" class="text2" name="linkColor" value="${cmpOrgStyle.linkColor}"/>
								</div>
								<div class="clr"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td width="120" align="right">链接Hover颜色</td>
						<td>
							<div>
								<div id="block_linkHoverColor" class="color_block" style="background-color: #${cmpOrgStyle.linkHoverColor }"></div>
								<div class="color_text">
									#<input id="_linkHoverColor" maxlength="6" type="text" class="text2" name="linkHoverColor" value="${cmpOrgStyle.linkHoverColor}"/>
								</div>
								<div class="clr"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td width="160" align="right">导航链接Hover背景颜色</td>
						<td>
							<div>
								<div id="block_navLinkHoverBgColor" class="color_block" style="background-color: #${cmpOrgStyle.navLinkHoverBgColor }"></div>
								<div class="color_text">
									#<input id="_navLinkHoverBgColor" maxlength="6" type="text" class="text2" name="navLinkHoverBgColor" value="${cmpOrgStyle.navLinkHoverBgColor}"/>
								</div>
								<div class="clr"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td align="right">
						</td>
						<td>
							<hk:submit clazz="btn split-r" value="保存"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
function updateerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function updateok(error,msg,v){
	refreshurl();
}
function subfrm(frmid){
	showGlass(frmid);
	return true;
}
var tmp_show_name='';
$(document).ready(function() {
	$('#_bgColor,#_titleColor,#_linkColor,#_linkHoverColor,#_navLinkHoverBgColor').ColorPicker({
		onSubmit: function(hsb, hex, rgb, el) {
			$(el).val(hex);
			$('#block_'+tmp_show_name).css('backgroundColor', '#' + hex);
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
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>