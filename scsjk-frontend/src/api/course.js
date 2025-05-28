import request from '@/utils/request'

// 分页查询
export function getCoursePage(current, size) {
  return request({
    url: '/course/page',
    method: 'get',
    params: { current, size }
  })
}

// 查询所有
export function getCourseAll() {
  return request({
    url: '/course/all',
    method: 'get'
  })
}

// 根据ID查询
export function getCourseById(cno) {
  return request({
    url: `/course/${cno}`,
    method: 'get'
  })
}

// 新增课程
export function addCourse(data) {
  return request({
    url: '/course',
    method: 'post',
    data
  })
}

// 修改课程
export function updateCourse(data) {
  return request({
    url: '/course',
    method: 'put',
    data
  })
}

// 删除课程
export function deleteCourse(cno) {
  return request({
    url: `/course/${cno}`,
    method: 'delete'
  })
}