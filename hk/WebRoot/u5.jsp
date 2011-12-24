<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.CmpModService"%>
<%@page import="com.hk.frame.util.HkUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.bean.CmpArticleBlock"%>
<%@page import="com.hk.bean.CmpArticle"%>
<%@page import="com.hk.svr.CmpArticleService"%>
<%
CmpModService cmpModService=(CmpModService)HkUtil.getBean("cmpModService");
CmpArticleService cmpArticleService=(CmpArticleService)HkUtil.getBean("cmpArticleService");
List<CmpArticleBlock> list=cmpModService.getCmpArticleBlockList();
for(CmpArticleBlock o:list){
	CmpArticle cmpArticle=cmpArticleService.getCmpArticle(o.getArticleId());
	if(cmpArticle==null){
		cmpModService.deleteCmpArticleBlock(o.getOid());
	}
	else{
		o.setCmpNavOid(cmpArticle.getCmpNavOid());
		cmpModService.updateCmpArticlePageBlock(o);
	}
}
%>
<h1>ok</h1>