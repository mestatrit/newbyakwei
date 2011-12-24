<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="绑定手机号 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
	绑定方法：短信编辑“bd昵称:密码”到1066916025绑定手机号.<br/>
	举例：昵称为心飞扬的火酷用户密码是123456,则需要编辑短信<span class="orange">bd心飞扬:123456</span>到1066916025就能绑定成功手机号.<br/>
	绑定手机号后,你就可以通过短信吹喇叭了.<br/>
	请在手机通讯录中建立一个联系人,姓名:火酷,手机号码:1066916025.以后发送短信内容<span class="orange">(内容不能少于7个字符)</span>到1066916025就自动更新了火酷的小喇叭. 
	</div>
	<div class="hang">
		<c:if test="${userOtherInfo.mobileBind==1}">
			您已经成功绑定了手机号${userOtherInfo.mobile}
		</c:if>
		<c:if test="${userOtherInfo.mobileBind!=1}">
			发送短信后,<hk:a href="/user/set/set_toSetMobile.do">刷新本页面</hk:a>查看绑定状态.
		</c:if>
	</div>
	<div class="hang"><hk:a href="/user/set/set_toSetMobile.do">返回</hk:a></div>
	<div class="hang"><hk:a href="/user/set/set.do">回到设置</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>