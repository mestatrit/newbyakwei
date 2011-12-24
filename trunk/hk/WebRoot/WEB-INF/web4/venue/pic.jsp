<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords" content="${companyPhoto.name}|${company.name }|<hk:data key="view2.website.title"/>"/>
		<link rel="stylesheet" type="text/css" href="<%=path%>/webst4/css/a.css" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/webst4/css/photo2.css" />
		<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/pub.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/photo2.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/jquery-ui-1.7.2.custom.min.js"></script>
		<title>${companyPhoto.name} ${company.name } - <hk:data key="view2.website.title"/> </title>
	</head>
	<body>
		<div class="hcenter bg0 rd" style="width:1000px;margin-top: 0px;padding-bottom: 20px;"><iframe id="hideframe" name="hideframe" class="hide"></iframe>
			<div style="padding: 10px;padding-left: 15px;padding-right: 15px;">
				<div class="rcon">
					<div style="margin-bottom: 20px;">
					<a class="more2" href="/venue/${companyId }"><hk:data key="view2.return"/></a>
					</div>
					<div style="margin-bottom:10px;">
						<a id="id_pre_btn" class="divbtn" onmousedown="mouseDownPre();" onmouseup="mouseUp();">上一页</a>
					</div>
					<div class="smphotocon">
						<div id="smphoto" class="smphoto">
						</div>
					</div>
					<div>
						<a id="id_next_btn"  class="divbtn" onmousedown="mouseDownNext();" onmouseup="mouseUp();">下一页</a>
					</div>
				</div>
				<div class="bigphotoouter">
					<div style="padding:10px;">
						<div class="bdtm" style="margin-bottom: 5px;">
							<div class="f_l b" style="font-size: 16px;">
								<div class="f_l" style="width:500px;margin-left: 20px;">
									<div id="pic_name"></div>
								</div>
								<div class="clr"></div>
							</div>
							<c:if test="${userLogin}">
								<div class="f_r" style="font-size: 16px">
									<div class="tip_todo_unchecked f_l" style="width: 80px;">
										<input style="margin: 0;padding: 0;" id="vote" type="checkbox" onclick="votecurrentpic()"/><label for="vote">顶一下</label>
									</div>
									<div class="clr"></div>
								</div>
							</c:if>
							<div class="clr"></div>
						</div>
						<div id="bigphoto" class="bigphoto">
						</div>
						<div style="margin-top: 20px;">
							
							<div class="f_r rd bg1" style="padding: 5px;margin-right: 20px;">
								<a href="<%=path %>/h4/op/user/venue_uploadpic.do?companyId=${companyId}"><hk:data key="view2.company.uploadpic"/></a>
								| 
								<a id="id_delpic" href="javascript:delpic()">删除图片</a>
								<c:if test="${cmpedit}"> | <a id="id_setheadpath" href="javascript:setheadpath()">设置头图</a></c:if>
							</div>
							<div class="clr"></div>
						</div>
						<div class="hcenter" style="width: 500px;margin-top: 20px;">
							<c:if test="${userLogin}">
								<div class="mod">
									<div class="mod_content">
										<div style="width: 480px;">
											<form id="cmtfrm" onsubmit="return subcmtfrm(this.id)" action="<%=path %>/h4/op/user/venue_createphotocmt.do" target="hideframe">
												<input id="_photoId" type="hidden" name="photoId" value=""/>
												<textarea name="content" style="width:480px;height:100px" onkeydown="subcmtfrm2(event)"></textarea>
												<div id="pic_cmt_info" class="infowarn"></div>
												<div style="text-align: right;"><hk:submit value="说一句" clazz="btn5"/></div>
											</form>
										</div>
									</div>
								</div>
							</c:if>
							<div id="user_pic_cmt_mod" class="mod" style="display: none;">
								<div class="mod_title">大家的点评<a name="cmt"></a></div>
								<div class="mod_content">
									<div id="cmtdata"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="footer">
			<div>
				<a>关于火酷</a> |
				<a> 广告服务</a> |
				<a>招聘信息</a> |
				<a>建议和意见</a> |
				<a>友情链接</a> |
				<a>联系我们 </a> 
			</div>
			<div>
				* Copyright ® 2009 huoku.com All rights reserved 京ICP备09054036号
			</div>
		</div>
<script type="text/javascript">
var path="<%=path%>";
var cmpheadpath="${company.headPath}";
var companyname="${company.name}";
var cmpedit=false;
<c:if test="${cmpedit }">
cmpedit=true;
</c:if>
var companyId=${companyId};
var photoId=${photoId};
loadpic(photoId);
var slideshow=getObj('smphoto');
smphoto_top=$('#smphoto').offset().top;
wheel(slideshow,function(e,delta){
	if(photo.length<=4){
		return;
	}
	if(slider_timeid!=null){//翻滚效果未完成
		return;
	}
	if(delta<0){
		sliderUp();
	}
	else{
		sliderDown();
	}
},false);
function subcmtfrm(frmid){
	getObj('_photoId').value=map[current_pic_idx];
	showGlass(frmid);
	return true;
}
function subcmtfrm2(event){
	if((event.ctrlKey)&&(event.keyCode==13)){
		subcmtfrm('cmtfrm');
		getObj('cmtfrm').submit();
	}
}
function showNewCmt(html,id){
	getObj('cmtfrm').content.value='';
	insertObjAfter(html,'cmtline');
	hideGlass();
	$('#user_pic_cmt_mod').css('display','');
	$('#cmt'+id).effect('highlight',{},1000,function(){});
	//window.location.hash='cmt';
}
function cmterror(error,msg,v){
	setHtml('pic_cmt_info',msg);
	hideGlass();
}
</script>
	</body>
</html>