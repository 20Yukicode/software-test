package com.soa.springcloud.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.soa.springcloud.entities.Picture;
import com.soa.springcloud.entities.Subscription;
import com.soa.springcloud.entities.Tweet;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.mapper.*;
import com.soa.springcloud.service.LikesService;
import com.soa.springcloud.service.SubscriptionService;
import com.soa.springcloud.service.TweetService;
import com.soa.springcloud.util.PictureUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
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

    public static final String endpoint = "oss-cn-shanghai.aliyuncs.com";
    public static final String bucketName = "soa-user-resume";


    @Value("${file.localPath}")
    public void setLocalPath(String localPath) {
        TweetServiceImpl.localPath = localPath;
    }
    @Value("${file.webPath}")
    public void setWebPath(String webPath) {
        TweetServiceImpl.webPath = webPath;
    }

//    /**
//     * 关注列表
//     * @param unifiedId
//     * @return
//     */
//    public List<JSONObject> followList(Integer unifiedId){
//
//    }
//    /**
//     * 粉丝列表
//     * @param unifiedId
//     * @return
//     */
//    public List<JSONObject> fansList(Integer unifiedId){
//
//    }
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
        queryWrapper.eq("unified_id",intervieweeId).lt("tweet_id",momentId);
        List<Tweet> tweetList = tweetMapper.selectList(queryWrapper);

        int size = tweetList.size();
        if(size>10) size = 9;
        JSONArray jsonArray = new JSONArray();

        for(int i=tweetList.size()-1;i>=0&&size>0; i--){
            size--;

            JSONObject object = JSONUtil.parseObj(tweetList.get(i));
            Integer id = (Integer) object.get("tweetId");
            if(id==null)log.info(object.toString());

            object.put("likeState", likesService.getLikes(visitorId,object.getInt("tweetId")));
            object.put("simpleUserInfo", getSimpleUserInfo(object.getInt("unifiedId")));
            jsonArray.add(object);
        }
        //jsonArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getInt("tweetId")));
        return jsonArray;
    }

    @Override
    public JSONArray getTweetList(Integer visitorId, Integer momentId){
        //try { TimeUnit.MILLISECONDS.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
        JSONArray tweetArray = new JSONArray();

        QueryWrapper<Subscription> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unified_id",visitorId);
        List<Subscription> subscriptionList = subscriptionMapper.selectList(queryWrapper);
        Subscription subscription = new Subscription();
        subscription.setUnifiedId(visitorId);
        subscription.setSubscribeId(visitorId);
        subscriptionList.add(subscription);
//        if(subscriptionList.size()>=1)
//            tweetArray.add(JSONUtil.parseObj(subscriptionList.get(0)));
//        return tweetArray;
        if(subscriptionList!=null) {
            for (int i = 0; i < subscriptionList.size(); i++) {
                QueryWrapper<Tweet> tweetQueryWrapper = new QueryWrapper<>();
                JSONObject object = JSONUtil.parseObj(subscriptionList.get(i));
                tweetQueryWrapper.eq("unified_id", object.getInt("subscribeId"));
                List list = tweetMapper.selectList(tweetQueryWrapper);
                if(list !=null) {
                    for (int j = 0; j < list.size(); j++) {
                        JSONObject tweetObject = JSONUtil.parseObj(list.get(j));

                        tweetObject.put("likeState", likesService.getLikes(visitorId, tweetObject.getInt("tweetId")));
                        tweetObject.put("simpleUserInfo", getSimpleUserInfo(object.getInt("subscribeId")));
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
            object.put("pictureUrl", user.getPictureUrl());
            object.put("trueName", user.getTrueName());
            object.put("briefInfo", user.getBriefInfo());
            object.put("userType", user.getUserType());
            object.put("unifiedId",user.getUnifiedId());
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

    public int createTweet(Integer unifiedId, String content, Date recordTime, List<MultipartFile> files)throws IOException{
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

    public int uploadPic(Integer tweetId,List<MultipartFile> files)throws IOException {
        int num=0;
        if(files==null)return num;

        Integer size = files.size();

        for(MultipartFile file :files) {

            num++;
            //String url = webPath + "/tweetpic/" + tweetId + "/" +num+"/" +file.getOriginalFilename();
            String url="https://"+bucketName+"."+endpoint+ "/tweetpic/" + tweetId + "/" +num+"/" +file.getOriginalFilename();
            //开始存文件
            //本地存储路径
            Picture picture = new Picture();
            picture.setPictureUrl(url);
            picture.setTweetId(tweetId);
            picture.setNumId(num);
            pictureMapper.insert(picture);

            //String path = localPath + "\\tweetpic\\" + tweetId + "\\" +num;
            String path = "tweetpic/"+tweetId+"/"+num;
            PictureUtils.saveUrl(file, path);
        }
        return num;
    }
    public int maxTweetId(){
        return tweetMapper.maxId()+1;
    }

}
