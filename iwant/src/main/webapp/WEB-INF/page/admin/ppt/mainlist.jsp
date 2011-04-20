<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%>
<c:set var="html_head_title" scope="request">项目简介管理</c:set>
<c:set scope="request" var="mgr_body_content">
<div class="mod">
	<div class="mod_title">项目简介管理</div>
	<div class="mod_content">
		<div>
		<input type="button" class="btn" value="创建项目简介" onclick="tocreate()"/>
		</div>
		<div style="padding-left: 50">
		<form method="get" action="${appctx_path}/mgr/ppt_mainlist.do">
			名称：<input type="text" class="text" name="name" value="<hk:value value="${pptSearchCdn.name}" onerow="true"/>"/>
			<input type="submit" value="搜索" class="btn"/>
		</form>
		</div>
		<ul class="rowlist">
			<c:forEach var="ppt" items="${list }" varStatus="idx">
			<li>
				<div class="f_l" style="width: 70px;margin-right: 20px">
					<c:if test="${not empty ppt.pic_path}"><img src="${ppt.pic1Url}"/></c:if>&nbsp;
				</div>
				<div class="f_l" style="width: 300px;margin-right: 20px">
					<a href="javascript:view(${ppt.pptid})">
					<hk:value value="${ppt.project.name}"/> - <hk:value value="${ppt.name }" onerow="true"/>
					</a>
				</div>
				<div class="f_l">
					<c:if test="${(page_size * (page - 1) + idx.count) > 1}">
						<a href="javascript:chgposfirst(${ppt.pptid })" class="split-r" id="op_chgposfirst${ppt.pptid }">移到首位</a>
						<a href="javascript:chgposup(${ppt.pptid })" class="split-r" id="op_chgposup${ppt.pptid }">上移</a>
					</c:if>
					<c:if test="${(page_size * (page - 1) + idx.count) < count}">
						<a href="javascript:chgposdown(${ppt.pptid })" class="split-r" id="op_chgposdown${ppt.pptid }">下移</a>
						<a href="javascript:chgposlast(${ppt.pptid })" class="split-r" id="op_chgposlast${ppt.pptid }">移到末位</a>
					</c:if>
				</div>
				<div class="clr"></div>
			</li>
			</c:forEach>
			<c:if test="${(fn:length(list)==0 && page==1) || (fn:length(list)==0 && page==null) }">
				<li><div class="nodata">本页没有数据</div></li>
			</c:if>
		</ul>
		<div>
			<c:set var="page_url" scope="request">${appctx_path }/mgr/ppt_maillist.do?name=${pptSearchCdn.encName }</c:set>
			<jsp:include page="../../inc/pagesupport_inc.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
var pptids=new Array();
<c:forEach var="id" items="${idList}" varStatus="idx">
pptids[${idx.index}]=${id};
</c:forEach>
$(document).ready(function(){
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).addClass('enter');
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
	});
});
function tocreate(){
	tourl('${appctx_path}/mgr/ppt_create.do?projectid=${projectid}');
}
function toupdate(pptid){
	tourl('${appctx_path}/mgr/ppt_update.do?pptid='+pptid+"&back_url="+encodeLocalURL());
}
function view(pptid){
	tourl('${appctx_path}/mgr/ppt_view.do?pptid='+pptid+"&back_url="+encodeLocalURL());
}
function opdel(pptid){
	if(window.confirm('确实要删除？')){
		var glassid_op=addGlass('op_delete_'+pptid,true);
		$.ajax({
			type:"POST",
			url:"${appctx_path}/mgr/ppt_delete.do?pptid="+pptid,
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
function chgposup(pptid){
	chgpos(pptid,getPrevPptid(pptid),'op_chgposup'+pptid);
}
function chgposdown(pptid){
	chgpos(pptid,getNextPptid(pptid),'op_chgposdown'+pptid);
}
function chgpos(pptid,pos_pptid,opobjid){
	var glassid_op=addGlass(opobjid,true);
	$.ajax({
		type:"POST",
		url:"${appctx_path}/mgr/ppt_chgmainpos.do?pptid="+pptid+"&pos_pptid="+pos_pptid,
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
function getPrevPptid(pptid){
	var prev_idx=-1;
	for(var i=0;i<pptids.length;i++){
		if(pptids[i]==pptid){
			prev_idx=i-1;
			break;
		}
	}
	if(prev_idx>-1){
		return pptids[prev_idx];
	}
	return 0;
}
function getNextPptid(pptid){
	var next_idx=-1;
	for(var i=0;i<pptids.length;i++){
		if(pptids[i]==pptid){
			next_idx=i+1;
			break;
		}
	}
	if(next_idx>-1 && next_idx<pptids.length ){
		return pptids[next_idx];
	}
	return 0;
}
function chgposfirst(pptid){
	var glassid_op=addGlass('op_chgposfirst'+pptid,true);
	$.ajax({
		type:"POST",
		url:"${appctx_path}/mgr/ppt_chgmainposfirst.do?pptid="+pptid,
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
function chgposlast(pptid){
	var glassid_op=addGlass('op_chgposlast'+pptid,true);
	$.ajax({
		type:"POST",
		url:"${appctx_path}/mgr/ppt_chgmainposlast.do?pptid="+pptid,
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
</script>
</c:set><jsp:include page="../inc/mgrframe.jsp"></jsp:include>