/**
 * Copyright 2013 Joan Zapata
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ganke.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction class of a BaseAdapter in which you only need
 * to provide the convert() implementation.<br/>
 * Using the provided BaseAdapterHelper, your code is minimalist.
 * @param <T> The type of the items in the list.
 */
public abstract class BaseQuickAdapter<T, H extends BaseAdapterHelper> extends BaseAdapter {

    protected static final String TAG = BaseQuickAdapter.class.getSimpleName();

    protected final Context context;

    protected final int layoutResId;

    protected final List<T> datas;

    /**
     * Create a QuickAdapter.
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     */
    public BaseQuickAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization datas.
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     * @param datas        A new list is created out of this one to avoid mutable list
     */
    public BaseQuickAdapter(Context context, int layoutResId, List<T> datas) {
        this.datas = datas == null ? new ArrayList<T>() : new ArrayList<T>(datas);
        this.context = context;
        this.layoutResId = layoutResId;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= datas.size()) return null;
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final H helper = getAdapterHelper(position, convertView, parent);
        T item = getItem(position);
        convert(helper, item, position);
        helper.setAssociatedObject(item);
        return helper.getView();
    }


    @Override
    public boolean isEnabled(int position) {
        return position < datas.size();
    }

    public void add(T elem) {
        datas.add(elem);
        notifyDataSetChanged();
    }

    public void addAll(List<T> elem) {
        datas.addAll(elem);
        notifyDataSetChanged();
    }

    public void set(T oldElem, T newElem) {
        set(datas.indexOf(oldElem), newElem);
    }

    public void set(int index, T elem) {
        datas.set(index, elem);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
        datas.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        datas.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        datas.clear();
        datas.addAll(elem);
        notifyDataSetChanged();
    }

    public boolean contains(T elem) {
        return datas.contains(elem);
    }

    /** Clear datas list */
    public void clear() {
        datas.clear();
        notifyDataSetChanged();
    }


    /**
     * Implement this method and use the helper to adapt the view to the given item.
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    protected abstract void convert(H helper, T item, int position);

    /**
     * You can override this method to use a custom BaseAdapterHelper in order to fit your needs
     * @param position    The position of the item within the adapter's datas set of the item whose view we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct datas, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return An instance of BaseAdapterHelper
     */
    protected abstract H getAdapterHelper(int position, View convertView, ViewGroup parent);

}
