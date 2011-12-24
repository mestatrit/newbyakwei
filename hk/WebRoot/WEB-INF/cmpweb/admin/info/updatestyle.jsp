<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
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
			<div class="mod_title">设置网站配色</div>
			<div class="mod_content">
				<br/>
				<form id="stylefrm" onsubmit="return substylefrm(this.id)" method="post" action="<%=path %>/epp/web/op/webadmin/info_updatestyle.do" target="hideframe">
					<hk:hide name="companyId" value="${companyId}"/>
					<hk:hide name="ch" value="1"/>
				<table class="nt all" cellpadding="0" cellspacing="0" style="font-size: 14px;color: #5A5858;">
					<tr>
						<td align="right">
						一级栏目背景颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.columnBgColor}">${cmpWebColor.columnBgColor}</c:if>
							<c:if test="${empty cmpWebColor.columnBgColor}">666666</c:if>
							</c:set>
							<div>
								<div id="column_bg_columnBgColor" class="color_block" style="background-color: #${color }"></div>
								<div class="color_text">
									#<input id="id_column_bg_columnBgColor" maxlength="6" type="text" class="text2" name="columnBgColor" value="${color}"/>
								</div>
								<div class="clr"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td align="right">
						一级栏目文字链接颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.columnLinkColor}">${cmpWebColor.columnLinkColor}</c:if>
							<c:if test="${empty cmpWebColor.columnLinkColor}">ffffff</c:if>
							</c:set>
							<div id="column_bg_columnLinkColor" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_columnLinkColor" maxlength="6" type="text" class="text2" name="columnLinkColor" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						当前一级栏目文字链接背景颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.columnLinkHoverBgColor}">${cmpWebColor.columnLinkHoverBgColor}</c:if>
							<c:if test="${empty cmpWebColor.columnLinkHoverBgColor}">999999</c:if>
							</c:set>
							<div id="column_bg_columnLinkHoverBgColor" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_columnLinkHoverBgColor" maxlength="6" type="text" class="text2" name="columnLinkHoverBgColor" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						二级栏目标题背景颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.column2BgColor}">${cmpWebColor.column2BgColor}</c:if>
							<c:if test="${empty cmpWebColor.column2BgColor}">004D39</c:if>
							</c:set>
							<div id="column_bg_column2BgColor" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_column2BgColor" maxlength="6" type="text" class="text2" name="column2BgColor" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						二级栏目标题颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.column2Color}">${cmpWebColor.column2Color}</c:if>
							<c:if test="${empty cmpWebColor.column2Color}">ffffff</c:if>
							</c:set>
							<div id="column_bg_column2Color" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_column2Color" maxlength="6" type="text" class="text2" name="column2Color" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						二级栏目链接颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.column2NavLinkColor}">${cmpWebColor.column2NavLinkColor}</c:if>
							<c:if test="${empty cmpWebColor.column2NavLinkColor}">5A5858</c:if>
							</c:set>
							<div id="column_bg_column2NavLinkColor" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_column2NavLinkColor" maxlength="6" type="text" class="text2" name="column2NavLinkColor" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						二级栏目链接选中颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.column2NavLinkActiveColor}">${cmpWebColor.column2NavLinkActiveColor}</c:if>
							<c:if test="${empty cmpWebColor.column2NavLinkActiveColor}">A10000</c:if>
							</c:set>
							<div id="column_bg_column2NavLinkActiveColor" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_column2NavLinkActiveColor" maxlength="6" type="text" class="text2" name="column2NavLinkActiveColor" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						首页产品区域背景颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.homeProductBgColor}">${cmpWebColor.homeProductBgColor}</c:if>
							<c:if test="${empty cmpWebColor.homeProductBgColor}">eef9f1</c:if>
							</c:set>
							<div id="column_bg_homeProductBgColor" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_homeProductBgColor" maxlength="6" type="text" class="text2" name="homeProductBgColor" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						首页模块标题颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.homeModTitleLinkColor}">${cmpWebColor.homeModTitleLinkColor}</c:if>
							<c:if test="${empty cmpWebColor.homeModTitleLinkColor}">5A5858</c:if>
							</c:set>
							<div id="column_bg_homeModTitleLinkColor" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_homeModTitleLinkColor" maxlength="6" type="text" class="text2" name="homeModTitleLinkColor" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						首页模块链接颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.homeTitleLinkColor}">${cmpWebColor.homeTitleLinkColor}</c:if>
							<c:if test="${empty cmpWebColor.homeTitleLinkColor}">5A5858</c:if>
							</c:set>
							<div id="column_bg_homeTitleLinkColor" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_homeTitleLinkColor" maxlength="6" type="text" class="text2" name="homeTitleLinkColor" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						用户导航背景颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.userNavBgColor}">${cmpWebColor.userNavBgColor}</c:if>
							<c:if test="${empty cmpWebColor.userNavBgColor}">E5E5E5</c:if>
							</c:set>
							<div id="column_bg_userNavBgColor" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_userNavBgColor" maxlength="6" type="text" class="text2" name="userNavBgColor" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						用户导航链接颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.userNavLinkColor}">${cmpWebColor.userNavLinkColor}</c:if>
							<c:if test="${empty cmpWebColor.userNavLinkColor}">3AA9D6</c:if>
							</c:set>
							<div id="column_bg_userNavLinkColor" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_userNavLinkColor" maxlength="6" type="text" class="text2" name="userNavLinkColor" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						网页文字颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.fontColor}">${cmpWebColor.fontColor}</c:if>
							<c:if test="${empty cmpWebColor.fontColor}">5A5858</c:if>
							</c:set>
							<div id="column_bg_fontColor" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_fontColor" maxlength="6" type="text" class="text2" name="fontColor" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						网页文字链接颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.linkColor}">${cmpWebColor.linkColor}</c:if>
							<c:if test="${empty cmpWebColor.linkColor}">2398C9</c:if>
							</c:set>
							<div id="column_bg_linkColor" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_linkColor" maxlength="6" type="text" class="text2" name="linkColor" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						按钮边框颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.buttonBorderColor}">${cmpWebColor.buttonBorderColor}</c:if>
							<c:if test="${empty cmpWebColor.buttonBorderColor}">CBEB7F</c:if>
							</c:set>
							<div id="column_bg_buttonBorderColor" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_buttonBorderColor" maxlength="6" type="text" class="text2" name="buttonBorderColor" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						按钮背景颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.buttonBgColor}">${cmpWebColor.buttonBgColor}</c:if>
							<c:if test="${empty cmpWebColor.buttonBgColor}">A9CD20</c:if>
							</c:set>
							<div id="column_bg_buttonBgColor" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_buttonBgColor" maxlength="6" type="text" class="text2" name="buttonBgColor" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						按钮文字颜色
						</td>
						<td>
							<c:set var="color">
							<c:if test="${not empty cmpWebColor.buttonColor}">${cmpWebColor.buttonColor}</c:if>
							<c:if test="${empty cmpWebColor.buttonColor}">FFFFFF</c:if>
							</c:set>
							<div id="column_bg_buttonColor" class="color_block" style="background-color: #${color }"></div>
							<div class="color_text">
								#<input id="id_column_bg_buttonColor" maxlength="6" type="text" class="text2" name="buttonColor" value="${color}"/>
							</div>
							<div class="clr"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						</td>
						<td>
							<hk:submit clazz="btn split-r" value="保存"/>
							<a href="<%=path %>/epp/web/op/webadmin/info_styledata.do?companyId=${companyId}">返回</a>
						</td>
					</tr>
				</table>
				</form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function showcolor(a,b){
	getObj('column_bg_'+a).style.cssText="background-color:#"+b;
}
function substylefrm(frmid){
	showGlass(frmid);
	return true;
}
function updateok(error,msg,v){
	refreshurl();
}
var tmp_show_name='';
$(document).ready(function() {
	$('#id_column_bg_buttonBorderColor,#id_column_bg_buttonBgColor,#id_column_bg_buttonColor,#id_column_bg_fontColor,#id_column_bg_homeModTitleLinkColor,#id_column_bg_column2NavLinkActiveColor,#id_column_bg_column2NavLinkColor,#id_column_bg_columnBgColor,#id_column_bg_columnLinkColor,#id_column_bg_columnLinkHoverBgColor,#id_column_bg_homeProductBgColor,#id_column_bg_homeTitleLinkColor,#id_column_bg_linkColor,#id_column_bg_column2BgColor,#id_column_bg_column2Color,#id_column_bg_userNavBgColor,#id_column_bg_userNavLinkColor').ColorPicker({
		onSubmit: function(hsb, hex, rgb, el) {
			$(el).val(hex);
			$('#column_bg_'+tmp_show_name).css('backgroundColor', '#' + hex);
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