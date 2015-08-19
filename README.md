# limetal 液金属

Implemented by means of online back-end automation development of Web projects, and is totally separated from the ends of the call, as well as real-time publishing to the cloud server or container to achieve low threshold and high efficiency of application development.

通过在线方式实现web项目的后端自动化开发，并形成完全分割的前后端调用，同时可实时发布到云服务器或容器中，实现应用开发的低门槛和高效率。

## Prerequisites 先决条件

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running 运行

运行前，有部分前段的js或css未递交，其中包含CodeMirror\WebIX，动态生成前台页面包含Ractive\Semantic-UI\BackboneJS。

当然，作为完全分离前后端，你完全可以自行实现前端，通过ajax方式获取后端所有数据。

前端的实现，最好是基于模块方式。

To start a web server for the application, run:

    lein ring server

## License 授权

Copyright © 2015 apache 2.0
