package radioJk;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(description = "RadioLib", urlPatterns = { "/QualeoMambo" , "/QualeoMambo.do"}, initParams = {@WebInitParam(name="id",value="1"),@WebInitParam(name="name",value="marcelino")})
public class RadioLibrary extends HttpServlet {
	
	public ArrayList <Music> radioLib = new ArrayList<Music>();
	public Music actualMusic = null;
	
    public RadioLibrary() {
        super();

        if(this.radioLib.isEmpty()){
        	radioLib.add(new Music("Regula- + uma vez", "WWR0TBNFRRQ",0,0,0,15));
         	radioLib.add(new Music("Summer - Calvin Harris", "jj2JyGwbeQ4",0,0,0,15));
    		ActualSongRoutine.start();
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		Music newSong = null;
	    String newTitle = request.getParameter("newTitle");
	    String newLink = request.getParameter("newLink");
	    String initMin = request.getParameter("newInitMin");
	    String initSec = request.getParameter("newInitSec");
	    String finalMin = request.getParameter("newFinalMin");
	    String finalSec = request.getParameter("newFinalSec");
	    
	    if(newTitle != null && newLink != null && initMin != null && initSec != null && finalMin != null && finalSec != null){
	 			newSong = new Music(newTitle, newLink, Integer.parseInt(initMin),  Integer.parseInt(initSec), Integer.parseInt(finalMin), Integer.parseInt(finalSec));
	 			radioLib.add(newSong);
	 			System.out.println("NEW SONG ADDED TO PLAYLIST ->Title: " + newTitle +" ->Url: " + newLink +" ->Start: " + initMin +":"+ initSec + " ->End: " + finalMin + ":" + finalSec);
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
	
}
