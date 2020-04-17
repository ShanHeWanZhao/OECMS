/* 登录信息（学生，老师，管理员通用）*/
CREATE TABLE `login_info`
(
    `user_id`        int(11) UNSIGNED    NOT NULL AUTO_INCREMENT COMMENT '登录账号的id',
    `account_number` varchar(30)         NOT NULL COMMENT '账号',
    `password`       varchar(30)         NOT NULL COMMENT '密码',
    `create_time`    timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '账号创建时间',
    `user_status`    tinyint(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '用户的状态（0 -> 可登录，1 -> 不可登陆）',
    `user_name`      varchar(30)         NOT NULL COMMENT '用户的真实姓名',
    `user_type`      tinyint(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '用户的类型（学生->0，老师->1，管理员->2）',
    `user_class_id`  int(11)             NOT NULL DEFAULT '-1' COMMENT '用户对应的班级id（为-1时代表该用户无班级）',
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8;
/* 实验课程*/
CREATE TABLE `exp_course`
(
    `exp_course_id`             int(11) UNSIGNED    NOT NULL AUTO_INCREMENT COMMENT '实验课程id',
    `exp_course_name`           varchar(50)         NOT NULL DEFAULT '' COMMENT '实验课名称',
    `exp_course_description`    varchar(512)        NOT NULL DEFAULT '' COMMENT '实验课的基本描述',
    `exp_course_location`       varchar(50)         NOT NULL DEFAULT '' COMMENT '上课地点',
    `exp_course_time`           timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上课时间',
    `teacher_id`                int(11) UNSIGNED    NOT NULL COMMENT '任课老师id',
    `student_class_id`          int(11) UNSIGNED    NOT NULL COMMENT '上课班级id',
    `exp_course_status`         tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '实验课程状态（未开始，进行中，已取消，已结束）',
    `exp_course_material`       varchar(255)        NOT NULL DEFAULT '' COMMENT '课程讲义存放的路径',
    `material_upload_count` tinyint(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '实验讲义上传的次数（最多上传3次）',
    `exp_course_create_time`    timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`exp_course_id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8;
/* 实验课程任务的情况*/
CREATE TABLE `course_task`
(
    `course_task_id`          int(11) UNSIGNED       NOT NULL AUTO_INCREMENT COMMENT '课程任务的id',
    `exp_course_id`           int(11) UNSIGNED       NOT NULL COMMENT '实验课的id',
    `student_id`              int(11) UNSIGNED       NOT NULL COMMENT '上课学生id',
    `teacher_id`              int(11) UNSIGNED       NOT NULL COMMENT '任课老师id',
    `exp_course_result_data`  varchar(100)           NOT NULL DEFAULT '' COMMENT '实验课的结果数据的存放地址',
    `exp_course_grade`        decimal(5, 2) UNSIGNED NULL COMMENT '该学生的该实验课的得分',
    `course_task_status`      tinyint(255) UNSIGNED  NOT NULL DEFAULT 0 COMMENT '该学生的该实验课程完成状态（未开始，进行中，已提交，已完成）',
    `course_task_create_time` timestamp              NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `course_task_comment`     varchar(512)           NOT NULL DEFAULT '' COMMENT '该学生的实验课程评语',
    PRIMARY KEY (`course_task_id`),
    INDEX `student_index` (`student_id`) USING BTREE COMMENT '学生id索引'
) ENGINE = InnoDB
  CHARACTER SET = utf8;
/* 学生班级*/
CREATE TABLE `student_class`
(
    `student_class_id`  int(11) UNSIGNED      NOT NULL AUTO_INCREMENT COMMENT '班级的id',
    `class_name`        varchar(50)           NOT NULL COMMENT '班级名字',
    `class_status`      tinyint(255) UNSIGNED NOT NULL DEFAULT 0 COMMENT '班级状态（0 -> 可操作。1 -> 不可操作）',
    `class_create_time` timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '班级创建时间',
    PRIMARY KEY (`student_class_id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8;