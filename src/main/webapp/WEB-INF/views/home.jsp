<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ page session="false" %>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html><html><head><title>stfu.</title> 
<script type="text/javascript">var RecaptchaOptions = {theme : 'clean'};</script>
<style>
#recaptcha_area{margin: 0 auto;}
.circle{border: 1px dotted #555;border-radius: 10px;padding: 10px;}
</style>
</head>
<body style="font-family: Consolas,monospace;">
<form method="POST" action="${pageContext.request.contextPath}/file" enctype="multipart/form-data">
<table style="min-width: 600px; width:500px; margin:0 auto;" cellpadding="15px">
    <tr><td colspan="2"><h1>stfu.</h1></td></tr>
    <tr><td><span class="circle">1.</span></td><td><input type="file" name="file" /></td></tr>
    <tr><td><span class="circle">2.</span></td><td>
            <%ReCaptcha c = ReCaptchaFactory.newReCaptcha("public-key", "private-key", false);
            out.print(c.createRecaptchaHtml(null, null));%>
            <c:if test="${!empty error }">
                <span style="color:red;">${error}</span>
            </c:if></td></tr>
    <tr><td><span class="circle">3.</span></td><td><input type="submit" value="Upload" style="font-size: 1.2em; padding: 5px 10px;"/></td></tr>
</table>
</form>
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