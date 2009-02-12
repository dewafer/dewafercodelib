using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.IO;
using ScriptRunner;

namespace FuckYouToDeath
{
    public partial class ScriptsManager : Form
    {
        private List<ScriptInformation> si_list_all = new List<ScriptInformation>();
        private List<ScriptInformation> si_list_enabled = new List<ScriptInformation>();
        private Runner sr;

        public ScriptInformation[] ScriptInformationList
        {
            get { return si_list_enabled.ToArray(); }
        }

        public ScriptsManager()
        {
            InitializeComponent();
            
            ColumnHeader[] headers = new ColumnHeader[9];

            headers[0] = new ColumnHeader();
            headers[0].Name = "脚本名";
            headers[0].Text = "脚本名";

            headers[1] = new ColumnHeader();
            headers[1].Name = "作者";
            headers[1].Text = "作者";

            headers[2] = new ColumnHeader();
            headers[2].Name = "说明";
            headers[2].Text = "说明";

            headers[3] = new ColumnHeader();
            headers[3].Name = "版本";
            headers[3].Text = "版本";

            headers[4] = new ColumnHeader();
            headers[4].Name = "日期";
            headers[4].Text = "日期";

            headers[5] = new ColumnHeader();
            headers[5].Name = "图标";
            headers[5].Text = "图标";

            headers[6] = new ColumnHeader();
            headers[6].Name = "文件名";
            headers[6].Text = "文件名";

            headers[7] = new ColumnHeader();
            headers[7].Name = "文件路径";
            headers[7].Text = "文件路径";

            headers[8] = new ColumnHeader();
            headers[8].Name = "已启用";
            headers[8].Text = "已启用";

            this.listView1.Columns.AddRange(headers);
        }

        public void LoadScripts(string path,Runner sr)
        {
            this.sr = sr;
            si_list_all.Clear();
            si_list_enabled.Clear();
            string[] files = Directory.GetFiles(path, "*.txt");
            foreach (string file in files)
            {
                ScriptInformation si = null;
                try
                {
                    si = sr.GetScriptInformation(file, Properties.Settings.Default.LOAD_CHK_SPT_FILE);
                }
                catch (Exception)
                { }
                finally
                {
                    if (si != null)
                    {
                        si_list_all.Add(si);
                        if (si.Enabled)
                        {
                            si_list_enabled.Add(si);
                        }
                    }
                }
            }
        }

        private void ScriptsManager_Load(object sender, EventArgs e)
        {
            /*
            LoadScripts(@"G:\Desktop\scripts",
                new Runner(@"G:\Desktop\AutoWebSurfing\InstructionRealization\instructionList.txt",new WebBrowser())
                );
             */
            LoadScripts(Properties.Settings.Default.DEF_SCP_DIR, sr);
            refilllistview();
        }

        private void refilllistview()
        {
            this.listView1.Items.Clear();
            this.listView1.BeginUpdate();
            foreach (ScriptInformation si in si_list_all)
            {
                ListViewItem lvi = new ListViewItem();
                ListViewItem.ListViewSubItem[] subItems = new ListViewItem.ListViewSubItem[8];

                lvi.Name = "脚本名";
                lvi.Tag = si;
                lvi.Text = si.Name;

                subItems[0] = new ListViewItem.ListViewSubItem();
                subItems[0].Name = "作者名";
                subItems[0].Text = si.Author;

                subItems[1] = new ListViewItem.ListViewSubItem();
                subItems[1].Name = "说明";
                subItems[1].Text = si.Description;

                subItems[2] = new ListViewItem.ListViewSubItem();
                subItems[2].Name = "版本";
                subItems[2].Text = si.Version;

                subItems[3] = new ListViewItem.ListViewSubItem();
                subItems[3].Name = "日期";
                subItems[3].Text = si.Date;

                subItems[4] = new ListViewItem.ListViewSubItem();
                subItems[4].Name = "图标";
                subItems[4].Text = si.Icon;

                subItems[5] = new ListViewItem.ListViewSubItem();
                subItems[5].Name = "文件名";
                subItems[5].Text = si.FileName;

                subItems[6] = new ListViewItem.ListViewSubItem();
                subItems[6].Name = "文件路径";
                subItems[6].Text = si.FileFullName;

                subItems[7] = new ListViewItem.ListViewSubItem();
                subItems[7].Name = "已启用";
                //subItems[7].Text = si.Enabled.ToString();
                subItems[7].Text = si_list_enabled.Contains(si).ToString();

                lvi.SubItems.AddRange(subItems);

                this.listView1.Items.Add(lvi);
                //this.listView1.Refresh();
            }
            this.listView1.EndUpdate();
            this.toolStripStatusLabel1.Text = "总 " + si_list_all.Count + " 个脚本 / 已启用 " + si_list_enabled.Count + " 个";
        }

        private void closeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.Hide();
        }

        private void reflashToolStripMenuItem_Click(object sender, EventArgs e)
        {
            refilllistview();
        }

        private void reloadToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ScriptsManager_Load(sender, e);
        }

        private void largeIconViewToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.listView1.View = View.LargeIcon;
        }

        private void detialsViewToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.listView1.View = View.Details;
        }

        private void smallIconViewToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.listView1.View = View.SmallIcon;
        }

        private void listViewToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.listView1.View = View.List;
        }

        private void tileViewToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.listView1.View = View.Tile;
        }

        private void listView1_ItemActivate(object sender, EventArgs e)
        {
            foreach (ListViewItem selected in this.listView1.SelectedItems)
            {
                System.Diagnostics.Process.Start(selected.SubItems["文件路径"].Text);
            }
        }

        private void openToolStripMenuItem_Click(object sender, EventArgs e)
        {
            listView1_ItemActivate(sender, e);
        }

        private void listView1_ItemSelectionChanged(object sender, ListViewItemSelectionChangedEventArgs e)
        {
            bool shouldEnabled = this.listView1.SelectedItems.Count > 0;
            this.openToolStripMenuItem.Enabled = shouldEnabled;
            this.deleteToolStripMenuItem.Enabled = shouldEnabled;
            this.forceEnabledToolStripMenuItem.Enabled = shouldEnabled;
            this.ForceDisableToolStripMenuItem.Enabled = shouldEnabled;
            this.selectionToolStripMenuItem.Enabled = shouldEnabled;
        }

        private void reflashToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            reflashToolStripMenuItem_Click(sender, e);
        }

        private void reloadToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            reloadToolStripMenuItem_Click(sender, e);
        }

        private void ForceDisableToolStripMenuItem_Click(object sender, EventArgs e)
        {
            foreach (ListViewItem lv in this.listView1.SelectedItems)
            {
                ScriptInformation si = (ScriptInformation)lv.Tag;
                si_list_enabled.Remove(si);
            }
            refilllistview();
        }

        private void forceEnabledToolStripMenuItem_Click(object sender, EventArgs e)
        {
            foreach (ListViewItem lv in this.listView1.SelectedItems)
            {
                ScriptInformation si = (ScriptInformation)lv.Tag;
                if (!si_list_enabled.Contains(si))
                {
                    si_list_enabled.Add(si);
                }
            }
            refilllistview();
        }

        private void deleteToolStripMenuItem_Click(object sender, EventArgs e)
        {
            DialogResult dr = MessageBox.Show("确认删除这些脚本吗？\n\r注意！删除后将不可恢复！","注意",
                MessageBoxButtons.YesNo,MessageBoxIcon.Warning,MessageBoxDefaultButton.Button2);
            if (dr == DialogResult.Yes)
            {
                foreach (ListViewItem lv in this.listView1.SelectedItems)
                {
                    File.Delete(lv.SubItems["文件路径"].Text);
                }
                ScriptsManager_Load(sender, e);
            }
        }

        private void loadChkSptFileToolStripMenuItem_CheckedChanged(object sender, EventArgs e)
        {
            Properties.Settings.Default.LOAD_CHK_SPT_FILE = this.loadChkSptFileToolStripMenuItem.Checked;
            Properties.Settings.Default.Save();
        }

        private void setDefDirToolStripMenuItem_Click(object sender, EventArgs e)
        {
            DialogResult dr = this.folderBrowserDialog1.ShowDialog();
            if (dr == DialogResult.OK)
            {
                Properties.Settings.Default.DEF_SCP_DIR = this.folderBrowserDialog1.SelectedPath;
                Properties.Settings.Default.Save();
            }
        }

        private void ScriptsManager_FormClosing(object sender, FormClosingEventArgs e)
        {
            e.Cancel = true;
            this.Hide();
        }


    }
}
