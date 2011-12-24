<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<div>
	<hk:form method="post" onsubmit="return subkeyfrm(this.id)" action="/box/op/op_updateboxkeyweb.do">
		<hk:hide name="boxId" value="${boxId}"/>
		<table class="infotable" cellpadding="0" cellspacing="0">
			<tr>
				<td width="90px">开箱暗号</td>
				<td>
					<div class="f_l">
						<input type="text" id="boxkey" name="boxKey" class="text" maxlength="20" />
						<br />
						<div class="error" id="boxkey_error"></div>
					</div>
					<div class="clr"></div>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<div class="form_btn">
						<hk:submit value="保存" clazz="btn"/>
					</div>
				</td>
			</tr>
		</table>
	</hk:form>
</div>