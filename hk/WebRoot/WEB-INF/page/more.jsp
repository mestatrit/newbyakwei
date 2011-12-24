<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="更多功能 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="inc/top.jsp"></jsp:include>
	<div class="hang even">
		<c:if test="${userauth}">
		<hk:rmBlankLines rm="true">
			<hk:data key="view.managemycompany"/><br/>
			<hk:a href="/e/op/op_myauth.do?f=1"><hk:data key="view.company.createcompanybox"/></hk:a>|
			<hk:a href="/e/op/op_myauth.do?f=2"><hk:data key="view.company.editcompanyinfo"/></hk:a>|
			<!-- 
			<hk:a href="/e/op/op_myauth.do?f=9&pcflg=1">购买关键词</hk:a>
			 -->
			<br/><br/>
		</hk:rmBlankLines>
		</c:if>
		基本导航<br/>
		<hk:rmBlankLines rm="true">
		<c:if test="${userLogin}"><hk:a href="/home.do?userId=${loginUser.userId }">主页</hk:a>|</c:if>
		<hk:a href="/square.do">喇叭</hk:a>|
		<hk:a href="/box/market.do">宝箱</hk:a>|
		<hk:a href="/invite/invite_toinvite.do">邀请</hk:a>|
		<hk:a href="/msg/pvtlist.do">私信</hk:a>|
		<hk:a href="/laba/fav.do?userId=${userId}">收藏</hk:a>|
		<hk:a href="/follow/follow.do?userId=${userId}">好友</hk:a>|
		<hk:a href="/user/list2.do">会员</hk:a>|
		<hk:a href="/s.do">搜索</hk:a>|
		<hk:a href="/laba/poplaba.do">流行</hk:a>|
		<hk:a href="/user/set/set.do">设置</hk:a>|
		<hk:a href="/op/orderform_wap.do">我的订单</hk:a>|
		<hk:a href="/help.do">帮助</hk:a>
	</hk:rmBlankLines><br/><br/>
	<c:if test="${loginUser!=null}">
		宝箱功能<br/>
		<hk:rmBlankLines rm="true">
			<hk:a href="/box/market.do">宝箱市场</hk:a>|
			<c:if test="${cancreatebox}"><hk:a href="/box/op/op.do">发布宝箱</hk:a>|</c:if>	
			<hk:a href="/box/op/op_admin.do">宝箱管理</hk:a>|
			<c:if test="${adminUser!=null}"><hk:a href="/box/admin/adminbox_admin.do">宝箱超管</hk:a>|</c:if>
			<c:if test="${userLogin}"><hk:a href="/box/box_list.do">查看开箱记录</hk:a></c:if>
		</hk:rmBlankLines><br/><br/>
		<c:if test="${userCmpFunc.couponOpen}">
			优惠券<br/>
			<hk:a href="/op/coupon_create.do">发布优惠券</hk:a>|	
			<hk:a href="/op/coupon_list.do">优惠券管理</hk:a>
			<br/><br/>
		</c:if>
		<c:if test="${userCmpFunc.hkAdOpen}">
			广告平台<br/>
			<hk:rmBlankLines rm="true">
				<hk:a href="/op/gg_create.do">发布广告</hk:a>|	
				<hk:a href="/op/gg_list.do">广告管理</hk:a>
			</hk:rmBlankLines><br/><br/>
		</c:if>
		信息台<br/>
		<hk:rmBlankLines rm="true">
			<hk:a href="/info/op/info.do">信息台</hk:a>
			|<hk:a href="/info/op/info_toadd.do">创建一个信息台</hk:a>
			<c:if test="${adminUser!=null}">
			|<hk:a href="/info/admin/admin.do">信息台过期提醒</hk:a>
			</c:if>
		</hk:rmBlankLines><br/><br/>
		我的名片<br/>
		<hk:rmBlankLines rm="true">
			<hk:a href="/chgcard/act.do">名片簿</hk:a>|
			<hk:a href="/chgcard/act_addtip.do">创建换名片活动</hk:a>|
			<hk:a href="/card/card_toedit.do">编辑名片</hk:a>
		</hk:rmBlankLines><br/><br/>
		我的足迹<br/>
		<hk:rmBlankLines rm="true">
			<hk:a href="/e/cmp_nearby.do">足迹</hk:a>|
			<hk:a href="/e/op/op_toadd.do">创建足迹</hk:a>|
			<hk:a href="/e/cmp_feedlist.do">足迹动态</hk:a>|
			<hk:a href="/user/hkuser.do"><hk:data key="view.nearby.hkuser"/></hk:a>
		</hk:rmBlankLines><br/><br/>
		<hk:data key="view.myact"/><br/>
		<hk:rmBlankLines rm="true">
			<hk:a href="/act/act_useract.do"><hk:data key="view.myjoinact"/></hk:a>|
			<hk:a href="/act/op/act_toadd.do"><hk:data key="view.createact"/></hk:a>
		</hk:rmBlankLines><br/><br/>
		我的日志<br/>
		<hk:rmBlankLines rm="true">
			<hk:a href="/user/log/log_scorelog.do"><hk:data key="view.scorelog_title"/></hk:a>|
			<hk:a href="/user/log/log_hkblog.do"><hk:data key="view.hkblog_title"/></hk:a>
		</hk:rmBlankLines><br/><br/>
		<c:if test="${userLogin && bomb}">
		内容管理<br/>
		<hk:rmBlankLines rm="true">
		<hk:a href="/bomb/bomb_list.do">内容管理</hk:a>
		<c:if test="${bombadmin}">
		|<hk:a href="/adminbomb/bomb.do">爆破手管理</hk:a>
		</c:if>
		</hk:rmBlankLines>
		<br/><br/>
		</c:if>
		<c:if test="${adminUser!=null}">
		企业管理<br/>
		<hk:rmBlankLines rm="true">
			<hk:a href="/e/admin/admin_createcmp.do">创建企业</hk:a>|
			<hk:a href="/e/admin/admin_clist.do">企业管理</hk:a>|
			<hk:a href="/e/admin/admin_authlist.do">企业认领审核</hk:a>|
			<hk:a href="/e/admin/admin_toaddtag.do">添加企业标签</hk:a>|
			<hk:a href="/e/admin/admin_tlist.do">企业标签列表</hk:a>|
			<hk:a href="/e/admin/admin_findUserZone.do?f=bizcle">添加商圈</hk:a>|
			<hk:a href="/e/admin/admin_bizclelist.do">商圈列表</hk:a>|
			<hk:a href="/e/admin/admin_findUserZone.do?f=build">添加地标</hk:a>|
			<hk:a href="/e/admin/admin_buildlist.do">地标列表</hk:a>|
			<hk:a href="/e/admin/admin_awarduser.do"><hk:data key="view.company.awarduser"/></hk:a>|
			<hk:a href="/e/admin/admin_findcmp.do?fn=1&f=1">企业酷币充值</hk:a>
			
			<br/><br/>
		</hk:rmBlankLines>
		</c:if>
		<c:if test="${adminUser!=null}">
		徽章相关管理<br/>
			<hk:a href="/admin/cmpadmingroup.do">足迹组</hk:a>|
			<hk:a href="/admin/badge.do">徽章</hk:a>
			
			<br/><br/>
		</c:if>
		<c:if test="${adminUser!=null}">
		联盟管理<br/>
		<hk:rmBlankLines rm="true">
			<hk:a href="/admin/union.do">联盟列表</hk:a>|
			<hk:a href="/admin/union_tocreate.do">创建联盟</hk:a>
		</hk:rmBlankLines><br/><br/>
		</c:if>
		<c:if test="${adminUser!=null}">
		活动管理<br/>
		<hk:rmBlankLines rm="true">
			<hk:a href="/e/admin/cmpact.do">活动列表</hk:a>|<hk:a href="/e/admin/cmpact_kindlist.do">企业活动分类</hk:a>
		</hk:rmBlankLines><br/><br/>
		</c:if>
		<c:if test="${adminUser!=null}">
		火酷管理<br/>
		<hk:rmBlankLines rm="true">
			<hk:a href="/admin/zoneadmin.do">地区管理员管理</hk:a>|
			<hk:a href="/admin/mgruser.do">用户管理</hk:a>|
			<hk:a href="/admin/admin_deffollowuser.do">注册默认好友</hk:a>|
			<hk:a href="/admin/admin_seluserforcmpfunc.do">开通企业功能</hk:a>|
			<hk:a href="/admin/admin_toaddHkb.do?f=1">酷币充值</hk:a>|
			<hk:a href="/admin/admin_hkblog.do">充值记录</hk:a>|
			<hk:a href="/admin/smssend.do">短信发送</hk:a>|
			<hk:a href="/e/admin/admincmpkind.do">足迹分类管理</hk:a>|
			<hk:a href="/admin/admin_gdlist.do">地皮管理</hk:a>|
			<hk:a href="/admin/article_list.do">相关文章审核</hk:a>|
			<hk:a href="/admin/authorapply_list.do">加入火眼金睛俱乐部审核</hk:a>|
			<hk:a href="/admin/zone.do">地区管理</hk:a>|
			<hk:a href="/admin/apiuser_toadd.do">api申请</hk:a> |
			<hk:a href="/admin/adminvipuser.do">VIP用户</hk:a>
		</hk:rmBlankLines>
		<br/><br/>
		</c:if>
		</c:if>
	</div>
	<c:if test="${loginUser!=null}">
	<div class="hang"><form method="post" action="<%=request.getContextPath() %>/logout.do"><hk:submit value="退出火酷"/></form></div>
	</c:if>
	<jsp:include page="inc/foot.jsp"></jsp:include>
</hk:wap>