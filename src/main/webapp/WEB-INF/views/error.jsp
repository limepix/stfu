<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ page session="false" %><!DOCTYPE html><html><head><title>stfu.</title></head>
<body style="font-family: Consolas, monospace;">

<table style="min-width: 500px; width:600px; margin:0 auto;" cellpadding="15px">
<tr><td> <h1>stfu.</h1> </td></tr>
<tr><td> <b>error: </b>${message} </td></tr>
<tr><td> <a href="${pageContext.request.contextPath}/">startpage</a> </td></tr>
</table>

<div style="position:fixed;bottom:0px;left:0px;color:#ddd;">
&copy; limepix (2013) - stfu. - short term file uploader - ${config.maxTimeOnline}ms - ${config.maxUploadSize}B - <a style="color:#ccc;" href="${pageContext.request.contextPath}/imprint">imprint</a>
</div>
<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-44263555-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>
</body>
</html>