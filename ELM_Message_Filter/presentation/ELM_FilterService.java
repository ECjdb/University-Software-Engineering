package presentation;

import business.CheckMsgHeader;
import business.Email_SIR;
import business.ListBuilder;
import business.Message_Factory;
import business.SIR;
import database.ListStorage;
import database.Reader_Writer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;

//40430615
//Joel Degner-Budd
//Software Engineering

public class ELM_FilterService 
{

	protected Shell shlElmFilterService;
	private Text msg_header;
	private Text msg_body;
	private Text outputSMS_P;
	private Text outputSMS_B;
	private Text outputSMS_H;
	private Text outputEmail_H;
	private Text outputEmail_Send;
	private Text outputEmail_Sub;
	private Text outputEmail_B;
	private Text outputTweet_H;
	private Text outputTweet_Send;
	private Text outputTweet_B;
	private Text SIR_H;
	private Text SIR_Send;
	private Text SIR_B;
	private Text SIR_sub;
	private Text SIR_center;
	private Text SIR_I;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) 
	{
		try 
		{
			ELM_FilterService window = new ELM_FilterService();
			window.open();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() 
	{
		Display display = Display.getDefault();
		createContents();
		shlElmFilterService.open();
		shlElmFilterService.layout();
		while (!shlElmFilterService.isDisposed()) 
		{
			if (!display.readAndDispatch()) 
			{
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() 
	{
		shlElmFilterService = new Shell();
		shlElmFilterService.setSize(1138, 612);
		shlElmFilterService.setText("ELM Filter Service");
		shlElmFilterService.setLayout(null);
		
		Label lblWelcomeToE = new Label(shlElmFilterService, SWT.NONE);
		lblWelcomeToE.setBounds(374, 0, 249, 15);
		lblWelcomeToE.setText("Welcome to Euston Leisure Messaging Service");
		
		Label lblErr = new Label(shlElmFilterService, SWT.NONE);
		lblErr.setBounds(10, 31, 338, 15);
		
		Combo sirDropList = new Combo(shlElmFilterService, SWT.NONE);
		sirDropList.setItems(new String[] {"Theft of Properties", 
												"Staff Attack", 
												"Device Damage", 
												"Raid", 
												"Customer Attack", 
												"Staff Abuse", 
												"Bomb Threat", 
												"Terrorism", 
												"Suspicious Incident", 
												"Sport Injury", 
												"Personal Info Leak"});
		sirDropList.setBounds(361, 90, 142, 23);
		sirDropList.setText("Select Incident Nature");
		sirDropList.setVisible(false);
		
		//load files at programme start
		ListStorage LS = ListStorage.getListStorageInstance();
		Reader_Writer RW = Reader_Writer.getRWInstance();
		LS.loadFiles();
		RW.readExcelFile();
		
		Button btnMessage = new Button(shlElmFilterService, SWT.NONE);
		btnMessage.addSelectionListener(new SelectionAdapter() 
		{
			public void widgetSelected(SelectionEvent e) 
			{
				
				CheckMsgHeader checkMsgSET = new CheckMsgHeader();//create instance of message checker
				String val = "";//string used to check if message is valid
				val = checkMsgSET.checkMessage(msg_header.getText(), msg_body.getText());//pass inputs to CheckMsgHeader for system to check
				
				//if the message was successfully validated, system returns string as "successful"
				if(val.equalsIgnoreCase("successful"))
				{
					lblErr.setText(val);
					//if SMS message display content in SMS textbox
					if(msg_header.getText().contains("S"))
					{
						outputSMS_H.setText(ListStorage.getListStorageInstance().getSms().getMsgHeader());
						outputSMS_P.setText(ListStorage.getListStorageInstance().getSms().getSmsSender());
						outputSMS_B.setText(ListStorage.getListStorageInstance().getSms().getMsgBody());
					}
					//if Email message display content in Email textbox
					else if(msg_header.getText().contains("E"))
					{
						outputEmail_H.setText(ListStorage.getListStorageInstance().getEmail().getMsgHeader());
						outputEmail_Send.setText(ListStorage.getListStorageInstance().getEmail().getSender());
						outputEmail_Sub.setText(ListStorage.getListStorageInstance().getEmail().getSubject());
						outputEmail_B.setText(ListStorage.getListStorageInstance().getEmail().getMsgBody());
					}
					//if Tweet message display content in Tweet textbox
					else if(msg_header.getText().contains("T"))
					{
						outputTweet_H.setText(ListStorage.getListStorageInstance().getTweet().getMsgHeader());
						outputTweet_Send.setText(ListStorage.getListStorageInstance().getTweet().getTwitterID());
						outputTweet_B.setText(ListStorage.getListStorageInstance().getTweet().getMsgBody());
					}
				}
				//if system returns string as SIR then is an SIR emergency
				else if(val.equalsIgnoreCase("SIR"))
				{
					lblErr.setText("SIR EMERGENCY: Please select nature of emergency");//set label to emergency message
					
					SelectionListener actionListener = new SelectionListener() 
					{
						@Override
						public void widgetDefaultSelected(SelectionEvent arg0) 
						{
					        System.out.println("selecting default");
						}
						@Override
						public void widgetSelected(SelectionEvent arg0) 
						{
							//process for retrieving type of incident from user. Is then passed to current object for storage
							String sirItem = sirDropList.getItem(sirDropList.getSelectionIndex());//get incident user selection
							SIR sirObj = new SIR(Message_Factory.getMessage_FactoryInstance().getCentreCode(), sirItem);//create sir object for SIR list
							ListStorage.getListStorageInstance().getSirList().add(sirObj.toString());//add new sir object to sirList
							ListStorage.getListStorageInstance().getSir().setIncident(sirItem);//set incident in SIR object
							ListStorage.getListStorageInstance().getSir().setMsgHead(msg_header.getText());//This line of code is a fix for a bug. When building object header would return null
							
							//display SIR email information
							SIR_H.setText(ListStorage.getListStorageInstance().getSir().getMsgHead());
							SIR_Send.setText(ListStorage.getListStorageInstance().getSir().getSender());
							SIR_B.setText(ListStorage.getListStorageInstance().getSir().getMsgBody());
							SIR_sub.setText(ListStorage.getListStorageInstance().getSir().getSirSubject());
							SIR_center.setText(ListStorage.getListStorageInstance().getSir().getSportsCenterCode());
							SIR_I.setText(ListStorage.getListStorageInstance().getSir().getIncident());
						}
					 };
					 
					sirDropList.addSelectionListener(actionListener);
					sirDropList.setVisible(true);
				}
				else
				{
					lblErr.setText(val);
				}
			}
		});
		
		
		btnMessage.setBounds(52, 417, 75, 25);
		btnMessage.setText("Send");
		
		//End session
		Button endBtn = new Button(shlElmFilterService, SWT.NONE);
		endBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				shlElmFilterService.dispose();//closes session
				ELM_MessageDisplay EMD = new ELM_MessageDisplay();
				EMD.msgDisplay();//opens list window
			}
		});
		
		endBtn.setText("End");
		endBtn.setBounds(230, 417, 75, 25);
		msg_header = new Text(shlElmFilterService, SWT.BORDER);
		msg_header.setBounds(10, 49, 338, 25);
		
		msg_body = new Text(shlElmFilterService, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		msg_body.setBounds(10, 90, 338, 308);
		
		outputSMS_P = new Text(shlElmFilterService, SWT.BORDER);
		outputSMS_P.setBounds(526, 131, 171, 25);
		
		outputSMS_B = new Text(shlElmFilterService, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		outputSMS_B.setBounds(526, 161, 171, 155);
		
		Label lblMessageInput = new Label(shlElmFilterService, SWT.NONE);
		lblMessageInput.setBounds(128, 10, 85, 15);
		lblMessageInput.setText("Message Input");
		
		Label lblMessageOutput = new Label(shlElmFilterService, SWT.NONE);
		lblMessageOutput.setText("Email output");
		lblMessageOutput.setBounds(759, 28, 75, 15);
		
		outputSMS_H = new Text(shlElmFilterService, SWT.BORDER);
		outputSMS_H.setBounds(526, 90, 171, 25);
		
		outputEmail_H = new Text(shlElmFilterService, SWT.BORDER);
		outputEmail_H.setBounds(716, 49, 171, 25);
		
		outputEmail_Sub = new Text(shlElmFilterService, SWT.BORDER);
		outputEmail_Sub.setBounds(716, 131, 171, 25);
		
		outputEmail_B = new Text(shlElmFilterService, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		outputEmail_B.setBounds(716, 161, 171, 155);
		
		SIR_H = new Text(shlElmFilterService, SWT.BORDER);
		SIR_H.setBounds(526, 397, 171, 25);
		
		SIR_Send = new Text(shlElmFilterService, SWT.BORDER);
		SIR_Send.setBounds(526, 428, 171, 25);
		
		SIR_B = new Text(shlElmFilterService, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		SIR_B.setBounds(716, 397, 164, 150);
		
		outputTweet_H = new Text(shlElmFilterService, SWT.BORDER);
		outputTweet_H.setBounds(912, 90, 171, 25);
		
		outputTweet_Send = new Text(shlElmFilterService, SWT.BORDER);
		outputTweet_Send.setBounds(912, 131, 171, 25);
		
		outputTweet_B = new Text(shlElmFilterService, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		outputTweet_B.setBounds(912, 161, 171, 155);
		
		outputEmail_Send = new Text(shlElmFilterService, SWT.BORDER);
		outputEmail_Send.setBounds(716, 90, 171, 25);
		
		SIR_sub = new Text(shlElmFilterService, SWT.BORDER);
		SIR_sub.setBounds(526, 459, 171, 25);
		
		SIR_center = new Text(shlElmFilterService, SWT.BORDER);
		SIR_center.setBounds(526, 490, 171, 25);
		
		SIR_I = new Text(shlElmFilterService, SWT.BORDER);
		SIR_I.setBounds(526, 521, 171, 25);
		
		Label lblSmsOutput = new Label(shlElmFilterService, SWT.NONE);
		lblSmsOutput.setText("SMS output");
		lblSmsOutput.setBounds(579, 59, 64, 15);
		
		Label lblTweetOutput = new Label(shlElmFilterService, SWT.NONE);
		lblTweetOutput.setText("Tweet output");
		lblTweetOutput.setBounds(955, 59, 75, 15);
		
		Label lblSirOutput = new Label(shlElmFilterService, SWT.NONE);
		lblSirOutput.setText("SIR output");
		lblSirOutput.setBounds(579, 371, 64, 15);

	}
}