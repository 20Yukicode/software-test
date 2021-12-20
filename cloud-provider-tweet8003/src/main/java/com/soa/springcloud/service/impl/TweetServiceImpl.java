package com.soa.springcloud.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.Picture;
import com.soa.springcloud.entities.Subscription;
import com.soa.springcloud.entities.Tweet;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.mapper.*;
import com.soa.springcloud.service.LikesService;
import com.soa.springcloud.service.SubscriptionService;
import com.soa.springcloud.service.TweetService;
import com.soa.springcloud.util.PictureUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class TweetServiceImpl implements TweetService{

    @Resource
    private TweetMapper tweetMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private LikesService likesService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private PictureMapper pictureMapper;

    @Resource
    private SubscriptionMapper subscriptionMapper;

    @Resource
    private SubscriptionService subscriptionService;

    private static String localPath;
    private static String webPath;

    @Value("${file.localPath}")
    public void setLocalPath(String localPath) {
        TweetServiceImpl.localPath = localPath;
    }
    @Value("${file.webPath}")
    public void setWebPath(String webPath) {
        TweetServiceImpl.webPath = webPath;
    }

    @Override
    public int addLikesNum(Integer tweetId){

        Tweet tweet = tweetMapper.selectById(tweetId);
        QueryWrapper<Tweet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tweet_id",tweetId);
        if(tweet == null)return -1;

        tweet.setPraiseNum(tweet.getPraiseNum()+1);
        tweetMapper.update(tweet,queryWrapper);
        return tweetMapper.selectById(tweetId).getPraiseNum();
    }
    @Override
    public int subtractLikesNum(Integer tweetId){
        Tweet tweet = tweetMapper.selectById(tweetId);
        QueryWrapper<Tweet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tweet_id",tweetId);
        if(tweet == null)return -1;

        tweet.setPraiseNum(tweet.getPraiseNum()-1);
        tweetMapper.update(tweet,queryWrapper);
        return tweetMapper.selectById(tweetId).getPraiseNum();
    }
    @Override
    public int deleteTweet(Integer tweetId){
        QueryWrapper<Tweet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tweet_id",tweetId);
        return tweetMapper.delete(queryWrapper);
    }

    @Override
    public JSONArray getSelfTweetList(Integer visitorId,Integer intervieweeId, Integer momentId){
        QueryWrapper<Tweet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unified_id",intervieweeId).gt("tweet_id",momentId);
        List tweetList = tweetMapper.selectList(queryWrapper);
        int size = tweetList.size();
        if(size>10) size = 9;
        JSONArray jsonArray = new JSONArray();

        for(int i=0;i<size; i++){
            JSONObject object = JSONUtil.parseObj(tweetList.get(i));
            object.put("like_state", likesService.getLikes(visitorId,object.getInt("tweet_id")));
            jsonArray.add(object);
        }
        return jsonArray;
    }

    @Override
    public JSONArray getTweetList(Integer visitorId, Integer momentId){

        JSONArray tweetArray = new JSONArray();

        QueryWrapper<Subscription> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unified_id",visitorId);
        List subscriptionList = subscriptionMapper.selectList(queryWrapper);


//        if(subscriptionList.size()>=1)
//            tweetArray.add(JSONUtil.parseObj(subscriptionList.get(0)));
//        return tweetArray;
        if(subscriptionList!=null) {
            for (int i = 0; i < subscriptionList.size(); i++) {
                QueryWrapper<Tweet> tweetQueryWrapper = new QueryWrapper<>();
                JSONObject object = JSONUtil.parseObj(subscriptionList.get(i));
                queryWrapper.eq("unified_id", object.getInt("subscribe_id"));
                List list = tweetMapper.selectList(tweetQueryWrapper);
                if(list !=null) {
                    for (int j = 0; j < list.size(); j++) {
                        JSONObject tweetObject = JSONUtil.parseObj(list.get(j));

                        tweetObject.put("like_state", likesService.getLikes(visitorId, object.getInt("tweetId")));
                        tweetObject.put("simpleUserInfo", getSimpleUserInfo(object.getInt("unifiedId")));
                        tweetArray.add(tweetObject);
                    }
                }
            }
        }
        return tweetArray;

    }
    @Override
    public JSONObject getSimpleUserInfo(Integer unifiedId){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId);
        User user = userMapper.selectOne(queryWrapper);
        if(user != null) {
            JSONObject object = new JSONObject();
            object.put("picture_url", user.getPictureUrl());
            object.put("true_name", user.getTrueName());
            object.put("brief_info", user.getBriefInfo());
            object.put("user_type", user.getUserType());
            return object;
        }
        return null;

    }

    @Override
    public List getTweetPictures(Integer tweetId){
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tweet_id",tweetId);
        return pictureMapper.selectList(queryWrapper);

    }

    public int createTweet(Integer unifiedId, String content, Date recordTime, MultipartFile[] files)throws IOException{
        Tweet tweet = new Tweet();
        tweet.setPraiseNum(0);
        tweet.setContents(content);
        tweet.setCommentNum(0);
        tweet.setState(1);
        tweet.setUnifiedId(unifiedId);
        tweet.setRecordTime(recordTime);
        Integer num = tweetMapper.maxId();
        if(num ==null)
            num = 1;
        else
            num ++;
        tweet.setTweetId(num);
        tweetMapper.insert(tweet);
        uploadPic(num,files);

        return num;
    }

    public int uploadPic(Integer tweetId,MultipartFile[] files)throws IOException {
        int num=0;
        if(files==null)return num;
        for(MultipartFile file :files) {
            num++;
            String url = webPath + "/tweetpic/" + tweetId + "/" +num+"/" +file.getOriginalFilename();
            //开始存文件
            //本地存储路径
            Picture picture = new Picture();
            picture.setPictureUrl(url);
            picture.setTweetId(tweetId);
            picture.setNumId(num);
            pictureMapper.insert(picture);

            String path = localPath + "\\tweetpic\\" + tweetId + "\\" +num;
            PictureUtils.saveUrl(file, path);
        }
        return num;
    }

}
