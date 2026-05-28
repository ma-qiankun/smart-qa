# SmartQA - 企业级智能知识库问答系统

> 一个从零开始、持续演进的 **全栈 AI 知识库问答平台**，目标覆盖从单机 CRUD 到 RAG 智能问答、从单体到微服务的完整学习与实战过程。

[Spring Boot](https://spring.io/projects/spring-boot)
[JDK](https://adoptium.net/)
[PostgreSQL](https://www.postgresql.org/)
[License](LICENSE)
[GitHub stars](https://github.com/ma-qiankun/smart-qa)

---

## 📖 项目简介

企业内部沉淀着大量文档（政策、技术方案、操作手册），员工查找知识效率低下。**SmartQA** 旨在提供一套完整解决方案：员工上传文档，系统通过 **AI 语义检索与生成（RAG）** 给出精确、可溯源的答案，让「企业知识随手可得」。

**当前阶段（v0.1.0）** 已完成核心领域模型与基础 REST API，包括用户注册登录、文档上传落盘、问答社区（问题 / 回答）等能力。向量检索、文档解析、大模型对话等 AI 能力将在后续版本逐步接入。

这也是个人 **Java 后端从「会用」到「精通」** 的实战记录仓库，每个 Tag 对应一个可运行的里程碑。

---

## 🏗️ 项目演进路线


| 版本                 | 里程碑                                  | 核心目标                     | 状态     |
| ------------------ | ------------------------------------ | ------------------------ | ------ |
| **V0.1 基础接口版**     | 领域模型、用户 / 文档 / 问答 CRUD、文件上传          | 打通主流程，夯实 Spring Data JPA | ✅ 进行中  |
| **V1.0 单机基石版**     | 文档解析（PDF/Word）、JWT 鉴权、统一异常处理         | 夯实工程化与可维护性               | 📋 规划中 |
| **V2.0 分布式进阶版**    | 微服务拆分、消息队列、缓存、分布式锁                   | 掌握分布式系统设计                | 📋 规划中 |
| **V3.0 全栈 AI 赋能版** | LangChain4j / Spring AI、RAG、pgvector | 智能问答核心能力                 | 📋 规划中 |
| **V4.0 云原生可观测版**   | Docker/K8s、Prometheus、Grafana、链路追踪   | 生产级交付与可观测性               | 📋 规划中 |


> **当前最新 Tag：** `[v0.1.0-核心域模型与基础接口完成](https://github.com/ma-qiankun/smart-qa/releases)`

---

## ✨ 当前已实现功能（v0.1.0）


| 模块         | 能力                                          |
| ---------- | ------------------------------------------- |
| **用户**     | 注册、登录（BCrypt 密码加密；登录返回占位 Token，尚未接入 JWT 校验） |
| **文档**     | 多格式文件上传（最大 50MB），本地磁盘存储，元数据入库               |
| **问答**     | 提问、列表查询、按 ID 查询；按问题查看 / 提交回答                |
| **API 文档** | SpringDoc OpenAPI（Swagger UI）               |


**尚未实现（后续版本）：** 文档正文解析与向量化、RAG 检索、大模型对话、前端界面、Redis、消息队列、完整 Spring Security 等。

---

## 🧰 技术栈

### 当前已使用


| 类别       | 技术                                    |
| -------- | ------------------------------------- |
| 语言 / 运行时 | Java 21                               |
| 核心框架     | Spring Boot 3.5.14                    |
| 持久化      | Spring Data JPA、Hibernate、PostgreSQL  |
| Web      | Spring MVC、Bean Validation            |
| 安全（局部）   | `spring-security-crypto`（BCrypt 密码编码） |
| 工具       | Lombok、Spring Boot DevTools           |
| API 文档   | springdoc-openapi 2.2.0               |


### 规划引入（路线图）

- **AI**：LangChain4j / Spring AI、OpenAI 或国产大模型 API、pgvector
- **缓存 / 消息**：Redis、RocketMQ / Kafka
- **安全**：Spring Security + JWT
- **文档解析**：Apache PDFBox、Apache POI
- **前端**：Next.js、Ant Design
- **运维**：Docker Compose、Kubernetes、Prometheus、Grafana

---

## 📁 仓库结构

```
smart-qa/
├── src/                          # 后端代码
│   └── main/java/com/smartqa/
│       ├── entity/               # 实体
│       ├── repository/           # 数据访问
│       ├── service/              # 业务逻辑
│       ├── controller/           # API 接口
│       └── config/               # 配置
├── frontend/                     # 前端代码 (Next.js)
├── notes/                        # 学习笔记与博客
├── docs/                         # 架构图、设计文档
├── docker-compose.yml            # 依赖服务编排
└── README.md
```

### 数据模型


| 表名           | 实体         | 说明                               |
| ------------ | ---------- | -------------------------------- |
| `t_user`     | `User`     | 用户（username、password、email、role） |
| `t_document` | `Document` | 文档元数据（title、filePath、status 等）   |
| `t_question` | `Question` | 问题                               |
| `t_answer`   | `Answer`   | 回答（关联问题与用户）                      |


表结构由 JPA `ddl-auto: update` 自动维护，首次启动会自动建表。

---

## 🚀 快速启动

### 环境要求


| 依赖         | 版本建议                                    |
| ---------- | --------------------------------------- |
| JDK        | **21**（与 `pom.xml` 中 `java.version` 一致） |
| Maven      | 3.9+（项目自带 `mvnw`，可不单独安装）                |
| PostgreSQL | 15+                                     |


> 当前版本**不需要** Docker、Redis、Node.js 即可运行后端。

### 1. 准备数据库

```sql
CREATE DATABASE smartqa;
```

默认连接配置见 `smartqa/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/smartqa
    username: postgres
    password: 123456   # 请按本地环境修改
```

### 2. 启动后端

```bash
cd smartqa
# Windows
.\mvnw clean compile spring-boot:run

# Linux / macOS
./mvnw clean compile spring-boot:run
```

启动成功后默认监听：**[http://localhost:8080](http://localhost:8080)**

### 3. 访问 API 文档

- Swagger UI：`http://localhost:8080/swagger-ui.html`
- OpenAPI JSON：`http://localhost:8080/v3/api-docs`

### 4. 配置说明


| 配置项                                      | 默认值       | 说明                           |
| ---------------------------------------- | --------- | ---------------------------- |
| `smartqa.upload-dir`                     | `uploads` | 上传文件目录（相对路径会解析为项目运行目录下的绝对路径） |
| `spring.servlet.multipart.max-file-size` | `50MB`    | 单文件上传大小上限                    |


---

## 📡 API 一览

基础路径：`http://localhost:8080`

### 用户 `/api/users`


| 方法     | 路径                    | 说明   |
| ------ | --------------------- | ---- |
| `POST` | `/api/users/register` | 用户注册 |
| `POST` | `/api/users/login`    | 用户登录 |


**注册示例：**

```json
POST /api/users/register
Content-Type: application/json

{
  "username": "zhangsan",
  "password": "123456",
  "email": "zhangsan@example.com"
}
```

**登录示例：**

```json
POST /api/users/login
Content-Type: application/json

{
  "username": "zhangsan",
  "password": "123456"
}
```

响应示例：`{ "token": "...", "username": "zhangsan" }`（Token 为占位实现，后续版本将替换为 JWT）

### 文档 `/api/documents`


| 方法     | 路径                      | 说明                          |
| ------ | ----------------------- | --------------------------- |
| `POST` | `/api/documents/upload` | 上传文档（`multipart/form-data`） |


**表单字段：** `file`（文件）、`title`（标题）、`userId`（上传用户 ID）

### 问答 `/api/questions`


| 方法     | 路径                                    | 说明           |
| ------ | ------------------------------------- | ------------ |
| `POST` | `/api/questions`                      | 创建问题         |
| `GET`  | `/api/questions`                      | 问题列表（含提问人信息） |
| `GET`  | `/api/questions/{id}`                 | 按 ID 查询问题    |
| `POST` | `/api/questions/{questionId}/answers` | 创建回答         |
| `GET`  | `/api/questions/{questionId}/answers` | 某问题下的回答列表    |


**创建问题示例：**

```json
POST /api/questions
Content-Type: application/json

{
  "title": "如何配置上传目录？",
  "content": "想把文件存到 D 盘",
  "userId": 1
}
```

---

## 🧪 本地验证建议

1. 调用 `POST /api/users/register` 创建用户，记下返回的 `id`
2. 使用 Postman 以 `form-data` 调用 `POST /api/documents/upload` 上传 PDF
3. 调用 `POST /api/questions` 创建问题，再 `GET /api/questions` 验证列表
4. 对某一 `questionId` 调用回答相关接口

也可直接通过 Swagger UI 在线调试上述接口。

---

## 🛠️ 开发与构建

```bash
cd smartqa

# 编译（跳过测试）
.\mvnw compile -DskipTests

# 运行测试
.\mvnw test

# 打包
.\mvnw package -DskipTests
```

---

## 📝 版本与发布


| Tag                   | 说明                 |
| --------------------- | ------------------ |
| `v0.1.0-核心域模型与基础接口完成` | 用户 / 文档 / 问答基础 API |


```bash
git tag -l
git checkout v0.1.0-核心域模型与基础接口完成
```

---

## ⚠️ 已知限制（v0.1.0）

- 登录 Token 未持久化、未做请求鉴权拦截
- 文档上传后 `status` 为 `PROCESSING`，尚未实现解析与向量化
- 接口未统一全局异常处理，业务错误多以 `IllegalArgumentException` 抛出
- 无前端项目，需通过 Postman / Swagger 调用
- 生产环境请修改数据库密码，勿将敏感配置提交至仓库

---

## 📄 License

[MIT](LICENSE)

---

## 🔗 相关链接

- 仓库地址：[https://github.com/ma-qiankun/smart-qa](https://github.com/ma-qiankun/smart-qa)
- Spring Boot 文档：[https://docs.spring.io/spring-boot/3.5.14/reference/](https://docs.spring.io/spring-boot/3.5.14/reference/)

