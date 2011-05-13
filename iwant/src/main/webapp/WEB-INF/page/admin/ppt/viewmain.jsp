<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request"><hk:value value="${project.name}"/>项目简介 - <hk:value value="${ppt.name}"/></c:set>
<c:set var="html_head_value" scope="request">
<script type="text/javascript">

</script>
</c:set>
<c:set scope="request" var="mgr_body_content">
<div class="mod">
	<div class="mod_title">
		<hk:value value="${project.name}"/>项目简介 - <hk:value value="${ppt.name}"/>
	</div>
	<div class="mod_content">
		<div>
			<table class="formt" cellpadding="0" cellspacing="0">
				<c:if test="${not empty ppt.pic_path}">
				<tr>
					<td width="90" align="right">&nbsp;</td>
					<td>
						<img src="${ppt.pic1Url}"/>
					</td>
				</tr>
				</c:if>
				<tr>
					<td width="90" align="right">名称</td>
					<td>
						<hk:value value="${ppt.name }" onerow="true"/>
					</td>
				</tr>
				<tr>
					<td width="90" align="right"></td>
					<td>
						<a href="javascript:toupdate()" class="split-r">修改</a>
						<a href="javascript:opdel()" class="split-r">删除</a>
						<a href="${appctx_path }/mgr/ppt_back.do?projectid=${ppt.projectid}">返回</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<div class="mod">
	<div class="mod_title">
		<hk:value value="${ppt.name}"/>的内容
	</div>
	<div class="mod_content">
		<c:if test="${canaddslide}">
			<div>
				<input type="button" class="btn" onclick="tocreateslide()" value="添加内容"/>
			</div>
		</c:if>
		<ul class="rowlist">
			<c:forEach var="slide" items="${list }" varStatus="idx">
			<li id="row_${slide.slideid}">
				<div>
					<p align="center"><hk:value value="${slide.title }"/></p>
					<p align="center" style="width: 700px;overflow: hidden;"><img id="img_${slide.slideid}" src="${slide.pic2Url}"/></p>
					<p>${slide.descr}</p>
					<p align="center">
					<c:if test="${idx.index>0}"><a href="javascript:chgposup(${slide.slideid })" class="split-r" id="op_chgposup${slide.slideid }">上移</a></c:if>
					<c:if test="${idx.index<fn:length(list)-1}"><a href="javascript:chgposdown(${slide.slideid })" class="split-r" id="op_chgposdown${slide.slideid }">下移</a></c:if>
					<a href="javascript:toupdateslide(${slide.slideid })" class="split-r" id="op_updateslide_${slide.slideid }">修改</a>
					<a href="javascript:opdelslide(${slide.slideid })" class="split-r" id="op_deleteslide_${slide.slideid }">删除</a>
					</p>
				</div>
				<div class="clr"></div>
			</li>
			</c:forEach>
			<c:if test="${(fn:length(list)==0) }">
				<li><div class="nodata">目前还没有添加内容</div></li>
			</c:if>
		</ul>
	</div>
</div>
<script type="text/javascript">
var slideidarr=new Array();
<c:forEach var="slide" items="${list }" varStatus="idx">
slideidarr[${idx.index}]=${slide.slideid};
</c:forEach>
var submited=false;
var glassid=null;
function chgimgwidth(imgid){
	var img=new Image();
	img.src=getObj(imgid).src;
	if(img.width>700){
		var height=img.height*700/img.width;
		$('#'+imgid).attr('width',700);
		$('#'+imgid).attr('height',height);
	}
}
$(document).ready(function(){
	<c:forEach var="slide" items="${list }" varStatus="idx">
	chgimgwidth('img_${slide.slideid}');
	</c:forEach>
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).addClass('enter');
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
	});
});
function chgposup(slideid){
	chgpos(slideid,getPrevSlideid(slideid),'op_chgposup'+slideid);
}
function chgposdown(slideid){
	chgpos(slideid,getNextSlideid(slideid),'op_chgposdown'+slideid);
}
function chgpos(slideid,pos_slideid,opobjid){
	var glassid_op=addGlass(opobjid,true);
	$.ajax({
		type:"POST",
		url:"${appctx_path}/mgr/slide_chgpos.do?slideid="+slideid+"&pos_slideid="+pos_slideid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		},
		error:function(data){
			removeGlass(glassid_op);
			alert('服务器出错，请刷新页面稍后继续操作');
		}
	});
}
function getPrevSlideid(slideid){
	var prev_idx=-1;
	for(var i=0;i<slideidarr.length;i++){
		if(slideidarr[i]==slideid){
			prev_idx=i-1;
			break;
		}
	}
	if(prev_idx>-1){
		return slideidarr[prev_idx];
	}
	return 0;
}
function getNextSlideid(slideid){
	var next_idx=-1;
	for(var i=0;i<slideidarr.length;i++){
		if(slideidarr[i]==slideid){
			next_idx=i+1;
			break;
		}
	}
	if(next_idx>-1 && next_idx<slideidarr.length ){
		return slideidarr[next_idx];
	}
	return 0;
}
function toupdate(){
	tourl('${appctx_path}/mgr/ppt_updatemain.do?pptid=${pptid}&back_url='+encodeLocalURL());
}
function opdel(){
	if(window.confirm('确实要删除？')){
		var glassid_op=addGlass('op_delete',true);
		$.ajax({
			type:"POST",
			url:"${appctx_path}/mgr/ppt_deletemain.do?pptid=${pptid}",
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			},
			error:function(data){
				removeGlass(glassid_op);
				alert('服务器出错，请刷新页面稍后继续操作');
			}
		});
	}
}
function tocreateslide(){
	tourl('${appctx_path}/mgr/slide_create.do?pptid=${pptid}&from=mainppt');
}
function toupdateslide(slideid){
	tourl('${appctx_path}/mgr/slide_update.do?slideid='+slideid+'&back_url='+encodeLocalURL());
}
function opdelslide(slideid){
	if(window.confirm('确实要删除？')){
		var glassid_op=addGlass('op_deleteslide_'+slideid,true);
		$.ajax({
			type:"POST",
			url:"${appctx_path}/mgr/slide_delete.do?slideid="+slideid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			},
			error:function(data){
				removeGlass(glassid_op);
				alert('服务器出错，请刷新页面稍后继续操作');
			}
		});
	}
}
<c:if test="${myslideid>0}">
scrollToPos('row_${myslideid}');
<c:remove var="myslideid" scope="session"/>
</c:if>
</script>
</c:set><jsp:include page="../inc/mgrframe.jsp"></jsp:include>