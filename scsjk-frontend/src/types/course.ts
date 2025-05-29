 interface Course { // 课程信息
  cno: string;
  cname: string;
  cpno: string | null;
  ccredit: number;
}

interface PageResult<T> {//  分页查询结果
  records: T[];
  total: number;
}