<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="绑定MSN成功 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">绑定MSN成功</div>
	<div class="hang">
	1、将火酷msn机器人<span class="class">huoku.com@hotmail.com</span>添加成为自己的msn好友<br/>
	2、添加后,通过msn向火酷msn机器人发布的信息就变成火酷小喇叭了<br/>
	</div>
	<div class="hang">
		<input type="button" onclick="cplink()" value="复制火酷机器人地址"/>
	</div>
	<div class="hang"><hk:a href="/user/set/set_toSetMsn.do">重新绑定MSN</hk:a></div>
	<div class="hang"><hk:a href="/user/set/set.do">回到设置</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
	<script type="text/javascript">
	<!--
		var browser="";
		if(navigator.userAgent.toLowerCase().indexOf('msie') != -1){browser="ie";}else{browser="n";}
		function cplink(){
			if(browser!="ie"){
				alert("您的浏览器不支持复制，请手动选中文字，进行复制");
				return;
			}
		  	window.clipboardData.setData("Text","huoku.com@hotmail.com");
		  	window.alert("地址已复制到剪贴板，您可以通过MSN QQ发送给好友");
		}
	-->
	</script>
</hk:wap>