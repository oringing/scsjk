interface Sc {
  sno: string;
  cno: string;
  grade: number;
}

interface PageResult<T> {
  records: T[];
  total: number;
}