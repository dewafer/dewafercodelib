using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace littlesabisiigame2
{
	/// <summary>
	/// Form2 の概要の説明です。
	/// </summary>
	public class Form2 : System.Windows.Forms.Form
	{
		private System.Windows.Forms.TextBox textBox1;
		private System.Windows.Forms.TextBox textBox2;
		private System.Windows.Forms.TextBox textBox3;
		private System.Windows.Forms.TextBox textBox4;
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.Label label2;
		private System.Windows.Forms.Label label3;
		private System.Windows.Forms.Label label4;
		private System.Windows.Forms.Label label5;
		private System.Windows.Forms.Button button1;
		private System.Windows.Forms.Button button2;
		/// <summary>
		/// 必要なデザイナ変数です。
		/// </summary>
		private System.ComponentModel.Container components = null;

		private string[] a;
		private string[] b;
		private string[] c;
		private string[] d;
		public Form2()
		{
			//
			// Windows フォーム デザイナ サポートに必要です。
			//
			InitializeComponent();

			//
			// TODO: InitializeComponent 呼び出しの後に、コンストラクタ コードを追加してください。
			//
			loadfile();
		}

		/// <summary>
		/// 使用されているリソースに後処理を実行します。
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if(components != null)
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Windows フォーム デザイナで生成されたコード 
		/// <summary>
		/// デザイナ サポートに必要なメソッドです。このメソッドの内容を
		/// コード エディタで変更しないでください。
		/// </summary>
		private void InitializeComponent()
		{
			this.textBox1 = new System.Windows.Forms.TextBox();
			this.textBox2 = new System.Windows.Forms.TextBox();
			this.textBox3 = new System.Windows.Forms.TextBox();
			this.textBox4 = new System.Windows.Forms.TextBox();
			this.label1 = new System.Windows.Forms.Label();
			this.label2 = new System.Windows.Forms.Label();
			this.label3 = new System.Windows.Forms.Label();
			this.label4 = new System.Windows.Forms.Label();
			this.label5 = new System.Windows.Forms.Label();
			this.button1 = new System.Windows.Forms.Button();
			this.button2 = new System.Windows.Forms.Button();
			this.SuspendLayout();
			// 
			// textBox1
			// 
			this.textBox1.AcceptsReturn = true;
			this.textBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)));
			this.textBox1.Location = new System.Drawing.Point(8, 32);
			this.textBox1.Multiline = true;
			this.textBox1.Name = "textBox1";
			this.textBox1.ScrollBars = System.Windows.Forms.ScrollBars.Both;
			this.textBox1.Size = new System.Drawing.Size(200, 120);
			this.textBox1.TabIndex = 0;
			this.textBox1.Text = "";
			// 
			// textBox2
			// 
			this.textBox2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)));
			this.textBox2.Location = new System.Drawing.Point(256, 32);
			this.textBox2.Multiline = true;
			this.textBox2.Name = "textBox2";
			this.textBox2.ScrollBars = System.Windows.Forms.ScrollBars.Both;
			this.textBox2.Size = new System.Drawing.Size(184, 120);
			this.textBox2.TabIndex = 1;
			this.textBox2.Text = "";
			// 
			// textBox3
			// 
			this.textBox3.AcceptsReturn = true;
			this.textBox3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)));
			this.textBox3.Location = new System.Drawing.Point(448, 32);
			this.textBox3.Multiline = true;
			this.textBox3.Name = "textBox3";
			this.textBox3.ScrollBars = System.Windows.Forms.ScrollBars.Both;
			this.textBox3.Size = new System.Drawing.Size(120, 120);
			this.textBox3.TabIndex = 2;
			this.textBox3.Text = "";
			// 
			// textBox4
			// 
			this.textBox4.AcceptsReturn = true;
			this.textBox4.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)));
			this.textBox4.Location = new System.Drawing.Point(576, 32);
			this.textBox4.Multiline = true;
			this.textBox4.Name = "textBox4";
			this.textBox4.ScrollBars = System.Windows.Forms.ScrollBars.Both;
			this.textBox4.Size = new System.Drawing.Size(112, 120);
			this.textBox4.TabIndex = 3;
			this.textBox4.Text = "";
			// 
			// label1
			// 
			this.label1.Anchor = System.Windows.Forms.AnchorStyles.Top;
			this.label1.Font = new System.Drawing.Font("MS UI Gothic", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(128)));
			this.label1.Location = new System.Drawing.Point(12, 8);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(32, 23);
			this.label1.TabIndex = 4;
			this.label1.Text = "谁";
			// 
			// label2
			// 
			this.label2.Anchor = System.Windows.Forms.AnchorStyles.None;
			this.label2.Font = new System.Drawing.Font("MS UI Gothic", 18F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(128)));
			this.label2.Location = new System.Drawing.Point(216, 76);
			this.label2.Name = "label2";
			this.label2.Size = new System.Drawing.Size(40, 24);
			this.label2.TabIndex = 5;
			this.label2.Text = "在";
			// 
			// label3
			// 
			this.label3.Anchor = System.Windows.Forms.AnchorStyles.Top;
			this.label3.Font = new System.Drawing.Font("MS UI Gothic", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(128)));
			this.label3.Location = new System.Drawing.Point(260, 8);
			this.label3.Name = "label3";
			this.label3.Size = new System.Drawing.Size(96, 23);
			this.label3.TabIndex = 6;
			this.label3.Text = "地点";
			// 
			// label4
			// 
			this.label4.Anchor = System.Windows.Forms.AnchorStyles.Top;
			this.label4.Font = new System.Drawing.Font("MS UI Gothic", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(128)));
			this.label4.Location = new System.Drawing.Point(452, 8);
			this.label4.Name = "label4";
			this.label4.Size = new System.Drawing.Size(96, 23);
			this.label4.TabIndex = 7;
			this.label4.Text = "干";
			// 
			// label5
			// 
			this.label5.Anchor = System.Windows.Forms.AnchorStyles.Top;
			this.label5.Font = new System.Drawing.Font("MS UI Gothic", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(128)));
			this.label5.Location = new System.Drawing.Point(580, 8);
			this.label5.Name = "label5";
			this.label5.Size = new System.Drawing.Size(96, 23);
			this.label5.TabIndex = 8;
			this.label5.Text = "什么";
			// 
			// button1
			// 
			this.button1.Anchor = System.Windows.Forms.AnchorStyles.Right;
			this.button1.Location = new System.Drawing.Point(696, 60);
			this.button1.Name = "button1";
			this.button1.TabIndex = 9;
			this.button1.Text = "Save";
			this.button1.Click += new System.EventHandler(this.button1_Click);
			// 
			// button2
			// 
			this.button2.Anchor = System.Windows.Forms.AnchorStyles.Right;
			this.button2.DialogResult = System.Windows.Forms.DialogResult.Cancel;
			this.button2.Location = new System.Drawing.Point(696, 92);
			this.button2.Name = "button2";
			this.button2.TabIndex = 10;
			this.button2.Text = "Cancel";
			this.button2.Click += new System.EventHandler(this.button2_Click);
			// 
			// Form2
			// 
			this.AcceptButton = this.button1;
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 12);
			this.CancelButton = this.button2;
			this.ClientSize = new System.Drawing.Size(776, 157);
			this.Controls.Add(this.button2);
			this.Controls.Add(this.button1);
			this.Controls.Add(this.label5);
			this.Controls.Add(this.label4);
			this.Controls.Add(this.label3);
			this.Controls.Add(this.label2);
			this.Controls.Add(this.label1);
			this.Controls.Add(this.textBox4);
			this.Controls.Add(this.textBox3);
			this.Controls.Add(this.textBox2);
			this.Controls.Add(this.textBox1);
			this.MinimumSize = new System.Drawing.Size(784, 184);
			this.Name = "Form2";
			this.Text = "editor";
			this.ResumeLayout(false);

		}
		#endregion

		private void button2_Click(object sender, System.EventArgs e)
		{
			this.Close();
		}

		private void loadfile()
		{
			a = FileReader.GetContext("a.txt");
			b = FileReader.GetContext("b.txt");
			c = FileReader.GetContext("c.txt");
			d = FileReader.GetContext("d.txt");
			
			this.textBox1.Lines = a;
			this.textBox2.Lines = b;
			this.textBox3.Lines = c;
			this.textBox4.Lines = d;
		}

		private void button1_Click(object sender, System.EventArgs e)
		{
			FileReader.SaveContext("a.txt",this.textBox1.Lines);
			FileReader.SaveContext("b.txt",this.textBox2.Lines);
			FileReader.SaveContext("c.txt",this.textBox3.Lines);
			FileReader.SaveContext("d.txt",this.textBox4.Lines);
			this.Close();
		}

	}
}
