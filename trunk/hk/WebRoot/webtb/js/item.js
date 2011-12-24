var submit_cmt = false;
var popstar = null;
function set_item_ownner(o) {
	if (!is_user_login) {
		alert('请先登录');
		return;
	}
	if (o.checked == true) {
		doAjax(path + '/tb/item_prvcheckuseritem?itemid=' + itemid, function(data) {
			var tmp = data.split(';');
			var cmt_flg = parseInt(tmp[0]);
			var score_flg = parseInt(tmp[1]);
			if (cmt_flg == 1 && score_flg == 1) {// 已经有点评和打分
					getObj('wanter').checked = false;
					doAjax(path + '/tb/item_prvstatusown?itemid=' + itemid + '&own=1', function() {
					});
				}
				else if (cmt_flg == 0 && score_flg == 1) {// 没有点评，已经打分，提示写点评
					showcmtwin();
				}
				else if (cmt_flg == 1 && score_flg == 0) {// 已经点评，没有打分，提示打分
					showscorewin();
				}
				else {// 没有点评，没有打分
					showcmtandscorewin();
				}
			});
	}
	else {
		doAjax(path + '/tb/item_prvdelstatusown?itemid=' + itemid + '&own=1', function() {
		});
	}
}

function showscorewin() {
	var title = "你的打分";
	var html = "<div id='starscore2' style='margin-left:50px'>";
	html += '</div>';
	createWin('win_score', 500, 180, title, html, "hidescorewin()");
	popstar = new StarObj('starscore2', -1, function(star_idx) {
		doAjax(path + '/tb/item_prvcreatescore?itemid=' + itemid + '&score=' + (star_idx + 1), function(data) {
			getObj('ownner').checked = true;
			getObj('wanter').checked = false;
			star0 = new StarObj('starscore0', star_idx, function(star_idx) {
				doAjax(path + '/tb/item_prvcreatescore?itemid=' + itemid + '&score=' + (star_idx + 1), function(data) {
				});
			});
			star0.show();
			$('#my_score_area').css('display', 'block');
		});
	});
	popstar.show();
}

function hidescorewin() {
	popstar = null;
	if (star0 == null) {
		getObj('ownner').checked = false;
	}
	hideWindow('win_score');
}

function set_item_wanter(o) {
	if (!is_user_login) {
		alert('请先登录');
		return;
	}
	if (o.checked == true) {
		getObj('ownner').checked = false;
		doAjax(path + '/tb/item_prvstatuswant?itemid=' + itemid + '&want=1', function() {
		});
	}
	else {
		doAjax(path + '/tb/item_prvdelstatuswant?itemid=' + itemid + '&want=1', function() {
		});
	}
}

function subcmtfrm(frmid) {
	if (submit_cmt) {
		return false;
	}
	submit_cmt = true;
	if (frmid == 'win_cmtfrm') {
		setHtml('pop_info', '');
	}
	setHtml('info', '');
	showGlass(frmid);
	return true;
}

function keysubcmtfrm(frmid, event) {
	if ((event.ctrlKey) && (event.keyCode == 13)) {
		if (subcmtfrm(frmid)) {
			getObj(frmid).submit();
		}
	}
}

function cmterr(e, msg, v) {
	setHtml(v + 'info', msg);
	hideGlass();
	submit_cmt = false;
}

function cmtok(e, msg, v) {
	tourl(path + '/tb/item?itemid=' + itemid + '&tocmt=1');
}

function showcmtwin() {
	var title = "添加点评";
	var html = "<div>";
	html += '我也说两句：<span class="ruo">最多500字</span><a name="create_cmt_area"></a><br />';
	html += '<form id="win_cmtfrm" onsubmit="return subcmtfrm(this.id)" method="post" action="' + path + '/tb/itemcmt_prvcreate" target="hideframe">';
	html += '<input type="hidden" name="itemid" value="' + itemid + '"/><input type="hidden" name="formid" value="win_cmtfrm"/>';
	html += '<input type="hidden" name="holditem" value="true"/>';
	html += '<div class="row">';
	html += '<textarea name="content" onkeyup="keysubcmtfrm(\'win_cmtfrm\',event)" style="width: 560px; height: 120px;"></textarea>';
	html += '<div class="infowarn" id="pop_info"></div>';
	html += '</div>';
	html += '<div class="row" align="right" style="width:560px;">';
	html += '<input type="submit" class="btn" value="提交点评" />';
	html += '</div></form></div>';
	createWin('win_cmt', 640, 320, title, html, "hidecmtwin()");
}

function showcmtandscorewin() {
	var title = "添加点评";
	var html = "<div>";
	html += '我也说两句：<span class="ruo">最多500字</span><a name="create_cmt_area"></a><br />';
	html += '<form id="win_cmtfrm" onsubmit="return subcmtfrm(this.id)" method="post" action="' + path + '/tb/itemcmt_prvcreate" target="hideframe">';
	html += '<input type="hidden" name="itemid" value="' + itemid + '"/><input type="hidden" name="formid" value="win_cmtfrm"/>';
	html += '<input type="hidden" name="score" value="" id="_score"/>';
	html += '<input type="hidden" name="holditem" value="true"/>';
	html += '<div class="row">';
	html += '<div class="f_l">我的打分：</div><div class="f_l" id="starscore1"></div>';
	html += '</div>';
	html += '<div class="row">';
	html += '<textarea name="content" onkeyup="keysubcmtfrm(\'win_cmtfrm\',event)" style="width: 560px; height: 120px;"></textarea>';
	html += '<div class="infowarn" id="pop_info"></div>';
	html += '</div>';
	html += '<div class="row" align="right" style="width:560px;">';
	html += '<input type="submit" class="btn" value="提交点评" />';
	html += '</div></form></div>';
	createWin('win_cmt', 640, 360, title, html, "hidecmtwin()");
	popstar = new StarObj('starscore1', -1, function(star_idx) {
		getObj('_score').value = star_idx + 1;
	});
	popstar.show();
}

function hidecmtwin() {
	popstar = null;
	getObj('ownner').checked = false;
	hideWindow('win_cmt');
}

function delcmt(cmtid) {
	if (window.confirm('确实要删除？')) {
		addGlass('cmt_op_' + cmtid, true);
		doAjax(path + '/tb/itemcmt_prvdelete?cmtid=' + cmtid, function(data) {
			refreshurl();
		});
	}
}

function updatecmt(cmtid) {
	tourl(path + '/tb/itemcmt_prvupdate?cmtid=' + cmtid + "&return_url=" + encodeLocalURL());
}

function keysubreply(cmtid, event) {
	if ((event.ctrlKey) && (event.keyCode == 13)) {
		if (subreply('replyfrm_' + cmtid)) {
			getObj('replyfrm_' + cmtid).submit();
		}
	}
}

function subreply(frmid) {
	showGlass(frmid);
	return true;
}

function replyerr(e, msg, v) {

}

function replyok(e, msg, v) {
	refreshurl();
}

function replystatus(cmtid) {
	var status = $('#reply_area' + cmtid).attr('status');
	if (status == "0") {
		$('#li_' + cmtid).attr('status', '1');
		$('#li_' + cmtid).css('background-color', '#ffffff');
		var glassid = addGlass('cmt_op_' + cmtid, false);
		doAjax(path + "/tb/itemcmt_loadreply?cmtid=" + cmtid, function(data) {
			removeGlass(glassid);
			$('#reply_area' + cmtid).attr('status', '1');
			setHtml('reply_area' + cmtid, data);
			$('#reply_area' + cmtid).css('display', 'block');
		});
	}
	else {
		$('#li_' + cmtid).attr('status', '0');
		setHtml('reply_area' + cmtid, '');
		$('#reply_area' + cmtid).attr('status', '0');
		$('#reply_area' + cmtid).css('display', 'none');
	}
}

function delreply(replyid) {
	if (window.confirm('确实要删除此评论？')) {
		var glassid = addGlass('reply_' + replyid);
		doAjax(path + '/tb/itemcmt_prvdelreply?replyid=' + replyid, function(data) {
			removeGlass(glassid);
			delObj('reply_' + replyid);
		});
	}
}

function putnewreply(data, cmtid) {
	insertObjAfter(data, 'beginpos_' + cmtid);
	hideGlass();
	getObj('reply_content_' + cmtid).value = '';
	$('#listbox_' + cmtid).css('display', 'block');
}

function replyuser(cmtid, userid, nick){
	getObj('reply_content_' + cmtid).value = '回复 ' + nick+" ";
	$body = (window.opera) ? (document.compatMode == "CSS1Compat" ? $('html') : $('body')) : $('html,body');// 这行是 Opera 的补丁, 少了它 Opera 是直接用跳的而且画面闪烁 by willin
	$body.animate({
		scrollTop: $('#cmt_reply_area_' + cmtid).offset().top
	}, 100);
	getObj('reply_content_' + cmtid).focus();
}
