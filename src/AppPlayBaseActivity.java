/**
 * Created by Administrator on 2018/9/5 at 10:50.
 */

public class AppPlayBaseActivity extends GameBaseActivity{
  public static void main(String[] args) {
    GameBaseActivity appPlayBaseActivity = new GameBaseActivity();
    GameBaseActivity.StartOnlineShare("jsonstr", "imgpath", "url");
  }

  @Override
  protected void _FacebookShare(String jsonstr, String imgpath, String url){
    System.out.println("AppPlayBaseActivity._FacebookShare(): jsonstr = " + jsonstr);
    System.out.println("AppPlayBaseActivity._FacebookShare(): imgpath = " + imgpath);
    System.out.println("AppPlayBaseActivity._FacebookShare(): url = " + url);
    jsonstr.intern();
  }
}
