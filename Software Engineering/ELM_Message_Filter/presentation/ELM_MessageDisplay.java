package presentation;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import business.Message;
import database.ListStorage;
import database.Reader_Writer;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

//40430615
//Joel Degner-Budd
//Software Engineering

public class ELM_MessageDisplay {

	protected Shell shell;
	private Text msgBox;
	private Text sirBox;
	private Text mentionBox;
	private Text trendBox;
	private String msg = "";
	private String mntn = "";
	private String sir = "";

	/**
	 * Launch the application.
	 * @param args
	 * @wbp.parser.entryPoint
	 */
	public static void msgDisplay() {
		try {
			ELM_MessageDisplay window = new ELM_MessageDisplay();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() 
	{
		ListStorage ls = ListStorage.getListStorageInstance();
		shell = new Shell();
		shell.setSize(1093, 598);
		shell.setText("SWT Application");
		
		msgBox = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		msgBox.setBounds(10, 40, 224, 478);
		
		sirBox = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		sirBox.setBounds(276, 40, 224, 478);
		
		mentionBox = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		mentionBox.setBounds(545, 40, 224, 478);
		
		trendBox = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		trendBox.setText("[DISABLED - NON FUNCTIONAL]");
		trendBox.setBounds(817, 40, 224, 478);
		
		Label lblMessageList = new Label(shell, SWT.NONE);
		lblMessageList.setAlignment(SWT.CENTER);
		lblMessageList.setBounds(10, 10, 224, 15);
		lblMessageList.setText("Message List");
		
		Label lblSirList = new Label(shell, SWT.NONE);
		lblSirList.setText("SIR List");
		lblSirList.setAlignment(SWT.CENTER);
		lblSirList.setBounds(276, 10, 224, 15);
		
		Label lblMentionList = new Label(shell, SWT.NONE);
		lblMentionList.setText("Mention List");
		lblMentionList.setAlignment(SWT.CENTER);
		lblMentionList.setBounds(545, 10, 224, 15);
		
		Label lblTrendingList = new Label(shell, SWT.NONE);
		lblTrendingList.setText("Trending List");
		lblTrendingList.setAlignment(SWT.CENTER);
		lblTrendingList.setBounds(817, 10, 224, 15);
		
		//lists to display
		ArrayList <String> msgLst = ls.getMsgList();
		ArrayList <String> mntnLst = ls.getMentionsList();
		ArrayList <String> sirLst = ls.getSirList();
		
		for(String s : msgLst) 
		{
			msg = msg +"\n"+"\n"+ s.toString();
		}
		for(String x : mntnLst)
		{
			mntn = mntn + "\n"+x.toString();
		}
		for(String y : sirLst)
		{
			sir = sir + "\n"+y.toString();
		}
		
		msgBox.setText(msg);
		mentionBox.setText(mntn);
		sirBox.setText(sir);
		
		//save files at end of session
		ListStorage LS = ListStorage.getListStorageInstance();
		LS.saveFiles();
		
		//create JSON file at end of session
		Reader_Writer RW = Reader_Writer.getRWInstance();
		RW.createJsonFile(msgLst);

	}
}
