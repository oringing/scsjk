interface Student {
  sno: string;
  sname: string;
  ssex: '男' | '女';
  sage: number;
  sdept: string;
}

interface PageResult<T> {
  records: T[];
  total: number;
}