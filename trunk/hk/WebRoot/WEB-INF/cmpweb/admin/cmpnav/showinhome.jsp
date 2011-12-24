<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpNav"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">设置首页模块显示顺序</div>
		<div class="mod_content">
			<div class="f_l" style="width: 350px;">
				<div class="divrow">
					<strong>现有栏目</strong>
				</div>
				<c:forEach var="n" items="${list}">
					<div class="divrow">
						<span class="split-r">${n.name } </span>
						<c:if test="${n.canShowInHome && n.reffunc!=12}">
							<c:if test="${!n.showOnMiddle}">
								<a class="split-r" href="javascript:shownav(${n.oid },<%=CmpNav.HOMEPOS_MIDDLE %>)">显示在中间</a>
							</c:if>
							<c:if test="${!n.showOnRight}">
								<a class="split-r" href="javascript:shownav(${n.oid },<%=CmpNav.HOMEPOS_RIGHT %>)">显示在右边</a>
							</c:if>
							<a href="javascript:hidenav(${n.oid })">隐藏</a>
						</c:if>
					</div>
					<c:forEach var="child" items="${n.children}">
						<div class="divrow" style="margin-left: 30px;">
							<span class="split-r">${child.name } </span>
							<c:if test="${child.canShowInHome && child.reffunc!=12}">
								<c:if test="${!child.showOnMiddle}">
									<a class="split-r" href="javascript:shownav(${child.oid },<%=CmpNav.HOMEPOS_MIDDLE %>)">显示在中间</a>
								</c:if>
								<c:if test="${!child.showOnRight}">
									<a class="split-r" href="javascript:shownav(${child.oid },<%=CmpNav.HOMEPOS_RIGHT %>)">显示在右边</a>
								</c:if>
								<c:if test="${child.showInHome>0}">
								<a href="javascript:hidenav(${child.oid })">隐藏</a>
								</c:if>
							</c:if>
						</div>
					</c:forEach>
				</c:forEach>
			</div>
			<div class="f_r" style="width: 250px">
				<div class="divrow">
					<strong>首页模块显示顺序</strong>
				</div>
				<div class="f_l" style="width:120px">
					<div class="divrow">
						<strong>中间</strong>
					</div>
					<div class="divrow">
						<a href="<%=path %>/epp/web/op/webadmin/cmpproduct.do?companyId=${companyId}">产品展示</a>
					</div>
					<div class="divrow">
						<a href="<%=path %>/epp/web/op/webadmin/cmparticle_alllist.do?companyId=${companyId}">首页推荐文章</a>
					</div>
					<div id="middle_con">
					</div>
				</div>
				<div class="f_r" style="width:120px">
					<div class="divrow">
						<strong>右边</strong>
					</div>
					<div id="right_con">
					</div>
				</div>
			</div>
			<div class="clr"></div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var middleArr=new Array();
var rightArr=new Array();
<c:forEach var="idxn" items="${middle_navlist}" varStatus="idx">
middleArr[${idx.index}]=new Array(${idxn.oid},'${idxn.name}');
</c:forEach>
<c:forEach var="idxn" items="${right_navlist}" varStatus="idx">
rightArr[${idx.index}]=new Array(${idxn.oid},'${idxn.name}');
</c:forEach>
function initMiddle(){
	var s="";
	for(var i=0;i<middleArr.length;i++){
		s+='<div class="divrow">';
		s+=middleArr[i][1];
		if(i>0){
			s+=' <a href="javascript:moveMiddleUp('+i+')">上移</a>';
		}
		s+='</div>';
	}
	setHtml('middle_con',s);
}
function initRight(){
	var s="";
	for(var i=0;i<rightArr.length;i++){
		s+='<div class="divrow">';
		s+=rightArr[i][1];
		if (i > 0) {
			s+=' <a href="javascript:moveRightUp('+i+')">上移</a>';
		}
		s+='</div>';
	}
	setHtml('right_con',s);
}
function moveMiddleUp(idx){
	var tmp=middleArr[idx-1];
	middleArr[idx-1]=middleArr[idx];
	middleArr[idx]=tmp;
	initMiddle();
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/admincmpnav_movemiddleup.do?companyId=${companyId}&ch=1&oid="+middleArr[idx-1][0],
		cache:false,
    	dataType:"html",
		success:function(data){
		}
	});
}
function moveRightUp(idx){
	var tmp=rightArr[idx-1];
	rightArr[idx-1]=rightArr[idx];
	rightArr[idx]=tmp;
	initRight();
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/admincmpnav_moverightup.do?companyId=${companyId}&ch=1&oid="+rightArr[idx-1][0],
		cache:false,
    	dataType:"html",
		success:function(data){
		}
	});
}
function shownav(oid,homepos){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/admincmpnav_showinhome.do?companyId=${companyId}&ch=1&oid="+oid+"&homepos="+homepos,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function hidenav(oid,homepos){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/admincmpnav_showinhome.do?companyId=${companyId}&ch=1&flg=1&oid="+oid+"&homepos="+homepos,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
initMiddle();
initRight();
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>