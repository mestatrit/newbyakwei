var star_timer = null;
var rate_idx = -1;
$(document).ready(function(){
	$('ul.starlist li a').bind('mouseenter', function(){
		clearStarTimer();
		var star_idx = $(this).attr('idx');
		for (var i = 0; i < 5; i++) {
			if (i <= star_idx) {
				getObj('star_' + i).src = path + "/webtb/img/star_blue_g.gif";
			}
			else {
				getObj('star_' + i).src = path + "/webtb/img/star_gray_g.gif";
			}
		}
	}).bind('mouseleave', function(){
		clearStar();
	}).bind('click', function(){
		rate_idx = $(this).attr('idx');
		createscore(parseInt(rate_idx) + 1);
	});
});
function createscore(score){
}

function setStar(){
	if (rate_idx != -1) {
		for (var i = 0; i < 5; i++) {
			if (i <= rate_idx) {
				getObj('star_' + i).src = path + "/webtb/img/star_blue_g.gif";
			}
			else {
				getObj('star_' + i).src = path + "/webtb/img/star_gray_g.gif";
			}
		}
	}
	else {
		for (var i = 0; i < 5; i++) {
			getObj('star_' + i).src = path + "/webtb/img/star_gray_g.gif";
		}
	}
}

function clearStar(){
	star_timer = setTimeout(function(){
		clearStarTimer();
		setStar();
	}, 300);
}

function clearStarTimer(){
	if (star_timer) {
		clearTimeout(star_timer);
		star_timer = null;
	}
}
