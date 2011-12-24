<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="通过短信更新火酷 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
	通过短信更新火酷<br/>
	通过个人首页设置好手机号号,发送短信到1066916025直接更新火酷的小喇叭.短信吹喇叭将获得额外的积分奖励.<br/>
	在喇叭中支持@对象和#对象,不过要记得对象后面空格.@对象将自动匹配火酷会员,#对象将自动匹配火酷关键字.<br/>
	贴士：建议在手机通讯录中建立一个火酷联系人,其手机号设置为1066916025.也可以通过先拨打 1066916025这个号码,然后通过 通话记录发送短信或存储号码.<br/>
	</div>
	<div class="hang"><hk:a href="/help_back.do?${query}">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>