<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:if test="${fn:length(item_user_refvolist)==0}"><div class="b">没有数据</div></c:if>
<c:if test="${fn:length(item_user_refvolist)>0}">
<ul class="ulblock">
	<c:forEach var="vo" items="${item_user_refvolist}" varStatus="idx">
		<li idx="${vo.tbItemUserRef.oid }" <c:if test="${idx.index%2!=0}">class="even"</c:if>>
		<c:if test="${itemdata_shownum}"><div class="num">${idx.index+1 }.</div></c:if>
			<div class="item_img">
				<a href="${ctx_path }/tb/item?itemid=${vo.tbItemUserRef.itemid}"><img src="${vo.tbItemUserRef.tbItem.pic_url }_60x60.jpg" /></a>
			</div>
			<div class="content"><a href="${ctx_path }/tb/item?itemid=${vo.tbItemUserRef.itemid}">${vo.tbItemUserRef.tbItem.title}</a> ${vo.tbItemUserRef.tbItemCmt.content }</div>
			<div id="action_${vo.tbItemUserRef.oid }" class="action">
				<div id="div_hold_${vo.tbItemUserRef.oid }" class="a_checked0">
					<input id="ch_hold_${vo.tbItemUserRef.oid }" type="checkbox" onclick="opitem_user_ref_hold(${vo.tbItemUserRef.itemid},this,${vo.tbItemUserRef.oid })" <c:if test="${vo.has_hold}">checked="checked"</c:if>/><label for="ch_hold_${vo.tbItemUserRef.oid }">我有</label>
				</div>
				<div id="div_want_${vo.tbItemUserRef.oid }" class="a_checked">
					<input id="ch_want_${vo.tbItemUserRef.oid }" type="checkbox" onclick="opitem_user_ref_want(${vo.tbItemUserRef.itemid},this,${vo.tbItemUserRef.oid })" <c:if test="${vo.has_want}">checked="checked"</c:if>/><label for="ch_want_${vo.tbItemUserRef.oid }">想买</label>
				</div>
			</div>
			<div class="clr"></div>
		</li>
	</c:forEach>
</ul>
</c:if>