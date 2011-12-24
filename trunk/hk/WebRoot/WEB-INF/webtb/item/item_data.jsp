<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%>
<c:if test="${taobao_success}">
	<c:if test="${taobao_item==null}">
		<div class="b">在淘宝没有查询到此商品</div>
	</c:if>
	<c:if test="${taobao_item!=null}">
		<div class="f_l" style="width:330px;width:150px;">
			<img src="${taobao_item.picUrl }_120x120.jpg"/>
		</div>
		<div class="f_l" style="width:500px">
			名　　称：${taobao_item.title }<br/>
			价　　格：${taobao_item.price }元<br/>
			所在地区：${taobao_item.location.state } ${taobao_item.location.city }<br/>
			宝贝类型：<c:if test="${taobao_item.stuffStatus=='new'}">全新</c:if><c:if test="${taobao_item.stuffStatus=='unused'}">限制</c:if><c:if test="${taobao_item.stuffStatus=='second'}">二手</c:if>
		</div>
		<div class="clr"></div><br/>
		<div id="cmt_area" class="rd" style="width:520px;">
		<form id="frm" method="post" onsubmit="return subcmt(this.id)" action="${ctx_path }/tb/item_prvcreate" target="hideframe">
			<hk:hide name="ch" value="1"/>
			<input type="hidden" id="_score" name="score"/>
			<input id="_taobao_item_url" name="taobao_item_url" type="hidden" value="${taobao_item_url }"/>
			<div>
				<div class="f_l">
					<input id="want_item" type="radio" name="user_item_status" value="1" onclick="showscore()"/><label for="want_item">我有</label>
					<input id="hold_item" type="radio" name="user_item_status" value="0" onclick="hidescore()"/><label for="hold_item">想买</label>
				</div>
				<div id="score_area" class="f_l" style="margin-left: 30px;display: none;">
					<div class="f_l">我的打分：</div>
					<div class="f_l"><div id="starscore"></div></div>
					<div class="clr"></div>
				</div>
				<div class="clr"></div>
			</div>
			<div>
				说两句：
				<br/>
				<textarea name="content" onkeyup="keysubcmt(event)" style="width:500px;height:150px;"></textarea>
				<div class="infowarn" id="info"></div>
			</div>
			<div class="row" align="right">
				<c:if test="${user_has_sinaapi}">
					<input id="_tosina" type="checkbox" name="create_to_sina_weibo" value="true"/><label for="_tosina">发送到新浪微博</label>
				</c:if>
				<input type="submit" class="btn" value="提交点评"/>
			</div>
		</form>
		</div>
	</c:if>
</c:if>
<c:if test="${!taobao_success}">
<div class="b">淘宝暂时没有响应，请稍后再试</div>
</c:if>