-- 查询条件：studentId, courseTaskStatus,expCourseName,expCourseLocation, startTime, endTime, teacherName
-- course_task（主表）、exp_course、login_info
# 完成的查询语句
SELECT co.*,exp_detail.exp_course_name, exp_detail.exp_course_location, exp_detail.exp_course_time,teacher_detail.user_name as teacher_name, 
exp_detail.exp_course_status, exp_detail.material_upload_count, exp_detail.exp_course_material
FROM course_task co
join (
	select inc_1.course_task_id, inc_1.exp_course_id, ine_1.exp_course_name, 
	ine_1.exp_course_location, ine_1.exp_course_time, ine_1.exp_course_status, ine_1.material_upload_count, ine_1.exp_course_material
	from course_task as inc_1
	join exp_course as ine_1 
		on inc_1.exp_course_id=ine_1.exp_course_id
	   ) as exp_detail 
	on exp_detail.course_task_id = co.course_task_id
join (
	select inc_2.course_task_id,inc_2.teacher_id, inl_1.user_name from course_task as inc_2
	join login_info as inl_1 
		on inc_2.teacher_id = inl_1.user_id
		 ) as teacher_detail
on teacher_detail.course_task_id = co.course_task_id
WHERE co.student_id = 2  -- 查询本人
and exp_detail.exp_course_name like '%%' -- 实验名
and exp_detail.exp_course_location like '%%' -- 上课地点
and teacher_detail.user_name like '%%'  -- 任课老师
and exp_detail.exp_course_time > '2018-00-00 00:00:00' and exp_detail.exp_course_time < '2990-00-00 00:00:00' -- 上课时间
and co.course_task_status = 0 -- 课程任务状态
ORDER BY exp_detail.exp_course_time DESC;





select inc_1.exp_course_id, ine_1.exp_course_name, ine_1.exp_course_location, ine_1.exp_course_time
from course_task as inc_1
join exp_course as ine_1 on inc_1.exp_course_id=ine_1.exp_course_id
where inc_1.student_id = 2

select inc_2.course_task_id,inc_2.teacher_id, inl_1.user_name from course_task as inc_2
join login_info as inl_1 on inc_2.teacher_id = inl_1.user_id
WHERE inc_2.student_id = 2;


 
 
 
 
 
 
 
 
 
 
 
 
 
 
 