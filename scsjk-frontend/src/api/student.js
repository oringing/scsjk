import request from '@/utils/request'

// 按学号精确查询
export function getStudentBySno(sno) {
  return request({
    url: `/student/${sno}`,
    method: 'get'
  })
}

// 分页查询（保持原样）
export function getStudentPage(current, size) {
  return request({
    url: '/student/page',
    method: 'get',
    params: { current, size }
  })
}

// 按姓名模糊搜索学生（带分页参数）
export function searchStudentByName(name, current = 1, size = 10) {
  return request({
    url: '/student/search',
    method: 'get',
    params: { name, current, size }
  })
}

// 组合条件分页查询（学号、姓名、年龄等）
export function getStudentByCondition(params) {
  return request({
    url: '/student/condition',
    method: 'get',
    params: params // 可传递 { sno, name, age, current, size } 等参数
  })
}

// 新增学生
export function addStudent(data) {
  return request({
    url: '/student',
    method: 'post',
    data
  })
}

// 修改学生信息
export function updateStudent(data) {
  return request({
    url: '/student',
    method: 'put',
    data
  })
}

// 删除学生
export function deleteStudent(sno) {
  return request({
    url: `/student/${sno}`,
    method: 'delete'
  })
}