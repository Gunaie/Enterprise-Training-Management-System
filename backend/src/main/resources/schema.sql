DROP TABLE IF EXISTS exam_record;
DROP TABLE IF EXISTS exam_question;
DROP TABLE IF EXISTS emp_expr;
DROP TABLE IF EXISTS emp_log;
DROP TABLE IF EXISTS emp;
DROP TABLE IF EXISTS dept;
DROP TABLE IF EXISTS exam;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS learning_progress;
DROP TABLE IF EXISTS course_enroll;
DROP TABLE IF EXISTS lesson;
DROP TABLE IF EXISTS chapter;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS course_category;
DROP TABLE IF EXISTS sys_user_role;
DROP TABLE IF EXISTS role_permission;
DROP TABLE IF EXISTS permission;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS sys_user;
DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS sys_user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50) NOT NULL,
    gender INT DEFAULT 1,
    phone VARCHAR(20),
    email VARCHAR(100),
    avatar VARCHAR(500),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS role (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS permission (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    code VARCHAR(100) NOT NULL UNIQUE,
    url VARCHAR(200),
    method VARCHAR(10),
    parent_id INT DEFAULT 0,
    order_num INT DEFAULT 0,
    icon VARCHAR(50),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sys_user_role (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES sys_user(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS role_permission (
    role_id INT NOT NULL,
    permission_id INT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permission(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS course_category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    sort_order INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS course (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    cover_image VARCHAR(500),
    category_id INT,
    teacher_id INT,
    duration INT DEFAULT 0,
    status INT DEFAULT 1,
    total_students INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES course_category(id)
);

CREATE TABLE IF NOT EXISTS chapter (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    sort_order INT DEFAULT 0,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS lesson (
    id INT PRIMARY KEY AUTO_INCREMENT,
    chapter_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    video_url VARCHAR(500),
    duration INT DEFAULT 0,
    sort_order INT DEFAULT 0,
    is_free INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (chapter_id) REFERENCES chapter(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS course_enroll (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    course_id INT NOT NULL,
    progress INT DEFAULT 0,
    is_completed INT DEFAULT 0,
    enroll_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    complete_time DATETIME,
    UNIQUE KEY uk_user_course (user_id, course_id)
);

CREATE TABLE IF NOT EXISTS learning_progress (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    lesson_id INT NOT NULL,
    is_completed INT DEFAULT 0,
    complete_time DATETIME,
    UNIQUE KEY uk_user_lesson (user_id, lesson_id),
    FOREIGN KEY (lesson_id) REFERENCES lesson(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS question (
    id INT PRIMARY KEY AUTO_INCREMENT,
    type INT DEFAULT 1,
    content TEXT NOT NULL,
    options TEXT,
    answer VARCHAR(100) NOT NULL,
    score INT DEFAULT 10,
    course_id INT,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS exam (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    course_id INT,
    duration INT DEFAULT 60,
    total_score INT DEFAULT 100,
    pass_score INT DEFAULT 60,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS exam_question (
    id INT PRIMARY KEY AUTO_INCREMENT,
    exam_id INT NOT NULL,
    question_id INT NOT NULL,
    sort_order INT DEFAULT 0,
    FOREIGN KEY (exam_id) REFERENCES exam(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS exam_record (
    id INT PRIMARY KEY AUTO_INCREMENT,
    exam_id INT NOT NULL,
    user_id INT NOT NULL,
    answers TEXT,
    score DOUBLE DEFAULT 0,
    is_passed INT DEFAULT 0,
    submit_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (exam_id) REFERENCES exam(id) ON DELETE CASCADE
);

INSERT INTO sys_user (username, password, name, gender, phone, email, status) VALUES ('admin', 'admin', '管理员', 1, '13800138000', 'admin@eduforge.cn', 1);
INSERT INTO sys_user (username, password, name, gender, phone, email, status) VALUES ('zhangsan', '123456', '张三', 1, '13800138001', 'zhangsan@eduforge.cn', 1);
INSERT INTO sys_user (username, password, name, gender, phone, email, status) VALUES ('lisi', '123456', '李四', 2, '13800138002', 'lisi@eduforge.cn', 1);

INSERT INTO role (name, code, description, status) VALUES ('超级管理员', 'admin', '拥有系统全部权限', 1);
INSERT INTO role (name, code, description, status) VALUES ('培训讲师', 'instructor', '负责课程内容与考试管理', 1);
INSERT INTO role (name, code, description, status) VALUES ('学员', 'learner', '参与课程学习与考试', 1);

INSERT INTO permission (name, code, url, method, parent_id, order_num, icon, status) VALUES ('课程查看', 'course:view', '/courses', 'GET', 0, 1, 'book-open', 1);
INSERT INTO permission (name, code, url, method, parent_id, order_num, icon, status) VALUES ('课程管理', 'course:manage', '/courses', 'POST', 0, 2, 'edit', 1);
INSERT INTO permission (name, code, url, method, parent_id, order_num, icon, status) VALUES ('考试管理', 'exam:manage', '/exams', 'POST', 0, 3, 'clipboard-list', 1);
INSERT INTO permission (name, code, url, method, parent_id, order_num, icon, status) VALUES ('学员管理', 'student:manage', '/students', 'GET', 0, 4, 'users', 1);
INSERT INTO permission (name, code, url, method, parent_id, order_num, icon, status) VALUES ('数据统计', 'statistics:view', '/statistics', 'GET', 0, 5, 'bar-chart-3', 1);
INSERT INTO permission (name, code, url, method, parent_id, order_num, icon, status) VALUES ('角色管理', 'role:manage', '/roles', 'GET', 0, 6, 'shield', 1);

INSERT INTO role_permission (role_id, permission_id) VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6);
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 1), (2, 2), (2, 3);
INSERT INTO role_permission (role_id, permission_id) VALUES (3, 1);

INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1), (2, 3), (3, 3);

INSERT INTO course_category (name, sort_order) VALUES ('技术开发', 1);
INSERT INTO course_category (name, sort_order) VALUES ('产品设计', 2);
INSERT INTO course_category (name, sort_order) VALUES ('项目管理', 3);
INSERT INTO course_category (name, sort_order) VALUES ('职场技能', 4);

INSERT INTO course (title, description, cover_image, category_id, teacher_id, duration, status, total_students) VALUES
('Java高级编程', '深入学习Java核心技术和设计模式', 'https://picsum.photos/seed/java/600/400', 1, 1, 24, 1, 120),
('Vue.js前端开发', '从零到一掌握Vue.js框架', 'https://picsum.photos/seed/vue/600/400', 1, 1, 20, 1, 85),
('产品经理实战', '系统学习产品设计和需求分析', 'https://picsum.photos/seed/product/600/400', 2, 2, 18, 1, 60),
('项目管理PMP', '项目管理知识体系精讲', 'https://picsum.photos/seed/pmp/600/400', 3, 3, 32, 1, 45);

INSERT INTO chapter (course_id, title, sort_order) VALUES (1, 'Java基础回顾', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (1, '面向对象进阶', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (1, '设计模式', 3);
INSERT INTO chapter (course_id, title, sort_order) VALUES (2, 'Vue基础', 1);
INSERT INTO chapter (course_id, title, sort_order) VALUES (2, '组件开发', 2);
INSERT INTO chapter (course_id, title, sort_order) VALUES (2, '状态管理', 3);

INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (1, 'Java8新特性', 'https://example.com/video1.mp4', 45, 1, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (1, 'Lambda表达式', 'https://example.com/video2.mp4', 30, 2, 1);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (2, '抽象类与接口', 'https://example.com/video3.mp4', 40, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (2, '内部类详解', 'https://example.com/video4.mp4', 35, 2, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (3, '单例模式', 'https://example.com/video5.mp4', 50, 1, 0);
INSERT INTO lesson (chapter_id, title, video_url, duration, sort_order, is_free) VALUES (3, '工厂模式', 'https://example.com/video6.mp4', 45, 2, 0);

INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, 'Java中哪个关键字用于定义接口？', 'A. class\nB. interface\nC. abstract\nD. implements', 'B', 10, 1);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, 'Vue.js中用于双向数据绑定的指令是？', 'A. v-bind\nB. v-model\nC. v-if\nD. v-for', 'B', 10, 2);
INSERT INTO question (type, content, options, answer, score, course_id) VALUES (1, '以下哪个不是设计模式的分类？', 'A. 创建型\nB. 结构型\nC. 行为型\nD. 数据型', 'D', 10, 1);

INSERT INTO exam (title, course_id, duration, total_score, pass_score, status) VALUES ('Java高级编程结业考试', 1, 60, 100, 60, 1);
INSERT INTO exam (title, course_id, duration, total_score, pass_score, status) VALUES ('Vue.js前端开发考试', 2, 60, 100, 60, 1);

INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (1, 1, 1);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (1, 3, 2);
INSERT INTO exam_question (exam_id, question_id, sort_order) VALUES (2, 2, 1);
