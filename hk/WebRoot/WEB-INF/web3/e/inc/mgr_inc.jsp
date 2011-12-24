<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();JspDataUtil.loadCompany(request);%>
<c:set var="body_hk_content" scope="request">
<div class="mod_left">
	<c:if test="${cmptable_show}">
		<div class="mod-1">
			<%=Hkcss2Util.rd_bg%>
			<div class="tit">座位分类</div>
			<div class="cont">
			<ul class="userset">
				<li><a class="n1" href="<%=path %>/e/op/auth/table_list2.do?companyId=${companyId }"><hk:data key="view.company.allcmptable"/></a></li>
			</ul>
			<c:forEach var="cmpTableSort" items="${sortlist}">
				<div class="subtit">${cmpTableSort.name }</div>
				<ul class="userset">
					<c:forEach var="cpt" items="${cmpTableSort.cmpPersonTableList}">
						<c:set var="css_class"><c:if test="${sortId==cpt.sortId && cpt.personNum==num}">active</c:if></c:set>
						<li>
							<a id="num_${cpt.sortId }_${cpt.personNum}" class="n1 ${css_class }" href="<%=path %>/e/op/auth/table_list2.do?companyId=${companyId }&sortId=${cpt.sortId }&num=${cpt.personNum}"><hk:data key="view.company.cmptable.personnum" arg0="${cpt.personNum}"/> <span>(${cpt.freeCount }/${cpt.totalCount })</span></a>
						</li>
						<c:set var="css_class"></c:set>
					</c:forEach>
				</ul>
			</c:forEach>
			</div>
			<%=Hkcss2Util.rd_bg_bottom%>
		</div>
	</c:if>
	<c:if test="${!mgr_bar_not_show || mgr_bar_not_show==null}">
		<div class="mod-1">
			<%=Hkcss2Util.rd_bg%>
			<div class="tit">足迹信息管理</div>
			<div class="cont"> 
				<div class="subtit">信息</div>
				<ul class="userset">
					<li><a id="op_0" class="n1" href="<%=path %>/e/op/op_toeditweb.do?companyId=${companyId }"><hk:data key="view.company.mgr.info"/></a></li>
					<li><a id="op_3" class="n1" href="<%=path %>/e/op/op_tosetmapweb.do?companyId=${companyId }"><hk:data key="view.company.mgr.setmap"/></a></li>
					<li><a id="op_4" class="n1" href="<%=path %>/e/op/photo/photo_list.do?companyId=${companyId }">图片管理</a></li>
					<li><a id="op_5" class="n1" href="#">点评管理</a></li>
					<li><a id="op_9" class="n1" href="<%=path %>/e/op/op_toselbizcircleweb.do?companyId=${companyId }">商圈管理</a></li>
					<li><a id="op_20" class="n1" href="<%=path %>/e/op/op_taglist.do?companyId=${companyId }">标签管理</a></li>
					<c:if test="${jsp_company.userId>0}">
					<li><a id="op_17" class="n1" href="<%=path %>/e/op/op_cmpunionlist.do?companyId=${companyId }">申请加入联盟</a></li>
					</c:if>
				</ul>
				<c:if test="${jsp_company.userId>0}">
					<div class="subtit"><hk:data key="view.company.product"/></div>
					<ul class="userset">
						<li><a id="op_1" class="n1" href="<%=path %>/e/op/product/op_sortlistweb.do?companyId=${companyId }"><hk:data key="view.company.productsort.mgr.mgr"/></a></li>
						<li><a id="op_10" class="n1" href="<%=path %>/e/op/product/op_productlistweb2.do?companyId=${companyId }"><hk:data key="view.company.product.mgr.everydaymgr"/></a></li>
						<li><a id="op_2" class="n1" href="<%=path %>/e/op/product/op_productlistweb.do?companyId=${companyId }"><hk:data key="view.company.product.mgr.mgr"/></a></li>
						<li><a id="op_18" class="n1" href="<%=path %>/e/op/op_tosetsearchtype.do?companyId=${companyId }">设置产品搜索方式</a></li>
					</ul>
					<c:if test="${jsp_company.parentKindId==1}">
						<div class="subtit"><hk:data key="view.company.mgr.table.title"/></div>
						<ul class="userset">
							<li><a id="op_14" class="n1" href="<%=path %>/e/op/auth/table_sortlist.do?companyId=${companyId }"><hk:data key="view.company.mgr.table.sort"/></a></li>
							<li><a id="op_15" class="n1" href="<%=path %>/e/op/auth/table.do?companyId=${companyId }"><hk:data key="view.company.mgr.table"/></a></li>
							<li><a id="op_16" class="n1" href="<%=path %>/e/op/auth/table/photo_photosetlist.do?companyId=${companyId }"><hk:data key="view.company.mgr.table.photoset"/></a></li>
						</ul>
					</c:if>
					<div class="subtit">优惠券</div>
					<ul class="userset">
						<li><a id="op_19" class="n1" href="<%=path %>/e/op/auth/coupon.do?companyId=${companyId }">优惠券管理</a></li>
					</ul>
					<!-- 
					<div class="subtit"><hk:data key="view.company.order"/></div>
					<ul class="userset">
						<li><a id="op_6" class="n1" href="<%=path %>/e/op/cmporder_myorderlist.do?companyId=${companyId }&flg=0"><hk:data key="view.company.order.home"/></a></li>
						<li><a id="op_7" class="n1" href="<%=path %>/e/op/cmporder_myorderlist.do?companyId=${companyId }&flg=1"><hk:data key="view.company.order.keytag"/></a></li>
						<li><a id="op_8" class="n1" href="<%=path %>/e/op/cmporder_myorderlist.do?companyId=${companyId }&flg=2"><hk:data key="view.company.order.kind"/></a></li>
					</ul>
					 -->
					<div class="subtit"><hk:data key="view.company.orderform"/></div>
					<ul class="userset">
						<li><a id="op_9" class="n1" href="<%=path %>/e/op/orderform.do?companyId=${companyId }"><hk:data key="view.company.orderform"/></a></li>
					</ul>
					<div class="subtit"><hk:data key="view.company.mgr.member"/></div>
					<ul class="userset">
						<li><a id="op_12" class="n1" href="<%=path %>/e/op/auth/member.do?companyId=${companyId }"><hk:data key="view.company.mgr.memberinfo"/></a></li>
						<li><a id="op_13" class="n1" href="<%=path %>/e/op/auth/member_gradelist.do?companyId=${companyId }"><hk:data key="view.company.mgr.membergradeinfo"/></a></li>
					</ul>
					<div class="subtit">活动</div>
					<ul class="userset">
						<li><a id="op_21" class="n1" href="<%=path %>/e/op/auth/act_tocreate.do?companyId=${companyId }">创建活动</a></li>
						<li><a id="op_22" class="n1" href="<%=path %>/e/op/auth/act_list.do?companyId=${companyId }">活动列表</a></li>
					</ul>
				</c:if>
			</div>
			<%=Hkcss2Util.rd_bg_bottom%>
		</div>
	</c:if>
</div>
<div class="mod_primary">
	<div class="nav-2">
		<div class="subnav">
			<div class="l">
			</div>
			<div class="mid">
				<ul>
					<li class="path">
						<ul>
							<li>
								<a class="home" href="#"></a>
							</li>
							<li>
								<a class="nav-a" href="<%=path %>/cmp.do?companyId=${jsp_company.companyId}">${jsp_company.name }</a>
							</li>
							<c:if test="${nav2_subnav_content!=null}">${nav2_subnav_content}</c:if>
							<li>
								<a class="nav-a" href="#">${html_title }</a>
							</li>
						</ul>
					</li>
				</ul>
				<div class="clr"></div>
			</div>
			<div class="r"></div>
			<div class="clr"></div>
		</div>
		<div class="clr"></div>
	</div>
	<div class="inner">${mgr_content }</div>
</div>
<div class="clr"></div>
<script type="text/javascript">
<c:if test="${op_func!=null }">
var op_func=${op_func };
</c:if>
<c:if test="${op_func==null }">
var op_func=-1;
</c:if>
var obj=getObj("op_"+op_func);
if(obj!=null){
	obj.className="n1 active";
}
</script>
</c:set>
<jsp:include page="../../inc/cmpmgrframe.jsp"></jsp:include>