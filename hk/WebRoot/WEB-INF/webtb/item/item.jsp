<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">${tbItem.title }
</c:set><c:set var="html_head_value" scope="request">
<meta name="description" content="${tbItem.title }" />
<meta name="keywords"  content="${tbItem.title }|${tbItemCat.name }|${tbUser.location }|顾问家" />
<script type="text/javascript" src="${ctx_path }/webtb/js/hovertip.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	  window.setTimeout(hovertipInit, 1);
   });
</script>
</c:set><c:set var="html_body_content" scope="request">
<hk:actioninvoke mappinguri="/tb/item_checkusersysadmin"/>
<div class="pl">
	<div class="mod main_item">
		<div class="head">
			<a href="/p/${tbUser.userid}"><img src="${tbUser.pic_url_80 }" /></a><br/>
			<a href="/p/${tbUser.userid}">${tbUser.show_nick }</a><c:if test="${tbUser.sinaVerified}"><br/><a target="_blank" href="http://t.sina.com.cn/pub/verified"><img src="${ctx_path }/webtb/img/sina_vip.gif" title="新浪微博认证用户"/></a></c:if>
		</div>
		<div class="item_area">
			<div class="item_img">
				<c:if test="${not empty tbItem.pic_url}">
				<a href="javascript:totb()"><img src="${tbItem.pic_url }_b.jpg" /></a>
				</c:if>
			</div>
			<div class="item_prop">
				<c:if test="${user_sysadmin}">
				<a id="link_delitem" href="javascript:delitem(${itemid })">删除商品</a>
				<script type="text/javascript">
				function delitem(itemid){
					if (confirm('确定要删除此商品？')) {
						addGlass('link_delitem', true);
						doAjax('${ctx_path}/tb/item_prvdelete?itemid=' + itemid, function(data){
							tourl('/');
						});
					}
				}
				</script>
				</c:if>
				<p class="b">${tbItem.title }</p>
				<c:if test="${tbItem.hkscore>0 && tbItem.hkscore_num>0}">总体评价：<img src="${ctx_path }/webtb/img/star/star${tbItem.avgScore }.gif"/><br /></c:if>
				价　　格：${tbItem.price }元<br />
				所在地区：${tbItem.location }<br />
				宝贝类型：<hk:data key="ehk.tb_item_stuff_status${tbItem.stuff_status}"/><br />
				<c:if test="${tbItem.volume>0}">
				最近售出：${tbItem.volume }件
				</c:if>
				<div id="status_ownner" class="a_checked0 f_l" style="margin-right: 20px">
					<input id="ownner" type="checkbox" onclick="set_item_ownner(this)" <c:if test="${tbItemUserRef_ownner!=null}">checked="checked"</c:if>/><label for="ownner">我有</label>
				</div>
				<div id="status_wanter" class="a_checked f_l">
					<input id="wanter" type="checkbox" onclick="set_item_wanter(this)" <c:if test="${tbItemUserRef_want!=null}">checked="checked"</c:if>/><label for="wanter">想买</label>
				</div>
				<div class="clr"></div>
				<c:if test="${user_score<=0}"><c:set var="display_style">display:none</c:set></c:if>
				<div id="my_score_area" style="${display_style }">
					<div class="f_l">我的评分：</div>
					<div class="f_l">
						<div id="starscore0"></div>
						<div class="clr"></div>
					</div>
					<div class="clr"></div>
				</div>
				<c:if test="${tbItem.userYouHuiPrice==0}">
					<div>
						<input type="button" class="btn" value="去淘宝购买" onclick="totb()"/>
						<input type="checkbox" checked="checked" id="_share_item" value="true"/><label for="_share_item">自动分享</label>
					</div>
				</c:if>
			</div>
			<div class="clr"></div>
		</div>
		<div class="clr"></div>
	</div>
	<c:if test="${tbItem.userYouHuiPrice>0}">
		<div class="mod">
			<fmt:formatNumber var="_price" value="${tbItem.userYouHuiPrice }" pattern="#.#"/>
			<input type="button" class="btn split-r" value="通过绿色通道去淘宝购买，成功后本站返还${_price }元" onclick="totb()"/>
			<input type="checkbox" checked="checked" id="_share_item" value="true"/><label for="_share_item">自动分享</label><br/>
			惊喜：本商品参加了本站的购买分享就送红包活动。登录本站，通过绿色通道进入淘宝（会自动分享到微博），成功购买并确认收货后，我们将发放${_price }元红包。
			<a href="${ctx_path }/tb/help_buy" onclick="toexplain();return false;">详细说明</a>
		</div>
	</c:if>
	<div class="mod">
		<div class="mod_title">大家也在说<a name="cmt_area"></a></div>
		<div class="mod_content">
			<jsp:include page="itemcmtlist_inc.jsp"></jsp:include>
			<c:if test="${more_cmt}">
			<a class="more2" href="${ctx_path }/tb/itemcmt_list?itemid=${itemid}">更多</a>
			</c:if>
		</div>
	</div>
</div>
<div class="pr">
	<div class="mod">
		<div class="fr">
		<script type="text/javascript">
		obj_width=265;
		</script>
		<jsp:include page="../inc/share.jsp"></jsp:include>
		</div>
		<div class="clr"></div>
	</div>
	<c:if test="${fn:length(item_user_ownnerlist)>0}">
		<div class="mod">
			<div class="mod_title">谁已经拥有</div>
			<div class="mod_content">
				<ul class="headlist">
					<c:forEach var="userref" items="${item_user_ownnerlist}">
						<li>
							<a href="/p/${userref.userid }"><img title="${userref.tbUser.show_nick }" src="${userref.tbUser.pic_url_80 }" width="60" height="60" /></a>
						</li>
					</c:forEach>
				</ul>
				<div class="clr"></div>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(item_user_reflist)>0}">
		<div class="mod">
			<div class="mod_title">${tbUser.show_nick }还推荐</div>
			<div class="mod_content">
				<ul class="itemlist">
					<c:forEach var="ref" items="${item_user_reflist}">
						<li>
							<a href="${ctx_path }/tb/item?itemid=${ref.itemid}"><img src="${ref.tbItem.pic_url }_120x120.jpg" width="100" height="100" /></a>
							<p><a href="${ctx_path }/tb/item?itemid=${ref.itemid}">${ref.tbItem.title }</a></p>
						</li>
					</c:forEach>
				</ul>
				<div class="clr"></div>
			</div>
		</div>
	</c:if>
</div>
<div class="clr"></div>
<link rel="stylesheet" type="text/css" href="${ctx_path }/webtb/css/starobj.css" />
<script type="text/javascript" src="${ctx_path }/webtb/js/starobj.js"></script>
<script type="text/javascript" src="${ctx_path }/webtb/js/item.js"></script>
<script type="text/javascript">
var itemid=${itemid};
function toexplain(){
	tourl('${ctx_path}/tb/help_buy?return_url='+encodeLocalURL());
}
function totb(){
	window.open('${ctx_path}/tb/item_prvurl?itemid=${itemid}&share_item='+getObj('_share_item').checked);
}
$(document).ready(function(){
	$('ul.itemcmtlist li').bind('mouseenter', function(){
		if($(this).attr('status')=="0"){
			$(this).addClass('enter');
		}
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
	});
});
<c:if test="${tocmt==1}">
window.location.hash="cmt_area";
</c:if>
var star0=null;
<c:if test="${user_score>0}">
star0=new StarObj('starscore0',${user_score-1},function(star_idx){
	if (!is_user_login) {
		alert('请先登录');
		return;
	}
	doAjax(path + '/tb/item_prvcreatescore?itemid=' + itemid + '&score=' + (star_idx+1), function(data){
	});
});
star0.show();
</c:if>
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>