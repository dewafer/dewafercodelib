using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
//using MyCourses;
using System.Text.RegularExpressions;

namespace FuckYouToDeath
{
    public partial class MainForm
    {
        private void login_event()
        {
            try
            {
                ctrl.SetElementAttrib("TextBox1", "value", this.IdTextBox.Text);
                ctrl.SetElementAttrib("TextBox2", "value", this.pwdTextBox.Text);
                ctrl.WaitAMoment();
                ctrl.TriggerElementMethod("Button1", "click");
                //ctrl.DocComplete += new WebBrowserController.Controller.RunAfterDocComplete(getPersonalInfo);
            }
            catch (Exception ex)
            {
                errorProcess(ex);
            }
        }

        private void test_event()
        {
            MessageBox.Show("test_event() run!");
        }

        private void click_inquiryScore()
        {
            try
            {
                ctrl.WaitAMoment();
                ctrl.TriggerElementMethod((ctrl.FindLinks("成绩查询"))[0], "click");
            }
            catch (Exception ex)
            {
                errorProcess(ex);
            }
        }

        private void click_inquiryTermScore()
        {
            try
            {
                //ctrl.SetElementAttrib("zhuti", "ddlXN", "value", "2008-2009");
                //ctrl.SetElementAttrib("zhuti", "ddlXQ", "value", "1");
                ctrl.SetElementAttrib("zhuti", "ddlXN", "value", getXueNian());
                ctrl.SetElementAttrib("zhuti", "ddlXQ", "value", getXueQi());
                ctrl.WaitAMoment();
                ctrl.TriggerElementMethod("zhuti", "btn_xq", "click");
            }
            catch (Exception ex)
            {
                errorProcess(ex);
            }
        }

        private string getXueNian()
        {
            DateTime now = DateTime.Now;
            StringBuilder sb = new StringBuilder();
            if (now.Month < 9)
            {
                sb.Append(now.Year - 1);
                sb.Append("-");
                sb.Append(now.Year);
            }
            else
            {
                sb.Append(now.Year);
                sb.Append("-");
                sb.Append(now.Year + 1);
            }
            return sb.ToString();
        }

        private string getXueQi()
        {
            DateTime now = DateTime.Now;
            if (now.Month < 3)
            {
                return "1";
            }
            else if (now.Month < 10)
            {
                return "2";
            }
            else
            {
                return "1";
            }
        }

        private void errorProcess(Exception ex)
        {
            MessageBox.Show("发生错误，无法完成操作，错误信息：\n\n" + ex.Message,
                "错误告知",MessageBoxButtons.OK,MessageBoxIcon.Error);
            sr.Stop();
            ctrl.ClearEvent();

        }

        private void userCancel()
        {
            errorProcess(new Exception("用户取消"));
        }

        private void finish_event()
        {
            WaitForm.GetWaitForm().Close();
        }

        private void finish_event(object sender, object args)
        {
            finish_event();
        }
        /*
        /// <summary>
        /// No use now
        /// </summary>
        private void getPersonalInfo()
        {
            try
            {
                PersonalInfo p = PersonalInfo.GetPersonalInfo();
                string[] strs = ctrl.GetElementInnerText("xhxm").Split(' ');
                p.Id = strs[0];
                p.Name = strs[1].Substring(0, strs[1].Length - 2);
                p.Password = this.pwdTextBox.Text;
                MessageBox.Show(p.ToString());
            }
            catch (Exception ex)
            {
                MessageBox.Show("发生错误，无法完成操作，错误信息：\n\n" + ex.Message);
            }
            finally
            {
                ctrl.DocComplete -= getPersonalInfo;
            }

        }
         */
    }
}
