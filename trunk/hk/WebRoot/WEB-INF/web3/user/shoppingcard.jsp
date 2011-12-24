<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.shppingcard.title" /></c:set>
<c:set var="mgr_content" scope="request">
<div>
<c:if test="${fn:length(productvolist)>0}">
	<table cellpadding="0" cellspacing="0" class="infotable">
		<tr>
			<th width="260px">名称</th>
			<th width="90px">数量</th>
			<th width="90px">单价</th>
			<th width="90px">小计</th>
			<th width="90px">操作</th>
		</tr>
		<c:forEach var="vo" items="${productvolist}" varStatus="idx">
		<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if>
		<c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<tr class="${clazz_var }">
			<td>
			<a href="<%=path %>/product.do?pid=${vo.cmpProduct.productId}" target="_blank">
			<img class="middle" src="${vo.cmpProduct.head60 }" width="32" height="32"/>
			${vo.cmpProduct.name }</a></td>
			<td align="center"><input type="hidden" id="hide_count_${vo.cmpProduct.productId}" value="${vo.count }"/>
			<input onblur="updatePrize(${vo.cmpProduct.productId },this.value)" type="text" id="count_${vo.cmpProduct.productId }" size="5" value="${vo.count }" class="text_short_4"/>
			<div id="tip${vo.cmpProduct.productId}" class="tip2"></div>
			</td>
			<td align="center">￥${vo.cmpProduct.money }</td>
			<td align="center">￥<span id="product_total_price${vo.cmpProduct.productId }">${vo.cmpProduct.money*vo.count }</span></td>
			<td align="center"><a href="javascript:del(${vo.cmpProduct.productId })">删除</a></td>
		</tr>
		</c:forEach>
		<tr><td colspan="5" align="right"> <strong>金额总计： ￥<span id="totalPrice">${totalPrice }</span></strong></td></tr>
		<tr><td colspan="5" >
		<div class="f_l text_16">
			<a class="udline" href="javascript:cleancard()">清空购物车</a>
		</div>
		<div id="orderformdiv" class="f_r">
			<hk:form oid="orderformfrm" onsubmit="return suborderform(this.id)" action="/op/orderform_cfm.do">
				<hk:button value="继续购物" onclick="continueShopping();" clazz="btn"/>
				<hk:submit value="去结算" clazz="btn"/>
			</hk:form>
		</div>
		<div class="clr"></div>
		</td></tr>
	</table>
</c:if>
<c:if test="${fn:length(productvolist)==0}">
<div class="text_16"><strong><hk:data key="view.shoppingcard.empty"/></strong>
<c:if test="${companyId>0}">
<a href="<%=path %>/product_list.do?companyId=${companyId}">继续购物</a>
</c:if>
<c:if test="${companyId==0}">
<a href="<%=path %>/cmp_pklist.do?parentId=1">继续购物</a>
</c:if>
</div>
</c:if>
</div>
<script type="text/javascript">
function continueShopping(){
	tourl('<%=path %>/product_list.do?companyId=${companyId}');
}
function cleancard(){
	if(window.confirm("确定要清空购物车？")){
		tourl("<%=path%>/shoppingcard_clean.do");
	}
}
function suborderform(frmid){
	showSubmitDiv(frmid);
	return true;
}
function updatePrize(pid,count){
	if(getObj("hide_count_"+pid).value!=count){
		$.ajax({
			type:"POST",
			url:'<%=path %>/shoppingcard_updateProductPrice.do?pid='+pid+"&count="+count,
			cache:false,
	    	dataType:"html",
			success:function(data){
				var res=data.split(";");
				getObj("hide_count_"+pid).value=count;
				setHtml("product_total_price"+pid,res[0]);
				setHtml("totalPrice",res[1]);
				setHtml("tip"+pid,"修改成功");
				delay("hideTip("+pid+")",2000);
			}
		});
	}
}
function hideTip(pid){
	setHtml("tip"+pid,"");
}
function del(pid){
	if(window.confirm("确实要删除？")){
		tourl("<%=path%>/shoppingcard_delproduct.do?pid="+pid);
	}
}
</script>
</c:set>
<jsp:include page="../inc/usermgr_inc.jsp"></jsp:include>