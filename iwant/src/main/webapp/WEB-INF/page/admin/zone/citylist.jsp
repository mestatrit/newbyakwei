<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set scope="request" var="mgr_body_content">
<div class="mod">
	<div class="mod_title">城市列表 - ${province.name}
	<a class="more" href="${appctx_path}/mgr/zone_provincelist.do">返回</a>
	</div>
	<div class="mod_content">
		<div>
		<input type="button" class="btn" value="创建城市" onclick="tocreate()"/>
		</div>
		<ul class="rowlist">
			<li>
				<div class="f_l" style="width: 150px;margin-right: 20px">名称</div>
				<div class="f_l" style="width: 80px;margin-right: 20px">顺序</div>
				<div class="clr"></div>
			</li>
			<c:forEach var="c" items="${list }" varStatus="idx">
			<li>
				<div class="f_l" style="width: 150px;margin-right: 20px">
					<hk:value value="${c.name }" onerow="true"/>
					<c:if test="${c.hidden}"><span class="ruo">隐藏中</span></c:if>
				</div>
				<div class="f_l" style="width: 240px;margin-right: 20px">
				<a href="${appctx_path}/mgr/zone_districtlist.do?cityid=${c.cityid}" class="split-r">查看地区</a>
					<c:if test="${!c.hidden}">
					<a id="ophide_${c.cityid}" href="javascript:hidecity(${c.cityid})" class="split-r">隐藏</a>
					</c:if>
					<c:if test="${c.hidden}">
					<a id="opshow_${c.cityid}" href="javascript:showcity(${c.cityid})" class="split-r">显示</a>
					</c:if>
					<a href="javascript:toupdate(${c.cityid })" class="split-r" id="op_update_${c.cityid }">修改</a>
					<a href="javascript:opdel(${c.cityid })" class="split-r" id="op_delete_${c.cityid }">删除</a>
				</div>
				<div class="f_l" style="width: 80px;margin-right: 20px">
					<c:if test="${idx.index>0}">
						<a id="optoup_${c.cityid}" class="split-r" href="javascript:toup(${c.cityid})">上移</a>
					</c:if>
					<c:if test="${idx.index<fn:length(list)-1}">
						<a id="optoup_${c.cityid}" class="split-r" href="javascript:todown(${c.cityid})">下移</a>
					</c:if>
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
<c:forEach var="c" items="${list }" varStatus="idx">
parr[${idx.index}]=${c.cityid};
</c:forEach>
$(document).ready(function(){
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).addClass('enter');
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
	});
});
function tocreate(){
	tourl('${appctx_path}/mgr/zone_createcity.do?provinceid=${provinceid}');
}
function toupdate(cid){
	tourl('${appctx_path}/mgr/zone_updatecity.do?cityid='+cid);
}
function opdel(cid){
	if(window.confirm('确实要删除？')){
		var glassid_op=addGlass('op_delete_'+cid,false);
		$.ajax({
			type:"POST",
			url:"${appctx_path}/mgr/zone_deletecity.do?cityid="+cid,
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
function getPrevId(cid){
	var prev_idx=-1;
	for(var i=0;i<parr.length;i++){
		if(parr[i]==cid){
			prev_idx=i-1;
			break;
		}
	}
	if(prev_idx>-1){
		return parr[prev_idx];
	}
	return 0;
}
function getNextId(cid){
	var prev_idx=-1;
	for(var i=0;i<parr.length;i++){
		if(parr[i]==cid){
			prev_idx=i+1;
			break;
		}
	}
	if(prev_idx<parr.length){
		return parr[prev_idx];
	}
	return 0;
}
function toup(cid){
	changePos(cid,getPrevId(cid));
}
function todown(cid){
	changePos(cid,getNextId(cid));
}
function changePos(cid,toid){
	var glassid_op=addGlass('optoup_'+cid,false);
	$.ajax({
		type:"POST",
		url:"${appctx_path}/mgr/zone_changeposcity.do?cityid="+cid+"&toid="+toid,
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
function hidecity(cid){
	var glassid_op=addGlass('ophide_'+cid,false);
	$.ajax({
		type:"POST",
		url:"${appctx_path}/mgr/zone_hidecity.do?cityid="+cid,
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
function showcity(cid){
	var glassid_op=addGlass('opshow_'+cid,false);
	$.ajax({
		type:"POST",
		url:"${appctx_path}/mgr/zone_showcity.do?cityid="+cid,
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