import request from '@/utils/request'

// 分页查询
export function getStudentPage(current, size) {
  return request({
    url: '/student/page',
    method: 'get',
    params: { current, size }
  })
}

// 查询所有
export function getAllStudents() {
  return request({
    url: '/student/all',
    method: 'get'
  })
}

// 根据ID查询
export function getStudentById(sno) {
  return request({
    url: `/student/${sno}`,
    method: 'get'
  })
}

// 添加学生
export function addStudent(student) {
  return request({
    url: '/student',
    method: 'post',
    data: student
  })
}

// 修改学生
export function updateStudent(student) {
  return request({
    url: '/student',
    method: 'put',
    data: student
  })
}

// 删除学生
export function deleteStudent(sno) {
  return request({
    url: `/student/${sno}`,
    method: 'delete'
  })
}

// 按姓名查询
export function searchStudentByName(name) {
  return request({
    url: '/student/search',
    method: 'get',
    params: { name }
  })
}