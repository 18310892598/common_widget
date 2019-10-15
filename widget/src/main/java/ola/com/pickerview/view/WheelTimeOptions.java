package ola.com.pickerview.view;

import android.view.View;

import java.util.ArrayList;

import ola.com.dialog.R;
import ola.com.pickerview.adapter.ArrayTimeWheelAdapter;
import ola.com.pickerview.listener.OnItemSelectedListener;
import ola.com.pickerview.timer.WheelTimerView;

public class WheelTimeOptions<T> {
	private View view;
	private WheelTimerView wvOption1;
	private WheelTimerView wvOption2;
	private WheelTimerView wvOption3;

	private ArrayList<T> mOptions1Items;
	private ArrayList<ArrayList<T>> mOptions2Items;
	private ArrayList<ArrayList<ArrayList<T>>> mOptions3Items;

    private boolean linkage = false;
    private OnItemSelectedListener wheelListenerOption1;
    private OnItemSelectedListener wheelListenerOption2;
    private OnItemSelectedListener wheelListenerOption3;
    private OnSelectChangeListener2 onSelectChangeListener2;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public WheelTimeOptions(View view) {
		super();
		this.view = view;
		setView(view);
	}

	public void setPicker(ArrayList<T> optionsItems) {
		setPicker(optionsItems, null, null, false);
	}

	public void setPicker(ArrayList<T> options1Items,
			ArrayList<ArrayList<T>> options2Items, boolean linkage) {
		setPicker(options1Items, options2Items, null, linkage);
	}

	public void setPicker(ArrayList<T> options1Items,
			ArrayList<ArrayList<T>> options2Items,
			ArrayList<ArrayList<ArrayList<T>>> options3Items,
			boolean linkage) {
        this.linkage = linkage;
		this.mOptions1Items = options1Items;
		this.mOptions2Items = options2Items;
		this.mOptions3Items = options3Items;
		int len = ArrayTimeWheelAdapter.DEFAULT_LENGTH;
		if (this.mOptions3Items == null) {
			len = 8;
		}
		if (this.mOptions2Items == null) {
			len = 12;
		}
		// 选项1
		wvOption1 = (WheelTimerView) view.findViewById(R.id.options1);
		// 设置显示数据
		wvOption1.setAdapter(new ArrayTimeWheelAdapter(mOptions1Items, len));
		// 初始化时显示的数据
		wvOption1.setCurrentItem(0);
		// 选项2
		wvOption2 = (WheelTimerView) view.findViewById(R.id.options2);
		if (mOptions2Items != null) {
			// 设置显示数据
			wvOption2.setAdapter(new ArrayTimeWheelAdapter(mOptions2Items.get(0)));
		}
		// 初始化时显示的数据
		wvOption2.setCurrentItem(wvOption1.getCurrentItem());
		// 选项3
		wvOption3 = (WheelTimerView) view.findViewById(R.id.options3);
		if (mOptions3Items != null) {
			// 设置显示数据
			wvOption3.setAdapter(new ArrayTimeWheelAdapter(mOptions3Items.get(0).get(0)));
		}
		// 初始化时显示的数据
		wvOption3.setCurrentItem(wvOption3.getCurrentItem());

		int textSize = 25;

		wvOption1.setTextSize(textSize);
		wvOption2.setTextSize(textSize);
		wvOption3.setTextSize(textSize);

		if (this.mOptions2Items == null) {
			wvOption2.setVisibility(View.GONE);
		}
		if (this.mOptions3Items == null) {
			wvOption3.setVisibility(View.GONE);
		}

		// 联动监听器
        wheelListenerOption1 = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(int index) {
				int opt2Select = 0;
				if (mOptions2Items != null) {
					//上一个opt2的选中位置
                    opt2Select = wvOption2.getCurrentItem();
					//新opt2的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                    opt2Select = opt2Select >= mOptions2Items.get(index).size() - 1 ? mOptions2Items.get(index).size() - 1 : opt2Select;

					wvOption2.setAdapter(new ArrayTimeWheelAdapter(mOptions2Items.get(index)));
					wvOption2.setCurrentItem(opt2Select);
				}
				if (mOptions3Items != null) {
                    wheelListenerOption2.onItemSelected(opt2Select);
				}
			}
		};
        wheelListenerOption2 = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(int index) {
				if (mOptions3Items != null) {
                    int opt1Select = wvOption1.getCurrentItem();
                    opt1Select = opt1Select >= mOptions3Items.size() - 1 ? mOptions3Items.size() - 1 : opt1Select;
                    index = index >= mOptions2Items.get(opt1Select).size() - 1 ?  mOptions2Items.get(opt1Select).size() - 1 : index;
					int opt3 = wvOption3.getCurrentItem();//上一个opt3的选中位置
                    //新opt3的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                    opt3 = opt3 >= mOptions3Items.get(opt1Select).get(index).size() - 1 ? mOptions3Items.get(opt1Select).get(index).size() - 1 : opt3;

					wvOption3.setAdapter(new ArrayTimeWheelAdapter(mOptions3Items.get(wvOption1.getCurrentItem()).get(index)));
					wvOption3.setCurrentItem(opt3);
//					onSelectChangeListener2.onSelectChange();

				}
			}
		};
		if (options2Items != null && linkage) {
			wvOption1.setOnItemSelectedListener(wheelListenerOption1);
		}
		if (options3Items != null && linkage) {
			wvOption2.setOnItemSelectedListener(wheelListenerOption2);
		}
	}

	public interface OnSelectChangeListener2 {
		void onSelectChange();
	}

	public void setOnSelectChangeListener2(
			OnSelectChangeListener2 onSelectChangeListener2) {
		this.onSelectChangeListener2 = onSelectChangeListener2;
	}
	/**
	 * 设置选项的单位
	 * @param label1 单位
	 * @param label2 单位
	 * @param label3 单位
	 */
	public void setLabels(String label1, String label2, String label3) {
		if (label1 != null) {
			wvOption1.setLabel(label1);
		}
		if (label2 != null) {
			wvOption2.setLabel(label2);
		}
		if (label3 != null) {
			wvOption3.setLabel(label3);
		}
	}

	/**
	 * 设置是否循环滚动
	 * @param cyclic 是否循环
	 */
	public void setCyclic(boolean cyclic) {
		wvOption1.setCyclic(cyclic);
		wvOption2.setCyclic(cyclic);
		wvOption3.setCyclic(cyclic);
	}

	/**
	 * 分别设置第一二三级是否循环滚动
	 * @param cyclic1,cyclic2,cyclic3 是否循环
	 */
	public void setCyclic(boolean cyclic1,boolean cyclic2,boolean cyclic3) {
        wvOption1.setCyclic(cyclic1);
        wvOption2.setCyclic(cyclic2);
        wvOption3.setCyclic(cyclic3);
	}
    /**
     * 设置第二级是否循环滚动
     * @param cyclic 是否循环
     */
    public void setOption2Cyclic(boolean cyclic) {
        wvOption2.setCyclic(cyclic);
    }

	/**
     * 设置第三级是否循环滚动
     * @param cyclic 是否循环
     */
    public void setOption3Cyclic(boolean cyclic) {
        wvOption3.setCyclic(cyclic);
    }

	/**
	 * 返回当前选中的结果对应的位置数组 因为支持三级联动效果，分三个级别索引，0，1，2
	 * @return 索引数组
     */
	public int[] getCurrentItems() {
		int[] currentItems = new int[3];
		currentItems[0] = wvOption1.getCurrentItem();
		currentItems[1] = wvOption2.getCurrentItem();
		currentItems[2] = wvOption3.getCurrentItem();
		return currentItems;
	}

	public void setCurrentItems(int option1, int option2, int option3) {
        if(linkage){
            itemSelected(option1, option2, option3);
        }
        wvOption1.setCurrentItem(option1);
        wvOption2.setCurrentItem(option2);
        wvOption3.setCurrentItem(option3);
	}

	private void itemSelected(int opt1Select, int opt2Select, int opt3Select) {
		if (mOptions2Items != null) {
			wvOption2.setAdapter(new ArrayTimeWheelAdapter(mOptions2Items
					.get(opt1Select)));
			wvOption2.setCurrentItem(opt2Select);
		}
		if (mOptions3Items != null) {
			wvOption3.setAdapter(new ArrayTimeWheelAdapter(mOptions3Items
					.get(opt1Select).get(
							opt2Select)));
			wvOption3.setCurrentItem(opt3Select);
		}
	}


}
