<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
<hk:hide name="aid" value="${aid}"/>
<hk:hide name="ch" value="1"/>
<div class="row">我出钱，你操心</div>
<hk:actioninvoke mappinguri="/tb/ask_loadaskcat"/>
<div class="row">
问题分类：<select id="id_parent" name="parent_cid" class="split-r" onchange="initcat('id_cid',this.value,0)"></select>
<select id="id_cid" name="cid"></select>
<div class="infowarn" id="_cid"></div>
</div>
<div class="row">
	请简明清晰地描述您的疑问(最多50字)<br/>
	<hk:textarea name="title" style="width:582px;height: 45px;" value="${title}"/>
	<div class="infowarn" id="_title"></div>
	<a id="ask_detail_a" class="ask_detail_down" href="javascript:showdetail()">详细提问</a>
</div>
<div id="detail" class="row" style="display: none;">
	详细描述您所遇到的问题(最多500字)<br/>
	<hk:textarea name="content" style="width:582px;height: 150px" value="${tbAsk.content}"/>
	<div class="infowarn" id="_content"></div>
</div>
<div class="row" align="right">
	<c:if test="${user_has_sinaapi}">
		<input id="_tosina" type="checkbox" name="create_to_sina" value="true"/><label for="_tosina">发送到新浪微博</label>
	</c:if>
	<input type="submit" class="btn split-r" value="提交问题"/>
	<c:if test="${aid>0}"><a href="${denc_return_url }">返回</a></c:if>
	<c:if test="${aid==null}"><a href="${ctx_path }/tb/user_asked?userid=${login_user.userid}">回到我的问题列表</a></c:if>
</div>
</form>
<script type="text/javascript">
var parent_cid=${parent_cid};
var cid=${cid};
var cat=new Array();
<c:forEach var="cat" items="${askcatlist}" varStatus="idx">
cat[${idx.index }]=new Array(${cat.cid },${cat.parent_cid },'${cat.name }');
</c:forEach>
function initprentcat(select_parent_cid_id, selected_cid){
	var o = getObj(select_parent_cid_id);
	o.options.length = 0;
	o.options[0] = new Option('请选择', 0);
	for (var i = 0; i < cat.length; i++) {
		if (cat[i][1] == 0) {
			o.options[o.options.length] = new Option(cat[i][2], cat[i][0]);
			if (selected_cid == cat[i][0]) {
				o.options[o.options.length - 1].selected = true;
			}
		}
	}
}

function initcat(select_cid_id, parent_cid, selected_cid){
	var o = getObj(select_cid_id);
	o.options.length = 0;
	o.options[0] = new Option('请选择', 0);
	for (var i = 0; i < cat.length; i++) {
		if (cat[i][1] == parent_cid && cat[i][1] > 0) {
			o.options[o.options.length] = new Option(cat[i][2], cat[i][0]);
			if (selected_cid == cat[i][0]) {
				o.options[o.options.length - 1].selected = true;
			}
		}
	}
}
initprentcat('id_parent',parent_cid);
initcat('id_cid',parent_cid,cid);
</script>
<script type="text/javascript">
var err_code_<%=Err.TB_ASK_CID_ERROR %>={objid:"_cid"};
var err_code_<%=Err.TB_ASK_TITLE_ERROR %>={objid:"_title"};
var err_code_<%=Err.TB_ASK_CONTENT_ERROR %>={objid:"_content"};
function showdetail(){
	if ($('#detail').css('display') == 'none') {
		$('#detail').css('display', 'block');
		$('#ask_detail_a').addClass('ask_detail_up').removeClass('ask_detail_down');
	}
	else {
		$('#detail').css('display', 'none');
		$('#ask_detail_a').addClass('ask_detail_down').removeClass('ask_detail_up');
	}
}
function keysubfrm(event){
	if((event.ctrlKey)&&(event.keyCode==13)){
		if(subfrm('frm')){
			getObj('frm').submit();
		}
	}
}
function subfrm(frmid){
	showGlass(frmid);
	setHtml('_title','');
	setHtml('_content','');
	return true;
}
<c:if test="${not empty tbAsk.content}">
showdetail();
</c:if>

</script>