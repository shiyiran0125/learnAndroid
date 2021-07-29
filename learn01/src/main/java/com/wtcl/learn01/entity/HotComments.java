package com.wtcl.learn01.entity;

/**
 * @project: LearnAndroid
 * @desc: this is ...
 * @author: WTCL
 * @date: 2021/7/8
 */
public class HotComments {

    /**
     * content : 回国之后少见的大气磅礴的歌曲, 不是古风或者是民族风的曲风了, 各种风格都可以驾驭的兰兰, 再一次成功的没有让我们失望！！！这首很好的凸现了阿兰的嗓音, 低音细吟浅唱, 中音铿锵有力, 高音荡气回肠, 完美的演绎出了电影里主人公克服艰难重获新生的过程！非常有画面感了, 听着不禁肃然起敬！
     * songPic : http://p1.music.126.net/1xPlAv-1n8KzM2f_jIVQFw==/109951164499853671.jpg
     * songAutho : 阿兰
     * songName : 冰之翼
     * songId : 1404900754
     * nickname : 守住yi份未来
     * avatar : http://p1.music.126.net/Cl3w1od6BpnJETW0Lpqmfw==/19068830160979425.jpg
     * likedCount : 218
     * time : 1574300985041
     */

    //热评内容
    private String content;
    //音乐封面
    private String songPic;
    //音乐作者
    private String songAutho;
    //音乐名称
    private String songName;
    //音乐ID
    private String songId;
    //用户昵称
    private String nickname;
    //用户头像
    private String avatar;
    //点赞数量
    private String likedCount;
    //发表时间
    private String time;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSongPic() {
        return songPic;
    }

    public void setSongPic(String songPic) {
        this.songPic = songPic;
    }

    public String getSongAutho() {
        return songAutho;
    }

    public void setSongAutho(String songAutho) {
        this.songAutho = songAutho;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(String likedCount) {
        this.likedCount = likedCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
