
# 企业培训课程学习管理系统 - 开发计划

## 一、新领域主题方向

### 1.1 领域选择
**企业培训-课程学习管理系统**（Enterprise Training & Learning Management System）

### 1.2 领域独特性分析

| 维度 | 说明 |
|------|------|
| **业务独特性** | 专注于企业内部培训管理，涵盖课程体系、学习路径、考核认证等核心场景，不同于通用的员工管理或图书管理系统 |
| **价值差异化** | 提供学习进度追踪、知识图谱、能力评估等特色功能，帮助企业构建人才发展体系 |
| **技术挑战性** | 需要处理学习进度状态机、课程依赖关系、在线考试等复杂业务逻辑 |

### 1.3 核心业务场景

```
┌─────────────────────────────────────────────────────────────┐
│                    培训管理系统                              │
├─────────────┬─────────────┬─────────────┬───────────────────┤
│ 课程管理    │ 学习管理    │ 考核管理    │ 数据统计          │
│ ├─ 课程CRUD │ ├─ 选课管理 │ ├─ 在线考试 │ ├─ 学习报表       │
│ ├─ 章节管理 │ ├─ 进度追踪 │ ├─ 成绩管理 │ ├─ 能力分析       │
│ ├─ 讲师管理 │ ├─ 学习记录 │ ├─ 证书管理 │ └─ 培训效果评估   │
│ └─ 分类管理 │ └─ 学习提醒 │ └─ 题库管理 │                   │
└─────────────┴─────────────┴─────────────┴───────────────────┘
```

---

## 二、代码复用方案

### 2.1 核心架构复用

| 层级 | 组件 | 复用策略 | 复用率 |
|------|------|----------|--------|
| **启动层** | TrainingPlatformApplication | 完全复用 | 100% |
| **配置层** | application.yml, WebConfig | 完全复用（修改数据库名） | 95% |
| **依赖管理** | pom.xml | 完全复用 | 100% |
| **认证层** | JwtUtils, TokenFilter, LoginController | 完全复用 | 100% |
| **响应封装** | Result, PageResult | 完全复用 | 100% |
| **异常处理** | GlobalExceptionHandler, BusinessException | 完全复用 | 100% |
| **工具类** | CurrentHolder | 完全复用 | 100% |
| **AOP日志** | Log 注解, OperationLogAspect | 完全复用 | 100% |
| **文件上传** | UploadController (Python) | 完全复用 | 100% |
| **Nginx配置** | nginx.conf | 完全复用 | 100% |

### 2.2 数据层复用

| 组件 | 复用内容 | 修改内容 |
|------|----------|----------|
| MyBatis配置 | mapper-locations, type-aliases-package | 新增mapper包扫描路径 |
| PageHelper分页 | 分页查询逻辑 | 完全复用 |
| Mapper接口模板 | CRUD方法定义模式 | 参考现有模式 |
| XML映射模板 | resultMap, 动态SQL | 参考现有模式 |

### 2.3 业务层复用

| 组件 | 复用内容 |
|------|----------|
| Service接口模式 | 定义业务方法规范 |
| ServiceImpl模板 | 分页查询、事务管理 |
| Controller模式 | RESTful API设计 |
| 日志记录 | SLF4J日志规范 |

---

## 三、复用组件适用性评估

### 3.1 认证系统

**评估结果：完全适用**

- JwtUtils：通用的JWT生成和解析工具，与业务无关
- TokenFilter：从"token"header提取并验证token，逻辑通用
- LoginController：基于username/password认证，可直接复用

### 3.2 响应与异常处理

**评估结果：完全适用**

- Result：通用的响应封装，code/msg/data结构适用于任何业务
- PageResult：通用的分页结果封装，泛型设计支持任意实体
- GlobalExceptionHandler：统一的异常捕获和处理，与业务无关

### 3.3 工具类

**评估结果：完全适用**

- CurrentHolder：ThreadLocal存储当前用户ID，通用设计

### 3.4 AOP日志

**评估结果：完全适用**

- @Log注解 + OperationLogAspect：记录操作日志，与业务无关

### 3.5 文件上传

**评估结果：完全适用**

- Python Flask服务处理文件上传，可用于课程资料上传

---

## 四、差异化功能开发规范

### 4.1 数据库设计

#### 4.1.1 核心数据表

```sql
-- 用户表（复用原emp表结构，重命名）
CREATE TABLE `user` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(50) UNIQUE NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `email` VARCHAR(100),
    `phone` VARCHAR(20),
    `role` INT DEFAULT 0, -- 0:普通用户, 1:讲师, 2:管理员
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 课程分类表
CREATE TABLE `course_category` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `parent_id` INT DEFAULT 0,
    `sort_order` INT DEFAULT 0,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 课程表
CREATE TABLE `course` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `title` VARCHAR(200) NOT NULL,
    `description` TEXT,
    `cover_image` VARCHAR(255),
    `category_id` INT,
    `teacher_id` INT,
    `duration` INT, -- 课时（分钟）
    `level` INT DEFAULT 1, -- 1:初级, 2:中级, 3:高级
    `status` INT DEFAULT 0, -- 0:草稿, 1:发布, 2:下架
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`category_id`) REFERENCES `course_category`(`id`),
    FOREIGN KEY (`teacher_id`) REFERENCES `user`(`id`)
);

-- 章节表
CREATE TABLE `chapter` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `course_id` INT NOT NULL,
    `title` VARCHAR(200) NOT NULL,
    `sort_order` INT DEFAULT 0,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`course_id`) REFERENCES `course`(`id`)
);

-- 课时表
CREATE TABLE `lesson` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `chapter_id` INT NOT NULL,
    `title` VARCHAR(200) NOT NULL,
    `type` INT DEFAULT 1, -- 1:视频, 2:文档, 3:测验
    `content_url` VARCHAR(500),
    `duration` INT, -- 时长（秒）
    `sort_order` INT DEFAULT 0,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`chapter_id`) REFERENCES `chapter`(`id`)
);

-- 选课记录表
CREATE TABLE `course_enroll` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `course_id` INT NOT NULL,
    `status` INT DEFAULT 0, -- 0:学习中, 1:已完成, 2:已放弃
    `progress` INT DEFAULT 0, -- 学习进度(%)
    `score` DECIMAL(5,2), -- 最终成绩
    `certificate_no` VARCHAR(50), -- 证书编号
    `enroll_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `complete_time` DATETIME,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY (`course_id`) REFERENCES `course`(`id`),
    UNIQUE KEY (`user_id`, `course_id`)
);

-- 学习进度表
CREATE TABLE `learning_progress` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `lesson_id` INT NOT NULL,
    `progress` INT DEFAULT 0, -- 当前学习进度(%)
    `is_completed` TINYINT DEFAULT 0,
    `last_learn_time` DATETIME,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY (`lesson_id`) REFERENCES `lesson`(`id`),
    UNIQUE KEY (`user_id`, `lesson_id`)
);

-- 题库表
CREATE TABLE `question` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `course_id` INT,
    `type` INT DEFAULT 1, -- 1:单选, 2:多选, 3:判断, 4:填空, 5:问答
    `content` TEXT NOT NULL,
    `options` TEXT, -- JSON格式选项
    `answer` TEXT NOT NULL,
    `score` INT DEFAULT 10,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`course_id`) REFERENCES `course`(`id`)
);

-- 考试表
CREATE TABLE `exam` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `title` VARCHAR(200) NOT NULL,
    `course_id` INT,
    `duration` INT DEFAULT 60, -- 考试时长（分钟）
    `pass_score` INT DEFAULT 60, -- 及格分数
    `status` INT DEFAULT 0, -- 0:草稿, 1:发布, 2:结束
    `start_time` DATETIME,
    `end_time` DATETIME,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`course_id`) REFERENCES `course`(`id`)
);

-- 考试题目关联表
CREATE TABLE `exam_question` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `exam_id` INT NOT NULL,
    `question_id` INT NOT NULL,
    `sort_order` INT DEFAULT 0,
    FOREIGN KEY (`exam_id`) REFERENCES `exam`(`id`),
    FOREIGN KEY (`question_id`) REFERENCES `question`(`id`)
);

-- 答题记录表
CREATE TABLE `exam_record` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `exam_id` INT NOT NULL,
    `answers` TEXT, -- JSON格式答题记录
    `score` DECIMAL(5,2),
    `status` INT DEFAULT 0, -- 0:进行中, 1:已提交
    `submit_time` DATETIME,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY (`exam_id`) REFERENCES `exam`(`id`)
);
```

### 4.2 POJO 类设计规范

#### 4.2.1 通用规范

- 使用 Lombok 注解（@Data, @Builder, @NoArgsConstructor, @AllArgsConstructor）
- 字段命名使用驼峰式
- 时间字段使用 LocalDateTime / LocalDate
- 逻辑删除字段使用 status 字段表示

#### 4.2.2 新增实体类

| 类名 | 对应表 | 说明 |
|------|--------|------|
| User | user | 用户实体（替代原Emp） |
| CourseCategory | course_category | 课程分类 |
| Course | course | 课程实体 |
| Chapter | chapter | 章节实体 |
| Lesson | lesson | 课时实体 |
| CourseEnroll | course_enroll | 选课记录 |
| LearningProgress | learning_progress | 学习进度 |
| Question | question | 题目实体 |
| Exam | exam | 考试实体 |
| ExamQuestion | exam_question | 考试题目关联 |
| ExamRecord | exam_record | 答题记录 |

### 4.3 Controller 开发规范

#### 4.3.1 通用规范

- 使用 @RestController + @RequestMapping
- 类名格式：`{业务模块}Controller`
- 方法命名：`list()`, `page()`, `getInfo()`, `save()`, `update()`, `delete()`
- 返回值统一使用 `Result` 封装
- 日志使用 SLF4J，格式：`log.info("操作描述: {}", 参数)`

#### 4.3.2 新增 Controller

| Controller | 路径 | 说明 |
|------------|------|------|
| UserController | /users | 用户管理（替代原EmpController） |
| CourseCategoryController | /categories | 课程分类管理 |
| CourseController | /courses | 课程管理 |
| ChapterController | /chapters | 章节管理 |
| LessonController | /lessons | 课时管理 |
| EnrollController | /enrolls | 选课管理 |
| ProgressController | /progress | 学习进度管理 |
| QuestionController | /questions | 题库管理 |
| ExamController | /exams | 考试管理 |
| ReportController | /reports | 统计报表 |

### 4.4 Service 开发规范

#### 4.4.1 通用规范

- 接口命名：`{业务模块}Service`
- 实现类命名：`{业务模块}ServiceImpl`
- 使用 `@Service` 注解
- 事务管理使用 `@Transactional(rollbackFor = Exception.class)`
- 依赖注入使用 `@Autowired`

#### 4.4.2 核心业务方法

| Service | 方法 | 说明 |
|---------|------|------|
| CourseService | page() | 课程分页查询 |
| CourseService | save() | 新增课程（含章节） |
| CourseService | update() | 更新课程 |
| CourseService | delete() | 删除课程 |
| EnrollService | enroll() | 用户选课 |
| EnrollService | getProgress() | 获取学习进度 |
| EnrollService | complete() | 完成课程 |
| ProgressService | update() | 更新课时进度 |
| ExamService | createExam() | 创建考试 |
| ExamService | submitExam() | 提交考试 |
| ExamService | grade() | 自动阅卷 |

### 4.5 Mapper 开发规范

#### 4.5.1 通用规范

- 使用 `@Mapper` 注解
- XML 文件与接口同目录
- SQL 语句使用 MyBatis 动态SQL
- 字段映射使用驼峰转下划线

#### 4.5.2 特殊 SQL 场景

| 场景 | 实现方式 |
|------|----------|
| 课程+章节+课时关联查询 | 嵌套查询或多表联查 |
| 学习进度统计 | GROUP BY + COUNT/SUM |
| 考试自动阅卷 | SQL 统计或 Java 逻辑 |

---

## 五、开发步骤

### 5.1 第一阶段：基础架构搭建（100%复用）

| 步骤 | 内容 | 状态 |
|------|------|------|
| 1 | 创建新数据库 `training` | 待执行 |
| 2 | 修改 application.yml 数据库配置 | 待执行 |
| 3 | 保留认证系统（JwtUtils, TokenFilter, LoginController） | 待执行 |
| 4 | 保留响应封装（Result, PageResult） | 待执行 |
| 5 | 保留全局异常处理（GlobalExceptionHandler） | 待执行 |
| 6 | 保留工具类（CurrentHolder） | 待执行 |
| 7 | 保留 AOP 日志（Log, OperationLogAspect） | 待执行 |

### 5.2 第二阶段：核心业务模块开发

| 步骤 | 内容 | 状态 |
|------|------|------|
| 1 | 创建 User 相关类（POJO, Mapper, Service, Controller） | 待执行 |
| 2 | 创建 CourseCategory 相关类 | 待执行 |
| 3 | 创建 Course 相关类 | 待执行 |
| 4 | 创建 Chapter 相关类 | 待执行 |
| 5 | 创建 Lesson 相关类 | 待执行 |

### 5.3 第三阶段：学习管理模块开发

| 步骤 | 内容 | 状态 |
|------|------|------|
| 1 | 创建 CourseEnroll 相关类 | 待执行 |
| 2 | 创建 LearningProgress 相关类 | 待执行 |
| 3 | 实现选课逻辑 | 待执行 |
| 4 | 实现学习进度追踪 | 待执行 |
| 5 | 实现课程完成逻辑 | 待执行 |

### 5.4 第四阶段：考核认证模块开发

| 步骤 | 内容 | 状态 |
|------|------|------|
| 1 | 创建 Question 相关类 | 待执行 |
| 2 | 创建 Exam 相关类 | 待执行 |
| 3 | 创建 ExamQuestion 相关类 | 待执行 |
| 4 | 创建 ExamRecord 相关类 | 待执行 |
| 5 | 实现考试创建逻辑 | 待执行 |
| 6 | 实现答题提交逻辑 | 待执行 |
| 7 | 实现自动阅卷逻辑 | 待执行 |

### 5.5 第五阶段：统计报表模块开发

| 步骤 | 内容 | 状态 |
|------|------|------|
| 1 | 创建 ReportController | 待执行 |
| 2 | 实现课程学习统计 | 待执行 |
| 3 | 实现用户学习统计 | 待执行 |
| 4 | 实现考试成绩统计 | 待执行 |

---

## 六、风险与应对

### 6.1 技术风险

| 风险 | 概率 | 影响 | 应对措施 |
|------|------|------|----------|
| 课程与章节的级联操作复杂 | 高 | 中 | 使用事务管理，确保数据一致性 |
| 学习进度计算性能问题 | 中 | 中 | 定期异步更新进度，避免实时计算 |
| 考试自动阅卷逻辑复杂 | 中 | 高 | 先实现客观题自动阅卷，主观题人工批改 |

### 6.2 数据迁移风险

| 风险 | 概率 | 影响 | 应对措施 |
|------|------|------|----------|
| 现有员工数据迁移 | 低 | 中 | 创建数据迁移脚本，保留原emp表 |
| 数据库表名冲突 | 低 | 高 | 使用独立数据库 `training` |

### 6.3 依赖风险

| 风险 | 概率 | 影响 | 应对措施 |
|------|------|------|----------|
| MyBatis 版本兼容性 | 低 | 高 | 保持与现有项目一致的版本 |
| PageHelper 分页异常 | 低 | 中 | 测试不同场景下的分页查询 |

---

## 七、复用率预估

| 类别 | 原代码行数 | 复用行数 | 复用率 |
|------|------------|----------|--------|
| 认证系统 | ~200 | 200 | 100% |
| 响应与异常处理 | ~150 | 150 | 100% |
| 工具类 | ~50 | 50 | 100% |
| AOP日志 | ~100 | 100 | 100% |
| 配置文件 | ~50 | 45 | 90% |
| **总计** | **~550** | **~645** | **~75%** |

> 注：复用率计算基于核心架构组件，业务逻辑代码需全新开发。

---

## 八、完成标准

1. ✅ 所有核心架构组件正常运行（认证、响应封装、异常处理）
2. ✅ 用户管理模块完整（CRUD + 登录认证）
3. ✅ 课程管理模块完整（课程、章节、课时的增删改查）
4. ✅ 学习管理模块完整（选课、进度追踪、课程完成）
5. ✅ 考核认证模块完整（题库、考试、答题、阅卷）
6. ✅ 统计报表模块完整（学习统计、成绩统计）
7. ✅ 数据库初始化脚本完整
8. ✅ 后端服务可正常启动并通过接口测试
