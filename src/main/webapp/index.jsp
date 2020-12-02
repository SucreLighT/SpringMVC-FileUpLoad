<%--
  Created by IntelliJ IDEA.
  User: sucre
  Date: 2020/12/2
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <h3>Springmvc文件上传</h3>

        <form action="file/fileUpload" method="post" enctype="multipart/form-data">
            选择文件：<input type="file" name="uploadFile" /><br/>
            <input type="submit" value="上传" />
        </form>
    </body>
</html>
