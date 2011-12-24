<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%String path = request.getContextPath();%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/css/a.css?v=1" />
		<link rel="shortcut icon" href="<%=path %>/favicon.ico?v=2" />
		<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/pub.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/hovertip.js"></script>
		<script type="text/javascript">
		var path="<%=path %>";
		obj_width=265;
		$(document).ready(function(){
			window.setTimeout(hovertipInit, 1);
		});
		</script>
		<c:if test="${js_value!=null}">${js_value}</c:if>
		<title>${html_title } - 火酷网</title>
	</head>
	<body>
		<div style="position: relative;">
			<a id="foo" class="ashare" href="#">分享</a>
			<div target="foo" class="share hovertip" style="display: none;">
				<a title="分享到新浪微博" href="javascript:(function(){window.open('http://v.t.sina.com.cn/share/share.php?title='+encodeURIComponent(document.title)+'&url='+encodeURIComponent(location.href)+'&source=bookmark','_blank');})()">
				<img src="<%=path %>/webst4/img/ico_sina.gif" alt="分享到新浪微博" border="0"/> 新浪微博
				</a>
				<a title="分享到网易微博" href="javascript:(function(){window.open('http://t.163.com/article/user/checkLogin.do?link=http://news.163.com/&source='+''+ '&info='+encodeURIComponent(document.title)+' '+encodeURIComponent(location.href),'_blank');})()">
				<img src="<%=path %>/webst4/img/ico_163.png"  alt="分享到网易微博" border="0"/> 分享到网易微博
				</a>
				<a href="javascript:void((function(s,d,e){if(/renren\.com/.test(d.location))return;var f='http://share.renren.com/share/buttonshare?link=',u=d.location,l=d.title,p=[e(u),'&title=',e(l)].join('');function%20a(){if(!window.open([f,p].join(''),'xnshare',['toolbar=0,status=0,resizable=1,width=626,height=436,left=',(s.width-626)/2,',top=',(s.height-436)/2].join('')))u.href=[f,p].join('');};if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else%20a();})(screen,document,encodeURIComponent));" title="分享到人人"><img src="<%=path %>/cmpwebst4/mod/pub/img/ico_renren.gif" title="分享到人人"/>人人网</a>
				<a title="分享到开心网" href="javascript:d=document;t=d.selection?(d.selection.type!='None'?d.selection.createRange().text:''):(d.getSelection?d.getSelection():'');void(kaixin=window.open('http://www.kaixin001.com/~repaste/repaste.php?&amp;rurl='+escape(d.location.href)+'&amp;rtitle='+escape(d.title)+'&amp;rcontent='+escape(d.title),'kaixin'));kaixin.focus();">
				<img src="<%=path %>/webst4/img/ico_kaixin.gif" alt="分享到开心网" border="0"/>  开心网
				</a>
				<a title="分享到豆瓣" href="javascript:var u='http://www.douban.com/recommend/?url='+location.href+'&amp;title='+encodeURIComponent(document.title);window.open(u,'douban');void(0)">
				<img src="<%=path %>/webst4/img/ico_douban.png"/> 豆瓣
				</a>
				<a title="分享到QQ空间" href="javascript:void(window.open('http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url='+encodeURIComponent(document.location.href)));">
					<img src="<%=path %>/webst4/img/ico_qzone.gif" alt="分享到QQ空间" /> 分享到QQ空间</a>
				<div class="clr"></div>
			</div>
		</div>
	</body>
</html>