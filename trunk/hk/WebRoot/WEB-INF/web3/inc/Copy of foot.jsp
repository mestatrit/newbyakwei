<script src="http://ditu.google.cn/maps?file=api&amp;v=2&amp;key=ABQIAAAAkQpuppuD1_QypIsrwIucBRQQqrU_1HKnJbTL5TGDmzIVOk3OkBTvQ0iV4kr3S0aAepKbqRN4NgtH2A&hl=zh-CN" type="text/javascript">
</script>
<script type="text/javascript">
function initialize() {
	if (GBrowserIsCompatible()) {
		var map = new GMap2(document.getElementById("map_canvas"))
		var center=new GLatLng(39.9389780580737,116.435680389404);
		map.setCenter(center, 14);
		map.addControl(new GSmallMapControl());
		var marker = new GMarker(center, {draggable: false});
		map.addOverlay(marker);
	}
}
setTimeout("initialize()",2000);

         var img = new Array();
         
         img[0] = new Array(15, 'img/h60.jpg', 'img/h320.jpg');
         img[1] = new Array(16, 'img/h60.jpg', 'img/h320.jpg');
         img[2] = new Array(17, 'img/h60.jpg', 'img/h320.jpg');
         
         var noimg = false;
         if (img.length == 0) {
             noimg = true;
         }
         function changebig(idx){
             getObj("bigimg").src = img[idx][2];
         }
         
         function init_imgslider(){
             var s = "";
             for (var i = 0; i < img.length; i++) {
                 s += '<li style="overflow: hidden; float: left; width: 90px; height: 75px;"><a class="imga" ><img src="' + img[i][1] + '" onclick="changebig(' + i + ')"/></a></li>';
             }
             if (img.length > 0) {
                 setHtml("imgslider", s);
             }
         }
         
         init_imgslider();
         $(".default .slider").jCarouselLite({
             btnNext: " .next_button",
             btnPrev: " .previous_button"
         });
</script>