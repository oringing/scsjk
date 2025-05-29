import request from '@/utils/request'

// 分页查询
export function getScPage(current, size) {
  return request({ // 这里的url是/sc/page?current=${current}&size=${size}
    url: `/sc/page?current=${current}&size=${size}`, 
    method: 'get' // 请求方式
  })
}

// 查询所有
export function getScAll() {
  return request({
    url: '/sc/all',
    method: 'get'
  })
}

// 根据学号和课程号查询
export function getScByIds(sno, cno) {
  return request({
    url: `/sc/${sno}/${cno}`,
    method: 'get'
  })
}

// 新增选课记录
export function addSc(data) {
  return request({
    url: '/sc',
    method: 'post',
    data
  })
}

// 修改选课记录
export function updateSc(data) {
  return request({
    url: '/sc',
    method: 'put',
    data
  })
}

// 删除选课记录
export function deleteSc(sno, cno) {
  return request({
    url: `/sc/${sno}/${cno}`,
    method: 'delete'
  })
}