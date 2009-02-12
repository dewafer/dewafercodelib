using System;
using System.Collections.Generic;
using System.Text;
using System.Text.RegularExpressions;
using System.Windows.Forms;
using System.Threading;

namespace WebBrowserController
{
    public class Controller
    {
        public delegate void RunAfterDocComplete();
        public delegate void EventQueueFinishedRun();


        private EventQueueFinishedRun finallyRun = null;
        private Queue<RunAfterDocComplete> queue = new Queue<RunAfterDocComplete>();
        private Queue<RunAfterDocComplete[]> multiTaskQueue = new Queue<RunAfterDocComplete[]>();

        private bool cancelingFlag = false;

        public bool Canceling
        {
            get { return cancelingFlag; }
            set { cancelingFlag = value; }
        }
        public enum MatchOption
        {
            MatchWithInnerText,MatchWithInnerHtml
        }

        private WebBrowser web = null;

        public Controller() {
            web = new WebBrowser();
            initWebBrowser();
        }

        public Controller(WebBrowser webBrowserToUse)
        {
            web = webBrowserToUse;
            initWebBrowser();
        }

        private void initWebBrowser()
        {
            if (web == null)
                return;
            web.DocumentCompleted += new WebBrowserDocumentCompletedEventHandler(web_DocumentCompleted);
        }

        private void web_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            //TODO: documentCompleted event here
            if (cancelingFlag)
            {
                ClearEvent();
                return;
            }
            if (queue.Count > 0)
            {
                RunAfterDocComplete run = queue.Dequeue();
                run();
            }
            if(multiTaskQueue.Count > 0)
            {
                RunAfterDocComplete[] runs = multiTaskQueue.Dequeue();
                foreach (RunAfterDocComplete task in runs)
                {
                    task();
                }
            }
            if (multiTaskQueue.Count < 1 && queue.Count < 1 && finallyRun!= null)
            {
                finallyRun();
            }
        }

        public void PushEvent(RunAfterDocComplete run)
        {
            queue.Enqueue(run);
        }
        public void PushEvent(RunAfterDocComplete[] multiRuns)
        {
            multiTaskQueue.Enqueue(multiRuns);
        }

        public void SetFinallyRun(EventQueueFinishedRun finallyRun)
        {
            this.finallyRun = finallyRun;
        }

        public void ClearEvent()
        {
            queue.Clear();
            multiTaskQueue.Clear();
        }

        public void Navigate(string str)
        {
            web.Navigate(str);
        }

        public WebBrowser GetWebBrowser()
        {
            return web;
        }

        public void SetElementAttrib(string elementId, string attrib, string vale)
        {
            HtmlElement e = web.Document.GetElementById(elementId);
            SetElementAttrib(e, attrib, vale);
        }

        public void SetElementAttrib(string frameId,string elementId, string attrib, string value)
        {
            HtmlElement e = web.Document.Window.Frames[frameId].Document.GetElementById(elementId);
            SetElementAttrib(e, attrib, value);
        }

        public void SetElementAttrib(HtmlElement element, string attrib, string value)
        {
            element.SetAttribute(attrib, value);
        }

        public string GetElementAttrib(string elementId, string attrib)
        {
            HtmlElement e = web.Document.GetElementById(elementId);
            return GetElementAttrib(e, attrib);
        }
        
        public string GetElementAttrib(string frameId, string elementId, string attrib)
        {
            HtmlElement e = web.Document.Window.Frames[frameId].Document.GetElementById(elementId);
            return GetElementAttrib(e, attrib);
        }
        
        public string GetElementAttrib(HtmlElement element, string attrib)
        {
            return element.GetAttribute(attrib);
        }

        public object TriggerElementMethod(HtmlElement element, string method, object[] parms)
        {
            return element.InvokeMember(method, parms);
        }

        public object TriggerElementMethod(string elementId, string method, object[] parms)
        {
            HtmlElement element = web.Document.GetElementById(elementId);
            return TriggerElementMethod(element, method, parms);
        }

        public object TriggerElementMethod(HtmlElement element, string method)
        {
            return element.InvokeMember(method);
        }

        public object TriggerElementMethod(string elementId, string method )
        {
            HtmlElement element = web.Document.GetElementById(elementId);
            return TriggerElementMethod(element, method);
        }

        public object TriggerElementMethod(string frameId,string elementId, string method)
        {
            HtmlElement element = web.Document.Window.Frames[frameId].Document.GetElementById(elementId);
            return TriggerElementMethod(element, method);
        }

        private HtmlElement[] find(Regex regExp,HtmlElementCollection range)
        {
            return find(regExp,range,MatchOption.MatchWithInnerText);
        }

        private HtmlElement[] find(Regex regExp,HtmlElementCollection range,MatchOption option)
        {
            List<HtmlElement> elements = new List<HtmlElement>();
            foreach(HtmlElement e in range)
            {
                string input = "";
                switch(option)
                {
                    case MatchOption.MatchWithInnerHtml:
                        //input = e.InnerHtml;
                        input = (e.InnerHtml == null) ? "" : e.InnerHtml;
                        break;
                    case MatchOption.MatchWithInnerText:
                        //input = e.InnerText;
                        input = (e.InnerText == null) ? "" : e.InnerText;
                        break;
                }
                if(regExp.IsMatch(input))
                {
                    elements.Add(e);
                }
            }
            return elements.ToArray();

        }

        public HtmlElement[] FindElements(string regExp)
        {
            Regex reg = new Regex(regExp);
            return find(reg,web.Document.All);
        }

        public HtmlElement[] FindLinks(string regExp)
        {
            Regex reg = new Regex(regExp);
            return find(reg,web.Document.Links);

        }

        public HtmlElement[] FindLinks(string frameId, string regExp)
        {
            Regex reg = new Regex(regExp);
            return find(reg, web.Document.Window.Frames[frameId].Document.Links);
        }


        public string GetElementInnerText(string frameId,string elementId)
        {
            HtmlElement e = web.Document.Window.Frames[frameId].Document.GetElementById(elementId);
            return GetElementInnerText(e);
        }

        public string GetElementInnerText(string elementId)
        {
            HtmlElement e = web.Document.GetElementById(elementId);
            return GetElementInnerText(e);
        }

        public string GetElementInnerText(HtmlElement element)
        {
            return element.InnerText;
        }

        /*
        public void WaitForDocumentComplete() 
        {
            while (web.ReadyState != WebBrowserReadyState.Complete)
            {
                Console.WriteLine(web.ReadyState.ToString());
                //WaitAMoment();
            }
        }
         */
        public void WaitAMoment()
        {
            System.Threading.Thread.Sleep(2500);
            //System.Threading.Thread.SpinWait(1000);
        }
        public void WaitAMoment(int howLong)
        {
            System.Threading.Thread.Sleep(howLong);
            //System.Threading.Thread.SpinWait(1000);
        }


    }
}
