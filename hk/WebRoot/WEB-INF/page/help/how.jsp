<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="怎么吹喇叭？ - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">怎么吹喇叭？</div>
	<div class="hang">
	1、你可以输入最多140个字,记录并分享此刻的你以及发生在你周围的事情。<br/>
	2、通过个人主页的设置绑定手机号后,可以通过短信吹喇叭,发送短信到1066916025即可短信吹喇叭,随时随地记录.<br/>
	3、在喇叭内容中,可以通过@昵称 来引用其他火酷会员.<br/>
	4、在喇叭内容中,可以通过#关键词 来创造或者加入一个关键词,把当前喇叭聚合在某关键词下面. <br/>
	</div>
	<div><hk:a href="/back.do?from=${from}&w=${w }&repage=${repage }&ouserId=${ouserId }">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>