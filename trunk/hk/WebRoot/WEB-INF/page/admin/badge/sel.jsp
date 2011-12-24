<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="创建徽章 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		创建徽章<br/>
		<div class="hang even"><hk:a href="/admin/badge_createnolimit.do">不限制条件</hk:a>(只需要配置报到数量的徽章)</div>
		<div class="hang even"><hk:a href="/admin/badge_createsys.do">离散条件</hk:a>(无规律的徽章)</div>
		<div class="hang even"><hk:a href="/admin/badge_searchcmp.do?method=createcycle">周期足迹报到数量限制</hk:a>(限制在某个周期内某个足迹的报到数量)</div>
		<div class="hang even"><hk:a href="/admin/badge_searchcmp.do?method=createcmpcheckin">某个足迹报到数量限制</hk:a>(现在某个足迹的报到数量)</div>
		<div class="hang even"><hk:a href="/admin/badge_parentkindlist.do?method=createcmpcheckin">足迹分类报到数量限制</hk:a>(限制某个分类的所有足迹报到数量)</div>
		<div class="hang even"><hk:a href="/admin/badge_searchcmpadmingroup.do?method=createcmpgroupcheckin">足迹组数量限制</hk:a>(设置某个足迹组的报到数量，需要先设置足迹组的数据)</div>
		<div class="hang even"><hk:a href="/admin/badge_createinvite.do">邀请获得徽章</hk:a></div>
	</div>
	<div class="hang"><hk:a href="/admin/badge.do">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>