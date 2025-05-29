import request from '@/utils/request'

// 分页查询课程信息
export function getCoursePage(current, size) { // current表示当前页码，size表示每页显示的记录数
  return request({
    url: '/course/page', // 请求地址
    method: 'get', // 请求方式
    params: { current, size } // 请求参数,通过params传递参数
  })
}

// 查询所有课程信息
export function getCourseAll() {
  return request({
    url: '/course/all',
    method: 'get'
  })
}

// 根据ID查询课程信息
export function getCourseById(cno) {
  return request({
    url: `/course/${cno}`,
    method: 'get'
  })
}

// 新增课程信息
export function addCourse(data) {
  return request({
    url: '/course',
    method: 'post',
    data
  })
}

// 修改课程信息
export function updateCourse(data) {
  return request({
    url: '/course',
    method: 'put',
    data
  })
}

// 删除课程信息
export function deleteCourse(cno) {
  return request({
    url: `/course/${cno}`,
    method: 'delete'
  })
}