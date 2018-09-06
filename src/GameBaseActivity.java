/**
 * Created by Administrator on 2018/9/5 at 10:49.
 */

public class GameBaseActivity {
  public static GameBaseActivity msGameBaseActivity;
  GameBaseActivity(){
    msGameBaseActivity = this;
  }
  public static void StartOnlineShare(String jsonStr, String imgpath, String url){
    msGameBaseActivity._StartOnlineShare(imgpath, url);
    msGameBaseActivity._FacebookShare(jsonStr, imgpath, url);
  }
  void _StartOnlineShare(String imgpath, String url){

  }
  protected void _FacebookShare(String jsonstr, String imgpath, String url){
    System.out.println("GameBaseActivity._FacebookShare(): jsonstr = " + jsonstr);
    System.out.println("GameBaseActivity._FacebookShare(): imgpath = " + imgpath);
    System.out.println("GameBaseActivity._FacebookShare(): url = " + url);

  }
}
