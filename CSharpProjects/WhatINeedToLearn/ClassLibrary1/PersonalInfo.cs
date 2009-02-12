using System;
using System.Collections.Generic;
using System.Text;

namespace MyCourses
{
    public class PersonalInfo
    {
        private static PersonalInfo pi = null;
        private PersonalInfo() { }

        public static PersonalInfo GetPersonalInfo()
        {
            if (pi == null)
                pi = new PersonalInfo();
            return pi;
        }

        private string _id;

        public string Id
        {
            get { return _id; }
            set { _id = value; }
        }
        private string _pwd;

        public string Password
        {
            get { return _pwd; }
            set { _pwd = value; }
        }

        private string _name;

        public string Name
        {
            get { return _name; }
            set { _name = value; }
        }

        public override string ToString()
        {
            return "Name:" + _name + " Id:" + _id + " pwd:" + _pwd;
        }

    }
}
