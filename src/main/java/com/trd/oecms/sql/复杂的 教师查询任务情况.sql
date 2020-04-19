-- 完整的
select co.*, lo.user_name, lo.user_class_id, ex.exp_course_name, innercs.class_name
from  course_task co		 /* 课程任务表 */
join login_info lo on co.student_id = lo.user_id 		/* 登录表 */
join exp_course ex on ex.exp_course_id = co.exp_course_id  		/* 实验课程表 */
join (
	select cs.*, st.class_name
	from student_class st
	join (
			select innerco.course_task_id, innerlo.user_class_id
			from  course_task innerco 
			join login_info innerlo on innerco.student_id = innerlo.user_id
				) 
		as cs
		on cs.user_class_id = st.student_class_id
		) as innercs on innercs.course_task_id=co.course_task_id
where 
	co.teacher_id = 8
	and lo.user_name like '%%'   /* 学生姓名*/
	and co.course_task_status = 0     /* 完成情况*/
	and ex.exp_course_name like '%%'	   /* 实验课名称*/
 	and innercs.class_name like '%%'     /* 上课班级*/
ORDER BY co.course_task_id;

-- 只查询数量，分页使用
select count(co.course_task_id)
from  course_task co /* 课程任务表 */
join login_info lo on co.student_id = lo.user_id /* 登录表 */
join exp_course ex on ex.exp_course_id = co.exp_course_id  /* 实验课程表 */
join (
	select cs.*, st.class_name
	from student_class st
	join (
			select innerco.course_task_id, innerlo.user_class_id
			from  course_task innerco 
			join login_info innerlo on innerco.student_id = innerlo.user_id
				) 
		as cs
		on cs.user_class_id = st.student_class_id
		) as innercs on innercs.course_task_id=co.course_task_id
where 
	co.teacher_id = 8
	and lo.user_name like '%%' /* 学生姓名*/
	and co.course_task_status = 0   /* 完成情况*/
	and ex.exp_course_name like '%%'	 /* 实验课名称*/
	and innercs.class_name like '%%'   /* 上课班级*/
ORDER BY co.course_task_id;

-- 老师写的
SELECT *
FROM course_task co, login_info lo, exp_course ex, student_class st
WHERE co.student_id =  lo.user_id
AND co.exp_course_id =  ex.exp_course_id
AND lo.user_id IN 
(SELECT innerio.user_id 
FROM login_info innerio, student_class innerst
WHERE innerio.user_class_id = innerst.student_class_id
AND innerio.user_name LIKE '%%' 
AND innerst.class_name LIKE '%%')

AND course_task.course_task_status = 0
AND ex.exp_course_name like '%%'            /* 实验课名称*/



