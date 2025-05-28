interface Course {
  cno: string;
  cname: string;
  cpno: string | null;
  ccredit: number;
}

interface PageResult<T> {
  records: T[];
  total: number;
}