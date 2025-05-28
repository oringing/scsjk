export interface Student {
  sno: string;     // 学号
  sname: string;   // 姓名
  ssex: string;    // 性别
  sage: number;    // 年龄
  sdept: string;  // 院系
}

export interface StudentQueryParams {
  pageNum: number;
  pageSize: number;
  searchKey?: string;
}