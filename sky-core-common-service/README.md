# 目录说明
>当前目录存放项目业务应用和业务构件，使用MAVEN3.6构建工程，本地话配置依赖见../sky-core-configuration/settings_10.47.0.170.xml<br/>

>sky-core-service-api-struct：API领域对象、实体公用工程
>sky-core-service-api-support：API交易抽象接口封装工程，包含事件接口、交易插件、交易处理器、插件链等
>sky-core-service-api-implements：API交易实现工程，包含HTTP类交易/MQ类交易分发器，实时类/异步类/辅助类/查询类交易分发器、插件、处理器等
>sky-core-service-gateway-connector：HTTP-API网关接口基础组件封装，包含网关容器接口等
>sky-core-service-gateway-connector：HTTP-API网关实现，包含NETTY容器、对象配置组件、启动器等
>