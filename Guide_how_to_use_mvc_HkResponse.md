
```

HkResponse中的方法使用:
继承了HttpServletResponse

// 向页面输出xml数据，xml头信息已经输出<?xml version=\"1.0\" encoding=\"UTF-8\"?>
resp.sendXML("<resp>hello</resp>");
// 向页面输出数据html页面显示
resp.sendHtml("data");

```