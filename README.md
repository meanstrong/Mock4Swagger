# Mock4Swagger - A mock server for Swagger API specification.

## About
Mock4Swagger是一个基于swagger API json文件的mock server，返回数据中自动填充faker data。

## Requirements
- Java >= 1.8

## Usage
1. clone源代码至本地使用maven编译，或者下载已发布的jar包：[Mock4Swagger.jar](https://github.com/meanstrong/Mock4Swagger/releases)
2. 执行如下命令启动mock server：
    ```shell
    java -jar Mock4Swagger.jar --swagger-file=swagger.json [--port=8080]
    ```
    使用--help查看更多使用帮助。This is an official example swagger-file: [swagger.json](http://petstore.swagger.io/v2/swagger.json)
3. 浏览器访问swagger文件中定义的API接口，例如：``http://127.0.0.1:8080/v2/pet/findByStatus``，查看响应：
    ```json
    [{"photoUrls":["FdfcG","PAChlnoe","VBuYBbVaf"],"name":"TRaRHgPv","id":73,"category":{"name":"vedgWCe","id":4},"tags":[{"name":"pnPWRpTO","id":8},{"name":"ZNKRVSP","id":79},{"name":"SbXjB","id":32}],"status":"pending"}]
    ```

## Author
- <a href="mailto:pmq2008@gmail.com">Rocky Peng</a>
