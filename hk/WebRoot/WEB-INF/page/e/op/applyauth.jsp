<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		1、确认您是这个商户的所有者或当前经营者。<br/>
		2、认领后，您将可以维护商户信息并管理会员，发行商户的电子会员卡和优惠券等。<br/>
		3、认领后，消费者可以使用在线预定，其信息将发送到您的商户指定的手机中。<br/>
		4、认领一个商户，需要20000个火酷币，价值2000元人民币。如果没有，可以<a href="http://shop58916393.taobao.com/" target="_blank" class="line">马上充值</a>。<br/>
		5、如果仍然不清楚，可以<hk:a clazz="line" href="/e/op/op_toApplyAuth2.do?companyId=${companyId}">提交信息，等待火酷工作人员联系您</hk:a>。<br/>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>