## 文件上传

1. 传统方式的文件上传，指的是上传的文件和访问的应用存在于同一台服务器上。 并且上传完成之后，浏览器可能跳转。

2. 导入文件上传相关的jar包

   ```xml
   <!-- 文件传输的依赖包 -->
           <dependency>
               <groupId>commons-fileupload</groupId>
               <artifactId>commons-fileupload</artifactId>
               <version>1.3.1</version>
           </dependency>
           <dependency>
               <groupId>commons-io</groupId>
               <artifactId>commons-io</artifactId>
               <version>2.4</version>
           </dependency>
   ```

3. 前端界面

   ```jsp
   <h3>文件上传</h3>
   <form action="file/fileUpload" method="post" enctype="multipart/form-data">
       选择文件：<input type="file" name="uploadFile"/><br/>
       <input type="submit" value="上传"/>
   </form>
   ```

   - **form表单的enctype取值必须是：multipart/form-data**，enctype:是表单请求正文的类型；
   - method属性取值必须是Post；
   - 提供一个文件选择域input。

4. 控制器代码

   ```java
   @Controller
   @RequestMapping("/file")
   public class FileUpLoadController {
       @RequestMapping("/fileUpload")
       public String fileUpLoad(HttpServletRequest request, MultipartFile uploadFile) throws IOException {
           System.out.println("SpringMVC方式进行文件上传...");
           // 先获取到要上传的文件目录
           String path = request.getSession().getServletContext().getRealPath("/uploads");
           // 创建File对象，一会向该路径下上传文件
           File file = new File(path);
           // 判断路径是否存在，如果不存在，创建该路径
           if (!file.exists()) {
               file.mkdirs();
           }
           // 获取到上传文件的名称
           String filename = uploadFile.getOriginalFilename();
           String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
           // 把文件的名称唯一化
           filename = uuid + "_" + filename;
           // 上传文件
           uploadFile.transferTo(new File(file, filename));
           return "success";
       }
   }
   ```

   + :star:SpringMVC框架提供MultipartFile类用于实现文件上传的组件。**控制器方法的参数中MultipartFile的参数名必须与前端文件的name属性相同。**
   + 为每个文件设置一个uuid，保证多次上传相同的文件时不会因为同名而覆盖。

5. 配置文件解析器

   ```xml
    <!-- 配置文件解析器对象，要求id名称必须是multipartResolver -->
       <bean id="multipartResolver"
             class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
           <property name="maxUploadSize" value="10485760"/>
       </bean>
   ```

6. ⭐**注意事项**

   + **前端界面中的相对路径**：

     + 不加斜杠：参考当前访问的路径。

     + 加斜杠：参考根路径。

       所以在本实例中前端路径应该不加/，index.jsp的路径为`http://localhost:8080/SpringMVC_FileUpLoad_war/`，不加/点击跳转后的路径为`http://localhost:8080/SpringMVC_FileUpLoad_war/file/fileUpload`，如果加上/就变成`http://localhost:8080/file/fileUpload`导致404错误。

   + 后台服务器中的代码，参考的根目录为**webapp**，如在springmvc.xml中设置的静态资源不过滤`<mvc:resources location="/css/" mapping="/css/**"/>`，前面加上/表示相对webapp下。

   + 控制器中获取的path为文件上传到服务器的目录，uploads是目标文件夹，取决于服务器布置的位置，使用tomcat布置时，该path的值为`D:\Java\tomcat8\webapps\SpringMVC_FileUpLoad_war\uploads`。

