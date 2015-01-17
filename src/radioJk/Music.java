package radioJk;

public class Music {
	
	private String title;
	private String url;
	private int startMin;
	private int startSec;
	private int finishMin;
	private int finishSec;
	private int refreshTime;
	
	Music(String title, String url, int startMin, int startSec, int finishMin, int finishSec){
		this.title = title;
		this.url = url;
		this.startMin = startMin;
		this.startSec = startSec;
		this.finishMin = finishMin;
		this.finishSec = finishSec;
		this.refreshTime = calcRefreshTime();
	}
	
	private int calcRefreshTime() {
		int timeInSec = this.finishMin*60 + this.finishSec;
		return timeInSec;
	}

	String getTitle(){	
		return(this.title);
	}
	
	String getUrl(){	
		return(this.url);
	}
	
	int getStartMin(){	
		return(this.startMin);
	}
	
	int getStartSec(){	
		return(this.startSec);
	}
	
	int getFinishMin(){
		return(this.finishMin);
	}
	
	int getFinishSec(){
		return(this.finishSec);
	}
	
	int getRefreshTime(){
		return(this.refreshTime);
	}
	
}


