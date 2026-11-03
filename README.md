# EduForge 企业培训管理平台

![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)
![Java](https://img.shields.io/badge/java-21-blue.svg)
![Spring Boot](https://img.shields.io/badge/spring%20boot-3.5.0-green.svg)
![Vue](https://img.shields.io/badge/vue-3.5-green.svg)

基于 **Spring Boot 3 + Vue 3 + TypeScript** 的企业级培训管理平台，实现课程管理、在线学习、考试考核等核心功能，采用前后端分离架构，支持Docker容器化部署。

## 功能模块

| 模块 | 功能说明 |
|------|----------|
| 用户认证 | JWT无状态认证、BCrypt密码加密、Token过期刷新 |
| 课程管理 | 课程CRUD、章节/课时管理、分类查询、封面上传 |
| 学习跟踪 | 报名管理、学习进度追踪、视频观看记录 |
| 考试系统 | 题库管理、在线考试、自动阅卷、成绩统计 |
| 数据统计 | 课程/学员/考试数据统计、Excel导出 |
| 权限管理 | 基于RBAC的角色权限控制 |

## 技术栈

### 后端技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.5.0 | 后端框架 |
| Spring Security | 6.2.x | 安全框架 |
| MyBatis | 3.0.3 | ORM框架 |
| JJWT | 0.12.3 | JWT认证 |
| Redis | 7.2+ | 分布式缓存 |
| SpringDoc OpenAPI | 2.3.0 | API文档 |
| Apache POI | 5.2.5 | Excel导出 |
| MySQL | 8.0+ | 数据库 |

### 前端技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 3.5.13 | 前端框架 |
| TypeScript | 5.8.3 | 类型语言 |
| Tailwind CSS | 3.4.19 | CSS框架 |
| Vue Router | 4.6.4 | 路由管理 |
| Axios | 1.18.1 | HTTP客户端 |
| Lucide Vue | 0.577.0 | 图标库 |

### 部署技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Docker | 24.0+ | 容器化 |
| Docker Compose | 3.8+ | 容器编排 |
| Nginx | 1.25+ | 反向代理 |

## 快速开始

### 环境要求

- JDK 21+
- Node.js 18+
- MySQL 8.0+（可选，Docker方式无需安装）
- Redis 7.0+（可选，Docker方式无需安装）
- Docker 24.0+（推荐）

### 方式一：Docker Compose一键部署（推荐）

使用Docker Compose可以一键启动所有服务，无需手动配置数据库和Redis。

```bash
# 构建前端
cd training-frontend
npm install
npm run build

# 一键启动所有服务（包含MySQL、Redis、后端、前端）
cd ..
docker compose up -d
```

服务启动后：
- 前端页面：http://localhost
- 后端API：http://localhost/api
- Swagger文档：http://localhost/swagger-ui.html
- 健康检查：http://localhost/actuator/health

**端口映射说明**：为避免与宿主机已有服务冲突，MySQL 映射到 `3307`、Redis 映射到 `6380`；容器内部通信仍使用标准端口，不影响页面访问。

**说明**：Docker Compose模式下，所有服务运行在同一个Docker网络中，Nginx通过内部服务名 `training-backend` 访问后端。

### 方式二：本地开发模式

适合开发调试，前后端分别启动。

#### 1. 启动数据库

**方式A：使用Docker（推荐）**

```bash
docker compose up -d mysql redis
```

**方式B：使用本地数据库**

```sql
CREATE DATABASE IF NOT EXISTS training DEFAULT CHARACTER SET utf8mb4;
```

#### 2. 启动后端

```bash
cd backend
mvn spring-boot:run
```

后端运行在 `http://localhost:8080`

#### 3. 启动前端

```bash
cd training-frontend
npm install
npm run dev
```

前端运行在 `http://localhost:3000`

**说明**：开发模式下，前端通过Vite代理将 `/api` 请求转发到 `http://localhost:8080`。

### 方式三：混合模式（Docker数据库 + 本地前后端）

结合Docker的便利性和本地开发的灵活性。

```bash
# 启动数据库（Docker）
docker compose up -d mysql redis

# 启动后端（本地）
cd backend
mvn spring-boot:run

# 启动前端（本地）
cd training-frontend
npm install
npm run dev
```

访问地址：
- 前端开发：http://localhost:3000
- 后端API：http://localhost:8080

> Docker 中 MySQL/Redis 映射到宿主机 `3307`/`6380`，本地后端需设置 `DB_URL=jdbc:mysql://localhost:3307/training?...` 与 `REDIS_PORT=6380`；若使用本机 MySQL 则无需修改。

### 方式四：Nginx反向代理（生产模式）

构建前端后，使用Nginx作为反向代理服务前端和转发API请求。

```bash
# 构建前端
cd training-frontend
npm install
npm run build

# 启动Nginx容器
docker run -d --name training-nginx -p 80:80 \
  -v "$(pwd)/training-frontend/dist:/usr/share/nginx/html" \
  -v "$(pwd)/training-frontend/nginx.conf:/etc/nginx/conf.d/default.conf" \
  nginx:1.25-alpine
```

**注意**：此模式需要确保后端服务已启动，且 `nginx.conf` 中的后端地址指向正确的后端服务。

### Nginx配置文件说明

项目包含两个Nginx配置文件：

| 文件 | 用途 | 后端地址 |
|------|------|----------|
| `nginx.conf` | 本地独立运行模式 | `host.docker.internal:8080`（指向宿主机） |
| `nginx-docker.conf` | Docker Compose模式 | `training-backend:8080`（Docker内部服务名） |

**切换配置**：
- Docker Compose模式：`docker-compose.yml` 默认使用 `nginx-docker.conf`
- 本地独立模式：手动启动Nginx时使用 `nginx.conf`

### 默认账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin | 超级管理员 |
| zhangsan | 123456 | 学员 |
| lisi | 123456 | 学员 |

## API文档

启动项目后访问 Swagger UI：

- 本地：http://localhost:8080/swagger-ui.html
- Docker：http://localhost/swagger-ui.html

## 项目结构

### 后端结构

```
backend/src/main/java/com/eduforge/training/
├── controller/          # REST API控制层
├── service/             # 业务逻辑层
│   └── impl/            # 服务实现类
├── mapper/              # MyBatis数据访问层
├── pojo/                # 数据模型
├── dto/                 # 数据传输对象
│   ├── request/         # 请求DTO
│   └── response/        # 响应DTO
├── config/              # 配置类
│   ├── SecurityConfig   # 安全配置
│   ├── RedisConfig      # Redis配置
│   └── OpenApiConfig    # API文档配置
├── filter/              # 过滤器
│   └── TokenFilter      # JWT令牌过滤器
├── exception/           # 异常处理
│   ├── GlobalExceptionHandler
│   └── BusinessException
├── aop/                 # AOP切面
│   └── OperationLogAspect
├── utils/               # 工具类
│   ├── JwtUtils         # JWT工具类
│   └── CurrentHolder    # 用户上下文
└── TrainingPlatformApplication.java
```

### 前端结构

```
training-frontend/src/
├── views/               # 页面组件
│   ├── Layout.vue       # 布局组件
│   ├── Login.vue        # 登录页面
│   ├── Dashboard.vue    # 首页工作台
│   ├── Courses.vue      # 课程管理
│   ├── Exams.vue        # 考试管理
│   ├── Students.vue     # 学员管理
│   ├── Enrollments.vue  # 报名管理
│   └── Statistics.vue   # 数据统计
├── api/                 # API接口封装
├── router/              # 路由配置
├── types/               # TypeScript类型定义
├── components/          # 公共组件
├── App.vue              # 根组件
├── main.ts              # 入口文件
└── style.css            # 全局样式
```

## 核心技术亮点

### 1. 安全架构
- **JWT无状态认证**：基于JJWT实现Token签发与验证
- **BCrypt密码加密**：安全的密码存储方案
- **ThreadLocal用户上下文**：避免参数传递，提升代码简洁性
- **环境变量密钥管理**：敏感配置从环境变量读取

### 2. 性能优化
- **Redis缓存**：课程分类、热门课程等高频数据缓存
- **HikariCP连接池**：高性能数据库连接管理
- **MyBatis分页**：支持大数据量分页查询
- **SQL日志**：详细的SQL执行日志便于性能分析

### 3. 代码规范
- **DTO分层设计**：请求/响应DTO分离，避免敏感字段泄露
- **参数校验**：基于Jakarta Validation的参数校验机制
- **统一响应格式**：泛型Result\<T\>统一API响应
- **全局异常处理**：统一的异常捕获和错误响应

### 4. 运维能力
- **Docker Compose编排**：一键启动所有服务
- **健康检查**：Spring Boot Actuator健康监控
- **日志管理**：MDC请求追踪、分级日志输出
- **API文档**：自动生成Swagger API文档

### 5. 功能完整性
- **文件上传**：支持图片和视频上传
- **Excel导出**：学员列表、考试记录导出
- **RBAC权限**：角色权限管理系统

## 开发规范

### 命名规范
- 包名：小写，使用点分隔（com.eduforge.training.controller）
- 类名：大驼峰（CourseController）
- 方法名：小驼峰（findById）
- 常量名：全大写，下划线分隔（MAX_SIZE）

### API规范
- 使用RESTful风格
- 统一响应格式：{ "code": 200, "msg": "success", "data": {} }
- 错误码：400参数错误，401未授权，403禁止访问，404资源不存在，500服务器错误

### 数据库规范
- 表名：小写，下划线分隔（course_category）
- 字段名：小写，下划线分隔（cover_image）
- 使用utf8mb4字符集

## 扩展功能建议

- [ ] 视频直播功能
- [ ] 消息通知系统
- [ ] 学习路径规划
- [ ] 证书管理系统
- [ ] 数据可视化大屏
- [ ] 微服务拆分

## License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details.