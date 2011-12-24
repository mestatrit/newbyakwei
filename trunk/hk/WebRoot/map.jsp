<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <title>Google 地图 JavaScript API 示例: 简单地址解析</title>
    <script src="http://ditu.google.cn/maps?file=api&amp;v=2.x&amp;key=ABQIAAAAkQpuppuD1_QypIsrwIucBRS0h3FEHwLNJo6ge1-OUF8FYTgdTRSZiKKsquqAcdWwCEXJ8Nwcoh2rVg&hl=zh-CN" type="text/javascript"></script>
    <script type="text/javascript">

    var map = null;
    var geocoder = null;

    function initialize() {
      if (GBrowserIsCompatible()) {
        map = new GMap2(document.getElementById("map_canvas"));
        map.setCenter(new GLatLng(39.917, 116.397), 13);
        geocoder = new GClientGeocoder();
      }
    }

    function showAddress(address) {
      if (geocoder) {
        geocoder.getLatLng(
          address,
          function(point) {
            if (!point) {
              alert("不能解析: " + address);
            } else {
              map.setCenter(point, 13);
              var marker = new GMarker(point);
              map.addOverlay(marker);
              marker.openInfoWindowHtml(address);
            }
          }
        );
      }
    }
    </script>
  </head>

  <body onload="initialize()" onunload="GUnload()">
    <form action="#" onsubmit="showAddress(this.address.value); return false">
      <p>
        <input type="text" size="60" name="address" value="北京市海淀区" />
        <input type="submit" value="Go!" />
      </p>
      <div id="map_canvas" style="width: 500px; height: 300px"></div>
    </form>

  </body>
</html>