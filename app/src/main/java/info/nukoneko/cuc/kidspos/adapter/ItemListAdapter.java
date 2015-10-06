package info.nukoneko.cuc.kidspos.adapter;import android.support.v7.widget.RecyclerView;import android.util.TypedValue;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.LinearLayout;import android.widget.TextView;import java.util.ArrayList;import butterknife.Bind;import butterknife.ButterKnife;import info.nukoneko.cuc.kidspos.R;import info.nukoneko.cuc.kidspos.model.ItemObject;/** * Created by TEJNEK on 2015/10/04. */public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {    private ArrayList<ItemObject> items = new ArrayList<>();    @Override    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {        View itemView = LayoutInflater.from(parent.getContext())                .inflate(R.layout.item_item_menu, parent, false);        itemView.setClickable(true);        TypedValue outValue = new TypedValue();        parent.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);        itemView.setBackgroundResource(outValue.resourceId);        return new ViewHolder(itemView);    }    @Override    public void onBindViewHolder(ViewHolder holder, int position) {        holder.price.setText(String.valueOf(this.items.get(position).price));        holder.name.setText(this.items.get(position).name);    }    @Override    public int getItemCount() {        return items.size();    }    public void add(ItemObject object){        this.items.add(object);        this.notifyDataSetChanged();    }    public void insert(ItemObject object){        this.items.add(0, object);    }    public class ViewHolder extends RecyclerView.ViewHolder{        @Bind(R.id.price) TextView price;        @Bind(R.id.name) TextView name;        public View v;        public ViewHolder(View view) {            super(view);            v = view;            ButterKnife.bind(this, view);        }        public View getView(){            return v;        }    }}