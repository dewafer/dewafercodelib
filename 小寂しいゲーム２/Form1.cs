using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;

namespace littlesabisiigame2
{
	/// <summary>
	/// Form1 �̊T�v�̐����ł��B
	/// </summary>
	public class Form1 : System.Windows.Forms.Form
	{
		private System.Windows.Forms.Button button1;
		/// <summary>
		/// �K�v�ȃf�U�C�i�ϐ��ł��B
		/// </summary>
		private System.ComponentModel.Container components = null;

		private bool startflag = false;
		private string[] a;
		private string[] b;
		private string[] c;
		private string[] d;
		Random rand = new Random();
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.Button edit;

		private Timer timer = new Timer();

		public Form1()
		{
			//
			// Windows �t�H�[�� �f�U�C�i �T�|�[�g�ɕK�v�ł��B
			//
			InitializeComponent();

			//
			// TODO: InitializeComponent �Ăяo���̌�ɁA�R���X�g���N�^ �R�[�h��ǉ����Ă��������B
			//
			loadfile();

			timer.Interval = 10;
			timer.Enabled = startflag;
			timer.Tick +=new EventHandler(timer_Tick);

		}

		private void loadfile()
		{
			a = FileReader.GetContext("a.txt");
			b = FileReader.GetContext("b.txt");
			c = FileReader.GetContext("c.txt");
			d = FileReader.GetContext("d.txt");

		}

		/// <summary>
		/// �g�p����Ă��郊�\�[�X�Ɍ㏈�������s���܂��B
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if (components != null) 
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Windows �t�H�[�� �f�U�C�i�Ő������ꂽ�R�[�h 
		/// <summary>
		/// �f�U�C�i �T�|�[�g�ɕK�v�ȃ��\�b�h�ł��B���̃��\�b�h�̓��e��
		/// �R�[�h �G�f�B�^�ŕύX���Ȃ��ł��������B
		/// </summary>
		private void InitializeComponent()
		{
			this.button1 = new System.Windows.Forms.Button();
			this.label1 = new System.Windows.Forms.Label();
			this.edit = new System.Windows.Forms.Button();
			this.SuspendLayout();
			// 
			// button1
			// 
			this.button1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
			this.button1.Location = new System.Drawing.Point(536, 248);
			this.button1.Name = "button1";
			this.button1.TabIndex = 0;
			this.button1.Text = "start";
			this.button1.Click += new System.EventHandler(this.button1_Click);
			// 
			// label1
			// 
			this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
				| System.Windows.Forms.AnchorStyles.Left) 
				| System.Windows.Forms.AnchorStyles.Right)));
			this.label1.Font = new System.Drawing.Font("MS UI Gothic", 50F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(128)));
			this.label1.Location = new System.Drawing.Point(8, 8);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(680, 222);
			this.label1.TabIndex = 1;
			this.label1.Text = "�N���ǂ��ŉ�������";
			this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			// 
			// edit
			// 
			this.edit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
			this.edit.Location = new System.Drawing.Point(616, 248);
			this.edit.Name = "edit";
			this.edit.TabIndex = 2;
			this.edit.Text = "editor";
			this.edit.Click += new System.EventHandler(this.edit_Click);
			// 
			// Form1
			// 
			this.AcceptButton = this.button1;
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 12);
			this.ClientSize = new System.Drawing.Size(696, 275);
			this.Controls.Add(this.edit);
			this.Controls.Add(this.label1);
			this.Controls.Add(this.button1);
			this.Name = "Form1";
			this.Text = "�Q�[��";
			this.ResumeLayout(false);

		}
		#endregion

		/// <summary>
		/// �A�v���P�[�V�����̃��C�� �G���g�� �|�C���g�ł��B
		/// </summary>
		[STAThread]
		static void Main() 
		{
			Application.Run(new Form1());
		}

		private void button1_Click(object sender, System.EventArgs e)
		{
			if(startflag)
			{
				stop();
			}
			else
			{
                start();
			}
		}
		private void start()
		{
			startflag = true;
			this.button1.Text = "stop";
			this.timer.Enabled = startflag;
			this.timer.Start();		}
		private void stop()
		{
			startflag = false;
			this.button1.Text = "start";
			this.timer.Enabled = startflag;
			this.timer.Stop();		}

		private void timer_Tick(object sender, EventArgs e)
		{
			string output = string.Empty;
			output += ((0==a.Length)?"NULL":a[rand.Next(a.Length)]) +" �� ";
			output +=((0==b.Length)?"NULL": b[rand.Next(b.Length)]) + " ";
			output +=((0==c.Length)?"NULL": c[rand.Next(c.Length)]) + " ";
			output += ((0==d.Length)?"NULL":d[rand.Next(d.Length)]);
			this.label1.Text = output;
		}

		private void edit_Click(object sender, System.EventArgs e)
		{
			stop();
			new Form2().ShowDialog();
			loadfile();
		}
	}
}
