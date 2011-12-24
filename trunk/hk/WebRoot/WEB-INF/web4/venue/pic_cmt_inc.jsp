<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<html>
<head>
<title>
</title>
</head>
<body>
<div id="data">
<div id="cmt${cmt.cmtId }" class="block" style="background-color: #ffffff">
	<div class="pic">
		<a href="/user/${cmt.userId }/"><img src="${loginUser.head32Pic }"/></a>
	</div>
	<div class="action">
		<a href="javascript:delcmt(${cmt.cmtId })"><hk:data key="view2.delete"/></a>
	</div>
	<div class="content">
	<a class="b" href="/user/${cmt.userId }/">${loginUser.nickName }</a><br/>
	${cmt.content } <span class="ruo2"><fmt:formatDate value="${cmt.createTime}" pattern="yy-MM-dd HH:mm"/></span>
	</div>
</div>
</div>
<script type="text/javascript">
var html=document.getElementById('data').innerHTML;
parent.showNewCmt(html,${cmt.cmtId });
</script>
</body>
</html>
