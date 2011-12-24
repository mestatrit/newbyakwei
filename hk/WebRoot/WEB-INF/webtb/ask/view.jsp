<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">${tbAsk.title }
</c:set><c:set var="html_head_value" scope="request">
<meta name="description" content="${tbAsk.title }" />
<meta name="keywords"  content="${tbAsk.title }|顾问家" />
<script type="text/javascript" src="${ctx_path }/webtb/js/hovertip.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	  window.setTimeout(hovertipInit, 1);
   });
</script>
</c:set><c:set var="html_body_content" scope="request">
<div class="pl">
	<div class="ask_ans">
		<div class="mod ask" style="margin-bottom: 0">
			<ul class="itemcmtlist ask_list">
				<li style="border: none;">
					<div class="head">
						<a href="/p/${tbUser.userid }"><img src="${tbUser.pic_url_48 }"/><br/>${tbUser.show_nick }</a>
						<c:if test="${tbUser.sinaVerified}"><br/><a target="_blank" href="http://t.sina.com.cn/pub/verified"><img src="${ctx_path }/webtb/img/sina_vip.gif" title="新浪微博认证用户"/></a></c:if>
					</div>
					<div class="body">
						<p class="b"><c:if test="${tbAsk.resolved}">(已解决)</c:if>
						${tbAsk.title }</p>
						<fmt:formatDate value="${tbAsk.create_time}" pattern="yyyy-MM-dd HH:mm"/><br/>
						${tbAsk.content }
						<div class="op">
						<c:if test="${tbAsk.userid==login_user.userid}">
							<a class="opa" href="javascript:updateask(${tbAsk.aid })">修改</a>
							<a class="opa" href="javascript:delask(${tbAsk.aid })">删除</a>
						</c:if>
						</div>
					</div>
					<div class="clr"></div>
				</li>
			</ul>
		</div>
		<c:if test="${tbAsk.ansid>0}">
		<div class="mod bestans" style="margin-bottom: 0">
			<div class="mod_title" style="border: none;"><h1>最佳答案</h1></div>
			<ul class="itemcmtlist ask_list">
				<li style="border: none;">
					<div class="head">
						<a href="/p/${bestans.tbUser.userid }"><img src="${bestans.tbUser.pic_url_48 }"/><br/>${bestans.tbUser.show_nick }</a>
						<c:if test="${bestans.tbUser.sinaVerified}"><br/><a target="_blank" href="http://t.sina.com.cn/pub/verified"><img src="${ctx_path }/webtb/img/sina_vip.gif" title="新浪微博认证用户"/></a></c:if>
					</div>
					<div class="body">
						<fmt:formatDate value="${tbAsk.create_time}" pattern="yyyy-MM-dd HH:mm"/><br/>
						${bestans.content }
						<c:if test="${not empty bestans.resolve_content}">
							<p class="b">建议方案：</p>
							<c:forEach var="item" items="${bestans.tbAnswerItemList}">
								<div class="picbox">
								<a target="_blank" href="${ctx_path }/tb/ask_item?ansid=${bestans.ansid}&id=${item.id}"><img src="${item.pic_url }_60x60.jpg"/></a>
								<p>￥${item.price }</p>
								</div>
							</c:forEach>
							<div class="clr"></div>
							<div class="row">合计：${bestans.expression } 元</div>
						</c:if>
						<div class="op">
							<c:if test="${login_user!=null}">
							<a class="opa" href="javascript:supportanswer(${bestans.ansid })">支持<span id="ans_${bestans.ansid }_support_num"><c:if test="${bestans.support_num>0}">(${bestans.support_num })</c:if></span></a>
							<a class="opa" href="javascript:discmdanswer(${bestans.ansid })">反对<span id="ans_${bestans.ansid }_discmd_num"><c:if test="${bestans.discmd_num>0}">(${bestans.discmd_num })</c:if></span></a>
							</c:if>
							<c:if test="${tbAsk.userid==login_user.userid}">
								<c:if test="${tbAsk.ansid!=bestans.ansid}"><a class="opa" href="javascript:selbestanswer(${bestans.ansid })">选择这个方案</a></c:if>
								<c:if test="${tbAsk.ansid==bestans.ansid}"><a class="opa" href="javascript:cancelbestanswer(${aid })">取消最佳答案</a></c:if>
							</c:if>
							<c:if test="${bestans.userid==login_user.userid}">
								<a class="opa" href="javascript:updateanswer(${bestans.ansid })">修改</a>
								<a class="opa" href="javascript:delanswer(${bestans.ansid })">删除</a>
							</c:if>
						</div>
					</div>
					<div class="clr"></div>
				</li>
			</ul>
		</div>
		</c:if>
	</div>
	<div class="mod" style="margin-top: 20px">
		<div class="mod_title">大家给出的方案<a name="answer_area"></a></div>
		<div class="mod_content">
			<ul class="itemcmtlist anslist">
				<c:if test="${fn:length(anslist)==0 && page==1}"><p class="b align_c">暂时还没有人回答</p></c:if>
				<c:if test="${fn:length(list)==0 && page>1}"><p class="b align_c">本页没有数据</p></c:if>
				<c:forEach var="ans" items="${anslist}" varStatus="idx">
					<li<c:if test="${idx.index%2!=0}"> class="odd"</c:if>>
						<div class="head">
							<a href="/p/${ans.tbUser.userid }"><img src="${ans.tbUser.pic_url_48 }"/><br/>${ans.tbUser.show_nick }</a>
							<c:if test="${ans.tbUser.sinaVerified}"><br/><a target="_blank" href="http://t.sina.com.cn/pub/verified"><img src="${ctx_path }/webtb/img/sina_vip.gif" title="新浪微博认证用户"/></a></c:if>
						</div>
						<div class="body">
							<fmt:formatDate value="${tbAsk.create_time}" pattern="yyyy-MM-dd HH:mm"/><br/>
							${ans.content }
							<c:if test="${not empty ans.resolve_content}">
							<p class="b">建议方案：</p>
							<c:forEach var="item" items="${ans.tbAnswerItemList}">
								<div class="picbox">
								<a target="_blank" href="${ctx_path }/tb/ask_item?ansid=${ans.ansid}&id=${item.id}"><img src="${item.pic_url }_60x60.jpg"/></a>
								<p>￥${item.price }</p>
								</div>
							</c:forEach>
							<div class="clr"></div>
							<div class="row">合计：${ans.expression } 元</div>
							</c:if>
							<div class="op">
								<c:if test="${login_user!=null}">
								<a class="opa" href="javascript:supportanswer(${ans.ansid })">支持<span id="ans_${ans.ansid }_support_num"><c:if test="${ans.support_num>0}">(${ans.support_num })</c:if></span></a>
								<a class="opa" href="javascript:discmdanswer(${ans.ansid })">反对<span id="ans_${ans.ansid }_discmd_num"><c:if test="${ans.discmd_num>0}">(${ans.discmd_num })</c:if></span></a>
								</c:if>
								<c:if test="${tbAsk.userid==login_user.userid}">
									<c:if test="${tbAsk.ansid!=ans.ansid}"><a class="opa" href="javascript:selbestanswer(${ans.ansid })">选择这个方案</a></c:if>
									<c:if test="${tbAsk.ansid==ans.ansid}"><a class="opa" href="javascript:cancelbestanswer(${aid })">取消最佳答案</a></c:if>
								</c:if>
								<c:if test="${ans.userid==login_user.userid}">
									<a class="opa" href="javascript:updateanswer(${ans.ansid })">修改</a>
									<a class="opa" href="javascript:delanswer(${ans.ansid })">删除</a>
								</c:if>
							</div>
						</div>
						<div class="clr"></div>
					</li>
				</c:forEach>
			</ul>
			<div>
				<c:set var="page_url" scope="request">${ctx_path}/tb/ask?aid=${aid}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
			<c:if test="${login_user!=null}">
				我的建议：<span class="ruo">最多3000字</span><a name="create_answer_area"></a><br />
				<form id="answerfrm" onsubmit="return subanswerfrm(this.id)" method="post" action="${ctx_path }/tb/ask_prvanswer" target="hideframe">
					<hk:hide name="aid" value="${aid}"/>
					<div class="row">
						<textarea name="content" onkeyup="keysubanswerfrm(event)" style="width: 560px; height: 120px;"></textarea>
						<div class="infowarn" id="info"></div>
					</div>
					<a id="addurlinput_a" class="split-r" href="javascript:addurlinput()">添加链接</a>（支持淘宝商品链接）<br/>
					<div class="row" align="right" style="width:560px;">
						<c:if test="${user_has_sinaapi}">
							<input id="_tosina" type="checkbox" name="create_to_sina" value="true"/><label for="_tosina">发送到新浪微博</label>
						</c:if>
						<input type="submit" class="btn" value="提交" />
					</div>
				</form>
			</c:if>
		</div>
	</div>
</div>
<div class="pr">
<div class="mod">
	<div class="fr">
	<script type="text/javascript">
	obj_width=265;
	</script>
	<jsp:include page="../inc/share.jsp"></jsp:include>
	</div>
	<div class="clr"></div>
</div>
<jsp:include page="../inc/ask_right_inc.jsp"></jsp:include>
</div>
<div class="clr"></div>
<script type="text/javascript">
var submited=false;
var len=0;
$(document).ready(function(){
	$('ul.anslist li').bind('mouseenter', function(){
		$(this).addClass('enter');
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
	});
	$('ul.ask_list li').bind('mouseenter', function(){
		$(this).addClass('enter3');
	}).bind('mouseleave', function(){
		$(this).removeClass('enter3');
	});
});
function keysubanswerfrm(event){
	if ((event.ctrlKey) && (event.keyCode == 13)) {
		if (subanswerfrm('answerfrm')) {
			getObj('answerfrm').submit();
		}
	}
}
function subanswerfrm(frmid){
	if(submited){
		return false;
	}
	submited=true;
	showGlass(frmid);
	setHtml('info', '');
	return true;
}
function answererr(e, msg, v){
	submited=false;
	setHtml('info', msg);
	hideGlass();
}
function answerok(e, msg, v){
	tourl('${ctx_path}/tb/ask?aid=${aid}&toanswer=1');
}
function addurlinput(){
	if(len>=10){
		$('#addurlinput_a').css('display','none');
		return;
	}
	var id = createObjId('row_url');
	var html = '<div id="' + id + '" class="row">商品链接：<hk:text name="item_url" clazz="text" style="width:400px"/> <a href="javascript:removeurlinput(\'' + id + '\')">删除</a></div>';
	insertObjBefore(html, "addurlinput_a");
	len++;
}
function removeurlinput(id){
	delObj(id);
	$('#addurlinput_a').css('display','block');
	len--;
}
function updateanswer(ansid){
	tourl('${ctx_path}/tb/ask_prvupdateanswer?ansid=' + ansid + "&return_url=" + encodeLocalURL());
}
function delanswer(ansid){
	if (window.confirm('确实要删除此建议？')) {
		doAjax('${ctx_path}/tb/ask_prvdelanswer?ansid=' + ansid, function(data){
			refreshurl();
		});
	}
}
function delask(aid){
	if (window.confirm('确实要删除此问题？')) {
		doAjax('${ctx_path}/tb/ask_prvdelask?aid=' + aid, function(data){
			tourl('${ctx_path}/tb/user_asked?userid=${tbAsk.userid}');
		});
	}
}
function updateask(aid){
	tourl('${ctx_path}/tb/ask_prvupdateask?aid=' + aid + "&return_url=" + encodeLocalURL());
}
function supportanswer(ansid){
	doAjax('${ctx_path}/tb/ask_prvsupportanswer?ansid=' + ansid, function(data){
		showvoteinfo(ansid, data);
	});
}

function discmdanswer(ansid){
	doAjax('${ctx_path}/tb/ask_prvdiscmdanswer?ansid=' + ansid, function(data){
		showvoteinfo(ansid, data);
	});
}

function showvoteinfo(ansid, data){
	var arr = data.split(';');
	var res_support = "";
	var res_discmd = "";
	if (parseInt(arr[0]) > 0) {
		res_support = '(' + arr[0] + ')';
	}
	if (parseInt(arr[1]) > 0) {
		res_discmd = '(' + arr[1] + ')';
	}
	setHtml('ans_' + ansid + '_support_num', res_support);
	setHtml('ans_' + ansid + '_discmd_num', res_discmd);
}
function selbestanswer(ansid){
	doAjax('${ctx_path}/tb/ask_prvselbestanswer?ansid=' + ansid, function(data){
		refreshurl();
	});
}
function cancelbestanswer(aid){
	doAjax('${ctx_path}/tb/ask_prvcancelbestanswer?aid=' + aid, function(data){
		refreshurl();
	});
}
<c:if test="${toanswer==1}">
topos('answer_area');
</c:if>
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>