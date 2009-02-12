using System;
using System.Collections.Generic;
using System.Collections;
using System.Text;

namespace MyCourses
{
    class CoursesCollection : CollectionBase
    {
        public Course this[int index]
        {
            get
            {
                return ((Course)List[index]);
            }
            set
            {
                if(!List.Contains(value))
                    List[index] = value;
            }
        }

        public int Add(Course value)
        {
            if (!List.Contains(value))
                return (List.Add(value));
            else
                return -1;
        }

        public int IndexOf(Course value)
        {
            return (List.IndexOf(value));
        }

        public void Insert(int index, Course value)
        {
            if(!List.Contains(value))
                List.Insert(index, value);
        }

        public void Remove(Course value)
        {
            List.Remove(value);
        }

        public bool Contains(Course value)
        {
            return (List.Contains(value));
        }
    }
}
