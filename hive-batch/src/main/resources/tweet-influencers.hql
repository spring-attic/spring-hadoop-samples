create external table if not exists tweetdata (value STRING) LOCATION '/tweets/input';

insert overwrite directory '/tweets/hiveout'
select r.retweetedUser, '\t', count(r.retweetedUser) as count 
  from tweetdata j 
       lateral view json_tuple(j.value, 'retweet', 'retweetedStatus') t as retweet, retweetedStatus 
       lateral view json_tuple(t.retweetedStatus, 'fromUser') r as retweetedUser
 where t.retweet = 'true' 
 group by r.retweetedUser order by count desc limit 10;

