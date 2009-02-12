using System;
using System.Collections.Generic;
using System.Text;

namespace MyCourses
{
    class AcademicYear
    {
        private int startYear;
        private int endYear;
        public AcademicYear(int startYear)
        {
            this.startYear = startYear;
            this.endYear = startYear +1;
        }
        /*
        public AcademicYear(int endYear)
        {
            this.startYear = endYear - 1;
            this.endYear = endYear;
        }
         */
        public override string ToString()
        {
            return startYear.ToString() + "-" + endYear.ToString();
        }
        public override bool Equals(Object obj)
        {
            AcademicYear acd = (AcademicYear)obj;
            if (this.endYear == acd.endYear && this.startYear == acd.startYear)
                return true;
            else
                return false;
        }
    }
}
