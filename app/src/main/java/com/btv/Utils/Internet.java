package com.btv.Utils;

/**
 * Created by Administrator on 2016/12/2.
 */

public class Internet {
    //        public static final String BASE_URL = MyApplication.getInstance().getB() + "btvrestapp/app/";
//    public static final String BASE_URL = "http://111.160.226.141:8989/btvrestapp/app/";
    public static final String BASE_URL = "http://111.160.226.141:8989/btvrestapp/app/";
    //登录
    public static final String LOGIN = BASE_URL + "v1.0/userLogin/login";
    //告警界面
    public static final String WARNING = BASE_URL + "v1.0/notification/home?_wadl";
    //告警搜索
    public static final String WARSEARCH = BASE_URL + "v1.0/notification/home?_wadl";
    //关注/取消警告：（确认告警与全部确认写在8.1接口中）
//    public static  final String ISWARNING=BASE_URL+"v1.0/isAttention/notification?_wadl";
    //待办列表即工单列表
    public static final String GONGDAN = BASE_URL + "v1.0/workForm/list";
    //关注资源显示：
    public static final String GUANZHU = BASE_URL + "v1.0/homeAttention/home";
    //机房一览显示：
    public static final String JIFANG = BASE_URL + "v1.0/room/view";
    //服务一览显示：
    public static final String FUWU = BASE_URL + "v1.0/bizsm/view";
    //工单详情
    public static final String GDDETAIL = BASE_URL + "v1.0/workOrder/detail";
    //新建工单页面数据
    public static final String SHOWGDDATA = BASE_URL + "v1.0/showData/show";
    //新建工单
    public static final String CREATGD = BASE_URL + "v1.0/newWorkForm/create";
    //首页公告
    public static final String GONGGAO = BASE_URL + "v1.0/homeNotice/notice";
    //关注和取消关注
    public static final String ISATTENTION = BASE_URL + "v1.0/isAttention/notification?_wadl";
    //确认和取消告警
    public static final String ISCONFIRM = BASE_URL + "v1.0/confirmNotification/modify?_wadl";


    //昝宏伟接的接口  start

    //监控资源界面
    public static final String MONITORING_RESOURCE = BASE_URL + "v1.0/monitor/command";
    //监控机房基本信息
    public static final String JIFANG_BASE_INFO = BASE_URL + "v1.0/roomDetails/info";



    //昝宏伟接的接口  end
}


//
//                                  _oo8oo_
//                                 o8888888o
//                                 88" . "88
//                                 (| -_- |)
//                                 0\  =  /0
//                               ___/'==='\___
//                             .' \\|     |// '.
//                            / \\|||  :  |||// \
//                           / _||||| -:- |||||_ \
//                          |   | \\\  -  /// |   |
//                          | \_|  ''\---/''  |_/ |
//                          \  .-\__  '-'  __/-.  /
//                        ___'. .'  /--.--\  '. .'___
//                     ."" '<  '.___\_<|>_/___.'  >' "".
//                    | | :  `- \`.:`\ _ /`:.`/ -`  : | |
//                    \  \ `-.   \_ __\ /__ _/   .-` /  /
//                =====`-.____`.___ \_____/ ___.`____.-`=====
//                                  `=---=`
//
//
//       ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//              佛祖保佑                                代码无Bug
//

