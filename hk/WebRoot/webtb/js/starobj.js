function StarObj(_container_id, _rate_idx, _func_onselectstar){
	var star_gray_g_src = path + '/webtb/img/star_gray_g.gif';
	var star_blue_g_src = path + '/webtb/img/star_blue_g.gif';
//	var star_gray_g_src = 'js/starimg/star_gray_g.gif';
//	var star_blue_g_src = 'js/starimg/star_blue_g.gif';
	this.star_timer = null;
	this.rate_idx = _rate_idx;
	this.container_id = _container_id;
	this.star_tip = ['很差', '差', '一般', '好', '很好'];
	var self_obj = this;
	this.show = function(){
		var s = '<ul class="starlist">';
		for (var i = 0; i < 5; i++) {
			s += '<li><a idx="' + i + '" href="javascript:void(0)"><img id="star_' + i + '" src="' + star_gray_g_src + '" /></a></li>';
		}
		s += '</ul><div class="starttip" id="star_tip' + _container_id + '"></div><div class="startclr"></div>';
		setHtml(this.container_id, s);
		$('ul.starlist li a').bind('mouseenter', function(){
			self_obj.clearStarTimer();
			var star_idx = $(this).attr('idx');
			for (var i = 0; i < 5; i++) {
				if (i <= star_idx) {
					getObj('star_' + i).src = star_blue_g_src;
				}
				else {
					getObj('star_' + i).src = star_gray_g_src;
				}
			}
			setHtml('star_tip' + self_obj.container_id, self_obj.star_tip[star_idx]);
		}).bind('mouseleave', function(){
			self_obj.clearStar();
		}).bind('click', function(){
			self_obj.rate_idx = parseInt($(this).attr('idx'));
			_func_onselectstar(self_obj.rate_idx);
		});
		this.setStar();
	}
	this.setStar = function(){
		if (this.rate_idx != -1) {
			for (var i = 0; i < 5; i++) {
				if (i <= this.rate_idx) {
					getObj('star_' + i).src = star_blue_g_src;
				}
				else {
					getObj('star_' + i).src = star_gray_g_src;
				}
			}
			setHtml('star_tip' + this.container_id, this.star_tip[this.rate_idx]);
		}
		else {
			for (var i = 0; i < 5; i++) {
				getObj('star_' + i).src = star_gray_g_src;
			}
			setHtml('star_tip' + this.container_id, '');
		}
	}
	this.clearStar = function(){
		this.star_timer = setTimeout(function(){
			self_obj.clearStarTimer();
			self_obj.setStar();
		}, 300);
	}
	this.clearStarTimer = function(){
		if (this.star_timer) {
			clearTimeout(this.star_timer);
			this.star_timer = null;
		}
	}
}
