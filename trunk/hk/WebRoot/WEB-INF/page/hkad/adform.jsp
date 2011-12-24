<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.HkAd"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:form enctype="multipart/form-data" action="${adform_action}">
	<hk:hide name="oid" value="${oid}"/>
	<hk:hide name="ch" value="1"/>
	名称：<br/>
	<hk:text name="name" value="${o.name}"/><br/><br/>
	展示地区(<span class="ruo s">为空时，全球展示</span>)：<br/>
	<hk:text name="zoneName" value="${zoneName}"/><br/><br/>
	浏览数量：<br/>
	<hk:text name="totalViewCount" value="${o.totalViewCount}"/><br/><br/>
	展示方式：<br/>
	<hk:radioarea name="showflg" checkedvalue="${o.showflg}">
		<hk:radio oid="char" value="<%=HkAd.SHOWFLG_CHAR %>"/><label for="char">文字广告</label><br/>
		<hk:text name="adData" value="${o.adData}"/><br/><br/>
		<hk:radio oid="img" value="<%=HkAd.SHOWFLG_IMG %>"/><label for="img">图片广告</label><br/>
		<hk:file name="f"/><br/>
	</hk:radioarea><br/>
	广告链接:<br/>
	<hk:text name="href" value="${o.href}"/><br/>
	<hk:submit value="提交"/>
</hk:form>