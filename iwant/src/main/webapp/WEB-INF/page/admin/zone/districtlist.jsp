<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set scope="request" var="mgr_body_content">
<div class="mod">
	<div class="mod_title">区列表 - ${city.name}
	<a class="more" href="${appctx_path}/mgr/zone_citylist.do?provinceid=${city.provinceid}">返回</a>
	</div>
	<div class="mod_content">
		<div>
		<input type="button" class="btn" value="创建区" onclick="tocreate()"/>
		</div>
		<ul class="rowlist">
			<li>
				<div class="f_l" style="width: 150px;margin-right: 20px">名称</div>
				<div class="f_l" style="width: 80px;margin-right: 20px">顺序</div>
				<div class="clr"></div>
			</li>
			<c:forEach var="d" items="${list }" varStatus="idx">
			<li>
				<div class="f_l" style="width: 150px;margin-right: 20px">
					<hk:value value="${d.name }" onerow="true"/>
					<c:if test="${d.hidden}"><span class="ruo">隐藏中</span></c:if>
				</div>
				<div class="f_l" style="width: 200px;margin-right: 20px">
					<c:if test="${!d.hidden}">
					<a id="ophide_${d.did}" href="javascript:hidedistrict(${d.did})" class="split-r">隐藏</a>
					</c:if>
					<c:if test="${d.hidden}">
					<a id="opshow_${d.did}" href="javascript:showdistrict(${d.did})" class="split-r">显示</a>
					</c:if>
					<a href="javascript:toupdate(${d.did })" class="split-r" id="op_update_${d.did }">修改</a>
					<a href="javascript:opdel(${d.did })" class="split-r" id="op_delete_${d.did }">删除</a>
				</div>
				<div class="clr"></div>
			</li>
			</c:forEach>
			<c:if test="${(fn:length(list)==0)}">
				<li><div class="nodata">本页没有数据</div></li>
			</c:if>
		</ul>
	</div>
</div>
<script type="text/javascript">
var parr=new Array();
<c:forEach var="d" items="${list }" varStatus="idx">
parr[${idx.index}]=${d.did};
</c:forEach>
$(document).ready(function(){
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).addClass('enter');
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
	});
});
function tocreate(){
	tourl('${appctx_path}/mgr/zone_createdistrict.do?cityid=${cityid}');
}
function toupdate(did){
	tourl('${appctx_path}/mgr/zone_updatedistrict.do?did='+did);
}
function opdel(did){
	if(window.confirm('确实要删除？')){
		var glassid_op=addGlass('op_delete_'+did,false);
		$.ajax({
			type:"POST",
			url:"${appctx_path}/mgr/zone_deletedistrict.do?did="+did,
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
function getPrevId(did){
	var prev_idx=-1;
	for(var i=0;i<parr.length;i++){
		if(parr[i]==did){
			prev_idx=i-1;
			break;
		}
	}
	if(prev_idx>-1){
		return parr[prev_idx];
	}
	return 0;
}
function getNextId(did){
	var prev_idx=-1;
	for(var i=0;i<parr.length;i++){
		if(parr[i]==did){
			prev_idx=i+1;
			break;
		}
	}
	if(prev_idx<parr.length){
		return parr[prev_idx];
	}
	return 0;
}
function toup(did){
	changePos(did,getPrevId(did));
}
function todown(did){
	changePos(did,getNextId(did));
}
function changePos(did,toid){
	var glassid_op=addGlass('optoup_'+did,false);
	$.ajax({
		type:"POST",
		url:"${appctx_path}/mgr/zone_changeposdistrict.do?did="+did+"&toid="+toid,
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
function hidedistrict(did){
	var glassid_op=addGlass('ophide_'+did,false);
	$.ajax({
		type:"POST",
		url:"${appctx_path}/mgr/zone_hidedistrict.do?did="+did,
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
function showdistrict(did){
	var glassid_op=addGlass('opshow_'+did,false);
	$.ajax({
		type:"POST",
		url:"${appctx_path}/mgr/zone_showdistrict.do?did="+did,
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