function opitem_user_ref_hold(itemid, o, oid){
	var con_id = 'div_hold_' + oid;
	var want_con_id = 'div_want_' + oid;
	if (!is_user_login) {
		alert('请先登录');
		return;
	}
	if (o.checked == true) {
		doAjax(path + '/tb/item_prvcheckuseritem?itemid=' + itemid, function(data){
			var tmp = data.split(';');
			var cmt_flg = parseInt(tmp[0]);
			var score_flg = parseInt(tmp[1]);
			if (cmt_flg == 1) {// 已经有点评
				getObj('ch_want_' + oid).checked = false;
				doAjax(path + '/tb/item_prvstatusown?itemid=' + itemid + '&own=1', function(){
				});
			}
			else {// 没有点评，提示写点评
				showcmtwin(o.id, itemid);
			}
		});
	}
	else {
		doAjax(path + '/tb/item_prvdelstatusown?itemid=' + itemid + '&own=1', function(){
		});
	}
}

function opitem_user_ref_want(itemid, o, oid){
	var con_id = 'div_want_' + oid;
	var hold_con_id = 'div_hold_' + oid;
	if (!is_user_login) {
		alert('请先登录');
		return;
	}
	if (o.checked == true) {
		getObj('ch_hold_' + oid).checked = false;
		doAjax(path + '/tb/item_prvstatuswant?itemid=' + itemid + '&want=1', function(){
		});
	}
	else {
		doAjax(path + '/tb/item_prvdelstatuswant?itemid=' + itemid + '&want=1', function(){
		});
	}
}

function showcmtwin(checkbox_id, itemid){
	var title = "添加点评";
	var html = "<div>";
	html += '我也说两句：<span class="ruo">最多500字</span><a name="create_cmt_area"></a><br />';
	html += '<form id="win_cmtfrm" onsubmit="return subcmtfrm(this.id)" method="post" action="' + path + '/tb/itemcmt_prvcreate" target="hideframe">';
	html += '<input type="hidden" name="itemid" value="' + itemid + '"/><input type="hidden" name="formid" value="win_cmtfrm"/>';
	html += '<div class="row">';
	html += '<textarea name="content" onkeyup="keysubcmtfrm(\'win_cmtfrm\',event)" style="width: 560px; height: 120px;"></textarea>';
	html += '<div class="infowarn" id="info"></div>';
	html += '</div>';
	html += '<div class="row" align="right" style="width:560px;">';
	html += '<input type="submit" class="btn" value="提交点评" />';
	html += '</div></form></div>';
	createWin('win_cmt', 640, 320, title, html, "hidecmtwin('" + checkbox_id + "')");
}

function hidecmtwin(id){
	getObj(id).checked = false;
	hideWindow('win_cmt');
}

var submit_cmt = false;
function subcmtfrm(frmid){
	if (submit_cmt) {
		return false;
	}
	submit_cmt = true;
	setHtml('info', '');
	showGlass(frmid);
	return true;
}

function keysubcmtfrm(frmid, event){
	if ((event.ctrlKey) && (event.keyCode == 13)) {
		if (subcmtfrm(frmid)) {
			getObj(frmid).submit();
		}
	}
}

function cmterr(e, msg, v){
	setHtml(v + 'info', msg);
	hideGlass();
	submit_cmt = false;
}

function cmtok(e, msg, v){
	setHtml('win_cmt_content', '评论成功');
	hideGlass();
	delay(function(){
		hideWindow('win_cmt');
	}, 2000);
}
