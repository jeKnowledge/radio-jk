package radioJk;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(description = "RadioLib", urlPatterns = { "/QualeoMambo" , "/QualeoMambo.do"}, initParams = {@WebInitParam(name="id",value="1"),@WebInitParam(name="name",value="marcelino")})
public class RadioLibrary extends HttpServlet  implements java.io.Serializable {
	
	public ArrayList <Music> radioLib = new ArrayList<Music>();
	public Music actualMusic = null;
	public String fileName = "allSongsBackup";
    public RadioLibrary() {
        super();
        
		try {
				load(fileName);
		} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        if(this.radioLib.isEmpty()){
         	radioLib.add(new Music("NBC-PELA ARTE","8_YBAxkmwgQ",0,0,3,38));
         	try {
				save(fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
        }
        ActualSongRoutine.start();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		Music newSong = null;
	    String newTitle = request.getParameter("newTitle");
	    String newLink = request.getParameter("newLink");
	   /* 
	    String initMin = request.getParameter("newInitMin");
	    String initSec = request.getParameter("newInitSec");
	    
	    Initial Time (Minute): <input type="text" name="newInitMin" />
	<br />
		Initial Time (Seconds): <input type="text" name="newInitSec" />		
	<br />
	    */
	    String finalMin = request.getParameter("newFinalMin");
	    String finalSec = request.getParameter("newFinalSec");
	    
	    if(newTitle != null && newLink != null && finalMin != null && finalSec != null){
	 			newSong = new Music(newTitle, newLink,0,  0, Integer.parseInt(finalMin), Integer.parseInt(finalSec));
	 			radioLib.add(newSong);
	 			System.out.println("NEW SONG ADDED TO PLAYLIST ->Title: " + newTitle +" ->Url: " + newLink + " ->End: " + finalMin + ":" + finalSec);
	 			
	 			try {
					save(fileName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    }
		
		response.setIntHeader("Refresh", actualMusic.getRefreshTime());
		out.println(generateHtmlFromMusic());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	    
	}
	
	
	String generateHtmlFromMusic(){
		String html;
		
		String actualTitle = actualMusic.getTitle();
		String actualUrl = actualMusic.getUrl();
		String listTitles = "";
		String showVideo = "<iframe width=\"560\" height=\"315\" src=\"//www.youtube.com/embed/" + actualUrl +"?autoplay=1\" frameborder=\"0\"></iframe>";
		for(int i = 0; i < radioLib.size(); i++){
    		listTitles =listTitles + "<h5>" + radioLib.get(i).getTitle() + "</h5>";
		}
		
		html = "<html>"+
				"<body>" + 
				"<h2>" + actualTitle + "</h2>" +	
				"<h3>"+ showVideo +"</h3>" +
				"<h3> Playlist Songs: </h3>" + 
				"<p>" +
				listTitles + 
				"</p>" +
				"<form action=\"addNewSong.jsp\">" + 
			    "<input type=\"submit\" value=\"New Song\">" + 
			    "</form>" + 
				"</body>" + 
				"</html>";
		
		return html;
	}
    
	Thread ActualSongRoutine = new Thread() {
	    public void run() {
	    	int  j = 0;
	    	while(j < 10){
	    	for(int i = 0; i < radioLib.size(); i++){
	    		actualMusic = radioLib.get(i);
	    		System.out.println("NOW PLAYING ->" + actualMusic.getTitle() + " ->Refresh after " + actualMusic.getRefreshTime() + " seconds");
				 try {
					 Thread.sleep(actualMusic.getRefreshTime()*1000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
	    	}
	    	j++;
	     }
	    }
	};
	
	void save(String fileName) throws IOException {

		FileOutputStream FOS = new FileOutputStream(fileName);
		ObjectOutputStream OOS = new ObjectOutputStream(FOS);

		for(int i = 0; i < radioLib.size(); i++)
		{
				OOS.writeObject(radioLib.get(i));
		}
		OOS.close();
	}

	void load(String fileName) throws IOException, ClassNotFoundException {

		FileInputStream FIS = new FileInputStream(fileName);
		ObjectInputStream OIS = new ObjectInputStream(FIS);

		while(FIS.available() > 0)
		{
			radioLib.add((Music) OIS.readObject());
		}	
		OIS.close();
	
	
	}
	
}
