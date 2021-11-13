## 项目结构

```
├─cloud-api-commons
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─com
│  │  │  │      └─soa
│  │  │  │          └─springcloud
│  │  │  │              └─entities
│  │  │  └─resources
│  │  └─test
│  │      └─java
├─cloud-config-center-3344
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─com
│  │  │  │      └─soa
│  │  │  │          └─springcloud
│  │  │  └─resources
│  │  └─test
│  │      └─java
├─cloud-eureka-server7001
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─com
│  │  │  │      └─soa
│  │  │  │          └─springcloud
│  │  │  └─resources
│  │  └─test
│  │      └─java
├─cloud-eureka-server7002
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─com
│  │  │  │      └─soa
│  │  │  │          └─springcloud
│  │  │  └─resources
│  │  └─test
│  │      └─java
├─cloud-provider-user8001
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─com
│  │  │  │      └─soa
│  │  │  │          └─springcloud
│  │  │  │              ├─controller
│  │  │  │              ├─dao
│  │  │  │              └─service
│  │  │  │                  └─impl
│  │  │  └─resources
│  │  │      └─mapper
│  │  └─test
│  │      └─java
├─cloud-provider-user8002
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─com
│  │  │  │      └─soa
│  │  │  │          └─springcloud
│  │  │  │              ├─controller
│  │  │  │              ├─dao
│  │  │  │              └─service
│  │  │  │                  └─impl
│  │  │  └─resources
│  │  │      └─mapper
│  │  └─test
│  │      └─java
└─src
    ├─main
    │  ├─java
    │  └─resources
    └─test
        └─java

```

父工程SOA:

- cloud-api-commons（公用资源）

  - entities——数据实体类
  - static——静态资源

- cloud-eureka-server7001（服务治理中心）

- cloud-eureka-server7002（服务治理中心）

- cloud-provider-user8001（用户模块、微服务提供者）

  - springcloud：

    - controller——控制层

    - service——服务层

    - dao——数据持久层

  - resources：

    - mapper
      - UsersMapper.xml——数据库映射文件
    - application.xml——模块配置

- cloud-provider-user8002（用户模块、微服务提供者）





