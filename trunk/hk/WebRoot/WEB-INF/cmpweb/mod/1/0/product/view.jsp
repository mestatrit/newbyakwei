<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="epp_other_value" scope="request">
	<meta name="keywords" content="${cmpProduct.name }|${cmpProductSort.name }|${o.name}" />
	<meta name="description" content="${cmpProduct.name }|${cmpProductSort.name }|${o.name}" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/cmpwebst4/mod/1/0/css/jcarousel/skins/1/skin.css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/cmpwebst4/mod/1/0/js/jqzoom/style/jqzoom.css" />
	<script type="text/javascript" language="javascript" src="<%=path %>/cmpwebst4/mod/pub/js/jquery.jcarousel.js"></script>
	<script type="text/javascript" language="javascript" src="<%=path %>/cmpwebst4/mod/pub/js/jquery.jqzoom.js"></script>
</c:set>
<c:set var="html_body_content" scope="request">
	<div class="product">
		<div class="product_l">
			<div class="scroll">
				<ul id="mycarousel" class="jcarousel-skin-tango">
					<c:forEach var="pic" items="${photolist}" varStatus="idx">
					<li><a href="javascript:chgimg(${idx.index },'${pic.pic320 }','${pic.pic800 }')"><img id="small${idx.index }" src="${pic.pic60 }" /></a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="imgshow">
				<img id="bigimg" src="${cmpProduct.head320 }" class="jqzoom" alt="${cmpProduct.head800 }" />
			</div>
			<div class="clr"></div>
			<c:if test="${not empty cmpProduct.intro }">
				<div class="product_info">
					<div class="b bdtm">
						<hk:data key="epp.product.intro"/>
					</div>
					${cmpProduct.intro }
				</div>
			</c:if>
		</div>
		<div class="product_r">
			<div class="product_title">
				${cmpProduct.name }
			</div>
			<div class="product_attr">
				<span class="attr"><hk:data key="epp.product.price"/></span>： ￥${cmpProduct.money }<br />
				<c:if test="${not empty cmpProductSortAttrObject.attr1Name}">
				<span class="attr">${cmpProductSortAttrObject.attr1Name }</span>： ${cmpProductAttrValue.attr1.name }<br />
				</c:if>
				<c:if test="${not empty cmpProductSortAttrObject.attr2Name}">
				<span class="attr">${cmpProductSortAttrObject.attr2Name }</span>： ${cmpProductAttrValue.attr2.name }<br />
				</c:if>
				<c:if test="${not empty cmpProductSortAttrObject.attr3Name}">
				<span class="attr">${cmpProductSortAttrObject.attr3Name }</span>： ${cmpProductAttrValue.attr3.name }<br />
				</c:if>
				<c:if test="${not empty cmpProductSortAttrObject.attr4Name}">
				<span class="attr">${cmpProductSortAttrObject.attr4Name }</span>： ${cmpProductAttrValue.attr4.name }<br />
				</c:if>
				<c:if test="${not empty cmpProductSortAttrObject.attr5Name}">
				<span class="attr">${cmpProductSortAttrObject.attr5Name }</span>： ${cmpProductAttrValue.attr5.name }<br />
				</c:if>
				<c:if test="${not empty cmpProductSortAttrObject.attr6Name}">
				<span class="attr">${cmpProductSortAttrObject.attr6Name }</span>： ${cmpProductAttrValue.attr6.name }<br />
				</c:if>
				<c:if test="${not empty cmpProductSortAttrObject.attr7Name}">
				<span class="attr">${cmpProductSortAttrObject.attr7Name }</span>： ${cmpProductAttrValue.attr7.name }<br />
				</c:if>
				<c:if test="${not empty cmpProductSortAttrObject.attr8Name}">
				<span class="attr">${cmpProductSortAttrObject.attr8Name }</span>： ${cmpProductAttrValue.attr8.name }<br />
				</c:if>
				<c:if test="${not empty cmpProductSortAttrObject.attr9Name}">
				<span class="attr">${cmpProductSortAttrObject.attr9Name }</span>： ${cmpProductAttrValue.attr9.name }<br />
				</c:if>
			</div>
		</div>
		<div class="clr"></div>
	</div>
<script type="text/javascript">
var endIdx=${fn:length(photolist)};
$(document).ready(function(){
    $("img.jqzoom").jqueryzoom({
        xzoom: 300, //zooming div default width(default width value is 200)
        yzoom: 300, //zooming div default width(default height value is 200)
        offset: 10 //zooming div default offset(default offset value is 10)
        //position: "right" //zooming div position(default position value is "right")
    });
});
$(document).ready(function(){
    $('#mycarousel').jcarousel({
        vertical: true,
        scroll: 2
    });
});
function chgimg(idx,img320,img800){
	for(var i=0;i<=endIdx;i++){
		if(getObj('small'+i)!=null){
			getObj('small'+i).className='';
		}
	}
	getObj('small'+idx).className='active';
	getObj('bigimg').src=img320;
	getObj('bigimg').alt=img800;
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>