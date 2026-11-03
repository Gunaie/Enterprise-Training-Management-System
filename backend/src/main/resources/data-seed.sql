INSERT INTO sys_user (username, password, name, gender, phone, email, status) VALUES ('wangwu', '123456', '王五', 1, '13800138003', 'wangwu@eduforge.cn', 1);
INSERT INTO sys_user (username, password, name, gender, phone, email, status) VALUES ('zhaoliu', '123456', '赵六', 2, '13800138004', 'zhaoliu@eduforge.cn', 1);
INSERT INTO sys_user (username, password, name, gender, phone, email, status) VALUES ('sunqi', '123456', '孙七', 1, '13800138005', 'sunqi@eduforge.cn', 1);
INSERT INTO sys_user (username, password, name, gender, phone, email, status) VALUES ('zhouba', '123456', '周八', 2, '13800138006', 'zhouba@eduforge.cn', 1);
INSERT INTO sys_user (username, password, name, gender, phone, email, status) VALUES ('wujiu', '123456', '吴九', 1, '13800138007', 'wujiu@eduforge.cn', 1);
INSERT INTO sys_user (username, password, name, gender, phone, email, status) VALUES ('zhengshi', '123456', '郑十', 2, '13800138008', 'zhengshi@eduforge.cn', 1);
INSERT INTO sys_user (username, password, name, gender, phone, email, status) VALUES ('chenyi', '123456', '陈一', 1, '13800138009', 'chenyi@eduforge.cn', 1);
INSERT INTO sys_user (username, password, name, gender, phone, email, status) VALUES ('liuer', '123456', '刘二', 2, '13800138010', 'liuer@eduforge.cn', 1);
INSERT INTO sys_user (username, password, name, gender, phone, email, status) VALUES ('qianqi', '123456', '钱七', 1, '13800138011', 'qianqi@eduforge.cn', 1);
INSERT INTO sys_user (username, password, name, gender, phone, email, status) VALUES ('sunba', '123456', '孙八', 2, '13800138012', 'sunba@eduforge.cn', 1);

INSERT INTO sys_user_role (user_id, role_id) VALUES (4, 3), (5, 3), (6, 3), (7, 3), (8, 3), (9, 3), (10, 3), (11, 3), (12, 3), (13, 3);

INSERT INTO course_category (name, sort_order) VALUES ('数据分析', 5);
INSERT INTO course_category (name, sort_order) VALUES ('人工智能', 6);
INSERT INTO course_category (name, sort_order) VALUES ('云计算', 7);
INSERT INTO course_category (name, sort_order) VALUES ('网络安全', 8);

INSERT INTO course (title, description, cover_image, category_id, teacher_id, duration, status, total_students) VALUES 
('Spring Boot实战', '从零搭建企业级Spring Boot应用，掌握自动配置、数据访问、安全认证等核心技术', 'https://picsum.photos/seed/springboot/600/400', 1, 1, 32, 1, 200),
('微服务架构设计', '深入理解微服务原理，学习Spring Cloud、Dubbo等主流微服务框架', 'https://picsum.photos/seed/microservice/600/400', 1, 1, 40, 1, 150),
('React前端开发', '掌握React生态系统，包括Hooks、Redux、React Router等核心技术', 'https://picsum.photos/seed/react/600/400', 1, 2, 28, 1, 180),
('TypeScript进阶', '深入学习TypeScript类型系统，提升大型项目开发效率', 'https://picsum.photos/seed/typescript/600/400', 1, 2, 24, 1, 160),
('UI/UX设计基础', '学习用户界面和用户体验设计的基本原则和方法', 'https://picsum.photos/seed/uiux/600/400', 2, 3, 20, 1, 90),
('交互设计原理', '掌握交互设计的核心理论和实践技巧', 'https://picsum.photos/seed/interaction/600/400', 2, 3, 16, 1, 75),
('敏捷开发方法论', '学习Scrum、Kanban等敏捷开发方法，提升团队协作效率', 'https://picsum.photos/seed/agile/600/400', 3, 3, 18, 1, 110),
('软件测试基础', '系统学习软件测试理论和实践，掌握测试用例设计方法', 'https://picsum.photos/seed/testing/600/400', 3, 1, 22, 1, 125),
('沟通表达技巧', '提升职场沟通能力，学会有效表达和倾听', 'https://picsum.photos/seed/communication/600/400', 4, 2, 12, 1, 140),
('时间管理与效率', '掌握时间管理方法，提升工作和学习效率', 'https://picsum.photos/seed/time/600/400', 4, 3, 10, 1, 130),
('Python数据分析', '使用Python进行数据处理、分析和可视化', 'https://picsum.photos/seed/pythondata/600/400', 5, 1, 30, 1, 220),
('机器学习入门', '学习机器学习基础算法，动手实现经典模型', 'https://picsum.photos/seed/ml/600/400', 6, 1, 36, 1, 190),
('Docker容器化', '掌握Docker容器技术，实现应用容器化部署', 'https://picsum.photos/seed/docker/600/400', 7, 1, 24, 1, 175),
('Kubernetes运维', '学习K8s集群管理和运维，掌握容器编排技术', 'https://picsum.photos/seed/k8s/600/400', 7, 2, 32, 1, 145),
('网络安全基础', '了解网络安全原理，学习常见安全防护措施', 'https://picsum.photos/seed/security/600/400', 8, 1, 26, 1, 165);

INSERT INTO chapter (course_id, title, sort_order) VALUES (1, 'Spring Boot入门', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (1, '配置管理', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (1, '数据访问', 3);
INSERT INTO chapter (course_id, title, sort_order) VALUES (1, '安全认证', 4);
INSERT INTO chapter (course_id, title, sort_order) VALUES (2, '微服务概述', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (2, '服务注册与发现', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (2, '服务调用', 3);
INSERT INTO chapter (course_id, title, sort_order) VALUES (2, '配置中心', 4);
INSERT INTO chapter (course_id, title, sort_order) VALUES (3, 'React基础', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (3, 'Hooks详解', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (3, '状态管理', 3);
INSERT INTO chapter (course_id, title, sort_order) VALUES (4, 'TypeScript基础', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (4, '类型系统', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (4, '高级类型', 3);
INSERT INTO chapter (course_id, title, sort_order) VALUES (5, '设计原则', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (5, '视觉设计', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (6, '交互原理', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (6, '用户研究', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (7, '敏捷概述', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (7, 'Scrum实践', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (8, '测试基础', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (8, '测试方法', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (9, '沟通基础', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (9, '表达技巧', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (10, '时间管理', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (10, '效率提升', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (11, 'Python基础', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (11, '数据处理', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (11, '可视化', 3);
INSERT INTO chapter (course_id, title, sort_order) VALUES (12, 'ML基础', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (12, '监督学习', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (13, 'Docker基础', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (13, '镜像与容器', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (14, 'K8s基础', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (14, '资源管理', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (15, '安全基础', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (15, '防护措施', 2);

INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (7, 'Spring Boot简介', 'https://example.com/v1.mp4', 30, 1, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (7, '环境搭建', 'https://example.com/v2.mp4', 40, 2, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (8, 'application.yml配置', 'https://example.com/v3.mp4', 35, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (8, '配置文件优先级', 'https://example.com/v4.mp4', 30, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (9, 'MyBatis集成', 'https://example.com/v5.mp4', 45, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (9, 'JPA使用', 'https://example.com/v6.mp4', 40, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (10, 'Spring Security', 'https://example.com/v7.mp4', 50, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (10, 'JWT认证', 'https://example.com/v8.mp4', 45, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (11, '微服务概念', 'https://example.com/v9.mp4', 35, 1, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (11, '优缺点分析', 'https://example.com/v10.mp4', 30, 2, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (12, 'Eureka注册中心', 'https://example.com/v11.mp4', 45, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (12, 'Nacos配置', 'https://example.com/v12.mp4', 40, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (13, 'Feign声明式调用', 'https://example.com/v13.mp4', 40, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (13, 'OpenFeign使用', 'https://example.com/v14.mp4', 35, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (14, 'Spring Cloud Config', 'https://example.com/v15.mp4', 45, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (14, 'Nacos配置中心', 'https://example.com/v16.mp4', 40, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (15, 'React组件基础', 'https://example.com/v17.mp4', 40, 1, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (15, 'JSX语法', 'https://example.com/v18.mp4', 35, 2, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (16, 'useState Hook', 'https://example.com/v19.mp4', 45, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (16, 'useEffect Hook', 'https://example.com/v20.mp4', 50, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (17, 'Redux状态管理', 'https://example.com/v21.mp4', 55, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (17, 'React Query', 'https://example.com/v22.mp4', 45, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (18, 'TypeScript入门', 'https://example.com/v23.mp4', 35, 1, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (18, '基础类型', 'https://example.com/v24.mp4', 40, 2, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (19, '接口与类型别名', 'https://example.com/v25.mp4', 45, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (19, '泛型详解', 'https://example.com/v26.mp4', 50, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (20, '条件类型', 'https://example.com/v27.mp4', 40, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (20, '映射类型', 'https://example.com/v28.mp4', 35, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (21, '设计原则概述', 'https://example.com/v29.mp4', 30, 1, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (21, '可用性设计', 'https://example.com/v30.mp4', 35, 2, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (22, '色彩理论', 'https://example.com/v31.mp4', 40, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (22, '排版设计', 'https://example.com/v32.mp4', 35, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (23, '交互设计原则', 'https://example.com/v33.mp4', 35, 1, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (23, '用户流程', 'https://example.com/v34.mp4', 40, 2, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (24, '用户调研方法', 'https://example.com/v35.mp4', 45, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (24, '可用性测试', 'https://example.com/v36.mp4', 40, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (25, '敏捷宣言', 'https://example.com/v37.mp4', 30, 1, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (25, '敏捷原则', 'https://example.com/v38.mp4', 35, 2, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (26, 'Scrum框架', 'https://example.com/v39.mp4', 45, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (26, 'Sprint实践', 'https://example.com/v40.mp4', 40, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (27, '测试分类', 'https://example.com/v41.mp4', 35, 1, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (27, '测试流程', 'https://example.com/v42.mp4', 30, 2, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (28, '黑盒测试', 'https://example.com/v43.mp4', 40, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (28, '白盒测试', 'https://example.com/v44.mp4', 45, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (29, '沟通原则', 'https://example.com/v45.mp4', 30, 1, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (29, '非暴力沟通', 'https://example.com/v46.mp4', 35, 2, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (30, '演讲技巧', 'https://example.com/v47.mp4', 40, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (30, '汇报技巧', 'https://example.com/v48.mp4', 35, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (31, '时间管理方法', 'https://example.com/v49.mp4', 35, 1, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (31, '四象限法则', 'https://example.com/v50.mp4', 30, 2, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (32, '番茄工作法', 'https://example.com/v51.mp4', 35, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (32, 'GTD方法', 'https://example.com/v52.mp4', 40, 2, 0);

INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, 'Spring Boot的启动类需要使用哪个注解？', 'A. @SpringBoot\nB. @SpringApplication\nC. @SpringBootApplication\nD. @EnableAutoConfiguration', 'C', 10, 5);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, '@Autowired注解的作用是什么？', 'A. 自动配置\nB. 自动装配依赖\nC. 声明Bean\nD. 开启组件扫描', 'B', 10, 5);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, '以下哪个不是Spring Boot的配置文件格式？', 'A. .properties\nB. .yml\nC. .yaml\nD. .xml', 'D', 10, 5);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, '@RestController注解是由哪两个注解组合而成的？', 'A. @Controller + @ResponseBody\nB. @Service + @ResponseBody\nC. @Component + @ResponseBody\nD. @Repository + @ResponseBody', 'A', 10, 5);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, 'Spring Boot默认的端口号是多少？', 'A. 8080\nB. 8081\nC. 80\nD. 3000', 'A', 10, 5);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, '微服务架构的特点不包括以下哪项？', 'A. 单一职责\nB. 独立部署\nC. 集中式管理\nD. 服务自治', 'C', 10, 6);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, 'Eureka是什么组件？', 'A. 配置中心\nB. 服务注册中心\nC. 网关\nD. 消息队列', 'B', 10, 6);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, 'Feign主要用于什么场景？', 'A. 服务发现\nB. 声明式服务调用\nC. 负载均衡\nD. 熔断降级', 'B', 10, 6);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, 'React中用于创建组件的方式不包括？', 'A. 函数组件\nB. 类组件\nC. ES5构造函数\nD. 箭头函数', 'C', 10, 7);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, 'useEffect的依赖数组为空数组时，effect何时执行？', 'A. 每次渲染都执行\nB. 只在组件挂载时执行\nC. 永不执行\nD. 只在状态改变时执行', 'B', 10, 7);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, 'TypeScript中interface和type的区别？', 'A. interface可以扩展，type不能\nB. type可以表示联合类型，interface不能\nC. interface可以声明合并，type不能\nD. 以上都对', 'D', 10, 8);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, 'TypeScript中泛型的作用是什么？', 'A. 提高代码复用性\nB. 在编译时进行类型检查\nC. 支持多种类型的通用逻辑\nD. 以上都对', 'D', 10, 8);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, '以下哪个不是设计原则？', 'A. 对齐\nB. 对比\nC. 重复\nD. 复杂', 'D', 10, 9);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, 'UI设计中常用的色彩模式是？', 'A. RGB\nB. CMYK\nC. HSB\nD. 以上都对', 'D', 10, 9);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, '敏捷宣言的核心价值观不包括？', 'A. 个体和互动高于流程和工具\nB. 工作的软件高于详尽的文档\nC. 合同谈判高于客户合作\nD. 响应变化高于遵循计划', 'C', 10, 11);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, 'Scrum中的Sprint通常持续多长时间？', 'A. 1-4周\nB. 1个月\nC. 3个月\nD. 6个月', 'A', 10, 11);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, '单元测试的主要目的是什么？', 'A. 测试整个系统\nB. 测试单个模块或函数\nC. 测试性能\nD. 测试安全性', 'B', 10, 12);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, '黑盒测试关注的是？', 'A. 代码内部逻辑\nB. 输入输出关系\nC. 代码覆盖率\nD. 内存使用', 'B', 10, 12);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, 'Docker容器和虚拟机的主要区别是？', 'A. 容器更轻量\nB. 虚拟机隔离性更好\nC. 容器启动更快\nD. 以上都对', 'D', 10, 13);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, 'Kubernetes中Pod的作用是什么？', 'A. 存储配置\nB. 管理节点\nC. 部署应用容器\nD. 负载均衡', 'C', 10, 14);

INSERT INTO exam (title, course_id, duration, total_score, pass_score, status) VALUES ('Spring Boot实战考试', 5, 60, 100, 60, 1);
INSERT INTO exam (title, course_id, duration, total_score, pass_score, status) VALUES ('微服务架构设计考试', 6, 90, 100, 60, 1);
INSERT INTO exam (title, course_id, duration, total_score, pass_score, status) VALUES ('React前端开发考试', 7, 60, 100, 60, 1);
INSERT INTO exam (title, course_id, duration, total_score, pass_score, status) VALUES ('TypeScript进阶考试', 8, 60, 100, 60, 1);
INSERT INTO exam (title, course_id, duration, total_score, pass_score, status) VALUES ('UI/UX设计基础考试', 9, 60, 100, 60, 1);
INSERT INTO exam (title, course_id, duration, total_score, pass_score, status) VALUES ('敏捷开发方法论考试', 11, 60, 100, 60, 1);
INSERT INTO exam (title, course_id, duration, total_score, pass_score, status) VALUES ('软件测试基础考试', 12, 60, 100, 60, 1);
INSERT INTO exam (title, course_id, duration, total_score, pass_score, status) VALUES ('Docker容器化考试', 13, 60, 100, 60, 1);

INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (3, 4, 1);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (3, 5, 2);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (3, 6, 3);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (3, 7, 4);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (3, 8, 5);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (4, 9, 1);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (4, 10, 2);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (5, 11, 1);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (5, 12, 2);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (6, 13, 1);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (6, 14, 2);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (7, 15, 1);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (7, 16, 2);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (8, 17, 1);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (8, 18, 2);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (9, 19, 1);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (9, 20, 2);

INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (2, 1, 80, 0, '2024-01-15 10:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (2, 2, 100, 1, '2024-01-10 14:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (2, 5, 50, 0, '2024-02-01 09:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (3, 1, 30, 0, '2024-01-20 11:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (3, 3, 100, 1, '2024-01-05 15:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (3, 7, 60, 0, '2024-02-05 10:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (4, 2, 70, 0, '2024-01-18 09:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (4, 4, 100, 1, '2024-01-08 16:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (4, 8, 40, 0, '2024-02-08 14:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (5, 1, 100, 1, '2024-01-01 10:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (5, 5, 90, 0, '2024-01-25 11:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (5, 9, 20, 0, '2024-02-10 09:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (6, 3, 50, 0, '2024-01-12 14:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (6, 6, 80, 0, '2024-01-22 10:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (6, 10, 100, 1, '2024-01-03 15:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (7, 4, 60, 0, '2024-01-14 09:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (7, 7, 30, 0, '2024-01-28 11:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (7, 11, 70, 0, '2024-02-03 14:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (8, 5, 40, 0, '2024-01-16 10:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (8, 8, 90, 0, '2024-01-26 15:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (8, 12, 25, 0, '2024-02-06 09:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (9, 6, 55, 0, '2024-01-17 14:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (9, 9, 85, 0, '2024-01-29 11:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (9, 13, 100, 1, '2024-01-06 16:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (10, 7, 45, 0, '2024-01-19 09:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (10, 10, 75, 0, '2024-01-30 14:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (10, 14, 35, 0, '2024-02-09 10:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (11, 8, 65, 0, '2024-01-21 10:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (11, 11, 100, 1, '2024-01-04 15:00:00');
INSERT INTO course_enroll (user_id, course_id, progress, is_completed, enroll_time) VALUES (11, 15, 50, 0, '2024-02-11 09:00:00');

INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (2, 1, 1, '2024-01-15 11:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (2, 2, 1, '2024-01-15 12:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (2, 3, 1, '2024-01-16 10:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (2, 4, 0, NULL);
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (3, 1, 1, '2024-01-20 12:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (3, 2, 0, NULL);
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (4, 4, 1, '2024-01-18 15:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (4, 5, 1, '2024-01-19 10:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (5, 1, 1, '2024-01-01 11:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (5, 2, 1, '2024-01-01 12:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (5, 3, 1, '2024-01-02 10:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (5, 4, 1, '2024-01-02 11:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (6, 7, 1, '2024-01-12 15:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (6, 8, 0, NULL);
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (7, 11, 1, '2024-01-14 10:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (7, 12, 1, '2024-01-14 11:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (8, 15, 1, '2024-01-16 11:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (8, 16, 0, NULL);
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (9, 19, 1, '2024-01-17 15:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (9, 20, 1, '2024-01-17 16:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (10, 23, 1, '2024-01-19 10:00:00');
INSERT INTO learning_progress (user_id, lesson_id, is_completed, complete_time) VALUES (10, 24, 0, NULL);

INSERT INTO exam_record (exam_id, user_id, answers, score, is_passed, submit_time) VALUES (1, 2, '{"1":"B","3":"D"}', 20, 0, '2024-01-20 10:00:00');
INSERT INTO exam_record (exam_id, user_id, answers, score, is_passed, submit_time) VALUES (2, 2, '{"2":"B"}', 10, 0, '2024-01-21 14:00:00');
INSERT INTO exam_record (exam_id, user_id, answers, score, is_passed, submit_time) VALUES (1, 3, '{"1":"B","3":"D"}', 20, 0, '2024-01-22 09:00:00');
INSERT INTO exam_record (exam_id, user_id, answers, score, is_passed, submit_time) VALUES (3, 5, '{"4":"C","5":"B","6":"D","7":"A","8":"A"}', 50, 0, '2024-02-01 10:00:00');
INSERT INTO exam_record (exam_id, user_id, answers, score, is_passed, submit_time) VALUES (3, 5, '{"4":"C","5":"B","6":"D","7":"A","8":"A"}', 50, 0, '2024-02-05 14:00:00');
INSERT INTO exam_record (exam_id, user_id, answers, score, is_passed, submit_time) VALUES (4, 7, '{"9":"D","10":"D"}', 20, 0, '2024-02-03 10:00:00');
INSERT INTO exam_record (exam_id, user_id, answers, score, is_passed, submit_time) VALUES (5, 9, '{"19":"D","20":"D"}', 20, 0, '2024-02-06 09:00:00');
INSERT INTO exam_record (exam_id, user_id, answers, score, is_passed, submit_time) VALUES (6, 6, '{"15":"C","16":"A"}', 20, 0, '2024-02-08 14:00:00');

