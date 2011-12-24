<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="iteratorproduct">
	<c:forEach var="vo" items="${productvolist}">
		<c:set var="product_url"><%=path %>/product.do?pid=${vo.cmpProduct.productId }</c:set>
		<div class="cmp" onmouseout="this.className='cmp';" onmouseover="this.className='cmp bg3';">
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="image"><a href="${product_url }"><img src="${vo.cmpProduct.head240 }" /></a></td>
					<td class="content">
						<div>
							<div class="f_l">
								<a class="text_16" href="${product_url }">${vo.cmpProduct.name }</a> ￥${vo.cmpProduct.money }
							</div>
							<div id="carddiv${vo.cmpProduct.productId }" class="f_r">
								<c:if test="${!vo.addToCard}">
								<a class="text_16" href="javascript:createtocard(${vo.cmpProduct.productId })"><hk:data key="view.product.addtocard"/></a>
								</c:if> 
								<c:if test="${vo.addToCard}">
								下单成功，<a href="<%=path%>/shoppingcard.do">马上确认</a>
								</c:if> 
							</div> 
							<div class="clr"></div>
						</div>
						<div>
							<c:if test="${vo.cmpProduct.score>0}"><img class="imgd" src="<%=path %>/webst3/img/stars/star${vo.cmpProduct.starsLevel }.gif" /></c:if>
							<c:if test="${vo.cmpProduct.reviewCount>0}"><span class="small"><hk:data key="view.company.reviewcount" arg0="${vo.cmpProduct.reviewCount}"/></span></c:if>
						</div>
						${vo.cmpProduct.intro }
					</td>
				</tr>
			</table>
		</div>
	</c:forEach>
</c:set>
<c:if test="${fn:length(productvolist)>0}">
	<div class="cmplist">
		${iteratorproduct }
	</div>
	<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>
</c:if>
<script type="text/javascript">
function createtocard(pid,chgflg){
	setHtml('carddiv'+pid,'下单中 ...');
	$.ajax({
		type:"POST",
		url:'<%=path %>/product_addtocard.do?pid='+pid+"&chgflg="+chgflg,
		cache:false,
    	dataType:"html",
		success:function(data){
			if(data==-1){
				if(window.confirm("您预订的产品与购物车中的产品不在同一个足迹中，是否要更换足迹？")){
					createtocard(pid,1);
				}
				else{
					setHtml('carddiv'+pid,'<a class="text_16" href="javascript:createtocard('+pid+')"><hk:data key="view.product.addtocard"/></a>');
				}
			}
			else{
				setHtml('carddiv'+pid,'下单成功，<a href="<%=path%>/shoppingcard.do">马上确认</a>');
			}
		}
	});
}
</script>