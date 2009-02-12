using System;
using System.Collections.Generic;
using System.Text;

namespace MyCourses
{
    class Course
    {
        private AcademicYear _academic;

        /// <summary>
        /// 学年
        /// </summary>
        internal AcademicYear Academic
        {
            get { return _academic; }
            set { _academic = value; }
        }
        private Semester _semester;

        /// <summary>
        /// 学期
        /// </summary>
        internal Semester Semester
        {
            get { return _semester; }
            set { _semester = value; }
        }
        private int _courseCode;

        /// <summary>
        /// 课程代码
        /// </summary>
        public int CourseCode
        {
            get { return _courseCode; }
            set { _courseCode = value; }
        }
        private string _courseName;

        /// <summary>
        /// 课程名称
        /// </summary>
        public string CourseName
        {
            get { return _courseName; }
            set { _courseName = value; }
        }
        private string _couresAttribute;

        /// <summary>
        /// 课程性质
        /// </summary>
        public string CouresAttribute
        {
            get { return _couresAttribute; }
            set { _couresAttribute = value; }
        }
        private string _attribute;

        /// <summary>
        /// 课程归属
        /// </summary>
        public string Attribute
        {
            get { return _attribute; }
            set { _attribute = value; }
        }
        private int _credit;

        /// <summary>
        /// 学分
        /// </summary>
        public int Credit
        {
            get { return _credit; }
            set { _credit = value; }
        }
        private float _point;

        /// <summary>
        /// 积点
        /// </summary>
        public float Point
        {
            get { return _point; }
            set { _point = value; }
        }
        private int _score;

        /// <summary>
        /// 成绩
        /// </summary>
        public int Score
        {
            get { return _score; }
            set { _score = value; }
        }

        private string _minor;

        /// <summary>
        /// 辅修
        /// </summary>
        public string Minor
        {
            get { return _minor; }
            set { _minor = value; }
        }

        private int _makeScore;

        /// <summary>
        /// 补考成绩
        /// </summary>
        public int MakeupScore
        {
            get { return _makeScore; }
            set { _makeScore = value; }
        }
        private int _repertCourseScore;

        /// <summary>
        /// 重修成绩
        /// </summary>
        public int RepertCourseScore
        {
            get { return _repertCourseScore; }
            set { _repertCourseScore = value; }
        }
        private string _college;

        /// <summary>
        /// 开课学院
        /// </summary>
        public string College
        {
            get { return _college; }
            set { _college = value; }
        }
        private string _remark;

        /// <summary>
        /// 备注
        /// </summary>
        public string Remark
        {
            get { return _remark; }
            set { _remark = value; }
        }

        public override bool Equals(Object obj)
        {
            Course c = (Course)obj;
            if (c.CourseCode == this.CourseCode)
                return true;
            else
                return false;
        }
    }
}
