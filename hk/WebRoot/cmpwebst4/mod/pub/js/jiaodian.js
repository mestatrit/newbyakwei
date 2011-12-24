/**
 * @author akwei
 */
function setTimeHandler(divid, handler){
    $('#' + divid).data('time_handler', handler);
}

function getTimeHandler(divid){
    return $('#' + divid).data('time_handler');
}

function autoRun(divid, optime){
    var t = setInterval(function(){
        showimgnext(divid);
    }, optime);
    setTimeHandler(divid, t);
}

function stopAutoRun(divid){
    window.clearInterval(getTimeHandler(divid));
}

function showimgnext(divid){
    var link_num_list = $('#' + divid + " ul.num li");
    var idx = 0;
    link_num_list.filter('.cur').each(function(i){
        idx = $(this).attr('picid');
    });
    var idx = parseInt(idx);
    var pic_size = parseInt($('#' + divid).data('pic_size'));
    if (idx >= pic_size - 1) {
        idx = 0;
    }
    else {
        idx++;
    }
    showimg(divid, idx);
}

function showimg(divid, idx){
    var currentIdx = idx;
    if (currentIdx == -1) {
        currentIdx = 0;
    }
    else {
        currentIdx = idx;
    }
    var link_num_list = $('#' + divid + " ul.num li");
    link_num_list.each(function(i){
        $(this).removeClass("cur");
        if (i == currentIdx) {
            $(this).addClass('cur');
        }
        else {
            $(this).removeClass("cur");
        }
    });
    var li_list = $('#' + divid + " ul.pic li");
	if(li_list.filter('.cur').length==0){
		li_list.eq(0).addClass('cur');
	}
    li_list.filter('.cur').fadeOut(100, function(){
        li_list.eq(currentIdx).fadeIn(200);
    });
    li_list.removeClass("cur").eq(currentIdx).addClass("cur");
}

function init(divid){
    var o = $('#' + divid);
    var li_list = $('#' + divid + " ul.pic li");
    var pic_size = 0;
    li_list.each(function(i){
        $(this).attr('id', divid + 'imgnum_' + i);
        $(this).css('display', 'none');
        pic_size++;
    });
    $('#' + divid).data('pic_size', pic_size);
    var link_num_list = $('#' + divid + " ul.num li");
    link_num_list.each(function(i){
        $(this).attr('id', divid + 'num_' + i);
        $(this).attr('picid', i);
    });
    
    $('ul.num li').hover(function(){
        showimg(divid, $(this).attr('picid'));
    }, function(){
    
    });
    showimg(divid, 0);
}