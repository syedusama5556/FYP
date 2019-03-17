package usama.utech.firebasepractice.Adatpters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import usama.utech.firebasepractice.ModelClasses.User;
import usama.utech.firebasepractice.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private ArrayList<User> list = new ArrayList<>();

    public MyAdapter(ArrayList<User> mList) {
        this.list = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_design, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final User obj = list.get(position);
        holder.txt01.setText(obj.getName());
        holder.txt02.setText(obj.getId());
        holder.txt03.setText(obj.getAge());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseDatabase.getInstance().getReference("User").child(obj.getId()).setValue(null);
                HashMap<String, String> map = new HashMap<>();
                map.put("name", "abc");
                FirebaseDatabase.getInstance().getReference("User").child(obj.getId()).updateChildren((HashMap)map);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txt01;
        private TextView txt02;
        private TextView txt03;
        private LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            txt01 = (TextView) view.findViewById(R.id.txt_01);
            txt02 = (TextView) view.findViewById(R.id.txt_02);
            txt03 = (TextView) view.findViewById(R.id.txt_03);
            layout = view.findViewById(R.id.main_layout);
        }
    }
}
