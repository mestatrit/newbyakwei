<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><a href="/p/${current_notice.dataMap.userid }">${current_notice.dataMap.nick }</a> 点评了你发布的商品 <a href="${ctx_path }/tb/item?itemid=${current_notice.dataMap.itemid}">${current_notice.dataMap.itemtitle }</a> ： ${current_notice.dataMap.item_cmt_content} <a class="b" href="${ctx_path }/tb/item?itemid=${current_notice.dataMap.itemid}"> ....</a>