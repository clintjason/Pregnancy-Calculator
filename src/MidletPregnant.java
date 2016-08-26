/*
 *          Authors: 
 * PRINCEWILL NGWANA ANJE FE13A180
 * VAUMI VAUMI EZECHIEL JOACHIM FE13A203
 * NGASSA YEPGWA CLINT JASON FE13A151
 
 */
import java.io.IOException;
import java.util.TimeZone;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

 class MidletPregnant extends MIDlet implements CommandListener {

    TextBox input = null;  
    //the midlet's display object
    private Display display;

    //flag indicating first call of startApp
    private boolean started;
    private static final int DATE = 0;
    private Image image;  
    int avgPeriod = 28;
    // today date
      private DateField today = new DateField("Current Date:", DateField.DATE, TimeZone.getTimeZone("GMT"));
    //last period
     DateField lastmenses = new DateField("last menses: ", DateField.DATE, TimeZone.getTimeZone("GMT"));
     //form
     Form form = new Form("Pregnancy Calculator");
     
      // command
    static final Command backCommand = new Command("Back", Command.BACK, 0);
    static final Command mainMenuCommand = new Command("Menu", Command.SCREEN, 1);
    static final Command exitCommand = new Command("Exit", Command.STOP, 2);
    static final  Command ok= new Command("Ok", Command.OK, 3);
    String currentMenu = null;
     
     public MidletPregnant(){}
        
    public void startApp() {
         if (!started) {
            display = Display.getDisplay(this);
                       
            try{
            image = Image.createImage("/download.jpg");
            }
             catch(IOException e){ throw new RuntimeException("Unable to load Image: "+e); }
            
            form.append(image);
            form.append("Welcome to the Pregnancy \n     Test Calculator");         
            form.append("Please select the day of your last menstrual Period\n");
            form.append(lastmenses);
            form.append("Please enter the current date\n");
            form.append(today);
            form.addCommand(exitCommand);
            form.addCommand(ok);
            form.setCommandListener(this);                            
            display.setCurrent(form);
            started = true;
            }
        }
    
   public String pregnancyCalculator(){
   	// validate that date was written 
   	  if(today.getDate()==null ||lastmenses.getDate()==null && (today.getDate().getTime() - lastmenses.getDate().getTime())<0 )
          {
            return "An error occured please try again";
   	  }
          else{
            if((today.getDate().getTime() - lastmenses.getDate().getTime()) <= avgPeriod*24*60*60*1000)
   	  	 return "you are not pregnant ";
               else{   	  					
                  long interval = (today.getDate().getTime() - lastmenses.getDate().getTime());
                    //get the number of months pregnant
                    long time = 60*60*24*1000;
                    long days = interval/time;
                    long month = days/30;
                    long week = (days%30)/7;
                    long day = (days%30)%7;
                    return "Congratulations you are " + month +" months, " + week + " weeks and " + day +" days pregnant.";	
   	  		}
            } 
	 }
 
    public void pauseApp() {
        display = null;
      form = null;
    }
    
    public void destroyApp(boolean unconditional) {
          notifyDestroyed();
    }
    
     public void testTextBox() {
      //System.out.println("textbox called");
      input = new TextBox("Result:", "", 255, TextField.UNEDITABLE | TextField.ANY);
      input.setTicker(new Ticker("Pregnancy Test results"));
      input.addCommand(backCommand);
      input.setCommandListener(this);
      input.setString(pregnancyCalculator());
      display.setCurrent(input);
      currentMenu = "input";
    }
      public void commandAction(Command cmd, Displayable d) {
      String label = cmd.getLabel();
      if (label.equals("Exit")) {
         destroyApp(true);
      } else if (label.equals("Back") ) {
          if(currentMenu.equals("input") ) {
            // go back to menu
            Menu();
          } 

      } else if (label.equals("Ok") ){
         testTextBox();
         }
            
      }
      
      void Menu() {
      display.setCurrent(form);
      currentMenu = "Menu"; 
    }
   
}    
