package com.btv.swiprefresh;

//public class ListViewActivity extends AppCompatActivity implements XListView.IXListViewListener {

//    private XListView mListView;
//    private Handler mHandler;
//    private ArrayList<String> nums;
//    private MyAdapter adapter;
//    private int i = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_view);
//        ButterKnife.inject(this);
//        InitData();
//    }
//
//    //刷新之后新添加数据
//    private ArrayList<String> getData() {
//        nums.add("新加" + (i++));
//        return nums;
//    }
//    //Toast.makeText(ListViewActivity.this, "asdfasfs", Toast.LENGTH_SHORT).show();
//
//
//    //初始化数据
//    private void InitData() {
//        /** 下拉刷新，上拉加载 */
//        nums = new ArrayList<String>();
//        for (int i = 0; i < 10; i++) {
//            nums.add(i + "");
//        }
//        mListView = (XListView) findViewById(R.id.techan_xListView);// 你这个listview是在这个layout里面
//        mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
//        mListView.setXListViewListener(this);
//        mHandler = new Handler();
//        adapter = new MyAdapter(this);
//        mListView.setAdapter(adapter);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ListViewActivity.this, "dsafsdafas", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    /**
//     * 停止刷新，
//     */
//    private void onLoad() {
//        mListView.stopRefresh();
//        mListView.stopLoadMore();
//        mListView.setRefreshTime("刚刚");
//    }
//
//    //开始下拉刷新
//    @Override
//    public void onRefresh() {
//        mHandler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                getData();
//                mListView.setAdapter(adapter);
//                onLoad();
//            }
//        }, 2000);
//
//    }
//
//    //上拉加载更多
//    @Override
//    public void onLoadMore() {
//        mHandler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                getData();
//                adapter.notifyDataSetChanged();
//                onLoad();
//            }
//        }, 2000);
//
//    }
//
//    //适配器,并且在适配器中添加侧滑按钮
//    private class MyAdapter extends BaseSwipeAdapter {
//        private Context mContext;
//
//        public MyAdapter(Context mContext) {
//            this.mContext = mContext;
//        }
//
//        @Override
//        public int getSwipeLayoutResourceId(int position) {
//            return R.id.swipe;
//        }
//
//        @Override
//        public View generateView(final int position, ViewGroup parent) {
//            final View v = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
//            SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
//            LinearLayout ll_content = (LinearLayout) v.findViewById(R.id.ll_content);
//            final TextView delet = (TextView) v.findViewById(R.id.delete);
//            if (position % 2 == 0) {
//                delet.setText("关注");
//            } else {
//                delet.setText("取消关注");
//            }
//            swipeLayout.addSwipeListener(new SimpleSwipeListener() {
//                @Override
//                public void onOpen(SwipeLayout layout) {
//                }
//            });
//            swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
//                @Override
//                public void onDoubleClick(SwipeLayout layout, boolean surface) {
//                    Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
//                }
//            });
//            ll_content.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(ListViewActivity.this, position + "", Toast.LENGTH_SHORT).show();
//                }
//            });
//            ll_content.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    Toast.makeText(ListViewActivity.this, "45646", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//            });
//            v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    if ("关注".equals(delet.getText())) {
//                        delet.setText("取消关注");
//                    } else {
//                        delet.setText("关注");
//                    }
//                }
//            });
//            return v;
//        }
//
//        @Override
//        public void fillValues(int position, View convertView) {
//        }
//
//        @Override
//        public int getCount() {
//            return nums.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return nums.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//    }
//}
